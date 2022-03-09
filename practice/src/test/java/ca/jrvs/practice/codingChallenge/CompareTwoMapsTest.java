package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class CompareTwoMapsTest {

    @Test
    public void testCompareMaps() {

        CompareTwoMaps comparer = new CompareTwoMaps();

        HashMap<Integer, String> m1 = new HashMap<>();
        HashMap<Integer, String> m2 = new HashMap<>();
        m1.put(1, "The Smashing Pumpkins");
        m2.put(1, "The Smashing Pumpkins");
        m1.put(2, "David Bowie");
        m2.put(2, "David Bowie");
        m1.put(3, "Miles Davis");
        m2.put(3, "Miles Davis");

        assertTrue(comparer.compareMaps(m1, m2));
    }
}