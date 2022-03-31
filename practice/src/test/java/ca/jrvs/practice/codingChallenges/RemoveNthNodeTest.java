package ca.jrvs.practice.codingChallenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RemoveNthNodeTest {

    @Test
    public void testRemoveNthFromEnd1() {

        RemoveNthNode.ListNode fifth = new RemoveNthNode.ListNode(5, null);
        RemoveNthNode.ListNode fourth = new RemoveNthNode.ListNode(4, fifth);
        RemoveNthNode.ListNode third = new RemoveNthNode.ListNode(3, fourth);
        RemoveNthNode.ListNode second = new RemoveNthNode.ListNode(2, third);
        RemoveNthNode.ListNode head = new RemoveNthNode.ListNode(1, second);

        RemoveNthNode tester = new RemoveNthNode();
        tester.removeNthFromEnd(head, 2);

        assertEquals(5, third.next.val);
    }

    @Test
    public void testRemoveNthFromEnd2() {

        RemoveNthNode.ListNode second = new RemoveNthNode.ListNode(2, null);
        RemoveNthNode.ListNode head = new RemoveNthNode.ListNode(1, second);

        RemoveNthNode tester = new RemoveNthNode();
        tester.removeNthFromEnd(head, 1);

        assertNull(head.next);
    }
}
