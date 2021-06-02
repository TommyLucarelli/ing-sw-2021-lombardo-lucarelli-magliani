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
        if(getPlayers().size() == numPlayers) throw new InvalidResponseException("This lobby has already reached max capacity! Try again after a player leaves or create(1)/join(2) a new lobby");
        if(gameInProgress) throw new InvalidResponseException("This lobby has already a game in progress! Try again later or create(1)/join(2) a new lobby");
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
            if(getPlayers().size() == numPlayers && numPlayers > 1) sendStartGameCommand();
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

    /**
     * Method to handle the different messages from the client
     * @param responseMsg message from the client
     */
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

    /**
     * Method to start the game, when the first player is ready
     */
    private void startGame(){
        Collections.shuffle(players);
        try {
            currentGame = new Game(this.id, players);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        startHandler.startMatch();
    }

    /**
     * Method to send the command to start the game
     */
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

    /**
     * Method to send a broadcast message to all the players, usually used for update
     * @param updateMsg
     */
    public void notifyAllPlayers(RequestMsg updateMsg){
        for(PlayerHandler player: getPlayers()){
            player.newMessage(updateMsg);
        }
    }

    /**
     * Method to send a message to a specific player
     * @param player 
     * @param updateMsg
     */
    public void notifyPlayer(PlayerHandler player, RequestMsg updateMsg){
        player.newMessage(updateMsg);
    }

    /**
     * Method to send a message to the current player
     * @param updateMsg
     */
    public void notifyCurrentPlayer(RequestMsg updateMsg){
        for(PlayerHandler player: getPlayers()){
            if(player.getPlayerId() == currentPlayer.getPlayerID()) {
                player.newMessage(updateMsg);
                break;
            }
        }
    }

    /**
     * Method that builds and sends the update to all players at the end of the round.
     * Inside the update there are all the structures that have been modified in the model. The structures are sent in compact format
     * as a json file.
     */
    public void updateBuilder(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "UPDATE");
        int x = currentGame.getTurn().getTypeOfAction();

        //aggiungi messaggio lastTurn

        if(currentGame.getSinglePlayer()){
            payload.addProperty("message", ((SingleTurn)currentGame.getTurn()).getSoloActionToken().getMessage());
        }else {
            if (x == 1)
                payload.addProperty("message", currentPlayer.getNickname() + " has taken resources from the Market");
            else if (x == 2)
                payload.addProperty("message", currentPlayer.getNickname() + " has bought a Development Card");
            else if(x == 3)
                payload.addProperty("message", currentPlayer.getNickname() + " has activated the production");
        }

        currentGame.getTurn().setTypeOfAction(0);

        if(currentGame.getTurn().isLastTurn() == 1)
            payload.addProperty("endMessage", "\nThis is the final turn, because "+currentPlayer.getNickname()+" has bought the seventh development card \n");
        else if(currentGame.getTurn().isLastTurn() == 2){
            for (PlayerHandler player : players) {
                Player p = currentGame.fromIdToPlayer(player.getPlayerId());
                if (p.getBoard().getFaithTrack().getPosition() == 24) {
                    payload.addProperty("endMessage", "\nThis is the final turn, because " + p.getNickname() + " has reached the last position in the faith track\n");
                    break;
                }
            }
        }
        payload.addProperty("currentPlayerID", currentPlayer.getPlayerID());

        //controlle se siamo nella fase finale, manda messaggio fine partita
        Player oldPlayer = currentPlayer;
        currentPlayer = currentGame.getTurn().nextPlayer(); //controllo finepartita

        payload.addProperty("nextPlayerID", currentPlayer.getPlayerID());
        Gson gson = new Gson();

        String json = gson.toJson(currentGame.getTurn().getLeaderCardDiscarded());
        payload.addProperty("discardedLeaderCards", json);
        currentGame.getTurn().resetDiscarded();

        payload.addProperty("action", true);

        payload.add("market", currentGame.getMarket().toCompactMarket());
        payload.add("devCardStructure", currentGame.getDevCardStructure().toCompactDevCardStructure());

        payload.add("player", oldPlayer.toCompactPlayer());

        JsonArray playersArray = new JsonArray();
        for (int i=0; i<players.size();i++) {
            playersArray.add(currentGame.fromIdToPlayer(players.get(i).getPlayerId()).toCompactFaith2());
        }
        payload.add("faithTracks", playersArray);

        if(currentGame.getSinglePlayer()){
            payload.add("lorenzoTrack", ((SingleBoard)currentPlayer.getBoard()).getLorenzoTrack().toCompactFaithTrack());
        }

        this.notifyAllPlayers(new RequestMsg(MessageType.GAME_MESSAGE, payload));
    }

    /**
     *  Method to prepare the initialUpdate. This update contains all the choice that the players have taken
     *  in the first phase of the game.
     */
    public void initialUpdate(){
        currentPlayer = currentGame.getTurn().nextPlayer();

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

    /**
     * Counter for the first phase of the game. It counts the number of players that have completed the first phase.
     * @return true, if all the players are ready to start the real turn.
     */
    public boolean setCountStartPhase() {
        countStartPhase++;
        if(countStartPhase==numPlayers)
            return true;
        else
            return false;
    }

    /**
     * Method that prepare the final update, with the winner and the victory points of each player
     * @param playerID of the player that has asked for the final update
     */
    public void finalUpdate(int playerID){
        JsonObject payload = new JsonObject();
        JsonArray p = new JsonArray();
        ArrayList<Integer> results = new ArrayList<>();
        Player player;
        int vp;

        for (int i = 0; i < players.size(); i++) {
            player = currentGame.fromIdToPlayer(players.get(i).getPlayerId());
            vp = player.getBoard().victoryPoints();
            if(!results.contains(vp))
                results.add(vp);
        }
        results.sort(Collections.reverseOrder());
        for (int i = 0; i < results.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                player = currentGame.fromIdToPlayer(players.get(j).getPlayerId());
                if(player.getBoard().victoryPoints() == results.get(i)){
                    JsonObject payload2 = new JsonObject();
                    payload2.addProperty("position", i+1);
                    payload2.addProperty("playerID", player.getPlayerID());
                    payload2.addProperty("name", player.getNickname());
                    payload2.addProperty("victoryPoints", results.get(i));
                    p.add(payload2);
                }
            }
        }
        payload.add("players", p);

        if(currentGame.getSinglePlayer())
            payload.addProperty("finalScenario", currentGame.getTurn().isLastTurn());

        payload.addProperty("gameAction", "FINAL_UPDATE");

        this.notifyPlayer(fromIdToPlayerHandler(playerID), new RequestMsg(MessageType.GAME_MESSAGE, payload));
    }

    /**
     * Method to handle the event of a player going offline
     * @param playerHandler
     */
    public void handleDisconnection(PlayerHandler playerHandler){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "SHORT_UPDATE");
        payload.addProperty("message", "\nINFO: "+playerHandler.getUsername()+" has disconnected\n");
        this.notifyAllPlayers(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        boolean flag = currentGame.getTurn().addInBlackList(playerHandler.getPlayerId());

        if(!flag) {
            if (countStartPhase == players.size()) {
                if (playerHandler.getPlayerId() == currentPlayer.getPlayerID()){
                    if(getCurrentGame().getTurn().getTypeOfAction()==2){
                        devCardHandler.disconnectionPlacement();
                    }
                    updateBuilder();
                }
            } else {
                //rimuovo due carte leader a caso se non l'ha fatto
                currentGame.fromIdToPlayer(playerHandler.getPlayerId()).getBoard().randomRemoveLeaderCard();
                boolean check = setCountStartPhase();
                if (check)
                    initialUpdate();
            }
        }
    }

    /**
     * Method to handle the event of a player coming back online
     * @param playerHandler
     */
    public void handleReconnection(PlayerHandler playerHandler){
        JsonObject payload = new JsonObject();
        for (PlayerHandler player : players) {
            if (player.equals(playerHandler)) {
                if(currentGame.getTurn().blackListSize() == numPlayers){
                    currentGame.getTurn().removeFromBlacklist(playerHandler.getPlayerId());
                    currentPlayer = currentGame.getTurn().nextPlayer();
                    reconnectionUpdate(playerHandler, true);
                }else {
                    currentGame.getTurn().removeFromBlacklist(playerHandler.getPlayerId());
                    if (countStartPhase == players.size())
                        reconnectionUpdate(playerHandler, false);
                    else {
                        payload.addProperty("gameAction", "SHORT_UPDATE");
                        payload.addProperty("message", "\nWait for others player\n");
                        this.notifyPlayer(player, new RequestMsg(MessageType.GAME_MESSAGE, payload));
                    }
                }
            } else {
                payload.addProperty("gameAction", "SHORT_UPDATE");
                payload.addProperty("message", "\nINFO: " + playerHandler.getUsername() + " has reconnected\n");
                this.notifyPlayer(player, new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }

        }

    }

    private void reconnectionUpdate(PlayerHandler playerHandler, boolean first){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "RECONNECTION_UPDATE");

        if(first)
            payload.addProperty("first", true);

        if(currentGame.getSinglePlayer()){
            payload.add("lorenzoTrack", ((SingleBoard)currentPlayer.getBoard()).getLorenzoTrack().toCompactFaithTrack());
        }

        payload.addProperty("currPlayer", currentPlayer.getNickname());

        payload.addProperty("myPlayerID", playerHandler.getPlayerId());
        payload.addProperty("myName", playerHandler.getUsername());

        payload.add("market", currentGame.getMarket().toCompactMarket());
        payload.add("devCardStructure", currentGame.getDevCardStructure().toCompactDevCardStructure());

        payload.addProperty("numOfPlayers", players.size());

        JsonArray playersArray = new JsonArray();
        for (int i=0; i<players.size();i++) {
            playersArray.add(currentGame.fromIdToPlayer(players.get(i).getPlayerId()).toCompactPlayerInitial());
        }
        payload.add("players", playersArray);

        this.notifyPlayer(playerHandler, new RequestMsg(MessageType.GAME_MESSAGE, payload));
    }

}
