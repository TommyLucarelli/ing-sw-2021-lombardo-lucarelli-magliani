package it.polimi.ingsw.net.server;

import com.google.gson.Gson;
import it.polimi.ingsw.net.client.QuitConnectionException;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
    private final ResponseHandler responseHandler;
    private Lobby lobby;

    /**
     * Class constructor.
     * @param socket the instance of the socket representing the connection to the client
     * @param id the id of the client assigned automatically by the server
     */
    public ClientHandler(Socket socket, int id){
        this.responseHandler = new ResponseHandler(this);
        this.socket = socket;
        this.id = id;
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

    protected void joinLobby(Lobby lobby){
        this.lobby = lobby;
    }

    public void run(){
        Gson gson = new Gson();
        try{
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e){
            System.err.println("IOException from ClientHandler");
        }

        /**
         * Preparing the first request to the client
         */
        RequestMsg requestMsg = responseHandler.firstMessage();

        /**
         * Main loop for client-server communication
         */
        try{
            while(true){
                outputStream.writeObject(gson.toJson(requestMsg));
                ResponseMsg next = gson.fromJson((String)inputStream.readObject(), ResponseMsg.class);
                try {
                    requestMsg = responseHandler.handleRequest(next);
                } catch (InvalidResponseException e) {
                    outputStream.writeObject(gson.toJson(requestMsg));
                    //TODO: migliorare la gestione degli errori
                } catch (QuitConnectionException e) {
                    ServerUtils.numClients--;
                    ServerUtils.usernames.remove(name);
                    System.out.println("[clientId: " + id + "] Connection dropped - number of clients currently connected: " + ServerUtils.numClients);
                    break;
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
}