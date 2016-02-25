package part1.week2.sorts;


public class BubbleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> void sort(T[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = array.length - 1; j > i; j--) {
                if (array[i].compareTo(array[j]) > 0) {
                    swap(array, i, j);
                }
            }
        }
    }
}
