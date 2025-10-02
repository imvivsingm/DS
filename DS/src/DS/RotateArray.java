package DS;

import java.util.Arrays;

/**
 * Explanation
 *
 * After the full reversal, the array [1, 2, 3, 4, 5, 6, 7] becomes [7, 6, 5, 4, 3, 2, 1].
 *
 * Reversing first k=3 elements results in [5, 6, 7, 4, 3, 2, 1].
 *
 * Reversing remaining elements results in [5, 6, 7, 1, 2, 3, 4].
 */
public class RotateArray {
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;  // Handle cases where k > n

        // Step 1: Reverse the whole array
        reverse(nums, 0, n - 1);

        // Step 2: Reverse first k elements
        reverse(nums, 0, k - 1);

        // Step 3: Reverse the remaining elements
        reverse(nums, k, n - 1);
    }

    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    // Main method to test
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        rotate(nums, k);
        System.out.println("Rotated array: " + Arrays.toString(nums));
    }
}

