package ca.jrvs.practice.codingChallenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DuplicateNumberTest {

    @Test
    public void testFindDuplicate() {

        DuplicateNumber duplicateNumber = new DuplicateNumber();

        assertEquals(3, duplicateNumber.findDuplicate(new int[] {3,1,3,4,2}));
    }
}
