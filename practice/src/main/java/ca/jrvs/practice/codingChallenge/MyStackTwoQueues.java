package ca.jrvs.practice.codingChallenge;

import java.util.ArrayDeque;

/**
 * https://www.notion.so/jarvisdev/Implement-Stack-using-Queue-6096df31031d4102a825b9f1d05c6150
 */

public class MyStackTwoQueues {

    private ArrayDeque<Integer> frontQueue;
    private ArrayDeque<Integer> backgroundQueue;

    public MyStackTwoQueues() {

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
