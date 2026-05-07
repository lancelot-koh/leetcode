package collections;
import java.util.Deque;
import java.util.LinkedList;

public class DequeDemo {
    public static void main(String[] args) {
        // Create a deque of strings
        Deque<String> deque = new LinkedList<>();
        deque.add("Apple");
        deque.add("Banana");
        deque.add("Cherry");
        deque.offerLast("test1");
        deque.offerFirst("test2");  
        deque.offerLast("test33");

        // Display the deque
        System.out.println("Deque: " + deque);

        // Accessing the front and rear elements
        System.out.println("Front element: " + deque.peekFirst());
        System.out.println("Rear element: " + deque.peekLast());

        // Removing elements from both ends
        System.out.println("Removed front element: " + deque.pollFirst());
        System.out.println("Removed rear element: " + deque.pollLast());
        System.out.println("Deque after removals: " + deque);

        // Iterating through the deque
        System.out.println("Iterating through the Deque:");
        for (String fruit : deque) {
            System.out.println(fruit);
        }

        // Size of the deque
        System.out.println("Size of the Deque: " + deque.size());

        // Clear the deque
        deque.clear();
        System.out.println("Deque after clearing: " + deque);
    }
}
