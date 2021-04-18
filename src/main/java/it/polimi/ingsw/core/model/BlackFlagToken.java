package it.polimi.ingsw.core.model;

/**
 * Class representing black flag token
 */
public class BlackFlagToken implements SoloActionToken{

    private int spaces;
    private boolean shuffle;

    /**
     * Class constructor
     * @param spaces is the number of space to move the indicator on the faith track
     * @param shuffle is true if the token imply the shuffle of the deck
     */
    public BlackFlagToken(int spaces, boolean shuffle){
        this.spaces = spaces;
        this.shuffle = shuffle;
    }

    public int getSpaces(){
        return this.spaces;
    }

    public boolean shufflesDeck(){
        return this.shuffle;
    }

    @Override
    public String getAction() {
        String s = "BFT"+spaces+shuffle;
        return s;
    }
}
