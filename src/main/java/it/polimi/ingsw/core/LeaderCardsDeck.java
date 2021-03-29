package it.polimi.ingsw.core;

import java.util.Collections;
import java.util.Stack;

/**
 * Class representing the deck of leader cards generated at the beginning of the game
 * @author Giacomo Lombardo
 */
public class LeaderCardsDeck {
    private Stack<LeaderCard> deck;

    /**
     * Class constructor. Generates the 16 leader cards.
     */
    public LeaderCardsDeck(){

    }

    /**
     * Shuffles the deck.
     */
    public void shuffle(){
        Collections.shuffle(deck);
    }

    //TODO: (jack) aggiungere getter del deck (?) e pop (pesca carta)
}
