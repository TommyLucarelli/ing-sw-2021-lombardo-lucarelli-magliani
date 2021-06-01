package it.polimi.ingsw.view.compact;

import it.polimi.ingsw.core.model.Resource;

import java.util.Arrays;

public class CompactBoard {
    private int[] strongbox;
    private Resource[] warehouse;

    private int[][] devCardSlots;
    private int[] abilityActivationFlag;

    private int[] leaderCards; //per capire quali mostrare, controllare se sono state attivate [deglio elementi possono essere 0]
    private int faithTrackIndex;
    private boolean[] favCards;

    private int lorenzoIndex;

    private boolean opponent;

    public CompactBoard(){
        setStrongbox(new int[4]);
        setWarehouse(new Resource[10]);
        setDevCardSlots(new int[3][3]);
        setLeaderCards(new int[2]);
        setFaithTrackIndex(0);
        setFavCards(new boolean[3]); //settare tutti false
        abilityActivationFlag = new int[8];
        for (int i = 0; i < 8; i++) {
            abilityActivationFlag[i] = 0;
        }
        for (int i = 0; i < 10; i++) {
            warehouse[i] = Resource.ANY;
        }
        lorenzoIndex = 0;
        opponent = false;
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
        int[] lcs = {0, 0};
        if(!opponent)
            return leaderCards;
        else{
            for (int i = 0; i < 2; i++) {
                if(leaderCards[i]!=0) {
                    for (int k = 0; k < 8; k++) {
                        if (abilityActivationFlag[k] == leaderCards[i])
                            lcs[i] = leaderCards[i];
                    }
                }
            }
            return lcs;
        }
    }

    public void setLeaderCards(int[] leaderCards) {
        this.leaderCards = leaderCards;
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

    public int[] getAbilityActivationFlag() {
        return abilityActivationFlag;
    }

    public void setAbilityActivationFlag(int[] abilityActivationFlag) {
        this.abilityActivationFlag = abilityActivationFlag;
    }

    public void removeDiscardedCards(int[] discarded){
        for (int i = 0; i < discarded.length; i++) {
            if(discarded[i] != 0){
                for (int j = 0; j < leaderCards.length; j++) {
                    if(discarded[i] == leaderCards[j])
                        leaderCards[j] = 0;
                }
            }
        }
    }

    public int getLorenzoIndex() {
        return lorenzoIndex;
    }

    public void setLorenzoIndex(int lorenzoIndex) {
        this.lorenzoIndex = lorenzoIndex;
    }


    public int[] printHelperSlots(){
        int[] result = new int[3];
        for (int j = 0; j < 3; j++) {
            for (int i = 2; i >= 0; i--) {
                if(devCardSlots[j][i] != 0 || i == 0) {
                    result[j] = i;
                    break;
                }
            }
        }
        return result;
    }

    public boolean isOpponent() {
        return opponent;
    }

    public void setOpponent(boolean opponent) {
        this.opponent = opponent;
    }
}
