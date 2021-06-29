package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.BlackFlagToken;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlackFlagTokenTest {
    @Test
    public void testGetSpaces() {
        BlackFlagToken bft = new BlackFlagToken(1,true);

        assertEquals(bft.getSpaces(), 1);
    }

    @Test
    public void testShufflesDeck() {
        BlackFlagToken bft = new BlackFlagToken(1,true);

        assertTrue(bft.shufflesDeck());
    }

    @Test
    public void getAction() {
        BlackFlagToken bft = new BlackFlagToken(1,true);

        JsonObject payload = bft.getAction();

        assertEquals(1, payload.get("type").getAsInt());
        assertEquals(true, payload.get("shuffle").getAsBoolean());

    }

    @Test
    public void testGetMessage() {
        BlackFlagToken bft = new BlackFlagToken(1,true);
        BlackFlagToken bft1 = new BlackFlagToken(1,false);
        String s1 = "You revealed a Black Flag Token with 1 faithpoint and shuffle";
        String s2 = "You revealed a Black Flag Token with 2 faithpoints";
        assertEquals(s1,bft.getMessage());
        assertEquals(s2, bft1.getMessage());
    }
}