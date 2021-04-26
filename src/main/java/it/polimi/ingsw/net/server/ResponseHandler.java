package it.polimi.ingsw.net.server;

import com.google.gson.JsonObject;
import it.polimi.ingsw.core.controller.PlayerHandler;
import it.polimi.ingsw.net.client.QuitConnectionException;
import it.polimi.ingsw.net.msg.*;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Handles the various responses sent by the client.
 * @author Giacomo Lombardo
 */
public class ResponseHandler {
    private final ClientHandler client;
    private PlayerHandler playerHandler;
    private MessageType lastType;

    /**
     * Class constructor.
     * @param client is necessary for some collateral effects of the functions.
     */
    public ResponseHandler(ClientHandler client){
        this.client = client;
    }

    /**
     * Main function that acts as a switch, depending on the message type received from the client.
     * @param response the response received from the client
     * @return the new request message which will be sent to the client
     * @throws InvalidResponseException if the received message hasn't the expected format/fields.
     * @throws QuitConnectionException if the received message signals the end of the connection from the client.
     */
    public RequestMsg handleRequest(ResponseMsg response) throws InvalidResponseException, QuitConnectionException {
        switch (response.getMessageType()){
            case QUIT_MESSAGE:
                throw new QuitConnectionException();
            case REGISTRATION_MESSAGE:
                return handleRegistration(response.getPayload());
            case WELCOME_MESSAGE:
                return handleWelcome(response.getPayload());
            case NUMBER_OF_PLAYERS:
                return handleCreateGame(response.getPayload());
            case GAME_MESSAGE:
            case PING:
                if(playerHandler.isActivePlayer() || playerHandler.isNewUpdate()){
                    return playerHandler.nextMessage(response);
                } else {
                    return pingMessage();
                }
            case JOIN_GAME:
                return handleJoinGame(response.getPayload());
            default:
                return testingMessage(response.getPayload());
        }
    }

    /**
     * Builds the first message of the communication.
     * @return the first message to be sent to the client
     */
    public RequestMsg firstMessage(){
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "Welcome! Please enter a username:");
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "string");
        payload.add("expectedResponse", expectedResponse);
        return new RequestMsg(MessageType.REGISTRATION_MESSAGE, payload);
    }

    /**
     * Handler for the response to a registration message. If the username is not available the request will be again
     * a registration request. If the username is instead available adds it to the list of registered ones and assigns it
     * to the clientHandler.
     * @param response the response received from the client
     * @return the new request message which will be sent to the client
     * @throws InvalidResponseException if the received message hasn't the expected format/fields.
     */
    private RequestMsg handleRegistration(JsonObject response) throws InvalidResponseException {
        String input = response.get("input").getAsString();
        if(input.isBlank()){
            throw new InvalidResponseException("Invalid username. Please enter a valid username");
        } else if(ServerUtils.usernames.contains(input)){
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "This username is already taken, please enter another username.");
            JsonObject expectedResponse = new JsonObject();
            expectedResponse.addProperty("type", "string");
            payload.add("expectedResponse", expectedResponse);
            return new RequestMsg(MessageType.REGISTRATION_MESSAGE, payload);
        } else {
            ServerUtils.usernames.add(input);
            client.setName(input);
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "Welcome, " + input +
                    "! Enter \"1\" to create a new lobby or \"2\" to join an existing one.");
            JsonObject expectedResponse = new JsonObject();
            expectedResponse.addProperty("type", "int");
            expectedResponse.addProperty("min", 1);
            expectedResponse.addProperty("max", 2);
            payload.add("expectedResponse", expectedResponse);
            return new RequestMsg(MessageType.WELCOME_MESSAGE, payload);
        }
    }

    private RequestMsg handleWelcome(JsonObject response) throws InvalidResponseException {
        int choice = 0;
        try{
            choice = Integer.parseInt(response.get("input").getAsString());
        } catch (NumberFormatException e){
            throw new InvalidResponseException("Please enter a valid input.");
        }
        if(choice == 1){
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "How many players will the game have?");
            JsonObject expectedResponse = new JsonObject();
            expectedResponse.addProperty("type", "int");
            expectedResponse.addProperty("min", 1);
            expectedResponse.addProperty("max", 4);
            payload.add("expectedResponse", expectedResponse);
            return new RequestMsg(MessageType.NUMBER_OF_PLAYERS, payload);
        } else if (choice == 2){
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "Enter the lobbyId: ");
            JsonObject expectedResponse = new JsonObject();
            expectedResponse.addProperty("type", "int");
            expectedResponse.addProperty("min", 10000);
            expectedResponse.addProperty("max", 99999);
            payload.add("expectedResponse", expectedResponse);
            return new RequestMsg(MessageType.JOIN_GAME, payload);
        } else throw new InvalidResponseException("Invalid input. Enter \"1\" to create a new lobby or \"2\" to join an existing one.");
    }

    private RequestMsg handleCreateGame(JsonObject response) throws InvalidResponseException {
        Lobby lobby = new Lobby(response.get("input").getAsInt());
        playerHandler = lobby.addPlayer(client.getId(), client.getName());
        ServerUtils.lobbies.add(lobby);
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "WAIT_FOR_PLAYERS");
        payload.addProperty("message", "Lobby created successfully! Lobby ID: " + lobby.getId() + " - Waiting for other players to join");
        return new RequestMsg(MessageType.GAME_MESSAGE, payload);
    }

    private RequestMsg handleJoinGame(JsonObject response){
        int id = response.get("input").getAsInt();
        JsonObject payload = new JsonObject();
        for(Lobby lobby: ServerUtils.lobbies){
            if(lobby.getId() == id){
                try{
                    playerHandler = lobby.addPlayer(client.getId(), client.getName());
                } catch (InvalidResponseException e){
                    payload.addProperty("message", e.getErrorMessage());
                    return new RequestMsg(MessageType.ERROR_MESSAGE, payload);
                }
                payload.addProperty("message", "You have successfully joined the lobby! Players currently in the lobby: " + lobby.getPlayersInLobby() +
                        " --- The game will be starting soon!");
                payload.addProperty("gameAction", "WAIT_START_GAME");
                return new RequestMsg(MessageType.GAME_MESSAGE, payload);
            }
        }
        payload.addProperty("message", "The specified lobby does not exist! Enter \"1\" to create a new lobby or \"2\" to join an existing one.");
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "int");
        expectedResponse.addProperty("min", 1);
        expectedResponse.addProperty("max", 2);
        payload.add("expectedResponse", expectedResponse);
        return new RequestMsg(MessageType.WELCOME_MESSAGE, payload);
    }

    public RequestMsg pingMessage(){
        Timer timer = new Timer();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new RequestMsg(MessageType.PING, new JsonObject());
    }

    /**
     * ONLY FOR TESTING: Handler for the response to a testing message. Basically the communication enters an endless loop
     * of testing messages which is interrupted only if the client drops the connection.
     * @param response the response received from the client
     * @return the new request message which will be sent to the client
     */
    private RequestMsg testingMessage(JsonObject response){
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "Server received: " + response.get("input").toString());
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "string");
        payload.add("expectedResponse", expectedResponse);
        return new RequestMsg(MessageType.TESTING_MESSAGE, payload);
    }



}
