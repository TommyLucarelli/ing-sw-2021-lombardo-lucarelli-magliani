package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.view.compact.CompactBoard;
import it.polimi.ingsw.view.compact.CompactDevCardStructure;
import it.polimi.ingsw.view.compact.CompactMarket;
import it.polimi.ingsw.view.compact.CompactPlayer;

import java.io.PrintStream;
import java.util.Scanner;

public class Cli {

        private PrintStream stream;
        private Scanner scanner;

        public Cli(){
            stream = new PrintStream(System.out, true);
            scanner = new Scanner(System.in);
        }

        public void setIPAddress(){}

        public void setPlayerID(){
            //This method ask the PlayerID and set a connection with the server
        }

        public void printMarket() {
            CompactMarket market = new CompactMarket();
            int[] newMarket = {1, 3, 5, 2, 6, 5, 1, 2, 3, 4, 1, 1, 4};
            market.setMarket(newMarket);

            StringBuilder string = new StringBuilder();
            string.append("\t\t" + 1 + " \t\t" + 2 + "\t\t" + 3 + "\t\t" + 4 + "\n");
            string.append("\t -------------------------------\n" + 1 + "\t|");

            for (int i = 0; i < 13; i++) {
                switch (market.getMarket()[i]) {
                    case 1:
                        string.append("\t" + Color.WHITE_BOLD.color() + "●\t");
                        break;
                    case 2:
                        string.append("\t" + Color.PURPLE_BOLD.color() + "●\t");
                        break;
                    case 3:
                        string.append("\t" + Color.YELLOW_BOLD.color() + "●\t");
                        break;
                    case 4:
                        string.append("\t" + Color.BLUE_BOLD.color() + "●\t");
                        break;
                    case 5:
                        string.append("\t" + Color.BLACK_BOLD.color() + "●\t");
                        break;
                    case 6:
                        string.append("\t" + Color.RED_BOLD.color() + "●\t");
                        break;
            }

                if (i == 3) {
                    string.append(Color.RESET + "|\n" + 2 + "\t|");
                } else if (i == 7) {
                    string.append(Color.RESET + "|\n" + 3 + "\t|");
                } else if (i == 11) {
                    string.append("|\t");
                }
            }

            string.append(Color.RESET + "\n\t -------------------------------");
            stream.print(string);
        }


    public void printDevCardSlot(){
        StringBuilder string = new StringBuilder();
        CompactBoard board= new CompactBoard();
            for (int i = 0; i < 3; i++) {
                string.append("" + board.getDevCardSlots()[i][0]);
                string.append("" + board.getDevCardSlots()[i][1]);
                string.append("" + board.getDevCardSlots()[i][2]);
            }
        }

        public void printDevCardStructure(){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    //come??? matrice di id ma come stampo carta??
                }
            }
        }

        public void printWarehouse(){
            CompactBoard board = new CompactBoard();
            Resource[] resources = {Resource.COIN,Resource.SHIELD, Resource.SHIELD, Resource.STONE, Resource.STONE, Resource.STONE, Resource.SERVANT, Resource.SERVANT, Resource.ANY, Resource.ANY};
            board.setWarehouse(resources);
            StringBuilder string = new StringBuilder();

            string.append("\n" + Color.GREEN_BOLD.color() + "\t\t\t\t\tWAREHOUSE\n");
            string.append("\t\t\t\t");

            for (int i = 0; i < 10; i++) {
                switch (board.getWarehouse()[i]) {
                    case STONE:
                        string.append("\t" + Color.WHITE_BOLD.color() + " STONE");
                        break;
                    case SERVANT:
                        string.append("\t" + Color.PURPLE_BOLD.color() + "SERVANT");
                        break;
                    case COIN:
                        string.append("\t" + Color.YELLOW_BOLD.color() + " COIN");
                        break;
                    case SHIELD:
                        string.append("\t" + Color.BLUE_BOLD.color() + "SHIELD");
                        break;
                    case ANY:
                        string.append("");
                }

                if(i==0){
                    string.append("\n\t\t\t");
                } else if(i==2){
                    string.append("\n\t\t");
                } else if(i==5){
                    string.append(Color.RESET + "\n\t----------------------------------------\n\t");
                }
            }
            string.append("\n");
            stream.print(string);
        }

        public void printStrongbox(){
            CompactBoard board = new CompactBoard();
            int[] strongbox = {0,1,3,1};
            board.setStrongbox(strongbox);

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
                    string.append(Color.RESET + "\n\t|");
                }
            }
            string.append(Color.RESET + "\n\t-----------------------------------------");
            stream.print(string);
        }

        public void printFaithTrack(){
            StringBuilder string = new StringBuilder();
            CompactBoard board = new CompactBoard();
            board.setFaithTrackIndex(10);

            string.append(Color.YELLOW_BOLD.color() + "{●} = Victory Points Space\t" + Color.RESET + Color.LIME.color() + "{†} = Faith Marker\n" + Color.RESET
                            + Color.HEAVENLY_BOLD.color() + "{☼} = Pope's Favor tiles\t" + Color.RESET + Color.RED_BOLD.color() + "{♣} = Pope Space\t" + Color.RESET
                            + Color.PURPLE_BOLD.color() + "─── = Vatican Report section\n\n" + Color.RESET);

            string.append(Color.PURPLE_BOLD.color() + "\t\t\t\t\t┌" + "─".repeat(17) +"┐"
                            + "\t\t\t   ┌" + "────".repeat(6) +"┐" + "\t\t  ┌─" + "───".repeat(10) +"─┐" + "\n" + Color.RESET);

            for (int i = 0; i < 25; i++) {
                if(i==5 || i==9 || i==12 || i==17 || i==19){
                    string.append(Color.PURPLE_BOLD.color() +"| " +Color.RESET);
                }

                if(board.getFaithTrackIndex()==i){
                    string.append(Color.LIME.color() + "† " + Color.RESET);
                } else if(i%3==0 && i!=0 && i!=24) {
                    string.append(Color.YELLOW_BOLD.color() + "{●} " + Color.RESET);
                } else if(i%8==0 && i!=0 && i!=24) {
                    string.append(Color.RED_BOLD.color() + "{♣} " + Color.RESET);
                } else if(i!=24) {
                    string.append("{" + i + "} " + Color.RESET);
                }

                if(i==24){
                    string.append(Color.YELLOW_BOLD.color()+"{●" + Color.RED_BOLD.color() + " ♣}" + Color.PURPLE_BOLD.color() +" |" +Color.RESET);
                }
            }
            string.append("\n\t\t\t\t\t" + Color.PURPLE_BOLD.color() + "|\t\t\t\t  |\t\t\t   |\t\t\t\t\t\t|\t\t  |\t\t\t\t\t\t\t\t   |");

            stream.print(string);
        }

        public void printPersonalBoard(){
            printWarehouse();
            printStrongbox();
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