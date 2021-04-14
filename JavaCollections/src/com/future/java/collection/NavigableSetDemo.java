package com.future.java.collection;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class NavigableSetDemo {
    /**
     * NavigableSet Implementations
     * TreeSet
     */

    public static void main(String[] args) {
        NavigableSet navigableSet = new TreeSet();
        navigableSet.add("a");
        navigableSet.add("b");
        navigableSet.add("c");
        navigableSet.add("e");
        navigableSet.add("f");
        navigableSet.add("g");
        navigableSet.add("d");

        for(Object s: navigableSet) {
            System.out.println(s);
        }

        System.out.println("======reverse==========");
        NavigableSet reverse = navigableSet.descendingSet();
        for(Object s: reverse) {
            System.out.println(s);
        }

        Iterator reverseIterator = navigableSet.descendingIterator();

        SortedSet headSet = navigableSet.headSet("d");
        SortedSet tailSet = navigableSet.tailSet("g");
        SortedSet subSet = navigableSet.subSet("c","g");


        NavigableSet<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(3);
        set.add(6);
        set.add(7);
        set.add(9);
        set.add(50);
        set.add(100);

        // ceiling >=
        System.out.println("ceiling: 2:" + set.ceiling(2));
        System.out.println("ceiling: 6:" + set.ceiling(6));
        System.out.println("ceiling: 21:" + set.ceiling(21));
        System.out.println("ceiling: 55:" + set.ceiling(55));


        // floor <=
        System.out.println("floor: 2:" + set.floor(2));
        System.out.println("floor: 6:" + set.floor(6));
        System.out.println("floor: 21:" + set.floor(21));
        System.out.println("floor: 55:" + set.floor(55));
        System.out.println("floor: 999:" + set.floor(999));


        // higher
        System.out.println("higher: 2:" + set.higher(2));
        System.out.println("higher: 6:" + set.higher(6));
        System.out.println("higher: 21:" + set.higher(21));
        System.out.println("higher: 55:" + set.higher(55));
        System.out.println("higher: 999:" + set.higher(999));

        // lower
        System.out.println("lower: 2:" + set.lower(2));
        System.out.println("lower: 6:" + set.lower(6));
        System.out.println("lower: 21:" + set.lower(21));
        System.out.println("lower: 55:" + set.lower(55));
        System.out.println("lower: 999:" + set.lower(999));


        //pollFirst

        System.out.println("pollFirst: " + set.pollFirst());
        System.out.println("pollLast: " + set.pollLast());
    }
}
