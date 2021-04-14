package com.future.java.collection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueDemo {
    /**
     * Queue Implementations
     *    1, 2, 3, 4, 5  <- insert
     *    ^           ^
     *   head         tail
     * java.util.LinkedList
     * java.util.PriorityQueue
     */
    public static void main(String[] args) {
        Queue q1 = new LinkedList();
        Queue q2 = new PriorityQueue();


        Queue<String> queue = new LinkedList<>();
        queue.add("One");
        queue.add("Two");
        queue.add("Three");
        queue.add("four");
        queue.add("five");
        System.out.println("queue sizeL "+ queue.size());

        for(String s: queue){
            System.out.println(s);
        }

        String pollElement = queue.poll();
        System.out.println("poll:" + pollElement);


        System.out.println("queue sizeL "+ queue.size());
        // remove from head
        String element2 = queue.remove();
        System.out.println("===remove ===: " + element2);

        for(String s: queue){
            System.out.println(s);
        }

        System.out.println("queue sizeL "+ queue.size());

        // peek() == element()
        // peek return null if empty
        // element throw exception if empty
        System.out.println("First element:" + queue.element());
        System.out.println("Queue peek:" + queue.peek());

        System.out.println("queue sizeL "+ queue.size());


        System.out.println(queue.contains("five"));

        // loop

        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
