package it.polimi.ingsw.net.msg;

import com.google.gson.JsonObject;

public class ErrorMsg extends NetworkMsg{
    private MessageType messageType = MessageType.ERROR_MESSAGE;
    private JsonObject payload;

    public ErrorMsg(String message){
        payload.addProperty("errorMessage", "message");
    }
}
