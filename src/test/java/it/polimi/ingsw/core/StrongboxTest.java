package it.polimi.ingsw.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class StrongboxTest {

    @Test
    public void testAdderGetterResource() {
        Strongbox sb = new Strongbox();
        ResourceQty rq = new ResourceQty(Resource.COIN, 3);
        sb.addResource(rq);

        assertEquals(sb.getResourceQtyX(Resource.COIN),3);
        assertEquals(sb.getResources().get(0), rq);
    }

    @Test
    public void testUseResource() {
        Strongbox sb = new Strongbox();
        ResourceQty rq = new ResourceQty(Resource.COIN, 3);
        ResourceQty rq2 = new ResourceQty(Resource.COIN, 2);

        sb.addResource(rq);
        sb.useResource(rq2);

        assertEquals(1, sb.getResourceQtyX(Resource.COIN));

    }



}