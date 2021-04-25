package it.polimi.ingsw.core.controller;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;

public class TurnHandler {
    private MainController controller;

    public TurnHandler(MainController controller){
        this.controller = controller;
    }

    public RequestMsg mainChoice(ResponseMsg ms) {
        if (ms.equals(0)) {
            //costruzione e invio messaggio Pick
            return null;
        } else if (ms.equals(1)) {
            //costruzione e invio messaggio Choose_Production
            return null;
        } else{
            //costruzione e invio messaggio Choose_DevCard
            return null;
        }
    }

    public RequestMsg leaderActivation(ResponseMsg ms){
        if(ms.equals(1)){
            //costruzione e invio messaggio leader_Action
            return null;
        }
        else{
            //costruzione e invio messaggio main_choice
            return null;
        }
    }

    public RequestMsg comeBack(ResponseMsg ms){
        //costruzione e invo messaggio main_choice se siamo all'inizio
        //o messaggio update se siamo alla fine  [per capire ciò salveremo in turn a che fase siamo del gioco]
        return null;
    }
}
