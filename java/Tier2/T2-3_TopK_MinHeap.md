# T2-3 — Top-K (Min-Heap Size k) 前K大（大小为k的最小堆）

> **Core idea:** Maintain a min-heap of exactly k elements. When the heap exceeds k, evict the smallest — guaranteeing the heap always holds the k largest seen so far. The heap top is the Kth largest.
> **核心思想：** 维护恰好k个元素的最小堆。超过k时弹出最小值，堆中始终保留目前见过的k个最大值。堆顶即第k大。
>
> Complexity: O(n log k) time, O(k) space. Better than O(n log n) full sort when k << n.
> Full reference: `Heap/description.md` Patterns 1–2

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
PriorityQueue<Integer> minHeap = new PriorityQueue<>();   // default = min-heap

for (int num : nums) {
    minHeap.offer(num);
    if (minHeap.size() > k) minHeap.poll();   // evict smallest → keep k largest
}

int kthLargest = minHeap.peek();   // top of min-heap = Kth largest
```

### K Smallest (max-heap size k)

```java
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

for (int num : nums) {
    maxHeap.offer(num);
    if (maxHeap.size() > k) maxHeap.poll();   // evict largest → keep k smallest
}

int kthSmallest = maxHeap.peek();
```

### K-way Merge

```java
PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
// {value, listIndex, elementIndex}

// Initialize: push first element of each list
for (int i = 0; i < lists.length; i++)
    if (lists[i] != null)
        minHeap.offer(new int[]{lists[i].val, i, 0});

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
        if (minHeap.size() > k) minHeap.poll();
    }
    return minHeap.peek();
}
```

### Top K Frequent Elements (LC 347)
```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) freq.merge(n, 1, Integer::sum);

    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
        minHeap.offer(new int[]{e.getKey(), e.getValue()});
        if (minHeap.size() > k) minHeap.poll();
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
        if (maxHeap.size() > k) maxHeap.poll();
    }
    return maxHeap.toArray(new int[k][]);
}
```

### Merge K Sorted Lists (LC 23)
```java
public ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
    for (ListNode l : lists) if (l != null) minHeap.offer(l);

    ListNode dummy = new ListNode(0), cur = dummy;
    while (!minHeap.isEmpty()) {
        ListNode node = minHeap.poll();
        cur.next = node; cur = cur.next;
        if (node.next != null) minHeap.offer(node.next);
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
