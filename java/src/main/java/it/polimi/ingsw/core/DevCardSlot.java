package it.polimi.ingsw.core;

import java.util.ArrayList;

/**
 * Class representing each slot inside the board for the development card
 * @author Tommaso Lucarelli
 */
public class DevCardSlot
{
    //potrebbe aver senso inserire un id -> per scelta con la gui di dove piazzare la carta (alternativamente usare indice array)
    private ArrayList<DevCard> slot;

    /**
     * Class constructor
     */
    public DevCardSlot()
    {
        slot = new ArrayList<DevCard>();
    }

    /**
     * Method to add a new development card in the slot
     * @param dc is the development card that is being inserted in the array
     */
    public void addCard(DevCard dc)
    {
        //controllo livello (probabilmente va messa un exception)
        slot.add(dc);
    }
}
