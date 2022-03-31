package ca.jrvs.practice.codingChallenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ListNodeChallengesTest {

    @Test
    public void testRemoveNthFromEnd1() {

        ListNodeChallenges.ListNode fifth = new ListNodeChallenges.ListNode(5, null);
        ListNodeChallenges.ListNode fourth = new ListNodeChallenges.ListNode(4, fifth);
        ListNodeChallenges.ListNode third = new ListNodeChallenges.ListNode(3, fourth);
        ListNodeChallenges.ListNode second = new ListNodeChallenges.ListNode(2, third);
        ListNodeChallenges.ListNode head = new ListNodeChallenges.ListNode(1, second);

        ListNodeChallenges tester = new ListNodeChallenges();
        tester.removeNthFromEnd(head, 2);

        assertEquals(5, third.next.val);
    }

    @Test
    public void testRemoveNthFromEnd2() {

        ListNodeChallenges.ListNode second = new ListNodeChallenges.ListNode(2, null);
        ListNodeChallenges.ListNode head = new ListNodeChallenges.ListNode(1, second);

        ListNodeChallenges tester = new ListNodeChallenges();
        tester.removeNthFromEnd(head, 1);

        assertNull(head.next);
    }
}
