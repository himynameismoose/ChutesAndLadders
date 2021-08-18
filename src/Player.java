/**
 * The Player class represents the players of the game of Chutes and Ladders.
 *
 * @author Mershelle Rivera
 * @version 1.0
 */
public class Player {
    // Class fields
    private String name;    // Stores the Name of the Player instance
    private int position;   // Stores the player's position on the game board

    /**
     * The constructor to set the player's name and the initial position to 0.
     *
     * @param name Sets the player's name
     */
    public Player(String name) {
        this.name = name;
        setPosition(0);
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
     * Sets the player's name
     *
     * @param name The player's name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Change the player's position after spin
     *
     * @param position The position to change
     */
    public void changePosition(int position) {
        setPosition(position);
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
        return name;
    }

    /**
     * The player "spins" the spinner and moves to a new position
     */
    public void spin(Spinner spinner) {

    }
}
