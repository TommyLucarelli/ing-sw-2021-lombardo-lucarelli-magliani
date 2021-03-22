package it.polimi.ingsw.core;

/**
 * This class represent the special ability that consist in a discount related to a DevCard
 * @author martina
 */

public class SpecialDiscount extends SpecialAbility {

    Resource discount;

    /**
     * Class constructor
     * @param discount represent a special discount on the price of a DevCard
     */
    public SpecialDiscount(Resource discount){
        super(1, discount);
    }

    @Override
    public Resource getResource() {
        return super.getResource();
    }

    public Resource getDiscount() {
        return discount;
    }
}
