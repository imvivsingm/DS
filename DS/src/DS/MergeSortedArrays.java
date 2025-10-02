package DS;

import java.util.*;

public class MergeSortedArrays {
    public static int[] mergeSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] result = new int[m + n];
        int i = 0, j = 0, k = 0;

        // Merge both arrays
        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                result[k++] = nums1[i++];
            } else {
                result[k++] = nums2[j++];
            }
        }

        // Copy remaining elements
        while (i < m) result[k++] = nums1[i++];
        while (j < n) result[k++] = nums2[j++];

        return result;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 3, 5};
        int[] nums2 = {2, 4, 6};

        int[] merged = mergeSortedArrays(nums1, nums2);
        System.out.println("Merged Array: " + Arrays.toString(merged));
    }
}

