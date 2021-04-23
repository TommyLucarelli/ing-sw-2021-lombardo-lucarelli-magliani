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
        //più un recipe per la prod base e due per le special ability
        board = controller.getCurrentPlayer().getBoard();
        ArrayList<Integer> response = new ArrayList<>();
        int[] personalResources = board.personalResQtyToArray();
        ArrayList<ResourceQty> inputResources;
        for(int i=0; i<response.size(); i++){
            if(response.get(i)==1){
                //recipe mex
                inputResources = board.getDevCardSlot(0).getTopCard().getRecipe().getInputResources();
                switch (inputResources.get(i).getResource()){
                    case COIN: {
                        personalResources[0]--;
                        break;
                    }
                    case STONE: {
                        personalResources[1]--;
                        break;
                    }
                    case SHIELD: {
                        personalResources[2]--;
                        break;
                    }
                    case SERVANT: {
                        personalResources[3]--;
                        break;
                    }
                }
            }else if(response.get(i)>=2 && response.get(i)<5){
                //recipe devcard

            }else{
                //se sono attivate vedi input res
            }

            //allResources>0
        }
        return null;
    }
}
