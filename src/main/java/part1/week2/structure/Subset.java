
package part1.week2.structure;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Subset {
    public static void main(String[] args) {
        final int k = Integer.parseInt(args[0]);

        String[] lines = StdIn.readAllLines();
        Deque<String> queue = new Deque<>();
        for (String line: lines) {
            for (String item: line.split("\\p{javaWhitespace}+")) {
                if (!item.isEmpty()) {
                    queue.addLast(item);
                }
            }
        }

        final boolean[] usedIndexes = new boolean[queue.size()];
        for (int i = 0; i < k; i++) {
            addRandomIndex(queue.size(), usedIndexes);
        }

        Iterator<String> iterator = queue.iterator();
        for (int i = 0; i < queue.size(); i++) {
            if (usedIndexes[i]) {
                System.out.println(iterator.next());
            } else {
                iterator.next();
            }
        }
    }

    private static int addRandomIndex(final int size, final boolean[] usedIndexes) {
        int index;
        do {
            index = StdRandom.uniform(size);
        } while (usedIndexes[index]);
        usedIndexes[index] = true;
        return index;
    }
}