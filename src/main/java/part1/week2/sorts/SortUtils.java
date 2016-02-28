package part1.week2.sorts;

public class SortUtils {

	public static <T> void swap(final T[] array, final int first, final int second) {
		T temp = array[first];
		array[first] = array[second];
		array[second] = temp;
	}
}
