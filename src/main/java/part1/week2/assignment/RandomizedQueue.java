package part1.week2.assignment;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] queue;

    public RandomizedQueue() {

        this.queue = (Item[]) new Object[1];
    }

    public static void main(String[] args) {

        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < 50; i++) {
            rq.enqueue(30);
        }
        for (int i = 0; i < 50; i++) {
            rq.dequeue();
        }

        //        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
//        q.enqueue(1);
//        q.enqueue(2);
//        q.enqueue(3);
//        q.enqueue(4);
//        q.enqueue(5);
//        System.out.println("q.size() = " + q.size());
//        System.out.println("q.dequeue() = " + q.dequeue());
//        System.out.println("q.dequeue() = " + q.dequeue());
//        System.out.println("q.dequeue() = " + q.dequeue());
//        System.out.println("q.dequeue() = " + q.dequeue());
//        System.out.println("q.dequeue() = " + q.dequeue());
//        System.out.println("q.size() = " + q.size());
//        q.enqueue(11);
//        q.enqueue(12);
//        q.enqueue(13);
//        q.enqueue(14);
//        q.enqueue(15);
//        System.out.println("q.size() = " + q.size());
//        for (int i = 0; i < 10; i++) {
//            System.out.println("q.sample() = " + q.sample());
//        }
//        for (Integer item : q) {
//            System.out.println("item = " + item);
//        }
    }

    public boolean isEmpty() {

        return size == 0;
    }

    public int size() {

        return size;
    }

    public void enqueue(final Item item) {

        if (item == null) {
            throw new NullPointerException("null item");
        }

        if (size >= queue.length) {
            resize(2 * queue.length);
        }

        queue[size++] = item;
    }

    public Item dequeue() {

        if (isEmpty()) {
            throw new NoSuchElementException("empty queue");
        }

        if (size > 1 && size < queue.length / 4) {
            resize(queue.length / 2);
        }

        int index = StdRandom.uniform(size);
        Item item = queue[index];
        queue[index] = queue[--size];
        queue[size] = null;
        return item;
    }

    private void resize(final int count) {

        Item[] items = (Item[]) new Object[count];
        for (int i = 0; i < Math.min(queue.length, count); i++) {
            items[i] = queue[i];
        }
        queue = items;
    }

    public Item sample() {

        if (isEmpty()) {
            throw new NoSuchElementException("empty queue");
        }

        int index = StdRandom.uniform(size);
        return queue[index];
    }

    public Iterator<Item> iterator() {

        Item[] items = (Item[]) new Object[size];
        boolean[] used = new boolean[size];
        for (int i = 0; i < size; i++) {
            int index;
            do {
                index = StdRandom.uniform(size);
            } while (used[index]);

            used[index] = true;
            items[i] = queue[index];
        }

        return new RandomIterator<>(items);
    }

    private void swap(final Item[] array, final int i, final int j) {

        Item temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static class RandomIterator<T> implements Iterator<T> {

        private final T[] queue;
        private int current;

        private RandomIterator(final T[] queue) {

            this.queue = queue;
        }

        @Override
        public boolean hasNext() {

            return current < queue.length;
        }

        @Override
        public T next() {

            if (!hasNext()) {
                throw new NoSuchElementException("empty iterator");
            }

            return queue[current++];
        }
    }
}
