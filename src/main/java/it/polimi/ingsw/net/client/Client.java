package it.polimi.ingsw.net.client;

import com.google.gson.Gson;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Main class for the client side of the network.
 * @author Giacomo Lombardo
 */
public class Client implements Runnable{
    private final String serverIp;
    private final int portNumber;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private final RequestHandler requestHandler;

    /**
     * Class constructor.
     * @param serverIp the IP address of the server
     * @param portNumber the port number of the server.
     */
    public Client(String serverIp, int portNumber){
        this.serverIp = serverIp;
        this.portNumber = portNumber;
        this.requestHandler = new RequestHandler();
    }

    public static void main(String[] args) {
        String ip;
        int port;

        /**
         * Checks that the arguments passed are correct. If some arguments are missing or there are invalid arguments
         * prints a help message and ends the execution.
         */
        if(args.length != 4){
            printHelpMessage();
            return;
        }
        if(args[0].equals("-s")){
            ip = args[1];
        } else {
            printHelpMessage();
            return;
        }
        if(args[2].equals("-p")){
            port = Integer.parseInt(args[3]);
        } else {
            printHelpMessage();
            return;
        }

        /**
         * Sets the IP address and the port number passed as arguments, then starts the client execution.
         */
        Client client = new Client(ip, port);
        client.run();
    }

    /**
     * Prints a help message to the terminal.
     */
    public static void printHelpMessage(){
        System.out.println("To execute the client, add the following arguments: ");
        System.out.println("-s the IP address of the server");
        System.out.println("-p the port of the server");
        System.out.println("Example: java Client -s 127.0.0.1 -p 7777");
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        Socket server;
        try{
            server = new Socket(serverIp, portNumber);
            outputStream = new ObjectOutputStream(server.getOutputStream());
            inputStream = new ObjectInputStream(server.getInputStream());
        } catch (IOException e){
            System.err.println("IOException from Client::run - server unreachable");
            return;
        }
        System.out.println("Connected to server successful! Type \"quit\" to close the connection.");

        RequestMsg requestMsg = null;
        ResponseMsg responseMsg;

        /**
         * Receives the first message from the server
         */
        try {
            requestMsg = gson.fromJson((String) inputStream.readObject(), RequestMsg.class);
        } catch (IOException e) {
            System.err.println("IOException in Client - cannot receive first request");
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException in Client - cannot receive first request");
        }

        /**
         * Main loop for client-server communication: the client receives the message, passes it to the requestHandler which
         * returns the response for the message.
         */
        try {
            while(true){
                try {
                    responseMsg = requestHandler.handleRequest(requestMsg);
                    outputStream.writeObject(gson.toJson(responseMsg));
                } catch (QuitConnectionException e) {
                    /**
                     * Whenever the user decides to quit, an exception is thrown: the client sends a "quit" message to
                     * the server, then proceeds to close the connection.
                     */
                    responseMsg = new ResponseMsg(requestMsg.getIdentifier(), MessageType.QUIT_MESSAGE, null);
                    outputStream.writeObject(gson.toJson(responseMsg));
                    break;
                }
                requestMsg = gson.fromJson((String) inputStream.readObject(), RequestMsg.class);
            }
            System.out.println("Closing connection with server...");
            server.close();
            System.out.println("Connection closed!");
        } catch (IOException e){
            System.err.println("IOException in Client - communication with server failed");
        } catch (ClassNotFoundException e){
            System.err.println("ClassNotFoundException in Client - communication with server failed");
        }
    }
}
