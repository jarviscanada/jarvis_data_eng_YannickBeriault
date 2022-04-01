package ca.jrvs.practice.codingChallenges;

import java.util.ArrayList;

public class ListNodeChallenges {

    public boolean hasCycle(ListNode head) {

        if (head == null || head.next == null)
            return false;

        ListNode cycleMonitorNode = head;
        ListNode currentNode = head.next;

        for (int i = 0; true; i++) {

            if (currentNode.next != null) {

                if (currentNode.next == cycleMonitorNode)
                    return true;
                else {

                    currentNode = currentNode.next;
                    //Modulo 6 has no effect on lists with sizes < 8 with pos = 0 and seems to work faster
                    // on average with a batch of test cases.
                    if (i % 6 == 0)
                        cycleMonitorNode = cycleMonitorNode.next;
                }
            } else
                return false;
        }
    }

    public ListNode middleNode(ListNode head) {

        if (head == null)
            return null;

        ListNode tailNode = head;
        ListNode middleNode = head;
        int counter = 0;

        while (tailNode.next != null) {

            tailNode = tailNode.next;
            if (counter % 2 == 0)
                middleNode = middleNode.next;

            counter++;
        }

        return middleNode;
    }

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
