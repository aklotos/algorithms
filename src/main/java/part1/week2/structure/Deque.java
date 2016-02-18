package part1.week2.structure;

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
        Deque<Integer> deque = new Deque<>();
        for (Integer item: deque) {
            System.out.println("item = " + item);
        }
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(4);
        System.out.println("deque.removeFirst() = " + deque.removeFirst());
        System.out.println("deque.removeFirst() = " + deque.removeFirst());
        System.out.println("deque.removeFirst() = " + deque.removeFirst());
        System.out.println("deque.removeLast() = " + deque.removeLast());
        for (Integer item: deque) {
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
        } else {
            tail.next = new Node<>(item, tail, null);
            tail = tail.next;
        }

        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty deque");
        }

        size--;

        Item item = head.item;
        head = head.next;
        if (!isEmpty()) {
            head.prev = null;
        } else {
            tail = null;
        }

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty deque");
        }

        size--;

        Item item = tail.item;
        tail = tail.prev;
        if (!isEmpty()) {
            tail.next = null;
        } else {
            head = null;
        }

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