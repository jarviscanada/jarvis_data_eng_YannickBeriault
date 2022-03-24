package ca.jrvs.practice.codingChallenges;

import java.util.LinkedList;
import java.util.ListIterator;

public class ValidPalindrome {

    private static String workingS;

    public static boolean isPalindrome(String s) {

        if (s.length() <= 1)
            return true;
        else {

            workingS = s;
            return fastIsPalindromeRecursive(0, s.length() - 1);
        }
    }

    private static boolean fastIsPalindromeRecursive(int head, int tail) {

        if (head >= tail)
            return true;

        if (Character.toLowerCase(workingS.charAt(head))
                == Character.toLowerCase(workingS.charAt(tail))) {

            if (tail == 1)
                return true;
            else
                return fastIsPalindromeRecursive( head + 1, tail - 1);
        } else if (!Character.isLetterOrDigit(workingS.charAt(head)))
            return fastIsPalindromeRecursive(head + 1, tail);
        else if (!Character.isLetterOrDigit(workingS.charAt(tail)))
            return fastIsPalindromeRecursive(head, tail - 1);
        else
            return false;
    }

    public static boolean isPalindromeSlow(String s) {

        if (s.length() <= 1)
            return true;

        StringBuilder workString = new StringBuilder(s);

        return isPalindromeRecursive(workString);
    }

    private static boolean isPalindromeRecursive(StringBuilder workString) {

        int tailIndex = workString.length() - 1;
        if (tailIndex == 0)
            return true;

        if (!Character.isLetterOrDigit(workString.charAt(0)))
            return isPalindromeRecursive(workString.deleteCharAt(0));
        if (!Character.isLetterOrDigit(workString.charAt(tailIndex)))
            return isPalindromeRecursive(workString.deleteCharAt(tailIndex));

        if (Character.toLowerCase(workString.charAt(0))
                == Character.toLowerCase(workString.charAt(tailIndex))) {

            if (tailIndex == 1)
                return true;
            else {
                workString = workString.deleteCharAt(tailIndex);
                return isPalindromeRecursive(workString.deleteCharAt(0));
            }
        } else
            return false;
    }

    //By "pointers" I did not think what was meant was really indices. That is why I devised a solution using iterators
    //The idea is similar to what it would be with indices, plus the work done to build the list.
    public static boolean isPalindromeWastefulPointers(String s) {

        boolean itIs = false;
        LinkedList<Character> cleanedS = new LinkedList<>();
        ListIterator<Character> headIterator;
        ListIterator<Character> tailIterator;

        for (int i = 0; i < s.length(); i++) {

            char currentChar = s.charAt(i);
            if (Character.isLetterOrDigit(currentChar))
                cleanedS.add(Character.toLowerCase(currentChar));
        }

        if (cleanedS.size() <= 1)
            return true;

        headIterator = cleanedS.listIterator();
        tailIterator = cleanedS.listIterator(cleanedS.size());
        while (!(headIterator.nextIndex() >= tailIterator.previousIndex())) {

            if (headIterator.next() != tailIterator.previous())
                return false;
            else
                itIs = true;
        }

        return itIs;
    }
}