package it.polimi.ingsw.core;

public class Market {

    Marble[][] structure;
    int line, column;

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

}
