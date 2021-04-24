package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

public class TurnHandler implements PhaseHandler{

    public RequestMsg leaderCard(ResponseMsg ms){
        if(ms){
            //costruzione e invio messaggio leader_Activation
            return null;
        }
        else{
            //costruzione e invio messaggio main_choice
            return null;
        }
    }
}
