package part1.week3.assignment;

import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {

    private static final double EPS = 0.000_000_001;
    private List<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(final Point[] points) {

        validate(points);

        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                double pqSlope = points[p].slopeTo(points[q]);
                for (int r = q + 1; r < points.length; r++) {
                    double prSlope = points[p].slopeTo(points[r]);
                    if (equalSlopes(pqSlope, prSlope)) {
                        for (int s = r + 1; s < points.length; s++) {
                            double psSlope = points[p].slopeTo(points[s]);
                            if (equalSlopes(pqSlope, psSlope)) {
                                segments.add(new LineSegment(min(points, p, q, r, s), max(points, p, q, r, s)));
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean equalSlopes(final double slopeA, final double slopeB) {

        return slopeA == slopeB || Math.abs(slopeA - slopeB) < EPS;
    }

    private static void validate(final Point[] points) {

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Equal points");
                }
            }
        }
    }

    private static Point min(final Point[] points, final int... indexes) {

        Point min = points[indexes[0]];
        for (int index : indexes) {
            if (min.compareTo(points[index]) > 0) {
                min = points[index];
            }
        }
        return min;
    }

    private static Point max(final Point[] points, final int... indexes) {

        Point max = points[indexes[0]];
        for (int index : indexes) {
            if (max.compareTo(points[index]) < 0) {
                max = points[index];
            }
        }
        return max;
    }

    public int numberOfSegments() {

        return segments.size();
    }

    public LineSegment[] segments() {

        return segments.toArray(new LineSegment[segments.size()]);
    }
}
