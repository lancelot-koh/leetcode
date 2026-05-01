package collections;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class ArrayListDemo {
    public static void main(String[] args) {
        // Create an ArrayList of strings
        ArrayList<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");

        // Display the ArrayList
        System.out.println("ArrayList: " + list);

        // Accessing elements
        System.out.println("First element: " + list.get(0));
        System.out.println("Second element: " + list.get(1));

        // Modifying elements
        list.set(1, "Blueberry");
        System.out.println("Modified ArrayList: " + list);

        // Removing elements
        list.remove(0);
        System.out.println("ArrayList after removal: " + list);

        // Iterating through the ArrayList
        System.out.println("Iterating through the ArrayList:");
        for (String fruit : list) {
            System.out.println(fruit);
        }

        // Size of the ArrayList
        System.out.println("Size of the ArrayList: " + list.size());

        // Clear the ArrayList
        list.clear();
        System.out.println("ArrayList after clearing: " + list);

        //size, indexOf, contains, isEmpty
        list.add("Apple");
        list.add("Banana");    
        System.out.println("Size of the ArrayList: " + list.size());
        System.out.println("Index of 'Banana': " + list.indexOf("Banana"));        
        System.out.println("Contains 'Cherry': " + list.contains("Cherry"));
        System.out.println("Is the ArrayList empty? " + list.isEmpty());

        Collections.sort(list);
        System.out.println("Sorted ArrayList: " + list);

        Collections.reverse(list);
        System.out.println("Reversed ArrayList: " + list);


        System.out.println("ArrayList sorted in reverse order - before sorting: " + list);
        Collections.sort(list, Collections.reverseOrder());
        System.out.println("ArrayList sorted in reverse order: " + list);

        System.out.println("ArrayList sorted in order using lambda - before sorting: " + list);
        Collections.sort(list, (a, b) -> a.compareTo(b));
        System.out.println("ArrayList sorted in order using lambda: " + list);


        //min and max
        list.add("Cherry");
        System.out.println("ArrayList: " + list);  
        String minElement = Collections.min(list);
        String maxElement = Collections.max(list);
        System.out.println("Minimum element: " + minElement);
        System.out.println("Maximum element: " + maxElement);


        //convert ArrayList to array
        String[] array = list.toArray(new String[0]);
        System.out.println("Array from ArrayList: " + Arrays.toString(array));

        //convert array to ArrayList
        String[] fruitsArray = {"Mango", "Pineapple", "Grapes"};
        ArrayList<String> fruitsList = new ArrayList<>(Arrays.asList(fruitsArray));
        System.out.println("ArrayList from array: " + fruitsList);


        //initialize ArrayList with values
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(5, 3, 8, 1, 4));
        System.out.println("Initialized ArrayList: " + numbers);


    }
}
