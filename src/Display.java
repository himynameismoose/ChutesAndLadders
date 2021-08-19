import java.io.File;
import java.io.FileNotFoundException;
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
    private static final int POSITIONS = 2;                             // Represents the start and end positions
                                                                        // of the chutes and ladders
    private static final int start = 0;                                 // The start position of chute/ladder
    private static final int end = 1;                                   // The end position of chute/ladder

    // 2D arrays to represent the number of chutes and ladders and their start and end positions
    private static int[][] ladders;
    private static int[][] chutes;

    /**
     * This starts the application
     *
     * @param args command line string arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        start();
    }

    /**
     * Prints out the welcome message
     */
    public static void welcome() {
        System.out.println("\nLet's play Chutes and Ladders!");
    }

    /**
     * Starts the game
     *
     * @throws FileNotFoundException if file does not exists
     */
    public static void start() throws FileNotFoundException {
        welcome();
        createPlayers();
        buildLadders();
        buildChutes();
        spin();
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

    /**
     * Each player will take turns spinning until player reaches the 100 square
     */
    public static void spin() {
        Spinner spinner = new Spinner(SPINNER); // Create Spinner Instance
        boolean gameActive = true;              // Store game state
        String winner = "";                     // Store game winner
        // Game logic
        while (gameActive) {
            // Have the players take turns
            for (Player player : PLAYERS) {
                if (winner.equals("")) {
                    player.spin(spinner);
                    System.out.println(player.getName() + " position is: " + player.getPosition());
                }

                if (player.getPosition() > LAST_SQUARE) {
                    winner = player.getName();
                    gameActive = false;
                }
            }
        }

        System.out.println(winner + " is the winner!");
    }

    /**
     * Counts the number of lines in file and returns the number of lines in file
     *
     * @param file The name of the file
     *
     * @throws FileNotFoundException if file does not exists
     */
    public static int countLinesInFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        int lines = 0;
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            lines++;
        }

        return lines;
    }

    /**
     * Builds the ladders of the game stored in ladders.txt
     *
     * @throws FileNotFoundException if file does not exists
     */
    public static void buildLadders() throws FileNotFoundException {
        File file = new File("src/Ladders.txt");
        int laddersTotal = countLinesInFile(file);
        ladders = new int[laddersTotal][POSITIONS];

        Scanner scanner = new Scanner(file);
        int index = 0;

        while (scanner.hasNextLine()) {
            int ladderStart = scanner.nextInt();
            int ladderEnd = scanner.nextInt();

            ladders[index][start] = ladderStart;
            ladders[index][end] = ladderEnd;

            index++;
        }
    }

    public static void buildChutes() throws FileNotFoundException {
        File file = new File("src/Chutes.txt");
        int chutesTotal = countLinesInFile(file);
        chutes = new int[chutesTotal][POSITIONS];

        Scanner scanner = new Scanner(file);
        int index = 0;

        while (scanner.hasNextLine()) {
            int chuteStart = scanner.nextInt();
            int chuteEnd = scanner.nextInt();

            chutes[index][start] = chuteStart;
            chutes[index][end] = chuteEnd;

            index++;
        }
    }
}
