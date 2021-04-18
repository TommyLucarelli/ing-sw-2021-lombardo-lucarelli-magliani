package it.polimi.ingsw.core.model;

import java.util.ArrayList;

/**
 * Class representing the warehouse inside the board
 * @author Tommaso Lucarelli
 */
public class Warehouse
{
    /**
     * structure layout:
     * first six spot represent the basic warehouse split in three section,
     * the other four spot represent the extended spot after special ability activation.
     * A empty space is represent with the resource "ANY"
     */
    private ArrayList<Resource> structure;

    /**
     * Class constructor
     */
    public Warehouse(){
        structure = new ArrayList<Resource>();
        structure.add(Resource.ANY);
        structure.add(Resource.ANY);
        structure.add(Resource.ANY);
        structure.add(Resource.ANY);
        structure.add(Resource.ANY);
        structure.add(Resource.ANY);
        structure.add(Resource.ANY);
        structure.add(Resource.ANY);
        structure.add(Resource.ANY);
        structure.add(Resource.ANY);
    }

    /**
     * Method used to update the configuration after user action
     * @param aRes array with the new configuration
     */
    public void updateConfiguration(ArrayList<Resource> aRes){
        structure = (ArrayList<Resource>) aRes.clone();
    }

    /**
     * Getter method
     * @return the configuration of the warehouse
     */
    public ArrayList<Resource> getStructure() {
        return (ArrayList<Resource>) structure.clone();
    }
}
