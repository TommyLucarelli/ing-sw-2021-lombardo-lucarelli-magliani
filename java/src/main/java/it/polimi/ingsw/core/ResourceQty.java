package it.polimi.ingsw.core;

public class ResourceQty {

    Resource resource;
    int qty;

    public ResourceQty(Resource resource, int qty){
        this.qty = qty;
        this.resource = resource;
    }

    public int getQty() {
        return qty;
    }

    public Resource getResource() {
        return resource;
    }
}
