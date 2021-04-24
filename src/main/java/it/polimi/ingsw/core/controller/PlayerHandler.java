package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.net.msg.GameRequestMsg;
import it.polimi.ingsw.net.msg.GameResponseMsg;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

public class PlayerHandler {
    private int playerId;
    private MainController controller;

    protected PlayerHandler(int playerId, MainController controller){
        this.playerId = playerId;
        this.controller = controller;
    }

    public int getPlayerId;

    public RequestMsg nextMessage(ResponseMsg responseMsg){
        return controller.handle(responseMsg);
    }

}
