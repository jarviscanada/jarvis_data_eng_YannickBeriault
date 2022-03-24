package ca.jrvs.practice.codingChallenges;

public class RecursiveFibo {

    public static int getFiboValueForPosition(int position) {

        if (position == 0 || position == 1)
            return position;
        else {
            return getFiboValueForPosition(position - 1)
                    + getFiboValueForPosition(position - 2);
        }
    }

    public static int getStairClimbingCombinations(int stairs) {

        if (stairs == 0 || stairs == 1 || stairs == 2)
            return stairs;
        else {
            return getStairClimbingCombinations(stairs - 1)
                    + getStairClimbingCombinations(stairs - 2);
        }
    }
}
