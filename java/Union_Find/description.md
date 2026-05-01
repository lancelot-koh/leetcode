# Union-Find (Disjoint Set Union) 并查集

> **Core idea:** Maintain a collection of disjoint sets with near-O(1) `union` and `find` operations. Efficiently answers: "Are these two elements in the same group?"
> **核心思想：** 维护不相交集合，近似 O(1) 的合并和查找操作。高效回答："这两个元素在同一组吗？"
>
> With path compression + union by rank: O(α(n)) ≈ O(1) per operation.
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| connected components | 连通分量 |
| dynamic connectivity | 动态连通性 |
| cycle detection (undirected) | 无向图判环 |
| grouping / clustering | 分组 / 聚类 |
| friend circles | 朋友圈 |
| accounts merge | 账号合并 |
| redundant connection | 多余的边 |
| Kruskal's MST | 最小生成树 |

**Union-Find vs BFS/DFS**

| Scenario | Prefer |
|---|---|
| Static graph, find all components once | BFS/DFS |
| Dynamic: edges added over time | Union-Find |
| Need shortest path | BFS/Dijkstra |
| Need only "same component?" queries | Union-Find |
| Undirected cycle detection | Either (Union-Find simpler) |

---

## 2. Core Implementation 核心实现

```java
class UnionFind {
    int[] parent, rank;
    int components;

    UnionFind(int n) {
        parent = new int[n];
        rank   = new int[n];
        components = n;
        for (int i = 0; i < n; i++) parent[i] = i;   // each node is its own root
    }

    // Find root with path compression
    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]);   // path compression: flatten tree
        return parent[x];
    }

    // Union by rank
    boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py) return false;         // already connected — edge is redundant

        if      (rank[px] < rank[py]) parent[px] = py;
        else if (rank[px] > rank[py]) parent[py] = px;
        else { parent[py] = px; rank[px]++; }

        components--;
        return true;
    }

    boolean connected(int x, int y) { return find(x) == find(y); }
}
```

**Path compression 路径压缩**

After finding the root, point every node on the path directly to the root. Future `find` calls are O(1).
找到根后，把路径上所有节点直接指向根。之后的 `find` 几乎 O(1)。

**Union by rank 按秩合并**

Attach the shorter tree under the taller one. Prevents the tree from becoming a linked list.
把较矮的树接在较高的树下面，防止树退化为链表。

---

## 3. Quick Decision Guide 快速判断

```
Count connected components?                       → Pattern 1: Basic Union-Find
Detect if adding edge creates a cycle?            → Pattern 2: Cycle detection
Merge groups with shared properties?              → Pattern 3: Grouping
Build MST (minimum spanning tree)?                → Pattern 4: Kruskal's
Map non-integer nodes to indices?                 → Pattern 5: Map-based Union-Find
```

---

## 4. Patterns 模式

---

### Pattern 1 — Connected Components 连通分量

**When:** count groups or check if nodes are reachable from each other.
**适用：** 计数分组或判断节点是否互相可达。

**Template 模板**

```java
UnionFind uf = new UnionFind(n);
for (int[] edge : edges)
    uf.union(edge[0], edge[1]);
return uf.components;
```

**Variants 变形**

| Variant | Example |
|---|---|
| Number of provinces (friend circles) | LC 547 |
| Number of connected components | LC 323 |
| Number of islands (grid) | LC 200 (or BFS/DFS) |
| Accounts merge | LC 721 |

**Example: Number of Provinces (LC 547)**

```java
public int findCircleNum(int[][] isConnected) {
    int n = isConnected.length;
    UnionFind uf = new UnionFind(n);
    for (int i = 0; i < n; i++)
        for (int j = i + 1; j < n; j++)
            if (isConnected[i][j] == 1) uf.union(i, j);
    return uf.components;
}
```

---

### Pattern 2 — Cycle Detection 判环（无向图）

**When:** check if adding an edge creates a cycle in an undirected graph.
**适用：** 判断无向图中某条边是否形成环。

**Key insight 核心原理**

If `union(u, v)` returns `false` (they were already connected), adding edge (u, v) creates a cycle.
若 `union(u, v)` 返回 `false`（已连通），则该边形成环。

**Template 模板**

```java
for (int[] edge : edges) {
    if (!uf.union(edge[0], edge[1]))
        return edge;   // this edge is redundant (creates a cycle)
}
```

**Variants 变形**

| Variant | Example |
|---|---|
| Find redundant connection | LC 684 |
| Detect cycle in undirected graph | LC 261 |
| Kruskal's MST (skip edges that form cycles) | Pattern 4 |

---

### Pattern 3 — Grouping with Shared Properties 按共同属性分组

**When:** merge items that share a property (same email → same account, same character → same group).
**适用：** 有共同属性的项目合并（同邮件→同账号，同字符→同组）。

**Pattern:** map each unique property value to an index, then union items sharing the property.

**Example: Accounts Merge (LC 721)**

```java
public List<List<String>> accountsMerge(List<List<String>> accounts) {
    Map<String, Integer> emailToId = new HashMap<>();
    int id = 0;
    for (List<String> account : accounts)
        for (int i = 1; i < account.size(); i++)
            emailToId.putIfAbsent(account.get(i), id++);

    UnionFind uf = new UnionFind(id);
    for (List<String> account : accounts)
        for (int i = 2; i < account.size(); i++)
            uf.union(emailToId.get(account.get(1)), emailToId.get(account.get(i)));

    // Group emails by root
    Map<Integer, TreeSet<String>> groups = new HashMap<>();
    for (Map.Entry<String, Integer> e : emailToId.entrySet())
        groups.computeIfAbsent(uf.find(e.getValue()), k -> new TreeSet<>()).add(e.getKey());

    // Build result (attach owner name)
    // ...
}
```

---

### Pattern 4 — Kruskal's MST 最小生成树

**When:** find the minimum spanning tree of a weighted graph.
**适用：** 求加权图的最小生成树。

**Template 模板**

```java
Arrays.sort(edges, (a, b) -> a[2] - b[2]);   // sort by weight ascending

UnionFind uf = new UnionFind(n);
int totalCost = 0, edgesUsed = 0;

for (int[] edge : edges) {
    if (uf.union(edge[0], edge[1])) {          // only add if not creating a cycle
        totalCost += edge[2];
        edgesUsed++;
        if (edgesUsed == n - 1) break;          // MST has exactly n-1 edges
    }
}
return edgesUsed == n - 1 ? totalCost : -1;   // -1 if graph is disconnected
```

**Variants 变形**

| Variant | Example |
|---|---|
| Min cost to connect all points | LC 1584 |
| Critical connections / bridges | LC 1489 |
| Optimize water distribution | LC 1168 |

---

### Pattern 5 — Map-based Union-Find (Non-integer Nodes) 字符串节点的并查集

**When:** nodes are strings, characters, or other non-integer types.
**适用：** 节点是字符串或其他非整数类型时。

```java
class MapUnionFind {
    Map<String, String> parent = new HashMap<>();

    String find(String x) {
        parent.putIfAbsent(x, x);
        if (!parent.get(x).equals(x))
            parent.put(x, find(parent.get(x)));   // path compression
        return parent.get(x);
    }

    void union(String x, String y) {
        String px = find(x), py = find(y);
        if (!px.equals(py)) parent.put(px, py);
    }

    boolean connected(String x, String y) { return find(x).equals(find(y)); }
}
```

---

## 5. Advanced Skills 进阶技能

### Skill 1 — Always Track Component Count 追踪连通分量数

Initialize `components = n`, decrement by 1 in each successful `union`. Gives O(1) answer to "how many components?"

### Skill 2 — Weighted Union-Find 带权并查集

For problems where you need to track the relationship/ratio between nodes (e.g., LC 399 Evaluate Division):

```java
double[] weight;   // weight[i] = ratio from i to parent[i]

double find(int x) {
    if (parent[x] == x) return 1.0;
    double parentWeight = find(parent[x]);
    weight[x] *= parentWeight;   // accumulate weight during compression
    parent[x] = parent[parent[x]];
    return weight[x];
}
```

### Skill 3 — Grid Union-Find 网格并查集

Convert 2D grid position `(r, c)` to a 1D index: `id = r * cols + c`.

```java
int id(int r, int c) { return r * cols + c; }
// Then: uf.union(id(r, c), id(nr, nc));
```

### Skill 4 — Virtual Node Trick 虚拟节点技巧

Add a virtual "super-source" or "super-sink" node to connect all sources/sinks:

```java
int virtualNode = n;   // node index n is virtual
for (int source : sources) uf.union(source, virtualNode);
// Now check: uf.connected(a, b) ↔ a and b both connect to virtual node
```

---

## 6. Interview Script 面试话术

**English:**
> I'd use Union-Find because the problem involves dynamic grouping / connectivity queries. Each `find` uses path compression to flatten the tree, and `union` uses rank to keep trees shallow. Together they give O(α(n)) ≈ O(1) per operation. I track the component count by decrementing it on each successful union.

**中文：**
> 我会用并查集，因为题目涉及动态分组/连通性查询。`find` 用路径压缩压平树，`union` 用按秩合并保持树的高度。两者结合每次操作 O(α(n)) ≈ O(1)。通过每次成功 union 时减少分量计数，O(1) 回答"有多少连通分量"。

---

## 7. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Connected components | 547, 323, 200 |
| 2. Cycle detection | 684, 261 |
| 3. Grouping | 721, 839 |
| 4. Kruskal's MST | 1584, 1168 |
| 5. Map-based | 399, 952 |

**Recommended order:** 547 → 323 → 684 → 1584 → 721 → 399

---

## 8. One-line Summary 一句话总结

```
Union-Find = near-O(1) grouping; union merges sets, find identifies the group root.
并查集 = 近似 O(1) 分组；union 合并集合，find 识别所属组的根节点。
```
