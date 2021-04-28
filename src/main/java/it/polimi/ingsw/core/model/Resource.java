package it.polimi.ingsw.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * Enum class representing every type of resource.
 * @author Martina Magliani
 */
public enum Resource {
    @SerializedName("0")
    COIN,
    @SerializedName("1")
    STONE,
    @SerializedName("2")
    SHIELD,
    @SerializedName("3")
    SERVANT,
    @SerializedName("4")
    FAITH,
    @SerializedName("5")
    ANY
}
