package it.polimi.ingsw.core.model;

/**
 * Class rapresenting each single player
 * @author Tommaso Lucarelli
 */
public class Player {
    private int playerID;
    private String nickname;
    private Board board;

    /**
     * Class constructor
     * @param playerID unique identifier of the player
     * @param nickname nickname of the player
     */
    public Player(int playerID, String nickname)
    {
        this.playerID = playerID;
        this.nickname = nickname;
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
}

//TODO: (group) ma va fatto il test di una classe con solo getter?
