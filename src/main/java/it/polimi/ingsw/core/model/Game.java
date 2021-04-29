package it.polimi.ingsw.core.model;

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

    /**
     * Class constructor.
     * @param id the id of the game
     * @param playerNames an ArrayList containing the names of the players which will be generated
     */
    public Game(int id, ArrayList<String> playerNames) throws FileNotFoundException {
        this.gameId = id;
        this.players = new ArrayList<Player>();
        for(int i = 0; i < playerNames.size(); i++){
            players.add(new Player(i, playerNames.get(i)));
        }
        this.market = new Market();
        this.devCardStructure = new DevCardStructure();
        this.leaderCards = new LeaderCardsDeck();
        this.turn = new Turn();
        faithTrackMarker = 0;
    }

    public static void main( String[] args )
    {

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
                        val = play.getBoard().getFaithTrack().getPosition();;
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
        if(flag && (val > faithTrackMarker)){
            faithTrackMarker = val;
            for(int i = 0; i < players.size(); i++){
                players.get(i).getBoard().getFaithTrack().setFavourCardsFlag(val); //potrebbe essere interessante controllare il true/false per il short update
            }
        }
        if(marker)
            return true;
        else
            return false;
    }

    public int getFaithTrackMarker() {
        return faithTrackMarker;
    }

    public Turn getTurn() {
        return turn;
    }
}
