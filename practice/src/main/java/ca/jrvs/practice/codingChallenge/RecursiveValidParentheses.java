package ca.jrvs.practice.codingChallenge;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://www.notion.so/jarvisdev/YannickB-Coding-Challenges-4-questions-per-week-fb89f886ffc44c7cbd856042e895e874
 */

public class RecursiveValidParentheses {

    private static HashMap<Character, Character> bracketMapper = createMapper();
    private static ArrayDeque<Character> stackedString;

    private static HashMap<Character, Character> createMapper() {

        HashMap<Character, Character> mapper = (HashMap<Character, Character>) Stream.of(new char[][] {
                {'(', ')'}, {'{', '}'}, {'[', ']'}
        }).collect(Collectors.toMap(data -> data[0], data -> data [1]));

        return mapper;
    }

    public boolean isValid(String s) {

        boolean valid = true;

        if (s.length() == 0)
            return valid;

        stackedString = new ArrayDeque<>();
        for (int i = s.length() - 1; i >= 0; i--)
            stackedString.push(s.charAt(i));

        while (!stackedString.isEmpty() && valid) {

            if (bracketMapper.containsKey(stackedString.peek())) {
                valid = closer(bracketMapper.get(stackedString.pop()));
            } else
                valid = false;
        }

        return valid;
    }

    private static boolean closer(char c) {

        if (stackedString.isEmpty())
            return false;

        char nextC = stackedString.pop();

        if (nextC == c)
            return true;
        else if (bracketMapper.containsKey(nextC))
            return (closer(bracketMapper.get(nextC)) && closer(c));
        else
            return false;
    }
}
