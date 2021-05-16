package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
        for(int i=0; i<10; i++)
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

    /**
     * Method used to decrease Resources inside the warehouse
     * @param a Array with the amount of resource we have to take from the warehouse
     */
    public void decResWarehouse(int[] a){
        int x;
        for (int i=9; i>=0; i--){
            if(structure.get(i) != Resource.ANY && a[structure.get(i).ordinal()]>0) {
                a[structure.get(i).ordinal()]--;
                structure.set(i,Resource.ANY);
            }
        }
    }

    /**
     * Getter method used for network communication
     * @return a brief representation of the warehouse via a Json object.
     */
    public JsonObject toCompactWarehouse(){
        Resource[] arr = new Resource[10];
        arr = structure.toArray(arr);

        Gson gson = new Gson();
        JsonObject payload = new JsonObject();
        String json = gson.toJson(arr);
        payload.addProperty("structure", json);

        return payload;
    }
}
