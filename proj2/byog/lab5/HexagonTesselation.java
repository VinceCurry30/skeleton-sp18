package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class HexagonTesselation {
    private static final int WIDTH = 45;
    private static final int HEIGHT = 45;

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

    //Only for 19 total hexagons.
    private static int numberPerColumn(int column) {
        if (column == 0 || column == 4) {
            return 3;
        } else if (column == 2) {
            return 5;
        } else {
            return 4;
        }
    }

    private static void drawAColumn(int size, int column, TETile[][] tiles, int positionX, int positionY) {
        int number = numberPerColumn(column);
        for (int i = 0; i < number; i++) {
            AddSingleHexagon.addHexagon(size, tiles, positionX, positionY, randomTile());
            positionY += (2 * size);
        }
    }

    public static void drawTesselation(int size, TETile[][] tiles, int positionX, int positionY) {
        positionY += 2 * size;
        for (int i = 0; i < 5; i++) {
            drawAColumn(size, i, tiles, positionX, positionY);
            positionX += (2 * size - 1);
            if (i < 2) {
                positionY -= size;
            } else {
                positionY += size;
            }
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

        drawTesselation(4, world, 2, 2);
        ter.renderFrame(world);
    }
}
