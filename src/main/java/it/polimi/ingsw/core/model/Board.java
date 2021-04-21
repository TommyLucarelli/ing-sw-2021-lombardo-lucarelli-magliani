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
        //lancia IndexOutofBoundException da gestire nel controller
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
        if(leaderCards.get(0).getId() == id)
            return leaderCards.get(0);
        else
            return leaderCards.get(1);
        /* se non avviene nessuno dei due abbiamo un problema, ci può stare fare un exception */
    }

    //TODO: metodi per il controllo di una bandiera di livello x
}
