package DS;

import java.util.*;

public class PalindromeRearrangement {

    /**
     * Rearranges the given string to form a palindrome if possible.
     * If not possible, returns an appropriate message.
     *
     * @param s The input string
     * @return A palindrome rearrangement or a message if impossible
     */
    public static String rearrangeToPalindrome(String s) {
        // Array to count frequency of each character (assuming ASCII)
        int[] charCount = new int[256];

        // Count frequency of each character in the string
        for (char c : s.toCharArray()) {
            charCount[c]++;
        }

        int oddCount = 0;  // Counts how many characters have odd frequency
        char oddChar = 0;  // Stores the character with odd frequency (if any)

        // Check the frequency of each character to determine if palindrome is possible
        for (int i = 0; i < 256; i++) {
            if (charCount[i] % 2 != 0) {  // If count is odd
                oddCount++;
                oddChar = (char) i;       // Remember the odd frequency character
            }
            // More than one odd frequency means palindrome is not possible
            if (oddCount > 1) {
                return "Not possible to form a palindrome";
            }
        }

        // StringBuilder to construct the first half of the palindrome
        StringBuilder firstHalf = new StringBuilder();

        // Add half of each character count to the first half of palindrome
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < charCount[i] / 2; j++) {
                firstHalf.append((char) i);
            }
        }

        // Start building the full palindrome
        StringBuilder palindrome = new StringBuilder(firstHalf);

        // If there's a character with odd count, add it in the middle
        if (oddCount == 1) {
            palindrome.append(oddChar);
        }

        // Append the reverse of the first half to complete the palindrome
        palindrome.append(firstHalf.reverse());

        // Return the palindrome string
        return palindrome.toString();
    }

    public static void main(String[] args) {
        String s = "aabbcc";
        System.out.println(rearrangeToPalindrome(s));  // Example output: "abccba" or similar palindrome
    }
}

