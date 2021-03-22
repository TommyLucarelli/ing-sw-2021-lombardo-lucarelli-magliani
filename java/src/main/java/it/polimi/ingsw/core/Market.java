package it.polimi.ingsw.core;

/**
 * Class represent the market structure, composed by (3 line) * (4 column) marbles
 * @author martina
 */

public class Market {

    Marble[][] structure;
    int line, column;

    /**
     * Class constructor
     * @param structure contains all marbles on the market place
     */
    public Market(Marble[][] structure){
        this.structure = structure;
    }

    public Marble[][] getStructure() {
        return structure;
    }

    public void setStructure(Marble[][] structure) {
        this.structure = structure;
    }

    public void pickLine(int line){}

    public void pickColumn(int column){}
    
    //private void MarketToResource(Marble[][] structure, ){}

}
