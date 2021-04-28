package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.core.model.Market;

public class Cli {

        private Market market;

        public Cli(){}

        public void setIPAddress(){}

        public void setPlayerID(){
            //This method ask the PlayerID and set a connection with the server
        }

        public void printMarket(){

            System.out.println("------------------------------" + "\n");
            for (int i = 0; i < market.getStructureAsArray().length; i++) {
                System.out.println("| " +market.getStructureAsArray()[i].toString());
                if (i==3 || i==7){
                    System.out.println("------------------------------" + "\n");
                }
            }
            //CAPIRE COME STAMPARE A COLORI SULLA CLI UNA STRINGA
        }

        public void printDevCardSlot(){}

        public void printWarehouse(){}

        private void printStrongbox(){}

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

    public static void main(String[] args) {

    }
}