package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*TODO: Replace every TETile[][] argument with instance variable*/

public class Map {
    private final TETile[][] floorTiles;
    private final int WIDTH;
    private final int HEIGHT;

    Random random = new Random();

    public Map(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        floorTiles = new TETile[WIDTH][HEIGHT];
    }

    private static class Position {
        protected int X;
        protected int Y;

        public Position(int x, int y) {
            X = x;
            Y = y;
        }

        protected void goUp() {
            Y++;
        }

        protected void goRight() {
            X++;
        }

        protected void goLeft() {
            X--;
        }

        protected void goDown() {
            Y--;
        }
    }

    private static class Room {
        protected int width;
        protected int height;
        protected int X;
        protected int Y;

        public Room(int width, int height, int x, int y) {
            this.width = width;
            this.height = height;
            X = x;
            Y = y;
        }
    }

    public TETile[][] initialize() {
        // TODO: Create a random world map

        int roomNum = RandomUtils.uniform(random, 22, 27);

        Room[] roomsList = createRoomScheme(roomNum);

        drawRooms(roomsList, floorTiles);

        fillWithNothing(floorTiles);

//        linkRooms(roomsList, floorTiles);

        return floorTiles;
    }

    private void linkRooms(Room[] rooms, TETile[][] tiles) {
        Room home = rooms[RandomUtils.uniform(random, rooms.length)];

        int[][] digitMap = tilesToDigitMap(tiles);

        for (Room room : rooms) {
            // if room (tile0 of a room) is NOT connected to home (tile0 of home), link the room to one of adjacent floor tile
        }

    }
/*
    private boolean isConnected(Position A, Position B, int[][] digitMap) {
        Position ptr = B;

        while (detectTrue(B, digitMap) == -1) {
            digitMap[ptr.X][ptr.Y] = 1;     // 1 means accessible
            switch () {
                case 0:
                    ptr.goUp();
                case 1:
                    ptr.goRight();
                case 2:
                    ptr.goDown();
                case 3:
                    ptr.goLeft();
                default:
                    switch () {
                        case 0:
                    }

            }
        }
    }
 */

    private int detectFloor(Position pos, int[][] digitMap) {
        if (pos.Y < digitMap[0].length - 1) {
            if (digitMap[pos.X][pos.Y + 1] == 0) return 0;
        } else if (pos.X < digitMap.length - 1) {
            if (digitMap[pos.X + 1][pos.Y] == 0) return 1;
        } else if (pos.Y > 0) {
            if (digitMap[pos.X][pos.Y - 1] == 0) return 2;
        } else if (pos.X > 0) {
            if (digitMap[pos.X - 1][pos.Y] == 0) return 3;
        }
        return -1;
    }

    private int detectTrue(Position pos, int[][] digitMap) {
        if (pos.X > 0) {
            if (digitMap[pos.X - 1][pos.Y] == 1) return 3;
        } else if (pos.Y > 0) {
            if (digitMap[pos.X][pos.Y - 1] == 1) return 2;
        } else if (pos.X < digitMap.length - 1) {
            if (digitMap[pos.X + 1][pos.Y] == 1) return 1;
        } else if (pos.Y < digitMap[0].length - 1) {
            if (digitMap[pos.X][pos.Y + 1] == 1) return 0;
        }
        return -1;
    }

    private int[][] tilesToDigitMap(TETile[][] tiles) {

        int[][] digitMap = new int[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (tiles[x][y].description().equals("nothing")) {
                    digitMap[x][y] = -1;        // -1 means nothing
                } else {
                    digitMap[x][y] = 0;         // 0 means unchecked floor
                }
            }
        }
        return digitMap;

    }

    // Update the given TETile[][]
    private void linkTwoRoom(Room A, Room B, TETile[][] tiles) {

    }


    private void fillWithNothing(TETile[][] tiles) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (tiles[i][j] == null) {
                    tiles[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    // Return a list of room instances
    private Room[] createRoomScheme(int roomNum) {
        Room[] rooms = new Room[roomNum];

        boolean[][] roomMap = new boolean[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                roomMap[i][j] = true;
            }
        }

        blockBanner(roomMap, WIDTH - 11, 0, 11, HEIGHT);
        blockBanner(roomMap, 0, HEIGHT - 11, WIDTH, 11);
        blockBanner(roomMap, 0, 0, WIDTH, 1);
        blockBanner(roomMap, 0, 0, 1, HEIGHT);

        for (int i = 0; i < roomNum; i++) {
            List<Position> truePosition = booleanMapToList(roomMap);
            Position position = truePosition.get(RandomUtils.uniform(random, 0, truePosition.size()));
            int randX = position.X;
            int randY = position.Y;
            int randWidth = RandomUtils.uniform(random, 2, 10);
            int randHeight = RandomUtils.uniform(random, 2, 10);

            blockBanner(roomMap, randX, randY, randWidth, randHeight);
            rooms[i] = new Room(randWidth, randHeight, randX, randY);
        }
        return rooms;
    }


    private List<Position> booleanMapToList(boolean[][] map) {
        List<Position> list = new ArrayList<>();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (map[x][y]) {
                    list.add(new Position(x, y));
                }
            }
        }

        return list;
    }

    private void blockBanner(boolean[][] map, int X, int Y, int width, int height) {


        for (int i = X; i < X + width; i++) {
            for (int j = Y; j < Y + height; j++) {
                map[i][j] = false;
            }
        }
    }

    private void drawRooms(Room[] rooms, TETile[][] tiles) {
        for (Room room : rooms) {
            for (int x = room.X; x < room.width + room.X; x++) {
                for (int y = room.Y; y < room.height + room.Y; y++) {
                    tiles[x][y] = Tileset.UNLOCKED_DOOR;
                }
            }
        }
    }


    public void control() {
        // TODO: Get the control signal, updating the floorTile set

    }

}