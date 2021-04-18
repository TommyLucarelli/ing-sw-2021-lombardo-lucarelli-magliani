package it.polimi.ingsw.core.model;

/**
 * Class that represent the different special ability that a leader card has.
 */
public class SpecialAbility {
    /**
     * abilityType value:
     * 0 - special warehouse
     * 1 - special marble
     * 2 - special discount
     * 3 - special production
     */
    private int abilityType;
    private Resource abilityResource;

    /**
     * Class constructor
     * @param at type of special ability
     * @param r type of resource linked to the special ability
     */
    public SpecialAbility(int at, Resource r)
    {
        abilityType = at;
        abilityResource = r;
    }

    /**
     * Getter method
     * @return type of special ability
     */
    public int getAbilityType() {
        return abilityType;
    }

    /**
     * Getter method
     * @return type of resource
     */
    public Resource getAbilityResource() {
        return abilityResource;
    }
}
