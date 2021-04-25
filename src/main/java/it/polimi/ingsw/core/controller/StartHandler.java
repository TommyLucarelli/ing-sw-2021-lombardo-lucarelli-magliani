package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

public class StartHandler {

    public RequestMsg startGame(ResponseMsg ms) {
        //costruzione e invio messaggio Short_update
        return null;
    }

    public RequestMsg join(ResponseMsg ms) {
        //costruzione e invio messaggio Short_Update
        return null;
    }

    public RequestMsg startMatch(ResponseMsg ms) {
        //costruzione e invio messaggio Short_Update
        return null;
    }

    public RequestMsg startResAndLeader(ResponseMsg ms) {
        //costruzione e invio messaggio Choose_start_resourcesLeaders
        //qua vanno pescate 4 carte leader
        //inoltre bisogna dire al giocatore il numero di risorse che può prendere
        return null;
    }
}