package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.ListIterator;

public class ValidPalindrome {

    public static boolean isPalindrome(String s) {

        boolean itIs = false;
        LinkedList<Character> cleanedS = new LinkedList<>();
        ListIterator<Character> headIterator;
        ListIterator<Character> tailIterator;

        for (int i = 0; i < s.length(); i++) {

            char currentChar = s.charAt(i);
            if (Character.isAlphabetic(currentChar) || Character.isDigit(currentChar))
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