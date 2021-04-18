package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.Marble;
import it.polimi.ingsw.core.model.Resource;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarbleTest {

    @Test
    public void valueOf() {
        Marble marble = Marble.WHITE;
        assertEquals(Marble.valueOf("WHITE"), marble);
    }

    @Test
    public void toResource() {
        assertEquals(Marble.GREY.toResource(), Resource.STONE);
    }
}