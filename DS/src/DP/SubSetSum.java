package DP;

import java.util.*;

public class SubSetSum {

    public static boolean canPartition(int[] nums) {
        int sum = 0;

        // Calculate total sum
        for (int num : nums)
            sum += num;

        // If total sum is odd, can't split into two equal subsets
        if (sum % 2 != 0)
            return false;

        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;  // base case: 0 sum is always achievable

        // For each number, update the dp array
        for (int num : nums) {
            // Traverse backwards to avoid reusing the same number
            /**
             * Visualizing with an example for dp[j - num]:
             *
             * Say you want to know if sum 7 is possible (j = 7), and you have a current number num = 3.
             *
             * To make sum 7 including 3, you need a subset that sums to 7 - 3 = 4 without using this 3.
             *
             * If dp[4] is true (meaning you can make sum 4 from the previous numbers), then by adding this 3, you now can make sum 7.
             */
            for (int j = target; j >= num; j--) {
                dp[j] = dp[j] || dp[j - num];
            }
        }

        return dp[target];  // whether target sum is achievable
    }

    public static void main(String[] args) {
        int[] nums = {1, 5, 11, 5};

        boolean result = canPartition(nums);
        System.out.println("Can partition: " + result);  // Expected: true
    }
}

