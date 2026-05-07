# T2-6 — 2D DP (Sequences & Grid) 二维动态规划

> **Core idea:** Build a 2D table where `dp[i][j]` represents the answer for the subproblem on the first `i` elements of one dimension and first `j` of another. Fill row by row; each cell depends on previously filled neighbors.
> **核心思想：** 建立二维表，`dp[i][j]`表示第一个维度前i个、第二个维度前j个的子问题解。逐行填充，每格依赖已填的相邻格。
>
> Complexity: O(m×n) time, O(m×n) space (often reducible to O(n) with a rolling array).

---

## From 1D to 2D DP — Why Do We Need Two Dimensions?

In 1D DP, one thing changes: position `i` in an array, or amount `j` in a knapsack.

In 2D DP, **two things change simultaneously**:
- Two strings being compared (LCS, Edit Distance)
- Two axes of a grid (Unique Paths)
- Items and capacity (Knapsack — also 2D by nature, though often space-optimized to 1D)

The key insight: the answer at `dp[i][j]` depends on smaller subproblems. "Smaller" means either `i` or `j` (or both) decreased. So we fill the table from top-left to bottom-right, and every cell we need is already computed.

---

## The Same 4 Steps — Applied to 2D

### Step 1 — Define `dp[i][j]` in plain English

Precision matters even more in 2D. Write it out before touching code.

**Example — LCS:**
> `dp[i][j]` = length of the longest common subsequence of `s1[0..i-1]` and `s2[0..j-1]`

**Example — Edit Distance:**
> `dp[i][j]` = minimum operations to convert `word1[0..i-1]` into `word2[0..j-1]`

**Example — Unique Paths:**
> `dp[i][j]` = number of paths from cell `(0,0)` to cell `(i,j)`

### Step 2 — Recurrence

Ask: "at cell `(i,j)`, what choices do I have?"

**LCS:** Does `s1[i-1]` match `s2[j-1]`?
- Match → we can use both characters: `dp[i][j] = dp[i-1][j-1] + 1`
- No match → skip one character from either string: `dp[i][j] = max(dp[i-1][j], dp[i][j-1])`

**Edit Distance:** Does `word1[i-1]` match `word2[j-1]`?
- Match → no operation needed: `dp[i][j] = dp[i-1][j-1]`
- No match → try all 3 operations, take the min:
  - Replace: `dp[i-1][j-1] + 1`
  - Delete from word1: `dp[i-1][j] + 1`
  - Insert into word1: `dp[i][j-1] + 1`

### Step 3 — Base cases

Row 0 and column 0 are the "empty string" base cases.

**LCS:** `dp[i][0] = dp[0][j] = 0` — LCS of anything with empty string is 0 (automatic with array init)

**Edit Distance:**
- `dp[i][0] = i` — converting `word1[0..i-1]` to empty string requires `i` deletions
- `dp[0][j] = j` — converting empty string to `word2[0..j-1]` requires `j` insertions

**Grid Paths:** `dp[i][0] = dp[0][j] = 1` — only one way to travel along an edge

### Step 4 — Fill and read

Two nested loops: outer over `i` (1 to m), inner over `j` (1 to n). Answer is `dp[m][n]`.

---

## Full Worked Example: LCS of "ABCB" and "BDCAB"

`s1 = "ABCB"` (m=4), `s2 = "BDCAB"` (n=5)

**Definition:** `dp[i][j]` = LCS length of `s1[0..i-1]` and `s2[0..j-1]`

**Base cases:** Row 0 and column 0 are all zeros.

**Fill the table:**

```
       ""   B   D   C   A   B
   ""[  0   0   0   0   0   0 ]
   A [  0   0   0   0   1   1 ]
   B [  0   1   1   1   1   2 ]
   C [  0   1   1   2   2   2 ]
   B [  0   1   1   2   2   3 ]
```

Let's trace a few cells:
```
dp[1][1]: s1[0]='A', s2[0]='B' → no match → max(dp[0][1], dp[1][0]) = max(0,0) = 0
dp[1][4]: s1[0]='A', s2[3]='A' → MATCH   → dp[0][3] + 1 = 0 + 1 = 1
dp[2][1]: s1[1]='B', s2[0]='B' → MATCH   → dp[1][0] + 1 = 0 + 1 = 1
dp[2][5]: s1[1]='B', s2[4]='B' → MATCH   → dp[1][4] + 1 = 1 + 1 = 2
dp[4][5]: s1[3]='B', s2[4]='B' → MATCH   → dp[3][4] + 1 = 2 + 1 = 3
```

**Answer:** `dp[4][5] = 3` → LCS is `"BCB"` (or `"ACB"`)

---

## Full Worked Example: Edit Distance "horse" → "ros"

`word1 = "horse"` (m=5), `word2 = "ros"` (n=3)

**Base cases:**
- `dp[i][0] = i` (delete i chars from word1)
- `dp[0][j] = j` (insert j chars to reach word2)

```
       ""  r   o   s
   ""[  0   1   2   3 ]
   h [  1   1   2   3 ]
   o [  2   2   1   2 ]
   r [  3   2   2   2 ]
   s [  4   3   3   2 ]
   e [  5   4   4   3 ]
```

Tracing key cells:
```
dp[1][1]: h≠r → 1 + min(dp[0][0], dp[0][1], dp[1][0]) = 1 + min(0,1,1) = 1  (replace h→r)
dp[2][2]: o=o → dp[1][1] = 1   (match, free)
dp[5][3]: e≠s → 1 + min(dp[4][2], dp[4][3], dp[5][2]) = 1 + min(3,2,4) = 3
```

**Answer:** `dp[5][3] = 3`
```
horse → rorse (replace h→r)
rorse → rose  (delete r)
rose  → ros   (delete e)
```

---

## Understanding What Each Cell Means

The hardest part of 2D DP is keeping track of what `dp[i][j]` actually means. Here's a visual:

**LCS — each cell = best subsequence length so far:**
```
When you're at dp[i][j], you're asking:
"Considering only s1[0..i-1] and s2[0..j-1], what's the longest common subsequence?"
The rest of the strings don't exist yet.
```

**Edit Distance — each cell = minimum cost:**
```
When you're at dp[i][j], you're asking:
"I have word1[0..i-1] and I want to turn it into word2[0..j-1].
What's the minimum number of operations?"
```

**Grid Paths — each cell = number of ways:**
```
When you're at dp[i][j], you're asking:
"How many ways can I reach cell (i,j) from (0,0) going only right or down?"
```

---

## The 3 Operations in Edit Distance — Visualized

For `word1 = "horse"` → `word2 = "ros"`, at position `(i=1, j=1)` comparing `h` vs `r`:

```
Option 1 — Replace (dp[i-1][j-1] + 1):
  Solve "hors" → "ro" already optimal, then replace h→r
  Cost: dp[0][0] + 1 = 0 + 1 = 1

Option 2 — Delete from word1 (dp[i-1][j] + 1):
  Delete h, then solve "" → "r"
  Cost: dp[0][1] + 1 = 1 + 1 = 2

Option 3 — Insert into word1 (dp[i][j-1] + 1):
  Insert r at front, then solve "horse" → "ro"... wait that's getting longer
  (This corresponds to: match the r in word2 by inserting, advance j only)
  Cost: dp[1][0] + 1 = 1 + 1 = 2

Take min(1, 2, 2) = 1
```

**Memory trick for edit distance:**
```
dp[i-1][j-1] → replace   (both pointers advance)
dp[i-1][j]   → delete    (only i advances — we consumed a char from word1)
dp[i][j-1]   → insert    (only j advances — we produced a char into word2)
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Compare two strings, find similarity | LCS, Edit Distance |
| Count paths in a grid | LC 62, 63 |
| Match patterns | Wildcard, Regex |
| Build one string from another | Interleaving |
| Find largest shape of 1s in a matrix | Maximal Square |

**Signal:** Two sequences or a grid; the optimal answer depends on progress in both dimensions at once.

---

## Core Templates 核心模板

### LCS Template (two sequences)

```java
int m = s1.length(), n = s2.length();
int[][] dp = new int[m+1][n+1];  // dp[i][j]: LCS of s1[0..i-1] and s2[0..j-1]
                                  // row 0 and col 0 = empty string → all zeros (default)
for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        if (s1.charAt(i-1) == s2.charAt(j-1)) {
            dp[i][j] = dp[i-1][j-1] + 1;                    // match: extend diagonal
        } else {
            dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);   // no match: skip one char
        }
    }
}
return dp[m][n];
```

### Edit Distance Template

```java
int m = word1.length(), n = word2.length();
int[][] dp = new int[m+1][n+1];
for (int i = 0; i <= m; i++) { dp[i][0] = i; }  // delete i chars to reach empty
for (int j = 0; j <= n; j++) { dp[0][j] = j; }  // insert j chars from empty

for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        if (word1.charAt(i-1) == word2.charAt(j-1)) {
            dp[i][j] = dp[i-1][j-1];                        // free: chars match
        } else {
            dp[i][j] = 1 + Math.min(dp[i-1][j-1],          // replace
                           Math.min(dp[i-1][j],              // delete from word1
                                    dp[i][j-1]));            // insert into word1
        }
    }
}
return dp[m][n];
```

### Grid Paths Template

```java
int[][] dp = new int[m][n];
for (int i = 0; i < m; i++) { dp[i][0] = 1; }  // only one way along left edge
for (int j = 0; j < n; j++) { dp[0][j] = 1; }  // only one way along top edge

for (int i = 1; i < m; i++) {
    for (int j = 1; j < n; j++) {
        dp[i][j] = dp[i-1][j] + dp[i][j-1];    // came from above or from left
    }
}
return dp[m-1][n-1];
```

---

## Variants 变形

| Variant | Key difference in recurrence | Example |
|---|---|---|
| LCS (subsequence) | mismatch → `max(skip-either)` | LC 1143 |
| LCS (substring) | mismatch → reset to `0` (no gaps allowed) | LC 718 |
| Edit distance | 3-way min: replace / delete / insert | LC 72 |
| Unique paths | sum from above and left | LC 62 |
| Unique paths + obstacles | same but skip obstacle cells | LC 63 |
| Maximal square of 1s | `min(left, up, diagonal) + 1` | LC 221 |
| Wildcard `*` | `dp[i][j] = dp[i-1][j] \|\| dp[i][j-1]` | LC 44 |

---

## Key Examples 关键例题

### Longest Common Subsequence (LC 1143)

```java
public int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[][] dp = new int[m+1][n+1];   // +1 for the empty-string base case row/col
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (text1.charAt(i-1) == text2.charAt(j-1)) {
                dp[i][j] = dp[i-1][j-1] + 1;               // match: both chars consumed
            } else {
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]); // no match: skip the worse char
            }
        }
    }
    return dp[m][n];
}
```

---

### Edit Distance (LC 72)

```java
public int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m+1][n+1];
    for (int i = 0; i <= m; i++) { dp[i][0] = i; }  // cost to delete all of word1
    for (int j = 0; j <= n; j++) { dp[0][j] = j; }  // cost to insert all of word2
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (word1.charAt(i-1) == word2.charAt(j-1)) {
                dp[i][j] = dp[i-1][j-1];                    // chars match: free
            } else {
                dp[i][j] = 1 + Math.min(dp[i-1][j-1],      // replace
                               Math.min(dp[i-1][j],          // delete
                                        dp[i][j-1]));        // insert
            }
        }
    }
    return dp[m][n];
}
```

---

### Maximal Square (LC 221)

`dp[i][j]` = side length of the largest all-1s square whose **bottom-right corner** is `(i,j)`

```
Why min(left, up, diagonal)?
If any of the three neighboring squares is smaller, the current square is limited by it.
Think of it as: the square can only be as large as its shortest "neighboring arm".

Example:
  1 1 1        dp:  1 1 1
  1 1 1    →       1 2 2
  1 1 1             1 2 3   ← dp[2][2] = min(2,2,2)+1 = 3, area = 9
```

```java
public int maximalSquare(char[][] matrix) {
    int m = matrix.length, n = matrix[0].length, side = 0;
    int[][] dp = new int[m+1][n+1];   // +1 padding to avoid bounds checks
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (matrix[i-1][j-1] == '1') {
                // largest square ending here = min of three neighbors + 1
                dp[i][j] = Math.min(dp[i-1][j],
                            Math.min(dp[i][j-1], dp[i-1][j-1])) + 1;
                side = Math.max(side, dp[i][j]);
            }
            // if '0': dp[i][j] stays 0 — no square can end here
        }
    }
    return side * side;
}
```

---

### Unique Paths with Obstacles (LC 63)

```java
public int uniquePathsWithObstacles(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dp = new int[m][n];
    // init left column: stop filling once we hit an obstacle (all below are 0)
    for (int i = 0; i < m && grid[i][0] == 0; i++) { dp[i][0] = 1; }
    // init top row: same logic
    for (int j = 0; j < n && grid[0][j] == 0; j++) { dp[0][j] = 1; }
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            if (grid[i][j] == 0) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1]; // sum of paths from above and left
            }
            // if obstacle: dp[i][j] stays 0 — no path can pass through here
        }
    }
    return dp[m-1][n-1];
}
```

---

## Space Optimization: Rolling 1D Array 空间优化

When the recurrence only needs the previous row, we can replace the full 2D table with a single 1D array.

**LCS space-optimized:**

```java
// Instead of dp[m+1][n+1], use a single dp[n+1] representing the current row.
// Problem: dp[i-1][j-1] (the diagonal) gets overwritten before we use it.
// Fix: save it in variable `prev` before the overwrite.

int[] dp = new int[n + 1];
for (int i = 1; i <= m; i++) {
    int prev = 0;              // prev = dp[i-1][j-1]: diagonal value before overwrite
    for (int j = 1; j <= n; j++) {
        int temp = dp[j];      // save current dp[j] = dp[i-1][j] before it's overwritten
        if (s1.charAt(i-1) == s2.charAt(j-1)) {
            dp[j] = prev + 1;  // diagonal: use the saved value
        } else {
            dp[j] = Math.max(dp[j], dp[j-1]);  // dp[j]=above (old), dp[j-1]=left (new)
        }
        prev = temp;           // slide: next j's diagonal = current j's old value
    }
}
return dp[n];
```

Space goes from O(m×n) → O(n). Use this when the interviewer asks for space optimization.

---

## LCS vs LCSS — A Common Confusion

| | LCS (Subsequence) | LCSS (Substring) |
|---|---|---|
| "Contiguous?" | No — characters can be scattered | Yes — characters must be adjacent |
| Mismatch handling | `max(skip-either)` → carry over | Reset to `0` |
| Example | `"ABCB"` and `"BDCAB"` → `"BCB"` (len 3) | `"ABCB"` and `"BDCAB"` → `"BC"` (len 2) |
| Answer location | `dp[m][n]` | Track `max` during fill |

```java
// LCSS (Substring): only change is the mismatch case
if (s1.charAt(i-1) == s2.charAt(j-1)) {
    dp[i][j] = dp[i-1][j-1] + 1;
    max = Math.max(max, dp[i][j]);
} else {
    dp[i][j] = 0;   // ← reset: substring must be contiguous, no "skipping" allowed
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Topic | Rule |
|---|---|
| Use 1-indexed dp | `dp[i][j]` covers first `i` and `j` chars → row/col 0 = empty string = free base case |
| Always fill base cases | Edit distance: `dp[i][0]=i`, `dp[0][j]=j`. Forgetting these = all wrong values |
| LCS vs LCSS | Subsequence: carry the max on mismatch. Substring: reset to 0 on mismatch. |
| Maximal square: min of 3 | The square is bottlenecked by the smallest of the three neighboring squares |
| Obstacles in grid | Stop initializing the edge row/col once you hit an obstacle — everything beyond is 0 |
| Space opt: save diagonal | `prev` variable saves `dp[i-1][j-1]` before `dp[j]` is overwritten |
| Read the answer | For most: `dp[m][n]`. For max-tracking (LCSS, Maximal Square): track `max` during fill |

---

## Practice Problems 练习题

| Difficulty | Problem | Focus |
|---|---|---|
| Medium | LC 62 Unique Paths | Grid DP, base cases |
| Medium | LC 63 Unique Paths II | Grid DP with obstacles |
| Medium | LC 1143 Longest Common Subsequence | Two-string DP |
| Medium | LC 718 Maximum Length of Repeated Subarray | LCSS (reset on mismatch) |
| Hard | LC 72 Edit Distance | 3-operation recurrence |
| Hard | LC 221 Maximal Square | min-of-three-neighbors |
| Hard | LC 97 Interleaving String | Two-string paths |
| Hard | LC 44 Wildcard Matching | Pattern matching with `*` |

**Recommended order:** 62 → 63 → 1143 → 718 → 72 → 221 → 97 → 44

> **Suggested approach:** For each problem, first write the `dp[i][j]` definition in a comment at the top of your function. Then derive the recurrence on paper. Then code. This order prevents the most common mistakes.

---

## One-line Summary

```
2D DP = dp[i][j] solves subproblem on first i and j items; match → extend diagonal,
mismatch → take best neighbor (LCS) or reset (substring) or try all 3 ops (edit distance).
```
