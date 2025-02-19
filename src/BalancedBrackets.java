import java.awt.event.AdjustmentEvent;
import java.util.HashSet;
import java.util.Stack;

public class BalancedBrackets {

    public static void main(String[] args) {
        String s = "{[()]}";


        boolean b = balancedBrackets(s);
        System.out.println(b);
    }

        public static boolean balancedBrackets(String s){
            if (s.length() % 2 != 0) {
                return false;
            }

            Stack<Character> stack = new Stack<>();
            for(Character ch : s.toCharArray()) {
                switch (ch) {
                    case ('('):
                    case ('['):
                    case ('{'):
                        stack.push(ch);
                    case(')'):
                    case(']'):
                    case('}'):
                      //  stack.peek(ch);
                    default:
                        stack.pop();
                }
            }
        return true;
        }

}
