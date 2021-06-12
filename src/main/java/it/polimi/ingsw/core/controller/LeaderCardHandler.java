package it.polimi.ingsw.core.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.*;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.util.ArrayList;

/**
 * Class that handles the activation or discard of a Leader Card
 * @author Martina Magliani
 */
public class LeaderCardHandler{

    private MainController controller;
    private boolean check;
    private LeaderCard lc;

    public LeaderCardHandler(MainController controller){
        this.controller = controller;
    }


    /**
     * Method to manage the action made by the player on the leader card.
     * If the action is possible it will be done, otherwise it will be signaled to the user that he cannot activate the leader card.
     * @param ms client message
     */
    public void leaderAction(ResponseMsg ms){
        //parse risposta con carta scelta -> lcID e  azione -> action boolean (T -> activate) (F -> discard)
        boolean action = ms.getPayload().get("action").getAsBoolean();
        int lcID = ms.getPayload().get("cardID").getAsInt();
        int vp;
        check = false;
        JsonObject payload = new JsonObject();
        if (action) {
            lc = controller.getCurrentPlayer().getBoard().getLeader(lcID);
            check = checkRequirements(lc);
            if(check){
                lc.setAbilityActivation();
                controller.getCurrentPlayer().getBoard().setAbilityActivationFlag(lc.getSpecialAbility().getAbilityType(), lcID);
            }
            if(check){
                if(controller.getCurrentGame().getTurn().isEndGame()){
                    controller.getCurrentGame().getTurn().setEndGame(false);
                    //update
                    controller.updateBuilder();
                } else{
                    payload.addProperty("gameAction", "MAIN_CHOICE");
                    Gson gson = new Gson();
                    String json = gson.toJson(controller.getCurrentPlayer().getBoard().getAbilityActivationFlag());
                    payload.addProperty("abilityActivationFlag", json);
                    controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
                }
            }else{
                payload.addProperty("gameAction", "LEADER_ACTION");
                payload.addProperty("problem", true);
                controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }
        } else {
            lc = controller.getCurrentPlayer().getBoard().getLeader(lcID);
            controller.getCurrentGame().getTurn().setLeaderCardDiscarded(lc.getId());
            controller.getCurrentPlayer().getBoard().removeLeaderCard(controller.getCurrentPlayer().getBoard().getLeader(lcID));
            controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 1, 0);
            if(controller.getCurrentGame().getTurn().isEndGame()){
                controller.getCurrentGame().getTurn().setEndGame(false);
                //QUA PRENDERE I TOKEN
                if(controller.getCurrentGame().getSinglePlayer()){
                    Gson gson = new Gson();
                    SoloActionToken sat = ((SingleBoard)controller.getCurrentPlayer().getBoard()).getSoloActionToken();
                    JsonObject payload2 = sat.getAction();
                    ((SingleTurn)controller.getCurrentGame().getTurn()).setSoloActionToken(sat);
                    if(payload2.get("type").getAsInt() == 0){
                        Colour c = gson.fromJson(payload2.get("colour").getAsString(), Colour.class);
                        if(controller.getCurrentGame().getDevCardStructure().discardSingle(c))
                            controller.getCurrentGame().getTurn().setLastTurn(4);
                    }else{
                        if(payload2.get("shuffle").getAsBoolean()){
                            ((SingleBoard) controller.getCurrentPlayer().getBoard()).shuffleDeck();
                            controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 0, 1);
                        }else{
                            controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 0, 2);
                        }
                    }
                    if(controller.getCurrentGame().getTurn().isLastTurn() == 4)
                        controller.finalUpdate(controller.getCurrentPlayer().getPlayerID());
                    else
                        controller.updateBuilder();
                }else{
                    controller.updateBuilder();
                }
            } else {
                payload.addProperty("gameAction", "MAIN_CHOICE");
                Gson gson = new Gson();
                String json = gson.toJson(controller.getCurrentGame().getTurn().getLeaderCardDiscarded());
                payload.addProperty("discardedLeaderCards", json);
                controller.getCurrentGame().getTurn().resetDiscarded();
                controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }
        }


    }

    /**
     * Method for checking the requirements to activate a leader card.
     * @param lc Leader card
     * @return True or false depending on whether the requirements are met or not
     */
    protected boolean checkRequirements(LeaderCard lc){
        int check1, check2;
        Resource r;
        ArrayList<Flag> flags;
        int resPlayer[];
        switch (lc.getSpecialAbility().getAbilityType()){
            case 0: //controllo nel warehouse e nello strongbox se ho 5 risorse del tipo r
                r = lc.getRequiredResources().getResource();
                resPlayer = controller.getCurrentPlayer().getBoard().personalResQtyToArray();
                return resPlayer[r.ordinal()] >= 5;
            case 1: //2 carte di una bandiera e 1 di un altra
                flags = lc.getRequiredFlags();
                check1 = controller.getCurrentPlayer().getBoard().countFlags(flags.get(0), false);
                check2 = controller.getCurrentPlayer().getBoard().countFlags(flags.get(1), false);
                return check1 > 0 && check2 > 1;
            case 2: //1 carta di una bandiera e 1 di un altra
                flags = lc.getRequiredFlags();
                check1 = controller.getCurrentPlayer().getBoard().countFlags(flags.get(0), false);
                check2 = controller.getCurrentPlayer().getBoard().countFlags(flags.get(1), false);
                return check1 > 0 && check2 > 0;
            case 3: //1 carta di una bandiera, ma di livello 2
                flags = lc.getRequiredFlags();
                check1 = controller.getCurrentPlayer().getBoard().countFlags(flags.get(0), true);
                return check1 > 0;
        }
        return true;
    }
}
