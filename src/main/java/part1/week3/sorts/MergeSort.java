package part1.week3.sorts;

import part1.week2.sorts.InsertionSort;

public class MergeSort {

	private static final int CUTOFF = 7;

	public static <T extends Comparable<T>> void sort(final T[] array) {

		T[] temp = (T[]) new Comparable[array.length];
		sort(array, temp, 0, array.length - 1);
	}

	private static <T extends Comparable<T>> void sort(final T[] array, final T[] temp, final int low, final int high) {

		if (low >= high) {
			return;
		}
		if (high - low <= CUTOFF) {
			InsertionSort.sort(array, low, high);
			return;
		}

		int mid = low + (high - low) / 2;
		sort(array, temp, low, mid);
		sort(array, temp, mid + 1, high);
		if (array[mid].compareTo(array[mid + 1]) <= 0) {
			return;
		}
		merge(array, temp, low, mid, high);
	}

	private static <T extends Comparable<T>> void merge(final T[] array, final T[] temp, final int low, final int mid,
	                                             final int high) {
		System.arraycopy(array, low, temp, low, high - low + 1);

		int i = low, j = mid + 1;
		for (int k = low; k <= high; k++) {
			if (i > mid) {
				array[k] = temp[j++];
			} else if (j > high) {
				array[k] = temp[i++];
			} else if (temp[i].compareTo(temp[j]) <= 0) {
				array[k] = temp[i++];
			} else {
				array[k] = temp[j++];
			}
		}
	}
}
