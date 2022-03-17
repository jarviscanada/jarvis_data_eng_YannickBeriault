package ca.jrvs.practice.codingChallenge;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValidParentheses {

    private static HashMap<Character, Character> bracketMapper = createMapper();

    private static HashMap<Character, Character> createMapper() {

        HashMap<Character, Character> mapper = (HashMap<Character, Character>) Stream.of(new char[][] {
                {'(', '('}, {')', '('},
                {'{', '{'}, {'}', '{'},
                {'[', '['}, {']', '['}
        }).collect(Collectors.toMap(data -> data[0], data -> data [1]));

        return mapper;
    }

    public boolean isValid(String s) {

        ArrayDeque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {

            char currentChar = s.charAt(i);
            char mapped = bracketMapper.get(currentChar);

            if (currentChar == mapped)
                stack.push(currentChar);
            else {

                if (!stack.isEmpty()) {

                    char popped = stack.pop();
                    if (popped != mapped)
                        return false;
                } else
                    return false;
            }
        }

        if (stack.isEmpty())
            return true;
        else
            return false;
    }
}
