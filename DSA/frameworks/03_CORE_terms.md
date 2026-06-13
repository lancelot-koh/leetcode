# 🗣️ DSA Terminology & Interview Vocabulary Guide

**Master the language of algorithms to think like an interviewer**

---

## 📍 Why Vocabulary Matters

In Google/Meta interviews, vocabulary signals:
- ✅ You understand the concept (not just memorized)
- ✅ You think algorithmically (not just coding)
- ✅ You can communicate clearly under pressure

**Key insight:** Interviewer judges through your words:
- ❌ "I need to store numbers" → Coding without modeling
- ✅ "I'll maintain the state as..." → Understanding the problem

---

## 🎯 Universal Framework

**Before solving ANY problem, ask:**

```
1. What is the STATE?
2. What are the CHOICES?
3. What METRIC am I optimizing?
4. How do I AGGREGATE results?
```

This single framework applies to:
- DP problems
- Graph problems  
- Greedy problems
- Search problems

---

# 1️⃣ DP VOCABULARY

**The language of Dynamic Programming**

## Core Concepts

| Term | Definition | Usage |
|------|-----------|-------|
| **State** | The information you track at each step | "The state is the current position" |
| **State Definition** | Formally defining what each state represents | "dp[i] = max profit at day i" |
| **Decision / Choice** | The options available at each state | "We can either buy or skip" |
| **Dependency** | Which previous states inform current state | "dp[i] depends on dp[i-1] and dp[i-2]" |
| **Transition** | How to compute current state from previous states | "The transition is dp[i] = dp[i-1] + value[i]" |
| **Recurrence Relation** | Mathematical formula for transition | "T(n) = T(n-1) + T(n-2)" |
| **Base Case** | Initial state(s) with known values | "Base case: dp[0] = 1" |
| **Optimization Goal** | What you're maximizing or minimizing | "We're minimizing the number of coins" |
| **Metric** | The value being optimized | "Metric: minimum cost" |
| **Aggregation** | How to combine choices (min/max/sum/or) | "Aggregation: we take the minimum" |

## Implementation Approaches

| Term | Meaning | When to Use |
|------|---------|------------|
| **Top-Down DP** | Memoization - start from goal, recurse down | Recursive thinking, complex dependencies |
| **Bottom-Up DP** | Tabulation - build from base cases up | Iterative thinking, clear order |
| **Space Optimization** | Reduce space complexity (e.g., 2D → 1D) | After getting correct solution working |
| **State Compression** | Reduce state dimensions while maintaining correctness | "We can compress dp[i][j] to just two variables" |
| **Rolling Array** | Use limited array size instead of full grid | "We only need the previous row" |

## Interview Phrases

```
"The STATE is..."
"The METRIC I'm optimizing is..."
"The TRANSITION formula is..."
"I'll use AGGREGATION to combine the choices..."

"The base case is..."
"For each STATE, I have these CHOICES..."
"This DEPENDENCY means I must process in order..."
```

---

# 2️⃣ GRAPH VOCABULARY

**The language of graphs and networks**

## Graph Structure

| Term | Definition | Example |
|------|-----------|---------|
| **Node / Vertex** | Individual element in graph | City, person, website |
| **Edge** | Connection between nodes | Road, friendship, hyperlink |
| **Directed Edge** | One-way connection | One-way street |
| **Undirected Edge** | Two-way connection | Friendship |
| **Weight / Cost** | Value associated with edge | Distance, time, cost |
| **Degree** | Number of edges connected to node | "This node has degree 3" |
| **Indegree** | Number of incoming edges (directed) | Prerequisites pointing TO this course |
| **Outdegree** | Number of outgoing edges (directed) | Courses this course points TO |

## Connectivity

| Term | Definition | Interview Phrase |
|------|-----------|-----------------|
| **Connected** | Path exists between nodes | "Are A and B connected?" |
| **Reachable** | Can reach from one node to another | "Node A is reachable from B" |
| **Disconnected** | No path between nodes | "These nodes are disconnected" |
| **Connected Component** | Maximal set of connected nodes | "Find all connected components" |
| **Bridge** | Edge whose removal disconnects graph | "This is a critical edge" |
| **Strongly Connected** | Every node can reach every other (directed) | "This graph is strongly connected" |

## Cycles

| Term | Definition | Usage |
|------|-----------|-------|
| **Cycle** | Path that starts and ends at same node | "There's a cycle: A→B→A" |
| **Cycle Detection** | Algorithm to find cycles | "Use DFS to detect cycles" |
| **Acyclic** | No cycles present | "This is a DAG (directed acyclic graph)" |
| **Topological Order** | Linear order respecting dependencies | "Sort the graph topologically" |

## Traversal & Exploration

| Term | Definition | When to Use |
|------|-----------|------------|
| **Traversal** | Visiting all nodes | "Let's traverse the graph" |
| **DFS (Depth-First)** | Go deep before backtracking | "I'll use DFS to explore" |
| **BFS (Breadth-First)** | Visit level by level | "BFS for shortest path in unweighted graph" |
| **Visited Set** | Track which nodes already explored | "Add to visited to avoid revisiting" |
| **Expansion** | Adding neighbors to frontier | "Expand all neighbors of current node" |

## Shortest Path

| Term | Definition | Interview Phrase |
|------|-----------|-----------------|
| **Distance** | Minimum cost path between nodes | "The distance from A to B is 5" |
| **Weight** | Cost of an edge | "The weight of this edge is 10" |
| **Relaxation** | Try shorter path through intermediate node | "We relax the edge from A to C through B" |
| **Shortest Path Tree** | Set of shortest paths from source | "Build the shortest path tree" |
| **Negative Cycle** | Cycle with negative total weight | "This graph has a negative cycle" |

## Dependency Problems

| Term | Definition | Example |
|------|-----------|---------|
| **Prerequisite** | Thing that must come first | "Take course A as prerequisite for B" |
| **Topological Sort** | Order respecting dependencies | "Find valid course order" |
| **DAG** | Directed Acyclic Graph - no cycles | "Schedule this using topological sort" |
| **Dependency Chain** | Sequence of prerequisites | "A→B→C form a chain" |

---

# 3️⃣ BFS VOCABULARY

**The language of Breadth-First Search**

| Term | Definition | Interview Phrase |
|------|-----------|-----------------|
| **State** | Current node being processed | "The state is the current node" |
| **Frontier** | Nodes waiting to be expanded (important in Google!) | "Add to the frontier" |
| **Level / Layer** | Distance from source | "All nodes at level k" |
| **Visited Set** | Nodes we've seen before | "Mark as visited to avoid cycles" |
| **Expansion** | Processing a node's neighbors | "Expand this node" |
| **Queue** | FIFO structure for BFS | "Process in FIFO order" |
| **Layer Order** | Level-by-level processing | "Process layer by layer" |
| **Multi-Source BFS** | Start from multiple nodes | "All sources start simultaneously" |

## Common Phrases

```
"Let's traverse the graph using BFS..."
"Add to the frontier..."
"Process this level completely before next..."
"Mark nodes as visited to avoid cycles..."
```

## When to Use BFS

- ✅ Shortest path in **unweighted** graph
- ✅ Level-order traversal (Word Ladder, etc.)
- ✅ Finding **closest** node by distance
- ✅ Multi-source spreading (rotting oranges)

---

# 4️⃣ DFS VOCABULARY

**The language of Depth-First Search**

| Term | Definition | Interview Phrase |
|------|-----------|-----------------|
| **Backtracking** | Undo choice and try alternative | "I'll backtrack and try another choice" |
| **Choose** | Make a decision | "Choose this node" |
| **Explore** | Recursively process | "Explore this branch" |
| **Undo / Restore** | Return to previous state | "Undo the choice" |
| **State Restoration** | Return state to before choice | "Restore the state" |
| **Recursion Stack** | Call stack maintains state | "Use recursion to maintain state" |
| **Path** | Sequence from start to current | "Track the current path" |
| **Visited Set** | Nodes seen in current path | "Mark as visited in this path" |

## Backtracking Pattern

```
Choose:  path.add(choice)
Explore: dfs(remaining)
Undo:    path.remove(choice)  ← KEY STEP
```

## Common Phrases

```
"I'll use backtracking to explore all possibilities..."
"Choose this node, explore, then backtrack..."
"Restore the state when returning..."
"The recursion stack maintains our state..."
```

---

# 5️⃣ SLIDING WINDOW VOCABULARY

**The language of Sliding Window pattern (HIGH FREQUENCY)**

| Term | Definition | Interview Phrase |
|------|-----------|-----------------|
| **Window** | Current subarray/substring | "The window is [left, right]" |
| **Expand Window** | Increase window size | "Expand the window by moving right pointer" |
| **Shrink Window** | Decrease window size | "Shrink the window when invalid" |
| **Valid Window** | Window satisfies all conditions | "The window is valid if..." |
| **Invalid Window** | Window violates conditions | "The window becomes invalid when..." |
| **Pointer** | Left and right boundaries | "Move the left pointer when..." |
| **Frequency Map / Counter** | Track element counts in window | "Maintain a frequency map" |
| **Condition** | What makes window valid/invalid | "The condition is: at most k distinct chars" |

## Key Characteristics

```
✅ LEFT pointer NEVER backtracks → O(n) not O(n²)
✅ Each element enters window once, exits once
✅ Maintain auxiliary data structure for O(1) check
```

## Common Phrases

```
"When the window becomes invalid, I shrink it..."
"Maintain a frequency counter..."
"The left pointer only moves right..."
"Expand to include more elements, shrink to satisfy condition..."
```

---

# 6️⃣ GREEDY VOCABULARY

**The language of Greedy algorithms (prepare for WHY questions)**

| Term | Definition | Interview Phrase |
|------|-----------|---|
| **Greedy Choice** | Locally optimal choice at each step | "At each step, I make the greedy choice..." |
| **Locally Optimal** | Best choice for current state | "Choose the locally optimal option" |
| **Globally Optimal** | Best solution overall | "Hope the local choices lead to global optimum" |
| **Proof of Correctness** | Why greedy works | "This greedy choice is optimal because..." |
| **Exchange Argument** | Standard greedy proof technique | "Suppose optimal doesn't choose X. We can exchange..." |
| **Counterexample** | When greedy fails | "Greedy fails on this example..." |
| **Pruning** | Remove branches that can't improve | "Prune this state..." |

## The Exchange Argument (Most Important!)

```
Suppose an optimal solution does not make choice X.
We can exchange it with choice X
without making the solution worse.

Therefore, X is safe to choose.
```

## Common Phrases

```
"At each step, I make the greedy choice..."
"This greedy choice is optimal because [exchange argument]..."
"Prune branches that can't lead to better solution..."
"Why is this choice locally optimal?"
```

---

# 7️⃣ BINARY SEARCH VOCABULARY

**The language of Binary Search (Google favorite!)**

| Term | Definition | Interview Phrase |
|------|-----------|---|
| **Search Space** | Range of possible answers | "Define the search space first" |
| **Monotonic Property** | Answer space is all T or all F | "The property is monotonic" |
| **Threshold** | Boundary between F and T | "Find the threshold between no and yes" |
| **Left Pointer** | Lower bound of search space | "Move left = need larger answer" |
| **Right Pointer** | Upper bound of search space | "Move right = can go smaller" |
| **Mid Point** | Middle of search space | "Calculate mid without overflow" |
| **Invariant** | What's always true | "Maintain [left, right) as valid search space" |
| **Converge** | Left and right meet | "When left == right, we found answer" |

## Key Insight

```
Binary Search on ANSWER, not on DATA

Not searching array for value.
Searching answer space for threshold.
```

## Monotonic Property Pattern

```
FFFF TTTT
    ↑
    Threshold (binary search finds this)
```

## Common Phrases

```
"Let's define the search space..."
"The property is monotonic because..."
"If we can achieve X, we can achieve X+1..."
"Use binary search to find the threshold..."
```

---

# 8️⃣ UNION FIND VOCABULARY

**The language of Union Find / Disjoint Set Union**

| Term | Definition | Interview Phrase |
|------|-----------|---|
| **Union** | Merge two sets | "Union set A and set B" |
| **Find** | Which set does element belong to | "Find the root of X" |
| **Parent** | Representative of set | "The parent of X is..." |
| **Root** | Ultimate representative | "Find the root element" |
| **Path Compression** | Flatten structure | "Optimize with path compression" |
| **Union by Rank** | Keep tree shallow | "Use union by rank" |
| **Connected Component** | All elements in same set | "How many connected components?" |
| **Same Set** | Two nodes in same component | "X and Y are in the same set" |

## Core Operations

```
find(x)     → Which set/root does x belong to?
union(x, y) → Merge the sets containing x and y
```

## Common Phrases

```
"Two nodes belong to the same set..."
"Union these components..."
"Find the root of this element..."
"Use path compression to optimize..."
```

---

# 9️⃣ PROBLEM CLASSIFICATION VOCABULARY

**How to categorize what you see**

| Problem Type | Key Indicator | Vocabulary |
|---|---|---|
| **Optimization** | Find max/min | "Metric: minimize cost", "Aggregate with Math.min" |
| **Counting** | How many ways | "Aggregate with addition" |
| **Reachability** | Is it possible | "Aggregate with OR" |
| **Search** | Find element | "Use DFS/BFS/Binary Search" |
| **Path** | Route from A to B | "Shortest path", "Use Dijkstra" |
| **Subsequence** | Order-preserving subset | "DP with two pointers" |
| **Substring** | Contiguous subset | "Use sliding window" |
| **Subarray** | Contiguous numbers | "Use prefix sum or sliding window" |

---

# 🔟 QUICK REFERENCE TABLE

### By Pattern Type

| Pattern | Core Terms | Key Vocabulary |
|---------|-----------|---|
| **DP** | State, Transition, Metric, Aggregation | Recurrence, Base Case, Dependency |
| **Graph** | Node, Edge, Reachable, Dependency | Connectivity, Cycle, Topological Sort |
| **BFS** | Level, Frontier, Visited, Queue | Multi-source, Expansion, Layer |
| **DFS** | Backtrack, Choose, Explore, Undo | State Restoration, Recursion Stack |
| **Sliding Window** | Expand, Shrink, Valid, Window | Frequency Map, Condition, Pointer |
| **Greedy** | Greedy Choice, Proof, Exchange Argument | Pruning, Locally Optimal |
| **Binary Search** | Search Space, Monotonic, Threshold | Convergence, Invariant |
| **Union Find** | Union, Find, Root, Parent | Path Compression, Connected Component |

---

# 1️⃣1️⃣ INTERVIEW CHEAT SHEET

## Before Coding

Ask these questions out loud:

```
1. "What is the STATE?"
   Answer: Clearly define what you track

2. "What are the CHOICES?"
   Answer: What options exist at each state

3. "What METRIC am I optimizing?"
   Answer: What are you min/max/counting/finding

4. "How do I AGGREGATE?"
   Answer: How to combine choices (min/max/+/||)

5. "What are the BASE CASES?"
   Answer: Initial known values

6. "What's the DEPENDENCY ORDER?"
   Answer: In what order can I compute states
```

## While Coding

Use these phrases:

```
"I'll maintain the state as..."
"The transition is..."
"I aggregate by taking the..."
"This dependency means..."
"I can optimize using..."
```

## After Coding

Verify you can explain:

```
"Why is this STATE definition correct?"
"Why is this METRIC what we want to optimize?"
"Why does this AGGREGATION work?"
"Why is this approach efficient?"
```

---

# 1️⃣2️⃣ THE BIGGEST INSIGHT

**The moment you start using this vocabulary, you've crossed a threshold:**

❌ "Hmm, let me try this..."  → Trial and error

✅ "The state is... The metric is... The aggregation is..."  → Algorithmic thinking

---

**This is the difference between:**
- Someone who memorized problems
- Someone who understands algorithms
- Someone Google wants to hire

**Master this vocabulary, and original questions become solvable.** 🚀
