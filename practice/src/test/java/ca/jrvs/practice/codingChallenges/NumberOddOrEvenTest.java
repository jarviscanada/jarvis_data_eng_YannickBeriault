package ca.jrvs.practice.codingChallenges;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberOddOrEvenTest {

    @Test
    public void TestCheckWithModuloEven() {
        String answer;
        String expected = "even";

        answer = NumberOddOrEven.checkWithModulo(8);

        assertEquals(answer, expected);
    }

    @Test
    public void TestCheckWithModuloOdd() {
        String answer;
        String expected = "odd";

        answer = NumberOddOrEven.checkWithModulo(15);

        assertEquals(answer, expected);
    }

    @Test
    public void TestCheckWithBitOperatorEven() {
        String answer;
        String expected = "even";

        answer = NumberOddOrEven.checkWithBitOperator(8);

        assertEquals(answer, expected);
    }

    @Test
    public void TestCheckWithBitOperatorOdd() {
        String answer;
        String expected = "odd";

        answer = NumberOddOrEven.checkWithBitOperator(15);

        assertEquals(answer, expected);
    }
}
