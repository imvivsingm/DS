package DS;

import java.util.*;

public class GroupAnagrams {

    public static List<List<String>> groupAnagrams(String[] strs) {
        // Map to store sorted word as key and list of anagrams as value
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            // Convert string to char array, sort it, convert back to string
            char[] charArray = s.toCharArray();
            Arrays.sort(charArray);
            String sorted = new String(charArray);

            // Add to map

            map.computeIfAbsent(sorted, k -> new ArrayList<>()).add(s);
        }

        // Return all grouped anagrams
        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        String[] input = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> result = groupAnagrams(input);

        System.out.println("Grouped Anagrams:");
        for (List<String> group : result) {
            System.out.println(group);
        }
    }
}

