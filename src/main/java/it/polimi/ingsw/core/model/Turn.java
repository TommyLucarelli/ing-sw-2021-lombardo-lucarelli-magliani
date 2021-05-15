package it.polimi.ingsw.core.model;

import java.util.ArrayList;

/**
 * Class to save the state of the turn. Also have a method that compute the next player
 */
public class Turn {
    private ArrayList<Player> players;
    private int currentPlayer;
    private boolean lastTurn;
    private boolean endGame;
    private int typeOfAction; //per decidere che tipo di update fare
    private int[] leaderCardDiscarded;
    //TODO: controllo che resetto tutto a fine turno

    public Turn(ArrayList<Player> players){
        this.players = players;
        leaderCardDiscarded = new int[2];
        currentPlayer = 0;
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isLastTurn() {
        return lastTurn;
    }

    public void setLastTurn(boolean lastTurn) {
        this.lastTurn = lastTurn;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }


    public Player nextPlayer(){
        int x;
        x = currentPlayer+1;
        if(x == players.size())
            x=0;
        currentPlayer = x;
        return  players.get(x);
    }


    public int getTypeOfAction() {
        return typeOfAction;
    }

    public void setTypeOfAction(int typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

    public int[] getLeaderCardDiscarded() {
        int[] x = leaderCardDiscarded.clone();
        leaderCardDiscarded[0] = 0;
        leaderCardDiscarded[1] = 0;
        return x;
    }

    public void setLeaderCardDiscarded(int id) {
        if(leaderCardDiscarded[0] == 0)
            leaderCardDiscarded[0] = id;
        else if(leaderCardDiscarded[1] == 0)
            leaderCardDiscarded[1] = id;
    }

    public void resetDiscarded(){
        leaderCardDiscarded[0] = 0;
        leaderCardDiscarded[1] = 0;
    }
}

