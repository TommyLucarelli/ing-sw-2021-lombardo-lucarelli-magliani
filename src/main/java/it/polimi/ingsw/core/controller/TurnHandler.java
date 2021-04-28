package it.polimi.ingsw.core.controller;

import com.google.gson.JsonObject;
import com.sun.tools.javac.Main;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

public class TurnHandler {
    private MainController controller;

    public TurnHandler(MainController controller){
        this.controller = controller;
    }

    public RequestMsg mainChoice(ResponseMsg ms) {
        controller.getCurrentGame().getTurn().setEndGame(true);
        String actionChoice = ms.getPayload().get("actionChoice").getAsString();
        if (actionChoice.equals("market")) {
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "PICK");
            return new RequestMsg(MessageType.GAME_MESSAGE, payload);
        } else if (actionChoice.equals("production")) {
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "CHOOSE_PRODUCTION");
            return new RequestMsg(MessageType.GAME_MESSAGE, payload);
        } else{
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "CHOOSE_DEVCARD");
            return new RequestMsg(MessageType.GAME_MESSAGE, payload);
        }
    }

    public RequestMsg leaderActivation(ResponseMsg ms){
        boolean activation = ms.getPayload().get("activation").getAsBoolean();
        if(activation){
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "LEADER_ACTION");
            return new RequestMsg(MessageType.GAME_MESSAGE, payload);
        }
        else{
            if(controller.getCurrentGame().getTurn().isEndGame()){
                controller.getCurrentGame().getTurn().setEndGame(false);
                //costruzione e ritorno messaggio update
            } else{
                JsonObject payload = new JsonObject();
                payload.addProperty("gameAction", "MAIN_CHOICE");
                return new RequestMsg(MessageType.GAME_MESSAGE, payload);
            }
        }
        return null;
    }

    public RequestMsg comeBack(){

        if(controller.getCurrentGame().getTurn().isEndGame()){
            //messaggio update
        }else{
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "MAIN_CHOICE");
            return new RequestMsg(MessageType.GAME_MESSAGE, payload);
        }

        return null;
    }
}