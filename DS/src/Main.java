import java.awt.desktop.PreferencesEvent;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

        public String longestCommonSubsequence(String text1, String text2) {
            int m = text1.length();
            int n = text2.length();

            // DP table for lengths
            int[][] dp = new int[m + 1][n + 1];

            // Build the dp table bottom-up
            for (int i = 1; i <= m; i++) {
                char c1 = text1.charAt(i - 1);
                for (int j = 1; j <= n; j++) {
                    char c2 = text2.charAt(j - 1);
                    if (c1 == c2) {
                        dp[i][j] = 1 + dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }

            // Backtrack to find the LCS string
            StringBuilder lcs = new StringBuilder();
            int i = m, j = n;
            while (i > 0 && j > 0) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    lcs.append(text2.charAt(j - 1));
                    i--;
                    j--;
                } else if (dp[i - 1][j] > dp[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }
            }

            // Since we appended from the end, reverse it
            return lcs.reverse().toString();
        }

        public static void main(String[] args) {
            Main lcs = new Main();
            String text1 = "acx";
            String text2 = "abcde";
            String result = lcs.longestCommonSubsequence(text1, text2);
            System.out.println("LCS string: " + result);  // Output: "ace"
        }
    }


