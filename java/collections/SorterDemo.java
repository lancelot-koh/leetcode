package collections;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class SorterDemo {
    public static void main(String[] args) {
        // Create an array of integers
        int[] numbers = {5, 2, 9, 1, 5, 6};
        System.out.println("Original array: " + Arrays.toString(numbers));

        // Sort the array
        Arrays.sort(numbers);
        System.out.println("Sorted array: " + Arrays.toString(numbers));

        // Create a list of strings
        List<String> names = new ArrayList<>();
        names.add("Charlie");
        names.add("Alice");
        names.add("Bob");
        System.out.println("Original list: " + names);

        // Sort the list
        Collections.sort(names);
        System.out.println("Sorted list: " + names);


        // sort 2-d array
        int[][] matrix = {
            {3, 2, 1},
            {6, 5, 4},
            {9, 8, 7}
        };
        System.out.println("Original matrix:");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));   
        }
        // Sort each row of the matrix
        for (int[] row : matrix) {
            Arrays.sort(row);
        }
        System.out.println("Sorted matrix:");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }

        // Sort 2d array by first column and second column
        Arrays.sort(matrix, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            } else {                
                return Integer.compare(a[1], b[1]);
            }
        });
        System.out.println("Sorted matrix by first and second column:");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }

        // Sort list of strings by length
        Collections.sort(names, (a, b) -> Integer.compare(a.length(), b.length()));
        System.out.println("Sorted list by length: " + names);

        names.sort(Comparator.comparingInt(String::length));
        System.out.println("Sorted list by length using Comparator: " + names);


        // Stable sort with multiple keys   
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 30));
        people.add(new Person("David", 25));
        System.out.println("Original people list: " + people);
        Collections.sort(people, Comparator.comparingInt(Person::getAge).thenComparing(Person::getName));
        System.out.println("Sorted people list by age and name: " + people);    


    }

    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return name + " (" + age + ")";
        }
    }
}
