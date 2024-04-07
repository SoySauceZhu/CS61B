package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        // Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char ch = (char) (rand.nextInt(26) + 65);
            sb.append(ch);
        }
        return sb.toString();
    }

    public void drawFrame(String s) {
        // Take the string and display it in the center of the screen
        int midWidth = width / 2;
        int midHeight = height / 2;

        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);

        // If game is not over, display relevant game information at the top of the screen
        if (!gameOver) {
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            StdDraw.text(midWidth, height - 1, playerTurn ? "Type!" : "Watch!");
        }

        StdDraw.text(midWidth, midHeight, s);

        StdDraw.show();
    }

    public void flashSequence(String letters) {
        // Display each character in letters, making sure to blank the screen between letters
        String[] strings = letters.split("");

        for (String str : strings) {
            drawFrame(str);
            StdDraw.pause(1000);
        }

    }

    public String solicitNCharsInput(int n) {
        // Read n letters of player input
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < n) {
            if (StdDraw.hasNextKeyTyped()) {
                i++;
                char key = StdDraw.nextKeyTyped();
                sb.append(key);
                drawFrame(sb.toString());
            }
        }
        StdDraw.pause(500);
        return sb.toString();
    }

    public void startGame() {
        gameOver = false;
        playerTurn = false;
        round = 1;

        while (!gameOver) {
            playerTurn = false;
            String str = generateRandomString(round);
            flashSequence(str);

            playerTurn = true;
            drawFrame("");
            String input = solicitNCharsInput(round);
            if (input.equals(str)) {
                drawFrame("Nice! Well Done !");
                StdDraw.pause(1500);
                round++;
            } else {
                gameOver = true;
                drawFrame("GameOver! Final round : " + round);
                String restart = solicitNCharsInput(1);
                if (!restart.isEmpty()) {
                    startGame();
                }
            }
        }

    }

}
