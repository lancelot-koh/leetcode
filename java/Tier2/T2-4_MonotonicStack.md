# T2-4 — Monotonic Stack 单调栈

> **Core idea:** A stack that maintains elements in sorted order. When an incoming element breaks the monotonic order, it batch-resolves (pops) all weaker elements — giving each element its "nearest stronger neighbor."
> **核心思想：** 栈始终保持有序。新元素破坏单调性时，批量弹出并解决所有"较弱"元素——每个元素得到其"最近更强邻居"。
>
> Complexity: O(n) — each element pushed and popped at most once.
> Full reference: `MonotonicStack/description.md`

---

## How it Works — Mental Model 算法原理

A monotonic stack keeps elements in sorted order (all increasing or all decreasing from bottom to top). The insight is: when element `nums[i]` arrives and is **stronger** than the top of the stack, the top's search for its "nearest stronger neighbor" is over — `nums[i]` is the answer for everything it beats. We pop all those resolved elements, record their answers, then push `nums[i]`. Elements that remain on the stack when the loop ends never found a stronger neighbor (their answer is -1 or a sentinel). Because each element is pushed once and popped at most once, the total work is O(n) regardless of how many pops occur.

**Key invariant:** The stack is always monotonically ordered from bottom to top (e.g., decreasing for "next greater"). Any element on the stack is still "waiting" for its next greater element; it has not been resolved yet.

**Common mistake / gotcha:** Using `if` instead of `while` for the pop condition. One incoming element can be the next-greater for multiple pending stack elements. `if` resolves only one; `while` resolves all of them correctly.

---

## Step-by-Step Trace 执行步骤示意

Example: `dailyTemperatures([73, 74, 75, 71, 69, 72, 76, 73])`
```
i=0: stack=[], push 0        → stack=[0]
i=1: 74>73(top) → pop 0, answer[0]=1-0=1; push 1  → stack=[1]
i=2: 75>74(top) → pop 1, answer[1]=2-1=1; push 2  → stack=[2]
i=3: 71<75, push 3           → stack=[2,3]
i=4: 69<71, push 4           → stack=[2,3,4]
i=5: 72>69 → pop 4, answer[4]=5-4=1; 72>71 → pop 3, answer[3]=5-3=2; 72<75, push 5 → stack=[2,5]
i=6: 76>72 → pop 5, answer[5]=6-5=1; 76>75 → pop 2, answer[2]=6-2=4; push 6 → stack=[6]
i=7: 73<76, push 7           → stack=[6,7]  (indices 6,7 remain → answer stays 0)
Result: [1,1,4,2,1,1,0,0]
```

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
Arrays.fill(res, -1);                         // default: no next greater found
Deque<Integer> stack = new ArrayDeque<>();     // stores INDICES (not values) so we can write back

for (int i = 0; i < n; i++) {
    // nums[i] is stronger than everything it beats — batch-resolve them now
    while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
        res[stack.pop()] = nums[i];            // nums[i] is the next greater for the popped index
    }
    stack.push(i);   // push current index; it waits for its own next-greater
}
```

### Previous Greater (look at stack top BEFORE pushing)

```java
int[] prevGreater = new int[n];
Deque<Integer> stack = new ArrayDeque<>();

for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
        stack.pop();   
    }                         // pop weaker elements
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
    int[] answer = new int[n];   // default 0 means "never a warmer day"
    Deque<Integer> stack = new ArrayDeque<>();  // decreasing stack of indices
    for (int i = 0; i < n; i++) {
        // Current day is warmer → resolve all pending colder days at once
        while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            answer[stack.peek()] = i - stack.pop();  // distance = current index minus waiting index
        } 
        stack.push(i);  // today waits for its own warmer future day
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
        while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
            stack.pop();
        }
        left[i] = stack.isEmpty() ? -1 : stack.peek();
        stack.push(i);
    }
    stack.clear();
    
    for (int i = n-1; i >= 0; i--) {
        while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
            stack.pop();
        }
        right[i] = stack.isEmpty() ? n : stack.peek();
        stack.push(i);
    }
    for (int i = 0; i < n; i++) {
        max = Math.max(max, heights[i] * (right[i] - left[i] - 1));
    }
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
    while (k-- > 0) { stack.pop(); }    // remove from top if k remaining

    StringBuilder sb = new StringBuilder();
    boolean leadingZero = true;
    for (char c : stack) {           // iterate bottom to top
        if (leadingZero && c == '0') { continue; }
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
