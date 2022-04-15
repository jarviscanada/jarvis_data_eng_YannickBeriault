package ca.jrvs.practice.codingChallenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DuplicateNumberTest {

    @Test
    public void testFindDuplicate() {

        DuplicateNumber duplicateNumber = new DuplicateNumber();

        assertEquals(9, duplicateNumber.findDuplicate(new int[] {11, 2, 12, 5, 9, 6, 9, 3, 8, 4, 7, 1, 10}));
    }
}
