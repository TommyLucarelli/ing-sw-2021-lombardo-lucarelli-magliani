package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Colour;
import it.polimi.ingsw.core.model.DevCard;
import it.polimi.ingsw.core.model.DevCardStructure;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;

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

    @Test
    public void testToStringTopStructure() throws FileNotFoundException{
        DevCardStructure devCardStructure = new DevCardStructure();

        System.out.println(devCardStructure.toStringTopStructure());
        //FIXME: non è un test...
    }

    @Test
    public void testToCompact() throws FileNotFoundException {
        DevCardStructure devCardStructure = new DevCardStructure();
        JsonObject payload = new JsonObject();
        payload = devCardStructure.toCompactDevCardStructure();
        Gson gson = new Gson();
        String json = payload.get("structure").getAsString();
        Type collectionType = new TypeToken<int[][]>(){}.getType();
        int[][] structure2 = gson.fromJson(json, collectionType);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(" "+structure2[i][j]+" ");
            }
        }
    }
}