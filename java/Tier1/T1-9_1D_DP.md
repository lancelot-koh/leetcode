# T1-9 — 1-D DP (Bottom-Up) 一维动态规划（自底向上）

> **Core idea:** Break the problem into overlapping sub-problems. Fill a 1D table from smallest to largest. Each cell depends on a constant number of previous cells.
> **核心思想：** 将问题分解为重叠子问题，从小到大填写一维表格，每格依赖固定数量的前一格。
>
> Complexity: O(n) time; O(n) space, reducible to O(1) when only the last 1–2 values are needed.

---

## Why DP Feels Hard — And How to Break Through

DP is consistently ranked the hardest topic in coding interviews. Here's why:

**The problem:** You can't solve DP top-down. You can't start from the question and work forward. You have to *define the answer to a subproblem first*, then figure out how the big answer depends on it. This is backwards from how we normally think.

**The good news:** Every DP problem has the same skeleton. Once you see it, you can't unsee it.

> **The key mental shift:** Stop asking "how do I reach the answer?" and start asking "if I already knew the answer to a smaller version of this problem, how would I use it?"

---

## The Journey: Recursion → Memoization → Bottom-Up DP

The best way to understand DP is to watch how we arrive at it. We'll use **Fibonacci numbers** as the example.

---

### Step 0 — Naive Recursion (correct but slow)

```java
int fib(int n) {
    if (n <= 1) { return n; }           // base cases
    return fib(n - 1) + fib(n - 2);    // recurrence
}
```

This is correct. But look at what happens when `n = 5`:

```
                    fib(5)
                   /       \
              fib(4)        fib(3)
             /     \        /    \
         fib(3)  fib(2)  fib(2)  fib(1)
         /   \    /  \    /  \
      fib(2) fib(1) ...  ...  ...
```

`fib(3)` is computed **twice**. `fib(2)` is computed **three times**. For `fib(50)`, this tree has over a trillion nodes. Time complexity: **O(2^n)** — exponential.

**The core observation: we are solving the same subproblems over and over.**

---

### Step 1 — Top-Down Memoization (cache the results)

Simple fix: store the result the first time you compute it, and return the cached value next time.

```java
int[] memo = new int[n + 1];
Arrays.fill(memo, -1);                  // -1 means "not computed yet"

int fib(int n) {
    if (n <= 1) { return n; }
    if (memo[n] != -1) { return memo[n]; }  // already computed → return immediately
    memo[n] = fib(n - 1) + fib(n - 2);     // compute once, store it
    return memo[n];
}
```

Now each value is computed **exactly once**. Time complexity: **O(n)**. This is called **top-down DP** (or memoization).

---

### Step 2 — Bottom-Up DP (fill a table, no recursion)

Instead of recursing down and caching, we directly fill a table from smallest to largest:

```java
int fib(int n) {
    int[] dp = new int[n + 1];
    dp[0] = 0;                          // base case
    dp[1] = 1;                          // base case
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2]; // use already-computed values
    }
    return dp[n];
}
```

**Why is bottom-up preferred?**
- No recursion stack → no stack overflow risk
- Cache-friendly memory access (sequential)
- Easier to space-optimize (see next)

### Step 3 — Space Optimization (when you only need last 2 values)

```java
int fib(int n) {
    if (n <= 1) { return n; }
    int prev2 = 0, prev1 = 1;
    for (int i = 2; i <= n; i++) {
        int cur = prev1 + prev2;
        prev2 = prev1;
        prev1 = cur;
    }
    return prev1;
}
```

**The three approaches solve the same problem.** Bottom-up DP is just memoization rewritten to avoid recursion.

---

## What Makes a Problem "DP"?

Two conditions must both be true:

| Condition | Meaning | Test |
|---|---|---|
| **Overlapping subproblems** | The same smaller problem appears multiple times in the recursion tree | Draw the recursion tree — do any nodes repeat? |
| **Optimal substructure** | The best answer to the whole problem is built from best answers to sub-problems | Can you say "the optimal solution *contains* optimal solutions to sub-problems"? |

**DP vs Greedy:**
- Greedy: make the locally best choice at each step, never look back
- DP: at each step, consider ALL choices, pick the best one by looking up previously computed results
- Use greedy only when you can *prove* local optimum = global optimum. When in doubt → DP.

**The clearest signal for DP:**
> The problem asks for a count, minimum, maximum, or yes/no answer, and the input has a sequence or capacity that grows.

---

## The 4-Step Framework 四步法（最重要）

**This is the framework. Apply it to every DP problem.**

---

### Step 1 — Define `dp[i]` in plain English

This is the hardest step. The definition must be:
- **Precise** — not "something about position i"
- **Complete** — the answer to the whole problem must be expressible as `dp[something]`
- **Self-contained** — `dp[i]` should not reference other `dp` values in its definition

**Bad definition:** "`dp[i]` = something about the first i houses"
**Good definition:** "`dp[i]` = maximum money we can rob from houses `0` through `i`, inclusive"

---

### Step 2 — Write the recurrence

Ask: **"If I'm at position `i`, what choices do I have, and what does each choice cost/give?"**

For each choice, the result is: `(cost of this choice) + dp[smaller subproblem]`

Take the min/max/sum over all choices.

**Example — House Robber:**
At house `i`, I have exactly 2 choices:
- **Skip house `i`**: best result = `dp[i-1]` (same as best for first i-1 houses)
- **Rob house `i`**: I can't rob `i-1`, so best result = `dp[i-2] + nums[i]`

Take the max: `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`

---

### Step 3 — Set base cases

Base cases are the smallest subproblems you can answer directly without the recurrence.

- Usually `dp[0]` and sometimes `dp[1]`
- Think: "what is the answer when there's nothing, or just one item?"

**Common base cases:**
```
Count problems:   dp[0] = 1  (one way to do nothing)
Min problems:     dp[0] = 0  (zero cost to do nothing)
Bool problems:    dp[0] = true  (empty = always valid)
```

---

### Step 4 — Fill the table and read the answer

Fill left to right (index 0 → n). The answer is usually `dp[n]` or `dp[target]`.

---

## Full Worked Example: Climbing Stairs (LC 70) — Step by Step

> "You can climb 1 or 2 steps at a time. How many distinct ways to reach step n?"

**Step 1 — Define `dp[i]`:**
`dp[i]` = number of distinct ways to reach step `i`

**Step 2 — Recurrence:**
To be at step `i`, I came from either:
- Step `i-1` (I took 1 step) → `dp[i-1]` ways to get here
- Step `i-2` (I took 2 steps) → `dp[i-2]` ways to get here

So: `dp[i] = dp[i-1] + dp[i-2]`

**Step 3 — Base cases:**
- `dp[0] = 1` (one way to be at the ground: just stand there)
- `dp[1] = 1` (one way to reach step 1: take one step)

**Step 4 — Fill the table (`n = 5`):**

```
i    :  0   1   2   3   4   5
dp[i]:  1   1   2   3   5   8

dp[2] = dp[1] + dp[0] = 1 + 1 = 2   (1+1 or 2)
dp[3] = dp[2] + dp[1] = 2 + 1 = 3   (1+1+1, 1+2, 2+1)
dp[4] = dp[3] + dp[2] = 3 + 2 = 5
dp[5] = dp[4] + dp[3] = 5 + 3 = 8
```

Answer: `dp[5] = 8`

---

## Full Worked Example: House Robber (LC 198) — Step by Step

> "Rob houses in a row. You can't rob two adjacent houses. Maximize total."
> `nums = [2, 7, 3, 1]`

**Step 1 — Define `dp[i]`:**
`dp[i]` = maximum money robbing from houses `0` through `i`

**Step 2 — Recurrence:**
At each house `i`, exactly two choices:
- Skip `i`: `dp[i] = dp[i-1]`
- Rob `i`: `dp[i] = dp[i-2] + nums[i]`

`dp[i] = max(dp[i-1], dp[i-2] + nums[i])`

**Step 3 — Base cases:**
- `dp[0] = nums[0]` = 2 (only one house, rob it)
- `dp[1] = max(nums[0], nums[1])` = max(2, 7) = 7

**Step 4 — Fill the table:**

```
i      :  0   1   2   3
nums[i]:  2   7   3   1
dp[i]  :  2   7   7   8

dp[2] = max(dp[1], dp[0] + nums[2]) = max(7, 2+3) = 7   ← skip house 2
dp[3] = max(dp[2], dp[1] + nums[3]) = max(7, 7+1) = 8   ← rob house 3 (skip house 2)
```

Answer: `dp[3] = 8` (rob houses 1 and 3: 7 + 1 = 8)

---

## The 3 DP "Flavors" and Their Initialization

| Flavor | Question | `dp[i]` type | Init `dp[0]` | Recurrence form |
|---|---|---|---|---|
| Count | "How many ways?" | int | `1` | `dp[i] += dp[i-k]` |
| Optimize | "Min/max value?" | int | `0` or `∞` | `dp[i] = min/max(dp[i-k] + cost)` |
| Feasibility | "Is it possible?" | boolean | `true` | `dp[i] \|= dp[i-k]` |

Getting the initialization wrong is the #1 source of bugs in DP.

---

## When to Use 什么时候用

| Signal in the problem | Pattern |
|---|---|
| "How many ways to reach..." | Count DP |
| "Minimum / maximum to reach..." | Optimize DP |
| "Can you achieve exactly..." | Boolean DP |
| Items can be reused (unlimited) | Unbounded knapsack — iterate **left → right** |
| Each item used at most once | 0/1 knapsack — iterate **right → left** |
| Depends on last 1–2 steps | Linear recurrence (Fibonacci-style) |

---

## Core Templates 核心模板

### Template 1 — Linear recurrence (Fibonacci-style)

Applies when `dp[i]` depends only on the previous 1–2 states.

```java
int[] dp = new int[n + 1];
dp[0] = base0;  // base case: empty / zero
dp[1] = base1;  // base case: first element
for (int i = 2; i <= n; i++) {
    dp[i] = f(dp[i-1], dp[i-2]);  // replace f() with your recurrence
}
return dp[n];
```

**Space-optimized** — if you only look back 2 steps, you need only 2 variables:

```java
int prev2 = base0, prev1 = base1;
for (int i = 2; i <= n; i++) {
    int cur = f(prev1, prev2);
    prev2 = prev1;  // slide the window forward
    prev1 = cur;
}
return prev1;
```

---

### Template 2 — 0/1 Knapsack (each item used at most once)

**Why right-to-left?**
Going left → right would update `dp[j - num]` before we read it in the same pass — meaning the same `num` could count twice. Right → left reads from the "old" row (before this item), so each item is used at most once.

```
Example: nums=[3], target=6
Left→right:  dp[3] = dp[0]=true  →  dp[6] = dp[3]=true  ← WRONG (3 used twice)
Right→left:  dp[6] = dp[3]=false →  dp[3] = dp[0]=true  ← CORRECT
```

```java
boolean[] dp = new boolean[target + 1];
dp[0] = true;                              // base: sum 0 is always achievable
for (int num : nums) {                     // outer = items, process one item at a time
    for (int j = target; j >= num; j--) { // RIGHT to LEFT — reads old values
        dp[j] |= dp[j - num];
    }
}
return dp[target];
```

---

### Template 3 — Unbounded Knapsack (each item reusable)

**Why left-to-right?**
Going left → right means `dp[j - coin]` is already updated in this pass — the same coin can contribute again. That's the desired behavior.

```java
int[] dp = new int[amount + 1];
Arrays.fill(dp, amount + 1);              // sentinel: "impossible"
dp[0] = 0;                                // base: 0 coins needed for amount 0
for (int coin : coins) {
    for (int j = coin; j <= amount; j++) { // LEFT to RIGHT — allows reuse
        if (dp[j - coin] != amount + 1) {
            dp[j] = Math.min(dp[j], dp[j - coin] + 1);
        }
    }
}
return dp[amount] > amount ? -1 : dp[amount];
```

**0/1 vs Unbounded — one-line rule:**
> Right-to-left = each item once. Left-to-right = each item unlimited times.

---

## Visualizing the Table 可视化

Always fill in the table by hand before coding. It prevents off-by-one errors and makes the recurrence obvious.

**Coin Change** `coins = [1, 2, 5]`, `amount = 6`

```
Initialize: dp = [0, ∞, ∞, ∞, ∞, ∞, ∞]

After coin=1:
  j=1: dp[1] = min(∞, dp[0]+1) = 1
  j=2: dp[2] = min(∞, dp[1]+1) = 2
  j=3: dp[3] = min(∞, dp[2]+1) = 3  ...
  dp = [0, 1, 2, 3, 4, 5, 6]

After coin=2:
  j=2: dp[2] = min(2, dp[0]+1) = 1
  j=3: dp[3] = min(3, dp[1]+1) = 2
  j=4: dp[4] = min(4, dp[2]+1) = 2  ...
  dp = [0, 1, 1, 2, 2, 3, 3]

After coin=5:
  j=5: dp[5] = min(3, dp[0]+1) = 1
  j=6: dp[6] = min(3, dp[1]+1) = 2
  dp = [0, 1, 1, 2, 2, 1, 2]

Answer: dp[6] = 2  →  [5, 1]
```

---

## Key Examples 关键例题

### LC 70 — Climbing Stairs

```java
public int climbStairs(int n) {
    if (n <= 2) { return n; }
    int prev2 = 1, prev1 = 2;           // dp[1]=1, dp[2]=2
    for (int i = 3; i <= n; i++) {
        int cur = prev1 + prev2;         // ways to reach i = from i-1 + from i-2
        prev2 = prev1;
        prev1 = cur;
    }
    return prev1;
}
```

---

### LC 746 — Min Cost Climbing Stairs

`dp[i]` = minimum cost to reach step `i` (you pay `cost[i]` when you *leave* step `i`)

```java
public int minCostClimbingStairs(int[] cost) {
    int n = cost.length;
    int prev2 = cost[0], prev1 = cost[1]; // dp[0], dp[1]
    for (int i = 2; i < n; i++) {
        int cur = cost[i] + Math.min(prev1, prev2); // pay cost[i], come from min of last two
        prev2 = prev1;
        prev1 = cur;
    }
    return Math.min(prev1, prev2);         // can stop at last or second-to-last step
}
```

---

### LC 198 — House Robber

`dp[i]` = max money robbing from houses `0..i`

```java
public int rob(int[] nums) {
    if (nums.length == 1) { return nums[0]; }
    int prev2 = nums[0], prev1 = Math.max(nums[0], nums[1]);
    for (int i = 2; i < nums.length; i++) {
        int cur = Math.max(prev1, prev2 + nums[i]); // skip i, or rob i (skip i-1)
        prev2 = prev1;
        prev1 = cur;
    }
    return prev1;
}
```

---

### LC 322 — Coin Change (min coins)

`dp[i]` = minimum coins to make amount `i`

```java
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);          // amount+1 is impossible → safe sentinel
    dp[0] = 0;                            // 0 coins to make amount 0
    for (int coin : coins) {
        for (int j = coin; j <= amount; j++) {
            dp[j] = Math.min(dp[j], dp[j - coin] + 1); // use this coin once more
        }
    }
    return dp[amount] > amount ? -1 : dp[amount];
}
```

---

### LC 518 — Coin Change II (count ways)

`dp[i]` = number of ways to make amount `i`

```java
public int change(int amount, int[] coins) {
    int[] dp = new int[amount + 1];
    dp[0] = 1;                            // one way to make 0: use nothing
    for (int coin : coins) {              // outer=coins → counts combinations not permutations
        for (int j = coin; j <= amount; j++) {
            dp[j] += dp[j - coin];        // add ways that use this coin one more time
        }
    }
    return dp[amount];
}
```

> **Combinations vs Permutations:** With `coins=[1,2]`, amount=3:
> - Outer=coins (above): `[1,1,1]`, `[1,2]`, `[2,1]` — wait, actually `[1,2]` and `[2,1]` both map to amount 3 but outer=coins prevents counting them separately. You get 2 combinations: `{1,1,1}` and `{1,2}`.
> - Outer=amount: you'd count `[1,2]` and `[2,1]` as different → 3 permutations. Use this when order matters.

---

### LC 416 — Partition Equal Subset Sum

`dp[j]` = can we pick some numbers from `nums` that sum to exactly `j`?

```java
public boolean canPartition(int[] nums) {
    int sum = Arrays.stream(nums).sum();
    if (sum % 2 != 0) { return false; }  // odd total can't be split equally
    int target = sum / 2;
    boolean[] dp = new boolean[target + 1];
    dp[0] = true;                         // sum 0 is always achievable (pick nothing)
    for (int num : nums) {
        for (int j = target; j >= num; j--) { // right→left: use each num at most once
            dp[j] |= dp[j - num];
        }
    }
    return dp[target];
}
```

---

### LC 91 — Decode Ways

`dp[i]` = number of ways to decode `s[0..i-1]`

Two decode choices at each position:
- 1-digit decode of `s[i-1]` alone — valid if not `'0'`
- 2-digit decode of `s[i-2..i-1]` — valid if it's 10–26

```java
public int numDecodings(String s) {
    int n = s.length();
    int[] dp = new int[n + 1];
    dp[0] = 1;                                         // empty prefix: 1 way
    dp[1] = s.charAt(0) == '0' ? 0 : 1;               // leading zero → 0 ways
    for (int i = 2; i <= n; i++) {
        int one = Integer.parseInt(s.substring(i-1, i));
        int two = Integer.parseInt(s.substring(i-2, i));
        if (one >= 1) {
            dp[i] += dp[i-1];                          // decode s[i-1] alone
        }
        if (two >= 10 && two <= 26) {
            dp[i] += dp[i-2];                          // decode s[i-2..i-1] as pair
        }
    }
    return dp[n];
}
```

**Trace** `s = "226"`:
```
dp[0] = 1
dp[1] = 1            (s[0]='2', valid single)
dp[2]: one=2(valid)→dp[1]=1, two=22(valid)→dp[0]=1  → dp[2]=2
dp[3]: one=6(valid)→dp[2]=2, two=26(valid)→dp[1]=1  → dp[3]=3
Ways: "2,2,6" / "22,6" / "2,26"
```

---

### LC 139 — Word Break

`dp[i]` = can `s[0..i-1]` be segmented into dictionary words?

```java
public boolean wordBreak(String s, List<String> wordDict) {
    Set<String> dict = new HashSet<>(wordDict);
    int n = s.length();
    boolean[] dp = new boolean[n + 1];
    dp[0] = true;                                      // empty string always valid
    for (int i = 1; i <= n; i++) {
        for (int j = 0; j < i; j++) {                 // try all split points [j, i)
            if (dp[j] && dict.contains(s.substring(j, i))) {
                dp[i] = true;
                break;                                 // one valid split is enough
            }
        }
    }
    return dp[n];
}
```

**Trace** `s = "leetcode"`, `dict = ["leet","code"]`:
```
dp[0]=true
dp[4]: j=0, dp[0]=T, s[0..3]="leet"∈dict → dp[4]=true
dp[8]: j=4, dp[4]=T, s[4..7]="code"∈dict → dp[8]=true ✓
```

---

## Skills & Pitfalls 技巧与陷阱

| Topic | Rule |
|---|---|
| Define `dp[i]` first | Write the full English sentence before writing any code |
| Start with recursion | If you can't find the recurrence, write brute-force recursion first, then memoize |
| 0/1 vs unbounded | **Right-to-left** = each item once; **Left-to-right** = reuse allowed |
| Initialization | Count: `dp[0]=1`. Min: `dp[0]=0`, rest=`∞`. Bool: `dp[0]=true`, rest=`false` |
| Sentinel for impossible | Use `amount+1` for min problems (avoids overflow from `MAX_VALUE + 1`) |
| Space optimization | If recurrence only needs last 1–2 values, replace array with 2 variables |
| Combinations vs permutations | Outer=items → combinations. Outer=capacity → permutations |
| Off-by-one in array size | Usually `dp[n+1]` for 1-indexed recurrences; `dp[target+1]` for knapsack |

---

## How to Approach a New DP Problem in an Interview

```
1. Read the problem → does it ask for count / min / max / yes-no?  → likely DP
2. Try to define dp[i]:  "dp[i] = ______ for the first i elements"
3. Ask: "at position i, what choices do I have?"
4. For each choice: result = (cost of choice) + dp[previous state]
5. Take min/max/sum over all choices → that's your recurrence
6. Set base cases (dp[0], dp[1])
7. Fill table left-to-right, return dp[n] or dp[target]
8. Space optimize if needed
```

When stuck: **write the brute-force recursion first.** The recursive structure directly gives you the recurrence.

---

## Practice Problems 练习题

| Difficulty | Problem | Key concept |
|---|---|---|
| Easy | LC 70 Climbing Stairs | Linear, Fibonacci-style |
| Easy | LC 746 Min Cost Climbing Stairs | Linear, min |
| Medium | LC 198 House Robber | Skip-or-take |
| Medium | LC 213 House Robber II | Rob() on two subarrays |
| Medium | LC 322 Coin Change | Unbounded knapsack, min |
| Medium | LC 518 Coin Change II | Unbounded knapsack, count |
| Medium | LC 416 Partition Equal Subset | 0/1 knapsack, bool |
| Medium | LC 91 Decode Ways | Linear + conditional |
| Medium | LC 139 Word Break | Linear + inner split loop |

**Recommended order (start here):** 70 → 746 → 198 → 322 → 518 → 416 → 91 → 213 → 139

> For each problem: first write the recursive solution, then add memoization, then convert to bottom-up. Doing all three versions builds the deepest understanding.

---

## One-line Summary

```
DP = define what dp[i] means → write recurrence (choices at i) → set base cases → fill table.
When stuck: write naive recursion first, then memoize, then convert to bottom-up.
```
