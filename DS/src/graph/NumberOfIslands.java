package graph;

public class NumberOfIslands {

    private static final int[] ROW_DIRECTIONS = {-1, 1, 0, 0};  // Up, Down
    private static final int[] COLUMN_DIRECTIONS = {0, 0, -1, 1};  // Left, Right

    /**
     * Counts the number of islands in the given grid.
     *
     * @param grid 2D char array where '1' = land and '0' = water
     * @return number of islands
     */
    public static int countIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int totalRows = grid.length;
        int totalColumns = grid[0].length;
        int islandCount = 0;

        // Traverse each cell in the grid
        for (int row = 0; row < totalRows; row++) {
            for (int column = 0; column < totalColumns; column++) {
                // When land is found, explore the entire island
                if (grid[row][column] == '1') {
                    islandCount++;
                    exploreIsland(grid, row, column, totalRows, totalColumns);
                }
            }
        }

        return islandCount;
    }

    /**
     * Depth-First Search to mark all connected land cells as visited.
     */
    private static void exploreIsland(char[][] grid, int row, int column, int totalRows, int totalColumns) {
        // Boundary check and skip water or already visited cells
        if (row < 0 || column < 0 || row >= totalRows || column >= totalColumns || grid[row][column] == '0') {
            return;
        }

        // Mark current land cell as visited by setting it to '0'
        grid[row][column] = '0';

        // Explore all four adjacent directions (up, down, left, right)
        for (int direction = 0; direction < 4; direction++) {
            int nextRow = row + ROW_DIRECTIONS[direction];
            int nextColumn = column + COLUMN_DIRECTIONS[direction];
            exploreIsland(grid, nextRow, nextColumn, totalRows, totalColumns);
        }
    }

    public static void main(String[] args) {
        char[][] map = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '1', '0', '0'},
                {'1', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        int totalIslands = countIslands(map);
        System.out.println("Number of islands: " + totalIslands);  // Output: 3
    }
}
