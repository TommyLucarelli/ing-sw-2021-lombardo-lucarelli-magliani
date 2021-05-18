package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Class rapresenting each single player
 * @author Tommaso Lucarelli
 */
public class Player {
    private int playerID;
    private String nickname;
    private Board board;
    private Game game;

    /**
     * Class constructor
     * @param playerID unique identifier of the player
     * @param nickname nickname of the player
     */
    public Player(int playerID, String nickname, Game game)
    {
        this.playerID = playerID;
        this.nickname = nickname;
        this.game = game;
        board = new Board();
    }

    /**
     * Getter method
     * @return the board of the player
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Getter method
     * @return the ID of the player
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Getter method
     * @return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }


    public JsonObject toCompactPlayer(){
        JsonObject payload = new JsonObject();
        payload.add("devCardSlots", board.toCompactDevCardSlots());
        payload.add("warehouse", board.getWarehouse().toCompactWarehouse());
        payload.add("strongbox", board.getStrongbox().toCompactStrongBox());

        Gson gson = new Gson();
        String json = gson.toJson(game.getTurn().getLeaderCardDiscarded());
        payload.addProperty("discardedLeaderCard", json);

        return payload;
    }

    public JsonObject toCompactPlayerInitial(){
        JsonObject payload = new JsonObject();
        payload.addProperty("playerID", playerID);
        payload.addProperty("playerName", nickname);
        payload.add("faithTrack", board.getFaithTrack().toCompactFaithTrack());
        payload.add("warehouse", board.getWarehouse().toCompactWarehouse());

        return payload;
    }

    public JsonObject toCompactFaith2(){
        JsonObject payload = new JsonObject();
        payload.addProperty("playerID", playerID);
        payload.add("faithTrack", board.getFaithTrack().toCompactFaithTrack());
        return payload;
    }
}

