package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

public class PlayerHandler {
    private final int playerId;
    private final String username;
    private final MainController controller;
    private boolean activePlayer;
    private boolean newUpdate;
    private RequestMsg update;

    protected PlayerHandler(int playerId, String username, MainController controller){
        this.username = username;
        this.playerId = playerId;
        this.controller = controller;
        this.activePlayer = false;
        this.newUpdate = false;
    }

    public int getPlayerId() {
        return playerId;
    }

    public RequestMsg nextMessage(ResponseMsg responseMsg){
        if(newUpdate){
            newUpdate = false;
            return update;
        } else {
            return controller.handle(responseMsg);
        }
    }

    public boolean isNewUpdate() {
        return newUpdate;
    }

    public boolean isActivePlayer() {
        return activePlayer;
    }

    public void update(RequestMsg updateMsg){
        this.update = updateMsg;
        this.newUpdate = true;
        activePlayer = updateMsg.getPayload().get("activePlayerId").getAsInt() == playerId;
    }
}
