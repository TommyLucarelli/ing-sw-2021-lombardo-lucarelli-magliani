package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.controller.PlayerHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
     * @param playerHandlers the playerHandlers of the players added to the game
     * @throws FileNotFoundException if the json files containing cards information cannot be found.
     */
    public Game(int id, ArrayList<PlayerHandler> playerHandlers) throws FileNotFoundException {
        this.gameId = id;
        this.players = new ArrayList<>();
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

    /**
     * Getter method
     * @return the game's id.
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * Getter method
     * @return the players of the game.
     */
    public ArrayList<Player> getPlayers() {
        return (ArrayList<Player>) players.clone();
    }

    /**
     * Getter method
     * @return the game's market
     */
    public Market getMarket() {
        return market;
    }

    /**
     * Getter method
     * @return the game's dev card structure
     */
    public DevCardStructure getDevCardStructure() {
        return devCardStructure;
    }

    /**
     * Getter method
     * @return the deck of leader cards.
     */
    public LeaderCardsDeck getLeaderCards() {
        return leaderCards;
    }

    /**
     * Method to update the faithrack of all player simultaneously, in order to handle the pope actions
     * p represent the value of faithtrack movement
     * @param play the player calling the faith track update
     * @param p1 main player
     * @param p2 other player
     */
    public void faithTrackUpdate(Player play, int p1, int p2){
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
                    if (marker && (play.getBoard().getFaithTrack().getPosition() > val)) {
                        val = play.getBoard().getFaithTrack().getPosition();
                        if(val == 24)
                            turn.setLastTurn(2);
                    }
                }
            }else{
                for(int j = 0; j < p2; j++) {
                    marker = players.get(i).getBoard().getFaithTrack().moveFaithIndicator();
                    if(!flag)
                        flag = marker;
                    if (marker && (players.get(i).getBoard().getFaithTrack().getPosition() > val)){
                        val = players.get(i).getBoard().getFaithTrack().getPosition();
                        if(val == 24)
                            turn.setLastTurn(2);
                    }
                }
            }
        }
        if(singlePlayer){
            for(int j = 0; j < p2; j++) {
                marker = ((SingleBoard)play.getBoard()).getLorenzoTrack().moveFaithIndicator();
                if(!flag)
                    flag = marker;
                if (marker && (((SingleBoard)play.getBoard()).getLorenzoTrack().getPosition() > val))
                {
                    val = ((SingleBoard)play.getBoard()).getLorenzoTrack().getPosition();
                    if(val == 24)
                        turn.setLastTurn(3);
                }
            }
        }
        if(flag && (val > faithTrackMarker)){
            faithTrackMarker = val;
            for(int i = 0; i < players.size(); i++){
                players.get(i).getBoard().getFaithTrack().setFavourCardsFlag(val); //potrebbe essere interessante controllare il true/false per il short update
            }
        }
    }

    /**
     * Getter method
     * @return the value of the faith track marker.
     */
    public int getFaithTrackMarker() {
        return faithTrackMarker;
    }

    /**
     * Getter method
     * @return the current turn.
     */
    public Turn getTurn() {
        return turn;
    }

    /**
     * Method to get a player from his id
     * @param id of the player
     * @return the player related to the id
     */
    public Player fromIdToPlayer(int id){
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getPlayerID() == id)
                return players.get(i); //clone
        }
        //gestire exception
        return null;
    }

    /**
     * Getter method
     * @return true if the game is a single player game
     */
    public Boolean getSinglePlayer() {
        return singlePlayer;
    }

}
