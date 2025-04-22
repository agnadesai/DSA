package HashMap;

import java.util.HashMap;
import java.util.HashSet;

public class CrushableWord {
    HashMap<String, Boolean> memo; /// aaaaaaa
    HashSet<String> dictionary;



    public static void main(String[] args) {
        HashSet<String> dictionary = new HashSet<>();
        dictionary.add("SPRINT");
        dictionary.add("PRINT");
        dictionary.add(("PINT"));
        dictionary.add("PIT");
        dictionary.add("IT");
        dictionary.add("I");

        String word = "SPRINT";
        //isCrushable(dictionary, word);
    }

    public boolean isCrushable(HashSet<String> dictionary, String word) {
        this.dictionary = dictionary;
        this.memo = new HashMap<>();

        return this.isCrushable(word);
    }

    public boolean isCrushable(String word) {
        boolean result = false;
        for (int i=0; i< word.length() ; i++) {
            String smallerWord = word.substring(0,i) + word.substring(i+1);
            if(dictionary.contains(smallerWord)) {
                if(isCrushable(dictionary, word)) {
                    result = true;
                }
            }
        }
        memo.put(word, result);
        return false;
    }
}
