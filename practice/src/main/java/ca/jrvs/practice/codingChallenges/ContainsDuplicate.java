package ca.jrvs.practice.codingChallenges;

import java.util.HashSet;

/**
 * https://www.notion.so/jarvisdev/Contains-Duplicate-5b0288ac798e4c8e9b54f92dc55fecc8
 */

public class ContainsDuplicate {

    public boolean containsDuplicate(int[] nums) {

        HashSet<Integer> singles = new HashSet<>();

        for (int i : nums) {

            if (!singles.add(i))
                return true;
        }

        return false;
    }
}
