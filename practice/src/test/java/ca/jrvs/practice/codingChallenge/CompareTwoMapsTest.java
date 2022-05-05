package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CompareTwoMapsTest {

    final static String MUSICIAN_1 = "The Smashing Pumpkins";
    final static String MUSICIAN_2 = "David Bowie";
    final static String MUSICIAN_3 = "Miles Davis";
    final static String MUSICIAN_4 = "MF DOOM";

    private static HashMap<Integer, String> populateHashMap(String... musicians) {

        HashMap<Integer, String> hashMap = new HashMap<>();
        int index = 1;

        for (String musician : musicians) {
            hashMap.put(index++, musician);
        }

        return hashMap;
    }

    @Test
    public void testCompareMapsTrue() {

        CompareTwoMaps comparator = new CompareTwoMaps();

        HashMap<Integer, String> m1 = populateHashMap(MUSICIAN_1, MUSICIAN_2, MUSICIAN_3);
        HashMap<Integer, String> m2 = populateHashMap(MUSICIAN_1, MUSICIAN_2, MUSICIAN_3);

        assertTrue(comparator.compareMaps(m1, m2));
    }

    @Test
    public void testCompareMapsFalse() {

        CompareTwoMaps comparator = new CompareTwoMaps();

        HashMap<Integer, String> m1 = populateHashMap(MUSICIAN_1, MUSICIAN_2, MUSICIAN_3);
        HashMap<Integer, String> m2 = populateHashMap(MUSICIAN_1, MUSICIAN_2, MUSICIAN_4);

        assertFalse(comparator.compareMaps(m1, m2));
    }

    @Test
    public void testCompareMapsDifferentSizes() {

        CompareTwoMaps comparator = new CompareTwoMaps();

        HashMap<Integer, String> m1 = populateHashMap(MUSICIAN_1, MUSICIAN_2);
        HashMap<Integer, String> m2 = populateHashMap(MUSICIAN_1, MUSICIAN_2, MUSICIAN_4);

        assertFalse(comparator.compareMaps(m1, m2));
    }
}