package it.polimi.ingsw.net.msg;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RequestMsg extends NetworkMsg{
    private MessageType messageType;
    private JsonObject payload;

    public RequestMsg(MessageType messageType, JsonObject payload){
        super();
        this.messageType = messageType;
        this.payload = payload;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public JsonObject getPayload() {
        return payload;
    }
}
