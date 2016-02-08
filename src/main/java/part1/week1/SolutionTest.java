package part1.week1;

import org.junit.Assert;
import org.junit.Test;
import part1.week1.quickunion.FindUnionProblem;
import part1.week1.quickunion.QuickFind;
import part1.week1.quickunion.QuickUnion;
import part1.week1.quickunion.QuickUnionWeighted;
import part1.week1.quickunion.QuickUnionWeightedImproved;

import java.util.Random;

public class SolutionTest {

	private static final Random R = new Random();

	@Test
	public void question1() {
		FindUnionProblem p = new QuickFind(10);
		p.union(8, 3);
		p.union(9, 4);
		p.union(0, 7);
		p.union(2, 4);
		p.union(1, 7);
		p.union(1, 2);

		System.out.println("p = " + p);
	}

	@Test
	public void question2() {
		FindUnionProblem p = new QuickUnionWeighted(10);
		p.union(3, 1);
		p.union(2, 8);
		p.union(7, 9);
		p.union(2, 4);
		p.union(1, 9);
		p.union(4, 0);
		p.union(6, 2);
		p.union(3, 6);
		p.union(1, 5);

		System.out.println("p = " + p);
	}

	@Test
	public void testProblem() {

		int count = 500;
		FindUnionProblem p1 = new QuickFind(count);
		FindUnionProblem p2 = new QuickUnion(count);
		FindUnionProblem p3 = new QuickUnionWeighted(count);
		FindUnionProblem p4 = new QuickUnionWeightedImproved(count);

		for (int k = 0; k < count; k++) {
			int a = R.nextInt(count);
			int b = R.nextInt(count);

			p1.union(a, b);
			p2.union(a, b);
			p3.union(a, b);
			p4.union(a, b);

			for (int i = 0; i < count; i++) {
				for (int j = i; j < count; j++) {
					Assert.assertEquals(p1.connected(i, j), p4.connected(i, j));
					Assert.assertEquals(p2.connected(i, j), p4.connected(i, j));
					Assert.assertEquals(p3.connected(i, j), p4.connected(i, j));
				}
			}
		}
	}

	@Test
	public void testSpeed() {

		int size = 10000;
		int unions = 10000;
		int finds = 10000;

		long p1Union = 0, p2Union = 0, p3Union = 0, p4Union = 0;
		long p1Find = 0, p2Find = 0, p3Find = 0, p4Find = 0;
		for (int times = 0; times < 100; times++) {
			int[] unionPairs = generatePairs(unions, size);
			int[] findPairs = generatePairs(finds, size);

			FindUnionProblem p1 = new QuickFind(size);
			FindUnionProblem p2 = new QuickUnion(size);
			FindUnionProblem p3 = new QuickUnionWeighted(size);
			FindUnionProblem p4 = new QuickUnionWeightedImproved(size);

			p1Union += measureUnion(p1, unionPairs);
			p1Find += measureFind(p1, findPairs);
			p2Union += measureUnion(p2, unionPairs);
			p2Find += measureFind(p2, findPairs);
			p3Union += measureUnion(p3, unionPairs);
			p3Find += measureFind(p3, findPairs);
			p4Union += measureUnion(p4, unionPairs);
			p4Find += measureFind(p4, findPairs);
		}

		System.out.println(String.format("quickFind\t\t\t\ttotal ~ %d ns\t[union ~ %d ns,\tfind ~ %d ns]\n" +
				                                 "quickUnion\t\t\t\ttotal ~ %d ns\t[union ~ %d ns,\tfind ~ %d ns]\n" +
				                                 "quickUnionWeighted\t\ttotal ~ %d ns\t[union ~ %d ns,\tfind ~ %d ns]\n" +
				                                 "quickUnionWeightedImpr\ttotal ~ %d ns\t[union ~ %d ns,\tfind ~ %d ns]",
		                                 p1Union + p1Find, p1Union, p1Find, p2Union + p2Find, p2Union, p2Find, p3Union + p3Find,
		                                 p3Union, p3Find, p4Union + p4Find, p4Union, p4Find));
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
