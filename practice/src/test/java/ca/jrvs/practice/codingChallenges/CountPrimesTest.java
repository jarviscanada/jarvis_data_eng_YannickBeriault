package ca.jrvs.practice.codingChallenges;

import ca.jrvs.practice.codingChallenges.CountPrimes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountPrimesTest {

    @Test
    public void testCountPrimes1() {

        CountPrimes countPrimes = new CountPrimes();

        assertEquals(4, countPrimes.countPrimes(10));
    }

    @Test
    public void testCountPrimes2() {

        CountPrimes countPrimes = new CountPrimes();

        countPrimes.countPrimes(499979);
    }
}
