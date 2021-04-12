package com.future.java.collection;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {
        List<String> items = new ArrayList<String>();

        items.add("one");
        items.add("twoo");
        items.add("three");
        items.add("one-hundred");

        Stream<String> stream = items.stream();

        Stream<String> oStream = stream.filter( item -> item.startsWith("o"));
        oStream.forEach(item -> System.out.println(item));

        stream = items.stream();
        oStream = stream.map( item -> item.toUpperCase());
        oStream.forEach(item -> System.out.println(item));

        stream = items.stream();
        Collection c = stream.filter(item -> item.startsWith("o")).collect(Collectors.toList());
        c.forEach(item -> System.out.println(item));

        stream = items.stream();
        String i = stream.min(Comparator.comparing(item -> item.length())).get();
        System.out.println(i);


        stream = items.stream();
        i = stream.max(Comparator.comparing(item -> item.length())).get();
        System.out.println(i);


        stream = items.stream();
        long cnt = stream.filter(item -> item.startsWith("o")).count();
        System.out.println(cnt);


        String reduced2 = items.stream()
                .reduce((acc, item) -> acc + " " + item)
                .get();

        System.out.println(reduced2);

        String reduced = items.stream()
                .reduce("prefix-", (acc, item) -> acc + " " + item);
        System.out.println(reduced);


        String reduced3 = items.stream()
                .filter( item -> item.startsWith("o"))
                .reduce("pre-", (acc, item) -> acc + " " + item);
        System.out.println(reduced3);

    }
}
