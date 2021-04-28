package it.polimi.ingsw.net.client;

import com.google.gson.Gson;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerListener implements Runnable {
    private final ObjectInputStream in;
    private final ResponseManager responseManager;
    private final Client client;

    protected ServerListener(Client client, ObjectInputStream in) {
        this.client = client;
        this.in = in;
        this.responseManager = new ResponseManager();
    }


    @Override
    public void run(){
        Gson gson = new Gson();
        RequestMsg serverRequest = null;
        ResponseMsg response;
        try {
            while (true) {
                try {
                    serverRequest = gson.fromJson((String) in.readObject(), RequestMsg.class);
                    response = responseManager.handleRequest(serverRequest);
                    client.send(response);
                } catch (QuitConnectionException e) {
                    /*
                     * Whenever the user decides to quit, an exception is thrown: the client sends a "quit" message to
                     * the server, then proceeds to close the connection.
                     */
                    response = new ResponseMsg(serverRequest.getIdentifier(), MessageType.QUIT_MESSAGE, null);
                    client.send(response);
                    break;
                }
            }
        } catch (IOException e){
            System.err.println("IOException in Client - communication with server failed");
        } catch (ClassNotFoundException e){
            System.err.println("ClassNotFoundException in Client - communication with server failed");
        }
    }
}
