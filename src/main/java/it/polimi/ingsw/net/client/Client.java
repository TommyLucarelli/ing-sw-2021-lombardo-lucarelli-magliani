package it.polimi.ingsw.net.client;

import com.google.gson.Gson;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;

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
    private final ResponseManager responseManager;
    private Socket server;
    private ObjectOutputStream out;
    private Timer pingTimer;

    /**
     * Class constructor.
     * @param serverIp the IP address of the server
     * @param portNumber the port number of the server.
     */
    public Client(String serverIp, int portNumber){
        this.serverIp = serverIp;
        this.portNumber = portNumber;
        this.responseManager = new ResponseManager();
        this.pingTimer = new Timer();
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

        send(new ResponseMsg(null, MessageType.FIRST_MESSAGE, null));
        pingTimer.scheduleAtFixedRate(new Ping(this), 1000, 5000);

    }

    protected void closeConnection(){
        System.out.println("Closing connection with server...");
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed!");
    }

    protected void send(ResponseMsg msg){
        Gson gson = new Gson();
        try {
            out.writeObject(gson.toJson(msg));
        } catch (IOException e) {
            System.err.println("IOException in Client::send - couldn't send message to server");
            e.printStackTrace();
        }
    }
}
