package it.polimi.ingsw.core;
/**
 * This enum class defines all marbles types
 * @author Martina Magliani
 */

public enum Marble {

    RED,BLUE,YELLOW,WHITE,GREY,PURPLE;

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