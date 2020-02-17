package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import java.lang.Math;

public class PercolationStats {
    private int count;
    private Percolation[] perco;
    private double[] openfraction;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        count = T;
        perco = new Percolation[T];
        for (Percolation p : perco) {
            p = pf.make(N);
        }
        openfraction = new double[T];
        for (int i = 0; i < T; i++) {
            while (!perco[i].percolates()) {
                perco[i].open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            openfraction[i] = perco[i].numberOfOpenSites() * 1.0 / (N * N * 1.0);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openfraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openfraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(count);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(count);
    }
}
