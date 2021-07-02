package it.polimi.ingsw.core.model;

import java.util.ArrayList;

/**
 * Class representing a turn of a single player game
 */
public class SingleTurn extends Turn{
    private SoloActionToken soloActionToken;

    /**
     * Class constructor
     * @param players an array containing only the player of the single game
     */
    public SingleTurn(ArrayList<Player> players) {
        super(players);
    }

    /**
     * Getter method
     * @return the solo action token activated during the turn
     */
    public SoloActionToken getSoloActionToken() {
        return soloActionToken;
    }

    /**
     * Setter method
     * @param soloActionToken the solo action token activated during the turn
     */
    public void setSoloActionToken(SoloActionToken soloActionToken) {
        this.soloActionToken = soloActionToken;
    }
}
