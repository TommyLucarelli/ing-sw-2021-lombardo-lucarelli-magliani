package it.polimi.ingsw.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * Enum class representing every type of resource.
 * @author Martina Magliani
 */
public enum Resource {
    /**
     * Coin resource
     */
    COIN, //0 yellow

    /**
     * Stone resource
     */
    STONE, //1 gray

    /**
     * Shield resource
     */
    SHIELD, //2 blue

    /**
     * Servant resource
     */
    SERVANT, //3 purple

    /**
     * Faith resource
     */
    FAITH, //4 red

    /**
     * Any resource
     */
    ANY; //5 white
}
