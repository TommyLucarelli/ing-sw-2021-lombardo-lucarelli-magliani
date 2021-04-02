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

    public boolean isActivated();

    public void activate();

}
