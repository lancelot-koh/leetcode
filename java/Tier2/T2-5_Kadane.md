# T2-5 — Kadane's Algorithm 卡丹算法

> **Core idea:** Track the maximum subarray sum ending at the current index. At each step: either extend the previous subarray or start fresh. One-pass O(n).
> **核心思想：** 跟踪以当前位置结尾的最大子数组和。每步选择：延伸前一子数组或从当前重新开始。一次遍历O(n)。
>
> Complexity: O(n) time, O(1) space.
> Full reference: `DP/description.md` Pattern 1

---

## How it Works — Mental Model 算法原理

Kadane's algorithm answers the question: "Is it worth dragging the previous subarray along, or should I start fresh here?" At each index `i`, `curMax` represents the maximum sum of any subarray that **ends at i**. If `curMax` was negative before adding `nums[i]`, the baggage of that previous subarray hurts more than it helps, so we drop it and start a new subarray from `i` alone. If `curMax` was positive, extending it adds value. The global maximum is tracked separately as we scan. This greedy local decision is globally optimal because the best subarray must end somewhere — and at that ending index, it must have been the best subarray ending there.

**Key invariant:** `curMax` is always the maximum sum of any subarray ending at the current index. `globalMax` is the maximum over all `curMax` values seen so far.

**Common mistake / gotcha:** Initializing `curMax` and `globalMax` to `0` instead of `nums[0]`. If all numbers are negative, the correct answer is the largest (least negative) single element — initializing to 0 would incorrectly return 0.

---

## Step-by-Step Trace 执行步骤示意

Example: `maxSubArray([-2, 1, -3, 4, -1, 2, 1, -5, 4])`
```
i=0: curMax=max(-2, 0+(-2))=-2,   globalMax=-2
i=1: curMax=max(1, -2+1)=1,       globalMax=1    ← restart (dropped the -2)
i=2: curMax=max(-3, 1+(-3))=-2,   globalMax=1
i=3: curMax=max(4, -2+4)=4,       globalMax=4    ← restart again
i=4: curMax=max(-1, 4+(-1))=3,    globalMax=4
i=5: curMax=max(2, 3+2)=5,        globalMax=5
i=6: curMax=max(1, 5+1)=6,        globalMax=6
i=7: curMax=max(-5, 6+(-5))=1,    globalMax=6
i=8: curMax=max(4, 1+4)=5,        globalMax=6
Result: 6  (subarray [4,-1,2,1])
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Maximum subarray sum | LC 53 |
| Maximum subarray product | LC 152 |
| Circular maximum subarray | LC 918 |
| Best time to buy stock | LC 121 (price diff array) |
| Maximum sum of non-adjacent elements | House Robber LC 198 |

**Signal:** "contiguous subarray", "maximum sum", "minimum sum" over a range with no data structure needed.

---

## Core Templates 核心模板

### Basic Kadane (max subarray sum)

```java
public int maxSubArray(int[] nums) {
    int curMax = nums[0], globalMax = nums[0];  // init to nums[0], NOT 0 (handles all-negative input)
    for (int i = 1; i < nums.length; i++) {
        // If curMax < 0, dragging it forward hurts; restart from nums[i] alone
        curMax = Math.max(nums[i], curMax + nums[i]);  // extend or restart
        globalMax = Math.max(globalMax, curMax);        // track best ending seen so far
    }
    return globalMax;
}
```

### With subarray indices (track start/end)

```java
int curMax = nums[0], globalMax = nums[0];
int start = 0, end = 0, tempStart = 0;

for (int i = 1; i < nums.length; i++) {
    if (nums[i] > curMax + nums[i]) {
        curMax = nums[i];
        tempStart = i;          // restart: new start
    } else {
        curMax += nums[i];
    }
    if (curMax > globalMax) {
        globalMax = curMax;
        start = tempStart;
        end = i;
    }
}
// nums[start..end] is the max subarray
```

### Max subarray PRODUCT (track min too)

```java
public int maxProduct(int[] nums) {
    int curMax = nums[0], curMin = nums[0], globalMax = nums[0];
    for (int i = 1; i < nums.length; i++) {
        int a = curMax * nums[i], b = curMin * nums[i];
        // A large negative curMin × negative nums[i] can flip to a large positive
        curMax = Math.max(nums[i], Math.max(a, b));   // negative × negative = positive
        curMin = Math.min(nums[i], Math.min(a, b));   // track min for future negative multiplications
        globalMax = Math.max(globalMax, curMax);
    }
    return globalMax;
}
```

---

## Variants 变形

| Variant | Key change | Example |
|---|---|---|
| Max subarray sum | Basic Kadane | LC 53 |
| Max product subarray | Track both curMax and curMin | LC 152 |
| Circular max subarray | `max(kadane, total - minKadane)` | LC 918 |
| Min subarray sum | Flip sign or track curMin | custom |
| Max sum of non-adjacent | DP: `dp[i] = max(dp[i-1], dp[i-2] + nums[i])` | LC 198 |
| Best time to buy stock | Run Kadane on price differences | LC 121 |

---

## Key Examples 关键例题

### Maximum Subarray (LC 53)
```java
public int maxSubArray(int[] nums) {
    int curMax = nums[0], globalMax = nums[0];
    for (int i = 1; i < nums.length; i++) {
        curMax = Math.max(nums[i], curMax + nums[i]);
        globalMax = Math.max(globalMax, curMax);
    }
    return globalMax;
}
```

### Maximum Product Subarray (LC 152)
```java
public int maxProduct(int[] nums) {
    int curMax = nums[0], curMin = nums[0], res = nums[0];
    for (int i = 1; i < nums.length; i++) {
        int a = curMax * nums[i], b = curMin * nums[i];
        curMax = Math.max(nums[i], Math.max(a, b));
        curMin = Math.min(nums[i], Math.min(a, b));
        res = Math.max(res, curMax);
    }
    return res;
}
```

### Maximum Sum Circular Subarray (LC 918)
```java
public int maxSubarraySumCircular(int[] nums) {
    int totalSum = 0;
    int curMax = 0, globalMax = Integer.MIN_VALUE;
    int curMin = 0, globalMin = Integer.MAX_VALUE;

    for (int num : nums) {
        curMax = Math.max(curMax + num, num);
        globalMax = Math.max(globalMax, curMax);  // standard Kadane for non-wrapping case
        curMin = Math.min(curMin + num, num);
        globalMin = Math.min(globalMin, curMin);  // Kadane for min subarray (the "excluded middle")
        totalSum += num;
    }
    // Circular subarray wraps around = total − (minimum middle subarray)
    // If all negative: globalMax is the answer (globalMin==totalSum would leave empty array)
    return globalMax > 0 ? Math.max(globalMax, totalSum - globalMin) : globalMax;
}
```

### House Robber (LC 198) — non-adjacent
```java
public int rob(int[] nums) {
    if (nums.length == 1) { return nums[0]; }
    int prev2 = nums[0], prev1 = Math.max(nums[0], nums[1]);
    for (int i = 2; i < nums.length; i++) {
        int cur = Math.max(prev1, prev2 + nums[i]);
        prev2 = prev1;
        prev1 = cur;
    }
    return prev1;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Restart condition | `curMax = max(nums[i], curMax + nums[i])` — restart when previous sum is negative |
| Product: track curMin | A large negative × negative = positive; must track both |
| Circular: total − minKadane | The circular max subarray = total sum minus the minimum middle subarray |
| Edge case: all negative | `globalMax` initialized to `nums[0]` handles this (returns the least negative) |
| Non-adjacent = 1D DP | Not Kadane; use `dp[i] = max(dp[i-1], dp[i-2] + nums[i])` |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 53 Maximum Subarray |
| Medium | LC 152 Maximum Product Subarray |
| Medium | LC 918 Maximum Sum Circular Subarray |
| Medium | LC 198 House Robber |
| Medium | LC 213 House Robber II (circular array) |

**Order:** 53 → 152 → 198 → 213 → 918

---

## One-line Summary

```
Kadane = at each index, max(start fresh, extend previous); one-pass O(n) max subarray.
卡丹 = 每个位置取max(重新开始, 延伸前一子数组)；一次O(n)求最大子数组。
```
