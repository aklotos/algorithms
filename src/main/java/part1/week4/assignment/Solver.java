package part1.week4.assignment;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

public class Solver {

	private boolean isSolvable = false;
	private SearchNode solution;

	public Solver(final Board initial) {

		if (initial == null) {
			throw new NullPointerException("Null initial board");
		}

		Comparator<SearchNode> manhattanCmp = (sn1, sn2) -> sn1.board.manhattan() + sn1.moves - sn2.board.manhattan() - sn2.moves;

		MinPQ<SearchNode> pq = new MinPQ<>(manhattanCmp);
		pq.insert(new SearchNode(initial, 0, null));

		MinPQ<SearchNode> pqTwin = new MinPQ<>(manhattanCmp);
		pqTwin.insert(new SearchNode(initial.twin(), 0, null));

		while (true) {
			SearchNode minSN = pq.delMin();
			if (minSN.board.isGoal()) {
				isSolvable = true;
				solution = minSN;
				break;
			} else {
				Iterable<Board> neighbors = minSN.board.neighbors();
				for (Board neighbor : neighbors) {
					if (minSN.prev == null || !neighbor.equals(minSN.prev.board)) {
						pq.insert(new SearchNode(neighbor, minSN.moves + 1, minSN));
					}
				}
			}

			SearchNode minSNTwin = pqTwin.delMin();
			if (minSNTwin.board.isGoal()) {
				isSolvable = false;
				break;
			} else {
				Iterable<Board> neighbors = minSNTwin.board.neighbors();
				for (Board neighbor : neighbors) {
					if (minSNTwin.prev == null || !neighbor.equals(minSNTwin.prev.board)) {
						pqTwin.insert(new SearchNode(neighbor, minSNTwin.moves + 1, minSNTwin));
					}
				}
			}
		}
	}

	public static void main(String[] args) {

		int[][] blocks = { { 0, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 10, 11 }, { 12, 13, 14, 15 } };

		Solver solver = new Solver(new Board(blocks));
		System.out.println("solver.isSolvable = " + solver.isSolvable);
		if (solver.isSolvable) {
			for (Board b : solver.solution()) {
				System.out.println(b.toString());
				System.out.println("b.manhattan() = " + b.manhattan());
				System.out.println("------------------");
			}
			System.out.println("solver.moves() = " + solver.moves());
		}
	}

	public boolean isSolvable() {

		return isSolvable;
	}

	public int moves() {

		if (isSolvable) {
			return solution.moves;
		} else {
			return -1;
		}
	}

	public Iterable<Board> solution() {

		if (!isSolvable) {
			return null;
		} else {
			Deque<Board> solutionBoards = new LinkedList<>();
			SearchNode node = solution;
			while (node != null) {
				solutionBoards.addFirst(node.board);
				node = node.prev;
			}
			return solutionBoards;
		}
	}

	private static class SearchNode {

		private Board board;
		private int moves;
		private SearchNode prev;

		private SearchNode(final Board board, final int moves, final SearchNode prev) {

			this.board = board;
			this.moves = moves;
			this.prev = prev;
		}
	}
}
