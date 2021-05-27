package it.polimi.ingsw.core.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TurnTest {

    @Test
    public void testNextPlayer() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(1,"luca", null, false));
        Player a = new Player(2,"luca2", null, false);
        players.add(a);
        Player b = new Player(3,"luca3", null, false);
        players.add(b);
        Player c = new Player(4,"luca4", null, false);
        players.add(c);

        Turn turn = new Turn(players);

        assertEquals(turn.nextPlayer(), a);

        turn.addInBlackList(3);

        assertEquals(turn.nextPlayer(), c);
    }
}