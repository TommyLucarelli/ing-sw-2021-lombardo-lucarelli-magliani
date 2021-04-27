package it.polimi.ingsw.core.model;
import java.util.ArrayList;

/**
 * Class for the representation of the board
 * @author Tommaso Lucarelli
 */
public class Board {
    private Warehouse warehouse;
    private Strongbox strongbox;
    private FaithTrack faithtrack;
    private ArrayList<DevCardSlot> devCardSlots;
    private ArrayList<LeaderCard> leaderCards;

    /**
     * 0 if the ability isn't activated, a number representing the id of the card is the ability is activated
     * abiltyActivationFlag layout:
     * 0 - special warehouse 1
     * 1 - special warehouse 2
     * 2 - special marble 1
     * 3 - special marble 2
     * 4 - special discount 1
     * 5 - special discount 2
     * 6 - special production 1
     * 7 - special production 2
     */
    private int[] abilityActivationFlag;


    /**
     * Constructor method that initialize each structure inside the board
     */
    public Board()
    {
        warehouse = new Warehouse();
        strongbox = new Strongbox();
        faithtrack = new FaithTrack();
        devCardSlots = new ArrayList<DevCardSlot>();
        devCardSlots.add(new DevCardSlot());
        devCardSlots.add(new DevCardSlot());
        devCardSlots.add(new DevCardSlot());
        leaderCards = new ArrayList<LeaderCard>();
        abilityActivationFlag = new int[8]; //dovrebbero essere già tutti 0
    }

    /**
     * Getter method
     * @return the faith track
     */
    public FaithTrack getFaithTrack() {
        return faithtrack;
    }

    /**
     * Getter method
     * @return the strongbox
     */
    public Strongbox getStrongbox() {
        return strongbox;
    }

    /**
     * Getter method
     * @return the warehouse
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Getter method
     * @return the slot for Development Card selected
     */
    public DevCardSlot getDevCardSlot(int i){
        return devCardSlots.get(i);
    }

    /**
     * Getter method
     * @return the Leader Card selected
     */
    public LeaderCard getLeaderCard(int i){
        return leaderCards.get(i);
    }

    /**
     * Method to give the leader card to each player at the beginning of the game
     * @param lc is the Leader Card that is being inserted in the array
     */
    public void addLeaderCard(LeaderCard lc){
        leaderCards.add(lc);
    }

    public void removeLeaderCard(LeaderCard lc){
        leaderCards.remove(lc);
    }


    /**
     * Setter method to set true the flag of a special ability that has become active
     * @param i spot representing the ability that the user has activated, the spot layout is determined by the legend above
     * @param id the identifier of the card with the special ability
     */
    public void setAbilityActivationFlag(int i, int id) {
        abilityActivationFlag[i] = id;
    }

    /**
     * getter method to to see whether an ability is activated or not
     * @param i spot representing the ability that the user has activated, the spot layout is determined by the legend above
     * @return the current value of the flag
     */
    public int isActivated(int i){
        return abilityActivationFlag[i];
    }

    public LeaderCard getLeader(int id){
        for (LeaderCard lc : leaderCards) {
            if(lc.getId() == id)
                return lc;
        }
        /* se non avviene nessuno problema, ci può stare fare un exception */
        return null;
    }

    /**
     * @return the player resources with the following layout: COIN|STONE|SHIELD|SERVANT
     */
    public int[] personalResQtyToArray() {
        int[] resPlayer = new int[4];
        for (int i = 0; i < warehouse.getStructure().size(); i++) {
            switch (warehouse.getStructure().get(i)) {
                case COIN:
                    resPlayer[0] ++;
                    break;
                case STONE:
                    resPlayer[1] ++;
                    break;
                case SHIELD:
                    resPlayer[2] ++;
                    break;
                case SERVANT:
                    resPlayer[3] ++;
                    break;
            }
        }
        for (int i = 0; i < strongbox.getResources().size(); i++) {
            switch (strongbox.getResources().get(i).getResource()) {
                case COIN:
                    resPlayer[0] += strongbox.getResources().get(i).getQty();
                    break;
                case STONE:
                    resPlayer[1] += strongbox.getResources().get(i).getQty();
                    break;
                case SHIELD:
                    resPlayer[2] += strongbox.getResources().get(i).getQty();
                    break;
                case SERVANT:
                    resPlayer[3] += strongbox.getResources().get(i).getQty();
                    break;
            }
        }
        return resPlayer;
    }

    public int countFlags(Flag f, boolean level) {
        int count = 0;
        ArrayList<Flag> flags;
        if(level){
            for(int i=0; i<devCardSlots.size(); i++){
                flags = devCardSlots.get(i).getSlotFlags();
                if(flags.size() >= f.getLevel() && flags.get(f.getLevel() - 1).getColour() == f.getColour())
                    count++;
            }
        }else{
            for(int i=0; i<devCardSlots.size(); i++){
                flags = devCardSlots.get(i).getSlotFlags();
                for(int j=0; j<flags.size(); j++){
                    if(flags.get(j).getColour() == f.getColour())
                        count++;
                }
            }
        }
        return count;
    }

    public int victoryPoints(){
        int vp=0;
        int cont=0;
        int x[];
        vp+= numberOfDevCard();
        vp+= faithtrack.getPosition();
        vp+= faithtrack.favourVictoryPoints();
        x = personalResQtyToArray();
        for (int i : x) {
            cont+=i;
        }
        if(leaderCards.get(0).getAbilityActivation())
            vp+=leaderCards.get(0).getVictoryPoints();
        if(leaderCards.get(1).getAbilityActivation())
            vp+=leaderCards.get(1).getVictoryPoints();
        vp+= (cont/5);
        return vp;
    }

    public int numberOfDevCard(){
        int cont=0;
        for (DevCardSlot d : devCardSlots) {
            cont+=d.numberOfDevCard();
        }
        return cont;
    }


}
