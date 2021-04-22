package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.core.model.*;

import java.util.ArrayList;

public class DevCardHandler implements PhaseHandler{

    private MainController controller;
    private Board board;
    private DevCard devCard;
    private Resource resource;
    private int[] costArray = devCard.resQtyToArray();

    public DevCardHandler(MainController controller){
        this.controller = controller;
    }

    @Override
    public boolean runPhase() {
        board = controller.getCurrentPlayer().getBoard();
        ArrayList<Integer> checkPlace;
        //costruzione messaggio CHOICE
        //arriva il messaggio dal client con la scelta della carta sviluppo come pos (i,j) nel devcardstructure
        discount(4);
        discount(5);
        checkPlace = placeable();
        if (affordable() && checkPlace.size()>0) {
            //do{
            // invio messaggio al client per SCELTA RISORSE, in attesa di a,b,c
            // check = check_resources(a,b,c)
            // }while(!check);
        }
        //board.getStrongbox().decreaseResource(c);
        //board.getWarehouse().decResWarehouse(b, true);
        //board.getWarehouse().decResWarehouse(a, false);
        //chiedere piazzamento carta con messaggio PIAZZALACAZZODICARTA inviando Array CheckPlace
        //Sarà il client a controllare che il piazzamento sia giusto, il client invia INDICE index
        //board.getDevCardSlot(index).addCard(devCard);
        //SHORT_UPDATE
        return true;
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
            for (int i = 0; i < costArray.length; i++) {
                if (costArray.equals(resource))
                    costArray[i]--;
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

    public boolean check_resources(int[] ware, int[] specialWare, int[]strong) {
        for (int i = 0; i < costArray.length; i++) {
            if (costArray[i] != ware[i]+strong[i]+specialWare[i])
                return false;
        }
        return true;
    }
}
