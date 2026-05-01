package collections;
import java.util.LinkedList;
import java.util.Queue;

public class QueueDemo {
    public static void main(String[] args) {
        // Create a queue of strings
        Queue<String> queue = new LinkedList<>();
        queue.add("Apple");
        queue.add("Banana");
        queue.add("Cherry");

        // Display the queue
        System.out.println("Queue: " + queue);

        // Accessing the front element
        System.out.println("Front element: " + queue.peek());

        // Removing the front element
        System.out.println("Removed element: " + queue.poll());
        System.out.println("Queue after removal: " + queue);

        // Iterating through the queue
        System.out.println("Iterating through the Queue:");
        for (String fruit : queue) {
            System.out.println(fruit);
        }

        // Size of the queue
        System.out.println("Size of the Queue: " + queue.size());

        // Clear the queue
        queue.clear();
        System.out.println("Queue after clearing: " + queue);
    }
}
