package it.polimi.ingsw.core;

import java.util.ArrayList;

/**
 * Class which represents a Development Card
 * @author Giacomo Lombardo
 */
public class DevCard extends Card{
    private Flag flag;
    private Recipe recipe;
    private ArrayList<ResourceQty> cost;
    private int victoryPoints;

    /**
     * Class constructor
     * @param flag the flag of the development card
     * @param recipe the recipe of the development card
     * @param cost the cost of the card, represented by an ArrayList of ResourceQty
     * @param victoryPoints the amount of victory points assigned by the card
     */
    public DevCard(int id, Flag flag, Recipe recipe, ArrayList<ResourceQty> cost, int victoryPoints) {
        super(id);
        this.flag = flag;
        this.recipe = recipe;
        this.cost = cost;
        this.victoryPoints = victoryPoints;
    }

    /**
     * Getter method.
     * @return the flag of the card
     */
    public Flag getFlag() {
        return flag;
    }

    /**
     * Getter method.
     * @return the recipe of the card
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * Getter method.
     * @return the cost of the card
     */
    public ArrayList<ResourceQty> getCost() {
        return cost;
    }

    /**
     * Getter method.
     * @return the amount of victory points assigned by the card
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    @Override
    public void show(){
        System.out.println(this.toString());
    }
}
