package it.polimi.ingsw.core.model;

/**
 * Class representing a special ability of a Leader Card
 * @author Giacomo Lombardo
 */
public abstract class SpecialAbility {
    private Resource resource;
    private boolean isActivated;

    public SpecialAbility(Resource resource){
        this.resource = resource;
    }

    /**
     * Getter method
     * @return a description of the special abilty
     */
    public abstract String getDescription();

    /**
     * Getter method
     * @return the resource related to the special abilty
     */
    public Resource getResource(){
        return this.resource;
    }

    /**
     * Getter method.
     * @return true if the card is activated, false otherwise.
     */
    public boolean isActivated(){
        return this.isActivated;
    }

    /**
     * Activates the card.
     */
    public void activate(){
        this.isActivated = true;
    }

}
