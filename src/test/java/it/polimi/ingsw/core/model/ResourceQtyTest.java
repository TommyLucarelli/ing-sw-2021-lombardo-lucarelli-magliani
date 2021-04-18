package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.core.model.ResourceQty;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceQtyTest {

    @Test
    public void testGetResource() {
    }


    @Test
    public void testIncreaseQty() {
        ResourceQty r = new ResourceQty(Resource.COIN,3);
        r.increaseQty(1);
        assertEquals(r.getQty(),4);
    }

    @Test
    public void testDecreaseQty() {
        ResourceQty r = new ResourceQty(Resource.COIN,3);
        r.decreaseQty(1);
        assertEquals(r.getQty(),2);
    }
}