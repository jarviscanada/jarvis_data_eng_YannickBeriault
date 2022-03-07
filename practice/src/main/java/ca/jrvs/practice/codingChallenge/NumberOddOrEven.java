package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/jarvisdev/Sample-Check-if-a-number-is-even-or-odd-f8f06cb4f11d483fa0424e0c90992560
 */

public class NumberOddOrEven {

    public static String checkWithBitOperator(int numberToCheck) {

        String answer;
        int maskedValue = numberToCheck&1;

        if (maskedValue == 0) {
            answer = "even";
        } else {
            answer = "odd";
        }

        return answer;
    }

    public static String checkWithModulo(int numberToCheck) {

        String answer;

        if (numberToCheck % 2 == 0) {
            answer = "even";
        } else {
            answer = "odd";
        }

        return answer;
    }

    public static void main(String[] args) {

        int numberToCheck;

        try {
            numberToCheck = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Argument must be an integer!");
            return;
        }

        System.out.println(checkWithModulo(numberToCheck));
        System.out.println(checkWithBitOperator(numberToCheck));
    }
}
