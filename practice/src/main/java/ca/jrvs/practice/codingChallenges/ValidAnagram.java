package ca.jrvs.practice.codingChallenges;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ValidAnagram {

    private static String initial;
    private static String anagrammed;
    private Character[] puzzle;

    public boolean isAnagram(String s, String t) {

        if (s.length() != t.length())
            return false;

        HashMap<Character, Integer> sMapper = new HashMap<>();
        HashMap<Character, Integer> tMapper = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {

            char sChar = s.charAt(i);
            sMapper.put(sChar, sMapper.getOrDefault(sChar, 0) + 1);

            char tChar = t.charAt(i);
            tMapper.put(tChar, tMapper.getOrDefault(tChar, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : sMapper.entrySet()) {

            if (!tMapper.getOrDefault(entry.getKey(), -1).equals(entry.getValue()))
                return false;
        }

        return true;
    }

    public boolean isAnagramFirstTry(String s, String t) {

        int length = s.length();

        if (length != t.length())
            return false;

        initial = s;
        anagrammed = t;
        puzzle = new Character[length];

        for (int i = 0; i < length; i++) {

            if (!seeker(i, 0))
                return false;
        }

        return true;
    }

    private boolean seeker(int i, int index) {

        Character currentChar = initial.charAt(i);

        index = anagrammed.indexOf(currentChar, index);
        if (index > -1) {

            if (puzzle[index] != null)
                return seeker(i, index + 1);
            else {

                puzzle[index] = currentChar;
                return true;
            }
        } else
            return false;
    }
}
