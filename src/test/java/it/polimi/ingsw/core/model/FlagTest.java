package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.Colour;
import it.polimi.ingsw.core.model.Flag;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlagTest {

    @Test
    public void getColour() {
        Flag flag = new Flag(1, Colour.GREEN);
        assertEquals(Colour.GREEN, flag.getColour());
    }

    @Test
    public void getLevel() {
        Flag flag = new Flag(1, Colour.GREEN);
        assertEquals(1, flag.getLevel());
    }

    @Test
    public void equalOrStrongerThan() {
        Flag flag1 = new Flag(2, Colour.GREEN);
        Flag flag2 = new Flag(1, Colour.GREEN);
        Flag flag3 = new Flag(1, Colour.BLUE);

        assertTrue(flag1.equalOrStrongerThan(flag2));
        assertTrue(flag1.equalOrStrongerThan(flag1));
        assertFalse(flag1.equalOrStrongerThan(flag3));
        assertFalse(flag2.equalOrStrongerThan(flag1));
    }
}