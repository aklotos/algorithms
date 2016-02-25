package part1.week2.sorts;

public interface SortAlgorithm {

    <T extends Comparable<T>> void sort(T[] array);

    default <T> void swap(T[] array, int first, int second) {
        T temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }
}
