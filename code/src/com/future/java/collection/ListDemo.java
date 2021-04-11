package com.future.java.collection;

import java.util.*;
import java.util.stream.Stream;

public class ListDemo {
    /**
     * List implementation
     * java.util.ArrayList
     * java.util.LinkedList
     * java.util.Vector
     * java.util.Stack
     */
    public static void main(String[] args) {
        List listA = new ArrayList();
        List listB = new LinkedList();
        List listC = new Vector();
        List listD = new Stack();

        // Generic list
        List<String> gList = new ArrayList<>();
        gList.add("First");
        gList.add("Item 1");
        gList.add("Item 2");

        for(String s: gList) {
            System.out.println(s);
        }

        gList.add("Item 4");
        gList.add("Item 4");
        gList.add("Item 2");
        gList.add(0, "zero");
        System.out.println("first element:" + gList.get(0));

        Object element = null;
        List<Object> list = new ArrayList<>();
        list.add(element);

        List<String> sList = new ArrayList<>();
        sList.add("123");
        sList.add("123");

        sList.add("456");

        gList.addAll(sList);
        for(String s: gList) {
            System.out.println(s);
        }

        System.out.println("zero index: "+gList.indexOf("zero"));
        System.out.println("123 index: "+gList.indexOf("123"));

        System.out.println("789 index: "+gList.indexOf("789"));
        System.out.println("789 contains: "+gList.contains("789"));

        System.out.println("item 2 index: "+gList.indexOf("Item 2"));
        System.out.println("item 2 contains: "+gList.contains("Item 2"));

        System.out.println("item 2 index: "+gList.lastIndexOf("Item 2"));


        gList.remove("Item 2");
        System.out.println("item 2 contains: "+gList.contains("Item 2"));
        System.out.println("item 2 index: "+gList.indexOf("Item 2"));

        gList.remove("Item 1");
        System.out.println("item 1 contains: "+gList.contains("Item 1"));


        System.out.println("sList size - before clear: "+ sList.size());
        sList.clear();
        System.out.println("sList size - after clear: "+ sList.size());


        List<String> listx      = new ArrayList<>();
        List<String> listy = new ArrayList<>();

        String element1 = "element 1";
        String element2 = "element 2";
        String element3 = "element 3";
        String element4 = "element 4";

        listx.add(element1);
        listx.add(element2);
        listx.add(element3);

        listy.add(element1);
        listy.add(element3);
        listy.add(element4);

        listx.retainAll(listy);
        System.out.println("=======retainAll=========");

        for(String s: listx) {
            System.out.println(s);
        }

        System.out.println("=======sublist=========");

        List<String> subList = gList.subList(1,4);
        for(String s: subList) {
            System.out.println(s);
        }

        System.out.println("=======convert list to set=========");
        System.out.println("=======gList size=========" + gList.size());

        Set<String> set = new HashSet<>();
        set.addAll(gList);
        System.out.println("=======set size=========" + set.size());


        // convert list to Array
        Object []strs = gList.toArray();
        String []strs2 = gList.toArray(new String[0]);
        System.out.println("=======list to array =========" +  Arrays.toString(strs));
        System.out.println("=======list to array 2 =========" + Arrays.toString(strs2));

        //convert array to list
        String[] values = new String[]{ "one", "two", "three" };
        String[] values2 = { "1", "2", "3" };
        List<String> cList = Arrays.asList(values);
        List<String> cList2 = Arrays.asList(values2);

        // sort
        Collections.sort(cList);
        for(String s: cList) {
            System.out.println(s);
        }

        // sort with comparator
        class Car {
            public String brand;
            public int wheels;

            public Car(String brand, int wheels) {
                this.brand = brand;
                this.wheels = wheels;
            }
        }

        List<Car> carList = new ArrayList<>();
        carList.add(new Car("Volvo 40", 4));
        carList.add(new Car("Citron C1", 6));
        carList.add(new Car("Dodge Ram", 8));

        Comparator<Car> carComparator = new Comparator<Car>() {
            @Override
            public int compare(Car o1, Car o2) {
                return o1.brand.compareTo(o2.brand);
            }
        };

        Collections.sort(carList, carComparator);
        for(Car car: carList){
            System.out.println(car.brand +" / "+ car.wheels);
        }

        // loop
        List<String> lList = new ArrayList<>();
        for(int i = 0; i < 10000000; i++){
            lList.add("Testing "+ i);
        }

        long begin = System.currentTimeMillis();
        for(int i = 0; i < lList.size(); i++) {
            //
        }
        long end = System.currentTimeMillis();
        System.out.println("Standard loop caused: " + (end - begin));

        begin = System.currentTimeMillis();
        Iterator<String> iterator = lList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        end = System.currentTimeMillis();
        System.out.println("Iterator loop caused: " + (end - begin));

        begin = System.currentTimeMillis();
        for(String s: lList) {

        }
        end = System.currentTimeMillis();
        System.out.println("for-each loop caused: " + (end - begin));

        begin = System.currentTimeMillis();
        Stream<String> stream = lList.stream();
        stream.forEach( item -> {
//            System.out.println(item);
        });
        end = System.currentTimeMillis();
        System.out.println("stream loop caused: " + (end - begin));

        gList.isEmpty();
        gList.removeAll(lList);
    }
}
