package collections;

import java.util.PriorityQueue;

public class PriorityQueueDemo {
    
    public static void main(String[] args) {
        // Create a priority queue of integers
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(5);
        pq.add(2);
        pq.add(8);
        pq.add(1);

        // Display the priority queue
        System.out.println("Priority Queue: " + pq);

        // Accessing the head element (smallest element)
        System.out.println("Head element: " + pq.peek());

        // Removing the head element
        System.out.println("Removed head element: " + pq.poll());
        System.out.println("Priority Queue after removal: " + pq);

        // Iterating through the priority queue
        System.out.println("Iterating through the Priority Queue:");
        for (Integer num : pq) {
            System.out.println(num);
        }

        // Size of the priority queue
        System.out.println("Size of the Priority Queue: " + pq.size());

        // Clear the priority queue
        pq.clear();
        System.out.println("Priority Queue after clearing: " + pq);


        //max heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(java.util.Collections.reverseOrder());
        maxHeap.add(5);
        maxHeap.add(2);
        maxHeap.add(8);
        maxHeap.add(1);

        System.out.println("Max Heap: " + maxHeap);
        System.out.println("Head element (largest): " + maxHeap.peek());
        System.out.println("Removed head element (largest): " + maxHeap.poll());
        System.out.println("Max Heap after removal: " + maxHeap);

        //K-size heap pattern (Top K largest)
        int k = 3;
        PriorityQueue<Integer> topK = new PriorityQueue<>();
        int[] nums = {5, 2, 8, 1, 10, 3};
        for (int num : nums) {
            topK.add(num);
            if (topK.size() > k) {
                topK.poll();
            }
        }
        System.out.println("Top " + k + " largest elements: " + topK);


        //min heap pattern (Top K smallest)
        PriorityQueue<Integer> topKSmallest = new PriorityQueue<>();
        for (int num : nums) {
            topKSmallest.add(num);
            if (topKSmallest.size() > k) {
                topKSmallest.poll();
            }
        }
        System.out.println("Top " + k + " smallest elements: " + topKSmallest);
    }
}
