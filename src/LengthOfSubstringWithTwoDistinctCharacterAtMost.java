import java.util.Collections;
import java.util.HashMap;

public class LengthOfSubstringWithTwoDistinctCharacterAtMost {

    public static int lengthOfLongestSubstringTwoDistinct(String s) {
        int left = 0;
        int right = 0;
        int n = s.length();
        int maxLength = 0;
        HashMap<Character, Integer> hashMap = new HashMap();
        maxLength = 2;
        while(right < n) {
            hashMap.put(s.charAt(right), right++);
            if(hashMap.size()==3) {
                int delIndex = Collections.min(hashMap.values());
                hashMap.remove(s.charAt(delIndex));
                left = delIndex+1;
            }
            maxLength = Math.max(maxLength, right-left);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        int t = lengthOfLongestSubstringTwoDistinct("lengthOfLongestSubstringTwoDistinct");

    }
}
