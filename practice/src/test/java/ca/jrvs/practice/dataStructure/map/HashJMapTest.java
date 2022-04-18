package ca.jrvs.practice.dataStructure.map;

import org.junit.Test;

import static org.junit.Assert.*;

public class HashJMapTest {

    @Test
    public void testPutAndResize() {

        HashJMap hashJMap = new HashJMap(4, 0.5f);

        hashJMap.put(1, 1);
        hashJMap.put(2, 2);
        hashJMap.put(3, 3);
        hashJMap.put(4, 4);
        hashJMap.put(5, 5);
        hashJMap.put(6, 6);

        assertEquals(6, hashJMap.size());
        assertEquals(1, hashJMap.get(1));
        assertEquals(2, hashJMap.get(2));
        assertEquals(3, hashJMap.get(3));
        assertEquals(4, hashJMap.get(4));
        assertEquals(5, hashJMap.get(5));
        assertEquals(6, hashJMap.get(6));
    }

    @Test
    public void testContainsKey() {

        HashJMap hashJMap = new HashJMap(4, 0.5f);

        hashJMap.put(1, 1);
        hashJMap.put(2, 2);
        hashJMap.put(3, 3);
        hashJMap.put(4, 4);
        hashJMap.put(5, 5);
        hashJMap.put(6, 6);

        assertTrue(hashJMap.containsKey(5));
        assertFalse(hashJMap.containsKey(8));
    }

    @Test
    public void testGetValueWithKey() {

        HashJMap hashJMap = new HashJMap(4, 0.5f);

        hashJMap.put(1, 1);
        hashJMap.put(2, 2);
        hashJMap.put(3, 3);
        hashJMap.put(4, 4);
        hashJMap.put(5, 5);
        hashJMap.put(6, 6);

        assertEquals(4, hashJMap.get(4));
        assertEquals(6, hashJMap.get(6));
        assertNotEquals(1, hashJMap.get(3));
        assertTrue(hashJMap.get(8) == null);
    }
}
