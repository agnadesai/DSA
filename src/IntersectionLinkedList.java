import java.awt.*;

public class IntersectionLinkedList {

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // System.out.println("headA"+ headA.next.val);
        while(headA != null) {
            ListNode pB = headB;
            while(pB != null) {
                if(headA == pB) {
                    return headA;
                }
                pB = pB.next;
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

