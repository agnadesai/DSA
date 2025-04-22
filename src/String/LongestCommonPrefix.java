package String;

import java.util.Arrays;

public class LongestCommonPrefix {
    public static void main(String[] args) {
        String string = longestCommonPrefix(new String[]{"flower", "flow", "flight"
        });
        System.out.println("output " + string);
    }

    public static String longestCommonPrefix(String[] v) {
        Arrays.sort(v); // doing this will arrange strings in lexical order so we only have to compre first and last string
        String s1 = v[0];
        String s2 = v[v.length - 1];
        int idx = 0;
        while (idx < s1.length() && idx < s2.length()) {
            if (s1.charAt(idx) == s2.charAt(idx)) {
                idx++;
            } else {
                break;
            }
        }
        return s1.substring(0, idx);
    }
}
