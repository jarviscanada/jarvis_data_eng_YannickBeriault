package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ValidPalindromeTest {

    @Test
    public void testPointerIsPalindrome() {
        assertTrue(ValidPalindrome.isPalindromeWastefulPointers("A man, a plan, a canal: Panama"));
    }

    @Test
    public void testIsPalindromeRecursive() {
        assertTrue(ValidPalindrome.isPalindrome("A man, a plan, a canal: Panama"));
    }
}
