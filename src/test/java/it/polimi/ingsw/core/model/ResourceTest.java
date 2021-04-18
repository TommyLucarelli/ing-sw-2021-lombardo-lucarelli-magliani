package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.Resource;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceTest {

    @Test
    public void valueOf() {
        Resource resource = Resource.COIN;
        assertEquals(Resource.valueOf("COIN"),resource);
    }
}