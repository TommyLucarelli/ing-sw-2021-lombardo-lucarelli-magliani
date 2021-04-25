package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

public class TurnHandler {

    public RequestMsg mainChoice(ResponseMsg ms) {
        if (ms.equals(0)) {
            //costruzione e invio messaggio Pick
            return null;
        } else if (ms.equals(1)) {
            //costruzione e invio messaggio Choose_Production
            return null;
        } else{
            //costruzione e invio messaggio Choose_DevCard
            return null;
        }
    }

    public RequestMsg leaderCard(ResponseMsg ms){
        if(ms.equals(1)){
            //costruzione e invio messaggio leader_Activation
            return null;
        }
        else{
            //costruzione e invio messaggio main_choice
            return null;
        }
    }
}
