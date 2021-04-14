package com.future.java.collection;

import java.util.*;

public class CollectionDemo {
    /**
     * Collection Sub Type:
     * List
     * Set
     * SortedSet
     * NavigableSet
     * Queue
     * Deque
     */

    public static void main(String[] args) {
        Collection collection = new ArrayList();
        Collection collection2 = new LinkedList();
        Collection<String> collection3 = new HashSet<String>();

        collection.add("element");
        collection2.add("element");
        collection2.add("element 2");
        collection3.add("element 1");
        collection3.add("element 2");

        collection3.remove("element 1");


        Set<String> set = new HashSet<>();
        set.add("one");
        set.add("two");
        collection3.addAll(set);

        for(String s: collection3) {
            System.out.println(s);
        }

        System.out.println("========remove collection 2=========");
        collection3.removeAll(collection2);
        for(String s: collection3) {
            System.out.println(s);
        }



        Collection ca = new ArrayList();
        Collection cb = new ArrayList();
        ca.add("A");
        ca.add("B");
        ca.add("C");

        cb.add("1");
        cb.add("2");
        cb.add("3");

        Collection t = new HashSet();
        t.addAll(ca);
        t.addAll(cb);
        for(Object s: t) {
            System.out.println(s);
        }

        System.out.println("contains: " + t.contains("1"));

        t.retainAll(ca);
        System.out.println("contains: " + t.contains("1"));

        for(Object s: t) {
            System.out.println(s);
        }
    }
}
