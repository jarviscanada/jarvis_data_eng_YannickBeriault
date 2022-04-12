package ca.jrvs.practice.codingChallenges;

/**
 * https://www.notion.so/jarvisdev/Swap-two-numbers-347e2af862134a56af55d0f6cc218812
 */

public class SwapTwoNumbers {

    public static void doIt(int[] iJ) {

        iJ[0] ^= iJ[1];
        iJ[1] ^= iJ[0];
        iJ[0] ^= iJ[1];

        System.out.println("[" + iJ[0] + "," + iJ[1] + "]");
    }

    public static void doItBadly(int[] iJ) {

        iJ[0] += iJ[1];
        iJ[1] = iJ[0] - iJ[1];
        iJ[0] = iJ[0] - iJ[1];

        System.out.println("[" + iJ[0] + "," + iJ[1] + "]");
    }

    public static void main(String[] args) {

        doIt(new int[] {Integer.valueOf(args[0]), Integer.valueOf(args[1])});
        doItBadly(new int[] {Integer.valueOf(args[0]), Integer.valueOf(args[1])});
    }
}