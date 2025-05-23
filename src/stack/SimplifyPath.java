package stack;

import java.util.Stack;

public class SimplifyPath {
    class Solution {
        public String simplifyPath(String path) {
            Stack<String> stack = new Stack<String>();
            String[] components = path.split("/");

            // split the string
            for(String directory: components) {
                if(directory.equals(".") || directory.isEmpty()) {
                    continue;
                } else if(directory.equals("..")) {
                    if(!stack.isEmpty()) {
                        stack.pop();
                    }
                } else {
                    stack.add(directory);
                }
            }

            StringBuilder result = new StringBuilder();
            for(String dir: stack) {
                result.append("/");
                result.append(dir);
            }
            return result.length()>0 ? result.toString() : "/";
        }
    }
}
