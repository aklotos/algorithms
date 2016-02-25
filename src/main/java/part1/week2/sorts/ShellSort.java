package part1.week2.sorts;

public class ShellSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> void sort(T[] array) {
        int h = 1;
        while (h < array.length / 3) {
            h = 3 * h + 1;
        }

        while (h > 0) {
            for (int i = 0; i < array.length; i++) {
                for (int j = i; j >= h; j -= h) {
                    if (array[j - h].compareTo(array[j]) > 0) {
                        swap(array, j - h, j);
                    } else {
                        break;
                    }
                }
            }
            h = h/3;
        }
    }
}
