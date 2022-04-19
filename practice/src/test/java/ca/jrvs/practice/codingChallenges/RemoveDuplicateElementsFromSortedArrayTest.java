package ca.jrvs.practice.codingChallenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RemoveDuplicateElementsFromSortedArrayTest {

    @Test
    public void testRemoveDuplicates() {

        RemoveDuplicatesFromSortedArray rem = new RemoveDuplicatesFromSortedArray();

        assertEquals(5, rem.removeDuplicates(new int[] {0,0,1,1,1,2,2,3,3,4}));
    }
}
