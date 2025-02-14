import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LargestSubstring {
    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;

        Map<Character, Integer> map = new HashMap<>();
        // s to character array - forloop (add to hashmap)
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                System.out.println(" j " + s.charAt(j));
                System.out.println("charToNextIndex.get(s.charAt(j))"+ map.get(s.charAt(j)));
                i = Math.max(map.get(s.charAt(j)), i);

            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        //map.keySet().size();
//    }
       return ans;
    }

    public static void main(String[] args) {

        int a = lengthOfLongestSubstring("pwwkew");
        System.out.println("a" +a);
    }
}
