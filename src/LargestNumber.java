import java.util.Arrays;

public class LargestNumber {

    public static void main(String[] args) {
        int[] input = {3, 30, 34, 5, 9};
        System.out.println(largestNumber(input));
    }

    public static String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) return "";
        String[] strNums = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strNums[i] = String.valueOf(nums[i]);
        }
        System.out.println("StrNums" + Arrays.toString(strNums));
        Arrays.sort(strNums, (a, b) -> {
            System.out.println(" a " + a + " b " + b);
            System.out.println(" b+a " + b+a);
            System.out.println(" a+b " + a+b);
            System.out.println(" b+a.compareTo(a+b) " + (b + a).compareTo(a + b));
            return (a + b).compareTo(b + a);
        });
        System.out.println("StrNums" + Arrays.toString(strNums));
        if (strNums[0].equals("0")) return "0";
        StringBuilder sb = new StringBuilder();
        for (String s : strNums) {
            sb.append(s);
        }
        return sb.toString();
    }
}
