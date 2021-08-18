import java.util.Scanner;

/**
 * The Display class represents the role of the game board and the game logic
 *
 * @author mershellerivera
 * @version 1.0
 */
public class Display {
    private static final int NUM_OF_PLAYERS = 4;                        // The max number of players
    private static final int SPINNER = 6;                               // The spinner will land between 1-6
    private static final Player[] PLAYERS = new Player[NUM_OF_PLAYERS]; // An array to hold players of the game
    private static final int LAST_SQUARE = 100;                         // The last square on the game board

    /**
     * This starts the application
     *
     * @param args command line string arguments
     */
    public static void main(String[] args) {
        welcome();
        createPlayers();
        Spinner spinner = new Spinner(SPINNER); // Create Spinner Instance
        boolean gameActive = true;              // Store game state
        String winner = "";                     // Store game winner
        // Game logic
        while (gameActive) {
            // Have the players take turns
            for (Player player : PLAYERS) {
                player.spin(spinner);
                System.out.println(player.getName() + " position is: " +
                        player.getPosition());

                if (player.getPosition() > LAST_SQUARE) {
                    winner = player.getName();
                    gameActive = false;
                }
            }
        }

        System.out.println(winner + " is the winner!");
    }

    /**
     * Prints out the welcome message
     */
    public static void welcome() {
        System.out.println("\nLet's play Chutes and Ladders!");
    }

    /**
     * This will create players for the game
     */
    public static void createPlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nBefore we can play, let's create players for the game:");
        int playerCount = 0;
        boolean addNewPlayer = true;
        while (playerCount < NUM_OF_PLAYERS && addNewPlayer) {
            System.out.print("Name: ");
            Player player = new Player(scanner.nextLine());
            PLAYERS[playerCount] = player;
            playerCount++;
            System.out.print("Do you want to add another player? (y/n) ");
            String answer = scanner.nextLine().toLowerCase();

            if (answer.equals("n"))
                addNewPlayer = false;
        }

        printPlayers();
    }

    /**
     * This will print the players of the game
     */
    public static void printPlayers() {
        System.out.print("The players are: ");
        int index = 0;

        while (index < PLAYERS.length && PLAYERS[index] != null) {
            System.out.print(PLAYERS[index].getName());

            if (index != PLAYERS.length - 1 && PLAYERS[index + 1] != null)
                System.out.print(", ");

            index++;
        }
    }
}
