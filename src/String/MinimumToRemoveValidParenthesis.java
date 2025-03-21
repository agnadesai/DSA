package String;

import java.util.*;

public class MinimumToRemoveValidParenthesis {

        public static String minRemoveToMakeValid(String s) {
            Set<Integer> indexesToRemove = new HashSet<>();
            Deque<Integer> stack = new ArrayDeque<>();

            for(int i = 0; i<s.length(); i++) {
                if(s.charAt(i) == '(') {
                    stack.push(i);
                } if(s.charAt(i) == ')') {
                    if(stack.isEmpty()) {
                        indexesToRemove.add(i);
                    } else {
                        stack.pop();
                    }
                }
            }
            while(!stack.isEmpty()) indexesToRemove.add(stack.pop());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< s.length(); i++) {
                if(!indexesToRemove.contains(i)){
                    sb.append(s.charAt(i));
                }
            }
            return sb.toString();

    }
    public static void main(String[] args) {
        String s = minRemoveToMakeValid("l(t))lee(t(c)o)de)");
        System.out.println("s"+ s);
      //  return "";
    }

}
