# Backtracking 回溯

> **Core idea:** Build candidates incrementally; abandon (backtrack) as soon as the current candidate cannot lead to a valid solution.
> **核心思想：** 逐步构建候选解；一旦当前候选不可能成为有效解就立即放弃（回溯）。
>
> Framework: **Choose → Explore → Unchoose** (make a choice, recurse, undo the choice).
> 框架：**选择 → 探索 → 撤销**（做选择，递归，撤销选择）。
>
> Complexity: O(k^n) worst case, but pruning makes it far faster in practice.
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| all subsets / power set | 所有子集 / 幂集 |
| all permutations | 所有排列 |
| all combinations | 所有组合 |
| generate all... | 生成所有... |
| N-Queens / Sudoku | N皇后 / 数独 |
| partition string into palindromes | 分割回文串 |
| path in maze | 迷宫路径 |
| word search in board | 单词搜索 |

**When NOT to use 不适用**

- You only need **one** answer, and a greedy/DP approach is available → use that
- The search space is independent sub-problems → use DP
- Backtracking is correct but too slow → identify a DP recurrence instead

**Backtracking vs DP**

| | Backtracking | DP |
|---|---|---|
| Explores | all valid paths explicitly | overlapping sub-problems via memoization |
| Returns | a list of all solutions | a single optimal value |
| State space | tree-shaped | DAG with shared nodes |
| Use when | need all solutions / enumeration | need count / optimal value |

---

## 2. Quick Decision Guide 快速判断

```
All subsets (include or not each element)?     → Pattern 1: Subsets
All permutations (order matters)?              → Pattern 2: Permutations
All combinations (choose k from n)?            → Pattern 3: Combinations
Grid / board placement problem?                → Pattern 4: Board (N-Queens, Sudoku)
Partition string into valid parts?             → Pattern 5: String partition
```

---

## 3. Patterns 模式

---

### Pattern 1 — Subsets 子集

**When:** generate all possible subsets (power set) of a set.
**适用：** 生成所有可能的子集（幂集）。

**Template 模板**

```java
List<List<Integer>> result = new ArrayList<>();

public List<List<Integer>> subsets(int[] nums) {
    backtrack(nums, 0, new ArrayList<>());
    return result;
}

private void backtrack(int[] nums, int start, List<Integer> current) {
    result.add(new ArrayList<>(current));   // add at every node (not just leaf)

    for (int i = start; i < nums.length; i++) {
        current.add(nums[i]);               // Choose
        backtrack(nums, i + 1, current);    // Explore (i+1 = no reuse)
        current.remove(current.size() - 1); // Unchoose
    }
}
```

**Key insight 核心原理**

Add to result **at every node** (before choosing), not just at leaves — every partial set is a valid subset.
每个节点都加入结果（选择之前），不只在叶子节点，因为每个中间状态都是有效子集。

**Variants 变形**

| Variant | Change | Example |
|---|---|---|
| Subsets with duplicates | sort + `if (i > start && nums[i] == nums[i-1]) continue` | LC 90 |
| Subsets of size k | add to result only when `current.size() == k` | LC 77 |

---

### Pattern 2 — Permutations 排列

**When:** generate all orderings of elements.
**适用：** 生成所有元素的排列。

**Template 模板**

```java
List<List<Integer>> result = new ArrayList<>();
boolean[] used;

public List<List<Integer>> permute(int[] nums) {
    used = new boolean[nums.length];
    backtrack(nums, new ArrayList<>());
    return result;
}

private void backtrack(int[] nums, List<Integer> current) {
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) { continue; }
        used[i] = true;                         // Choose
        current.add(nums[i]);
        backtrack(nums, current);               // Explore
        current.remove(current.size() - 1);    // Unchoose
        used[i] = false;
    }
}
```

**Difference from subsets:** permutations start from index 0 every time (order matters), controlled by `used[]` instead of `start`.
与子集的区别：排列每次从0开始遍历（顺序有意义），用 `used[]` 而非 `start` 控制重复使用。

**Variants 变形**

| Variant | Change | Example |
|---|---|---|
| Permutations with duplicates | sort + `if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue` | LC 47 |
| Next permutation (in-place) | two-pointer swap trick | LC 31 |

---

### Pattern 3 — Combinations 组合

**When:** choose k elements from n (order doesn't matter, no repetition).
**适用：** 从n个元素中选k个（顺序无关，不重复）。

**Template 模板**

```java
private void backtrack(int n, int k, int start, List<Integer> current) {
    if (current.size() == k) {
        result.add(new ArrayList<>(current));
        return;
    }
    // Pruning: remaining elements must be enough to fill k slots
    for (int i = start; i <= n - (k - current.size()) + 1; i++) {
        current.add(i);                         // Choose
        backtrack(n, k, i + 1, current);        // Explore
        current.remove(current.size() - 1);    // Unchoose
    }
}
```

**Pruning 剪枝**

`i <= n - (k - current.size()) + 1` skips iterations where there aren't enough remaining elements.
当剩余元素不足以填满 k 个时，提前终止循环。

**Variants 变形**

| Variant | Change | Example |
|---|---|---|
| Combination sum (elements reusable) | `backtrack(candidates, target - val, i, ...)` (pass `i` not `i+1`) | LC 39 |
| Combination sum II (no reuse, duplicates) | sort + skip duplicates + pass `i+1` | LC 40 |
| Combinations from 1..n | iterate 1..n | LC 77 |

**Example: Combination Sum (LC 39)**

```java
private void backtrack(int[] candidates, int remain, int start, List<Integer> current) {
    if (remain == 0) { result.add(new ArrayList<>(current)); return; }
    if (remain < 0) { return; }

    for (int i = start; i < candidates.length; i++) {
        current.add(candidates[i]);
        backtrack(candidates, remain - candidates[i], i, current); // i not i+1 (reuse allowed)
        current.remove(current.size() - 1);
    }
}
```

---

### Pattern 4 — Board Problems (N-Queens, Sudoku) 棋盘问题

**When:** place elements on a board with row/column/diagonal constraints.
**适用：** 在棋盘上放置元素，有行列对角线约束。

**Template 模板 (N-Queens)**

```java
boolean[] cols, diag1, diag2;   // column and two diagonals occupied

private void backtrack(int row, int n, List<String> board) {
    if (row == n) { result.add(new ArrayList<>(board)); return; }

    for (int col = 0; col < n; col++) {
        if (cols[col] || diag1[row - col + n] || diag2[row + col]) { continue; }

        cols[col] = diag1[row - col + n] = diag2[row + col] = true;  // Choose
        board.add(buildRow(col, n));
        backtrack(row + 1, n, board);                                  // Explore
        cols[col] = diag1[row - col + n] = diag2[row + col] = false; // Unchoose
        board.remove(board.size() - 1);
    }
}
```

**Diagonal encoding:**
- `\` diagonal: `row - col + n` (constant for same diagonal)
- `/` diagonal: `row + col` (constant for same anti-diagonal)

**Variants 变形**

| Variant | Example |
|---|---|
| N-Queens (all solutions) | LC 51 |
| N-Queens II (count solutions) | LC 52 |
| Sudoku solver | LC 37 |

---

### Pattern 5 — String Partition 字符串分割

**When:** partition a string into valid segments (palindromes, words in dictionary).
**适用：** 把字符串分割成满足条件的段（回文、字典中的词）。

**Template 模板 (palindrome partition)**

```java
private void backtrack(String s, int start, List<String> current) {
    if (start == s.length()) {
        result.add(new ArrayList<>(current));
        return;
    }
    for (int end = start + 1; end <= s.length(); end++) {
        String sub = s.substring(start, end);
        if (!isPalindrome(sub)) { continue; }      // prune invalid segments
        current.add(sub);                       // Choose
        backtrack(s, end, current);            // Explore (next start = end)
        current.remove(current.size() - 1);   // Unchoose
    }
}
```

**Variants 变形**

| Variant | Example |
|---|---|
| Palindrome partitioning | LC 131 |
| Word break (all sentences) | LC 140 |
| Restore IP addresses | LC 93 |
| Letter combinations phone number | LC 17 |

---

## 4. Advanced Skills 进阶技能

### Skill 1 — Pruning is Everything 剪枝决定性能

Backtracking without pruning is brute-force. Add constraints as early as possible:

```java
// In combination sum: stop when remain < 0
if (remain < 0) { return; }

// In combinations: stop when not enough elements left
for (int i = start; i <= n - (k - current.size()) + 1; i++)

// In N-Queens: check constraints before placing
if (cols[col] || diag1[...] || diag2[...]) { continue; }
```

### Skill 2 — Deduplication Pattern 去重模板

When input has duplicates and you want unique results:

```java
Arrays.sort(nums);  // sort first

for (int i = start; i < nums.length; i++) {
    // Skip same value at same level of recursion tree
    if (i > start && nums[i] == nums[i - 1]) { continue; }
    ...
}
```

`i > start` (not `i > 0`) — skip only if same value appears at the **same level**, not across levels.
用 `i > start` 而非 `i > 0`：只跳过同一层出现的重复，不跳跨层重复。

### Skill 3 — `i + 1` vs `i` (Reuse Control) 是否允许重用

| Case | Next call passes | Meaning |
|---|---|---|
| No reuse (standard) | `i + 1` | each element used at most once |
| Reuse allowed (combination sum) | `i` | same element can be picked again |
| Permutation | `0` (with `used[]`) | all elements are candidates each time |

### Skill 4 — Always Copy Before Adding 加入结果前要复制

```java
result.add(new ArrayList<>(current));   // CORRECT: snapshot
result.add(current);                    // WRONG: reference, will be modified later
```

### Skill 5 — StringBuilder for String Building 字符串构建用StringBuilder

When building strings character by character:
```java
sb.append(c);                     // Choose
backtrack(sb, ...);               // Explore
sb.deleteCharAt(sb.length() - 1); // Unchoose
```

---

## 5. Interview Script 面试话术

**English:**
> I'd use backtracking because the problem asks for all valid [subsets / permutations / arrangements]. The framework is: at each step, choose an element, recurse to explore further, then unchoose to restore state. I prune branches early by checking constraints before recursing, which avoids exploring clearly invalid paths.

**中文：**
> 我会用回溯，因为题目要求所有有效的[子集/排列/方案]。框架是：每步选一个元素，递归继续探索，然后撤销选择恢复状态。在递归前提前检查约束来剪枝，避免探索明显无效的分支。

---

## 6. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Subsets | 78, 90 |
| 2. Permutations | 46, 47 |
| 3. Combinations | 77, 39, 40 |
| 4. Board | 51, 52, 37 |
| 5. String partition | 131, 93, 17, 140 |

**Recommended order:** 78 → 46 → 77 → 39 → 90 → 47 → 131 → 51 → 37

---

## 7. One-line Summary 一句话总结

```
Backtracking = choose → explore → unchoose; prune branches that cannot lead to valid solutions.
回溯 = 选择 → 探索 → 撤销；提前剪掉不可能产生有效解的分支。
```
