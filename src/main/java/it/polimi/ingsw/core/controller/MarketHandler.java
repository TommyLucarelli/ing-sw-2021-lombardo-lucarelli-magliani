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
    private boolean work;

    public MarketHandler(MainController controller){
        this.controller = controller;
        blackList = new ArrayList<>();
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
                //resources = resources.stream().filter(resource -> resource != Resource.ANY).collect(Collectors.toCollection(ArrayList::new));
                if (resources.get(i) == Resource.ANY)
                {
                    resources.remove(i);
                    i--;
                }
            }
        }

        //sistemazione punti fede
        //scorro l'array e tolgo i punti fede e conto quanti devo aggiungerne al giocatore
        //metodo punti fede per aggiornarli alla fine
        //preparazione messaggio placement con array risorse
        //attesa messaggio con array disposizione "placed"
        //controllo disposizione

        //check warehouse normale
        /**
        if(placed.get(0) != Resource.ANY)
            blackList.add(placed.get(0));
        if(placed.get(1) != Resource.ANY){
            blackList.add(placed.get(1));
            if(placed.get(1) != Resource.ANY){
                if(placed.get(1) != placed.get(2))
            }
        }
         */
        //Fre non fare push di codice che non compila

        return true;
    }

    //metodi con le varie fasi -> meglio per il debugging (es. capisci se ha il flag)
}
