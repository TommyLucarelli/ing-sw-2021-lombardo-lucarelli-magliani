package it.polimi.ingsw.net.client;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.util.Scanner;

/**
 * Handles the various requests messages (RequestMsg) from the server.
 * @author Giacomo Lombardo
 */
public class RequestHandler {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Main function that acts as a switch, forwarding the request to the relative handling function depending on the
     * messageType.
     * @param request the message received from the server.
     * @return the response which will be sent to the server.
     * @throws QuitConnectionException whenever the user decides to quit.
     */
    public ResponseMsg handleRequest(RequestMsg request) throws QuitConnectionException{
        switch (request.getMessageType()){
            case REGISTRATION_MESSAGE:
                return handleRegistration(request);
            case WELCOME_MESSAGE:
                return handleWelcome(request);
            default:
                return handleTesting(request);
        }
    }

    /**
     * Handler for the registration message sent by the server
     * @param request the message received from the server
     * @return the response which will be sent to the server.
     * @throws QuitConnectionException whenever the user decides to quit.
     */
    private ResponseMsg handleRegistration(RequestMsg request) throws QuitConnectionException{
        System.out.println(request.getPayload().get("message").getAsString());
        String name = scanner.nextLine();
        if(name.equals("quit")) throw new QuitConnectionException();
        JsonObject payload = new JsonObject();
        payload.addProperty("name", name);
        return new ResponseMsg(request.getIdentifier(), MessageType.REGISTRATION_MESSAGE, payload);
    }

    /**
     * Handler for the welcome message sent by the server
     * @param request the message received from the server
     * @return the response which will be sent to the server.
     * @throws QuitConnectionException whenever the user decides to quit.
     */
    private ResponseMsg handleWelcome(RequestMsg request) throws QuitConnectionException{
        System.out.println(request.getPayload().get("message").getAsString());
        String input = scanner.nextLine();
        if(input.equals("quit")) throw new QuitConnectionException();
        JsonObject payload = new JsonObject();
        payload.addProperty("message", input);
        return new ResponseMsg(request.getIdentifier(), MessageType.WELCOME_MESSAGE, payload);
    }

    /**
     * ONLY FOR TESTING: Handler for the testing message sent by the server
     * @param request the message received from the server
     * @return the response which will be sent to the server.
     * @throws QuitConnectionException whenever the user decides to quit.
     */
    private ResponseMsg handleTesting(RequestMsg request) throws QuitConnectionException{
        System.out.println(request.getPayload().get("message").getAsString());
        String input = scanner.nextLine();
        if(input.equals("quit")) throw new QuitConnectionException();
        JsonObject payload = new JsonObject();
        payload.addProperty("message", input);
        return new ResponseMsg(request.getIdentifier(), MessageType.TESTING_MESSAGE, payload);
    }
}
