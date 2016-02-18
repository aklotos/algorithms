import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private int size;
	private int last;
	private Item[] queue;

	public RandomizedQueue() {

		this.queue = (Item[]) new Object[1];
	}

	public static void main(String[] args) {

		RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(4);
		q.enqueue(5);
		q.enqueue(6);
		q.enqueue(7);
		q.enqueue(8);
		q.enqueue(9);
		q.enqueue(10);
		System.out.println("q.size() = " + q.size());
		System.out.println("q.dequeue() = " + q.dequeue());
		System.out.println("q.dequeue() = " + q.dequeue());
		System.out.println("q.dequeue() = " + q.dequeue());
		System.out.println("q.dequeue() = " + q.dequeue());
		System.out.println("q.dequeue() = " + q.dequeue());
		System.out.println("q.size() = " + q.size());
		q.enqueue(11);
		q.enqueue(12);
		q.enqueue(13);
		q.enqueue(14);
		q.enqueue(15);
		System.out.println("q.size() = " + q.size());
		for (int i = 0; i < 10; i++) {
			System.out.println("q.sample() = " + q.sample());
		}
		for (Integer item : q) {
			System.out.println("item = " + item);
		}
	}

	public boolean isEmpty() {

		return size == 0;
	}

	public int size() {

		return size;
	}

	public void enqueue(Item item) {

		if (item == null) {
			throw new NullPointerException("null item");
		}

		if (last >= queue.length) {
			resize(2 * queue.length);
		}

		queue[last++] = item;
		size++;
	}

	public Item dequeue() {

		if (isEmpty()) {
			throw new NoSuchElementException("empty queue");
		}

		if (size - 1 <= queue.length / 4) {
			resize(queue.length / 2);
		}

		int index;
		do {
			index = StdRandom.uniform(last + 1);
		} while (queue[index] == null);
		if (index == last - 1) {
			last--;
		}

		Item item = queue[index];
		queue[index] = null;
		size--;
		return item;
	}

	private void resize(final int count) {

		Item[] items = (Item[]) new Object[count];
		int j = 0;
		for (int i = 0; i < queue.length; i++) {
			if (queue[i] != null) {
				items[j++] = queue[i];
			}
		}
		queue = items;
	}

	public Item sample() {

		if (isEmpty()) {
			throw new NoSuchElementException("empty queue");
		}

		int index;
		do {
			index = StdRandom.uniform(last + 1);
		} while (queue[index] == null);
		return queue[index];
	}

	public Iterator<Item> iterator() {

		Item[] items = (Item[]) new Object[size];
		int j = 0;
		for (int i = 0; i < queue.length; i++) {
			if (queue[i] != null) {
				items[j++] = queue[i];
			}
		}
		for (int i = 0; i < items.length; i++) {
			swap(items, i, StdRandom.uniform(items.length));
		}

		return new RandomIterator<>(items);
	}

	private void swap(Item[] array, int i, int j) {

		Item temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	private static class RandomIterator<T> implements Iterator<T> {

		private final T[] queue;
		private int current;

		private RandomIterator(T[] queue) {

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
