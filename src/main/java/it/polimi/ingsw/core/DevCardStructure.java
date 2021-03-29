package it.polimi.ingsw.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Class representing the structure of Development Cards generated at the beginning of the game
 * @author Giacomo Lombardo
 */
public class DevCardStructure {
    // memo: green-blue-yellow-purple 3 rows, 4 columns
    private ArrayList<DevCard>[][] structure;
    private Recipe rec;
    private ArrayList<ResourceQty> startRes;
    private ArrayList<ResourceQty> endRes;
    private ArrayList<ResourceQty> cost;
    private static final Type CARD_TYPE = new TypeToken<ArrayList<DevCard>>() {}.getType();
    private static final String filename = "src/main/resources/devcards.json";

    /**
     * Class constructor. Initializes the structure and then generates the development cards.
     */
    protected DevCardStructure() throws FileNotFoundException{
        Gson g = new Gson();
        JsonReader jreader = new JsonReader(new FileReader(filename));
        ArrayList<DevCard> cards = g.fromJson(jreader, CARD_TYPE);

        //TODO: (jack) implementare la creazione della struttura
    }


}
