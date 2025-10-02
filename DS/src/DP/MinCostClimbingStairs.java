package DP;

public class MinCostClimbingStairs {

    public static int minCostClimbingStairs(int[] cost) {
        int prev2 = 0;
        int prev1 = 0;

        for (int i = 2; i <= cost.length; i++) {
            int curr = Math.min(
                    prev1 + cost[i - 1],
                    prev2 + cost[i - 2]
            );
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    public static int minCostDp(int[] cost) {
       int len = cost.length;
       int []dp = new int[len];
       dp[0] = cost[0];
       dp[1] = cost[1];
       for (int i = 2; i < len; i++) {
           dp[i] = cost[i] +  Math.min(dp[i-1], dp[i-2]);
       }
       return Math.min(dp[len-1], dp[len-2]);
    }

    public static void main(String[] args) {
        // Example 1
        int[] cost1 = {10, 15, 20};
        System.out.println("Minimum cost: " + minCostClimbingStairs(cost1));
        System.out.println("Minimum cost: " + minCostDp(cost1));
        // Output: 15

        // Example 2
        int[] cost2 = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        System.out.println("Minimum cost: " + minCostClimbingStairs(cost2));
        System.out.println("Minimum cost: " + minCostDp(cost2));
        // Output: 6

        // Example 3 (custom)
        int[] cost3 = {5, 10, 2, 3, 1};
        System.out.println("Minimum cost: " + minCostClimbingStairs(cost3));
        System.out.println("Minimum cost: " + minCostDp(cost3));
        // Output: 6
    }
}

