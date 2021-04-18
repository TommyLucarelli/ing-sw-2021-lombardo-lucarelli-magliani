package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.core.model.Warehouse;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WarehouseTest {

    @Test
    public void testUpdateConfiguration() {
        Warehouse w = new Warehouse();
        ArrayList<Resource> a = new ArrayList<>();
        ArrayList<Resource> b = new ArrayList<>();
        a.add(Resource.STONE);
        a.add(Resource.ANY);
        a.add(Resource.COIN);
        a.add(Resource.SERVANT);
        a.add(Resource.SERVANT);
        a.add(Resource.SERVANT);

        w.updateConfiguration(a);
        b = w.getStructure();

        assertEquals(a, b);

        b.add(Resource.STONE);
        a = w.getStructure();

        assertNotEquals(a, b);
    }
}