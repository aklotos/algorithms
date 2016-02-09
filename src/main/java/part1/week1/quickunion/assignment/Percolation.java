package part1.week1.quickunion.assignment;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private final int N;
	private final int top;
	private final int bottom;
	private final WeightedQuickUnionUF unionFind;
	private final int[] rootsFull;
	private final int[] sizeFull;

	public Percolation(final int N) {

		if (N < 1) {
			throw new IllegalArgumentException("N < 1");
		}
		this.N = N;
		int n2 = N * N;
		this.top = 0;
		this.bottom = n2 + 1;

		this.unionFind = new WeightedQuickUnionUF(n2 + 2);

		this.rootsFull = new int[n2 + 1];
		this.sizeFull = new int[n2 + 1];
		for (int i = 0; i < n2 + 1; i++) {
			this.rootsFull[i] = i;
			this.sizeFull[i] = 1;
		}
	}

	public static void main(final String[] args) {

		int N = 200;
		Percolation p = new Percolation(N);
		int opened = 0;
		while (!p.percolates()) {
			int i = StdRandom.uniform(1, N + 1);
			int j = StdRandom.uniform(1, N + 1);

			if (!p.isOpen(i, j)) {
				p.open(i, j);
				opened++;
			}
			System.out.println("p.percolationProbability() = " + (double) opened / (N * N));
		}
	}

	public void open(final int i, final int j) {

		if (i < 1 || i > N || j < 1 || j > N) {
			throw new IndexOutOfBoundsException(String.format("i: %d, j: %d must belong [1..%d]", i, j, this.N));
		}

		if (isOpen(i, j)) {
			return;
		}

		int siteIndex = index(i, j);
		sizeFull[siteIndex]++; // this ugly workaround fix made to make isOpen work without additional memory

		if (i == 1) { // top
			unionFind.union(top, siteIndex);
			unionFull(top, siteIndex);
		}
		if (i == N) { // bottom
			unionFind.union(bottom, siteIndex);
		}
		if (i > 1 && isOpen(i - 1, j)) {  // up
			unionFind.union(siteIndex, index(i - 1, j));
			unionFull(siteIndex, index(i - 1, j));
		}
		if (i < N && isOpen(i + 1, j)) { // down
			unionFind.union(siteIndex, index(i + 1, j));
			unionFull(siteIndex, index(i + 1, j));
		}
		if (j > 1 && isOpen(i, j - 1)) { // left
			unionFind.union(siteIndex, index(i, j - 1));
			unionFull(siteIndex, index(i, j - 1));
		}
		if (j < N && isOpen(i, j + 1)) { // right
			unionFind.union(siteIndex, index(i, j + 1));
			unionFull(siteIndex, index(i, j + 1));
		}
	}

	private int index(final int i, final int j) {

		return (i - 1) * N + j;
	}

	public boolean isOpen(final int i, final int j) {

		if (i < 1 || i > N || j < 1 || j > N) {
			throw new IndexOutOfBoundsException(String.format("i: %d, j: %d must belong [1..%d]", i, j, this.N));
		}

		return rootFull(index(i, j)) != index(i, j) || sizeFull[index(i, j)] != 1;
	}

	public boolean isFull(final int i, final int j) {

		if (i < 1 || i > N || j < 1 || j > N) {
			throw new IndexOutOfBoundsException(String.format("i: %d, j: %d must belong [1..%d]", i, j, this.N));
		}

		return isOpen(i, j) && connectedFull(index(i, j), top);
	}

	public boolean percolates() {

		return unionFind.connected(top, bottom);
	}

	private int rootFull(int node) {

		while (node != rootsFull[node]) {
			rootsFull[node] = rootsFull[rootsFull[node]];
			node = rootsFull[node];
		}
		return node;
	}

	private void unionFull(final int a, final int b) {

		int rootA = rootFull(a);
		int rootB = rootFull(b);
		if (sizeFull[rootA] < sizeFull[rootB]) {
			rootsFull[rootA] = rootB;
			sizeFull[rootB] += sizeFull[rootA];
		} else {
			rootsFull[rootB] = rootA;
			sizeFull[rootA] += sizeFull[rootB];
		}
	}

	private boolean connectedFull(final int a, final int b) {

		return rootFull(a) == rootFull(b);
	}

}
