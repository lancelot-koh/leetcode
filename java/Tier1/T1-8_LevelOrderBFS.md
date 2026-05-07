# T1-8 — Level-Order BFS with Levels 带层信息的层序遍历

> **Core idea:** BFS on a tree, processing all nodes at depth d before moving to depth d+1. Capture `queue.size()` at the start of each level to know exactly how many nodes belong to that level.
> **核心思想：** 在树上BFS，处理完第d层所有节点后再处理第d+1层。每层开始时快照 `queue.size()` 得知本层节点数。
>
> Complexity: O(n) time, O(w) space where w = max width of tree.
> Full reference: `BFS_DFS/description.md` BFS Pattern 2

---

## When to Use 什么时候用

| Problem type | Example |
|---|---|
| Level-by-level result | LC 102 Level Order, LC 103 Zigzag |
| Right-side view | LC 199 (last node of each level) |
| Max / min depth | LC 104, 111 |
| Average of each level | LC 637 |
| Connect next-right pointers | LC 116, 117 |
| Cousins check (same level, different parent) | LC 993 |

---

## How it Works — Mental Model 原理与直觉

At the start of each outer `while` iteration, the queue contains exactly the nodes at the current depth — no more, no less. By snapshotting `queue.size()` before the inner loop, we fix how many nodes we process as "this level." All children added during the inner loop land in the queue *after* this count, so they are naturally deferred to the next outer iteration.

This `levelSize` snapshot is the one line that separates "plain BFS" (which doesn't know level boundaries) from "level-aware BFS." Without it, you'd need a sentinel node or two alternating queues to track where levels end.

**Invariant:** At the start of each outer `while` iteration, the queue holds all and only the nodes at depth `d` (for increasing values of `d`). After processing the inner loop, they are replaced by all nodes at depth `d+1`.

---

## Step-by-Step Trace — Level Order 执行追踪

```
Tree:    3
        / \
       9  20
         /  \
        15   7

Iteration 1: levelSize=1, poll 3, enqueue [9,20] → level=[3]
Iteration 2: levelSize=2, poll 9 (no children), poll 20 (enqueue 15,7) → level=[9,20]
Iteration 3: levelSize=2, poll 15, poll 7 (no children) → level=[15,7]
Queue empty → done.
Result: [[3],[9,20],[15,7]]
```

---

## Core Template 核心模板

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) { return result; }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int levelSize = queue.size();          // SNAPSHOT before inner loop — children added inside don't count
        List<Integer> level = new ArrayList<>();

        for (int i = 0; i < levelSize; i++) {   // process exactly this level's nodes
            TreeNode node = queue.poll();
            level.add(node.val);

            if (node.left  != null) { queue.offer(node.left); }   // these land AFTER levelSize boundary
            if (node.right != null) { queue.offer(node.right); }
        }
        result.add(level);   // finished one depth layer
    }
    return result;
}
```

**Key:** `int levelSize = queue.size()` snapshots the level boundary. Children added during the loop belong to the next level.
**关键：** `int levelSize = queue.size()` 快照当前层边界，循环中加入的子节点属于下一层。

---

## Variants 变形

| Variant | Change from base template | Example |
|---|---|---|
| Right-side view | Collect `i == levelSize - 1` node | LC 199 |
| Zigzag level order | Alternate `addFirst` vs `addLast` using Deque | LC 103 |
| Max depth | Count levels | LC 104 |
| Min depth | Return level when first leaf found | LC 111 |
| Level averages | Sum / levelSize | LC 637 |
| Largest values per level | `Collections.max(level)` | LC 515 |
| Connect next pointers | `prev.next = cur` within level loop | LC 116 |

---

## Key Examples 关键例题

### Right Side View (LC 199)
```java
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) { return result; }
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    while (!q.isEmpty()) {
        int sz = q.size();
        for (int i = 0; i < sz; i++) {
            TreeNode node = q.poll();
            if (i == sz - 1) { result.add(node.val); }   // rightmost node
            if (node.left  != null) { q.offer(node.left); }
            if (node.right != null) { q.offer(node.right); }
        }
    }
    return result;
}
```

### Zigzag Level Order (LC 103)
```java
public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) { return res; }
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    boolean leftToRight = true;

    while (!q.isEmpty()) {
        int sz = q.size();
        Deque<Integer> level = new ArrayDeque<>();
        for (int i = 0; i < sz; i++) {
            TreeNode node = q.poll();
            if (leftToRight) { level.addLast(node.val); }
            else             { level.addFirst(node.val); }  // reverse direction
            if (node.left  != null) { q.offer(node.left); }
            if (node.right != null) { q.offer(node.right); }
        }
        res.add(new ArrayList<>(level));
        leftToRight = !leftToRight;
    }
    return res;
}
```

### Minimum Depth (LC 111) — Return Early at First Leaf
```java
public int minDepth(TreeNode root) {
    if (root == null) { return 0; }
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    int depth = 1;
    while (!q.isEmpty()) {
        for (int sz = q.size(); sz > 0; sz--) {
            TreeNode node = q.poll();
            if (node.left == null && node.right == null) { return depth; }  // first leaf
            if (node.left  != null) { q.offer(node.left); }
            if (node.right != null) { q.offer(node.right); }
        }
        depth++;
    }
    return depth;
}
```

### Populating Next Right Pointers (LC 116)
```java
public Node connect(Node root) {
    if (root == null) { return null; }
    Queue<Node> q = new LinkedList<>();
    q.offer(root);
    while (!q.isEmpty()) {
        Node prev = null;
        for (int sz = q.size(); sz > 0; sz--) {
            Node node = q.poll();
            if (prev != null) { prev.next = node; }
            prev = node;
            if (node.left  != null) { q.offer(node.left); }
            if (node.right != null) { q.offer(node.right); }
        }
    }
    return root;
}
```

---

## Common Mistake / Gotcha 常见错误

**Max depth vs min depth confusion:** For max depth, you count all levels and return at the end. For min depth, you return as soon as you dequeue the *first* leaf — BFS guarantees this is the shallowest leaf. A common error is using the same max-depth logic (counting all levels) for min depth, which gives wrong results when the tree is not balanced. Additionally for min depth: a node with only one child is not a leaf — don't return early at a node that has one null child.

**`q.size()` inside the inner loop:** If you call `q.size()` as the loop condition (e.g., `for (int i = 0; i < q.size(); i++)`), the size grows as you enqueue children, and you process more nodes than belong to this level. Always snapshot `int sz = q.size()` before the inner `for` loop.

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Snapshot level size | `int sz = q.size()` BEFORE the inner loop — size grows as you enqueue children inside |
| Null check before offering | Always check `node.left != null` before `q.offer(node.left)` |
| Depth counting | Increment `depth` after processing each full level |
| Early exit for min depth | Return as soon as you dequeue the first leaf (BFS guarantees minimum) |
| Zigzag with Deque | Use `addFirst`/`addLast` on a Deque, not reversing after the fact |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 104 Max Depth, LC 637 Average of Levels |
| Medium | LC 102 Level Order, LC 199 Right Side View |
| Medium | LC 103 Zigzag, LC 515 Largest Values |
| Medium | LC 116 Populate Next Pointers, LC 111 Min Depth |

**Order:** 102 → 104 → 199 → 111 → 103 → 116 → 637

---

## One-line Summary

```
Level-order BFS = snapshot queue.size() at level start to process each depth layer separately.
层序BFS = 每层开始时快照队列大小，精确控制每层节点的处理。
```
