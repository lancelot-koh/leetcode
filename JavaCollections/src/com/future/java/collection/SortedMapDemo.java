package com.future.java.collection;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class SortedMapDemo {
    /**
     * SortedMap Implementation
     * TreeMap
     *
     */

    public static void main(String[] args) {
//        SortedMap sortedMap = new TreeMap();
        Comparator comparator = null;

        SortedMap sortedMap2 = new TreeMap(comparator);


        SortedMap map = new TreeMap();
        map.put("a", "one");
        map.put("b", "two");
        map.put("c", "three");
        map.put("d", "four");
        map.put("e", "five");

        Iterator iterator = map.keySet().iterator();
        System.out.println("===========map iterator============");
        while (iterator.hasNext()) {
            System.out.println(map.get(iterator.next()));
        }
        Iterator iterator1 = map.values().iterator();
        Iterator iterator2 = map.entrySet().iterator();
        System.out.println("===========map  descendingKeySet iterator============");



        Iterator iterator3 = ((TreeMap<?, ?>) map).descendingKeySet().iterator();
        while (iterator3.hasNext()) {
            System.out.println(map.get(iterator3.next()));
        }
        System.out.println("===========map  descendingKeySet descendingIterator============");

        Iterator iterator4 = ((TreeMap<?, ?>) map).descendingKeySet().descendingIterator();
        while (iterator4.hasNext()) {
            System.out.println(map.get(iterator4.next()));
        }


        String firstKey = (String) map.firstKey();
        System.out.println("first key: " + firstKey);

        System.out.println("last key: " + map.lastKey());

        System.out.println("========headmap=========");
        SortedMap headMap = map.headMap("c");
        for (Object c : headMap.keySet()) {
            System.out.println(headMap.get(c));
        }

        System.out.println("========tailmap=========");

        SortedMap tailMap = map.tailMap("d");
        for (Object c : tailMap.keySet()) {
            System.out.println(tailMap.get(c));
        }

        System.out.println("========sub map=========");
        // b =< X < d  => (2,3)
        SortedMap subMap = map.subMap("b","d");
        for (Object c : subMap.keySet()) {
            System.out.println(subMap.get(c));
        }

    }
}
