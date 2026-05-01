# T3-3 — Dijkstra's Algorithm 迪杰斯特拉算法

> **Core idea:** Greedy shortest-path from a single source in a weighted graph (non-negative weights). Use a min-heap: always relax the unvisited node with the smallest known distance.
> **核心思想：** 非负权有向图单源最短路径。用最小堆：每次松弛已知距离最小的未访问节点。
>
> Complexity: O((V + E) log V) with binary heap (PriorityQueue).
> Full reference: `BFS_DFS/description.md`, `Heap/description.md`

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Shortest path with weighted edges | LC 743, 1514 |
| Network delay time | LC 743 |
| Path with minimum effort | LC 1631 |
| Cheapest flights within k stops | LC 787 (modified Dijkstra) |
| Minimum cost to reach destination | many graph problems |

**Signal:** weighted graph, "minimum cost/distance/time", non-negative weights.

**Dijkstra vs BFS vs Bellman-Ford:**
| Situation | Algorithm |
|---|---|
| Unweighted graph | BFS (O(V+E)) |
| Non-negative weights | Dijkstra O((V+E)logV) |
| Negative weights (no negative cycles) | Bellman-Ford O(VE) |
| All pairs shortest path | Floyd-Warshall O(V³) |

---

## Core Templates 核心模板

### Standard Dijkstra

```java
public int[] dijkstra(int n, List<List<int[]>> adj, int src) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;

    // min-heap: {distance, node}
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    pq.offer(new int[]{0, src});

    while (!pq.isEmpty()) {
        int[] cur = pq.poll();
        int d = cur[0], u = cur[1];

        if (d > dist[u]) continue;    // stale entry (lazy deletion)

        for (int[] edge : adj.get(u)) {
            int v = edge[0], w = edge[1];
            if (dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
                pq.offer(new int[]{dist[v], v});
            }
        }
    }
    return dist;   // dist[i] = shortest distance from src to i
}
```

### With path reconstruction

```java
int[] prev = new int[n];
Arrays.fill(prev, -1);

// In the relaxation step:
if (dist[u] + w < dist[v]) {
    dist[v] = dist[u] + w;
    prev[v] = u;                  // track predecessor
    pq.offer(new int[]{dist[v], v});
}

// Reconstruct path to target:
List<Integer> path = new ArrayList<>();
for (int at = target; at != -1; at = prev[at]) path.add(at);
Collections.reverse(path);
```

---

## Variants 变形

| Variant | Key change | Example |
|---|---|---|
| Single-source shortest path | Standard Dijkstra | LC 743 |
| Path with max probability | Use max-heap, multiply probs | LC 1514 |
| Minimum effort path (grid) | State = (effort, r, c); relax via max edge | LC 1631 |
| Shortest path with k stops | State = (cost, node, stops); limit stops | LC 787 |
| 0-1 BFS (weights 0 or 1) | Deque: 0-weight → front, 1-weight → back | LC 1368 |
| Bidirectional Dijkstra | Run from src and dst; meet in middle | interview |

---

## Key Examples 关键例题

### Network Delay Time (LC 743)
```java
public int networkDelayTime(int[][] times, int n, int k) {
    List<List<int[]>> adj = new ArrayList<>();
    for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
    for (int[] t : times) adj.get(t[0]).add(new int[]{t[1], t[2]});

    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;

    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    pq.offer(new int[]{0, k});

    while (!pq.isEmpty()) {
        int[] cur = pq.poll();
        int d = cur[0], u = cur[1];
        if (d > dist[u]) continue;
        for (int[] edge : adj.get(u)) {
            int v = edge[0], w = edge[1];
            if (dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
                pq.offer(new int[]{dist[v], v});
            }
        }
    }

    int maxDist = 0;
    for (int i = 1; i <= n; i++) {
        if (dist[i] == Integer.MAX_VALUE) return -1;
        maxDist = Math.max(maxDist, dist[i]);
    }
    return maxDist;
}
```

### Path with Minimum Effort (LC 1631)
```java
public int minimumEffortPath(int[][] heights) {
    int m = heights.length, n = heights[0].length;
    int[][] effort = new int[m][n];
    for (int[] row : effort) Arrays.fill(row, Integer.MAX_VALUE);
    effort[0][0] = 0;

    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    pq.offer(new int[]{0, 0, 0});   // {effort, row, col}

    while (!pq.isEmpty()) {
        int[] cur = pq.poll();
        int e = cur[0], r = cur[1], c = cur[2];
        if (r == m-1 && c == n-1) return e;
        if (e > effort[r][c]) continue;
        for (int[] d : dirs) {
            int nr = r+d[0], nc = c+d[1];
            if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                int newE = Math.max(e, Math.abs(heights[nr][nc] - heights[r][c]));
                if (newE < effort[nr][nc]) {
                    effort[nr][nc] = newE;
                    pq.offer(new int[]{newE, nr, nc});
                }
            }
        }
    }
    return 0;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Skip stale entries: `if (d > dist[u]) continue` | Lazy deletion; avoids DecreaseKey operation |
| `Integer.MAX_VALUE` overflow | `dist[u] + w` can overflow; check `dist[u] != MAX_VALUE` or use `long` |
| Min-heap comparator: `a[0] - b[0]` | Sorts by distance ascending; use `Integer.compare(a[0], b[0])` to avoid overflow |
| Only works with non-negative weights | Negative edge → use Bellman-Ford |
| No visited[] needed | `d > dist[u]` check is equivalent; first pop of a node has optimal distance |
| State expansion for constraints | Add stops/fuel/color to heap entry: `{dist, node, extraState}` |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 743 Network Delay Time |
| Medium | LC 1631 Path with Minimum Effort |
| Medium | LC 1514 Path with Maximum Probability |
| Hard | LC 787 Cheapest Flights Within K Stops |
| Hard | LC 1368 Minimum Cost to Make at Least One Valid Path |

**Order:** 743 → 1631 → 1514 → 787 → 1368

---

## One-line Summary

```
Dijkstra = min-heap of (dist, node); always relax the closest unvisited node; skip stale pops with d > dist[u].
Dijkstra = 最小堆(距离, 节点)；每次松弛已知距离最小节点；跳过距离大于当前最优的旧条目。
```
