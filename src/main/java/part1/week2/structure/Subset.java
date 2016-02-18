
package part1.week2.structure;

import edu.princeton.cs.algs4.StdIn;

public class Subset {
    public static void main(String[] args) {
        final int k = Integer.parseInt(args[0]);
        String[] items = StdIn.readLine().split("\\p{javaWhitespace}+");

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        for (String item: items) {
            queue.enqueue(item);
        }
        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }
    }
}