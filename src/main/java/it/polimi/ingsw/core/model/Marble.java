package it.polimi.ingsw.core.model;
/**
 * This enum class defines all marbles types
 * @author Martina Magliani
 */

public enum Marble {
    /**
     * Yellow marble
     */
    YELLOW,

    /**
     * Grey Marble
     */
    GREY,

    /**
     * Blue marble
     */
    BLUE,

    /**
     * Purple marble
     */
    PURPLE,

    /**
     * Red marble
     */
    RED,

    /**
     * White marble
     */
    WHITE;

    /**
     * Transforms the marble into the related resource.
     * @return the resource related to the marble.
     */
    public Resource toResource(){
        switch (this){
            case GREY:
                return Resource.STONE;
            case BLUE:
                return Resource.SHIELD;
            case RED:
                return Resource.FAITH;
            case YELLOW:
                return Resource.COIN;
            case PURPLE:
                return Resource.SERVANT;
            case WHITE:
                return Resource.ANY;
        }
        return null;
    }
}