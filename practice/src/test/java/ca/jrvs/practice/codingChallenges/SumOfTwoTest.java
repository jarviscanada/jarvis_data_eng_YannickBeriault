package ca.jrvs.practice.codingChallenges;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SumOfTwoTest {

    @Test
    public void testFindTwoSumNaive() {

        int[] value = {1, 5, 7, 8, 9, 4};
        int[] expectedSolution = {1, 3};

        assertArrayEquals(expectedSolution, SumOfTwo.findTwoSumNaive(value, 13));
        assertFalse(Arrays.equals(expectedSolution, SumOfTwo.findTwoSumNaive(value, 8)));
    }

    @Test
    public void testFindTwoSumNaiveNull() {

        int[] value = {1, 5, 8, 9, 4};

        assertNull(SumOfTwo.findTwoSumNaive(value, 50));
    }

    @Test
    public void testFindTwoSumHashMap() {

        int[] value = {1, 5, 7, 8, 9, 4};
        int[] valueBis = {3, 2, 4};
        int[] expectedSolution = {1, 3};
        int[] expectedBis = {1, 2};

        assertArrayEquals(expectedSolution, SumOfTwo.findTwoSumHashMap(value, 13));
        assertArrayEquals(expectedBis, SumOfTwo.findTwoSumHashMap(valueBis, 6));
        assertFalse(Arrays.equals(expectedSolution, SumOfTwo.findTwoSumHashMap(value, 8)));
    }

    @Test
    public void testFindTwoSumHashMapNull() {

        int[] value = {1, 5, 8, 9, 4};

        assertNull(SumOfTwo.findTwoSumHashMap(value, 50));
    }
}
