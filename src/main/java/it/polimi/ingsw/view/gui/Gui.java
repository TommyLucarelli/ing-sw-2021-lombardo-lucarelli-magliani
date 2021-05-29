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
    private HashMap<Integer, CompactPlayer> opponents = new HashMap<>();

    public Gui(Client client){
        this.client = client;
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
                        Platform.runLater(() -> {
                            JavaFxApp.setRoot("gameboard");
                            handleInitialUpdate(request.getPayload());
                        });
                        break;
                    case "UPDATE":
                        Platform.runLater(() -> JavaFxApp.setData(request.getPayload()));
                        handleUpdate(request.getPayload());
                    case "LEADER_ACTIVATION":
                        Platform.runLater(() -> JavaFxApp.showPopup("leaderactivation"));
                        break;
                    case "LEADER_ACTION":
                        Platform.runLater(() -> JavaFxApp.showPopup("leaderaction"));
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

    public CompactPlayer getMyself(){
        return mySelf;
    }

    public void firstSetup(int playerId, String username, int[] leaders){
        mySelf = new CompactPlayer(playerId, username);
        mySelf.getCompactBoard().setLeaderCards(leaders);
    }

    private void handleInitialUpdate(JsonObject update){
        compactMarket = new CompactMarket();
        compactDevCardStructure = new CompactDevCardStructure();
        int nextPlayerID;

        JsonObject payload = update.get("market").getAsJsonObject();
        nextPlayerID = update.get("nextPlayerID").getAsInt();
        Gson gson = new Gson();
        String json = payload.get("structure").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
        int[] structure = gson.fromJson(json, collectionType);
        compactMarket.setMarket(structure);

        payload = update.get("devCardStructure").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        int[][] structure2 = gson.fromJson(json, collectionType);
        compactDevCardStructure.setDevCardStructure(structure2);

        JsonObject payload2;
        JsonArray players = update.get("players").getAsJsonArray();
        for (JsonElement player: players) {
            if (player.getAsJsonObject().get("playerID").getAsInt() == mySelf.getPlayerID()) {
                payload2 = player.getAsJsonObject().get("faithTrack").getAsJsonObject();
                mySelf.getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>() {
                }.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setFavCards(fav);

                payload2 = player.getAsJsonObject().get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<Resource[]>() {
                }.getType();
                Resource[] ware = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setWarehouse(ware);

            } else {
                opponents.put(player.getAsJsonObject().get("playerID").getAsInt(), new CompactPlayer(player.getAsJsonObject().get("playerID").getAsInt(), player.getAsJsonObject().get("playerName").getAsString()));

                payload2 = player.getAsJsonObject().get("faithTrack").getAsJsonObject();
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>() {
                }.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setFavCards(fav);

                payload2 = player.getAsJsonObject().get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<ArrayList<Resource>>() {
                }.getType();
                ArrayList<Resource> ware = gson.fromJson(json, collectionType);
                Resource[] wa = new Resource[10];
                wa = ware.toArray(wa);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setWarehouse(wa);
            }
        }

        updateGameBoard();

        JsonObject payload3 = new JsonObject();
        payload3.addProperty("gameAction", "INITIAL_UPDATE");
        payload3.addProperty("playerID", mySelf.getPlayerID());
        client.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload3));
    }

    private void handleUpdate(JsonObject data){
        Gson gson = new Gson();
        JsonObject payload, payload2;

        int currentPlayerID = data.get("currentPlayerID").getAsInt();
        String message = data.get("message").getAsString();


        CompactPlayer player;
        if(mySelf.getPlayerID() == currentPlayerID)
            player = mySelf;
        else
            player = opponents.get(currentPlayerID); //da controllare

        String json = data.get("abilityActivationFlag").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
        int[] abilityActivationFlag = gson.fromJson(json, collectionType);
        player.getCompactBoard().setAbilityActivationFlag(abilityActivationFlag);

        json = data.get("discardedLeaderCards").getAsString();
        collectionType = new TypeToken<int[]>(){}.getType();
        player.getCompactBoard().removeDiscardedCards(gson.fromJson(json, collectionType));


        payload = data.get("market").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[]>(){}.getType();
        int[] structure = gson.fromJson(json, collectionType);
        compactMarket.setMarket(structure);

        payload = data.get("devCardStructure").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        int[][] structure2 = gson.fromJson(json, collectionType);
        compactDevCardStructure.setDevCardStructure(structure2);

        payload = data.get("player").getAsJsonObject();

        payload2 = payload.get("warehouse").getAsJsonObject();
        json = payload2.get("structure").getAsString();
        collectionType = new TypeToken<Resource[]>(){}.getType();
        Resource[] ware = gson.fromJson(json, collectionType);
        player.getCompactBoard().setWarehouse(ware);

        payload2 = payload.get("strongbox").getAsJsonObject();
        json = payload2.get("structure").getAsString();
        collectionType = new TypeToken<int[]>(){}.getType();
        player.getCompactBoard().setStrongbox(gson.fromJson(json,collectionType));

        payload2 = payload.get("devCardSlots").getAsJsonObject();
        json = payload2.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        player.getCompactBoard().setDevCardSlots(gson.fromJson(json,collectionType));

        JsonArray players = data.get("faithTracks").getAsJsonArray();
        CompactPlayer p2;
        for (JsonElement p: players) {
            if(mySelf.getPlayerID() == p.getAsJsonObject().get("playerID").getAsInt())
                p2 = mySelf;
            else
                p2 = opponents.get(p.getAsJsonObject().get("playerID").getAsInt());
            payload2 = p.getAsJsonObject().get("faithTrack").getAsJsonObject();
            p2.getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
            json = payload2.get("favCards").getAsString();
            collectionType = new TypeToken<boolean[]>() {}.getType();
            boolean[] fav = gson.fromJson(json, collectionType);
            p2.getCompactBoard().setFavCards(fav);
        }

        if(data.has("lorenzoTrack")){
            JsonObject payload4 = data.get("lorenzoTrack").getAsJsonObject();
            player.getCompactBoard().setLorenzoIndex(payload4.get("index").getAsInt());
        }

        if(player.getPlayerID() != mySelf.getPlayerID() || opponents.size()==0)
            System.out.println("\n"+message+"\n");

        if(data.has("endMessage") && !mySelf.getLastTurn()){
            mySelf.setLastTurn(true);
            System.out.println(data.get("endMessage").getAsString());
        }

        updateGameBoard();

        JsonObject payload3 = new JsonObject();
        payload3.addProperty("gameAction", "UPDATE");
        payload3.addProperty("playerID", mySelf.getPlayerID());

        client.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload3));
    }

    private void updateGameBoard(){
        JsonObject data = new JsonObject();
        Gson gson = new Gson();

        data.addProperty("leaders", gson.toJson(mySelf.getCompactBoard().getLeaderCards()));
        data.addProperty("warehouse", gson.toJson(mySelf.getCompactBoard().getWarehouse()));
        data.addProperty("devCardSlots", gson.toJson(mySelf.getCompactBoard().getDevCardSlots()));
        data.addProperty("faithPoints", mySelf.getCompactBoard().getFaithTrackIndex());
        data.addProperty("favourCards", gson.toJson(mySelf.getCompactBoard().getFavCards()));
        data.addProperty("market", gson.toJson(compactMarket.getMarket()));
        data.addProperty("cardStructure", gson.toJson(compactDevCardStructure.getDevCardStructure()));

        Platform.runLater(() -> JavaFxApp.setData(data));
    }


}
