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

        turn.nextPlayer();
        assertEquals(turn.nextPlayer(), a);

        turn.addInBlackList(3);

        assertEquals(turn.nextPlayer(), c);
    }

    @Test
    public void testLeaderCardDiscarded(){
        ArrayList<Player> p = new ArrayList<>();
        p.add(new Player(0, "n", null, true));
        Turn turn = new Turn(p);

        turn.setLeaderCardDiscarded(1);
        turn.setLeaderCardDiscarded(2);

        int[] x = turn.getLeaderCardDiscarded();

        assertEquals(x[0], 1);
        assertEquals(x[1], 2);

        turn.resetDiscarded();
        x = turn.getLeaderCardDiscarded();

        assertEquals(x[0], 0);
        assertEquals(x[1], 0);
    }
}