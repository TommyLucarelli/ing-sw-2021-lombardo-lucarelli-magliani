package it.polimi.ingsw.net.client;

import it.polimi.ingsw.core.model.Market;

public class Cli {

    private Market market;

    private void printMarket() {

        System.out.println("------------------------------" + "\n");
        for (int i = 0; i < market.getStructureAsArray().length; i++) {
            System.out.println("| " +market.getStructureAsArray()[i].toString());
            if (i==3 || i==7){
                System.out.println("------------------------------" + "\n");
            }
        }
            //CAPIRE COME STAMPARE A COLORI SULLA CLI UNA STRINGA
    }

    private void printDevCardSlot(){

    }

    private void printWarehouse(){

    }

    private void printStrongbox(){

    }


}
