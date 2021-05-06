package it.polimi.ingsw.net.client;

import com.google.gson.Gson;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Runnable class that listens to the server and processes the received messages.
 *
 * @author Giacomo Lombardo
 */
public class ServerListener implements Runnable {
    private final ObjectInputStream in;
    private final Client client;

    /**
     * Class constructor.
     * @param client the client related to the listener
     * @param in the input stream from the server
     */
    protected ServerListener(Client client, ObjectInputStream in) {
        this.client = client;
        this.in = in;
    }


    @Override
    public void run(){
        Gson gson = new Gson();
        RequestMsg serverRequest = null;
        ResponseMsg response;
        try {
            /*
             * Main loop: reads the message from the server, passes the request to the RequestHandler
             */
            while (true) {
                serverRequest = gson.fromJson((String) in.readObject(), RequestMsg.class);
                this.client.handleRequest(serverRequest);
            }
        } catch (IOException e){
            System.err.println("IOException in ServerListener - communication with server failed");
        } catch (ClassNotFoundException e){
            System.err.println("ClassNotFoundException in ServerListener - communication with server failed");
        }
    }
}
