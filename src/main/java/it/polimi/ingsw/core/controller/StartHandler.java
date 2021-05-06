package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.util.ArrayList;

public class StartHandler {

    private MainController controller;

    public StartHandler(MainController controller){
        this.controller = controller;
    }

    public RequestMsg startGame(ResponseMsg ms) {
        //TODO: create game
        System.out.println("startGame");
        return controller.handleTestMessage(ms.getPayload());
    }

    public RequestMsg startMatch(ResponseMsg ms) {
        //costruzione e invio messaggio Short_Update
        //pescaggio carte e invio messaggio start leaders
        int[] cardID = new int[4];

        for(int i=0; i<4;i++){
            controller.getCurrentPlayer().getBoard().addLeaderCard(controller.getCurrentGame().getLeaderCards().drawCard());
            cardID[i] = controller.getCurrentPlayer().getBoard().getLeaderCard(i).getId();
        }
        //invio messaggio con cardID

        return null;
    }


    public RequestMsg chooseStartLeaders(ResponseMsg ms) {
        //arrivo scelta carte leader array con id carte scartate
        //invio messaggio start resources
        int discardedID[] = new int[2];
        controller.getCurrentPlayer().getBoard().removeLeaderCard(controller.getCurrentPlayer().getBoard().getLeader(discardedID[0]));
        controller.getCurrentPlayer().getBoard().removeLeaderCard(controller.getCurrentPlayer().getBoard().getLeader(discardedID[1]));
        switch (controller.getCurrentGame().getTurn().getCurrentPlayer()){
            case 1: //messaggio update
            case 2: //messaggio choose resources 1
            case 3: //messaggio choose resources 1 + 1 punto fede
            case 4: //messaggio choose resources 2 + 1 punto fede
        }

        return null;
    }

    public RequestMsg chooseStartResources(ResponseMsg ms) {
        //arrivo risorse da piazzare placed
        //controller.getCurrentPlayer().getBoard().getWarehouse().updateConfiguration(placed);
        //messaggio di attesa inizio gioco
        return null;
    }


}