package backtracking;

import java.util.ArrayList;
import java.util.List;

public class PermutationsWithDetailedTrace {

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        backtrack(nums, new ArrayList<>(), results, new boolean[nums.length], 0);
        return results;
    }

    private static void backtrack(int[] nums, List<Integer> currentPermutation,
                                  List<List<Integer>> results, boolean[] used, int depth) {

        // Print current call stack (indentation based on recursion depth)
        System.out.println("Depth " + depth + ": Current permutation: " + currentPermutation);

        // If complete permutation found
        if (currentPermutation.size() == nums.length) {
            System.out.println("** Found complete permutation: " + currentPermutation + "\n");
            results.add(new ArrayList<>(currentPermutation));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;

            // Choose
            currentPermutation.add(nums[i]);
            used[i] = true;
            System.out.println("Depth " + (depth + 1) + ": Added " + nums[i] + " (i=" + i + "), permutation now: " + currentPermutation);

            // Explore deeper
            backtrack(nums, currentPermutation, results, used, depth + 1);

            // Undo choice (backtrack)
            int removed = currentPermutation.remove(currentPermutation.size() - 1);
            used[i] = false;
            System.out.println("Depth " + (depth + 1) + ": Removed " + removed + " (i=" + i + "), back to permutation: " + currentPermutation);
        }

        System.out.println("Backtracking from depth " + depth + ": " + currentPermutation + "\n");
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        List<List<Integer>> permutations = permute(nums);

        System.out.println("All permutations:");
        for (List<Integer> perm : permutations) {
            System.out.println(perm);
        }
    }
}



