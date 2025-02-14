import java.util.LinkedList;

public class SwapInLinkedList {

    public static void main(String[] args) {


    LinkedList<Integer> list = new LinkedList<>();

    list.add(11);
    list.add(21);
    list.add(31);
    list.add(41);
    list.add(51);

    System.out.println("size" + list.size());

    // swap 31 to 11
    int index1 = list.indexOf(31);
    int index2 = list.indexOf(11);

    int temp = list.get(index1);
    list.set(index1, list.get(index2));
    list.set(index2, temp);

    for(int i = 0; i < list.size(); i++) {
        System.out.println("test" + list.get(i));
    }

    }
}
