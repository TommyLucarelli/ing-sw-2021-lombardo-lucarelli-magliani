package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DevCardSlotTest {

    @Test
    public void testAddCard() {
        DevCardSlot dcs = new DevCardSlot();
        Flag f = new Flag(1, Colour.BLUE);
        Flag f1 = new Flag(2, Colour.BLUE);
        ArrayList<ResourceQty> rq1 = new ArrayList<>();
        ArrayList<ResourceQty> rq2 = new ArrayList<>();
        ArrayList<ResourceQty> rq3 = new ArrayList<>();
        rq1.add(new ResourceQty(Resource.SERVANT));
        rq2.add(new ResourceQty(Resource.SERVANT));
        rq3.add(new ResourceQty(Resource.SERVANT));
        Recipe r = new Recipe(rq1, rq2);
        DevCard dc = new DevCard(1, f, r, rq3, 10);
        DevCard dc2 = new DevCard(2, f1, r, rq3, 5);
        DevCard dc3 = new DevCard(2, f1, r, rq3, 2);

        assertTrue(dcs.addCard(dc));
        assertEquals(dcs.getSlot().get(0), dc);

        assertTrue(dcs.addCard(dc2));
        assertEquals(dcs.getSlot().get(0), dc);

        assertFalse(dcs.addCard(dc3));
    }


    @Test
    public void testGetTopCard() {
        DevCardSlot dcs = new DevCardSlot();
        Flag f = new Flag(1, Colour.BLUE);
        Flag f1 = new Flag(2, Colour.BLUE);
        ArrayList<ResourceQty> rq1 = new ArrayList<>();
        ArrayList<ResourceQty> rq2 = new ArrayList<>();
        ArrayList<ResourceQty> rq3 = new ArrayList<>();
        rq1.add(new ResourceQty(Resource.SERVANT));
        rq2.add(new ResourceQty(Resource.SERVANT));
        rq3.add(new ResourceQty(Resource.SERVANT));
        Recipe r = new Recipe(rq1, rq2);
        DevCard dc = new DevCard(1, f, r, rq3, 10);
        DevCard dc2 = new DevCard(2, f1, r, rq3, 5);

        dcs.addCard(dc);
        assertEquals(dcs.getTopCard(), dc);

        dcs.addCard(dc2);
        assertEquals(dcs.getTopCard(), dc2);
    }

    @Test
    public void testGetSlotFlags() {
        DevCardSlot dcs = new DevCardSlot();
        Flag f = new Flag(1, Colour.BLUE);
        Flag f1 = new Flag(2, Colour.BLUE);
        ArrayList<ResourceQty> rq1 = new ArrayList<>();
        ArrayList<ResourceQty> rq2 = new ArrayList<>();
        ArrayList<ResourceQty> rq3 = new ArrayList<>();
        rq1.add(new ResourceQty(Resource.SERVANT));
        rq2.add(new ResourceQty(Resource.SERVANT));
        rq3.add(new ResourceQty(Resource.SERVANT));
        Recipe r = new Recipe(rq1, rq2);
        DevCard dc = new DevCard(1, f, r, rq3, 10);
        DevCard dc2 = new DevCard(2, f1, r, rq3, 5);
        dcs.addCard(dc);
        dcs.addCard(dc2);

        ArrayList<Flag> flags = new ArrayList<>();
        flags.add(f);
        flags.add(f1);

        assertEquals(dcs.getSlotFlags(), flags);
    }

    @Test
    public void testNumberOfDevCard() {
        DevCardSlot dcs = new DevCardSlot();
        Flag f = new Flag(1, Colour.BLUE);
        Flag f1 = new Flag(2, Colour.BLUE);
        ArrayList<ResourceQty> rq1 = new ArrayList<>();
        ArrayList<ResourceQty> rq2 = new ArrayList<>();
        ArrayList<ResourceQty> rq3 = new ArrayList<>();
        rq1.add(new ResourceQty(Resource.SERVANT));
        rq2.add(new ResourceQty(Resource.SERVANT));
        rq3.add(new ResourceQty(Resource.SERVANT));
        Recipe r = new Recipe(rq1, rq2);
        DevCard dc = new DevCard(1, f, r, rq3, 10);
        DevCard dc2 = new DevCard(2, f1, r, rq3, 5);
        dcs.addCard(dc);
        dcs.addCard(dc2);

        assertEquals(dcs.numberOfDevCard(), 2);
    }
}