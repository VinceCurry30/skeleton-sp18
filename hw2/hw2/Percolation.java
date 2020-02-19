package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    // the value in grid represent the site is open(true) or blocked(false)
    private Boolean[][] grid;
    private WeightedQuickUnionUF DS;
    private WeightedQuickUnionUF dsWithVirtualTop;
    private int opensitesnumber;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        size = N;
        grid = new Boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = false;
            }
        }
        // last one is for virtual bottom site, second to last one is for virtual top site
        DS = new WeightedQuickUnionUF(size * size + 2);
        // avoid backwash
        dsWithVirtualTop = new WeightedQuickUnionUF(size * size + 1);
        opensitesnumber = 0;
    }

    private int xyTo1D(int r, int c) {
        return r * size + c;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;
        opensitesnumber += 1;
        int number = xyTo1D(row, col);
        if (row != 0) {
            if (isOpen(row - 1, col)) {
                DS.union(number, number - size);
                dsWithVirtualTop.union(number, number - size);
            }
        }
        if (row != size - 1) {
            if (isOpen(row + 1, col)) {
                DS.union(number, number + size);
                dsWithVirtualTop.union(number, number + size);
            }
        }
        if (col != 0) {
            if (isOpen(row, col - 1)) {
                DS.union(number, number - 1);
                dsWithVirtualTop.union(number, number - 1);
            }
        }
        if (col != size - 1) {
            if (isOpen(row, col + 1)) {
                DS.union(number, number + 1);
                dsWithVirtualTop.union(number, number + 1);
            }
        }

        // if open the site in the first row, union it with the virtual top site
        if (row == 0) {
            DS.union(number, size * size);
            dsWithVirtualTop.union(number, size * size);
        }
        // if open the site in the last row, union it with the virtual bottom site
        if (row == size - 1) {
            DS.union(number, size * size + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int number = xyTo1D(row, col);
        /*
        for (int i = 0; i < size; i++) {
            if (DS.connected(number, i) && isOpen(row, col)) {
                return true;
            }
        }
        return false;
        */
        return dsWithVirtualTop.connected(number, size * size);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return opensitesnumber;
    }

    // does the system percolate?
    public boolean percolates() {
        /*
        for (int i = 0; i < size; i++) {
            if (isFull(size - 1, i)) {
                return true;
            }
        }
        return false;
        */
        return DS.connected(size * size, size * size + 1);
    }

    public static void main(String[] args) {

    }
}
