package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.core.model.Board;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.core.model.ResourceQty;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.util.ArrayList;

public class ProductionHandler {

    private Board board;
    private MainController controller;

    public ProductionHandler(MainController controller){
        this.controller = controller;
    }

    public RequestMsg chooseProduction(ResponseMsg ms){
        //costruzione messaggio choose_production e invio
        return null;
    }

    public RequestMsg prodActivation(ResponseMsg ms){
        //ms è un array list di integer da 1 a 6 , rappresentatnti gli slot di attivazione
        //più un recipe per la prod base e due ResourceQty per le special ability
        boolean check = false;
        board = controller.getCurrentPlayer().getBoard();
        ArrayList<Integer> response = new ArrayList<>();
        int[] personalResources = board.personalResQtyToArray();
        ArrayList<ResourceQty> inputResources;
        ArrayList<ResourceQty> specialResources = new ArrayList<>();
        for(int i=0; i<response.size(); i++){
            if(response.get(i)==1){
                //recipe mex
                //inResource = recipeBasicProduction.getInputResources()
                //personalResources = reduceResource(inResources, personalResources);
            }else if(response.get(i)>=2 && response.get(i)<5){
                //recipe devcard
                inputResources = board.getDevCardSlot(response.get(i)-2).getTopCard().getRecipe().getInputResources();
                personalResources = reduceResource(inputResources, personalResources);
            } else{
                //se sono attivate vedi input res
                if(controller.getCurrentPlayer().getBoard().isActivated(i+1) != 0){
                    specialResources.add(new ResourceQty(controller.getCurrentPlayer().getBoard().getLeader(controller.getCurrentPlayer().getBoard().isActivated(i+1)).getSpecialAbility().getAbilityResource(),1));
                    personalResources = reduceResource(specialResources, personalResources);
                }else
                    check = true;
            }

        }
        for(int i=0; i < personalResources.length; i++){
            if(personalResources[i] < 0)
                check = true;
        }
        if(!check){

        }else{
           //costruzione messaggio choose_production e invio
        }
        return null;
    }

    protected int[] reduceResource(ArrayList<ResourceQty> inputResources, int[] personalResources){
        for(int i=0; i < inputResources.size(); i++)
        switch (inputResources.get(i).getResource()) {
            case COIN: {
                personalResources[0] -= inputResources.get(i).getQty();
                break;
            }
            case STONE: {
                personalResources[1] -= inputResources.get(i).getQty();
                break;
            }
            case SHIELD: {
                personalResources[2] -= inputResources.get(i).getQty();
                break;
            }
            case SERVANT: {
                personalResources[3] -= inputResources.get(i).getQty();
                break;
            }
        }
        return personalResources;
    }
}
