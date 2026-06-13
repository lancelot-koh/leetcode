# 🌍 Problem Worlds - Complete Templates & Variants

**Master templates for solving any problem by identifying which world it belongs to**

---

## 📋 QUICK REFERENCE CHEAT SHEET

### 🎯 Identify the World (by Keywords)

| World | Keywords | Primary Algorithm | Complexity | Use When |
|-------|----------|-------------------|-----------|----------|
| **1. Dependency** | prerequisite, ordering, dependency, course, task, schedule | Topological Sort (Kahn's) | O(V+E) | Need valid order or detect cycle |
| **2. Reachability** | can reach, connected, component, island, union, find | DFS/BFS/Union Find | O(V+E) | Check connectivity or find groups |
| **3. Shortest Path** | shortest, minimum, distance, cost, path, least | BFS/Dijkstra/Floyd-Warshall | O(E log V) | Find minimum distance between points |
| **4. Optimization** | maximize, minimize, best, profit, optimal, count | DP/Greedy | O(n²) | Max/min value or count ways |
| **5. Search Space** | binary search, monotonic, capacity, speed, answer | Binary Search | O(log n) | Search in sorted/monotonic space |
| **6. Enumeration** | all combinations, permutations, generate, subset, backtrack | Backtracking | O(2ⁿ) | Generate all valid solutions |
| **7. Continuous Range** | substring, subarray, window, consecutive, continuous | Sliding Window/Two Pointers | O(n) | Find optimal contiguous elements |
| **8. Priority** | top K, frequent, heap, max/min, priority | Priority Queue/Heap | O(n log k) | Efficiently select top/min elements |
| **9. Scheduling** | interval, overlap, merge, meeting, activity, schedule | Greedy Interval | O(n log n) | Optimize interval selection |
| **10. State Transition** | state, transition, stock, cooldown, DP, regex, word | State Machine DP | O(n·states) | Track valid state sequences |
| **11. Trees** | tree, node, parent, subtree, hierarchy, traversal, binary | DFS/BFS/Tree DP | O(n) | Process hierarchical structures |

### ⚡ Decision Path (Fastest Way to Pick World)

```
1. Has ordering/dependency constraints? → WORLD 1 (Topological Sort)
2. Check if connected/reachable? → WORLD 2 (DFS/BFS/Union Find)
3. Find shortest distance? → WORLD 3 (BFS/Dijkstra)
4. Maximize/minimize/count? → WORLD 4 (DP/Greedy)
5. Need to search large space? → WORLD 5 (Binary Search)
6. Generate all combinations? → WORLD 6 (Backtracking)
7. Work with substring/subarray? → WORLD 7 (Sliding Window)
8. Need top K frequently? → WORLD 8 (Heap/Priority Queue)
9. Merge/optimize intervals? → WORLD 9 (Greedy Interval)
10. Track states/transitions? → WORLD 10 (State Machine DP)
11. Tree structure? → WORLD 11 (DFS/BFS/Tree DP)
```

---

## 📍 The 4 Problem Worlds at a Glance

```
┌─ WORLD 1: DEPENDENCY WORLD ─────────────────────────┐
│ Keywords: prerequisite, dependency, order            │
│ Algorithms: Topological Sort, DFS, Cycle Detection  │
│ Questions: Can all be done? Valid order?             │
└─────────────────────────────────────────────────────┘

┌─ WORLD 2: REACHABILITY WORLD ──────────────────────┐
│ Keywords: can reach, connected, component           │
│ Algorithms: DFS, BFS, Union Find                    │
│ Questions: Are they connected? How many groups?     │
└─────────────────────────────────────────────────────┘

┌─ WORLD 3: SHORTEST PATH WORLD ─────────────────────┐
│ Keywords: shortest, minimum, least                  │
│ Algorithms: BFS (unweighted), Dijkstra (weighted)   │
│ Questions: Minimum distance/cost to reach?          │
└─────────────────────────────────────────────────────┘

┌─ WORLD 4: OPTIMIZATION WORLD ──────────────────────┐
│ Keywords: maximize, minimize, best, optimal         │
│ Algorithms: DP, Greedy, Binary Search              │
│ Questions: Best solution? Most profit? Least cost? │
└─────────────────────────────────────────────────────┘
```

---

## 🏗️ WORLD 1: DEPENDENCY WORLD

**Problem Pattern:** Objects have dependencies. Order matters.

### Template Structure

```
ENTITY:    What are the entities? (courses, tasks, jobs)
DEPENDENCY: How do they depend? (A requires B)
GOAL:      Find valid order or detect impossibility
SOLUTION:  Topological sort or cycle detection
```

### Primary Template: Topological Sort (DFS)

```java
public List<Integer> topologicalSort(int n, List<List<Integer>> edges) {
    // Build graph
    Map<Integer, List<Integer>> graph = new HashMap<>();
    int[] indegree = new int[n];
    
    for (List<Integer> edge : edges) {
        int from = edge.get(0);
        int to = edge.get(1);
        graph.putIfAbsent(from, new ArrayList<>());
        graph.get(from).add(to);
        indegree[to]++;
    }
    
    // Kahn's algorithm (BFS-based topological sort)
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (indegree[i] == 0) {
            queue.offer(i);
        }
    }
    
    List<Integer> result = new ArrayList<>();
    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);
        
        for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            indegree[neighbor]--;
            if (indegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }
    
    // Check if there's a cycle
    if (result.size() != n) {
        return new ArrayList<>();  // Cycle exists
    }
    
    return result;
}
```

### Variant 1: DFS-Based Topological Sort (with cycle detection)

```java
public List<Integer> topologicalSortDFS(int n, List<List<Integer>> edges) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (List<Integer> edge : edges) {
        graph.putIfAbsent(edge.get(0), new ArrayList<>());
        graph.get(edge.get(0)).add(edge.get(1));
    }
    
    int[] state = new int[n];  // 0: unvisited, 1: visiting, 2: visited
    List<Integer> result = new ArrayList<>();
    
    for (int i = 0; i < n; i++) {
        if (state[i] == 0) {
            if (!dfs(i, graph, state, result)) {
                return new ArrayList<>();  // Cycle detected
            }
        }
    }
    
    Collections.reverse(result);
    return result;
}

private boolean dfs(int node, Map<Integer, List<Integer>> graph, 
                   int[] state, List<Integer> result) {
    state[node] = 1;  // Mark as visiting
    
    for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
        if (state[neighbor] == 1) {
            return false;  // Back edge = cycle
        }
        if (state[neighbor] == 0) {
            if (!dfs(neighbor, graph, state, result)) {
                return false;
            }
        }
    }
    
    state[node] = 2;  // Mark as visited
    result.add(node);
    return true;
}
```

### Variant 2: Lexicographically Smallest Order

```java
public List<Integer> lexicographicalTopSort(int n, List<List<Integer>> edges) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    int[] indegree = new int[n];
    
    for (List<Integer> edge : edges) {
        graph.putIfAbsent(edge.get(0), new ArrayList<>());
        graph.get(edge.get(0)).add(edge.get(1));
        indegree[edge.get(1)]++;
    }
    
    // Use PriorityQueue instead of LinkedList for lexicographic order
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    for (int i = 0; i < n; i++) {
        if (indegree[i] == 0) {
            pq.offer(i);
        }
    }
    
    List<Integer> result = new ArrayList<>();
    while (!pq.isEmpty()) {
        int node = pq.poll();
        result.add(node);
        
        for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            indegree[neighbor]--;
            if (indegree[neighbor] == 0) {
                pq.offer(neighbor);
            }
        }
    }
    
    return result.size() == n ? result : new ArrayList<>();
}
```

### Variant 3: Cycle Detection Only (Yes/No)

```java
public boolean hasCycle(int n, List<List<Integer>> edges) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    int[] state = new int[n];  // 0: unvisited, 1: visiting, 2: visited
    
    for (List<Integer> edge : edges) {
        graph.putIfAbsent(edge.get(0), new ArrayList<>());
        graph.get(edge.get(0)).add(edge.get(1));
    }
    
    for (int i = 0; i < n; i++) {
        if (state[i] == 0) {
            if (hasCycleDFS(i, graph, state)) {
                return true;
            }
        }
    }
    
    return false;
}

private boolean hasCycleDFS(int node, Map<Integer, List<Integer>> graph, int[] state) {
    state[node] = 1;
    
    for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
        if (state[neighbor] == 1) {
            return true;  // Back edge found
        }
        if (state[neighbor] == 0) {
            if (hasCycleDFS(neighbor, graph, state)) {
                return true;
            }
        }
    }
    
    state[node] = 2;
    return false;
}
```

### When to Use Each Variant

| Variant | Use When | Example |
|---------|----------|---------|
| **Kahn's (BFS)** | Need indegree, want simplicity | Course schedule |
| **DFS-based** | Need to detect cycles explicitly | Build order with validation |
| **Lexicographic** | Need specific ordering preference | Alien dictionary |
| **Cycle detection** | Only care if valid, not the order | Valid prerequisites |

---

## 🌐 WORLD 2: REACHABILITY WORLD

**Problem Pattern:** Can we reach from A to B? Are they connected?

### Template Structure

```
ENTITY:    What are the entities? (nodes, cells, persons)
CONNECTION: How are they connected? (edges, adjacency)
GOAL:      Connected? Same group? How many groups?
SOLUTION:  DFS, BFS, or Union Find
```

### Primary Template: DFS

```java
public int numConnectedComponents(int n, int[][] edges) {
    // Build adjacency list
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) {
        graph.put(i, new ArrayList<>());
    }
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
    }
    
    // DFS to find components
    boolean[] visited = new boolean[n];
    int count = 0;
    
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            dfs(i, graph, visited);
            count++;
        }
    }
    
    return count;
}

private void dfs(int node, Map<Integer, List<Integer>> graph, boolean[] visited) {
    visited[node] = true;
    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            dfs(neighbor, graph, visited);
        }
    }
}
```

### Variant 1: BFS (Level-by-level)

```java
public int numConnectedComponentsBFS(int n, int[][] edges) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) {
        graph.put(i, new ArrayList<>());
    }
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
    }
    
    boolean[] visited = new boolean[n];
    int count = 0;
    
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(i);
            visited[i] = true;
            
            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int neighbor : graph.get(node)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
            count++;
        }
    }
    
    return count;
}
```

### Variant 2: Union Find (Disjoint Set Union)

```java
public int numConnectedComponentsUF(int n, int[][] edges) {
    UnionFind uf = new UnionFind(n);
    
    for (int[] edge : edges) {
        uf.union(edge[0], edge[1]);
    }
    
    int count = 0;
    for (int i = 0; i < n; i++) {
        if (uf.find(i) == i) {
            count++;
        }
    }
    
    return count;
}

class UnionFind {
    int[] parent;
    int[] rank;
    
    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }
    
    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    
    void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) return;
        
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
}
```

### Variant 3: Multi-source (All starting points)

```java
public int numIslands(char[][] grid) {
    int rows = grid.length;
    int cols = grid[0].length;
    int count = 0;
    
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (grid[i][j] == '1') {
                bfsIsland(i, j, grid);
                count++;
            }
        }
    }
    
    return count;
}

private void bfsIsland(int i, int j, char[][] grid) {
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{i, j});
    grid[i][j] = '0';
    
    int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] dir : directions) {
            int ni = cell[0] + dir[0];
            int nj = cell[1] + dir[1];
            
            if (ni >= 0 && ni < grid.length && 
                nj >= 0 && nj < grid[0].length && 
                grid[ni][nj] == '1') {
                
                grid[ni][nj] = '0';
                queue.offer(new int[]{ni, nj});
            }
        }
    }
}
```

### When to Use Each Variant

| Variant | Use When | Example |
|---------|----------|---------|
| **DFS** | Simple connectivity, prefer recursion | Connected components |
| **BFS** | Need level-order, grid problems | Islands in matrix |
| **Union Find** | Dynamic connectivity, merging groups | Friends circles |
| **Multi-source** | Multiple starting points | Rotting oranges |

---

## 📍 WORLD 3: SHORTEST PATH WORLD

**Problem Pattern:** Find minimum distance/cost to reach destination.

### Template Structure

```
ENTITY:    Nodes/positions
EDGES:     Weighted or unweighted connections
GOAL:      Minimum distance to target
SOLUTION:  BFS (unweighted) or Dijkstra (weighted)
```

### Primary Template: BFS (Unweighted)

```java
public int shortestPath(int n, int[][] edges, int start, int end) {
    // Build adjacency list
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) {
        graph.put(i, new ArrayList<>());
    }
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
    }
    
    // BFS
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();
    
    queue.offer(start);
    visited.add(start);
    int distance = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        
        for (int i = 0; i < size; i++) {
            int node = queue.poll();
            
            if (node == end) {
                return distance;
            }
            
            for (int neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        
        distance++;
    }
    
    return -1;  // Unreachable
}
```

### Variant 1: Dijkstra (Weighted)

```java
public int dijkstra(int n, int[][] edges, int start, int end) {
    // Build adjacency list with weights
    Map<Integer, List<int[]>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) {
        graph.put(i, new ArrayList<>());
    }
    for (int[] edge : edges) {
        graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        graph.get(edge[1]).add(new int[]{edge[0], edge[2]});
    }
    
    // Distance array
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[start] = 0;
    
    // Priority queue: {distance, node}
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    pq.offer(new int[]{0, start});
    
    while (!pq.isEmpty()) {
        int[] current = pq.poll();
        int d = current[0];
        int node = current[1];
        
        if (d > dist[node]) continue;
        
        if (node == end) {
            return dist[end];
        }
        
        for (int[] neighbor : graph.get(node)) {
            int nextNode = neighbor[0];
            int weight = neighbor[1];
            
            if (dist[node] + weight < dist[nextNode]) {
                dist[nextNode] = dist[node] + weight;
                pq.offer(new int[]{dist[nextNode], nextNode});
            }
        }
    }
    
    return dist[end] == Integer.MAX_VALUE ? -1 : dist[end];
}
```

### Variant 2: Multi-source Shortest Path

```java
public int[][] updateMatrix(int[][] mat) {
    int rows = mat.length;
    int cols = mat[0].length;
    int[][] dist = new int[rows][cols];
    Queue<int[]> queue = new LinkedList<>();
    
    // Start from all 0s simultaneously
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (mat[i][j] == 0) {
                queue.offer(new int[]{i, j});
                dist[i][j] = 0;
            } else {
                dist[i][j] = -1;  // Unvisited
            }
        }
    }
    
    int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        int i = cell[0];
        int j = cell[1];
        
        for (int[] dir : directions) {
            int ni = i + dir[0];
            int nj = j + dir[1];
            
            if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && dist[ni][nj] == -1) {
                dist[ni][nj] = dist[i][j] + 1;
                queue.offer(new int[]{ni, nj});
            }
        }
    }
    
    return dist;
}
```

### Variant 3: Bellman-Ford (Negative Weights)

```java
public int[] bellmanFord(int n, int[][] edges, int start) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[start] = 0;
    
    // Relax edges n-1 times
    for (int i = 0; i < n - 1; i++) {
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
            }
        }
    }
    
    // Check for negative cycle
    for (int[] edge : edges) {
        int u = edge[0];
        int v = edge[1];
        int w = edge[2];
        
        if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
            return new int[]{};  // Negative cycle detected
        }
    }
    
    return dist;
}
```

### Variant 4: Floyd-Warshall (All-Pairs Shortest Path)

```java
// All pairs shortest paths
public void floydWarshall(int[][] dist) {
    int n = dist.length;
    
    // Initialize: dist[i][i] = 0, direct edges set
    // dist[i][j] = weight if edge exists, else infinity
    
    // Try using each vertex as intermediate
    for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Path through k better than direct?
                if (dist[i][k] != Integer.MAX_VALUE && 
                    dist[k][j] != Integer.MAX_VALUE) {
                    dist[i][j] = Math.min(dist[i][j], 
                                         dist[i][k] + dist[k][j]);
                }
            }
        }
    }
}

// Usage example
public static void main(String[] args) {
    int n = 4;
    int[][] dist = new int[n][n];
    
    // Initialize
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (i == j) {
                dist[i][j] = 0;
            } else {
                dist[i][j] = Integer.MAX_VALUE / 2;  // Avoid overflow
            }
        }
    }
    
    // Add edges
    dist[0][1] = 4;
    dist[0][2] = 2;
    dist[1][2] = 1;
    dist[1][3] = 5;
    dist[2][3] = 8;
    
    floydWarshall(dist);
    
    // dist[i][j] now contains shortest path from i to j
}
```

**Use case:** LeetCode 1334 (Find the City With the Smallest Number of Neighbors)
**Complexity:** O(V³) time, O(V²) space - best for dense graphs with V ≤ 500

### When to Use Each Variant

| Variant | Use When | Complexity |
|---------|----------|-----------|
| **BFS** | Unweighted graph | O(V+E) |
| **Dijkstra** | Non-negative weights, single source | O((V+E)logV) |
| **Bellman-Ford** | Negative weights, single source | O(VE) |
| **Floyd-Warshall** | All pairs, dense graphs | O(V³) |
| **Multi-source** | Multiple starting points | O(V+E) |

---

## ⚙️ WORLD 4: OPTIMIZATION WORLD

**Problem Pattern:** Maximize or minimize value, count ways, or find best solution.

### Template Structure

```
STATE:     What describes a subproblem?
CHOICE:    What options do we have?
METRIC:    What are we optimizing?
GOAL:      Max/min/count?
SOLUTION:  DP or Greedy
```

### Primary Template: Linear DP (Maximize/Minimize)

```java
public int robHouses(int[] nums) {
    int n = nums.length;
    
    // dp[i] = max money robbing houses [0...i]
    int[] dp = new int[n];
    dp[0] = nums[0];
    if (n > 1) {
        dp[1] = Math.max(nums[0], nums[1]);
    }
    
    for (int i = 2; i < n; i++) {
        // Option 1: Rob this house (skip previous)
        // Option 2: Skip this house (take previous max)
        dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
    }
    
    return dp[n-1];
}
```

### Variant 1: Unbounded DP (Can use multiple times)

```java
public int coinChange(int[] coins, int amount) {
    // dp[i] = min coins to make amount i
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    
    for (int i = 1; i <= amount; i++) {
        for (int coin : coins) {
            if (i >= coin && dp[i - coin] != Integer.MAX_VALUE) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
    }
    
    return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
}
```

### Variant 2: 2D DP (Sequence comparison)

```java
public int editDistance(String word1, String word2) {
    int m = word1.length();
    int n = word2.length();
    
    // dp[i][j] = min edits to transform word1[0...i-1] to word2[0...j-1]
    int[][] dp = new int[m + 1][n + 1];
    
    // Base cases
    for (int i = 0; i <= m; i++) {
        dp[i][0] = i;
    }
    for (int j = 0; j <= n; j++) {
        dp[0][j] = j;
    }
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1];
            } else {
                dp[i][j] = 1 + Math.min({
                    dp[i - 1][j],      // Delete
                    dp[i][j - 1],      // Insert
                    dp[i - 1][j - 1]   // Replace
                });
            }
        }
    }
    
    return dp[m][n];
}
```

### Variant 3: State Machine DP

```java
public int maxProfitWithCooldown(int[] prices) {
    int n = prices.length;
    
    // dp[i][0] = max profit at day i if NOT holding
    // dp[i][1] = max profit at day i if HOLDING
    int[][] dp = new int[n][2];
    
    dp[0][0] = 0;
    dp[0][1] = -prices[0];
    
    for (int i = 1; i < n; i++) {
        // Not holding: either stay not holding, or sell today
        dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
        
        // Holding: either stay holding, or buy today (need cooldown)
        dp[i][1] = Math.max(dp[i-1][1], 
                           (i >= 2 ? dp[i-2][0] : 0) - prices[i]);
    }
    
    return dp[n-1][0];
}
```

### Variant 4: Counting DP

```java
public int uniquePaths(int m, int n) {
    // dp[i][j] = number of ways to reach (i,j)
    int[][] dp = new int[m][n];
    
    // Base: only one way to reach first row/column
    for (int i = 0; i < m; i++) {
        dp[i][0] = 1;
    }
    for (int j = 0; j < n; j++) {
        dp[0][j] = 1;
    }
    
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            dp[i][j] = dp[i-1][j] + dp[i][j-1];
        }
    }
    
    return dp[m-1][n-1];
}
```

### When to Use Each Variant

| Variant | Use When | Decision |
|---------|----------|----------|
| **Linear** | Single dimension, bounded choice | Max/min on linear |
| **Unbounded** | Can reuse items/states | Coin change |
| **2D** | Compare two sequences | Edit distance |
| **State Machine** | Multiple discrete states | Stock trading |
| **Counting** | Sum all paths | Unique paths |

---

## 🎯 Quick Decision Guide

```
See a problem?
  │
  ├─ Has dependencies or ordering?
  │  └─ DEPENDENCY WORLD → Topological Sort
  │
  ├─ Connected/reachable?
  │  └─ REACHABILITY WORLD → DFS/BFS/Union Find
  │
  ├─ Shortest distance/cost?
  │  └─ SHORTEST PATH WORLD → BFS/Dijkstra
  │
  └─ Maximize/minimize/count?
     └─ OPTIMIZATION WORLD → DP/Greedy
```

---

## 📊 Variant Selection Matrix

```
DEPENDENCY WORLD:
├─ Need all nodes? → Topological Sort (Kahn's)
├─ Need specific order? → Topological Sort (Lexicographic)
├─ Just detect cycle? → Cycle Detection
└─ Small graph? → DFS

REACHABILITY WORLD:
├─ Simple grid? → DFS
├─ Level-order needed? → BFS
├─ Dynamic connectivity? → Union Find
└─ Multi-source? → Multi-source BFS

SHORTEST PATH WORLD:
├─ Unweighted? → BFS
├─ Positive weights? → Dijkstra
├─ Negative weights? → Bellman-Ford
└─ Multiple sources? → Multi-source BFS

OPTIMIZATION WORLD:
├─ Single sequence? → Linear DP
├─ Can reuse? → Unbounded DP
├─ Two sequences? → 2D DP
├─ Multiple states? → State Machine DP
└─ Count ways? → Counting DP
```

---

## 🌍 WORLD 5: SEARCH SPACE WORLD

**Problem Pattern:** Search for a value in a large space. The space has monotonic property.

### Template Structure

```
ENTITY:    Search space (sorted array, answer space, etc.)
PROPERTY:  Monotonic (left half wrong, right half correct)
GOAL:      Find boundary/answer
SOLUTION:  Binary search to narrow space
```

### Primary Template: Binary Search (Value Search)

```java
// Search for specific value in sorted array
int binarySearch(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) return mid;      // Found
        else if (nums[mid] < target) {
            left = mid + 1;                        // Search right
        } else {
            right = mid - 1;                       // Search left
        }
    }
    
    return -1;  // Not found
}
```

**Complexity:** O(log n) time, O(1) space

### Variant 1: Answer Space Binary Search

```java
// When you can't directly access answer, use answer space
int searchAnswer(int[] nums, int target) {
    // Instead of array index, search the answer space [1, max_value]
    int left = 1, right = nums.length;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        if (isValid(nums, mid)) {       // Check if answer >= mid works
            right = mid;                 // Try smaller
        } else {
            left = mid + 1;              // Need larger
        }
    }
    
    return left;
}

// Helper: validate if capacity/speed/amount is sufficient
boolean isValid(int[] nums, int mid) {
    // Custom logic: can we ship all packages in mid days?
    // Can we reach destination in mid time?
    int days = 1, load = 0;
    for (int item : nums) {
        if (load + item > mid) {
            days++;
            load = 0;
        }
        load += item;
    }
    return days <= 3;  // Example: 3 days available
}
```

**Use case:** LeetCode 1011 (Capacity), 1482 (Min Day)
**Complexity:** O(n log m) where m = answer space

### Variant 2: First/Last Occurrence

```java
// Find first occurrence of target
int findFirst(int[] nums, int target) {
    int left = 0, right = nums.length - 1, result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            result = mid;              // Found, but keep searching left
            right = mid - 1;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}

// Find last occurrence of target
int findLast(int[] nums, int target) {
    int left = 0, right = nums.length - 1, result = -1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            result = mid;              // Found, but keep searching right
            left = mid + 1;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}
```

**Use case:** LeetCode 34 (Find First and Last Position)
**Complexity:** O(log n)

### Variant 3: Rotated Array Search

```java
// Search in rotated sorted array
int searchRotated(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) return mid;
        
        // Determine which half is sorted
        if (nums[left] <= nums[mid]) {
            // Left half is sorted
            if (nums[left] <= target && target < nums[mid]) {
                right = mid - 1;       // Target in left sorted half
            } else {
                left = mid + 1;        // Target in right half
            }
        } else {
            // Right half is sorted
            if (nums[mid] < target && target <= nums[right]) {
                left = mid + 1;        // Target in right sorted half
            } else {
                right = mid - 1;       // Target in left half
            }
        }
    }
    
    return -1;
}
```

**Use case:** LeetCode 33 (Search in Rotated Sorted Array)
**Complexity:** O(log n)

### Variant 4: Binary Search on Sorted Matrix

```java
// Search in 2D matrix (each row sorted, last of row < first of next row)
boolean searchMatrix(int[][] matrix, int target) {
    if (matrix.length == 0) return false;
    
    int m = matrix.length;    // rows
    int n = matrix[0].length; // cols
    
    // Treat 2D matrix as 1D sorted array
    int left = 0, right = m * n - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        // Convert 1D index to 2D: row = mid/n, col = mid%n
        int value = matrix[mid / n][mid % n];
        
        if (value == target) return true;
        else if (value < target) left = mid + 1;
        else right = mid - 1;
    }
    
    return false;
}

// Alternative: Search where rows/cols are both sorted
boolean searchMatrixII(int[][] matrix, int target) {
    if (matrix.length == 0) return false;
    
    // Start from top-right or bottom-left
    int m = matrix.length;
    int n = matrix[0].length;
    int row = 0, col = n - 1;  // Start top-right
    
    while (row < m && col >= 0) {
        if (matrix[row][col] == target) {
            return true;
        } else if (matrix[row][col] < target) {
            row++;  // Need larger value
        } else {
            col--;  // Need smaller value
        }
    }
    
    return false;
}
```

**Use case:** LeetCode 74 (Search a 2D Matrix), LeetCode 240 (Search a 2D Matrix II)
**Complexity:** O(log(m*n)) for fully sorted, O(m+n) for partially sorted

**Decision Tree:**
```
Search Space World:
├─ Direct value search? → Basic Binary Search
├─ Need to find answer (not value)? → Answer Space Binary Search
├─ Find boundary/occurrence? → First/Last Binary Search
├─ Array rotated/special? → Rotated Array Search
└─ 2D matrix search? → Matrix Binary Search
```

---

## 🌍 WORLD 6: ENUMERATION WORLD

**Problem Pattern:** Generate all/some possible combinations or permutations. Exhaustively explore choices.

### Template Structure

```
ENTITY:    Elements to choose from
CHOICES:   Which elements to include/order
GOAL:      Find all valid combinations/permutations
SOLUTION:  Backtracking (choose-explore-undo)
```

### Primary Template: Backtracking (Combinations)

```java
// Generate all combinations of size k from n elements
List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), 1, n, k);
    return result;
}

void backtrack(List<List<Integer>> result, List<Integer> current, 
               int start, int n, int k) {
    // Base case: reached target size
    if (current.size() == k) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Choose: add next element
    for (int i = start; i <= n; i++) {
        current.add(i);                      // Choose
        backtrack(result, current, i + 1, n, k);  // Explore
        current.remove(current.size() - 1);  // Undo
    }
}
```

**Use case:** LeetCode 77 (Combinations)
**Complexity:** O(C(n,k) * k) = O(2^n * k) in worst case

### Variant 1: Permutations

```java
// Generate all permutations
List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrackPermute(result, new ArrayList<>(), nums, new boolean[nums.length]);
    return result;
}

void backtrackPermute(List<List<Integer>> result, List<Integer> current,
                      int[] nums, boolean[] used) {
    // Base case: all elements used
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Choose any unused element
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        
        current.add(nums[i]);                // Choose
        used[i] = true;
        backtrackPermute(result, current, nums, used);  // Explore
        current.remove(current.size() - 1);  // Undo
        used[i] = false;
    }
}
```

**Use case:** LeetCode 46 (Permutations)
**Complexity:** O(n! * n)

### Variant 2: Subset Generation

```java
// Generate all subsets (power set)
List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrackSubsets(result, new ArrayList<>(), nums, 0);
    return result;
}

void backtrackSubsets(List<List<Integer>> result, List<Integer> current,
                      int[] nums, int start) {
    // Add current subset (can be empty)
    result.add(new ArrayList<>(current));
    
    // Choose next element
    for (int i = start; i < nums.length; i++) {
        current.add(nums[i]);                // Choose
        backtrackSubsets(result, current, nums, i + 1);  // Explore
        current.remove(current.size() - 1);  // Undo
    }
}
```

**Use case:** LeetCode 78 (Subsets)
**Complexity:** O(2^n * n)

### Variant 3: Valid Parentheses

```java
// Generate all valid parentheses combinations
List<String> generateParenthesis(int n) {
    List<String> result = new ArrayList<>();
    backtrackParens(result, "", 0, 0, n);
    return result;
}

void backtrackParens(List<String> result, String current, 
                     int open, int close, int n) {
    // Base case: used all parentheses
    if (open == n && close == n) {
        result.add(current);
        return;
    }
    
    // Can add opening parenthesis?
    if (open < n) {
        backtrackParens(result, current + "(", open + 1, close, n);
    }
    
    // Can add closing parenthesis? (must have open > close)
    if (close < open) {
        backtrackParens(result, current + ")", open, close + 1, n);
    }
}
```

**Use case:** LeetCode 22 (Generate Parentheses)
**Complexity:** O(4^n / sqrt(n))

**Decision Tree:**
```
Enumeration World:
├─ Choose k from n? → Combinations
├─ Order all elements? → Permutations
├─ Include/exclude each? → Subsets
└─ Custom constraint? → Constrained Backtracking
```

---

## 🌍 WORLD 7: CONTINUOUS RANGE WORLD

**Problem Pattern:** Process contiguous elements. Maintain a window that satisfies constraint.

### Template Structure

```
ENTITY:    Array/string elements
WINDOW:    Contiguous subarray/substring
GOAL:      Find optimal window (min/max/exact)
SOLUTION:  Sliding window (two pointers on same direction)
```

### Primary Template: Sliding Window (Min Length)

```java
// Find minimum length window containing all chars from t
String minWindow(String s, String t) {
    if (t.length() > s.length()) return "";
    
    // Frequency maps
    Map<Character, Integer> tCount = new HashMap<>();
    for (char c : t.toCharArray()) {
        tCount.put(c, tCount.getOrDefault(c, 0) + 1);
    }
    
    int required = tCount.size();               // Chars we need to have
    int formed = 0;                             // Chars with required frequency
    
    Map<Character, Integer> windowCount = new HashMap<>();
    
    int[] ans = {-1, 0, 0};  // {length, left, right}
    int left = 0;
    
    // Expand right pointer
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        windowCount.put(c, windowCount.getOrDefault(c, 0) + 1);
        
        // If frequency of current char matches tCount, increment formed
        if (tCount.containsKey(c) && 
            windowCount.get(c).intValue() == tCount.get(c).intValue()) {
            formed++;
        }
        
        // Shrink left pointer
        while (formed == required && left <= right) {
            c = s.charAt(left);
            
            // Save this window if it's smaller
            if (ans[0] == -1 || right - left + 1 < ans[0]) {
                ans[0] = right - left + 1;
                ans[1] = left;
                ans[2] = right;
            }
            
            windowCount.put(c, windowCount.get(c) - 1);
            if (tCount.containsKey(c) && 
                windowCount.get(c).intValue() < tCount.get(c).intValue()) {
                formed--;
            }
            
            left++;
        }
    }
    
    return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
}
```

**Use case:** LeetCode 76 (Minimum Window Substring)
**Complexity:** O(n) where n = length of s

### Variant 1: Fixed Window Size

```java
// Find max sum of k consecutive elements
int maxSumFixedWindow(int[] nums, int k) {
    int windowSum = 0;
    
    // Build first window
    for (int i = 0; i < k; i++) {
        windowSum += nums[i];
    }
    
    int maxSum = windowSum;
    
    // Slide window
    for (int i = k; i < nums.length; i++) {
        windowSum = windowSum - nums[i - k] + nums[i];  // Remove left, add right
        maxSum = Math.max(maxSum, windowSum);
    }
    
    return maxSum;
}
```

**Use case:** LeetCode 643 (Maximum Average Subarray)
**Complexity:** O(n)

### Variant 2: Two Pointers (Opposite Directions)

```java
// Find pair with target sum (sorted array)
int[] twoSum(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left < right) {
        int sum = nums[left] + nums[right];
        
        if (sum == target) {
            return new int[]{left, right};
        } else if (sum < target) {
            left++;                         // Need bigger sum
        } else {
            right--;                        // Need smaller sum
        }
    }
    
    return new int[]{};
}
```

**Use case:** LeetCode 167 (Two Sum II)
**Complexity:** O(n)

### Variant 3: Longest Substring Without Repeating

```java
// Find longest substring with all unique characters
int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> charIndex = new HashMap<>();
    int maxLen = 0;
    int left = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        
        // If char seen and in current window, move left
        if (charIndex.containsKey(c) && charIndex.get(c) >= left) {
            left = charIndex.get(c) + 1;   // Skip the duplicate
        }
        
        charIndex.put(c, right);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    
    return maxLen;
}
```

**Use case:** LeetCode 3 (Longest Substring Without Repeating Characters)
**Complexity:** O(n)

**Decision Tree:**
```
Continuous Range World:
├─ Find min window? → Shrinkable Sliding Window
├─ Fixed k elements? → Fixed Window Size
├─ Opposite ends? → Two Pointers
└─ Constraint: no repeats? → Hash-based Sliding Window
```

---

## 🌍 WORLD 8: PRIORITY WORLD

**Problem Pattern:** Need to repeatedly select maximum/minimum element efficiently. Many insertions and removals.

### Template Structure

```
ENTITY:    Elements to manage
PRIORITY:  Max/min value or custom comparator
GOAL:      Efficiently get top K or maintain sorted order
SOLUTION:  Heap or Priority Queue
```

### Primary Template: Max Heap (Top K Problem)

```java
// Find K largest elements
List<Integer> findKLargest(int[] nums, int k) {
    // Min heap: keep k largest elements
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    
    for (int num : nums) {
        minHeap.offer(num);              // Add element
        
        if (minHeap.size() > k) {
            minHeap.poll();              // Remove smallest
        }
    }
    
    return new ArrayList<>(minHeap);
}

// Alternative: Max heap approach
List<Integer> findKLargestMaxHeap(int[] nums, int k) {
    PriorityQueue<Integer> maxHeap = 
        new PriorityQueue<>((a, b) -> b - a);  // Max heap
    
    for (int num : nums) {
        maxHeap.offer(num);
    }
    
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < k; i++) {
        result.add(maxHeap.poll());
    }
    
    return result;
}
```

**Use case:** LeetCode 215 (Kth Largest Element)
**Complexity:** O(n log k) for min-heap approach

### Variant 1: Custom Comparator

```java
// Merge K sorted lists
ListNode mergeKLists(ListNode[] lists) {
    // Min heap by node value
    PriorityQueue<ListNode> minHeap = 
        new PriorityQueue<>((a, b) -> a.val - b.val);
    
    // Add all list heads
    for (ListNode list : lists) {
        if (list != null) {
            minHeap.offer(list);
        }
    }
    
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    
    // Keep extracting min and adding next
    while (!minHeap.isEmpty()) {
        ListNode node = minHeap.poll();
        current.next = node;
        current = node;
        
        if (node.next != null) {
            minHeap.offer(node.next);
        }
    }
    
    return dummy.next;
}
```

**Use case:** LeetCode 23 (Merge K Sorted Lists)
**Complexity:** O(n log k) where k = number of lists

### Variant 2: Frequency Heap

```java
// Top K frequent elements
List<Integer> topKFrequent(int[] nums, int k) {
    // Count frequencies
    Map<Integer, Integer> count = new HashMap<>();
    for (int num : nums) {
        count.put(num, count.getOrDefault(num, 0) + 1);
    }
    
    // Min heap by frequency
    PriorityQueue<Integer> minHeap = 
        new PriorityQueue<>((a, b) -> count.get(a) - count.get(b));
    
    for (int num : count.keySet()) {
        minHeap.offer(num);
        if (minHeap.size() > k) {
            minHeap.poll();
        }
    }
    
    return new ArrayList<>(minHeap);
}
```

**Use case:** LeetCode 347 (Top K Frequent Elements)
**Complexity:** O(n log k)

### Variant 3: MedianFinder

```java
// Find median from data stream
class MedianFinder {
    PriorityQueue<Integer> maxHeap;     // Left half (larger values, max heap)
    PriorityQueue<Integer> minHeap;     // Right half (smaller values, min heap)
    
    public MedianFinder() {
        maxHeap = new PriorityQueue<>((a, b) -> b - a);  // Max heap
        minHeap = new PriorityQueue<>();                  // Min heap
    }
    
    public void addNum(int num) {
        // Always add to max heap first
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        
        // Balance: maxHeap size = minHeap.size or size + 1
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }
    
    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        }
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
}
```

**Use case:** LeetCode 295 (Find Median from Data Stream)
**Complexity:** O(log n) per addNum, O(1) for findMedian

**Decision Tree:**
```
Priority World:
├─ Top K elements? → Min Heap (size k)
├─ Merge sorted? → Min Heap with comparator
├─ Frequency based? → Heap by count
└─ Median stream? → Two heaps (max + min)
```

---

## 🌍 WORLD 9: SCHEDULING WORLD

**Problem Pattern:** Select or schedule intervals/activities. Maximize count or minimize overlap.

### Template Structure

```
ENTITY:    Intervals with start/end times
CONFLICT:  Do they overlap?
GOAL:      Select max non-overlapping or minimize conflicts
SOLUTION:  Greedy interval selection
```

### Primary Template: Interval Scheduling (Greedy)

```java
// Maximum non-overlapping intervals (activity selection)
int eraseOverlapIntervals(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Sort by end time (greedy: choose ending earliest)
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
    
    int count = 1;
    int lastEnd = intervals[0][1];
    
    for (int i = 1; i < intervals.length; i++) {
        // If current doesn't overlap with last
        if (intervals[i][0] >= lastEnd) {
            count++;
            lastEnd = intervals[i][1];
        }
        // else: overlaps, skip this interval
    }
    
    return intervals.length - count;  // Intervals to remove
}
```

**Use case:** LeetCode 435 (Non-overlapping Intervals)
**Complexity:** O(n log n) due to sorting

### Variant 1: Merge Overlapping Intervals

```java
// Merge overlapping intervals
int[][] merge(int[][] intervals) {
    if (intervals.length == 0) return new int[0][0];
    
    // Sort by start time
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    List<int[]> merged = new ArrayList<>();
    int[] current = intervals[0];
    
    for (int i = 1; i < intervals.length; i++) {
        // Overlapping? Merge
        if (intervals[i][0] <= current[1]) {
            current[1] = Math.max(current[1], intervals[i][1]);
        } else {
            // No overlap? Save and move to next
            merged.add(current);
            current = intervals[i];
        }
    }
    
    merged.add(current);
    return merged.toArray(new int[0][]);
}
```

**Use case:** LeetCode 56 (Merge Intervals)
**Complexity:** O(n log n)

### Variant 2: Meeting Rooms

```java
// Minimum meeting rooms needed
int minMeetingRooms(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Separate start and end times
    int[] starts = new int[intervals.length];
    int[] ends = new int[intervals.length];
    
    for (int i = 0; i < intervals.length; i++) {
        starts[i] = intervals[i][0];
        ends[i] = intervals[i][1];
    }
    
    Arrays.sort(starts);
    Arrays.sort(ends);
    
    int rooms = 0, endIdx = 0;
    
    // For each start, check if an earlier meeting ended
    for (int start : starts) {
        // If meeting ends before next starts, reuse room
        if (endIdx < ends.length && ends[endIdx] <= start) {
            endIdx++;
        } else {
            rooms++;  // Need new room
        }
    }
    
    return rooms;
}
```

**Use case:** LeetCode 253 (Meeting Rooms II)
**Complexity:** O(n log n)

### Variant 3: Interval List Intersections

```java
// Find intersection of two interval lists
int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
    List<int[]> intersections = new ArrayList<>();
    int i = 0, j = 0;
    
    // Two pointers: advance whichever ends first
    while (i < firstList.length && j < secondList.length) {
        int start = Math.max(firstList[i][0], secondList[j][0]);
        int end = Math.min(firstList[i][1], secondList[j][1]);
        
        // If valid intersection
        if (start <= end) {
            intersections.add(new int[]{start, end});
        }
        
        // Advance the one that ends first
        if (firstList[i][1] < secondList[j][1]) {
            i++;
        } else {
            j++;
        }
    }
    
    return intersections.toArray(new int[0][]);
}
```

**Use case:** LeetCode 986 (Interval List Intersections)
**Complexity:** O(m + n)

### Variant 4: Video Stitching (Greedy Interval Coverage)

```java
// Minimum clips needed to cover [0, T]
int videoStitching(int[][] clips, int time) {
    // Sort by start time, then by end time descending
    Arrays.sort(clips, (a, b) -> {
        if (a[0] != b[0]) return a[0] - b[0];
        return b[1] - a[1];  // Longer clips first
    });
    
    int count = 0;
    int currentEnd = 0;      // Current coverage
    int maxEnd = 0;          // Maximum we can reach
    int i = 0;
    int n = clips.length;
    
    while (currentEnd < time) {
        // Check if we can extend coverage
        while (i < n && clips[i][0] <= currentEnd) {
            maxEnd = Math.max(maxEnd, clips[i][1]);
            i++;
        }
        
        // If no progress, impossible
        if (maxEnd == currentEnd) return -1;
        
        // Use one clip to extend
        currentEnd = maxEnd;
        count++;
    }
    
    return count;
}

// Alternative: Using greedy with no sorting (O(n) vs O(n log n))
int videoStitchingGreedy(int[][] clips, int time) {
    int count = 0;
    int currentEnd = 0;
    int maxEnd = 0;
    
    for (int[] clip : clips) {
        // If clip starts beyond current coverage, skip
        if (clip[0] > currentEnd) break;
        
        // Extend maximum reach
        maxEnd = Math.max(maxEnd, clip[1]);
        
        // If reached end of coverage, use a clip
        if (clip[0] <= currentEnd && maxEnd > currentEnd) {
            currentEnd = maxEnd;
            count++;
            
            if (currentEnd >= time) return count;
        }
    }
    
    return currentEnd >= time ? count : -1;
}
```

**Use case:** LeetCode 1024 (Video Stitching)
**Complexity:** O(n log n) with sorting, O(n) without

**Decision Tree:**
```
Scheduling World:
├─ Maximum non-overlapping? → Greedy by end time
├─ Merge overlapping? → Sort by start, then merge
├─ Rooms needed? → Two-pointer on sorted start/end
├─ Find intersections? → Two pointers advancing
└─ Minimum coverage? → Greedy interval stitching
```

---

## 🌍 WORLD 10: STATE TRANSITION WORLD

**Problem Pattern:** Multiple states with transitions between them. Need to track valid state changes.

### Template Structure

```
ENTITY:    Items/days in sequence
STATES:    Different conditions/positions
GOAL:      Valid sequence of states
SOLUTION:  State machine DP or graph transitions
```

### Primary Template: State Machine DP (Stock with Cooldown)

```java
// Stock trading with cooldown
int maxProfit(int[] prices) {
    if (prices.length <= 1) return 0;
    
    int n = prices.length;
    // States: 0=hold, 1=sold, 2=cooldown
    int[] hold = new int[n];
    int[] sold = new int[n];
    int[] cooldown = new int[n];
    
    hold[0] = -prices[0];  // Buy first stock
    
    for (int i = 1; i < n; i++) {
        // Hold: either held before or buy today
        hold[i] = Math.max(hold[i-1], cooldown[i-1] - prices[i]);
        
        // Sold: must have held before, sell today
        sold[i] = hold[i-1] + prices[i];
        
        // Cooldown: either cooldown before or just sold
        cooldown[i] = Math.max(cooldown[i-1], sold[i-1]);
    }
    
    return Math.max(sold[n-1], cooldown[n-1]);  // Can't be holding at end
}
```

**Use case:** LeetCode 309 (Best Time to Buy and Sell Stock with Cooldown)
**Complexity:** O(n) time, O(n) space

### Variant 1: Graph State Transitions

```java
// Word ladder (state = current word, transition = one letter change)
int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0;
    
    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    
    Map<String, Integer> distance = new HashMap<>();
    distance.put(beginWord, 1);
    
    while (!queue.isEmpty()) {
        String word = queue.poll();
        
        // Get all neighbors (one letter change)
        for (String neighbor : getNeighbors(word, wordSet)) {
            if (!distance.containsKey(neighbor)) {
                distance.put(neighbor, distance.get(word) + 1);
                
                if (neighbor.equals(endWord)) {
                    return distance.get(neighbor);
                }
                
                queue.offer(neighbor);
            }
        }
    }
    
    return 0;
}

List<String> getNeighbors(String word, Set<String> wordSet) {
    List<String> neighbors = new ArrayList<>();
    char[] chars = word.toCharArray();
    
    // Try changing each character
    for (int i = 0; i < chars.length; i++) {
        char oldChar = chars[i];
        
        for (char c = 'a'; c <= 'z'; c++) {
            chars[i] = c;
            String newWord = new String(chars);
            
            if (wordSet.contains(newWord)) {
                neighbors.add(newWord);
            }
        }
        
        chars[i] = oldChar;
    }
    
    return neighbors;
}
```

**Use case:** LeetCode 127 (Word Ladder)
**Complexity:** O(n * L * 26) where L = word length

### Variant 2: DFA (Deterministic Finite Automaton)

```java
// Validate IP address (state machine: different states for different segments)
boolean isValidIP(String ip) {
    String[] parts = ip.split("\\.");
    if (parts.length != 4) return false;
    
    for (String part : parts) {
        // Validate each octet
        if (part.isEmpty() || part.length() > 3) return false;
        
        // No leading zeros (except "0" itself)
        if (part.charAt(0) == '0' && part.length() > 1) return false;
        
        // All digits
        for (char c : part.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        
        // 0-255
        int num = Integer.parseInt(part);
        if (num < 0 || num > 255) return false;
    }
    
    return true;
}
```

**Use case:** Input validation, protocol parsing
**Complexity:** O(n)

### Variant 3: Regex Matching (DP State Machine)

```java
// Regex matching with . and *
boolean isMatch(String s, String p) {
    int m = s.length(), n = p.length();
    
    // dp[i][j] = s[0..i-1] matches p[0..j-1]
    boolean[][] dp = new boolean[m+1][n+1];
    dp[0][0] = true;
    
    // Handle patterns like a*, a*b*, etc.
    for (int j = 2; j <= n; j++) {
        if (p.charAt(j-1) == '*') {
            dp[0][j] = dp[0][j-2];  // * can match zero
        }
    }
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j-1) == '*') {
                // * matches zero OR one/more of previous
                dp[i][j] = dp[i][j-2] ||  // Zero match
                    (dp[i-1][j] && 
                     (s.charAt(i-1) == p.charAt(j-2) || 
                      p.charAt(j-2) == '.'));  // One or more
            } else if (s.charAt(i-1) == p.charAt(j-1) || 
                       p.charAt(j-1) == '.') {
                dp[i][j] = dp[i-1][j-1];
            }
        }
    }
    
    return dp[m][n];
}
```

**Use case:** LeetCode 10 (Regular Expression Matching)
**Complexity:** O(m * n)

**Decision Tree:**
```
State Transition World:
├─ Buy/sell stocks? → State Machine DP
├─ Word/letter transitions? → Graph BFS
├─ Input parsing? → DFA / State Machine
└─ Pattern matching? → DP State Machine
```

---

## 🌍 WORLD 11: TREE WORLD

**Problem Pattern:** Hierarchical node structure. Parent-child relationships. Process nodes with dependencies.

### Template Structure

```
ENTITY:    Tree nodes with parent-child links
TRAVERSAL: DFS, BFS, or in-order/pre-order/post-order
GOAL:      Find values, validate, transform, or count
SOLUTION:  Tree traversal or Tree DP
```

### Primary Template: DFS Tree Traversal (Post-Order)

```java
// Maximum path sum in binary tree (post-order: process children first)
int maxPathSum(TreeNode root) {
    int[] max = {Integer.MIN_VALUE};
    dfsMaxPath(root, max);
    return max[0];
}

int dfsMaxPath(TreeNode node, int[] max) {
    if (node == null) return 0;
    
    // Must include current node, so max(0, path) through children
    int leftSum = Math.max(0, dfsMaxPath(node.left, max));
    int rightSum = Math.max(0, dfsMaxPath(node.right, max));
    
    // Update global max: path through this node
    max[0] = Math.max(max[0], leftSum + node.val + rightSum);
    
    // Return: best path from this node down (left OR right, not both)
    return node.val + Math.max(leftSum, rightSum);
}
```

**Use case:** LeetCode 124 (Binary Tree Maximum Path Sum)
**Complexity:** O(n) time (visit each node), O(h) space (recursion depth)

### Variant 1: BFS Level-Order Traversal

```java
// Level-order traversal (BFS)
List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        List<Integer> level = new ArrayList<>();
        
        // Process all nodes at current level
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        result.add(level);
    }
    
    return result;
}
```

**Use case:** LeetCode 102 (Binary Tree Level Order Traversal)
**Complexity:** O(n)

### Variant 2: Tree DP (Subtree Solution)

```java
// Diameter of binary tree (longest path between two nodes)
int diameterOfBinaryTree(TreeNode root) {
    int[] diameter = {0};
    dfsHeight(root, diameter);
    return diameter[0];
}

int dfsHeight(TreeNode node, int[] diameter) {
    if (node == null) return 0;
    
    // Height of left and right subtrees
    int leftHeight = dfsHeight(node.left, diameter);
    int rightHeight = dfsHeight(node.right, diameter);
    
    // Path through current node
    diameter[0] = Math.max(diameter[0], leftHeight + rightHeight);
    
    // Return height: +1 for current node
    return 1 + Math.max(leftHeight, rightHeight);
}

// Alternative: Lowest Common Ancestor (LCA)
TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) return null;
    
    // If either p or q is root, root is LCA
    if (root == p || root == q) return root;
    
    // Search in left and right
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    
    // Both found in different subtrees -> root is LCA
    if (left != null && right != null) return root;
    
    // Both in same subtree
    return left != null ? left : right;
}
```

**Use case:** LeetCode 543 (Diameter of Binary Tree), LeetCode 236 (LCA)
**Complexity:** O(n)

### Variant 3: In-Order Traversal (BST Property)

```java
// Validate BST using in-order traversal (should be sorted)
boolean isValidBST(TreeNode root) {
    List<Integer> inorder = new ArrayList<>();
    dfsInorder(root, inorder);
    
    for (int i = 1; i < inorder.size(); i++) {
        if (inorder.get(i) <= inorder.get(i-1)) {
            return false;  // Not strictly increasing
        }
    }
    
    return true;
}

void dfsInorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    dfsInorder(node.left, result);        // Left
    result.add(node.val);                 // Current
    dfsInorder(node.right, result);       // Right
}

// Alternative: Validate with min/max bounds (no extra space)
boolean isValidBSTBounds(TreeNode root) {
    return dfsValidate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

boolean dfsValidate(TreeNode node, long min, long max) {
    if (node == null) return true;
    
    // Current value must be within bounds
    if (node.val <= min || node.val >= max) return false;
    
    // Left subtree: values < node.val
    // Right subtree: values > node.val
    return dfsValidate(node.left, min, node.val) &&
           dfsValidate(node.right, node.val, max);
}
```

**Use case:** LeetCode 98 (Validate Binary Search Tree)
**Complexity:** O(n)

### Variant 4: Morris Traversal (O(1) Space)

```java
// Inorder traversal without recursion or stack
List<Integer> inorderMorris(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    TreeNode current = root;
    
    while (current != null) {
        if (current.left == null) {
            // No left subtree: visit current and go right
            result.add(current.val);
            current = current.right;
        } else {
            // Find predecessor (rightmost in left subtree)
            TreeNode predecessor = current.left;
            while (predecessor.right != null && predecessor.right != current) {
                predecessor = predecessor.right;
            }
            
            if (predecessor.right == null) {
                // First visit: establish link and go left
                predecessor.right = current;
                current = current.left;
            } else {
                // Second visit: process node, remove link, go right
                result.add(current.val);
                predecessor.right = null;
                current = current.right;
            }
        }
    }
    
    return result;
}
```

**Use case:** Space-optimized tree traversal
**Complexity:** O(n) time, O(1) space (no recursion stack)

**Decision Tree:**
```
Tree World:
├─ Single value query? → Post-order DFS (find max/sum/path)
├─ Level-by-level? → BFS Level-order
├─ Subtree metrics? → Tree DP (height, diameter, LCA)
├─ BST validation? → In-order + bounds checking
└─ Space-critical? → Morris Traversal
```

---

## 📊 Complete 11-World Decision Guide

```
┌─────────────────────────────────────────────────────────────┐
│ See a Problem? Use This Decision Tree                       │
└─────────────────────────────────────────────────────────────┘

1. DEPENDENCY? (Prerequisites, ordering, topological)
   → WORLD 1: Topological Sort

2. REACHABILITY? (Connected, components, can reach)
   → WORLD 2: DFS/BFS/Union Find

3. SHORTEST PATH? (Minimum distance, least cost)
   → WORLD 3: BFS/Dijkstra/Bellman-Ford

4. OPTIMIZATION? (Maximize, minimize, best choice)
   → WORLD 4: DP/Greedy

5. SEARCH SPACE? (Binary search, answer space, monotonic)
   → WORLD 5: Binary Search

6. ENUMERATION? (All combinations, permutations, exhaustive)
   → WORLD 6: Backtracking

7. CONTINUOUS RANGE? (Substring, subarray, sliding window)
   → WORLD 7: Sliding Window/Two Pointers

8. PRIORITY? (Top K, heap, efficient max/min)
   → WORLD 8: Priority Queue/Heap

9. SCHEDULING? (Intervals, non-overlapping, merging)
   → WORLD 9: Greedy Interval Scheduling

10. STATE TRANSITIONS? (Multiple states, valid transitions)
    → WORLD 10: State Machine DP/Graph

11. TREE STRUCTURE? (Hierarchical nodes, parent-child, traversals)
    → WORLD 11: Tree Traversal & Tree DP
```

---

## 🎓 Complete World Reference

All 11 worlds with primary templates and 3-4 variants each = **44 complete solutions** ready to use.

### Study order:

**Tier 1 - Foundations (Days 1-5):**
1. Worlds 1-4: Graph & DP (most fundamental)

**Tier 2 - High Frequency (Days 6-15):**
2. World 11: Trees (18% of interviews!)
3. Worlds 5-7: Search, Enumeration, Sliding Window (high frequency)

**Tier 3 - Specialized (Days 16-25):**
3. Worlds 8-10: Priority, Scheduling, States (specialized but important)

### Complete variant list:

```
World 1: Dependency (Topological Sort)
  ├─ Kahn's Algorithm (BFS)
  ├─ DFS-based
  ├─ Lexicographically smallest
  └─ Cycle detection only

World 2: Reachability (DFS/BFS/Union Find)
  ├─ DFS
  ├─ BFS
  ├─ Union Find
  └─ Multi-source BFS

World 3: Shortest Path (BFS/Dijkstra/Bellman-Ford/Floyd-Warshall)
  ├─ BFS (unweighted)
  ├─ Dijkstra (positive weights)
  ├─ Multi-source
  ├─ Bellman-Ford (negative weights)
  └─ Floyd-Warshall (all pairs)

World 4: Optimization (DP/Greedy)
  ├─ Linear DP
  ├─ Unbounded DP
  ├─ 2D DP
  ├─ State Machine DP
  └─ Counting DP

World 5: Search Space (Binary Search)
  ├─ Basic binary search
  ├─ Answer space search
  ├─ First/Last occurrence
  ├─ Rotated array
  └─ Sorted matrix

World 6: Enumeration (Backtracking)
  ├─ Combinations
  ├─ Permutations
  ├─ Subsets
  └─ Valid parentheses

World 7: Continuous Range (Sliding Window)
  ├─ Min length window
  ├─ Fixed window
  ├─ Two pointers (opposite)
  └─ Longest without repeating

World 8: Priority (Heap/Priority Queue)
  ├─ Top K elements
  ├─ Custom comparator
  ├─ Frequency heap
  └─ Median finder

World 9: Scheduling (Intervals)
  ├─ Non-overlapping intervals
  ├─ Merge overlapping
  ├─ Meeting rooms
  ├─ Interval intersections
  └─ Video stitching

World 10: State Transitions (State Machine)
  ├─ Stock with cooldown
  ├─ Word ladder
  ├─ DFA validation
  └─ Regex matching

World 11: Trees (Traversal & DP)
  ├─ Post-order DFS
  ├─ BFS Level-order
  ├─ Tree DP (LCA, diameter)
  ├─ In-order BST validation
  └─ Morris Traversal (O(1) space)
```

Use together: Templates reference each other for comparative analysis.

---

## 🚀 INTERVIEW WALKTHROUGH EXAMPLES

### Example 1: LeetCode 1011 (Capacity To Ship Packages Within D Days)

**Problem:** Given array of weights and days D, find minimum ship capacity.

**Step 1: Identify World**
```
Keywords: "minimum capacity", "within D days"
→ This is WORLD 5 (Search Space)
Why? Searching for answer in range [max(weights), sum(weights)]
```

**Step 2: Recognize Pattern**
```
Can't directly access answer → Answer Space Binary Search
Constraint: Given days D, is capacity C sufficient?
```

**Step 3: Solve**
```
Left = max weight (minimum needed)
Right = sum of all weights (never need more)

For each mid capacity:
  - Simulate shipping: how many days needed?
  - If days <= D: capacity is enough, try smaller
  - Else: capacity not enough, need bigger
```

**Step 4: Code**
```java
int shipWithinDays(int[] weights, int days) {
    int left = 0, right = 0;
    for (int w : weights) {
        left = Math.max(left, w);
        right += w;
    }
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (canShip(weights, mid, days)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}

boolean canShip(int[] weights, int capacity, int days) {
    int currentDays = 1, currentLoad = 0;
    for (int w : weights) {
        if (currentLoad + w > capacity) {
            currentDays++;
            currentLoad = 0;
        }
        currentLoad += w;
    }
    return currentDays <= days;
}
```

**Time Complexity:** O(n * log(sum)) where n = weights length

---

### Example 2: LeetCode 200 (Number of Islands)

**Problem:** Count islands in 2D grid (connected 1s).

**Step 1: Identify World**
```
Keywords: "islands", "connected", "count groups"
→ This is WORLD 2 (Reachability)
Why? Need to find connected components
```

**Step 2: Recognize Pattern**
```
Find connected components → DFS/BFS
Subproblem: Mark visited to avoid revisiting
```

**Step 3: Solve**
```
For each unvisited cell with '1':
  1. Increment island count
  2. DFS/BFS to mark entire island as visited
```

**Step 4: Code**
```java
int numIslands(char[][] grid) {
    int count = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1') {
                dfs(grid, i, j);
                count++;
            }
        }
    }
    return count;
}

void dfs(char[][] grid, int i, int j) {
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || 
        grid[i][j] == '0') return;
    
    grid[i][j] = '0';  // Mark visited
    dfs(grid, i+1, j);
    dfs(grid, i-1, j);
    dfs(grid, i, j+1);
    dfs(grid, i, j-1);
}
```

**Time Complexity:** O(m × n)

---

### Example 3: LeetCode 300 (Longest Increasing Subsequence)

**Problem:** Find length of longest strictly increasing subsequence.

**Step 1: Identify World**
```
Keywords: "longest", "increasing", "subsequence"
→ This is WORLD 4 (Optimization)
Why? Need to maximize length of valid subsequence
```

**Step 2: Recognize Pattern**
```
Optimization over sequence → Linear DP
State: dp[i] = longest ending at position i
Transition: Try all j < i where arr[j] < arr[i]
```

**Step 3: Solve**
```
dp[i] = max(dp[j] + 1) for all j < i where arr[j] < arr[i]
Or dp[i] = 1 if no such j
```

**Step 4: Code**
```java
int lengthOfLIS(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    Arrays.fill(dp, 1);  // Minimum: each element itself
    
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
    }
    
    return Arrays.stream(dp).max().orElse(0);
}
```

**Time Complexity:** O(n²) | Can optimize to O(n log n) with binary search

---

## 🚨 COMMON PITFALLS & DEBUGGING BY WORLD

### WORLD 1: Dependency (Topological Sort)

**Pitfall 1:** Assuming BFS is always faster
- **Problem:** DFS can be simpler for small graphs
- **Fix:** For large sparse graphs use Kahn's; for small use DFS

**Pitfall 2:** Not detecting cycles
- **Problem:** Forgetting to check indegree or color marking
- **Fix:** Always check if all nodes are processed (n nodes should be in result)

**Pitfall 3:** Wrong edge direction
- **Problem:** Confusing "A depends on B" vs "A → B"
- **Fix:** Re-read: "A must come before B" → edge A → B

### WORLD 2: Reachability (DFS/BFS/Union Find)

**Pitfall 1:** Stack overflow in DFS
- **Problem:** Deep recursion on large graph
- **Fix:** Use iterative DFS with explicit stack or BFS with queue

**Pitfall 2:** Not marking visited
- **Problem:** Infinite loop in cyclic graph
- **Fix:** Mark visited BEFORE processing neighbors

**Pitfall 3:** Union Find path compression forgotten
- **Problem:** O(n) operations instead of O(α(n))
- **Fix:** Always compress path: `parent[x] = find(parent[x])`

### WORLD 3: Shortest Path

**Pitfall 1:** Dijkstra with negative weights
- **Problem:** Returns wrong answer silently
- **Fix:** Check graph first; use Bellman-Ford if negatives exist

**Pitfall 2:** Not initializing distances correctly
- **Problem:** Wrong shortest paths
- **Fix:** Start with `dist[source] = 0`, all others = infinity

**Pitfall 3:** Popping visited nodes multiple times
- **Problem:** Inefficiency or wrong answer
- **Fix:** Check visited before processing or when popping

### WORLD 4: Optimization (DP)

**Pitfall 1:** Wrong base case
- **Problem:** Cascade errors through entire DP table
- **Fix:** Manually verify base case for n=0, n=1

**Pitfall 2:** Dimension mismatch in 2D DP
- **Problem:** Index out of bounds
- **Fix:** Clearly define what dp[i][j] means

**Pitfall 3:** Modifying array while iterating (for unbounded DP)
- **Problem:** Using updated values instead of previous iteration
- **Fix:** Use separate array or clear understanding of update order

### WORLD 5: Search Space (Binary Search)

**Pitfall 1:** Off-by-one in boundaries
- **Problem:** Missing element or infinite loop
- **Fix:** Test with n=1 and verify boundaries are inclusive/exclusive

**Pitfall 2:** Integer overflow in mid calculation
- **Problem:** `mid = (left + right) / 2` fails on large numbers
- **Fix:** Use `mid = left + (right - left) / 2`

**Pitfall 3:** Forgetting to handle duplicate elements
- **Problem:** Wrong result in rotated array with duplicates
- **Fix:** Handle edge case: when left=mid=right

### WORLD 6: Enumeration (Backtracking)

**Pitfall 1:** Forgetting to undo (backtrack)
- **Problem:** Accumulates choices from previous branches
- **Fix:** Remove element after recursive call

**Pitfall 2:** Duplicate solutions in result
- **Problem:** Same combination generated multiple times
- **Fix:** Sort and use `start` index or skip duplicates

**Pitfall 3:** No pruning/early termination
- **Problem:** Generates way more than needed
- **Fix:** Add constraints: `if (current.size() == target) return`

### WORLD 7: Continuous Range (Sliding Window)

**Pitfall 1:** Window too small or too large
- **Problem:** Missing valid windows
- **Fix:** Expand on every element, shrink only when needed

**Pitfall 2:** Not maintaining window state correctly
- **Problem:** Wrong frequency count
- **Fix:** Always update map when expanding/shrinking

**Pitfall 3:** Edge case: empty result
- **Problem:** Forgetting to initialize or validate
- **Fix:** Check `if (result.isEmpty())` explicitly

### WORLD 8: Priority (Heap)

**Pitfall 1:** Wrong heap type (min vs max)
- **Problem:** Getting wrong element
- **Fix:** Verify what you're comparing: `(a,b) -> a - b` is min heap

**Pitfall 2:** Heap size management
- **Problem:** Getting top K when heap has size != K
- **Fix:** Check `if (heap.size() > k)` AFTER insertion

**Pitfall 3:** Not using comparator correctly
- **Problem:** Wrong ordering
- **Fix:** Test custom comparator on sample data

### WORLD 9: Scheduling (Intervals)

**Pitfall 1:** Forgetting to sort
- **Problem:** Greedy doesn't work on unsorted input
- **Fix:** Always sort by start (or end for greedy)

**Pitfall 2:** Wrong merge condition
- **Problem:** `a.end < b.start` vs `a.end <= b.start`
- **Fix:** Clarify: do touching intervals overlap? (usually yes)

**Pitfall 3:** Not handling empty result
- **Problem:** Crash on empty array
- **Fix:** Check `if (intervals.length == 0) return ...`

### WORLD 10: State Transition (State Machine)

**Pitfall 1:** Wrong state definition
- **Problem:** Missing valid transitions
- **Fix:** Clearly define all possible states upfront

**Pitfall 2:** Forgetting state constraints
- **Problem:** Counting impossible transitions
- **Fix:** Verify constraints with examples

**Pitfall 3:** Array dimension mismatch
- **Problem:** Index out of bounds
- **Fix:** dp[n][states], verify both dimensions

### WORLD 11: Trees (Traversal & DP)

**Pitfall 1:** Forgetting to handle null children
- **Problem:** NullPointerException
- **Fix:** Check `if (node == null) return ...` at start

**Pitfall 2:** Post-order vs Pre-order confusion
- **Problem:** Wrong results
- **Fix:** Pre-order = current first, Post-order = children first

**Pitfall 3:** Not accounting for tree imbalance
- **Problem:** Stack overflow on deep tree
- **Fix:** Use iterative or BFS for very deep trees

---

## 🔗 HYBRID PROBLEMS (Problems Spanning Multiple Worlds)

### Type 1: Graph + DP Hybrids

**Example: LeetCode 1786 (Number of Restricted Paths)**
```
Combines: WORLD 2 (Reachability) + WORLD 4 (DP)

Insight: First use Dijkstra to find shortest paths (WORLD 3)
        Then use DP to count paths (WORLD 4)
        
When to recognize:
- "Count paths with constraint involving distance"
- Need to compute metric first, then optimize over it
```

**Example: LeetCode 743 (Network Delay Time)**
```
Combines: WORLD 3 (Shortest Path) + WORLD 2 (Reachability)

Insight: Use Dijkstra to find shortest paths from source
        Check if all nodes reached (reachability)
        Return maximum distance
        
When to recognize:
- "Minimum time for all nodes to receive signal"
- Need both: shortest paths AND check all reachable
```

### Type 2: Trees + DP Hybrids

**Example: LeetCode 337 (House Robber III)**
```
Combines: WORLD 11 (Trees) + WORLD 4 (DP)

Insight: DFS each subtree
        At each node: rob or don't rob
        Choose maximum
        
When to recognize:
- "Maximize value in tree structure"
- Choices: include current node or not
```

**Example: LeetCode 1932 (Merge BSTs into Single BST)**
```
Combines: WORLD 11 (Trees) + WORLD 2 (Reachability)

Insight: Use tree traversal to collect values
        Rebuild tree maintaining BST property
        
When to recognize:
- "Transform multiple trees into one"
- Tree structure + tree construction
```

### Type 3: Binary Search + DP Hybrids

**Example: LeetCode 1473 (Paint House III)**
```
Combines: WORLD 5 (Search/Constraints) + WORLD 4 (DP)

Insight: DP[house][color][neighborhoods]
        Binary search might be used if you need to find answer
        
When to recognize:
- "Constrain search space to valid values"
- Then DP over remaining choices
```

### Type 4: Sliding Window + HashMap Hybrids

**Example: LeetCode 30 (Substring with Concatenation of All Words)**
```
Combines: WORLD 7 (Sliding Window) + HashMap frequency tracking

Insight: Fixed-size sliding window (word length * num words)
        Maintain frequency of words in window
        
When to recognize:
- "Sliding window with exact word matching"
- Need exact count of multiple elements
```

### How to Recognize Hybrid Problems

```
1. Read problem twice
2. After first read, identify obvious world
3. After second read, check if there's a secondary metric
4. If problem has TWO components:
   - "Find shortest path AND count paths" → Graph + DP
   - "Traverse tree AND maximize value" → Tree + DP
   - "Binary search AND optimize" → Search + DP
5. Solution approach: Solve primary world, feed result to secondary
```

---

## 📊 COMPLEXITY COMPARISON MATRIX

### All Worlds - Time & Space Complexity

```
WORLD 1: DEPENDENCY (Topological Sort)
┌─────────────────────┬──────────┬─────────┬───────┬──────────┐
│ Algorithm           │ Time     │ Space   │ Best  │ Worst    │
├─────────────────────┼──────────┼─────────┼───────┼──────────┤
│ Kahn's (BFS)        │ O(V+E)   │ O(V)    │ Sparse│ ✓        │
│ DFS-based           │ O(V+E)   │ O(V)    │ Small │ ✓        │
│ Lexicographic       │ O(V+E)   │ O(V)    │ Rare  │ O(V+E)   │
│ Cycle Detection     │ O(V+E)   │ O(V)    │ Fast  │ ✓        │
└─────────────────────┴──────────┴─────────┴───────┴──────────┘

WORLD 2: REACHABILITY (DFS/BFS/Union Find)
┌─────────────────────┬──────────┬─────────┬──────────┬──────────┐
│ Algorithm           │ Time     │ Space   │ Best     │ Worst    │
├─────────────────────┼──────────┼─────────┼──────────┼──────────┤
│ DFS (recursive)     │ O(V+E)   │ O(V)    │ Small    │ Stack    │
│ DFS (iterative)     │ O(V+E)   │ O(V)    │ Large    │ ✓        │
│ BFS                 │ O(V+E)   │ O(V)    │ Balanced │ ✓        │
│ Union Find          │ O(α(V))  │ O(V)    │ Dynamic  │ ✓✓✓      │
│ Multi-source BFS    │ O(V+E)   │ O(V)    │ Multiple │ ✓        │
└─────────────────────┴──────────┴─────────┴──────────┴──────────┘

WORLD 3: SHORTEST PATH (BFS/Dijkstra/Bellman-Ford/Floyd-Warshall)
┌─────────────────────┬──────────────┬─────────┬────────┬──────────┐
│ Algorithm           │ Time         │ Space   │ Best   │ Worst    │
├─────────────────────┼──────────────┼─────────┼────────┼──────────┤
│ BFS (unweighted)    │ O(V+E)       │ O(V)    │ Simple │ ✓        │
│ Dijkstra (PQ)       │ O((V+E)logV) │ O(V)    │ Sparse │ ✓✓       │
│ Dijkstra (array)    │ O(V²)        │ O(V)    │ Dense  │ ✓        │
│ Bellman-Ford        │ O(VE)        │ O(V)    │ Nega.  │ ✓        │
│ Floyd-Warshall      │ O(V³)        │ O(V²)   │ Dense  │ All-pair │
│ Multi-source        │ O(V+E)       │ O(V)    │ Multi  │ ✓        │
└─────────────────────┴──────────────┴─────────┴────────┴──────────┘

WORLD 4: OPTIMIZATION (DP)
┌─────────────────────┬──────────┬─────────┬────────┬──────────┐
│ Pattern             │ Time     │ Space   │ Best   │ Worst    │
├─────────────────────┼──────────┼─────────┼────────┼──────────┤
│ Linear DP           │ O(n)     │ O(n)    │ Simple │ ✓        │
│ Unbounded DP        │ O(n·c)   │ O(n)    │ Small  │ Many ch. │
│ 2D DP               │ O(m·n)   │ O(m·n)  │ String │ ✓        │
│ State Machine DP    │ O(n·s)   │ O(n·s)  │ Fixed  │ ✓        │
│ Counting DP         │ O(n²)    │ O(n)    │ Path   │ ✓        │
│ DP (optimized)      │ O(n)     │ O(1)    │ Space  │ ✓✓       │
└─────────────────────┴──────────┴─────────┴────────┴──────────┘

WORLD 5: SEARCH SPACE (Binary Search)
┌─────────────────────┬──────────┬─────────┬────────┬──────────┐
│ Variant             │ Time     │ Space   │ Best   │ Worst    │
├─────────────────────┼──────────┼─────────┼────────┼──────────┤
│ Basic Search        │ O(log n) │ O(1)    │ Simple │ ✓✓✓      │
│ Answer Space        │ O(n·logX)│ O(1)    │ Custom │ Overhead │
│ First/Last          │ O(log n) │ O(1)    │ Dupes  │ ✓        │
│ Rotated Array       │ O(log n) │ O(1)    │ Rotate │ ✓        │
│ Matrix              │ O(log(m·n)) │ O(1) │ 2D     │ ✓        │
└─────────────────────┴──────────┴─────────┴────────┴──────────┘

WORLD 6: ENUMERATION (Backtracking)
┌─────────────────────┬──────────┬─────────┬────────┬──────────┐
│ Pattern             │ Time     │ Space   │ Best   │ Worst    │
├─────────────────────┼──────────┼─────────┼────────┼──────────┤
│ Combinations C(n,k) │ O(C(n,k)·k) │ O(k) │ Small  │ ✓        │
│ Permutations        │ O(n!·n)  │ O(n)    │ Small  │ Huge     │
│ Subsets             │ O(2^n·n) │ O(n)    │ Small  │ ✓        │
│ Valid Parentheses   │ O(4^n/√n)│ O(n)    │ Medium │ ✓        │
│ Pruned Search       │ Variable │ O(n)    │ Better │ ✓        │
└─────────────────────┴──────────┴─────────┴────────┴──────────┘

WORLD 7: CONTINUOUS RANGE (Sliding Window)
┌─────────────────────┬──────────┬─────────┬────────┬──────────┐
│ Pattern             │ Time     │ Space   │ Best   │ Worst    │
├─────────────────────┼──────────┼─────────┼────────┼──────────┤
│ Min Window          │ O(n)     │ O(k)    │ Simple │ ✓        │
│ Fixed Window        │ O(n)     │ O(k)    │ Simple │ ✓✓       │
│ Two Pointers        │ O(n)     │ O(1)    │ Simple │ ✓✓✓      │
│ Hash-based          │ O(n)     │ O(k)    │ Unique │ ✓        │
└─────────────────────┴──────────┴─────────┴────────┴──────────┘

WORLD 8: PRIORITY (Heap/Priority Queue)
┌─────────────────────┬──────────┬─────────┬────────┬──────────┐
│ Pattern             │ Time     │ Space   │ Best   │ Worst    │
├─────────────────────┼──────────┼─────────┼────────┼──────────┤
│ Top K Elements      │ O(nlogk) │ O(k)    │ Large  │ ✓        │
│ Merge K Lists       │ O(nlogk) │ O(k)    │ Many   │ ✓        │
│ Frequency Heap      │ O(nlogk) │ O(n)    │ Freq   │ ✓        │
│ Median Finder       │ O(logn)  │ O(n)    │ Stream │ ✓        │
└─────────────────────┴──────────┴─────────┴────────┴──────────┘

WORLD 9: SCHEDULING (Intervals)
┌─────────────────────┬──────────┬─────────┬────────┬──────────┐
│ Pattern             │ Time     │ Space   │ Best   │ Worst    │
├─────────────────────┼──────────┼─────────┼────────┼──────────┤
│ Non-overlapping     │ O(nlogn) │ O(1)    │ Greedy │ ✓        │
│ Merge Overlapping   │ O(nlogn) │ O(n)    │ Dense  │ ✓        │
│ Meeting Rooms       │ O(nlogn) │ O(n)    │ Dense  │ ✓        │
│ Intersections       │ O(m+n)   │ O(1)    │ Sorted │ ✓✓       │
│ Video Stitching     │ O(nlogn) │ O(1)    │ Greedy │ ✓        │
└─────────────────────┴──────────┴─────────┴────────┴──────────┘

WORLD 10: STATE TRANSITIONS (State Machine DP)
┌─────────────────────┬──────────┬─────────┬────────┬──────────┐
│ Pattern             │ Time     │ Space   │ Best   │ Worst    │
├─────────────────────┼──────────┼─────────┼────────┼──────────┤
│ Stock with Cooldown │ O(n)     │ O(1)    │ Stream │ ✓✓       │
│ Word Ladder         │ O(nL·26) │ O(n)    │ Short  │ ✓        │
│ DFA Validation      │ O(n)     │ O(1)    │ Simple │ ✓        │
│ Regex Matching      │ O(m·n)   │ O(m·n)  │ Match  │ ✓        │
└─────────────────────┴──────────┴─────────┴────────┴──────────┘

WORLD 11: TREES (Traversal & DP)
┌─────────────────────┬──────────┬─────────┬────────┬──────────┐
│ Pattern             │ Time     │ Space   │ Best   │ Worst    │
├─────────────────────┼──────────┼─────────┼────────┼──────────┤
│ Post-order DFS      │ O(n)     │ O(h)    │ Normal │ ✓        │
│ BFS Level-order     │ O(n)     │ O(w)    │ Narrow │ ✓        │
│ Tree DP             │ O(n)     │ O(h)    │ Solve  │ ✓        │
│ In-order BST        │ O(n)     │ O(h)    │ Verify │ ✓        │
│ Morris Traversal    │ O(n)     │ O(1)    │ Space! │ ✓✓✓      │
└─────────────────────┴──────────┴─────────┴────────┴──────────┘

Legend: ✓ = Good choice | ✓✓ = Excellent | ✓✓✓ = Best | O(X) = Time Complexity
```

---

## 🚨 EDGE CASES BY WORLD

### WORLD 1: Dependency
- Empty graph (0 nodes)
- Single node (no dependencies)
- Disconnected components
- Self-loops (cycle)
- Linear chain (A→B→C→D)

**Test with:** `[], [[0,1]], [[1,0]]`

### WORLD 2: Reachability
- Empty grid
- Single cell
- All 0s / All 1s
- Disconnected islands
- Grid with walls everywhere

**Test with:** `[[]]`, `[['1']]`, `[['0']]`

### WORLD 3: Shortest Path
- No path exists
- Unreachable nodes
- Negative weights (use Bellman-Ford)
- Negative cycles
- Single node

**Test with:** `n=1`, `edges=[]`, disconnected graphs

### WORLD 4: Optimization (DP)
- Empty array `[]`
- Single element `[x]`
- All same values
- All increasing / All decreasing
- Negative numbers

**Test with:** `[], [0], [1,1,1], [1,2,3,4]`

### WORLD 5: Search Space
- Element not found
- Duplicate elements (rotated array)
- Array of size 1
- First or last element target
- Empty array

**Test with:** `[1], [1,1,1], [3,1,1]`

### WORLD 6: Enumeration
- Empty input
- Duplicate elements (combinations)
- n=0 or k=0
- k > n
- All same elements

**Test with:** `n=0, k=1`, `[1,1,1]`, `k=0`

### WORLD 7: Continuous Range
- Empty string/array
- Single character/element
- No valid window
- Entire string is answer
- Window larger than input

**Test with:** `""`, `"a"`, impossible constraint

### WORLD 8: Priority (Heap)
- Empty input
- Single element
- k > size
- All same values
- Negative numbers

**Test with:** `[], [1], k=10 on size 3`

### WORLD 9: Scheduling
- Empty intervals
- Single interval
- Overlapping all
- No overlap at all
- Touching intervals (edge inclusive vs exclusive)

**Test with:** `[], [[1,2]], [[1,5],[2,3],[4,6]]`

### WORLD 10: State Transitions
- Empty input
- Single element
- No valid transition
- All same state
- Alternating states

**Test with:** `[], [0], prices = []`

### WORLD 11: Trees
- Empty tree (null)
- Single node
- Left-skewed (linked list)
- Right-skewed (linked list)
- Unbalanced tree

**Test with:** `null`, `root = [1]`, `[1,2,3,4,5,6,7]`

---

## ⚡ OPTIMIZATION TECHNIQUES BY WORLD

### WORLD 1: Dependency
**Current:** O(V+E)
- Use adjacency list not matrix
- Avoid creating strings for ordering
- Single pass if possible

### WORLD 2: Reachability
**Current:** O(V+E) for DFS/BFS, O(α(n)) for Union Find
- **To O(1):** Can't improve further
- **Space Optimization:** Union Find uses O(V) space best
- **Pro tip:** Union Find is fastest for dynamic connectivity

### WORLD 3: Shortest Path
**Current:** O((V+E)logV) for Dijkstra
- **For sparse graphs:** Dijkstra with PQ is optimal
- **For dense graphs:** Floyd-Warshall O(V³) if all-pairs needed
- **For negative:** Bellman-Ford O(VE)
- **Pro tip:** Use SPFA if negative edges but no negative cycles

### WORLD 4: Optimization (DP)
**Current:** O(n²) for many DP problems
- **To O(n log n):** Segment tree or Binary Indexed Tree
- **To O(n):** Better state transition or greedy
- **Space to O(1):** Rolling arrays (only keep last 2 rows)
- **Pro tip:** Always try space optimization after getting correct answer

### WORLD 5: Search Space
**Current:** O(log n)
- **Already optimal:** Can't improve beyond binary search
- **Optimization:** Narrow search space with better bounds
- **Pro tip:** Answer space binary search often cleaner than direct access

### WORLD 6: Enumeration
**Current:** O(2^n) inherent
- **Pruning:** Cut invalid branches early
- **Deduplication:** Sort to skip duplicates
- **Pro tip:** Pruning can improve by orders of magnitude

### WORLD 7: Continuous Range
**Current:** O(n)
- **Already optimal:** Can't beat linear
- **Space:** Use HashMap or Set for storage
- **Pro tip:** Two pointers can achieve O(1) space if no duplicate tracking needed

### WORLD 8: Priority
**Current:** O(n log k) for top K
- **To O(n):** QuickSelect (average case)
- **Optimization:** Don't build full heap if k is small
- **Pro tip:** For k << n, min-heap approach is best

### WORLD 9: Scheduling
**Current:** O(n log n) due to sorting
- **Optimization:** Greedy already optimal after sort
- **Two-pointer:** O(m+n) if both lists sorted
- **Pro tip:** Sorting dominates; choose sort algorithm carefully

### WORLD 10: State Transitions
**Current:** O(n·states)
- **Optimization:** Reduce states if possible
- **To O(n):** Sometimes states reduce to 2-3 only
- **Space to O(1):** Use variables instead of array
- **Pro tip:** Define states carefully from start

### WORLD 11: Trees
**Current:** O(n) for traversal
- **Space to O(1):** Morris Traversal (inorder only)
- **For recursive:** O(h) stack space best
- **For iterative:** Same O(h) with explicit stack
- **Pro tip:** BFS uses O(w) width space; use if tree is shallow

---

## 💬 INTERVIEW COMMUNICATION TIPS

### When to mention each world (What to SAY)

#### WORLD 1: Dependency (Topological Sort)
**Good:** "I see dependencies between items, so I'll use topological sort to find valid ordering."
**Better:** "There are prerequisite relationships. I'll use Kahn's algorithm with indegree tracking to detect cycles and find order."
**Best:** "I'll build a DAG and use topological sort. If a cycle exists, return empty. Otherwise, return valid ordering using BFS-based Kahn's for efficiency."

#### WORLD 2: Reachability (DFS/BFS)
**Good:** "I'll check if they're connected using DFS."
**Better:** "I'll use DFS to explore connected components. For each unvisited node, I'll mark the entire component as visited."
**Best:** "I'll count islands by incrementing counter for each new component and using DFS to mark neighbors visited. This avoids processing same component twice."

#### WORLD 3: Shortest Path
**Good:** "I'll find the shortest path using BFS."
**Better:** "The graph is weighted with non-negative edges, so I'll use Dijkstra with a priority queue for efficiency."
**Best:** "I'll use Dijkstra with O((V+E)logV) time complexity. I maintain a min-heap of distances and relax edges only when I find shorter paths."

#### WORLD 4: Optimization (DP)
**Good:** "This is a DP problem where I maximize/minimize."
**Better:** "I'll define state dp[i] = the answer for subproblem up to position i. Transition is dp[i] = max(dp[j] + benefit) for valid j < i."
**Best:** "I'll use DP with clear state definition: dp[i] means best solution using first i elements. Base case is dp[0] = 0. Transition considers all previous states and picks maximum. Time is O(n²), space can be O(1) with rolling array."

#### WORLD 5: Search Space  
**Good:** "I'll binary search on the answer."
**Better:** "The answer space is monotonic - if capacity X works, then X+1 also works. I'll binary search between left (minimum) and right (maximum) bounds."
**Best:** "I'll search answer space [min_capacity, sum_weights]. For each mid value, I verify if it's sufficient in O(n). This gives O(n·logX) where X is range."

#### WORLD 6: Enumeration
**Good:** "I'll use backtracking to generate all combinations."
**Better:** "I'll use backtracking with choose-explore-undo pattern. To avoid duplicates, I use a start index so each element appears at most once."
**Best:** "Backtracking: at each position, I choose an element starting from start_index, recursively explore with start_index+1, then undo and try next element. This generates C(n,k) combinations without duplicates."

#### WORLD 7: Continuous Range
**Good:** "I'll use a sliding window of size k."
**Better:** "I maintain a window and expand right pointer. When condition breaks, I shrink left pointer. Window is always valid."
**Best:** "For each right pointer position, I shrink left pointer until window is valid again. I track the best window seen. This avoids revisiting same elements - O(n) single pass."

#### WORLD 8: Priority (Heap)
**Good:** "I'll use a heap to get top K elements."
**Better:** "I'll maintain a min-heap of size K. As I iterate, I add element and if heap size exceeds K, I remove minimum. Final heap contains K largest."
**Best:** "I use a min-heap with max size K. Time is O(n·logK) - insertion is O(logK) and I do it n times. Space is O(K). This is optimal for large n and small K."

#### WORLD 9: Scheduling
**Good:** "I'll sort intervals and greedily select non-overlapping ones."
**Better:** "I sort by end time (greedy: always pick ending earliest). Then iterate and pick interval if it doesn't overlap with last picked."
**Best:** "Greedy approach: sort by end time because picking earliest-ending intervals leaves maximum room for future intervals. This maximizes count. Time O(nlogn) for sorting."

#### WORLD 10: State Transition
**Good:** "I'll use DP with states for each condition."
**Better:** "I define states: hold=best profit while holding, sold=best profit after selling, cooldown=best profit in cooldown. Transitions connect them based on actions."
**Best:** "State machine DP with 3 states. At each day, I either buy (from cooldown), sell (from hold), or cooldown (from sold). I track maximum profit in each state. Transitions ensure valid state sequences."

#### WORLD 11: Trees
**Good:** "I'll use DFS to traverse the tree."
**Better:** "I'll use post-order DFS: process both children first, then use their results to compute current node value."
**Best:** "Post-order traversal allows me to compute bottom-up. At each node, I get left_result and right_result, compute current value, then return to parent. Time O(n), space O(h) for recursion."

### What NOT to say

❌ "I'll just iterate and check" (too vague)
❌ "Brute force first" (wastes time in interview)
❌ "I'll optimize later" (run out of time)
❌ "I think this is X" (be confident)
❌ "Let me try a different approach" (sounds uncertain)

### What to say instead

✅ "The pattern is X because [reason], so I'll use [algorithm]"
✅ "Time complexity is O(n·logn) due to sorting and O(logn) for binary search"
✅ "Base case is [X], transition is [Y], final answer is [Z]"
✅ "To optimize, I'll use [technique] to reduce space from O(n) to O(1)"
✅ "Edge case: [empty/single/negative]. I handle it by [solution]"

---

## 📈 PROBLEM DIFFICULTY MAPPING

### By Difficulty Level

#### EASY PROBLEMS (Single World)

**WORLD 1:** 
- LeetCode 207 (Course Schedule) - Detect cycle
- LeetCode 1915 (Number of Wonderful Substrings) - Order matters

**WORLD 2:**
- LeetCode 200 (Number of Islands) - Count components
- LeetCode 695 (Max Area of Island) - Find largest

**WORLD 3:**
- LeetCode 1091 (Shortest Path in Binary Matrix) - BFS
- LeetCode 1302 (Deepest Leaves Sum) - Level-order

**WORLD 4:**
- LeetCode 70 (Climbing Stairs) - Linear DP
- LeetCode 121 (Best Time to Buy and Sell Stock) - Linear DP

**WORLD 5:**
- LeetCode 704 (Binary Search) - Basic search
- LeetCode 35 (Search Insert Position) - Find position

**WORLD 6:**
- LeetCode 77 (Combinations) - Generate combinations
- LeetCode 78 (Subsets) - Generate subsets

**WORLD 7:**
- LeetCode 643 (Maximum Average Subarray) - Fixed window
- LeetCode 121 (Two Sum) - Two pointers

**WORLD 8:**
- LeetCode 215 (Kth Largest Element) - Top K
- LeetCode 703 (Kth Largest Element in Stream) - Heap

**WORLD 9:**
- LeetCode 452 (Minimum Number of Arrows) - Greedy intervals
- LeetCode 919 (Meeting Rooms) - Check overlap

**WORLD 10:**
- LeetCode 303 (Range Sum Query) - Cumulative sum
- LeetCode 309 (Stock with Cooldown) - State DP

**WORLD 11:**
- LeetCode 104 (Maximum Depth of Binary Tree) - Tree height
- LeetCode 111 (Minimum Depth of Binary Tree) - Shortest path

---

#### MEDIUM PROBLEMS (Single or Hybrid)

**WORLD 1:**
- LeetCode 207 (Course Schedule) - Build graph, detect cycle
- LeetCode 210 (Course Schedule II) - Return valid ordering

**WORLD 2:**
- LeetCode 261 (Graph Valid Tree) - V nodes, V-1 edges, connected?
- LeetCode 323 (Number of Connected Components) - Find all components

**WORLD 3:**
- LeetCode 787 (Cheapest Flights Within K Stops) - Constrained shortest path
- LeetCode 743 (Network Delay Time) - Find maximum shortest path

**WORLD 4:**
- LeetCode 300 (Longest Increasing Subsequence) - O(n²) DP
- LeetCode 91 (Decode Ways) - Count valid decodings
- LeetCode 198 (House Robber) - Choose max non-adjacent

**WORLD 5:**
- LeetCode 33 (Search in Rotated Sorted Array) - Handle rotation
- LeetCode 74 (Search a 2D Matrix) - Binary search on matrix
- LeetCode 875 (Koko Eating Bananas) - Answer space binary search

**WORLD 6:**
- LeetCode 46 (Permutations) - Generate all orderings
- LeetCode 22 (Generate Parentheses) - Valid parentheses combinations

**WORLD 7:**
- LeetCode 3 (Longest Substring Without Repeating) - Expand/shrink window
- LeetCode 76 (Minimum Window Substring) - Shrinkable window

**WORLD 8:**
- LeetCode 347 (Top K Frequent Elements) - Frequency + heap
- LeetCode 23 (Merge K Sorted Lists) - Custom comparator

**WORLD 9:**
- LeetCode 435 (Non-overlapping Intervals) - Remove minimum
- LeetCode 56 (Merge Intervals) - Merge overlapping

**WORLD 10:**
- LeetCode 123 (Best Time to Buy and Sell Stock III) - State tracking
- LeetCode 1137 (N-th Tribonacci Number) - Multi-state

**WORLD 11:**
- LeetCode 236 (Lowest Common Ancestor) - Tree DP
- LeetCode 124 (Binary Tree Maximum Path Sum) - Post-order

---

#### HARD PROBLEMS (Hybrid or Advanced)

**Hybrid Type 1: Graph + DP**
- LeetCode 1786 (Number of Restricted Paths) - Dijkstra + DP
- LeetCode 1928 (Minimum Cost to Reach Destination) - Graph + DP

**Hybrid Type 2: Tree + DP**
- LeetCode 337 (House Robber III) - Tree DP
- LeetCode 1932 (Merge BSTs into Single BST) - Tree reconstruction

**Hybrid Type 3: Search + DP**
- LeetCode 1473 (Paint House III) - Constraints + DP
- LeetCode 1723 (Find Minimum Time to Finish All Jobs) - Search answer + verify

**Hybrid Type 4: Complex State Machines**
- LeetCode 10 (Regular Expression Matching) - Complex state DP
- LeetCode 44 (Wildcard Matching) - Dynamic state transitions

**Advanced Techniques:**
- LeetCode 587 (Erect the Fence) - Convex hull + geometry
- LeetCode 1172 (Dinner Plate Stacks) - Design + DS

---

## 🔗 CROSS-WORLD PROBLEM PATTERNS

### Pattern 1: Find shortest path in special graphs

**Worlds involved:** 2 (Reachability) + 3 (Shortest Path)

**When:** "Find path where path must exist in certain form"

**Examples:**
- Shortest path only through certain nodes
- Path avoiding obstacles
- Minimum hops in network

**Approach:** 
1. Use Reachability to identify valid nodes
2. Apply Shortest Path on filtered graph

---

### Pattern 2: Optimize over tree structure

**Worlds involved:** 11 (Trees) + 4 (Optimization)

**When:** "Maximize/minimize something in tree"

**Examples:**
- Maximize path sum
- Minimize house paint cost
- Maximum profit with constraints

**Approach:**
1. DFS to explore tree
2. DP state at each node
3. Combine children results

---

### Pattern 3: Count paths with constraints

**Worlds involved:** 2 (Reachability) + 6 (Enumeration) + 4 (Optimization)

**When:** "Count valid paths/sequences/combinations"

**Examples:**
- Count paths with sum = target
- Count subsequences
- Count connected components

**Approach:**
1. Identify all possibilities (enumeration)
2. Filter by constraints (reachability)
3. Count using DP (optimization)

---

### Pattern 4: Binary search within another algorithm

**Worlds involved:** 5 (Search Space) + any world

**When:** "Within main algorithm, need to search for parameter"

**Examples:**
- Find minimum capacity (binary search within simulation)
- Find optimal speed (binary search + verification)

**Approach:**
1. Binary search on candidate answer
2. Verify each candidate using main algorithm
3. Return result that satisfies constraint

---

### Pattern 5: Topological sort as preprocessing

**Worlds involved:** 1 (Dependency) + 4 (Optimization)

**When:** "Must process in valid order, then optimize over order"

**Examples:**
- Course scheduling with prerequisites
- Build system with dependencies
- Task scheduling

**Approach:**
1. First: Find valid topological order (World 1)
2. Then: Apply DP over valid orders (World 4)

---

### Pattern 6: Interval scheduling with constraints

**Worlds involved:** 9 (Scheduling) + 4 (Optimization)

**When:** "Select optimal intervals with complex constraints"

**Examples:**
- Select meetings to maximize attendance
- Paint houses with color constraints
- Activity selection with weights

**Approach:**
1. Sort by relevant metric
2. DP over possible selections
3. Track state through selections

---

### Pattern 7: Heap-based algorithms

**Worlds involved:** 8 (Priority) + 3 (Shortest Path)

**When:** "Use heap for efficient selection in main algorithm"

**Examples:**
- Dijkstra uses priority queue internally
- Merge K sorted lists
- Find K-th largest

**Approach:**
1. Heap maintains ordering
2. Supports efficient extract-min/max
3. Integrate into main algorithm

---

### Pattern 8: Multi-source problems

**Worlds involved:** 2 (Reachability) + 3 (Shortest Path) + 9 (Scheduling)

**When:** "Start from multiple sources simultaneously"

**Examples:**
- Multi-source BFS (rotting oranges)
- Multi-source shortest paths
- Simultaneous spread (infection)

**Approach:**
1. Initialize queue with all sources
2. Process level-by-level (BFS) or by distance (Dijkstra)
3. Natural handling of simultaneity

---

Use together: Templates reference each other for comparative analysis.

---

**Choose the right world, then pick the right variant. Problem solved!** 🚀
