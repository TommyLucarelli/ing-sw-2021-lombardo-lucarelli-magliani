package it.polimi.ingsw.core;

import java.util.ArrayList;

/**
 * Class representing the faith track inside the board
 * @author Tommaso Lucarelli
 */
public class FaithTrack {
    private int position;
    private Boolean[] favourCardsFlag;

    /**
     * Class constructor
     */
    public FaithTrack(){
        position = 0;
        favourCardsFlag = new Boolean[3];
        favourCardsFlag[0] = false;
        favourCardsFlag[1] = false;
        favourCardsFlag[2] = false;
    }

    /**
     * Getter method
     * @return position of the Faith Indicator
     */
    public int getPosition() {
        return position;
    }

    /**
     * Getter method
     * @return boolean array that represents whether a favour card is activated
     */
    public Boolean[] getFavourCardsFlag() {
        return favourCardsFlag.clone();
    }

    /**
     * Getter method, returns the favour card at the specified index
     * @param i the index of the card
     * @return boolean value that is true or false whether the card is activated or not.
     */
    public boolean getFavourCardsFlag(int i){
        return favourCardsFlag[i];
        //TODO: (tommy) out of bounds exception
    }

    /**
     * Method to move the Faith Indicator forward in the track
     */
    public void moveFaithIndicator(){
        position++;
        //da aggiungere controllo (o eccezione) se si finisce su uno spot papale per l'attivazione delle carte favore
    }
}
