package ca.jrvs.practice.dataStructure.map;

import ca.jrvs.practice.codingChallenge.CompareTwoMaps;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CompareTwoHashJMapsTest {

    final static String MUSICIAN_1 = "The Smashing Pumpkins";
    final static String MUSICIAN_2 = "David Bowie";
    final static String MUSICIAN_3 = "Miles Davis";
    final static String MUSICIAN_4 = "MF DOOM";

    private static HashJMap<Integer, String> populateHashMap(String... musicians) {

        HashJMap<Integer, String> hashJMap = new HashJMap<>();
        int index = 1;

        for (String musician : musicians) {
            hashJMap.put(index++, musician);
        }

        return hashJMap;
    }

    @Test
    public void testCompareMapsTrue() {

        CompareTwoHashJMaps comparator = new CompareTwoHashJMaps();

        HashJMap<Integer, String> m1 = populateHashMap(MUSICIAN_1, MUSICIAN_2, MUSICIAN_3);
        HashJMap<Integer, String> m2 = populateHashMap(MUSICIAN_1, MUSICIAN_2, MUSICIAN_3);

        assertTrue(comparator.compareMaps(m1, m2));
    }

    @Test
    public void testCompareMapsFalse() {

        CompareTwoHashJMaps comparator = new CompareTwoHashJMaps();

        HashJMap<Integer, String> m1 = populateHashMap(MUSICIAN_1, MUSICIAN_2, MUSICIAN_3);
        HashJMap<Integer, String> m2 = populateHashMap(MUSICIAN_1, MUSICIAN_2, MUSICIAN_4);

        assertFalse(comparator.compareMaps(m1, m2));
    }

    @Test
    public void testCompareMapsDifferentSizes() {

        CompareTwoHashJMaps comparator = new CompareTwoHashJMaps();

        HashJMap<Integer, String> m1 = populateHashMap(MUSICIAN_1, MUSICIAN_2);
        HashJMap<Integer, String> m2 = populateHashMap(MUSICIAN_1, MUSICIAN_2, MUSICIAN_4);

        assertFalse(comparator.compareMaps(m1, m2));
    }
}