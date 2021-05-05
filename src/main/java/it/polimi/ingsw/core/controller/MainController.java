package it.polimi.ingsw.core.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.*;
import it.polimi.ingsw.net.msg.*;
import it.polimi.ingsw.net.server.InvalidResponseException;
import it.polimi.ingsw.net.server.RequestManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainController{
    private int id;
    private Game currentGame;
    private int numPlayers;
    private Player currentPlayer;
    private boolean gameInProgress;
    private ArrayList<PlayerHandler> players; //shuffle this and create the turn order
    private MarketHandler marketHandler;
    private LeaderCardHandler leaderCardHandler;
    private TurnHandler turnHandler;
    private ProductionHandler productionHandler;
    private DevCardHandler devCardHandler;
    private StartHandler startHandler;


    public MainController(int id, int numPlayers)
    {
        this.id = id;
        this.numPlayers = numPlayers;
        this.gameInProgress = false;
        this.players = new ArrayList<>();
        this.marketHandler = new MarketHandler(this);
        this.leaderCardHandler = new LeaderCardHandler(this);
        this.turnHandler = new TurnHandler(this);
        this.productionHandler = new ProductionHandler(this);
        this.devCardHandler = new DevCardHandler(this);
        this.startHandler = new StartHandler(this);
    }

    public PlayerHandler addPlayer(int id, String username, RequestManager manager) throws InvalidResponseException {
        if(players.size() == 4) throw new InvalidResponseException("This lobby has already reached max capacity! Try again after a player leaves or create/join a new lobby");
        else {
            PlayerHandler player = new PlayerHandler(id, username,this, manager);
            if(players.size() != 0){
                JsonObject payload = new JsonObject();
                payload.addProperty("gameAction", "SHORT_UPDATE");
                payload.addProperty("message", username + " has joined the lobby.");
                payload.addProperty("activePlayerId", 0);
                notifyAllPlayers(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }
            players.add(player);
            if(players.size() == numPlayers) sendStartGameCommand();
            return player;
        }
    }

    public int getPlayersInGame(){
        return players.size();
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public synchronized void handle(ResponseMsg responseMsg){
        switch (responseMsg.getPayload().get("gameAction").getAsString()){
            case "START_GAME_COMMAND":
                startHandler.startGame(responseMsg);
            case "TESTING_MESSAGE":
                handleTestMessage(responseMsg.getPayload());
                return;
            case "LEADER_ACTIVATION":
                turnHandler.leaderActivation(responseMsg);
                return;
            case "LEADER_ACTION":
                leaderCardHandler.leaderAction(responseMsg);
                return;
            case "MAIN_CHOICE":
                turnHandler.mainChoice(responseMsg);
                return;
            case "PICK":
                marketHandler.pick(responseMsg);
                return;
            case "WAREHOUSE_PLACEMENT":
                marketHandler.warehousePlacement(responseMsg);
                return;
            case "CHOOSE_PRODUCTION":
                productionHandler.chooseProduction(responseMsg);
                return;
            case "CHOOSE_DEVCARD":
                devCardHandler.chooseDevCard(responseMsg);
                return;
            case "DEVCARD_PLACEMENT":
                devCardHandler.devCardPlacement(responseMsg);
                return;
            case "COMEBACK":
                turnHandler.comeBack();
        }
    }

    private void startGame(){
        ArrayList<String> usernames = new ArrayList<>();
        for(PlayerHandler player: players) usernames.add(player.getUsername());
        try {
            currentGame = new Game(this.id, usernames);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //startHandler.startMatch()
    }

    public void sendStartGameCommand() {
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "All the players have joined the lobby! Type \"start\" to start the game!");
        payload.addProperty("gameAction", "START_GAME_COMMAND");
        payload.addProperty("activePlayerId", 0);
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "string");
        payload.add("expectedResponse", expectedResponse);
        notifyPlayer(players.get(0), new RequestMsg(MessageType.GAME_MESSAGE, payload));
    }

    public RequestMsg handleTestMessage(JsonObject response){
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "Server received: " + response.get("input").toString());
        payload.addProperty("gameAction", "TESTING_MESSAGE");
        payload.addProperty("activePlayerId", 0);
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "string");
        payload.add("expectedResponse", expectedResponse);
        return new RequestMsg(MessageType.GAME_MESSAGE, payload);
    }

    public void notifyAllPlayers(RequestMsg updateMsg){
        for(PlayerHandler player: players){
            player.newMessage(updateMsg);
        }
    }

    public void notifyPlayer(PlayerHandler player, RequestMsg updateMsg){
        player.newMessage(updateMsg);
    }

    public void notifyCurrentPlayer(RequestMsg updateMsg){
        for(PlayerHandler player: players){
            if(player.getPlayerId() == currentPlayer.getPlayerID()) {
                player.newMessage(updateMsg);
                break;
            }
        }
    }
}
