package it.polimi.ingsw.net.client;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.util.Scanner;

public class RequestHandler {
    private Scanner scanner = new Scanner(System.in);

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

    private ResponseMsg handleRegistration(RequestMsg request) throws QuitConnectionException{
        System.out.println(request.getPayload().get("message").getAsString());
        String name = scanner.nextLine();
        if(name.equals("quit")) throw new QuitConnectionException();
        JsonObject payload = new JsonObject();
        payload.addProperty("name", name);
        return new ResponseMsg(request.getIdentifier(), MessageType.REGISTRATION_MESSAGE, payload);
    }

    private ResponseMsg handleWelcome(RequestMsg request) throws QuitConnectionException{
        System.out.println(request.getPayload().get("message").getAsString());
        String input = scanner.nextLine();
        if(input.equals("quit")) throw new QuitConnectionException();
        JsonObject payload = new JsonObject();
        payload.addProperty("message", input);
        return new ResponseMsg(request.getIdentifier(), MessageType.WELCOME_MESSAGE, payload);
    }

    private ResponseMsg handleTesting(RequestMsg request) throws QuitConnectionException{
        System.out.println(request.getPayload().get("message").getAsString());
        String input = scanner.nextLine();
        if(input.equals("quit")) throw new QuitConnectionException();
        JsonObject payload = new JsonObject();
        payload.addProperty("message", input);
        return new ResponseMsg(request.getIdentifier(), MessageType.TESTING_MESSAGE, payload);
    }
}
