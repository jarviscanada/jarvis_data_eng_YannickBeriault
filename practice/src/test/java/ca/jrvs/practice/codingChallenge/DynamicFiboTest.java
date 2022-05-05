package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DynamicFiboTest {

    @Test
    public void testGetFiboValueForPosition() {

        assertEquals(1, DynamicFibo.getFiboValueForPosition(1));
        assertEquals(2, DynamicFibo.getFiboValueForPosition(3));
        assertEquals(5, DynamicFibo.getFiboValueForPosition(5));
        assertEquals(13, DynamicFibo.getFiboValueForPosition(7));
        assertEquals(55, DynamicFibo.getFiboValueForPosition(10));
        assertEquals(377, DynamicFibo.getFiboValueForPosition(14));
        assertEquals(317811, DynamicFibo.getFiboValueForPosition(28));
    }

    @Test
    public void testGetStairClimbingCombinations() {

        assertEquals(1, DynamicFibo.getStairClimbingCombinations(1));
        assertEquals(2, DynamicFibo.getStairClimbingCombinations(2));
        assertEquals(3, DynamicFibo.getStairClimbingCombinations(3));
        assertEquals(8, DynamicFibo.getStairClimbingCombinations(5));
        assertEquals(34, DynamicFibo.getStairClimbingCombinations(8));
    }
}
