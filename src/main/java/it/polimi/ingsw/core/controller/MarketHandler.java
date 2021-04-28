package it.polimi.ingsw.core.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Market;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * This Class will handle the phase of acquiring resource from the market
 * @author Tommaso Lucarelli
 */
public class MarketHandler {

    private Market market;
    private MainController controller;
    private ArrayList<Resource> resources;
    private ArrayList<Resource> blackList;
    private boolean configWorks;
    private Resource r;
    private ArrayList<Resource> placed;
    int faithP1 = 0, faithP2 = 0;

    /**
     * Class constructor
     * @param controller that called this class
     */
    public MarketHandler(MainController controller){
        this.controller = controller;
        blackList = new ArrayList<>();
    }

    public RequestMsg pick(ResponseMsg ms){
        String choice = ms.getPayload().get("choice").getAsString();
        int number = ms.getPayload().get("line").getAsInt();
        //risposta di pick con scelta colonna o riga del mercato e il valore x
        if(choice.equals("column"))
            resources = market.updateColumnAndGetResources(number);
        else
            resources = market.updateLineAndGetResources(number);

        //nel messaggio c'è anche la risorsa speciale con cui vuole cambiare la bianca "r"
        if(controller.getCurrentPlayer().getBoard().isActivated(2) != 0 || controller.getCurrentPlayer().getBoard().isActivated(3) != 0){
                for (Resource resource : resources) {
                    if (resource == Resource.ANY)
                        resource = r;
                }
            }
        resources = resources.stream().filter(resource -> resource != Resource.ANY).collect(Collectors.toCollection(ArrayList::new));

        for(int i=0; i < resources.size(); i++){
            if(resources.get(i) == Resource.FAITH){
                resources.remove(i);
                i--;
                faithP1++;
            }
        }


        //preparazione messaggio placement con array risorse
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "WAREHOUSE_PLACEMENT");
        Gson gson = new Gson();
        String json = gson.toJson(resources);
        payload.addProperty("resources array", json);
        return new RequestMsg(MessageType.GAME_MESSAGE, payload);
    }

    public RequestMsg warehousePlacement(ResponseMsg ms){
        //arrivo ARRAY di RESOURCES con il piazzamento int numero risorse scartate
        Gson gson = new Gson();
        String json = ms.getPayload().get("placed").getAsString();
        Type collectionType = new TypeToken<ArrayList<Resource>>(){}.getType();
        placed = gson.fromJson(json, collectionType);
        
        if(checkPlacement(placed)){
            //aggiornamento struttura warehouse
            controller.getCurrentPlayer().getBoard().getWarehouse().updateConfiguration(placed);

            //aggiornamento punti fede
            controller.getCurrentGame().FaithTrackUpdate(controller.getCurrentPlayer(), faithP1, faithP2);
            //prep messaggio ShortUpdate / leader activation

        }else{
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "WAREHOUSE_PLACEMENT");
            Gson output = new Gson();
            payload.add("ResourcesArray", output.toJsonTree(placed));
            return new RequestMsg(MessageType.GAME_MESSAGE, payload);
        }

        return null;
    }

    /**
     * Method to check if the placement proposed by the user is right
     * @param placed proposed placement
     * @return true if the placement is ok
     */
    protected boolean checkPlacement(ArrayList<Resource> placed)
    {
        //check normal warehouse
        if(placed.get(0) != Resource.ANY)
            blackList.add(placed.get(0));
        if(placed.get(1) != Resource.ANY){
            blackList.add(placed.get(1));
            if(placed.get(2) != Resource.ANY && placed.get(2) != placed.get(1))
                return false;
        }
        if(placed.get(3) != Resource.ANY){
            blackList.add(placed.get(3));
            if(placed.get(4) != Resource.ANY && placed.get(4) != placed.get(3))
                return false;
            else if(placed.get(4) != Resource.ANY && placed.get(4) == placed.get(3)){
                if(placed.get(5) != Resource.ANY && placed.get(5) != placed.get(4))
                    return false;
            }
        }
        for(int i=0; i< blackList.size(); i++)
        {
            for(int j=0; j< blackList.size(); j++)
            {
                if(i != j && blackList.get(i) == blackList.get(j))
                    return false;
            }
        }
        //check extended warehouse
        if(controller.getCurrentPlayer().getBoard().isActivated(0) != 0){
            r = controller.getCurrentPlayer().getBoard().getLeader(controller.getCurrentPlayer().getBoard().isActivated(0)).getSpecialAbility().getAbilityResource();
            if((r != placed.get(6) && placed.get(6) != Resource.ANY) || (r != placed.get(7) && placed.get(7) != Resource.ANY))
                return false;
        }
        if(controller.getCurrentPlayer().getBoard().isActivated(1) != 0){
            r = controller.getCurrentPlayer().getBoard().getLeader(controller.getCurrentPlayer().getBoard().isActivated(1)).getSpecialAbility().getAbilityResource();
            if((r != placed.get(8) && placed.get(8) != Resource.ANY) || (r != placed.get(9) && placed.get(9) != Resource.ANY))
                return false;
        }
        return true;
    }

}


