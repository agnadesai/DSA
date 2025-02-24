package Arrays;

public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        int maxProfit = bestTimeToBuyOrSellStock(new int[]{7,1,5,3,6,4});
        System.out.print("maxProfit" + maxProfit);
    }

    public static int bestTimeToBuyOrSellStock(int[] prices) {
        int maxProfit = 0;
        int j = 0;

        for(int i=1;i<prices.length;i++) {
            int compare = prices[i] - prices[j];
            if(compare > maxProfit) {
                maxProfit = compare;
            }
            if(prices[i] < prices[j]) {
                j=i;
            }
        }
        return maxProfit;
    }
}
