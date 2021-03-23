package it.polimi.ingsw.core;

import java.util.ArrayList;

/**
 * Class representing the strongbox inside the board
 * @author Tommaso Lucarelli
 */
public class Strongbox
{
    private ArrayList<ResourceQty> resources;

    /**
     * Constructor method
     */
    public Strongbox(){
        resources = new ArrayList<ResourceQty>();
    }

    /**
     *
     * @param rq
     */
    public void addResource(ResourceQty rq){
        //potrebbe starci un controllo per capire se esistono già risorse di quel tipo e quindi unirle
        resources.add(rq);
    }

    /**
     *
     * @param r
     * @param qty
     */
    public void useResource(Resource r, int qty){
        //usa e dunque rimuove una quantità "qty" dalla
    }

    /**
     *
     * @param r
     * @return
     */
    public ResourceQty getResourceQtyX(Resource r){
        //scorre l'array per cercare se c'è la risorsa "r" nel strongbox
    }

    /**
     *
     * @return
     */
    public ArrayList<ResourceQty> getResources() {
        return resources;
    }

}
