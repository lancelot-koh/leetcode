# T3-7 — Monotonic Deque (Sliding Window Maximum) 单调双端队列

> **Core idea:** A deque that maintains indices in decreasing order of their values. When the window slides, evict indices outside the window from the front; evict smaller elements from the back before adding a new index. Front always holds the window maximum.
> **核心思想：** 双端队列按值降序维护索引。窗口滑动时，从头部移除越界索引；加入新索引前从尾部移除较小值。队头始终是窗口最大值。
>
> Complexity: O(n) — each element pushed and popped at most once.
> Full reference: `MonotonicStack/description.md` Pattern 7

---

## How It Works — Mental Model 理解模型

The key insight is that a smaller element entering the window from the right can never be the maximum while a larger element in the same window is still present. So we "pre-evict" any element from the back of the deque that is smaller than the new arrival — it is permanently dominated and will never be useful. This maintains the deque in strictly decreasing order. The front of the deque is always the current maximum, readable in O(1). When the window slides forward, any element whose index is now out of bounds is evicted from the front. Because every element is pushed and popped at most once, the total work across all windows is O(n), not O(nk).

**Key invariant:** At every step after adding index `i`, the deque contains indices of a decreasing subsequence of values; indices outside the current window have already been removed; and the front index always points to the maximum element of the current window.

**Common mistake:** Storing values instead of indices in the deque. You need the index to check whether the front element has fallen outside the window (`deque.peekFirst() <= i - k`). If you store values you cannot perform this eviction and the algorithm breaks silently.

---

## Step-by-Step Trace 逐步追踪

```
nums = [3, 1, 3, 5, 2],  k = 3

i=0, val=3: deque=[] → push 0.         deque=[0]  (values: [3])
i=1, val=1: 1<3, no back eviction.     deque=[0,1] (values: [3,1])
i=2, val=3: 3>=1 evict 1; 3>=3 evict 0; push 2.
            deque=[2] (values: [3])   window full → result[0] = nums[2] = 3

i=3, val=5: 5>=3 evict 2; push 3.      deque=[3] (values: [5])
            front 3 in window [1..3] → result[1] = nums[3] = 5

i=4, val=2: 2<5, no back eviction.     deque=[3,4] (values: [5,2])
            front 3 in window [2..4] → result[2] = nums[3] = 5

Output: [3, 5, 5]
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Maximum (or minimum) in every sliding window of size k | LC 239 |
| Constrained sliding window needing range maximum | LC 1438, 2398 |
| Jump game with sliding window | LC 1696 |
| Shortest subarray with sum ≥ k (deque on prefix sums) | LC 862 |
| Largest rectangle optimization | advanced |

**Signal:** fixed or variable window where you need the max/min of the window at each step.

**Deque vs Max-Heap:**
| Approach | Time | When |
|---|---|---|
| Monotonic Deque | O(n) | Sliding window max; eviction by index |
| Max-Heap (lazy delete) | O(n log n) | When elements can be removed out of order |

---

## Core Templates 核心模板

### Sliding Window Maximum (decreasing deque)

```java
public int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] result = new int[n - k + 1];
    Deque<Integer> deque = new ArrayDeque<>();  // stores INDICES (not values) — needed for eviction check

    for (int i = 0; i < n; i++) {
        // Step 1: evict the front if it has slid out of the current window [i-k+1, i]
        while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
            deque.pollFirst();
        }

        // Step 2: evict smaller elements from the back — they are dominated by nums[i]
        // and will never be the maximum while nums[i] is still in the window
        while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
            deque.pollLast();
        }

        deque.offerLast(i);  // push current index; deque remains decreasing in value

        // Step 3: window is full once i reaches k-1; front index = index of window max
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.peekFirst()];
        }
    }
    return result;
}
```

### Sliding Window Minimum (increasing deque)

```java
// Same structure, but flip the comparison: evict when back is >= new element
// This keeps the deque in INCREASING order so the front holds the minimum
while (!deque.isEmpty() && nums[deque.peekLast()] >= nums[i]) {
    deque.pollLast();
}
// Front holds the minimum of the current window
```

### Variable window with deque (constrained max)

```java
// LC 1438: longest subarray where max - min <= limit
Deque<Integer> maxQ = new ArrayDeque<>();   // decreasing (front = max)
Deque<Integer> minQ = new ArrayDeque<>();   // increasing (front = min)
int left = 0, res = 0;

for (int right = 0; right < nums.length; right++) {
    while (!maxQ.isEmpty() && nums[maxQ.peekLast()] <= nums[right]) { maxQ.pollLast(); }
    while (!minQ.isEmpty() && nums[minQ.peekLast()] >= nums[right]) { minQ.pollLast(); }
    maxQ.offerLast(right);
    minQ.offerLast(right);

    while (nums[maxQ.peekFirst()] - nums[minQ.peekFirst()] > limit) {
        left++;
        if (maxQ.peekFirst() < left) { maxQ.pollFirst(); }
        if (minQ.peekFirst() < left) { minQ.pollFirst(); }
    }
    res = Math.max(res, right - left + 1);
}
return res;
```

---

## Variants 变形

| Variant | Deque order | Example |
|---|---|---|
| Sliding window max | Decreasing (front = max) | LC 239 |
| Sliding window min | Increasing (front = min) | custom |
| Window with max-min constraint | Two deques (one each) | LC 1438 |
| Shortest subarray sum ≥ k | Deque on prefix sums (increasing) | LC 862 |
| Jump game with deque | Max reachable in window | LC 1696 |

---

## Key Examples 关键例题

### Sliding Window Maximum (LC 239)
```java
public int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] res = new int[n - k + 1];
    Deque<Integer> dq = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
        while (!dq.isEmpty() && dq.peekFirst() <= i - k) { dq.pollFirst(); }
        while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) { dq.pollLast(); }
        dq.offerLast(i);
        if (i >= k - 1) { res[i - k + 1] = nums[dq.peekFirst()]; }
    }
    return res;
}
```

### Longest Subarray with Absolute Diff ≤ Limit (LC 1438)
```java
public int longestSubarray(int[] nums, int limit) {
    Deque<Integer> maxQ = new ArrayDeque<>(), minQ = new ArrayDeque<>();
    int left = 0, res = 0;
    for (int right = 0; right < nums.length; right++) {
        while (!maxQ.isEmpty() && nums[maxQ.peekLast()] <= nums[right]) { maxQ.pollLast(); }
        while (!minQ.isEmpty() && nums[minQ.peekLast()] >= nums[right]) { minQ.pollLast(); }
        maxQ.offerLast(right);
        minQ.offerLast(right);
        while (nums[maxQ.peekFirst()] - nums[minQ.peekFirst()] > limit) {
            if (maxQ.peekFirst() == left) { maxQ.pollFirst(); }
            if (minQ.peekFirst() == left) { minQ.pollFirst(); }
            left++;
        }
        res = Math.max(res, right - left + 1);
    }
    return res;
}
```

### Shortest Subarray with Sum ≥ K (LC 862) — deque on prefix sums
```java
public int shortestSubarray(int[] nums, int k) {
    int n = nums.length;
    long[] prefix = new long[n + 1];
    for (int i = 0; i < n; i++) { prefix[i+1] = prefix[i] + nums[i]; }

    Deque<Integer> dq = new ArrayDeque<>();   // indices into prefix, increasing values
    int res = Integer.MAX_VALUE;

    for (int i = 0; i <= n; i++) {
        // Try to form valid subarrays: prefix[i] - prefix[dq.front] >= k
        while (!dq.isEmpty() && prefix[i] - prefix[dq.peekFirst()] >= k) {
            res = Math.min(res, i - dq.pollFirst());
        }
        // Maintain increasing prefix sums in deque
        while (!dq.isEmpty() && prefix[dq.peekLast()] >= prefix[i]) {
            dq.pollLast();
        }
        dq.offerLast(i);
    }
    return res == Integer.MAX_VALUE ? -1 : res;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Store INDICES not values | Need index to check if element has left the window |
| Front eviction: `<= i - k` | Strict less-than would keep elements exactly at boundary; use `<=` |
| Back eviction with `<=` vs `<` | `<=` removes equal elements (strict max); `<` keeps duplicates |
| Two deques for range constraint | One for max, one for min; shrink window when `max - min > limit` |
| Prefix sum deque: increasing | For LC 862 we want smallest prefix[left] to maximize the difference |
| `peekFirst()` on empty deque | Guard with `!dq.isEmpty()` before every peek/poll |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Hard | LC 239 Sliding Window Maximum |
| Medium | LC 1438 Longest Subarray with Absolute Diff ≤ Limit |
| Hard | LC 862 Shortest Subarray with Sum at Least K |
| Medium | LC 1696 Jump Game VI |
| Hard | LC 2398 Maximum Number of Robots Within Budget |

**Order:** 239 → 1438 → 1696 → 862 → 2398

---

## One-line Summary

```
Monotonic deque = evict out-of-window from front, evict weaker elements from back; front is always the window max/min in O(1).
单调双端队列 = 从头移除越界索引，从尾移除较弱元素；队头始终是O(1)的窗口最大/最小值。
```
