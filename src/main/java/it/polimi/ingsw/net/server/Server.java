package it.polimi.ingsw.net.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public final static int SOCKET_PORT = 7777;
    private static Set<String> usernames = new HashSet<>();

    public static void main(String[] args) {
        int numClients = 0;
        ServerSocket socket;
        ExecutorService pool = Executors.newFixedThreadPool(100);
        try{
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e){
            System.err.println("IOException from Server: cannot open server socket");
            System.exit(1);
            return;
        }

        System.out.println("Server started on port " + SOCKET_PORT + " - Waiting for connections...");
        try {
            while(true){
                pool.execute(new ClientHandler(socket.accept(), numClients + 1));
                numClients++;
                System.out.println("New client connected!");
            }
        } catch (IOException e){
            System.err.println("IOException from Server");
        }



    }

    public static boolean availableUsername(String username){
        if(!username.isBlank() && !usernames.contains(username)){
            usernames.add(username);
            return true;
        }
        return false;
    }

    private static class ClientHandler implements Runnable{
        private int id;
        private String name;
        private Socket socket;
        private ObjectInputStream inputStream;
        private ObjectOutputStream outputStream;

        public ClientHandler(Socket socket, int id){
            this.socket = socket;
            this.id = id;
        }

        public void run(){
            try{
                inputStream = new ObjectInputStream(socket.getInputStream());
                outputStream = new ObjectOutputStream(socket.getOutputStream());

                String welcome = "Welcome! Please enter a username: ";
                outputStream.writeObject((Object)welcome);
                while(true){
                    Object next = inputStream.readObject();
                    name = (String) next;
                    if(availableUsername(name)) break;
                    else outputStream.writeObject((Object)"This username is not available, please enter another one:");
                }
                outputStream.writeObject((Object)"Welcome, " + name + "!");

            } catch (IOException e){
                System.err.println("IOException from ClientHandler");
            } catch (ClassNotFoundException e){
                System.err.println("ClassNotFoundException from ClientHandler");
            }

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

        public void handleRequest(Object req){
            try{
                System.out.println((String) req);
                outputStream.writeObject("[SERVER] Received: " + req);
            } catch (IOException e){
                System.err.println("IOException in handleRequest - could not process the request.");
            }

        }
    }
}
