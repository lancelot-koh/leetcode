# T4-1 — Iterative DFS 迭代深度优先搜索

> **Core idea:** Replace the call stack with an explicit `Deque` (used as stack). Push neighbors in reverse order so the first neighbor is processed first. Eliminates stack-overflow risk on deep graphs.
> **核心思想：** 用显式`Deque`（作为栈）替代调用栈。逆序压入邻居使第一个邻居优先处理。消除深图递归栈溢出风险。
>
> Complexity: O(V + E) — same as recursive DFS.
> Full reference: `BFS_DFS/description.md`

---

## How It Works — Mental Model 直觉理解

Recursive DFS borrows the CPU call stack to remember where to return after visiting a child. Iterative DFS replaces that implicit stack with an explicit `Deque`: instead of "call a function that will return later," you "push a node that you will pop and process later." The LIFO property of a stack naturally mirrors depth-first exploration — the most recently discovered node is always processed next, so the algorithm drives as deep as possible before backtracking. If you push right before left, the left child sits on top and is processed first, matching the natural left-to-right DFS order. Without the `visited` check on pop (not on push), the same node can be processed multiple times because it may be pushed by multiple neighbors before any of them are popped.

**Key invariant:** Every node on the stack has been discovered but not yet fully processed. A node is marked visited only when it is popped, ensuring each node is processed exactly once even if it was pushed multiple times.

**Common mistake:** Checking `visited` at push time instead of pop time. This works for trees but silently skips nodes in graphs when a node is reachable via multiple paths — the second push is rejected at push time, but the first push may never be processed if it is buried under other items.

---

## Step-by-Step Trace (Iterative Graph DFS)

```
Graph: 0→1, 0→2, 1→3   Start: 0

Stack: [0]        visited: {}
Pop 0 → mark visited, push neighbors [2,1] (right first so 1 is on top)
Stack: [2,1]      visited: {0}

Pop 1 → mark visited, push neighbor [3]
Stack: [2,3]      visited: {0,1}

Pop 3 → mark visited, no unvisited neighbors
Stack: [2]        visited: {0,1,3}

Pop 2 → mark visited, no unvisited neighbors
Stack: []         visited: {0,1,2,3}   ✓ DFS order: 0,1,3,2
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| DFS on deep graphs (n > 10⁴ nodes) | Stack overflow risk with recursion |
| Iterative pre/in/post-order tree traversal | LC 144, 94, 145 |
| Graph DFS without recursion limit | Large grids, very deep trees |
| Path tracking without recursion | Explicit path on stack |

**When to prefer recursive DFS:** Shallow graphs, backtracking (unchoose step is simpler in recursion).

---

## Core Templates 核心模板

### Iterative DFS — graph

```java
void dfsIterative(int start, List<List<Integer>> adj, boolean[] visited) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(start);  // seed the stack with the source node

    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (visited[node]) { continue; }  // may have been pushed multiple times; skip duplicates
        visited[node] = true;             // mark AFTER pop, not at push

        // Process node here
        for (int nei : adj.get(node)) {
            if (!visited[nei]) { stack.push(nei); }  // push unvisited neighbors (may push duplicates; handled on pop)
        }
    }
}
```

### Iterative Pre-order Tree Traversal

```java
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) { return result; }
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);

    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        result.add(node.val);           // visit before children (pre = root first)
        if (node.right != null) { stack.push(node.right); }  // right pushed first → popped last → left processed first
        if (node.left  != null) { stack.push(node.left); }   // left pushed last → popped first → correct pre-order
    }
    return result;
}
```

### Iterative In-order (see T1-7 for the canonical version)

```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode cur = root;

    while (cur != null || !stack.isEmpty()) {
        while (cur != null) { stack.push(cur); cur = cur.left; }  // go left
        cur = stack.pop();
        result.add(cur.val);    // visit
        cur = cur.right;        // go right
    }
    return result;
}
```

### Iterative Post-order (two-stack trick)

```java
public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) { return result; }
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);

    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        result.add(0, node.val);    // prepend instead of append — this reverses the traversal order
        // Push left before right so right is processed first → produces root→right→left order
        // When we prepend each visit, that reverses to left→right→root = correct post-order
        if (node.left  != null) { stack.push(node.left); }
        if (node.right != null) { stack.push(node.right); }
    }
    return result;   // already in post-order (root last)
}
```

---

## Key Differences: Iterative vs Recursive

| Aspect | Recursive | Iterative |
|---|---|---|
| Stack depth | Call stack (limited, ~10⁴) | Heap-allocated Deque (unlimited) |
| Backtracking | Natural (unwind after return) | Must store state explicitly on stack |
| Code clarity | Simpler | More verbose |
| Post-order | Easy (`left; right; visit`) | Needs trick (prepend or two-stack) |

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `visited` check AFTER pop | Same node can be pushed multiple times; check on pop not push |
| Push right before left | Stack is LIFO → push right first so left is processed first |
| Post-order: prepend to result | Pop order (root→right→left) reversed = left→right→root |
| Path tracking | Push `(node, path)` pair, or reconstruct from `parent[]` array |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 144 Binary Tree Preorder Traversal |
| Easy | LC 94 Binary Tree Inorder Traversal |
| Hard | LC 145 Binary Tree Postorder Traversal |
| Medium | LC 200 Number of Islands (iterative DFS) |

**Order:** 144 → 94 → 145 → 200

---

## One-line Summary

```
Iterative DFS = explicit Deque as stack; check visited on pop; push right-before-left for correct traversal order.
迭代DFS = 显式Deque当栈；出栈时检查已访问；先压右再压左保证遍历顺序正确。
```
