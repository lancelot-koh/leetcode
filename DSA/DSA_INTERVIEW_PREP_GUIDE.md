# 📚 DSA Interview Prep - Complete Study Guide

**Your Ultimate Reference for 6-8 Week Mastery & Interview Success**

---

## 🎯 Quick Navigation

- **Starting out?** → Go to [LEARNING PATH](#1-learning-path-by-difficulty)
- **Need quick answers?** → Go to [QUICK PATTERN CHEAT SHEET](#2-quick-pattern-cheat-sheet)
- **Stuck on a problem?** → Go to [PROBLEM-SOLVING ROADMAP](#3-problem-solving-roadmap)
- **What to study today?** → Go to [DAILY STUDY PLAN](#4-daily-study-plan)
- **In an interview?** → Go to [INTERVIEW TIPS](#5-interview-tips)
- **Need examples?** → Go to [RESOURCES BY PATTERN](#6-resources-by-pattern)

---

## 1. LEARNING PATH (By Difficulty)

### Foundation Level (Patterns 1-5)
Start here if you're new to DSA or need a refresher. These patterns build intuition.

#### Pattern 1: Prefix Sum
- **LeetCode Example:** [LC560 Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/)
- **Core Idea:** Precompute cumulative sums to answer range queries in O(1)
- **Key Learning Points:**
  1. Use HashMap to store prefix sums and frequencies
  2. For each position, check if (current_sum - target) exists in HashMap
  3. Handle both prefix sum array and hash map approaches
- **Common Mistakes:**
  - Forgetting to initialize HashMap with {0: 1}
  - Not updating HashMap AFTER processing each element
  - Confusing cumulative sum with differences
- **Estimated Practice Time:** 2-3 hours (1-2 problems)
- **Difficulty:** ⭐⭐ (Easy-Medium)
- **Variants to Master:** Range sum, contiguous array, target sum

---

#### Pattern 2: Sliding Window
- **LeetCode Example:** [LC3 Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)
- **Core Idea:** Use two pointers to maintain a valid window, shrink from left when invalid
- **Key Learning Points:**
  1. Define what makes a window valid (problem-dependent)
  2. Expand right pointer to include new elements
  3. Shrink left pointer until window becomes valid again
  4. Track the answer at each valid state
- **Common Mistakes:**
  - Shrinking window too much (should stop at first valid state)
  - Not properly tracking character frequencies
  - Confusion between "at least K" vs "exactly K" problems
- **Estimated Practice Time:** 2-3 hours (2-3 problems)
- **Difficulty:** ⭐⭐ (Easy-Medium)
- **Variants to Master:** "At most K", "Exactly K", different criteria (length, frequency, etc.)

---

#### Pattern 3: Two Pointers
- **LeetCode Example:** [LC167 Two Sum II - Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)
- **Core Idea:** Use opposite-direction pointers to solve in O(n) time without extra space
- **Key Learning Points:**
  1. Only works on sorted arrays (or requires sorting first)
  2. Move pointers based on comparison: too small → move left pointer right, too large → move right pointer left
  3. Can be combined with other patterns (e.g., containers, trapping rain)
- **Common Mistakes:**
  - Forgetting to sort when needed
  - Moving pointers incorrectly (moving towards instead of away from issue)
  - Not handling duplicates when required
- **Estimated Practice Time:** 2-3 hours (2 problems)
- **Difficulty:** ⭐⭐ (Easy-Medium)
- **Variants to Master:** Container with most water, merge sorted arrays, palindrome validation

---

#### Pattern 4: Binary Search
- **LeetCode Example:** [LC704 Binary Search](https://leetcode.com/problems/binary-search/)
- **Core Idea:** Eliminate half of the search space in each iteration
- **Key Learning Points:**
  1. Requires sorted data (or monotonic property)
  2. Template: left=0, right=n-1, mid=(left+right)//2
  3. Three variants: exact match, first position, last position
  4. Be careful with boundary conditions (off-by-one errors)
- **Common Mistakes:**
  - Not recognizing when binary search applies
  - Incorrect boundary adjustments (left=mid vs left=mid+1)
  - Integer overflow when calculating mid (use mid=left+(right-left)//2)
  - Confusion between inclusive/exclusive boundaries
- **Estimated Practice Time:** 2-3 hours (2-3 problems)
- **Difficulty:** ⭐⭐ (Easy-Medium)
- **Variants to Master:** Search rotated array, find peak, search in matrix

---

#### Pattern 5: Monotonic Stack/Deque
- **LeetCode Example:** [LC739 Daily Temperatures](https://leetcode.com/problems/daily-temperatures/)
- **Core Idea:** Maintain a stack of elements in monotonic order to find next/previous extrema
- **Key Learning Points:**
  1. Use stack to store indices (not values) to access original element
  2. While incoming element breaks monotonic property, pop and process
  3. Answer is typically (current_index - popped_index)
  4. Monotonic Deque extends this for range queries
- **Common Mistakes:**
  - Storing values instead of indices
  - Processing elements after popping (timing of answer update)
  - Not initializing deque correctly for range problems
  - Confusing increasing vs decreasing stack order
- **Estimated Practice Time:** 3-4 hours (2-3 problems)
- **Difficulty:** ⭐⭐⭐ (Medium)
- **Variants to Master:** Next greater/smaller, sliding window max/min, trapping rain water

---

### Intermediate Level (Patterns 6-15)
These patterns handle more complex scenarios. Expect to spend 3-4 hours per pattern.

#### Pattern 6: Dynamic Programming on Sequences
- **LeetCode Example:** [LC300 Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)
- **Core Idea:** dp[i] = best solution considering elements 0..i
- **Key Learning Points:**
  1. Define state: dp[i] = ??? (what does it represent exactly?)
  2. Base case: dp[0] = ...
  3. Transition: for each i, check all j < i that satisfy constraint
  4. Answer: max(dp) or dp[n-1] depending on problem
- **Common Mistakes:**
  - Incomplete state definition (forgetting needed dimensions)
  - Wrong initialization values
  - Incorrect transition logic
  - Off-by-one in loop bounds
- **Estimated Practice Time:** 3-4 hours (2-3 problems)
- **Difficulty:** ⭐⭐⭐ (Medium-Hard)
- **Variants to Master:** LIS, LCS, house robber, partition equal subset sum

---

#### Pattern 7: Dynamic Programming on 2D Arrays
- **LeetCode Example:** [LC1143 Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/)
- **Core Idea:** dp[i][j] considers both dimensions of the problem
- **Key Learning Points:**
  1. Often compares two sequences: dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])
  2. Base cases: first row and column
  3. Direction of computation matters (usually top-left to bottom-right)
- **Common Mistakes:**
  - Forgetting to initialize borders correctly
  - Computing in wrong direction
  - Confusing (i,j) coordinate meanings
- **Estimated Practice Time:** 3-4 hours (2-3 problems)
- **Difficulty:** ⭐⭐⭐ (Medium-Hard)
- **Variants to Master:** LCS, edit distance, distinct paths, maximum path sum

---

#### Pattern 8: BFS (Breadth-First Search)
- **LeetCode Example:** [LC102 Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/)
- **Core Idea:** Explore nodes level by level, perfect for shortest path in unweighted graphs
- **Key Learning Points:**
  1. Use Queue: add initial state → while queue not empty → process and add neighbors
  2. Track visited to avoid cycles
  3. For each level, process all nodes before moving to next
  4. Can modify to track distance/parent for path reconstruction
- **Common Mistakes:**
  - Forgetting to mark visited BEFORE adding to queue (causes duplicates)
  - Not processing all nodes at current level before next level
  - Confusion about when to track depth/distance
- **Estimated Practice Time:** 3-4 hours (2-3 problems)
- **Difficulty:** ⭐⭐⭐ (Medium)
- **Variants to Master:** Multi-source BFS, 01-matrix, surrounded regions, word ladder

---

#### Pattern 9: DFS (Depth-First Search)
- **LeetCode Example:** [LC200 Number of Islands](https://leetcode.com/problems/number-of-islands/)
- **Core Idea:** Explore as far as possible along each branch before backtracking
- **Key Learning Points:**
  1. Can use recursion (natural) or stack (explicit)
  2. Mark visited to avoid revisiting
  3. Base case: boundary or visited conditions
  4. Recursive case: explore all unvisited neighbors
- **Common Mistakes:**
  - Not marking visited in right place (before vs after recursion)
  - Stack overflow with deep recursion
  - Forgetting boundary checks
  - Confusion about when to return vs when to continue
- **Estimated Practice Time:** 3-4 hours (2-3 problems)
- **Difficulty:** ⭐⭐ (Easy-Medium)
- **Variants to Master:** Island variations, connected components, bipartite checking, path finding

---

#### Pattern 10: Backtracking
- **LeetCode Example:** [LC46 Permutations](https://leetcode.com/problems/permutations/)
- **Core Idea:** Explore all possibilities by making a choice, recursing, then undoing
- **Key Learning Points:**
  1. Choose → Explore → Unchoose (backtrack)
  2. Use seen set to track used elements
  3. Base case: when we've made enough choices
  4. At each step, try all valid next choices
- **Common Mistakes:**
  - Forgetting to backtrack (undo) before trying next choice
  - Wrong base case (stopping too early or too late)
  - Not tracking what's been used
  - Creating new list vs modifying existing one
- **Estimated Practice Time:** 3-4 hours (2-3 problems)
- **Difficulty:** ⭐⭐⭐ (Medium)
- **Variants to Master:** Permutations, combinations, subsets, N-queens, sudoku solver

---

#### Pattern 11: Union-Find (Disjoint Set Union)
- **LeetCode Example:** [LC547 Number of Provinces](https://leetcode.com/problems/number-of-provinces/)
- **Core Idea:** Efficiently track connected components and union them
- **Key Learning Points:**
  1. parent[x] = representative of component containing x
  2. Path compression: parent[x] = find(parent[x]) for faster lookups
  3. Union by rank: attach smaller tree to larger
  4. find(x) returns ultimate parent; union(x,y) merges components
- **Common Mistakes:**
  - Not implementing path compression (will TLE on large inputs)
  - Union without checking if already in same component
  - Forgetting to initialize parent array
  - Confusion about what find returns
- **Estimated Practice Time:** 3-4 hours (2 problems)
- **Difficulty:** ⭐⭐⭐ (Medium-Hard)
- **Variants to Master:** Connected components, redundant edges, accounts merge, graph validity

---

#### Pattern 12: Greedy Algorithms
- **LeetCode Example:** [LC55 Jump Game](https://leetcode.com/problems/jump-game/)
- **Core Idea:** Make locally optimal choice at each step, hoping for global optimum
- **Key Learning Points:**
  1. Not always correct—requires proof that greedy choice is optimal
  2. Often combines with other patterns (e.g., sorting + greedy)
  3. Answer question: "What's the best choice right now?"
  4. Prove: this choice doesn't prevent optimal future choices
- **Common Mistakes:**
  - Using greedy when DP is needed
  - Not verifying greedy choice is actually optimal
  - Ignoring counter-examples
  - Choosing wrong sorting criterion
- **Estimated Practice Time:** 3-4 hours (2-3 problems)
- **Difficulty:** ⭐⭐⭐ (Medium-Hard)
- **Variants to Master:** Jump game, interval scheduling, gas station, candy distribution

---

#### Pattern 13: Dijkstra's Algorithm
- **LeetCode Example:** [LC743 Network Delay Time](https://leetcode.com/problems/network-delay-time/)
- **Core Idea:** Find shortest path in weighted graphs using priority queue
- **Key Learning Points:**
  1. Always process node with minimum distance first
  2. Use PriorityQueue: (distance, node)
  3. Once a node is processed, its distance is finalized
  4. Update neighbor distances when we find shorter paths
- **Common Mistakes:**
  - Processing same node multiple times (forgot visited set)
  - Updating priority queue instead of ignoring old entries
  - Not initializing distances correctly
  - Handling edges with negative weights (Dijkstra doesn't work!)
- **Estimated Practice Time:** 3-4 hours (2 problems)
- **Difficulty:** ⭐⭐⭐⭐ (Hard)
- **Variants to Master:** Shortest path, network delay, reachable nodes, path with maximum probability

---

#### Pattern 14: DP on Intervals (Matrix Chain)
- **LeetCode Example:** [LC1039 Minimum Score Triangulation of Polygon](https://leetcode.com/problems/minimum-score-triangulation-of-polygon/)
- **Core Idea:** dp[i][j] = optimal solution for interval from i to j
- **Key Learning Points:**
  1. Process intervals in increasing length order
  2. For each interval, try all ways to split it (k from i to j-1)
  3. Combine solutions of two sub-intervals
- **Common Mistakes:**
  - Processing in wrong order (must do smaller intervals first)
  - Not considering all split points
  - Confusion about interval boundaries
- **Estimated Practice Time:** 3-4 hours (2 problems)
- **Difficulty:** ⭐⭐⭐⭐ (Hard)
- **Variants to Master:** Matrix chain multiplication, burst balloons, palindrome partitioning

---

#### Pattern 15: Bitmask DP
- **LeetCode Example:** [LC847 Shortest Path Visiting All Nodes](https://leetcode.com/problems/shortest-path-visiting-all-nodes/)
- **Core Idea:** Use bit representation for subset of nodes visited
- **Key Learning Points:**
  1. dp[mask][i] = optimal solution when visited nodes = mask, ending at node i
  2. Transition: try visiting next unvisited node
  3. Base case: starting from each node individually
  4. Answer: when mask = all nodes visited
- **Common Mistakes:**
  - Confusion about bit operations (1<<i means ith bit is set)
  - Wrong state definition
  - Inefficient iteration over all next states
- **Estimated Practice Time:** 3-4 hours (1-2 problems)
- **Difficulty:** ⭐⭐⭐⭐⭐ (Very Hard)
- **Variants to Master:** TSP variants, shortest path visiting all nodes, minimum cost to connect all cities

---

### Advanced Level (Patterns 16-23)
These are specialized patterns for specific problem types.

#### Pattern 16: Segment Tree
- **Core Idea:** Hierarchical data structure for efficient range queries and updates
- **When:** Need O(log n) range sum/min/max with frequent updates
- **Time Complexity:** O(log n) per operation
- **Space:** O(n)

#### Pattern 17: Trie (Prefix Tree)
- **Core Idea:** Store strings efficiently, prefix-based search
- **When:** Word search, autocomplete, IP routing
- **Time:** O(length of string) per operation

#### Pattern 18: Minimum Spanning Tree
- **Core Idea:** Connect all nodes with minimum total edge weight
- **Algorithms:** Kruskal (Union-Find), Prim (Dijkstra-like)
- **When:** Network design, connecting cities with minimum cost

#### Pattern 19: Topological Sort
- **Core Idea:** Linear ordering of vertices in DAG
- **When:** Task scheduling, course prerequisites, dependency resolution
- **Methods:** Kahn's algorithm (BFS), DFS

#### Pattern 20: Sweep Line
- **Core Idea:** Process events in order to handle overlapping intervals
- **When:** Merge intervals, meeting rooms, skyline problem
- **Complexity:** O(n log n) typically

#### Pattern 21: Coordinate Compression
- **Core Idea:** Map large coordinate space to small range
- **When:** Discretization needed, space constraints
- **Common:** With events or coordinate-based problems

#### Pattern 22: Bit Manipulation
- **Core Idea:** Use bitwise operations for optimization
- **When:** Single number, power of two, bit counting
- **Techniques:** Xor, and, or, bit shifting, bit counting

#### Pattern 23: Custom Comparators & Sorting
- **Core Idea:** Sort with custom rules for optimal selection
- **When:** Interval scheduling, task ordering, meeting rooms
- **Key:** Carefully define comparison function that respects problem logic

---

## 2. QUICK PATTERN CHEAT SHEET

### All 23 Patterns at a Glance

| # | Pattern | When to Use | Time/Space | Difficulty | Key Template |
|---|---------|------------|-----------|-----------|--------------|
| 1 | **Prefix Sum** | Fast range sum queries | O(n)/O(n) | ⭐⭐ | HashMap + cumsum |
| 2 | **Sliding Window** | Fixed/variable window constraints | O(n)/O(1-n) | ⭐⭐ | Two pointers + map |
| 3 | **Two Pointers** | Sorted array operations | O(n)/O(1) | ⭐⭐ | Left/right pointers |
| 4 | **Binary Search** | Sorted data, search space | O(log n)/O(1) | ⭐⭐ | left, right, mid |
| 5 | **Monotonic Stack** | Next/previous extrema | O(n)/O(n) | ⭐⭐⭐ | Stack of indices |
| 6 | **DP Sequence** | Optimal subsequence | O(n²)/O(n) | ⭐⭐⭐ | dp[i] = f(dp[j<i]) |
| 7 | **DP 2D** | Sequence comparison, paths | O(n²)/O(n²) | ⭐⭐⭐ | dp[i][j] = ... |
| 8 | **BFS** | Shortest path, level order | O(V+E)/O(V) | ⭐⭐⭐ | Queue + visited |
| 9 | **DFS** | Traversal, connectivity | O(V+E)/O(h) | ⭐⭐ | Recursion + visited |
| 10 | **Backtracking** | All combinations/permutations | O(2^n)/O(n) | ⭐⭐⭐ | Choose→Explore→Undo |
| 11 | **Union-Find** | Connected components | O(α(n))/O(n) | ⭐⭐⭐ | parent[] + path compression |
| 12 | **Greedy** | Local optimal → global optimal | Varies | ⭐⭐⭐ | Prove choice is optimal |
| 13 | **Dijkstra** | Shortest path (weighted) | O(ElogV)/O(V) | ⭐⭐⭐⭐ | PriorityQueue + distance[] |
| 14 | **DP Interval** | Interval merging, splitting | O(n³)/O(n²) | ⭐⭐⭐⭐ | dp[i][j] = min(dp[i][k] + dp[k+1][j]) |
| 15 | **Bitmask DP** | Subset/permutation enumeration | O(2^n·n)/O(2^n) | ⭐⭐⭐⭐⭐ | dp[mask][i] |
| 16 | **Segment Tree** | Range queries + updates | O(log n)/O(n) | ⭐⭐⭐⭐ | Build tree, query, update |
| 17 | **Trie** | Prefix-based search | O(length)/O(n·k) | ⭐⭐⭐ | TrieNode tree structure |
| 18 | **MST** | Minimum spanning tree | O(ElogE)/O(V) | ⭐⭐⭐⭐ | Kruskal/Prim |
| 19 | **Topological Sort** | DAG ordering, dependencies | O(V+E)/O(V) | ⭐⭐⭐ | Kahn's or DFS |
| 20 | **Sweep Line** | Overlapping intervals/events | O(nlogn)/O(n) | ⭐⭐⭐ | Events + process order |
| 21 | **Coordinate Compression** | Large coordinate space | O(nlogn)/O(n) | ⭐⭐⭐ | Map to smaller range |
| 22 | **Bit Manipulation** | Bit-level optimization | O(1-n)/O(1) | ⭐⭐⭐ | XOR, AND, OR, shifts |
| 23 | **Custom Sort** | Interval/task ordering | O(nlogn)/O(1) | ⭐⭐⭐ | Comparator function |

---

### Decision Tree: How to Choose a Pattern

```
Start with problem analysis
│
├─ "Range or subsequence?"
│  ├─ "Sum/AND/XOR of range?" → Prefix Sum (Pattern 1)
│  ├─ "Longest/shortest substring?" → Sliding Window (Pattern 2)
│  └─ "Optimal subsequence?" → DP Sequence (Pattern 6)
│
├─ "Array operation?"
│  ├─ "Need two elements?" → Two Pointers (Pattern 3)
│  ├─ "Search or find boundary?" → Binary Search (Pattern 4)
│  └─ "Find max in sliding window?" → Monotonic Deque (Pattern 5)
│
├─ "Graph or tree problem?"
│  ├─ "Shortest path (unweighted)?" → BFS (Pattern 8)
│  ├─ "Visit all/connected components?" → DFS (Pattern 9)
│  ├─ "Union/connectivity?" → Union-Find (Pattern 11)
│  └─ "Shortest path (weighted)?" → Dijkstra (Pattern 13)
│
├─ "Generate all/optimization?"
│  ├─ "All permutations/combinations?" → Backtracking (Pattern 10)
│  ├─ "Optimal decision sequence?" → DP (Pattern 6/7/14)
│  ├─ "Make greedy choice?" → Greedy (Pattern 12)
│  └─ "Subset enumeration?" → Bitmask DP (Pattern 15)
│
├─ "String or prefix operation?"
│  ├─ "Word search/autocomplete?" → Trie (Pattern 17)
│  └─ "Edit/LCS?" → DP 2D (Pattern 7)
│
├─ "Special data structure?"
│  ├─ "Range query + update?" → Segment Tree (Pattern 16)
│  ├─ "Course prerequisites?" → Topological Sort (Pattern 19)
│  └─ "Interval overlap?" → Sweep Line (Pattern 20)
│
└─ "Coordination/ordering?"
   ├─ "Large coordinates?" → Coordinate Compression (Pattern 21)
   ├─ "Interval scheduling?" → Custom Sort (Pattern 23)
   └─ "Bit optimization?" → Bit Manipulation (Pattern 22)
```

---

### Red Flag Keywords

| Keyword | Pattern | Example |
|---------|---------|---------|
| "sum equals K" | Prefix Sum | LC560 |
| "longest/shortest substring" | Sliding Window | LC3, LC76 |
| "two sum" | Two Pointers / Hash | LC1 |
| "search in sorted" | Binary Search | LC704, LC33 |
| "next greater/smaller" | Monotonic Stack | LC739, LC496 |
| "longest increasing" | DP Sequence | LC300 |
| "shortest path" | BFS / Dijkstra | LC1091, LC743 |
| "connected components" | Union Find / DFS | LC200, LC547 |
| "all permutations/subsets" | Backtracking / DP | LC46, LC78 |
| "optimal selection" | Greedy / DP | LC55, LC134 |
| "traverse tree level" | BFS | LC102 |
| "island/connected" | DFS / Union Find | LC200, LC695 |
| "visit all nodes" | Bitmask DP | LC847 |
| "match words" | Trie / DP | LC208 |
| "course order" | Topological Sort | LC207 |
| "merge intervals" | Sweep Line | LC56 |
| "single number" | Bit Manipulation | LC136 |

---

## 3. PROBLEM-SOLVING ROADMAP

### Step 1: Analyze the Problem (5 minutes)

```
□ Read problem 2-3 times slowly
□ Identify input/output format
□ List all constraints
□ Find what makes it hard
□ Identify any ambiguities → ask clarifying questions
```

### Step 2: Recognize the Pattern (3 minutes)

Use the decision tree above to identify which pattern(s) apply:
- One pattern (simple case)
- Multiple patterns combined (harder case)
- No standard pattern (design custom approach)

### Step 3: Design Solution (10 minutes)

```
1. State Definition:
   □ What do I need to track?
   □ What is the minimum information needed?
   □ Is my state definition complete?

2. Transition:
   □ How does state change?
   □ What are the constraints?
   □ Can I move from state A to state B?

3. Data Structures:
   □ What do I need to store?
   □ What operations are most frequent?
   □ Which DS optimizes those operations?

4. Complexity:
   □ Time: O(?)
   □ Space: O(?)
   □ Will this pass constraints? (n ≤ ?)
```

### Step 4: Code Implementation (15-20 minutes)

```
1. Write skeleton first (function signature, base case)
2. Implement core logic with clear variable names
3. Add boundary checks
4. Test with provided examples
5. Walk through edge cases mentally
```

### Step 5: Test & Optimize (5-10 minutes)

```
□ Dry run on example 1
□ Dry run on example 2
□ Check edge cases (empty, single, n-1, all same)
□ Optimize if necessary (better DS, better transition)
```

---

### Real Interview Example Walkthrough

**Problem:** LC127 - Word Ladder

**Problem Statement:** Given two words (beginWord and endWord) and a dictionary, find the shortest path from beginWord to endWord. You can only change one letter at a time and each intermediate word must exist in the dictionary.

#### Step 1: Analysis
```
Input: beginWord, endWord, wordList
Output: Shortest number of words in path (or 0 if impossible)
Constraints: 1 ≤ wordList.length ≤ 5000, word length ≤ 5

Hard part: Need to explore shortest path efficiently
```

#### Step 2: Pattern Recognition
**Red flags:** "shortest path" → BFS is the answer!
- Nodes: words
- Edges: two words that differ by 1 letter
- Find: shortest path from start to end

#### Step 3: Design
```
State: Current word being considered
Neighbors: All words in dictionary that differ by 1 letter

Algorithm: BFS
- Queue starts with (beginWord, distance=1)
- For each word, find all neighbors (differ by 1 letter)
- If neighbor == endWord, return distance
- Otherwise, add to queue if not visited

Data Structures:
- Queue: words to process
- Set: visited words (avoid cycles)
- Set: dictionary (O(1) lookup)
```

#### Step 4: Code
```python
def ladderLength(beginWord, endWord, wordList):
    wordSet = set(wordList)
    if endWord not in wordSet:
        return 0
    
    # BFS
    queue = [(beginWord, 1)]
    visited = {beginWord}
    
    while queue:
        word, dist = queue.pop(0)
        
        if word == endWord:
            return dist
        
        # Find neighbors (differ by 1 letter)
        for i in range(len(word)):
            for c in 'abcdefghijklmnopqrstuvwxyz':
                neighbor = word[:i] + c + word[i+1:]
                if neighbor in wordSet and neighbor not in visited:
                    visited.add(neighbor)
                    queue.append((neighbor, dist + 1))
    
    return 0
```

#### Step 5: Complexity & Optimization
```
Time: O(N × L × 26) where N = # words, L = word length
  - Generate all neighbors: L positions × 26 letters = 26L
  - Do this for each of N words
  - Each word added once to queue

Space: O(N) for queue and visited set

Optimization: Bidirectional BFS (search from both ends)
  - Reduces search space significantly
  - Meet in middle faster
  - Time: O(N × L × 26) but smaller constant
```

---

## 4. DAILY STUDY PLAN

### Week 1: Foundations (Patterns 1-5)
**Goal:** Understand the 7-step framework and master basic patterns

| Day | Focus | Patterns | Time | Checkpoint |
|-----|-------|----------|------|-----------|
| 1-2 | Framework theory | - | 4h | Can describe Modeling/State/Transition? |
| 3-4 | Prefix Sum | 1 | 3h | Solve 2 problems |
| 5-6 | Sliding Window | 2 | 3h | Solve 2 problems |
| 7 | Review | 1-2 | 2h | Can solve both patterns from scratch? |

**Deliverables:** Framework notes + 4 annotated solutions

---

### Week 2: Core Patterns (Patterns 2-4)
**Goal:** Master the two most frequently used patterns

| Day | Focus | Patterns | Time | Checkpoint |
|-----|-------|----------|------|-----------|
| 1-2 | Two Pointers | 3 | 3h | Solve 2 problems |
| 3-4 | Binary Search | 4 | 3h | Solve 2 problems |
| 5-7 | Practice + Review | 2,3,4 | 4h | 20min template recall |

**Deliverables:** 4 new solutions + pattern templates

---

### Week 3: Stack & DP (Patterns 5-6)
**Goal:** Handle "next/previous" problems and sequence optimization

| Day | Focus | Patterns | Time | Checkpoint |
|-----|-------|----------|------|-----------|
| 1-2 | Monotonic Stack | 5 | 3h | Solve 2 problems |
| 3-4 | DP Sequence | 6 | 4h | Solve 2-3 problems |
| 5-7 | Practice | 5-6 | 4h | State design clarity |

**Deliverables:** 5 new solutions + DP transition explanation

---

### Week 4: Search Algorithms (Patterns 7-10)
**Goal:** Master graph search and generation problems

| Day | Focus | Patterns | Time | Checkpoint |
|-----|-------|----------|------|-----------|
| 1-2 | DP 2D | 7 | 4h | Solve 2 problems |
| 3-4 | BFS | 8 | 3h | Solve 2 problems |
| 5 | DFS | 9 | 3h | Solve 2 problems |
| 6-7 | Backtracking | 10 | 4h | Solve 2 problems |

**Deliverables:** 8 new solutions + BFS/DFS/Backtracking templates

---

### Week 5: Advanced Patterns (Patterns 11-15)
**Goal:** Complete mastery of core 12 patterns

| Day | Focus | Patterns | Time | Checkpoint |
|-----|-------|----------|------|-----------|
| 1-2 | Union-Find | 11 | 3h | Solve 2 problems |
| 3-4 | Greedy | 12 | 3h | Solve 2 problems |
| 5 | Dijkstra | 13 | 3h | Solve 1 problem |
| 6-7 | DP Interval + Bitmask | 14-15 | 4h | Solve 2 problems |

**Deliverables:** 9 new solutions + full pattern mastery

---

### Week 6: Specialist Patterns (Patterns 16-23)
**Goal:** Learn specialized patterns for specific problem types

| Day | Focus | Patterns | Time | Checkpoint |
|-----|-------|----------|------|-----------|
| 1-2 | Segment Tree + Trie | 16-17 | 4h | Understand concepts |
| 3-4 | MST + Topological Sort | 18-19 | 4h | Solve 1-2 problems |
| 5-7 | Sweep Line + Others | 20-23 | 4h | Recognize when to use |

**Deliverables:** 3-4 new solutions + pattern recognition practice

---

### Week 7: Mixed Pattern Practice (Integration)
**Goal:** Solve problems that combine multiple patterns

| Day | Focus | Time | Checkpoint |
|-----|-------|------|-----------|
| 1 | Easy mixed problems | 3h | Solve 3 in 15min each |
| 2-3 | Medium mixed problems | 5h | Solve 5 in 20min each |
| 4-5 | Hard mixed problems | 5h | Solve 3 in 30min each |
| 6-7 | Mock interview (5 random Medium) | 4h | 90 min total, include explanation |

**Deliverables:** 11 new solutions + interview confidence

---

### Week 8: Consolidation & Review
**Goal:** Deep mastery and readiness for interview

| Day | Focus | Time | Checkpoint |
|-----|-------|------|-----------|
| 1-2 | Review all 23 patterns | 3h | Can describe each pattern? |
| 3-4 | Weak area targeted practice | 3h | Fill knowledge gaps |
| 5-7 | Timed challenges | 4h | Beat time limits consistently |

**Deliverables:** Personal cheat sheet + pattern mastery certificates

---

## 5. INTERVIEW TIPS

### Before the Interview (1 week)

```
□ Review 04_QUICK_REFERENCE.md (1 hour)
  - All 23 patterns at a glance
  - Decision tree
  - Time allocation strategies

□ Do 3 mock interviews (2-3 hours)
  - Time yourself (45 min per interview)
  - Include verbal explanation
  - Record yourself if possible

□ Revisit weak patterns (2 hours)
  - Problems you struggled with
  - Edge cases you missed
  - Complexity analysis

□ Review framework (1 hour)
  - 7-step process
  - Common pitfalls
  - Explanation techniques

□ Prepare 2-3 follow-ups (30 min)
  - "Can we optimize space?"
  - "Can we handle large inputs?"
  - "What if constraints change?"
```

### During the Interview (45 minutes)

```
5 min (0-5):   UNDERSTAND
  □ Read problem carefully
  □ Ask clarifying questions:
    - "Can input be empty/null?"
    - "Are there duplicates?"
    - "Any time/space constraints?"
  □ Confirm understanding of examples

3 min (5-8):   ANALYZE & RECOGNIZE PATTERN
  □ Identify key characteristics
  □ Mention which pattern applies
  □ Explain why this pattern
  Example: "This is a shortest path problem → BFS"

10 min (8-18): DESIGN
  □ Define state clearly
  □ Explain transitions/algorithm
  □ Describe data structures needed
  □ Discuss complexity:
    - Time: explain reasoning
    - Space: tradeoffs
  □ Walk through example with algorithm

15 min (18-33): CODE
  □ Write clean, readable code
  □ Use meaningful variable names
  □ Add comments for complex logic
  □ Explain code as you write
  □ Don't worry about syntax perfection

7 min (33-40): TEST
  □ Manually run example 1
  □ Manually run example 2
  □ Check edge cases:
    - Empty input
    - Single element
    - All same elements
    - Boundary values

5 min (40-45): OPTIMIZE
  □ Discuss potential optimizations
  □ Ask if interviewer wants you to code it
  □ Mention trade-offs
  Example: "We could use bidirectional BFS to halve search space"
```

### How to Explain Your Approach

**Good structure (2-3 minutes):**

```
1. "This problem's essence is..."
   [1 sentence: problem category]
   
2. "The state I need to track is..."
   [Clear definition of what represents solution]
   
3. "I'll transition using..."
   [Algorithm name: BFS, DP, Greedy, etc.]
   
4. "I'll use [DataStructure] because..."
   [Justify DS choice by most frequent operations]
   
5. "Time complexity is O(...) because..."
   [Explain each component of analysis]
```

**Example:**
```
"This is a shortest path problem. 
The state is the current node we're exploring. 
I'll use BFS to explore nodes level by level.
I'll use a queue to store nodes and a set to track visited nodes.
Time: O(V+E) since each node/edge processed once.
Space: O(V) for queue and visited set."
```

### How to Optimize After First Solution

**Questions to ask:**

1. **Can we reduce time complexity?**
   - Different algorithm? (e.g., DP instead of recursion)
   - Better data structure? (e.g., HashMap instead of O(n) search)
   - Preprocessing? (e.g., sorting for O(log n) lookup)

2. **Can we reduce space complexity?**
   - Eliminate auxiliary structures?
   - Use rolling array for DP?
   - In-place modification if possible?

3. **Can we handle edge cases better?**
   - Duplicates? Negatives? Zeros?
   - Empty input? Single element?
   - Very large input?

4. **Can we make code cleaner?**
   - Remove redundant checks?
   - Extract helper functions?
   - Simplify variable names?

---

### What To Do If You Get Stuck

```
Stuck on problem analysis?
  → Ask clarifying questions
  → Draw a small example
  → Start with brute force first

Stuck on algorithm selection?
  → "What's the problem asking? Min/max? All? First?"
  → "What data structure would help?"
  → "Did I see similar problem before?"

Stuck on coding?
  → Explain logic in pseudocode first
  → Code a simple version first (might be slow)
  → Add optimizations after basic version works

Stuck on complexity?
  → Start simple: count loops
  → How many times does each statement run?
  → What's the constraint? Will it TLE?

Can't find pattern?
  → Try brute force to understand problem better
  → "Is there substructure?" → might be DP
  → "Is there ordering?" → might be greedy/sort
  → "Is there graph?" → might be BFS/DFS
```

---

### Common Interview Mistakes

| Mistake | Example | Fix |
|---------|---------|-----|
| Skip understanding | Immediately code | Spend 5 min asking questions |
| Jump to code | No state design | Draw diagram, explain approach |
| Forget edge cases | Crash on empty input | Test: empty, single, boundary |
| Poor variable names | `f(x, a, b)` | Use clear names: `getMaxPath(node, left, right)` |
| No complexity analysis | "I don't know" | Always estimate: count loops |
| Defensive coding | Excessive checks | Only what's necessary per constraints |
| Not asking follow-ups | Assume interview is done | "Want me to optimize further?" |
| Silent coding | Minutes of quiet | Explain: "I'm thinking about..." |

---

## 6. RESOURCES BY PATTERN

### Organization of DSA System

Your complete DSA system has these key files:

#### Core Reference Files
- **PATTERNS_QUICK_REFERENCE.md** - 23 patterns one-page overview
- **04_QUICK_REFERENCE.md** - Framework quick card + templates
- **PATTERNS_WITH_EXAMPLES.md** - Code examples for each pattern
- **Auxiliary_DS_Design.md** - Data structure decision matrix

#### Learning & Roadmap Files
- **02_LEARNING_ROADMAP.md** - 6-8 week detailed study plan
- **01_START_HERE.md** - Action checklist for today/this week
- **03_PROBLEM_INDEX.md** - Navigate problems by week/pattern/difficulty

#### Problem Collections (80+ problems)
- **common_pattern1.md** - 13 foundational problems (100% annotated)
- **common_pattern2.md** - 56+ comprehensive problems
- **common_pattern3.md** - 11 problems focusing on Sliding Window & Prefix Sum

#### Framework Guides
- **框架指南_Framework_Guide.md** - Complete 7-step framework explanation
- **模式框架应用指南.md** - How to apply framework to each pattern
- **框架自助应用工具包.md** - DIY problem analysis toolkit

#### Specialized Topics
- **06_BITMASK_TECHNIQUES.md** - Bit manipulation deep dive
- **hire_manager.md** - Interview preparation guide

---

### How to Use This Guide

**Looking for pattern explanation?**
1. Search this file for pattern name (Ctrl+F)
2. Find summary + learning points
3. Check RESOURCES BY PATTERN section below for detailed guides

**Need to solve a specific problem?**
1. Read problem statement carefully
2. Use decision tree to identify pattern
3. Check corresponding pattern section for example
4. Look in common_pattern1/2/3.md for similar problems

**Want to improve at interviews?**
1. Read INTERVIEW TIPS section repeatedly
2. Practice time allocation (5-3-10-15-5-7 split)
3. Do mock interviews with timer
4. Review mistakes systematically

**Need quick reference while coding?**
1. Use 04_QUICK_REFERENCE.md for framework card
2. Use this file's QUICK PATTERN CHEAT SHEET
3. Check template code sections

---

### Pattern → LeetCode Problems Mapping

#### Pattern 1: Prefix Sum
- LC560 Subarray Sum Equals K (Medium)
- LC525 Contiguous Array (Medium)
- LC974 Subarray Sums Divisible by K (Medium)
- LC523 Continuous Subarray Sum (Medium)
- LC1480 Running Sum of 1d Array (Easy)

#### Pattern 2: Sliding Window
- LC3 Longest Substring Without Repeating Characters (Medium)
- LC76 Minimum Window Substring (Hard)
- LC209 Minimum Size Subarray Sum (Medium)
- LC424 Longest Repeating Character Replacement (Medium)
- LC159 Longest Substring with At Most Two Distinct Characters (Medium)

#### Pattern 3: Two Pointers
- LC167 Two Sum II (Easy)
- LC11 Container With Most Water (Medium)
- LC15 3Sum (Medium)
- LC42 Trapping Rain Water (Hard)
- LC125 Valid Palindrome (Easy)

#### Pattern 4: Binary Search
- LC704 Binary Search (Easy)
- LC33 Search in Rotated Sorted Array (Medium)
- LC153 Find Minimum in Rotated Sorted Array (Medium)
- LC35 Search Insert Position (Easy)
- LC74 Search a 2D Matrix (Medium)

#### Pattern 5: Monotonic Stack
- LC739 Daily Temperatures (Medium)
- LC496 Next Greater Element I (Easy)
- LC84 Largest Rectangle in Histogram (Hard)
- LC42 Trapping Rain Water (Hard)
- LC901 Online Stock Span (Medium)

#### Pattern 6: DP on Sequence
- LC300 Longest Increasing Subsequence (Medium)
- LC1143 Longest Common Subsequence (Medium)
- LC198 House Robber (Medium)
- LC740 Delete and Earn (Medium)
- LC1025 Divisor Game (Easy)

#### Pattern 7: DP on 2D
- LC1143 Longest Common Subsequence (Medium)
- LC72 Edit Distance (Hard)
- LC62 Unique Paths (Medium)
- LC1143 Longest Common Subsequence (Medium)
- LC121 Best Time to Buy and Sell Stock (Easy)

#### Pattern 8: BFS
- LC102 Binary Tree Level Order Traversal (Medium)
- LC1091 Shortest Path in Binary Matrix (Medium)
- LC127 Word Ladder (Hard)
- LC752 Open the Lock (Medium)
- LC675 Cut Off Trees for Golf Event (Hard)

#### Pattern 9: DFS
- LC200 Number of Islands (Medium)
- LC695 Max Area of Island (Medium)
- LC130 Surrounded Regions (Medium)
- LC133 Clone Graph (Medium)
- LC399 Evaluate Division (Medium)

#### Pattern 10: Backtracking
- LC46 Permutations (Medium)
- LC47 Permutations II (Medium)
- LC78 Subsets (Medium)
- LC90 Subsets II (Medium)
- LC17 Letter Combinations of a Phone Number (Medium)

#### Pattern 11: Union Find
- LC547 Number of Provinces (Medium)
- LC684 Redundant Connection (Medium)
- LC200 Number of Islands (Medium)
- LC128 Longest Consecutive (Hard)
- LC990 Satisfiability of Equality Equations (Medium)

#### Pattern 12: Greedy
- LC55 Jump Game (Medium)
- LC45 Jump Game II (Medium)
- LC134 Gas Station (Medium)
- LC121 Best Time to Buy and Sell Stock (Easy)
- LC1090 Largest Values From Labels (Medium)

#### Pattern 13: Dijkstra
- LC743 Network Delay Time (Medium)
- LC787 Cheapest Flights Within K Stops (Medium)
- LC1514 Path with Maximum Probability (Medium)
- LC2290 Minimum Obstacle Removal to Reach Corner (Hard)

#### Pattern 14: DP Interval
- LC1039 Minimum Score Triangulation of Polygon (Medium)
- LC312 Burst Balloons (Hard)
- LC1547 Minimum Cost to Cut a Stick (Hard)

#### Pattern 15: Bitmask DP
- LC847 Shortest Path Visiting All Nodes (Hard)
- LC864 Shortest Path to Get All Keys (Hard)
- LC1595 Minimum Cost to Connect Two Groups of Points (Hard)

---

## 7. REFERENCE: 7-STEP FRAMEWORK QUICK RECAP

Every problem can be analyzed using this universal framework:

### 1. MODELING
**Question:** What is the problem's mathematical structure?
- Graph? Tree? Array? State space?
- What are nodes and edges?

### 2. STATE DEFINITION
**Question:** What is the minimum information needed to represent a partial solution?
- State = (?)
- Why these variables?

### 3. AUXILIARY STRUCTURES
**Question:** What data structures support frequent operations?
- What are we querying/updating most?
- Which DS optimizes that?

### 4. STATE TRANSITIONS
**Question:** How do we go from one state to another?
- Rules for transitions
- Constraints on transitions

### 5. ALGORITHM SELECTION
**Question:** Which algorithm explores the state space efficiently?
- BFS/DFS? DP? Greedy? Binary Search?
- Why is this optimal?

### 6. COMPLEXITY ANALYSIS
**Question:** Will our solution pass within constraints?
- Time complexity: O(?)
- Space complexity: O(?)
- Constraint check: n ≤ ?

### 7. IMPLEMENTATION
**Question:** How do we code the solution cleanly?
- Template/skeleton
- Edge case handling
- Testing

---

## Final Checklist Before Interview

```
□ Reviewed 04_QUICK_REFERENCE.md
□ Understand 23 patterns and can recognize each
□ Can explain 7-step framework clearly
□ Did 3 mock interviews with timer
□ Know time allocation: 5-3-10-15-5-7
□ Practiced explaining approach (not just coding)
□ Reviewed common mistakes
□ Know 3 code templates (BFS, DP, Sliding Window)
□ Can identify and optimize complexity
□ Feel confident about patterns 1-12
□ Have list of follow-up questions ready
```

---

## Quick Facts to Remember

- **Prefix Sum:** O(n) preprocessing → O(1) queries
- **Sliding Window:** O(n) time, O(1-n) space depending on variant
- **Binary Search:** Works on sorted/monotonic data
- **BFS:** Always finds shortest path (unweighted)
- **DFS:** Natural for tree traversal and connectivity
- **DP:** Optimal substructure + overlapping subproblems
- **Union Find:** O(α(n)) ≈ O(1) with path compression
- **Dijkstra:** Shortest path with weights (no negative edges)
- **Greedy:** Requires proof that local optimum → global optimum

---

## Document Version & Updates

**Version:** 1.0  
**Created:** June 9, 2026  
**Purpose:** Comprehensive reference for DSA interview preparation  
**Coverage:** 23 algorithm patterns, 80+ problems, 7-step framework  
**Audience:** Interview candidates preparing for technical interviews  

**To use this guide:**
1. Start with LEARNING PATH section
2. Use QUICK PATTERN CHEAT SHEET for reference
3. Follow DAILY STUDY PLAN structure
4. Practice using PROBLEM-SOLVING ROADMAP
5. Study INTERVIEW TIPS before interviews
6. Cross-reference with problem collections and framework guides

---

**Good luck with your DSA interview prep! With consistent effort following this guide, you'll master all 23 patterns and be ready to confidently handle any interview problem.**
