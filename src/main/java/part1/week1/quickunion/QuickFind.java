package part1.week1.quickunion;

import java.util.Arrays;
import java.util.stream.IntStream;

public class QuickFind implements FindUnionProblem {

	private int[] ids;

	public QuickFind(final int count) {

		this.ids = IntStream.range(0, count).toArray();
	}

	public void union(final int a, final int b) {

		if (ids[a] == ids[b]) {
			return;
		}

		int keyA = ids[a];
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] == keyA) {
				ids[i] = ids[b];
			}
		}
	}

	public boolean connected(final int a, final int b) {

		return this.ids[a] == this.ids[b];
	}

	@Override
	public String toString() {

		return Arrays.toString(ids);
	}
}
