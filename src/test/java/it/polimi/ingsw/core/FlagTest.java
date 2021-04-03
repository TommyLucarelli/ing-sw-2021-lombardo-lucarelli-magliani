package it.polimi.ingsw.core;

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
}