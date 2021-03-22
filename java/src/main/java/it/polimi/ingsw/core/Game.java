package it.polimi.ingsw.core;

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

    /**
     * Class constructor.
     * @param id the id of the game
     * @param playerNames an ArrayList containing the names of the players which will be generated
     */
    public Game(int id, ArrayList<String> playerNames){
        this.gameId = id;
        for(int i = 0; i < playerNames.size(); i++){
            players.add(new Player(i, playerNames.get(i)));
        }
        this.market = new Market();
        this.devCardStructure = new DevCardStructure();
        this.leaderCards = new LeaderCardsDeck();
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
