package it.polimi.ingsw.core;

import java.util.ArrayList;

/**
 * Class representing the warehouse inside the board
 *
 * @author Tommaso Lucarelli
 */
public class Warehouse
{
    private ArrayList<Resource> structure; //gli spazi vuoti sono del tipo any del resource

    /**
     * Class constructor
     */
    public Warehouse(){
        structure = new ArrayList<Resource>();
    }

    /**
     * Method used to update the configuration after user action
     * @param aRes array with the new configuration
     */
    public void updateConfiguration(ArrayList<Resource> aRes){
        //forse da aggiungere il controllo che la disposizione sia giusta
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
