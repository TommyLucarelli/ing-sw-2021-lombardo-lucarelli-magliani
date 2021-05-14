package it.polimi.ingsw.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * Enum class representing every type of resource.
 * @author Martina Magliani
 */
public enum Resource {
    COIN, //0 yellow
    STONE, //1 gray
    SHIELD, //2 blue
    SERVANT, //3 purple
    FAITH, //4 red
    ANY; //5 white
}
