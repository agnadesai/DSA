package Arrays;
/*
Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.
If target is not found in the array, return [-1, -1].
You must write an algorithm with O(log n) runtime complexity.

Example 1:

Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
 */
public class FirstAndLastPositionSortedArray {
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};

        int firstOccurrence = findBounds(nums, target, true);
        int lastOccurrence = findBounds(nums, target, false);


        return new int[]{ firstOccurrence, lastOccurrence};
    }

    public int findBounds(int[] nums, int target, boolean isFirst) {
        int left = 0;
        int right = nums.length -1;

        while(left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                if (isFirst) {
                    if (mid == 0 || nums[mid - 1] != target) {
                        return mid;
                    }
                    right = mid - 1;
                } else {
                    if(mid == right || nums[mid +1] != target) {
                        return mid;
                    }
                    left = mid+1;
                }

            } else if (nums[mid] < target) {
                left = mid +1;
            } else {
                right = mid -1;
            }
        }
        return target;
    }
}
