package part1.week3.sorts;

public class BottomUpMergeSort {

	public static <T extends Comparable<T>> void sort(final T[] array) {

		T[] temp = (T[]) new Comparable[array.length];
		for (int step = 1; step < array.length; step += step) {
			for (int i = 0; i < array.length; i += 2*step) {
				MergeSort.merge(array, temp, i, i + step - 1, Math.min(array.length - 1, i + 2*step - 1));
			}
		}
	}

}
