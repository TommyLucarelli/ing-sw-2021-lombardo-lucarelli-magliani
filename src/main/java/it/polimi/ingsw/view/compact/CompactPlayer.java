package it.polimi.ingsw.view.compact;

public class CompactPlayer {
    private final int playerID;
    private final String playerName;
    private final CompactBoard compactBoard;
    private int victoryPoints;
    private Boolean lastTurn;

    public CompactPlayer(int playerID, String playerName){
        this.compactBoard = new CompactBoard();
        this.playerID = playerID;
        this.playerName = playerName;
        this.lastTurn = false;
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

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
