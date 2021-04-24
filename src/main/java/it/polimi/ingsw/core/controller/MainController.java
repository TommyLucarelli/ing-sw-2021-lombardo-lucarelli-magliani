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


    public MainController(int numPlayers)
    {
        this.numPlayers = numPlayers;
        this.gameInProgress = false;
        this.players = new ArrayList<>();
       //currentGame = new Game(); mi serve capire chi crea il controller per passare i dati giusti al game
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

    /*
    public Player computeNextPlayer(Player p){
        //passa il giocatore successivo
        //se è single mode restituisce Lorenzo che fa il suo turno speciale tipo
    }*/

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
