package collections;

import java.util.*;

public class ArrayDemo {
    public static void main(String[] args) {
        // Create an array of integers
        // declare and initialize an array
        int[] numbers = {1, 2,3,4, 5};
        int[] arr = new int[5];
        int[][] mat = new int[3][3]; 
        // Accessing elements in the array
        System.out.println("First element: " + numbers[0]);
        System.out.println("Second element: " + numbers[1]);    

        System.out.println("Array length: " + numbers.length);

        // Iterating through the array
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("Element at index " + i + ": " + numbers[i]);
        }

        // Using enhanced for loop
        System.out.println("Using enhanced for loop:");
        for (int num : numbers) {
            System.out.println(num);
        }

        //sort 
        Arrays.sort(numbers);
        System.out.println("Sorted array: " + Arrays.toString(numbers));

        Integer[] numbers2 = {5, 4, 3, 2, 1};
        Arrays.sort(numbers2, (a, b) -> b - a);
        System.out.println("Sorted array in reverse order: " + Arrays.toString(numbers));


        //copying array
        int[] copiedArray = Arrays.copyOf(numbers, numbers.length);
        System.out.println("Copied array: " + Arrays.toString(copiedArray));

        int[] rangeArray = Arrays.copyOfRange(numbers, 1, 4);
        System.out.println("Range array: " + Arrays.toString(rangeArray));  

        //fill array
        int[] filledArray = new int[5];
        Arrays.fill(filledArray, 10);
        System.out.println("Filled array: " + Arrays.toString(filledArray));


        // Compare arrays
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        int[] array3 = {4, 5, 6};   
        System.out.println("array1 equals array2: " + Arrays.equals(array1, array2));
        System.out.println("array1 equals array3: " + Arrays.equals(array1, array3));

        // Convert array to list 
        List<Integer> numberList = Arrays.asList(numbers2);
        System.out.println("Array as list: " + numberList); 


        // Convert list to array
        Integer[] listToArray = numberList.toArray(new Integer[0]);
        System.out.println("List to array: " + Arrays.toString(listToArray));


        // Multidimensional array
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Element at (1, 2): " + matrix[1][2]);

        // Iterating through a multidimensional array
        System.out.println("Multidimensional array:");
        for (int i = 0; i < matrix.length; i++) {   
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        // Using Arrays.deepToString for multidimensional arrays
        System.out.println("Multidimensional array (using deepToString): " + Arrays.deepToString(matrix));

        // Sorting a 2D array based on the first element of each sub-array
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.println("Sorted intervals: " + Arrays.deepToString(intervals));


        int[][] intervals2 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        Arrays.sort(intervals2, (a, b) -> a[0] - b[0]);
        System.out.println("Sorted intervals2: " + Arrays.deepToString(intervals2));
    }
}