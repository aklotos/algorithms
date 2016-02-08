package part1.week1;

import org.junit.Assert;
import org.junit.Test;
import part1.week1.quickunion.FindUnionProblem;
import part1.week1.quickunion.QuickFind;
import part1.week1.quickunion.QuickUnion;
import part1.week1.quickunion.QuickUnionImproved;

import java.util.Random;
import java.util.stream.Stream;

public class App {

	private static final Random R = new Random();

	@Test
	public void testProblem() {

		int count = 500;
		FindUnionProblem p1 = new QuickFind(count);
		FindUnionProblem p2 = new QuickUnion(count);
		FindUnionProblem p3 = new QuickUnionImproved(count);

		for (int k = 0; k < count; k++) {
			int a = R.nextInt(count);
			int b = R.nextInt(count);

			p1.union(a, b);
			p2.union(a, b);
			p3.union(a, b);

			for (int i = 0; i < count; i++) {
				for (int j = i; j < count; j++) {
					Assert.assertEquals(p1.connected(i, j), p2.connected(i, j));
					Assert.assertEquals(p1.connected(i, j), p3.connected(i, j));
				}
			}
		}
	}

	@Test
	public void testSpeed() {

		int size = 10000;
		int unions = 10000;
		int finds = 10000;

		long p1Union = 0, p2Union = 0, p3Union = 0;
		long p1Find = 0, p2Find = 0, p3Find = 0;
		for (int times = 0; times < 100; times++) {
			int[] unionPairs = generatePairs(unions, size);
			int[] findPairs = generatePairs(finds, size);

			FindUnionProblem p1 = new QuickFind(size), p2 = new QuickUnion(size), p3 = new QuickUnionImproved(size);

			p1Union += measureUnion(p1, unionPairs);
			p1Find += measureFind(p1, findPairs);
			p2Union += measureUnion(p2, unionPairs);
			p2Find += measureFind(p2, findPairs);
			p3Union += measureUnion(p3, unionPairs);
			p3Find += measureFind(p3, findPairs);
		}

		System.out.println(String.format("quickFind\t\ttotal ~ %d ns\t[union ~ %d ns,\tfind ~ %d ns]\n" +
				                                 "quickUnion\t\ttotal ~ %d ns\t[union ~ %d ns,\tfind ~ %d ns]\n" +
				                                 "quickUnionImpr\ttotal ~ %d ns\t[union ~ %d ns,\tfind ~ %d ns]", p1Union +
				p1Find, p1Union, p1Find, p2Union + p2Find, p2Union, p2Find, p3Union + p3Find,
		                                 p3Union, p3Find));
	}

	private long measureUnion(final FindUnionProblem p, final int[] unionPairs) {

		long unionTime = 0;

		for (int i = 0; i < unionPairs.length; i += 2) {
			long start = System.nanoTime();
			p.union(unionPairs[i], unionPairs[i + 1]);
			unionTime = System.nanoTime() - start;
		}

		return unionTime;
	}

	private long measureFind(final FindUnionProblem p, final int[] findPairs) {

		long findTime = 0;

		for (int i = 0; i < findPairs.length; i += 2) {
			long start = System.nanoTime();
			p.connected(findPairs[i], findPairs[i + 1]);
			findTime = System.nanoTime() - start;
		}

		return findTime;
	}

	private int[] generatePairs(final int count, final int size) {

		int[] pairs = new int[count * 2];
		for (int i = 0; i < pairs.length; i++) {
			pairs[i] = R.nextInt(size);
		}
		return pairs;
	}
}
