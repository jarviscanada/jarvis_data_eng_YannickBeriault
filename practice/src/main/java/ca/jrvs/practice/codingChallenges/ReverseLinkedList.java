package ca.jrvs.practice.codingChallenge;

import java.util.List;

public class ReverseLinkedList {

    private static ListNode reversedHead;

    public ListNode reverseList(ListNode head) {

        if (head == null)
            return null;

        recursiveReverse(head);
        head.next = null;

        return reversedHead;
    }

    private ListNode recursiveReverse(ListNode head) {

        if (head.next == null) {

            reversedHead = head;
            return head;
        }

        ListNode nuHead = recursiveReverse(head.next);
        nuHead.next = head;

        return head;
    }

    public ListNode reverseListThroughIteration(ListNode head) {

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