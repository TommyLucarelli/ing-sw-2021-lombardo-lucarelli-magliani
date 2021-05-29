package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.controller.PlayerHandler;
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
    public void faithTrackUpdate() {
        Game gioco = null;
        ArrayList<Player> players;
        ArrayList<PlayerHandler> playerHandlers = new ArrayList<>();
        playerHandlers.add(new PlayerHandler(1, "tom", null, null));
        playerHandlers.add(new PlayerHandler(2, "tom2", null, null));
        playerHandlers.add(new PlayerHandler(3, "tom3", null, null));
        try {
            gioco = new Game(222, playerHandlers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    @Test
    public void fromIdToPlayer() {
        Game gioco = null;
        ArrayList<Player> players;
        ArrayList<PlayerHandler> playerHandlers = new ArrayList<>();
        playerHandlers.add(new PlayerHandler(1, "tom", null, null));
        playerHandlers.add(new PlayerHandler(2, "tom2", null, null));
        playerHandlers.add(new PlayerHandler(3, "tom3", null, null));
        try {
            gioco = new Game(222, playerHandlers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        players = gioco.getPlayers();

        assertEquals(players.get(0), gioco.fromIdToPlayer(1));
        assertEquals(players.get(1), gioco.fromIdToPlayer(2));
        assertEquals(players.get(2), gioco.fromIdToPlayer(3));

    }

}
