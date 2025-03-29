package backtracking;

import java.util.HashMap;
import java.util.HashSet;
//"ab", "redblue"
public class WordPatternII {

    // Driver Code
    public static void main(String[] args) {
        WordPatternII solution = new WordPatternII();

        System.out.println(solution.wordPatternMatch("ab", "redblue")); // true
        //System.out.println(solution.wordPatternMatch("aaaa", "asdasdasdasd")); // true
       // System.out.println(solution.wordPatternMatch("aabb", "xyzabcxzyabc")); // false
    }

    public boolean wordPatternMatch(String pattern, String s) {
        return backtrack(pattern, s, new HashMap<>(), new HashSet<>(), 0, 0);
    }

    private boolean backtrack(String pattern, String s, HashMap<Character, String> map, HashSet<String> set, int i, int j) {
        // Base case: if both pattern and string are fully matched
        if (i == pattern.length() && j == s.length()) {
            return true;
        }
        // If one is finished but the other is not, return false
        if (i == pattern.length() || j == s.length()) {
            return false;
        }

        char c = pattern.charAt(i);

        // If character is already mapped
        if (map.containsKey(c)) {
            String mappedStr = map.get(c);
            // Check if the string at position j starts with the mapped substring
            if (!s.startsWith(mappedStr, j)) {
                return false;
            }
            // If it matches, continue recursion with next indices
            return backtrack(pattern, s, map, set, i + 1, j + mappedStr.length());
        }

        // Try mapping new substrings
        for (int k = j; k < s.length(); k++) {
            String sub = s.substring(j, k + 1);

            // If this substring is already used, skip it
            if (set.contains(sub)) continue;

            // Assign new mapping and mark it as used
            map.put(c, sub);
            set.add(sub);

            // Recurse with next character
            if (backtrack(pattern, s, map, set, i + 1, k + 1)) {
                return true;
            }

            // Backtrack: undo assignment
            map.remove(c);
            set.remove(sub);
        }

        return false;
    }


}
