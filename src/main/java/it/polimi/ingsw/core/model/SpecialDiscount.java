package it.polimi.ingsw.core.model;

/**
 * This class represents the special ability that consists in a discount related to a DevCard
 * @author Martina Magliani
 */

public class SpecialDiscount extends SpecialAbility {
    /**
     * Class constructor
     * @param resource represent a special discount on the price of a DevCard
     */
    public SpecialDiscount(Resource resource){
        super(resource);
    }

    public String getDescription() {
        return "Quando compri una carta sviluppo, puoi pagarne il costo con uno sconto della risorsa indicata" +
                " (se la carta che stai comprando ha quella risorsa come costo).";
    }

    @Override
    public void activate() {
        super.activate();
    }
}
