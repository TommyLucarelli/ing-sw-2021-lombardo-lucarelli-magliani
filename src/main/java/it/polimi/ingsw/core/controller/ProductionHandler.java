package it.polimi.ingsw.core.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Board;
import it.polimi.ingsw.core.model.Recipe;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.core.model.ResourceQty;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProductionHandler {

    private Board board;
    private MainController controller;

    public ProductionHandler(MainController controller){
        this.controller = controller;
    }

    /**
     * Method to manage production.
     * First of all it will be checked if it is possible to do the production and then the new resources will be generated and
     * placed in the strongbox.
     * @param ms client message
     */
    public void chooseProduction(ResponseMsg ms){
        boolean check = true;
        ArrayList<ResourceQty> inputResources = new ArrayList<>();
        ArrayList<ResourceQty> specialResources = new ArrayList<>();
        ArrayList<ResourceQty> outputResources = new ArrayList<>();

        board = controller.getCurrentPlayer().getBoard();
        int[] personalResources = board.personalResQtyToArray();
        int[] copyPersonalResources = board.personalResQtyToArray();


        ArrayList<Integer> productions;
        Gson gson = new Gson();
        String json = ms.getPayload().get("productions").getAsString();
        Type collectionType = new TypeToken<ArrayList<Integer>>(){}.getType();
        productions = gson.fromJson(json, collectionType);

        for(int i=0; i<productions.size(); i++){
            if(productions.get(i)==1){
                Recipe recipeBasicProduction;
                String json2 = ms.getPayload().get("basicProduction").getAsString();
                recipeBasicProduction = gson.fromJson(json2, Recipe.class);
                inputResources = recipeBasicProduction.getInputResources();
                personalResources = reduceResource(inputResources, personalResources);
                outputResources.addAll(recipeBasicProduction.getOutputResources());
            }else if(productions.get(i)>=2 && productions.get(i)<5){
                //recipe devcard
                inputResources = board.getDevCardSlot(productions.get(i)-2).getTopCard().getRecipe().getInputResources();
                personalResources = reduceResource(inputResources, personalResources);
                ArrayList<ResourceQty> useful = board.getDevCardSlot(productions.get(i)-2).getTopCard().getRecipe().getOutputResources();
                for (int j = 0; j < useful.size(); j++) {
                    if(useful.get(j).getResource().equals(Resource.FAITH)){
                        controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 1, 0);
                        useful.remove(j);
                        j--;
                    }
                }
                outputResources.addAll(useful);
            }else{
                //se sono attivate vedi input res
                if(board.isActivated(productions.get(i)+1) != 0){
                    Resource rq;
                    String json2 = ms.getPayload().get("specialProduction"+(productions.get(i)-4)).getAsString(); //special ability 1 o 2
                    rq = gson.fromJson(json2, Resource.class);
                    specialResources.add(new ResourceQty(board.getLeader(board.isActivated(productions.get(i)+1)).getSpecialAbility().getAbilityResource(),1));
                    personalResources = reduceResource(specialResources, personalResources);
                    outputResources.add(new ResourceQty(rq, 1));
                    controller.getCurrentGame().faithTrackUpdate(controller.getCurrentPlayer(), 1, 0);
                }else{
                    check = false;
                    break;
                }
            }
        }

        for(int i=0; i < personalResources.length; i++){
            copyPersonalResources[i]-=personalResources[i];
            if(personalResources[i] < 0)
                check = false;
        }

        if(check){
            //rimozione risorse
            board.getWarehouse().decResWarehouse(copyPersonalResources);
            board.getStrongbox().decreaseResource(copyPersonalResources);
            //aggiunta risorse della produzione
            board.getStrongbox().addResource(outputResources);
            //costruzione messaggio ShortUpdate / LeaderActivation
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "LEADER_ACTIVATION");
            payload.addProperty("endTurn", true);
            controller.getCurrentGame().getTurn().setTypeOfAction(3);
            controller.getCurrentGame().getTurn().setEndGame(true);
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        }else{
           //costruzione messaggio choose_production e invio
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "CHOOSE_PRODUCTION");
            payload.addProperty("problem", true);
            controller.notifyCurrentPlayer(new RequestMsg(MessageType.GAME_MESSAGE, payload));
        }
    }

    /**
     * Method to reduce the resources, with the array representation
     * @param inputResources input resources for the production
     * @param personalResources player resources from strongbox and warehouse
     * @return personalResources updated
     */
    protected int[] reduceResource(ArrayList<ResourceQty> inputResources, int[] personalResources){
        for(int i=0; i < inputResources.size(); i++) {
            personalResources[inputResources.get(i).getResource().ordinal()] -= inputResources.get(i).getQty();
        }
        return personalResources;
    }
}