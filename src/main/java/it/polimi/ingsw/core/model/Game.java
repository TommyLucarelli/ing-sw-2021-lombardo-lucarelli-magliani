package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.controller.PlayerHandler;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class representing the main game
 * @author Giacomo Lombardo
 */
public class Game
{
    private int gameId;
    private ArrayList<Player> players;
    private Market market;
    private DevCardStructure devCardStructure;
    private LeaderCardsDeck leaderCards;
    private int faithTrackMarker;
    private Turn turn;
    private Boolean singlePlayer;

    /**
     * Class constructor.
     * @param id the id of the game
     */
    public Game(int id, ArrayList<PlayerHandler> playerHandlers) throws FileNotFoundException {
        this.gameId = id;
        this.players = new ArrayList<Player>();
        this.market = new Market();
        if(playerHandlers.size() == 1){
            singlePlayer = true;
        }else{
            singlePlayer = false;
        }

        for (int i = 0; i < playerHandlers.size(); i++) {
            players.add(new Player(playerHandlers.get(i).getPlayerId(), playerHandlers.get(i).getUsername(), this, singlePlayer));
        }
        this.devCardStructure = new DevCardStructure();
        this.leaderCards = new LeaderCardsDeck();
        faithTrackMarker = 0;

        if(singlePlayer)
            this.turn = new SingleTurn(this.players);
        else
            this.turn = new Turn(this.players);
    }


    public int getGameId() {
        return gameId;
    }

    public ArrayList<Player> getPlayers() {
        return (ArrayList<Player>) players.clone();
    }

    public Market getMarket() {
        return market;
    }

    public DevCardStructure getDevCardStructure() {
        return devCardStructure;
    }

    public LeaderCardsDeck getLeaderCards() {
        return leaderCards;
    }

    /**
     * Method to update the faithrack of all player simultaneously, in order to handle the pope actions
     * p represent the value of faithtrack movement
     * @param p1 main player
     * @param p2 other player
     * @return true if Favour Cards have been flipped, it's useful for shortupdate
     */
    public boolean faithTrackUpdate(Player play, int p1, int p2){
        boolean marker = false;
        boolean flag = false;
        int val = 0;
        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).equals(play)){
                for(int j = 0; j < p1; j++) {
                    marker = play.getBoard().getFaithTrack().moveFaithIndicator();
                    if(!flag)
                        flag = marker;
                    if (marker && (play.getBoard().getFaithTrack().getPosition() > val))
                        val = play.getBoard().getFaithTrack().getPosition(); //controllo se è 24 setta fine partita
                }
            }else{
                for(int j = 0; j < p2; j++) {
                    marker = players.get(i).getBoard().getFaithTrack().moveFaithIndicator();
                    if(!flag)
                        flag = marker;
                    if (marker && (players.get(i).getBoard().getFaithTrack().getPosition() > val))
                        val = players.get(i).getBoard().getFaithTrack().getPosition();
                }
            }
        }
        if(singlePlayer){
            for(int j = 0; j < p2; j++) {
                marker = ((SingleBoard)play.getBoard()).getLorenzoTrack().moveFaithIndicator();
                if(!flag)
                    flag = marker;
                if (marker && (((SingleBoard)play.getBoard()).getLorenzoTrack().getPosition() > val))
                    val = ((SingleBoard)play.getBoard()).getLorenzoTrack().getPosition();
            }
        }
        if(flag && (val > faithTrackMarker)){
            if(val == 24)
                turn.setLastTurn(1);
            faithTrackMarker = val;
            for(int i = 0; i < players.size(); i++){
                players.get(i).getBoard().getFaithTrack().setFavourCardsFlag(val); //potrebbe essere interessante controllare il true/false per il short update
            }
            if(singlePlayer){
                ((SingleBoard)play.getBoard()).getLorenzoTrack().setFavourCardsFlag(val);
            }
        }
        return marker;
    }

    public int getFaithTrackMarker() {
        return faithTrackMarker;
    }

    public Turn getTurn() {
        return turn;
    }

    public Player fromIdToPlayer(int id){
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getPlayerID() == id)
                return players.get(i); //clone
        }
        //gestire exception
        return null;
    }

    public Boolean getSinglePlayer() {
        return singlePlayer;
    }

}
