package it.polimi.ingsw.view.compact;

public class CompactDevCardStructure {

    private int[][] devCardStructure;

    public CompactDevCardStructure(){
        setDevCardStructure(new int[3][4]);
    }

    public int[][] getDevCardStructure() {
        return devCardStructure;
    }

    public void setDevCardStructure(int[][] devCardStructure) {
        this.devCardStructure = devCardStructure;
    }
}

