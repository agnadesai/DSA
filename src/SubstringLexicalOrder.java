import java.util.ArrayList;
import java.util.List;

public class SubstringLexicalOrder {
    public static String findSmallestSubstring(String str) {
        String smallest = str;
        String substring = null;
        StringBuilder stringBuilder = new StringBuilder();
        // Generate all possible substrings
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                System.out.println("i " + i);
                System.out.println("char" + str.charAt(i));
                if (i + 1 != str.length()) {
                    if ((int) str.charAt(i) < (int) str.charAt(j)) {
                        System.out.println(str.charAt(i));
                        stringBuilder.append(str.charAt(i));
                    }
                }
                else {
                    if((int)str.charAt(str.length()-2) < (int)str.charAt(str.length()-1)) {
                        stringBuilder.append(str.charAt(str.length()-1));
                    }
                }
            }

                // Compare lexicographically
//                if (substring.compareTo(smallest) < 0) {
//                    smallest = substring;

         //   }
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String input = "ecbdca";
        System.out.println("Lexicographically smallest substring: " + findSmallestSubstring(input));
    }

}
