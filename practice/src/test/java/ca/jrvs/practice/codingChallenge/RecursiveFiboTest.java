package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RecursiveFiboTest {

    @Test
    public void testGetFiboValueForPosition() {

        assertEquals(1, RecursiveFibo.getFiboValueForPosition(1));
        assertEquals(2, RecursiveFibo.getFiboValueForPosition(3));
        assertEquals(5, RecursiveFibo.getFiboValueForPosition(5));
        assertEquals(13, RecursiveFibo.getFiboValueForPosition(7));
        assertEquals(55, RecursiveFibo.getFiboValueForPosition(10));
        assertEquals(377, RecursiveFibo.getFiboValueForPosition(14));
        assertEquals(317811, RecursiveFibo.getFiboValueForPosition(28));
    }

    @Test
    public void testGetStairClimbingCombinations() {

        assertEquals(1, RecursiveFibo.getStairClimbingCombinations(1));
        assertEquals(2, RecursiveFibo.getStairClimbingCombinations(2));
        assertEquals(3, RecursiveFibo.getStairClimbingCombinations(3));
        assertEquals(8, RecursiveFibo.getStairClimbingCombinations(5));
        assertEquals(34, RecursiveFibo.getStairClimbingCombinations(8));
    }
}
