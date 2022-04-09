package ca.jrvs.practice.codingChallenges;

import java.util.*;

/**
 * https://www.notion.so/jarvisdev/Missing-Number-8fbe5a378ee147089eb3b483ae50a5c4
 */

public class MissingNumber {

    public int doItUsingGaussFormula(int[] nums) {

        long gaussSum = ((long) nums.length * (1 + nums.length)) / 2;
        long sum = 0;

        for (int i : nums)
            sum += i;

        return (int) (gaussSum - sum);
    }

    public int doItWithSet(int[] nums) {

        HashSet<Integer> numsSet = new HashSet<>();
        for (int i : nums)
            numsSet.add(i);

        for (int i = 0; i <= nums.length; i++) {

            if (!numsSet.contains(i))
                return i;
        }

        return -1;
    }

    public int doItWithSums(int[] nums) {

        long nSum = ((long) nums.length * ((long) nums.length + 1)) / 2;
        long sum = 0;

        for (int i : nums)
            sum += i;

        return (int) (nSum - sum);
    }

    //I used this main method for a limits test.
    public static void main(String[] args) {
        long bigSum = ((long) Integer.MAX_VALUE * ((long) Integer.MAX_VALUE + 1));

        System.out.println(bigSum);
    }
}
