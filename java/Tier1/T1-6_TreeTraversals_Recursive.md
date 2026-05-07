# T1-6 — Tree Traversals (Recursive) 树的递归遍历

> **Core idea:** Visit all nodes of a binary tree in a specific order by recursing on left and right subtrees. The position of `process(node)` determines the traversal type.
> **核心思想：** 通过递归左右子树访问二叉树所有节点，`process(node)` 的位置决定遍历类型。
>
> Complexity: O(n) time, O(h) space (h = tree height; O(log n) balanced, O(n) skewed).
> Full reference: `BFS_DFS/description.md` DFS Pattern 3

---

## When to Use 什么时候用

| Traversal | Use case |
|---|---|
| **Inorder** (left→node→right) | BST operations: get sorted order, validate BST, find kth smallest |
| **Preorder** (node→left→right) | Serialize tree, copy tree, construct from traversal |
| **Postorder** (left→right→node) | Delete tree, compute subtree properties (height, diameter, path sum) |
| **Any traversal** | Path problems, ancestor problems, subtree matching |

---

## How it Works — Mental Model 原理与直觉

Recursive tree traversal works because every subtree is itself a tree — the same three-step structure (left, node, right) applies at every level. The recursion stack implicitly remembers where to return after finishing a subtree, so you never have to manage that bookkeeping manually.

The position of `process(node)` determines what information is available at call time. In **preorder**, you process a node before knowing anything about its children — useful for passing data downward (e.g., path prefix, depth). In **postorder**, both children have already returned their results — useful for aggregating values upward (e.g., height, subtree sum). Inorder gives sorted order for BSTs because, by definition, all left subtree values are smaller than the current node.

**Invariant:** When a postorder `dfs(node)` call returns, the return value represents the complete, fully computed result for the subtree rooted at `node`. Nothing about that subtree needs to be revisited.

---

## Step-by-Step Trace — Max Depth (Postorder) 执行追踪

```
Tree:      1
          / \
         2   3
        /
       4

dfs(4): left=dfs(null)=0, right=dfs(null)=0 → return 1+max(0,0)=1
dfs(2): left=dfs(4)=1,    right=dfs(null)=0 → return 1+max(1,0)=2
dfs(3): left=dfs(null)=0, right=dfs(null)=0 → return 1+max(0,0)=1
dfs(1): left=dfs(2)=2,    right=dfs(3)=1   → return 1+max(2,1)=3
Answer: 3
```

---

## Core Template 核心模板

```java
public void traverse(TreeNode node) {
    if (node == null) { return; }   // base case: empty subtree contributes nothing

    // PREORDER: process(node) here — visit before children (top-down: parent info available)
    traverse(node.left);
    // INORDER:  process(node) here — visit between children (BST sorted order; left done, right not yet)
    traverse(node.right);
    // POSTORDER: process(node) here — visit after children (bottom-up: both subtree results available)
}
```

**Key insight:** Preorder and postorder are mirror images in thinking:
- **Preorder:** "What do I know about this node before seeing its children?" (top-down info)
- **Postorder:** "What can I compute from my children's results?" (bottom-up aggregation)

---

## Variants 变形

| Variant | Traversal | Key technique | Example |
|---|---|---|---|
| BST sorted order | Inorder | collect into list | LC 94, 230 |
| Validate BST | Inorder | track prev value | LC 98 |
| Tree height | Postorder | `1 + max(left, right)` | LC 104 |
| Diameter | Postorder | `return height; update diameter = l + r` | LC 543 |
| Max path sum | Postorder | `return maxOneSide; update globalMax` | LC 124 |
| Serialize / deserialize | Preorder | write node + nulls | LC 297 |
| Lowest common ancestor | Postorder | found in left OR right | LC 236 |
| Same tree / subtree | Preorder | compare structure | LC 100, 572 |

---

## Key Examples 关键例题

### Binary Tree Maximum Path Sum (LC 124) — Classic Postorder
```java
int maxSum = Integer.MIN_VALUE;

public int maxPathSum(TreeNode root) {
    dfs(root); return maxSum;
}

private int dfs(TreeNode node) {
    if (node == null) { return 0; }
    int left  = Math.max(0, dfs(node.left));   // ignore negative branches
    int right = Math.max(0, dfs(node.right));
    maxSum = Math.max(maxSum, node.val + left + right);  // path through this node
    return node.val + Math.max(left, right);             // return best single side
}
```

### Diameter of Binary Tree (LC 543)
```java
int diameter = 0;

public int diameterOfBinaryTree(TreeNode root) {
    height(root); return diameter;
}

private int height(TreeNode node) {
    if (node == null) { return 0; }
    int left = height(node.left), right = height(node.right);
    diameter = Math.max(diameter, left + right);
    return 1 + Math.max(left, right);
}
```

### Lowest Common Ancestor (LC 236)
```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) { return root; }
    TreeNode left  = lowestCommonAncestor(root.left,  p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) { return root; }  // p in one side, q in other
    return left != null ? left : right;
}
```

### Serialize / Deserialize (LC 297) — Preorder
```java
public String serialize(TreeNode root) {
    if (root == null) { return "#"; }
    return root.val + "," + serialize(root.left) + "," + serialize(root.right);
}

public TreeNode deserialize(String data) {
    Queue<String> q = new LinkedList<>(Arrays.asList(data.split(",")));
    return build(q);
}
private TreeNode build(Queue<String> q) {
    String val = q.poll();
    if (val.equals("#")) { return null; }
    TreeNode node = new TreeNode(Integer.parseInt(val));
    node.left  = build(q);
    node.right = build(q);
    return node;
}
```

---

## Common Mistake / Gotcha 常见错误

**Two-role confusion in path sum problems (LC 124):** The DFS function has two responsibilities: (1) update the global maximum using the path *through* the current node (left + node + right), and (2) *return* only the best single-side path (left OR right, not both). Returning the full through-path value up to the parent is wrong because a path cannot split — it cannot go left, come back through the parent, and also go right.

**Missing `Math.max(0, child)` for negative nodes:** If you allow a negative child contribution, you make the path sum worse. Clamping to 0 means "don't extend the path through this child" — effectively cutting off the negative branch.

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Base case first | Always `if (node == null) return ...` as first line |
| Global variable for path problems | Use instance variable for max/diameter updated inside DFS |
| Return value convention | Path problems: return "best single-side contribution" from each node, not the through-path |
| Top-down vs bottom-up | Preorder passes info DOWN; postorder aggregates info UP |
| Don't forget `Math.max(0, child)` | Negative subtrees should be cut off in path sum problems |

---

## Practice Problems 练习题

| Difficulty | Problem | Traversal |
|---|---|---|
| Easy | LC 104 Max Depth, LC 110 Balanced Tree | Postorder |
| Easy | LC 100 Same Tree, LC 572 Subtree | Preorder |
| Medium | LC 543 Diameter, LC 437 Path Sum III | Postorder |
| Medium | LC 94 Inorder, LC 98 Validate BST | Inorder |
| Medium | LC 236 LCA, LC 297 Serialize | Mixed |
| Hard | LC 124 Max Path Sum | Postorder |

**Order:** 104 → 100 → 94 → 543 → 236 → 124 → 297

---

## One-line Summary

```
Recursive tree traversal = place process(node) before (preorder), between (inorder), or after (postorder) the recursive calls.
递归树遍历 = process(node) 放在递归调用前（前序）、中间（中序）或后面（后序）。
```
