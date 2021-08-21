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
    private static final int SPINNER = 6;           // The spinner will land between 1-6
    private static final int BOARD_SIZE = 100;      // The last square on the game board
    private static final int POSITIONS = 2;         // Represents the start and end positions
                                                    // of the chutes and ladders
    private static final int start = 0;             // The start position of chute/ladder
    private static final int end = 1;               // The end position of chute/ladder

    // 2D arrays to represent the number of chutes and ladders and their start and end positions
    private static int[][] ladders;
    private static int[][] chutes;

    private static Player[] players;    // Array to hold the players of the game

    /**
     * The start of the application
     *
     * @param args command line arguments
     * @throws FileNotFoundException if file does not exists
     * @throws InterruptedException if thread cannot sleep
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        start();
        playAgain();
    }

    /**
     * Prints out the welcome message
     */
    public static void welcome() {
        System.out.println("\nLet's play Chutes and Ladders!");
    }

    /**
     * This method starts the game
     *
     * @throws FileNotFoundException if file does not exists
     * @throws InterruptedException if thread cannot sleep
     */
    public static void start() throws FileNotFoundException, InterruptedException {
        welcome();
        createPlayers();
        buildLadders();
        buildChutes();
        spin();
    }

    /**
     * This will create players for the game
     *
     * @throws InterruptedException if thread cannot sleep
     */
    public static void createPlayers() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nBefore we can play, let's create players for the game:");
        Thread.sleep(2000);

        int numOfPlayers = getNumOfPlayers(scanner);    // Get the number of players from user
        players = new Player[numOfPlayers];             // Initialize the size of players array

        for (int i = 0; i < players.length; i++) {
            System.out.print("Name of Player " + (i + 1) + ": ");
            Player player = new Player(scanner.next());
            players[i] = player;
        }

        printPlayers();
    }

    /**
     * This method is to ask the user how many players will be playing.
     * The game allows for 2-4 players. Returns the number of players.
     *
     * @param scanner for user inputs
     * @return the number of players
     * @throws InterruptedException if thread cannot sleep
     */
    public static int getNumOfPlayers(Scanner scanner) throws InterruptedException {
        int numOfPlayers = 0;
        int minOfPlayers = 2, maxOfPlayers = 4; // Min and max of players allowed

        do {
            System.out.print("How many players will be playing? (Enter between 2-4) ");

            try {
                numOfPlayers = Integer.parseInt(scanner.next());

                if (numOfPlayers < minOfPlayers)
                    System.out.println("We need at least " + minOfPlayers + " players");
                else if (numOfPlayers > maxOfPlayers)
                    System.out.println(numOfPlayers + " is too many. The max is " + maxOfPlayers);

            } catch (NumberFormatException exception) {
                System.out.println("Sorry, let's try that again.");
                Thread.sleep(1000);
            }
            Thread.sleep(500);
        } while (numOfPlayers < minOfPlayers || numOfPlayers > maxOfPlayers);

        Thread.sleep(500);
        System.out.println(numOfPlayers + " players it is!");

        return numOfPlayers;
    }

    /**
     * This will print the players of the game
     */
    public static void printPlayers() {
        System.out.print("The players are: ");
        int index = 0;

        while (index < players.length && players[index] != null) {
            System.out.print(players[index].getName());

            if (index != players.length - 1 && players[index + 1] != null)
                System.out.print(", ");

            index++;
        }

        System.out.println();
    }

    /**
     * Players will take turns spinning
     *
     * @throws InterruptedException if thread cannot sleep
     */
    public static void spin() throws InterruptedException {
        Spinner spinner = new Spinner(SPINNER); // Create Spinner Instance
        boolean gameActive = true;              // Store game state
        String winner = "";                     // Store game winner
        int playerLocation;
        // Game logic
        while (gameActive) {
            // Have the players take turns
            for (Player player : players) {
                if (winner.equals("")) {
                    playerLocation = player.getPosition();  // Store the player's location
                    player.spin(spinner);
                    // Check player's position after spin
                    if (player.getPosition() > BOARD_SIZE) {
                        System.out.println("Sorry, you must land on exactly " + BOARD_SIZE);
                        player.setPosition(playerLocation);
                    } else {
                        checkLadder(player);
                        checkChute(player);
                    }

                    System.out.println(player.getName() + " position is: " + player.getPosition());
                }

                if (player.getPosition() == BOARD_SIZE) {
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
     * Builds the ladders of the game stored in Ladders.txt
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

    /**
     * Builds the chutes of the game stored in Chutes.txt
     *
     * @throws FileNotFoundException if file does not exists
     */
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

    /**
     * Checks if a player has landed on a ladder
     *
     * @param player the player
     */
    public static void checkLadder(Player player) {
        for (int[] ladder : ladders) {
            if (player.getPosition() == ladder[start]) {
                player.setPosition(ladder[end]);
                System.out.println(player.getName() + " landed on a ladder!");
            }
        }
    }

    /**
     * Checks if a player has landed on a chute
     *
     * @param player the player
     */
    public static void checkChute(Player player) {
        for (int[] chute : chutes) {
            if (player.getPosition() == chute[start]) {
                player.setPosition(chute[end]);
                System.out.println(player.getName() + " landed on a chute!");
            }
        }
    }

    /**
     * Prompts the user to play again
     *
     * @throws FileNotFoundException if files does not exists
     * @throws InterruptedException if thread cannot sleep
     */
    public static void playAgain() throws FileNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to play again? (y/n) ");
        String answer = scanner.next().toLowerCase();
        if (answer.equals("y"))
            start();
        else
            goodbye();
    }

    /**
     * Prints the end of game
     *
     * @throws InterruptedException if thread cannot sleep
     */
    public static void goodbye() throws InterruptedException {
        Thread.sleep(500);
        System.out.println("Thanks for playing Chutes and Ladders!");
    }
}