package it.polimi.ingsw.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class MarketTest {

    @Test
    public void testGetLine() {
        Market m = new Market();
        ArrayList<Marble> marble, marble2;
        ArrayList<Resource> resources;
        Marble resMarble;

        marble = m.chooseLine(1);
        resMarble = m.getReserveMarble();
        resources = m.getLine(1);

        for (int i=0; i<4; i++) {
            assertEquals(marble.get(i).toResource(), resources.get(i));
        }

        marble2 = m.chooseLine(1);
        for (int i=0; i<3;i++){
            assertEquals(marble2.get(i), marble.get(i+1));
        }
        assertEquals(marble2.get(3),resMarble);
        assertEquals(m.getReserveMarble(),marble.get(0));
    }

    @Test
    public void testGetColumn() {
        Market m = new Market();
        ArrayList<Marble> marble, marble2;
        ArrayList<Resource> resources;
        Marble resMarble;

        marble = m.chooseColumn(1);
        resMarble = m.getReserveMarble();
        resources = m.getColumn(1);

        for (int i=0; i<3; i++) {
            assertEquals(marble.get(i).toResource(), resources.get(i));
        }

        marble2 = m.chooseColumn(1);
        for (int i=0; i<2;i++){
            assertEquals(marble2.get(i), marble.get(i+1));
        }
        assertEquals(marble2.get(2),resMarble);
        assertEquals(m.getReserveMarble(),marble.get(0));
    }
}