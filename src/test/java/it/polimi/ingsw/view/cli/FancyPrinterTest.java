package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.core.model.*;
import it.polimi.ingsw.view.compact.CompactBoard;
import it.polimi.ingsw.view.compact.CompactDevCardStructure;
import it.polimi.ingsw.view.compact.CompactMarket;
import org.junit.Test;

import java.util.ArrayList;

public class FancyPrinterTest {

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
        CompactBoard board = new CompactBoard();
        int[][] slots = new int[3][3];
        slots[0][0] = 3;
        slots[0][1] = 9;
        slots[0][2] = 36;
        slots[1][0] = 20;
        slots[1][1] = 27;
        slots[1][2] = 32;
        slots[2][0] = 16;
        slots[2][1] = 39;
        slots[2][2] = 47;
        board.setDevCardSlots(slots);
        FancyPrinter fancyPrinter = new FancyPrinter();
        fancyPrinter.printDevCardSlot(board);
    }

    @Test
    public void printDevCardStructure() {
        CompactDevCardStructure devCardStructure = new CompactDevCardStructure();
        int[][] structure = new int[3][4];
        structure[0][0] = 10;
        structure[0][1] = 6;
        structure[0][2] = 30;
        structure[0][3] = 2;
        structure[1][0] = 30;
        structure[1][1] = 27;
        structure[1][2] = 32;
        structure[1][3] = 33;
        structure[2][0] = 26;
        structure[2][1] = 39;
        structure[2][2] = 47;
        structure[2][3] = 4;
        devCardStructure.setDevCardStructure(structure);
        FancyPrinter fancyPrinter = new FancyPrinter();
        fancyPrinter.printDevCardStructure(devCardStructure);
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
    public void printWarehouseV2() {
        FancyPrinter fancyPrinter = new FancyPrinter();
        CompactBoard board = new CompactBoard();
        Resource[] resources = {Resource.COIN,Resource.SHIELD, Resource.SHIELD, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT, Resource.SERVANT, Resource.ANY, Resource.ANY};
        board.setWarehouse(resources);
        fancyPrinter.printWarehouseV2(board);
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
        FancyPrinter fancyPrinter =new FancyPrinter();
        fancyPrinter.printDevCard(48);
    }

    @Test
    public void printLeaderCard() {
        FancyPrinter fancyprinter = new FancyPrinter();
        fancyprinter.printLeaderCard(64);
    }

    @Test
    public void printArrayLeaderCard() {
        int[] array = {49,56,64,52};
        FancyPrinter fancyprinter = new FancyPrinter();
        fancyprinter.printArrayLeaderCard(array);
    }
}