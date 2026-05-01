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

## Core Template 核心模板

```java
public void traverse(TreeNode node) {
    if (node == null) return;

    // PREORDER: process(node) here — visit before children
    traverse(node.left);
    // INORDER:  process(node) here — visit between children
    traverse(node.right);
    // POSTORDER: process(node) here — visit after children
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
    if (node == null) return 0;
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
    if (node == null) return 0;
    int left = height(node.left), right = height(node.right);
    diameter = Math.max(diameter, left + right);
    return 1 + Math.max(left, right);
}
```

### Lowest Common Ancestor (LC 236)
```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    TreeNode left  = lowestCommonAncestor(root.left,  p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) return root;  // p in one side, q in other
    return left != null ? left : right;
}
```

### Serialize / Deserialize (LC 297) — Preorder
```java
public String serialize(TreeNode root) {
    if (root == null) return "#";
    return root.val + "," + serialize(root.left) + "," + serialize(root.right);
}

public TreeNode deserialize(String data) {
    Queue<String> q = new LinkedList<>(Arrays.asList(data.split(",")));
    return build(q);
}
private TreeNode build(Queue<String> q) {
    String val = q.poll();
    if (val.equals("#")) return null;
    TreeNode node = new TreeNode(Integer.parseInt(val));
    node.left  = build(q);
    node.right = build(q);
    return node;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Base case first | Always `if (node == null) return ...` as first line |
| Global variable for path problems | Use instance variable for max/diameter updated inside DFS |
| Return value convention | Path problems: return "best single-side contribution" from each node |
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
