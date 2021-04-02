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

    /**
     * Getter method.
     * @return the entire deck.
     */
    public Stack<LeaderCardsDeck> getDeck(){
        return (Stack<LeaderCardsDeck>) deck.clone();
    }

    /**
     * Pop method.
     * @return the card on top of the deck.
     */
    public LeaderCard drawCard(){
        return deck.pop();
    }
}
