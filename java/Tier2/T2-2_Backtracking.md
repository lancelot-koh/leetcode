# T2-2 — Backtracking 回溯

> **Core idea:** Build candidates step by step; prune (backtrack) as soon as the current path cannot lead to a valid solution. Framework: Choose → Explore → Unchoose.
> **核心思想：** 逐步构建候选解，一旦当前路径不可能产生有效解就剪枝回溯。框架：选择 → 探索 → 撤销。
>
> Complexity: O(k^n) worst case; pruning makes practical performance far better.
> Full reference: `Backtracking/description.md`

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| All subsets / power set | LC 78 |
| All permutations | LC 46, 47 |
| All combinations | LC 77, 39 |
| N-Queens / Sudoku | LC 51, 37 |
| Partition string into valid parts | LC 131 |

**Signal:** problem says "find ALL", "generate ALL", "list ALL valid" → backtracking.

---

## How it Works — Mental Model 算法原理

Backtracking is systematic trial and error on a **decision tree**. At each node you make one choice (add an element, place a queen), recurse to explore all subtrees rooted at that choice, then undo the choice so the parent node can try its next option. The power comes from **pruning**: if you can detect early that a partial candidate can never be extended to a valid solution, you cut off that entire subtree without visiting it. Without the "unchoose" step, each recursive call would see a corrupted state and produce wrong results. Without pruning, backtracking degenerates to brute-force enumeration.

**Key invariant:** When `backtrack` is called, `current` contains only choices made on the current root-to-node path; when it returns, `current` is restored exactly to its state before the call.

**Common mistake / gotcha:** Adding `current` directly to `result` instead of copying it: `result.add(current)` stores a reference that will be emptied by later `remove` calls. Always use `result.add(new ArrayList<>(current))`.

---

## Step-by-Step Trace 执行步骤示意

Example: Subsets of `[1, 2, 3]`
```
backtrack(start=0, cur=[])     → add [] to result
  choose 1 → backtrack(start=1, cur=[1]) → add [1]
    choose 2 → backtrack(start=2, cur=[1,2]) → add [1,2]
      choose 3 → backtrack(start=3, cur=[1,2,3]) → add [1,2,3]; return
    unchoose 2 → cur=[1]
    choose 3 → backtrack(start=3, cur=[1,3]) → add [1,3]; return
  unchoose 1 → cur=[]
  choose 2 → backtrack(start=2, cur=[2]) → add [2]
    choose 3 → backtrack(start=3, cur=[2,3]) → add [2,3]; return
  unchoose 2; choose 3 → add [3]
Result: [], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]
```

---

## Core Template 核心模板

```java
List<List<Integer>> result = new ArrayList<>();

private void backtrack(/* params */, int start, List<Integer> current) {
    // Base case: current is a complete valid solution
    if (/* complete */) {
        result.add(new ArrayList<>(current));   // COPY — current will be mutated later
        return;
    }

    for (int i = start; i < n; i++) {
        // Pruning: skip invalid choices early (avoids entire subtree)
        if (/* invalid */) { continue; }

        current.add(choice[i]);                  // Choose: extend the current path
        backtrack(/* params */, i + 1, current); // Explore: all completions of this path
        current.remove(current.size() - 1);      // Unchoose: restore state for next iteration
    }
}
```

---

## Pattern Comparison 模式对比

| Pattern | `start` | `used[]` | Add to result when |
|---|---|---|---|
| Subsets | `i+1` (no reuse) | No | At every node (before loop) |
| Permutations | `0` always | Yes (track used) | At leaf (size == n) |
| Combinations | `i+1` | No | At leaf (size == k) |
| Combination Sum (reuse) | `i` (same index) | No | remain == 0 |

---

## Key Examples 关键例题

### Subsets (LC 78)
```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    backtrack(nums, 0, new ArrayList<>(), res);
    return res;
}
private void backtrack(int[] nums, int start, List<Integer> cur, List<List<Integer>> res) {
    res.add(new ArrayList<>(cur));        // add at every node
    for (int i = start; i < nums.length; i++) {
        cur.add(nums[i]);
        backtrack(nums, i + 1, cur, res);
        cur.remove(cur.size() - 1);
    }
}
```

### Permutations (LC 46)
```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    backtrack(nums, new boolean[nums.length], new ArrayList<>(), res);
    return res;
}
private void backtrack(int[] nums, boolean[] used, List<Integer> cur, List<List<Integer>> res) {
    if (cur.size() == nums.length) { res.add(new ArrayList<>(cur)); return; }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) { continue; }
        used[i] = true; cur.add(nums[i]);
        backtrack(nums, used, cur, res);
        used[i] = false; cur.remove(cur.size() - 1);
    }
}
```

### Combination Sum (LC 39) — reuse allowed
```java
private void backtrack(int[] candidates, int remain, int start, List<Integer> cur, List<List<Integer>> res) {
    if (remain == 0) { res.add(new ArrayList<>(cur)); return; }
    for (int i = start; i < candidates.length; i++) {
        if (candidates[i] > remain) { break; }          // pruning: array is sorted, so all further are also too big
        cur.add(candidates[i]);
        backtrack(candidates, remain - candidates[i], i, cur, res);  // i (not i+1) allows reusing same element
        cur.remove(cur.size() - 1);
    }
}
```

### Subsets / Permutations with Duplicates — dedup pattern
```java
Arrays.sort(nums);    // sort first so duplicates are adjacent

for (int i = start; i < nums.length; i++) {
    // Skip same value AT SAME LEVEL of recursion tree.
    // i > start (not i > 0) ensures we only skip duplicate siblings,
    // not duplicate values chosen at different depths of the tree.
    if (i > start && nums[i] == nums[i - 1]) { continue; }
    // ...
}
```

### N-Queens (LC 51)
```java
boolean[] cols, diag1, diag2;

private void backtrack(int row, int n, List<String> board, List<List<String>> res) {
    if (row == n) { res.add(new ArrayList<>(board)); return; }
    for (int col = 0; col < n; col++) {
        if (cols[col] || diag1[row-col+n] || diag2[row+col]) { continue; }
        cols[col] = diag1[row-col+n] = diag2[row+col] = true;
        board.add("Q".repeat(col) + "Q" + ".".repeat(n-col-1));
        backtrack(row + 1, n, board, res);
        cols[col] = diag1[row-col+n] = diag2[row+col] = false;
        board.remove(board.size() - 1);
    }
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Always COPY before adding | `result.add(new ArrayList<>(current))` not `result.add(current)` |
| `i > start` for dedup | Skip same value at SAME level; `i > 0` would skip across levels |
| `i + 1` vs `i` | `i+1` = no reuse; `i` = reuse same element (combination sum) |
| Prune early | Sort candidates then `if (candidates[i] > remain) break` |
| StringBuilder for strings | `sb.deleteCharAt(sb.length()-1)` to unchoose |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 78 Subsets, LC 90 Subsets II |
| Medium | LC 46 Permutations, LC 47 Permutations II |
| Medium | LC 77 Combinations, LC 39 Combination Sum |
| Medium | LC 40 Combination Sum II, LC 131 Palindrome Partitioning |
| Hard | LC 51 N-Queens, LC 37 Sudoku Solver |

**Order:** 78 → 46 → 77 → 39 → 90 → 47 → 131 → 51 → 37

---

## One-line Summary

```
Backtracking = choose, explore, unchoose; prune invalid branches before recursing.
回溯 = 选择、探索、撤销；递归前提前剪掉无效分支。
```
