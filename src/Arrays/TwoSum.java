package Arrays;

import java.util.HashMap;

public class TwoSum {

    //check if sum is equal to target
    //optimized solution with hashmap
    //first create hashmap with int , int - store num as key and index as the value
    public static void main(String[] args) {
        int[] ints = twoSUm(new int[]{3, 2, 4}, 6);
        System.out.print("ints" + ints[0] + "ints 1 " + ints[1]);


    }

    public static int[] twoSUm(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i< nums.length;i++) {
            int complement = target - nums[i];
            System.out.println("complement"+ complement);
            if(map.containsKey(complement)) {
                return new int[] { map.get(complement), i};
            }
            map.put(nums[i],i);
        }
        return new int[]{};
    }
}
