package com.future.java.collection;

import java.util.*;
import java.util.stream.Stream;

public class StackDemo {
    public static void main(String[] args) {
        Stack stackA = new Stack();

        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        stack.push("e");
        System.out.println("========elements=======");
        for(String s: stack) {
            System.out.println(s);
        }

        while (!stack.isEmpty()) {
            String p = stack.pop();
            System.out.println("pop element: "+p);
        }

        stack.push("one");
        stack.push("two");
        // peek and pop throw exception if empty
        String pk = stack.peek();
        System.out.println("peek element: "+pk);

        stack.push("three");
        stack.push("four");

        System.out.println("search four: "+ stack.search("four"));
        System.out.println("search five: "+ stack.search("five"));

        System.out.println("size: " + stack.size());

        // iterator
        Iterator it = stack.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }

        // stream
        System.out.println("=====stream loop======");
        Stream stream = stack.stream();
        stream.forEach((element) -> {
            System.out.println(element);
        });



        // reverse
        List<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");
        System.out.println(list);

        Stack<String> st = new Stack<String>();
        while(list.size() > 0) {
            st.push(list.remove(0));
        }

        while(st.size() > 0){
            list.add(st.pop());
        }

        System.out.println(list);


        // Deque as a Stack
        Deque<String> dequeAsStack = new ArrayDeque<String>();

        dequeAsStack.push("one");
        dequeAsStack.push("two");
        dequeAsStack.push("three");

        String one   = dequeAsStack.pop();
        String two   = dequeAsStack.pop();
        String three = dequeAsStack.pop();
    }
}
