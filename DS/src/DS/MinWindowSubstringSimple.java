package DS;

public class MinWindowSubstringSimple {
    public static String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";

        int[] freq = new int[128];  // ASCII character frequency for t
        for (char c : t.toCharArray()) {
            freq[c]++;
        }

        int left = 0, count = t.length(), minStart = 0, minLen = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);

            if (freq[rightChar] > 0) {
                count--;  // one char matched
            }
            freq[rightChar]--;

            // When all characters are matched, try to shrink from the left
            while (count == 0) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }

                char leftChar = s.charAt(left);
                freq[leftChar]++;
                if (freq[leftChar] > 0) {
                    count++;  // we lost a needed char
                }
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }

    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println(minWindow(s, t));  // Output: "BANC"
    }
}

