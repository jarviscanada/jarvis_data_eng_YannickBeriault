package ca.jrvs.practice.codingChallenges;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RotateStringTest {

    @Test
    public void testRotateString() {

        RotateString rS = new RotateString();

        assertTrue(rS.rotateString("vcuszhlbtpmksjleuchmjffufrwpiddgyynfujnqblngzoogzg",
                "fufrwpiddgyynfujnqblngzoogzgvcuszhlbtpmksjleuchmjf"));
    }
}
