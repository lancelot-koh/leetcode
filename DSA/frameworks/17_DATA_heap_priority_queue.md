# 📚 Heap & Priority Queue - Efficient Ordering

**Get min/max efficiently, process by priority**

---

## Interview Frequency: **4% of problems** ⭐⭐⭐

---

## Core Templates

### Min Heap (Get Smallest)

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
minHeap.offer(5);
minHeap.offer(2);
minHeap.offer(8);

while (!minHeap.isEmpty()) {
    System.out.println(minHeap.poll());  // 2, 5, 8
}
```

### Max Heap (Get Largest)

```java
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
maxHeap.offer(5);
maxHeap.offer(2);
maxHeap.offer(8);

while (!maxHeap.isEmpty()) {
    System.out.println(maxHeap.poll());  // 8, 5, 2
}
```

### Custom Comparator

```java
PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> {
    if (a.freq != b.freq) {
        return a.freq - b.freq;
    }
    return a.val - b.val;
});
```

---

## Examples

- **Top K Elements:** Find k largest/smallest
- **Merge K Lists:** Merge multiple sorted lists
- **Median Finder:** Track median
- **Dijkstra:** Shortest path with weights

---

## Key Points

- **Min Heap:** Root is smallest, children larger
- **Max Heap:** Root is largest, children smaller
- **Operations:** O(log n) for add/remove, O(1) for peek
- **Use When:** Need efficient min/max, not full sort

---

**Master Heap/PQ. It's 4% of interviews.** 🚀
