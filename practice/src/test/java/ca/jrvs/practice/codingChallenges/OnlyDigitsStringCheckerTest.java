package ca.jrvs.practice.codingChallenges;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OnlyDigitsStringCheckerTest {

    @Test
    public void testAsciiChecker() {

        assertTrue(OnlyDigitsStringChecker.asciiChecker("41894968498"));
        assertFalse(OnlyDigitsStringChecker.asciiChecker("967489984:"));
    }

    @Test
    public void testParserChecker() {

        assertTrue(OnlyDigitsStringChecker.parserChecker("41894968498"));
        assertFalse(OnlyDigitsStringChecker.parserChecker("967489984:"));
        assertTrue(OnlyDigitsStringChecker.parserChecker("49684968984986968496848912347896541238546"));
    }

    @Test
    public void testRegexChecker() {

        assertTrue(OnlyDigitsStringChecker.regexChecker("41894968498"));
        assertFalse(OnlyDigitsStringChecker.regexChecker("967489984:"));
        assertTrue(OnlyDigitsStringChecker.regexChecker("49684968984986968496848912347896541238546"));
    }
}