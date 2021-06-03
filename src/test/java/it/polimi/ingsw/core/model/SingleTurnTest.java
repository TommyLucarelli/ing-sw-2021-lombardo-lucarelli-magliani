package it.polimi.ingsw.core.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SingleTurnTest {

    @Test
    public void testModifySoloActionToken() {
        ArrayList<Player> p = new ArrayList<>();
        p.add(new Player(0, "n", null, true));
        SingleTurn turn = new SingleTurn(p);

        DevCardToken dt = new DevCardToken(Colour.GREEN);

        turn.setSoloActionToken(dt);

        assertEquals(dt, turn.getSoloActionToken());

    }
}