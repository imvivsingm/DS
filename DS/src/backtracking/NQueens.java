package backtracking;

import java.util.ArrayList;
import java.util.List;

public class NQueens {

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> results = new ArrayList<>();
        int[] queens = new int[n]; // queens[col] = row where queen is placed in column 'col'
        backtrack(0, n, queens, results);
        return results;
    }

    private static void backtrack(int col, int n, int[] queens, List<List<String>> results) {
        if (col == n) {
            // All queens placed - add solution
            results.add(buildBoard(queens, n));
            System.out.println("Solution found: " + results.get(results.size() - 1));
            return;
        }

        for (int row = 0; row < n; row++) {
            if (isSafe(row, col, queens)) {
                queens[col] = row;
                System.out.println("Placing queen at (row=" + row + ", col=" + col + ")");
                backtrack(col + 1, n, queens, results);
                System.out.println("Removing queen from (row=" + row + ", col=" + col + ")");
                queens[col] = -1; // backtrack
            }
        }
    }

    private static boolean isSafe(int row, int col, int[] queens) {
        for (int c = 0; c < col; c++) {
            int r = queens[c];
            if (r == row || Math.abs(row - r) == Math.abs(col - c)) {
                return false; // same row or diagonal attack
            }
        }
        return true;
    }

    private static List<String> buildBoard(int[] queens, int n) {
        List<String> board = new ArrayList<>();
        for (int row : queens) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(i == row ? 'Q' : '.');
            }
            board.add(sb.toString());
        }
        return board;
    }

    public static void main(String[] args) {
        int n = 4;
        System.out.println("Solving " + n + "-Queens Problem");
        List<List<String>> solutions = solveNQueens(n);

        System.out.println("\nAll solutions (" + solutions.size() + "):");
        for (List<String> solution : solutions) {
            for (String row : solution) {
                System.out.println(row);
            }
            System.out.println();
        }
    }
}

