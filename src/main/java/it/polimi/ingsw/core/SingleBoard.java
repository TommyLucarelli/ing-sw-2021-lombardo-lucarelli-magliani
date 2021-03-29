package it.polimi.ingsw.core;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class extending the board in mode single player
 */
public class SingleBoard extends Board{
    private ArrayList<SoloActionToken> soloActionTokens;
    private int counter = 0;

    /**
     * Constructor method
     */
    public SingleBoard(){
        super();
        soloActionTokens = new ArrayList<SoloActionToken>();
        soloActionTokens.add(new DevCardToken(Colour.GREEN));
        soloActionTokens.add(new DevCardToken(Colour.BLUE));
        soloActionTokens.add(new DevCardToken(Colour.YELLOW));
        soloActionTokens.add(new DevCardToken(Colour.PURPLE));
        soloActionTokens.add(new BlackFlagToken(1,true));
        soloActionTokens.add(new BlackFlagToken(2,false));
        Collections.shuffle(soloActionTokens);
    }

    /**
     * Getter method to draw a token from the stack on the board
     * @return the first token
     */
    public SoloActionToken getSoloActionToken(){
        if(counter!=0)
            counter ++;
        return soloActionTokens.get(counter);
        //forse andrebbe ritornato un clone
    }

    /**
     * method to reset the counter when the player draw a "shuffle" token
      */
    public void resetCounter(){
        counter = 0;
    }

    /**
     * method to shuffle the deck of tokens
     */
    public void shuffleDeck(){
        Collections.shuffle(soloActionTokens);
    }
    

}
