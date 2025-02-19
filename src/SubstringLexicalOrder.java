import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SubstringLexicalOrder {
    /// ecbdca -cbd
    public static String findSmallestSubstring(String str) {
        List<String> longestSubstring = new ArrayList<>();
        int maxLength = 0;
        int left = 0;
        String maxString = "";
        for(int right = str.length()-1; right!=0; right--) {
            if((int) str.charAt(left) < (int)str.charAt(right)) {
                longestSubstring.add(str.substring(right-left));
            } else {
                left++;
            }
        }
       for(String longest: longestSubstring) {
           if(longest.length()> maxString.length()) {
               maxString = longest;
           }
       }
       System.out.println("max"+ maxString);
       return maxString;
    }

    public static void main(String[] args) {
        String input = "ecbdca";
        System.out.println("Lexicographically smallest substring: " + findSmallestSubstring(input));
    }

}
