package it.polimi.ingsw.core;

/**
 * Class which represents a flag of a Development/Leader Card
 * @author Martina Magliani
 */
public class Flag {
    int level;
    Colour colour;

    /**
     * Class constructor
     * @param level the level of the card
     * @param colour the colour of the card
     */
    public Flag(int level, Colour colour) {
        this.level = level;
        this.colour = colour;
    }

    /**
     * Getter method.
     * @return the colour of the card
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Getter method.
     * @return the level of the card
     */
    public int getLevel() {
        return level;
    }
}
