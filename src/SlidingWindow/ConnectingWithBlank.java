package SlidingWindow;

import java.util.ArrayList;
import java.util.List;

//Connecting words with '-' as blank spaces, no exceeds maxLength
// e.g. ["1p3acres", "is", "a", "good", "place", "to", "communicate"], 12 => {"1p3acres-is", "a-good-place", "for", "communicate"}
//
// Given input  ["1p3acres", "is", "a", "good", "place", "to", "communicate"]
//should return {"1p3acres-is", "a-good-place", "for", "communicate"}
public class ConnectingWithBlank {
    public static List<String> wrapLines(String[] words, int maxLength) {
        List<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int p = 0;

        while (p < words.length) {
            if (sb.length() == 0) { // If the StringBuilder is empty
                sb.append(words[p]);
                p++;
            } else if (sb.length() + 1 + words[p].length() <= maxLength) { // If we can add the next word with a space
                sb.append('-').append(words[p]);
                p++;
            } else { // If we can't add any more words, save the line and reset StringBuilder
                ans.add(sb.toString());
                sb.setLength(0); // Clear the StringBuilder for the next line
            }
        }
        if (sb.length() > 0) { // Add the last line if there's remaining text
            ans.add(sb.toString());
        }

        return ans;
    }

    public static void main(String[] args) {
        String[] inputWords = {"1p3acres", "is", "a", "good", "place", "to", "communicate"};
        int maxLength = 12;
        List<String> result = wrapLines(inputWords, maxLength);
        System.out.println(result); // Output: ["1p3acres-is", "a-good-place", "to-communicate"]
    }
}