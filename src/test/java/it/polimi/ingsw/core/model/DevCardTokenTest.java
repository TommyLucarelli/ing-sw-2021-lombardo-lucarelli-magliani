package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.Colour;
import it.polimi.ingsw.core.model.DevCardToken;
import org.junit.Test;

import static org.junit.Assert.*;

public class DevCardTokenTest {

    @Test
    public void getAction() {
        DevCardToken dt = new DevCardToken(Colour.GREEN);


        JsonObject payload = dt.getAction();

        assertEquals(0, payload.get("type").getAsInt());
        String json = payload.get("colour").getAsString();
        Gson gson = new Gson();
        assertEquals(Colour.GREEN, gson.fromJson(json, Colour.class));
    }
}