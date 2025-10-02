package DS;

import java.util.Comparator;
import java.util.PriorityQueue;

public class KthLargestElement {

    /**
     * Returns the kth largest element in the array using a min-heap.
     */
    public static int findKthLargest(int[] nums, int k) {
        // Min-heap to keep track of k largest elements
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);

            // If heap size exceeds k, remove the smallest element
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // The root of the min-heap is the kth largest element
        return minHeap.peek();
    }

    public static void main(String[] args) {
        int[] nums = {7,6,3, 2, 1, 5, 4};
        int k = 3;
        int kthLargest = findKthLargest(nums, k);
        System.out.println(k + "th largest element is: " + kthLargest);  // Output: 5
    }
}

