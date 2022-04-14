package ca.jrvs.practice.codingChallenges;

/**
 * https://www.notion.so/jarvisdev/Find-the-Duplicate-Number-6c353b7b8dd145c5986edcf0be6f0a54
 * I decided to skip the approaches suggested and solve the problem with the constraint specified in the
 * online judge: use only constant extra space.
 */

public class DuplicateNumber {

    public int findDuplicate(int[] nums) {

        int lowLimit = 0;
        int highLimit = nums.length - 1;
        int candidate = highLimit / 2;

        while (lowLimit != highLimit) {

            if (asMoreNumsLowerOrEqualThanValue(nums, candidate)) {


            }
        }
    }

    private static boolean asMoreNumsLowerOrEqualThanValue(int[] nums, int candidate) {

        int howMany = 0;

        for (int y : nums) {

            if (y <= candidate)
                howMany++;
        }

        if (howMany > candidate)
            return true;
        else
            return false;
    }
}
