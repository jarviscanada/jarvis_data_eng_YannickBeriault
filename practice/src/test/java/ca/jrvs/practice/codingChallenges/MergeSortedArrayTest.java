package ca.jrvs.practice.codingChallenges;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MergeSortedArrayTest {

    @Test
    public void testMerge() {

        int[] nums1 = new int[] {4,5,6,0,0,0};
        int[] nums2 = new int[] {1,2,3};
        MergeSortedArrays mergeSortedArrays = new MergeSortedArrays();

        mergeSortedArrays.merge(nums1, 3, nums2, 3);

        assertArrayEquals(new int[] {1,2,3,4,5,6}, nums1);
    }
}