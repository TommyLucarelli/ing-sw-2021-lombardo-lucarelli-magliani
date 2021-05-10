package it.polimi.ingsw.view.compact;

public class CompactMarket {

    private int[] market;
    private int reserveMarble;

    public CompactMarket(){
        market = new int[12];
    }

    public int[] getMarket() {
        return market;
    }


    public void setMarket(int[] market) {
        this.market = market;
    }

    public int getReserveMarble() {
        return reserveMarble;
    }

    public void setReserveMarble(int reserveMarble) {
        this.reserveMarble = reserveMarble;
    }
}
