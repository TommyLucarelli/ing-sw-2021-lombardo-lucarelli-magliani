package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Class representing the faith track inside the board
 * @author Tommaso Lucarelli
 */
public class FaithTrack {
    private int position;
    private boolean[] favourCardsFlag;

    /**
     * Class constructor
     */
    public FaithTrack(){
        position = 0;
        favourCardsFlag = new boolean[3];
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
    public boolean[] getFavourCardsFlag() {
        return favourCardsFlag.clone();
    }

    /**
     * Getter method, returns the favour card at the specified index
     * @param i the index of the card
     * @return boolean value that is true or false whether the card is activated or not.
     */
    public boolean getFavourCardsFlag(int i){
        return favourCardsFlag[i];
        //exception outofbound già integrata, da gestire nel controller
    }

    /**
     * Method to put the favour card face up
     * @param i position of the pope spot reached by the user ahead
     * @return true if the Favour Card is set, false if the user can't set the Favour Card due to his position in the faith track
     */
    public boolean setFavourCardsFlag(int i){
        if(this.position > (i-4)){
            i = (i/8) - 1;
            favourCardsFlag[i] = true;
            return true;
        } else
            return false;
    }

    /**
     * Method to move the Faith Indicator forward in the track
     * @return true or false whether the faith indicator is on a pope spot or not
     */
    public boolean moveFaithIndicator(){
        position++;
        if(position == 8 || position == 16 || position == 24)
            return true; //se è true bisogna controllare che non siamo arrivati alla fine
        else
            return false;
    }

    /**
     * Method to compute the victory points based on the favour cards activated in the faith track
     * @return victory points
     */
    public int favourVictoryPoints(){
        int cont=0;
        for(int i=0; i<3; i++){
            if(favourCardsFlag[i])
                cont= cont+i+2;
        }
        return cont;
    }

    /**
     * Method used for network communication
     * @return a brief representation of the faith track as a Json Object
     */
    public JsonObject toCompactFaithTrack(){

        Gson gson = new Gson();
        JsonObject payload = new JsonObject();
        String json = gson.toJson(favourCardsFlag);
        payload.addProperty("index", position);
        payload.addProperty("favCards", json);

        return payload;
    }


}
