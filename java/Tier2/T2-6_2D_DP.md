# T2-6 — 2D DP (Sequences & Grid) 二维动态规划

> **Core idea:** Build a 2D table where `dp[i][j]` represents the optimal solution for subproblems on the first `i` elements of one dimension and first `j` of another. Fill row by row; each cell depends on neighbors.
> **核心思想：** 建立二维表，`dp[i][j]`表示第一个维度前i个、第二个维度前j个的最优子问题解。逐行填充，每格依赖相邻格。
>
> Complexity: O(m×n) time, O(m×n) space (often optimizable to O(n) with rolling array).
> Full reference: `DP/description.md` Pattern 4-5

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Longest common subsequence / substring | LC 1143, 718 |
| Edit distance between two strings | LC 72 |
| Count paths in grid | LC 62, 63 |
| Interleaving strings | LC 97 |
| Regex / wildcard matching | LC 10, 44 |
| Maximum square of 1s | LC 221 |

**Signal:** Two sequences or a grid; optimal solution depends on both dimensions simultaneously.

---

## Core Templates 核心模板

### LCS Template (two sequences)

```java
int[][] dp = new int[m+1][n+1];   // dp[i][j]: LCS of s1[0..i-1] and s2[0..j-1]

for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        if (s1.charAt(i-1) == s2.charAt(j-1))
            dp[i][j] = dp[i-1][j-1] + 1;         // chars match: extend
        else
            dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);  // skip one char
    }
}
return dp[m][n];
```

### Edit Distance Template

```java
int[][] dp = new int[m+1][n+1];
for (int i = 0; i <= m; i++) dp[i][0] = i;    // delete all of word1
for (int j = 0; j <= n; j++) dp[0][j] = j;    // insert all of word2

for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        if (word1.charAt(i-1) == word2.charAt(j-1))
            dp[i][j] = dp[i-1][j-1];            // no operation needed
        else
            dp[i][j] = 1 + Math.min(dp[i-1][j-1],    // replace
                           Math.min(dp[i-1][j],        // delete
                                    dp[i][j-1]));      // insert
    }
}
return dp[m][n];
```

### Grid Paths Template

```java
int[][] dp = new int[m][n];
// Initialize first row and column
for (int i = 0; i < m; i++) dp[i][0] = 1;
for (int j = 0; j < n; j++) dp[0][j] = 1;

for (int i = 1; i < m; i++)
    for (int j = 1; j < n; j++)
        dp[i][j] = dp[i-1][j] + dp[i][j-1];   // from above or from left

return dp[m-1][n-1];
```

---

## Variants 变形

| Variant | Recurrence | Example |
|---|---|---|
| LCS (subsequence) | match: `dp[i-1][j-1]+1`; else: `max(dp[i-1][j], dp[i][j-1])` | LC 1143 |
| LCS (substring) | match: `dp[i-1][j-1]+1`; else: `0` (reset) | LC 718 |
| Edit distance | 3-way min: replace/delete/insert | LC 72 |
| Unique paths | `dp[i][j] = dp[i-1][j] + dp[i][j-1]` | LC 62 |
| Unique paths with obstacles | Same + skip obstacle cells | LC 63 |
| Maximal square of 1s | `dp[i][j] = min(left, up, diag)+1` | LC 221 |
| Wildcard matching | `*` matches anything: `dp[i][j] = dp[i-1][j] || dp[i][j-1]` | LC 44 |
| Space-optimized | Rolling 1D array: keep only previous row | Most 2D DP |

---

## Key Examples 关键例题

### Longest Common Subsequence (LC 1143)
```java
public int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[][] dp = new int[m+1][n+1];
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            dp[i][j] = text1.charAt(i-1) == text2.charAt(j-1)
                ? dp[i-1][j-1] + 1
                : Math.max(dp[i-1][j], dp[i][j-1]);
    return dp[m][n];
}
```

### Edit Distance (LC 72)
```java
public int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m+1][n+1];
    for (int i = 0; i <= m; i++) dp[i][0] = i;
    for (int j = 0; j <= n; j++) dp[0][j] = j;
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            dp[i][j] = word1.charAt(i-1) == word2.charAt(j-1)
                ? dp[i-1][j-1]
                : 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
    return dp[m][n];
}
```

### Maximal Square (LC 221)
```java
public int maximalSquare(char[][] matrix) {
    int m = matrix.length, n = matrix[0].length, side = 0;
    int[][] dp = new int[m+1][n+1];
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            if (matrix[i-1][j-1] == '1') {
                dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1])) + 1;
                side = Math.max(side, dp[i][j]);
            }
    return side * side;
}
```

### Unique Paths with Obstacles (LC 63)
```java
public int uniquePathsWithObstacles(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dp = new int[m][n];
    for (int i = 0; i < m && grid[i][0] == 0; i++) dp[i][0] = 1;
    for (int j = 0; j < n && grid[0][j] == 0; j++) dp[0][j] = 1;
    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; j++)
            if (grid[i][j] == 0)
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
    return dp[m-1][n-1];
}
```

---

## Space Optimization 空间优化

```java
// Roll 2D dp[m+1][n+1] → 1D dp[n+1] (LCS example)
int[] dp = new int[n+1];
for (int i = 1; i <= m; i++) {
    int prev = 0;              // tracks dp[i-1][j-1]
    for (int j = 1; j <= n; j++) {
        int temp = dp[j];      // save before overwrite
        dp[j] = s1.charAt(i-1) == s2.charAt(j-1)
            ? prev + 1
            : Math.max(dp[j], dp[j-1]);
        prev = temp;
    }
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Off-by-one: use 1-indexed dp | `dp[i][j]` = answer for first `i` and `j` chars → row/col 0 = empty string base case |
| LCS vs LCSS | Subsequence: `max(skip-either)` on mismatch; Substring: reset to 0 on mismatch |
| Edit distance base case | `dp[i][0] = i` (delete i chars), `dp[0][j] = j` (insert j chars) |
| Maximal square: min of three | `min(left, up, diagonal) + 1` — the bottleneck direction |
| Space opt: save `prev` before overwrite | In rolling 1D, `dp[j]` (before update) = `dp[i-1][j-1]` for next iteration |
| `*` in wildcard | `dp[i][j] = dp[i-1][j]` (skip one in s) or `dp[i][j-1]` (match empty in p) |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 62 Unique Paths |
| Medium | LC 63 Unique Paths II |
| Medium | LC 1143 Longest Common Subsequence |
| Medium | LC 718 Maximum Length of Repeated Subarray |
| Hard | LC 72 Edit Distance |
| Hard | LC 221 Maximal Square |
| Hard | LC 97 Interleaving String |
| Hard | LC 44 Wildcard Matching |

**Order:** 62 → 63 → 1143 → 718 → 72 → 221 → 97 → 44

---

## One-line Summary

```
2D DP = fill a table where dp[i][j] solves subproblem on first i and j items; match → extend diagonal, mismatch → take best neighbor.
二维DP = 填表，dp[i][j]表示前i和前j个元素的子问题解；匹配→对角线延伸，不匹配→取邻格最优。
```
