import java.awt.*;
import java.util.HashSet;

public class IntersectionLinkedListWithHashSet {

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // System.out.println("headA"+ headA.next.val);
        HashSet<ListNode> listNodesSetB = new HashSet<>();
        while(headB!=null) {
            listNodesSetB.add(headB);
            headB = headB.next;
        }

        while(headA!=null) {
            if(listNodesSetB.contains(headA)) {
                return headA;
            }
            headA = headA.next;
        }
        return null;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(10);
        listNode.next = new ListNode(15);

        listNode.next.next = new ListNode(30);

        // creation of second list: 3 -> 6 -> 9 -> 15 -> 30
        ListNode head2 = new ListNode(3);
        head2.next = new ListNode(6);
        head2.next.next = new ListNode(9);
        head2.next.next.next = listNode.next;

        ListNode intersectionPoint = getIntersectionNode(listNode, head2);
        System.out.println("intersection" + intersectionPoint.val);

    }

}

