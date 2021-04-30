package it.polimi.ingsw.view.compact;

public class CompactMarket {
    private char[] market;
    private char reserveMarble;

    public CompactMarket(){
        setMarket(new char[12]);
    }

    public char[] getMarket() {
        return market;
    }

    public void setMarket(char[] market) {
        this.market = market;
    }

    public char getReserveMarble() {
        return reserveMarble;
    }

    public void setReserveMarble(char reserveMarble) {
        this.reserveMarble = reserveMarble;
    }
}
