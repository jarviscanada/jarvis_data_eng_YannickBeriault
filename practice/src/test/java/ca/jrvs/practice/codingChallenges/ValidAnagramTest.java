package ca.jrvs.practice.codingChallenges;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidAnagramTest {

    @Test
    public void TestIsAnagram() {

        ValidAnagram validAnagram = new ValidAnagram();
        assertFalse(validAnagram.isAnagram("rat", "car"));
    }
}
