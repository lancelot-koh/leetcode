# T4-2 — Recursive DFS on Graphs (Mark/Unmark) 图上递归DFS（标记/取消标记）

> **Core idea:** For graphs (not trees), DFS must mark nodes to avoid revisiting. For backtracking-style graph DFS, mark on enter and unmark on exit so the same node can appear in different paths.
> **核心思想：** 图DFS必须标记节点避免重复访问。回溯式图DFS进入时标记、退出时取消，使同一节点可出现在不同路径中。
>
> Complexity: O(V + E) for simple DFS; O(V! × E) worst case for exhaustive path enumeration.
> Full reference: `BFS_DFS/description.md`, `Backtracking/description.md`

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Find all paths between two nodes | LC 797 |
| Cycle detection in directed graph | LC 207 (DFS variant) |
| Bipartite graph check (2-coloring) | LC 785, 886 |
| Connected components in undirected graph | LC 200, 547 |
| Count paths / reachability | DFS with memo |

**Signal:** graph traversal where the same node can be revisited in different search branches (paths enumeration), or where you need to detect structural properties (cycles, bipartiteness).

---

## Core Templates 核心模板

### Simple graph DFS (permanent mark)

```java
boolean[] visited = new boolean[n];

void dfs(int node, List<List<Integer>> adj) {
    visited[node] = true;
    for (int nei : adj.get(node))
        if (!visited[nei]) dfs(nei, adj);
    // post-order processing here if needed
}
```

### Backtracking DFS — all paths (mark/unmark)

```java
List<List<Integer>> result = new ArrayList<>();

void dfs(int node, int target, boolean[] visited,
         List<Integer> path, List<List<Integer>> adj) {
    if (node == target) { result.add(new ArrayList<>(path)); return; }
    for (int nei : adj.get(node)) {
        if (!visited[nei]) {
            visited[nei] = true;
            path.add(nei);
            dfs(nei, target, visited, path, adj);
            path.remove(path.size() - 1);   // unmark path
            visited[nei] = false;            // unmark visited
        }
    }
}
```

### Cycle detection — directed graph (3-color)

```java
int[] color = new int[n];  // 0=unvisited, 1=in-stack, 2=done

boolean hasCycle(int node, List<List<Integer>> adj) {
    color[node] = 1;                    // entering: mark gray
    for (int nei : adj.get(node)) {
        if (color[nei] == 1) return true;         // back edge = cycle
        if (color[nei] == 0 && hasCycle(nei, adj)) return true;
    }
    color[node] = 2;                    // done: mark black
    return false;
}
```

### Bipartite check (2-coloring DFS)

```java
int[] color = new int[n];  // 0=uncolored, 1 or -1 = two colors

boolean bipartite(int node, int c, List<List<Integer>> adj) {
    color[node] = c;
    for (int nei : adj.get(node)) {
        if (color[nei] == c) return false;          // same color = not bipartite
        if (color[nei] == 0 && !bipartite(nei, -c, adj)) return false;
    }
    return true;
}
```

---

## Variants 变形

| Variant | Mark strategy | Example |
|---|---|---|
| Connectivity (undirected) | Permanent mark | LC 200, 547 |
| All paths enumeration | Mark + unmark (backtrack) | LC 797 |
| Cycle detection (directed) | 3-color: gray/black/white | LC 207 |
| Bipartite check | 2-color alternating | LC 785 |
| Topological sort (DFS) | Post-order + reverse | LC 210 |
| Tarjan SCC | Lowlink + stack | advanced |

---

## Key Examples 关键例题

### All Paths From Source to Target (LC 797)
```java
public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    path.add(0);
    dfs(graph, 0, path, result);
    return result;
}

void dfs(int[][] graph, int node, List<Integer> path, List<List<Integer>> result) {
    if (node == graph.length - 1) { result.add(new ArrayList<>(path)); return; }
    for (int nei : graph[node]) {
        path.add(nei);
        dfs(graph, nei, path, result);
        path.remove(path.size() - 1);
    }
}
```

### Is Graph Bipartite? (LC 785)
```java
public boolean isBipartite(int[][] graph) {
    int n = graph.length;
    int[] color = new int[n];
    for (int i = 0; i < n; i++)
        if (color[i] == 0 && !dfs(graph, i, 1, color)) return false;
    return true;
}

boolean dfs(int[][] graph, int node, int c, int[] color) {
    color[node] = c;
    for (int nei : graph[node]) {
        if (color[nei] == c) return false;
        if (color[nei] == 0 && !dfs(graph, nei, -c, color)) return false;
    }
    return true;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Mark before recursing | `visited[node] = true` BEFORE iterating neighbors to handle self-loops |
| Unmark only for path enumeration | Simple reachability/components: permanent mark; all-paths: must unmark |
| 3-color for directed cycles | 2-color (boolean) misses back edges in directed graphs |
| Disconnected graph | Loop through all nodes calling DFS to handle disconnected components |
| Tree vs Graph DFS | Trees have no cycles, so no visited array needed; graphs always need one |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 797 All Paths From Source to Target |
| Medium | LC 785 Is Graph Bipartite? |
| Medium | LC 886 Possible Bipartition |
| Medium | LC 207 Course Schedule (DFS cycle detection) |
| Medium | LC 695 Max Area of Island |

**Order:** 695 → 797 → 207 → 785 → 886

---

## One-line Summary

```
Graph DFS = permanent mark for connectivity; mark+unmark for path enumeration; 3-color for directed cycle detection.
图DFS = 永久标记做连通性；进入标记+退出取消做路径枚举；3色标记做有向环检测。
```
