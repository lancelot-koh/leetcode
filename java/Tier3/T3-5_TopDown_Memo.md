# T3-5 — Top-Down DP (Memoization) 自顶向下DP（记忆化）

> **Core idea:** Recursion + cache. Write the recursive brute-force solution first, then add a memo table to cache results by state. No need to determine fill order; just let recursion handle it.
> **核心思想：** 递归 + 缓存。先写递归暴力解，再加备忘录缓存状态结果。无需确定填表顺序，递归自动处理。
>
> Complexity: O(states × work per state) — same as bottom-up DP.
> Full reference: `DP/description.md` Pattern 6

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Recursive structure is obvious | Fibonacci, recursive tree problems |
| State space is sparse (not all states reached) | LC 329, 472 |
| Problem has complex state transitions | LC 87 Scramble String |
| Interval DP where subproblems are ranges | LC 1312, 312 |
| Need only a few paths, not all states | LC 913 |

**Memoization vs Tabulation:**
| Approach | When to prefer |
|---|---|
| Top-down (memo) | State transitions are complex, sparse states, easier to code |
| Bottom-up (table) | Performance critical, iterative is clearer, all states needed |

---

## Core Templates 核心模板

### HashMap memo (general state)

```java
Map<String, Integer> memo = new HashMap<>();

int dp(int i, int j, /* state params */) {
    String key = i + "," + j;
    if (memo.containsKey(key)) return memo.get(key);

    // Base cases
    if (/* base condition */) return /* base value */;

    // Recursive transitions
    int result = /* combine sub-results */;

    memo.put(key, result);
    return result;
}
```

### int[][] memo (two-index state)

```java
int[][] memo = new int[m][n];
// -1 = uncomputed (use Arrays.fill trick)
for (int[] row : memo) Arrays.fill(row, -1);

int dp(int i, int j) {
    if (i < 0 || j < 0) return /* base */;
    if (memo[i][j] != -1) return memo[i][j];

    int result = /* transitions */;
    return memo[i][j] = result;
}
```

### Interval DP pattern (range [l, r])

```java
int[][] memo = new int[n][n];
// -1 = uncomputed

int dp(int l, int r) {
    if (l > r) return /* base */;
    if (l == r) return /* single element */;
    if (memo[l][r] != -1) return memo[l][r];

    int result = /* try all splits at k in [l, r-1] */;
    for (int k = l; k < r; k++)
        result = Math.max(result, dp(l, k) + dp(k+1, r) + /* cost */);

    return memo[l][r] = result;
}
```

---

## Variants 变形

| Variant | State representation | Example |
|---|---|---|
| Fibonacci / climbing stairs | 1D index | LC 70 |
| Grid path with obstacles | 2D (row, col) | LC 63 |
| LCS / Edit distance | 2D (i, j) two strings | LC 1143, 72 |
| Interval DP | 2D (left, right) | LC 1312, 312 |
| DP on DAG | Node as state | LC 329 |
| Bitmask DP | State = bitmask | LC 847 |
| String match with wildcards | 2D (i, j) | LC 10, 44 |

---

## Key Examples 关键例题

### Longest Increasing Path in Matrix (LC 329)
```java
int[][] memo;
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

public int longestIncreasingPath(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    memo = new int[m][n];
    int res = 0;
    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++)
            res = Math.max(res, dfs(matrix, i, j, m, n));
    return res;
}

int dfs(int[][] matrix, int r, int c, int m, int n) {
    if (memo[r][c] != 0) return memo[r][c];
    int best = 1;
    for (int[] d : dirs) {
        int nr = r+d[0], nc = c+d[1];
        if (nr >= 0 && nr < m && nc >= 0 && nc < n && matrix[nr][nc] > matrix[r][c])
            best = Math.max(best, 1 + dfs(matrix, nr, nc, m, n));
    }
    return memo[r][c] = best;
}
```

### Minimum Insertion Steps to Make String Palindrome (LC 1312)
```java
int[][] memo;

public int minInsertions(String s) {
    int n = s.length();
    memo = new int[n][n];
    for (int[] row : memo) Arrays.fill(row, -1);
    return dp(s, 0, n-1);
}

int dp(String s, int l, int r) {
    if (l >= r) return 0;
    if (memo[l][r] != -1) return memo[l][r];
    if (s.charAt(l) == s.charAt(r))
        return memo[l][r] = dp(s, l+1, r-1);
    return memo[l][r] = 1 + Math.min(dp(s, l+1, r), dp(s, l, r-1));
}
```

### Word Break (LC 139) — top-down
```java
public boolean wordBreak(String s, List<String> wordDict) {
    Set<String> words = new HashSet<>(wordDict);
    Boolean[] memo = new Boolean[s.length()];
    return dp(s, 0, words, memo);
}

boolean dp(String s, int start, Set<String> words, Boolean[] memo) {
    if (start == s.length()) return true;
    if (memo[start] != null) return memo[start];
    for (int end = start + 1; end <= s.length(); end++)
        if (words.contains(s.substring(start, end)) && dp(s, end, words, memo))
            return memo[start] = true;
    return memo[start] = false;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Check memo BEFORE base cases | Or after (but consistent): `if (memo[i][j] != -1) return memo[i][j]` first |
| Initialize memo to -1 not 0 | 0 may be a valid answer; use -1 or `Integer.MIN_VALUE` as sentinel |
| `Boolean[]` not `boolean[]` | `Boolean[]` allows null check: `if (memo[i] != null) return memo[i]` |
| `String` key in HashMap vs array | Array index is faster; use String key only when state is complex/sparse |
| Stack overflow on large inputs | Convert to iterative bottom-up if n > ~10000 |
| Don't forget to RETURN the stored value | `return memo[l][r] = result;` stores and returns in one line |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 139 Word Break |
| Medium | LC 329 Longest Increasing Path in Matrix |
| Hard | LC 1312 Minimum Insertion Steps to Make Palindrome |
| Hard | LC 312 Burst Balloons (interval DP) |
| Hard | LC 87 Scramble String |
| Hard | LC 847 Shortest Path Visiting All Nodes (bitmask DP) |

**Order:** 139 → 329 → 1312 → 312 → 87 → 847

---

## One-line Summary

```
Memoization = recursive brute-force + cache by state; check memo first, compute if missing, store before returning.
记忆化 = 递归暴力解 + 按状态缓存；先查缓存，缺失则计算，返回前存储。
```
