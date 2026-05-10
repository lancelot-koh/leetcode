# Tree DFS Patterns & Mental Models

You have already started mastering:
- Traversal
- Subtree return
- Recursive DFS
- Height / depth
- Diameter
- Balance

The next layer is the DFS patterns and mental models below.

## Priority Legend

| Label | Meaning |
|---|---|
| `P1` | Master now — foundational, appears in almost every tree problem |
| `P2` | Learn soon — important, regularly seen in interviews |
| `P3` | Advanced — revisit after P1 and P2 feel solid |

---

## 1. Tree Traversal Order `P1`

### Preorder — `root → left → right`

Use cases:
- Serialize a tree
- Clone a tree
- Build tree structure
- Prefix expression

### Inorder — `left → root → right`

> **VERY important for BST** — BST inorder traversal produces a sorted sequence.

Use cases:
- Kth smallest element
- BST validation
- Sorted output

### Postorder — `left → right → root`

Most important recursion pattern — children are solved before the parent.

Use cases:
- Height
- Balanced tree
- Diameter
- Max path sum
- Subtree aggregation

---

## 2. Two Core DFS Styles `P1`

### A. Side Effect DFS

```java
void dfs(TreeNode node, Result result)
```

Modifies an external structure: result list, visited set, or global variable.

Examples: traversal, collect paths, islands, permutations

### B. Return Value DFS

```java
int dfs(TreeNode node)
```

Returns subtree information upward.

Examples: height, depth, balance check, subtree sum

---

## 3. The Most Important Tree Question `P1`

> Before writing any code, ask:
>
> **What does this subtree return?**

This is THE key recursion skill.

---

## 4. Bottom-Up vs Top-Down DFS `P1`

### Bottom-Up (Most Common)

Children solve first, parent combines answers.

```java
int left  = dfs(node.left);
int right = dfs(node.right);
return combine(left, right);
```

Examples: diameter, balanced tree, max depth

### Top-Down

Pass information downward through parameters.

```java
void dfs(TreeNode node, int currentState)
```

Examples: path sum, current depth, current path

---

## 5. Global Variable + DFS Pattern `P1`

DFS returns one thing (e.g. height) while updating a global (e.g. max diameter).

```java
int diameter = 0;

int dfs(TreeNode node) {
    if (node == null) return 0;
    int left  = dfs(node.left);
    int right = dfs(node.right);
    diameter = Math.max(diameter, left + right);  // update global
    return Math.max(left, right) + 1;             // return height
}
```

---

## 6. Height vs Depth `P1`

| Concept | Meaning |
|---------|---------|
| **depth** | distance from root **downward** to a node |
| **height** | longest path from a node **downward** to a leaf |

---

## 7. Height: Node Count vs Edge Count `P1`

Most LeetCode height problems count **nodes**. Diameter counts **edges**.

```
Tree:  1 → 2 → 3

Height (node count) = 3
Diameter (edge count) = 2
```

---

## 8. Recursive Call Stack Mental Model `P1`

Each recursive call has its own `node`, local variables, and return value — they do **not** overwrite each other. The call stack keeps them isolated.

---

## 9. Subtree Thinking `P1`

Each node asks:

> **What is the answer for MY subtree?**

Not: "What is the answer for the whole tree?" This mindset shift is what makes recursion click.

---

## 10. The Master Pattern `P1`

Most tree problems reduce to:

```java
int left  = dfs(node.left);
int right = dfs(node.right);
// then one of:
return compare(left, right);
return combine(left, right);
return aggregate(left, right);
return validate(left, right);
```

---

## 11. Common Recursive Return Types `P1`

| Return Type     | Meaning                 |
|-----------------|-------------------------|
| `int`           | height / sum / depth    |
| `boolean`       | validity                |
| `TreeNode`      | subtree root            |
| `List`          | collected paths         |
| `-1` (sentinel) | failure / invalid state |

---

## 12. Failure Propagation Pattern `P2`

```java
int dfs(TreeNode node) {
    if (node == null) return 0;
    int left  = dfs(node.left);
    int right = dfs(node.right);

    if (left == -1 || right == -1) return -1;      // propagate failure
    if (Math.abs(left - right) > 1) return -1;     // mark as failed

    return Math.max(left, right) + 1;
}
```

> Subtree already failed → propagate `-1` upward. Avoids a separate `boolean` return.

---

## 13. Iterative DFS = Manual Recursion Simulation `P2`

| | Recursive | Iterative |
|---|---|---|
| State management | call stack (automatic) | explicit stack (manual) |
| Code length | shorter | longer |

Iterative DFS uses: a `Stack`, a `curr` pointer, and explicit visited tracking.

---

## 14. Tree BFS vs DFS `P1`

| | BFS | DFS |
|---|---|---|
| Data structure | Queue | Stack / Recursion |
| Traversal order | Level by level | Subtree by subtree |
| Best for | Shortest path, level problems | Recursive decomposition |

---

## 15. Multi-Return Meaning DFS `P2`

DFS encodes two pieces of info in one return value:

| Return value | Meaning |
|---|---|
| `>= 0` | valid subtree, value = height |
| `-1` | invalid subtree |

---

## 16. BST (Binary Search Tree) `P1`

Core property: `left < root < right`

### Validate BST

Pass min/max bounds downward:

```java
boolean validate(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return validate(node.left, min, node.val)
        && validate(node.right, node.val, max);
}
```

### Inorder = Sorted

BST inorder traversal produces a sorted sequence — use this for:
- Kth smallest element
- BST validation via sorted check
- Sorted output

### Lowest Common Ancestor (BST)

Use BST ordering: if both nodes are less than root → go left; both greater → go right; otherwise current node is LCA.

```java
TreeNode lca(TreeNode node, TreeNode p, TreeNode q) {
    if (p.val < node.val && q.val < node.val) return lca(node.left, p, q);
    if (p.val > node.val && q.val > node.val) return lca(node.right, p, q);
    return node;
}
```

---

## 17. Path Problems `P1`

### Path Sum (Top-Down)

Pass remaining target downward:

```java
boolean hasPath(TreeNode node, int target) {
    if (node == null) return false;
    if (node.left == null && node.right == null) return node.val == target;
    int rem = target - node.val;
    return hasPath(node.left, rem) || hasPath(node.right, rem);
}
```

### Binary Tree Paths (Backtracking)

```java
void dfs(TreeNode node, List<String> path, List<String> result) {
    path.add(String.valueOf(node.val));
    if (node.left == null && node.right == null) {
        result.add(String.join("->", path));
    }
    if (node.left  != null) dfs(node.left,  path, result);
    if (node.right != null) dfs(node.right, path, result);
    path.remove(path.size() - 1);   // backtrack
}
```

---

## 18. Lowest Common Ancestor — General Binary Tree `P1`

> If left and right both found a target node, current node is the LCA.

```java
TreeNode lca(TreeNode node, TreeNode p, TreeNode q) {
    if (node == null || node == p || node == q) return node;
    TreeNode left  = lca(node.left,  p, q);
    TreeNode right = lca(node.right, p, q);
    if (left != null && right != null) return node;  // found on both sides
    return left != null ? left : right;
}
```

The return value simultaneously carries the search result and the LCA answer — a big recursion milestone.

---

## 19. Tree Serialization `P2`

Very Google-style.

**Serialize** — preorder DFS, mark nulls with a sentinel:

```java
void serialize(TreeNode node, StringBuilder sb) {
    if (node == null) { sb.append("N,"); return; }
    sb.append(node.val).append(",");
    serialize(node.left, sb);
    serialize(node.right, sb);
}
```

**Deserialize** — rebuild recursively using a queue of tokens:

```java
TreeNode deserialize(Queue<String> q) {
    String val = q.poll();
    if (val.equals("N")) return null;
    TreeNode node = new TreeNode(Integer.parseInt(val));
    node.left  = deserialize(q);
    node.right = deserialize(q);
    return node;
}
```

---

## 20. Build Tree from Traversals `P2`

Classic: given **preorder + inorder** (or inorder + postorder), reconstruct the tree.

- Preorder/postorder gives the **root**
- Root splits inorder into left and right subtrees
- Recurse on each half

```java
TreeNode build(int[] pre, int preL, int[] in, int inL, int inR) {
    if (inL > inR) return null;
    TreeNode root = new TreeNode(pre[preL]);
    int mid = inorderIndex(root.val);
    int leftSize = mid - inL;
    root.left  = build(pre, preL + 1,             in, inL,     mid - 1);
    root.right = build(pre, preL + leftSize + 1,  in, mid + 1, inR);
    return root;
}
```

---

## 21. Tree Width / Level Concepts — BFS `P1`

All use **queue + size loop** pattern:

```java
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);
while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        // process node
        if (node.left  != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }
}
```

Examples: level order traversal, zigzag traversal, right side view, average of levels.

---

## 22. Backtracking on Trees `P2`

```java
void dfs(TreeNode node, List<Integer> path, List<List<Integer>> result) {
    if (node == null) return;
    path.add(node.val);
    if (node.left == null && node.right == null) result.add(new ArrayList<>(path));
    dfs(node.left,  path, result);
    dfs(node.right, path, result);
    path.remove(path.size() - 1);  // backtrack
}
```

---

## 23. Parent Contribution vs Subtree Contribution `P3`

Some problems ask: **What can this subtree contribute upward?**

A subtree can only return a **single-side** gain — returning both sides means the path branches and cannot continue through the parent.

```java
int maxSum = Integer.MIN_VALUE;

int dfs(TreeNode node) {
    if (node == null) return 0;
    int left  = Math.max(0, dfs(node.left));
    int right = Math.max(0, dfs(node.right));
    maxSum = Math.max(maxSum, node.val + left + right);  // update global
    return node.val + Math.max(left, right);             // single-side only
}
```

---

## 24. DFS State Passing Styles `P1`

| Style | Signature | When to use |
|---|---|---|
| Top-down | `dfs(node, depth, sum, path)` | pass context downward |
| Bottom-up | `left = dfs(left); right = dfs(right)` | aggregate from children |

---

## 25. N-ary Tree DFS `P2`

Same recursion structure — replace `left`/`right` with a loop:

```java
void dfs(Node node) {
    if (node == null) return;
    for (Node child : node.children) {
        dfs(child);
    }
}
```

---

## 26. Tree As Graph `P3`

Some problems add parent relationships to convert a tree into an undirected graph:

```java
Map<TreeNode, TreeNode> parent = new HashMap<>();

void buildParent(TreeNode node, TreeNode par) {
    if (node == null) return;
    parent.put(node, par);
    buildParent(node.left,  node);
    buildParent(node.right, node);
}
```

Examples: Distance K nodes, burn tree, infection spread.

---

## 27. Common Tree Interview Patterns

| Pattern | Example Problem | Priority |
|---|---|---|
| Subtree height | Max Depth | P1 |
| Subtree validation | Balanced Tree, Valid BST | P1 |
| Subtree aggregation | Subtree Sum | P1 |
| Path tracking | Path Sum | P1 |
| Traversal collection | Preorder / Inorder | P1 |
| Parent-child comparison | Same Tree | P1 |
| Divide & combine | Diameter | P1 |
| Both subtrees found | LCA | P1 |
| Level processing | Right Side View, Zigzag | P1 |
| Serialize / deserialize | LeetCode 297 | P2 |
| Reconstruct from traversals | LeetCode 105 | P2 |
| Backtracking paths | Path Sum II | P2 |
| Single-side gain | Max Path Sum | P3 |
| Tree as graph | Distance K | P3 |

---

## 28. The Staff-Level Insight

Most tree problems are:

> **recursive decomposition + subtree information flow**

Not memorizing unrelated solutions. Once recursive subtree intuition feels natural, most tree problems become variations of the same pattern.
