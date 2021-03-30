package it.polimi.ingsw.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlackFlagTokenTest {

    @Test
    public void getAction() {
        BlackFlagToken bft = new BlackFlagToken(1,true);
        String s1 = "BFT1true";
        String s2;

        s2 = bft.getAction();

        assertEquals(s1,s2);

    }
}