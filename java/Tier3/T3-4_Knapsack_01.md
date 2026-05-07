# T3-4 — 0/1 Knapsack 0/1背包

> **Core idea:** For each item, decide to include or exclude. State: `dp[i][w]` = max value using first `i` items with capacity `w`. Space-optimize to 1D by iterating weight RIGHT-TO-LEFT (prevents reuse of same item).
> **核心思想：** 每件物品选或不选。状态：`dp[i][w]`=前i件物品在容量w下的最大价值。逆向遍历重量压缩到1D（防止同件物品重复选取）。
>
> Complexity: O(n × W) time, O(W) space with rolling array.
> Full reference: `DP/description.md` Pattern 2

---

## How It Works — Mental Model 理解模型

The 0/1 knapsack builds an answer item-by-item. For each new item you face a binary decision: skip it (copy the best result from the row above) or take it (add its value to the best result achievable with the remaining capacity). The recurrence `dp[i][w] = max(dp[i-1][w], dp[i-1][w-wt] + val)` captures exactly this choice. When we compress to 1D the same logic holds, but the update for item `i` must read from the "previous item" values — that's why we iterate capacity from right to left. If we iterated left to right, `dp[w-wt]` would already reflect item `i` being used, accidentally allowing the same item to be taken multiple times (turning it into an unbounded knapsack).

**Key invariant:** After processing item `i`, `dp[w]` holds the maximum value achievable using only items `0..i` with total weight at most `w`. The right-to-left sweep ensures this invariant is never violated by an item counting itself twice.

**Common mistake:** Using left-to-right iteration in the 1D loop when you intend 0/1 knapsack. It compiles and runs without error, silently producing the unbounded knapsack answer. Always ask: "should each item be usable only once?" — if yes, sweep right to left.

---

## Step-by-Step Trace 逐步追踪

```
Items: weight=[2,3], value=[3,4]   Capacity W=4

Initial dp: [0, 0, 0, 0, 0]   (indices 0..4)

Process item 0 (wt=2, val=3), sweep w=4..2:
  w=4: dp[4] = max(dp[4], dp[4-2]+3) = max(0, 0+3) = 3
  w=3: dp[3] = max(dp[3], dp[3-2]+3) = max(0, 0+3) = 3
  w=2: dp[2] = max(dp[2], dp[2-2]+3) = max(0, 0+3) = 3
dp: [0, 0, 3, 3, 3]

Process item 1 (wt=3, val=4), sweep w=4..3:
  w=4: dp[4] = max(dp[4], dp[4-3]+4) = max(3, 0+4) = 4   ← dp[1] still "before item 1"
  w=3: dp[3] = max(dp[3], dp[3-3]+4) = max(3, 0+4) = 4
dp: [0, 0, 3, 4, 4]

Result: dp[4] = 4   (take item 1 alone, weight 3, value 4)
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Select subset with weight limit | Classic Knapsack |
| Partition array into two equal-sum halves | LC 416 |
| Target sum with +/- assignment | LC 494 |
| Last stone weight II | LC 1049 |
| Count subsets with given sum | LC 698 variant |

**Signal:** "choose some items", "subset sum", "partition into two groups with equal sum", "exactly k items."

**0/1 vs Unbounded vs Bounded:**
| Type | Item reuse | Weight traversal |
|---|---|---|
| 0/1 Knapsack | Each item once | RIGHT-TO-LEFT (inner loop) |
| Unbounded Knapsack | Unlimited reuse | LEFT-TO-RIGHT (inner loop) |
| Bounded Knapsack | At most k_i times | Binary grouping or monotone deque |

---

## Core Templates 核心模板

### 2D DP (clearer logic)

```java
// dp[i][w] = max value achievable using first i items with capacity exactly w
int[][] dp = new int[n+1][W+1];

for (int i = 1; i <= n; i++) {
    int wt = weight[i-1], val = value[i-1];
    for (int w = 0; w <= W; w++) {
        dp[i][w] = dp[i-1][w];                                    // choice 1: skip item i
        if (w >= wt) {
            // choice 2: take item i — must read from previous row (item i used only once)
            dp[i][w] = Math.max(dp[i][w], dp[i-1][w-wt] + val);
        }
    }
}
return dp[n][W];
```

### 1D space-optimized (RIGHT-TO-LEFT is critical)

```java
int[] dp = new int[W + 1];  // dp[w] = max value with capacity w

for (int i = 0; i < n; i++) {
    int wt = weight[i], val = value[i];
    // RIGHT-TO-LEFT: when we compute dp[w], dp[w-wt] still holds the pre-item-i value,
    // ensuring item i contributes at most once. Left-to-right would let item i be reused.
    for (int w = W; w >= wt; w--) {
        dp[w] = Math.max(dp[w], dp[w - wt] + val);
    }
}
return dp[W];
```

### Boolean variant (can we reach exactly target?)

```java
boolean[] dp = new boolean[target + 1];
dp[0] = true;   // base case: empty subset always achieves sum 0

for (int num : nums) {
    // Right-to-left: dp[w - num] is the "before current num" state,
    // so each number is either included once or not at all
    for (int w = target; w >= num; w--) {
        dp[w] |= dp[w - num];  // can we reach w by adding num to a previously reachable sum?
    }
}
return dp[target];
```

---

## Variants 变形

| Variant | Change | Example |
|---|---|---|
| Max value (standard) | `dp[w] = max(dp[w], dp[w-wt]+val)` | Classic |
| Can-reach (boolean) | `dp[w] \|= dp[w-num]` | LC 416 |
| Count ways | `dp[w] += dp[w-num]` | LC 494 (after transform) |
| Partition equal subset | `target = sum/2`, boolean reach | LC 416 |
| Target sum +/- | Count subsets with sum = `(total + target) / 2` | LC 494 |
| Last stone weight II | Minimize `|S1 - S2|` = minimize `sum - 2*S1` | LC 1049 |

---

## Key Examples 关键例题

### Partition Equal Subset Sum (LC 416)
```java
public boolean canPartition(int[] nums) {
    int sum = 0;
    for (int n : nums) { sum += n; }
    if (sum % 2 != 0) { return false; }
    int target = sum / 2;

    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    for (int num : nums) {
        for (int w = target; w >= num; w--) {  // right-to-left
            dp[w] |= dp[w - num];
        }
    }
    return dp[target];
}
```

### Target Sum (LC 494) — count assignments
```java
public int findTargetSumWays(int[] nums, int target) {
    int sum = 0;
    for (int n : nums) { sum += n; }
    // P = subset with +, N = subset with -
    // P - N = target, P + N = sum  →  P = (sum + target) / 2
    int total = sum + target;
    if (total < 0 || total % 2 != 0) { return 0; }
    int t = total / 2;

    int[] dp = new int[t + 1];
    dp[0] = 1;
    for (int num : nums) {
        for (int w = t; w >= num; w--) {  // right-to-left
            dp[w] += dp[w - num];
        }
    }
    return dp[t];
}
```

### Last Stone Weight II (LC 1049)
```java
public int lastStoneWeightII(int[] stones) {
    int sum = 0;
    for (int s : stones) { sum += s; }
    int target = sum / 2;

    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    for (int s : stones) {
        for (int w = target; w >= s; w--) {
            dp[w] |= dp[w - s];
        }
    }

    for (int w = target; w >= 0; w--) {
        if (dp[w]) { return sum - 2 * w; }   // minimize |sum - 2w|
    }
    return sum;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| RIGHT-TO-LEFT = 0/1 | Each item used at most once: `for w = W downTo wt` |
| LEFT-TO-RIGHT = Unbounded | Unlimited reuse: `for w = wt to W` |
| `dp[0] = true` or `dp[0] = 1` | Base case: empty subset achieves sum 0 |
| `(sum + target) % 2 != 0` | Parity check for Target Sum transformation |
| 2D helps verify 1D | When unsure about traversal order, debug with 2D first |
| Integer overflow in count problems | Use `long` if the number of ways can exceed int range |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 416 Partition Equal Subset Sum |
| Medium | LC 494 Target Sum |
| Medium | LC 1049 Last Stone Weight II |
| Medium | LC 474 Ones and Zeroes (2D knapsack) |
| Hard | LC 879 Profitable Schemes |

**Order:** 416 → 494 → 1049 → 474 → 879

---

## One-line Summary

```
0/1 Knapsack = right-to-left 1D DP; each item used at most once; dp[w] = max value with capacity w.
0/1背包 = 逆序遍历1D DP；每件物品最多选一次；dp[w]=容量w下最大价值。
```
