package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class AddSingleHexagon {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            default: return Tileset.MOUNTAIN;
        }
    }

    private static int numberPerRow(int size, int row) {
        /*First row is row 0.*/
        if (row >= size) {
            row = (2 * size - 1) - row;
        }
        return size + 2 * row;
    }

    private static void drawARow(int size, int row, TETile[][] tiles, int positionX, int positionY, TETile tile) {
        /*Maximum number per row is 3 * size - 2.*/
        int number = numberPerRow(size, row);
        int startingPosition = (3 * size - 2 - number) / 2;
        for (int i = startingPosition; i < startingPosition + number; i++) {
            tiles[i + positionX][row + positionY] = tile;
        }
    }

    public static void addHexagon(int size, TETile[][] tiles, int positionX, int positionY, TETile tile) {
        for (int i = 0; i < 2 * size; i++) {
            drawARow(size, i, tiles, positionX, positionY, tile);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        addHexagon(4, world, 3, 1, randomTile());
        ter.renderFrame(world);
    }
}
