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
        //ms è un array list di integer da 1 a 6 , rappresentatnti gli slot di attivazione
        //più un recipe per la prod base e due ResourceQty per le special ability
        boolean check = true;
        board = controller.getCurrentPlayer().getBoard();
        ArrayList<Integer> response = new ArrayList<>();
        int[] personalResources = board.personalResQtyToArray();
        int[] copyPersonalResources = board.personalResQtyToArray();
        ArrayList<ResourceQty> inputResources;
        ArrayList<ResourceQty> specialResources = new ArrayList<>();
        ArrayList<ResourceQty> outputResources= new ArrayList<>();
        for(int i=0; i<response.size(); i++){
            if(response.get(i)==1){
                //recipe mex
                //inResource = recipeBasicProduction.getInputResources();
                //personalResources = reduceResource(inResources, personalResources);
                //outputResources.add(recipeBasicProduction.getOutputResources());
            }else if(response.get(i)>=2 && response.get(i)<5){
                //recipe devcard
                inputResources = board.getDevCardSlot(response.get(i)-2).getTopCard().getRecipe().getInputResources();
                personalResources = reduceResource(inputResources, personalResources);
                outputResources.addAll(board.getDevCardSlot(response.get(i)-2).getTopCard().getRecipe().getOutputResources());
            } else{
                //se sono attivate vedi input res
                if(board.isActivated(i+1) != 0){
                    specialResources.add(new ResourceQty(board.getLeader(board.isActivated(i+1)).getSpecialAbility().getAbilityResource(),1));
                    personalResources = reduceResource(specialResources, personalResources);
                    outputResources.addAll(specialResources);
                }else {
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
            board.getWarehouse().decResWarehouse(copyPersonalResources, false);
            board.getWarehouse().decResWarehouse(copyPersonalResources, true);
            board.getStrongbox().decreaseResource(copyPersonalResources);
            //aggiunta risorse della produzione
            board.getStrongbox().addResource(outputResources);
            //costruzione messaggio ShortUpdate / LeaderActivation
        }else{
           //costruzione messaggio choose_production e invio
        }
        return null;
    }

    protected int[] reduceResource(ArrayList<ResourceQty> inputResources, int[] personalResources){
        for(int i=0; i < inputResources.size(); i++) {
            personalResources[inputResources.get(i).getResource().ordinal()] -= inputResources.get(i).getQty();
        }
        return personalResources;
    }
}
