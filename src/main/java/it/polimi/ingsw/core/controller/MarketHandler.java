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
import java.util.stream.Collectors;

/**
 * Class that handles the phase of acquiring resource from the market
 * @author Tommaso Lucarelli
 */
public class MarketHandler {

    private MainController controller;
    private ArrayList<Resource> resources;
    private boolean configWorks;
    private ArrayList<Resource> placed;
    int faithP1 = 0, faithP2 = 0;

    /**
     * Class constructor
     * @param controller that called this class
     */
    public MarketHandler(MainController controller){
        this.controller = controller;
    }

    /**
     * Method that manages the taking of resources from the market.
     * The resources are taken, processed and sent to the player to be placed in the warehouse.
     * @param ms client message
     */
    public void pick(ResponseMsg ms){
        String choice = ms.getPayload().get("choice").getAsString();
        int number = ms.getPayload().get("number").getAsInt();
        int flag = 0;
        Resource r = null;

        if(choice.equals("c"))
            resources = controller.getCurrentGame().getMarket().updateColumnAndGetResources(number);
        else
            resources = controller.getCurrentGame().getMarket().updateLineAndGetResources(number);

        if(controller.getCurrentPlayer().getBoard().isActivated(2) != 0 && controller.getCurrentPlayer().getBoard().isActivated(3) != 0){
            flag = 1;
        } else if(controller.getCurrentPlayer().getBoard().isActivated(2) != 0){
            flag = 2;
            r = controller.getCurrentPlayer().getBoard().getLeader(controller.getCurrentPlayer().getBoard().getAbilityActivationFlag()[2]).getSpecialAbility().getAbilityResource();
        }
        Gson gson = new Gson();
        String json;
        if(flag == 1) {
            for (int i = 0; i < resources.size(); i++) {
                if (resources.get(i) == Resource.ANY){
                    json = ms.getPayload().get("resource"+i).getAsString();
                    resources.set(i, gson.fromJson(json, Resource.class));
                }
            }
        }else if(flag == 2){
            for (int i = 0; i < resources.size(); i++) {
                if (resources.get(i) == Resource.ANY)
                    resources.set(i, r);
            }
        }else
            resources = resources.stream().filter(resource -> resource != Resource.ANY).collect(Collectors.toCollection(ArrayList::new));

        for(int i=0; i < resources.size(); i++){
            if(resources.get(i) == Resource.FAITH){
                resources.remove(i);
                i--;
                faithP1++;
            }
        }


        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "WAREHOUSE_PLACEMENT");
        json = gson.toJson(resources); //forse sarebbe meglio trasformarlo in array
        payload.addProperty("resourcesArray", json);
        controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
    }

    /**
     * Method that manages the placement of resources in the warehouse.
     * If the placement is not correct, it will be reported to the user.
     * @param ms client message
     */
    public void warehousePlacement(ResponseMsg ms){
        controller.getCurrentGame().getTurn().setTypeOfAction(1);

        faithP2 = ms.getPayload().get("discarded").getAsInt();
        Gson gson = new Gson();
        String json = ms.getPayload().get("placed").getAsString();
        Type collectionType = new TypeToken<ArrayList<Resource>>(){}.getType();
        placed = gson.fromJson(json, collectionType);
        if(checkPlacement(placed)){
            //aggiornamento struttura warehouse
            controller.getCurrentPlayer().getBoard().getWarehouse().updateConfiguration(placed);
            //aggiornamento punti fede
            controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), faithP1, faithP2);
            faithP1 = 0;
            //prep messaggio ShortUpdate / leader activation

            if(controller.getCurrentGame().getTurn().isLastTurn() == 3){
                controller.finalUpdate(controller.getCurrentPlayer().getPlayerID());
            }else {
                JsonObject payload = new JsonObject();
                payload.addProperty("gameAction", "LEADER_ACTIVATION");
                payload.addProperty("endTurn", true);
                controller.getCurrentGame().getTurn().setEndGame(true);
                controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
            }
        }else{
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "WAREHOUSE_PLACEMENT");
            payload.addProperty("problem", true);
            json = gson.toJson(resources);
            payload.addProperty("resourcesArray", json);
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        }

    }

    /**
     * Method to check if the placement proposed by the user is right
     * @param placed proposed placement
     * @return true if the placement is ok
     */
    protected boolean checkPlacement(ArrayList<Resource> placed)
    {
        Resource r;
        ArrayList<Resource> blackList = new ArrayList<>();
        //check normal warehouse
        if(placed.get(0) != Resource.ANY)
            blackList.add(placed.get(0));
        if(placed.get(1) != Resource.ANY){
            blackList.add(placed.get(1));
            if(placed.get(2) != Resource.ANY && placed.get(2) != placed.get(1))
                return false;
        }else if(placed.get(2) != Resource.ANY){
               blackList.add(placed.get(2));
        }
        if(placed.get(3) != Resource.ANY){
            blackList.add(placed.get(3));
            if(placed.get(4) != Resource.ANY && placed.get(4) != placed.get(3))
                return false;
            else if(placed.get(4) != Resource.ANY && placed.get(4) == placed.get(3)){
                if(placed.get(5) != Resource.ANY && placed.get(5) != placed.get(4))
                    return false;
            }
        }else if(placed.get(4) != Resource.ANY){
            blackList.add(placed.get(4));
            if(placed.get(5) != Resource.ANY && placed.get(5) != placed.get(4))
                return false;
        }else if(placed.get(5) != Resource.ANY){
            blackList.add(placed.get(5));
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


