package ca.jrvs.practice.codingChallenge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * https://www.notion.so/jarvisdev/Find-Largest-Smallest-b8d189a2986f47e2bc1cd270fc4c8cdd
 * Since the data structure and data type are not specified for this challenge, I've chosen to work with the most
 * generic structure and type possible.
 */

public class FindMaxAndMin {

    public static <T extends Comparable<T>> ArrayList<T>
    standardOnePassImplementation(Collection<T> collection) {

        Iterator<T> iterator = collection.iterator();
        if (!iterator.hasNext())
            return null;

        T min;
        T max;
        max = min = iterator.next();

        for (T item : collection) {

            if (item.compareTo(min) < 0)
                min = item;
            else if (item.compareTo(max) > 0)
                max = item;
        }

        ArrayList<T> arrayList = new ArrayList<>();
        arrayList.add(min);
        arrayList.add(max);

        return arrayList;
    }
}
