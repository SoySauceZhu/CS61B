package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Test {
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 35);

        Map map = new Map(80, 35);

        map.initialize();

        TETile[][] tiles = map.getFloorTiles();


        ter.renderFrame(tiles);

    }
}
