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
    private StringBuilder updates = new StringBuilder("Updates: \n");


    /**
     * Class constructor that launches the JavaFX application.
     * @param client the Client connected to the server.
     */
    public Gui(Client client){
        this.client = client;
        (new Thread(() -> Application.launch((JavaFxApp.class)))).start();
        JavaFxApp.setManager(this);
    }

    @Override
    public void handleRequest(RequestMsg request) {
        JsonObject data;
        switch (request.getMessageType()) {
            case REGISTRATION_MESSAGE:
                Platform.runLater(() -> JavaFxApp.setRoot("registration"));
                if(request.getPayload().has("error")) Platform.runLater(() -> JavaFxApp.setData("error", "true"));
                break;
            case WELCOME_MESSAGE:
                Platform.runLater(() -> JavaFxApp.setRootWithData("welcome", request.getPayload()));
                break;
            case RECONNECTION_MESSAGE:
                Platform.runLater(() -> JavaFxApp.setRootWithData("reconnection", request.getPayload()));
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
                        handleInitialUpdate(request.getPayload());
                        break;
                    case "UPDATE":
                        handleUpdate(request.getPayload());
                        break;
                    case "LEADER_ACTIVATION":
                        Platform.runLater(() -> JavaFxApp.showPopup("leaderactivation"));
                        break;
                    case "LEADER_ACTION":
                        data = new JsonObject();
                        data.addProperty("leaders", (new Gson()).toJson(mySelf.getCompactBoard().getLeaderCards()));
                        Platform.runLater(() -> JavaFxApp.showPopupWithData("leaderaction", data));
                        break;
                    case "MAIN_CHOICE":
                        Platform.runLater(() -> JavaFxApp.showPopup("mainchoice"));
                        break;
                    case "PICK":
                        data = new JsonObject();
                        data.addProperty("marbles", (new Gson()).toJson(compactMarket.getMarket()));
                        Platform.runLater(() -> JavaFxApp.showPopupWithData("marketaction", data));
                        break;
                    case "WAREHOUSE_PLACEMENT":
                        handleWarehousePlacement(request.getPayload());
                        break;
                }
        }
    }

    /**
     * Sends a message to the server.
     * @param responseMsg the message to be sent.
     */
    public void send(ResponseMsg responseMsg){
        client.send(responseMsg);
    }

    /**
     * Starts the client thread.
     */
    public void start(){
        this.client.run();
    }

    /**
     * Closes the application and the connection to the server.
     */
    public void close(){
        this.client.closeConnection();
        System.exit(0);
    }

    /**
     * Getter method.
     * @return the compactPlayer object containing the player's information.
     */
    public CompactPlayer getMyself(){
        return mySelf;
    }

    /**
     * Initializes the compactPlayer containing the player's information.
     * @param playerId the player's Id.
     * @param username the player's username.
     * @param leaders the player's starting leader cards.
     */
    public void firstSetup(int playerId, String username, int[] leaders){
        mySelf = new CompactPlayer(playerId, username);
        mySelf.getCompactBoard().setLeaderCards(leaders);
    }

    /**
     * Handles the first update sent by the server at the beginning of the game, saving the information into the data
     * structures.
     * @param data the data sent by the server.
     */
    private void handleInitialUpdate(JsonObject data){
        compactMarket = new CompactMarket();
        compactDevCardStructure = new CompactDevCardStructure();
        int nextPlayerID;

        JsonObject payload = data.get("market").getAsJsonObject();
        nextPlayerID = data.get("nextPlayerID").getAsInt();
        Gson gson = new Gson();
        String json = payload.get("structure").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
        int[] structure = gson.fromJson(json, collectionType);
        compactMarket.setMarket(structure);

        payload = data.get("devCardStructure").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        int[][] structure2 = gson.fromJson(json, collectionType);
        compactDevCardStructure.setDevCardStructure(structure2);

        JsonObject payload2;
        JsonArray players = data.get("players").getAsJsonArray();
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

                json = player.getAsJsonObject().get("leaderCards").getAsString();
                collectionType = new TypeToken<int[]>(){}.getType();
                int[] lcs = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setLeaderCards(lcs);

            }else{
                opponents.put(player.getAsJsonObject().get("playerID").getAsInt(), new CompactPlayer(player.getAsJsonObject().get("playerID").getAsInt(),player.getAsJsonObject().get("playerName").getAsString()));

                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setOpponent(true);

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

                json = player.getAsJsonObject().get("leaderCards").getAsString();
                collectionType = new TypeToken<int[]>(){}.getType();
                int[] lcs = gson.fromJson(json, collectionType);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setLeaderCards(lcs);
            }
        }

        Platform.runLater(() -> {
            JavaFxApp.setRoot("gameboard");
            updateGameBoard();
        });

        JsonObject payload3 = new JsonObject();
        payload3.addProperty("gameAction", "INITIAL_UPDATE");
        payload3.addProperty("playerID", mySelf.getPlayerID());
        client.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload3));
    }

    /**
     * Handles the  update sent by the server at the end of every turn, saving the information into the data
     * structures.
     * @param data the data sent by the server.
     */
    private void handleUpdate(JsonObject data){

        Gson gson = new Gson();
        JsonObject payload, payload2;
        String message;

        int currentPlayerID = data.get("currentPlayerID").getAsInt();

        CompactPlayer player;
        if(mySelf.getPlayerID() == currentPlayerID)
            player = mySelf;
        else
            player = opponents.get(currentPlayerID); //da controllare

        String json = data.get("discardedLeaderCards").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
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

        json = payload.get("abilityActivationFlag").getAsString();
        collectionType = new TypeToken<int[]>(){}.getType();
        int[] abilityActivationFlag = gson.fromJson(json, collectionType);
        player.getCompactBoard().setAbilityActivationFlag(abilityActivationFlag);

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

        if(data.has("message")){
            message = data.get("message").getAsString();
            if(player.getPlayerID() != mySelf.getPlayerID() || opponents.size()==0)
                updates.append(message).append("\n");
        }

        if(data.has("lorenzoTrack")){
            JsonObject payload4 = data.get("lorenzoTrack").getAsJsonObject();
            player.getCompactBoard().setLorenzoIndex(payload4.get("index").getAsInt());
        }

        if(data.has("endMessage") && !mySelf.getLastTurn()){
            mySelf.setLastTurn(true);
            System.out.println(data.get("endMessage").getAsString());
        }

        Platform.runLater(() -> {
            JavaFxApp.setRoot("gameboard");
            updateGameBoard();
        });

        JsonObject payload3 = new JsonObject();
        payload3.addProperty("gameAction", "UPDATE");
        payload3.addProperty("playerID", mySelf.getPlayerID());

        client.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload3));
    }

    private void handleWarehousePlacement(JsonObject data){
        Gson gson = new Gson();
        data.addProperty("warehouse", gson.toJson(mySelf.getCompactBoard().getWarehouse()));
        Platform.runLater(() -> JavaFxApp.showPopupWithData("warehouseplacement", data));
    }

    /**
     * Method called to update the game board scene everytime an update is received.
     */
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
        data.addProperty("updates", updates.toString());

        JavaFxApp.setData(data);
    }


}
