package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.core.model.DevCard;
import it.polimi.ingsw.core.model.LeaderCard;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.view.compact.*;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class used to print the elements of the game in the CLI.
 */
public class FancyPrinter {
    private final PrintStream stream;
    private final Scanner scanner;
    CardCollector cardCollector;
    ArrayList<String> strDevCard = new ArrayList<>();

    public FancyPrinter(){
        try {
           cardCollector = new CardCollector();
        }catch(FileNotFoundException e){
            System.out.println("\nFile not found");
        }
        stream = new PrintStream(System.out, true);
        scanner = new Scanner(System.in);
    }

    /**
     * Prints the market.
     * @param market the market saved in the client view.
     */
    public void printMarket(CompactMarket market) {
        StringBuilder string = new StringBuilder();
        string.append("\t\t").append(1).append(" \t\t").append(2).append("\t\t").append(3).append("\t\t").append(4).append("\n");
        string.append("\t -------------------------------\n").append(1).append("\t|");

        for (int i = 0; i < 13; i++) {
            switch (market.getMarket()[i]) {
                case 0:
                    string.append("\t").append(Color.YELLOW_BOLD.color()).append("●\t");
                    break;
                case 1:
                    string.append("\t").append(Color.WHITE_BOLD.color()).append("●\t");
                    break;
                case 2:
                    string.append("\t").append(Color.BLUE_BOLD.color()).append("●\t");
                    break;
                case 3:
                    string.append("\t").append(Color.PURPLE_BOLD.color()).append("●\t");
                    break;
                case 4:
                    string.append("\t").append(Color.RED_BOLD.color()).append("●\t");
                    break;
                case 5:
                    string.append("\t").append(Color.RESET).append("●\t");
                    break;
        }

            if (i == 3) {
                string.append(Color.RESET).append("|\n").append(2).append("\t|");
            } else if (i == 7) {
                string.append(Color.RESET).append("|\n").append(3).append("\t|");
            } else if (i == 11) {
                string.append(Color.RESET).append("|\t");
            }
        }

        string.append(Color.RESET).append("\n\t -------------------------------");
        stream.print(string);
    }

    /**
     * @param id of a devCard
     * @return string of Leader Card
     */
    public ArrayList<StringBuilder> devCardToArrayList(int id){
        StringBuilder string1 = new StringBuilder();
        StringBuilder string2 = new StringBuilder();
        StringBuilder string3 = new StringBuilder();
        StringBuilder string4 = new StringBuilder();
        StringBuilder string5 = new StringBuilder();
        StringBuilder string6 = new StringBuilder();
        StringBuilder string7 = new StringBuilder();
        StringBuilder string8 = new StringBuilder();
        StringBuilder string9 = new StringBuilder();
        ArrayList<StringBuilder> stringDevCard = new ArrayList<>();
        DevCard devCard1;
        if(id==0){
            string1.append("\t┌───────────────────┐");
            string2.append("\t│\t\t\t\t\t│");
            string3.append("\t│\t\t\t\t\t│");
            string4.append("\t│\t\t\t\t\t│");
            string5.append("\t│\t\t\t\t\t│");
            string6.append("\t│\t\t\t\t\t│");
            string7.append("\t│\t\t\t\t\t│");
            string8.append("\t│\t\t\t\t\t│");
            string9.append("\t└───────────────────┘");
        } else {
            devCard1 = cardCollector.getDevCard(id);

            string1.append("\t┌───────────────────┐");
            string2.append("\t│  ");
            switch (devCard1.getFlag().getColour()) {
                case YELLOW:
                    string2.append(Color.YELLOW_BOLD.color()).append("▓").append(Color.RESET);
                    break;
                case BLUE:
                    string2.append(Color.HEAVENLY_BOLD.color()).append("▓").append(Color.RESET);
                    break;
                case GREEN:
                    string2.append(Color.GREEN_BOLD.color()).append("▓").append(Color.RESET);
                    break;
                case PURPLE:
                    string2.append(Color.PURPLE_BOLD.color()).append("▓").append(Color.RESET);
                    break;
            }

            switch (devCard1.getCost().size()) {
                case 1:
                    string2.append("  ");
                case 2:
                    string2.append("  ");
                case 3:
                    string2.append(" ");
            }

            for (int i = 0; i < devCard1.getCost().size(); i++) {
                int qty = devCard1.getCost().get(i).getQty();
                switch (devCard1.getCost().get(i).getResource()) {
                    case COIN:
                        string2.append(Color.YELLOW_BOLD.color()).append(qty).append(" $").append(Color.RESET);
                        break;
                    case SERVANT:
                        string2.append(Color.PURPLE_BOLD.color()).append(qty).append(" ■").append(Color.RESET);
                        break;
                    case SHIELD:
                        string2.append(Color.HEAVENLY_BOLD.color()).append(qty).append(" ◊").append(Color.RESET);
                        break;
                    case STONE:
                        string2.append(Color.WHITE_BOLD.color()).append(qty).append(" ⌂").append(Color.RESET);
                        break;
                }
                switch (devCard1.getCost().size()) {
                    case 1:
                        string2.append("  ");
                    case 2:
                        string2.append(" ");
                    case 3:
                        string2.append(" ");
                }
            }


            switch (devCard1.getFlag().getColour()) {
                case YELLOW:
                    string2.append(Color.YELLOW_BOLD.color()).append("▓\t").append(Color.RESET).append("│");
                    break;
                case BLUE:
                    string2.append(Color.HEAVENLY_BOLD.color()).append("▓\t").append(Color.RESET).append("│");
                    break;
                case GREEN:
                    string2.append(Color.GREEN_BOLD.color()).append("▓\t").append(Color.RESET).append("│");
                    break;
                case PURPLE:
                    string2.append(Color.PURPLE_BOLD.color()).append("▓\t").append(Color.RESET).append("│");
                    break;
            }

            string3.append("\t│\t\t\t\t\t│");
            string4.append("\t│\t");

            for (int i = 0; i < devCard1.getRecipe().getInputResources().size(); i++) {
                int qtyIn = devCard1.getRecipe().getInputResources().get(i).getQty();
                switch (devCard1.getRecipe().getInputResources().get(i).getResource()) {
                    case COIN:
                        string4.append(Color.YELLOW_BOLD.color()).append(qtyIn).append(" $").append(Color.RESET);
                        break;
                    case SERVANT:
                        string4.append(Color.PURPLE_BOLD.color()).append(qtyIn).append(" ■").append(Color.RESET);
                        break;
                    case SHIELD:
                        string4.append(Color.HEAVENLY_BOLD.color()).append(qtyIn).append(" ◊").append(Color.RESET);
                        break;
                    case STONE:
                        string4.append(Color.WHITE_BOLD.color()).append(qtyIn).append(" ⌂").append(Color.RESET);
                        break;
                }
                switch (devCard1.getRecipe().getInputResources().size()) {
                    case 1:
                        string4.append("\t");
                    case 2:
                        string4.append("  ");
                    case 3:
                        string4.append(" ");
                }
            }

            switch (devCard1.getRecipe().getInputResources().size()) {
                case 1:
                    string4.append("\t\t\t│");
                    break;
                case 2:
                    string4.append("\t│");
                    break;
                case 3:
                    string4.append(" │");
                    break;
            }

            if (devCard1.getRecipe().getOutputResources().size() != 3) {
                string5.append("\t│\t─►\t");
            } else {
                string5.append("\t│ ─► ");
            }

            for (int i = 0; i < devCard1.getRecipe().getOutputResources().size(); i++) {
                int qty = devCard1.getRecipe().getOutputResources().get(i).getQty();
                switch (devCard1.getRecipe().getOutputResources().get(i).getResource()) {
                    case COIN:
                        string5.append(Color.YELLOW_BOLD.color()).append(qty).append(" $").append(Color.RESET);
                        break;
                    case SERVANT:
                        string5.append(Color.PURPLE_BOLD.color()).append(qty).append(" ■").append(Color.RESET);
                        break;
                    case SHIELD:
                        string5.append(Color.HEAVENLY_BOLD.color()).append(qty).append(" ◊").append(Color.RESET);
                        break;
                    case STONE:
                        string5.append(Color.WHITE_BOLD.color()).append(qty).append(" ⌂").append(Color.RESET);
                        break;
                    case FAITH:
                        string5.append(Color.RED_BOLD.color()).append(qty).append(" †").append(Color.RESET);
                }
                switch (devCard1.getRecipe().getOutputResources().size()) {
                    case 1:
                        string5.append("\t");
                        break;
                    case 2:
                        string5.append("  ");
                        break;
                    case 3:
                        string5.append(" ");
                        break;
                }
            }
            switch (devCard1.getRecipe().getOutputResources().size()) {
                case 1:
                    string5.append("\t");
                case 2:
                    string5.append("");
                case 3:
                    string5.append(" ");
            }
            string5.append("\t│");
            string6.append("\t│\t\t\t\t\t│");
            string7.append("\t│\t\t\t\t\t│");
            string8.append("\t│ VictoryPoints:").append(devCard1.getVictoryPoints()).append("\t│");
            string9.append("\t└───────────────────┘");

        }

        stringDevCard.add(string1);
        stringDevCard.add(string2);
        stringDevCard.add(string3);
        stringDevCard.add(string4);
        stringDevCard.add(string5);
        stringDevCard.add(string6);
        stringDevCard.add(string7);
        stringDevCard.add(string8);
        stringDevCard.add(string9);

        return stringDevCard;
    }

    /**
     * Prints a development card.
     * @param id of the development card.
     * @return
     */
    public void printDevCard(int id) {
        for (int i = 0; i < 9; i++) {
            stream.print(devCardToArrayList(id).get(i)+"\n");
        }
    }

    public ArrayList<StringBuilder> printBase(int id) {
        StringBuilder base1 = new StringBuilder();
        StringBuilder base2 = new StringBuilder();
        CardCollector cardCollector = null;
        try {
            cardCollector = new CardCollector();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        base1.append("\t│  ");
        switch (cardCollector.getDevCard(id).getFlag().getColour()) {
               case YELLOW:
                   base1.append(Color.YELLOW_BOLD.color()).append("▓").append(Color.RESET);
                   break;
               case BLUE:
                   base1.append(Color.HEAVENLY_BOLD.color()).append("▓").append(Color.RESET);
                   break;
               case GREEN:
                   base1.append(Color.GREEN_BOLD.color()).append("▓").append(Color.RESET);
                   break;
               case PURPLE:
                   base1.append(Color.PURPLE_BOLD.color()).append("▓").append(Color.RESET);
                   break;
                }
        base1.append("\t VP:").append(cardCollector.getDevCard(id).getVictoryPoints());
        switch (cardCollector.getDevCard(id).getFlag().getColour()) {
               case YELLOW:
                    base1.append(Color.YELLOW_BOLD.color()).append("\t ▓").append(Color.RESET);
                    break;
               case BLUE:
                    base1.append(Color.HEAVENLY_BOLD.color()).append("\t ▓").append(Color.RESET);
                    break;
               case GREEN:
                    base1.append(Color.GREEN_BOLD.color()).append("\t ▓").append(Color.RESET);
                    break;
               case PURPLE:
                    base1.append(Color.PURPLE_BOLD.color()).append("\t ▓").append(Color.RESET);
                    break;
        }
        base1.append("  │");
        base2.append("\t└───────────────────┘");
        ArrayList<StringBuilder> base = new ArrayList<>();
        base.add(base1);
        base.add(base2);
        return base;
    }

    /**
     * Prints a development card slot from the player's board.
     * @param board the player's board.
     */
    public void printDevCardSlot(CompactBoard board, boolean production){
        CardCollector cardCollector = null;
        try {
            cardCollector = new CardCollector();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder devCardSlot = new StringBuilder();
        if(production){
            devCardSlot.append("   \t\t\t2\t\t\t\t\t\t\t3\t\t\t\t\t\t\t4\n");
        }
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 3; i++) {
                devCardSlot.append(devCardToArrayList(board.getDevCardSlots()[0][i]).get(j)).append("\t");
            }
            devCardSlot.append("\n");
        }
        for (int k = 1; k < 3; k++) {
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 3; i++) {
                    devCardSlot.append(printBase(board.getDevCardSlots()[k][i]).get(j)).append("\t");
                }
                devCardSlot.append("\n");
            }
        }

        int[] sum =  {0,0,0};
        /*for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum[i] = sum[i] + cardCollector.getDevCard(board.getDevCardSlots()[i][j]).getVictoryPoints();
            }
        }
        devCardSlot.append("\t\tTotal VP:").append(sum[0]).append("\t\t\t\t\tTotal VP:").append(sum[1]).append("\t\t\t\t\tTotal VP:").append(sum[2]);
        */
        stream.print(devCardSlot);
    }

    /**
     * Prints the development card structure.
     */
    public void printDevCardStructure(CompactDevCardStructure devCardStructure) {
        CardCollector cardCollector = null;
        try {
            cardCollector = new CardCollector();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder strDevCardStructure = new StringBuilder();
        int count =3;
        strDevCardStructure.append("\t\t\t\t\tGreen\t\t\t\t\t\tBlue\t\t\t\t\tYellow\t\t\t\t\t\tPurple");

        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < 9; i++) {
                if (i != 4) {
                    strDevCardStructure.append("\n\t\t");
                } else {
                    strDevCardStructure.append("\n\tLevel ").append(count);
                }
                for (int j = 0; j < 4; j++) {
                    strDevCardStructure.append(devCardToArrayList(devCardStructure.getDevCardStructure()[k][j]).get(i)).append("\t");
                }
            }
            count--;
            strDevCardStructure.append("\n");
        }
        stream.print(strDevCardStructure);
    }

    /**
     * @return the LeaderCard in ArrayList of StringBuilder
     * @param id is the identification of a Leader Card
     */
    public ArrayList<StringBuilder> leaderCardToArrayList(int id){
        StringBuilder string1 = new StringBuilder();
        StringBuilder string2 = new StringBuilder();
        StringBuilder string3 = new StringBuilder();
        StringBuilder string4 = new StringBuilder();
        StringBuilder string5 = new StringBuilder();
        StringBuilder string6 = new StringBuilder();
        StringBuilder string7 = new StringBuilder();
        StringBuilder string8 = new StringBuilder();
        StringBuilder string9 = new StringBuilder();
        StringBuilder string10 = new StringBuilder();
        ArrayList<StringBuilder> stringLeaderCard = new ArrayList<>();
        LeaderCard leaderCard;
        if(id==0){
            string1.append("\t┌───────────────────┐");
            string2.append("\t│\t\t\t\t\t│");
            string3.append("\t│\t\t\t\t\t│");
            string4.append("\t│\t\t\t\t\t│");
            string5.append("\t│\t\t\t\t\t│");
            string6.append("\t│\t\t\t\t\t│");
            string7.append("\t│\t\t\t\t\t│");
            string8.append("\t│\t\t\t\t\t│");
            string9.append("\t│\t\t\t\t\t│");
            string10.append("\t└───────────────────┘");
        } else {
            leaderCard = cardCollector.getLeaderCard(id);
            int[] num = new int[4];

            string1.append("\t┌───────────────────┐");
            string2.append("\t│  ");
            if (leaderCard.getSpecialAbility().getAbilityType() == 0) {
                switch (leaderCard.getRequiredResources().getResource()) {
                    case COIN:
                        string2.append(Color.YELLOW_BOLD.color()).append(5).append(" $").append(Color.RESET).append("\t\t\t\t│");
                        break;
                    case SERVANT:
                        string2.append(Color.PURPLE_BOLD.color()).append(5).append(" ■").append(Color.RESET).append("\t\t\t\t│");
                        break;
                    case SHIELD:
                        string2.append(Color.HEAVENLY_BOLD.color()).append(5).append(" ◊").append(Color.RESET).append("\t\t\t\t│");
                        break;
                    case STONE:
                        string2.append(Color.WHITE_BOLD.color()).append(5).append(" ⌂").append(Color.RESET).append("\t\t\t\t│");
                        break;
                }
            } else {

                for (int i = 0; i < leaderCard.getRequiredFlags().size(); i++) {
                    num[leaderCard.getRequiredFlags().get(i).getColour().ordinal()]++;
                }

                if (num[0] != 0)
                    string2.append(num[0]).append(Color.GREEN_BOLD.color()).append(" ▓  ").append(Color.RESET);
                if (num[1] != 0)
                    string2.append(num[1]).append(Color.YELLOW_BOLD.color()).append(" ▓  ").append(Color.RESET);
                if (num[2] != 0)
                    string2.append(num[2]).append(Color.PURPLE_BOLD.color()).append(" ▓  ").append(Color.RESET);
                if (num[3] != 0)
                    string2.append(num[3]).append(Color.HEAVENLY_BOLD.color()).append(" ▓  ").append(Color.RESET);

                int cont = 0;
                for (int i = 0; i < 4; i++) {
                    if (num[i] != 0) cont++;
                }
                if (cont == 2) {
                    string2.append("\t\t│");
                } else {
                    string2.append("\t\t\t│");
                }
            }

            string3.append("\t│\t\t\t\t\t│");
            string4.append("\t│\t\t\t\t\t│");
            string5.append("\t│ VictoryPoints:").append(leaderCard.getVictoryPoints()).append("\t│");
            string6.append("\t│\t\t\t\t\t│");
            string7.append("\t│ SpecialAbility:\t│");
            string8.append("\t│ ");

            switch (leaderCard.getSpecialAbility().getAbilityType()) {
                case 0:
                    string8.append("Special Warehouse │");
                    string9.append("\t│\t +2");
                    break;
                case 1:
                    string8.append("Special Marble    │");
                    string9.append("\t│\t● =");
                    break;
                case 2:
                    string8.append("Special Discount  │");
                    string9.append("\t│\t -1 ");
                    break;
                case 3:
                    string8.append("Special Production│");
                    string9.append("\t│\t");
                    break;
            }

            if (leaderCard.getSpecialAbility().getAbilityType() != 3) {
                switch (leaderCard.getSpecialAbility().getAbilityResource()) {
                    case COIN:
                        string9.append(Color.YELLOW_BOLD.color()).append(" $").append(Color.RESET);
                        break;
                    case SERVANT:
                        string9.append(Color.PURPLE_BOLD.color()).append(" ■").append(Color.RESET);
                        break;
                    case SHIELD:
                        string9.append(Color.HEAVENLY_BOLD.color()).append(" ◊").append(Color.RESET);
                        break;
                    case STONE:
                        string9.append(Color.WHITE_BOLD.color()).append(" ⌂").append(Color.RESET);
                        break;
                }
                string9.append("\t\t\t│");
                string10.append("\t└───────────────────┘");
            } else {
                switch (leaderCard.getSpecialAbility().getAbilityResource()) {
                    case COIN:
                        string9.append(Color.YELLOW_BOLD.color()).append(1).append(" $").append(Color.RESET);
                        break;
                    case SERVANT:
                        string9.append(Color.PURPLE_BOLD.color()).append(1).append(" ■").append(Color.RESET);
                        break;
                    case SHIELD:
                        string9.append(Color.HEAVENLY_BOLD.color()).append(1).append(" ◊").append(Color.RESET);
                        break;
                    case STONE:
                        string9.append(Color.WHITE_BOLD.color()).append(1).append(" ⌂").append(Color.RESET);
                        break;
                }
                string9.append(" ─► 1 ").append(Color.WHITE_BOLD.color()).append("? ").append(Color.RESET).append(1).append(Color.RED_BOLD.color()).append(" †").append(Color.RESET).append("  │");
                string10.append("\t└───────────────────┘");
            }
            stringLeaderCard.add(string1);
            stringLeaderCard.add(string2);
            stringLeaderCard.add(string3);
            stringLeaderCard.add(string4);
            stringLeaderCard.add(string5);
            stringLeaderCard.add(string6);
            stringLeaderCard.add(string7);
            stringLeaderCard.add(string8);
            stringLeaderCard.add(string9);
            stringLeaderCard.add(string10);
        }

        return stringLeaderCard;
    }

    /**
     * Prints a LeaderCard
     * @param id of the LeaderCard
     */
    public void printLeaderCard(int id){
        for (int i = 0; i < 10; i++) {
            stream.print(leaderCardToArrayList(id).get(i)+"\n");
        }
    }

    /**
     * Prints all the id of the LeaderCards in the Array side by side
     * @param leaderCardArray contains all the id of the LeaderCards
     */
    public void printArrayLeaderCard(int[] leaderCardArray){
        StringBuilder leaderCardSlot = new StringBuilder();
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < leaderCardArray.length; i++) {
                leaderCardSlot.append(leaderCardToArrayList(leaderCardArray[i]).get(j)).append("\t");
            }
            leaderCardSlot.append("\n");
        }
        stream.print(leaderCardSlot);
    }

    /**
     * Improved version of printWarehouse.
     * @param board the player's board.
     */
    public void printWarehouseV2(CompactBoard board){
        StringBuilder string = new StringBuilder();
        string.append("        WAREHOUSE\n").append("         ┌─────┐\n").append("         |  ");
        symbolForWarehouse(string, board.getWarehouse()[0], 1);
        string.append("  |\n").append("         └─────┘\n").append("      ┌───────────┐\n").append("      |  ");
        symbolForWarehouse(string, board.getWarehouse()[1], 2);
        string.append("     ");
        symbolForWarehouse(string, board.getWarehouse()[2], 3);
        string.append("  |\n").append("      └───────────┘\n").append("    ┌───────────────┐\n").append("    |  ");
        symbolForWarehouse(string, board.getWarehouse()[3], 4);
        string.append("    ");
        symbolForWarehouse(string, board.getWarehouse()[4], 5);
        string.append("    ");
        symbolForWarehouse(string, board.getWarehouse()[5], 6);
        string.append("  |\n").append("    └───────────────┘\n");
        if(board.getAbilityActivationFlag()[0] != 0){
            string.append("      ┌───────────┐\n").append("      |  ");
            symbolForWarehouse(string, board.getWarehouse()[6], 7);
            string.append("     ");
            symbolForWarehouse(string, board.getWarehouse()[7], 8);
            string.append("  | ");
            switch(cardCollector.getLeaderCard(board.getAbilityActivationFlag()[0]).getSpecialAbility().getAbilityResource()){
                case STONE:
                    string.append(Color.WHITE_BOLD.color()).append("⌂");
                    break;
                case SERVANT:
                    string.append(Color.PURPLE_BOLD.color()).append("■");
                    break;
                case COIN:
                    string.append(Color.YELLOW_BOLD.color()).append("$");
                    break;
                case SHIELD:
                    string.append(Color.BLUE_BOLD.color()).append("◊");
                    break;
            }
            string.append(" ONLY\n").append(Color.RESET).append("      └───────────┘\n");
            if(board.getAbilityActivationFlag()[1] != 0){
                string.append("      ┌───────────┐\n").append("      |  ");
                symbolForWarehouse(string, board.getWarehouse()[8], 9);
                string.append("     ");
                switch(board.getWarehouse()[9]){
                    case STONE:
                        string.append(Color.WHITE_BOLD.color()).append("⌂ ");
                        break;
                    case SERVANT:
                        string.append(Color.PURPLE_BOLD.color()).append("■ ");
                        break;
                    case COIN:
                        string.append(Color.YELLOW_BOLD.color()).append("$ ");
                        break;
                    case SHIELD:
                        string.append(Color.BLUE_BOLD.color()).append("◊ ");
                        break;
                    case ANY:
                        string.append("10");
                        break;
                }
                string.append(" | ");
                switch(cardCollector.getLeaderCard(board.getAbilityActivationFlag()[1]).getSpecialAbility().getAbilityResource()){
                    case STONE:
                        string.append(Color.WHITE_BOLD.color()).append("⌂");
                        break;
                    case SERVANT:
                        string.append(Color.PURPLE_BOLD.color()).append("■");
                        break;
                    case COIN:
                        string.append(Color.YELLOW_BOLD.color()).append("$");
                        break;
                    case SHIELD:
                        string.append(Color.BLUE_BOLD.color()).append("◊");
                        break;
                }
                string.append(" ONLY\n").append(Color.RESET).append("      └───────────┘\n");
            }

        } else if(board.getAbilityActivationFlag()[1] != 0) {
            string.append("      ┌───────────┐\n").append("      |  ");
            symbolForWarehouse(string, board.getWarehouse()[6], 7);
            string.append("     ");
            symbolForWarehouse(string, board.getWarehouse()[7], 8);
            string.append(" | ");
            switch(cardCollector.getLeaderCard(board.getAbilityActivationFlag()[1]).getSpecialAbility().getAbilityResource()){
                case STONE:
                    string.append(Color.WHITE_BOLD.color()).append("⌂");
                    break;
                case SERVANT:
                    string.append(Color.PURPLE_BOLD.color()).append("■");
                    break;
                case COIN:
                    string.append(Color.YELLOW_BOLD.color()).append("$");
                    break;
                case SHIELD:
                    string.append(Color.BLUE_BOLD.color()).append("◊");
                    break;
            }
            string.append(" ONLY\n").append(Color.RESET).append("      └───────────┘\n");
        }

        stream.print(string);
    }

    /**
     * Helper method for printWarehouseV2
     * @param string the stringbuilder
     * @param resource the warehouse's resource.
     * @param any the symbol to print in case the resource is ANY.
     */
    private void symbolForWarehouse(StringBuilder string, Resource resource, int any){
        switch(resource){
            case STONE:
                string.append(Color.WHITE_BOLD.color()).append("⌂");
                break;
            case SERVANT:
                string.append(Color.PURPLE_BOLD.color()).append("■");
                break;
            case COIN:
                string.append(Color.YELLOW_BOLD.color()).append("$");
                break;
            case SHIELD:
                string.append(Color.BLUE_BOLD.color()).append("◊");
                break;
            case ANY:
                string.append(any);
                break;
        }
        string.append(Color.RESET);
    }

    /**
     * Prints the warehouse and the resources stored in it.
     * @param board the player's board.
     * @deprecated
     */
    public void printWarehouse(CompactBoard board){
        StringBuilder string = new StringBuilder();

        string.append("\n").append(Color.GREEN_BOLD.color()).append("\t\t\t\t\tWAREHOUSE\n");
        string.append("\t\t\t\t");

        for (int i = 0; i < 10; i++) {
                switch (board.getWarehouse()[i]) {
                    case STONE:
                        string.append("\t").append(Color.WHITE_BOLD.color()).append(" STONE");
                        break;
                    case SERVANT:
                        string.append("\t").append(Color.PURPLE_BOLD.color()).append("SERVANT");
                        break;
                    case COIN:
                        string.append("\t").append(Color.YELLOW_BOLD.color()).append(" COIN");
                        break;
                    case SHIELD:
                        string.append("\t").append(Color.BLUE_BOLD.color()).append("SHIELD");
                        break;
                    case ANY:
                        string.append("");
                }


            if(i==0){
                string.append("\n\t\t\t");
            } else if(i==2){
                string.append("\n\t\t");
            } else if(i==5){
                string.append(Color.RESET).append("\n\t----------------------------------------\n\t");
            }
        }
        string.append("\n");
        stream.print(string);
    }

    /**
     * Prints the player's strongbox and its resources.
     * @param board the player's board.
     */
    public void printStrongbox(CompactBoard board){

        StringBuilder string = new StringBuilder();
        string.append("\n" + Color.GREEN_BOLD.color() + "\t\t\t\t\tSTRONGBOX\n");
        string.append(Color.RESET + "\t-----------------------------------------\n\t|");

        for (int i = 0; i < 4; i++) {
            if(board.getStrongbox()[i]==0){
                string.append("\t".repeat(10)+"|");
            }
            for (int j = 0; j < board.getStrongbox()[i]; j++) {
                switch(i){
                    case 0:
                        string.append("\t" + Color.WHITE_BOLD.color() + "STONE");
                        break;
                    case 1:
                        string.append("\t" + Color.YELLOW_BOLD.color() + "COIN");
                        break;
                    case 2:
                        string.append("\t" + Color.BLUE_BOLD.color() + "SHIELD");
                        break;
                    case 3:
                        string.append("\t" + Color.PURPLE_BOLD.color() + "SERVANT");
                        break;
                    }
                    if(j==board.getStrongbox()[i]-1){
                        string.append("\t\t".repeat(5-board.getStrongbox()[i])+ Color.RESET+ "|");
                    }
            }
            if(i<3) {
                string.append(Color.RESET).append("\n\t|");
            }
        }
        string.append(Color.RESET).append("\n\t-----------------------------------------");
        stream.print(string);
    }

    /**
     * Prints the player's faith track.
     * @param board the player's board.
     */
    public void printFaithTrack(CompactBoard board){
        StringBuilder string = new StringBuilder();

        string.append(Color.YELLOW_BOLD.color()).append("{●} = Victory Points Space\t").append(Color.RESET).append(Color.LIME.color()).append("{†} = Faith Marker\n").append(Color.RESET).append(Color.HEAVENLY_BOLD.color()).append("{☼} = Pope's Favor tiles\t").append(Color.RESET).append(Color.RED_BOLD.color()).append("{♣} = Pope Space\t").append(Color.RESET).append(Color.PURPLE_BOLD.color()).append("─── = Vatican Report section\n\n").append(Color.RESET);

        string.append(Color.PURPLE_BOLD.color()).append("\t\t\t\t\t┌").append("─".repeat(17)).append("┐").append("\t\t\t   ┌").append("───────────────────────").append("┐").append("\t\t  ┌─").append("──────────────────────────────").append("┐").append("\n").append(Color.RESET);

        for (int i = 0; i < 25; i++) {
            if(i==5 || i==9 || i==12 || i==17 || i==19){
                string.append(Color.PURPLE_BOLD.color()).append("| ").append(Color.RESET);
            }

            if(board.getFaithTrackIndex()==i){
                string.append(Color.LIME.color()).append("† ").append(Color.RESET);
            } else if(i%3==0 && i!=0 && i!=24) {
                string.append(Color.YELLOW_BOLD.color()).append("{●} ").append(Color.RESET);
            } else if(i%8==0 && i!=0 && i!=24) {
                string.append(Color.RED_BOLD.color()).append("{♣} ").append(Color.RESET);
            } else if(i!=24) {
                string.append("{").append(i).append("} ").append(Color.RESET);
            }

            if(i==24){
                string.append(Color.YELLOW_BOLD.color()).append("{●").append(Color.RED_BOLD.color()).append(" ♣}").append(Color.PURPLE_BOLD.color()).append(" |").append(Color.RESET);
            }
        }
        string.append("\n\t\t\t\t\t").append(Color.PURPLE_BOLD.color()).append("└────┐ \t\t ┌────┘\t\t\t   └──────┐\t\t\t┌──────┘\t\t  └──────────┐\t\t\t┌─────────┘");
        string.append("\n\t\t\t\t\t     ").append(Color.PURPLE_BOLD.color()).append("|  ").append(Color.HEAVENLY_BOLD.color()).append("{☼}").append(Color.PURPLE_BOLD.color()).append("  |\t\t\t\t\t\t  |  ").append(Color.HEAVENLY_BOLD.color()).append(" {☼}  ").append(Color.PURPLE_BOLD.color()).append(" |\t\t\t\t\t\t\t |    ").append(Color.HEAVENLY_BOLD.color()).append("{☼}").append(Color.PURPLE_BOLD.color()).append("   |").append("\n\t\t\t\t\t     └───────┘").append("\t\t\t\t\t\t  └─────────┘").append("\t\t\t\t\t\t\t └──────────┘");
        stream.print(string);
    }

    /**
     * Prints the player's board.
     * @param board the player's board.
     */
    public void printPersonalBoard(CompactBoard board){
        printWarehouse(board);
        printStrongbox(board);
    }

    public void changePlayerTurn(){
    //This method print the update board of other players at the end of their turn
    }

    public void endTurn(){
    //this method print the end message of personal turn and show the update board
    }

    public void updateCli(){}

    public void updateBoard(){}

    public static void clearScreen(){}

}