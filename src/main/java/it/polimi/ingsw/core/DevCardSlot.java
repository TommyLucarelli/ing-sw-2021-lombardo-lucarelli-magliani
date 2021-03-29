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
    public boolean addCard(DevCard dc)
    {
        if(dc.getFlag().getLevel() == (slot.size() + 1)){
            slot.add(dc);
            return true;
        } else {
            return false;
        }
        //aggiungere eventuale exception
    }

    /**
     * Getter method
     * @return the list of the development card in the slot
     */
    public ArrayList<DevCard> getSlot() {
        return (ArrayList<DevCard>) slot.clone();
    }

    /**
     * Getter method
     * @return the card on the top of the slot
     */
    public DevCard getTopCard(){
        return slot.get(slot.size() - 1);
        //TODO: (tommy) aggiungere exception
    }
}
