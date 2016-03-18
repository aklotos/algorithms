package part1.week3.assignment;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class FastCollinearPoints {

    private static final double EPS = 0.000_000_001;
    private Set<Segment> segmentSet = new TreeSet<>((s1, s2) -> {
        int cmp = s1.min.compareTo(s2.min);
        if (cmp == 0) {
            cmp = s1.max.compareTo(s2.max);
        }
        return cmp;
    });
    private LineSegment[] resultSegments;

    private static class Segment {
        private final Point min;
        private final Point max;

        private Segment(final Point min, final Point max) {

            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(final Object o) {

            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Segment segment = (Segment) o;
            return min.compareTo(segment.min) == 0 && max.compareTo(segment.max) == 0;
        }

        @Override
        public int hashCode() {

            return 31 * min.hashCode() + max.hashCode();
        }
    }

    public FastCollinearPoints(final Point[] points) {

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Point[] pointsToIterate = Arrays.copyOf(points, points.length);

        validate(pointsCopy);

        for (int p = 0; p < pointsToIterate.length; p++) {
            final Point origin = pointsToIterate[p];
            Arrays.sort(pointsCopy, (q, r) -> {
                if (origin.slopeTo(q) < origin.slopeTo(r)) {
                    return -1;
                } else if (origin.slopeTo(q) > origin.slopeTo(r)) {
                    return 1;
                } else {
                    return 0;
                }
            });

            int count;
            for (int i = 0; i < pointsCopy.length; i += count) {
                count = 1;
                while (i + count < pointsCopy.length
                        && equalSlopes(origin.slopeTo(pointsCopy[i]), origin.slopeTo(pointsCopy[i + count]))) {
                    count++;
                }

                if (count > 2) {
                    Point min = minBetween(pointsCopy, origin, i, i + count - 1);
                    Point max = maxBetween(pointsCopy, origin, i, i + count - 1);
                    segmentSet.add(new Segment(min, max));
                }
            }
        }

        calculateResultSegments();
    }

    private void calculateResultSegments() {

        int i = 0;
        resultSegments = new LineSegment[numberOfSegments()];
        Iterator<Segment> it;
        for (it = segmentSet.iterator(); it.hasNext();) {
            Segment segment = it.next();
            resultSegments[i++] = new LineSegment(segment.min, segment.max);
        }
    }

    private static boolean equalSlopes(final double slopeA, final double slopeB) {

        return slopeA == slopeB || Math.abs(slopeA - slopeB) < EPS;
    }

    private static void validate(final Point[] points) {

        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("Equal points");
            }
        }
    }

    private static Point maxBetween(final Point[] points, final Point max, final int left, final int right) {

        Point newMax = max;
        for (int i = left; i <= right; i++) {
            if (newMax.compareTo(points[i]) < 0) {
                newMax = points[i];
            }
        }
        return newMax;
    }

    private static Point minBetween(final Point[] points, final Point min, final int left, final int right) {

        Point newMin = min;
        for (int i = left; i <= right; i++) {
            if (newMin.compareTo(points[i]) > 0) {
                newMin = points[i];
            }
        }
        return newMin;
    }

    public int numberOfSegments() {

        return segmentSet.size();
    }

    public LineSegment[] segments() {

        return Arrays.copyOf(resultSegments, numberOfSegments());
    }
}
