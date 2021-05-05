package it.polimi.ingsw.view;

import it.polimi.ingsw.net.client.Client;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.view.compact.CompactBoard;
import it.polimi.ingsw.view.compact.CompactDevCardStructure;
import it.polimi.ingsw.view.compact.CompactMarket;
import it.polimi.ingsw.view.compact.CompactPlayer;

import java.util.ArrayList;

public class ViewManager {
    private CompactBoard board;
    private CompactMarket market;
    private CompactDevCardStructure devCardStructure;
    private ArrayList<CompactPlayer> otherPlayers;
    private Client client;
    private UserInterface ui;

    public ViewManager(String ip, int port, boolean CLI_ON){
        this.client = new Client(ip, port);
        this.client.run();
        if(CLI_ON){
            // this.ui = new Cli();
        } else {
            // this.ui = new Gui();
        }
    }

    public void handle(RequestMsg request){

    }
}
