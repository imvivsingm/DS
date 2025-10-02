package DS;

import java.util.Arrays;

public class MinimumPlatforms {
    public static int findMinPlatforms(int[] arr, int[] dep) {
        Arrays.sort(arr);
        Arrays.sort(dep);

        int platformNeeded = 1;  // at least one platform needed initially
        int maxPlatforms = 1;

        int i = 1;  // pointer for arrival
        int j = 0;  // pointer for departure

        while (i < arr.length && j < dep.length) {
            if (arr[i] <= dep[j]) {
                platformNeeded++;
                i++;
                maxPlatforms = Math.max(maxPlatforms, platformNeeded);
            } else {
                platformNeeded--;
                j++;
            }
        }

        return maxPlatforms;
    }

    public static void main(String[] args) {
        int[] arr1 = {900, 940, 950, 1100, 1500, 1800};
        int[] dep1 = {910, 1200, 1120, 1130, 1900, 2000};
        System.out.println(findMinPlatforms(arr1, dep1));  // Output: 3

        int[] arr2 = {900, 1235, 1100};
        int[] dep2 = {1000, 1240, 1200};
        System.out.println(findMinPlatforms(arr2, dep2));  // Output: 1
    }
}
