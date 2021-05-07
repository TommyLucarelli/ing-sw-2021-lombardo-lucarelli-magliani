package it.polimi.ingsw;

import it.polimi.ingsw.net.client.Client;
import it.polimi.ingsw.net.server.Server;
import it.polimi.ingsw.view.ViewManager;

import java.util.Arrays;

public class Launcher {
    public static void main(String[] args) {
        if(args.length == 0){
            printHelpMessage("Missing argument:");
            return;
        }
        switch (args[0]){
            case "-s":
                Server.main(args);
                break;
            case "-c":
                if(args.length == 1){
                    printHelpMessage("Missing argument:");
                    return;
                }
                if(args[1].equals("-cli")){
                    if(args[2].matches("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)")){
                        Client.main(Arrays.copyOfRange(args, 1, 3));
                        return;
                    } else {
                        printHelpMessage("Invalid argument.");
                    }
                } else if (args[1].matches("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)")){
                    String[] parts = args[1].split(":");
                    Client.main(Arrays.copyOfRange(args, 1, 2));
                    return;
                } else {
                    printHelpMessage("Invalid argument.");
                }
                break;
            default:
                printHelpMessage("Invalid argument.");
                break;
        }
        System.exit(0);
    }

    /**
     * Prints a help message to the terminal.
     */
    public static void printHelpMessage(String errorMessage){
        System.out.println(errorMessage);
        System.out.println("-s to launch the server");
        System.out.println("-c IP_ADDRESS:PORT to launch the client in GUI mode");
        System.out.println("-c -cli IP_ADDRESS:PORT to launch the client in CLI mode");
    }
}
