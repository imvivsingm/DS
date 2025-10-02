package DS;

import java.util.Arrays;

public class MinimumPlatformNeeded {
    public static int findMinimumPlatforms(int[] arrival, int[] departure) {
        Arrays.sort(arrival);
        Arrays.sort(departure);

        int platformNeeded = 0, maxPlatforms = 0;
        int i = 0, j = 0;
        int n = arrival.length;

        while (i < n && j < n) {
            if (arrival[i] <= departure[j]) {
                platformNeeded++;
                i++;
            } else {
                platformNeeded--;
                j++;
            }
            maxPlatforms = Math.max(maxPlatforms, platformNeeded);
        }
        return maxPlatforms;
    }
    public static void main(String[] args) {
        int[] arrival = {900, 900, 900, 900, 900, 900};
        int[] departure = {910, 910, 916, 920, 1900, 2000};

        int result = findMinimumPlatforms(arrival, departure);
        System.out.println("Minimum number of platforms needed: " + result);
    }
}
