package DS;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstringWithoutRepeating {


    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int left = 0, maxLen = 0;
        Set<Character> seen = new HashSet<>();

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            while (seen.contains(c)) {
                seen.remove(s.charAt(left));
                left++;
            }
            seen.add(c);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        String s1 = "abcabcbb";
        String s2 = "bbbbb";
        String s3 = "pwwkew";
        String s4 = "";

        System.out.println("Length of longest substring without repeating characters:");
        System.out.println("Input: " + s1 + " -> Output: " + lengthOfLongestSubstring(s1));  // 3 ("abc")
        System.out.println("Input: " + s2 + " -> Output: " + lengthOfLongestSubstring(s2));  // 1 ("b")
        System.out.println("Input: " + s3 + " -> Output: " + lengthOfLongestSubstring(s3));  // 3 ("wke")
        System.out.println("Input: " + s4 + " -> Output: " + lengthOfLongestSubstring(s4));  // 0
    }
}

