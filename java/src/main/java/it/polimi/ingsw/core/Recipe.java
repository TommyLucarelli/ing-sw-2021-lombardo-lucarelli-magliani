package it.polimi.ingsw.core;
import java.util.ArrayList;

/**
 * Class represent input and output resource of every DevCard
 * @author martina
 */

public class Recipe {

    ArrayList inputResources = new ArrayList<ResourceQty>();
    ArrayList outputResources = new ArrayList<ResourceQty>();

    /**
     * Class constructor
     * @param inputResources are input resource of a DevCard
     * @param outputResources are output resource of a DevCard
     */
    public Recipe(ArrayList inputResources, ArrayList outputResources){
        this.outputResources = outputResources;
        this.inputResources = inputResources;
    }

    public ArrayList getOutputResources() {
        return outputResources;
    }

    public ArrayList getInputResources() {
        return inputResources;
    }

    public void addInputResource(ResourceQty resourceQty){
        inputResources.add(resourceQty);
    }

    public void addOutputResource(ResourceQty resourceQty){
        outputResources.add(resourceQty);
    }
}
