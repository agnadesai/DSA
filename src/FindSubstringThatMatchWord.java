import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindSubstringThatMatchWord {

    public static List<Integer> solution(String s, String[] words) {
        List<Integer> answer = new ArrayList<>();
        int n1 = words[0].length();
        int n2 = s.length();

        Map<String, Integer> map= new HashMap<>();
        for(String word: words) {
            map.put(word, map.getOrDefault(word,0)+1);
        }
    return answer;
    }

    public static void main(String[] args) {
        String s = "barfoofoobarthefoobarman";
        String[] words = {"bar","foo","the"};
        List<Integer> solution = solution(s, words);
    }
}
