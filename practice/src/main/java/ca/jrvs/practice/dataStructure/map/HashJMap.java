package ca.jrvs.practice.dataStructure.map;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class HashJMap<K, V> implements JMap<K, V> {

    /**
     * The default initial capacity - MUST be a power of two.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    /**
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * The load factor for the hash table.
     *
     * @serial
     */
    final float loadFactor;

    /**
     * The table, initialized on first use, and resized as
     * necessary. When allocated, length is always a power of two.
     * (We also tolerate length zero in some operations to allow
     * bootstrapping mechanics that are currently not needed.)
     */
    Node<K, V>[] table;

    /**
     * Holds cached entrySet(). Note that AbstractMap fields are used
     * for keySet() and values().
     */
    Set<Map.Entry<K, V>> entrySet;

    /**
     * The number of key-value mappings contained in this map.
     */
    int size;

    /**
     * The next size value at which to resize (capacity * load factor).
     * Use #capacity() to compute capacity
     *
     * @serial
     */
    // (The javadoc description is true upon serialization.
    // Additionally, if the table array has not been allocated, this
    // field holds the initial array capacity, or zero signifying
    // DEFAULT_INITIAL_CAPACITY.)
    int threshold;

    public HashJMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and load factor.
     *
     * @param  initialCapacity the initial capacity
     * @param  loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive
     */
    public HashJMap(int initialCapacity, float loadFactor) {

        if (initialCapacity < 0)
            throw new IllegalArgumentException("Initial capacity must be a positive integer.");
        if (loadFactor <= 0.0)
            throw new IllegalArgumentException("Load factor must be positive and non-zero.");

        this.loadFactor = loadFactor;
        //threshold = capacity *
        this.threshold = (int) ((float) initialCapacity * loadFactor);
    }

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).  If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A map
     * <tt>m</tt> is said to contain a mapping for a key <tt>k</tt> if and only
     * if {@link #containsKey(Object) m.containsKey(k)} would return
     * <tt>true</tt>.)
     *
     * Key cannot be null
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *         (A <tt>null</tt> return can also indicate that the map
     *         previously associated <tt>null</tt> with <tt>key</tt>,
     *         if the implementation supports <tt>null</tt> values.)
     * @throws NullPointerException if the specified key or value is null
     *         and this map does not permit null keys or values
     */
    @Override
    public V put(K key, V value) {

        if (key == null)
            throw new NullPointerException("Key cannot be null.");

        if (this.table == null) {

            this.table = (Node<K, V>[]) new Node[this.capacity()];
            this.size = 0;
        }

        int index = getHashedIndex(key);
        Node<K, V> newNode = new HashJMap.Node(key.hashCode(), key, value, null);
        Node<K, V> inspectedNode = table[index];
        Node<K, V> previousNode;

        if (inspectedNode == null) {
            table[index] = newNode;
        } else {

            do {

                if (inspectedNode.getKey() == key)
                    return inspectedNode.setValue(value);

                previousNode = inspectedNode;
                inspectedNode = inspectedNode.next;
            } while (inspectedNode != null);

            previousNode.next = newNode;
        }
        this.size++;

        if (this.size > threshold)
            this.resize();

        return null;
    }

    final int capacity() {
        return (table != null) ? table.length :
                (threshold > 0) ? threshold :
                        DEFAULT_INITIAL_CAPACITY;
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.  More formally, returns <tt>true</tt> if and only if
     * this map contains a mapping for a key <tt>k</tt> such that
     * <tt>(key==null ? k==null : key.equals(k))</tt>.  (There can be
     * at most one such mapping.)
     *
     * @param key key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     *         key
     * @throws NullPointerException if the specified key is null and this map
     *         does not permit null keys
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean containsKey(Object key) {

        if (key == null)
            throw new NullPointerException("Key cannot be null.");

        int index = getHashedIndex((K) key);

        return (iterateOverBucket((K) key, index) != null);
    }


    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     *         {@code null} if this map contains no mapping for the key
     * @throws NullPointerException if the specified key is null and this map
     *         does not permit null keys
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public V get(Object key) {

        int index = getHashedIndex((K) key);
        return iterateOverBucket((K) key, index);
    }

    private V iterateOverBucket(K key, int index) {

        Node<K, V> currentNode = table[index];
        while (currentNode != null) {

            if (!currentNode.getKey().equals(key))
                currentNode = currentNode.next;
            else
                return currentNode.getValue();
        }

        return null;
    }

    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of key-value mappings in this map
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.
     *
     * @return a set view of the mappings contained in this map
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    private int getHashedIndex(K key) {
        return key.hashCode() % (table.length - 1);
    }

    private void resize() {

        HashJMap<K, V> tempHashMap = new HashJMap<>(table.length << 1, 1.0f);

        for (Map.Entry<K, V> node : this.entrySet())
            tempHashMap.put(node.getKey(), node.getValue());

        table = tempHashMap.table;
        threshold = (int) ((float)table.length * loadFactor);
    }

    final class EntrySet extends AbstractSet<Map.Entry<K,V>> {

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            return super.spliterator();
        }

       /* @Override
        public <T> T[] toArray(IntFunction<T[]> generator) {
            return super.toArray(generator);
        }*/

        @Override
        public boolean removeIf(Predicate<? super Map.Entry<K, V>> filter) {
            return super.removeIf(filter);
        }

        @Override
        public Stream<Map.Entry<K, V>> stream() {
            return super.stream();
        }

        @Override
        public Stream<Map.Entry<K, V>> parallelStream() {
            return super.parallelStream();
        }

        @Override
        public void forEach(Consumer<? super Map.Entry<K, V>> action) {
            super.forEach(action);
        }
    }

    final class EntryIterator implements Iterator<Map.Entry<K,V>> {

        Node<K, V> currentNode;
        Node<K, V> nextNode;
        int index;

        public EntryIterator() {

            currentNode = nextNode = null;
            index = 0;

            nextNode = getNextHeadNode();
        }

        private Node<K, V> getNextHeadNode() {

            Node<K, V> returnNode = null;

            if (table != null) {

                while (table[index] == null) {

                    if (index < table.length - 1)
                        index++;
                    else
                        break;
                }

                returnNode = table[index];
            }

            return returnNode;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public Node<K, V> next() {

            currentNode = nextNode;
            if (nextNode.next != null)
                nextNode = nextNode.next;
            else {
                index++;
                nextNode = getNextHeadNode();
            }

            return currentNode;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }

        @Override
        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> action) {
            Iterator.super.forEachRemaining(action);
        }
    }

    /**
     * Basic hash bin node, used for most entries.  (See below for
     * TreeNode subclass, and in LinkedHashMap for its Entry subclass.)
     */
    static class Node<K, V> implements Map.Entry<K, V> {

        final int hash;
        final K key;
        V value;
        HashJMap.Node<K, V> next;

        Node(int hash, K key, V value, HashJMap.Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue())) {
                    return true;
                }
            }
            return false;
        }
    }
}