package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.Marble;
import it.polimi.ingsw.core.model.Market;
import it.polimi.ingsw.core.model.Resource;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarketTest {

    @Test
    public void testUpdateLine() {
        Market m = new Market();
        Marble[] marble, marble2;
        Resource[] resources;
        Marble resMarble;

        marble = m.getLine(1);
        resMarble = m.getReserveMarble();
        resources = m.updateLineAndGetResources(1);

        for (int i=0; i<4; i++) {
            assertEquals(marble[i].toResource(), resources[i]);
        }

        marble2 = m.getLine(1);
        for (int i=0; i<3;i++){
            assertEquals(marble2[i], marble[i + 1]);
        }
        assertEquals(marble2[3], resMarble);
        assertEquals(m.getReserveMarble(), marble[0]);
    }

    @Test
    public void testGetColumn() {
        Market m = new Market();
        Marble[] marble, marble2;
        Resource[] resources;
        Marble resMarble;

        marble = m.getColumn(1);
        resMarble = m.getReserveMarble();
        resources = m.updateColumnAndGetResources(1);

        for (int i=0; i<3; i++) {
            assertEquals(marble[i].toResource(), resources[i]);
        }

        marble2 = m.getColumn(1);
        for (int i=0; i<2;i++){
            assertEquals(marble2[i], marble[i + 1]);
        }
        assertEquals(marble2[2], resMarble);
        assertEquals(m.getReserveMarble(), marble[0]);
    }

    @Test
    public void testGetStructureAsArray(){
        Market market = new Market();
        Marble[] structure = market.getStructureAsArray();
        for(int i = 0; i < 3; i++)
            for(int ii = 0; ii < 4; ii++)
                assertEquals(market.getMarble(i, ii), structure[(i * 4) + ii]);
    }
}