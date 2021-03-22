package it.polimi.ingsw.core;

import java.util.ArrayList;

/**
 * Class representing the strongbox inside the board
 * @author Tommaso Lucarelli
 */
public class Strongbox
{
    private ArrayList<ResourceQty> resources;

    public Strongbox(){
        resources = new ArrayList<ResourceQty>();
    }

    public void addResource(ResourceQty rq){
        //potrebbe starci un controllo per capire se esistono già risorse di quel tipo e quindi unirle
        resources.add(rq);
    }

    public void useResource(Resource r, int qty){
        //usa e dunque rimuove una quantità "qty" dalla
    }

    public ResourceQty getResourceQtyX(Resource r){
        //scorre l'array per cercare se c'è la risorsa "r" nel strongbox
    }

    public ArrayList<ResourceQty> getResources() {
        return resources;
    }

}
