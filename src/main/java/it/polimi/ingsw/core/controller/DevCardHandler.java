package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.core.model.*;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.util.ArrayList;

public class DevCardHandler{

    private MainController controller;
    private Board board;
    private DevCard devCard;
    private Resource resource;
    private int[] costArray;

    public DevCardHandler(MainController controller){
        this.controller = controller;
    }


    public RequestMsg chooseDevCard(ResponseMsg rm) {
        board = controller.getCurrentPlayer().getBoard();
        ArrayList<Integer> checkPlace;
        int i=0,j=0;
        //arriva il messaggio dal client con la scelta della carta sviluppo come pos (i,j) nel devcardstructure
        devCard = controller.getCurrentGame().getDevCardStructure().getTopCard(i,j);
        costArray = devCard.resQtyToArray();
        discount(4);
        discount(5);
        checkPlace = placeable();
        if (affordable() && checkPlace.size() > 0) {
            board.getWarehouse().decResWarehouse(costArray, false);
            board.getWarehouse().decResWarehouse(costArray, true);
            board.getStrongbox().decreaseResource(costArray);
            devCard = controller.getCurrentGame().getDevCardStructure().drawCard(i,j);
            //preparazione invio messaggio placement con payload devCardPlacement
        } else {
            //preparazione e invio messaggio CHOOSE_DEVCARD
        }
        return null;
    }

    public RequestMsg devCardPlacement(ResponseMsg ms){
        int index = 0;
        //arriva posizione di dove mettere la carta nel devcard slot
        board.getDevCardSlot(index).addCard(devCard);
        devCard = null;
        //controllo 7 carte ed eventualmente messaggio UPDATE
        if(controller.getCurrentPlayer().getBoard().numberOfDevCard()>=7){
            controller.getCurrentGame().getTurn().setLastTurn(true);
            //invio messaggio update
        }
        //costruzione messagio short update o leader_Activation
        return null;
    }

    /**
     * @return true if the player can buy the card with his resources
     */
    public boolean affordable(){
        for (int i=0; i<4; i++) {
            if (board.personalResQtyToArray()[i] >= devCard.resQtyToArray()[i])
                return false;
        }
    return true;
    }

    /**
     * This method modify the array cost if a LeaderCard with a SpecialAbility of SpecialDiscount is activated
     * @param x is the type of specialAbility
     */
    public void discount(int x){
        if (controller.getCurrentPlayer().getBoard().isActivated(x) != 0) {
            resource = controller.getCurrentPlayer().getBoard().getLeader(controller.getCurrentPlayer().getBoard().isActivated(x)).getSpecialAbility().getAbilityResource();
            switch (resource) {
                case COIN:
                    costArray[0]--;
                    break;
                case STONE:
                    costArray[1]--;
                    break;
                case SHIELD:
                    costArray[2]--;
                    break;
                case SERVANT:
                    costArray[3]--;
                    break;
            }
        }
    }

    /**
     * @return true if the card is placeable
     */
    public ArrayList<Integer> placeable() {
        board = controller.getCurrentPlayer().getBoard();
        ArrayList<Integer> check = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            if ((board.getDevCardSlot(i).getSlot().size() != 0)) {
                if (board.getDevCardSlot(i).getTopCard().getFlag().getLevel() < devCard.getFlag().getLevel()
                        && board.getDevCardSlot(i).getTopCard().getFlag().getLevel() > devCard.getFlag().getLevel() + 2)
                    check.add(i);
            }
            else{
                if (board.getDevCardSlot(i).getTopCard().getFlag().getLevel()==1)
                    check.add(i);
            }
        }
        return check;
    }
}