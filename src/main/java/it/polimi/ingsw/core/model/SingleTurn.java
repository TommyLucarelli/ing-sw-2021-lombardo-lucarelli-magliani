package it.polimi.ingsw.core.model;

import java.util.ArrayList;

public class SingleTurn extends Turn{
    private SoloActionToken soloActionToken;

    public SingleTurn(ArrayList<Player> players) {
        super(players);
    }

    public SoloActionToken getSoloActionToken() {
        return soloActionToken;
    }

    public void setSoloActionToken(SoloActionToken soloActionToken) {
        this.soloActionToken = soloActionToken;
    }
}
