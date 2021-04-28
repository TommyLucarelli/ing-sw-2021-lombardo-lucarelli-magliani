package it.polimi.ingsw.net.server;

import com.google.gson.Gson;
import it.polimi.ingsw.net.client.QuitConnectionException;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class handles the connection to a client.
 * @author Giacomo Lombardo
 */
public class ClientHandler implements Runnable{
    private final int id;
    private String name;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Timer connectionDroppedTimer;
    private final RequestManager requestManager;
    private Lobby lobby;
    private final Gson gson;

    /**
     * Class constructor.
     * @param socket the instance of the socket representing the connection to the client
     * @param id the id of the client assigned automatically by the server
     */
    public ClientHandler(Socket socket, int id){
        this.requestManager = new RequestManager(this);
        this.socket = socket;
        this.id = id;
        this.connectionDroppedTimer = new Timer();
        this.gson = new Gson();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void run(){
        try{
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e){
            System.err.println("IOException from ClientHandler");
        }

        connectionDroppedTimer.schedule(new CloseConnectionTask(), 10000);

        /**
         * Main loop for client-server communication
         */
        try{
            while(!Thread.currentThread().isInterrupted()){
                ResponseMsg next = gson.fromJson((String)inputStream.readObject(), ResponseMsg.class);
                if(next.getMessageType() == MessageType.PING) restoreTimer();
                else {
                    try {
                        requestManager.handleRequest(next);
                    } catch (InvalidResponseException e) {
                        e.printStackTrace();
                    } catch (QuitConnectionException e) {
                        ServerUtils.numClients--;
                        ServerUtils.usernames.remove(name);
                        System.out.println("[clientId: " + id + "] Connection dropped - number of clients currently connected: " + ServerUtils.numClients);
                        break;
                    }
                }
            }
        } catch(IOException e){
            System.err.println("IOException from ClientHandler - Client ID: " + id);
        } catch (ClassNotFoundException e){
            System.err.println("ClassNotFoundException from ClientHandler - Client ID: " + id);
        }

        /**
         * Closes the connection.
         */
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void send(RequestMsg request){
        try {
            outputStream.writeObject(gson.toJson(request));
        } catch (IOException e) {
            System.err.println("[clientId: " + id + "] IOException in ClientHandler::send");
            disconnect();
        }
    }

    private void restoreTimer(){
        System.out.println("PInG");
        connectionDroppedTimer.cancel();
        connectionDroppedTimer = new Timer();
        connectionDroppedTimer.schedule(new CloseConnectionTask(), 10000);
    }

    private void disconnect(){
        System.out.println("[clientId: " + id + "] Closing connection...");

        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("[clientId: " + id + "] IOException in ClientHandler::disconnect");
        }

        ServerUtils.numClients--;
        ServerUtils.usernames.remove(name);
        System.out.println("[clientId: " + id + "] Connection closed - number of clients currently connected: " + ServerUtils.numClients);
        Thread.currentThread().interrupt();
    }

    private class CloseConnectionTask extends TimerTask{
        @Override
        public void run(){
            System.out.println("[clientId: " + id + "] Timeout reached");
            disconnect();
        }
    }
}