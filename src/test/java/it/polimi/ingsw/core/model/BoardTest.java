package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testAddLeaderCard() {
        Board b = new Board();
        ArrayList<Flag> requiredFlags = new ArrayList<>();
        requiredFlags.add(new Flag(2, Colour.BLUE));
        SpecialAbility specialAbility = new SpecialAbility(1, Resource.COIN);
        LeaderCard lc = new LeaderCard(1, requiredFlags, null, 5, specialAbility);
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
        SpecialAbility specialAbility = new SpecialAbility(1, Resource.COIN);
        LeaderCard lc = new LeaderCard(1, requiredFlags, null, 5, specialAbility);
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

    @Test
    public void testPersonalResQtyToArray() {
        Board b = new Board();
        int[] output = {3,2,4,1};
        int[] test = new int[4];

        for (int i = 0; i < 3; i++) {
            b.getWarehouse().getStructure().set(i,Resource.COIN);
        }
        for (int i = 3; i < 5; i++) {
            b.getWarehouse().getStructure().set(i,Resource.STONE);
        }
        for (int i = 5; i < 9; i++) {
            b.getWarehouse().getStructure().set(i,Resource.SHIELD);
        }
        b.getWarehouse().getStructure().set(9,Resource.SERVANT);

        test = b.personalResQtyToArray();

        assertArrayEquals(output,test);
    }

    @Test
    public void countFlags() {
    }

    @Test
    public void victoryPoints() {
    }

    @Test
    public void numberOfDevCard() {
    }
}