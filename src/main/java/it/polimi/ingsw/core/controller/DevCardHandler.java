package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.core.model.*;

import java.util.ArrayList;

public class DevCardHandler implements PhaseHandler{

    private MainController controller;
    private Board board;
    private DevCard devCard;

    public DevCardHandler(MainController controller){
        this.controller = controller;
    }

    @Override
    public boolean runPhase() {
        board = controller.getCurrentPlayer().getBoard();
        //costruzione messaggio CHOICE
        //arriva il messaggio dal client con la scelta della carta sviluppo come pos (i,j) nel devcardstructure
        if (affordable()){

        }

        return true;
    }

    public boolean affordable(){
        for (int i=0; i<4; i++) {
            if (board.personalResQtyToArray()[i] >= devCard.resQtyToArray()[i])
                return false;
        }
    return true;
    }
}
