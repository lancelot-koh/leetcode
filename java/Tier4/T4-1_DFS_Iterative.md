# T4-1 — Iterative DFS 迭代深度优先搜索

> **Core idea:** Replace the call stack with an explicit `Deque` (used as stack). Push neighbors in reverse order so the first neighbor is processed first. Eliminates stack-overflow risk on deep graphs.
> **核心思想：** 用显式`Deque`（作为栈）替代调用栈。逆序压入邻居使第一个邻居优先处理。消除深图递归栈溢出风险。
>
> Complexity: O(V + E) — same as recursive DFS.
> Full reference: `BFS_DFS/description.md`

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
    stack.push(start);

    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (visited[node]) continue;
        visited[node] = true;

        // Process node here
        for (int nei : adj.get(node))
            if (!visited[nei]) stack.push(nei);
    }
}
```

### Iterative Pre-order Tree Traversal

```java
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);

    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        result.add(node.val);           // visit before children
        if (node.right != null) stack.push(node.right);  // right first (popped last)
        if (node.left  != null) stack.push(node.left);
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
    if (root == null) return result;
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);

    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        result.add(0, node.val);    // prepend = reverse of pre-order variant
        if (node.left  != null) stack.push(node.left);
        if (node.right != null) stack.push(node.right);
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
