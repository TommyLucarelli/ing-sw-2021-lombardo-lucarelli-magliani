package it.polimi.ingsw.net.server;

import com.google.gson.JsonObject;
import it.polimi.ingsw.core.controller.PlayerHandler;
import it.polimi.ingsw.net.client.QuitConnectionException;
import it.polimi.ingsw.net.msg.*;

import java.util.concurrent.TimeUnit;

/**
 * Handles the various responses sent by the client.
 * @author Giacomo Lombardo
 */
public class RequestManager {
    private final ClientHandler client;
    private PlayerHandler playerHandler;

    /**
     * Class constructor.
     * @param client is necessary for some collateral effects of the functions.
     */
    public RequestManager(ClientHandler client){
        this.client = client;
    }

    /**
     * Main function that acts as a switch, depending on the message type received from the client.
     * @param response the response received from the client
     * @throws InvalidResponseException if the received message hasn't the expected format/fields.
     * @throws QuitConnectionException if the received message signals the end of the connection from the client.
     */
    public void handleRequest(ResponseMsg response) throws InvalidResponseException, QuitConnectionException {
        if(response.getMessageType() == MessageType.GAME_MESSAGE){
            System.out.println("[from ClientId: " + client.getId() + "]" + response.getMessageType() + " - " + response.getPayload().get("gameAction"));
        } else {
            System.out.println("[from ClientId: " + client.getId() + "]" + response.getMessageType());
        }
        switch (response.getMessageType()){
            case QUIT_MESSAGE:
                throw new QuitConnectionException();
            case FIRST_MESSAGE:
                sendFirstMessage();
                break;
            case REGISTRATION_MESSAGE:
                handleRegistration(response.getPayload());
                break;
            case RECONNECTION_MESSAGE:
                handleReconnection(response.getPayload());
                break;
            case WELCOME_MESSAGE:
                handleWelcome(response.getPayload());
                break;
            case NUMBER_OF_PLAYERS:
                handleCreateGame(response.getPayload());
                break;
            case GAME_MESSAGE:
                playerHandler.handleMessage(response);
                break;
            case JOIN_GAME:
                handleJoinGame(response.getPayload());
                break;
            default:
                testingMessage(response.getPayload());
                break;
        }
    }

    /**
     * Builds the first message of the communication.
     */
    public void sendFirstMessage(){
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "Welcome! Please enter a username:");
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "string");
        payload.add("expectedResponse", expectedResponse);
        client.send(new RequestMsg(MessageType.REGISTRATION_MESSAGE, payload));
    }

    /**
     * Handler for the response to a registration message. If the username is not available the request will be again
     * a registration request. If the username is instead available adds it to the list of registered ones and assigns it
     * to the clientHandler.
     * @param response the response received from the client
     * @throws InvalidResponseException if the received message hasn't the expected format/fields.
     */
    private void handleRegistration(JsonObject response) throws InvalidResponseException {
        String input = response.get("input").getAsString();
        input = input.trim();
        if(input.isBlank()) {
            throw new InvalidResponseException("Invalid username. Please enter a valid username");
        } else if(ServerUtils.disconnectedPlayers.containsKey(input)){
            client.setName(input);
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "Welcome back, " + input + "! Enter \"1\" if you want to resume the game you left, \"2\" if you want to start a new one.");
            payload.addProperty("username", input);
            JsonObject expectedResponse = new JsonObject();
            expectedResponse.addProperty("type", "int");
            expectedResponse.addProperty("min", 1);
            expectedResponse.addProperty("max", 2);
            payload.add("expectedResponse", expectedResponse);
            client.send(new RequestMsg(MessageType.RECONNECTION_MESSAGE, payload));
        } else if(ServerUtils.usernames.contains(input)){
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "This username is already taken, please enter another username.");
            payload.addProperty("error", true);
            JsonObject expectedResponse = new JsonObject();
            expectedResponse.addProperty("type", "string");
            payload.add("expectedResponse", expectedResponse);
            client.send(new RequestMsg(MessageType.REGISTRATION_MESSAGE, payload));
        } else {
            ServerUtils.usernames.add(input);
            client.setName(input);
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "Welcome, " + input +
                    "! Enter \"1\" to create a new lobby or \"2\" to join an existing one.");
            payload.addProperty("username", input);
            JsonObject expectedResponse = new JsonObject();
            expectedResponse.addProperty("type", "int");
            expectedResponse.addProperty("min", 1);
            expectedResponse.addProperty("max", 2);
            payload.add("expectedResponse", expectedResponse);
            client.send(new RequestMsg(MessageType.WELCOME_MESSAGE, payload));
        }
    }

    private void handleWelcome(JsonObject response) throws InvalidResponseException {
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
            client.send(new RequestMsg(MessageType.NUMBER_OF_PLAYERS, payload));
        } else if (choice == 2){
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "Enter the lobbyId: ");
            JsonObject expectedResponse = new JsonObject();
            expectedResponse.addProperty("type", "int");
            expectedResponse.addProperty("min", 10000);
            expectedResponse.addProperty("max", 99999);
            payload.add("expectedResponse", expectedResponse);
            client.send(new RequestMsg(MessageType.JOIN_GAME, payload));
        } else throw new InvalidResponseException("Invalid input. Enter \"1\" to create a new lobby or \"2\" to join an existing one.");
    }

    private void handleCreateGame(JsonObject response) throws InvalidResponseException {
        Lobby lobby = new Lobby(response.get("input").getAsInt());
        ServerUtils.lobbies.add(lobby);
        JsonObject payload = new JsonObject();
        if(lobby.getLobbySize() == 1){
            payload.addProperty("gameAction", "START_GAME_COMMAND");
            payload.addProperty("message", "Single player game initialized! Type \"start\" to start the game");
            payload.addProperty("activePlayerId", 0);
            JsonObject expectedResponse = new JsonObject();
            expectedResponse.addProperty("type", "string");
            expectedResponse.addProperty("regex", "(start)");
            payload.add("expectedResponse", expectedResponse);
        } else {
            payload.addProperty("gameAction", "WAIT_FOR_PLAYERS");
            payload.addProperty("message", "Lobby created successfully! Lobby ID: " + lobby.getId() + " - Waiting for other players to join");
        }
        client.send(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playerHandler = lobby.addPlayer(client.getId(), client.getName(), this);
    }

    private void handleJoinGame(JsonObject response){
        int id = response.get("input").getAsInt();
        String msg = "The specified lobby does not exist! Enter \"1\" to create a new lobby or \"2\" to join an existing one.";
        JsonObject payload = new JsonObject();
        for(Lobby lobby: ServerUtils.lobbies){
            if(lobby.getId() == id){
                try{
                    playerHandler = lobby.addPlayer(client.getId(), client.getName(), this);
                } catch (InvalidResponseException e){
                    msg = e.getErrorMessage();
                    break;
                }
                payload.addProperty("message", "You have successfully joined the lobby! Players currently in the lobby: " + lobby.getPlayersInLobby() +
                        " --- The game will be starting soon!");
                payload.addProperty("gameAction", "WAIT_START_GAME");
                client.send(new RequestMsg(MessageType.GAME_MESSAGE, payload));
                return;
            }
        }
        payload.addProperty("message", msg);
        payload.addProperty("error", true);
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "int");
        expectedResponse.addProperty("min", 1);
        expectedResponse.addProperty("max", 2);
        payload.add("expectedResponse", expectedResponse);
        client.send(new RequestMsg(MessageType.WELCOME_MESSAGE, payload));
    }

    public void sendGameMessage(RequestMsg msg){
        client.send(msg);
    }

    public void disconnection(){
        if(playerHandler != null){
            System.out.println("[CLIENT " + client.getId() +"] handling disconnection from game...");
            this.playerHandler.handleDisconnection();
            ServerUtils.disconnectedPlayers.put(client.getName(), playerHandler);
        }
    }

    public void handleReconnection(JsonObject response){
        if(response.get("input").getAsInt() == 1) {
            this.playerHandler = ServerUtils.disconnectedPlayers.get(client.getName());
            this.playerHandler.setManager(this);
            ServerUtils.disconnectedPlayers.remove(client.getName());
            playerHandler.handleReconnection();
        } else {
            ServerUtils.disconnectedPlayers.remove(client.getName());
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "Welcome, " + client.getName() +
                    "! Enter \"1\" to create a new lobby or \"2\" to join an existing one.");
            payload.addProperty("username", client.getName());
            JsonObject expectedResponse = new JsonObject();
            expectedResponse.addProperty("type", "int");
            expectedResponse.addProperty("min", 1);
            expectedResponse.addProperty("max", 2);
            payload.add("expectedResponse", expectedResponse);
            client.send(new RequestMsg(MessageType.WELCOME_MESSAGE, payload));
        }
    }

    /**
     * ONLY FOR TESTING: Handler for the response to a testing message. Basically the communication enters an endless loop
     * of testing messages which is interrupted only if the client drops the connection.
     * @param response the response received from the client
     * @return the new request message which will be sent to the client
     */
    private void testingMessage(JsonObject response){
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "Server received: " + response.get("input").toString());
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "string");
        payload.add("expectedResponse", expectedResponse);
        client.send(new RequestMsg(MessageType.TESTING_MESSAGE, payload));
    }
}