# 📚 DSA Interview Prep - Complete Study Guide

**Your companion guide for mastering 23 patterns across 60+ LeetCode problems**

---

## 🚀 QUICK START

**New to DSA?** Start here:
1. Read **Learning Path** (below)
2. Pick **Easy** patterns first
3. Practice 2-3 problems per pattern
4. Use **Decision Tree** to identify patterns in new problems

**Preparing for interview?** 
1. Review **Pattern Cheat Sheet**
2. Practice **Medium** problems
3. Study **Interview Tips**
4. Do timed practice (45 min per problem)

---

## 📊 LEARNING PATH (By Difficulty)

### **TIER 1: FOUNDATIONS** (Easy - Week 1-2)
Essential patterns everyone needs to know

| Pattern | LeetCode | Why Learn First | Time |
|---------|----------|-----------------|------|
| Sliding Window | LC3, LC76 | Most intuitive, builds array thinking | 4h |
| Prefix Sum | LC560, LC303 | Precomputation trick, speeds up queries | 2h |
| Two Pointers | LC167, LC11 | Sorted array optimization | 2h |
| Binary Search | LC875, LC300 | Optimization technique | 3h |

**Key Insight:** These 4 patterns solve ~20% of interview problems and are foundations for harder patterns.

---

### **TIER 2: GRAPHS & TREES** (Medium - Week 2-3)
Graph traversal is critical for tree/graph problems

| Pattern | LeetCode | Why This Tier | Time |
|---------|----------|---------------|------|
| BFS | LC200, LC994 | Shortest path (unweighted), level-order | 6h |
| DFS | LC200, LC94 | Connectivity, traversal, backtracking foundation | 5h |
| Union Find | LC323, LC684 | Component grouping, cycle detection | 3h |
| Topological Sort | LC207 | Dependency ordering | 2h |

**Key Insight:** Understand these 4, you can solve 30% of medium problems. BFS/DFS are essential for backtracking.

---

### **TIER 3: OPTIMIZATION** (Medium-Hard - Week 3-4)
Make solutions faster and more elegant

| Pattern | LeetCode | Why This Tier | Time |
|---------|----------|---------------|------|
| Dynamic Programming | LC70, LC121, LC198 | Overlapping subproblems, memoization | 10h |
| Greedy | LC55, LC134 | Local optimal → global optimal | 3h |
| Monotonic Stack | LC739, LC496 | Next greater/smaller efficiently | 3h |
| Heap | LC215, LC23 | Top K, priority ordering | 3h |

**Key Insight:** DP is the hardest but most rewarding. Monotonic Stack/Heap are elegant optimizations.

---

### **TIER 4: ADVANCED** (Hard - Week 4-5)
Rare patterns, impressive to solve in interview

| Pattern | LeetCode | Why This Tier | Time |
|---------|----------|---------------|------|
| Interval DP | LC312 | Complex optimization, practice thinking | 4h |
| Segment Tree | LC307 | Range queries, advanced data structure | 4h |
| Trie | LC208, LC212 | String searching, prefix optimization | 3h |
| Bitmask DP | LC847 | Exponential state space, TSP | 3h |

**Key Insight:** These patterns appear in 5-10% of interviews. Solving 1-2 impresses interviewers significantly.

---

## 🎯 PATTERN CHEAT SHEET

| # | Pattern | When to Use | Time | Space | Key Insight |
|---|---------|-------------|------|-------|------------|
| 1 | Sliding Window | "longest/shortest subarray" | O(n) | O(1) | Two pointers, never backtrack left |
| 2 | Prefix Sum | "range sum", "subarray sum" | O(n) | O(n) | Precompute cumulative, use map for O(1) lookup |
| 3 | Two Pointers | "pair", "sorted array" | O(n) | O(1) | Move toward center, monotonic space |
| 4 | Fast/Slow Pointer | "cycle detection", "linked list" | O(n) | O(1) | Meeting point → cycle exists, reset → find entrance |
| 5 | Greedy | "maximize/minimize", "local optimal" | O(n) | O(1) | **Must prove works**, not always correct |
| 6 | Binary Search | "minimize maximum", "threshold" | O(log n) | O(1) | Search answer space, not data |
| 7 | BFS | "shortest path (unweighted)" | O(V+E) | O(V) | Layer-by-layer, first reach = shortest |
| 8 | DFS | "connectivity", "traversal" | O(V+E) | O(h) | Recursion naturally backtracks |
| 9 | Backtracking | "all possibilities", "permutation" | O(n!) | O(n) | **Undo is critical**, reuse path list |
| 10 | Heap | "top k", "k-th largest" | O(n log k) | O(k) | Min-heap of size k, pop smallest |
| 11 | Dijkstra | "shortest path (weighted, non-neg)" | O((V+E) log V) | O(V) | Greedy + relaxation, pick closest node |
| 12 | Monotonic Stack | "next greater/smaller" | O(n) | O(n) | Maintain decreasing stack, pop when violated |
| 13 | Trie | "prefix search", "autocomplete" | O(m) | O(26m) | Shared prefixes → compressed tree |
| 14 | Union Find | "connected components" | O(α(n)) | O(n) | Path compression → nearly O(1) |
| 15 | 1D DP | "optimal value", "overlapping subproblems" | O(n) | O(1) | Only need previous state |
| 16 | DP on Graph | "graph traversal", "dependencies" | O(V+E) | O(V) | Topological sort + DP |
| 17 | Interval DP | "range optimization" | O(n³) | O(n²) | Try all split points k |
| 18 | State Machine DP | "stock trading", "state transitions" | O(n) | O(1) | Track explicit states |
| 19 | Bitmask DP | "visit all nodes", "TSP" | O(2^n × n) | O(2^n × n) | Bit = visited node |
| 20 | Memoized DFS | "recursive structure" | Depends | Depends | Natural recursion + cache |
| 21 | Monotonic Deque | "sliding window max" | O(n) | O(k) | Remove both ends (expired & smaller) |
| 22 | Segment Tree | "range query + update" | O(log n) | O(n) | Tree aggregate, update path to root |
| 23 | Interval Sweep | "overlapping intervals" | O(n log n) | O(n) | Events + sort, no pairwise check |

---

## 🗺️ PROBLEM-SOLVING DECISION TREE

```
See problem keywords?

├─ "longest/shortest subarray/substring"?
│  └─ SLIDING WINDOW (LC3, LC76)
│
├─ "sum", "range", "continuous"?
│  └─ PREFIX SUM (LC560, LC303)
│
├─ "shortest path" + "unweighted"?
│  └─ BFS (LC200, LC994)
│
├─ "shortest path" + "weighted, non-negative"?
│  └─ DIJKSTRA (LC787)
│
├─ "optimal value" + "overlapping subproblems"?
│  └─ DYNAMIC PROGRAMMING (LC70, LC121)
│
├─ "all possibilities" + "permutation/combination"?
│  └─ BACKTRACKING (LC46, LC39, LC78)
│
├─ "next greater/smaller", "histogram"?
│  └─ MONOTONIC STACK (LC739, LC496)
│
├─ "cycle", "linked list", "middle"?
│  └─ FAST/SLOW POINTER (LC141, LC202)
│
├─ "prefix search", "dictionary", "autocomplete"?
│  └─ TRIE (LC208, LC212)
│
├─ "top k", "k-th largest"?
│  └─ HEAP (LC215, LC347)
│
├─ "connected components", "cycle in graph"?
│  └─ UNION FIND (LC323, LC684)
│
├─ "two numbers", "pair", "sorted array"?
│  └─ TWO POINTERS (LC167, LC11)
│
└─ "maximize/minimize", "local optimal"?
   └─ GREEDY (LC55, LC134)
```

---

## 📅 5-WEEK STRUCTURED STUDY PLAN

### **Week 1: Array Fundamentals**
**Goal:** Master sliding window, prefix sum, two pointers

**Monday-Tuesday:** Sliding Window
- LC643 (Fixed size)
- LC3 (Variable size)
- LC424 (At most K)
- LC76 (Minimum window)
- Practice: Code 2 solutions from scratch, no looking

**Wednesday-Thursday:** Prefix Sum
- LC303 (1D)
- LC560 (HashMap trick)
- LC304 (2D)
- LC525 (Balance)
- Insight: Precomputation trades time for space

**Friday:** Two Pointers
- LC167, LC11, LC42
- Practice: Identify sortedness, monotonicity

**Weekend:** Review + Practice
- Solve 3 mixed problems (sliding window vs prefix sum vs two pointers)

---

### **Week 2: Graph Traversal**
**Goal:** Master BFS, DFS, recognize graph patterns

**Monday-Tuesday:** BFS
- LC200 (Grid)
- LC133 (Graph)
- LC752 (State space)
- LC994 (Multi-source)
- Key: Level-by-level = shortest path

**Wednesday-Thursday:** DFS
- LC200 (Grid DFS)
- LC94 (Tree traversal)
- LC323 (Components)
- LC79 (Word search)
- Key: Recursion naturally backtracks

**Friday:** Union Find
- LC323 (Components)
- LC684 (Cycle detection)
- Insight: Path compression makes it nearly O(1)

**Weekend:** Graph Mixed
- Mix BFS, DFS, Union Find problems

---

### **Week 3: Backtracking & Optimization**
**Goal:** Master backtracking, greedy, monotonic stack

**Monday-Tuesday:** Backtracking
- LC46 (Permutation)
- LC39 (Combination)
- LC78 (Subset)
- LC79 (Word Search)
- **Critical:** Undo is the key, reuse path list

**Wednesday:** Greedy + Monotonic Stack
- LC55 (Jump Game)
- LC739 (Daily Temperatures)
- LC496 (Next Greater)
- Insight: Greedy doesn't always work (prove it!)

**Thursday-Friday:** Binary Search + Heap
- LC875 (Binary search on answer)
- LC215 (Top K with heap)
- LC23 (Merge K lists)

**Weekend:** Medium mixed
- Harder combinations of patterns

---

### **Week 4: Dynamic Programming**
**Goal:** Understand DP fundamentals, multiple variants

**Monday-Tuesday:** 1D DP
- LC70 (Climbing stairs)
- LC198 (House robber)
- LC121 (Stock trading)
- Pattern: `dp[i] = optimal at position i`

**Wednesday:** DP Variants
- LC416 (0-1 Knapsack)
- LC322 (Coin change)
- LC300 (LIS)
- Insight: State definition is key

**Thursday:** State Machine DP
- LC121, LC309 (Stock with cooldown)
- Key: Explicit states, clear transitions

**Friday:** Interval DP
- LC312 (Burst balloons)
- Key: Try all split points

**Weekend:** DP mixed problems

---

### **Week 5: Advanced + Mixed Practice**
**Goal:** Solve hard problems, interview simulation

**Monday-Tuesday:** Trie + Segment Tree
- LC208 (Trie implementation)
- LC212 (Word search II)
- LC307 (Range sum mutable)
- LC315 (Count smaller - Fenwick tree)

**Wednesday-Thursday:** Bitmask DP + Advanced
- LC847 (TSP)
- LC864 (Keys + gates)
- Mixed hard patterns

**Friday:** Interview Simulation
- Timed problem solving (45 min per problem)
- Mix of all patterns

**Weekend:** Review + Weak Areas
- Retake problems where you struggled
- Review conceptual ideas

---

## 💡 INTERVIEW TIPS

### ⏱️ **Time Management in Interview**
```
45-minute interview, 1 problem typically

0-5 min:   Understand problem (ask clarifying questions)
5-15 min:  Think of approach (multiple if possible)
15-35 min: Code solution
35-40 min: Test with examples
40-45 min: Optimize / discuss complexity
```

### 🗣️ **How to Explain Your Approach**
1. **Name the pattern:** "This is a sliding window problem"
2. **Explain the intuition:** "We maintain a window and shrink when invalid"
3. **State the complexity:** "O(n) time, O(1) space"
4. **Then code:** Let code speak

### 🚀 **How to Optimize After First Solution**
1. **Ask:** "Can we optimize space or time?"
2. **Common optimizations:**
   - Remove extra data structure
   - Use precomputation
   - Combine two passes into one
   - Use bit manipulation

### 🆘 **What to Do If You Get Stuck**
1. **First 10 min stuck?** Ask interviewer for hint
2. **Explain your thinking:** "I'm thinking about... but..." → Get feedback
3. **Start coding partial solution:** Show you can code, even if not optimal
4. **Never go silent** → Talk through your thought process

### ✅ **What Impresses Interviewers**
- ✅ Identify pattern quickly
- ✅ Discuss trade-offs (time vs space)
- ✅ Test your own code with examples
- ✅ Optimize without being asked
- ✅ Ask clarifying questions
- ✅ Explain clearly (not just code)

### ❌ **What Hurts You**
- ❌ Jump into coding without thinking
- ❌ Forget edge cases
- ❌ Claim your solution works without testing
- ❌ Ignore space complexity
- ❌ Go silent when stuck
- ❌ Write messy code

---

## 🔗 CROSS-REFERENCES

| Need | File | Purpose |
|------|------|---------|
| **Conceptual Ideas** | PATTERNS_QUICK_REFERENCE.md | Core insight + real-world analogy for each pattern |
| **Real Problems** | PATTERNS_WITH_EXAMPLES.md | 40+ LeetCode problems with full statements + Java code |
| **Detailed Walkthroughs** | common_pattern1/2/3.md | 60 problems with conversational explanations |
| **Interview Prep** | This file | Learning path, tips, decision tree |

---

## 📈 PROGRESS TRACKER

Print this and track your progress:

```
TIER 1: FOUNDATIONS
□ Sliding Window (LC3, LC76, LC424, LC76, LC239)
□ Prefix Sum (LC560, LC303, LC304, LC525)
□ Two Pointers (LC167, LC11, LC42)
□ Binary Search (LC875, LC300)

TIER 2: GRAPHS
□ BFS (LC200, LC133, LC752, LC994, LC847)
□ DFS (LC94, LC323, LC207, LC733, LC79)
□ Union Find (LC323, LC684)

TIER 3: OPTIMIZATION
□ DP (LC70, LC121, LC198, LC416, LC322, LC300)
□ Greedy (LC55, LC134, LC452)
□ Monotonic Stack (LC739, LC496)
□ Heap (LC215, LC23, LC295)

TIER 4: ADVANCED
□ Trie (LC208, LC212)
□ Interval DP (LC312)
□ Segment Tree (LC307)
□ Bitmask DP (LC847, LC864)
```

---

## 🎯 SUCCESS METRICS

By end of 5 weeks, you should:
- ✅ Recognize patterns instantly (< 1 min)
- ✅ Code 80% of problems in 30-40 minutes
- ✅ Explain your approach clearly
- ✅ Optimize solutions after first pass
- ✅ Handle edge cases proactively

**This is a realistic, achievable goal!**

---

**Good luck! You've got this! 🚀**
