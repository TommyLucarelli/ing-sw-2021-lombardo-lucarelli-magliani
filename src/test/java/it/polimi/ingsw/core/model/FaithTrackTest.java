package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.FaithTrack;
import org.junit.Test;

import static org.junit.Assert.*;

public class FaithTrackTest {

    @Test
    public void testSetFavourCardsFlag() {
        FaithTrack ft = new FaithTrack();

        assertEquals(ft.getFavourCardsFlag(0), false);
        assertEquals(ft.getFavourCardsFlag(1), false);
        assertEquals(ft.getFavourCardsFlag(2), false);

        for(int i=0; i<14; i++)
            ft.moveFaithIndicator();
        ft.setFavourCardsFlag(16);

        assertEquals(ft.getFavourCardsFlag(0), false);
        assertEquals(ft.getFavourCardsFlag(1), true);
        assertEquals(ft.getFavourCardsFlag(2), false);
    }

    @Test
    public void testMoveFaithIndicator() {
        FaithTrack ft = new FaithTrack();

        ft.moveFaithIndicator();
        ft.moveFaithIndicator();
        ft.moveFaithIndicator();

        assertEquals(3, ft.getPosition());
    }

    @Test
    public void testFavourVictoryPoints(){
        FaithTrack ft = new FaithTrack();
        for(int i=0; i<14; i++)
            ft.moveFaithIndicator();
        ft.setFavourCardsFlag(16);

        assertEquals(3, ft.favourVictoryPoints());
    }

    @Test
    public void testFaithVictoryPoints(){
        FaithTrack ft = new FaithTrack();
        for(int i=0; i<16; i++)
            ft.moveFaithIndicator();

        assertEquals(9, ft.faithVictoryPoints());
    }
}