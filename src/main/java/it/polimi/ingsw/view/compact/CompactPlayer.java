package it.polimi.ingsw.view.compact;

public class CompactPlayer {
    private final int playerID;
    private final String playerName;
    private final CompactBoard compactBoard;

    public CompactPlayer(int playerID, String playerName){
        compactBoard = new CompactBoard();
        this.playerID = playerID;
        this.playerName = playerName;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public CompactBoard getCompactBoard() {
        return compactBoard;
    }
}
