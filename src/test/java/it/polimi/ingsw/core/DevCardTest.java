package it.polimi.ingsw.core;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DevCardTest {

    @Test
    public void getFlag() {
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
        DevCard devCard = new DevCard(39, new Flag(3, Colour.BLUE), recipe, cost, 10);

        assertEquals(devCard.getFlag().getColour(), Colour.BLUE);
        assertEquals(devCard.getFlag().getLevel(), 3);
    }

    @Test
    public void getRecipe() {
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
        DevCard devCard = new DevCard(39, new Flag(3, Colour.BLUE), recipe, cost, 10);

        assertEquals(devCard.getRecipe().getInputResources().get(0).getQty(), 1);
        assertEquals(devCard.getRecipe().getInputResources().get(0).getResource(), Resource.COIN);
        assertEquals(devCard.getRecipe().getOutputResources().get(1).getQty(), 2);
        assertEquals(devCard.getRecipe().getOutputResources().get(1).getResource(), Resource.STONE);
    }

    @Test
    public void getCost() {
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
        DevCard devCard = new DevCard(39, new Flag(3, Colour.BLUE), recipe, cost, 10);

        assertEquals(devCard.getCost().get(0).getResource(), Resource.COIN);
        assertEquals(devCard.getCost().get(0).getQty(), 5);
    }

    @Test
    public void getVictoryPoints() {
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
        DevCard devCard = new DevCard(39, new Flag(3, Colour.BLUE), recipe, cost, 10);

        assertEquals(devCard.getVictoryPoints(), 10);
    }
}