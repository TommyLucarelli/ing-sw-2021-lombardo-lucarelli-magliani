package it.polimi.ingsw.core.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Resource;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MarketHandlerTest {

    @Test
    public void pick() {
        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Resource> placed = new ArrayList<>();
        resources.add(Resource.STONE);
        resources.add(Resource.STONE);
        resources.add(Resource.COIN);

        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "WAREHOUSE_PLACEMENT");
        Gson gson = new Gson();
        String json = gson.toJson(resources);
        payload.addProperty("resourcesArray", json);

        System.out.println(payload);

        String json2 = payload.get("resourcesArray").getAsString();
        Type collectionType = new TypeToken<ArrayList<Resource>>(){}.getType();
        placed = gson.fromJson(json2, collectionType);

        for (int i = 0; i < placed.size(); i++) {
            System.out.println(placed.get(i));
        }
    }
}