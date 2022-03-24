package ca.jrvs.practice.codingChallenges;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RecursiveValidParenthesesTest {

    @Test
    public void TestSimple() {

        RecursiveValidParentheses rVP = new RecursiveValidParentheses();

        assertTrue(rVP.isValid("{[]}"));
    }
}
