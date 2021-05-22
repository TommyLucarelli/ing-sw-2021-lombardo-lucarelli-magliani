package it.polimi.ingsw.core.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.*;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

public class TurnHandler {
    private MainController controller;

    public TurnHandler(MainController controller){
        this.controller = controller;
    }

    public void mainChoice(ResponseMsg ms) {

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
                //QUA PRENDERE I TOKEN
                if(controller.getCurrentGame().getSinglePlayer()){
                    Gson gson = new Gson();
                    SoloActionToken sat = ((SingleBoard)controller.getCurrentPlayer().getBoard()).getSoloActionToken();
                    JsonObject payload = sat.getAction();
                    ((SingleTurn)controller.getCurrentGame().getTurn()).setSoloActionToken(sat);
                    if(payload.get("type").getAsInt() == 0){
                        Colour c = gson.fromJson(payload.get("colour").getAsString(), Colour.class);
                        controller.getCurrentGame().getDevCardStructure().discardSingle(c); //controllo boolean per endGame
                    }else if(payload.get("type").getAsInt() == 1) {
                        if(payload.get("shuffle").getAsBoolean()){
                            ((SingleBoard) controller.getCurrentPlayer().getBoard()).shuffleDeck();
                            controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 0, 1);
                        }else{
                            controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 0, 2);
                        }
                    }
                }
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

    public void update(ResponseMsg responseMsg){
        controller.getCurrentGame().getTurn().setEndGame(false);
        int playerID = responseMsg.getPayload().get("playerID").getAsInt();
        JsonObject payload = new JsonObject();
        if( playerID != controller.getCurrentPlayer().getPlayerID()){
            payload.addProperty("gameAction", "START_TURN");
            payload.addProperty("message", controller.getCurrentPlayer().getNickname()+" is now playing");
            controller.notifyPlayer(controller.fromIdToPlayerHandler(playerID), new RequestMsg(MessageType.GAME_MESSAGE, payload));
        } else{
            payload.addProperty("gameAction", "LEADER_ACTIVATION");
            payload.addProperty("endTurn", false);
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        }

    }

}