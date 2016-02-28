package part1.week2.sorts;

public class InsertionSort {

	public static <T extends Comparable<T>> void sort(final T[] array, final int low, final int high) {

		for (int i = low + 1; i <= high; i++) {
			for (int j = i; j > low; j--) {
				if (array[j - 1].compareTo(array[j]) > 0) {
					SortUtils.swap(array, j - 1, j);
				} else {
					break;
				}
			}
		}
	}

	public static <T extends Comparable<T>> void sort(final T[] array) {
		sort(array, 0, array.length - 1);
	}

}
