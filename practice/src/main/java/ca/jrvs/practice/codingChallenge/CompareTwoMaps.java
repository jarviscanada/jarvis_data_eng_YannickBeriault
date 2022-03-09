package ca.jrvs.practice.codingChallenge;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CompareTwoMaps {

    public <K,V> boolean compareMaps(Map<K,V> m1, Map<K,V> m2) {

        if (m1 == m2) {
            return true;
        }
        if (m1.size() != m2.size()) {
            return false;
        }

        Iterator<Map.Entry<K, V>> m1Iterator = m1.entrySet().iterator();
        Iterator<Map.Entry<K, V>> m2Iterator = m2.entrySet().iterator();

        while (m1Iterator.hasNext()) {

            Map.Entry<K, V> m1Entry = m1Iterator.next();
            Map.Entry<K, V> m2Entry = m2Iterator.next();
            if (!m1Entry.getKey().equals(m2Entry.getKey())
                    || !m1Entry.getValue().equals(m2Entry.getValue())) {
                return false;
            }
        }

        return true;
    }
}