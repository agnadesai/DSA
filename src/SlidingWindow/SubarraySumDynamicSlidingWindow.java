package SlidingWindow;

public class SubarraySumDynamicSlidingWindow {

    public static void main(String[] args) {
        int[] subarray = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(subarraySUm(subarray));
    }

        public static int subarraySUm(int[] nums) {
            int currentSum = 0;
            int maxSum = 0;

            for(int j =0 ; j< nums.length; j++) {
                int num = nums[j];
                currentSum = Math.max(num, currentSum + num);
                maxSum = Math.max(maxSum, currentSum);


        }
            return maxSum;
    }
}
