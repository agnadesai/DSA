import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SubstringLexicalOrder {
    /// ecbdca -cbd
    public static Integer findMaxChainLength(String str) {
        List<String> longestSubstring = new ArrayList<>();
        int maxLength = 0;
        int left = 0;
        String maxString = "";

        for(int i = 0; i < str.length()-1; i++) {
            for(int j=i+1; j<str.length(); j++) {
                if(str.charAt(i) < str.charAt(j)) {
                    maxLength = Math.max(maxLength, j-i+1);
                }
            }
        }

       return maxLength;
    }

    public static void main(String[] args) {
        String input = "abcd";
        System.out.println("Lexicographically smallest substring: " + findMaxChainLength(input));
    }

}
