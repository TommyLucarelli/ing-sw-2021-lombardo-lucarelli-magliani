package it.polimi.ingsw.core.model;

import java.util.ArrayList;

/**
 * Class which represents a Leader Card
 * @author Giacomo Lombardo
 */
public class LeaderCard extends Card {
    private ArrayList<Flag> requiredFlags;
    private ResourceQty requiredResources;
    private int victoryPoints;
    private SpecialAbility specialAbility;
    private Boolean abilityActivation;
    /**
     * Class constructor
     * @param requiredFlags type of flags required to play the leader card
     * @param requiredResources quantity of flags required to play the leader card
     * @param victoryPoints quantity of victory points assigned from the leader card
     * @param specialAbility type of special ability and resource
     */
    public LeaderCard(int id, ArrayList<Flag> requiredFlags, ResourceQty requiredResources, int victoryPoints,
                      SpecialAbility specialAbility) {
        super(id);
        this.requiredFlags = requiredFlags;
        this.requiredResources = requiredResources;
        this.victoryPoints = victoryPoints;
        this.specialAbility = specialAbility;
        abilityActivation = false;
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
    public ResourceQty getRequiredResources() {
        return requiredResources;
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

    /**
     * Setter method
     * The flag representing the activation of this card is set true
     */
    public void setAbilityActivation() {
        this.abilityActivation = true;
    }

    public Boolean getAbilityActivation() {
        return abilityActivation;
    }
}
