package it.polimi.ingsw.core.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.*;
import it.polimi.ingsw.net.msg.MessageType;
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


    public void chooseDevCard(ResponseMsg ms) {
        board = controller.getCurrentPlayer().getBoard();
        ArrayList<Integer> checkPlace;
        //arriva il messaggio dal client con la scelta della carta sviluppo come pos (i,j) nel devcardstructure
        int i = ms.getPayload().get("line").getAsInt();
        int j = ms.getPayload().get("column").getAsInt();
        devCard = controller.getCurrentGame().getDevCardStructure().getTopCard(i,j);
        costArray = devCard.resQtyToArray();
        discount(4);
        discount(5);
        checkPlace = placeable();
        System.out.println("check size: "+checkPlace.size()+"affordable: "+affordable());
        if (affordable() && checkPlace.size() > 0) {
            board.getWarehouse().decResWarehouse(costArray); //questo array viene modificato o ho bisogno di ritornarlo
            board.getStrongbox().decreaseResource(costArray);
            devCard = controller.getCurrentGame().getDevCardStructure().drawCard(i,j);
            //preparazione invio messaggio placement con payload devCardPlacement
            Gson gson = new Gson();
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "DEVCARD_PLACEMENT");
            String json = gson.toJson(checkPlace);
            payload.addProperty("freeSpots", json);
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        } else {
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "CHOOSE_DEVCARD");
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        }
    }

    public void devCardPlacement(ResponseMsg ms){
        //arriva posizione di dove mettere la carta nel devcard slot
        int index = ms.getPayload().get("index").getAsInt();
        board.getDevCardSlot(index).addCard(devCard);
        devCard = null;
        //controllo 7 carte ed eventualmente messaggio UPDATE -> fine turno
        if(controller.getCurrentPlayer().getBoard().numberOfDevCard()>=7){
            controller.getCurrentGame().getTurn().setLastTurn(true);
            //update
            controller.updateBuilder();
        }
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "LEADER_ACTIVATION");
        controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
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
            costArray[resource.ordinal()]--;
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
                if (devCard.getFlag().getLevel() == 1)
                    check.add(i);
            }
        }
        return check;
    }
}