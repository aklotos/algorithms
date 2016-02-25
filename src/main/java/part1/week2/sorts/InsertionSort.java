package part1.week2.sorts;

public class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> void sort(T[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j - 1].compareTo(array[j]) > 0) {
                    swap(array, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }
}
