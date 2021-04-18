package it.polimi.ingsw.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main server class.
 * @author Giacomo Lombardo
 */
public class Server {
    public final static int SOCKET_PORT = 7777;

    public static void main(String[] args) {
        ServerSocket socket;
        ExecutorService pool = Executors.newFixedThreadPool(100);

        /**
         * Initialize the ServerSocket.
         */
        try{
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e){
            System.err.println("IOException from Server: cannot open server socket");
            System.exit(1);
            return;
        }

        System.out.println("Server started on port " + SOCKET_PORT + " - Waiting for connections...");

        /**
         * Accept connection from clients.
         */
        try {
            while(true){
                pool.execute(new ClientHandler(socket.accept(), ServerUtils.numClients + 1));
                ServerUtils.numClients++;
                System.out.println("New client connected - number of clients currently connected: " + ServerUtils.numClients);
            }
        } catch (IOException e){
            System.err.println("IOException from Server");
        }
    }
}
