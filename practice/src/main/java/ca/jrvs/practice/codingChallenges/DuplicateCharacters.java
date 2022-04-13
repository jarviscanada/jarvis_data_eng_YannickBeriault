package ca.jrvs.practice.codingChallenges;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class DuplicateCharacters {

    public static void doIt(String isThere) {

        HashSet<Character> checkSet = new HashSet<>();
        TreeSet<Character> duplicates = new TreeSet<>();

        for (int i = 0; i < isThere.length(); i++) {

            char currentChar = Character.toLowerCase(isThere.charAt(i));

            if (Character.isAlphabetic(currentChar)) {

                if (!checkSet.add(currentChar))
                    duplicates.add(currentChar);
            }
        }

        System.out.print("[");
        if (!duplicates.isEmpty()) {

            Iterator<Character> iterator = duplicates.iterator();

            System.out.print("\"" + iterator.next() + "\"");
            while (iterator.hasNext())
                System.out.print(", \"" + iterator.next() + "\"");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        doIt("A black cat");
    }
}
