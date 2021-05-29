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

    /**
     * Method that manages the choice of the main action of the turn made by the user.
     * Depending on the choice, a different message will be prepared that will start the process of each action.
     * @param ms
     */
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

    /**
     * Method that manages the choice of activating or not a leader card.
     * This method for the action it represents can also be called at the end of the turn, so possibly also prepare the update.
     * @param ms client message
     */
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
                        if(controller.getCurrentGame().getDevCardStructure().discardSingle(c))
                            controller.getCurrentGame().getTurn().setLastTurn(4);
                    }else if(payload.get("type").getAsInt() == 1) {
                        if(payload.get("shuffle").getAsBoolean()){
                            ((SingleBoard) controller.getCurrentPlayer().getBoard()).shuffleDeck();
                            controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 0, 1);
                        }else{
                            controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 0, 2);
                        }
                    }
                    if(controller.getCurrentGame().getTurn().isLastTurn() == 4)
                        controller.finalUpdate(controller.getCurrentPlayer().getPlayerID());
                    else
                        controller.updateBuilder();
                }else{
                    controller.updateBuilder();
                }
            } else{
                JsonObject payload = new JsonObject();
                payload.addProperty("gameAction", "MAIN_CHOICE");
                controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }
        }
    }

    /**
     * Method called when a user wants to go back in the turn or go to the next turn.
     */
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

    /**
     * Method that receives the acknowledgment of receipt of the update and starts the new turn.
     * To the user who has to play, the message "LEADER ACTIVATION" will be sent, to the others the message "START_TURN".
     * @param responseMsg client message
     */
    public void update(ResponseMsg responseMsg){
        controller.getCurrentGame().getTurn().setEndGame(false);
        int playerID = responseMsg.getPayload().get("playerID").getAsInt();
        JsonObject payload = new JsonObject();
        Turn turn = controller.getCurrentGame().getTurn();
        if((turn.isLastTurn() == 1 || turn.isLastTurn() == 2 || turn.isLastTurn() == 3) && turn.getCurrentPlayer() == 0){
            controller.finalUpdate(playerID);
        }else {
            if (playerID != controller.getCurrentPlayer().getPlayerID()) {
                payload.addProperty("gameAction", "START_TURN");
                payload.addProperty("message", controller.getCurrentPlayer().getNickname() + " is now playing");
                controller.notifyPlayer(controller.fromIdToPlayerHandler(playerID), new RequestMsg(MessageType.GAME_MESSAGE, payload));
            } else {
                payload.addProperty("gameAction", "LEADER_ACTIVATION");
                payload.addProperty("endTurn", false);
                controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }
        }

    }

}