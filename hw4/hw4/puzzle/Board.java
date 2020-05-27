package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board implements WorldState {
    private int[][] board;
    private int size;

    public Board(int[][] tiles) {
        int[][] copy = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, copy[i], 0, tiles.length);
        }
        board = copy;
        size = tiles.length;
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > size - 1 || j < 0 || j > size - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return board[i][j];
    }

    public int size() {
        return size;
    }

    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != i * size + j + 1) {
                    count++;
                }
            }
        }
        for (int j = 0; j < size - 1; j++) {
            if (board[size - 1][j] != (size - 1) * size + j + 1) {
                count++;
            }
        }
        return count;
    }

    public int manhattan() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    continue;
                }
                int row = (board[i][j] - 1) / size;
                int col = (board[i][j] - 1) % size;
                count = count + Math.abs(row - i) + Math.abs(col - j);
            }
        }
        return count;
    }

    public int estimatedDistanceToGoal() {
        return this.manhattan();
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }
        Board b = (Board) y;
        if (this.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.tileAt(i, j) != b.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
