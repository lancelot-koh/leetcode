package com.future.java.collection;

import java.util.*;

public class CollectionMethodDemo {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        Collections.addAll(list, "one","two", "three", "four", "five","six");
        System.out.println("======addAll========");
        for(String s: list) {
            System.out.println(s);
        }

        System.out.println("======before default sort========");

        Collections.sort(list);
        System.out.println("Binary Search:" + Collections.binarySearch(list, "four"));

        System.out.println("======after default sort========");

        for(String s: list) {
            System.out.println(s);
        }
        System.out.println("======customized sort========");

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return -o1.compareTo(o2);
            }
        });
        for(String s: list) {
            System.out.println(s);
        }
        System.out.println("Binary Search descending order :" + Collections.binarySearch(list, "four"));

        Collections.reverse(list);
        System.out.println("Binary Search ascending order : :" + Collections.binarySearch(list, "four"));


        // shuffle
        Collections.shuffle(list);

        // copy
        List<String> dest = new ArrayList<String>(Arrays.asList(new String[list.size()]));
        Collections.copy(dest, list);
        System.out.println("======after copy========");
        for(String s: dest) {
            System.out.println(s);
        }

        // min and max
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        integerList.add(5);
        integerList.add(3);

        System.out.println("min:" + Collections.min(integerList));
        System.out.println("max:" + Collections.max(integerList));

        Collections.replaceAll(integerList, 3, 33);
        System.out.println("min:" + Collections.min(integerList));
        System.out.println("max:" + Collections.max(integerList));
    }
}
