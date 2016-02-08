/*
 * (c) Copyright 2016 EVRYTHNG Ltd London / Zurich
 * www.evrythng.com
 */

package part1.week1.quickunion;

import java.util.Arrays;
import java.util.stream.IntStream;

public class QuickUnionWeighted implements FindUnionProblem {

	private int[] roots;
	private int[] size;

	public QuickUnionWeighted(final int count) {

		this.roots = IntStream.range(0, count).toArray();
		this.size = IntStream.iterate(1, i -> 1).limit(count).toArray();
	}

	public int root(int node) {

		while (node != roots[node]) {
			node = roots[node];
		}
		return node;
	}

	public void union(final int a, final int b) {

		int rootA = root(a);
		int rootB = root(b);
		if (size[rootA] < size[rootB]) {
			roots[rootA] = rootB;
			size[rootB] += size[rootA];
		} else {
			roots[rootB] = rootA;
			size[rootA] += size[rootB];
		}
	}

	public boolean connected(final int a, final int b) {

		return root(a) == root(b);
	}

	@Override
	public String toString() {

		return Arrays.toString(roots);
	}
}
