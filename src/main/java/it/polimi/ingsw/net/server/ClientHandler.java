package it.polimi.ingsw.net.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class handles the connection to a client.
 * @author Giacomo Lombardo
 */
public class ClientHandler implements Runnable{
    private int id;
    private String name;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    /**
     * Class constructor.
     * @param socket the instance of the socket representing the connection to the client
     * @param id the id of the client assigned automatically by the server
     */
    public ClientHandler(Socket socket, int id){
        this.socket = socket;
        this.id = id;
    }

    public void run(){
        try{
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            /**
             * Requires the client to register with a unique username.
             */
            String welcome = "Welcome! Please enter a username: ";
            outputStream.writeObject((Object)welcome);
            while(true){
                Object next = inputStream.readObject();
                name = (String) next;
                if(!ServerUtils.usernames.contains(name)) break;
                else outputStream.writeObject((Object)"This username is not available, please enter another one:");
            }
            outputStream.writeObject((Object)"Welcome, " + name + "!");

        } catch (IOException e){
            System.err.println("IOException from ClientHandler");
        } catch (ClassNotFoundException e){
            System.err.println("ClassNotFoundException from ClientHandler");
        }

        /**
         * Main loop for receiving and handling the client requests.
         */
        try{
            while(true){
                Object next = inputStream.readObject();
                handleRequest(next);
            }
        } catch(IOException e){
            System.err.println("IOException from ClientHandler - Client ID: " + id);
        } catch (ClassNotFoundException e){
            System.err.println("ClassNotFoundException from ClientHandler - Client ID: " + id);
        }
    }

    /**
     * Handles the client request
     * @param req the client's request.
     */
    public void handleRequest(Object req){
        try{
            System.out.println("[" + name + "]: " + (String) req);
            outputStream.writeObject("[SERVER] Received: " + req);
        } catch (IOException e){
            System.err.println("IOException in handleRequest - could not process the request.");
        }

    }
}