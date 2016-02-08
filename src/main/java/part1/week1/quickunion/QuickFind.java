package part1.week1.quickunion;

import java.util.Arrays;
import java.util.stream.IntStream;

public class QuickFind implements FindUnionProblem {

	private int[] keys;

	public QuickFind(final int count) {

		this.keys = IntStream.range(0, count).toArray();
		//        System.out.println(this.toString());
	}

	public void union(final int a, final int b) {

		if (keys[a] == keys[b]) {
			return;
		}

		int keyA = keys[a];
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == keyA) {
				keys[i] = keys[b];
			}
		}

		//        System.out.println(this.toString());
	}

	public boolean connected(final int a, final int b) {

		return this.keys[a] == this.keys[b];
	}

	@Override
	public String toString() {

		return Arrays.toString(keys);
	}
}
