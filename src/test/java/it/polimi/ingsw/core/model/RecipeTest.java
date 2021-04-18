package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.Recipe;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.core.model.ResourceQty;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RecipeTest {

    @Test
    public void testGetOutputResources() {
        ArrayList<ResourceQty> inputResources = new ArrayList<ResourceQty>();
        inputResources.add(new ResourceQty(Resource.SERVANT));
        ArrayList<ResourceQty> outputResource = new ArrayList<>();
        outputResource.add(new ResourceQty(Resource.SERVANT));
        Recipe r = new Recipe(inputResources,outputResource);
        assertNotNull(r.getOutputResources());
    }

    @Test
    public void testGetInputResources() {
        ArrayList<ResourceQty> inputResources = new ArrayList<ResourceQty>();
        inputResources.add(new ResourceQty(Resource.SERVANT));
        ArrayList<ResourceQty> outputResource = new ArrayList<>();
        outputResource.add(new ResourceQty(Resource.SERVANT));
        Recipe r = new Recipe(inputResources,outputResource);
        assertNotNull(r.getInputResources());
    }
}