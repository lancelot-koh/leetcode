package com.future.java.collection;

import java.util.*;

public class SortDdemo {
    /**
     * Collections.sort
     * List/LinkedList
     */

    public static void main(String[] args) {
        List list = new ArrayList();
        Collections.sort(list);


        // comparator

        List listA = new LinkedList();
//        Comparator<Employee> comparator = new MyComparator<Employee>();
//        Collections.sort(listA, comparator);

    }

    static class Employee {
        private int salary;
        public Employee(int salary) {
            this.salary = salary;
        }

        public int getSalary() {
            return salary;
        }
    }

//     static class MyComparator<Employee> implements Comparator<Employee> {
//        public int compare(Employee o1, Employee o2) {
////            if(o1.getSalary() <  o2.getSalary()) return -1;
////            if(o1.getSalary() == o2.getSalary()) return 0;
//            return 1;
//
//            //  return emp1.getSalary() - emp2.getSalary();
//        }
//    }
}
