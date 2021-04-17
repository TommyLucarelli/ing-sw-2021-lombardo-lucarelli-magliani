package it.polimi.ingsw.core;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testAddLeaderCard() {
        Board b = new Board();
        ArrayList<Flag> requiredFlags = new ArrayList<>();
        requiredFlags.add(new Flag(2,Colour.BLUE));
        LeaderCard lc = new LeaderCard(1, requiredFlags, null, 5, 4, Resource.COIN);
        LeaderCard x;

        b.addLeaderCard(lc);
        x = b.getLeaderCard(0);

        assertEquals(lc, x);
    }

    @Test
    public void testRemoveLeaderCard() {
        Board b = new Board();
        ArrayList<Flag> requiredFlags = new ArrayList<>();
        requiredFlags.add(new Flag(2,Colour.BLUE));
        LeaderCard lc = new LeaderCard(1, requiredFlags, null, 5, 4, Resource.COIN);
        LeaderCard x;

        b.addLeaderCard(lc);
        x = b.getLeaderCard(0);
        b.removeLeaderCard(lc);
        try{
            x = b.getLeaderCard(0);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Non ci sono carte leader in questo Deck");
        }

    }
}