package ca.jrvs.practice.codingChallenges;

import java.util.HashMap;

/**
 * https://www.notion.so/jarvisdev/Two-Sum-a0e56cef0c1548848f5f66b400ad2155
 * This class contains two solutions to the Two sum problem: a naive solution and a more efficient one.
 * It is assumed that the array is not necessarily sorted, that one element cannot be added to itself
 * and that only one solution is expected.
 */

public class SumOfTwo {

    public static int[] findTwoSumHashMap(int[] values, int target) {

        int[] solution = new int[2];
        HashMap<Integer, Integer> searchMap = new HashMap<>();

        for (int i = 0; i < values.length; i++) {

            int value = values[i];
            int toFind = target - value;

            if (searchMap.containsKey(toFind)) {

                solution[1] = i;
                solution[0] = searchMap.get(toFind);
                return solution;
            }

            searchMap.put(value, i);
        }

        return null;
    }

    public static int[] findTwoSumNaive(int[] values, int target) {

        int[] solution = new int[2];

        for (int i = 0; i < values.length - 1; i++) {

            for (int j = i + 1; j < values.length; j++) {

                if (values[i] + values[j] == target) {

                    solution[0] = i;
                    solution[1] = j;
                    return solution;
                }
            }
        }

        return null;
    }
}
