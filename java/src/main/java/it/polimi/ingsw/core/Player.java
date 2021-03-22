package it.polimi.ingsw.core;

public class Player {
    private int playerID;
    private String nickname;
    private Board board;

    public Player(int playerID, String nickname)
    {
        this.playerID = playerID;
        this.nickname = nickname;
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getNickname() {
        return nickname;
    }
}
