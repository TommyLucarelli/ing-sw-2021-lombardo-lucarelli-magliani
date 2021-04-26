package it.polimi.ingsw.net.client;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.*;

/**
 * Handles the various requests messages (RequestMsg) from the server.
 * @author Giacomo Lombardo
 */
public class RequestHandler {

    /**
     * Main function that acts as a switch, forwarding the request to the relative handling function depending on the
     * messageType.
     * @param request the message received from the server.
     * @return the response which will be sent to the server.
     * @throws QuitConnectionException whenever the user decides to quit.
     */
    public ResponseMsg handleRequest(RequestMsg request) throws QuitConnectionException{
        if(request.getPayload().has("message"))
            System.out.println(request.getPayload().get("message").getAsString());
        if(request.getMessageType() == MessageType.GAME_MESSAGE) return handleGameRequest(request);
        JsonObject payload = new JsonObject();
        if(request.getPayload().has("expectedResponse")) {
            payload = InputHandler.getInput(request.getPayload().getAsJsonObject("expectedResponse"));
        } else {
            payload.addProperty("input", "received");
        }
        return new ResponseMsg(request.getIdentifier(), request.getMessageType(), payload);
    }

    private ResponseMsg handleGameRequest(RequestMsg request){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", request.getPayload().get("gameAction").getAsString());
        if(request.getPayload().has("expectedResponse")) {
            payload = InputHandler.getInput(request.getPayload().getAsJsonObject("expectedResponse"));
        } else {
            payload.addProperty("input", "received");
        }
        return new ResponseMsg(request.getIdentifier(), request.getMessageType(), payload);
    }
}
