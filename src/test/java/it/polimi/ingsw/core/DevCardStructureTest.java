package it.polimi.ingsw.core;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class DevCardStructureTest {
    @Test
    public void testDrawCard() {
        try {
            DevCardStructure devCardStructure = new DevCardStructure();

            DevCard devCard = devCardStructure.drawCard(0, 0);
            assertEquals(devCard.getFlag().getLevel(), 1);
            assertEquals(devCard.getFlag().getColour(), Colour.GREEN);

            devCard = devCardStructure.drawCard(0, 0);
            assertEquals(devCard.getFlag().getLevel(), 1);
            assertEquals(devCard.getFlag().getColour(), Colour.GREEN);

            devCard = devCardStructure.drawCard(0, 2);
            assertEquals(devCard.getFlag().getLevel(), 1);
            assertEquals(devCard.getFlag().getColour(), Colour.YELLOW);

            devCard = devCardStructure.drawCard(1, 1);
            assertEquals(devCard.getFlag().getLevel(), 2);
            assertEquals(devCard.getFlag().getColour(), Colour.BLUE);

            devCard = devCardStructure.drawCard(2, 3);
            assertEquals(devCard.getFlag().getLevel(), 3);
            assertEquals(devCard.getFlag().getColour(), Colour.PURPLE);

            //FIXME: ogni tanto non funziona e non si capisce perchè...
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}