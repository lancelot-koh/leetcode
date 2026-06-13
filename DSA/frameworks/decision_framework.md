# 🎯 DSA DECISION FRAMEWORK

**Master the problem-solving thought process, not just algorithms**

---

## 📖 TABLE OF CONTENTS

1. **The Core Philosophy** - What this framework teaches
2. **Universal Problem-Solving Process** - Steps before coding
3. **9 Problem Worlds** - With patterns and real-world applications
4. **Follow-up Thinking Framework** - How assumptions lead to algorithm changes
5. **Ultimate Goal** - Why this matters

---

# 💡 THE CORE PHILOSOPHY

## What This Framework Is NOT

❌ Algorithm → Problem (memorization)
❌ Pattern matching by problem name
❌ "LeetCode X means use algorithm Y"

## What This Framework IS

✅ Problem → State → Assumption → Pattern
✅ Understanding WHY algorithms change
✅ Predicting follow-ups before they're asked
✅ Connecting DSA to real-world systems

## The Key Insight

```
You're not solving 20 different problems.
You're understanding 1 problem's evolution 
across different assumption sets.

算法被降级成"Pattern"，而不是主角。
主角变成了：Problem → State → Assumption
```

When Google L6 interviews you, they're not asking:
- "What algorithm for this problem?"

They're asking:
- "Why did you choose THIS pattern?"
- "What assumption made that pattern optimal?"
- "What if assumption changes?"

---

# 🔍 UNIVERSAL PROBLEM-SOLVING PROCESS

**Before solving ANY problem, ask these 7 questions:**

1. **What is the problem asking?**
   - Reachability? Shortest path? Optimization? Ranking?
   - Identify the core question

2. **What is the state?**
   - What represents a subproblem?
   - What information must we track?

3. **What assumptions exist?**
   - What is the interviewer NOT asking?
   - What constraints are hidden?

4. **What pattern fits?**
   - Which algorithm best matches this state?
   - Why does it work?

5. **What trade-offs exist?**
   - Time vs Space?
   - Simplicity vs Optimality?
   - Online vs Offline?

6. **What if assumptions change?**
   - If interviewer says "but what if..."
   - How does algorithm shift?

7. **What does this represent in the real world?**
   - Is this social networks? Routing? Caching?
   - Does real-world context suggest optimization?

---

# 🌍 THE 9 PROBLEM WORLDS

## WORLD 1: REACHABILITY

### Core Question
```
Can I reach from A to B?
Are they connected?
Is it possible to get there?
```

### State
- Node / Vertex
- Position
- Connected component membership

### Assumption → Algorithm

| Assumption | Algorithm | Why |
|-----------|-----------|-----|
| Static graph | DFS / BFS | Simple traversal |
| Connectivity queries | Union Find | Fast lookup |
| Dynamic connectivity | Incremental Union Find | Maintain as graph changes |

### Core Algorithm: DFS for Reachability

```java
// Check if can reach target from start
public boolean canReach(List<Integer>[] graph, int start, int target) {
    Set<Integer> visited = new HashSet<>();
    return dfs(graph, start, target, visited);
}

private boolean dfs(List<Integer>[] graph, int node, int target, Set<Integer> visited) {
    if (node == target) {
        return true;
    }
    
    if (visited.contains(node)) {
        return false;
    }
    
    visited.add(node);
    
    for (int neighbor : graph[node]) {
        if (dfs(graph, neighbor, target, visited)) {
            return true;
        }
    }
    
    return false;
}
```

### Variant: Union Find for Connected Components

```java
class UnionFind {
    private Map<Integer, Integer> parent = new HashMap<>();
    private Map<Integer, Integer> rank = new HashMap<>();
    
    public int find(int x) {
        if (!parent.containsKey(x)) {
            parent.put(x, x);
            rank.put(x, 0);
        }
        
        if (parent.get(x) != x) {
            parent.put(x, find(parent.get(x)));  // Path compression
        }
        
        return parent.get(x);
    }
    
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) {
            return;  // Already connected
        }
        
        if (rank.get(rootX) < rank.get(rootY)) {
            parent.put(rootX, rootY);
        } else if (rank.get(rootX) > rank.get(rootY)) {
            parent.put(rootY, rootX);
        } else {
            parent.put(rootY, rootX);
            rank.put(rootX, rank.get(rootX) + 1);
        }
    }
    
    public boolean isConnected(int x, int y) {
        return find(x) == find(y);
    }
}

// Usage:
UnionFind uf = new UnionFind();
for (int[] edge : edges) {
    uf.union(edge[0], edge[1]);
}
if (uf.isConnected(a, b)) {
    // a and b are in same component
}
```

### Pattern Evolution Chain
```
Basic Reachability
    ↓
Connected Components (count/find all)
    ↓
Union Find (efficient queries)
    ↓
Dynamic Connectivity (edges added/removed)
```

### Real-World Applications
- Social networks (friend connections)
- Service dependencies (can service A reach database B?)
- Computer networks (is network partitioned?)
- Road networks (can I drive from city A to B?)

### Key Follow-up Patterns
- **Dynamic**: "Edges added over time?" → Dynamic union-find
- **Concurrency**: "Multiple threads?" → Thread-safe union-find
- **Output**: "Return all nodes in component?" → Collect during DFS/BFS

---

## WORLD 2: SHORTEST PATH

### Core Question
```
What is the cheapest path?
What is the shortest distance?
What is the minimum cost?
```

### State
- Node + Cost/Weight
- Distance from source

### Assumption → Algorithm

| Assumption | Algorithm | Complexity | Why |
|-----------|-----------|-----------|-----|
| All edges = 1 (unweighted) | BFS | O(V+E) | Equal cost, simple level-order |
| Weights ≥ 0 | Dijkstra | O((V+E)logV) | Greedy: always pick smallest |
| Negative weights allowed | Bellman-Ford | O(VE) | Relax edges V-1 times |
| Need all pairs | Floyd-Warshall | O(V³) | Triple nested loop |
| Limited to K edges | Bellman-Ford variant / DP | O(VE) / O(nk) | Add dimension for edge count |

### Core Algorithm: BFS for Unweighted

```java
public int[] bfsShortestPath(List<Integer>[] graph, int start, int n) {
    int[] distance = new int[n];
    Arrays.fill(distance, -1);
    distance[start] = 0;
    
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    
    while (!queue.isEmpty()) {
        int node = queue.poll();
        for (int neighbor : graph[node]) {
            if (distance[neighbor] == -1) {
                distance[neighbor] = distance[node] + 1;
                queue.offer(neighbor);
            }
        }
    }
    
    return distance;
}
```

### Variant 1: Dijkstra (with Weights ≥ 0)

```java
public int[] dijkstra(List<int[]>[] graph, int start, int n) {
    int[] distance = new int[n];
    Arrays.fill(distance, Integer.MAX_VALUE);
    distance[start] = 0;
    
    Set<Integer> visited = new HashSet<>();
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
    pq.offer(new int[]{0, start});  // {distance, node}
    
    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int currDist = curr[0];
        int node = curr[1];
        
        if (visited.contains(node)) {
            continue;  // Already processed
        }
        
        visited.add(node);
        
        for (int[] edge : graph[node]) {
            int neighbor = edge[0];
            int weight = edge[1];
            int newDist = distance[node] + weight;
            
            if (newDist < distance[neighbor]) {
                distance[neighbor] = newDist;
                pq.offer(new int[]{newDist, neighbor});
            }
        }
    }
    
    return distance;
}

// KEY DIFFERENCE from BFS:
// BFS: All edges weight = 1, use simple queue
// Dijkstra: Edges have weights, use priority queue
```

### Variant 2: Bellman-Ford (with Negative Weights)

```java
public int[] bellmanFord(int n, int[][] edges, int start) {
    int[] distance = new int[n];
    Arrays.fill(distance, Integer.MAX_VALUE);
    distance[start] = 0;
    
    // Relax edges V-1 times
    for (int i = 0; i < n - 1; i++) {
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            
            if (distance[u] != Integer.MAX_VALUE && 
                distance[u] + weight < distance[v]) {
                distance[v] = distance[u] + weight;
            }
        }
    }
    
    // Check for negative cycles
    for (int[] edge : edges) {
        int u = edge[0];
        int v = edge[1];
        int weight = edge[2];
        
        if (distance[u] != Integer.MAX_VALUE && 
            distance[u] + weight < distance[v]) {
            throw new RuntimeException("Negative cycle detected");
        }
    }
    
    return distance;
}

// KEY DIFFERENCE from Dijkstra:
// Dijkstra: Greedy, pick minimum each time (no negative)
// Bellman-Ford: Relax ALL edges V-1 times (handles negative)
```

### Pattern Evolution Chain
```
BFS (unweighted)
    ↓
Dijkstra (positive weights)
    ↓
Bellman-Ford (negative weights)
    ↓
Floyd-Warshall (all pairs)
    ↓
DP variant (with edge/resource limits)
```

### Real-World Applications
- Google Maps (find fastest route)
- Flight routing (lowest cost flights)
- Network routing (minimum latency)
- Crypto arbitrage (currency exchange paths)
- Cost optimization (minimize expenses)

### Key Follow-up Patterns
- **Cost**: "Weighted edges?" → Dijkstra
- **Constraint**: "At most K edges?" → DP or constrained Bellman-Ford
- **Size**: "1B nodes?" → Dijkstra with priority queue (sparse)
- **Output**: "Return path not distance?" → Track parent pointers

---

## WORLD 3: RANKING & TOP K

### Core Question
```
Who are the best K?
What are the largest K elements?
What are the smallest K elements?
```

### State
- Candidate set
- Priority/Score

### Assumption → Algorithm

| Assumption | Algorithm | Complexity | Why |
|-----------|-----------|-----------|-----|
| Small dataset | Sorting | O(nlogn) | Simple, cache-friendly |
| Large dataset, need top K | Min/Max Heap | O(nlogK) | Keep only K best |
| Need exact Kth element | Quick Select | O(n) average | Partition-based, no extra space |

### Core Algorithm: Heap for Top K

```java
public int findKthLargest(int[] nums, int k) {
    // Use MIN HEAP of size K
    // When heap has K elements, peek() gives Kth largest
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    
    for (int num : nums) {
        if (minHeap.size() < k) {
            minHeap.offer(num);
        } else if (num > minHeap.peek()) {
            minHeap.poll();
            minHeap.offer(num);
        }
    }
    
    return minHeap.peek();
}

// WHY MIN HEAP?
// - Keep K largest elements in heap
// - Root (min of K largest) = Kth largest
// - If new element > min, replace it
// - Time: O(n log k)
// - Space: O(k)
```

### Variant 1: Quick Select (O(n) Average)

```java
public int findKthLargestQuickSelect(int[] nums, int k) {
    // Find Kth largest element in O(n) average
    // Convert: Kth largest = (n - k)th smallest
    int targetIndex = nums.length - k;
    return select(nums, 0, nums.length - 1, targetIndex);
}

private int select(int[] nums, int left, int right, int targetIndex) {
    if (left == right) {
        return nums[left];
    }
    
    // Partition and find pivot
    int pivotIndex = partition(nums, left, right);
    
    if (targetIndex == pivotIndex) {
        return nums[pivotIndex];
    } else if (targetIndex < pivotIndex) {
        return select(nums, left, pivotIndex - 1, targetIndex);
    } else {
        return select(nums, pivotIndex + 1, right, targetIndex);
    }
}

private int partition(int[] nums, int left, int right) {
    int pivot = nums[right];
    int i = left;
    
    for (int j = left; j < right; j++) {
        if (nums[j] <= pivot) {
            swap(nums, i, j);
            i++;
        }
    }
    
    swap(nums, i, right);
    return i;
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}

// KEY DIFFERENCE from Heap:
// Heap: O(n log k), always reliable
// QuickSelect: O(n) average but O(n²) worst case
// Use QuickSelect when average case matters
```

### Variant 2: Streaming Top K (Continuous Data)

```java
public List<Integer> streamingTopK(int[] dataStream, int k) {
    List<Integer> result = new ArrayList<>();
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    
    for (int num : dataStream) {
        if (minHeap.size() < k) {
            minHeap.offer(num);
        } else if (num > minHeap.peek()) {
            minHeap.poll();
            minHeap.offer(num);
        }
        
        // Output current top K
        result.addAll(new ArrayList<>(minHeap));
    }
    
    return result;
}

// When data arrives one-by-one, maintain running top K
// Heap naturally supports this: O(1) peek, O(log k) update
```

### Pattern Evolution Chain
```
Sorting (sort everything)
    ↓
Heap (keep only top K)
    ↓
Quick Select (find Kth in O(n))
```

### Real-World Applications
- Leaderboards (top 10 players)
- Ad ranking (best ads to show)
- Recommendation systems (top movies)
- Search results (most relevant first)
- Streaming data (running top K)

### Key Follow-up Patterns
- **Size**: "1B elements?" → Min-heap of size K
- **Streaming**: "Data arrives continuously?" → Min-heap for running top K
- **Concurrency**: "Multiple threads?" → Thread-safe priority queue
- **Output**: "With scores?" → Store full elements in heap

---

## WORLD 4: RESOURCE ALLOCATION (Intervals)

### Core Question
```
How many resources do I need?
Are resources overlapping?
Can resources be merged?
```

### State
- [start, end] interval
- Overlap count
- Merged ranges

### Patterns

| Pattern | Algorithm | Use Case |
|---------|-----------|----------|
| Merge Intervals | Sort + Scan | Combine overlapping intervals |
| Insert Interval | Merge variant | Add new interval to merged set |
| Overlap Detection | Sort + Scan | Check if intervals conflict |
| Resource Count | Min Heap / Two Pointers | How many concurrent resources needed? |

### Core Algorithm: Merge Intervals

```java
public int[][] mergeIntervals(int[][] intervals) {
    if (intervals.length == 0) {
        return new int[0][0];
    }
    
    // Sort by start time
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    List<int[]> merged = new ArrayList<>();
    merged.add(intervals[0]);
    
    for (int i = 1; i < intervals.length; i++) {
        int[] current = intervals[i];
        int[] last = merged.get(merged.size() - 1);
        
        // Check if overlapping
        if (current[0] <= last[1]) {
            // Merge: extend end time
            last[1] = Math.max(last[1], current[1]);
        } else {
            // No overlap: add as new interval
            merged.add(current);
        }
    }
    
    return merged.toArray(new int[0][]);
}

// KEY INSIGHT:
// After sorting, overlapping intervals are adjacent
// Just need to check if current[0] <= last[1]
```

### Variant 1: Meeting Rooms II (Resource Count)

```java
public int minMeetingRooms(int[][] intervals) {
    if (intervals.length == 0) {
        return 0;
    }
    
    // Separate starts and ends
    int[] starts = new int[intervals.length];
    int[] ends = new int[intervals.length];
    
    for (int i = 0; i < intervals.length; i++) {
        starts[i] = intervals[i][0];
        ends[i] = intervals[i][1];
    }
    
    Arrays.sort(starts);
    Arrays.sort(ends);
    
    int roomsNeeded = 0;
    int endIdx = 0;
    
    for (int start : starts) {
        // Check if a meeting already ended
        if (endIdx < ends.length && ends[endIdx] <= start) {
            endIdx++;  // Reuse this room
        } else {
            roomsNeeded++;  // Need new room
        }
    }
    
    return roomsNeeded;
}

// KEY INSIGHT:
// Two pointers: one for starts (ascending), one for ends (ascending)
// At each start: did something end? If yes, reuse room
// Time: O(n log n) for sorting, O(n) for scanning
```

### Variant 2: Insert Interval

```java
public int[][] insertInterval(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int newStart = newInterval[0];
    int newEnd = newInterval[1];
    
    for (int[] interval : intervals) {
        int start = interval[0];
        int end = interval[1];
        
        // No overlap: interval ends before newInterval starts
        if (end < newStart) {
            result.add(interval);
        }
        // No overlap: interval starts after newInterval ends
        else if (start > newEnd) {
            result.add(new int[]{newStart, newEnd});
            newStart = start;
            newEnd = end;
        }
        // Overlap: merge
        else {
            newStart = Math.min(newStart, start);
            newEnd = Math.max(newEnd, end);
        }
    }
    
    result.add(new int[]{newStart, newEnd});
    return result.toArray(new int[0][]);
}

// Process: non-overlapping before, merge overlapping, non-overlapping after
```

### Pattern Evolution Chain
```
Merge Intervals (basic)
    ↓
Insert Interval (add one)
    ↓
Overlap Detection (check conflicts)
    ↓
Resource Counting (concurrent usage)
```

### Real-World Applications
- Calendar management (merge meeting times)
- Meeting room booking (how many rooms needed?)
- Hotel bookings (concurrent reservations)
- CPU scheduling (when is CPU busy?)
- Memory allocation (fragmentation)

### Key Follow-up Patterns
- **Constraint**: "At most K resources?" → Add constraint check
- **Dynamic**: "Intervals added/removed?" → Rebuild or update incrementally

---

## WORLD 5: DEPENDENCY

### Core Question
```
What must happen first?
What depends on what?
How do I order dependencies?
```

### State
- Node
- In-degree (dependencies count)
- Dependency graph

### Patterns

| Pattern | Algorithm | Use Case |
|---------|-----------|----------|
| Topological Sort | BFS (Kahn's) or DFS | Find valid ordering |
| Cycle Detection | DFS or Union Find | Check for circular dependency |
| Lexicographically Smallest | Topological with Priority Queue | Specific ordering |

### Core Algorithm: Kahn's Algorithm (BFS-based)

```java
public int[] topologicalSortKahn(int numNodes, int[][] edges) {
    // Build graph and in-degree count
    List<Integer>[] graph = new List[numNodes];
    int[] inDegree = new int[numNodes];
    
    for (int i = 0; i < numNodes; i++) {
        graph[i] = new ArrayList<>();
    }
    
    for (int[] edge : edges) {
        graph[edge[0]].add(edge[1]);
        inDegree[edge[1]]++;
    }
    
    // Start with nodes that have no dependencies
    Queue<Integer> queue = new LinkedList<>();
    for (int node = 0; node < numNodes; node++) {
        if (inDegree[node] == 0) {
            queue.offer(node);
        }
    }
    
    int[] result = new int[numNodes];
    int idx = 0;
    
    // Process nodes with no dependencies
    while (!queue.isEmpty()) {
        int node = queue.poll();
        result[idx++] = node;
        
        // Remove this node and update dependencies
        for (int neighbor : graph[node]) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }
    
    // Check for cycle
    if (idx != numNodes) {
        throw new RuntimeException("Cycle detected - impossible");
    }
    
    return result;
}

// KEY INSIGHT:
// - Maintain in-degree count for each node
// - Process nodes with in-degree = 0 first
// - When processing node, reduce in-degree of neighbors
// - If any node never reaches in-degree = 0, there's a cycle
```

### Variant 1: Lexicographically Smallest Order

```java
public int[] topologicalSortLex(int numNodes, int[][] edges) {
    List<Integer>[] graph = new List[numNodes];
    int[] inDegree = new int[numNodes];
    
    for (int i = 0; i < numNodes; i++) {
        graph[i] = new ArrayList<>();
    }
    
    for (int[] edge : edges) {
        graph[edge[0]].add(edge[1]);
        inDegree[edge[1]]++;
    }
    
    // Use PriorityQueue (min-heap) instead of regular queue
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    for (int node = 0; node < numNodes; node++) {
        if (inDegree[node] == 0) {
            pq.offer(node);  // Smaller nodes processed first
        }
    }
    
    int[] result = new int[numNodes];
    int idx = 0;
    
    while (!pq.isEmpty()) {
        int node = pq.poll();
        result[idx++] = node;
        
        for (int neighbor : graph[node]) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) {
                pq.offer(neighbor);
            }
        }
    }
    
    return result;
}

// KEY DIFFERENCE:
// Regular Kahn's: Any order is valid
// Lex Kahn's: Use PriorityQueue to process smaller nodes first
```

### Variant 2: DFS-based Cycle Detection

```java
public boolean hasCycleDFS(int numNodes, int[][] edges) {
    List<Integer>[] graph = new List[numNodes];
    for (int i = 0; i < numNodes; i++) {
        graph[i] = new ArrayList<>();
    }
    
    for (int[] edge : edges) {
        graph[edge[0]].add(edge[1]);
    }
    
    // States: 0=unvisited, 1=visiting, 2=visited
    int[] state = new int[numNodes];
    
    for (int node = 0; node < numNodes; node++) {
        if (state[node] == 0) {
            if (dfsCycle(node, graph, state)) {
                return true;  // Cycle found
            }
        }
    }
    
    return false;
}

private boolean dfsCycle(int node, List<Integer>[] graph, int[] state) {
    state[node] = 1;  // Mark as visiting
    
    for (int neighbor : graph[node]) {
        if (state[neighbor] == 1) {
            return true;  // Back edge = cycle
        } else if (state[neighbor] == 0) {
            if (dfsCycle(neighbor, graph, state)) {
                return true;
            }
        }
    }
    
    state[node] = 2;  // Mark as visited
    return false;
}

// KEY INSIGHT:
// Back edge (to node being visited) = cycle
// Use 3 colors: unvisited(0), visiting(1), visited(2)
```

### Pattern Evolution Chain
```
Detect valid ordering
    ↓
Topological Sort (any valid order)
    ↓
Lexicographic ordering (specific order)
    ↓
Cycle detection (is it possible?)
```

### Real-World Applications
- Build systems (compile dependencies)
- Task scheduling (execution order)
- Course prerequisites (education paths)
- Microservices (initialization order)
- Makefile dependencies

### Key Follow-up Patterns
- **Dynamic**: "Add/remove edges?" → Incremental topological sort
- **Output**: "All valid orderings?" → Backtracking + topological sort
- **Concurrency**: "Parallel build?" → Thread-safe dependency tracking

---

## WORLD 6: ENUMERATION

### Core Question
```
What are ALL possible solutions?
Generate all combinations/permutations?
```

### State
- Current path / solution
- Decisions made
- Visited set

### Patterns

| Pattern | Definition | Meaning |
|---------|-----------|---------|
| **Subset** | Include / Exclude | For each element: Take it? Skip it? |
| **Combination** | Choose K, no repeats | Choose K items, order doesn't matter |
| **Permutation** | Arrange all | All orderings, order matters |

### Key Distinction

```
Subset:    [1,2,3] → [], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]
Combination: Choose 2 → [1,2], [1,3], [2,3]  (no order)
Permutation: All 3 → [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]
```

### Core Algorithm: Subsets (Include/Exclude)

```java
public List<List<Integer>> generateSubsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, 0, new ArrayList<>(), result);
    return result;
}

private void backtrack(int[] nums, int index, List<Integer> current, 
                       List<List<Integer>> result) {
    // Base case: processed all elements
    if (index == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Choice 1: Include nums[index]
    current.add(nums[index]);
    backtrack(nums, index + 1, current, result);
    current.remove(current.size() - 1);  // Undo
    
    // Choice 2: Exclude nums[index]
    backtrack(nums, index + 1, current, result);
}

// Pattern: Include/Exclude for each element
// At each index: try taking it, then try skipping it
// Time: O(2^n) - 2 choices per element
// Space: O(n) recursion depth
```

### Variant 1: Combinations (Choose K)

```java
public List<List<Integer>> generateCombinations(int n, int k) {
    List<List<Integer>> result = new ArrayList<>();
    backtrackCombinations(1, n, k, new ArrayList<>(), result);
    return result;
}

private void backtrackCombinations(int start, int n, int k, 
                                   List<Integer> current, 
                                   List<List<Integer>> result) {
    // Base case: chosen K elements
    if (current.size() == k) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Try choosing each element from start to n
    for (int i = start; i <= n; i++) {
        current.add(i);
        // Only try future elements (no duplicates)
        backtrackCombinations(i + 1, n, k, current, result);
        current.remove(current.size() - 1);
    }
}

// KEY INSIGHT: Use START index to avoid duplicates
// If we have [1,2,3] and want combinations of 2:
// - From 1: try (1,2), (1,3)
// - From 2: try (2,3)
// - From 3: nothing left
// Never try (2,1) because we started from 2, which is > 1
```

### Variant 2: Permutations (Order Matters)

```java
public List<List<Integer>> generatePermutations(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    boolean[] used = new boolean[nums.length];
    backtrackPermutations(nums, new ArrayList<>(), used, result);
    return result;
}

private void backtrackPermutations(int[] nums, List<Integer> current, 
                                   boolean[] used, 
                                   List<List<Integer>> result) {
    // Base case: all elements used
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    
    // Try each unused element
    for (int i = 0; i < nums.length; i++) {
        if (!used[i]) {
            current.add(nums[i]);
            used[i] = true;
            backtrackPermutations(nums, current, used, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
}

// KEY INSIGHT: Use USED array to track which elements taken
// For each position, try any unused element
// Need to try same element at different positions
// Time: O(n!) - n choices for position 1, n-1 for position 2, etc
```

### Pattern Evolution Chain
```
Subsets (include/exclude)
    ↓
Combinations (choose K)
    ↓
Permutations (order matters)
    ↓
Handling Duplicates
```

### Real-World Applications
- Feature selection (which features to enable?)
- Team formation (select people for project)
- Scheduling (arrange people/tasks)
- Route planning (all possible routes)

### Key Follow-up Patterns
- **Constraint**: "No duplicate results?" → Sort and skip duplicates
- **Output**: "Return count not list?" → Return count instead of collecting

---

## WORLD 7: OPTIMIZATION (Dynamic Programming)

### Core Question
```
What is the BEST answer?
Maximum? Minimum? Number of ways?
```

### State
- Index / Position
- Capacity / Amount
- Current state

### Patterns & Assumption → Algorithm

| Assumption | Pattern | Key Insight | Loop Order |
|-----------|---------|-------------|-----------|
| One item per slot | Linear DP | dp[i] = best choice at i | Forward only |
| Use each item at most once | 0/1 Knapsack | Can't reuse | Backward (capacity) |
| Can reuse items | Unbounded Knapsack | Can reuse | Forward (capacity) |
| Need 2 dimensions | 2D DP | String matching / Grid | Both dimensions |
| Multiple states | State Machine DP | Hold/Sell/Cooldown | State dimension |

### Core Algorithm: Linear DP (House Robber)

```java
public int maxRob(int[] houses) {
    if (houses.length == 0) {
        return 0;
    }
    
    int n = houses.length;
    int[] dp = new int[n];
    
    // Base case
    dp[0] = houses[0];
    
    // Transition: at each house, choose rob or skip
    for (int i = 1; i < n; i++) {
        // Option 1: Rob this house + best from i-2
        int robCurrent = houses[i] + (i >= 2 ? dp[i - 2] : 0);
        
        // Option 2: Skip this house, best from i-1
        int skipCurrent = dp[i - 1];
        
        dp[i] = Math.max(robCurrent, skipCurrent);
    }
    
    return dp[n - 1];
}

// KEY: dp[i] depends only on dp[i-1] and dp[i-2]
// Pattern: at each position, compare two choices and pick best
```

### Variant 1: 0/1 Knapsack (Use Each Item Once)

```java
public int knapsack01(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[] dp = new int[capacity + 1];
    
    // Process each item
    for (int i = 0; i < n; i++) {
        int weight = weights[i];
        int value = values[i];
        
        // BACKWARD loop - critical!
        for (int w = capacity; w >= weight; w--) {
            // Option 1: Don't take item i
            // (dp[w] remains unchanged)
            
            // Option 2: Take item i
            // (need dp[w - weight] from PREVIOUS iteration)
            dp[w] = Math.max(dp[w], dp[w - weight] + value);
        }
    }
    
    return dp[capacity];
}

// WHY BACKWARD?
// - We want dp[w - weight] from iteration i-1
// - If we go forward, dp[w - weight] = current iteration (wrong!)
// - Going backward ensures we use previous state
```

### Variant 2: Unbounded Knapsack (Reuse Items)

```java
public int knapsackUnbounded(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[] dp = new int[capacity + 1];
    
    for (int i = 0; i < n; i++) {
        int weight = weights[i];
        int value = values[i];
        
        // FORWARD loop - different from 0/1!
        for (int w = weight; w <= capacity; w++) {
            // Can use same item multiple times
            // We want dp[w - weight] from CURRENT iteration
            dp[w] = Math.max(dp[w], dp[w - weight] + value);
        }
    }
    
    return dp[capacity];
}

// WHY FORWARD?
// - We want dp[w - weight] to be SAME iteration (can reuse)
// - Going forward means dp[w - weight] is already updated with current item
// - This allows using same item multiple times
```

### Variant 3: 2D DP (Edit Distance)

```java
public int editDistance(String s1, String s2) {
    int m = s1.length();
    int n = s2.length();
    int[][] dp = new int[m + 1][n + 1];
    
    // Base cases
    for (int i = 0; i <= m; i++) {
        dp[i][0] = i;  // Delete all from s1
    }
    for (int j = 0; j <= n; j++) {
        dp[0][j] = j;  // Insert all to get s2
    }
    
    // Fill table
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1];  // No operation needed
            } else {
                int insert = dp[i][j - 1] + 1;
                int delete = dp[i - 1][j] + 1;
                int replace = dp[i - 1][j - 1] + 1;
                dp[i][j] = Math.min(Math.min(insert, delete), replace);
            }
        }
    }
    
    return dp[m][n];
}

// KEY: Dimension for each sequence
// Transition: 3 options (insert, delete, replace)
```

### Variant 4: State Machine DP (Stock with Cooldown)

```java
public int maxProfitCooldown(int[] prices) {
    if (prices.length < 2) {
        return 0;
    }
    
    int n = prices.length;
    int[] hold = new int[n];
    int[] sold = new int[n];
    int[] cooldown = new int[n];
    
    hold[0] = -prices[0];  // Buy on day 0
    
    for (int i = 1; i < n; i++) {
        hold[i] = Math.max(hold[i - 1], cooldown[i - 1] - prices[i]);
        sold[i] = hold[i - 1] + prices[i];
        cooldown[i] = Math.max(cooldown[i - 1], sold[i - 1]);
    }
    
    return Math.max(sold[n - 1], cooldown[n - 1]);
}

// KEY: Extra dimension for states
// Transition between states matters, not just choices
// hold → sold → cooldown → hold (not allowed)
```

### Pattern Evolution Chain
```
Linear DP (simple sequence)
    ↓
0/1 Knapsack (use once)
    ↓
Unbounded Knapsack (reuse)
    ↓
2D DP (sequence comparison)
    ↓
State Machine DP (multiple states)
    ↓
Advanced DP (complex dependencies)
```

### 0/1 vs Unbounded Knapsack - WHY the difference?

```
0/1 Knapsack (backward capacity loop):
  for item in items:
    for capacity from C down to item.weight:  // BACKWARD
      dp[capacity] = max(dp[capacity], dp[capacity - weight] + value)
      
Why backward? 
Because dp[capacity - weight] is from PREVIOUS item iteration.
Using backward prevents reusing current item.

Unbounded Knapsack (forward capacity loop):
  for item in items:
    for capacity from item.weight to C:  // FORWARD
      dp[capacity] = max(dp[capacity], dp[capacity - weight] + value)

Why forward?
Because dp[capacity - weight] might be CURRENT item iteration.
Using forward allows reusing current item.
```

### Real-World Applications
- Budget planning (allocate limited budget)
- Inventory optimization (stock levels)
- Investment allocation (distribute capital)
- Capacity planning (maximize usage)
- Stock trading (buy/sell decisions)

### Key Follow-up Patterns
- **Constraint**: "Can't use more than K?" → Add constraint dimension
- **Memory**: "Optimize space?" → Rolling array (O(1) to O(n))
- **Output**: "Which items chosen?" → Backtrack through DP table

---

## WORLD 8: SEARCH SPACE EXPLORATION

### Core Question
```
Find a solution inside a HUGE search space.
Need to try possibilities, but space is too large.
```

### Patterns

| Pattern | Approach | Use Case |
|---------|----------|----------|
| **Backtracking** | Try → Undo → Try Again | Exhaustive search with pruning |
| **Branch and Bound** | Prune impossible states | Cut branches that can't beat best |

### Core Algorithm: Backtracking (N-Queens)

```java
public List<List<String>> solveNQueens(int n) {
    List<List<String>> result = new ArrayList<>();
    List<int[]> board = new ArrayList<>();
    backtrackQueens(board, 0, n, result);
    return result;
}

private void backtrackQueens(List<int[]> board, int row, int n, 
                             List<List<String>> result) {
    // Base case: placed all queens
    if (row == n) {
        result.add(buildBoard(board, n));
        return;
    }
    
    // Try placing queen in each column
    for (int col = 0; col < n; col++) {
        if (isSafe(board, row, col)) {
            board.add(new int[]{row, col});  // CHOOSE
            backtrackQueens(board, row + 1, n, result);  // EXPLORE
            board.remove(board.size() - 1);  // UNDO
        }
    }
}

private boolean isSafe(List<int[]> board, int row, int col) {
    // Check if placing queen at (row, col) is safe
    for (int[] queen : board) {
        int r = queen[0];
        int c = queen[1];
        if (c == col) {  // Same column
            return false;
        }
        if (Math.abs(r - row) == Math.abs(c - col)) {  // Same diagonal
            return false;
        }
    }
    return true;
}

private List<String> buildBoard(List<int[]> board, int n) {
    List<String> lines = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < n; j++) {
            sb.append('.');
        }
        lines.add(sb.toString());
    }
    
    for (int[] queen : board) {
        int r = queen[0];
        int c = queen[1];
        String line = lines.get(r);
        lines.set(r, line.substring(0, c) + 'Q' + line.substring(c + 1));
    }
    
    return lines;
}

// KEY: Try-Explore-Undo pattern
// Pruning: isSafe() cuts invalid branches early
// Time: O(n!) worst case, but pruning reduces significantly
```

### Variant: Sudoku Solver with Backtracking

```java
public void solveSudoku(char[][] board) {
    solve(board, 0);
}

private boolean solve(char[][] board, int pos) {
    if (pos == 81) {
        return true;  // All cells filled
    }
    
    int row = pos / 9;
    int col = pos % 9;
    
    // If cell already filled, move to next
    if (board[row][col] != '.') {
        return solve(board, pos + 1);
    }
    
    // Try each digit 1-9
    for (char digit = '1'; digit <= '9'; digit++) {
        if (isValidSudoku(board, row, col, digit)) {
            board[row][col] = digit;  // CHOOSE
            
            if (solve(board, pos + 1)) {  // EXPLORE
                return true;
            }
            
            board[row][col] = '.';  // UNDO
        }
    }
    
    return false;
}

private boolean isValidSudoku(char[][] board, int row, int col, char digit) {
    // Check row
    for (int c = 0; c < 9; c++) {
        if (board[row][c] == digit) {
            return false;
        }
    }
    
    // Check column
    for (int r = 0; r < 9; r++) {
        if (board[r][col] == digit) {
            return false;
        }
    }
    
    // Check 3x3 box
    int boxRow = (row / 3) * 3;
    int boxCol = (col / 3) * 3;
    for (int r = boxRow; r < boxRow + 3; r++) {
        for (int c = boxCol; c < boxCol + 3; c++) {
            if (board[r][c] == digit) {
                return false;
            }
        }
    }
    
    return true;
}

// Backtracking is natural for constraint satisfaction
// Each placement has constraints to check before proceeding
```

### Real-World Applications
- Sudoku solving
- N-Queens problem
- Configuration search
- Constraint satisfaction problems

### Key Follow-up Patterns
- **Pruning**: "Speed up?" → Add better pruning conditions
- **Space**: "Memory limit?" → Iterative backtracking

---

## WORLD 9: AGGREGATION

### Core Question
```
How many total?
How much cumulative?
What's the aggregate result?
```

### Patterns

| Pattern | Use Case | Benefit |
|---------|----------|---------|
| **Prefix Sum** | Range sum queries | O(1) after O(n) preprocessing |
| **Difference Array** | Range updates | O(1) updates, reconstruct O(n) |
| **Running Sum** | Cumulative total | Track as we scan |
| **Sweep Line** | Overlapping ranges | Process events efficiently |

### Core Algorithm: Prefix Sum (Range Sum Query)

```java
class PrefixSum {
    private int[] prefix;
    
    public PrefixSum(int[] nums) {
        int n = nums.length;
        prefix = new int[n + 1];
        
        // Build prefix sum array
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }
    
    public int rangeSum(int left, int right) {
        // Sum of nums[left..right] inclusive
        return prefix[right + 1] - prefix[left];
    }
}

// KEY: prefix[i] = sum of nums[0..i-1]
// Query range sum in O(1) after O(n) preprocessing
// Example: nums = [1,2,3,4,5]
//          prefix = [0,1,3,6,10,15]
//          sum(1..3) = prefix[4] - prefix[1] = 10 - 1 = 9
//                    = 2 + 3 + 4 = 9 ✓
```

### Variant 1: Difference Array (Range Updates)

```java
public int[] rangeUpdate(int[] nums, int[][] updates) {
    int n = nums.length;
    int[] diff = new int[n + 1];
    
    // Apply each update
    for (int[] update : updates) {
        int left = update[0];
        int right = update[1];
        int delta = update[2];
        
        diff[left] += delta;
        diff[right + 1] -= delta;
    }
    
    // Reconstruct original array
    int[] result = new int[n];
    int current = 0;
    for (int i = 0; i < n; i++) {
        current += diff[i];
        result[i] = nums[i] + current;
    }
    
    return result;
}

// KEY: Difference array allows O(1) range updates
// Batch all updates, reconstruct in O(n) at the end
// Efficient when many updates, few queries
```

### Variant 2: 2D Prefix Sum (Area Queries)

```java
class PrefixSum2D {
    private int[][] prefix;
    
    public PrefixSum2D(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        prefix = new int[m + 1][n + 1];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefix[i + 1][j + 1] = matrix[i][j] + 
                                       prefix[i][j + 1] + 
                                       prefix[i + 1][j] - 
                                       prefix[i][j];
            }
        }
    }
    
    public int sumRegion(int r1, int c1, int r2, int c2) {
        // Sum of matrix[r1..r2][c1..c2]
        return prefix[r2 + 1][c2 + 1] - 
               prefix[r1][c2 + 1] - 
               prefix[r2 + 1][c1] + 
               prefix[r1][c1];
    }
}

// 2D inclusion-exclusion:
// Total - left_exclude - top_exclude + overlap_counted_twice
```

### Variant 3: Sweep Line (Events Processing)

```java
public int[][] skylineProblem(int[][] buildings) {
    List<int[]> events = new ArrayList<>();
    
    // Create start and end events
    for (int[] building : buildings) {
        int left = building[0];
        int right = building[1];
        int height = building[2];
        
        events.add(new int[]{left, -height});  // Start event (negative for sorting)
        events.add(new int[]{right, height});   // End event
    }
    
    // Sort events by position, then by type (start before end)
    Collections.sort(events, (a, b) -> {
        if (a[0] != b[0]) {
            return a[0] - b[0];
        }
        return a[1] - b[1];
    });
    
    List<int[]> result = new ArrayList<>();
    Map<Integer, Integer> heightCount = new HashMap<>();
    int prevMaxHeight = 0;
    
    for (int[] event : events) {
        int pos = event[0];
        int height = Math.abs(event[1]);
        
        if (event[1] < 0) {  // Start event
            heightCount.put(height, heightCount.getOrDefault(height, 0) + 1);
        } else {  // End event
            heightCount.put(height, heightCount.get(height) - 1);
            if (heightCount.get(height) == 0) {
                heightCount.remove(height);
            }
        }
        
        // Current max height from active buildings
        int currMaxHeight = heightCount.isEmpty() ? 0 : 
                           Collections.max(heightCount.keySet());
        
        // Height changed, add to result
        if (currMaxHeight != prevMaxHeight) {
            result.add(new int[]{pos, currMaxHeight});
            prevMaxHeight = currMaxHeight;
        }
    }
    
    return result.toArray(new int[0][]);
}

// KEY: Sweep line processes events in order
// Maintains active elements, outputs when state changes
```

### Real-World Applications
- Analytics dashboards
- Metrics and monitoring
- Traffic analysis
- Cumulative statistics

### Key Follow-up Patterns
- **Query**: "Range sum?" → Prefix sum
- **Update**: "Update ranges?" → Difference array

---

# 🔄 FOLLOW-UP THINKING FRAMEWORK

## The Master Pattern

```
Assumption 1 + Algorithm 1
    ↓
Assumption Changes
    ↓
Algorithm Must Change
```

## Real Examples (Problem Evolution Chains)

### Example 1: Shortest Path

```
BFS (unweighted, O(V+E))
    ↓
"What if edges have weights?"
    ↓
CHANGE: Cost structure changes
NEW ASSUMPTION: Weights might be different
NEW ALGORITHM: Dijkstra (O((V+E)logV))
    ↓
"What if negative weights exist?"
    ↓
CHANGE: Greedy no longer optimal
NEW ASSUMPTION: Negative edges possible
NEW ALGORITHM: Bellman-Ford (O(VE))
```

### Example 2: Knapsack

```
0/1 Knapsack: Use each item at most once
    ↓
"What if items can be reused?"
    ↓
CHANGE: Item reusability
NEW ASSUMPTION: Unlimited quantity
NEW ALGORITHM: Unbounded Knapsack (backward → forward loop)
    ↓
"What if we have a count limit on each?"
    ↓
CHANGE: Quantity constraints
NEW ASSUMPTION: K copies max
NEW ALGORITHM: Bounded Knapsack variant
```

### Example 3: Top K

```
Sorting: Get all Ks
    ↓
"What if K << N and N is huge?"
    ↓
CHANGE: Data size relative to K
NEW ASSUMPTION: Only top K matters
NEW ALGORITHM: Heap (O(nlogK) vs O(nlogn))
    ↓
"What if we need exact Kth element in O(n)?"
    ↓
CHANGE: Time requirement
NEW ASSUMPTION: Average O(n) acceptable
NEW ALGORITHM: Quick Select
```

### Example 4: Ranking & Scheduling

```
Sorting (simple case)
    ↓
"Data arrives continuously?"
    ↓
CHANGE: Data availability
NEW ASSUMPTION: Online processing
NEW ALGORITHM: Running top K with heap
    ↓
"Multiple concurrent requests?"
    ↓
CHANGE: Concurrency requirement
NEW ASSUMPTION: Thread safety needed
NEW ALGORITHM: Thread-safe PriorityQueue or locks
```

---

## How to Apply This in Interviews

**When interviewer asks "What if...":**

1. **Identify the change**
   ```
   "I see - that's a change in [assumption type]."
   E.g., "change in cost structure" or "change in data availability"
   ```

2. **Locate the bottleneck**
   ```
   "This change means [current algorithm] no longer works for reason X."
   ```

3. **Propose the new algorithm**
   ```
   "So we'd switch to [new algorithm] which gives us [complexity]."
   ```

4. **Show you anticipated it**
   ```
   "This is why [original algorithm] was optimal for original assumptions."
   ```

---

# 🎯 ULTIMATE GOAL

## What NOT to Learn

❌ LeetCode 207 → Topological Sort
❌ LeetCode 1 → Two Sum
❌ Problem name → Algorithm name

## What TO Learn

✅ **Problem** → What is it asking?
✅ **State** → What do we track?
✅ **Assumption** → What's implicit?
✅ **Pattern** → Which algorithm?
✅ **Trade-off** → What are the costs?
✅ **Follow-up** → How does it evolve?
✅ **Real-World** → Where does this apply?

## Why This Matters

This framework is foundational for:

1. **LeetCode** - Solve ANY problem systematically
2. **Google Interviews** - L6 level thinking
3. **System Design** - Same thinking: tradeoffs, assumptions, evolution
4. **Real Engineering** - Production systems evolve with changing assumptions

## The Mental Model Shift

```
Before: Memorization
"LC 207 = Topological Sort"
"LC 300 = DP"
"LC 215 = Heap"

After: Understanding
"Problem → State → Assumption → Pattern"
"When assumptions change, pattern changes"
"I can handle ANY variation"
```

## Your Superpower

When interviewer says "What if...", you don't panic.

You:
1. Identify which ASSUMPTION changed
2. Explain why it affects the algorithm
3. Propose the new algorithm confidently
4. Show you ANTICIPATED this possibility

This is how L6 engineers think.

---

**Master this framework. Master any interview. Master DSA.** 🚀
