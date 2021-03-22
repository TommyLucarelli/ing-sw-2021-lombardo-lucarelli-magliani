package it.polimi.ingsw.core;

/**
 * Abstract class representing a card. Extended by DevCard and LeaderCard
 * @author Giacomo Lombardo
 */
public abstract class Card {
    private int id;

    public Card(int id){
        this.id = id;
    }

    /**
     * Getter method.
     * @return the id of the card
     */
    public int getId() {
        return id;
    }

    /**
     * Abstract method to implement: returns a representation of the card
     */
    public abstract void show();
}
