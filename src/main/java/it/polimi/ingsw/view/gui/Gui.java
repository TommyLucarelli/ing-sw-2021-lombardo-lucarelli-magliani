package it.polimi.ingsw.view.gui;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.net.client.Client;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.compact.CompactDevCardStructure;
import it.polimi.ingsw.view.compact.CompactMarket;
import it.polimi.ingsw.view.compact.CompactPlayer;
import javafx.application.Application;
import javafx.application.Platform;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Gui implements UserInterface {
    private final Client client;
    private CompactPlayer mySelf;
    private CompactMarket compactMarket;
    private CompactDevCardStructure compactDevCardStructure;
    private HashMap<Integer, CompactPlayer> opponents;

    public Gui(Client client){
        this.client = client;
        this.opponents = new HashMap<>();
        (new Thread(() -> Application.launch((JavaFxApp.class)))).start();
        JavaFxApp.setManager(this);
    }

    @Override
    public void handleRequest(RequestMsg request) {
        switch (request.getMessageType()) {
            case REGISTRATION_MESSAGE:
                Platform.runLater(() -> JavaFxApp.setRoot("registration"));
                if(request.getPayload().has("error")) Platform.runLater(() -> JavaFxApp.setData("error", "true"));
                break;
            case WELCOME_MESSAGE:
                Platform.runLater(() -> JavaFxApp.setRootWithData("welcome", request.getPayload()));
                break;
            case NUMBER_OF_PLAYERS:
                Platform.runLater(() -> JavaFxApp.setRoot("creategame"));
                break;
            case JOIN_GAME:
                Platform.runLater(() -> JavaFxApp.setRoot("joinlobby"));
                break;
            case GAME_MESSAGE:
                switch (request.getPayload().get("gameAction").getAsString()){
                    case "WAIT_FOR_PLAYERS":
                    case "WAIT_START_GAME":
                        Platform.runLater(() -> JavaFxApp.setRootWithData("waitplayers", request.getPayload()));
                        break;
                    case "SHORT_UPDATE":
                    case "START_GAME_COMMAND":
                        Platform.runLater(() -> JavaFxApp.setData(request.getPayload()));
                        break;
                    case "CHOOSE_START_LEADERS":
                        Platform.runLater(() -> JavaFxApp.setRootWithData("leaderschoice", request.getPayload()));
                        break;
                    case "CHOOSE_START_RESOURCES":
                        Platform.runLater(() -> JavaFxApp.setRootWithData("resourceschoice", request.getPayload()));
                        break;
                    case "INITIAL_UPDATE":
                        handleInitialUpdate(request);
                        Platform.runLater(() -> JavaFxApp.setRoot("gameboard"));
                        //TODO: setData()
                        break;

                }
        }
    }

    public void send(ResponseMsg responseMsg){
        client.send(responseMsg);
    }

    public void start(){
        this.client.run();
    }

    public void close(){
        this.client.closeConnection();
        System.exit(0);
    }

    public int getPlayerId(){
        return mySelf.getPlayerID();
    }

    public void firstSetup(int playerId, String username, int[] leaders){
        mySelf = new CompactPlayer(playerId, username);
        mySelf.getCompactBoard().setLeaderCards(leaders);
    }

    /**
     * Method used to handle the initial update sent by the server to initialize the client-side model structures.
     * @param requestMsg the request sent by the server.
     */
    private void handleInitialUpdate(RequestMsg requestMsg){
        compactMarket = new CompactMarket();
        compactDevCardStructure = new CompactDevCardStructure();
        int nextPlayerID;

        JsonObject payload = requestMsg.getPayload().get("market").getAsJsonObject();
        nextPlayerID = requestMsg.getPayload().get("nextPlayerID").getAsInt();
        Gson gson = new Gson();
        String json = payload.get("structure").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
        int[] structure = gson.fromJson(json, collectionType);
        compactMarket.setMarket(structure);

        payload = requestMsg.getPayload().get("devCardStructure").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        int[][] structure2 = gson.fromJson(json, collectionType);
        compactDevCardStructure.setDevCardStructure(structure2);

        JsonObject payload2;
        JsonArray players = requestMsg.getPayload().get("players").getAsJsonArray();
        for (JsonElement player: players) {
            if(player.getAsJsonObject().get("playerID").getAsInt() == mySelf.getPlayerID()){
                payload2 = player.getAsJsonObject().get("faithTrack").getAsJsonObject();
                mySelf.getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>(){}.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setFavCards(fav);

                payload2 = player.getAsJsonObject().get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<Resource[]>(){}.getType();
                Resource[] ware = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setWarehouse(ware);

            }else{
                opponents.put(player.getAsJsonObject().get("playerID").getAsInt(), new CompactPlayer(player.getAsJsonObject().get("playerID").getAsInt(),player.getAsJsonObject().get("playerName").getAsString()));

                payload2 = player.getAsJsonObject().get("faithTrack").getAsJsonObject();
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>(){}.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setFavCards(fav);

                payload2 = player.getAsJsonObject().get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<ArrayList<Resource>>(){}.getType();
                ArrayList<Resource> ware = gson.fromJson(json, collectionType);
                Resource[] wa = new Resource[10];
                wa = ware.toArray(wa);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setWarehouse(wa);
            }
        }


        JsonObject payload3 = new JsonObject();
        payload3.addProperty("gameAction", "INITIAL_UPDATE");
        payload3.addProperty("playerID", mySelf.getPlayerID());
        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload3));
    }
}
