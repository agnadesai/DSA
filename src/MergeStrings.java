public class MergeStrings {

    public static void main(String[] args) {
        String s1 = "abcde";
        String s2 = "pqr";
        mergeAlternatively(s1, s2);
    }

    public static String mergeAlternatively(String s1, String s2) {
     int l1 = s1.length();
     int l2 = s2.length();

     int p1 = 0; int p2 = 0;

        StringBuilder sb = new StringBuilder();
        while(p1 < l1 || p2 < l2) {
            if (p1 < l1) {
                sb.append(s1.charAt(p1));
                p1++;
            }
            if (p2 < l2) {
                sb.append(s2.charAt(p2));
                p2++;
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

}
