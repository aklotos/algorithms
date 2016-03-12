package part1.week3.sorts;

import edu.princeton.cs.algs4.StdRandom;
import part1.week2.sorts.SortUtils;

public class QuickSort {

	public static <T extends Comparable<T>> void sort(final T[] array) {

		StdRandom.shuffle(array);
		partition(array, 0, array.length - 1);
	}

	private static <T extends Comparable<T>> void partition(final T[] array, int low, int high) {

		if (low >= high) {
			return;
		}

		final int oldLow = low, oldHigh = high;

		int i = low + 1;
		while (i <= high) {
			int cmp = array[i].compareTo(array[low]);
			if (cmp > 0) {
				SortUtils.swap(array, i, high--);
			} else if (cmp < 0) {
				SortUtils.swap(array, i++, low++);
			} else {
				i++;
			}
		}

		partition(array, oldLow, low - 1);
		partition(array, high + 1, oldHigh);
	}

}
