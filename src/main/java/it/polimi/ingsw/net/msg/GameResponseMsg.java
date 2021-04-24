package it.polimi.ingsw.net.msg;

import com.google.gson.JsonObject;

import java.util.UUID;

public class GameResponseMsg extends ResponseMsg{
    private GameMessageType gameMessageType;

    public GameResponseMsg(UUID requestUUID, GameMessageType gameMessageType, JsonObject payload) {
        super(requestUUID, MessageType.GAME_MESSAGE, payload);
        this.gameMessageType = gameMessageType;
    }

    public GameMessageType getGameMessageType() {
        return gameMessageType;
    }
}
