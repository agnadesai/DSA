import java.util.HashSet;

public class PartitionString {
    // partion string abacaba - should become ab, ac, ab, a - return 4
    //Leetcode 2405

    //hashset
    public static int partitionString(String s) {
        int substring = 1;
        HashSet<Character> charSet = new HashSet<>();

        for(char c: s.toCharArray()) {
            if(!charSet.contains(c)) {
                charSet.add(c);
                continue;
            }
            substring++;
            charSet.clear();
            charSet.add(c);
        }
        return substring;
    }

    public static void main(String[] args) {
        String s = "abacaba";
        int c = partitionString("abacaba");
        System.out.println("substring" + c);
    }
}
