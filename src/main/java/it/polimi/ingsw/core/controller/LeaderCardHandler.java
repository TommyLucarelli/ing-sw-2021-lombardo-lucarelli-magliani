package it.polimi.ingsw.core.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.*;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.util.ArrayList;

public class LeaderCardHandler{

    private MainController controller;
    private boolean check;
    private LeaderCard lc;

    public LeaderCardHandler(MainController controller){
        this.controller = controller;
    }


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
                        controller.getCurrentGame().getDevCardStructure().discardSingle(c); //controllo boolean per endGame
                    }else{
                        if(payload2.get("shuffle").getAsBoolean()){
                            ((SingleBoard) controller.getCurrentPlayer().getBoard()).shuffleDeck();
                            controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 0, 1);
                        }else{
                            controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 0, 2);
                        }
                    }
                }
                //update
                controller.updateBuilder();
            } else {
                payload.addProperty("gameAction", "MAIN_CHOICE");
                controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }
        }


    }

    protected boolean checkRequirements(LeaderCard lc){
        int check1, check2;
        Resource r;
        ArrayList<Flag> flags;
        int resPlayer[];
        switch (lc.getSpecialAbility().getAbilityType()){
            case 0: //controllo nel warehouse e nello strongbox se ho 5 risorse del tipo r
                r = lc.getRequiredResources().getResource();
                resPlayer = controller.getCurrentPlayer().getBoard().personalResQtyToArray();
                if(resPlayer[r.ordinal()] >= 5)
                    return true;
                else
                    return false;
            case 1: //2 carte di una bandiera e 1 di un altra
                flags = lc.getRequiredFlags();
                check1 = controller.getCurrentPlayer().getBoard().countFlags(flags.get(0), false);
                check2 = controller.getCurrentPlayer().getBoard().countFlags(flags.get(1), false);
                if(check1 > 1 && check2 > 0)
                    return true;
                else
                    return false;
            case 2: //1 carta di una bandiera e 1 di un altra
                flags = lc.getRequiredFlags();
                check1 = controller.getCurrentPlayer().getBoard().countFlags(flags.get(0), false);
                check2 = controller.getCurrentPlayer().getBoard().countFlags(flags.get(1), false);
                if(check1 > 0 && check2 > 0)
                    return true;
                else
                    return false;
            case 3: //1 carta di una bandiera, ma di livello 2
                flags = lc.getRequiredFlags();
                check1 = controller.getCurrentPlayer().getBoard().countFlags(flags.get(0), true);
                if(check1 > 0)
                    return true;
                else
                    return false;
        }
        return true;
    }
}
