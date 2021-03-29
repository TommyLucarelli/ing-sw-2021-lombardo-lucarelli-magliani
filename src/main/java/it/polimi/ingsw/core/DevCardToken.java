package it.polimi.ingsw.core;

/**
 * Class representing the development card token
 */
public class DevCardToken implements SoloActionToken {

    private Colour c;

    /**
     * Class constructor
     * @param c is the colour of the flag of the development card
     */
    public DevCardToken(Colour c){
        this.c = c;
    }

    @Override
    public String getAction() {
        String s = "DT"+c;
        return s;
    }

}
