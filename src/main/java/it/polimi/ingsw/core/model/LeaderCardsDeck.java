package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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
     * @throws FileNotFoundException if the json file containing the leader cards cannot be found.
     */
    public LeaderCardsDeck() throws FileNotFoundException {
        Type CARD_TYPE = new TypeToken<Stack<LeaderCard>>() {}.getType();
        Gson g = new Gson();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/leadercards.json")));
        deck = g.fromJson(jsonReader, CARD_TYPE);

        Collections.shuffle(deck);
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
    public Stack<LeaderCard> getDeck(){
        return (Stack<LeaderCard>) deck.clone();
    }

    /**
     * Pop method.
     * @return the card on top of the deck.
     */
    public LeaderCard drawCard(){
        return deck.pop();
    }
}
