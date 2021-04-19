package it.polimi.ingsw.net.msg;

import com.google.gson.JsonObject;

import java.util.UUID;

public class ResponseMsg {
    private MessageType messageType;
    private UUID requestUUID;
    private JsonObject payload;

    public ResponseMsg(UUID requestUUID, MessageType messageType, JsonObject payload){
        this.messageType = messageType;
        this.requestUUID = requestUUID;
        this.payload = payload;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public UUID getRequestUUID() {
        return requestUUID;
    }

    public JsonObject getPayload() {
        return payload;
    }
}
