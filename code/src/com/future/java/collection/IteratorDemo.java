package com.future.java.collection;

import java.util.*;

public class IteratorDemo {
    /**
     * Method	Description
     * hasNext()	Returns true if the Iterator has more elements, and false if not.
     * next()	Return the next element from the Iterator
     * remove()	Removes the latest element returned from next() from the Collection the Iterator is iterating over.
     * forEachRemaining()	Iterates over all remaining elements in the Iterator and calls a Java Lambda Expression passing each remaining element as parameter to the lambda expression.
     */

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        list.add("three");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.equals("two")) {
                iterator.remove();
            }
            System.out.println(s);
        }

        for(String s: list) {
            System.out.println(s);
        }

        Set set = new HashSet<>();
        set.add(123);
        set.add(456);
        set.add(789);
        Iterator iterator1 = set.iterator();
        while (iterator1.hasNext()) {
            int s = (int)iterator1.next();
            if (s == 789) {
                iterator1.remove();
            }
            System.out.println(s);
        }

        System.out.println("===after removed======");
        Object [] setToArray = set.toArray();
        for(int i = 0; i < setToArray.length; i++) {
            System.out.println(setToArray[i]);
        }

        List<String> list1 = new ArrayList<>();
        list1.add("abc");
        list1.add("def");
        list1.add("ghi");

        Iterator<String> iterator2 = list1.iterator();
        iterator2.forEachRemaining((element) -> {
            System.out.println(element);
        });

        List<String> list2 = new ArrayList<>();
        list2.add("Jane");
        list2.add("Heidi");
        list2.add("Hannah");

        ListIterator<String> listIterator = list2.listIterator();
        while(listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }

        while (listIterator.hasPrevious()) {
            System.out.println(listIterator.previous());
        }

    }
}
