# T1-5 — BFS (Graph) 图的广度优先搜索

> **Core idea:** Explore a graph level by level using a queue. The first time you reach any node is via the shortest path (in unweighted graphs).
> **核心思想：** 用队列逐层探索图。第一次到达任意节点时，路径即为最短路径（无权图）。
>
> Complexity: O(V + E) time, O(V) space.
> Full reference: `BFS_DFS/description.md` BFS Patterns 1–4

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Shortest path / minimum steps (unweighted) | LC 127 Word Ladder, LC 433 |
| Level-by-level processing | Level-order traversal |
| Spread from multiple sources at once | LC 994 Rotting Oranges, LC 542 |
| Implicit graph (states as nodes) | LC 752 Open Lock |
| Connectivity check | Is A reachable from B? |

**BFS not DFS when:** you need the shortest path — DFS doesn't guarantee it.

---

## How it Works — Mental Model 原理与直觉

BFS processes nodes in order of their distance from the source: first all nodes at distance 0 (the source itself), then distance 1, then distance 2, and so on. This level-by-level expansion is exactly why BFS guarantees the shortest path in an unweighted graph — any path discovered later must be longer.

The queue enforces this FIFO discipline: the first node dequeued is always the one closest to the source that hasn't been processed yet. Marking nodes as visited *when enqueuing* (not when dequeuing) is critical because multiple nodes at the same level can all generate the same neighbor. Without early marking, that neighbor gets enqueued multiple times and processed multiple times.

Multi-source BFS simply treats all sources as "level 0" nodes enqueued before the main loop starts. From the algorithm's perspective there is no difference — it still explores level by level, but the frontier originates from multiple starting points simultaneously.

**Invariant:** Every node in the queue at any moment has the same `steps` value OR `steps + 1`. Nodes at the same level are processed before any node at the next level is dequeued.

---

## Step-by-Step Trace — Shortest Path 执行追踪

```
Graph: 0→1, 0→2, 1→3, 2→3, 3→4. Find shortest path 0→4.
Init:    queue=[0], visited={0}, steps=0
Level 0: poll 0 → neighbors 1,2 → enqueue both, visited={0,1,2}
         steps++ → steps=1
Level 1: poll 1 → neighbor 3 → enqueue, visited={0,1,2,3}
         poll 2 → neighbor 3 already visited → skip
         steps++ → steps=2
Level 2: poll 3 → neighbor 4 → enqueue, visited={0,1,2,3,4}
         poll 4 → node==target → return steps=3  ✓
```

---

## Core Template 核心模板

```java
Queue<Integer> queue = new LinkedList<>();
boolean[] visited = new boolean[n];

queue.offer(start);
visited[start] = true;   // mark on ENQUEUE — if you wait until dequeue, duplicates flood the queue
int steps = 0;

while (!queue.isEmpty()) {
    int size = queue.size();             // snapshot: nodes added during this loop belong to level+1
    for (int i = 0; i < size; i++) {
        int node = queue.poll();
        if (node == target) { return steps; }   // first arrival = shortest path

        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;       // mark immediately, before other nodes can re-enqueue it
                queue.offer(neighbor);
            }
        }
    }
    steps++;   // finished all nodes at this level; next level is one step further
}
return -1;  // unreachable
```

**Key rule:** Mark visited **when enqueuing** (not when dequeuing) — prevents the same node from being added to the queue multiple times.
**关键：** 入队时标记已访问，而非出队时——防止同一节点多次入队。

---

## Variants 变形

| Variant | Change | Example |
|---|---|---|
| Single source shortest path | standard template | LC 127 |
| Multi-source BFS | enqueue ALL sources upfront | LC 994, 542 |
| Grid BFS | `int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}}` | LC 200, 286 |
| Implicit graph | encode state as key (string, int) | LC 752, 127 |
| Level-order tree | same template on TreeNode | LC 102 |

---

## Key Examples 关键例题

### BFS Shortest Path (Generic)
```java
public int bfs(int[][] graph, int start, int target) {
    boolean[] visited = new boolean[graph.length];
    Queue<Integer> q = new LinkedList<>();
    q.offer(start); visited[start] = true;
    int steps = 0;
    while (!q.isEmpty()) {
        for (int sz = q.size(); sz > 0; sz--) {
            int cur = q.poll();
            if (cur == target) { return steps; }
            for (int nb : graph[cur]) {
                if (!visited[nb]) { visited[nb] = true; q.offer(nb); }
            }
        }
        steps++;
    }
    return -1;
}
```

### Multi-source BFS — Rotting Oranges (LC 994)
```java
public int orangesRotting(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    Queue<int[]> q = new LinkedList<>();
    int fresh = 0;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

    for (int r = 0; r < m; r++) {
        for (int c = 0; c < n; c++) {
            if (grid[r][c] == 2) { q.offer(new int[]{r, c}); }
            if (grid[r][c] == 1) { fresh++; }
        }
    }

    int minutes = 0;
    while (!q.isEmpty() && fresh > 0) {
        for (int sz = q.size(); sz > 0; sz--) {
            int[] cell = q.poll();
            for (int[] d : dirs) {
                int nr = cell[0]+d[0], nc = cell[1]+d[1];
                if (nr>=0 && nr<m && nc>=0 && nc<n && grid[nr][nc]==1) {
                    grid[nr][nc] = 2; fresh--; q.offer(new int[]{nr,nc});
                }
            }
        }
        minutes++;
    }
    return fresh == 0 ? minutes : -1;
}
```

### Implicit Graph BFS — Word Ladder (LC 127)
```java
public int ladderLength(String begin, String end, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(end)) { return 0; }
    Queue<String> q = new LinkedList<>();
    q.offer(begin); wordSet.remove(begin);
    int steps = 1;
    while (!q.isEmpty()) {
        for (int sz = q.size(); sz > 0; sz--) {
            char[] word = q.poll().toCharArray();
            for (int i = 0; i < word.length; i++) {
                char orig = word[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    word[i] = c;
                    String next = new String(word);
                    if (next.equals(end)) { return steps + 1; }
                    if (wordSet.remove(next)) { q.offer(next); }
                }
                word[i] = orig;
            }
        }
        steps++;
    }
    return 0;
}
```

---

## Common Mistake / Gotcha 常见错误

**Marking visited on dequeue instead of enqueue:** This is the single most common BFS bug. If you mark a node only when you dequeue it, then between the time it is enqueued and dequeued, another neighbor at the same level can enqueue it again. In a densely connected graph this causes O(E) duplicate enqueues instead of O(V), and TLE or wrong answers on shortest-path problems.

**Forgetting the `fresh == 0` check (LC 994):** After the BFS completes, if `fresh > 0`, return -1 (some oranges are isolated and can never be reached). Returning `minutes` unconditionally gives wrong answers on grids with isolated fresh oranges.

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Mark visited on ENQUEUE | Prevents duplicate entries in queue; doing it on dequeue causes O(E) redundant enqueues |
| `remove()` from wordSet = visited | For implicit graphs, using the set as visited avoids a separate `Set<String> visited` |
| Grid direction array | `int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}}` — standard 4-direction |
| Level counter | Increment `steps` after processing each entire level |
| Multi-source startup | Enqueue ALL source nodes before starting; they're all "level 0" |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 111 Minimum Depth of Binary Tree |
| Medium | LC 994 Rotting Oranges, LC 542 01 Matrix |
| Medium | LC 127 Word Ladder, LC 752 Open Lock |
| Medium | LC 286 Walls and Gates |
| Hard | LC 126 Word Ladder II |

**Order:** 994 → 542 → 286 → 127 → 752

---

## One-line Summary

```
BFS = explore level by level via queue; first arrival = shortest path; mark visited on enqueue.
BFS = 队列逐层探索，第一次到达 = 最短路径，入队时标记已访问。
```
