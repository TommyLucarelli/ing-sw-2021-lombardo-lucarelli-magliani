package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
     * Method to add an arraylist of resource qty
     * @param arq the arraylist
     */
    public void addResource(ArrayList<ResourceQty> arq){
        for(int i=0; i<arq.size(); i++){
            addResource(arq.get(i));
        }
    }
    /**
     * This method use the resources of the strongbox that the client has chosen to buy a devcard
     * @param a(i) quantity of resources required
     */
    public void decreaseResource(int[] a){
        useResource(new ResourceQty(Resource.COIN,a[0]));
        useResource(new ResourceQty(Resource.STONE,a[1]));
        useResource(new ResourceQty(Resource.SHIELD,a[2]));
        useResource(new ResourceQty(Resource.SERVANT,a[3]));
    }

    /**
     * Method to use resources
     * @param rq type and quantity of the resources we have used
     */
    protected void useResource(ResourceQty rq){
        for(int i=0; i< resources.size(); i++)
        {
            if(resources.get(i).getResource() == rq.getResource()){
                resources.get(i).decreaseQty(rq.getQty());
                if(resources.get(i).getQty()==0)
                    resources.remove(i);
                break;
            }
        }
        //TODO: (tommy) exception risorsa non presente, potrebbe non servire perchè il controller controlla prima che ci siano le risorse
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
    }

    /**
     * Getter method
     * @return the whole state of the strongbox, with all the resources
     */
    public ArrayList<ResourceQty> getResources() {
        return (ArrayList<ResourceQty>) resources.clone();
    }

    public JsonObject toCompactStrongBox(){
        int[] arr = new int[4];

        for (int i = 0; i < resources.size(); i++) {
            arr[resources.get(i).getResource().ordinal()] = resources.get(i).getQty();
        }

        Gson gson = new Gson();
        JsonObject payload = new JsonObject();
        String json = gson.toJson(arr);
        payload.addProperty("structure", json);

        return payload;
    }

}
