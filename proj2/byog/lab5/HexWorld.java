package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    static public void helper(TETile[][] world, int x0, int y0, int rows, TETile tile) {
        if (rows % 2 == 1) throw new IllegalArgumentException("Rows should be even");

        for (int y = y0; y < y0 + rows; y++) {
            for (int x = x0; x < x0 + rows + 1; x++) {
                if (x - x0 + y - y0 > rows / 2 - 2
                        && y - y0 - x + x0 < 1 + rows / 2
                        && y - y0 + x - x0 < 3 * rows / 2 + 1
                        && x - x0 - y + y0 < 2 + rows / 2) {
                    world[x][y] = tile;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // initialize the tile rendering engine
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize background tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }


        while (true) {

            // draw a hexagon
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    helper(world, j * 10, i * 6, 6, RandomWorldDemo.randomTile());
                }
            }

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 2; j++) {
                    helper(world, j * 10 + 5, i * 6 + 3, 6, RandomWorldDemo.randomTile());
                }

            }


            // draw the world tiles
            ter.renderFrame(world);

            Thread.sleep(100);
        }


    }
}
