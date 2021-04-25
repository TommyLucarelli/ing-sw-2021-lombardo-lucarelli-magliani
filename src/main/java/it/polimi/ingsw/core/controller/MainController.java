package it.polimi.ingsw.core.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.*;
import it.polimi.ingsw.net.msg.*;
import it.polimi.ingsw.net.server.InvalidResponseException;

import java.util.ArrayList;

public class MainController{
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


    public MainController(int numPlayers)
    {
        //dobbiamo creare il game qua dentro???
        this.numPlayers = numPlayers;
        this.gameInProgress = false;
        this.players = new ArrayList<>();
        this.marketHandler = new MarketHandler(this);
        this.leaderCardHandler = new LeaderCardHandler(this);
        this.turnHandler = new TurnHandler(this);
        this.productionHandler = new ProductionHandler(this);
        this.devCardHandler = new DevCardHandler(this);
    }

    public PlayerHandler addPlayer(int id) throws InvalidResponseException {
        if(players.size() == 4) throw new InvalidResponseException("This lobby has already reached max capacity! Try again after a player leaves or create/join a new lobby");
        else {
            PlayerHandler player = new PlayerHandler(id, this);
            players.add(player);
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

    public synchronized RequestMsg handle(ResponseMsg responseMsg){
        switch (responseMsg.getPayload().get("gameAction").getAsString()){
            case "WAIT_FOR_PLAYERS":
                return handleWaitForPlayers();
            case "WAIT_START_GAME":
                return handleWaitStartGame();
            case "TESTING_MESSAGE":
                return handleTestMessage(responseMsg.getPayload());
            case "LEADER_ACTIVATION":
                return turnHandler.leaderActivation(responseMsg);
            case "LEADER_ACTION":
                return leaderCardHandler.leaderAction(responseMsg);
            case "MAIN_CHOICE":
                return turnHandler.mainChoice(responseMsg);
            case "PICK":
                return marketHandler.pick(responseMsg);
            case "WAREHOUSE_PLACEMENT":
                return marketHandler.warehousePlacement(responseMsg);
            case "CHOOSE_PRODUCTION":
                return productionHandler.chooseProduction(responseMsg);
            case "CHOOSE_DEVCARD":
                return devCardHandler.chooseDevCard(responseMsg);
            case "DEVCARD_PLACEMENT":
                return devCardHandler.devCardPlacement(responseMsg);
            case "COMEBACK":
                return turnHandler.comeBack(responseMsg);
        }
        return null;
    }

    public RequestMsg handleWaitForPlayers() {
        /**
        while(getPlayersInGame() != numPlayers) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         */
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "All the players have joined the lobby! Type \"start\" to start the game!");
        payload.addProperty("gameAction", "WAIT_FOR_PLAYERS");
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "string");
        payload.add("expectedResponse", expectedResponse);
        return new RequestMsg(MessageType.GAME_MESSAGE, payload);
    }

    public RequestMsg handleWaitStartGame(){
        /**
        while(!gameInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         */
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "The game has started!");
        payload.addProperty("gameAction", "TESTING_MESSAGE");
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "string");
        payload.add("expectedResponse", expectedResponse);
        return new RequestMsg(MessageType.GAME_MESSAGE, payload);
    }

    public RequestMsg handleTestMessage(JsonObject response){
        System.out.println("Testing message");
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "Server received: " + response.get("input").toString());
        payload.addProperty("gameAction", "TESTING_MESSAGE");
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "string");
        payload.add("expectedResponse", expectedResponse);
        return new RequestMsg(MessageType.GAME_MESSAGE, payload);
    }
}
