package it.polimi.ingsw.core.model;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Stack;

import static org.junit.Assert.*;

public class LeaderCardsDeckTest {
    @Test
    public void testLeaderCardsDeck() {
        try {
            LeaderCardsDeck deck = new LeaderCardsDeck();
            System.out.println(deck.getDeck().size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDrawCard(){
        Stack<LeaderCard> de;
        LeaderCardsDeck deck = null;
        try {
            deck = new LeaderCardsDeck();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        de = deck.getDeck();

        LeaderCard lc = deck.drawCard();

        assertEquals(lc, de.peek());
    }

}