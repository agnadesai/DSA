package Arrays;

public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        int maxProfit = bestTimeToBuyOrSellStock(new int[]{7, 1, 5, 3, 6, 4});
        System.out.print("maxProfit" + maxProfit);
    }

    public static int bestTimeToBuyOrSellStock(int[] prices) {
        int maxProfit = 0;
        int j = 0;

        for (int i = 1; i < prices.length; i++) {
            int compare = prices[i] - prices[j]; // j buy i sell
            if (compare > maxProfit) {
                maxProfit = compare;
            }
            if (prices[i] < prices[j]) { // 1< 7
                j = i; // j = 1
            }
        }
        return maxProfit;
    }
}
