package it.polimi.ingsw.core;

import java.util.ArrayList;

/**
 * Class representing the strongbox inside the board
 * @author Tommaso Lucarelli
 */
public class Strongbox
{
    private ArrayList<ResourceQty> resources;

    /**
     * Constructor method
     */
    public Strongbox(){
        resources = new ArrayList<ResourceQty>();
    }

    /**
     * Method used to add resources inside the strongbox. It does that eiter easily adding the ResourceQty object
     * or updating the quantity if that kind of resource is already inside the strongbox.
     * @param rq type and quantity of the resources we want to add
     */
    public void addResource(ResourceQty rq){
        boolean flag = false;
        for (ResourceQty resource : resources) {
            if (resource.getResource() == rq.getResource()) {
                flag = true;
                resource.increaseQty(rq.getQty());
            }
        }
        if(!flag)
            resources.add(rq);
    }

    /**
     * Method to use resources
     * @param rq type and quantity of the resources we have used
     */
    public void useResource(ResourceQty rq){
        for(int i=0; i< resources.size(); i++)
        {
            if(resources.get(i).getResource() == rq.getResource()){
                resources.get(i).decreaseQty(rq.getQty());
                if(resources.get(i).getQty()==0)
                    resources.remove(i);
                break;
            }
        }
        //TODO: (tommy) exception risorsa non presente
    }

    /**
     * Getter method
     * @param r type of resource we are looking for
     * @return the object ResourceQty looked for
     */
    public int getResourceQtyX(Resource r){
        for(int i=0; i< resources.size(); i++)
        {
            if(resources.get(i).getResource() == r){
                return resources.get(i).getQty();
            }
        }
        return 0;
        //TODO: (tommy) exception risorsa non presente
    }

    /**
     * Getter method
     * @return the whole state of the strongbox, with all the resources
     */
    public ArrayList<ResourceQty> getResources() {
        return (ArrayList<ResourceQty>) resources.clone();
    }

}
