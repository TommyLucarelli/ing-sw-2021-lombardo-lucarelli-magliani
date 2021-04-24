package it.polimi.ingsw.core.model;

import java.util.ArrayList;

/**
 * Class to save the state of the turn. Also have a method that compute the next player
 */
public class Turn {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private boolean lastTurn;
    private boolean endGame;
}

