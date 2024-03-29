package it.polimi.ingsw;

import it.polimi.ingsw.net.client.Client;
import it.polimi.ingsw.net.server.Server;

import java.util.Arrays;

/**
 * Application launcher. Depending on the args launches the server or the client with GUI or CLI.
 * If the parameters are invalid or missing prints a help message and then exits.
 */
public class Launcher {
    /**
     * Main method launched at the beginning of the execution
     * @param args the arguments passed via terminal
     */
    public static void main(String[] args) {
        if(args.length == 0){
            printHelpMessage("Missing argument:");
            return;
        }
        switch (args[0]){
            case "-s":
                if(args.length == 1){
                    printHelpMessage("Missing argument:");
                    return;
                } else {
                    int portNumber;
                    try{
                        portNumber = Integer.parseInt(args[1]);
                        if(portNumber > 1 && portNumber < 65535) {
                            Server.main(Arrays.copyOfRange(args, 1, 2));
                            return;
                        } else printHelpMessage("Invalid argument.");
                    } catch (NumberFormatException e){
                        printHelpMessage("Invalid argument.");
                    }
                }
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
     * @param errorMessage The error to be printed at the beginning of the help message
     */
    public static void printHelpMessage(String errorMessage){
        System.out.println(errorMessage);
        System.out.println("-s PORT_NUMBER to launch the server");
        System.out.println("-c IP_ADDRESS:PORT_NUMBER to launch the client in GUI mode");
        System.out.println("-c -cli IP_ADDRESS:PORT_NUMBER to launch the client in CLI mode");
    }
}
