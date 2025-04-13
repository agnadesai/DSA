package heap;

import java.util.HashMap;
import java.util.PriorityQueue;

public class TopKElements {

    public static void main(String[] args) {
        int nums[] = {1,1,1, 2, 2, 3};
        int  k = 2;
        int[] ans = topKElements(nums, k);
    }

    public static int[] topKElements(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap();

        for(int i = 0 ; i< nums.length; i++) {
           map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        // heap - insrting it will keep the lower item on top in the binrary tree
        //
        PriorityQueue<Integer> heap =
                new PriorityQueue<>((n1, n2) -> map.get(n1) - map.get(n2));


        for (int key: map.keySet()) {
            System.out.println( " key " + key);
            heap.add(key);
            if(heap.size() > k) {
                heap.poll();
            }


        }

        int[] top = new int[k];
        for(int i=k-1; i>=0;--i) {
            top[i] = heap.poll();
        }

        return top;
    }
}
