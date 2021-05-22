package it.polimi.ingsw.core.model;

import com.google.gson.JsonObject;

/**
 * Class representing black flag token
 */
public class BlackFlagToken implements SoloActionToken{
    private final int spaces;
    private final boolean shuffle;

    /**
     * Class constructor
     * @param spaces is the number of space to move the indicator on the faith track
     * @param shuffle is true if the token imply the shuffle of the deck
     */
    public BlackFlagToken(int spaces, boolean shuffle){
        this.spaces = spaces;
        this.shuffle = shuffle;
    }

    /**
     * Getter method
     * @return the number of spaces to move the indicator.
     */
    public int getSpaces(){
        return this.spaces;
    }

    /**
     * Getter method.
     * @return true if the token - when it is drawn - causes the reshuffling of the tokens deck.
     */
    public boolean shufflesDeck(){
        return this.shuffle;
    }

    @Override
    public JsonObject getAction() {
        JsonObject payload = new JsonObject();
        payload.addProperty("type", 1);
        payload.addProperty("shuffle", shuffle);
        return payload;
    }

    public String getMessage(){
        String s;
        if(shuffle)
            s = "You revealed a Black Flag Token with 1 faithpoint and shuffle";
        else
            s = "You revealed a Black Flag Token with 2 faithpoints";
        return s;
    }
}
