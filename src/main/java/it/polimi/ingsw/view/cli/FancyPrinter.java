package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.core.model.DevCard;
import it.polimi.ingsw.view.compact.CompactBoard;
import it.polimi.ingsw.view.compact.CompactMarket;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Class used to print the elements of the game in the CLI.
 */
public class FancyPrinter {
    private final PrintStream stream;
    private final Scanner scanner;

    public FancyPrinter(){
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
                case 1:
                    string.append("\t").append(Color.WHITE_BOLD.color()).append("●\t");

                    break;
                case 2:
                    string.append("\t").append(Color.PURPLE_BOLD.color()).append("●\t");
                    break;
                case 3:
                    string.append("\t").append(Color.YELLOW_BOLD.color()).append("●\t");
                    break;
                case 4:
                    string.append("\t").append(Color.BLUE_BOLD.color()).append("●\t");
                    break;
                case 5:
                    string.append("\t").append(Color.BLACK_BOLD.color()).append("●\t");
                    break;
                case 6:
                    string.append("\t").append(Color.RED_BOLD.color()).append("●\t");
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
     * Prints a development card.
     * @param devCard the development card.
     */
    public void printDevCard(DevCard devCard) {
        StringBuilder string = new StringBuilder();

        string.append("\t┌───────────────────┐\n\t│  ");

        switch (devCard.getFlag().getColour()) {
            case YELLOW:
                string.append(Color.YELLOW_BOLD.color()).append("▓").append(Color.RESET); break;
            case BLUE:
                string.append(Color.HEAVENLY_BOLD.color()).append("▓").append(Color.RESET); break;
            case GREEN:
                string.append(Color.GREEN_BOLD.color()).append("▓").append(Color.RESET); break;
            case PURPLE:
                string.append(Color.PURPLE_BOLD.color()).append("▓").append(Color.RESET); break;
        }

        switch (devCard.getCost().size()){
            case 1: string.append("\t");
            case 2: string.append("  ");
            case 3: string.append("");
        }

        for (int i = 0; i < devCard.getCost().size(); i++) {
            int qty = devCard.getCost().get(i).getQty();
            switch (devCard.getCost().get(i).getResource()) {
                case COIN:
                    string.append(Color.YELLOW_BOLD.color()).append(qty).append(" $  ").append(Color.RESET);
                    break;
                case SERVANT:
                    string.append(Color.PURPLE_BOLD.color()).append(qty).append(" ■  ").append(Color.RESET);
                    break;
                case SHIELD:
                    string.append(Color.HEAVENLY_BOLD.color()).append(qty).append(" ◊  ").append(Color.RESET);
                    break;
                case STONE:
                    string.append(Color.WHITE_BOLD.color()).append(qty).append(" ⌂  ").append(Color.RESET);
                    break;
            }
        }

        switch (devCard.getFlag().getColour()) {
            case YELLOW:
                string.append(Color.YELLOW_BOLD.color()).append("▓\t").append(Color.RESET).append("│\n\t│\t\t\t\t\t│\n\t│\t"); break;
                case BLUE:
                    string.append(Color.HEAVENLY_BOLD.color()).append("▓\t").append(Color.RESET).append("│\n\t│\t\t\t\t\t│\n\t│\t"); break;
                case GREEN:
                    string.append(Color.GREEN_BOLD.color()).append("▓\t").append(Color.RESET).append("│\n\t│\t\t\t\t\t│\n\t│\t"); break;
                case PURPLE:
                    string.append(Color.PURPLE_BOLD.color()).append("▓\t").append(Color.RESET).append("│\n\t│\t\t\t\t\t│\n\t│\t"); break;
        }

        for (int i = 0; i < devCard.getRecipe().getInputResources().size(); i++) {
            int qtyIn = devCard.getRecipe().getInputResources().get(i).getQty();
            switch (devCard.getRecipe().getInputResources().get(i).getResource()){
                case COIN:
                    string.append(Color.YELLOW_BOLD.color()).append(qtyIn).append(" $").append(Color.RESET);
                    break;
                case SERVANT:
                    string.append(Color.PURPLE_BOLD.color()).append(qtyIn).append(" ■").append(Color.RESET);
                    break;
                case SHIELD:
                    string.append(Color.HEAVENLY_BOLD.color()).append(qtyIn).append(" ◊").append(Color.RESET);
                    break;
                case STONE:
                    string.append(Color.WHITE_BOLD.color()).append(qtyIn).append(" ⌂").append(Color.RESET);
                    break;
            }
            switch (devCard.getRecipe().getInputResources().size()){
                case 1: string.append("\t");
                case 2: string.append("   ");
                case 3: string.append(" ");
            }
        }
        switch (devCard.getRecipe().getInputResources().size()){
            case 1: string.append("\t\t│");
            case 2: string.append("\t│");
            case 3: string.append(" │");
        }

        string.append("\n\t│\t─►\t");

        for (int i = 0; i < devCard.getRecipe().getOutputResources().size(); i++) {
            int qty = devCard.getRecipe().getOutputResources().get(i).getQty();
            switch (devCard.getRecipe().getOutputResources().get(i).getResource()){
                case COIN:
                    string.append(Color.YELLOW_BOLD.color()).append(qty).append(" $").append(Color.RESET);
                    break;
                case SERVANT:
                    string.append(Color.PURPLE_BOLD.color()).append(qty).append(" ■").append(Color.RESET);
                    break;
                case SHIELD:
                    string.append(Color.HEAVENLY_BOLD.color()).append(qty).append(" ◊").append(Color.RESET);
                    break;
                case STONE:
                    string.append(Color.WHITE_BOLD.color()).append(qty).append(" ⌂").append(Color.RESET);
                    break;
            }
            switch (devCard.getRecipe().getOutputResources().size()){
                case 1: string.append("\t");
                case 2: string.append("   ");
                case 3: string.append(" ");
            }
        }
        string.append("\t│\n\t│\t\t\t\t\t│");
        string.append("\n\t│VictoryPoints:").append(devCard.getVictoryPoints()).append("\t│\n");
        string.append("\t│\t\t\t\t ").append(devCard.getId()).append(" │\n");
        string.append("\t└───────────────────┘");
        stream.print(string);
    }

    /**
     * Prints a development card slot from the player's board.
     * @param board the player's board.
     */
    public void printDevCardSlot(CompactBoard board){
    StringBuilder string = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            string.append(board.getDevCardSlots()[i][0]);
            string.append(board.getDevCardSlots()[i][1]);
            string.append(board.getDevCardSlots()[i][2]);
        }
    }

    /**
     * Prints the development card structure.
     */
    public void printDevCardStructure(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                //come??? matrice di id ma come stampo carta??
            }
        }
    }

    /**
     * Prints the warehouse and the resources stored in it.
     * @param board the player's board.
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

        string.append(Color.PURPLE_BOLD.color()).append("\t\t\t\t\t┌").append("─".repeat(17)).append("┐").append("\t\t\t   ┌").append("────".repeat(6)).append("┐").append("\t\t  ┌─").append("───".repeat(10)).append("─┐").append("\n").append(Color.RESET);

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
        string.append("\n\t\t\t\t\t").append(Color.PURPLE_BOLD.color()).append("└────┐ \t\t ┌────┘\t\t\t   └──────┐\t\t\t┌───────┘\t\t  └──────────┐\t\t\t┌──────────┘");
        string.append("\n\t\t\t\t\t     ").append(Color.PURPLE_BOLD.color()).append("|  ").append(Color.HEAVENLY_BOLD.color()).append("{☼}").append(Color.PURPLE_BOLD.color()).append("  |\t\t\t\t\t\t  |  ").append(Color.HEAVENLY_BOLD.color()).append(" {☼}  ").append(Color.PURPLE_BOLD.color()).append(" |\t\t\t\t\t\t\t |   ").append(Color.HEAVENLY_BOLD.color()).append("{☼}").append(Color.PURPLE_BOLD.color()).append("   |").append("\n\t\t\t\t\t     └───────┘").append("\t\t\t\t\t\t  └─────────┘").append("\t\t\t\t\t\t\t └──────────┘");
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