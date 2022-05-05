package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringToIntegerTest {

    @Test
    public void testAtoiWithoutJavaParse() {

        assertEquals(42, StringToInteger.atoiWithoutJavaParse("42"));
    }
}
