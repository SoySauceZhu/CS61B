package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.lab5.RandomWorldDemo;

public class Test {
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80,35);

        Map map = new Map(80,35);

        TETile[][] tiles = map.initialize();


        ter.renderFrame(tiles);
    }
}
