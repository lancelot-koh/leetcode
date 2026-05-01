# Heap / Priority Queue 堆 / 优先队列

> **Core idea:** A heap always gives you the min (or max) element in O(1), and insert/remove in O(log n). Use it whenever you need to repeatedly find the "best" element from a dynamic set.
> **核心思想：** 堆能 O(1) 取最小（或最大）元素，O(log n) 插入/删除。需要从动态集合中反复取"最优"元素时使用。
>
> Java: `PriorityQueue` is a **min-heap** by default. For max-heap: `new PriorityQueue<>(Collections.reverseOrder())`.
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| top K / K largest / K smallest | 前K大 / 前K小 |
| K-th largest / smallest | 第K大 / 第K小 |
| merge K sorted lists | 合并K个有序链表 |
| median of stream | 数据流中位数 |
| task scheduler | 任务调度 |
| find max/min repeatedly | 反复求最大/最小 |
| shortest path (weighted) | 最短路径（有权图）|

**Java PriorityQueue API**

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
PriorityQueue<int[]> custom  = new PriorityQueue<>((a, b) -> a[0] - b[0]);

heap.offer(val);      // insert — O(log n)
heap.poll();          // remove & return min — O(log n)
heap.peek();          // view min without removing — O(1)
heap.size();
```

**Min-heap vs Max-heap selection 选择技巧**

| Goal | Heap type | Why |
|---|---|---|
| Keep K **largest** elements | **Min**-heap size k | Poll when size > k; top is the Kth largest |
| Keep K **smallest** elements | **Max**-heap size k | Poll when size > k; top is the Kth smallest |
| Always get minimum | Min-heap | Natural |
| Median of stream | Min-heap (upper half) + Max-heap (lower half) | Two-heap trick |

---

## 2. Quick Decision Guide 快速判断

```
Find Kth largest / top-K largest?                → Pattern 1: Min-heap of size K
Find Kth smallest / top-K smallest?              → Pattern 1: Max-heap of size K
Merge multiple sorted sequences?                 → Pattern 2: K-way merge
Find median of a stream?                         → Pattern 3: Two heaps
Repeatedly process highest-priority task?        → Pattern 4: Task scheduling
Shortest path in weighted graph?                 → Pattern 5: Dijkstra
```

---

## 3. Patterns 模式

---

### Pattern 1 — Top-K Elements 前K大/小元素

**When:** find the K largest (or smallest) elements from an unsorted collection.
**适用：** 从无序集合中找K个最大（或最小）元素。

**Template 模板 (K largest)**

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();   // min-heap of size k

for (int num : nums) {
    minHeap.offer(num);
    if (minHeap.size() > k) minHeap.poll();    // evict smallest → keeps k largest
}
return minHeap.peek();                          // Kth largest
```

**Why min-heap for K largest? 为什么用最小堆找K大？**

The min-heap holds the K largest seen so far. The top is the **smallest of the K largest** = Kth largest. When a new element arrives and is larger than the Kth largest, it replaces it.
最小堆保存目前见过的K个最大值。堆顶是"K个最大值中的最小" = 第K大。新元素比堆顶大时，替换堆顶。

**Variants 变形**

| Variant | Heap type | Example |
|---|---|---|
| Kth largest element | min-heap size k | LC 215 |
| Top K frequent elements | min-heap by frequency | LC 347 |
| K closest points to origin | max-heap by distance | LC 973 |
| Sort K-sorted array | min-heap size k | custom |

**Example: Top K Frequent Elements (LC 347)**

```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) freq.merge(n, 1, Integer::sum);

    // Min-heap ordered by frequency (keep k most frequent)
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
        minHeap.offer(new int[]{e.getKey(), e.getValue()});
        if (minHeap.size() > k) minHeap.poll();
    }
    return minHeap.stream().mapToInt(a -> a[0]).toArray();
}
```

---

### Pattern 2 — K-way Merge K路合并

**When:** merge K sorted lists/arrays into one sorted sequence.
**适用：** 合并K个有序链表/数组为一个有序序列。

**Template 模板**

```java
// Merge K sorted linked lists (LC 23)
PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);

// Push the head of each list
for (ListNode list : lists)
    if (list != null) minHeap.offer(list);

ListNode dummy = new ListNode(0), cur = dummy;
while (!minHeap.isEmpty()) {
    ListNode node = minHeap.poll();
    cur.next = node;
    cur = cur.next;
    if (node.next != null) minHeap.offer(node.next);   // push next from same list
}
return dummy.next;
```

**Key insight 核心原理**

Always poll the globally smallest element across all K lists. Push the **next element from the same list** to keep the heap representing the current frontier of each list.
每次取全局最小；将同一链表的**下一个元素**入堆，维护每条链表的当前前沿。

**Variants 变形**

| Variant | Example |
|---|---|
| Merge K sorted linked lists | LC 23 |
| Merge K sorted arrays | custom |
| Smallest range covering K lists | LC 632 |
| Find K-th smallest in K sorted lists | custom |

---

### Pattern 3 — Two Heaps (Median of Stream) 双堆（数据流中位数）

**When:** maintain a running median as elements are added one by one.
**适用：** 动态维护数据流的中位数。

**Template 模板**

```java
PriorityQueue<Integer> lower = new PriorityQueue<>(Collections.reverseOrder()); // max-heap: lower half
PriorityQueue<Integer> upper = new PriorityQueue<>();                            // min-heap: upper half

public void addNum(int num) {
    lower.offer(num);
    upper.offer(lower.poll());              // balance: push max of lower to upper

    if (lower.size() < upper.size())
        lower.offer(upper.poll());          // keep lower.size() >= upper.size()
}

public double findMedian() {
    if (lower.size() > upper.size()) return lower.peek();
    return (lower.peek() + upper.peek()) / 2.0;
}
```

**Invariants 不变量**

- `lower` contains the smaller half; `lower.peek()` = max of lower half
- `upper` contains the larger half; `upper.peek()` = min of upper half
- `lower.size() == upper.size()` (even count) or `lower.size() == upper.size() + 1` (odd count)

**Variants 变形**

| Variant | Example |
|---|---|
| Median of data stream | LC 295 |
| Sliding window median | LC 480 |
| IPO (maximize capital) | LC 502 |

---

### Pattern 4 — Task Scheduling / Greedy with Heap 任务调度

**When:** repeatedly pick the highest-priority task; priorities change dynamically.
**适用：** 反复选最高优先级任务，优先级动态变化。

**Example: Reorganize String (LC 767)**

Always place the most frequent remaining character:

```java
public String reorganizeString(String s) {
    int[] freq = new int[26];
    for (char c : s.toCharArray()) freq[c - 'a']++;

    PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
    for (int i = 0; i < 26; i++)
        if (freq[i] > 0) maxHeap.offer(new int[]{i, freq[i]});

    StringBuilder sb = new StringBuilder();
    while (maxHeap.size() >= 2) {
        int[] first = maxHeap.poll(), second = maxHeap.poll();
        sb.append((char)('a' + first[0]));
        sb.append((char)('a' + second[0]));
        if (--first[1]  > 0) maxHeap.offer(first);
        if (--second[1] > 0) maxHeap.offer(second);
    }
    if (!maxHeap.isEmpty()) {
        if (maxHeap.peek()[1] > 1) return "";
        sb.append((char)('a' + maxHeap.poll()[0]));
    }
    return sb.toString();
}
```

---

### Pattern 5 — Dijkstra (Weighted Shortest Path) 最短路径

**When:** shortest path in a weighted graph with non-negative edge weights.
**适用：** 有权图（非负权）中的最短路径。

**Template 模板**

```java
int[] dist = new int[n];
Arrays.fill(dist, Integer.MAX_VALUE);
dist[src] = 0;

PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
minHeap.offer(new int[]{0, src});   // {distance, node}

while (!minHeap.isEmpty()) {
    int[] cur = minHeap.poll();
    int d = cur[0], u = cur[1];

    if (d > dist[u]) continue;     // stale entry — skip

    for (int[] edge : graph[u]) {
        int v = edge[0], w = edge[1];
        if (dist[u] + w < dist[v]) {
            dist[v] = dist[u] + w;
            minHeap.offer(new int[]{dist[v], v});
        }
    }
}
return dist[dst];
```

**Key insight 核心原理**

BFS for unit-weight graphs. For weighted graphs, the "distance" at each step is not uniform — use min-heap to always process the node with the current minimum known distance.
无权图用BFS。有权图中每步"距离"不均匀，用最小堆确保总是处理当前已知距离最小的节点。

**Variants 变形**

| Variant | Example |
|---|---|
| Shortest path (weighted) | LC 743, 1514 |
| K shortest paths | LC 787 (Bellman-Ford for negative) |
| Cheapest flights within K stops | LC 787 |

---

### Pattern 6 — Lazy Deletion 懒删除

**When:** you need to "delete" elements from a heap but can't do it directly.
**适用：** 需要从堆中删除元素但无法直接操作时。

```java
Set<Integer> toDelete = new HashSet<>();

// Instead of deleting, mark for lazy deletion
toDelete.add(val);

// When polling, skip marked elements
while (!heap.isEmpty() && toDelete.contains(heap.peek())) {
    toDelete.remove(heap.poll());
}
```

**Use case:** sliding window median (LC 480), interval scheduling with cancellations.

---

## 4. Advanced Skills 进阶技能

### Skill 1 — Custom Comparator 自定义比较器

```java
// Sort by first element ascending, then second descending
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) ->
    a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
```

**Warning:** never use `a[0] - b[0]` for values that could overflow. Use `Integer.compare(a[0], b[0])` for safety.

### Skill 2 — Heap vs Sorting

| Situation | Prefer |
|---|---|
| Need all K at once, static data | Sort — O(n log n) simpler |
| Data arrives dynamically, need running top-K | Heap — O(n log k) |
| Need Kth element only (not all K) | QuickSelect — O(n) avg |

### Skill 3 — Size K Heap Pattern

The size-k heap pattern appears in many problems. Always remember:
- **K largest** → min-heap size k (poll smallest to evict)
- **K smallest** → max-heap size k (poll largest to evict)

### Skill 4 — Heap for Scheduling Problems

Pattern: sort by start time + use heap to track end times of ongoing tasks.

```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);  // sort by start
PriorityQueue<Integer> heap = new PriorityQueue<>(); // track end times

for (int[] interval : intervals) {
    if (!heap.isEmpty() && heap.peek() <= interval[0])
        heap.poll();    // recycle a finished interval
    heap.offer(interval[1]);
}
return heap.size();  // number of concurrent intervals
```

---

## 5. Interview Script 面试话术

**English:**
> I'd use a [min/max]-heap because I need to repeatedly find the [smallest/largest] element from a dynamic set. A heap gives O(log n) insert and O(1) peek, so processing n elements is O(n log k) where k is the heap size. [For top-K: I maintain a min-heap of size k; when it exceeds k, I poll the smallest, which guarantees the heap always holds the k largest.]

**中文：**
> 我会用[最小/最大]堆，因为需要从动态集合中反复取[最小/最大]值。堆的插入是 O(log n)，取顶 O(1)，处理n个元素共 O(n log k)，k是堆大小。[前K大：用大小为k的最小堆，超过k时弹出最小值，堆中始终保留K个最大值。]

---

## 6. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Top-K | 215, 347, 973 |
| 2. K-way merge | 23, 632 |
| 3. Two heaps | 295, 480 |
| 4. Task scheduling | 621, 767, 502 |
| 5. Dijkstra | 743, 787, 1514 |
| 6. Lazy deletion | 480 |

**Recommended order:** 215 → 347 → 23 → 295 → 621 → 743 → 767

---

## 7. One-line Summary 一句话总结

```
Heap = O(1) access to min/max, O(log n) update; use when you need the "best" element repeatedly from a dynamic set.
堆 = O(1) 取极值，O(log n) 更新；反复从动态集合取最优元素时使用。
```
