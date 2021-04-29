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
        a.add(Resource.ANY);
        a.add(Resource.ANY);
        a.add(Resource.ANY);
        a.add(Resource.ANY);

        w.updateConfiguration(a);
        b = w.getStructure();

        assertEquals(a, b);

        b.add(Resource.STONE);
        a = w.getStructure();

        assertNotEquals(a, b);
    }

    @Test
    public void testDecResWarehouse(){
        Warehouse w = new Warehouse();
        ArrayList<Resource> a = new ArrayList<>();
        ArrayList<Resource> c;
        int b[] = new int[4];
        b[0] = 1;
        b[1] = 1;
        b[3] = 1;
        a.add(Resource.STONE);
        a.add(Resource.ANY);
        a.add(Resource.COIN);
        a.add(Resource.SERVANT);
        a.add(Resource.SERVANT);
        a.add(Resource.SERVANT);
        a.add(Resource.ANY);
        a.add(Resource.ANY);
        a.add(Resource.ANY);
        a.add(Resource.ANY);

        w.updateConfiguration(a);

        w.decResWarehouse(b);

        c = w.getStructure();

        assertEquals(c.get(0), Resource.ANY);
        assertEquals(c.get(2), Resource.ANY);
        assertEquals(c.get(3), Resource.SERVANT);
        assertEquals(c.get(5), Resource.ANY);
    }
}