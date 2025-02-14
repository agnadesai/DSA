import java.util.ArrayList;

public class FizzBuzz {

    public static void main(String[] args) {
        int n = 20;
        ArrayList<String> res = fizzBuzz(n);
        for (String s : res) {
            System.out.print(s + " ");
        }
    }
        static ArrayList<String> fizzBuzz(int n) {
        ArrayList<String> res = fizzBuzz(n);

        for(int i = 0; i< n; i++) {
            if(i%3 == 0) {
                res.add("fizz");
            }
        }
            return res;
        }

        }

