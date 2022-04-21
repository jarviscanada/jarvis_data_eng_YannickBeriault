package ca.jrvs.practice.codingChallenge;

public class ReverseLinkedList {

    public ListNode reverseList(ListNode head) {

        if (head == null || head.next == null)
            return head;

        ListNode nextNode = null;
        ListNode currentNode = head;
        while (currentNode.next != null) {

            ListNode tempNode = currentNode.next;
            currentNode.next = nextNode;
            nextNode = currentNode;
            currentNode = tempNode;
        }

        currentNode.next = nextNode;

        return currentNode;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}