package collections;
import java.util.Stack;

public class StackDemo {
    public static void main(String[] args) {
        // Create a stack of strings
        Stack<String> stack = new Stack<>();
        stack.push("Apple");
        stack.push("Banana");
        stack.push("Cherry");

        // Display the stack
        System.out.println("Stack: " + stack);

        // Accessing the top element
        System.out.println("Top element: " + stack.peek());

        // Removing the top element
        System.out.println("Popped element: " + stack.pop());
        System.out.println("Stack after popping: " + stack);

        // Iterating through the stack
        System.out.println("Iterating through the Stack:");
        for (String fruit : stack) {
            System.out.println(fruit);
        }

        // Size of the stack
        System.out.println("Size of the Stack: " + stack.size());

        // Clear the stack
        stack.clear();
        System.out.println("Stack after clearing: " + stack);
    }
}
