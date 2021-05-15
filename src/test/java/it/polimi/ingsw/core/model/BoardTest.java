package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void testAddLeaderCard() {
        Board b = new Board();
        ArrayList<Flag> requiredFlags = new ArrayList<>();
        requiredFlags.add(new Flag(2, Colour.BLUE));
        SpecialAbility specialAbility = new SpecialAbility(1, Resource.COIN);
        LeaderCard lc = new LeaderCard(1, requiredFlags, null, 5, specialAbility);
        LeaderCard x;

        b.addLeaderCard(lc);
        x = b.getLeaderCard(0);

        assertEquals(lc, x);
    }

    @Test
    public void testRemoveLeaderCard() {
        Board b = new Board();
        ArrayList<Flag> requiredFlags = new ArrayList<>();
        requiredFlags.add(new Flag(2, Colour.BLUE));
        SpecialAbility specialAbility = new SpecialAbility(1, Resource.COIN);
        LeaderCard lc = new LeaderCard(1, requiredFlags, null, 5, specialAbility);
        LeaderCard x;

        b.addLeaderCard(lc);
        x = b.getLeaderCard(0);
        b.removeLeaderCard(lc);
        try {
            x = b.getLeaderCard(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There are no leader cards in this deck.");
        }
    }

    @Test
    public void testPersonalResQtyToArray() {
        Board b = new Board();
        int[] output = {3, 2, 4, 1};
        int[] test;
        ArrayList<Resource> update = new ArrayList<Resource>();

        for (int i = 0; i < 3; i++) {
            update.add(Resource.COIN);
        }
        for (int i = 3; i < 5; i++) {
            update.add(Resource.STONE);
        }
        for (int i = 5; i < 9; i++) {
            update.add(Resource.SHIELD);
        }
        update.add(Resource.SERVANT);

        b.getWarehouse().updateConfiguration(update);
        test = b.personalResQtyToArray();

        assertArrayEquals(output, test);
    }

    @Test
    public void countFlags() {
        Board board = new Board();

        Flag f = new Flag(1, Colour.BLUE);
        Flag f1 = new Flag(2, Colour.PURPLE);
        Flag f2 = new Flag(1, Colour.BLUE);
        ArrayList<ResourceQty> rq1 = new ArrayList<>();
        ArrayList<ResourceQty> rq2 = new ArrayList<>();
        ArrayList<ResourceQty> rq3 = new ArrayList<>();
        rq1.add(new ResourceQty(Resource.SERVANT));
        rq2.add(new ResourceQty(Resource.SERVANT));
        rq3.add(new ResourceQty(Resource.SERVANT));
        Recipe r = new Recipe(rq1, rq2);
        DevCard dc = new DevCard(1, f, r, rq3, 10);
        DevCard dc2 = new DevCard(2, f1, r, rq3, 5);
        DevCard dc3 = new DevCard(2, f2, r, rq3, 2);

        board.getDevCardSlot(0).addCard(dc);
        board.getDevCardSlot(0).addCard(dc2);
        board.getDevCardSlot(1).addCard(dc3);

        assertEquals(2, board.countFlags(f, false));
        assertEquals(1, board.countFlags(f1, true));

    }

    @Test
    public void victoryPoints() {
        Board board = new Board();
        DevCardSlot dcs = new DevCardSlot();
        Flag f = new Flag(1, Colour.BLUE);
        Flag f1 = new Flag(2, Colour.PURPLE);
        Flag f2 = new Flag(1, Colour.BLUE);
        ArrayList<ResourceQty> rq1 = new ArrayList<>();
        ArrayList<ResourceQty> rq2 = new ArrayList<>();
        ArrayList<ResourceQty> rq3 = new ArrayList<>();
        rq1.add(new ResourceQty(Resource.SERVANT));
        rq2.add(new ResourceQty(Resource.SERVANT));
        rq3.add(new ResourceQty(Resource.SERVANT));
        Recipe r = new Recipe(rq1, rq2);
        DevCard dc = new DevCard(1, f, r, rq3, 10);
        DevCard dc2 = new DevCard(2, f1, r, rq3, 5);
        DevCard dc3 = new DevCard(2, f2, r, rq3, 2);

        board.getDevCardSlot(0).addCard(dc);
        board.getDevCardSlot(0).addCard(dc2);
        board.getDevCardSlot(1).addCard(dc3);

        for (int i = 0; i < 14; i++) {
            board.getFaithTrack().moveFaithIndicator();
        }
        board.getFaithTrack().setFavourCardsFlag(16);

        ArrayList<Flag> requiredFlags = new ArrayList<Flag>();
        requiredFlags.add(new Flag(Colour.GREEN));
        requiredFlags.add(new Flag(Colour.GREEN));
        requiredFlags.add(new Flag(Colour.PURPLE));
        SpecialAbility specialAbility = new SpecialAbility(1, Resource.COIN);
        LeaderCard leaderCard = new LeaderCard(1, requiredFlags, null, 5, specialAbility);

        SpecialAbility specialAbility1 = new SpecialAbility(1, Resource.COIN);
        LeaderCard leaderCard1 = new LeaderCard(2, requiredFlags, null, 5, specialAbility);

        board.addLeaderCard(leaderCard1);
        board.addLeaderCard(leaderCard);

        leaderCard.setAbilityActivation();

        ArrayList<Resource> update = new ArrayList<Resource>();

        for (int i = 0; i < 3; i++) {
            update.add(Resource.COIN);
        }
        for (int i = 3; i < 5; i++) {
            update.add(Resource.STONE);
        }
        for (int i = 5; i < 9; i++) {
            update.add(Resource.SHIELD);
        }
        update.add(Resource.SERVANT);

        board.getWarehouse().updateConfiguration(update);
        ResourceQty rq = new ResourceQty(Resource.COIN, 3);
        ResourceQty rq4 = new ResourceQty(Resource.STONE, 3);
        board.getStrongbox().addResource(rq);
        board.getStrongbox().addResource(rq4);

        assertEquals(board.victoryPoints(), 42);

    }

    @Test
    public void numberOfDevCard() {
        Board b = new Board();

        Flag f = new Flag(1, Colour.BLUE);
        Flag f1 = new Flag(2, Colour.PURPLE);
        Flag f2 = new Flag(1, Colour.BLUE);
        ArrayList<ResourceQty> rq1 = new ArrayList<>();
        ArrayList<ResourceQty> rq2 = new ArrayList<>();
        ArrayList<ResourceQty> rq3 = new ArrayList<>();
        rq1.add(new ResourceQty(Resource.SERVANT));
        rq2.add(new ResourceQty(Resource.SERVANT));
        rq3.add(new ResourceQty(Resource.SERVANT));
        Recipe r = new Recipe(rq1, rq2);
        DevCard dc = new DevCard(1, f, r, rq3, 10);
        DevCard dc2 = new DevCard(2, f1, r, rq3, 5);
        DevCard dc3 = new DevCard(2, f2, r, rq3, 2);

        b.getDevCardSlot(0).addCard(dc);
        b.getDevCardSlot(0).addCard(dc2);
        b.getDevCardSlot(1).addCard(dc3);

        assertEquals(3, b.numberOfDevCard());
    }

    @Test
    public void testSetAbilityActivationFlag() {
        Board b = new Board();
        assertArrayEquals(b.getAbilityActivationFlag(), new int[]{0, 0, 0, 0, 0, 0, 0, 0});

        b.setAbilityActivationFlag(0, 1);
        b.setAbilityActivationFlag(6, 6);

        assertArrayEquals(b.getAbilityActivationFlag(), new int[]{1, 0, 0, 0, 0, 0, 6, 0});
    }
}