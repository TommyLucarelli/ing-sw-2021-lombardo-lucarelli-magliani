package it.polimi.ingsw.core.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.*;
import it.polimi.ingsw.net.msg.*;
import it.polimi.ingsw.net.server.InvalidResponseException;
import it.polimi.ingsw.net.server.RequestManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
    private int countStartPhase;
    //WARNING: non confondersi tra istanze di player e di playerHandler

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
        countStartPhase = 0;
    }

    public PlayerHandler addPlayer(int id, String username, RequestManager manager) throws InvalidResponseException {
        if(getPlayers().size() == 4) throw new InvalidResponseException("This lobby has already reached max capacity! Try again after a player leaves or create/join a new lobby");
        else {
            PlayerHandler player = new PlayerHandler(id, username,this, manager);
            if(getPlayers().size() != 0){
                JsonObject payload = new JsonObject();
                payload.addProperty("gameAction", "SHORT_UPDATE");
                payload.addProperty("message", username + " has joined the lobby.");
                payload.addProperty("activePlayerId", 0);
                notifyAllPlayers(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }
            getPlayers().add(player);
            if(getPlayers().size() == numPlayers) sendStartGameCommand();
            return player;
        }
    }

    public int getPlayersInGame(){
        return getPlayers().size();
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
                startGame();
                return;
            case "TESTING_MESSAGE":
                handleTestMessage(responseMsg.getPayload());
                return;
            case "CHOOSE_START_LEADERS":
                startHandler.chooseStartLeaders(responseMsg);
                return;
            case "CHOOSE_START_RESOURCES":
                startHandler.chooseStartResources(responseMsg);
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
            case "COME_BACK":
                turnHandler.comeBack();
                return;
            case "UPDATE":
            case "INITIAL_UPDATE":
                turnHandler.update(responseMsg);
                return;
            default:
                return;

        }
    }

    private void startGame(){
        HashMap<Integer,String> playerInfo = new HashMap<Integer, String>();
        Collections.shuffle(players); //ma lo fa sto shuffle
        for(PlayerHandler player: players) {
            playerInfo.put(player.getPlayerId(), player.getUsername());
        }
        try {
            currentGame = new Game(this.id, playerInfo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        currentPlayer = currentGame.fromIdToPlayer(players.get(0).getPlayerId());
        startHandler.startMatch();
    }

    public void sendStartGameCommand() {
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "All the players have joined the lobby! Type \"start\" to start the game!");
        payload.addProperty("gameAction", "START_GAME_COMMAND");
        payload.addProperty("activePlayerId", 0);
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "string");
        expectedResponse.addProperty("regex", "(start)");
        payload.add("expectedResponse", expectedResponse);
        notifyPlayer(getPlayers().get(0), new RequestMsg(MessageType.GAME_MESSAGE, payload));
    }

    public void handleTestMessage(JsonObject response){
        JsonObject payload = new JsonObject();
        payload.addProperty("message", "Server received: " + response.get("input").toString());
        payload.addProperty("gameAction", "TESTING_MESSAGE");
        payload.addProperty("activePlayerId", 0);
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("type", "string");
        payload.add("expectedResponse", expectedResponse);
        new RequestMsg(MessageType.GAME_MESSAGE, payload);
    }

    public void notifyAllPlayers(RequestMsg updateMsg){
        for(PlayerHandler player: getPlayers()){
            player.newMessage(updateMsg);
        }
    }

    public void notifyPlayer(PlayerHandler player, RequestMsg updateMsg){
        player.newMessage(updateMsg);
    }

    public void notifyCurrentPlayer(RequestMsg updateMsg){
        for(PlayerHandler player: getPlayers()){
            if(player.getPlayerId() == currentPlayer.getPlayerID()) {
                player.newMessage(updateMsg);
                break;
            }
        }
    }


    public void updateBuilder(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "UPDATE");
        int x = currentGame.getTurn().getTypeOfAction();

        if (x == 0)
            payload.addProperty("message", currentPlayer.getNickname() + " has taken resources from the Market");
        else if (x == 1)
            payload.addProperty("message", currentPlayer.getNickname() + " has bought a Development Card");
        else
            payload.addProperty("message", currentPlayer.getNickname() + " has activated the production");


        payload.addProperty("currentPlayerID", currentPlayer.getPlayerID());
        System.out.println("finish player "+currentPlayer.getPlayerID());
        //compute next player
        Player oldPlayer = currentPlayer;
        currentPlayer = currentGame.getTurn().nextPlayer();
        System.out.println("next player "+currentPlayer.getPlayerID());

        payload.addProperty("nextPlayerID", currentPlayer.getPlayerID());
        Gson gson = new Gson();
        String json = gson.toJson(oldPlayer.getBoard().getAbilityActivationFlag());
        payload.addProperty("abilityActivationFlag", json);

        json = gson.toJson(currentGame.getTurn().getLeaderCardDiscarded());
        payload.addProperty("discardedLeaderCards", json);
        currentGame.getTurn().resetDiscarded();

        payload.addProperty("action", true);

        if(x == 0){
            payload.addProperty("action", true);
            payload.add("market", currentGame.getMarket().toCompactMarket());
        }if(x == 1)
        {
            payload.addProperty("action", false);
            payload.add("devCardStructure", currentGame.getDevCardStructure().toCompactDevCardStructure());
        }


        payload.add("player", oldPlayer.toCompactPlayer());

        this.notifyAllPlayers(new RequestMsg(MessageType.GAME_MESSAGE, payload));
    }

    public void initialUpdate(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "INITIAL_UPDATE");
        payload.addProperty("nextPlayerID", currentPlayer.getPlayerID());

        payload.add("market", currentGame.getMarket().toCompactMarket());
        payload.add("devCardStructure", currentGame.getDevCardStructure().toCompactDevCardStructure());

        payload.addProperty("numOfPlayers", players.size());

        JsonArray playersArray = new JsonArray();
        for (int i=0; i<players.size();i++) {
            playersArray.add(currentGame.fromIdToPlayer(players.get(i).getPlayerId()).toCompactPlayerInitial());
            //verificare se il nome della proprietà va bene
        }
        payload.add("players", playersArray);

        this.notifyAllPlayers(new RequestMsg(MessageType.GAME_MESSAGE, payload));
    }


    public ArrayList<PlayerHandler> getPlayers() {
        return players;
    }

    public PlayerHandler fromIdToPlayerHandler(int id){
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getPlayerId() == id)
                return players.get(i); //clone
        }
        //gestire exception
        return null;
    }

    public boolean setCountStartPhase() {
        countStartPhase++;
        if(countStartPhase==numPlayers)
            return true;
        else
            return false;
    }
}
