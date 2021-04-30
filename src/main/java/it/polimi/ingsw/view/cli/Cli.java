package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.compact.CompactBoard;
import it.polimi.ingsw.view.compact.CompactDevCardStructure;
import it.polimi.ingsw.view.compact.CompactMarket;
import it.polimi.ingsw.view.compact.CompactPlayer;

public class Cli {

        private CompactMarket market;
        private CompactDevCardStructure devCardStructure;
        private CompactPlayer player;
        private CompactBoard board;

        public Cli(){}

        public void setIPAddress(){}

        public void setPlayerID(){
            //This method ask the PlayerID and set a connection with the server
        }

        public void printMarket() {
            System.out.println("----------------------------------------" + "\n");
            System.out.println("\t" + 1 + " \t" + 2 + "\t" + 3 + "\t" + 4 + "\n");
            System.out.println("" + 1 + "   |\t");
            for (int i = 0; i < 12; i++) {
                switch (market.getMarket()[i]) {
                    case 1:
                        System.out.println("\t" + Color.WHITE_BOLD + "●");
                    case 2:
                        System.out.println("\t" + Color.PURPLE_BOLD + "●");
                    case 3:
                        System.out.println("\t" + Color.YELLOW_BOLD + "●");
                    case 4:
                        System.out.println("\t" + Color.BLUE_BOLD + "●");
                    case 5:
                        System.out.println("\t" + Color.BLACK_BOLD + "●");
                    case 6:
                        System.out.println("\t" + Color.RED_BOLD + "●");

                        if (i == 3) {
                            System.out.println("\t|\n" + "------------------------------" + "\n" + 2 + "   |\t");
                        } else if (i == 7) {
                            System.out.println("\n" + "------------------------------" + "\n" + 3 + "   |\t");
                        }
                }
            }
        }

        public void printDevCardSlot(){
            for (int i = 0; i < 3; i++) {
                    System.out.println("" + devCardStructure.getDevCardStructure()[i][0]);
                    System.out.println("" + devCardStructure.getDevCardStructure()[i][1]);
                    System.out.println("" + devCardStructure.getDevCardStructure()[i][2]);
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
            System.out.println("\t\t" + board.getWarehouse()[0].toString() + "\n");
            System.out.println("\t" + board.getWarehouse()[1].toString()  + board.getWarehouse()[2].toString()  + "\n");
            System.out.println("" + board.getWarehouse()[3].toString()  + board.getWarehouse()[4].toString()  + board.getWarehouse()[5].toString());

            if(board.getWarehouse().length>6){
                System.out.println("" + board.getWarehouse()[6]  + board.getWarehouse()[7]  + board.getWarehouse()[8] + board.getWarehouse()[9]);
            }
        }

        public void printStrongbox(){
            for (int i = 0; i < board.getStrongbox().length; i++) {
                System.out.println("" + board.getStrongbox()[i]);
            }
        }

        public void printFaithTrack(){

        }

        public void printPersonalBoard(){}

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

