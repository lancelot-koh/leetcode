package collections;
import java.util.LinkedList;
import java.util.Iterator;

public class LinkedListDemo {
    public static void main(String[] args) {

        // ── Create ────────────────────────────────────────────────────────────
        LinkedList<String> list = new LinkedList<>();
        list.add("Banana");
        list.add("Cherry");
        list.addFirst("Apple");      // insert at head
        list.addLast("Date");        // insert at tail
        System.out.println("LinkedList: " + list);

        // ── Access ────────────────────────────────────────────────────────────
        System.out.println("First : " + list.getFirst());
        System.out.println("Last  : " + list.getLast());
        System.out.println("Index 1: " + list.get(1));

        // ── Modify ────────────────────────────────────────────────────────────
        list.set(2, "Coconut");
        System.out.println("After set(2): " + list);

        // ── Remove ────────────────────────────────────────────────────────────
        list.removeFirst();
        list.removeLast();
        list.remove("Coconut");      // remove by value
        System.out.println("After removes: " + list);

        // ── Peek vs Poll (safe head access) ───────────────────────────────────
        list.addFirst("Apple");
        list.addLast("Cherry");
        System.out.println("peek  (no remove): " + list.peek());
        System.out.println("poll  (removes)  : " + list.poll());
        System.out.println("After poll: " + list);

        // ── Use as Stack (LIFO) ───────────────────────────────────────────────
        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(1);   // addFirst
        stack.push(2);
        stack.push(3);
        System.out.println("Stack: " + stack);
        System.out.println("pop: " + stack.pop());   // removeFirst
        System.out.println("Stack after pop: " + stack);

        // ── Use as Queue (FIFO) ───────────────────────────────────────────────
        LinkedList<Integer> queue = new LinkedList<>();
        queue.offer(10);  // addLast
        queue.offer(20);
        queue.offer(30);
        System.out.println("Queue: " + queue);
        System.out.println("poll: " + queue.poll());  // removeFirst
        System.out.println("Queue after poll: " + queue);

        // ── Use as Deque (both ends) ──────────────────────────────────────────
        LinkedList<String> deque = new LinkedList<>();
        deque.offerFirst("B");
        deque.offerFirst("A");
        deque.offerLast("C");
        deque.offerLast("D");
        System.out.println("Deque: " + deque);
        System.out.println("peekFirst: " + deque.peekFirst());
        System.out.println("peekLast : " + deque.peekLast());
        deque.pollFirst();
        deque.pollLast();
        System.out.println("Deque after pollFirst/Last: " + deque);

        // ── Search ────────────────────────────────────────────────────────────
        LinkedList<String> fruits = new LinkedList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Banana");
        System.out.println("contains 'Banana': " + fruits.contains("Banana"));
        System.out.println("indexOf  'Banana': " + fruits.indexOf("Banana"));
        System.out.println("lastIndexOf 'Banana': " + fruits.lastIndexOf("Banana"));

        // ── Iterate ───────────────────────────────────────────────────────────
        System.out.print("for-each: ");
        for (String f : fruits) {
            System.out.print(f + " ");
        }
        System.out.println();

        Iterator<String> it = fruits.descendingIterator();
        System.out.print("descending: ");
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();

        // ── Size / Clear ──────────────────────────────────────────────────────
        System.out.println("size: " + fruits.size());
        System.out.println("isEmpty: " + fruits.isEmpty());
        fruits.clear();
        System.out.println("After clear, isEmpty: " + fruits.isEmpty());
    }
}
