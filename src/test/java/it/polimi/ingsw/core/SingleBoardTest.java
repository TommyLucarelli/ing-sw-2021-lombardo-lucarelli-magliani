package it.polimi.ingsw.core;

import org.junit.Test;

import java.util.ArrayList;

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