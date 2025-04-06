package Arrays;
/*Remove duplicates from a sorted array – II
[1,1,1,2,2,3]
[1,1,2,2,3, _]
 In place
*/
class MergedSortedArray {
    public int removeDuplicates(int[] nums) {
// check if number of length is not zero
        if (nums.length == 0) {
            return 0;
        }
// initialize both pointers at 1, counter to 1
        int i = 1;
        int j = 1;
        int count = 1;
// while i does not reach end of nums
        while (i < nums.length) {
//
            if (nums[i] == nums[i - 1]) {
                count++;
                if (count > 2) {
                    i++;
                    continue;
                }
            } else {
                count = 1;
            }
            nums[j] = nums[i];
            i++;
            j++;
        }
        return j;

    }
}