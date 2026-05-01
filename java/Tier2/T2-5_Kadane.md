# T2-5 — Kadane's Algorithm 卡丹算法

> **Core idea:** Track the maximum subarray sum ending at the current index. At each step: either extend the previous subarray or start fresh. One-pass O(n).
> **核心思想：** 跟踪以当前位置结尾的最大子数组和。每步选择：延伸前一子数组或从当前重新开始。一次遍历O(n)。
>
> Complexity: O(n) time, O(1) space.
> Full reference: `DP/description.md` Pattern 1

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
    int curMax = nums[0], globalMax = nums[0];
    for (int i = 1; i < nums.length; i++) {
        curMax = Math.max(nums[i], curMax + nums[i]);  // extend or restart
        globalMax = Math.max(globalMax, curMax);
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
        curMax = Math.max(nums[i], Math.max(a, b));   // negative × negative = positive
        curMin = Math.min(nums[i], Math.min(a, b));
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
        globalMax = Math.max(globalMax, curMax);
        curMin = Math.min(curMin + num, num);
        globalMin = Math.min(globalMin, curMin);
        totalSum += num;
    }
    // If all negative: circular case = totalSum - globalMin would leave nothing
    return globalMax > 0 ? Math.max(globalMax, totalSum - globalMin) : globalMax;
}
```

### House Robber (LC 198) — non-adjacent
```java
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
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
