package it.polimi.ingsw.net.client;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

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
        System.out.println(request.getPayload().get("message").getAsString());
        JsonObject payload = new JsonObject();
        if(request.getPayload().has("expectedResponse")) {
            payload = InputHandler.getInput(request.getPayload().getAsJsonObject("expectedResponse"));
        } else {
            payload.addProperty("input", "received");
        }
        return new ResponseMsg(request.getIdentifier(), request.getMessageType(), payload);
    }

}
