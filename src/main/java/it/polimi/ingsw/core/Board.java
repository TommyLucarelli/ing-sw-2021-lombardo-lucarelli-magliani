package it.polimi.ingsw.core;
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
    //probabilmente verranno aggiunti dei flag per le abilità speciali delle carte Leader


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

}
