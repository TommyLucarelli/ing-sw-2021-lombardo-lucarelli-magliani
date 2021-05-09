package it.polimi.ingsw.core.model;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class GameTest
{
    @Test
    public void testFaithTrackUpdate() throws FileNotFoundException {
        ArrayList<Player> players;
        HashMap<Integer,String> playerNames = new HashMap<Integer, String>();
        playerNames.put(1,"Tommy");
        playerNames.put(2,"Toy");
        playerNames.put(3,"Tom");
        Game gioco = new Game(123, playerNames);
        players = gioco.getPlayers();
        for(int i=0; i<6; i++)
            players.get(0).getBoard().getFaithTrack().moveFaithIndicator();
        for(int i=0; i<3; i++)
            players.get(1).getBoard().getFaithTrack().moveFaithIndicator();
        for(int i=0; i<5; i++)
            players.get(2).getBoard().getFaithTrack().moveFaithIndicator();

        assertEquals(players.get(0).getBoard().getFaithTrack().getPosition(), 6);
        assertEquals(players.get(1).getBoard().getFaithTrack().getPosition(), 3);
        assertEquals(players.get(2).getBoard().getFaithTrack().getPosition(), 5);

        gioco.faithTrackUpdate(players.get(0), 2, 1);

        assertEquals(players.get(0).getBoard().getFaithTrack().getPosition(), 8);
        assertEquals(players.get(1).getBoard().getFaithTrack().getPosition(), 4);
        assertEquals(players.get(2).getBoard().getFaithTrack().getPosition(), 6);

        assertTrue(players.get(0).getBoard().getFaithTrack().getFavourCardsFlag(0));
        assertTrue(players.get(2).getBoard().getFaithTrack().getFavourCardsFlag(0));
        assertFalse(players.get(1).getBoard().getFaithTrack().getFavourCardsFlag(0));
    }
}
