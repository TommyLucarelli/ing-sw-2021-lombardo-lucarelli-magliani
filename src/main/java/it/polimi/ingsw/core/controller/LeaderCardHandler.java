package it.polimi.ingsw.core.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.Flag;
import it.polimi.ingsw.core.model.LeaderCard;
import it.polimi.ingsw.core.model.Resource;
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
        boolean action = ms.getPayload().getAsBoolean();
        int lcID = ms.getPayload().getAsInt();
        int vp;
        check = false;
        JsonObject payload = new JsonObject();
        if (action) {
            lc = controller.getCurrentPlayer().getBoard().getLeader(lcID);
            check = checkRequirements(lc);
            if(check){
                lc.setAbilityActivation();
                controller.getCurrentGame().getTurn().setLeaderCardActivated(lc.getId());
                controller.getCurrentPlayer().getBoard().setAbilityActivationFlag(lc.getSpecialAbility().getAbilityType(), lcID);
            }
        } else {
            controller.getCurrentGame().getTurn().setLeaderCardDiscarded(lc.getId());
            controller.getCurrentPlayer().getBoard().removeLeaderCard(controller.getCurrentPlayer().getBoard().getLeader(lcID));
            controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 1, 0);
            payload.addProperty("gameAction", "MAIN_CHOICE");
            payload.addProperty("leader Card", 0);
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            return;
        }
        if(check){
            if(controller.getCurrentGame().getTurn().isEndGame()){
                controller.getCurrentGame().getTurn().setEndGame(false);
                if(controller.getCurrentGame().getTurn().isLastTurn())
                    vp = controller.getCurrentPlayer().getBoard().victoryPoints();
                //update
                controller.updateBuilder(false);
            } else{
                payload.addProperty("gameAction", "MAIN_CHOICE");
                payload.addProperty("leader Card", lc.getId());
                controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }
        }else{
            payload.addProperty("gameAction", "LEADER_ACTION");
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
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
