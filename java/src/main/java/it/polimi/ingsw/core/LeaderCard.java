package it.polimi.ingsw.core;

import java.util.ArrayList;

/**
 * Class which represents a Leader Card
 * @author Giacomo Lombardo
 */
public class LeaderCard extends Card {
    private ArrayList<Flag> requiredFlags;
    private ArrayList<Integer> requiredQty;
    private int victoryPoints;
    private SpecialAbility specialAbility;

    /**
     * Class constructor
     * @param requiredFlags type of flags required to play the leader card
     * @param requiredQty quantity of flags required to play the leader card
     * @param victoryPoints quantity of victory points assigned from the leader card
     * @param specialAbility special ability of the card
     */
    public LeaderCard(int id, ArrayList<Flag> requiredFlags, ArrayList<Integer> requiredQty, int victoryPoints, SpecialAbility specialAbility) {
        super(id);
        this.requiredFlags = requiredFlags;
        this.requiredQty = requiredQty;
        this.victoryPoints = victoryPoints;
        this.specialAbility = specialAbility;
    }

    /**
     * Getter method
     * @return an ArrayList of flags required to play the card
     */
    public ArrayList<Flag> getRequiredFlags() {
        return requiredFlags;
    }

    /**
     * Getter method
     * @return an ArrayList of integers indicating the quantity of each flag required to play the card
     */
    public ArrayList<Integer> getRequiredQty() {
        return requiredQty;
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
