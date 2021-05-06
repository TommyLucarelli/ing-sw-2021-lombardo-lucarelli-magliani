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
    private int[] leaderCardActivated; //0 se no, id se si
    private int[] leaderCardDiscarded;
    //TODO: implementare questi attributi nei metodi handler

    public Turn(ArrayList<Player> players){
        this.players = players;
        leaderCardActivated = new int[2]; //controlla se sono 0
        leaderCardDiscarded = new int[2];
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


    public int getTypeOfAction() {
        return typeOfAction;
    }

    public void setTypeOfAction(int typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

    public int[] getLeaderCardActivated() {
        int[] x = leaderCardActivated.clone();
        leaderCardActivated[0] = 0;
        leaderCardActivated[1] = 0;
        return x;
    }

    public void setLeaderCardActivated(int id) {
        if(leaderCardActivated[0] == 0)
            leaderCardActivated[0] = id;
        else if(leaderCardActivated[1] == 0)
            leaderCardActivated[1] = id;
        //se no bho, va controllato
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
}

