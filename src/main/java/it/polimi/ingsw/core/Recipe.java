package it.polimi.ingsw.core;
import java.util.ArrayList;

/**
 * Class which represents input and output resources of a production power of a Development card
 * @author Martina Magliani
 */

public class Recipe {

    private final ArrayList<ResourceQty> inputResources;
    private final ArrayList<ResourceQty> outputResources;

    /**
     * Class constructor
     * @param inputResources input resources of the recipe
     * @param outputResources output resources of the recipe
     */
    public Recipe(ArrayList<ResourceQty> inputResources, ArrayList<ResourceQty> outputResources){
        this.outputResources = outputResources;
        this.inputResources = inputResources;
    }

    /**
     * Getter method
     * @return output resources of the recipe
     */
    public ArrayList<ResourceQty> getOutputResources() {
        return (ArrayList<ResourceQty>) outputResources.clone();
    }

    /**
     * Getter method
     * @return input resources of the recipe
     */
    public ArrayList<ResourceQty> getInputResources() {
        return (ArrayList<ResourceQty>) inputResources.clone();
    }

}
