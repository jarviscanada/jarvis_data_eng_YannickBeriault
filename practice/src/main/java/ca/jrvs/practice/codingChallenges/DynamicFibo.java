package ca.jrvs.practice.codingChallenges;

public class DynamicFibo {

    public static int getFiboValueForPosition(int position) {

        int[] fibonacciSequence = new int[position + 1];

        if (position < 0) {

            System.out.println("Position must be a natural number or 0.");
            return -1;
        }

        for (int i = 0; i <= position; i++) {

            if (i <= 1)
                fibonacciSequence[i] = i;
            else {
                fibonacciSequence[i] = fibonacciSequence[i - 1]
                        + fibonacciSequence[i - 2];
            }
        }

        return fibonacciSequence[position];
    }

    public static int getStairClimbingCombinations(int stairs) {

        int[] combinationsSequence = new int[stairs + 1];

        if (stairs < 0) {

            System.out.println("Position must be a natural number or 0.");
            return -1;
        }

        for (int i = 0; i <= stairs; i++) {

            if (i <= 2)
                combinationsSequence[i] = i;
            else {
                combinationsSequence[i] = combinationsSequence[i - 1]
                        + combinationsSequence[i - 2];
            }
        }

        return combinationsSequence[stairs];
    }
}