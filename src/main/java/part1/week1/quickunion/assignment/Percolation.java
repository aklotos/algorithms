/*
 * (c) Copyright 2016 EVRYTHNG Ltd London / Zurich
 * www.evrythng.com
 */
package part1.week1.quickunion.assignment;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.stream.IntStream;

public class Percolation {

	private final int N;
	private final int top;
	private final int bottom;
	private final int[] sites;
	private final WeightedQuickUnionUF unionFind;

	public Percolation(int N) {

		if (N < 0) {
			throw new IllegalArgumentException("N < 0");
		}
		this.N = N;
		this.top = 0;
		this.bottom = N * N + 1;

		this.unionFind = new WeightedQuickUnionUF(N * N + 2);
		this.sites = IntStream.iterate(0, i -> 0).limit(N * N + 2).toArray();
		this.sites[top] = 1;
		this.sites[bottom] = 1;
	}

	public static void main(String[] args) {

		int N = 1000;
		Percolation p = new Percolation(N);
		int opened = 0;
		while (!p.percolates()) {
			int i = StdRandom.uniform(1, N + 1);
			int j = StdRandom.uniform(1, N + 1);

			if (!p.isOpen(i, j)) {
				p.open(i, j);
				opened++;
			}
		}
		System.out.println("p.percolationProbability() = " + (double) opened / (N * N));
	}

	public void open(final int i, final int j) {

		if (i < 1 || i > N || j < 1 || j > N) {
			throw new IndexOutOfBoundsException(String.format("i: %d, j: %d must belong [1..%d]", i, j, this.N));
		}

		if (isOpen(i, j)) {
			return;
		}

		int siteIndex = index(i, j);
		this.sites[siteIndex] = 1;

		if (i == 1) { // top
			unionFind.union(top, siteIndex);
		} else if (i == N) { // bottom
			unionFind.union(bottom, siteIndex);
		}
		if (i > 1 && isOpen(i - 1, j)) {  // up
			unionFind.union(siteIndex, index(i - 1, j));
		}
		if (i < N && isOpen(i + 1, j)) { // down
			unionFind.union(siteIndex, index(i + 1, j));
		}
		if (j > 1 && isOpen(i, j - 1)) { // left
			unionFind.union(siteIndex, index(i, j - 1));
		}
		if (j < N && isOpen(i, j + 1)) { // right
			unionFind.union(siteIndex, index(i, j + 1));
		}
	}

	private int index(final int i, final int j) {

		return (i - 1) * N + j;
	}

	public boolean isOpen(final int i, final int j) {

		if (i < 1 || i > N || j < 1 || j > N) {
			throw new IndexOutOfBoundsException(String.format("i: %d, j: %d must belong [1..%d]", i, j, this.N));
		}

		return this.sites[index(i, j)] == 1;
	}

	public boolean isFull(final int i, final int j) {

		return unionFind.connected(index(i, j), top);
	}

	public boolean percolates() {

		return unionFind.connected(top, bottom);
	}

}
