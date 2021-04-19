package it.polimi.ingsw.net.server;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.client.QuitConnectionException;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

/**
 * Handles the various responses sent by the client.
 * @author Giacomo Lombardo
 */
public class ResponseHandler {
    private final ClientHandler client;

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
        if(!response.has("name")){
            throw new InvalidResponseException("Invalid response. Please enter a valid response");
        } else if(ServerUtils.usernames.contains(response.get("name").getAsString())){
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "This username is already taken, please enter another username.");
            return new RequestMsg(MessageType.REGISTRATION_MESSAGE, payload);
        } else {
            String name = response.get("name").getAsString();
            ServerUtils.usernames.add(name);
            client.setName(name);
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "Welcome, " + name +
                    "! Enter \"C\" to create a new lobby or join an existing one by entering its ID!");
            return new RequestMsg(MessageType.WELCOME_MESSAGE, payload);
        }
    }

    /**
     * ONLY FOR TESTING: Handler for the response to a testing message. Basically the communication enters an endless loop
     * of testing messages which is interrupted only if the client drops the connection.
     * @param response the response received from the client
     * @return the new request message which will be sent to the client
     * @throws InvalidResponseException if the received message hasn't the expected format/fields.
     */
    private RequestMsg testingMessage(JsonObject response){
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "Server received: " + response.get("message").toString());
        return new RequestMsg(MessageType.TESTING_MESSAGE, response);
    }



}
