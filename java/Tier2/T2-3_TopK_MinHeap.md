# T2-3 — Top-K (Min-Heap Size k) 前K大（大小为k的最小堆）

> **Core idea:** Maintain a min-heap of exactly k elements. When the heap exceeds k, evict the smallest — guaranteeing the heap always holds the k largest seen so far. The heap top is the Kth largest.
> **核心思想：** 维护恰好k个元素的最小堆。超过k时弹出最小值，堆中始终保留目前见过的k个最大值。堆顶即第k大。
>
> Complexity: O(n log k) time, O(k) space. Better than O(n log n) full sort when k << n.
> Full reference: `Heap/description.md` Patterns 1–2

---

## How it Works — Mental Model 算法原理

A min-heap of size k acts as a **filter**: it always keeps exactly the k largest elements it has seen so far. The key insight is that the heap top (the minimum of the heap) is the "weakest survivor" — the smallest element among the k largest. When a new element arrives, if it is larger than this weakest survivor, it deserves to be in the top-k and the weakest survivor must leave. If the new element is smaller, it cannot be in the top-k and is simply discarded. After scanning all n elements, the heap holds exactly the k largest, and its top is the Kth largest. The counter-intuitive trick is that you use a **min**-heap to find the **max**-k.

**Key invariant:** At all times, the min-heap contains exactly the k largest elements seen so far (or fewer than k if not enough elements have been processed yet). The heap top equals the Kth largest element seen so far.

**Common mistake / gotcha:** Using a max-heap for "top k largest" — a max-heap of all n elements would work but costs O(n log n) total. Also, avoid `a - b` as a comparator when values can overflow int; use `Integer.compare(a, b)` instead.

---

## Step-by-Step Trace 执行步骤示意

Example: `findKthLargest([3, 1, 5, 12, 2, 11], k=3)`
```
Process 3:  heap=[3]           (size 1 ≤ k, no eviction)
Process 1:  heap=[1,3]         (size 2 ≤ k)
Process 5:  heap=[1,3,5]       (size 3 = k, heap full)
Process 12: offer→heap=[1,3,5,12], size>k → poll min(1) → heap=[3,5,12]
Process 2:  offer→heap=[2,3,5,12], size>k → poll min(2) → heap=[3,5,12]
Process 11: offer→heap=[3,5,11,12], size>k → poll min(3) → heap=[5,11,12]
Result: heap.peek() = 5  (3rd largest)
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Kth largest / top-K largest | LC 215, 347 |
| Kth smallest / top-K smallest | reverse: max-heap size k |
| Merge K sorted lists/arrays | LC 23 |
| Find closest K points | LC 973 |
| Running top-K as elements arrive | streaming scenario |

**Heap vs Sort vs QuickSelect:**
| Method | Time | When |
|---|---|---|
| Sort | O(n log n) | Simple, static data |
| **Min-heap size k** | O(n log k) | k << n or streaming data |
| QuickSelect | O(n) avg | Only need Kth element, not all k |

---

## Core Templates 核心模板

### K Largest (min-heap size k)

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();   // default = min-heap (natural order)

for (int num : nums) {
    minHeap.offer(num);
    if (minHeap.size() > k) { minHeap.poll(); }   // evict the smallest → the k largest survive
}

int kthLargest = minHeap.peek();   // min of the k largest = the Kth largest overall
```

### K Smallest (max-heap size k)

```java
// Flip: max-heap evicts the largest, keeping the k smallest
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

for (int num : nums) {
    maxHeap.offer(num);
    if (maxHeap.size() > k) { maxHeap.poll(); }   // evict largest → keep k smallest
}

int kthSmallest = maxHeap.peek();  // max of the k smallest = the Kth smallest overall
```

### K-way Merge

```java
PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
// {value, listIndex, elementIndex}

// Initialize: push first element of each list
for (int i = 0; i < lists.length; i++) {
    if (lists[i] != null) {
        minHeap.offer(new int[]{lists[i].val, i, 0});
    }
}

while (!minHeap.isEmpty()) {
    int[] cur = minHeap.poll();
    // process cur[0] (the value)
    // push next element from same list
}
```

---

## Key Examples 关键例题

### Kth Largest Element (LC 215)
```java
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    for (int num : nums) {
        minHeap.offer(num);
        if (minHeap.size() > k) { minHeap.poll(); }
    }
    return minHeap.peek();
}
```

### Top K Frequent Elements (LC 347)
```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) { freq.merge(n, 1, Integer::sum); }

    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
        minHeap.offer(new int[]{e.getKey(), e.getValue()});
        if (minHeap.size() > k) { minHeap.poll(); }
    }
    return minHeap.stream().mapToInt(a -> a[0]).toArray();
}
```

### K Closest Points to Origin (LC 973)
```java
public int[][] kClosest(int[][] points, int k) {
    // Max-heap by distance: keep k closest (= k smallest distances)
    PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
        (a, b) -> (b[0]*b[0]+b[1]*b[1]) - (a[0]*a[0]+a[1]*a[1]));
    for (int[] p : points) {
        maxHeap.offer(p);
        if (maxHeap.size() > k) { maxHeap.poll(); }
    }
    return maxHeap.toArray(new int[k][]);
}
```

### Merge K Sorted Lists (LC 23)
```java
public ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);  // min by node value
    for (ListNode l : lists) { if (l != null) { minHeap.offer(l); } }  // seed with heads of all lists

    ListNode dummy = new ListNode(0), cur = dummy;  // dummy avoids null-check for first node
    while (!minHeap.isEmpty()) {
        ListNode node = minHeap.poll();             // globally smallest remaining node
        cur.next = node; cur = cur.next;
        if (node.next != null) { minHeap.offer(node.next); }  // advance that list by one
    }
    return dummy.next;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| K largest → min-heap | Counter-intuitive: MIN-heap for K LARGEST (evict the smallest) |
| K smallest → max-heap | MAX-heap for K SMALLEST (evict the largest) |
| Custom comparator overflow | Use `Integer.compare(a, b)` not `a - b` when values could overflow int |
| K-way merge: push next from same list | After polling a node, push `node.next` from the same list |
| Heap vs TreeMap | Heap: O(log n) push/pop, O(1) peek. TreeMap: O(log n) all ops but supports arbitrary removal |

---

## Java API Reference

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();                    // natural order
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
PriorityQueue<int[]>   custom  = new PriorityQueue<>((a, b) -> a[0] - b[0]);

heap.offer(val);     // insert O(log n)
heap.poll();         // remove & return min O(log n)
heap.peek();         // view min O(1)
heap.size();
```

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 215 Kth Largest Element |
| Medium | LC 347 Top K Frequent Elements |
| Medium | LC 973 K Closest Points |
| Hard | LC 23 Merge K Sorted Lists |
| Hard | LC 295 Find Median from Data Stream (two heaps) |

**Order:** 215 → 347 → 973 → 23 → 295

---

## One-line Summary

```
Top-K = min-heap of size k: offer every element, poll when size > k; heap top = Kth largest.
前K大 = 大小k的最小堆：每个元素都入堆，超过k时弹出；堆顶即第k大。
```
