package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.core.model.DevCard;
import it.polimi.ingsw.core.model.LeaderCard;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.view.compact.*;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Class used to print the elements of the game in the CLI.
 * @author Martina Magliani
 */
public class FancyPrinter {
    private final PrintStream stream;
    CardCollector cardCollector;

    public FancyPrinter(){
        try {
            cardCollector = new CardCollector();
        }catch(FileNotFoundException e){
            System.out.println("\nFile not found");
        }
        stream = new PrintStream(System.out, true);
    }

    /**
     * Prints the market.
     * @param market the market saved in the client view.
     */
    public void printMarket(CompactMarket market) {
        StringBuilder string = new StringBuilder();
        string.append("       ").append(1).append("      ").append(2).append("      ").append(3).append("      ").append(4).append("\n");
        string.append("   -------------------------------\n").append(1).append("  |");

        for (int i = 0; i < 13; i++) {
            switch (market.getMarket()[i]) {
                case 0:
                    string.append("   ").append(Color.YELLOW_BOLD.color()).append("●   ");
                    break;
                case 1:
                    string.append("   ").append(Color.WHITE_BOLD.color()).append("●   ");
                    break;
                case 2:
                    string.append("   ").append(Color.HEAVENLY_BOLD.color()).append("●   ");
                    break;
                case 3:
                    string.append("   ").append(Color.PURPLE_BOLD.color()).append("●   ");
                    break;
                case 4:
                    string.append("   ").append(Color.RED_BOLD.color()).append("●   ");
                    break;
                case 5:
                    string.append("   ").append(Color.RESET).append("●   ");
                    break;
            }

            if (i == 3) {
                string.append(Color.RESET).append(" |\n").append(2).append("  |");
            } else if (i == 7) {
                string.append(Color.RESET).append(" |\n").append(3).append("  |");
            } else if (i == 11) {
                string.append(Color.RESET).append(" |  ");
            }
        }

        string.append(Color.RESET).append("\n   -------------------------------");
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
            string1.append("  ┌───────────────────┐");
            string2.append("  │                   │");
            string3.append("  │                   │");
            string4.append("  │                   │");
            string5.append("  │                   │");
            string6.append("  │                   │");
            string7.append("  │                   │");
            string8.append("  │                   │");
            string9.append("  └───────────────────┘");
        } else {
            devCard1 = cardCollector.getDevCard(id);

            string1.append("  ┌───────────────────┐");
            string2.append("  │  ");
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
                        string2.append("   ");
                    case 2:
                        string2.append(" ");
                    case 3:
                        string2.append(" ");
                }
            }


            switch (devCard1.getFlag().getColour()) {
                case YELLOW:
                    string2.append(Color.YELLOW_BOLD.color()).append("▓  ").append(Color.RESET).append("│");
                    break;
                case BLUE:
                    string2.append(Color.HEAVENLY_BOLD.color()).append("▓  ").append(Color.RESET).append("│");
                    break;
                case GREEN:
                    string2.append(Color.GREEN_BOLD.color()).append("▓  ").append(Color.RESET).append("│");
                    break;
                case PURPLE:
                    string2.append(Color.PURPLE_BOLD.color()).append("▓  ").append(Color.RESET).append("│");
                    break;
            }

            string3.append("  │                   │");
            string4.append("  │   ");

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
                        string4.append("      ");
                    case 2:
                        string4.append("  ");
                    case 3:
                        string4.append(" ");
                }
            }

            switch (devCard1.getRecipe().getInputResources().size()) {
                case 1:
                    string4.append("    │");
                    break;
                case 2:
                    string4.append("    │");
                    break;
                case 3:
                    string4.append(" │");
                    break;
            }

            if (devCard1.getRecipe().getOutputResources().size() != 3) {
                string5.append("  │ ─►   ");
            } else {
                string5.append("  │ ─► ");
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
                        string5.append("     ");
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
                    string5.append("  ");
                case 2:
                    string5.append("");
                case 3:
                    string5.append(" ");
            }
            string5.append("  │");
            string6.append("  │                   │");
            string7.append("  │                   │");
            if(devCard1.getVictoryPoints()>9){
                string8.append("  │ VictoryPoints:").append(devCard1.getVictoryPoints()).append("  │");
            } else {
                string8.append("  │ VictoryPoints:").append(devCard1.getVictoryPoints()).append("   │");
            }
            string9.append("  └───────────────────┘");

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
     */
    public void printDevCard(int id) {
        for (int i = 0; i < 9; i++) {
            stream.print(devCardToArrayList(id).get(i)+"\n");
        }
    }

    /**
     * Save in a StringBuilder string the last part of the DevCardSlot
     * @param id od the DevCard
     * @return the string of the base
     */
    public ArrayList<StringBuilder> printBase(int id) {
        StringBuilder base1 = new StringBuilder();
        StringBuilder base2 = new StringBuilder();
        CardCollector cardCollector = null;
        try {
            cardCollector = new CardCollector();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(id!=0) {
            base1.append("  │  ");
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
            if(cardCollector.getDevCard(id).getVictoryPoints()<9) {
                base1.append("    VP: ").append(cardCollector.getDevCard(id).getVictoryPoints());
            } else {
                base1.append("    VP:").append(cardCollector.getDevCard(id).getVictoryPoints());
            }
            switch (cardCollector.getDevCard(id).getFlag().getColour()) {
                case YELLOW:
                    base1.append(Color.YELLOW_BOLD.color()).append("    ▓").append(Color.RESET);
                    break;
                case BLUE:
                    base1.append(Color.HEAVENLY_BOLD.color()).append("    ▓").append(Color.RESET);
                    break;
                case GREEN:
                    base1.append(Color.GREEN_BOLD.color()).append("    ▓").append(Color.RESET);
                    break;
                case PURPLE:
                    base1.append(Color.PURPLE_BOLD.color()).append("    ▓").append(Color.RESET);
                    break;
            }
            base1.append("  │");
            base2.append("  └───────────────────┘");
        } else {
            base1.append("  │                   │");
            base2.append("  └───────────────────┘");
        }
        ArrayList<StringBuilder> base = new ArrayList<>();
        base.add(base1);
        base.add(base2);
        return base;
    }

    /**
     * Save a development card slot from the player's board in a string.
     * @param board the player's board
     * @return string of the devCardSlot
     */
    public ArrayList<StringBuilder> devCardSlotToArray(CompactBoard board, boolean production){
        ArrayList<StringBuilder> string = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            string.add(new StringBuilder());
        }

        int[] indexHelper = board.printHelperSlots();

        int x = 0;
        if(production){
            string.get(0).append("            2                              3                              4");
            x = 1;
        }
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 3; i++) {
                string.get(j+x).append(devCardToArrayList(board.getDevCardSlots()[i][indexHelper[i]]).get(j)).append("        ");
            }
        }

        for (int i = 0; i < 3; i++)
            indexHelper[i]--;

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 3; i++) {
                if(indexHelper[i] < 0)
                    string.get(j + 9 + x).append(printBase(0).get(j)).append("        ");
                else
                    string.get(j + 9 + x).append(printBase(board.getDevCardSlots()[i][indexHelper[i]]).get(j)).append("        ");
            }
        }

        for (int i = 0; i < 3; i++)
            indexHelper[i]--;

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 3; i++) {
                if(indexHelper[i] < 0)
                    string.get(j + 11 + x).append(printBase(0).get(j)).append("        ");
                else
                    string.get(j + 11 + x).append(printBase(board.getDevCardSlots()[i][indexHelper[i]]).get(j)).append("        ");
            }
        }

        return string;
    }

    /**
     * Prints a development card slot from the player's board.
     * @param board the player's board.
     * @param production is a boolean representing the game phase when is called the method
     */
    public void printDevCardSlot(CompactBoard board, boolean production){
        for (int i = 0; i < 14; i++) {
            stream.print(devCardSlotToArray(board, production).get(i).append("\n"));
        }
    }

    /**
     * Prints the development card structure.
     */
    public void printDevCardStructure(CompactDevCardStructure devCardStructure) {
        StringBuilder strDevCardStructure = new StringBuilder();
        strDevCardStructure.append("                 Green                          Blue                          Yellow                         Purple");

        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < 9; i++) {
                if (i != 4) {
                    strDevCardStructure.append("\n       ");
                } else {
                    strDevCardStructure.append("\nLevel ").append(k+1);
                }
                for (int j = 0; j < 4; j++) {
                    strDevCardStructure.append(devCardToArrayList(devCardStructure.getDevCardStructure()[k][j]).get(i)).append("        ");
                }
            }
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
            string1.append("  ┌────────────────────┐");
            string2.append("  │                    │");
            string3.append("  │                    │");
            string4.append("  │                    │");
            string5.append("  │                    │");
            string6.append("  │                    │");
            string7.append("  │                    │");
            string8.append("  │                    │");
            string9.append("  │                    │");
            string10.append("  └────────────────────┘");
        } else {
            leaderCard = cardCollector.getLeaderCard(id);
            int[] num = new int[4];

            string1.append("  ┌────────────────────┐");
            string2.append("  │  ");
            if (leaderCard.getSpecialAbility().getAbilityType() == 0) {
                switch (leaderCard.getRequiredResources().getResource()) {
                    case COIN:
                        string2.append(Color.YELLOW_BOLD.color()).append(5).append(" $").append(Color.RESET).append("               │");
                        break;
                    case SERVANT:
                        string2.append(Color.PURPLE_BOLD.color()).append(5).append(" ■").append(Color.RESET).append("               │");
                        break;
                    case SHIELD:
                        string2.append(Color.HEAVENLY_BOLD.color()).append(5).append(" ◊").append(Color.RESET).append("               │");
                        break;
                    case STONE:
                        string2.append(Color.WHITE_BOLD.color()).append(5).append(" ⌂").append(Color.RESET).append("               │");
                        break;
                }
            } else {
                for (int i = 0; i < leaderCard.getRequiredFlags().size(); i++) {
                    num[leaderCard.getRequiredFlags().get(i).getColour().ordinal()]++;
                }

                if (num[0] != 0)
                    string2.append(num[0]).append(Color.GREEN_BOLD.color()).append(" ▓  ").append(Color.RESET);
                if (num[2] != 0)
                    string2.append(num[2]).append(Color.YELLOW_BOLD.color()).append(" ▓  ").append(Color.RESET);
                if (num[3] != 0)
                    string2.append(num[3]).append(Color.PURPLE_BOLD.color()).append(" ▓  ").append(Color.RESET);
                if (num[1] != 0)
                    string2.append(num[1]).append(Color.HEAVENLY_BOLD.color()).append(" ▓  ").append(Color.RESET);

                int cont = 0;
                for (int i = 0; i < 4; i++) {
                    if (num[i] != 0) cont++;
                }
                if (cont == 2) {
                    string2.append("        │");
                } else {
                    string2.append("             │");
                }
            }

            string3.append("  │                    │");
            string4.append("  │                    │");
            string5.append("  │ VictoryPoints:").append(leaderCard.getVictoryPoints());
            if(leaderCard.getVictoryPoints()<10) {
                string5.append("    │");
            } else {
                string5.append("   │");
            }
            string6.append("  │                    │");
            string7.append("  │ SpecialAbility:    │");
            string8.append("  │ ");

            switch (leaderCard.getSpecialAbility().getAbilityType()) {
                case 0:
                    string8.append("Special Warehouse  │");
                    string9.append("  │    +2");
                    break;
                case 1:
                    string8.append("Special Marble     │");
                    string9.append("  │   ● =");
                    break;
                case 2:
                    string8.append("Special Discount   │");
                    string9.append("  │   -1 ");
                    break;
                case 3:
                    string8.append("Special Production │");
                    string9.append("  │   ");
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
                string9.append("            │");
                string10.append("  └────────────────────┘");
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
                string9.append(" ─► 1 ").append(Color.WHITE_BOLD.color()).append("? ").append(Color.RESET).append(1).append(Color.RED_BOLD.color()).append(" †").append(Color.RESET).append("   │");
                string10.append("  └────────────────────┘");
            }
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
     * Save a leader card slot from the player's board in a string.
     * @param board the player's board
     * @return string of the leaderCardSlot
     */
    public ArrayList<StringBuilder> leaderCardSlotToArray(CompactBoard board){

        ArrayList<StringBuilder> string = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            string.add(new StringBuilder());
        }
        Boolean flag;
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < board.getLeaderCards().length; i++) {
                string.get(j).append(leaderCardToArrayList(board.getLeaderCards()[i]).get(j)).append("        ");
            }
        }

        for (int i = 0; i < board.getLeaderCards().length; i++) {
            flag = false;
            if(board.getLeaderCards()[i]!=0) {
                for (int k = 0; k < 8; k++) {
                    if (board.getAbilityActivationFlag()[k] == board.getLeaderCards()[i])
                        flag = true;
                }
            }
            if(flag)
                string.get(10).append("        Activated                ");
            else
                string.get(10).append("      Not Activated              ");
        }

        return string;
    }

    /**
     * Prints a LeaderCardSlot
     * @param board the player's board
     */
    public void printLeaderCardSlot(CompactBoard board){
        for (int i = 0; i < 11; i++) {
            stream.print(leaderCardSlotToArray(board).get(i).append("\n"));
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
                leaderCardSlot.append(leaderCardToArrayList(leaderCardArray[i]).get(j)).append("        ");
            }
            leaderCardSlot.append("\n");
        }
        stream.print(leaderCardSlot);
    }

    /**
     * Improved version of printWarehouse.
     * @param board the player's board.
     */
    public ArrayList<StringBuilder> warehouseV2ToArrayList(CompactBoard board){
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
        StringBuilder string11 = new StringBuilder();
        StringBuilder string12 = new StringBuilder();
        StringBuilder string13 = new StringBuilder();
        StringBuilder string14 = new StringBuilder();
        StringBuilder string15 = new StringBuilder();
        StringBuilder string16 = new StringBuilder();
        string1.append("        WAREHOUSE         ");
        string2.append("         ┌─────┐          ");
        string3.append("         |  ");
        symbolForWarehouse(string3, board.getWarehouse()[0], 1);
        string3.append("  |          ");
        string4.append("         └─────┘          ");
        string5.append("      ┌───────────┐       ");
        string6.append("      |  ");
        symbolForWarehouse(string6, board.getWarehouse()[1], 2);
        string6.append("     ");
        symbolForWarehouse(string6, board.getWarehouse()[2], 3);
        string6.append("  |       ");
        string7.append("      └───────────┘       ");
        string8.append("    ┌───────────────┐     ");
        string9.append("    |  ");
        symbolForWarehouse(string9, board.getWarehouse()[3], 4);
        string9.append("    ");
        symbolForWarehouse(string9, board.getWarehouse()[4], 5);
        string9.append("    ");
        symbolForWarehouse(string9, board.getWarehouse()[5], 6);
        string9.append("  |     ");
        string10.append("    └───────────────┘     ");

        if(board.getAbilityActivationFlag()[0] != 0){
            string11.append("      ┌───────────┐       ");
            string12.append("      |  ");
            symbolForWarehouse(string12, board.getWarehouse()[6], 7);
            string12.append("     ");
            symbolForWarehouse(string12, board.getWarehouse()[7], 8);
            string12.append("  | ");
            switch(cardCollector.getLeaderCard(board.getAbilityActivationFlag()[0]).getSpecialAbility().getAbilityResource()){
                case STONE:
                    string12.append(Color.WHITE_BOLD.color()).append("⌂");
                    break;
                case SERVANT:
                    string12.append(Color.PURPLE_BOLD.color()).append("■");
                    break;
                case COIN:
                    string12.append(Color.YELLOW_BOLD.color()).append("$");
                    break;
                case SHIELD:
                    string12.append(Color.HEAVENLY_BOLD.color()).append("◊");
                    break;
            }
            string12.append(" ONLY").append(Color.RESET);
            string13.append("      └───────────┘       ");

            if(board.getAbilityActivationFlag()[1] != 0){
                //1 caso
                string14.append("      ┌───────────┐       ");
                string15.append("      |  ");
                symbolForWarehouse(string15, board.getWarehouse()[8], 9);
                string15.append("     ");
                switch(board.getWarehouse()[9]){
                    case STONE:
                        string15.append(Color.WHITE_BOLD.color()).append("⌂ ");
                        break;
                    case SERVANT:
                        string15.append(Color.PURPLE_BOLD.color()).append("■ ");
                        break;
                    case COIN:
                        string15.append(Color.YELLOW_BOLD.color()).append("$ ");
                        break;
                    case SHIELD:
                        string15.append(Color.HEAVENLY_BOLD.color()).append("◊ ");
                        break;
                    case ANY:
                        string15.append("10");
                        break;
                }
                string15.append(Color.RESET).append(" | ");
                switch(cardCollector.getLeaderCard(board.getAbilityActivationFlag()[1]).getSpecialAbility().getAbilityResource()){
                    case STONE:
                        string15.append(Color.WHITE_BOLD.color()).append("⌂");
                        break;
                    case SERVANT:
                        string15.append(Color.PURPLE_BOLD.color()).append("■");
                        break;
                    case COIN:
                        string15.append(Color.YELLOW_BOLD.color()).append("$");
                        break;
                    case SHIELD:
                        string15.append(Color.HEAVENLY_BOLD.color()).append("◊");
                        break;
                }
                string15.append(" ONLY");
                string16.append(Color.RESET).append("      └───────────┘       ");
            }else{
                string14.append("                          ");
                string15.append("                          ");
                string16.append("                          ");
            }
        } else {
            string11.append("                          ");
            string12.append("                          ");
            string13.append("                          ");
            string14.append("                          ");
            string15.append("                          ");
            string16.append("                          ");
        }

        ArrayList<StringBuilder> string = new ArrayList<>();
        string.add(string1);
        string.add(string2);
        string.add(string3);
        string.add(string4);
        string.add(string5);
        string.add(string6);
        string.add(string7);
        string.add(string8);
        string.add(string9);
        string.add(string10);
        string.add(string11);
        string.add(string12);
        string.add(string13);
        string.add(string14);
        string.add(string15);
        string.add(string16);

        return string;
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
                string.append(Color.HEAVENLY_BOLD.color()).append("◊");
                break;
            case ANY:
                string.append(any);
                break;
        }
        string.append(Color.RESET);
    }

    /**
     * Improved version of printWarehouse.
     * @param board the player's board.
     */
    public void printWarehouseV2(CompactBoard board){
        int rows = 10;
        if(board.getAbilityActivationFlag()[0] != 0) rows += 3;
        if(board.getAbilityActivationFlag()[1] != 0) rows += 3;

        for (int i = 0; i < rows; i++) {
            stream.print(warehouseV2ToArrayList(board).get(i).append("\n"));
        }
    }

    /**
     * Prints the warehouse and the resources stored in it.
     * @param board the player's board.
     * @deprecated
     */
    public void printWarehouse(CompactBoard board){
        StringBuilder string = new StringBuilder();

        string.append("\n").append(Color.GREEN_BOLD.color()).append("        WAREHOUSE\n");
        string.append("        ");

        for (int i = 0; i < 10; i++) {
            switch (board.getWarehouse()[i]) {
                case STONE:
                    string.append("        ").append(Color.WHITE_BOLD.color()).append(" STONE");
                    break;
                case SERVANT:
                    string.append("        ").append(Color.PURPLE_BOLD.color()).append("SERVANT");
                    break;
                case COIN:
                    string.append("        ").append(Color.YELLOW_BOLD.color()).append(" COIN");
                    break;
                case SHIELD:
                    string.append("        ").append(Color.BLUE_BOLD.color()).append("SHIELD");
                    break;
                case ANY:
                    string.append("");
            }


            if(i==0){
                string.append("\n        ");
            } else if(i==2){
                string.append("\n        ");
            } else if(i==5){
                string.append(Color.RESET).append("\n        ----------------------------------------\n        ");
            }
        }
        string.append("\n");
        stream.print(string);
    }

    /**
     * Save the player's strongbox and its resources.
     * @param board the player's board.
     * @return the ArrayList representing the structure of the storngbox
     */
    public ArrayList<StringBuilder> strongboxToArrayList(CompactBoard board){
        StringBuilder string1 = new StringBuilder();
        StringBuilder string2 = new StringBuilder();
        StringBuilder string3 = new StringBuilder();
        StringBuilder string4 = new StringBuilder();
        StringBuilder string5 = new StringBuilder();
        StringBuilder string6 = new StringBuilder();
        StringBuilder string7 = new StringBuilder();

        string1.append(Color.GREEN_BOLD.color()).append("        STRONGBOX        ").append(Color.RESET);
        string2.append(Color.RESET).append("┌───────────────────────┐");
        string3.append("│     ").append(Color.YELLOW_BOLD.color()).append("COIN: ").append(board.getStrongbox()[0]).append(Color.RESET);
        if(board.getStrongbox()[0]>9){
            string3.append("          │");
        } else {
            string3.append("           │");
        }
        if(board.getStrongbox()[1]>9) {
            string4.append("│     ").append(Color.WHITE_BOLD.color()).append("STONE: ").append(board.getStrongbox()[1]).append(Color.RESET).append("         │");
        } else {
            string4.append("│     ").append(Color.WHITE_BOLD.color()).append("STONE: ").append(board.getStrongbox()[1]).append(Color.RESET).append("          │");
        }
        if(board.getStrongbox()[2]>9){
            string5.append("│     ").append(Color.HEAVENLY_BOLD.color()).append("SHIELD: ").append(board.getStrongbox()[2]).append(Color.RESET).append("        │");
        } else {
            string5.append("│     ").append(Color.HEAVENLY_BOLD.color()).append("SHIELD: ").append(board.getStrongbox()[2]).append(Color.RESET).append("         │");
        }
        if(board.getStrongbox()[3]>9){
            string6.append("│     ").append(Color.PURPLE_BOLD.color()).append("SERVANT: ").append(board.getStrongbox()[3]).append(Color.RESET).append("       │");
        } else {
            string6.append("│     ").append(Color.PURPLE_BOLD.color()).append("SERVANT: ").append(board.getStrongbox()[3]).append(Color.RESET).append("        │");
        }
        string7.append(Color.RESET).append("└───────────────────────┘");

        ArrayList<StringBuilder> string = new ArrayList<>();
        string.add(string1);
        string.add(string2);
        string.add(string3);
        string.add(string4);
        string.add(string5);
        string.add(string6);
        string.add(string7);

        return string;
    }

    /**
     * Prints the player's strongbox and its resources.
     * @param board the player's board.
     */
    public void printStrongbox(CompactBoard board){
        for (int i = 0; i < 7; i++) {
            stream.print(strongboxToArrayList(board).get(i).append("\n"));
        }
    }

    /**
     * Prints the player's faith track.
     * @param board the player's board.
     */
    public void printFaithTrack(CompactBoard board){
        StringBuilder string = new StringBuilder();

        string.append(Color.YELLOW_BOLD.color()).append("{●} = Victory Points Space        ").append(Color.RESET).append(Color.LIME.color()).append("{†} = Faith Marker\n").append(Color.RESET).append(Color.HEAVENLY_BOLD.color()).append("{☼} = Pope's Favor tiles        ").append(Color.RESET).append(Color.RED_BOLD.color()).append("{♣} = Pope Space        ").append(Color.RESET).append(Color.PURPLE_BOLD.color()).append("─── = Vatican Report section\n\n").append(Color.RESET);

        string.append(Color.PURPLE_BOLD.color()).append("                    ┌─────────────────┐");
        string.append("               ┌───────────────────────┐");
        string.append("           ┌───────────────────────────────┐").append("\n");
        string.append(Color.RESET);

        for (int i = 0; i < 25; i++) {
            if(i==5 || i==9 || i==12 || i==17 || i==19){
                string.append(Color.PURPLE_BOLD.color()).append("│ ").append(Color.RESET);
            }

            if(board.getFaithTrackIndex()==i){
                if(i == 10 || i == 11 || i == 13 || i == 14 || i == 17 || i == 19 || i == 20 || i == 22 || i == 23)
                    string.append(Color.LIME.color()).append(" {†} ").append(Color.RESET);
                else
                    string.append(Color.LIME.color()).append("{†} ").append(Color.RESET);
            } else if(i%3==0 && i!=0 && i!=24) {
                string.append(Color.YELLOW_BOLD.color()).append("{●} ").append(Color.RESET);
            } else if(i%8==0 && i!=0 && i!=24) {
                string.append(Color.RED_BOLD.color()).append("{♣} ").append(Color.RESET);
            } else if(i!=24) {
                string.append("{").append(i).append("} ").append(Color.RESET);
            }

            if(i==24){
                string.append(Color.YELLOW_BOLD.color()).append("{●").append(Color.RED_BOLD.color()).append(" ♣}").append(Color.PURPLE_BOLD.color()).append(" │").append(Color.RESET);
            }
        }

        string.append("\n                    ").append(Color.PURPLE_BOLD.color()).append("└────┐       ┌────┘               └──────┐         ┌──────┘           └───────────┐          ┌────────┘");
        string.append("\n                         ").append(Color.PURPLE_BOLD.color()).append("│ ").append(Color.HEAVENLY_BOLD.color());

        if (board.getFavCards()[0]) {
            string.append("VP:+").append(2).append(Color.PURPLE_BOLD.color()).append(" │                           │  ");
        } else {
            string.append(" {☼}").append(Color.PURPLE_BOLD.color()).append("  │                           │  ");
        }
        string.append(Color.HEAVENLY_BOLD.color());
        if(board.getFavCards()[1]){
            string.append("VP:+").append(3).append(Color.PURPLE_BOLD.color()).append("  │                              │");
        } else {
            string.append("  {☼}  ").append(Color.PURPLE_BOLD.color()).append("│                              │ ");
        }
        string.append(Color.HEAVENLY_BOLD.color());
        if(board.getFavCards()[2]){
            string.append("  VP: +").append(4).append(Color.PURPLE_BOLD.color()).append("  │");
        } else {
            string.append("   {☼}").append(Color.PURPLE_BOLD.color()).append("   │");
        }

        string.append("\n                         └───────┘").append("                           └─────────┘").append("                              └──────────┘");
        string.append(Color.RESET);
        stream.print(string);
    }

    public void printBlackFaithTrack(CompactBoard board){
        StringBuilder string = new StringBuilder();

        string.append(Color.BLACK_UNDERGROUND.color()).append("        LORENZO FAITH TRACK\n\n");

        for (int i = 0; i < 25; i++) {
            if(board.getLorenzoIndex()==i){
                string.append(Color.WHITE_UNDERGROUND.color()).append("{†} ").append(Color.RESET);
            } else if(i%8==0 && i!=0 && i!=24) {
                string.append(Color.RED_UNDERGROUND.color()).append("{♣} ").append(Color.RESET);
            } else if(i!=24) {
                string.append(Color.BLACK_UNDERGROUND.color()).append("{").append(i).append("} ").append(Color.RESET);
            }
        }
        string.append(Color.RESET);
        stream.print(string);
    }

    /**
     * Prints the player's board.
     * @param board the player's board.
     */
    public void printPersonalBoard(CompactBoard board){
        printFaithTrack(board);
        stream.print("\n\n");
        for (int i = 0; i < 26; i++) {
            if (i < 14) {
               stream.print(warehouseV2ToArrayList(board).get(i).append("            ").append(devCardSlotToArray(board, false).get(i)));
            }
            if(i==14){
                stream.print(warehouseV2ToArrayList(board).get(i));
            }
            if(i == 15){
                stream.print(warehouseV2ToArrayList(board).get(i).append("            ").append(leaderCardSlotToArray(board).get(i-15)));
            }
            if(i == 16){
                stream.print("                                      "+leaderCardSlotToArray(board).get(i-15));
            }
            if(i==17){
                stream.print(strongboxToArrayList(board).get(i-17).append("             "+leaderCardSlotToArray(board).get(i-15)));
            }
            if(i>17 && i<24){
                stream.print(strongboxToArrayList(board).get(i-17).append("             "+leaderCardSlotToArray(board).get(i-15)));
            }
            if(i == 24){
                stream.print("                                      "+leaderCardSlotToArray(board).get(i-15));
            }
            if(i == 25)
                stream.print("                                      "+leaderCardSlotToArray(board).get(i-15));
            stream.print("\n");
        }
    }

    public void printTiles(){
        String string = "┌───────┐\n" +
                "│  x 3  │\n" +
                "└───────┘";
        stream.print(string);
    }

}