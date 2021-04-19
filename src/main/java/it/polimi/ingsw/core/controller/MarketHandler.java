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

    public MarketHandler(MainController controller){
        this.controller = controller;
    }

    @Override
    public boolean runPhase() {
        market = controller.getCurrentGame().getMarket();
        //preparazione messaggio pick
        //attesa risposta di pick con scelta colonna o riga del mercato e il valore x (controllo validità messaggio, potrebbe essere fatto nel client alla composizione del messaggio)
        /*if(colonna)
            resources = market.updateColumnAndGetResources(x);
        else
            resources = market.updateLineAndGetResources(x);*/

        //processing array risorse con trasformazione marble bianche
        if(controller.getCurrentPlayer().getBoard().isActivated(2) != 0){
            if(controller.getCurrentPlayer().getBoard().isActivated(3) != 0){
                //crea messaggio marble transformation
                //aspetta la risposta e modifica l'array cambiando le risorse bianche con quelle selezionate
            }else{
                for (Resource resource : resources) {
                    if (resource == Resource.ANY)
                        resource = controller.getCurrentPlayer().getBoard().activeLeader(controller.getCurrentPlayer().getBoard().isActivated(2)).getSpecialAbility().getAbilityResource();
                }
            }
            for(int i = 0; i <resources.size(); i++){
                //può essere migliorato con uno stream
                //resources = resources.stream().filter(resource -> resource != Resource.ANY).collect(Collectors.toCollection(ArrayList::new));
                //così?
                if (resources.get(i) == Resource.ANY)
                {
                    resources.remove(i);
                    i--;
                }
            }


        }

        //sistemazione punti fede
        //preparazione messaggio placement
        //attesa messaggio
        //controllo disposizione



        return false;
    }
}
