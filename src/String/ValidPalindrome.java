package String;
//valid palindrom by atmost removing one character i.e abca - should return true
// abcd should return false
public class ValidPalindrome {

    public boolean validPalindrome(String s) {
        int i=0;
        int j = s.length()-1;

        while(i<j) {
            if(s.charAt(i)!=s.charAt(j)) {
                return checkPalindrome(s, i, j-1) || checkPalindrome(s, i+1, j);
            }
        }
        return true;
    }
    public boolean checkPalindrome(String s, int i, int j) {
        while(i<j) {
            if(s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}
