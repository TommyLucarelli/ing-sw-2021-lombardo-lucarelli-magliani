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
        int[] newMarket = {1, 3, 5, 2, 0, 5, 1, 2, 3, 4, 1, 1, 4};
        market.setMarket(newMarket);
        fancyPrinter.printMarket(market);
    }

    @Test
    public void printDevCardSlot() {
        CompactBoard board = new CompactBoard();
        int[][] slots = new int[3][3];
        slots[0][0] = 3;
        slots[0][1] = 3;
        slots[0][2] = 3;
        slots[1][0] = 20;
        slots[1][1] = 20;
        slots[1][2] = 20;
        slots[2][0] = 16;
        slots[2][1] = 16;
        slots[2][2] = 16;
        board.setDevCardSlots(slots);
        FancyPrinter fancyPrinter = new FancyPrinter();
        fancyPrinter.printDevCardSlot(board, false);
    }

    @Test
    public void printDevCardStructure() {
        CompactDevCardStructure devCardStructure = new CompactDevCardStructure();
        int[][] structure = new int[3][4];
        structure[0][0] = 10;
        structure[0][1] = 6;
        structure[0][2] = 0;
        structure[0][3] = 2;
        structure[1][0] = 30;
        structure[1][1] = 27;
        structure[1][2] = 0;
        structure[1][3] = 0;
        structure[2][0] = 26;
        structure[2][1] = 39;
        structure[2][2] = 0;
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
        Resource[] resources = {Resource.COIN,Resource.ANY, Resource.SHIELD, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT, Resource.ANY, Resource.STONE, Resource.ANY};
        board.setWarehouse(resources);
        int[] abilityActivationFlag = {53, 54, 0, 0, 0, 0, 0, 0};
        board.setAbilityActivationFlag(abilityActivationFlag);
        fancyPrinter.printWarehouseV2(board);
    }

    @Test
    public void printStrongbox() {
        CompactBoard board = new CompactBoard();
        int[] strongbox = {2,12,3,1};
        board.setStrongbox(strongbox);
        FancyPrinter fancyPrinter = new FancyPrinter();
        fancyPrinter.printStrongbox(board);
    }

    @Test
    public void printFaithTrack() {
        CompactBoard board = new CompactBoard();
        board.setFaithTrackIndex(14);
        board.getFavCards()[0]=true;
        board.getFavCards()[1]=true;
        board.getFavCards()[2]=true;

        FancyPrinter fancyPrinter = new FancyPrinter();
        fancyPrinter.printFaithTrack(board);
    }

    @Test
    public void printPersonalBoard() {
        CompactBoard board = new CompactBoard();
        FancyPrinter fancyPrinter = new FancyPrinter();
        Resource[] resources = {Resource.COIN,Resource.SHIELD, Resource.SHIELD, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT, Resource.SERVANT, Resource.ANY, Resource.ANY};
        board.setWarehouse(resources);
        int[] strongbox = {20,12,33,17};
        board.setStrongbox(strongbox);
        int[] leaderCards = {52,53};
        int[] flag = {52,0,0,0,0,0,0,0};
        board.setLeaderCards(leaderCards);
        board.setAbilityActivationFlag(flag);
        int[][] slots = new int[3][3];
        slots[0][0] = 3;
        slots[0][1] = 3;
        slots[0][2] = 3;
        slots[1][0] = 20;
        slots[1][1] = 20;
        slots[1][2] = 20;
        slots[2][0] = 16;
        slots[2][1] = 16;
        slots[2][2] = 16;
        board.setDevCardSlots(slots);
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

    @Test
    public void testPrintTiles() {
        FancyPrinter fancyprinter = new FancyPrinter();
        fancyprinter.printTiles();
    }

    @Test
    public void testPrintLeaderCardSlot() {
        FancyPrinter fancyprinter = new FancyPrinter();
        CompactBoard board = new CompactBoard();
        int[] leaderCards = {52,53};
        int[] flag = {52,0,0,0,0,0,0,0};
        board.setLeaderCards(leaderCards);
        board.setAbilityActivationFlag(flag);
        fancyprinter.printLeaderCardSlot(board);
    }
}