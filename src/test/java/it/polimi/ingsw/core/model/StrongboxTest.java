package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.core.model.ResourceQty;
import it.polimi.ingsw.core.model.Strongbox;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrongboxTest {

    @Test
    public void testAdderGetterResource(){
        Strongbox sb = new Strongbox();
        ResourceQty rq = new ResourceQty(Resource.COIN, 3);
        sb.addResource(rq);

        assertEquals(sb.getResourceQtyX(Resource.COIN),3);
        assertEquals(sb.getResources().get(0), rq);
    }

    @Test
    public void testUseResource(){
        Strongbox sb = new Strongbox();
        ResourceQty rq = new ResourceQty(Resource.COIN, 3);
        ResourceQty rq2 = new ResourceQty(Resource.COIN, 2);

        sb.addResource(rq);
        sb.useResource(rq2);

        assertEquals(1, sb.getResourceQtyX(Resource.COIN));

    }

    @Test
    public void testDecreaseResource(){
        Strongbox sb = new Strongbox();
        ResourceQty rq = new ResourceQty(Resource.COIN, 3);
        ResourceQty rq2 = new ResourceQty(Resource.STONE, 2);
        int a[] = new int[4];
        a[0] = 1;
        a[1] = 2;

        sb.addResource(rq);
        sb.addResource(rq2);

        sb.decreaseResource(a);

        assertEquals(sb.getResourceQtyX(Resource.COIN), 2);
        assertEquals(sb.getResourceQtyX(Resource.STONE), 0);
    }




}