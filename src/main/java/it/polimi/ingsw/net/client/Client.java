package it.polimi.ingsw.net.client;

import com.google.gson.Gson;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.gui.Gui;

import java.io.FileNotFoundException;
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
    private final Timer pingTimer;
    private UserInterface ui;

    /**
     * Class constructor.
     * @param serverIp the IP address of the server
     * @param portNumber the port number of the server.
     * @param CLI_ON true if the CLI mode is activated.
     */
    public Client(String serverIp, int portNumber, boolean CLI_ON){
        this.serverIp = serverIp;
        this.portNumber = portNumber;
        this.pingTimer = new Timer();

        if(CLI_ON){
            try {
                ui = new Cli(this);
            } catch (FileNotFoundException e) {
                System.err.println("\nFileNotFoundException in Client - Failed to launch CLI");
            }
        } else {
            ui = new Gui(this);
        }

    }

    /**
     * Client class main method
     * @param args the arguments passed by the terminal
     */
    public static void main(String[] args) {
        Client client;

        /*
         * Sets the IP address and the port number passed as arguments, then starts the client execution.
         */
        if(args.length == 2){
            System.out.println("Launching with CLI on...");
            String[] parts = args[1].split(":");
            client = new Client(parts[0], Integer.parseInt(parts[1]), true);
            client.run();
        } else {
            System.out.println("Launching with GUI on...");
            String[] parts = args[0].split(":");
            client = new Client(parts[0], Integer.parseInt(parts[1]), false);
        }
    }

    /**
     * Passes the request sent by the server to the user interface
     * @param requestMsg the request sent by the server.
     */
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
            ui.connectionError();
            return;
        }
        System.out.println("Connection to server successful!");

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
    public void closeConnection() {
        if (server != null) {
            System.out.println("Closing connection with server...");

            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connection closed!");
        }
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
