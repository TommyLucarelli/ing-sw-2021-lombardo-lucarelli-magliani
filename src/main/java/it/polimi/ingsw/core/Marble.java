package it.polimi.ingsw.core;
/**
 * This enum class defines all marbles types
 * @author Martina Magliani
 */

public enum Marble {

    RED,BLUE,YELLOW,WHITE,GREY,PURPLE,OTHER;

    /**
     * Transforms the marble into the related resource.
     * @return the resource related to the marble.
     */
    protected Resource toResource(){
        switch (this) {
            case RED:
                return Resource.FAITH;
            case BLUE:
                return Resource.SHIELD;
            case GREY:
                return Resource.STONE;
            case YELLOW:
                return Resource.COIN;
            case WHITE:
                return Resource.ANY;
            case PURPLE:
                return Resource.SERVANT;
        }
        return null;
    }

}