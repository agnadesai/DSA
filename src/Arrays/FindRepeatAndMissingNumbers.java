package Arrays;

import java.util.*;

// [3,1,2,3]
// [3,4]
public class FindRepeatAndMissingNumbers {

    public static int[] solution(int[] input) {
        Arrays.sort(input);
        int length = input.length;
        HashMap<Integer, Integer> hashMap = new HashMap<>();

        for(int j: input) {
            hashMap.computeIfAbsent(j,key->hashMap.getOrDefault(j,0));
        }


        Set<Integer> setint = new HashSet<>(); // 1,2,3
        List<Integer> listInterger = new ArrayList<>(); // 3
        for (int j : input) {
            if(!setint.contains(j)) {
                setint.add(j);
            } else {
                listInterger.add(j);
            }
        }

        for(int i=1;i<=length;i++) {
            if(!setint.contains(i)) {
               listInterger.add(i);
            }
        }
       // int[] intsarrya = listInterger.toArray();

        int[] array = listInterger.stream().mapToInt(Integer::intValue).toArray();
        return array;
    }

    public static void main(String[] args) {
        int[] input = new int[]{3,1,2,3};
        int[] solution = solution(input);
        for(int i: solution) {
            System.out.println("i " + i);

        }
    }
}
