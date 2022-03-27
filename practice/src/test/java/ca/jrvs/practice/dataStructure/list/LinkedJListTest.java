package ca.jrvs.practice.dataStructure.list;

import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedJListTest {

    @Test
    public void testSizeAndAdd() {

        LinkedJList linkedJList = new LinkedJList();
        assertEquals(linkedJList.size(), 0);

        linkedJList.add(8);
        assertEquals(linkedJList.size(), 1);
    }

    @Test
    public void testAddAndGet() {

        LinkedJList linkedJList = new LinkedJList();
        linkedJList.add(8);
        assertEquals(linkedJList.get(0), 8);
    }

    @Test
    public void testToArray() {

        Integer[] expected = {7, 6, 9};
        LinkedJList linkedJList = new LinkedJList();

        linkedJList.add(7);
        linkedJList.add(6);
        linkedJList.add(9);

        assertArrayEquals(linkedJList.toArray(), expected);
    }

    @Test
    public void testRemoveAndIsEmpty() {

        LinkedJList linkedJList = new LinkedJList();
        assertTrue(linkedJList.isEmpty());

        linkedJList.add(9);
        assertFalse(linkedJList.isEmpty());

        linkedJList.remove(0);
        assertTrue(linkedJList.isEmpty());
    }

    @Test
    public void testIndexOf() {

        LinkedJList linkedJList = new LinkedJList();

        linkedJList.add(7);
        linkedJList.add(6);
        linkedJList.add(9);

        assertEquals(linkedJList.indexOf(6), 1);
        assertEquals(linkedJList.indexOf(1), -1);
    }

    @Test
    public void testContains() {

        LinkedJList linkedJList = new LinkedJList();

        linkedJList.add(7);
        linkedJList.add(6);
        linkedJList.add(9);

        assertTrue(linkedJList.contains(9));
        assertFalse(linkedJList.contains(3));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexOut() {

        LinkedJList linkedJList = new LinkedJList();
        linkedJList.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveIndexOut() {

        LinkedJList linkedJList = new LinkedJList();
        linkedJList.add(8);
        linkedJList.add(8);

        linkedJList.remove(2);
    }

    @Test
    public void testClear() {

        LinkedJList linkedJList = new LinkedJList();
        linkedJList.add(8);
        linkedJList.add(2);
        linkedJList.add(3);

        linkedJList.clear();
        assertTrue(linkedJList.isEmpty());
    }

    @Test
    public void testRemoveDuplicates() {

        LinkedJList linkedJList = new LinkedJList();
        assertFalse(linkedJList.removeDuplicates());

        linkedJList.add(8);
        linkedJList.add(8);
        linkedJList.add(7);
        linkedJList.add(8);
        linkedJList.add(3);
        linkedJList.add(7);
        linkedJList.add(5);
        linkedJList.add(8);

        assertTrue(linkedJList.removeDuplicates());
        assertEquals(linkedJList.size(), 4);
        assertEquals(linkedJList.indexOf(5), 3);
    }
}
