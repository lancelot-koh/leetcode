package com.future.java.collection;

import java.util.*;

public class GenericCollectionDemo {
    public static void main(String[] args) {
        Collection<String> c1 = new ArrayList<>();
        Collection<String> c2 = new LinkedList<>();
        Collection<String> stringCollection = new HashSet<String>();

        Iterator<String> iterator = stringCollection.iterator();
        while(iterator.hasNext()) {
            String element = iterator.next();
            //do something with element.
        }

        for (String s: stringCollection) {
            System.out.println(s);
        }

    }
}
