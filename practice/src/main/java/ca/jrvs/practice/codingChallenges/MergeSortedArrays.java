package ca.jrvs.practice.codingChallenges;

/**
 * https://www.notion.so/jarvisdev/Merge-Sorted-Array-45d21d57d8194edba45aa9e70e2c2536
 */

public class MergeSortedArrays {

    public void merge(int[] nums1, int m, int[] nums2, int n) {

        if (nums2.length == 0)
            return;

        int i;
        int j;
        int k = j = i = 0;
        int tailIndex;

        while (nums1[i] < nums2[j] && i < m)
            i++;

        for (tailIndex = 1; m - tailIndex >= i; tailIndex++)
            nums1[nums1.length - tailIndex] = nums1[m - tailIndex];

        k = nums1.length - tailIndex + 1;

        while (j < n && k < nums1.length) {

            if (nums2[j] < nums1[k]) {

                nums1[i] = nums2[j];
                j++;
            } else {

                nums1[i] = nums1[k];
                k++;
            }

            i++;
        }

        for (; k < nums1.length; k++, i++)
            nums1[i] = nums1[k];

        for (; j < n; j++, i++)
            nums1[i] = nums2[j];
    }
}