package it.polimi.ingsw.view.cli;

public class Cli {

    /**
        private CompactMarket market;
        private CompactDevCardSlot devCardSlot;
        private CompactWarehouse warehouse;
        private CompacStrongbox strongbox;

        public Cli(){}

        public void setIPAddress(){}

        public void setPlayerID(){
            //This method ask the PlayerID and set a connection with the server
        }

        public void printMarket(){
            System.out.println("------------------------------" + "\n");
            for (int i = 0; i < market.getStructure().length; i++) {
                System.out.println("| " +market.getStructure()[i].toString());
                if (i==3 || i==7){
                    System.out.println("------------------------------" + "\n");
                }
            }
            //CAPIRE COME STAMPARE A COLORI SULLA CLI UNA STRINGA
        }

        public void printDevCardSlot(){
            for (int i = 0; i < 3; i++) {
                    System.out.println("" + devCardSlot[i][0].toString());
                    System.out.println("" + devCardSlot[i][1].getSlotFlag().toString());
                    System.out.println("" + devCardSlot[i][2].getSlotFlag().toString());
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
            System.out.println("\t\t" + warehouse.getStructure().get(0).toString() + "\n");
            System.out.println("\t" + warehouse.getStructure().get(1).toString() + warehouse.getStructure().get(2).toString() + "\n");
            System.out.println("" + warehouse.getStructure().get(3).toString() + warehouse.getStructure().get(4).toString() + warehouse.getStructure().get(5).toString());

            if(warehouse.specialWarehouse().equals(true)){
                System.out.println();
            }
        }

        public void printStrongbox(){
            for (int i = 0; i < strongbox.getStructure().lenght; i++) {
                System.out.println("" + strongbox.getStructure().get(i));
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

    public static void main(String[] args) {

    }
     **/
}

