package it.polimi.ingsw.view.compact;

public class CompactMarket {

    private int[] market;
    private char reserveMarble;

    public CompactMarket(){
        market = new int[12];
    }

    public int[] getMarket() {
        return market;
    }


    public void setMarket(int[] market) {
        this.market = market;
    }

    public char getReserveMarble() {
        return reserveMarble;
    }

    public void setReserveMarble(char reserveMarble) {
        this.reserveMarble = reserveMarble;
    }
}
