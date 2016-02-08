package part1.week1.quickunion;

import java.util.Arrays;
import java.util.stream.IntStream;

public class QuickUnion implements FindUnionProblem {

	private int[] roots;

	public QuickUnion(final int count) {

		this.roots = IntStream.range(0, count).toArray();
	}

	public int root(final int node) {

		int root = roots[node];
		while (root != roots[root]) {
			root = roots[root];
		}
		return root;
	}

	public void union(final int a, final int b) {

		int rootA = root(a);
		int rootB = root(b);
		roots[rootB] = rootA;

		//        System.out.println(this.toString());
	}

	public boolean connected(final int a, final int b) {

		return root(a) == root(b);
	}

	@Override
	public String toString() {

		return Arrays.toString(roots);
	}
}
