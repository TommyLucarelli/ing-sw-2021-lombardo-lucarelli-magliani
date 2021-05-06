package it.polimi.ingsw.core.model;

import com.google.gson.Gson;
import it.polimi.ingsw.core.model.*;
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
        SpecialAbility specialAbility = new SpecialAbility(1, Resource.COIN);
        LeaderCard leaderCard = new LeaderCard(1, requiredFlags, null, 5, specialAbility);

        assertEquals(leaderCard.getRequiredFlags().size(), 3);
        assertEquals(leaderCard.getRequiredFlags().get(1).getColour(), Colour.GREEN);
        assertEquals(leaderCard.getRequiredFlags().get(1).getLevel(), 0);
    }

    @Test
    public void getRequiredQty() {
        SpecialAbility specialAbility = new SpecialAbility(1, Resource.COIN);
        LeaderCard leaderCard = new LeaderCard(1, null, new ResourceQty(Resource.SERVANT, 5), 3, specialAbility);

        assertEquals(leaderCard.getRequiredResources().getQty(), 5);
        assertEquals(leaderCard.getRequiredResources().getResource(), Resource.SERVANT);
    }

    @Test
    public void getVictoryPoints() {
        SpecialAbility specialAbility = new SpecialAbility(1, Resource.COIN);
        LeaderCard leaderCard = new LeaderCard(1, null, new ResourceQty(Resource.SERVANT, 5), 3, specialAbility);

        assertEquals(leaderCard.getVictoryPoints(),3);
    }

    @Test
    public void getSpecialAbility() {
        SpecialAbility specialAbility = new SpecialAbility(1, Resource.COIN);
        LeaderCard leaderCard = new LeaderCard(1, null, new ResourceQty(Resource.SERVANT, 5), 3, specialAbility);

        assertEquals(leaderCard.getSpecialAbility().getAbilityResource(), Resource.COIN);
    }
}