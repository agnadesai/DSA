import java.util.LinkedList;


public class ReverseLinkedList {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(11);
        list.add(21);
        list.add(31);
        list.add(41);

        for(int i= list.size() - 1; i >= 0; i--) {
            System.out.println("test" + list.get(i));
        }

        String[] s = {"s", "t"};
        int[] a = {1,2};
    }
}
