package collections;
import java.util.HashSet;
import java.util.Set;

public class SetDemo {
    public static void main(String[] args) {
        // Create a set of strings
        Set<String> set = new HashSet<>();
        set.add("Apple");
        set.add("Banana");
        set.add("Cherry");
        set.add("Apple"); // Duplicate element, will not be added

        // Display the set
        System.out.println("Set: " + set);

        // Check if an element exists in the set
        System.out.println("Contains 'Banana': " + set.contains("Banana"));
        System.out.println("Contains 'Grapes': " + set.contains("Grapes"));

        // Remove an element from the set
        set.remove("Banana");
        System.out.println("Set after removing 'Banana': " + set);

        // Iterate through the set
        System.out.println("Iterating through the set:");
        for (String fruit : set) {
            System.out.println(fruit);
        }

        // Clear the set
        set.clear();
        System.out.println("Set after clearing: " + set);

        // Create a set of integers
        Set<Integer> intSet = new HashSet<>();
        intSet.add(1);
        intSet.add(2);
        intSet.add(3);      

        // Display the integer set
        System.out.println("Integer Set: " + intSet);       


        //any other operations on set
        Set<String> setA = new HashSet<>();
        setA.add("A");
        setA.add("B");
        setA.add("C");              

        Set<String> setB = new HashSet<>();
        setB.add("B");
        setB.add("C");
        setB.add("D");  
        // Union of setA and setB
        Set<String> unionSet = new HashSet<>(setA);
        unionSet.addAll(setB);
        System.out.println("Union of setA and setB: " + unionSet);      

        // Intersection of setA and setB
        Set<String> intersectionSet = new HashSet<>(setA);
        intersectionSet.retainAll(setB);
        System.out.println("Intersection of setA and setB: " + intersectionSet);    
        // Difference of setA and setB
        Set<String> differenceSet = new HashSet<>(setA);
        differenceSet.removeAll(setB);
        System.out.println("Difference of setA and setB: " + differenceSet);


    }
}
