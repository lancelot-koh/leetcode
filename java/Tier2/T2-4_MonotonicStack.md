# T2-4 — Monotonic Stack 单调栈

> **Core idea:** A stack that maintains elements in sorted order. When an incoming element breaks the monotonic order, it batch-resolves (pops) all weaker elements — giving each element its "nearest stronger neighbor."
> **核心思想：** 栈始终保持有序。新元素破坏单调性时，批量弹出并解决所有"较弱"元素——每个元素得到其"最近更强邻居"。
>
> Complexity: O(n) — each element pushed and popped at most once.
> Full reference: `MonotonicStack/description.md`

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Next greater / next smaller element | LC 496, 739 |
| Previous greater / previous smaller | Used inside LC 84, 907 |
| Histogram area | LC 84 |
| Remove k digits (greedy) | LC 402 |
| Stock span / daily temperatures | LC 739 |

**Stack type selection:**
- **Decreasing stack** → finds **next GREATER** (new element larger = it's the answer for top)
- **Increasing stack** → finds **next SMALLER**

---

## Core Templates 核心模板

### Next Greater Element (decreasing stack)

```java
int[] res = new int[n];
Arrays.fill(res, -1);
Deque<Integer> stack = new ArrayDeque<>();   // stores INDICES

for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
        res[stack.pop()] = nums[i];          // nums[i] is the next greater
    }
    stack.push(i);
}
```

### Previous Greater (look at stack top BEFORE pushing)

```java
int[] prevGreater = new int[n];
Deque<Integer> stack = new ArrayDeque<>();

for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[stack.peek()] <= nums[i])
        stack.pop();                          // pop weaker elements
    prevGreater[i] = stack.isEmpty() ? -1 : stack.peek();  // top = left neighbor
    stack.push(i);
}
```

---

## Variants 变形

| Variant | Stack order | Pop when | Example |
|---|---|---|---|
| Next Greater | Decreasing | `nums[i] > nums[top]` | LC 496, 739 |
| Next Smaller | Increasing | `nums[i] < nums[top]` | custom |
| Previous Greater | Decreasing | peek before push | LC 84 left |
| Circular array | Any | `i % n`, run 2n iters | LC 503 |
| Histogram area | Increasing | find L+R boundary | LC 84 |
| Remove K digits | Increasing | `top > cur && removals > 0` | LC 402 |
| Sum of subarray mins | Increasing | contribution pattern | LC 907 |

---

## Key Examples 关键例题

### Daily Temperatures (LC 739)
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

### Largest Rectangle in Histogram (LC 84)
```java
public int largestRectangleArea(int[] heights) {
    int n = heights.length, max = 0;
    int[] left = new int[n], right = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) stack.pop();
        left[i] = stack.isEmpty() ? -1 : stack.peek();
        stack.push(i);
    }
    stack.clear();
    for (int i = n-1; i >= 0; i--) {
        while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) stack.pop();
        right[i] = stack.isEmpty() ? n : stack.peek();
        stack.push(i);
    }
    for (int i = 0; i < n; i++)
        max = Math.max(max, heights[i] * (right[i] - left[i] - 1));
    return max;
}
```

### Remove K Digits (LC 402) — Greedy + Monotonic Stack
```java
public String removeKdigits(String num, int k) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : num.toCharArray()) {
        while (k > 0 && !stack.isEmpty() && stack.peek() > c) {
            stack.pop(); k--;
        }
        stack.push(c);
    }
    while (k-- > 0) stack.pop();    // remove from top if k remaining

    StringBuilder sb = new StringBuilder();
    boolean leadingZero = true;
    for (char c : stack) {           // iterate bottom to top
        if (leadingZero && c == '0') continue;
        leadingZero = false;
        sb.append(c);
    }
    return sb.length() == 0 ? "0" : sb.toString();
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `while` not `if` | One element can batch-resolve many; `if` only resolves one |
| Store INDEX not value | Need index for: distance (`i - idx`), write-back, boundary width |
| Strict `>` vs `>=` | `>=` evicts equal elements — prevents double-counting in contribution problems |
| Circular: run 2n, use `i % n` | Only push in first n; second pass resolves pending stack entries |
| Justify the pop | "nums[i] > top" → nums[i] is the next greater for top; top needs no more searching |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 496 Next Greater Element I |
| Medium | LC 739 Daily Temperatures |
| Medium | LC 503 Next Greater Element II (circular) |
| Medium | LC 402 Remove K Digits |
| Hard | LC 84 Largest Rectangle in Histogram |
| Hard | LC 42 Trapping Rain Water |
| Hard | LC 907 Sum of Subarray Minimums |

**Order:** 496 → 739 → 503 → 402 → 84 → 42 → 907

---

## One-line Summary

```
Monotonic stack = keep elements in sorted order; a stronger incoming element batch-resolves all weaker ones.
单调栈 = 保持栈内有序；更强的新元素批量解决所有较弱的待定元素。
```
