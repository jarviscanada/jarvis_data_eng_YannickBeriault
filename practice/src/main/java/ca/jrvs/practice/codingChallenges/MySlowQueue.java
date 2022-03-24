package ca.jrvs.practice.codingChallenges;

import java.util.ArrayDeque;

public class MySlowQueue {

    private ArrayDeque<Integer> frontStack;
    private ArrayDeque<Integer> backStack;

    public MySlowQueue() {

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
