package ca.jrvs.practice.codingChallenge;

import java.util.ArrayDeque;

/**
 * https://www.notion.so/jarvisdev/Implement-Stack-using-Queue-6096df31031d4102a825b9f1d05c6150
 */

public class MyStack {

    private ArrayDeque<Integer> clunkyStack;

    public MyStack() {
        this.clunkyStack = new ArrayDeque<>();
    }

    public void push(int x) {

        int size = clunkyStack.size();

        clunkyStack.add(x);

        while (size > 0) {

            clunkyStack.add(clunkyStack.pop());
            size--;
        }
    }

    public int pop() {
        return clunkyStack.pop();
    }

    public int top() {
        return clunkyStack.peek();
    }

    public boolean empty() {
        return clunkyStack.isEmpty();
    }
}
