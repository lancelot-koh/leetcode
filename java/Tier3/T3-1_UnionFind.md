# T3-1 — Union-Find (Disjoint Set Union) 并查集

> **Core idea:** Maintain a forest where each tree = one connected component. `find` returns the root (with path compression); `union` merges two components (by rank). Near O(1) amortized per operation.
> **核心思想：** 用森林维护连通分量，每棵树是一个分量。`find`返回根（路径压缩）；`union`合并两分量（按秩合并）。均摊近O(1)每次操作。
>
> Complexity: O(α(n)) per operation (inverse Ackermann — practically O(1)).
> Full reference: `Union_Find/description.md`

---

## How It Works — Mental Model 理解模型

Think of Union-Find as a collection of trees where every node points toward its root. The root is the "representative" of its component — any two nodes sharing the same root are in the same group. **Path compression** works by making every node on the `find` path point directly to the root, so future queries skip all intermediate steps. **Union by rank** ensures we always hang the shallower tree beneath the deeper one, keeping tree height logarithmic. Without path compression the structure degrades to a linked list, making `find` O(n) per call. Without union by rank, a sequence of unions can build a chain even with compression, erasing much of the benefit. Together, both optimizations give the near-O(1) inverse-Ackermann bound.

**Key invariant:** After every `union`, every node's root is the unique representative of its connected component; `find(x) == find(y)` if and only if `x` and `y` are connected.

**Common mistake:** Forgetting to call `find` before comparing parents. Checking `parent[x] == parent[y]` directly fails because two nodes in the same component can have different (non-root) parents — always use `find(x) == find(y)`.

---

## Step-by-Step Trace 逐步追踪

```
Nodes: 0 1 2 3 4   (each is its own root initially)
parent: [0,1,2,3,4]   rank: [0,0,0,0,0]   components: 5

union(0,1): roots 0,1 — rank equal, attach 1 under 0, rank[0]++
  parent: [0,0,2,3,4]   rank: [1,0,0,0,0]   components: 4

union(2,3): roots 2,3 — attach 3 under 2, rank[2]++
  parent: [0,0,2,2,4]   rank: [1,0,1,0,0]   components: 3

union(0,2): roots 0,2 — rank equal, attach 2 under 0, rank[0]++
  parent: [0,0,0,2,4]   rank: [2,0,1,0,0]   components: 2

find(3): 3→2→0  (path compression: parent[3]=0, parent[2]=0)
  parent: [0,0,0,0,4]  ← tree is now flat
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Connected components (online, dynamic edges) | LC 547, 684 |
| Cycle detection in undirected graph | LC 684 |
| Kruskal's minimum spanning tree | MST problems |
| Accounts merge / group by equivalence | LC 721 |
| Number of provinces / friend circles | LC 547 |
| Redundant connection | LC 684, 685 |

**Signal:** "group", "connected", "merge", "same component", dynamic edges added one at a time.

**BFS/DFS vs Union-Find:**
- BFS/DFS: static graph, one-time connectivity query → BFS/DFS
- Union-Find: edges arrive online, many "same component?" queries → Union-Find

---

## Core Templates 核心模板

### Standard Union-Find class

```java
class UnionFind {
    int[] parent, rank;
    int components;

    UnionFind(int n) {
        parent = new int[n];
        rank   = new int[n];
        components = n;
        // Each node starts as its own root — n isolated components
        for (int i = 0; i < n; i++) { parent[i] = i; }
    }

    int find(int x) {
        if (parent[x] != x) {
            // Path compression: make x point directly to root,
            // flattening the tree so future finds on this path are O(1)
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py) { return false; }  // already in same component — no merge needed
        // Union by rank: attach the tree with lower rank under the one with higher rank
        // This keeps the overall tree height at most O(log n)
        if (rank[px] < rank[py]) { int tmp = px; px = py; py = tmp; }
        parent[py] = px;                 // py's root now points to px's root
        if (rank[px] == rank[py]) { rank[px]++; }  // only increases rank on tie
        components--;                    // two components became one
        return true;
    }

    boolean connected(int x, int y) { return find(x) == find(y); }
}
```

### Iterative path compression (avoid stack overflow on large n)

```java
int find(int x) {
    while (parent[x] != x) {
        parent[x] = parent[parent[x]];  // path halving: skip one level per step,
        x = parent[x];                  // same O(α(n)) complexity, no recursion stack
    }
    return x;
}
```

---

## Variants 变形

| Variant | Key change | Example |
|---|---|---|
| Number of components | Track `components--` on each successful union | LC 547 |
| Cycle detection | `union` returns false → edge is redundant (cycle exists) | LC 684 |
| Weighted/bipartite UF | Store parity/weight relative to parent | LC 399 |
| Virtual node | Add node n as "all connected" super-root | LC 305 |
| Dynamic connectivity | Same template; edges added incrementally | stream problems |

---

## Key Examples 关键例题

### Number of Provinces (LC 547)
```java
public int findCircleNum(int[][] isConnected) {
    int n = isConnected.length;
    UnionFind uf = new UnionFind(n);
    for (int i = 0; i < n; i++) {
        for (int j = i+1; j < n; j++) {
            if (isConnected[i][j] == 1) { uf.union(i, j); }
        }
    }
    return uf.components;
}
```

### Redundant Connection (LC 684)
```java
public int[] findRedundantConnection(int[][] edges) {
    UnionFind uf = new UnionFind(edges.length + 1);
    for (int[] e : edges) {
        if (!uf.union(e[0], e[1])) { return e; }  // already connected = redundant
    }
    return new int[]{};
}
```

### Accounts Merge (LC 721)
```java
public List<List<String>> accountsMerge(List<List<String>> accounts) {
    Map<String, Integer> emailToId = new HashMap<>();
    Map<String, String> emailToName = new HashMap<>();
    int id = 0;
    UnionFind uf = new UnionFind(10001);

    for (List<String> acc : accounts) {
        String name = acc.get(0);
        for (int i = 1; i < acc.size(); i++) {
            String email = acc.get(i);
            emailToId.putIfAbsent(email, id++);
            emailToName.put(email, name);
            uf.union(emailToId.get(acc.get(1)), emailToId.get(email));
        }
    }

    Map<Integer, List<String>> groups = new HashMap<>();
    for (String email : emailToId.keySet()) {
        int root = uf.find(emailToId.get(email));
        groups.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
    }

    List<List<String>> res = new ArrayList<>();
    for (List<String> emails : groups.values()) {
        Collections.sort(emails);
        emails.add(0, emailToName.get(emails.get(0)));
        res.add(emails);
    }
    return res;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Path compression is essential | Without it, find is O(n) worst case |
| Union by rank prevents tall trees | Attach smaller-rank tree under larger-rank root |
| `parent[i] = i` initialization | Every node starts as its own root |
| `find(x) != find(y)` before union | Check prevents self-union and tracks component count correctly |
| Weighted UF for ratios | Store ratio relative to parent; combine on find |
| 1-indexed input | `new UnionFind(n+1)` and use 1-indexed directly |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 547 Number of Provinces |
| Medium | LC 684 Redundant Connection |
| Medium | LC 200 Number of Islands (UF variant) |
| Medium | LC 721 Accounts Merge |
| Hard | LC 685 Redundant Connection II (directed) |
| Hard | LC 399 Evaluate Division (weighted UF) |

**Order:** 547 → 684 → 200 → 721 → 685 → 399

---

## One-line Summary

```
Union-Find = path compression + union by rank; near-O(1) "same group?" and "merge groups" on dynamic edges.
并查集 = 路径压缩 + 按秩合并；动态加边时近O(1)回答"同组？"和"合并组"。
```
