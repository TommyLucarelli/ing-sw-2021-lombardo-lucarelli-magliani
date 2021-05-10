package it.polimi.ingsw.view.cli;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.client.Client;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.UserInterface;

/**
 * Main class for CLI operations.
 */
public class Cli implements UserInterface {
    Client client;

    /**
     * Class constructor.
     * @param client the client socket used to send messages to the server.
     */
    public Cli(Client client) {
        this.client = client;
    }

    @Override
    public void handleRequest(RequestMsg request){
        if(request.getPayload().has("message"))
            System.out.println(request.getPayload().get("message").getAsString());
        switch(request.getMessageType()){
            case REGISTRATION_MESSAGE:
            case WELCOME_MESSAGE:
            case JOIN_GAME:
            case NUMBER_OF_PLAYERS:
            case START_GAME:
                handleSimpleRequest(request);
                break;
            case WAIT_START_GAME:
                ackSimpleRequest(request);
                break;
            case GAME_MESSAGE:
                switch (request.getPayload().get("gameAction").getAsString()){
                    case "START_GAME_COMMAND":
                        handleSimpleRequest(request);
                        break;
                    case "WAIT_FOR_PLAYERS":
                    case "WAIT_START_GAME":
                    case "SHORT_UPDATE":
                        ackSimpleRequest(request);
                    case "CHOOSE_START_LEADERS":
                        chooseStartLeaders(request);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Method used to handle "simple" requests from the server: a simple request consists in a message and an expected
     * response such as a number or a string, with no collateral effects on the client.
     * @param requestMsg the request sent by the server.
     */
    private void handleSimpleRequest(RequestMsg requestMsg) {
        JsonObject payload = InputHandler.getInput(requestMsg.getPayload().getAsJsonObject("expectedResponse"));
        if(requestMsg.getMessageType() == MessageType.GAME_MESSAGE)
            payload.addProperty("gameAction", requestMsg.getPayload().get("gameAction").getAsString());
        client.send(new ResponseMsg(requestMsg.getIdentifier(), requestMsg.getMessageType(), payload));
    }

    /**
     * Method used to handle "simple" requests from the server which need only an acknowledgment. These requests consist
     * of a simple text message with no collateral effects.
     * @param requestMsg the request sent by the server.
     */
    private void ackSimpleRequest(RequestMsg requestMsg) {
        if(requestMsg.getMessageType() == MessageType.GAME_MESSAGE){
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", requestMsg.getPayload().get("gameAction").getAsString());
            client.send(new ResponseMsg(requestMsg.getIdentifier(), requestMsg.getMessageType(), payload));
        } else {
            client.send(new ResponseMsg(requestMsg.getIdentifier(), requestMsg.getMessageType(), null));
        }
    }

    private void chooseStartLeaders(RequestMsg requestMsg){
        //Should print the four leader cards but in absence of a method that does that, for now, prints the array of IDs
        System.out.println(requestMsg.getPayload().get("leaderCards").getAsJsonArray());
        
    }
}
