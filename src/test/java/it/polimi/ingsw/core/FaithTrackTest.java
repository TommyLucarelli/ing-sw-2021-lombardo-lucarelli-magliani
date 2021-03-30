package it.polimi.ingsw.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class FaithTrackTest {

    @Test
    public void setFavourCardsFlag() {
        FaithTrack ft = new FaithTrack();

        assertEquals(ft.getFavourCardsFlag(0), false);
        assertEquals(ft.getFavourCardsFlag(1), false);
        assertEquals(ft.getFavourCardsFlag(2), false);

        ft.setFavourCardsFlag(1);

        assertEquals(ft.getFavourCardsFlag(0), false);
        assertEquals(ft.getFavourCardsFlag(1), true);
        assertEquals(ft.getFavourCardsFlag(2), false);
    }

    @Test
    public void moveFaithIndicator() {
        FaithTrack ft = new FaithTrack();

        ft.moveFaithIndicator();
        ft.moveFaithIndicator();
        ft.moveFaithIndicator();

        assertEquals(3, ft.getPosition());
    }
}