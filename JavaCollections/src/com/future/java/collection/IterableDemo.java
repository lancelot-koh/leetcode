package com.future.java.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

public class IterableDemo {
    /**
     * Iterable Interface Definition
     * public interface Iterable<T> {
     *
     *   Iterator<T>    iterator();
     *
     *   Spliterator<T> spliterator();  - work with stream JDK8
     *
     *   void           forEach(Consumer<? super T> action);
     *
     * }
     *
     */

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
//        for(int i = 0; i < 10000; i++) {
//            list.add("one" + i);
//        }
        list.add("one");
        list.add("two");
        list.add("three");

        // fastest loop -standard loop performance will impact if array item is more than 10000
        long before = System.currentTimeMillis();
        for(int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
        }
        long after = System.currentTimeMillis();
        System.out.println("standard loop Execution time: ====: " + (after - before));

        before = System.currentTimeMillis();
        for(String s: list) {
//            System.out.println(s);
        }
        after = System.currentTimeMillis();
        System.out.println("for each loop Execution time: ====: " + (after - before));

//        Iterator<String> iterator = list.iterator();
//        while(iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
//
//        list.forEach((element) -> {
//                System.out.println(element);
//        });

        MyCollection<String> stringMyCollection = new MyCollection<>();
        for(String s: stringMyCollection) {
            //
        }


        Spliterator<String> spliterator = list.spliterator();

    }


    static class MyCollection<E> implements Iterable<E> {
        public Iterator<E> iterator() {
            return  new MyIterator<E>();
        }
    }

    static class MyIterator<T> implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }

        @Override
        public void remove() {

        }
    }
}
