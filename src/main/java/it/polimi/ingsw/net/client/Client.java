package it.polimi.ingsw.net.client;

import it.polimi.ingsw.net.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Main class for the client side of the network.
 * @author Giacomo Lombardo
 */
public class Client implements Runnable{
    private String serverIp;
    private int portNumber;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    /**
     * Class constructor.
     * @param serverIp the IP address of the server
     * @param portNumber the port number of the server.
     */
    public Client(String serverIp, int portNumber){
        this.serverIp = serverIp;
        this.portNumber = portNumber;
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
        Scanner scanner = new Scanner(System.in);
        String in;

        Socket server;
        try{
            server = new Socket(serverIp, Server.SOCKET_PORT);
            outputStream = new ObjectOutputStream(server.getOutputStream());
            inputStream = new ObjectInputStream(server.getInputStream());
        } catch (IOException e){
            System.err.println("IOException from Client::run - server unreachable");
            return;
        }
        System.out.println("Connected to server successful! Type \"quit\" to close the connection.");

        try{
            System.out.println(inputStream.readObject());
        } catch (IOException e){
            System.err.println("IOException in Client - communication with server failed");
        } catch (ClassNotFoundException e){
            System.err.println("ClassNotFoundException in Client - communication with server failed");
        }


        try {
            while(true){
                in = scanner.nextLine();
                if(in.equals("quit")) break;
                outputStream.writeObject(in);
                System.out.println(inputStream.readObject());
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
