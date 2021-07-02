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
    JsonObject getAction();

    /**
     * Getter method.
     * @return the message related to the action token.
     */
    String getMessage();
}
