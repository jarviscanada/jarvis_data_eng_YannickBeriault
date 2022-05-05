package ca.jrvs.practice.codingChallenge;

import java.util.ArrayDeque;

public class MyQueue {

    private ArrayDeque<Integer> stackA;
    private ArrayDeque<Integer> stackB;

    public MyQueue() {

        stackA = new ArrayDeque<>();
        stackB = new ArrayDeque<>();
    }

    public void push(int x) {
           stackB.push(x);
    }

    public int pop() {

        if (stackA.isEmpty())
            this.unStack();

        return stackA.pop();
    }

    private void unStack() {

        while (!stackB.isEmpty())
            stackA.push(stackB.pop());
    }

    public int peek() {

        if (stackA.isEmpty())
            this.unStack();

        return stackA.peek();
    }

    public boolean empty() {
        return (stackA.isEmpty() && stackB.isEmpty());
    }
}