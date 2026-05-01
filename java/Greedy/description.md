# Greedy 贪心

> **Core idea:** At each step, make the locally optimal choice. If the greedy choice property holds, local optima compose into a global optimum.
> **核心思想：** 每步做局部最优选择。若贪心选择性质成立，局部最优积累成全局最优。
>
> Greedy is faster than DP but requires proof that the greedy choice is safe.
> 贪心比DP快，但需要证明贪心选择不会错过全局最优。
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| minimum cost / maximum profit | 最小代价 / 最大收益 |
| interval scheduling / activity selection | 区间调度 / 活动选择 |
| always pick the best available | 总是选当前最好的 |
| sort then choose | 排序后选择 |
| task assignment | 任务分配 |
| jump game | 跳跃游戏 |

**Greedy vs DP — how to decide 如何判断**

Ask: "If I make the greedy choice now, does it ever force a worse outcome later?" If you can prove it never does (exchange argument), use greedy. If you can construct a counterexample, use DP.

| Greedy works | DP needed |
|---|---|
| Interval scheduling (sort by end time) | 0/1 Knapsack |
| Fractional knapsack | 0/1 Knapsack (can't split) |
| Coin change with standard coins | Coin change with arbitrary coins |
| Huffman encoding | General string problems |

**Exchange argument proof 交换论证**

Suppose the greedy makes choice A instead of B. Show that swapping B in for A in any optimal solution doesn't make it worse. Therefore the greedy solution is at least as good as optimal.

---

## 2. Quick Decision Guide 快速判断

```
Select maximum non-overlapping intervals?         → Pattern 1: Interval scheduling
Minimize total cost of merging?                   → Pattern 2: Minimum cost greedy
Can you reach the end? / Min jumps?               → Pattern 3: Jump game
Assign tasks to minimize wait / max throughput?   → Pattern 4: Task scheduling
Construct largest/smallest number?                → Pattern 5: Comparator greedy
Rebuild sequence with min changes?                → Pattern 6: Greedy + two pointers
```

---

## 3. Patterns 模式

---

### Pattern 1 — Interval Scheduling 区间调度

**When:** select the maximum number of non-overlapping intervals.
**适用：** 选择最多互不重叠的区间数量。

**Key insight 核心原理**

Sort by **end time**. Always pick the interval that ends earliest — it leaves the most room for future intervals.
按**结束时间**排序。总是选结束最早的区间——给未来留最多空间。

**Template 模板**

```java
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);  // sort by end time

int count = 0, end = Integer.MIN_VALUE;
for (int[] interval : intervals) {
    if (interval[0] >= end) {    // no overlap with last chosen
        count++;
        end = interval[1];
    }
}
return count;
```

**Variants 变形**

| Variant | Greedy rule | Example |
|---|---|---|
| Max non-overlapping intervals | sort by end, pick earliest ending | LC 435 (min removals = n - max) |
| Meeting rooms (can one person attend all?) | sort by start, check overlap | LC 252 |
| Min platforms / meeting rooms II | sweep line or sort both | LC 253 |
| Merge overlapping intervals | sort by start, merge greedily | LC 56 |

**Example: Non-overlapping Intervals (LC 435)**

```java
public int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
    int keep = 0, end = Integer.MIN_VALUE;
    for (int[] iv : intervals)
        if (iv[0] >= end) { keep++; end = iv[1]; }
    return intervals.length - keep;
}
```

---

### Pattern 2 — Minimum Cost Greedy 最小代价贪心

**When:** combine elements with minimum total cost; always merge the cheapest pair first.
**适用：** 合并元素，每次优先合并代价最小的一对。

**Key insight:** use a min-heap to always access the smallest elements in O(log n).
用最小堆总能在 O(log n) 内取到当前最小元素。

**Example: Connect Ropes with Minimum Cost**

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>(ropes);
int totalCost = 0;
while (minHeap.size() > 1) {
    int cost = minHeap.poll() + minHeap.poll();
    totalCost += cost;
    minHeap.offer(cost);
}
return totalCost;
```

**Variants 变形**

| Variant | Example |
|---|---|
| Connect ropes (min cost) | Huffman-like |
| Task scheduler (min time) | LC 621 |
| K closest points to origin | min-heap |

---

### Pattern 3 — Jump Game 跳跃游戏

**When:** from each position you can jump up to some range; find if/how you can reach the end.
**适用：** 每个位置能跳一定距离，判断能否/最少几步到达终点。

**Key insight 核心原理**

Track the **maximum reachable index** at each step. If current index > max reachable, stuck.
追踪当前能到达的**最远下标**。若当前位置超出最远可达，则无法继续。

**Template 模板**

```java
// Can reach? (LC 55)
int maxReach = 0;
for (int i = 0; i < nums.length; i++) {
    if (i > maxReach) return false;
    maxReach = Math.max(maxReach, i + nums[i]);
}
return true;

// Min jumps (LC 45)
int jumps = 0, curEnd = 0, farthest = 0;
for (int i = 0; i < nums.length - 1; i++) {
    farthest = Math.max(farthest, i + nums[i]);
    if (i == curEnd) {       // must jump here — end of current jump range
        jumps++;
        curEnd = farthest;
    }
}
return jumps;
```

---

### Pattern 4 — Task Scheduling 任务调度

**When:** assign tasks to minimize total time with cooldown constraints.
**适用：** 在冷却约束下分配任务最小化总耗时。

**Key insight (LC 621):**

The task that appears most frequently determines the minimum time. Fill idle slots with other tasks.

```java
public int leastInterval(char[] tasks, int n) {
    int[] freq = new int[26];
    for (char c : tasks) freq[c - 'A']++;
    Arrays.sort(freq);
    int maxFreq = freq[25];
    int idleSlots = (maxFreq - 1) * n;
    for (int i = 24; i >= 0 && freq[i] > 0; i--)
        idleSlots -= Math.min(freq[i], maxFreq - 1);
    return tasks.length + Math.max(0, idleSlots);
}
```

---

### Pattern 5 — Comparator Greedy 比较器贪心

**When:** the greedy order isn't natural (not simply ascending/descending) — define a custom comparator.
**适用：** 贪心排序不是简单升降序，需要自定义比较器。

**Example: Largest Number (LC 179)**

```java
// Greedy: for two numbers a and b, prefer a before b if (ab > ba) lexicographically
Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));
```

**Example: Meeting rooms with weighted priority**

```java
Arrays.sort(tasks, (a, b) -> a.deadline != b.deadline
    ? a.deadline - b.deadline
    : a.profit - b.profit);
```

---

### Pattern 6 — Greedy + Two Pointers 贪心 + 双指针

Covered in `TwoPointer/2PointersPattern.md` Pattern 8. Key examples: Container with Most Water, Trapping Rain Water.

---

## 4. Advanced Skills 进阶技能

### Skill 1 — Always Sort First 先排序

Most greedy problems require sorting. The hard part is knowing what to sort **by**:

| Problem | Sort by |
|---|---|
| Interval scheduling | End time (greedy leaves most room) |
| Interval merging | Start time (detect overlaps easily) |
| Largest number | Custom: `b+a` vs `a+b` string comparison |
| Job scheduling | Deadline, then profit |

### Skill 2 — Prove the Exchange Argument 证明交换论证

In an interview, sketch the proof:
1. Assume an optimal solution differs from greedy at position i
2. Show swapping the optimal choice with the greedy choice doesn't worsen the solution
3. Conclude greedy is at least as good as optimal

### Skill 3 — Greedy with Heap 贪心 + 堆

When the "best choice" at each step is dynamic (changes as you process elements), combine greedy with a min/max heap:

```java
PriorityQueue<Integer> heap = new PriorityQueue<>();
for (int item : items) {
    heap.offer(item);
    if (heap.size() > k) heap.poll();   // greedy: keep only best k
}
```

### Skill 4 — Spot When Greedy Fails 识别贪心失效

Greedy fails when:
- Choosing the best **now** prevents a better choice **later** (e.g., 0/1 knapsack)
- The value of a choice depends on future context

When in doubt, try a small counterexample. If one exists, switch to DP.

---

## 5. Interview Script 面试话术

**English:**
> I'd use greedy because [sort by end time / always pick the locally best option]. The key insight is that [greedy choice explanation]. By the exchange argument: if we swap the greedy choice with any other, the result can only be equal or worse. This gives O(n log n) due to the initial sort.

**中文：**
> 我会用贪心，因为[按结束时间排序/每次选当前最优]。关键洞察是[贪心选择解释]。通过交换论证：把贪心选择替换成其他任何选择，结果只会相同或更差。初始排序后总体 O(n log n)。

---

## 6. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Interval scheduling | 252, 253, 435, 56 |
| 2. Min cost greedy | 621 |
| 3. Jump game | 55, 45 |
| 4. Task scheduling | 621, 1235 |
| 5. Comparator greedy | 179, 406 |
| 6. Greedy + two pointers | 11, 42 |

**Recommended order:** 55 → 45 → 435 → 56 → 252 → 253 → 621 → 179

---

## 7. One-line Summary 一句话总结

```
Greedy = at each step, make the locally optimal choice, and trust it leads to global optimum.
贪心 = 每步做局部最优选择，并证明它必然导向全局最优。
```
