package it.polimi.ingsw.view.compact;

public class CompactMarket {

    private int[] market;

    public CompactMarket(){
        market = new int[13];
    }

    public int[] getMarket() {
        return market;
    }

    public void setMarket(int[] market) {
        this.market = market;
    }

    public int getWhites(boolean column, int n){
        int count = 0;
        if(column){
            for (int i = 0; i < 3; i++) {
                if(market[n+(i*4)]==5)
                    count++;
            }
        }else{
            for (int i = 0; i < 4; i++) {
                if(market[(n*4)+i]==5)
                    count++;
            }
        }
        return count;
    }


}
