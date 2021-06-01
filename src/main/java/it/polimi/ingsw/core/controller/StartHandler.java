package it.polimi.ingsw.core.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Player;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StartHandler {

    private MainController controller;

    public StartHandler(MainController controller){
        this.controller = controller;
    }


    /**
     * Method to prepare the first request for the clients.
     * Four leader cards will be chosen for each player and sent to them.
     */
    public void startMatch() {
        Player player;
        int[] cardID = new int[4];

        for(int j=0; j < controller.getPlayersInGame(); j++) {
            player = controller.getCurrentGame().fromIdToPlayer(controller.getPlayers().get(j).getPlayerId());
            for (int i = 0; i < 4; i++) {
                player.getBoard().addLeaderCard(controller.getCurrentGame().getLeaderCards().drawCard());
                cardID[i] = player.getBoard().getLeaderCard(i).getId();
            }
            //invio messaggio con cardID
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "CHOOSE_START_LEADERS");
            if(!controller.getCurrentGame().getSinglePlayer())
                payload.addProperty("playerOrder", j+1);
            Gson gson = new Gson();
            String json = gson.toJson(cardID); //forse sarebbe meglio trasformarlo in array
            payload.addProperty("leaderCards", json);
            payload.addProperty("playerID", player.getPlayerID());
            payload.addProperty("playerName", player.getNickname());
            controller.notifyPlayer(controller.getPlayers().get(j), new RequestMsg(MessageType.GAME_MESSAGE, payload));
        }
    }


    /**
     * Method that removes from the board the cards discarded by users
     * and depending on their position in the turns asks them to choose resources.
     * @param ms client message
     */
    public void chooseStartLeaders(ResponseMsg ms) {
        //arrivo scelta carte leader array con id carte scartate
        //invio messaggio start resources
        PlayerHandler playerHandler;
        Player player;
        int playerID = ms.getPayload().get("playerID").getAsInt();
        int[] discardedID;
        Gson gson = new Gson();
        String json = ms.getPayload().get("discardedLeaders").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
        discardedID = gson.fromJson(json, collectionType);

        playerHandler = controller.fromIdToPlayerHandler(playerID);
        player = controller.getCurrentGame().fromIdToPlayer(playerID);

        player.getBoard().removeLeaderCard(controller.getCurrentPlayer().getBoard().getLeader(discardedID[0]));
        player.getBoard().removeLeaderCard(controller.getCurrentPlayer().getBoard().getLeader(discardedID[1]));

        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "CHOOSE_START_RESOURCES");
        switch (controller.getPlayers().indexOf(playerHandler)){
            case 0: //messaggio: aspetta che gli altri faccianno le loro scelte
                boolean check = controller.setCountStartPhase();
                if(check){
                    controller.initialUpdate();
                }else{
                    payload.addProperty("resources", 0);
                    controller.notifyPlayer(playerHandler, new RequestMsg(MessageType.GAME_MESSAGE, payload));
                }
                break;
            case 1: //messaggio choose resources 1
                payload.addProperty("resources", 1);
                payload.addProperty("faithPoints", 0);
                controller.notifyPlayer(playerHandler, new RequestMsg(MessageType.GAME_MESSAGE, payload));
                break;
            case 2: //messaggio choose resources 1 + 1 punto fede
                payload.addProperty("resources", 1);
                payload.addProperty("faithPoints", 1);
                controller.getCurrentGame().faithTrackUpdate(player, 1, 0);
                controller.notifyPlayer(playerHandler, new RequestMsg(MessageType.GAME_MESSAGE, payload));
                break;
            case 3: //messaggio choose resources 2 + 1 punto fede
                payload.addProperty("resources", 2);
                payload.addProperty("faithPoints", 1);
                controller.getCurrentGame().faithTrackUpdate(player, 1, 0);
                controller.notifyPlayer(playerHandler, new RequestMsg(MessageType.GAME_MESSAGE, payload));
                break;
        }

    }

    /**
     * Method that places the resources chosen by the user in the warehouse and calls the construction of the initial update.
     * @param ms client message
     */
    public void chooseStartResources(ResponseMsg ms) {
        int playerID = ms.getPayload().get("playerID").getAsInt();
        Player player = controller.getCurrentGame().fromIdToPlayer(playerID);
        Gson gson = new Gson();
        String json = ms.getPayload().get("placed").getAsString();
        Type collectionType = new TypeToken<ArrayList<Resource>>() {}.getType();
        ArrayList<Resource> placed = gson.fromJson(json, collectionType);
        player.getBoard().getWarehouse().updateConfiguration(placed);

        boolean check = controller.setCountStartPhase();

        if (check) {
            controller.initialUpdate();
        }

    }
}