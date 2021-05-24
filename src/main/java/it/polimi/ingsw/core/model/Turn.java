package it.polimi.ingsw.core.model;

import java.util.ArrayList;

/**
 * Class to save the state of the turn. Also have a method that compute the next player
 */
public class Turn {
    private ArrayList<Player> players;
    private int currentPlayer;
    //0 -> no | 1 -> fine partita per dev | 2 -> fine partita per faith | 3 -> fine faith lorenzo | 4 -> fine carte sviluppo
    private int lastTurn;
    private boolean endGame;
    private int typeOfAction; //per decidere che tipo di update fare  3 per single player
    private int[] leaderCardDiscarded;
    private ArrayList<Player> blackList;

    public Turn(ArrayList<Player> players){
        this.players = players;
        leaderCardDiscarded = new int[2];
        currentPlayer = 0;
        blackList = new ArrayList<>();
        lastTurn = 0;
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
        boolean flag;
        do {
            flag = false;
            currentPlayer++;
            if (currentPlayer == players.size())
                currentPlayer = 0;
            for (Player player : blackList) {
                if (player.getPlayerID() == players.get(currentPlayer).getPlayerID()) {
                    flag = true;
                    break;
                }
            }
        }while(flag);
        return  players.get(currentPlayer);
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

    public void addInBlackList(int id){
        for (Player player : players) {
            if (player.getPlayerID() == id)
                blackList.add(player);
        }
    }

    public void removeFromBlacklist(int id){
        for (int i = 0; i < blackList.size(); i++) {
            if(blackList.get(i).getPlayerID() == id){
                blackList.remove(i);
                break;
            }
        }
    }
}

