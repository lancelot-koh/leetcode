# BFS / DFS — Graph & Tree Search

> **BFS core idea:** Explore level by level using a queue. Guarantees shortest path in unweighted graphs.
> **BFS 核心思想：** 用队列逐层探索，保证无权图中找到最短路径。
>
> **DFS core idea:** Explore as deep as possible before backtracking. Natural for exhaustive search, connected components, and tree traversals.
> **DFS 核心思想：** 尽可能深入后回溯，适合穷举搜索、连通分量、树遍历。
>
> Complexity: O(V + E) for both.
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**BFS keywords — 关键词**

| English | 中文 |
|---|---|
| shortest path (unweighted) | 最短路径（无权图）|
| minimum steps / hops | 最少步数 |
| level-order / layer by layer | 层序 / 按层处理 |
| spread from multiple sources | 多源扩散 |
| closest / nearest | 最近的 |

**DFS keywords — 关键词**

| English | 中文 |
|---|---|
| all paths / reachable | 所有路径 / 可达性 |
| connected components | 连通分量 |
| cycle detection | 判环 |
| topological order | 拓扑顺序 |
| tree traversal | 树遍历 |
| flood fill / island | 填充 / 岛屿 |

**BFS vs DFS 选择指南**

| Criterion | BFS | DFS |
|---|---|---|
| Shortest path (unweighted) | YES — level guarantees it | No |
| Memory (wide graphs) | High (whole level in queue) | Low (O(depth) stack) |
| Memory (deep graphs) | Low | High (risk of stack overflow) |
| Find any path | Either | DFS simpler |
| All paths / exhaustive | Either | DFS more natural |
| Level / distance info needed | BFS natural | Extra work needed |

---

## 2. Quick Decision Guide 快速判断

```
Shortest path in unweighted graph?           → BFS Pattern 1
Level-order / process by depth?              → BFS Pattern 2
Multiple starting points spreading?          → BFS Pattern 3: Multi-source BFS
Count connected components?                  → DFS Pattern 1 or Union-Find
Cycle detection in graph?                    → DFS Pattern 2
All paths / reachable nodes?                 → DFS Pattern 1
Tree traversal (pre/in/post)?                → DFS Pattern 3
Grid flood fill / island counting?           → DFS Pattern 1 or BFS Pattern 3
```

---

## 3. BFS Patterns BFS 模式

---

### BFS Pattern 1 — Shortest Path 最短路径

**When:** find minimum steps/distance from source to target in an unweighted graph.
**适用：** 无权图中求最少步数/最短距离。

**Template 模板**

```java
public int shortestPath(int[][] graph, int src, int dst) {
    boolean[] visited = new boolean[graph.length];
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(src);
    visited[src] = true;
    int steps = 0;

    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {           // process one level at a time
            int node = queue.poll();
            if (node == dst) return steps;
            for (int neighbor : graph[node]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
        steps++;
    }
    return -1;
}
```

**Key insight 核心原理**

Nodes are first seen at their minimum distance. Mark visited **when enqueued**, not when dequeued — prevents processing the same node multiple times.
节点第一次被发现时即为最短距离。**入队时**就标记访问，而非出队时，避免重复处理。

---

### BFS Pattern 2 — Level-Order with Level Info 带层信息的层序

**When:** tree/graph problems requiring per-level processing (max depth, right-side view, zigzag).
**适用：** 需要按层处理的树/图问题（最大深度、右视图、锯齿遍历）。

**Template 模板**

```java
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);
int depth = 0;

while (!queue.isEmpty()) {
    int size = queue.size();           // number of nodes at current level
    for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        // process node; i == size-1 means it's the rightmost at this level
        if (node.left  != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }
    depth++;
}
```

**Variants 变形**

| Variant | What to track per level | Example |
|---|---|---|
| Max depth | count levels | LC 104 |
| Right-side view | last node of each level | LC 199 |
| Zigzag traversal | alternate direction | LC 103 |
| Level averages | sum / size | LC 637 |

---

### BFS Pattern 3 — Multi-source BFS 多源BFS

**When:** BFS starts from **multiple** sources simultaneously (e.g., all rotten oranges, all 0-cells).
**适用：** 同时从多个源头出发的BFS（所有腐烂橘子、所有0格子）。

**Template 模板**

```java
Queue<int[]> queue = new LinkedList<>();

// Enqueue ALL sources upfront
for (int r = 0; r < m; r++)
    for (int c = 0; c < n; c++)
        if (grid[r][c] == SOURCE) {
            queue.offer(new int[]{r, c});
            visited[r][c] = true;
        }

int steps = 0;
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        int[] cell = queue.poll();
        for (int[] d : dirs) {
            int nr = cell[0] + d[0], nc = cell[1] + d[1];
            if (inBounds(nr, nc) && !visited[nr][nc] && canVisit(grid, nr, nc)) {
                visited[nr][nc] = true;
                queue.offer(new int[]{nr, nc});
            }
        }
    }
    steps++;
}
```

**Variants 变形**

| Variant | Example |
|---|---|
| Rotting oranges (min time to rot all) | LC 994 |
| 01 Matrix (distance to nearest 0) | LC 542 |
| Walls and gates (distance to nearest gate) | LC 286 |
| Spreading fire / infection | custom |

---

### BFS Pattern 4 — Implicit Graph BFS 隐式图BFS

**When:** the graph isn't given explicitly — states are nodes, transitions are edges.
**适用：** 图未显式给出，状态即节点，转换即边。

**Examples:** word ladder (each word = node), sliding puzzle states, combination lock states.

```java
// Word Ladder (LC 127)
Set<String> wordSet = new HashSet<>(wordList);
Queue<String> queue = new LinkedList<>();
queue.offer(beginWord);
int steps = 1;

while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) {
        String word = queue.poll();
        char[] chars = word.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            char orig = chars[j];
            for (char c = 'a'; c <= 'z'; c++) {
                chars[j] = c;
                String next = new String(chars);
                if (wordSet.contains(next)) {
                    if (next.equals(endWord)) return steps + 1;
                    wordSet.remove(next);   // remove = visited
                    queue.offer(next);
                }
            }
            chars[j] = orig;
        }
    }
    steps++;
}
```

---

## 4. DFS Patterns DFS 模式

---

### DFS Pattern 1 — Recursive DFS on Graph/Grid 递归DFS（图/网格）

**When:** explore all reachable cells, count components, flood fill.
**适用：** 探索所有可达节点，计数连通分量，填充区域。

**Template 模板**

```java
boolean[] visited;

public void dfs(int[][] graph, int node) {
    visited[node] = true;
    for (int neighbor : graph[node]) {
        if (!visited[neighbor]) dfs(graph, neighbor);
    }
}

// Grid version
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

public void dfsGrid(char[][] grid, int r, int c) {
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return;
    if (grid[r][c] != '1') return;
    grid[r][c] = '0';                   // mark visited by mutating
    for (int[] d : dirs) dfsGrid(grid, r + d[0], c + d[1]);
}
```

**Variants 变形**

| Variant | Example |
|---|---|
| Number of islands | LC 200 |
| Max area of island | LC 695 |
| Flood fill | LC 733 |
| Count connected components | LC 323 |

---

### DFS Pattern 2 — Cycle Detection 判环

**For undirected graph:** during DFS, if you reach a visited node that isn't your direct parent → cycle.
**For directed graph:** use coloring (0=unvisited, 1=in-stack, 2=done) — if you reach a node with color 1 → cycle.

**Directed graph (3-color DFS)**

```java
int[] color;  // 0=white, 1=gray(in stack), 2=black(done)

public boolean hasCycle(int node) {
    color[node] = 1;
    for (int neighbor : graph[node]) {
        if (color[neighbor] == 1) return true;   // back edge = cycle
        if (color[neighbor] == 0 && hasCycle(neighbor)) return true;
    }
    color[node] = 2;
    return false;
}
```

---

### DFS Pattern 3 — Tree Traversals 树遍历

**When:** process tree nodes in specific orders.

**All three in one:**

```java
public void traverse(TreeNode node) {
    if (node == null) return;
    // preorder:  process(node) here
    traverse(node.left);
    // inorder:   process(node) here
    traverse(node.right);
    // postorder: process(node) here
}
```

**Iterative inorder (avoids stack overflow for deep trees)**

```java
Deque<TreeNode> stack = new ArrayDeque<>();
TreeNode cur = root;

while (cur != null || !stack.isEmpty()) {
    while (cur != null) { stack.push(cur); cur = cur.left; }
    cur = stack.pop();
    process(cur);        // inorder position
    cur = cur.right;
}
```

**Iterative postorder (reverse of modified preorder)**

```java
Deque<TreeNode> stack = new ArrayDeque<>();
Deque<TreeNode> result = new ArrayDeque<>();
stack.push(root);
while (!stack.isEmpty()) {
    TreeNode n = stack.pop();
    result.push(n);
    if (n.left  != null) stack.push(n.left);
    if (n.right != null) stack.push(n.right);
}
// result deque now yields postorder
```

---

### DFS Pattern 4 — Iterative DFS 迭代DFS

**When:** graph is too deep for recursion (stack overflow risk) or explicit stack control needed.
**适用：** 图太深递归会栈溢出，或需要显式控制栈。

```java
Deque<Integer> stack = new ArrayDeque<>();
stack.push(src);
boolean[] visited = new boolean[n];
visited[src] = true;

while (!stack.isEmpty()) {
    int node = stack.pop();
    process(node);
    for (int neighbor : graph[node]) {
        if (!visited[neighbor]) {
            visited[neighbor] = true;
            stack.push(neighbor);
        }
    }
}
```

**Note:** iterative DFS does NOT give the same traversal order as recursive DFS due to stack reversal. If order matters, use recursive or reverse the neighbor list.

---

## 5. Advanced Skills 进阶技能

### Skill 1 — Mark Visited When Enqueuing (BFS) 入队时标记已访问

```java
// WRONG: mark when dequeuing — same node added to queue multiple times
while (!queue.isEmpty()) {
    int node = queue.poll();
    visited[node] = true;   // too late
    ...
}

// CORRECT: mark when enqueuing
queue.offer(src);
visited[src] = true;
```

### Skill 2 — Direction Array for Grids 方向数组

```java
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};            // 4-directional
int[][] dirs8 = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}}; // 8-directional
```

### Skill 3 — State Encoding for Implicit Graphs 隐式图状态编码

When nodes are complex (grid position + extra state): encode as a single key.
```java
int state = r * n + c;                    // 2D grid → 1D index
String state = r + "," + c + "," + k;    // with extra dimension k
```

### Skill 4 — BFS on Weighted Graph → Dijkstra

BFS only works for unit-weight edges. When edges have different weights, use Dijkstra (min-heap BFS).

### Skill 5 — Avoid Modifying Input 是否修改输入

Two choices for visited tracking:
- Mutate the grid (e.g., `grid[r][c] = '0'`) — simple, but destroys input
- Separate `boolean[][] visited` — safe, uses O(m×n) extra space

---

## 6. Interview Script 面试话术

**BFS English:**
> I'd use BFS because the problem asks for [shortest path / minimum steps / layer-by-layer processing]. BFS processes nodes level by level, so the first time we reach the target, it's guaranteed to be via the shortest path. I mark nodes visited when enqueuing to prevent duplicates.

**DFS English:**
> I'd use DFS because the problem requires [exploring all paths / checking connectivity / tree traversal]. DFS goes as deep as possible before backtracking. I mark nodes visited on entry and optionally unmark on exit if I need to explore multiple paths.

**中文：**
> [BFS] 用BFS因为要找最短路径/最少步数，BFS逐层扩展，第一次到达目标时必然是最短路径。入队时标记已访问防止重复。
> [DFS] 用DFS因为需要探索所有路径/判断连通性/树遍历，DFS深入到底再回溯，进入时标记访问，回溯时可以取消标记。

---

## 7. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| BFS 1: Shortest path | 127, 433, 1197 |
| BFS 2: Level-order | 102, 103, 199, 637 |
| BFS 3: Multi-source | 994, 542, 286 |
| BFS 4: Implicit graph | 127, 752 |
| DFS 1: Graph/grid | 200, 695, 733 |
| DFS 2: Cycle detection | 207, 785 |
| DFS 3: Tree traversals | 94, 144, 145, 105 |
| DFS 4: Iterative | 94 (iterative) |

**Recommended order:** 104 → 102 → 200 → 994 → 542 → 207 → 127 → 133

---

## 8. One-line Summary 一句话总结

```
BFS = shortest path via level-by-level queue expansion.
DFS = exhaustive exploration via deep-first recursion or stack.

BFS = 逐层队列扩展，保证最短路径。
DFS = 深入优先递归/栈，适合穷举探索。
```
