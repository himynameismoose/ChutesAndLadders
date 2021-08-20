/**
 * The Player class represents the players of the game of Chutes and Ladders.
 *
 * @author Mershelle Rivera
 * @version 1.0
 */
public class Player {
    // Class fields
    private final String NAME;    // Stores the Name of the Player instance
    private int position;   // Stores the player's position on the game board

    /**
     * The constructor to set the player's name and the initial position to 0.
     *
     * @param name Sets the player's name
     */
    public Player(String name) {
        this.NAME = name;
        position = 0;
    }

    /**
     * Sets the player's position
     *
     * @param position The number that sets the player's position on the board
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gets the player's position
     *
     * @return returns player's current position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Gets the player's name
     *
     * @return returns the player's name
     */
    public String getName() {
        return NAME;
    }

    /**
     * The player "spins" the spinner and moves to a new position
     *
     * @param spinner The spinner object to simulate a spin
     */
    public void spin(Spinner spinner) throws InterruptedException {
        Thread.sleep(500);
        int newSpin = spinner.getSpin();
        System.out.println(NAME + "'s spin was: " + newSpin);
        position += newSpin;
    }
}
