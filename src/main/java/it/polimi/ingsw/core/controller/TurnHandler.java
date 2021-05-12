package it.polimi.ingsw.core.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.Resource;
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
            for (int i = 0; i < 4; i++)
                addSpecial(i,payload);
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        } else if (actionChoice.equals("production")) {
            controller.getCurrentGame().getTurn().setTypeOfAction(1);
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "CHOOSE_PRODUCTION");
            for (int i = 6; i < 8; i++)
                addSpecial(i,payload);
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        } else{
            controller.getCurrentGame().getTurn().setTypeOfAction(2);
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "CHOOSE_DEVCARD");
            for (int i = 4; i < 6; i++)
                addSpecial(i,payload);
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
        for (int i = 0; i < controller.getPlayers().size(); i++) {
            if(controller.getPlayers().get(i).getPlayerId() != controller.getCurrentPlayer().getPlayerID()){
                payload.addProperty("gameAction", "START_TURN");
                payload.addProperty("message", controller.getCurrentPlayer().getNickname()+" is now playing");
                controller.notifyPlayer(controller.getPlayers().get(i), new RequestMsg(MessageType.GAME_MESSAGE, payload));
            } else{
                payload.addProperty("gameAction", "LEADER_ACTIVATION");
                controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }
        }
    }

    private void addSpecial(int x, JsonObject payload){
        if (controller.getCurrentPlayer().getBoard().isActivated(x) != 0){
            Resource resource = controller.getCurrentPlayer().getBoard().getLeader(controller.getCurrentPlayer().getBoard().isActivated(x)).getSpecialAbility().getAbilityResource();
            Gson gson = new Gson();
            String json = gson.toJson(resource);
            payload.addProperty("special"+x, json);
        }
    }
}