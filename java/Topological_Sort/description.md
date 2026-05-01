# Topological Sort 拓扑排序

> **Core idea:** Order nodes in a directed acyclic graph (DAG) such that for every edge u→v, u appears before v in the ordering.
> **核心思想：** 在有向无环图（DAG）中，对节点排序，使得每条边 u→v 中，u 都排在 v 之前。
>
> Two approaches: **Kahn's (BFS-based)** — iterative, cycle-detectable. **DFS-based** — post-order, elegant.
> Complexity: O(V + E).
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| course schedule / prerequisites | 课程表 / 先修课 |
| build order / dependency | 编译顺序 / 依赖关系 |
| task ordering | 任务排序 |
| alien dictionary | 外星语言字典 |
| deadlock detection | 死锁检测 |
| directed graph ordering | 有向图排序 |
| find if valid order exists | 判断是否存在有效顺序 |

**When NOT to use 不适用**

- Graph has cycles → topological sort doesn't exist (detect and report the cycle instead)
- Undirected graph → use BFS/DFS or Union-Find instead

**Key property:** Topological sort exists **if and only if** the directed graph has no cycles (is a DAG).
拓扑排序存在**当且仅当**有向图无环（是DAG）。

---

## 2. Quick Decision Guide 快速判断

```
Can all tasks be completed? (cycle check)      → Kahn's: return false if not all processed
Give a valid task order?                        → Kahn's: collect order while processing
Multiple valid orders exist?                    → Kahn's naturally produces one; DFS another
Count number of valid orders?                   → DP on DAG after topo sort
Find longest path in DAG?                       → DP on DAG after topo sort
Dictionary ordering of characters?             → Build graph from adjacent words → topo sort
```

---

## 3. Patterns 模式

---

### Pattern 1 — Kahn's Algorithm (BFS-based) Kahn算法（BFS实现）

**When:** detect cycles + produce a topological order iteratively.
**适用：** 检测环 + 迭代产生拓扑顺序。

**Template 模板**

```java
public int[] topoSort(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] inDegree = new int[n];

    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) {
        graph.get(e[0]).add(e[1]);
        inDegree[e[1]]++;
    }

    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++)
        if (inDegree[i] == 0) queue.offer(i);   // start with no-dependency nodes

    int[] order = new int[n];
    int idx = 0;

    while (!queue.isEmpty()) {
        int node = queue.poll();
        order[idx++] = node;
        for (int neighbor : graph.get(node)) {
            if (--inDegree[neighbor] == 0)       // all prerequisites done
                queue.offer(neighbor);
        }
    }

    return idx == n ? order : new int[0];        // idx < n → cycle detected
}
```

**Cycle detection 判环**

If `idx < n` after processing, some nodes were never enqueued (their in-degree never reached 0 due to a cycle).
若 `idx < n`，说明有节点始终无法入队（因为有环导致入度始终 > 0）。

**Why start with in-degree 0? 为什么从入度为0的节点开始？**

Nodes with in-degree 0 have no prerequisites — they can always be processed first.
入度为0的节点没有前置依赖，总是可以最先处理。

---

### Pattern 2 — DFS-based Topological Sort DFS实现拓扑排序

**When:** elegant recursive approach; post-order gives reverse topological order.
**适用：** 优雅的递归实现；后序遍历得到逆拓扑顺序。

**Template 模板**

```java
int[] state;    // 0=unvisited, 1=in-stack, 2=done
Deque<Integer> result = new ArrayDeque<>();

public boolean dfs(int node) {
    state[node] = 1;            // mark as in current DFS path
    for (int neighbor : graph.get(node)) {
        if (state[neighbor] == 1) return false;   // back edge = cycle
        if (state[neighbor] == 0 && !dfs(neighbor)) return false;
    }
    state[node] = 2;
    result.push(node);          // add to front → topological order
    return true;
}

// Call for all unvisited nodes
for (int i = 0; i < n; i++)
    if (state[i] == 0 && !dfs(i)) return false; // cycle detected
```

---

### Pattern 3 — Course Schedule (Canonical Problem) 课程表

**Example: Course Schedule I — can finish all? (LC 207)**

```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] inDegree = new int[numCourses];
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    for (int[] p : prerequisites) {
        graph.get(p[1]).add(p[0]);
        inDegree[p[0]]++;
    }

    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++)
        if (inDegree[i] == 0) queue.offer(i);

    int processed = 0;
    while (!queue.isEmpty()) {
        int cur = queue.poll();
        processed++;
        for (int next : graph.get(cur))
            if (--inDegree[next] == 0) queue.offer(next);
    }
    return processed == numCourses;
}
```

**Example: Course Schedule II — give the order (LC 210)**

Same as above — just collect nodes into `order[]` instead of counting.

---

### Pattern 4 — Alien Dictionary 外星语言字典

**When:** determine character ordering from a sorted list of words.
**适用：** 从有序单词列表推断字符的相对顺序。

**Approach:**
1. Compare adjacent words → extract ordering constraints (edges in char graph)
2. Topological sort the character graph
3. If cycle → invalid; if not all chars processed → invalid

```java
// Extract edges from adjacent word pairs
for (int i = 0; i < words.length - 1; i++) {
    String w1 = words[i], w2 = words[i + 1];
    int len = Math.min(w1.length(), w2.length());
    boolean found = false;
    for (int j = 0; j < len; j++) {
        if (w1.charAt(j) != w2.charAt(j)) {
            graph.get(w1.charAt(j) - 'a').add(w2.charAt(j) - 'a');
            inDegree[w2.charAt(j) - 'a']++;
            found = true;
            break;
        }
    }
    if (!found && w1.length() > w2.length()) return "";  // invalid ordering
}
```

---

### Pattern 5 — DP on DAG (Longest Path) DAG上的DP（最长路径）

**When:** after establishing topological order, propagate values along edges (longest path, minimum steps).
**适用：** 建立拓扑顺序后，沿边传播值（最长路径、最少步数）。

```java
int[] order = topoSort(n, edges);
int[] dp = new int[n];   // dp[i] = longest path ending at node i

for (int node : order) {
    for (int neighbor : graph.get(node)) {
        dp[neighbor] = Math.max(dp[neighbor], dp[node] + 1);
    }
}
return Arrays.stream(dp).max().getAsInt();
```

**Variants 变形**

| Variant | Example |
|---|---|
| Longest increasing path in matrix | LC 329 |
| Parallel courses minimum semesters | LC 1136 |
| Find all topological orderings | backtracking + Kahn's |

---

## 4. Advanced Skills 进阶技能

### Skill 1 — Build the Graph Correctly 正确建图

Most topological sort problems describe edges as "A must come before B" or "B requires A":

```java
// "course b requires course a" → edge: a → b
graph.get(a).add(b);
inDegree[b]++;

// "to take course v, must take u first" → edge: u → v
graph.get(u).add(v);
inDegree[v]++;
```

**Common mistake:** reversing edge direction. Always ask: "which node must come first?"

### Skill 2 — Kahn's vs DFS — When to Choose

| Prefer Kahn's | Prefer DFS |
|---|---|
| Need to detect cycle explicitly | Just need a valid order |
| Want BFS-style level processing | Recursive is more natural |
| Count nodes by processing layer | Post-order is the answer |

### Skill 3 — Multiple Sources (Parallel Processing)

In Kahn's, all in-degree-0 nodes are enqueued simultaneously — they can be processed "in parallel" (same level). This gives the **minimum number of steps** (levels in BFS-like processing).

```java
// Number of "rounds" / semesters needed = number of BFS levels
int rounds = 0;
while (!queue.isEmpty()) {
    int size = queue.size();
    for (int i = 0; i < size; i++) { ... }
    rounds++;
}
```

### Skill 4 — Verify All Nodes Processed 验证所有节点都被处理

```java
if (processed != n) {
    // Cycle exists — not all nodes could be processed
    return "CYCLE DETECTED";
}
```

---

## 5. Interview Script 面试话术

**English:**
> I'd use topological sort (Kahn's algorithm) because the problem involves ordering with dependencies. I build a directed graph where an edge u→v means u must come before v, then track in-degrees. I start with all zero-in-degree nodes and process them like BFS. If all n nodes are processed, a valid order exists; otherwise there's a cycle.

**中文：**
> 我会用拓扑排序（Kahn算法），因为题目涉及有依赖关系的顺序问题。建有向图，u→v 表示 u 必须在 v 之前，追踪每个节点的入度。从所有入度为0的节点开始，像BFS一样处理。若所有n个节点都处理完，说明存在有效顺序；否则有环。

---

## 6. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Kahn's basic | 207, 210 |
| 2. DFS-based | 207 (alternative) |
| 3. Course schedule | 207, 210, 630 |
| 4. Alien dictionary | LC 269 |
| 5. DP on DAG | 329, 1136 |

**Recommended order:** 207 → 210 → 329 → 269 → 1136 → 630

---

## 7. One-line Summary 一句话总结

```
Topological sort = process DAG nodes in dependency order; use Kahn's to detect cycles simultaneously.
拓扑排序 = 按依赖顺序处理DAG节点；用Kahn算法同时检测环。
```
