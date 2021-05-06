package it.polimi.ingsw.view.compact;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class CardCollectorTest {

    @Test
    public void testGetDevCard() throws FileNotFoundException {
        CardCollector collector = new CardCollector();
        assertEquals(collector.getDevCard(1).getId(), 1);
        assertEquals(collector.getDevCard(48).getId(), 48);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetDevCardException1() throws FileNotFoundException {
        CardCollector collector = new CardCollector();
        System.out.println(collector.getDevCard(0).getId());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetDevCardException2() throws FileNotFoundException {
        CardCollector collector = new CardCollector();
        System.out.println(collector.getDevCard(49).getId());
    }

    @Test
    public void testGetLeaderCard() throws FileNotFoundException{
        CardCollector collector = new CardCollector();
        assertEquals(collector.getLeaderCard(49).getId(), 49);
        assertEquals(collector.getLeaderCard(64).getId(), 64);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetLeaderCardException1() throws FileNotFoundException {
        CardCollector collector = new CardCollector();
        System.out.println(collector.getLeaderCard(0).getId());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetLeaderCardException2() throws FileNotFoundException {
        CardCollector collector = new CardCollector();
        System.out.println(collector.getLeaderCard(100).getId());
    }
}