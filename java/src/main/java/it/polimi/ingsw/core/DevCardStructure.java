package it.polimi.ingsw.core;

import com.google.gson.Gson;

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

    /**
     * Class constructor. Initializes the structure and then generates the development cards.
     */
    protected DevCardStructure(){
        structure = new ArrayList[3][4];
        Gson g = new Gson();

        //DOES THIS MAKE SENSE???? IS IT THE BEST WAY TO DO IT????
        startRes = new ArrayList<ResourceQty>();
        endRes = new ArrayList<ResourceQty>();
        cost = new ArrayList<ResourceQty>();
        startRes.add(new ResourceQty(Resource.COIN, 1));
        endRes.add(new ResourceQty(Resource.FAITH, 1));
        rec = new Recipe(startRes, endRes);
        cost.add(new ResourceQty(Resource.SHIELD, 2));
        structure[0][0].add(new DevCard(1, new Flag(1, Colour.GREEN), rec, cost, 1));

        startRes = new ArrayList<ResourceQty>();
        endRes = new ArrayList<ResourceQty>();
        cost = new ArrayList<ResourceQty>();
        startRes.add(new ResourceQty(Resource.STONE, 1));
        endRes.add(new ResourceQty(Resource.FAITH, 1));
        rec = new Recipe(startRes, endRes);
        cost.add(new ResourceQty(Resource.SERVANT, 2));
        structure[0][1].add(new DevCard(2, new Flag(1, Colour.PURPLE), rec, cost, 1));

        startRes = new ArrayList<ResourceQty>();
        endRes = new ArrayList<ResourceQty>();
        cost = new ArrayList<ResourceQty>();
        startRes.add(new ResourceQty(Resource.SHIELD, 1));
        endRes.add(new ResourceQty(Resource.FAITH, 1));
        rec = new Recipe(startRes, endRes);
        cost.add(new ResourceQty(Resource.COIN, 2));
        structure[0][2].add(new DevCard(1, new Flag(1, Colour.BLUE), rec, cost, 1));

        startRes = new ArrayList<ResourceQty>();
        endRes = new ArrayList<ResourceQty>();
        cost = new ArrayList<ResourceQty>();
        startRes.add(new ResourceQty(Resource.STONE, 1));
        endRes.add(new ResourceQty(Resource.SERVANT, 1));
        rec = new Recipe(startRes, endRes);
        cost.add(new ResourceQty(Resource.SHIELD, 1));
        cost.add(new ResourceQty(Resource.SERVANT, 1));
        cost.add(new ResourceQty(Resource.STONE, 1));
        structure[0][0].add(new DevCard(5, new Flag(1, Colour.GREEN), rec, cost, 2));
    }


}
