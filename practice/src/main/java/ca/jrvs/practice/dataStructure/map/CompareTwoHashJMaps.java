package ca.jrvs.practice.dataStructure.map;

import java.util.Map;

/**
 *https://www.notion.so/jarvisdev/How-to-compare-two-maps-8433b25bd53e4d97b7a6c3291d84ff00
 */
public class CompareTwoHashJMaps {

    //A first implementation was made comparing the results returned by two iterators, but I then realized it would
    //not be satisfying with big maps, because their order is not guaranteed.
    public <K,V> boolean compareMaps(HashJMap<K,V> m1, HashJMap<K,V> m2) {

        if (m1 == m2) {
            return true;
        }
        if (m1.size() != m2.size()) {
            return false;
        }

        for (Map.Entry node : m1.entrySet()) {

            K key1 = (K) node.getKey();
            V value1 = (V) node.getValue();

            if (!m2.containsKey(key1)) {
                return false;
            }

            V value2 = m2.get(key1);

            if (value1 != value2) {
                return false;
            }
        }

        return true;
    }
}