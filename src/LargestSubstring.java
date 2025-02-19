import java.util.*;
import java.util.stream.Collectors;

public class LargestSubstring {
    public static int lengthOfLongestSubstring(String s) {
        Set<Character> hashSet = new HashSet<Character>();
        int maxLength = 0;
        int left = 0;

        for(int right = 0; right<s.length(); right++) {
            if(!hashSet.contains(s.charAt(right))) {
                hashSet.add(s.charAt(right));
                maxLength = Math.max(maxLength, right-left+1);
            } else {
                while(s.charAt(left)!= s.charAt(right)) {
                    hashSet.remove(s.charAt(left));
                    left++;
                }
                hashSet.remove(s.charAt(left));
                left++;
                hashSet.add(s.charAt(right));
            }
        }
        return maxLength;
    }
    public static void main(String[] args) {

        int a = lengthOfLongestSubstring("pwwkew");
        System.out.println("a" +a);
    }
}
