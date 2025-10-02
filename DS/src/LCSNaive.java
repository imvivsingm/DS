public class LCSNaive {

    private static int[][] memo;

    public static int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        // Initialize memo with -1 to indicate uncomputed subproblems
        memo = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                memo[i][j] = -1;
            }
        }

        return lcsHelper(text1, text2, 0, 0);
    }

    private static int lcsHelper(String s1, String s2, int i, int j) {
        // Base case: end of either string reached
        if (i == s1.length() || j == s2.length()) {
            return 0;
        }

        // Return memoized result if already computed
        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        // Compute and memoize result
        if (s1.charAt(i) == s2.charAt(j)) {
            memo[i][j] = 1 + lcsHelper(s1, s2, i + 1, j + 1);
        } else {
            memo[i][j] = Math.max(
                    lcsHelper(s1, s2, i + 1, j),
                    lcsHelper(s1, s2, i, j + 1)
            );
        }

        return memo[i][j];
    }

    public static void main(String[] args) {
        System.out.println("LCS length (abcde, ace): " + longestCommonSubsequence("abcde", "ace")); // 3
        System.out.println("LCS length (AGGTAB, GXTXAYB): " + longestCommonSubsequence("AGGTAB", "GXTXAYB")); // 4
        System.out.println("LCS length (ABCDGH, AEDFHR): " + longestCommonSubsequence("ABCDGH", "AEDFHR")); // 3
        System.out.println("LCS length (ABC, DEF): " + longestCommonSubsequence("ABC", "DEF")); // 0
    }
}
