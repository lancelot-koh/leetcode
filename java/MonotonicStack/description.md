# Monotonic Stack 单调栈

> **Core idea:** Maintain a stack whose elements are always in sorted order (increasing or decreasing). When the current element breaks the order, the elements it "beats" get their answer resolved.
> **核心思想：** 维护一个始终有序（单调递增或递减）的栈。当前元素破坏单调性时，被"击败"的元素获得答案。
>
> The stack stores **unsolved** elements. A stronger incoming element **batch-resolves** them.
> 栈中存放"还没找到答案"的元素；更强的新元素批量解决它们。
>
> Complexity: O(n) — each element is pushed and popped at most once.
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| next greater / next smaller | 下一个更大 / 更小 |
| previous greater / previous smaller | 左边第一个更大 / 更小 |
| nearest greater / smaller | 最近的更大 / 更小 |
| daily temperatures | 温度类问题 |
| histogram / rectangle | 柱状图 / 矩形 |
| remove K digits | 移除K位数字 |
| lexicographically smallest / largest | 字典序最小/最大 |
| span / influence range | 元素影响范围 |
| subarray min/max contribution | 子数组最小/最大值贡献 |

**Monotonic stack vs sorting:** sorting loses positional relationships. Monotonic stack finds the **nearest** neighbor in O(n) while preserving positions.
排序会丢失位置关系。单调栈在保留位置的同时，O(n) 找到"最近"的邻居。

---

## 2. Stack Type Selection 栈类型选择

| Want to find | Stack order | Pop condition |
|---|---|---|
| Next **Greater** (right) | Decreasing (底→顶递减) | `nums[i] > nums[stack.top()]` |
| Next **Smaller** (right) | Increasing (底→顶递增) | `nums[i] < nums[stack.top()]` |
| Previous **Greater** (left) | Decreasing | peek before push |
| Previous **Smaller** (left) | Increasing | peek before push |

**Memory tip:** "Decreasing stack finds next greater" — the element that breaks the decreasing order IS the next greater.
记忆口诀：递减栈找下一个更大——破坏递减顺序的元素就是答案。

---

## 3. Patterns 模式

---

### Pattern 1 — Next Greater Element 下一个更大元素

**When:** for each element, find the first element to its right that is strictly greater.
**适用：** 对每个元素找右边第一个更大的值。

**Template 模板**

```java
public int[] nextGreater(int[] nums) {
    int n = nums.length;
    int[] res = new int[n];
    Arrays.fill(res, -1);
    Deque<Integer> stack = new ArrayDeque<>();  // stores indices

    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
            res[stack.pop()] = nums[i];  // nums[i] is the answer for popped index
        }
        stack.push(i);
    }
    return res;
}
```

**Key insight 核心原理**

- Stack is decreasing (by value). When `nums[i]` is larger than the top, it is the "next greater" for that top.
- `while` not `if` — one incoming element can resolve many pending elements.
- 栈保持单调递减。新元素比栈顶大时，新元素就是栈顶的"下一个更大"。
- 用 `while` 而非 `if`：一个新元素可以批量解决多个待定元素。

**Why store index, not value 为什么存下标不存值**

Because you often need: distance (`i - idx`), write-back to result array, or boundary width.
因为通常需要：距离、回填结果数组、或计算区间宽度。

**Variants 变形**

| Variant | Change | Example |
|---|---|---|
| Next smaller | increasing stack, pop when `nums[i] < top` | custom |
| Days until warmer | store index, `res[idx] = i - idx` | LC 739 |
| Next greater in circular array | run `2n`, use `i % n` | LC 503 |

**Example: Daily Temperatures (LC 739)**

```java
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] answer = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()])
            answer[stack.peek()] = i - stack.pop();
        stack.push(i);
    }
    return answer;
}
```

---

### Pattern 2 — Previous Greater / Smaller 左边第一个更大/更小

**When:** for each element, find the nearest element to its **left** that satisfies a condition.
**适用：** 找左边最近的满足条件的元素。

**Template 模板 (previous greater)**

```java
int[] prevGreater = new int[n];
Deque<Integer> stack = new ArrayDeque<>();

for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[stack.peek()] <= nums[i])
        stack.pop();                                      // pop smaller elements
    prevGreater[i] = stack.isEmpty() ? -1 : stack.peek(); // top is left neighbor
    stack.push(i);
}
```

**Key insight 核心原理**

Pop elements that are weaker than current. What remains on top after popping is the previous stronger element.
弹出比当前元素弱的，弹完后栈顶就是左边第一个更强的。

**Combining left + right boundaries 结合左右边界**

Many range problems need both boundaries simultaneously:

```java
// Compute left boundary (previous smaller) and right boundary (next smaller)
// then: width = rightBound[i] - leftBound[i] - 1
```

---

### Pattern 3 — Circular Array 环形数组

**When:** the array is circular — elements wrap around.
**适用：** 数组是环形的（首尾相连）。

**Technique 技巧**

```java
for (int i = 0; i < 2 * n; i++) {
    int idx = i % n;
    // same monotonic stack logic
    // only write results for i < n
}
```

**Why 2n:** on the second pass (i ≥ n), we only use indices to resolve pending stack entries from the first pass — we never push again (or guard with `if i < n`).

**Example: Next Greater Element II (LC 503)**

```java
public int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] res = new int[n];
    Arrays.fill(res, -1);
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i < 2 * n; i++) {
        while (!stack.isEmpty() && nums[i % n] > nums[stack.peek()])
            res[stack.pop()] = nums[i % n];
        if (i < n) stack.push(i);
    }
    return res;
}
```

---

### Pattern 4 — Span / Range (Boundary Finding) 范围查找

**When:** each element has a "span" — how far left/right it dominates. Combine left and right boundaries to compute area or contribution.
**适用：** 每个元素有一个"支配范围"，结合左右边界计算面积或贡献值。

**Template 模板**

```java
// Find left boundary: nearest index where nums[leftBound] < nums[i]
// Find right boundary: nearest index where nums[rightBound] < nums[i]
// Width = rightBound[i] - leftBound[i] - 1
// Contribution = nums[i] * width
```

**Example: Largest Rectangle in Histogram (LC 84)**

```java
public int largestRectangleArea(int[] heights) {
    int n = heights.length;
    int[] left = new int[n], right = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    // Previous smaller (left boundary)
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) stack.pop();
        left[i] = stack.isEmpty() ? -1 : stack.peek();
        stack.push(i);
    }
    stack.clear();

    // Next smaller (right boundary)
    for (int i = n - 1; i >= 0; i--) {
        while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) stack.pop();
        right[i] = stack.isEmpty() ? n : stack.peek();
        stack.push(i);
    }

    int max = 0;
    for (int i = 0; i < n; i++)
        max = Math.max(max, heights[i] * (right[i] - left[i] - 1));
    return max;
}
```

**Variants 变形**

| Variant | Example |
|---|---|
| Largest rectangle in histogram | LC 84 |
| Maximal rectangle (matrix) | LC 85 (histogram on each row) |
| Trapping rain water | LC 42 |
| Stock span problem | custom |

---

### Pattern 5 — Contribution Pattern 贡献模型

**When:** compute the sum of min (or max) over all subarrays. Don't enumerate subarrays — count how many subarrays each element is the minimum of.
**适用：** 求所有子数组的最小值之和。不枚举子数组，而是统计每个元素作为最小值的贡献次数。

**Formula 公式**

```
contribution of nums[i] = nums[i] × (left count) × (right count)

leftCount  = i - leftSmaller[i]    (subarrays extending left where nums[i] is still min)
rightCount = rightSmaller[i] - i   (subarrays extending right where nums[i] is still min)
```

**Strict vs non-strict 严格 vs 非严格**

To avoid double-counting with equal elements: use **strict** `<` on one side, **non-strict** `<=` on the other.
- Left boundary: find previous **strictly smaller** (`<`)
- Right boundary: find next **smaller or equal** (`<=`)

**Example: Sum of Subarray Minimums (LC 907)**

```java
public int sumSubarrayMins(int[] arr) {
    int n = arr.length;
    long MOD = 1_000_000_007L;
    int[] left = new int[n], right = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) stack.pop();
        left[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
        stack.push(i);
    }
    stack.clear();
    for (int i = n - 1; i >= 0; i--) {
        while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) stack.pop();
        right[i] = stack.isEmpty() ? n - i : stack.peek() - i;
        stack.push(i);
    }

    long ans = 0;
    for (int i = 0; i < n; i++)
        ans = (ans + (long) arr[i] * left[i] * right[i]) % MOD;
    return (int) ans;
}
```

---

### Pattern 6 — Greedy + Monotonic Stack 贪心 + 单调栈

**When:** construct the lexicographically smallest/largest result by greedily removing elements.
**适用：** 贪心构造字典序最小/最大序列，按需弹出较差元素。

**Template 模板 (smallest)**

```java
Deque<Integer> stack = new ArrayDeque<>();
int removals = k;  // k elements to remove

for (int num : nums) {
    while (removals > 0 && !stack.isEmpty() && stack.peek() > num) {
        stack.pop();
        removals--;
    }
    stack.push(num);
}
// Remove from tail if removals still > 0
while (removals-- > 0) stack.pop();
```

**Key insight 核心原理**

If a later digit is smaller, the current larger digit hurts the result — remove it greedily (as long as we have remaining removals).
后面出现更小的数字时，前面更大的数字会让结果变大——贪心删掉（只要还有删除次数）。

**Variants 变形**

| Variant | Example |
|---|---|
| Remove K digits → smallest number | LC 402 |
| Create maximum number from two arrays | LC 321 |
| Largest number from array (comparator) | LC 179 |

---

### Pattern 7 — Monotonic Stack + Deque (Sliding Window Max) 单调栈 + 双端队列

See `SlidingWindow/description.md` — Pattern 7. The deque acts as a monotonic structure but also evicts elements outside the window.

---

## 4. Advanced Skills 进阶技能

### Skill 1 — `while` Not `if` 用 `while` 不用 `if`

One incoming element can batch-resolve multiple stack elements. Using `if` would only resolve one.
一个新元素可以批量解决多个待定元素，用 `if` 只解决一个。

```java
// WRONG — misses chained resolutions
if (!stack.isEmpty() && nums[i] > nums[stack.peek()]) stack.pop();

// CORRECT
while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) stack.pop();
```

### Skill 2 — Strict vs Non-strict 严格 vs 非严格

| Comparison | Effect |
|---|---|
| `>` (strict) | equal elements both stay; may double-count contributions |
| `>=` (non-strict) | equal elements evict each other; use asymmetrically to avoid double-count |

When computing contributions with duplicates: use `<` on left side and `<=` on right side (or vice versa) to count each subarray exactly once.

### Skill 3 — Scan Direction 扫描方向

- Left → right: natural for "next" problems (next greater/smaller)
- Right → left: sometimes cleaner for "previous" problems
- Both directions: needed for span/range patterns (left boundary + right boundary)

### Skill 4 — Boundary Initialization 边界初始化

| Scenario | Default boundary |
|---|---|
| No element to the left | `-1` (index) or `0` (count) |
| No element to the right | `n` (index) or `n - i` (count) |
| Area calculation | use sentinel value `0` in array |

### Skill 5 — Justify the Pop 解释弹出理由

Interviewers will ask: "Why can you pop that element?"

Answer template: "When `nums[i]` is [greater/smaller] than `nums[stack.top()]`, `nums[i]` is the [next greater/smaller] element for the top. The top will never need to look further right for its answer because `nums[i]` already satisfies the condition."

---

## 5. Interview Script 面试话术

**English:**
> I'd use a monotonic [decreasing/increasing] stack. The stack holds indices of elements whose answers haven't been found yet. When the current element breaks the monotonic order, it becomes the [next greater/smaller] answer for the elements being popped. Since each element is pushed and popped at most once, the overall time is O(n).

**中文：**
> 我会用单调[递减/递增]栈。栈中保存的是还没找到答案的元素下标。当当前元素破坏单调性时，它就成为被弹出元素的答案。每个元素最多进栈出栈各一次，整体时间复杂度是 O(n)。

---

## 6. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Next Greater | 496, 739 |
| 2. Previous Greater/Smaller | used in 84, 907 |
| 3. Circular Array | 503 |
| 4. Span / Range | 84, 85, 42 |
| 5. Contribution | 907 |
| 6. Greedy + Stack | 402, 316 |
| 7. With deque | 239 (see Sliding Window) |

**Recommended order 建议练习顺序:**
496 → 739 → 503 → 402 → 84 → 42 → 907

---

## 7. One-line Summary 一句话总结

```
Monotonic stack = store unsolved elements in sorted order;
                  a stronger element batch-resolves them when it arrives.

单调栈 = 有序存放"待解决"元素；更强的新元素到来时批量给出答案。
```

**Next steps 下一步:**
- Prefix Sum — for range sum and subarray counting problems 前缀和（区间和与子数组计数）
- Monotonic Deque — sliding window max/min (dynamic version of monotonic stack) 单调队列（滑动窗口最大/最小值）
