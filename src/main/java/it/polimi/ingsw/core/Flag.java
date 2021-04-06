package it.polimi.ingsw.core;

/**
 * Class which represents a flag of a Development/Leader Card
 * @author Martina Magliani
 */
public class Flag {
    private final int level;
    private final Colour colour;

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
     * Class constructor. Initializes a flag without level (this.level == 0).
     * Used to instantiate the required flags to play a LeaderCard.
     * @param colour the colour of the card
     */
    public Flag(Colour colour){
        this.level = 0;
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

    @Override
    public String toString() {
        return "Flag{" +
                "level=" + level +
                ", colour=" + colour +
                '}';
    }

    /**
     * Method to compare the object with another class.
     * @param flag the flag which the object will be compared to.
     * @return true if the flags have the same colour and the object has the level >= than the other flag's level. False otherwise.
     */
    public boolean equalOrStrongerThan(Flag flag){
        return flag.getColour() == this.getColour() && flag.getLevel() <= this.getLevel();
    }
}
