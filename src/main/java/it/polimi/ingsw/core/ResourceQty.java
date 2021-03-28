package it.polimi.ingsw.core;
/**
 * This class represents a resource with its quantity.
 * @author Martina Magliani
 */

public class ResourceQty {
    private Resource resource;
    private int qty;

    /**
     * Class constructor.
     * @param resource the resource type.
     * @param qty the quantity of the resource.
     */
    public ResourceQty(Resource resource, int qty){
        this.qty = qty;
        this.resource = resource;
    }

    /**
     * Class constructor with quantity = 1.
     * @param resource the resource type.
     */
    public ResourceQty(Resource resource){
        this.qty = 1;
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

    /**
     * Increases the quantity by 1 unit.
     */
    public void increaseQty(){
        this.qty++;
    }

    /**
     * Increases the quantity by a defined amount.
     * @param qty the quantity added.
     */
    public void increaseQty(int qty){
        this.qty += qty;
    }

    /**
     * Decreases the quantity by 1 unit.
     */
    public void decreaseQty(){
        this.qty--;
    }

    /**
     * Decreases the quantity by a defined amount.
     * @param qty the quantity removed.
     */
    public void decreaseQty(int qty){
        this.qty -= qty;
    }
}
