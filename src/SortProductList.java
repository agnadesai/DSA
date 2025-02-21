import java.util.*;

public class SortProductList {
    public static void main(String[] args) {
         List<String> products = new ArrayList<>(Arrays.asList("cherry", "strawberry", "banana"));
         List<String> recommndedProducts = new ArrayList<>(Arrays.asList("banana", "strawberry", "kiwi"));

        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < recommndedProducts.size(); i++) {
            map.put(recommndedProducts.get(i), i);
        }

        products.sort(Comparator.comparingInt(p->map.getOrDefault(p, Integer.MAX_VALUE)));

        for(String p : products) {
            System.out.println("products"+ p);
        }

    }
}
