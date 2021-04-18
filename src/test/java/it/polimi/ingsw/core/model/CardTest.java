package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void getId() {
        ArrayList<ResourceQty> cost = new ArrayList<ResourceQty>();
        cost.add(new ResourceQty(Resource.COIN, 5));
        cost.add(new ResourceQty(Resource.STONE, 2));
        ArrayList<ResourceQty> endRes = new ArrayList<ResourceQty>();
        ArrayList<ResourceQty> startRes = new ArrayList<ResourceQty>();
        startRes.add(new ResourceQty(Resource.COIN, 1));
        startRes.add(new ResourceQty(Resource.SHIELD, 1));
        endRes.add(new ResourceQty(Resource.SERVANT, 2));
        endRes.add(new ResourceQty(Resource.STONE, 2));
        endRes.add(new ResourceQty(Resource.FAITH, 1));
        Recipe recipe = new Recipe(startRes, endRes);
        Card card = new DevCard(39, new Flag(3, Colour.BLUE), recipe, cost, 10);

        assertEquals(card.getId(), 39);
    }
}