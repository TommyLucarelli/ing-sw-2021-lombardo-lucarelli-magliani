package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Class representing the development card token
 */
public class DevCardToken implements SoloActionToken {

    private Colour c;

    /**
     * Class constructor
     * @param c is the colour of the flag of the development card
     */
    public DevCardToken(Colour c){
        this.c = c;
    }

    @Override
    public JsonObject getAction() {
        JsonObject payload = new JsonObject();
        payload.addProperty("type", "dct");
        Gson gson = new Gson();
        String json = gson.toJson(c);
        payload.addProperty("colour", json);
        return payload;
    }

    public String getMessage(){
        String s = "You revealed a Development Card Token "+c.toString();
        return s;
    }

}
