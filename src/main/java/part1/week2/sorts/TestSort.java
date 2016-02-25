package part1.week2.sorts;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

public class TestSort {

    public static Integer[] randomArray;

    @Test
    public void testSorts() throws InstantiationException, IllegalAccessException {
        final int COUNT = 10_000;

        Integer[] array = createRandom(COUNT);
        long bubbleTime = measureTime(array, BubbleSort.class);
        System.out.println("checkSorted = " + checkSorted(array) + "\t\tbubbleTime = " + bubbleTime + " ms");

        array = createRandom(COUNT);
        long selectionTime = measureTime(array, SelectionSort.class);
        System.out.println("checkSorted = " + checkSorted(array) + "\t\tselectionTime = " + selectionTime + " ms");

        array = createRandom(COUNT);
        long insertionTime = measureTime(array, InsertionSort.class);
        System.out.println("checkSorted = " + checkSorted(array) + "\t\tinsertionTime = " + insertionTime + " ms");

        array = createRandom(COUNT);
        long shellTime = measureTime(array, ShellSort.class);
        System.out.println("checkSorted = " + checkSorted(array) + "\t\tinsertionTime = " + shellTime + " ms");
    }

    private Integer[] createRandom(int count) {
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

    private <T extends Comparable<T>> boolean checkSorted(T[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(array[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    private <T extends Comparable<T>, C extends Class<? extends SortAlgorithm>> long measureTime(T[] array, C sortClass) throws IllegalAccessException, InstantiationException {
        SortAlgorithm sa = sortClass.newInstance();

        long start = System.currentTimeMillis();
        sa.sort(array);
        long end = System.currentTimeMillis();

        return end - start;
    }
}
