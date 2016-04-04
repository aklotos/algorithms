/*
 * (c) Copyright 2016 EVRYTHNG Ltd London / Zurich
 * www.evrythng.com
 */

package part1.week5.assignment;import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {

    private final Set<Point2D> points;

    public PointSET() {

        this.points = new TreeSet<>();
    }

    public static void main(final String[] args) {

    }

    public boolean isEmpty() {

        return points.isEmpty();
    }

    public int size() {

        return points.size();
    }

    public void insert(final Point2D p) {

        points.add(p);
    }

    public boolean contains(final Point2D p) {

        return points.contains(p);
    }

    public void draw() {

    }

    public Iterable<Point2D> range(final RectHV rect) {

        List<Point2D> ranged = new ArrayList<>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                ranged.add(point);
            }
        }

        return ranged;
    }

    public Point2D nearest(final Point2D p) {

        if (p == null) {
            throw new NullPointerException("null point");
        }

        if (isEmpty()) {
            return null;
        }

        return points.stream().reduce((p1, p2) -> {
            if (p.distanceTo(p1) < p.distanceTo(p2)) {
                return p1;
            } else {
                return p2;
            }
        }).get();
    }
}
