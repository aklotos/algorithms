import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private static class Node<T> {
		private T item;
		private Node<T> next;
		private Node<T> prev;

		private Node(final T item) {
			this(item, null, null);
		}

		private Node(final T item, final Node<T> prev, final Node<T> next) {
			this.item = item;
			this.prev = prev;
			this.next = next;
		}
	}

	private Node<Item> head;
	private Node<Item> tail;
	private int size;

	public Deque() { }

	public static void main(final String[] args) {
		Deque<String> deque = new Deque<>();
		deque.addFirst("4");
		deque.addFirst("3");
		deque.addLast("5");
		deque.addLast("6");
		deque.addFirst("2");
		deque.addLast("7");
		deque.addFirst("1");
		deque.addLast("8");
		deque.removeFirst();
		deque.removeLast();
		deque.removeFirst();
		deque.removeLast();
		deque.addFirst("c");
		deque.addLast("x");
		deque.addFirst("b");
		deque.addLast("y");
		deque.addFirst("a");
		deque.addLast("z");
		for (String item: deque) {
			System.out.println("item = " + item);
		}
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() { // return the number of items on the deque
		return size;
	}

	public void addFirst(final Item item) {
		if (item == null) {
			throw new NullPointerException("null item");
		}

		if (isEmpty()) {
			head = new Node<>(item);
			tail = head;
		} else {
			head.prev = new Node<>(item, null, head);
			head = head.prev;
		}

		size++;
	}

	public void addLast(final Item item) {
		if (item == null) {
			throw new NullPointerException("null item");
		}

		if (isEmpty()) {
			tail = new Node<Item>(item);
			head = tail;
		}
		tail.next = new Node<>(item, tail, null);
		tail = tail.next;

		size++;
	}

	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException("empty deque");
		}

		Item item = head.item;
		head = head.next;
		head.prev = null;

		size--;

		return item;
	}

	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException("empty deque");
		}

		Item item = tail.item;
		tail = tail.prev;
		tail.next = null;

		return item;
	}

	public Iterator<Item> iterator() {
		return new DequeIterator<>(this.head);
	}

	private static class DequeIterator<T> implements Iterator<T> {

		private Node<T> node;

		private DequeIterator(final Node<T> node) {
			this.node = node;
		}

		@Override
		public boolean hasNext() {

			return node != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			T item = node.item;
			node = node.next;
			return item;
		}
	}
}