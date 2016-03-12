package part1.week2.sorts;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import part1.week3.sorts.BottomUpMergeSort;
import part1.week3.sorts.MergeSort;
import part1.week3.sorts.QuickSort;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Consumer;

public class TestSort {

	public static Integer[] randomArray;

	@Test
	public void testSorts() throws InstantiationException, IllegalAccessException {

		final int COUNT = 10_000_000;

		Integer[] array = createRandom(1000000);

//		long bubbleTime = measureTime(array, BubbleSort::sort);
//		System.out.println("checkSorted = " + checkSorted(array) + "\t\tbubbleTime = " + bubbleTime + " ms");
//
//		array = createRandom(COUNT);
//		long selectionTime = measureTime(array, SelectionSort::sort);
//		System.out.println("checkSorted = " + checkSorted(array) + "\t\tselectionTime = " + selectionTime + " ms");
//
//		array = createRandom(COUNT);
//		long insertionTime = measureTime(array, InsertionSort::sort);
//		System.out.println("checkSorted = " + checkSorted(array) + "\t\tinsertionTime = " + insertionTime + " ms");
//
//		array = createRandom(COUNT);
//		long shellTime = measureTime(array, ShellSort::sort);
//		System.out.println("checkSorted = " + checkSorted(array) + "\t\tshellTime = " + shellTime + " ms");

		array = createRandom(COUNT);
		long mergeTime = measureTime(array, MergeSort::sort);
		System.out.println("checkSorted = " + checkSorted(array) + "\t\tmergeTime = " + mergeTime + " ms");

		array = createRandom(COUNT);
		long bottomUpMergeTime = measureTime(array, BottomUpMergeSort::sort);
		System.out.println("checkSorted = " + checkSorted(array) + "\t\tbottomUpMergeTime = " + bottomUpMergeTime + " ms");

		array = createRandom(COUNT);
		long quickSortTime = measureTime(array, QuickSort::sort);
		System.out.println("checkSorted = " + checkSorted(array) + "\t\tquickSortTime = " + quickSortTime + " ms");

	}

	private Integer[] createRandom(final int count) {

		if (randomArray == null || randomArray.length < count) {
			randomArray = new Integer[count];
			for (int i = 0; i < count; i++) {
				randomArray[i] = StdRandom.uniform(count);
			}
		}

		Integer[] newArray = new Integer[count];
		System.arraycopy(randomArray, 0, newArray, 0, count);
		return newArray;
	}

	private <T extends Comparable<T>> boolean checkSorted(final T[] array) {

		for (int i = 1; i < array.length; i++) {
			if (array[i].compareTo(array[i - 1]) < 0) {
				return false;
			}
		}
		return true;
	}

	private <T extends Comparable<T>> long measureTime(final T[] array, final Consumer<T[]> consumer) {

		long start = System.currentTimeMillis();
		consumer.accept(array);
		long end = System.currentTimeMillis();

		return end - start;
	}
}
