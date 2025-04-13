package HashMap;

import java.util.*;

class LongestSubsequenceTiles {

    public static void main(String[] args) {
        String[] A = { "RR", "GR", "RG", "GR", "GR", "RR"};
        int answer = solution(A);
        System.out.print("answer " + answer); // should be 5

        String[] B = { "GG", "GG", "RR", "GG", "RR"};
        int answer2 = solution(B);
        System.out.print("answer " + answer2); // should be 3

    }
    public static int solution(String[] A) {
        Map<String, Integer> tileCount = new HashMap<>();
        for (String tile : A) {
            tileCount.put(tile, tileCount.getOrDefault(tile, 0) + 1);
        }

        int maxLen = 0;
        for (String tile : tileCount.keySet()) {
            Map<String, Integer> used = new HashMap<>();
            used.put(tile, 1);
            maxLen = Math.max(maxLen, dfs(tile, tileCount, used, 1));
        }

        return maxLen;
    }

    private static int dfs(String current, Map<String, Integer> tiles, Map<String, Integer> used, int length) {
        int max = length;
        char right = current.charAt(1);

        for (String next : tiles.keySet()) {
            if (next.charAt(0) == right) {
                int usedCount = used.getOrDefault(next, 0);
                if (usedCount < tiles.get(next)) {
                    used.put(next, usedCount + 1);
                    max = Math.max(max, dfs(next, tiles, used, length + 1));
                    used.put(next, usedCount); // backtrack
                }
            }
        }

        return max;
    }
}
