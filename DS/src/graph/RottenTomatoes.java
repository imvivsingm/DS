package graph;

import java.util.LinkedList;
import java.util.Queue;

public class RottenTomatoes {

    private static final int[] ROW_DIRECTIONS = {-1, 1, 0, 0};  // Up, Down
    private static final int[] COLUMN_DIRECTIONS = {0, 0, -1, 1};  // Left, Right

    public static int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0) return -1;

        int rows = grid.length;
        int cols = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;

        // Step 1: Initialize queue with all rotten tomatoes
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) {
                    queue.offer(new int[]{r, c});
                } else if (grid[r][c] == 1) {
                    freshCount++;
                }
            }
        }

        if (freshCount == 0) return 0;  // No fresh tomatoes

        int minutesPassed = -1;

        // Step 2: BFS level-by-level (each level = 1 minute)
        while (!queue.isEmpty()) {
            int size = queue.size();
            minutesPassed++;

            for (int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                int row = pos[0], col = pos[1];

                for (int d = 0; d < 4; d++) {
                    int newRow = row + ROW_DIRECTIONS[d];
                    int newCol = col + COLUMN_DIRECTIONS[d];

                    if (newRow >= 0 && newRow < rows &&
                            newCol >= 0 && newCol < cols &&
                            grid[newRow][newCol] == 1) {

                        grid[newRow][newCol] = 2;  // Rot it
                        freshCount--;
                        queue.offer(new int[]{newRow, newCol});
                    }
                }
            }
        }

        return freshCount == 0 ? minutesPassed : -1;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        };

        int result = orangesRotting(grid);
        System.out.println("Minutes to rot all tomatoes: " + result); // Output: 4
    }
}

