package ca.jrvs.practice.codingChallenges;

public class PrintLettersWithNumbers {

    public static void doIt(String s) {

        for (int i = 0; i < s.length(); i++) {

            char currentChar = s.charAt(i);
            System.out.print(currentChar);

            if (currentChar > 96)
                System.out.print((int) currentChar - 96);
            else
                System.out.print((int) currentChar - 38);
        }
    }

    public static void main(String[] args) {
        doIt(args[0]);
    }
}
