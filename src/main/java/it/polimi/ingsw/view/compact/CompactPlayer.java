package it.polimi.ingsw.view.compact;

public class CompactPlayer {
    private int playerID;
    private String playerName;
    private CompactBoard compactBoard;

    public CompactPlayer(int playerID, String playerName){
        compactBoard = new CompactBoard();
        this.playerID = playerID;
        this.playerName = playerName;
    }
}
