package com.future.java.collection;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetDemo {
    /**
     * SortedSet Implementation
     * TreeSet
     */

    public static void main(String[] args) {
        SortedSet set1 = new TreeSet();

        Comparator comparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        };

        SortedSet set2 = new TreeSet(comparator);

        TreeSet set = new TreeSet();
        set.add("one");
        set.add("two");
        set.add("three");
        set.add("four");
        set.add("five");

        Iterator iteratorAsc = set.iterator();
        while (iteratorAsc.hasNext()) {
            System.out.println(iteratorAsc.next());
        }
        Iterator iteratorDes = set.descendingIterator();
        while (iteratorDes.hasNext()){
            System.out.println(iteratorDes.next());
        }

        Comparator comparator1 = set.comparator();

        set.remove("five");

        // ascending order-> four, one, three, two
        Object first = set.first();
        Object last = set.last();
        System.out.println("first element: " + first);
        System.out.println("last element: " + last);


        SortedSet sortedSet = new TreeSet();
        sortedSet.add("a");
        sortedSet.add("b");
        sortedSet.add("c");
        sortedSet.add("d");
        sortedSet.add("e");
        sortedSet.add("f");
        sortedSet.add("g");
        sortedSet.add("h");
        sortedSet.add("i");

        System.out.println("========headSet=======");
        SortedSet headSet = sortedSet.headSet("e");
        // < e elements
        for (Object c: headSet
             ) {
            System.out.println(c.toString());
        }

        System.out.println("========tailSet=======");
        // >= f elements
        SortedSet tailSet = sortedSet.tailSet("f");
        for (Object c: tailSet
        ) {
            System.out.println(c.toString());
        }

        System.out.println("========subSet=======");
        // c =< X < g
        SortedSet subSet = sortedSet.subSet("c","g");
        for (Object c: subSet
        ) {
            System.out.println(c.toString());
        }


        SortedSet<String> gSet = new TreeSet<>();
        gSet.add("one");
        for(String s: gSet) {
            System.out.println(s);
        }
    }
}
