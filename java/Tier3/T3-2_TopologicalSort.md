# T3-2 — Topological Sort 拓扑排序

> **Core idea:** For a DAG, produce a linear ordering where every edge `u→v` has `u` before `v`. Kahn's algorithm (BFS, in-degree): start from all zero-in-degree nodes. DFS: post-order reversal.
> **核心思想：** 对DAG产生线性排序，使每条边`u→v`中`u`排在`v`前。Kahn算法（BFS，入度法）：从所有零入度节点出发。DFS：后序逆序。
>
> Complexity: O(V + E) time, O(V + E) space.
> Full reference: `Topological_Sort/description.md`

---

## How It Works — Mental Model 理解模型

Topological sort exploits one simple observation: in a DAG, any node with no incoming edges is safe to output immediately — nothing needs to come before it. Kahn's algorithm (BFS) repeatedly finds such "ready" nodes, outputs them, and decrements the in-degree of their neighbors. When a neighbor's in-degree hits zero it becomes ready and joins the queue. The invariant maintained throughout is that every node in the queue has had all its prerequisites processed. If the graph has a cycle, some nodes will never reach in-degree zero, so the output list will be shorter than `n` — this is how cycle detection falls out naturally. The DFS approach reaches the same result through the opposite lens: a node is fully processed (and safe to output) only after all nodes reachable from it are processed, so post-order reversal gives the topological order.

**Key invariant:** A node enters the BFS queue exactly when its in-degree reaches zero, guaranteeing every predecessor has already been placed in the output list.

**Common mistake:** Getting the edge direction backwards. If course B requires course A, the edge is `A → B` (A must come before B), so `inDegree[B]++`. Swapping to `inDegree[A]++` silently produces wrong results — the "can finish" check still passes for acyclic graphs but the order is reversed.

---

## Step-by-Step Trace 逐步追踪

```
Courses: 0,1,2,3   Prerequisites: [[1,0],[2,0],[3,1],[3,2]]
Meaning: 0→1, 0→2, 1→3, 2→3

inDegree: [0, 1, 1, 2]
Init queue (in-degree 0): [0]

Pop 0: order=[0], decrement neighbors 1,2 → inDegree=[0,0,0,2]
  1 reaches 0 → enqueue 1;  2 reaches 0 → enqueue 2. queue=[1,2]

Pop 1: order=[0,1], decrement neighbor 3 → inDegree=[0,0,0,1]. queue=[2]

Pop 2: order=[0,1,2], decrement neighbor 3 → inDegree=[0,0,0,0]
  3 reaches 0 → enqueue 3. queue=[3]

Pop 3: order=[0,1,2,3]. queue empty.
order.size()==4==numCourses → no cycle, valid order found.
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Course schedule (prerequisites) | LC 207, 210 |
| Task dependency ordering | LC 210 |
| Detect cycle in directed graph | LC 207 (if not all nodes processed) |
| Compilation order / build systems | interview |
| Alien dictionary (derive ordering) | LC 269 |
| Minimum height trees (leaf-trimming) | LC 310 |

**Signal:** directed dependencies, "can we complete all tasks?", "find ordering that satisfies all constraints."

---

## Core Templates 核心模板

### Kahn's Algorithm (BFS, in-degree)

```java
int[] inDegree = new int[n];
List<List<Integer>> adj = new ArrayList<>();
for (int i = 0; i < n; i++) { adj.add(new ArrayList<>()); }

// Build directed graph: pre[1] must come before pre[0]
for (int[] pre : prerequisites) {
    adj.get(pre[1]).add(pre[0]);   // edge: prerequisite → course
    inDegree[pre[0]]++;            // course has one more dependency
}

// All nodes with no remaining dependencies are immediately processable
Queue<Integer> queue = new LinkedList<>();
for (int i = 0; i < n; i++) {
    if (inDegree[i] == 0) { queue.offer(i); }
}

List<Integer> order = new ArrayList<>();
while (!queue.isEmpty()) {
    int node = queue.poll();
    order.add(node);               // node is fully processed — safe to place in order
    for (int nei : adj.get(node)) {
        // Removing node satisfies one dependency for each neighbor
        if (--inDegree[nei] == 0) { queue.offer(nei); }  // neighbor now unblocked
    }
}

// If order.size() == n → valid topo order (no cycle)
// Else → some nodes still have inDegree > 0, meaning they're in a cycle
```

### DFS post-order (reverse = topo order)

```java
int[] color = new int[n];   // 0=white(unvisited), 1=gray(in current path), 2=black(done)
List<Integer> result = new ArrayList<>();
boolean hasCycle = false;

void dfs(int node, List<List<Integer>> adj) {
    color[node] = 1;                               // entering this node's DFS subtree
    for (int nei : adj.get(node)) {
        if (color[nei] == 1) { hasCycle = true; return; }  // back edge: nei is ancestor → cycle
        if (color[nei] == 0) { dfs(nei, adj); }            // unvisited: recurse
    }
    color[node] = 2;       // all descendants processed — this node is "done"
    result.add(node);      // post-order: node goes after everything it depends on
}

// After all DFS calls: Collections.reverse(result) = topo order
// (last-finished = no dependencies = should appear first)
```

---

## Variants 变形

| Variant | Key change | Example |
|---|---|---|
| Can finish all courses? | Kahn: `order.size() == n` | LC 207 |
| Find one valid order | Kahn: return `order` | LC 210 |
| Lexicographically smallest | Use PriorityQueue (min-heap) instead of plain Queue | interview |
| All topological orders | Backtracking + in-degree manipulation | interview |
| Minimum height trees | Trim leaves iteratively (reverse topo) | LC 310 |
| Alien dictionary | Build graph from adjacent word pairs | LC 269 |

---

## Key Examples 关键例题

### Course Schedule (LC 207) — can finish?
```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    int[] inDegree = new int[numCourses];
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) { adj.add(new ArrayList<>()); }

    for (int[] p : prerequisites) {
        adj.get(p[1]).add(p[0]);
        inDegree[p[0]]++;
    }

    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (inDegree[i] == 0) { q.offer(i); }
    }

    int processed = 0;
    while (!q.isEmpty()) {
        int node = q.poll(); processed++;
        for (int nei : adj.get(node)) {
            if (--inDegree[nei] == 0) { q.offer(nei); }
        }
    }
    return processed == numCourses;
}
```

### Course Schedule II (LC 210) — return order
```java
public int[] findOrder(int numCourses, int[][] prerequisites) {
    int[] inDegree = new int[numCourses];
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) { adj.add(new ArrayList<>()); }

    for (int[] p : prerequisites) {
        adj.get(p[1]).add(p[0]);
        inDegree[p[0]]++;
    }

    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (inDegree[i] == 0) { q.offer(i); }
    }

    int[] order = new int[numCourses];
    int idx = 0;
    while (!q.isEmpty()) {
        int node = q.poll();
        order[idx++] = node;
        for (int nei : adj.get(node)) {
            if (--inDegree[nei] == 0) { q.offer(nei); }
        }
    }
    return idx == numCourses ? order : new int[]{};
}
```

### Minimum Height Trees (LC 310) — trim leaves
```java
public List<Integer> findMinHeightTrees(int n, int[][] edges) {
    if (n == 1) { return List.of(0); }
    int[] degree = new int[n];
    List<Set<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) { adj.add(new HashSet<>()); }
    for (int[] e : edges) {
        adj.get(e[0]).add(e[1]);
        adj.get(e[1]).add(e[0]);
        degree[e[0]]++; degree[e[1]]++;
    }
    Queue<Integer> leaves = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (degree[i] == 1) { leaves.offer(i); }
    }

    int remaining = n;
    while (remaining > 2) {
        int size = leaves.size();
        remaining -= size;
        for (int i = 0; i < size; i++) {
            int leaf = leaves.poll();
            for (int nei : adj.get(leaf)) {
                adj.get(nei).remove(leaf);
                if (--degree[nei] == 1) { leaves.offer(nei); }
            }
        }
    }
    return new ArrayList<>(leaves);
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Cycle detection via size | If `order.size() < n` after Kahn's → cycle exists |
| Edge direction matters | `pre → course` means "take pre before course"; get direction right |
| DFS gray = cycle | Back edge (node still being visited) = cycle in directed graph |
| Kahn vs DFS | Kahn: iterative, cycle detection easy; DFS: natural for recursive structures |
| Lexicographic order | Replace Queue with PriorityQueue to always pick smallest available |
| Build adj list first | Kahn iterates the adj list repeatedly; build it in O(E) up front |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 207 Course Schedule |
| Medium | LC 210 Course Schedule II |
| Medium | LC 310 Minimum Height Trees |
| Hard | LC 269 Alien Dictionary |
| Hard | LC 329 Longest Increasing Path in Matrix |

**Order:** 207 → 210 → 310 → 269 → 329

---

## One-line Summary

```
Topological sort = BFS from zero-in-degree nodes, decrement neighbors; if processed count == n, valid order; else cycle.
拓扑排序 = 从零入度节点BFS，递减邻居入度；处理数==n则有效序列，否则有环。
```
