package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int count;
    private double[] openfraction;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        count = T;
        openfraction = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation perco = pf.make(N);
            while (!perco.percolates()) {
                perco.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            openfraction[i] = perco.numberOfOpenSites() * 1.0 / (N * N * 1.0);
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
