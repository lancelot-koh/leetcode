# 🌳 Tree - The Recursive Hierarchy Pattern

**Master node-based problems through systematic decomposition**

---

## 📍 Why This Matters

### Interview Frequency: **18% of all problems** ⭐⭐⭐⭐⭐

### The Insight
Trees are **naturally recursive**. Each subtree is a smaller tree. Solve subtrees, combine results.

```
                  Root
                 /    \
              L          R
             / \        / \
            LL LR     RL  RR

Solve(Root) = CombineResults(Solve(L), Solve(R))
```

---

## 🎯 The Core Concept

### Tree Definition
```java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
}
```

### Why Trees Are Special
- ✅ Naturally recursive
- ✅ Divide into left/right subproblems
- ✅ Base case: null node

---

## 🔧 The 4-Step Tree Framework

### Step 1: Identify the Tree Type

| Type | Structure | Example |
|---|---|---|
| **Binary Tree** | Each node ≤ 2 children | General tree |
| **Binary Search Tree** | Left < Root < Right | Ordered tree |
| **Balanced** | Height difference ≤ 1 | AVL, Red-Black |
| **Complete** | Filled left-to-right | Heap |

---

### Step 2: Choose Traversal Strategy

```
DFS (Depth First): Process deep before wide
  ├─ Preorder: Root → Left → Right (TOP-DOWN)
  ├─ Inorder: Left → Root → Right (LNR)
  └─ Postorder: Left → Right → Root (BOTTOM-UP)

BFS (Breadth First): Process level-by-level
  └─ Level Order: One level at a time
```

**When to use which:**
- **Top-down (Preorder):** Root decision affects children (validate BST)
- **Bottom-up (Postorder):** Combine child results (tree height, sum)
- **In-order:** Only for BST (sorted order)
- **Level-order:** When level matters (binary tree level order)

---

### Step 3: Define Recursive Function

**Two Patterns:**

**Pattern A: Return Value (Bottom-Up)**
```java
int solve(TreeNode node) {
    if (node == null) return BASE_CASE;
    
    int leftResult = solve(node.left);
    int rightResult = solve(node.right);
    
    return COMBINE(leftResult, rightResult, node.val);
}
```

**Pattern B: Process Current (Top-Down)**
```java
void solve(TreeNode node, int param) {
    if (node == null) return;
    
    // Process current
    PROCESS(node, param);
    
    // Pass to children
    solve(node.left, param);
    solve(node.right, param);
}
```

---

### Step 4: Combine Results

Decide how to combine left & right:
```
Sum: leftVal + rightVal
Max: Math.max(leftVal, rightVal)
Count: leftCount + rightCount
Path: Combine paths
```

---

## 📚 Code Templates

### Template 1: Bottom-Up (Return Value)

```java
public int solve(TreeNode root) {
    if (root == null) return BASE_CASE;
    
    int leftResult = solve(root.left);
    int rightResult = solve(root.right);
    
    return COMBINE(leftResult, rightResult, root.val);
}
```

**Examples:** Tree height, sum, max path

---

### Template 2: Top-Down (Process Current)

```java
public void solve(TreeNode root, int param) {
    if (root == null) return;
    
    // Process current node with passed parameter
    PROCESS(root, param);
    
    // Continue to children
    solve(root.left, newParam);
    solve(root.right, newParam);
}
```

**Examples:** Path sum, validate BST, assign levels

---

### Template 3: Two-Value Return

```java
private int[] solve(TreeNode root) {
    if (root == null) return new int[]{0, 0};
    
    int[] left = solve(root.left);
    int[] right = solve(root.right);
    
    int val1 = COMBINE1(left[0], right[0], root.val);
    int val2 = COMBINE2(left[1], right[1], root.val);
    
    return new int[]{val1, val2};
}
```

**Examples:** Max/min, balanced state, path info

---

## 💡 Real Examples (Quick)

### Example 1: Maximum Depth (LC104)

**Pattern:** Bottom-up, return value

```java
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    
    int leftDepth = maxDepth(root.left);
    int rightDepth = maxDepth(root.right);
    
    return Math.max(leftDepth, rightDepth) + 1;
}
```

---

### Example 2: Path Sum (LC112)

**Pattern:** Top-down, process current

```java
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) return false;
    
    if (root.left == null && root.right == null) {
        return root.val == targetSum;
    }
    
    return hasPathSum(root.left, targetSum - root.val) ||
           hasPathSum(root.right, targetSum - root.val);
}
```

---

### Example 3: Lowest Common Ancestor (LC236)

**Pattern:** Bottom-up, combine results

```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) return null;
    if (root == p || root == q) return root;
    
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    
    if (left != null && right != null) return root;
    if (left != null) return left;
    return right;
}
```

---

### Example 4: Level Order Traversal (LC102)

**Pattern:** BFS with queue

```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> level = new ArrayList<>();
        
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        result.add(level);
    }
    
    return result;
}
```

---

## 🎯 Decision Tree

```
Tree problem?
  │
  ├─ Need recursion/combine results?
  │  └─ YES → DFS (recursion)
  │
  ├─ Process level-by-level?
  │  └─ YES → BFS (queue)
  │
  ├─ BST property matters?
  │  └─ YES → In-order traversal or check BST
  │
  └─ Pattern:
     ├─ Return value from subtree? → Bottom-up
     ├─ Process current node? → Top-down
     └─ Level matters? → BFS
```

---

## ⚠️ Common Pitfalls

### Pitfall 1: Forgot Null Check

```java
❌ WRONG:
int depth = maxDepth(root.left);  // What if root is null?

✅ CORRECT:
if (root == null) return 0;
int depth = maxDepth(root.left);
```

---

### Pitfall 2: Wrong Return Value

```java
❌ WRONG:
return leftResult + rightResult;  // What about current node?

✅ CORRECT:
return leftResult + rightResult + root.val;
```

---

### Pitfall 3: Modifying Tree While Traversing

```java
❌ RISKY:
root.left = null;  // Might break references
```

---

## 🚀 Practice Plan

**Easy:** LC104, LC100, LC101, LC110
**Medium:** LC112, LC113, LC236, LC235
**Hard:** LC124, LC222, LC297

---

## 📋 Common Tree Problems

| Problem | Pattern | Key Insight |
|---|---|---|
| Max Depth | Bottom-up | Max of children + 1 |
| Path Sum | Top-down | Subtract as go down |
| LCA | Bottom-up | Where both found |
| Level Order | BFS | Track level size |
| Serialize | DFS | Pre-order traversal |
| Validate BST | Top-down | Track min/max range |

---

**Master tree recursion. It's 18% of all interviews.** 🚀
