package ca.jrvs.practice.dataStructure.list;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;

public class LinkedJList implements JList {

    public ListNode firstNode;
    public ListNode lastNode;
    private int listSize;

    public LinkedJList() {

        this.firstNode = this.lastNode = null;
        this.listSize = 0;
    }

    @Override
    public boolean add(Object o) {

        ListNode newNode = new ListNode(o);

        if (firstNode == null)
            firstNode = lastNode = newNode;
        else {

            lastNode.setNextNode(newNode);
            lastNode = newNode;
        }

        setSize(size() + 1);

        return true;
    }

    @Override
    public Object[] toArray() {

        Object[] arrayFromList = new Object[size()];
        LinkedJListIterator<Object> iterator = new LinkedJListIterator<>();
        int index = 0;

        while (iterator.hasNext()) {

            arrayFromList[index] = iterator.next();
            index++;
        }

        return arrayFromList;
    }

    @Override
    public int size() {
        return listSize;
    }

    private void setSize(int newSize) {
        listSize = newSize;
    }

    @Override
    public boolean isEmpty() {
        return firstNode == null;
    }

    @Override
    public int indexOf(Object o) {

        int index = 0;
        LinkedJListIterator<Object> iterator = new LinkedJListIterator<>();

        while (iterator.hasNext()) {

            if (iterator.next().equals(o))
                return index;
            else
                index++;
        }

        return -1;
    }

    @Override
    public boolean contains(Object o) {

        LinkedJListIterator<Object> iterator = new LinkedJListIterator<>();

        while (iterator.hasNext()) {

            if (iterator.next().equals(o))
                return true;
        }

        return false;
    }

    @Override
    public Object get(int index) {

        if (index < 0)
            throw new IndexOutOfBoundsException();

        Object toReturn = null;
        LinkedJListIterator<Object> iterator = new LinkedJListIterator<>();

        while (index >= 0) {

            if (!iterator.hasNext())
                throw new IndexOutOfBoundsException();

            toReturn = iterator.next();
            index--;
        }

        return toReturn;
    }

    @Override
    public Object remove(int index) {

        ListNode currentNode;
        ListNode previousNode;

        if (index < 0)
            throw new IndexOutOfBoundsException();
        else if (index == 0) {

            Object toReturn = firstNode.getContent();
            firstNode = firstNode.getNextNode();
            setSize(size() - 1);

            return toReturn;
        }

        currentNode = previousNode = firstNode;

        while (index > 0) {

            previousNode = currentNode;
            currentNode = currentNode.getNextNode();
            index--;

            if (currentNode == null)
                throw new IndexOutOfBoundsException();
        }

        previousNode.setNextNode(currentNode.getNextNode());
        setSize(size() - 1);

        return currentNode.getContent();
    }

    /**
     * Removes nodes containing objects that are already present in any node nearer from the beginning of the
     * list. Keeps the first occurence.
     * @return true if at least one node was removed, false otherwise.
     */
    public boolean removeDuplicates() {

        boolean didRemove = false;
        HashSet<Object> hashSet = new HashSet<>();

        ListNode previousNode = firstNode;
        ListNode currentNode = firstNode;

        while (currentNode != null) {

            if (!hashSet.add(currentNode.getContent())) {

                previousNode.setNextNode(currentNode.getNextNode());
                setSize(size() - 1);
                didRemove = true;
            } else {

                previousNode = currentNode;
            }

            currentNode = currentNode.getNextNode();
        }

        return didRemove;
    }

    @Override
    public void clear() {

        firstNode = lastNode = null;
        setSize(0);
    }

    public class ListNode {

        private Object content;
        private ListNode nextNode;

        public ListNode(Object o) {
            this.content = o;
            this.nextNode = null;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object o) {
            this.content = o;
        }

        public ListNode getNextNode() {
            return this.nextNode;
        }

        public void setNextNode(ListNode node) {
            this.nextNode = node;
        }
    }

    public class LinkedJListIterator<E> implements Iterator<E> {

        private ListNode headNode;
        private ListNode currentNode;

        public LinkedJListIterator() {

            currentNode = headNode = new ListNode(null);
            headNode.setNextNode(firstNode);
        }

        public ListNode getCurrentNode() {
            return currentNode;
        }

        public ListNode getNextNode() {
            return currentNode.getNextNode();
        }

        @Override
        public boolean hasNext() {
            return currentNode.getNextNode() != null;
        }

        @Override
        public E next() {

            currentNode = currentNode.getNextNode();
            E toReturn = (E) currentNode.getContent();

            return toReturn;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Iterator.super.forEachRemaining(action);
        }
    }
}
