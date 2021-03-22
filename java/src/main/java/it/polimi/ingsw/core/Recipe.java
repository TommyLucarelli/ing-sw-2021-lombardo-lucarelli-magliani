package it.polimi.ingsw.core;

import java.util.ArrayList;

public class Recipe {

    ArrayList startResources = new ArrayList<ResourceQty>();
    ArrayList endResources = new ArrayList<ResourceQty>();

    public Recipe(ArrayList startResources, ArrayList endResources){
        this.endResources = endResources;
        this.startResources = startResources;
    }

    public ArrayList getEndResources() {
        return endResources;
    }

    public ArrayList getStartResources() {
        return startResources;
    }

    public void addStartResource(ResourceQty resourceQty){
        startResources.add(resourceQty);
    }

    public void addEndResource(ResourceQty resourceQty){
        endResources.add(resourceQty);
    }
}
