package com.future.java.collection;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class DequeDemo {
    /**
     * Deque Implementations
     * java.util.LinkedList
     * java.util.ArrayDeque
     *
     * Deque recommend method:
     * add element: offer/offerFirst/OfferLast (return false if failed)
     * remove element: poll/pollFirst/pollLast (return false if failed)
     * check element: peek/peekFirst/peekLast (return null if empty)
     *
     */

    public static void main(String[] args) {
        Deque qA = new LinkedList();
        Deque qB = new ArrayDeque();

        Deque<String> deque = new LinkedList<>();

        // add element
        /**
         * add(): failed will throw exception
         * addLast()
         * addFirst()
         * offer(): failed will return false
         * offerFirst()
         * offerLast()
         */
        deque.add("one");
        deque.addLast("two");
        deque.add("three");
        deque.addFirst("zero");

        for(String s: deque){
            System.out.println(s);
        }

        deque.offer("a");
        deque.offerLast("b");
        deque.offerLast("c");
        deque.offerFirst("O");
        for(String s: deque){
            System.out.println(s);
        }

        // push throw exception if queue is full.
        deque.push("first");
        System.out.println("===========Total elements===============");

        for(String s: deque){
            System.out.println(s);
        }

        System.out.println("==========================");

        /**
         * Peek Element
         * peek(): return null if empty
         * peekFirst()
         * peekLast()
         * getFirst(): throw exception if empty
         * getLast()
         */

        System.out.println("peek: "+deque.peek());
        System.out.println("peekFirst: "+deque.peekFirst());
        System.out.println("peekLast: "+deque.peekLast());


        System.out.println("getFirst: "+deque.getFirst());
        System.out.println("getLast: "+deque.getLast());

        System.out.println("===========remove action===============");

        /**
         * Remove Element From Deque
         * remove() throw exception if empty
         * removeFirst()
         * removeLast()
         * poll()   return null if empty
         * pollFirst()
         * pollLast()
         */

        String el = deque.remove();
        System.out.println("Remove: " + el);

        el = deque.removeFirst();
        System.out.println("Remove first: " + el);

        el = deque.removeLast();
        System.out.println("Remove last: " + el);
        System.out.println("============removed==============");

        for(String s: deque){
            System.out.println(s);
        }
        System.out.println("size: "+deque.size());
        System.out.println("============poll action==============");

        el = deque.poll();
        System.out.println("poll: " + el);

        el = deque.pollFirst();
        System.out.println("pollFirst: " + el);

        el = deque.pollLast();
        System.out.println("pollLast: " + el);
        System.out.println("============polled==============");
        System.out.println("size: "+deque.size());

        for(String s: deque){
            System.out.println(s);
        }
        System.out.println("============pop==============");
        System.out.println("size: "+deque.size());
        String popEl = deque.pop();
        System.out.println("poped:" + popEl);
        for(String s: deque){
            System.out.println(s);
        }

        System.out.println("size: "+deque.size());

        System.out.println(deque.contains("three"));


        // loop
        Deque<String> dx = new LinkedList<>();
        dx.add("element 0");
        dx.add("element 1");
        dx.add("element 2");
        Iterator<String> ix = dx.iterator();
        while(ix.hasNext()){
            String element = ix.next();
            System.out.println(element);
        }
    }
}
