package it.polimi.ingsw.core;
/**
 * This class represents a resource with its quantity
 * @author Martina Magliani
 */

public class ResourceQty {

    Resource resource;
    int qty;

    /**
     * Class constructor
     * @param resource the resource type
     * @param qty the quantity of the resource
     */
    public ResourceQty(Resource resource, int qty){
        this.qty = qty;
        this.resource = resource;
    }

    /**
     * Getter method
     * @return the quantity of the resource
     */
    public int getQty() {
        return qty;
    }

    /**
     * Getter method
     * @return the type of resource
     */
    public Resource getResource() {
        return resource;
    }
}
