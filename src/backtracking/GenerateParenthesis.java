package backtracking;

import java.util.ArrayList;
import java.util.List;

/*
n=2
["(())","()()"]
 */
public class GenerateParenthesis {

    public static void main(String[] args) {

        List<String> strings = generateParenthesis(2);
        System.out.println("test" + strings.size());

        System.out.println(String.join(",", strings));


    }

    private static List<String> generateParenthesis(int n) {
        List<String> answer = new ArrayList<>();
        backtracking(answer, new StringBuilder(), 0, 0, n);
        return answer;
    }

    private static void backtracking(List<String> answer, StringBuilder curString,
                                     int leftcount, int rightcount, int n) {
        if(curString.length() == 2*n) {
            answer.add(curString.toString());
            return;
        }

        if(leftcount < n) {
            curString.append("(");
            backtracking(answer, curString, leftcount+1, rightcount, n);
            curString.deleteCharAt(curString.length()-1);
        }

        if( rightcount < leftcount) {
            curString.append(")");
            backtracking(answer, curString, leftcount, rightcount+1, n);
            curString.deleteCharAt(curString.length()-1);
        }

    }
}
