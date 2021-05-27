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

    /**
     * Class constructor
     * @param players array of players in the game
     */
    public Turn(ArrayList<Player> players){
        this.players = players;
        leaderCardDiscarded = new int[2];
        currentPlayer = 0;
        blackList = new ArrayList<>();
        lastTurn = 0;
    }

    /**
     * Getter method
     * @return index of the current player in the array
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Getter method
     * @return whether is last turn or not, and why is last turn (0->no|1->7th card|2->faith|3->faithLorenzo|4->cardLorenzo)
     */
    public int isLastTurn() {
        return lastTurn;
    }

    /**
     * Setter method
     * @param x representing the reason of being the last turn
     */
    public void setLastTurn(int x) {
        if(lastTurn == 0)
            lastTurn = x;
    }

    /**
     * Getter method
     * @return boolean representing the final phase of a turn
     */
    public boolean isEndGame() {
        return endGame;
    }

    /**
     * Setter method
     * @param endGame
     */
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /**
     * Methot to compute next player. Blacklist is used to skip the players that are currently offline
     * @return the next player
     */
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

    /**
     * Getter method
     * @return
     */
    public int getTypeOfAction() {
        return typeOfAction;
    }

    /**
     * Setter method
     * @param typeOfAction
     */
    public void setTypeOfAction(int typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

    /**
     * Getter method
     * @return array of discarded card in that turn
     */
    public int[] getLeaderCardDiscarded() {
        int[] x = leaderCardDiscarded.clone();
        leaderCardDiscarded[0] = 0;
        leaderCardDiscarded[1] = 0;
        return x;
    }

    /**
     * Setter method
     * @param id the card that the player has discarded
     */
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

    /**
     * Method to put a player, momentarily offline, in the blackList
     * @param id of the player
     */
    public void addInBlackList(int id){
        for (Player player : players) {
            if (player.getPlayerID() == id)
                blackList.add(player);
        }
    }

    /**
     * Method to remove a player from the blackList
     * @param id of the player
     */
    public void removeFromBlacklist(int id){
        for (int i = 0; i < blackList.size(); i++) {
            if(blackList.get(i).getPlayerID() == id){
                blackList.remove(i);
                break;
            }
        }
    }
}

