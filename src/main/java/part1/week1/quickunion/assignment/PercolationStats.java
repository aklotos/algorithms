/*
 * (c) Copyright 2016 EVRYTHNG Ltd London / Zurich
 * www.evrythng.com
 */
package part1.week1.quickunion.assignment;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private final double[] probabilities;
	private final int T;

	public PercolationStats(final int N, final int T) {

		if (N < 0 || T < 0) {
			throw new IllegalArgumentException("N and T args can not be negative");
		}

		this.T = T;
		this.probabilities = new double[T];

		for (int k = 0; k < T; k++) {
			Percolation p = new Percolation(N);
			while (!p.percolates()) {
				int i = StdRandom.uniform(1, N + 1);
				int j = StdRandom.uniform(1, N + 1);

				p.open(i, j);
			}
			this.probabilities[k] = p.percolationProbability();
		}
	}

	public double mean() {

		return StdStats.mean(this.probabilities);
	}

	public double stddev() {

		return StdStats.stddev(this.probabilities);
	}

	public double confidenceLo() {

		return mean() - (1.96 * stddev()) / Math.sqrt(this.T);
	}

	public double confidenceHi() {

		return mean() + (1.96 * stddev()) / Math.sqrt(this.T);
	}

	public static void main(String[] args) {

		if (args.length != 2) {
			throw new IllegalArgumentException("Expected 2 args: N and T");
		}

		int N = Integer.valueOf(args[0]);
		int T = Integer.valueOf(args[1]);

		PercolationStats ps = new PercolationStats(N, T);
		System.out.println("mean\t\t\t\t= " + ps.mean());
		System.out.println("stddev\t\t\t\t= " + ps.stddev());
		System.out.println("95% confidence interval\t= " + ps.confidenceLo() + ", " + ps.confidenceHi());
	}
}