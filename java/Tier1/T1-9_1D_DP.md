# T1-9 — 1-D DP (Bottom-Up) 一维动态规划（自底向上）

> **Core idea:** Break the problem into overlapping sub-problems. Fill a 1D table from smallest to largest. Each cell depends on a constant number of previous cells.
> **核心思想：** 将问题分解为重叠子问题，从小到大填写一维表格，每格依赖固定数量的前一格。
>
> Complexity: O(n) time; O(n) space reducible to O(1) when only last 1–2 values needed.
> Full reference: `DP/description.md` Patterns 1–3

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Count ways to reach goal | Climbing stairs, decode ways |
| Maximize/minimize linear sequence | House robber, max subarray |
| Can we achieve target? | Partition sum, coin change feasibility |
| Unlimited reuse of items | Coin change (min coins) |
| One-time use of items | Knapsack |

**Four steps:** 1) Define `dp[i]`  2) Write recurrence  3) Set base cases  4) Read answer from `dp[n]`

---

## Core Templates 核心模板

### Linear recurrence (depends on 1–2 prior states)

```java
int[] dp = new int[n + 1];
dp[0] = base0;
dp[1] = base1;
for (int i = 2; i <= n; i++)
    dp[i] = f(dp[i-1], dp[i-2]);   // recurrence
return dp[n];
```

### Space-optimized (only last 2 values needed)

```java
int prev2 = base0, prev1 = base1;
for (int i = 2; i <= n; i++) {
    int cur = f(prev1, prev2);
    prev2 = prev1; prev1 = cur;
}
return prev1;
```

### 0/1 Knapsack (each item used at most once)

```java
boolean[] dp = new boolean[target + 1];
dp[0] = true;
for (int num : nums)
    for (int j = target; j >= num; j--)   // RIGHT to LEFT prevents reuse
        dp[j] |= dp[j - num];
```

### Unbounded Knapsack (each item usable unlimited times)

```java
int[] dp = new int[amount + 1];
Arrays.fill(dp, Integer.MAX_VALUE); dp[0] = 0;
for (int coin : coins)
    for (int j = coin; j <= amount; j++)   // LEFT to RIGHT allows reuse
        if (dp[j - coin] != Integer.MAX_VALUE)
            dp[j] = Math.min(dp[j], dp[j - coin] + 1);
```

---

## Variants 变形

| Problem | `dp[i]` definition | Recurrence | Example |
|---|---|---|---|
| Climbing stairs | ways to reach step i | `dp[i-1] + dp[i-2]` | LC 70 |
| House robber | max rob first i houses | `max(dp[i-1], dp[i-2]+nums[i])` | LC 198 |
| Coin change (min) | min coins for amount i | `min(dp[i], dp[i-coin]+1)` | LC 322 |
| Coin change II (count) | ways to make amount i | `dp[i] += dp[i-coin]` | LC 518 |
| Partition equal subset | can sum to i? (bool) | `dp[j] |= dp[j-num]` | LC 416 |
| Decode ways | ways to decode s[0..i-1] | `dp[i-1] + dp[i-2]` (conditionally) | LC 91 |

---

## Key Examples 关键例题

### House Robber (LC 198)
```java
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    int prev2 = nums[0], prev1 = Math.max(nums[0], nums[1]);
    for (int i = 2; i < nums.length; i++) {
        int cur = Math.max(prev1, prev2 + nums[i]);
        prev2 = prev1; prev1 = cur;
    }
    return prev1;
}
```

### Coin Change (LC 322)
```java
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);   // sentinel for "impossible"
    dp[0] = 0;
    for (int coin : coins)
        for (int j = coin; j <= amount; j++)
            dp[j] = Math.min(dp[j], dp[j - coin] + 1);
    return dp[amount] > amount ? -1 : dp[amount];
}
```

### Partition Equal Subset Sum (LC 416)
```java
public boolean canPartition(int[] nums) {
    int sum = Arrays.stream(nums).sum();
    if (sum % 2 != 0) return false;
    int target = sum / 2;
    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    for (int num : nums)
        for (int j = target; j >= num; j--)
            dp[j] |= dp[j - num];
    return dp[target];
}
```

### Decode Ways (LC 91)
```java
public int numDecodings(String s) {
    int n = s.length();
    int[] dp = new int[n + 1];
    dp[0] = 1;
    dp[1] = s.charAt(0) == '0' ? 0 : 1;
    for (int i = 2; i <= n; i++) {
        int one = Integer.parseInt(s.substring(i-1, i));
        int two = Integer.parseInt(s.substring(i-2, i));
        if (one >= 1)          dp[i] += dp[i-1];
        if (two >= 10 && two <= 26) dp[i] += dp[i-2];
    }
    return dp[n];
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Define state precisely | `dp[i]` = "max rob using exactly houses 0..i" NOT "something about i" |
| 0/1 vs unbounded | Right-to-left = 0/1 (no reuse); left-to-right = unbounded (reuse allowed) |
| Sentinel value | Use `Integer.MAX_VALUE` or `amount+1` for "impossible" in min problems |
| Base cases | `dp[0]` often = 0 (min problems) or 1 (count problems); think carefully |
| Space optimization | If `dp[i]` only uses `dp[i-1]` and `dp[i-2]`, reduce to two variables |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 70 Climbing Stairs, LC 746 Min Cost Climbing |
| Medium | LC 198 House Robber, LC 213 House Robber II |
| Medium | LC 322 Coin Change, LC 518 Coin Change II |
| Medium | LC 416 Partition Equal Subset, LC 91 Decode Ways |
| Medium | LC 139 Word Break |

**Order:** 70 → 198 → 322 → 416 → 91 → 518 → 213 → 139

---

## One-line Summary

```
1D DP = define dp[i], write recurrence from previous states, fill left-to-right (or right-to-left for 0/1 knapsack).
一维DP = 定义dp[i]，从前序状态写递推，左到右填表（0/1背包则从右到左）。
```
