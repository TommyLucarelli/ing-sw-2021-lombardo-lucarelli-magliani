package it.polimi.ingsw.core.controller;

import it.polimi.ingsw.core.model.Market;
import it.polimi.ingsw.core.model.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * This Class will handle the phase of acquiring resource from the market
 * @author Tommaso Lucarelli
 */
public class MarketHandler implements PhaseHandler{

    private Market market;
    private MainController controller;
    private ArrayList<Resource> resources;
    private ArrayList<Resource> blackList;
    private ArrayList<Resource> placed;
    private boolean configWorks;
    private Resource r;

    /**
     * Class constructor
     * @param controller that called this class
     */
    public MarketHandler(MainController controller){
        this.controller = controller;
        blackList = new ArrayList<>();
    }

    /**
     * Main method that handle the market phase
     * @return true if the phase went well
     */
    public boolean runPhase() {
        market = controller.getCurrentGame().getMarket();
        int faithP1 = 0, faithP2 = 0;
        //preparazione messaggio pick
        //attesa risposta di pick con scelta colonna o riga del mercato e il valore x (controllo validità messaggio, potrebbe essere fatto nel client alla composizione del messaggio)
        /*if(colonna)
            resources = market.updateColumnAndGetResources(x);
        else
            resources = market.updateLineAndGetResources(x);*/

        //processing array risorse con trasformazione marble bianche
        if(controller.getCurrentPlayer().getBoard().isActivated(2) != 0){
            if(controller.getCurrentPlayer().getBoard().isActivated(3) != 0){
                //crea messaggio marble transformation LO TOGLIAMO
                //aspetta la risposta e modifica l'array cambiando le risorse bianche con quelle selezionate
            }else{
                for (Resource resource : resources) {
                    if (resource == Resource.ANY)
                        resource = controller.getCurrentPlayer().getBoard().getLeader(controller.getCurrentPlayer().getBoard().isActivated(2)).getSpecialAbility().getAbilityResource();
                }
            }
            resources = resources.stream().filter(resource -> resource != Resource.ANY).collect(Collectors.toCollection(ArrayList::new));
        }

        //sistemazione punti fede
        for(int i=0; i < resources.size(); i++){
            if(resources.get(i) == Resource.FAITH){
                resources.remove(i);
                i--;
                faithP1++;
            }
        }

        do{
              //preparazione messaggio placement con array risorse
              //attesa messaggio con array disposizione "placed" + risorse scartate int discRes -> faithP2
              //controllo disposizione
              configWorks = checkPlacement(placed);
        }while(!configWorks);

        //aggiornamento struttura warehouse
        controller.getCurrentPlayer().getBoard().getWarehouse().updateConfiguration(placed);

        //aggiornamento punti fede
        controller.getCurrentGame().FaithTrackUpdate(controller.getCurrentPlayer(), faithP1, faithP2);

        //Short_update [player x has acquired new resources from the market, + avanzamento fede + marker]
        //ha senso mandare uno short-update così senza mostrare l'aggiornamento vero e proprio ai giocatori
        //forse solo con l'azione scelta

        return true;
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


