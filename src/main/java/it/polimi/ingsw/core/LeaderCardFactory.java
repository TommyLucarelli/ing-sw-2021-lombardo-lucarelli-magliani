package it.polimi.ingsw.core;

import java.util.ArrayList;

public class LeaderCardFactory {
    private int id;
    private ArrayList<Flag> requiredFlags;
    private ArrayList<ResourceQty> requiredResources;
    private int victoryPoints;
    private int specialAbilityType;
    private Resource specialAbilityResource;


    public LeaderCardFactory(int id, ArrayList<Flag> requiredFlags, ArrayList<ResourceQty> requiredResources, int victoryPoints, int specialAbilityType, Resource specialAbilityResource) {
        this.id = id;
        this.requiredFlags = requiredFlags;
        this.requiredResources = requiredResources;
        this.victoryPoints = victoryPoints;
        this.specialAbilityType = specialAbilityType;
        this.specialAbilityResource = specialAbilityResource;
    }

    /**
     * Factory method.
     * @return A new LeaderCard with the specified attributes.
     */
    public LeaderCard createLeaderCard(){
        return new LeaderCard(this.id, this.requiredFlags, this.requiredResources, this.victoryPoints, this.specialAbilityType, this.specialAbilityResource);
    }

    /**
     * Factory method.
     * @param id the id of the card
     * @param requiredFlags an array of required flags
     * @param requiredResources an array of required ResourceQty
     * @param victoryPoints the victory points of the card
     * @param specialAbilityType the type of the special ability
     * @param specialAbilityResource the resource of the special ability
     * @return A new LeaderCard with the specified attributes.
     */
    public LeaderCard createLeaderCard(int id, ArrayList<Flag> requiredFlags, ArrayList<ResourceQty> requiredResources, int victoryPoints, int specialAbilityType, Resource specialAbilityResource){
        return new LeaderCard(id, requiredFlags, requiredResources, victoryPoints, specialAbilityType, specialAbilityResource);
    }
}
