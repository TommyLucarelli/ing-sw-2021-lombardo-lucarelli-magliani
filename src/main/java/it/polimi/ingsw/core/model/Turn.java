package it.polimi.ingsw.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Class to save the state of the turn. Also have a method that compute the next player
 */
public class Turn {
    private ArrayList<Player> players;
    private int currentPlayer;
    private boolean lastTurn;
    private boolean endGame;
    private int typeOfAction; //per decidere che tipo di update fare

    public Turn(ArrayList<Player> players){
        this.players = players;
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
        if(x==4)
            x=0;
        currentPlayer = x;
        return  players.get(x);
    }


}

