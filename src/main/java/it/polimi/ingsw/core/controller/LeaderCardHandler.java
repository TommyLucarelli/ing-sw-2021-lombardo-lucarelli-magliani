package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.core.model.Flag;
import it.polimi.ingsw.core.model.LeaderCard;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import javax.swing.*;
import java.util.ArrayList;

public class LeaderCardHandler{

    private MainController controller;
    private boolean action;
    private boolean check;
    private int lcID;
    private LeaderCard lc;

    public LeaderCardHandler(MainController controller){
        this.controller = controller;
        check = true;
    }


    public RequestMsg leaderAction(ResponseMsg rm){
        //parse risposta con carta scelta -> lcID e  azione -> action boolean (T -> activate) (F -> discard)
        if (action) {
            lc = controller.getCurrentPlayer().getBoard().getLeader(lcID);
            check = checkRequirements(lc);
            if(check){
                lc.setAbilityActivation();
                controller.getCurrentPlayer().getBoard().setAbilityActivationFlag(lc.getSpecialAbility().getAbilityType(), lcID);
            }
        } else {
            controller.getCurrentPlayer().getBoard().removeLeaderCard(controller.getCurrentPlayer().getBoard().getLeader(lcID));
            controller.getCurrentGame().FaithTrackUpdate(controller.getCurrentPlayer(), 1, 0);
        }
        if(check){
            if(controller.getCurrentGame().getTurn().isEndGame()){
                controller.getCurrentGame().getTurn().setEndGame(false);
                
                //costruzione e ritorno messaggio update
            } else{
                //costruzione e invio messaggio mainChoice
            }

        }else{
            //costruzione e ritorno messaggio leaderAction
        }

         return null;
    }

    protected boolean checkRequirements(LeaderCard lc){
        int check1, check2;
        Resource r;
        ArrayList<Flag> flags;
        int resPlayer[] ;
        switch (lc.getSpecialAbility().getAbilityType()){
            case 0: //controllo nel warehouse e nello strongbox se ho 5 risorse del tipo r
                r = lc.getRequiredResources().get(0).getResource();
                resPlayer = controller.getCurrentPlayer().getBoard().personalResQtyToArray();
                    switch (r){
                        case COIN:
                            if(resPlayer[0] >= 5)
                                return true;
                            else
                                return false;
                        case STONE:
                            if(resPlayer[1] >= 5)
                                return true;
                            else
                                return false;
                        case SHIELD:
                            if(resPlayer[2] >= 5)
                                return true;
                            else
                                return false;
                        case SERVANT:
                            if(resPlayer[3] >= 5)
                                return true;
                            else
                                return false;
                    }
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
