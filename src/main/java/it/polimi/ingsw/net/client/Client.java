package it.polimi.ingsw.net.client;

import it.polimi.ingsw.net.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
    private String serverIp;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String in;

        System.out.println("Please enter the IP address of the server: ");
        serverIp = scanner.nextLine();

        Socket server;
        try{
            server = new Socket(serverIp, Server.SOCKET_PORT);
            outputStream = new ObjectOutputStream(server.getOutputStream());
            inputStream = new ObjectInputStream(server.getInputStream());
            while(true){
                Object next = inputStream.readObject();
                System.out.println((String)next);
                outputStream.writeObject((Object)scanner.nextLine());
            }
        } catch (IOException e){
            System.err.println("IOException from Client::run - server unreachable");
            return;
        } catch (ClassNotFoundException e){
            System.err.println("ClassNotFoundException from Client::run");
        }
    }
}
