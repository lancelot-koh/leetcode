# 🌳 DFS & Backtracking - Exhaustive Search Pattern

**Master recursive exploration and undo mechanics**

---

## Interview Frequency: **8% of problems** ⭐⭐⭐

---

## The Core Concept

DFS explores deeply, Backtracking adds **undo** capability.

```
Pattern: Choose → Explore → Undo (backtrack)

def backtrack(path, remaining):
    if is_solution(path):
        add_to_result(path)
        return
    
    for choice in get_choices(remaining):
        path.append(choice)           # Choose
        backtrack(path, remaining-choice)  # Explore
        path.pop()                    # Undo (backtrack!)
```

---

## Code Templates

### Template 1: Permutations (All Orders)

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, new boolean[nums.length]);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> path, 
                      int[] nums, boolean[] used) {
    if (path.size() == nums.length) {
        result.add(new ArrayList<>(path));
        return;
    }
    
    for (int i = 0; i < nums.length; i++) {
        if (!used[i]) {
            path.add(nums[i]);
            used[i] = true;
            
            backtrack(result, path, nums, used);
            
            path.remove(path.size() - 1);  // Undo
            used[i] = false;
        }
    }
}
```

---

### Template 2: Combinations (Choose K Items)

```java
public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), 1, n, k);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> path,
                      int start, int n, int k) {
    if (path.size() == k) {
        result.add(new ArrayList<>(path));
        return;
    }
    
    for (int i = start; i <= n; i++) {
        path.add(i);
        backtrack(result, path, i + 1, n, k);
        path.remove(path.size() - 1);  // Undo
    }
}
```

---

### Template 3: N-Queens (Place Queens Safely)

```java
public List<List<String>> solveNQueens(int n) {
    List<List<String>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), n, new HashSet<>(), 
              new HashSet<>(), new HashSet<>());
    return result;
}

private void backtrack(List<List<String>> result, List<Integer> path, 
                      int n, Set<Integer> cols, Set<Integer> diag1, 
                      Set<Integer> diag2) {
    if (path.size() == n) {
        result.add(buildBoard(path));
        return;
    }
    
    int row = path.size();
    for (int col = 0; col < n; col++) {
        if (cols.contains(col) || diag1.contains(row - col) || 
            diag2.contains(row + col)) {
            continue;  // Invalid position
        }
        
        path.add(col);
        cols.add(col);
        diag1.add(row - col);
        diag2.add(row + col);
        
        backtrack(result, path, n, cols, diag1, diag2);
        
        path.remove(path.size() - 1);
        cols.remove(col);
        diag1.remove(row - col);
        diag2.remove(row + col);
    }
}

private List<String> buildBoard(List<Integer> path) {
    List<String> board = new ArrayList<>();
    for (int col : path) {
        char[] row = new char[path.size()];
        Arrays.fill(row, '.');
        row[col] = 'Q';
        board.add(new String(row));
    }
    return board;
}
```

---

## Quick Examples

**Permutation (LC46):** All orderings
**Combination (LC77):** Choose k from n
**N-Queens (LC51):** Place n queens
**Word Search (LC79):** DFS on grid
**Sudoku (LC37):** Constraint satisfaction

---

## ⚠️ Key Points

- **Must undo after exploring!** (backtrack.pop())
- **Avoid revisiting:** Use visited set or used array
- **Prune invalid states:** Check constraints early
- **Time:** Usually exponential (generates all solutions)
- **Space:** O(h) recursion depth + solution storage

---

**Master backtracking. It solves 8% of interview problems.** 🚀
