package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.core.model.*;

import java.util.ArrayList;

public class DevCardHandler implements PhaseHandler{

    private MainController controller;
    private DevCardStructure devCardstruct;
    private DevCard devCard;
    private ArrayList<ResourceQty> resqtyStrongbox = new ArrayList<>();
    private ArrayList<Resource> resqtyWarehouse = new ArrayList<>();
    private ArrayList<ResourceQty> resqtyDevCard = new ArrayList<>();
    private int[] resDevCard = new int[4];
    private int[] resPlayer = new int[4];
    int i,j;

    public DevCardHandler(MainController controller){
        this.controller = controller;
    }


    @Override
    public boolean runPhase() {
        devCardstruct = controller.getCurrentGame().getDevCardStructure();
        //costruzione messaggio CHOICE
        //arriva il messaggio dal client con la scelta della carta sviluppo come pos (i,j) nel devcardstructure


        return true;
    }

    public boolean affordable(){
        devCard = devCardstruct.getTopCard(i,j);
        resqtyStrongbox = controller.getCurrentPlayer().getBoard().getStrongbox().getResources();
        for(i=0; i<resqtyStrongbox.size(); i++){
        switch (resqtyStrongbox.get(i).getResource()){
            case COIN: resPlayer[0]+=resqtyStrongbox.get(i).getQty(); break;
            case STONE: resPlayer[1]+=resqtyStrongbox.get(i).getQty(); break;
            case SHIELD:resPlayer[2]+=resqtyStrongbox.get(i).getQty(); break;
            case SERVANT: resPlayer[3]+=resqtyStrongbox.get(i).getQty(); break;
        }
    }
    resqtyWarehouse = controller.getCurrentPlayer().getBoard().getWarehouse().getStructure();
        for(i=0; i<resqtyWarehouse.size(); i++){
        switch (resqtyWarehouse.get(i)){
            case COIN: resPlayer[0]++; break;
            case STONE: resPlayer[1]++; break;
            case SHIELD:resPlayer[2]++; break;
            case SERVANT: resPlayer[3]++; break;
        }
    }
    resqtyDevCard = devCard.getCost();
        for(i=0; i<resqtyDevCard.size(); i++){
        switch (resqtyDevCard.get(i).getResource()){
            case COIN: resDevCard[0]+=resqtyDevCard.get(i).getQty(); break;
            case STONE: resDevCard[1]+=resqtyDevCard.get(i).getQty(); break;
            case SHIELD:resDevCard[2]+=resqtyDevCard.get(i).getQty(); break;
            case SERVANT: resDevCard[3]+=resqtyDevCard.get(i).getQty(); break;
        }
    }

        for (i=0; i<4; i++) {
            if (resPlayer[i] >= resDevCard[i])
                return false;
        }
    return true;
    }
}
