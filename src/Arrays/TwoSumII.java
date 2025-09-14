package Arrays;

public class TwoSumII {

    public static void main(String[] args) {
        int[] numbers = {5, 25,75 };
        int target = 100;
        twoSum(numbers, target);
    }

    public static int[] twoSum(int[] nums, int target) {
        int p1 = 0;
        int p2 = nums.length -1;
        while(p1<p2) {
            int sum = nums[p1] + nums[p2];

            if(sum == target) {
                return new int[]{p1, p2};
            } else if(sum<target) {
                p1++;
            } else {
                p2--;
            }
        }
    return new int[]{-1,-1};
    }
}
