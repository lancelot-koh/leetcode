# 🌐 GRAPH PROBLEMS - Complete Traversal & Search Guide

**Master graph fundamentals, traversal algorithms, and advanced graph problems**

---

## 📖 TABLE OF CONTENTS

1. **Core Concept & Mental Model**
2. **Fundamental Problems (1-5)**
3. **Connectivity Problems (6-10)**
4. **Path & Distance Problems (11-15)**
5. **Advanced Graph Problems (16-20)**

---

# 🧠 CORE CONCEPT & MENTAL MODEL

## What is a Graph?

```
A graph G = (V, E) where:
- V = set of vertices (nodes)
- E = set of edges (connections)

Examples:
- Social network: people are nodes, friendships are edges
- Web pages: pages are nodes, links are edges
- Map: cities are nodes, roads are edges
```

## Types of Graphs

```
DIRECTED vs UNDIRECTED:
- Directed: edges have direction A→B (one-way)
- Undirected: edges bidirectional A↔B (two-way)

WEIGHTED vs UNWEIGHTED:
- Weighted: edges have costs/distances
- Unweighted: edges are just connections

CYCLIC vs ACYCLIC:
- Cyclic: can traverse A→B→C→A
- Acyclic: can't form cycles (DAG = Directed Acyclic Graph)

CONNECTED vs DISCONNECTED:
- Connected: can reach any node from any other
- Disconnected: some nodes unreachable from others
```

## Two Main Traversal Algorithms

```
DFS (Depth-First Search):
- Go deep before exploring alternatives
- Natural with recursion
- Uses implicit/explicit stack
- Best for: cycles, connectivity, path finding

BFS (Breadth-First Search):
- Explore all neighbors before going deep
- Uses explicit queue
- Finds shortest path in unweighted graphs
- Best for: level-order, shortest distances, layering
```

---

# 📋 LEVEL 1: FUNDAMENTAL PROBLEMS

## PROBLEM 1: Number of Islands (LeetCode 200)

**Problem:** Count distinct islands in grid (connected components)

#### Thinking Process

```
Step 1: REFRAME
"How many islands?" = "How many connected components of land?"
Each island = group of connected 1's
Ocean = 0's (separate components)

Step 2: TRAVERSAL APPROACH
When we visit a land cell (1):
- It's part of some island
- Mark the whole island as visited
- Count this as one island

Step 3: WHICH ALGORITHM?
Both DFS and BFS work equally
DFS: Simpler recursion, natural for exploring land
BFS: More explicit, use queue

Step 4: ALGORITHM
1. Scan grid cell by cell
2. When we find unvisited land (1):
   - Increment island count
   - DFS/BFS to mark entire island as visited
3. Count total increments
```

#### Code Implementation (DFS)

```java
public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    
    int rows = grid.length;
    int cols = grid[0].length;
    int count = 0;
    
    // Scan every cell
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            // Found unvisited land
            if (grid[i][j] == '1') {
                // Mark entire island as visited
                dfs(grid, i, j, rows, cols);
                count++;
            }
        }
    }
    
    return count;
}

private void dfs(char[][] grid, int i, int j, int rows, int cols) {
    // Check bounds and if this cell is land
    if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] != '1') {
        return;
    }
    
    // Mark as visited (change to 0)
    grid[i][j] = '0';
    
    // Explore all 4 directions
    dfs(grid, i + 1, j, rows, cols);  // down
    dfs(grid, i - 1, j, rows, cols);  // up
    dfs(grid, i, j + 1, rows, cols);  // right
    dfs(grid, i, j - 1, rows, cols);  // left
}
```

#### Code Implementation (BFS)

```java
public int numIslandsBFS(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    
    int rows = grid.length;
    int cols = grid[0].length;
    int count = 0;
    
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (grid[i][j] == '1') {
                bfs(grid, i, j, rows, cols);
                count++;
            }
        }
    }
    
    return count;
}

private void bfs(char[][] grid, int i, int j, int rows, int cols) {
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{i, j});
    grid[i][j] = '0';  // Mark as visited
    
    int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        int row = cell[0];
        int col = cell[1];
        
        // Explore all 4 neighbors
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            // Check bounds and if unvisited land
            if (newRow >= 0 && newRow < rows && 
                newCol >= 0 && newCol < cols && 
                grid[newRow][newCol] == '1') {
                
                grid[newRow][newCol] = '0';  // Mark visited
                queue.offer(new int[]{newRow, newCol});
            }
        }
    }
}
```

**Key Insight:**
- Visiting a cell multiple times = inefficient
- Mark cells as visited immediately to prevent revisits
- Counts = number of times we start a new DFS/BFS

---

## PROBLEM 2: Connected Components (LeetCode 323)

**Problem:** Count connected components in undirected graph

#### Thinking Process

```
Step 1: SIMILAR TO PROBLEM 1
But now: nodes instead of grid cells
Edges instead of adjacency

Step 2: SAME ALGORITHM
1. For each unvisited node
2. DFS/BFS to mark component
3. Count components

Step 3: REPRESENTATION
Use adjacency list (not grid)
```

#### Code Implementation

```java
public int countComponents(int n, int[][] edges) {
    // Build adjacency list
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
    }
    
    for (int[] edge : edges) {
        int u = edge[0];
        int v = edge[1];
        graph.get(u).add(v);
        graph.get(v).add(u);  // Undirected
    }
    
    // Count components
    boolean[] visited = new boolean[n];
    int components = 0;
    
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            dfs(i, graph, visited);
            components++;
        }
    }
    
    return components;
}

private void dfs(int node, List<List<Integer>> graph, boolean[] visited) {
    visited[node] = true;
    
    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            dfs(neighbor, graph, visited);
        }
    }
}
```

---

## PROBLEM 3: Graph Valid Tree (LeetCode 261)

**Problem:** Check if graph is a valid tree

#### Thinking Process

```
Step 1: PROPERTIES OF A TREE
A tree with n nodes has:
- Exactly n-1 edges (not more, not less)
- Is connected (all nodes reachable)
- No cycles

Step 2: ALGORITHM
Check all three properties:
1. edges.length == n-1
2. Graph is connected (DFS visits all nodes)
3. No cycles (during DFS, never revisit a visited node)

Actually, (1) + (2) guarantee (3)
If connected and has n-1 edges with n nodes: must be tree
```

#### Code Implementation

```java
public boolean validTree(int n, int[][] edges) {
    // Property 1: Must have exactly n-1 edges
    if (edges.length != n - 1) {
        return false;
    }
    
    // Build graph
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
    }
    
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
    }
    
    // Property 2: Must be connected (can reach all from node 0)
    boolean[] visited = new boolean[n];
    dfs(0, graph, visited);
    
    // Check if all nodes were visited
    for (boolean v : visited) {
        if (!v) return false;  // Some node unreachable
    }
    
    return true;
}

private void dfs(int node, List<List<Integer>> graph, boolean[] visited) {
    visited[node] = true;
    
    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            dfs(neighbor, graph, visited);
        }
    }
}
```

---

## PROBLEM 4: Detect Cycle in Graph (Directed)

**Problem:** Is there a cycle in directed graph?

#### Thinking Process

```
Step 1: DFS STATE TRACKING
White: unvisited
Gray: currently visiting
Black: finished visiting

Step 2: CYCLE DETECTION
If we encounter a GRAY node during DFS:
- That node is an ancestor we're still processing
- We're visiting it again = back edge = CYCLE

Step 3: ALGORITHM
Use 3-state DFS
```

#### Code Implementation

```java
public boolean hasCycle(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
    }
    
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
    }
    
    int[] state = new int[n];  // 0: white, 1: gray, 2: black
    
    for (int i = 0; i < n; i++) {
        if (state[i] == 0) {
            if (hasCycleDFS(i, graph, state)) {
                return true;
            }
        }
    }
    
    return false;
}

private boolean hasCycleDFS(int node, List<List<Integer>> graph, int[] state) {
    state[node] = 1;  // Mark as visiting (gray)
    
    for (int neighbor : graph.get(node)) {
        if (state[neighbor] == 1) {
            // Visiting an ancestor = back edge = cycle
            return true;
        } else if (state[neighbor] == 0) {
            if (hasCycleDFS(neighbor, graph, state)) {
                return true;
            }
        }
    }
    
    state[node] = 2;  // Mark as visited (black)
    return false;
}
```

---

# 📋 LEVEL 2: BFS SEARCH PATTERNS

## PROBLEM 5: Rotting Oranges (LeetCode 994)

**Problem:** Minimum time for all oranges to rot (multi-source BFS)

#### Thinking Process

```
Step 1: MULTI-SOURCE BFS
Normal BFS: single source
Multi-source: multiple starting points

Step 2: ALGORITHM
1. Start BFS from ALL rotten oranges simultaneously
2. Spread rot level by level
3. Track time elapsed
```

#### Code Implementation

```java
public int orangesRotting(int[][] grid) {
    int rows = grid.length;
    int cols = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int fresh = 0;
    
    // Find all rotten oranges and count fresh
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (grid[i][j] == 2) {
                queue.offer(new int[]{i, j});
            } else if (grid[i][j] == 1) {
                fresh++;
            }
        }
    }
    
    if (fresh == 0) return 0;
    
    int time = 0;
    int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    
    while (!queue.isEmpty() && fresh > 0) {
        int size = queue.size();
        
        // Process all rotten oranges at current level
        for (int i = 0; i < size; i++) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            
            // Infect neighbors
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < rows && 
                    newCol >= 0 && newCol < cols && 
                    grid[newRow][newCol] == 1) {
                    
                    grid[newRow][newCol] = 2;
                    queue.offer(new int[]{newRow, newCol});
                    fresh--;
                }
            }
        }
        
        if (fresh > 0) {
            time++;
        }
    }
    
    return fresh == 0 ? time : -1;
}
```

---

## PROBLEM 6: Network Delay Time (LeetCode 743)

**Problem:** Minimum time for signal to reach all nodes (Dijkstra's Algorithm)

#### Thinking Process

```
Step 1: DIJKSTRA'S ALGORITHM
For weighted graphs, find shortest paths
Priority queue ensures we process closest node first

Step 2: ALGORITHM STEPS
1. Mark start as distance 0
2. Use min-heap of (distance, node)
3. Process node with minimum distance
4. Update neighbors if found shorter path
5. Continue until all nodes processed

Step 3: ANSWER
Maximum distance to any node = latest signal arrival
```

#### Code Implementation

```java
public int networkDelayTime(int[][] times, int n, int k) {
    // Build adjacency list: {neighbor, weight}
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) {
        graph.add(new ArrayList<>());
    }
    
    for (int[] time : times) {
        graph.get(time[0]).add(new int[]{time[1], time[2]});
    }
    
    // Dijkstra's algorithm
    int[] dist = new int[n + 1];
    for (int i = 0; i <= n; i++) {
        dist[i] = Integer.MAX_VALUE;
    }
    dist[k] = 0;
    
    PriorityQueue<int[]> pq = new PriorityQueue<>(
        (a, b) -> a[0] - b[0]  // Min by distance
    );
    pq.offer(new int[]{0, k});  // {distance, node}
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int d = curr[0];
        int node = curr[1];
        
        if (d > dist[node]) continue;
        
        for (int[] edge : graph.get(node)) {
            int neighbor = edge[0];
            int weight = edge[1];
            
            if (dist[node] + weight < dist[neighbor]) {
                dist[neighbor] = dist[node] + weight;
                pq.offer(new int[]{dist[neighbor], neighbor});
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

---

# 🧭 PATTERN RECOGNITION

## When to Use Each Technique

```
Connected Components?
  → DFS/BFS or Union-Find

Shortest Path (unweighted)?
  → BFS

Shortest Path (weighted)?
  → Dijkstra's (min-heap)
  → Bellman-Ford (if negative weights)

Cycle Detection?
  → DFS (3-state) for directed
  → DFS (parent tracking) for undirected

Multi-source Spread?
  → BFS with all sources in queue initially

Topological Order?
  → Kahn's or DFS post-order
```

---

# 💡 KEY INSIGHTS

### Insight 1: Visited Arrays
```
Prevent revisiting nodes
Marks completion state
Different from being "processed"
```

### Insight 2: DFS vs BFS
```
DFS: Memory efficient, explores deep
BFS: Better for shortest path, explores wide
Both useful, choose based on problem
```

### Insight 3: Multi-Source BFS
```
Start with multiple source nodes in queue
Process level by level
Natural for "spread" problems
```

---

# 🔧 COMMON MISTAKES

### Mistake 1: Not Marking Visited
```
❌ WRONG: No visited tracking, revisit nodes infinitely
✅ RIGHT: Mark visited to prevent infinite loops
```

### Mistake 2: Wrong Graph Representation
```
❌ WRONG: Adjacency matrix for large sparse graph
✅ RIGHT: Adjacency list for sparse graphs
```

### Mistake 3: Forgetting Level Tracking in BFS
```
❌ WRONG: Process entire queue at once
✅ RIGHT: Process by level (size at start of iteration)
```

---

**Master these graph algorithms and you'll solve 70% of graph interview questions.** 🚀
