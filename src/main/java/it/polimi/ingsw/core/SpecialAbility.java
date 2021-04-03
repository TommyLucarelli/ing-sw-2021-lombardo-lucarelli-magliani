package it.polimi.ingsw.core;

/**
 * Class representing a special ability of a Leader Card
 * @author Giacomo Lombardo
 */
public interface SpecialAbility {
    /**
     * Getter method
     * @return a description of the special abilty
     */
    public String getDescription();

    /**
     * Getter method
     * @return the resource related to the special abilty
     */
    public Resource getResource();

    /**
     * Getter method.
     * @return true if the card is activated, false otherwise.
     */
    public boolean isActivated();

    /**
     * Activates the card.
     */
    public void activate();

}
