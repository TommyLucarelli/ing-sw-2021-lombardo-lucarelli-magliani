package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.net.server.RequestManager;

public class PlayerHandler {
    private final int playerId;
    private final String username;
    private final MainController controller;
    private final RequestManager manager;
    private Boolean active;

    protected PlayerHandler(int playerId, String username, MainController controller, RequestManager manager){
        this.username = username;
        this.playerId = playerId;
        this.controller = controller;
        this.manager = manager;
        this.active = true;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public void handleMessage(ResponseMsg msg){
        controller.handle(msg);
    }

    public void newMessage(RequestMsg updateMsg){
        if(active)
            manager.sendGameMessage(updateMsg);
    }

    public void handleDisconnection(){
        active = false;
        controller.handleDisconnection(this);
    }

    public void handleReconnection(){
        active = true;
        controller.handleReconnection(this);
    }
}
