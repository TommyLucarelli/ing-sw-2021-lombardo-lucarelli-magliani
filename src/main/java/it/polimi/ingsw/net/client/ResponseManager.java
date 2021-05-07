package it.polimi.ingsw.net.client;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.*;
import it.polimi.ingsw.view.cli.InputHandler;

/**
 * Handles the various requests messages (RequestMsg) from the server.
 * @author Giacomo Lombardo
 */
public class ResponseManager implements Runnable{
    private Client client;
    private RequestMsg request;

    protected ResponseManager(Client client, RequestMsg request){
        this.client = client;
        this.request = request;
    }

    @Override
    public void run(){
        ResponseMsg response;
        try {
            response = handleRequest(request);
            client.send(response);
        } catch (QuitConnectionException e) {
            response = new ResponseMsg(request.getIdentifier(), MessageType.QUIT_MESSAGE, null);
            client.send(response);
            client.closeConnection();
        }
    }

    /**
     * Main function that acts as a switch, forwarding the request to the relative handling function depending on the
     * messageType.
     * @param request the message received from the server.
     * @return the response which will be sent to the server.
     * @throws QuitConnectionException whenever the user decides to quit.
     */
    public synchronized ResponseMsg handleRequest(RequestMsg request) throws QuitConnectionException{
        if(request.getPayload().has("message"))
            System.out.println(request.getPayload().get("message").getAsString());
        JsonObject payload = new JsonObject();
        if(request.getPayload().has("expectedResponse")) {
            payload = InputHandler.getInput(request.getPayload().getAsJsonObject("expectedResponse"));
        } else {
            payload.addProperty("input", "received");
        }
        if(request.getPayload().has("gameAction")) {
            payload.addProperty("gameAction", request.getPayload().get("gameAction").getAsString());
        }
        return new ResponseMsg(request.getIdentifier(), request.getMessageType(), payload);
    }
}
