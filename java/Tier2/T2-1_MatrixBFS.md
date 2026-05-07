# T2-1 — Matrix BFS 矩阵BFS

> **Core idea:** Treat a 2D grid as a graph where each cell is a node and adjacent cells (4- or 8-directional) are neighbors. BFS gives shortest path; DFS gives connectivity.
> **核心思想：** 把二维网格当作图，每个格子是节点，相邻格子是边。BFS求最短路径，DFS求连通性。
>
> Complexity: O(m×n) time and space.
> Full reference: `BFS_DFS/description.md`

---

## When to Use 什么时候用

| Trigger | Use | Example |
|---|---|---|
| Shortest path in grid | BFS | LC 1091, 542, 994 |
| Flood fill / paint | DFS or BFS | LC 733, 200 |
| Number of islands / components | DFS or Union-Find | LC 200, 695 |
| Distance from each cell to nearest source | Multi-source BFS | LC 542, 286 |
| Region surrounded by boundary | BFS/DFS from border | LC 130 |

---

## How it Works — Mental Model 算法原理

BFS explores cells layer by layer, like ripples spreading from a stone dropped in water. Every cell at distance `d` is fully processed before any cell at distance `d+1` is touched. This layer-by-layer guarantee is exactly why BFS finds the **shortest** path: the first time you reach the destination, you arrived via the fewest steps — any later arrival would require at least as many. DFS, by contrast, dives deep before it fans out; it finds *a* path, not necessarily the shortest. Without marking a cell visited **before** enqueueing it, multiple cells can enqueue the same neighbor independently, causing exponential re-processing and potentially wrong distances.

**Key invariant:** Every cell in the queue has been marked visited. A cell is visited at most once, so total work is O(m×n).

**Common mistake / gotcha:** Marking visited **after** polling (instead of before enqueuing) allows the same cell to be added to the queue multiple times from different neighbors. In the worst case this turns O(m×n) BFS into something much slower and can produce incorrect shortest-path answers.

---

## Step-by-Step Trace 执行步骤示意

Example: `shortestPathBinaryMatrix` on a 3×3 grid (0=open, 1=blocked):
```
Grid:       Start=(0,0)  End=(2,2)
0 0 1
0 0 0
1 0 0
```
```
Init:  queue=[(0,0,dist=1)], grid[0][0]=1 (marked)
Step 1 (dist=1): poll (0,0) → explore 8 dirs → valid: (0,1),(1,0),(1,1) → enqueue all, mark visited
Step 2 (dist=2): poll (0,1) → valid neighbor: (1,2) → enqueue; poll (1,0) → valid: (2,1) → enqueue; poll (1,1) → valid: (2,2) → enqueue
Step 3 (dist=3): poll (1,2); poll (2,1); poll (2,2) → reached end → return 3
```

---

## Core Templates 核心模板

### Direction Array (always use this) 方向数组

```java
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};      // 4-directional
// int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}}; // 8-dir
```

### BFS Shortest Path in Grid

```java
public int bfsGrid(char[][] grid, int[] start, int[] end) {
    int m = grid.length, n = grid[0].length;
    boolean[][] visited = new boolean[m][n];
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    Queue<int[]> q = new LinkedList<>();

    q.offer(start);
    visited[start[0]][start[1]] = true;  // mark BEFORE enqueue, not after poll
    int steps = 0;

    while (!q.isEmpty()) {
        for (int sz = q.size(); sz > 0; sz--) {  // process one full layer at a time
            int[] cell = q.poll();
            if (cell[0] == end[0] && cell[1] == end[1]) { return steps; }  // first arrival = shortest
            for (int[] d : dirs) {
                int nr = cell[0]+d[0], nc = cell[1]+d[1];
                if (nr>=0 && nr<m && nc>=0 && nc<n
                        && !visited[nr][nc] && grid[nr][nc] != '#') {
                    visited[nr][nc] = true;        // mark visited here, not after poll
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        steps++;  // increment only after the entire layer is processed
    }
    return -1;
}
```

### DFS Flood Fill / Island

```java
private void dfs(char[][] grid, int r, int c) {
    int m = grid.length, n = grid[0].length;
    if (r < 0 || r >= m || c < 0 || c >= n) { return; }  // bounds check first
    if (grid[r][c] != '1') { return; }            // boundary or already visited
    grid[r][c] = '0';                          // mark visited by mutating (avoids separate boolean[][])
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    for (int[] d : dirs) { dfs(grid, r+d[0], c+d[1]); }  // recurse into all 4 neighbors
}

public int numIslands(char[][] grid) {
    int count = 0;
    for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[r][c] == '1') { dfs(grid, r, c); count++; }
        }
    }
    return count;
}
```

---

## Variants 变形

| Pattern | Approach | Example |
|---|---|---|
| Number of islands | DFS from each unvisited '1' | LC 200 |
| Max area of island | DFS + return area count | LC 695 |
| Shortest path (0/1 grid) | BFS from start | LC 1091 |
| Distance to nearest 0 | Multi-source BFS from all 0s | LC 542 |
| Walls and gates | Multi-source BFS from all gates | LC 286 |
| Surrounded regions | DFS from border 'O's, mark safe | LC 130 |
| Pacific Atlantic water flow | DFS from each ocean's border | LC 417 |

---

## Key Examples 关键例题

### Shortest Path in Binary Matrix (LC 1091)
```java
public int shortestPathBinaryMatrix(int[][] grid) {
    int n = grid.length;
    if (grid[0][0] == 1 || grid[n-1][n-1] == 1) { return -1; }
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};
    Queue<int[]> q = new LinkedList<>();
    q.offer(new int[]{0, 0, 1});   // {row, col, distance}
    grid[0][0] = 1;                // mark visited
    while (!q.isEmpty()) {
        int[] cur = q.poll();
        int r = cur[0], c = cur[1], dist = cur[2];
        if (r == n-1 && c == n-1) { return dist; }
        for (int[] d : dirs) {
            int nr = r+d[0], nc = c+d[1];
            if (nr>=0 && nr<n && nc>=0 && nc<n && grid[nr][nc]==0) {
                grid[nr][nc] = 1;
                q.offer(new int[]{nr, nc, dist+1});
            }
        }
    }
    return -1;
}
```

### 01 Matrix — Multi-source BFS (LC 542)
```java
public int[][] updateMatrix(int[][] mat) {
    int m = mat.length, n = mat[0].length;
    int[][] dist = new int[m][n];
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    Queue<int[]> q = new LinkedList<>();

    for (int r = 0; r < m; r++) {
        for (int c = 0; c < n; c++) {
            if (mat[r][c] == 0) { q.offer(new int[]{r, c}); }
            else                 { dist[r][c] = Integer.MAX_VALUE; }
        }
    }

    while (!q.isEmpty()) {
        int[] cell = q.poll();
        for (int[] d : dirs) {
            int nr = cell[0]+d[0], nc = cell[1]+d[1];
            if (nr>=0 && nr<m && nc>=0 && nc<n
                    && dist[nr][nc] > dist[cell[0]][cell[1]] + 1) {
                dist[nr][nc] = dist[cell[0]][cell[1]] + 1;
                q.offer(new int[]{nr, nc});
            }
        }
    }
    return dist;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Bounds check | Always `nr>=0 && nr<m && nc>=0 && nc<n` before accessing grid |
| Mark visited BEFORE enqueueing | `grid[nr][nc] = visited_marker; q.offer(...)` — not after poll |
| Mutate vs separate `visited[][]` | Mutating grid is simple but destroys input; use `boolean[][]` to preserve |
| Multi-source = enqueue ALL sources first | All 0s (or all gates) go in queue before starting BFS |
| Encode cell as `r * n + c` | When using HashSet for visited: `int key = r * n + c` |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 200 Number of Islands |
| Medium | LC 695 Max Area of Island |
| Medium | LC 542 01 Matrix |
| Medium | LC 994 Rotting Oranges |
| Medium | LC 286 Walls and Gates |
| Medium | LC 130 Surrounded Regions |
| Hard | LC 1091 Shortest Path in Binary Matrix |

**Order:** 200 → 695 → 542 → 994 → 286 → 130 → 1091

---

## One-line Summary

```
Matrix BFS = treat grid as graph with direction array; BFS for shortest path, DFS for flood fill/components.
矩阵BFS = 用方向数组把网格当图；BFS求最短路径，DFS求连通分量/填充。
```
