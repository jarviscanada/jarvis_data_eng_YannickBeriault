package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.codingChallenges.FindMaxAndMin;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class FindMaxAndMinTest {

    @Test
    public void testStandardOnePassImplementationDoublesLinkedList() {

        Double expectedMin = -89.9867;
        Double expectedMax = 98698.897;

        LinkedList<Double> doublesList = new LinkedList<>();
        doublesList.add(8.98);
        doublesList.add(expectedMin);
        doublesList.add(-78.987);
        doublesList.add(8979.98);
        doublesList.add(expectedMax);

        ArrayList<Double> returnedArray = FindMaxAndMin.standardOnePassImplementation(doublesList);
        assertEquals(expectedMin, returnedArray.get(0));
        assertEquals(expectedMax, returnedArray.get(1));
    }
}