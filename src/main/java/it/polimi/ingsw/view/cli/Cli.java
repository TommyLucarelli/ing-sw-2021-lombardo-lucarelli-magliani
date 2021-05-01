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
            CompactMarket market = new CompactMarket();
            int[] newMarket = {1,3,5,2,6,5,1,2,3,4,1,1};
            market.setMarket(newMarket);

            System.out.println("\t\t" + 1 + " \t\t" + 2 + "\t\t" + 3 + "\t\t" + 4 + "\n");
            System.out.println("\t------------------------------------" + "\n");

            System.out.println("" + 1 + "   |\t");
            for (int i = 0; i < 12; i++) {
                switch (market.getMarket()[i]) {
                    case 1:
                        System.out.println("\t" + Color.WHITE_BOLD + "●");
                        break;
                    case 2:
                        System.out.println("\t" + Color.PURPLE_BOLD + "●");
                        break;
                    case 3:
                        System.out.println("\t" + Color.YELLOW_BOLD + "●");
                        break;
                    case 4:
                        System.out.println("\t" + Color.BLUE_BOLD + "●");
                        break;
                    case 5:
                        System.out.println("\t" + Color.BLACK_BOLD + "●");
                        break;
                    case 6:
                        System.out.println("\t" + Color.RED_BOLD + "●");
                        break;
                }

                if (i == 3) {
                    System.out.println("\t|\n" + "------------------------------" + "\n" + 2 + "   |\t");
                } else if (i == 7) {
                    System.out.println("\n" + "------------------------------" + "\n" + 3 + "   |\t");
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