package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.net.server.RequestManager;

public class PlayerHandler {
    private final int playerId;
    private final String username;
    private final MainController controller;
    private final RequestManager manager;

    protected PlayerHandler(int playerId, String username, MainController controller, RequestManager manager){
        this.username = username;
        this.playerId = playerId;
        this.controller = controller;
        this.manager = manager;
    }

    public int getPlayerId() {
        return playerId;
    }

    protected void newMessage(RequestMsg msg){
        manager.sendGameMessage(msg);
    }

    public void handleMessage(ResponseMsg msg){
        controller.handle(msg);
    }

    public void update(RequestMsg updateMsg){
        newMessage(updateMsg);
    }
}
