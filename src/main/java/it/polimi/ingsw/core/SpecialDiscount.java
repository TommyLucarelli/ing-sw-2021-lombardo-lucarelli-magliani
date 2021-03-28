package it.polimi.ingsw.core;

/**
 * This class represents the special ability that consists in a discount related to a DevCard
 * @author Martina Magliani
 */

public class SpecialDiscount extends SpecialAbility {
    private Resource resource;

    /**
     * Class constructor
     * @param resource represent a special discount on the price of a DevCard
     */
    public SpecialDiscount(Resource resource){
        super(1, resource);
    }

    @Override
    public Resource getResource() {
        return super.getResource();
    }
}
