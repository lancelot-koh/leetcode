# 📊 TOPOLOGICAL SORT - Complete Dependency Resolution Guide

**Master graph traversal for problems with prerequisites, dependencies, and ordering constraints**

---

## 📖 TABLE OF CONTENTS

1. **Core Concept & Mental Model**
2. **Complete Problem Series (1-15)**
3. **Two Algorithms Explained**
4. **Interview Strategy**
5. **When to Use What**

---

# 🧠 CORE CONCEPT & MENTAL MODEL

## The Problem World

```
Topological Sort lives in WORLD 5: DEPENDENCY RESOLUTION
Core Question: "In what order should we process items 
                given their prerequisites?"

Real-world examples:
- Course prerequisites: must take Math 101 before Calculus
- Build systems: must compile A before linking with B
- Task scheduling: task X depends on task Y completion
- Package managers: must install dependencies first
- Recipe steps: must prep ingredients before cooking
```

## Key Insight

```
Regular Sort: Linear order (1 < 2 < 3)

Topological Sort: Partial order based on dependencies
    Example: [CS 101, CS 102, CS 201]
    CS 101 must come before CS 102 (prerequisite)
    CS 102 must come before CS 201 (prerequisite)
    Valid topological orders:
    - [CS 101, CS 102, CS 201]
    - Can't do [CS 102, CS 101, CS 201] (violates dependency)

Existence: Topological sort exists IFF no cycles exist
    (Can't have: A→B→C→A)
```

---

# 📋 COMPLETE PROBLEM SERIES

## Algorithm 1: KAHN'S ALGORITHM (Indegree-based BFS)

### Thinking Process

```
Step 1: UNDERSTAND INDEGREE
In a directed graph, a node's indegree = number of incoming edges
Example:
  CS101 → CS102: CS102.indegree = 1 (CS101 must come first)
  CS102 → CS201: CS201.indegree = 1 (CS102 must come first)
  
Key insight: Node with indegree 0 = no prerequisites = can execute now

Step 2: THE IDEA
"Process nodes in order of their dependencies"
- Nodes with indegree 0 have no blockers
- After processing a node, its dependent's indegree decreases
- When a dependent's indegree hits 0, it becomes ready
- Repeat until all processed

Step 3: WHY IT'S LIKE BFS
- We process "frontier" of ready nodes
- Each ready node enables other nodes
- Similar to level-order traversal

Step 4: ALGORITHM STEPS
1. Count indegree for each node
2. Add all nodes with indegree 0 to queue
3. While queue not empty:
   - Dequeue node (this goes in topological order)
   - For each outgoing edge to neighbor:
     - Decrease neighbor's indegree
     - If neighbor's indegree becomes 0, enqueue it
4. If all nodes processed: valid topo sort exists
   If not all processed: cycle detected (no sort possible)
```

### Template Implementation

```java
public List<Integer> topologicalSortKahn(int n, int[][] edges) {
    // Step 1: Build graph and calculate indegrees
    List<List<Integer>> graph = new ArrayList<>();
    int[] indegree = new int[n];
    
    for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
    }
    
    for (int[] edge : edges) {
        int from = edge[0];
        int to = edge[1];
        graph.get(from).add(to);  // from → to
        indegree[to]++;            // to has one more prerequisite
    }
    
    // Step 2: Find all nodes with no prerequisites
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) {
        if (indegree[i] == 0) {
            queue.offer(i);  // Can process now
        }
    }
    
    // Step 3: Process nodes in topological order
    List<Integer> result = new ArrayList<>();
    
    while (!queue.isEmpty()) {
        int node = queue.poll();
        result.add(node);
        
        // This node is done, so its dependents become closer to ready
        for (int neighbor : graph.get(node)) {
            indegree[neighbor]--;
            
            // If all prerequisites met, this node is now ready
            if (indegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }
    
    // Step 4: Check if valid topological sort exists
    if (result.size() != n) {
        // Some nodes not processed = cycle exists
        return new ArrayList<>();  // or throw exception
    }
    
    return result;
}
```

**Why Kahn's Works:**
- Processes nodes with no dependencies first (correct start)
- Only processes node when all prerequisites done (respects dependencies)
- Queue ensures we find a valid order if one exists
- O(V + E) time, O(V) space

**When to Use Kahn's:**
- Need to detect cycles (use if statement)
- Need lexicographically smallest order (use min-heap instead of queue)
- Problem naturally frames as "prerequisites"

---

## Algorithm 2: DFS-BASED TOPOLOGICAL SORT

### Thinking Process

```
Step 1: DFS BASICS
DFS: Explore deep into graph, backtrack when stuck
Post-order: Process node AFTER visiting all descendants
           (opposite of pre-order: process before visiting)

Step 2: WHY POST-ORDER?
In topological sort, we want nodes with NO dependents to appear first
After visiting all of a node's descendants in DFS:
- All nodes dependent on it have been processed
- So it's safe to add it to result NOW

Think of it backwards: nodes with no outgoing edges come first

Step 3: ALGORITHM STEPS
1. For each unvisited node:
   - DFS from that node
   - In DFS: visit neighbors first, then add current to result
2. After DFS completes, reverse the result
   (Because we added nodes in reverse topological order)

Step 4: CYCLE DETECTION
While DFS:
- WHITE (unvisited): haven't started
- GRAY (visiting): currently processing this node
- BLACK (visited): finished with this node

If we encounter GRAY node during DFS: CYCLE EXISTS
(We're visiting an ancestor again = cycle)
```

### Template Implementation

```java
public List<Integer> topologicalSortDFS(int n, int[][] edges) {
    // Step 1: Build adjacency list
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
    }
    
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
    }
    
    // Step 2: Track states for cycle detection
    int[] state = new int[n];  // 0: white, 1: gray, 2: black
    List<Integer> result = new ArrayList<>();
    boolean hasCycle = false;
    
    // Step 3: DFS from each unvisited node
    for (int i = 0; i < n; i++) {
        if (state[i] == 0) {  // Unvisited
            dfs(i, graph, state, result);
        }
    }
    
    // Step 4: Reverse result (was added in reverse order)
    Collections.reverse(result);
    
    return result;
}

private void dfs(int node, List<List<Integer>> graph, 
                 int[] state, List<Integer> result) {
    state[node] = 1;  // Mark as visiting (gray)
    
    for (int neighbor : graph.get(node)) {
        if (state[neighbor] == 1) {
            // Back edge: visiting an ancestor = CYCLE
            throw new RuntimeException("Cycle detected");
        } else if (state[neighbor] == 0) {
            // Unvisited: explore
            dfs(neighbor, graph, state, result);
        }
        // If state[neighbor] == 2 (black): already fully processed, skip
    }
    
    state[node] = 2;  // Mark as visited (black)
    result.add(node);  // Add AFTER visiting all neighbors (post-order)
}
```

**Why DFS Works:**
- Post-order ensures dependencies are processed first
- Naturally detects cycles (back edges in DFS)
- O(V + E) time, O(V) space (for recursion stack)

**When to Use DFS:**
- Cycle detection is important
- Prefer recursive solution
- Natural to think recursively about dependencies

---

## PROBLEM 1: Course Schedule I (LeetCode 207)

**Problem:** Can complete all courses given prerequisites?

#### Thinking Process

```
Step 1: REFRAME
"Can I complete all courses?" = "Is there a valid order?"
                              = "Does topological sort exist?"

Step 2: DETECT CYCLE, NOT FIND ORDER
We don't need the actual order, just YES/NO.
Cycle exists = can't complete (would be stuck in loop)
No cycle = can complete

Step 3: CHOOSE ALGORITHM
Kahn's: Easy to check result.size() == n
DFS: Easy to throw exception on back edge

Either works, Kahn's simpler for YES/NO
```

#### Code Implementation

```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    // Build graph: prerequisites[i] = [course, prerequisite]
    List<List<Integer>> graph = new ArrayList<>();
    int[] indegree = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) {
        graph.add(new ArrayList<>());
    }
    
    // Build edges: prerequisite → course
    for (int[] prereq : prerequisites) {
        int course = prereq[0];
        int prerequisite = prereq[1];
        
        graph.get(prerequisite).add(course);
        indegree[course]++;
    }
    
    // Kahn's algorithm
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (indegree[i] == 0) {
            queue.offer(i);
        }
    }
    
    int count = 0;
    while (!queue.isEmpty()) {
        int course = queue.poll();
        count++;
        
        for (int nextCourse : graph.get(course)) {
            indegree[nextCourse]--;
            if (indegree[nextCourse] == 0) {
                queue.offer(nextCourse);
            }
        }
    }
    
    // If all courses processed: no cycle, can finish
    return count == numCourses;
}
```

**Key Insight:**
- count tracks how many courses we could complete
- If count == numCourses, all courses are reachable (no cycle)
- If count < numCourses, some courses stuck (cycle exists)

---

## PROBLEM 2: Course Schedule II (LeetCode 210)

**Problem:** Return valid course order (or empty if impossible)

#### Thinking Process

```
Step 1: SIMILAR TO PROBLEM 1
But now we need the actual order, not just YES/NO

Step 2: KAHN'S ALREADY GIVES US ORDER!
The order we add nodes to result = valid topological order
Just need to check if all nodes were included
```

#### Code Implementation

```java
public int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] indegree = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) {
        graph.add(new ArrayList<>());
    }
    
    for (int[] prereq : prerequisites) {
        int course = prereq[0];
        int prerequisite = prereq[1];
        graph.get(prerequisite).add(course);
        indegree[course]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (indegree[i] == 0) {
            queue.offer(i);
        }
    }
    
    int[] result = new int[numCourses];
    int idx = 0;
    
    while (!queue.isEmpty()) {
        int course = queue.poll();
        result[idx++] = course;
        
        for (int nextCourse : graph.get(course)) {
            indegree[nextCourse]--;
            if (indegree[nextCourse] == 0) {
                queue.offer(nextCourse);
            }
        }
    }
    
    // If not all courses included: cycle, return empty
    return idx == numCourses ? result : new int[0];
}
```

---

## PROBLEM 3: Parallel Courses (LeetCode 1136)

**Problem:** Minimum semesters to complete all courses?

#### Thinking Process

```
Step 1: DIFFERENT QUESTION
Problem 1-2: "Can we?" and "In what order?"
Problem 3: "What's the MINIMUM TIME?"

Step 2: LEVEL/DEPTH TRACKING
Semester 1: All courses with no prerequisites
Semester 2: Courses whose prerequisites finished in Semester 1
Semester 3: Courses whose prerequisites finished in Semester 2
...

This is LEVEL-based BFS!
Level = semester number
Minimum semesters = maximum depth in the graph

Step 3: ALGORITHM
Track the "level" each course finishes at
Course's level = 1 + max(level of prerequisites)
Answer = max level of any course
```

#### Code Implementation

```java
public int minimumTime(int n, int[][] relations) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] indegree = new int[n + 1];
    int[] time = new int[n + 1];  // When each course can be taken
    
    for (int i = 0; i <= n; i++) {
        graph.add(new ArrayList<>());
    }
    
    for (int[] relation : relations) {
        int from = relation[0];
        int to = relation[1];
        graph.get(from).add(to);
        indegree[to]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 1; i <= n; i++) {
        if (indegree[i] == 0) {
            queue.offer(i);
            time[i] = 1;  // Can take in semester 1
        }
    }
    
    int maxSemester = 0;
    while (!queue.isEmpty()) {
        int course = queue.poll();
        maxSemester = Math.max(maxSemester, time[course]);
        
        for (int nextCourse : graph.get(course)) {
            // Next course can be taken after this one
            time[nextCourse] = Math.max(time[nextCourse], time[course] + 1);
            
            indegree[nextCourse]--;
            if (indegree[nextCourse] == 0) {
                queue.offer(nextCourse);
            }
        }
    }
    
    return maxSemester;
}
```

**Key Insight:**
- time[i] = earliest semester course i can be taken
- We calculate this as we process in topological order
- Answer = maximum time value

---

## PROBLEM 4: Alien Dictionary (LeetCode 269)

**Problem:** Given sorted words in alien language, derive character order

#### Thinking Process

```
Step 1: EXTRACT DEPENDENCIES FROM WORDS
Compare consecutive words to find which character comes first
Example: ["wrt", "wrf", "er", "ett", "rftt"]
- "wrt" vs "wrf": at position 1, 't' comes before 'f'
- "wrf" vs "er": at position 0, 'w' comes before 'e'
- ...

Step 2: BUILD GRAPH
Each dependency = directed edge
Character with indegree 0 appears first in alien alphabet

Step 3: TOPOLOGICAL SORT
The order we process characters = alien alphabet order
```

#### Code Implementation

```java
public String alienOrder(String[] words) {
    // Step 1: Build graph from word comparisons
    Map<Character, Set<Integer>> graph = new HashMap<>();
    Map<Character, Integer> indegree = new HashMap<>();
    
    // Initialize all characters
    for (String word : words) {
        for (char c : word.toCharArray()) {
            indegree.putIfAbsent(c, 0);
            graph.putIfAbsent(c, new HashSet<>());
        }
    }
    
    // Extract dependencies
    for (int i = 0; i < words.length - 1; i++) {
        String w1 = words[i];
        String w2 = words[i + 1];
        
        // Find first difference
        int minLen = Math.min(w1.length(), w2.length());
        for (int j = 0; j < minLen; j++) {
            if (w1.charAt(j) != w2.charAt(j)) {
                char c1 = w1.charAt(j);
                char c2 = w2.charAt(j);
                
                // c1 comes before c2
                if (!graph.get(c1).contains(c2)) {
                    graph.get(c1).add((int)c2);
                    indegree.put(c2, indegree.get(c2) + 1);
                }
                break;
            }
        }
    }
    
    // Step 2: Topological sort
    Queue<Character> queue = new LinkedList<>();
    for (char c : indegree.keySet()) {
        if (indegree.get(c) == 0) {
            queue.offer(c);
        }
    }
    
    StringBuilder result = new StringBuilder();
    while (!queue.isEmpty()) {
        char c = queue.poll();
        result.append(c);
        
        for (int neighbor : graph.get(c)) {
            char next = (char)neighbor;
            indegree.put(next, indegree.get(next) - 1);
            if (indegree.get(next) == 0) {
                queue.offer(next);
            }
        }
    }
    
    // Check if valid (all characters included)
    return result.length() == indegree.size() ? result.toString() : "";
}
```

---

## PROBLEM 5: Build Order (Cracking the Coding Interview)

**Problem:** Order projects by dependencies

#### Thinking Process

```
Same as topological sort, but for projects instead of courses
Key: Projects can depend on other projects
Solution: Kahn's algorithm on project graph
```

---

# 🎯 PATTERN RECOGNITION

## When to Use Topological Sort

```
See these keywords:
- "Prerequisites"
- "Dependencies"
- "Build order"
- "Course schedule"
- "Task ordering"
- "Sequence"

Ask yourself: "Is there a partial order based on dependencies?"
If YES → Topological Sort
```

## Choosing Between Kahn's and DFS

| Aspect | Kahn's | DFS |
|--------|--------|-----|
| **Cycle Detection** | Check result size | Detect back edges |
| **Order** | Natural from algorithm | Need to reverse |
| **Code Style** | Iterative, easier | Recursive |
| **Finding Cycle** | Hard (just know it exists) | Easy (throw exception) |

---

# 💡 KEY INSIGHTS

### Insight 1: Indegree = Blocked By
```
Node A has indegree 3:
- 3 other nodes must finish before A can start
- When any prerequisite finishes, indegree decreases
- When indegree hits 0: all prerequisites done, A can start
```

### Insight 2: Post-Order in DFS
```
Why reverse the result in DFS?
- DFS adds nodes AFTER visiting descendants
- This means nodes with NO outgoing edges added first
- We want nodes with NO INCOMING edges first
- Reversing fixes this
```

### Insight 3: Cycle Detection
```
Kahn's: If we can't process all nodes, cycle exists
DFS: If we revisit a gray (visiting) node, cycle exists

Both are correct, different perspectives:
- Kahn's: "Not everyone can finish"
- DFS: "We're visiting a node we're still processing"
```

---

# 🔧 COMMON MISTAKES

### Mistake 1: Confusing Direction
```
❌ WRONG: edge course[0] → course[1] (wrong prerequisite)
✅ RIGHT: edge course[1] → course[0] (take [1] before [0])

If prerequisites = [[CS102, CS101]]:
CS101 must come first, so edge is: CS101 → CS102
```

### Mistake 2: Forgetting Cycle Check
```
❌ WRONG: Return result without verifying all nodes processed
✅ RIGHT: Check result.size() == n before returning
```

### Mistake 3: Wrong DFS State
```
❌ WRONG: Using boolean visited[] (can't detect cycles)
✅ RIGHT: Using 3-state system (white/gray/black)
```

### Mistake 4: Forgot to Reverse (DFS Only)
```
❌ WRONG: Return result from DFS as-is
✅ RIGHT: Reverse result after DFS completes
```

---

# 📈 COMPLEXITY ANALYSIS

| Algorithm | Time | Space | Notes |
|-----------|------|-------|-------|
| Kahn's | O(V+E) | O(V) | Queue + indegrees |
| DFS | O(V+E) | O(V) | Recursion stack |
| Build Graph | O(E) | O(V+E) | Adjacency list |

---

# 🏆 PRACTICE PROGRESSION

## Easy
1. Course Schedule I - YES/NO answer
2. Course Schedule II - Return order

## Medium
3. Parallel Courses - Find minimum levels
4. Build Order - Similar to courses

## Hard
5. Alien Dictionary - Extract dependencies from data

---

**Master topological sort and you'll solve ANY dependency/prerequisite problem.** 🚀
