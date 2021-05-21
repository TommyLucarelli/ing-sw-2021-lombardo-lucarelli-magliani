package it.polimi.ingsw.core.model;

import com.google.gson.JsonObject;

/**
 * Interface for the solo action token
 */
public interface SoloActionToken {
    /**
     * method to get the action from a generic solo action token
     * @return a string with the description of the selected token
     */
    public JsonObject getAction();

    public String getMessage();
}
