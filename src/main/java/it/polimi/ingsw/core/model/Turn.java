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

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
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
}

