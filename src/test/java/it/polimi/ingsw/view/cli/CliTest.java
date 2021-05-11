package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.core.model.*;
import it.polimi.ingsw.view.compact.CardCollector;
import it.polimi.ingsw.view.compact.CompactBoard;
import it.polimi.ingsw.view.compact.CompactMarket;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CliTest {

    @Test
    public void printMarket() {
        FancyPrinter fancyPrinter = new FancyPrinter();
        CompactMarket market = new CompactMarket();
        int[] newMarket = {1, 3, 5, 2, 6, 5, 1, 2, 3, 4, 1, 1, 4};
        market.setMarket(newMarket);
        fancyPrinter.printMarket(market);
    }

    @Test
    public void printDevCardSlot() {
        ArrayList<ResourceQty> cost = new ArrayList<ResourceQty>();
        cost.add(new ResourceQty(Resource.COIN, 5));
        cost.add(new ResourceQty(Resource.STONE, 2));
        ArrayList<ResourceQty> endRes = new ArrayList<ResourceQty>();
        ArrayList<ResourceQty> startRes = new ArrayList<ResourceQty>();
        startRes.add(new ResourceQty(Resource.COIN, 1));
        startRes.add(new ResourceQty(Resource.SHIELD, 1));
        endRes.add(new ResourceQty(Resource.SERVANT, 2));
        endRes.add(new ResourceQty(Resource.STONE, 2));
        endRes.add(new ResourceQty(Resource.FAITH, 1));
        Recipe recipe = new Recipe(startRes, endRes);
        DevCard devCard = new DevCard(39, new Flag(1, Colour.GREEN), recipe, cost, 10);

        ArrayList<ResourceQty> cost1 = new ArrayList<ResourceQty>();
        cost.add(new ResourceQty(Resource.COIN, 5));
        cost.add(new ResourceQty(Resource.STONE, 2));
        ArrayList<ResourceQty> endRes1 = new ArrayList<ResourceQty>();
        ArrayList<ResourceQty> startRes1 = new ArrayList<ResourceQty>();
        startRes.add(new ResourceQty(Resource.COIN, 1));
        startRes.add(new ResourceQty(Resource.SHIELD, 1));
        endRes.add(new ResourceQty(Resource.SERVANT, 2));
        endRes.add(new ResourceQty(Resource.STONE, 2));
        endRes.add(new ResourceQty(Resource.FAITH, 1));
        Recipe recipe1 = new Recipe(startRes1, endRes1);
        DevCard devCard1 = new DevCard(39, new Flag(2, Colour.YELLOW), recipe1, cost1, 8);

        ArrayList<ResourceQty> cost2 = new ArrayList<ResourceQty>();
        cost.add(new ResourceQty(Resource.SHIELD, 5));
        cost.add(new ResourceQty(Resource.STONE, 2));
        ArrayList<ResourceQty> endRes2 = new ArrayList<ResourceQty>();
        ArrayList<ResourceQty> startRes2 = new ArrayList<ResourceQty>();
        startRes.add(new ResourceQty(Resource.COIN, 1));
        startRes.add(new ResourceQty(Resource.STONE, 1));
        endRes.add(new ResourceQty(Resource.SERVANT, 2));
        endRes.add(new ResourceQty(Resource.STONE, 2));
        endRes.add(new ResourceQty(Resource.FAITH, 1));
        Recipe recipe2 = new Recipe(startRes2, endRes2);
        DevCard devCard2 = new DevCard(11, new Flag(3, Colour.BLUE), recipe2, cost2, 5);
    }

    @Test
    public void printDevCardStructure() {
    }

    @Test
    public void printWarehouse() {
        FancyPrinter fancyPrinter = new FancyPrinter();
        CompactBoard board = new CompactBoard();
        Resource[] resources = {Resource.COIN,Resource.SHIELD, Resource.SHIELD, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT, Resource.SERVANT, Resource.ANY, Resource.ANY};
        board.setWarehouse(resources);
        fancyPrinter.printWarehouse(board);
    }

    @Test
    public void printStrongbox() {
        CompactBoard board = new CompactBoard();
        int[] strongbox = {0,1,3,1};
        board.setStrongbox(strongbox);
        FancyPrinter fancyPrinter = new FancyPrinter();
        fancyPrinter.printStrongbox(board);
    }

    @Test
    public void printFaithTrack() {
        CompactBoard board = new CompactBoard();
        board.setFaithTrackIndex(10);
        FancyPrinter fancyPrinter = new FancyPrinter();
        fancyPrinter.printFaithTrack(board);
    }

    @Test
    public void printPersonalBoard() {
        CompactBoard board = new CompactBoard();
        FancyPrinter fancyPrinter = new FancyPrinter();
        Resource[] resources = {Resource.COIN,Resource.SHIELD, Resource.SHIELD, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT, Resource.SERVANT, Resource.ANY, Resource.ANY};
        board.setWarehouse(resources);
        int[] strongbox = {0,1,3,1};
        board.setStrongbox(strongbox);
        fancyPrinter.printPersonalBoard(board);
    }

    @Test
    public void printDevCard() {
        ArrayList<ResourceQty> cost = new ArrayList<ResourceQty>();
        cost.add(new ResourceQty(Resource.COIN, 5));
        cost.add(new ResourceQty(Resource.SERVANT, 2));
        ArrayList<ResourceQty> endRes = new ArrayList<ResourceQty>();
        ArrayList<ResourceQty> startRes = new ArrayList<ResourceQty>();
        startRes.add(new ResourceQty(Resource.COIN, 1));


        endRes.add(new ResourceQty(Resource.SERVANT, 2));
        endRes.add(new ResourceQty(Resource.STONE, 2));
        endRes.add(new ResourceQty(Resource.FAITH, 1));
        Recipe recipe = new Recipe(startRes, endRes);
        DevCard devCard = new DevCard(39, new Flag(1, Colour.GREEN), recipe, cost, 10);

        FancyPrinter fancyPrinter =new FancyPrinter();
        fancyPrinter.printDevCard(devCard);

    }

    @Test
    public void printLeaderCard() {
        FancyPrinter fancyprinter = new FancyPrinter();
        fancyprinter.printLeaderCard(64);
    }
}