package it.polimi.ingsw.view.compact;

import it.polimi.ingsw.core.model.Resource;

public class CompactBoard {
    private int[] strongbox; //impostato come abbiamo fatto nel controller
    private Resource[] warehouse;
    private int[][] devCardSlots;
    private int[] leaderCards;
    private int[] leaderCardsActivated;
    private int faithTrackIndex;
    private boolean[] favCards;
    //servono dei flags per warehouse estesa cazzi e mazzi (ci penso io)

    public CompactBoard(){
        setStrongbox(new int[4]);
        setWarehouse(new Resource[10]);
        setDevCardSlots(new int[3][3]);
        setLeaderCards(new int[2]);
        setLeaderCardsActivated(new int[2]);
        setFaithTrackIndex(0);
        setFavCards(new boolean[3]); //settare tutti false
    }

    public int[] getStrongbox() {
        return strongbox;
    }

    public void setStrongbox(int[] strongbox) {
        this.strongbox = strongbox;
    }

    public Resource[] getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Resource[] warehouse) {
        this.warehouse = warehouse;
    }

    public int[][] getDevCardSlots() {
        return devCardSlots;
    }

    public void setDevCardSlots(int[][] devCardSlots) {
        this.devCardSlots = devCardSlots;
    }

    public int[] getLeaderCards() {
        return leaderCards;
    }

    public void setLeaderCards(int[] leaderCards) {
        this.leaderCards = leaderCards;
    }

    public int[] getLeaderCardsActivated() {
        return leaderCardsActivated;
    }

    public void setLeaderCardsActivated(int[] leaderCardsActivated) {
        this.leaderCardsActivated = leaderCardsActivated;
    }

    public int getFaithTrackIndex() {
        return faithTrackIndex;
    }

    public void setFaithTrackIndex(int faithTrackIndex) {
        this.faithTrackIndex = faithTrackIndex;
    }

    public boolean[] getFavCards() {
        return favCards;
    }

    public void setFavCards(boolean[] favCards) {
        this.favCards = favCards;
    }
}