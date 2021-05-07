package it.polimi.ingsw.net.client;

import com.google.gson.Gson;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.cli.Cli;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;

/**
 * Main class for the client side of the network.
 * @author Giacomo Lombardo
 */
public class Client implements Runnable{
    private final String serverIp;
    private final int portNumber;
    private Socket server;
    private ObjectOutputStream out;
    private Timer pingTimer;
    private RequestHandler requestHandler;
    private UserInterface ui;

    /**
     * Class constructor.
     * @param serverIp the IP address of the server
     * @param portNumber the port number of the server.
     */
    public Client(String serverIp, int portNumber, boolean CLI_ON){
        this.serverIp = serverIp;
        this.portNumber = portNumber;
        this.pingTimer = new Timer();
        /*
        if(CLI_ON){
            ui = new Cli(this);
        } else {
            ui = new Gui(this);
        }
         */
        this.ui = new Cli(this);
    }

    public static void main(String[] args) {
        Client client;

        /*
         * Sets the IP address and the port number passed as arguments, then starts the client execution.
         */
        if(args.length == 2){
            System.out.println("Launching with CLI on...");
            String[] parts = args[1].split(":");
            client = new Client(parts[0], Integer.parseInt(parts[1]), true);
        } else {
            System.out.println("Launching with CLI off...");
            String[] parts = args[0].split(":");
            client = new Client(parts[0], Integer.parseInt(parts[1]), false);
        }

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

    public void setRequestHandler(RequestHandler requestHandler){
        this.requestHandler = requestHandler;
    }

    protected void handleRequest(RequestMsg requestMsg){
        this.ui.handleRequest(requestMsg);
    }

    @Override
    public void run() {
        /*
         * Establishes the connection to the server, then launches the listener thread to catch the messages sent by
         * the server.
         */
        try{
            server = new Socket(serverIp, portNumber);
            out = new ObjectOutputStream(server.getOutputStream());
            ServerListener serverListener = new ServerListener(this,  new ObjectInputStream(server.getInputStream()));
            new Thread(serverListener).start();
        } catch (IOException e){
            System.err.println("IOException from Client::run - server unreachable");
            return;
        }
        System.out.println("Connected to server successful! Type \"quit\" to close the connection.");

        /*
         * Sends the first message to the server.
         */
        send(new ResponseMsg(null, MessageType.FIRST_MESSAGE, null));

        /*
         * Starts pinging the server.
         */
        pingTimer.scheduleAtFixedRate(new Ping(this), 1000, 5000);

    }

    /**
     * Method used to close the connection to the server safely.
     */
    protected void closeConnection(){
        System.out.println("Closing connection with server...");
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed!");
    }

    /**
     * Method used to send a message to the server.
     * @param msg the message to be sent.
     */
    public void send(ResponseMsg msg){
        Gson gson = new Gson();
        try {
            out.writeObject(gson.toJson(msg));
        } catch (IOException e) {
            System.err.println("IOException in Client::send - couldn't send message to server");
            closeConnection();
        }
    }
}
