package it.polimi.ingsw.core.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

public class TurnHandler {
    private MainController controller;

    public TurnHandler(MainController controller){
        this.controller = controller;
    }

    public void mainChoice(ResponseMsg ms) {
        controller.getCurrentGame().getTurn().setEndGame(true);
        String actionChoice = ms.getPayload().get("actionChoice").getAsString();
        if (actionChoice.equals("market")) {
            controller.getCurrentGame().getTurn().setTypeOfAction(0);
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "PICK");
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        } else if (actionChoice.equals("production")) {
            controller.getCurrentGame().getTurn().setTypeOfAction(1);
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "CHOOSE_PRODUCTION");
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        } else{
            controller.getCurrentGame().getTurn().setTypeOfAction(2);
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "CHOOSE_DEVCARD");
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        }
    }

    public void leaderActivation(ResponseMsg ms){
        boolean activation = ms.getPayload().get("activation").getAsBoolean();
        if(activation){
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "LEADER_ACTION");
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        }
        else{
            if(controller.getCurrentGame().getTurn().isEndGame()){
                controller.getCurrentGame().getTurn().setEndGame(false);
                //update
                controller.updateBuilder();
            } else{
                JsonObject payload = new JsonObject();
                payload.addProperty("gameAction", "MAIN_CHOICE");
                controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }
        }
    }

    public void comeBack(){
        if(controller.getCurrentGame().getTurn().isEndGame()){
            //update
            controller.updateBuilder();
        }else{
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "MAIN_CHOICE");
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        }
    }

    public void update(){
        controller.getCurrentGame().getTurn().setEndGame(false);
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "LEADER_ACTIVATION");
        controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
    }
}