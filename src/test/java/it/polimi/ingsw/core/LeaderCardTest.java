package it.polimi.ingsw.core;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LeaderCardTest {

    @Test
    public void getRequiredFlags() {
        ArrayList<Flag> requiredFlags = new ArrayList<Flag>();
        requiredFlags.add(new Flag(Colour.GREEN));
        requiredFlags.add(new Flag(Colour.GREEN));
        requiredFlags.add(new Flag(Colour.PURPLE));
        LeaderCard leaderCard = new LeaderCard(1, requiredFlags, null, 5, 2, Resource.SHIELD);

        assertEquals(leaderCard.getRequiredFlags().size(), 3);
        assertEquals(leaderCard.getRequiredFlags().get(1).getColour(), Colour.GREEN);
        assertEquals(leaderCard.getRequiredFlags().get(1).getLevel(), 0);
    }

    @Test
    public void getRequiredQty() {
        ArrayList<ResourceQty> requiredResources = new ArrayList<ResourceQty>();
        requiredResources.add(new ResourceQty(Resource.SERVANT, 5));
        LeaderCard leaderCard = new LeaderCard(1, null, requiredResources, 3, 4, Resource.SHIELD);

        assertEquals(leaderCard.getRequiredResources().get(0).getQty(), 5);
        assertEquals(leaderCard.getRequiredResources().get(0).getResource(), Resource.SERVANT);
    }

    @Test
    public void getVictoryPoints() {
        ArrayList<ResourceQty> requiredResources = new ArrayList<ResourceQty>();
        requiredResources.add(new ResourceQty(Resource.SERVANT, 5));
        LeaderCard leaderCard = new LeaderCard(1, null, requiredResources, 3, 4, Resource.SHIELD);

        assertEquals(leaderCard.getVictoryPoints(),3);
    }

    @Test
    public void getSpecialAbility() {
        ArrayList<ResourceQty> requiredResources = new ArrayList<ResourceQty>();
        requiredResources.add(new ResourceQty(Resource.SERVANT, 5));
        LeaderCard leaderCard = new LeaderCard(1, null, requiredResources, 3, 4, Resource.SHIELD);

        assertEquals(leaderCard.getSpecialAbility().getResource(), Resource.SHIELD);
    }
}