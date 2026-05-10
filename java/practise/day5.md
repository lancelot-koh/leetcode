# Weekend Consolidation Plan (Day 5 + Day 6)

Tomorrow is the weekend — more time available. The goal is **not** to learn more new topics.

> **Main Goal:** Turn concepts you already know into stable, fluent implementation ability.

You have already built:
- BFS graph intuition
- Topological sort
- Graph construction
- Matrix traversal (multi-source BFS, reverse traversal)

Now focus on converting these into reliable code you can write under pressure.

---

## Phase 1 — Tree DFS & Recursion `(HIGHEST PRIORITY · 40%)`

### Why This First

This is currently your **weakest** important foundation. You understand traversal conceptually, but recursive execution is still shaky.

### Step 1 — Rewrite Templates From Blank

```
Preorder:   process → left → right
Inorder:    left → process → right
Postorder:  left → right → process
```

### Step 2 — Must-Do Problems (Easy → Medium)

| # | Problem | Key Insight |
|---|---|---|
| 1 | Max Depth of Binary Tree | subtree returns value upward |
| 2 | Same Tree | recursive structure comparison |
| 3 | Invert Binary Tree | recursive subtree modification |
| 4 | Balanced Binary Tree | return height upward |
| 5 | Diameter of Binary Tree | global variable + subtree height |

> **Diameter of Binary Tree** is the big recursion milestone — global state + subtree return combined.

### The One Question to Ask Every Time

> **What does this subtree return?**

This single question is the key to tree recursion mastery. Ask it before writing a single line.

---

## Phase 2 — Trie Stabilization `(SECOND PRIORITY · 20%)`

### Goal

Write `TrieNode`, `insert`, `search`, and `startsWith` from blank without hesitation.

### Template

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isWord;
}
```

### Must Implement

1. `insert(String word)`
2. `search(String word)`
3. `startsWith(String prefix)`

> Skip `delete` — lower ROI for interviews.

### Optional Bonus (if time remains)

**Word Search II** — combines Trie + DFS backtracking. Very Google-style.

---

## Phase 3 — LRU Cache Implementation `(20%)`

### Goal

Understand pointer rewiring calmly. Do **not** try to memorize the full class at once.

### Build It Incrementally

```
Step 1 — Draw the list:   head <-> A <-> B <-> tail
Step 2 — Implement:       removeNode(node)
Step 3 — Implement:       addToHead(node)
Step 4 — Implement:       moveToHead(node)  =  removeNode + addToHead
Step 5 — Full LRU:        only after above is stable
```

### Key Pointer Rewiring

```java
// removeNode
node.prev.next = node.next;
node.next.prev = node.prev;

// addToHead
node.prev = head;
node.next = head.next;
head.next.prev = node;
head.next = node;
```

---

## Phase 4 — Union Find Quick Review `(10%)`

Rewrite from blank:
1. `find()` with path compression
2. `union()` with union by rank

Practice one problem:
- **Number of Connected Components**, or
- **Graph Valid Tree** (Union Find version)

---

## Phase 5 — Matrix BFS Consolidation `(10%)`

You have already improved a lot here. Quick review only — focus on recognizing the pattern fast.

| Problem | Pattern |
|---|---|
| 01 Matrix | Multi-source BFS |
| Walls and Gates | Multi-source BFS |
| Surrounded Regions | Reverse boundary traversal |
| Pacific Atlantic | Reverse multi-source BFS |

> **Goal:** Recognize source + condition within 30 seconds of reading the problem.

---

## Optional Bonus — Binary Search Template

Only if energy remains. Review:
- `left < right` vs `left <= right`
- Lower bound pattern
- `canSatisfy(mid)` monotonic condition

---

## Time Allocation

| Topic | Time |
|---|---|
| Tree DFS & Recursion | 40% |
| Trie | 20% |
| LRU Cache | 20% |
| Union Find | 10% |
| Matrix BFS Review | 10% |

---

## Google Interview Reminder

Start practicing **talking while coding**:

> *"I'm treating the matrix as an implicit graph. The border cells are my traversal sources. The visited array tracks reachability."*

This matters as much as the algorithm itself.

---

## Most Important Milestone This Weekend

If you can truly internalize:

> **subtree returns value upward**

your DFS ability will jump significantly. That is your biggest current bottleneck — not graph understanding anymore.


---

## Problems

---

### 144. Binary Tree Preorder Traversal `Easy`

Given the `root` of a binary tree, return the preorder traversal of its nodes' values.

**Example 1:**
- Input: `root = [1,null,2,3]`
- Output: `[1,2,3]`

**Example 2:**
- Input: `root = [1,2,3,4,5,null,8,null,null,6,7,9]`
- Output: `[1,2,4,5,6,7,3,8,9]`

**Example 3:**
- Input: `root = []`
- Output: `[]`

**Constraints:**
- The number of nodes in the tree is in the range `[0, 100]`
- `-100 <= Node.val <= 100`

> Follow up: Recursive solution is trivial, could you do it iteratively?

---

### 94. Binary Tree Inorder Traversal `Easy`

Given the `root` of a binary tree, return the inorder traversal of its nodes' values.

**Example 1:**
- Input: `root = [1,null,2,3]`
- Output: `[1,3,2]`

**Example 2:**
- Input: `root = [1,2,3,4,5,null,8,null,null,6,7,9]`
- Output: `[4,2,6,5,7,1,3,9,8]`

**Example 3:**
- Input: `root = []`
- Output: `[]`

**Constraints:**
- The number of nodes in the tree is in the range `[0, 100]`
- `-100 <= Node.val <= 100`

> Follow up: Recursive solution is trivial, could you do it iteratively?

---

### 145. Binary Tree Postorder Traversal `Easy`

Given the `root` of a binary tree, return the postorder traversal of its nodes' values.

**Example 1:**
- Input: `root = [1,null,2,3]`
- Output: `[3,2,1]`

**Example 2:**
- Input: `root = [1,2,3,4,5,null,8,null,null,6,7,9]`
- Output: `[4,6,7,5,2,9,8,3,1]`

**Example 3:**
- Input: `root = []`
- Output: `[]`

**Constraints:**
- The number of nodes in the tree is in the range `[0, 100]`
- `-100 <= Node.val <= 100`

> Follow up: Recursive solution is trivial, could you do it iteratively?

---

### 104. Maximum Depth of Binary Tree `Easy`

Given the `root` of a binary tree, return its maximum depth.

A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

**Example 1:**
- Input: `root = [3,9,20,null,null,15,7]`
- Output: `3`

**Example 2:**
- Input: `root = [1,null,2]`
- Output: `2`

**Constraints:**
- The number of nodes in the tree is in the range `[0, 10^4]`
- `-100 <= Node.val <= 100`

---

### 111. Minimum Depth of Binary Tree `Easy`

Given a binary tree, find its minimum depth.

The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.

> Note: A leaf is a node with no children.

**Example 1:**
- Input: `root = [3,9,20,null,null,15,7]`
- Output: `2`

**Example 2:**
- Input: `root = [2,null,3,null,4,null,5,null,6]`
- Output: `5`

**Constraints:**
- The number of nodes in the tree is in the range `[0, 10^5]`
- `-1000 <= Node.val <= 1000`

---

### 100. Same Tree `Easy`

Given the roots of two binary trees `p` and `q`, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.

**Example 1:**
- Input: `p = [1,2,3]`, `q = [1,2,3]`
- Output: `true`

**Example 2:**
- Input: `p = [1,2]`, `q = [1,null,2]`
- Output: `false`

**Example 3:**
- Input: `p = [1,2,1]`, `q = [1,1,2]`
- Output: `false`

**Constraints:**
- The number of nodes in both trees is in the range `[0, 100]`
- `-10^4 <= Node.val <= 10^4`

---

### 226. Invert Binary Tree `Easy`

Given the `root` of a binary tree, invert the tree, and return its root.

**Example 1:**
- Input: `root = [4,2,7,1,3,6,9]`
- Output: `[4,7,2,9,6,3,1]`

**Example 2:**
- Input: `root = [2,1,3]`
- Output: `[2,3,1]`

**Example 3:**
- Input: `root = []`
- Output: `[]`

**Constraints:**
- The number of nodes in the tree is in the range `[0, 100]`
- `-100 <= Node.val <= 100`

---

### 110. Balanced Binary Tree `Easy`

Given a binary tree, determine if it is height-balanced.

A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one.

**Example 1:**
- Input: `root = [3,9,20,null,null,15,7]`
- Output: `true`

**Example 2:**
- Input: `root = [1,2,2,3,3,null,null,4,4]`
- Output: `false`

**Example 3:**
- Input: `root = []`
- Output: `true`

**Constraints:**
- The number of nodes in the tree is in the range `[0, 5000]`
- `-10^4 <= Node.val <= 10^4`

---

### 543. Diameter of Binary Tree `Easy`

Given the `root` of a binary tree, return the length of the diameter of the tree.

The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.

The length of a path between two nodes is represented by the number of edges between them.

**Example 1:**
- Input: `root = [1,2,3,4,5]`
- Output: `3`
- Explanation: 3 is the length of the path `[4,2,1,3]` or `[5,2,1,3]`

**Example 2:**
- Input: `root = [1,2]`
- Output: `1`

**Constraints:**
- The number of nodes in the tree is in the range `[1, 10^4]`
- `-100 <= Node.val <= 100`

---

### 542. 01 Matrix `Medium`

Given an `m x n` binary matrix `mat`, return the distance of the nearest `0` for each cell.

The distance between two cells sharing a common edge is `1`.

**Example 1:**
- Input: `mat = [[0,0,0],[0,1,0],[0,0,0]]`
- Output: `[[0,0,0],[0,1,0],[0,0,0]]`

**Example 2:**
- Input: `mat = [[0,0,0],[0,1,0],[1,1,1]]`
- Output: `[[0,0,0],[0,1,0],[1,2,1]]`

**Constraints:**
- `m == mat.length`, `n == mat[i].length`
- `1 <= m, n <= 10^4`, `1 <= m * n <= 10^4`
- `mat[i][j]` is either `0` or `1`
- There is at least one `0` in `mat`

---

### 286. Walls and Gates `Medium` *(Premium)*

You are given an `m x n` grid `rooms` initialized with three possible values:
- `-1` — a wall or obstacle
- `0` — a gate
- `2147483647` (`INF`) — an empty room

Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, leave it as `INF`.

**Example 1:**
- Input: `rooms = [[INF,-1,0,INF],[INF,INF,INF,-1],[INF,-1,INF,-1],[0,-1,INF,INF]]`
- Output: `[[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]`

**Example 2:**
- Input: `rooms = [[-1]]`
- Output: `[[-1]]`

**Constraints:**
- `m == rooms.length`, `n == rooms[i].length`
- `1 <= m, n <= 250`
- `rooms[i][j]` is `-1`, `0`, or `2^31 - 1`

---

### 130. Surrounded Regions `Medium`

You are given an `m x n` matrix `board` containing letters `'X'` and `'O'`. Capture all regions that are surrounded:

- A region is surrounded if none of its `'O'` cells are on the edge of the board.
- To capture, replace all `'O'`s with `'X'`s in the surrounded region in-place.

**Example 1:**
- Input: `board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]`
- Output: `[["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]`
- Explanation: The bottom `'O'` is not captured because it is on the edge.

**Example 2:**
- Input: `board = [["X"]]`
- Output: `[["X"]]`

**Constraints:**
- `1 <= m, n <= 200`
- `board[i][j]` is `'X'` or `'O'`

---

### 417. Pacific Atlantic Water Flow `Medium`

There is an `m x n` island that borders both the Pacific Ocean (left and top edges) and Atlantic Ocean (right and bottom edges).

Given an `m x n` matrix `heights`, return all cells from which rain water can flow to **both** oceans. Water flows to a neighbor if the neighbor's height is less than or equal to the current cell's height.

**Example 1:**
- Input: `heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]`
- Output: `[[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]`

**Example 2:**
- Input: `heights = [[1]]`
- Output: `[[0,0]]`

**Constraints:**
- `1 <= m, n <= 200`
- `0 <= heights[r][c] <= 10^5`

---

### 146. LRU Cache `Medium`

Design a data structure that follows the constraints of a **Least Recently Used (LRU)** cache.

Implement the `LRUCache` class:
- `LRUCache(int capacity)` — initialize with positive size `capacity`
- `int get(int key)` — return the value if key exists, otherwise `-1`
- `void put(int key, int value)` — update or insert the key-value pair; if over capacity, evict the least recently used key

Both `get` and `put` must run in `O(1)` average time complexity.

**Example 1:**
```
Input:  ["LRUCache","put","put","get","put","get","put","get","get","get"]
        [[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]
Output: [null,null,null,1,null,-1,null,-1,3,4]

LRUCache(2)
put(1,1) → {1=1}
put(2,2) → {1=1, 2=2}
get(1)   → 1
put(3,3) → evicts 2, {1=1, 3=3}
get(2)   → -1
put(4,4) → evicts 1, {4=4, 3=3}
get(1)   → -1
get(3)   → 3
get(4)   → 4
```

**Constraints:**
- `1 <= capacity <= 3000`
- `0 <= key <= 10^4`, `0 <= value <= 10^5`
- At most `2 * 10^5` calls to `get` and `put`

---

### 208. Implement Trie (Prefix Tree) `Medium`

A trie (pronounced "try") is a tree data structure used to efficiently store and retrieve keys in a dataset of strings.

Implement the `Trie` class:
- `Trie()` — initializes the trie
- `void insert(String word)` — inserts `word` into the trie
- `boolean search(String word)` — returns `true` if `word` is in the trie
- `boolean startsWith(String prefix)` — returns `true` if any inserted word has the given prefix

**Example 1:**
```
Input:  ["Trie","insert","search","search","startsWith","insert","search"]
        [[],["apple"],["apple"],["app"],["app"],["app"],["app"]]
Output: [null,null,true,false,true,null,true]
```

**Constraints:**
- `1 <= word.length, prefix.length <= 2000`
- `word` and `prefix` consist only of lowercase English letters
- At most `3 * 10^4` calls to `insert`, `search`, and `startsWith`

---

### 1804. Implement Trie II (Prefix Tree) `Medium` *(Premium)*

Implement the `Trie` class with count-aware operations:
- `Trie()` — initializes the trie
- `void insert(String word)` — inserts `word` (may be inserted multiple times)
- `int countWordsEqualTo(String word)` — returns number of times `word` was inserted
- `int countWordsStartingWith(String prefix)` — returns number of inserted words with the given prefix
- `void erase(String word)` — removes one occurrence of `word` from the trie

**Example 1:**
```
Input:  ["Trie","insert","insert","countWordsEqualTo","countWordsStartingWith","erase","countWordsEqualTo","countWordsStartingWith"]
        [[],["apple"],["apple"],["apple"],["app"],["apple"],["apple"],["app"]]
Output: [null,null,null,2,2,null,1,1]
```

**Constraints:**
- `1 <= word.length, prefix.length <= 2000`
- `word` and `prefix` consist only of lowercase English letters
- At most `3 * 10^4` calls in total
- `erase` is only called for words that are currently in the trie

