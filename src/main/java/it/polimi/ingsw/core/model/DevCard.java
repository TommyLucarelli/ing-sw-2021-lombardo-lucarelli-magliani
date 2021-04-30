package it.polimi.ingsw.core.model;

import java.util.ArrayList;

/**
 * Class which represents a Development Card
 * @author Giacomo Lombardo
 */
public class DevCard extends Card {
    private final Flag flag;
    private final Recipe recipe;
    private final ArrayList<ResourceQty> cost;
    private final int victoryPoints;
    private int[] resDevCard = new int[4];

    /**
     * Class constructor
     *
     * @param flag          the flag of the development card
     * @param recipe        the recipe of the development card
     * @param cost          the cost of the card, represented by an ArrayList of ResourceQty
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
     *
     * @return the flag of the card
     */
    public Flag getFlag() {
        return flag;
    }

    /**
     * Getter method.
     *
     * @return the recipe of the card
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * Getter method.
     *
     * @return the cost of the card
     */
    public ArrayList<ResourceQty> getCost() {
        return (ArrayList<ResourceQty>) cost.clone();
    }

    /**
     * Getter method.
     *
     * @return the amount of victory points assigned by the card
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    @Override
    public String toString() {
        return "DevCard{" +
                "id=" + this.getId() +
                ", flag=" + flag +
                ", recipe=" + recipe +
                ", cost=" + cost +
                ", victoryPoints=" + victoryPoints +
                '}';
    }


    @Override
    public void show() {
        System.out.println(this.toString());
    }

    /**
     * @return the resources cost of the card with the following layout: COIN|STONE|SHIELD|SERVANT
     */
    public int[] resQtyToArray() {
        for (int i = 0; i < cost.size(); i++) {
            resDevCard[cost.get(i).getResource().ordinal()] += cost.get(i).getQty();
        }
        return resDevCard;
    }
}