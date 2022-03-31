package ca.jrvs.practice.codingChallenges;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class RemoveNthNode {

    public ListNode removeNthFromEnd(ListNode head, int n) {

        if (head == null || head.next == null)
            return null;

        ArrayList<ListNode> nodeArray = new ArrayList<>();
        ListNode currentNode = head;

        while (currentNode != null) {

            nodeArray.add(currentNode);
            currentNode = currentNode.next;
        }

        int size = nodeArray.size();
        int index;

        if (n == 1)
            nodeArray.get(size - 2).next = null;
        else {
            index = size - n;

            if (index == 0)
                head = head.next;
            else
                nodeArray.get(index - 1).next = nodeArray.get(index).next;
        }

        return head;
    }

     public static class ListNode {

         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
}
