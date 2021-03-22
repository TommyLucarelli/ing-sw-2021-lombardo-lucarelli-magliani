package it.polimi.ingsw.core;

/**
 * Class representing a special ability of a Leader Card
 * @author Giacomo Lombardo
 */
public class SpecialAbility {
    private int type;
    private Resource resource;

    /**
     * Class constructor
     * @param type the type of the ability (1 - Discount, 2 - Warehouse, 3 - White Marble, 4 - Extra production power)
     * @param resource the resource related to the ability
     */
    public SpecialAbility(int type, Resource resource) {
        this.type = type;
        this.resource = resource;
    }

    /**
     * Getter method
     * @return the type of the special abilty
     */
    public int getType() {
        return type;
    }

    /**
     * Getter method
     * @return the resource related to the special abilty
     */
    public Resource getResource() {
        return resource;
    }

}
