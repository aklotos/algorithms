package part1.week4.assignment;import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final int N;
    private final int[][] blocks;

    public Board(final int[][] blocks) {

        this.N = blocks.length;
        this.blocks = copyBlocks(blocks);
    }

    private int[][] copyBlocks(final int[][] blocksToCopy) {

        int n = blocksToCopy.length;
        final int [][] copy = new int [n][];
        for (int i = 0; i < N; i++) {
            copy[i] = Arrays.copyOf(blocksToCopy[i], n);
        }
        return copy;
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                if (blocks[i][j] != i * N + j + 1) {
                    sum++;
                }
            }
        }

        return sum;
    }

    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }

                int x = blocks[i][j];
                int i0 = (x - 1) / N;
                int j0 = (x - 1) % N;
                sum += Math.abs(i - i0) + Math.abs(j - j0);
            }
        }
        return sum;
    }

    public boolean isGoal() {
        return manhattan() == 0;
    }

    public Board twin() {

        int[][] copy = copyBlocks(this.blocks);
        Pair pair = nonZero();
        swap(copy, pair.a, pair.b);
        return new Board(copy);
    }

    private void swap(final int[][] array, final Cell left, final Cell right) {
        int temp = array[left.i][left.j];
        array[left.i][left.j] = array[right.i][right.j];
        array[right.i][right.j] = temp;
    }

    private Pair nonZero() {
        final Cell[] nonZeroCells = new Cell[3];
        int i = 0;
        for (Cell cell: new Cell[] { new Cell(0, 0), new Cell(0, 1), new Cell(1, 0)}) {
            if (blocks[cell.i][cell.j] != 0) {
                nonZeroCells[i++] = cell;
            }
        }

        return new Pair(nonZeroCells[0], nonZeroCells[1]);
    }

    public boolean equals(final Object y) {

        if (y == null) {
            return false;
        }
        if (this == y) {
            return true;
        }
        if (!this.getClass().equals(y.getClass())) {
            return false;
        }

        Board b = (Board) y;
        return this.N == b.N && Arrays.deepEquals(this.blocks, b.blocks);
    }

    private Cell findZero() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    return new Cell(i, j);
                }
            }
        }
        return null;
    }

    public Iterable<Board> neighbors() {

        Cell zero = findZero();
        final List<Board> neighbors = new ArrayList<>();
        if (zero.i > 0) {
            int[][] copy = copyBlocks(this.blocks);
            swap(copy, zero, new Cell(zero.i - 1, zero.j));
            neighbors.add(new Board(copy));
        }
        if (zero.i < N - 1) {
            int[][] copy = copyBlocks(this.blocks);
            swap(copy, zero, new Cell(zero.i + 1, zero.j));
            neighbors.add(new Board(copy));
        }
        if (zero.j > 0) {
            int[][] copy = copyBlocks(this.blocks);
            swap(copy, zero, new Cell(zero.i, zero.j - 1));
            neighbors.add(new Board(copy));
        }
        if (zero.j < N - 1) {
            int[][] copy = copyBlocks(this.blocks);
            swap(copy, zero, new Cell(zero.i, zero.j + 1));
            neighbors.add(new Board(copy));
        }

        return neighbors;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("" + N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                String leftpad = "";
                if (blocks[i][j] < 10) {
                    leftpad = " ";
                }
                sb.append(leftpad).append(blocks[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static class Cell {
        private int i;
        private int j;

        private Cell(final int i, final int j) {

            this.i = i;
            this.j = j;
        }
    }

    private static class Pair {
        private Cell a;
        private Cell b;

        private Pair(final Cell a, final Cell b) {

            this.a = a;
            this.b = b;
        }
    }
}

