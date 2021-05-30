package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;

/**
 * Interface used to create dynamic controllers which can be updated after initialization.
 */
public interface DynamicController {
    /**
     * Passes an object to the controller containing the information to visualize.
     * @param data a JSON Object containing the information.
     */
    void setData(JsonObject data);
}
