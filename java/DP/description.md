# Dynamic Programming 动态规划

> **Core idea:** Break a problem into overlapping sub-problems. Solve each sub-problem once and store results to avoid recomputation.
> **核心思想：** 将问题分解为有重叠的子问题，每个子问题只解一次并存储结果，避免重复计算。
>
> DP = **Optimal substructure** + **Overlapping sub-problems**.
> Two approaches: **bottom-up** (tabulation) and **top-down** (memoization).
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| minimum / maximum | 最小 / 最大 |
| number of ways / count | 方案数 / 计数 |
| can we achieve / is it possible | 能否实现 / 是否可行 |
| longest / shortest subsequence | 最长 / 最短子序列 |
| partition into... | 分割成... |
| optimal path / cost | 最优路径 / 代价 |

**When NOT to use 不适用**

- Problem requires **all** solutions → Backtracking
- No overlapping sub-problems → Divide and conquer
- Greedy works (can prove local optimum = global optimum) → Greedy

**Bottom-up vs Top-down 两种实现方式**

| | Bottom-up (tabulation) | Top-down (memoization) |
|---|---|---|
| Order | fill table iteratively | recursive + cache |
| Space | can often optimize to O(1) or O(n) | needs full memo table |
| Best for | regular recurrences, easy to see order | irregular dependencies, easier to write |
| Stack overflow | No risk | Risk for very deep recursion |

---

## 2. Four Steps to Solve Any DP 解任何DP的四步

```
1. Define state:    what does dp[i] (or dp[i][j]) represent?
2. Recurrence:      how does dp[i] depend on previous states?
3. Base cases:      what are the smallest valid states?
4. Answer:          which dp cell holds the final answer?
```

---

## 3. Quick Decision Guide 快速判断

```
Linear, depends on previous 1-2 elements?     → Pattern 1: 1D DP linear
Choose or skip each element?                  → Pattern 2: 0/1 Knapsack
Unlimited use of each element?                → Pattern 3: Unbounded Knapsack
Two sequences, find common structure?         → Pattern 4: 2D DP (LCS/Edit distance)
Sub-problems are intervals [i..j]?            → Pattern 5: Interval DP
Hard to see bottom-up order?                  → Pattern 6: Top-down memoization
```

---

## 4. Patterns 模式

---

### Pattern 1 — 1D Linear DP 一维线性DP

**When:** each state depends on a constant number of previous states; no "choose/skip" branching.
**适用：** 每个状态依赖固定数量的前一状态，无"选/不选"分支。

**Template 模板**

```java
int[] dp = new int[n + 1];
dp[0] = base;   // base case

for (int i = 1; i <= n; i++) {
    dp[i] = f(dp[i-1], dp[i-2], ...);  // recurrence
}
return dp[n];
```

**Variants 变形**

| Problem | State | Recurrence |
|---|---|---|
| Climbing stairs | `dp[i]` = ways to reach step i | `dp[i] = dp[i-1] + dp[i-2]` |
| House robber | `dp[i]` = max rob from house 0..i | `dp[i] = max(dp[i-1], dp[i-2] + nums[i])` |
| Max subarray (Kadane's) | `dp[i]` = max subarray ending at i | `dp[i] = max(nums[i], dp[i-1] + nums[i])` |
| Decode ways | `dp[i]` = ways to decode s[0..i-1] | `dp[i] = dp[i-1] (if valid 1-digit) + dp[i-2] (if valid 2-digit)` |

**Space optimization 空间优化**

When `dp[i]` only needs `dp[i-1]` and `dp[i-2]`:
```java
int prev2 = 0, prev1 = 1;
for (int i = 2; i <= n; i++) {
    int cur = prev1 + prev2;
    prev2 = prev1;
    prev1 = cur;
}
return prev1;
```

---

### Pattern 2 — 0/1 Knapsack 0/1背包

**When:** choose or skip each item (used at most once); maximize value within capacity.
**适用：** 每个物品最多使用一次，在容量限制内最大化价值。

**Template 模板**

```java
int[] dp = new int[capacity + 1];   // dp[w] = max value with weight limit w

for (int item : items) {
    for (int w = capacity; w >= item.weight; w--) {  // RIGHT to LEFT — prevents reuse
        dp[w] = Math.max(dp[w], dp[w - item.weight] + item.value);
    }
}
return dp[capacity];
```

**Why right-to-left? 为什么从右到左？**

Iterating left-to-right would allow using the same item multiple times (the updated `dp[w - weight]` would be used again). Right-to-left uses the values from the **previous** item's row.
从左到右会允许同一物品使用多次（更新后的 `dp[w-weight]` 会被再次使用）。从右到左确保使用的是前一轮的值。

**Variants 变形**

| Problem | State | Goal | Example |
|---|---|---|---|
| Partition equal subset sum | `dp[w]` = can we sum to w? | `dp[total/2] == true` | LC 416 |
| Target sum (assign +/-) | `dp[w]` = ways to reach sum w | `dp[target]` | LC 494 |
| Last stone weight II | same as partition | minimize difference | LC 1049 |

**Example: Partition Equal Subset (LC 416)**

```java
public boolean canPartition(int[] nums) {
    int total = Arrays.stream(nums).sum();
    if (total % 2 != 0) return false;
    int target = total / 2;

    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    for (int num : nums)
        for (int w = target; w >= num; w--)
            dp[w] |= dp[w - num];
    return dp[target];
}
```

---

### Pattern 3 — Unbounded Knapsack 完全背包

**When:** each item can be used unlimited times.
**适用：** 每个物品可以无限次使用。

**Template 模板**

```java
int[] dp = new int[amount + 1];
Arrays.fill(dp, Integer.MAX_VALUE);
dp[0] = 0;

for (int coin : coins) {
    for (int w = coin; w <= amount; w++) {    // LEFT to RIGHT — allows reuse
        if (dp[w - coin] != Integer.MAX_VALUE)
            dp[w] = Math.min(dp[w], dp[w - coin] + 1);
    }
}
return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
```

**Key difference from 0/1:** iterate **left-to-right** so the same item can be picked again.
与0/1背包的区别：从**左到右**遍历，允许同一物品再次被选取。

**Variants 变形**

| Problem | Goal | Example |
|---|---|---|
| Coin change (min coins) | min coins to reach amount | LC 322 |
| Coin change II (count ways) | number of combinations | LC 518 |
| Integer break | maximize product of parts | LC 343 |

---

### Pattern 4 — 2D DP (Two Sequences) 二维DP（两个序列）

**When:** problem involves two strings/sequences; relationship between their prefixes.
**适用：** 涉及两个字符串/序列，关注前缀之间的关系。

**Template 模板**

```java
int[][] dp = new int[m + 1][n + 1];
// dp[i][j] = answer for s1[0..i-1] and s2[0..j-1]

for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        if (s1.charAt(i-1) == s2.charAt(j-1))
            dp[i][j] = dp[i-1][j-1] + 1;       // characters match
        else
            dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);  // take best without one char
    }
}
return dp[m][n];
```

**Common 2D DP problems 常见二维DP**

| Problem | `dp[i][j]` means | Recurrence |
|---|---|---|
| LCS | length of LCS of s1[0..i-1] and s2[0..j-1] | match: `dp[i-1][j-1]+1`; else: `max(dp[i-1][j], dp[i][j-1])` |
| Edit distance | min ops to convert s1[0..i-1] to s2[0..j-1] | match: `dp[i-1][j-1]`; else: `1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])` |
| Unique paths | ways to reach cell (i,j) | `dp[i][j] = dp[i-1][j] + dp[i][j-1]` |
| Longest palindromic subsequence | LPS of s[i..j] | match: `dp[i+1][j-1]+2`; else: `max(dp[i+1][j], dp[i][j-1])` |

**Edit distance (LC 72)**

```java
for (int i = 0; i <= m; i++) dp[i][0] = i;
for (int j = 0; j <= n; j++) dp[0][j] = j;

for (int i = 1; i <= m; i++)
    for (int j = 1; j <= n; j++)
        if (word1.charAt(i-1) == word2.charAt(j-1))
            dp[i][j] = dp[i-1][j-1];
        else
            dp[i][j] = 1 + Math.min(dp[i-1][j-1],      // replace
                            Math.min(dp[i-1][j],          // delete
                                     dp[i][j-1]));        // insert
```

---

### Pattern 5 — Interval DP 区间DP

**When:** the optimal answer for a range `[i, j]` depends on splitting it at some point `k`.
**适用：** 区间 `[i, j]` 的最优解依赖于在某个点 `k` 处分割。

**Template 模板**

```java
int[][] dp = new int[n][n];

for (int len = 2; len <= n; len++) {          // iterate by interval length
    for (int i = 0; i <= n - len; i++) {
        int j = i + len - 1;
        dp[i][j] = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {         // try all split points
            dp[i][j] = Math.min(dp[i][j],
                                dp[i][k] + dp[k+1][j] + cost(i, k, j));
        }
    }
}
return dp[0][n-1];
```

**Variants 变形**

| Problem | Example |
|---|---|
| Burst balloons | LC 312 |
| Stone game / merge stones | LC 1000 |
| Matrix chain multiplication | classic |
| Palindrome partitioning II (min cuts) | LC 132 |

---

### Pattern 6 — Top-down Memoization 自顶向下记忆化

**When:** recurrence is natural to write recursively; dependency order is hard to determine for bottom-up.
**适用：** 递归写法自然，自底向上的填表顺序难以确定时。

**Template 模板**

```java
Map<String, Integer> memo = new HashMap<>();

public int solve(String state) {
    if (isBaseCase(state)) return baseValue(state);
    if (memo.containsKey(state)) return memo.get(state);

    int result = /* recursive calls */;
    memo.put(state, result);
    return result;
}
```

**When to use memo vs dp array:**
- Use `int[] memo` for single integer index
- Use `int[][] memo` for two indices
- Use `HashMap` for complex/sparse states

**Variants 变形**

| Variant | Example |
|---|---|
| Word break | LC 139 |
| Longest increasing subsequence | LC 300 |
| Distinct subsequences | LC 115 |
| Regular expression matching | LC 10 |

---

## 5. Advanced Skills 进阶技能

### Skill 1 — Define State Precisely 精确定义状态

The hardest part of DP. Ask: "What information do I need to uniquely characterize a sub-problem?"
DP 最难的部分。问："我需要什么信息才能唯一描述一个子问题？"

Bad state: `dp[i]` = "some answer involving i"
Good state: `dp[i]` = "maximum sum of subarray ending exactly at index i"

### Skill 2 — Draw the Recurrence Arrow 画递推关系箭头

Before coding, draw which cells `dp[i][j]` depends on. This prevents index errors and clarifies fill order.

```
LCS: dp[i][j] ← dp[i-1][j-1]  (match)
               ← dp[i-1][j]    (skip s1[i])
               ← dp[i][j-1]    (skip s2[j])
```

### Skill 3 — Space Optimization 空间优化

If `dp[i]` only depends on `dp[i-1]` (previous row), optimize from O(n²) → O(n):

```java
int[] prev = new int[n + 1], cur = new int[n + 1];
// after each row: prev = cur; cur = new int[n + 1];
```

For 1D, reduce further: `dp[i]` depends only on `dp[i-1]` → just two variables.

### Skill 4 — Handle Edge Cases First 先处理边界

Common edge cases:
- Empty string/array: `dp[0]` must be defined
- Single element: `dp[1]` might need special treatment
- Target = 0: usually `dp[0] = 1` (one way: take nothing)

### Skill 5 — DP vs Greedy: When to Switch 什么时候换贪心

If you find yourself writing DP for a problem, ask: "Is there a greedy choice that is always locally optimal AND globally optimal?" If yes, greedy is simpler and faster.

---

## 6. Interview Script 面试话术

**English:**
> I'd use dynamic programming because this problem has [optimal substructure / overlapping sub-problems]. I define `dp[i]` as [state definition]. The recurrence is `dp[i] = f(dp[i-1], ...)` because [explain dependency]. Base case: `dp[0] = X`. The answer is `dp[n]`.

**中文：**
> 我会用动态规划，因为这个问题有[最优子结构/重叠子问题]。定义 `dp[i]` 为[状态含义]，递推关系是 `dp[i] = f(dp[i-1], ...)` 因为[解释依赖关系]。基础情况 `dp[0] = X`，答案是 `dp[n]`。

---

## 7. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. 1D Linear | 70, 198, 53, 91 |
| 2. 0/1 Knapsack | 416, 494, 1049 |
| 3. Unbounded Knapsack | 322, 518, 343 |
| 4. 2D Sequences | 1143, 72, 62, 516 |
| 5. Interval DP | 312, 132, 1000 |
| 6. Top-down memo | 139, 300, 10 |

**Recommended order:** 70 → 198 → 322 → 53 → 416 → 1143 → 72 → 300 → 139 → 312

---

## 8. One-line Summary 一句话总结

```
DP = define state → write recurrence → fill base cases → read answer.
动态规划 = 定义状态 → 写递推关系 → 填基础情况 → 读答案。
```
