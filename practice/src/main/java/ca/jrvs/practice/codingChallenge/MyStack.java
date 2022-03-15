package ca.jrvs.practice.codingChallenge;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyStack {

    private ArrayDeque<Integer> frontQueue;
    private ArrayDeque<Integer> backgroundQueue;

    public MyStack() {

        this.frontQueue = new ArrayDeque<>();
        this.backgroundQueue = new ArrayDeque<>();
    }

    public void push(int x) {

        while(!frontQueue.isEmpty())
            backgroundQueue.add(frontQueue.pop());

        frontQueue.add(x);

        while(!backgroundQueue.isEmpty())
            frontQueue.add(backgroundQueue.pop());
    }

    public int pop() {
        return frontQueue.pop();
    }

    public int top() {
        return frontQueue.peek();
    }

    public boolean empty() {
        return frontQueue.isEmpty();
    }
}
