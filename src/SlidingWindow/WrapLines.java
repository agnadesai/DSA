package SlidingWindow;

import java.util.ArrayList;
import java.util.List;

public class WrapLines {
    public static List<String> wrapLines(String[] words, int maxLength) {
        List<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int p = 0;

        while (p < words.length) {
            if (sb.length() == 0) {
                // Add first word
                sb.append(words[p]);
            } else if (sb.length() + 1 + words[p].length() <= maxLength) {
                // Add word with a space
                sb.append("-").append(words[p]);
            } else {
                // Add the current line to answer and reset the StringBuilder
                ans.add(sb.toString());
                sb = new StringBuilder(words[p]);
            }
            p++;
        }

        // Add the last line if not empty
        if (sb.length() > 0) {
            ans.add(sb.toString());
        }

        return ans;
    }

    public static void main(String[] args) {
        String[] words = {"123", "45", "67", "8901234", "5678"};
        int maxLength = 10;
        List<String> result = wrapLines(words, maxLength);
        System.out.println(result); // should print: [ "123-45-67", "8901234", "5678" ]
    }
}
