/*
 * (c) Copyright 2016 EVRYTHNG Ltd London / Zurich
 * www.evrythng.com
 */

package part1.week5.assignment;import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

    private Node root;

    public KdTree() {

    }

//    public static final void main (String[] args) {
//
//        int N = 10;
//        KdTree kdTree = new KdTree();
//        kdTree.insert(new Point2D(0.1, 0.1));
//        kdTree.insert(new Point2D(0.2, 0.1));
//        kdTree.insert(new Point2D(0.1, 0.4));
//        System.out.println("kdTree.size() = " + kdTree.size());
//        System.out.println("kdTree.nearest() = " + kdTree.nearest(new Point2D(0.2, 0.2)));
//    }

    public boolean isEmpty() {

        return size() == 0;
    }

    public int size() {

        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(final Node x) {

        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }

    public boolean contains(final Point2D key) {

        if (key == null) {
            throw new NullPointerException("argument to contains() is null");
        }
        return get(key) != null;
    }

    private Point2D get(final Point2D key) {

        return get(root, key, true);
    }

    private Point2D get(final Node x, final Point2D key, final boolean vertical) {

        if (x == null) {
            return null;
        }

        if (x.key.equals(key)) {
            return x.key;
        }

        double cmp;
        if (vertical) {
            cmp = key.x() - x.key.x();
        } else {
            cmp = key.y() - x.key.y();
        }
        if (cmp <= 0) {
            return get(x.left, key, !vertical);
        } else {
            return get(x.right, key, !vertical);
        }
    }

    public void insert(final Point2D key) {

        if (key == null) {
            throw new NullPointerException("first argument to put() is null");
        }
        root = put(root, key, true);
    }

    private Node put(final Node x, final Point2D key, final boolean vertical) {

        if (x == null) {
            return new Node(key, 1);
        }
        double cmp;
        if (vertical) {
            cmp = key.x() - x.key.x();
        } else {
            cmp = key.y() - x.key.y();
        }
        if (key.equals(x.key)) {
            // do nothing
        } else if (cmp <= 0) {
            x.left = put(x.left, key, !vertical);
            x.N = 1 + size(x.left) + size(x.right);
        } else if (cmp > 0) {
            x.right = put(x.right, key, !vertical);
            x.N = 1 + size(x.left) + size(x.right);
        }
        return x;
    }

    public void draw() {

    }

    public Iterable<Point2D> range(final RectHV rect) {

        Queue<Point2D> ranged = new Queue<>();
        range(root, rect, ranged, true);
        return ranged;
    }

    private void range(final Node node, final RectHV rect, final Queue<Point2D> ranged, final boolean vertical) {

        if (node == null) {
            return;
        }

        if (rect.contains(node.key)) {
            ranged.enqueue(node.key);
            range(node.left, rect, ranged, !vertical);
            range(node.right, rect, ranged, !vertical);
        } else {
            if (onTheLeft(node.key, rect, vertical)) {
                range(node.left, rect, ranged, !vertical);
            } else if (onTheRight(node.key, rect, vertical)) {
                range(node.right, rect, ranged, !vertical);
            } else {
                range(node.left, rect, ranged, !vertical);
                range(node.right, rect, ranged, !vertical);
            }
        }
    }

    private boolean onTheLeft(final Point2D point, final RectHV rect, final boolean vertical) {

        if (vertical) {
            return rect.xmax() < point.x();
        } else {
            return rect.ymax() < point.y();
        }
    }

    private boolean onTheRight(final Point2D point, final RectHV rect, final boolean vertical) {

        if (vertical) {
            return rect.xmin() > point.x();
        } else {
            return rect.ymin() > point.y();
        }
    }

    public Point2D nearest(final Point2D p) {

        if (isEmpty()) {
            return null;
        }

        Holder<Point2D> nearest = new Holder<>(root.key);
        nearest(root, nearest, p, true);
        return nearest.value;
    }

    private void nearest(final Node node, final Holder<Point2D> nearest, final Point2D p, final boolean vertical) {

        if (node == null) {
            return;
        } else if (node.key.compareTo(p) == 0) {
            nearest.value = node.key;
            return;
        } else if (p.distanceTo(node.key) < p.distanceTo(nearest.value)) {
            nearest.value = node.key;
        }

        if (onTheLeft(p, node.key, vertical)) {
            nearest(node.left, nearest, p, !vertical);
            if (distanceToVertical(p, node.key, vertical) < p.distanceTo(nearest.value)) {
                nearest(node.right, nearest, p, !vertical);
            }
        } else if (onTheRight(p, node.key, vertical)) {
            nearest(node.right, nearest, p, !vertical);
            if (distanceToVertical(p, node.key, vertical) < p.distanceTo(nearest.value)) {
                nearest(node.left, nearest, p, !vertical);
            }
        }

    }

    private double distanceToVertical(final Point2D p, final Point2D v, final boolean vertical) {

        if (vertical) {
            return Math.abs(p.x() - v.x());
        } else {
            return Math.abs(p.y() - v.y());
        }
    }

    private boolean onTheLeft(final Point2D point, final Point2D current, final boolean vertical) {

        if (vertical) {
            return point.x() <= current.x();
        } else {
            return point.y() <= current.y();
        }
    }

    private boolean onTheRight(final Point2D point, final Point2D current, final boolean vertical) {

        if (vertical) {
            return point.x() >= current.x();
        } else {
            return point.y() >= current.y();
        }
    }

    /**
     * Returns the keys in the BST in level order (for debugging).
     *
     * @return the keys in the BST in level order traversal
     */
    private Iterable<Point2D> levelOrder() {

        Queue<Point2D> keys = new Queue<Point2D>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) {
                continue;
            }
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    private static class Holder<T> {

        private T value;

        public Holder(final T ref) {

            this.value = ref;
        }
    }

    private class Node {

        private Point2D key;
        private Node left, right;
        private int N;

        public Node(final Point2D key, final int N) {

            this.key = key;
            this.N = N;
        }
    }

}
