package it.polimi.ingsw.core.model;

import java.util.ArrayList;

/**
 * Class to save the state of the turn. Also have a method that compute the next player
 */
public class Turn {
    private ArrayList<Player> players;
    private int currentPlayer;
    private int lastTurn; //0 -> no | 1 -> fine partita per dev | 2 -> fine partita per faith
    private boolean endGame;
    private int typeOfAction; //per decidere che tipo di update fare  3 per single player
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

    public int isLastTurn() {
        return lastTurn;
    }

    public void setLastTurn(int x) {
        if(lastTurn == 0)
            lastTurn = x;
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
        if(x == players.size()) //aggiungere controllo lastTurn -> magari exception
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

