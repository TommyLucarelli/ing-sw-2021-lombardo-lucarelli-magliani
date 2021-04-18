package it.polimi.ingsw.core.model;

import java.util.ArrayList;

/**
 * Class which represents a Leader Card
 * @author Giacomo Lombardo
 */
public class LeaderCard extends Card {
    private ArrayList<Flag> requiredFlags;
    private ArrayList<ResourceQty> requiredResources;
    private int victoryPoints;
    private SpecialAbility specialAbility;

    /**TODO: (jack) special ability diventa un intero da 1 a 4 o un'enumerazione di special ability + uno spazio per
            tenere in memoria la risorsa della special ability (la classe SA potrebbe essere un simil ResourceQty)

    /**
     * Class constructor
     * @param requiredFlags type of flags required to play the leader card
     * @param requiredResources quantity of flags required to play the leader card
     * @param victoryPoints quantity of victory points assigned from the leader card
     * @param specialAbilityType the type of the special ability (1 - discount, 2 - extra resource from white marble,
     *                           3 - extra production power, 4 - extra warehouse)
     * @param specialAbilityResource the type of the resource of the special ability.
     */
    public LeaderCard(int id, ArrayList<Flag> requiredFlags, ArrayList<ResourceQty> requiredResources, int victoryPoints,
                      int specialAbilityType, Resource specialAbilityResource) {
        super(id);
        this.requiredFlags = requiredFlags;
        this.requiredResources = requiredResources;
        this.victoryPoints = victoryPoints;
        switch (specialAbilityType){
            case 1:
                this.specialAbility = new SpecialDiscount(specialAbilityResource);
                break;
            case 2:
                this.specialAbility = new SpecialExtraResource(specialAbilityResource);
                break;
            case 3:
                this.specialAbility = new SpecialProductionPower(specialAbilityResource);
                break;
            case 4:
                this.specialAbility = new SpecialWarehouse(specialAbilityResource);
                break;
        }
    }

    /**
     * Getter method
     * @return an ArrayList of flags required to play the card
     */
    public ArrayList<Flag> getRequiredFlags() {
        return (ArrayList<Flag>) requiredFlags.clone();
    }

    /**
     * Getter method
     * @return an ArrayList of integers indicating the quantity of each flag required to play the card
     */
    public ArrayList<ResourceQty> getRequiredResources() {
        return (ArrayList<ResourceQty>) requiredResources.clone();
    }

    /**
     * Getter method
     * @return the amount of victory points assigned by the card
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Getter method
     * @return the special ability of the card
     */
    public SpecialAbility getSpecialAbility() {
        return specialAbility;
    }

    @Override
    public void show() {
        System.out.println(this.toString());
    }
}
