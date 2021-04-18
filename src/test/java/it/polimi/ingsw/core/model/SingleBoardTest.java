package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.SingleBoard;
import it.polimi.ingsw.core.model.SoloActionToken;
import org.junit.Test;

import static org.junit.Assert.*;

public class SingleBoardTest {

    @Test
    public void getSoloActionToken() {
        SingleBoard sb = new SingleBoard();
        SoloActionToken sat;
        sat = sb.getSoloActionToken();

        assertNotNull(sat);
        assertNotEquals(sb.getSoloActionToken(),sat);
    }

}