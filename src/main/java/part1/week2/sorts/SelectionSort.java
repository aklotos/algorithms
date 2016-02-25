package part1.week2.sorts;

public class SelectionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> void sort(T[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j].compareTo(array[min]) < 0) {
                    min = j;
                }
            }
            swap(array, i, min);
        }
    }
}
