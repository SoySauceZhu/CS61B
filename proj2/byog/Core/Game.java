package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private final Font bigFont = new Font("Monaco", Font.BOLD, 20);
    private final Font smallFont = new Font("Monaco", Font.PLAIN, 15);

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        drawStart();

        char startKey = solicitKey();

        Map map = new Map(WIDTH, HEIGHT);

        if (startKey == 'n' || startKey == 'N') {
            map.initialize();
        }

        if (startKey == 'l' || startKey == 'L') {
            map = loadMap();
        }

        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(map.getFloorTiles());

        while (true) {
            char key = solicitKey();

            if (key == ':') {
                char[] optionKeys = optionKeys();
                if (optionKeys[0] == 'q' && optionKeys[1] == 10) {
                    break;
                }

                if (optionKeys[0] == 'w' && optionKeys[1] == 10) {
                    saveMap(map);
                }

                if (optionKeys[0] == 'w' && optionKeys[1] == 'q' && optionKeys[2] == 10) {
                    saveMap(map);
                    break;
                }

            } else {
                map.control(key);
                TETile[][] tiles = map.getFloorTiles();
                ter.renderFrame(tiles);

            }

        }
        playWithKeyboard();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {

        int seed = parseSeed(input);
        char start = parseStart(input);
        char[] control = parseControl(input);
        Map map;

        // N for new game
        if (start == 'n') {
            // Crete new map and init
            map = new Map(WIDTH, HEIGHT);
            map.setRandom(seed);
            map.initialize();

            controlMap(map, control);
            return map.getFloorTiles();         // reminder: getFloorTiles() will compress the world map and the player, while keep the world unchanged
        }

        if (start == 'l') {
            map = loadMap();

            controlMap(map, control);
            return map.getFloorTiles();         // reminder: getFloorTiles() will compress the world map and the player, while keep the world unchanged
        }

        if (start == 'e') {
            System.exit(0);
        }

        return null;
    }

    private char[] optionKeys() {
        char[] combo = new char[100];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            char option = solicitKey();
            sb.append(option);
            combo[i] = option;
            if (option == 10) {
                break;
            }
            if (option == '=') {
                combo = new char[100];
                break;
            }

            showInput(sb.toString());
        }
        return combo;
    }

    private void showInput(String str) {
        StdDraw.setFont(smallFont);
        StdDraw.textLeft(0, 10, "Map v0.1   Game v0.1");
    }

    // control the players' move through Map's api `control`
    private static void controlMap(Map map, char[] control) {
        for (int i = 0; i < control.length; i++) {
            char step = control[i];
            if (step == 'a' || step == 'w' || step == 's' || step == 'd') {
                map.control(step);
            }

            if (step == ':' && control[++i] == 'q') {
                saveMap(map);
                return;
            }
        }
    }

    private static int parseSeed(String input) {
        input = input.replaceAll("[^0-9]", "");
        return (input.isEmpty()) ? 42 : Integer.parseInt(input);
    }

    private static char parseStart(String input) {
        return input.toLowerCase().charAt(0);
    }

    private static char[] parseControl(String input) {
        input = input.toLowerCase().replaceAll("[0-9]", "");
        return input.substring(1).toCharArray();
    }

    private static void saveMap(Map map) {
        File f = new File("./map.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(map);
            os.close();
        } catch (IOException e) {
            System.out.println("file not found");
            System.exit(0);
        }
    }

    private static Map loadMap() {
        File f = new File("./map.ser");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                Map map = (Map) os.readObject();
                os.close();
                return map;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        Map map = new Map(WIDTH, HEIGHT);
        map.initialize();
        return map;
    }

    private static char solicitKey() {
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            return (StdDraw.nextKeyTyped());
        }
    }

    private void drawStart() {
        int width = 60 * 8;
        int height = 75 * 8;
        StdDraw.setCanvasSize(width, height);
        StdDraw.clear(Color.black);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.enableDoubleBuffering();

        int midWidth = width / 2;
        int midHeight = height / 2;

        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, "(N) New Game");
        StdDraw.text(midWidth, midHeight - 25, "(L) Load Game");
        StdDraw.setFont(smallFont);
        StdDraw.textLeft(0, 10, "Map v0.1   Game v0.1");
        StdDraw.show();

    }

}
