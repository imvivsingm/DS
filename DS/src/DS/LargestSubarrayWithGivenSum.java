package DS;

import java.util.*;

public class LargestSubarrayWithGivenSum {

    /**
     * Finds the largest contiguous subarray whose elements sum to the target value.
     *
     * @param nums The input array (can contain negative numbers)
     * @param targetSum The desired sum of the subarray
     * @return The largest subarray that sums to targetSum, or empty array if none exists
     */
    public static int[] findLargestSubarray(int[] nums, int targetSum) {
        // Map to store the first occurrence of each prefix sum
        Map<Integer, Integer> prefixSumToIndex = new HashMap<>();

        int prefixSum = 0;
        int maxLength = 0;
        int startIndexOfMax = -1;

        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];  // Accumulate sum up to index i

            // Case 1: If the entire subarray from index 0 to i sums to targetSum
            if (prefixSum == targetSum) {
                maxLength = i + 1;
                startIndexOfMax = 0;
            }

            // Case 2: Check if there is a previous prefixSum such that:
            // prefixSum - previousPrefixSum = targetSum
            // => subarray between those indices sums to targetSum
            /*
            Imagine array: [1, 2, 3, 4, 5] and targetSum = 9

            prefixSum at i=3 (sum of first 4 elements) = 1 + 2 + 3 + 4 = 10

            Calculate neededPrefixSum = prefixSum - targetSum = 10 - 9 = 1

            Check if prefix sum 1 exists before i=3:

            Yes! At i=0, prefixSum was 1.

            So the subarray from i=0 + 1 = 1 to i=3 (elements [2, 3, 4]) sums to 9.
             */
            int neededPrefixSum = prefixSum - targetSum;
            if (prefixSumToIndex.containsKey(neededPrefixSum)) {
                int previousIndex = prefixSumToIndex.get(neededPrefixSum);
                int currentLength = i - previousIndex;

                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    startIndexOfMax = previousIndex + 1;
                }
            }

            // Store the first occurrence of this prefixSum
            // We only store the first to ensure longest possible subarray
            if (!prefixSumToIndex.containsKey(prefixSum)) {
                prefixSumToIndex.put(prefixSum, i);
            }
        }

        // No valid subarray found
        if (startIndexOfMax == -1) {
            return new int[0];
        }

        // Return the largest subarray found
        return Arrays.copyOfRange(nums, startIndexOfMax, startIndexOfMax + maxLength);
    }

    // Test the code with a sample input
    public static void main(String[] args) {
        int[] nums = {1, -1, 5, -2, 3};
        int targetSum = 3;

        int[] result = findLargestSubarray(nums, targetSum);

        System.out.println("Largest subarray with sum " + targetSum + ": " + Arrays.toString(result));
    }
}
