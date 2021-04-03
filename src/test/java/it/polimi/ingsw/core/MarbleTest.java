package it.polimi.ingsw.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class MarbleTest {

    @Test
    public void valueOf() {
        Marble marble = Marble.WHITE;
        assertEquals(Marble.valueOf("WHITE"), marble);
    }

}