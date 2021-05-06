package it.polimi.ingsw.core.model;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class LeaderCardsDeckTest {
    @Test
    public void testLeaderCardsDeck() {
        try {
            LeaderCardsDeck deck = new LeaderCardsDeck();
            System.out.println(deck.getDeck().size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}