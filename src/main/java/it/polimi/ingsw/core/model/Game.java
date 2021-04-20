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
    private int faithTrackHead;

    /**
     * Class constructor.
     * @param id the id of the game
     * @param playerNames an ArrayList containing the names of the players which will be generated
     */
    public Game(int id, ArrayList<String> playerNames) throws FileNotFoundException {
        this.gameId = id;
        for(int i = 0; i < playerNames.size(); i++){
            getPlayers().add(new Player(i, playerNames.get(i)));
        }
        this.market = new Market();
        this.devCardStructure = new DevCardStructure();
        this.leaderCards = new LeaderCardsDeck();
    }

    public static void main( String[] args )
    {

    }

    public int getGameId() {
        return gameId;
    }

    public ArrayList<Player> getPlayers() {
        return players;
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

    public void FaithTrackUpdate(int p1, int p2, int p3, int p4){

    }
}
