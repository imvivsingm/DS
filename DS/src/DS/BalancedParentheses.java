package DS;

import java.util.Stack;

public class BalancedParentheses {

    /**
     * Checks if the input string has balanced parentheses.
     * Supports (), {}, [].
     *
     * @param s The input string
     * @return true if balanced, false otherwise
     */
    public static boolean isBalanced(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            // If opening bracket, push to stack
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }
            // If closing bracket, check top of stack
            else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) {
                    return false; // No matching opening bracket
                }
                char top = stack.pop();

                // Check for matching pairs
                if ((c == ')' && top != '(') ||
                        (c == '}' && top != '{') ||
                        (c == ']' && top != '[')) {
                    return false; // Mismatched pair
                }
            }
            // Ignore other characters (optional, based on your problem)
        }

        // Stack should be empty if all opening brackets are matched
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String test1 = "{[()]}";  // Balanced
        String test2 = "{[(])}";  // Not balanced
        String test3 = "{{[[(())]]}}";  // Balanced

        System.out.println(isBalanced(test1)); // true
        System.out.println(isBalanced(test2)); // false
        System.out.println(isBalanced(test3)); // true
    }
}

