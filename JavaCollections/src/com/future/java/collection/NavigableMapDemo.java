package com.future.java.collection;

import java.util.*;

public class NavigableMapDemo {
    /**
     * NavigableMap Implementations
     * TreeMap
     */
    public static void main(String[] args) {
        NavigableMap navigableMap = new TreeMap();
        Comparator comparator = null;

        SortedMap sortedMap = new TreeMap(comparator);

        NavigableSet reverse = navigableMap.descendingKeySet();
        TreeMap map = new TreeMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");
        map.put("e", "5");
        map.put("f", "6");
        map.put("g", "7");
        NavigableSet set = map.descendingKeySet();
        System.out.println("===========descendingKeySet=======");
        for (Object c : set) {
            System.out.println(c);
        }

        System.out.println("===========descendingMap=======");

        Map map1 = map.descendingMap();
        for (Object c : map1.keySet()) {
            System.out.println(map1.get(c));
        }

        NavigableMap original = new TreeMap();
        original.put("1", "1");
        original.put("2", "2");
        original.put("3", "3");

        //this headmap1 will contain "1" and "2"
        SortedMap headmap1 = original.headMap("3");

        //this headmap2 will contain "1", "2", and "3" because "inclusive"=true
        NavigableMap headmap2 = original.headMap("3", true);

        navigableMap.clear();

        navigableMap = new TreeMap();

        navigableMap.put("a", "1");
        navigableMap.put("c", "3");
        navigableMap.put("e", "5");
        navigableMap.put("d", "4");
        navigableMap.put("b", "2");

        SortedMap tailMap = navigableMap.tailMap("c");

        //this submap1 will contain "3", "3"
        SortedMap submap1 = original.subMap("2", "4");

        //this submap2 will contain ("2", "2") ("3", "3") and ("4", "4") because
        //    fromInclusive=true, and toInclusive=true
        NavigableMap submap2 = original.subMap("2", true, "4", true);

        original.clear();
        original = new TreeMap();
        original.put("1", "1");
        original.put("2", "2");
        original.put("3", "3");

        //ceilingKey will be "2".
        Object ceilingKey = original.ceilingKey("2");

        //floorKey will be "2".
        Object floorKey = original.floorKey("2");

        //higherKey will be "3".
        Object higherKey = original.higherKey("2");

        //lowerKey will be "1"
        Object lowerKey = original.lowerKey("2");


        original = new TreeMap();
        navigableMap.put("a", "1");
        navigableMap.put("c", "3");
        navigableMap.put("e", "5");
        navigableMap.put("d", "4");
        navigableMap.put("b", "2");

        //ceilingEntry will be ("c", "3").
        Map.Entry ceilingEntry = navigableMap.ceilingEntry("c");

        //floorEntry will be ("c, "3").
        Map.Entry floorEntry = navigableMap.floorEntry("c");

        //higherEntry will be ("d", "4").
        Map.Entry higherEntry = original.higherEntry("c");

        //lowerEntry will be ("a", "1")
        Map.Entry lowerEntry = original.lowerEntry("b");

        //first is ("a", "1")
        Map.Entry first = original.pollFirstEntry();

        //last is ("e", "5")
        Map.Entry last = original.pollLastEntry();
    }

}
