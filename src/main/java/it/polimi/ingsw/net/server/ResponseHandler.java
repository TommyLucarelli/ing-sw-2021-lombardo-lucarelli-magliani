package it.polimi.ingsw.net.server;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

public class ResponseHandler {
    private ClientHandler client;

    public ResponseHandler(ClientHandler client){
        this.client = client;
    }

    public RequestMsg handleRequest(ResponseMsg response) throws InvalidResponseException {
        switch (response.getMessageType()){
            case REGISTRATION_MESSAGE:
                return handleRegistration(response.getPayload());
            default:
                return testingMessage(response.getPayload());
        }
    }

    public RequestMsg firstMessage(){
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "Welcome! Please enter a username:");
        return new RequestMsg(MessageType.REGISTRATION_MESSAGE, payload);
    }

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

    private RequestMsg testingMessage(JsonObject response){
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "Server received: " + response.get("message").toString());
        return new RequestMsg(MessageType.TESTING_MESSAGE, response);
    }



}
