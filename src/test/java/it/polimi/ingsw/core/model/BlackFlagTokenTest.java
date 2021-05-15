package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.BlackFlagToken;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlackFlagTokenTest {
    @Test
    public void testGetSpaces() {
        BlackFlagToken bft = new BlackFlagToken(1,true);

        assertEquals(bft.getSpaces(), 1);
    }

    @Test
    public void testShufflesDeck() {
        BlackFlagToken bft = new BlackFlagToken(1,true);

        assertTrue(bft.shufflesDeck());
    }

    @Test
    public void getAction() {
        BlackFlagToken bft = new BlackFlagToken(1,true);
        String s1 = "BFT1true";
        String s2;

        s2 = bft.getAction();

        assertEquals(s1,s2);

    }
}