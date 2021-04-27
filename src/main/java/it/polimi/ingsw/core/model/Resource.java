package it.polimi.ingsw.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * Enum class representing every type of resource.
 * @author Martina Magliani
 */
public enum Resource {
    @SerializedName("1")
    COIN,
    STONE, SERVANT, SHIELD, FAITH, ANY

}
