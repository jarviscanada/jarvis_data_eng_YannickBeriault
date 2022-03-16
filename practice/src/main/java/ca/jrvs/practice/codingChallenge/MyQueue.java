package ca.jrvs.practice.codingChallenge;

import java.util.ArrayDeque;

public class MyQueue {

    private ArrayDeque<Integer> frontStack;
    private ArrayDeque<Integer> backStack;

    public MyQueue() {

        frontStack = new ArrayDeque<>();
        backStack = new ArrayDeque<>();
    }

    public void push(int x) {

        while (!frontStack.isEmpty())
            backStack.push(frontStack.pop());

        backStack.push(x);

        while (!backStack.isEmpty())
            frontStack.push(backStack.pop());
    }

    public int pop() {
        return frontStack.pop();
    }

    public int peek() {
        return frontStack.peek();
    }

    public boolean empty() {
        return frontStack.isEmpty();
    }
}
