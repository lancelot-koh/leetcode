# T1-7 — Tree Inorder (Iterative) 树的迭代中序遍历

> **Core idea:** Replace the call stack with an explicit stack. Push nodes as you go left; when you can't go further, pop, visit, then go right.
> **核心思想：** 用显式栈替代调用栈。一路向左压栈；无法继续时弹出访问，再向右。
>
> Complexity: O(n) time, O(h) space.
> Use when: deep trees risk stack overflow with recursion, or when you need **lazy evaluation** (generate values one at a time).
> Full reference: `BFS_DFS/description.md` DFS Pattern 4

---

## When to Use 什么时候用

| Situation | Why iterative |
|---|---|
| Very deep / skewed tree | Recursion risks `StackOverflowError` |
| BST iterator (LC 173) | Need to produce values on demand (lazy) |
| Inorder successor / predecessor | Need to stop mid-traversal |
| Controlled traversal order | Process one node at a time |

---

## How it Works — Mental Model 原理与直觉

Recursive inorder uses the call stack to remember "I still need to visit the parent after finishing the left subtree." The iterative version makes this explicit: we push nodes onto a stack as we walk left, and when we can't go further left, the stack top is the node whose left subtree is fully done — exactly the right moment to visit it.

After visiting a node, we move to its right child and repeat: walk all the way left, visiting each node as it becomes the new "leftmost unvisited." This mirrors the exact sequence the recursive version follows, but now you control the stack.

**Invariant:** Everything on the stack has been reached but not yet visited. The top of the stack is always the smallest unvisited node seen so far.

---

## Step-by-Step Trace — Iterative Inorder 执行追踪

```
Tree:    2
        / \
       1   3
cur=2: push 2, go left → cur=1
cur=1: push 1, go left → cur=null
Pop 1, visit 1, go right → cur=null
Pop 2, visit 2, go right → cur=3
cur=3: push 3, go left → cur=null
Pop 3, visit 3, go right → cur=null
Stack empty, cur=null → done
Result: [1, 2, 3]  ✓ (BST sorted order)
```

---

## Core Templates 核心模板

### Iterative Inorder (Standard) 标准迭代中序

```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode cur = root;

    while (cur != null || !stack.isEmpty()) {
        // Phase 1: walk left — push every node we pass over (we'll visit them after their left subtrees)
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        // Phase 2: left subtree exhausted — pop the top and visit it (inorder position)
        cur = stack.pop();
        result.add(cur.val);
        // Phase 3: now explore its right subtree; the outer while will push its left spine
        cur = cur.right;
    }
    return result;
}
```

**Pattern explanation:**
1. Push all left nodes onto stack
2. Pop → this is the inorder-next node → visit it
3. Move to right child, repeat

### Iterative Preorder 迭代前序

```java
Deque<TreeNode> stack = new ArrayDeque<>();
stack.push(root);
while (!stack.isEmpty()) {
    TreeNode node = stack.pop();
    process(node);                      // PREORDER: visit before pushing children
    if (node.right != null) { stack.push(node.right); }
    if (node.left  != null) { stack.push(node.left); }   // left on top → visited first
}
```

### Iterative Postorder (reverse of modified preorder) 迭代后序

```java
Deque<TreeNode> stack = new ArrayDeque<>();
Deque<TreeNode> output = new ArrayDeque<>();
stack.push(root);
while (!stack.isEmpty()) {
    TreeNode node = stack.pop();
    output.push(node);                  // collect in reverse order
    if (node.left  != null) { stack.push(node.left); }
    if (node.right != null) { stack.push(node.right); }
}
// output deque gives postorder when popped
```

---

## BST Iterator Pattern (LC 173) BST迭代器模式

This is the key interview use case — implement a lazy inorder iterator:

```java
class BSTIterator {
    Deque<TreeNode> stack = new ArrayDeque<>();

    public BSTIterator(TreeNode root) {
        pushLeft(root);
    }

    public int next() {
        TreeNode node = stack.pop();
        pushLeft(node.right);   // prepare right subtree lazily
        return node.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    private void pushLeft(TreeNode node) {
        while (node != null) { stack.push(node); node = node.left; }
    }
}
```

**Why this works:** `next()` is amortized O(1) — each node is pushed and popped exactly once total across all calls.

---

## Kth Smallest in BST (LC 230)

```java
public int kthSmallest(TreeNode root, int k) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode cur = root;
    while (cur != null || !stack.isEmpty()) {
        while (cur != null) { stack.push(cur); cur = cur.left; }
        cur = stack.pop();
        if (--k == 0) { return cur.val; }
        cur = cur.right;
    }
    return -1;
}
```

---

## Inorder Successor in BST (LC 285)

```java
public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
    TreeNode successor = null;
    while (root != null) {
        if (p.val < root.val) { successor = root; root = root.left; }
        else                   { root = root.right; }
    }
    return successor;
}
```

---

## Common Mistake / Gotcha 常见错误

**Using only `!stack.isEmpty()` as the loop condition:** When `cur` points to a right child (e.g., after popping and going right), the stack may be temporarily empty but `cur` is not null — there are still nodes to process. The condition must be `cur != null || !stack.isEmpty()`. Dropping the `cur != null` clause causes premature termination on right-heavy trees.

**Using `Stack<>` instead of `Deque<>`:** `java.util.Stack` is a legacy class synchronized on every push/pop, making it 3-5x slower than `ArrayDeque`. Always declare the type as `Deque<TreeNode> stack = new ArrayDeque<>()`.

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `cur != null OR !stack.isEmpty()` | Both conditions needed: `cur` covers descent, stack covers pending visits |
| Use `Deque<TreeNode>` not `Stack<>` | `ArrayDeque` is faster; `Stack` is legacy and synchronized |
| Preorder: push right THEN left | So left is on top of stack and visited first |
| Postorder: use output deque | Push to output then pop at end — no need for complex two-pass |
| Lazy evaluation | BST iterator: only push left spine; push right lazily on `next()` |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 94 Binary Tree Inorder Traversal (iterative version) |
| Medium | LC 173 BST Iterator |
| Medium | LC 230 Kth Smallest Element in BST |
| Medium | LC 285 Inorder Successor in BST |
| Medium | LC 144 Preorder, LC 145 Postorder (iterative) |

**Order:** 94 (iterative) → 173 → 230 → 285

---

## One-line Summary

```
Iterative inorder = push left spine onto stack; pop, visit, then move right.
迭代中序 = 左链全部压栈，弹出访问，再向右走。
```
