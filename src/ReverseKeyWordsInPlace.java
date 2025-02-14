import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReverseKeyWordsInPlace {



        public static void reverseWords(char[] s) {
            reverse(s, 0, s.length - 1);
            System.out.println(s);
            int i = 0;
            for (int j = 0; j <= s.length; j++) {
                if (j == s.length || s[j] == ' ') {
                    reverse(s, i, j - 1);
                    i = j + 1;
                }
            }
            System.out.println(s);

        }

        public static void reverse(char[] s, int left, int right) {
            while (left < right) {
                char temp = s[left];
                s[left] = s[right];
                s[right] = temp;
                left++;
                right--;
            }
        }

    public static void main(String[] args)   {
        char[] input = "the sky is blue".toCharArray();
        reverseWords(input);
        }

}
