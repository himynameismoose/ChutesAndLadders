import java.util.Random;

/**
 * The Spinner class is to be used by the Player class to generate a random number.
 *
 * @author Mershelle Rivera
 * @version 1.0
 */
public class Spinner {
    private int number; // To hold the number from "spin"

    /**
     * The constructor to create a Spinner
     *
     * @param number the number of "chances" on the spinner
     */
    public Spinner(int number) {
        this.number = number;
    }

    /**
     * Represents the "spin"
     *
     * @return a random number generated from "spin"
     */
    public int getSpin() {
        Random random = new Random();
        int newSpin = random.nextInt(number) + 1;

        return newSpin;
    }
}
