package it.polimi.ingsw.net.msg;

import com.google.gson.JsonObject;

public class RequestMsg extends NetworkMsg{
    private MessageType messageType;
    private JsonObject payload;

    public RequestMsg(MessageType messageType, JsonObject payload){
        super();
        this.messageType = messageType;
        this.payload = payload;
    }
}
