package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SumOfTwoTest {

    @Test
    public void testFindTwoSumNaive() {

        int[] value = {1, 5, 7, 8, 9, 4};
        int[] expectedSolution = {1, 2};

        assertTrue(Arrays.equals(expectedSolution, SumOfTwo.findTwoSumNaive(value, 13)));
        assertFalse(Arrays.equals(expectedSolution, SumOfTwo.findTwoSumNaive(value, 8)));
    }

    @Test
    public void testFindTwoSumNaiveNull() {

        int[] value = {1, 5, 8, 9, 4};

        assertNull(SumOfTwo.findTwoSumNaive(value, 50));
    }
}
