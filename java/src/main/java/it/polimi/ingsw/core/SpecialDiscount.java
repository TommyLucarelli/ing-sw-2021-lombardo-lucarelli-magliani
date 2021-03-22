package it.polimi.ingsw.core;

/**
 * This class represent the special ability that consist in a discount related to a DevCard
 * @author martina
 */

public class SpecialDiscount extends SpecialAbility {

    Resource resource;

    /**
     * Class constructor
     * @param resource represent a special discount on the price of a DevCard
     */
    public SpecialDiscount(Resource resource){
        super(1, resource);
    }

    /**
     * @Override of the superclass Getter method
     * @return the resource related to the special ability
     */
    @Override
    public Resource getResource() {
        return super.getResource();
    }
}
