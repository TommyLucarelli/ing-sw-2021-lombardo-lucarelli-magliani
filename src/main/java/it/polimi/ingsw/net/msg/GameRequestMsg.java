package it.polimi.ingsw.net.msg;

import com.google.gson.JsonObject;

public class GameRequestMsg extends RequestMsg{
    private GameMessageType gameMessageType;

    public GameRequestMsg(GameMessageType gameMessageType, JsonObject payload) {
        super(MessageType.GAME_MESSAGE, payload);
        this.gameMessageType = gameMessageType;
    }

    public GameMessageType getGameMessageType() {
        return gameMessageType;
    }
}
