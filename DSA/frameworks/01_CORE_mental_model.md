# 🧠 DSA Problem-Solving Mental Model

**Build the framework that works for original questions, not just memorized problems**

---

## 📍 The Core Insight

### Current Approach (Pattern Recognition)
```
See Question
    ↓
Recognize Pattern (or panic if unfamiliar)
    ↓
Apply Algorithm
```

**Problem:** Works only if you've seen it. Original questions = panic.

### Google L5/L6 Approach (Systematic Modeling)
```
Understand Problem
    ↓
Build the World Model
    ↓
Identify State & Goal
    ↓
Identify Transitions
    ↓
Choose Algorithm
```

**Advantage:** Works for ANY problem, even original questions.

---

## 🎯 The Universal 6-Step Framework

Apply this framework to **ANY** DSA problem:

### Step 1: Clarify the Problem

Before jumping to code, ask:

**Questions to ask:**
- ✅ What is the input? (array, string, graph, etc.)
- ✅ What is the output? (boolean, number, list, etc.)
- ✅ What are the constraints? (size, values, edge cases)
- ✅ Any special assumptions? (unique elements, sorted, etc.)
- ✅ Can I ask clarifying questions? (interview tip)

**Example: Coin Change**
```
Input: array of coin denominations, target amount
Output: minimum number of coins
Constraints: positive integers, unlimited supply of each coin
Goal: minimize count
```

**Time spent:** 1-2 minutes (worth it!)

---

### Step 2: Build the World

**This is the most important step.**

Ask: *What are the entities and relationships in this problem?*

| Problem | Entity | Relationship |
|---------|--------|--------------|
| Course Schedule | Course | Dependency |
| Word Ladder | Word | One-letter transformation |
| Coin Change | Amount | Coin transition |
| Bus Routes | Station | Reachability via bus |
| Climbing Stairs | Step | Can move 1 or 2 steps |
| House Robber | House | Adjacent constraint |
| Longest Increasing Subsequence | Element | Increasing relationship |

**Why this matters:**
- Clarifies what a "state" actually represents
- Shows structure of the problem
- Hints at which algorithm fits
- Helps communicate in interview

**Example: Course Schedule**
```
Entity: Courses
Relationship: Dependency (Course A depends on Course B)
Graph representation: 
  - Courses = nodes
  - Dependencies = edges
  - Can take courses if no cycles
```

**Time spent:** 2-3 minutes

---

### Step 3: Identify the Goal

**Ask: What am I ultimately trying to find?**

| Goal | Algorithm Hint | Example |
|------|---|---|
| **Any solution exists?** | DFS/BFS/Backtracking | Valid parentheses, word break |
| **Shortest path?** | BFS (unweighted) or Dijkstra (weighted) | Word ladder, shortest distance |
| **Longest path?** | DP or DFS | Longest increasing subsequence |
| **Maximum value?** | DP or Greedy | Max profit, house robber |
| **Minimum cost?** | DP or Dijkstra | Coin change, min cost climbing |
| **Count ways?** | DP with addition | Climbing stairs, unique paths |
| **Reachable?** | DFS/BFS | Can reach end, connected components |
| **Optimal ordering?** | Topological sort or Greedy | Course schedule, task scheduling |

**This step alone often determines the algorithm!**

**Example: Coin Change**
```
Goal: Minimize number of coins
→ This is a MINIMIZATION problem
→ Could use DP (optimal substructure) or Greedy (needs proof)
```

**Time spent:** 1 minute

---

### Step 4: Identify the State

**Ask: What uniquely describes my current situation?**

State = minimum information needed to solve from this point forward

| Pattern | State | Definition |
|---------|-------|-----------|
| **DP** | Current subproblem | dp[i] = solution for first i elements |
| **BFS/DFS** | Current node | Which node am I at? |
| **Sliding Window** | Window boundaries | [left, right] pointers |
| **Dijkstra** | Current node + distance | (distance, node) |
| **Backtracking** | Current path | Chosen elements so far |
| **2D DP** | Position | (row, col) |
| **State Machine DP** | Position + state | (day, holding/sold) |

**Example: Coin Change**
```
State: dp[amount] = minimum coins needed to make this amount
Base case: dp[0] = 0 (0 coins needed for amount 0)
For each amount i, check all coins: dp[i] = min(dp[i-coin] + 1)
```

**Example: Word Ladder**
```
State: Current word in transformation
Start: Initial word
Goal: Target word
Transition: One-letter transformation (valid if in dictionary)
→ This is BFS because we want shortest path
```

**Time spent:** 2-3 minutes

---

### Step 5: Identify State Transitions

**Ask: How do I move from one state to the next?**

Transitions show how problems decompose and relate to each other.

| Pattern | Transition | Example |
|---------|-----------|---------|
| **Linear DP** | dp[i] from dp[i-1], dp[i-2], ... | Climbing stairs: dp[i] = dp[i-1] + dp[i-2] |
| **Graph** | Current node → neighbors | Expand to adjacent nodes |
| **Sliding Window** | Left/right pointers move | Expand right, shrink left when invalid |
| **2D DP** | From top, left, or diagonal | Unique paths: dp[i][j] = dp[i-1][j] + dp[i][j-1] |
| **Backtracking** | Choose → Explore → Undo | DFS of decision tree |
| **Shortest Path** | Current node → neighbors (with cost) | Dijkstra: relax edges |

**Example: Coin Change**
```
Transition: To make amount i:
  - Use coin c1: need dp[i-c1] more coins → total dp[i-c1] + 1
  - Use coin c2: need dp[i-c2] more coins → total dp[i-c2] + 1
  - ...
  - Choose minimum: dp[i] = min(dp[i-c] + 1 for all valid coins)
```

**Time spent:** 2-3 minutes

---

### Step 6: Classify the Problem (Only Now!)

**Once you understand State and Transitions, THEN classify.**

Ask: *What type of world is this?*

## Problem Worlds

### 🔗 Dependency World
**Keywords:** prerequisite, dependency, workflow, build order, schedule

**Algorithms:** Topological sort, DFS (cycle detection)

**Examples:**
- Course schedule (courses depend on prerequisites)
- Build order (tasks have dependencies)
- Alien dictionary (character ordering)

**Solution pattern:**
1. Detect cycles (invalid order)
2. Perform topological sort
3. Return order or report impossible

### 🌐 Reachability World
**Keywords:** can reach?, connected?, path exists?, same component?

**Algorithms:** DFS, BFS, Union Find

**Examples:**
- Islands in grid (connected components)
- Bipartite graph (can partition?)
- Number of connected components

**Solution pattern:**
1. Explore from each unvisited node
2. Mark all reachable nodes
3. Count components or answer reachability

### 📍 Shortest Path World
**Keywords:** minimum steps, minimum cost, shortest, least distance

**Algorithms:** BFS (unweighted), Dijkstra (weighted), A*

**Decision tree:**
```
Is it shortest path?
  ├─ Unweighted graph? → BFS
  ├─ Weighted graph, non-negative? → Dijkstra
  ├─ Weighted graph, with negatives? → Bellman-Ford
  └─ Special heuristic known? → A*
```

**Examples:**
- Word ladder (BFS)
- Network delay time (Dijkstra)
- Flight routes with cost (Dijkstra)

**Solution pattern:**
1. Initialize distance array
2. Use priority queue to process closest next
3. Relax edges (try shorter paths)
4. Return shortest distance

### ⚙️ Optimization World
**Keywords:** max, min, count, best, optimal, most, least

**Algorithms:** DP, Greedy

**Decision tree:**
```
Is it optimization?
  ├─ Overlapping subproblems? → DP
  ├─ Can I prove greedy locally → global? → Greedy
  └─ Not sure? → Try DP (safer)
```

**DP Variants:**
- Linear DP: House robber, climbing stairs
- 2D DP: Unique paths, edit distance
- Knapsack: Coin change, partition sum
- State Machine: Stock trading
- Interval: Burst balloons

**Examples:**
- Coin change (minimize coins) → DP
- House robber (maximize profit) → DP
- Jump game (minimize jumps) → Greedy or DP

**Solution pattern:**
1. Define state clearly
2. Find recurrence relation
3. Identify base cases
4. Fill table or use memoization

---

## 🗺️ The Complete Mental Model (One-Page Cheat Sheet)

```
┌─────────────────────────────────────────────────────────┐
│          DSA PROBLEM-SOLVING FRAMEWORK                  │
├─────────────────────────────────────────────────────────┤
│                                                         │
│ Step 1: CLARIFY                                         │
│ • Input? Output? Constraints? Assumptions?              │
│                                                         │
│ Step 2: WORLD MODEL                                     │
│ • What are entities? What are relationships?            │
│ • Draw a simple diagram                                 │
│                                                         │
│ Step 3: GOAL                                            │
│ • Any solution? Shortest? Longest? Count? Max? Min?     │
│ • This often determines algorithm!                      │
│                                                         │
│ Step 4: STATE                                           │
│ • What uniquely describes current situation?            │
│ • What info do I need to continue?                      │
│                                                         │
│ Step 5: TRANSITION                                      │
│ • How do I move from one state to next?                 │
│ • How do subproblems relate?                            │
│                                                         │
│ Step 6: CLASSIFY                                        │
│ • Dependency World?      → Topological Sort             │
│ • Reachability World?    → DFS/BFS/Union Find           │
│ • Shortest Path World?   → BFS/Dijkstra                 │
│ • Optimization World?    → DP/Greedy                    │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## 💡 Real Problem Walkthroughs

### Example 1: Word Ladder

**Step 1: Clarify**
```
Input: startWord, endWord, wordList (dictionary)
Output: Shortest transformation sequence (or length)
Constraint: One letter change per step
```

**Step 2: Build the World**
```
Entity: Words
Relationship: One-letter transformation
Model: Each word is a node
       Edge exists if one letter different
       Valid if both words in dictionary
```

**Step 3: Goal**
```
Goal: Shortest path from start to end word
→ This is a SHORTEST PATH problem
→ Unweighted graph (each step = 1)
→ Algorithm: BFS
```

**Step 4: State**
```
State: Current word
Start: startWord
Goal: endWord
```

**Step 5: Transition**
```
From current word:
  - Try changing each letter (a-z, 26 possibilities)
  - Check if result is in dictionary
  - If yes and unvisited, add to queue
```

**Step 6: Classify**
```
This is SHORTEST PATH WORLD
→ Use BFS
→ Process level by level
→ First time reaching endWord = shortest path
```

**Code:**
```java
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> words = new HashSet<>(wordList);
        if (!words.contains(endWord)) return 0;
        
        Queue<String> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        q.offer(beginWord);
        visited.add(beginWord);
        int level = 1;
        
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String word = q.poll();
                if (word.equals(endWord)) return level;
                
                // Try changing each letter
                for (String neighbor : getNeighbors(word, words)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        q.offer(neighbor);
                    }
                }
            }
            level++;
        }
        
        return 0;
    }
    
    private List<String> getNeighbors(String word, Set<String> words) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = word.toCharArray();
        
        for (int i = 0; i < chars.length; i++) {
            char original = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                chars[i] = c;
                String neighbor = new String(chars);
                if (words.contains(neighbor)) {
                    neighbors.add(neighbor);
                }
            }
            chars[i] = original;
        }
        
        return neighbors;
    }
}
```

---

### Example 2: House Robber

**Step 1: Clarify**
```
Input: Array of house values
Output: Maximum money robbed
Constraint: Can't rob adjacent houses
```

**Step 2: Build the World**
```
Entity: Houses
Relationship: Adjacent constraint (can't rob next to each other)
Model: Linear sequence with constraint
```

**Step 3: Goal**
```
Goal: Maximize money
→ This is OPTIMIZATION WORLD
→ Algorithm: DP (overlapping subproblems)
```

**Step 4: State**
```
State: dp[i] = maximum money robbing houses [0...i]
```

**Step 5: Transition**
```
For each house i:
  Option 1: Rob house i → dp[i-2] + value[i]
  Option 2: Skip house i → dp[i-1]
  Choose max: dp[i] = max(dp[i-1], dp[i-2] + value[i])
```

**Step 6: Classify**
```
This is OPTIMIZATION WORLD
→ Linear DP
→ Recurrence: dp[i] = max(skip, rob)
```

**Code:**
```java
class Solution {
    public int rob(int[] nums) {
        if (nums.length == 1) return nums[0];
        
        int prev2 = 0;  // dp[i-2]
        int prev1 = 0;  // dp[i-1]
        
        for (int num : nums) {
            int curr = Math.max(prev1, prev2 + num);
            prev2 = prev1;
            prev1 = curr;
        }
        
        return prev1;
    }
}
```

---

## 🚀 How This Framework Beats Memorization

| Scenario | Memorization Approach | Framework Approach |
|----------|----------------------|-------------------|
| **Seen problem** | ✅ Fast | ✅ Fast (validates) |
| **Unseen problem** | ❌ Panic | ✅ Systematic |
| **Problem variant** | ❌ Confusion | ✅ Adapt systematically |
| **Original question** | ❌ Lost | ✅ Guided exploration |

---

## 📋 Interview Checklist

Before you start coding:

- [ ] **Step 1:** Clarified input/output/constraints?
- [ ] **Step 2:** Built mental model of world?
- [ ] **Step 3:** Identified goal clearly?
- [ ] **Step 4:** Defined state precisely?
- [ ] **Step 5:** Understood state transitions?
- [ ] **Step 6:** Classified the problem world?
- [ ] **Step 7:** Chose algorithm confidently?

Only then: Start coding!

---

## 🎯 Practice with This Framework

When solving any problem:

1. **Take 5 minutes** to go through all 6 steps
2. **Write down answers** on paper or whiteboard
3. **Communicate** while you do this (interview practice!)
4. **Only then** start coding

This investment of 5 minutes upfront:
- ✅ Catches mistakes early
- ✅ Makes code writing faster
- ✅ Impresses interviewer
- ✅ Works for original questions

---

## 💪 Why This Works (System Design Parallel)

You already do this in System Design:

```
Problem
  ↓
Requirements & Constraints
  ↓
High-Level Model
  ↓
Component Design
  ↓
Technology Choice
```

DSA is the same:

```
Problem
  ↓
Clarify & Model
  ↓
State & Goal
  ↓
Transitions
  ↓
Algorithm Choice
```

**You already have this mindset. Apply it here.**

---

## 🏆 The Result

Once this framework becomes automatic:

1. ✅ You can solve ANY problem, not just memorized ones
2. ✅ You can explain your thinking clearly
3. ✅ Original questions become solvable
4. ✅ You interview at Google level

**This is your competitive advantage.**

---

## 🔍 Quick Decision Trees

### When You See a Graph Problem

```
Graph Problem?
  ├─ Cycles exist? Check for cycles?
  │  └─ → DFS (track color: white/gray/black)
  ├─ Need order respecting dependencies?
  │  └─ → Topological Sort
  ├─ Need reachability / components?
  │  └─ → DFS or Union Find
  └─ Need shortest path?
     ├─ Unweighted?
     │  └─ → BFS
     └─ Weighted?
        └─ → Dijkstra
```

### When You See an Optimization Problem

```
Optimization?
  ├─ Can break into subproblems?
  │  └─ → DP (but which variant?)
  ├─ Can prove locally-optimal → globally-optimal?
  │  └─ → Greedy (write proof)
  └─ String/Sequence comparison?
     └─ → DP (2D: edit distance, LCS)
```

### When You See a Search Problem

```
Search Problem?
  ├─ Need exact value?
  │  └─ → Linear/Binary Search
  ├─ Multiple sources spreading?
  │  └─ → Multi-source BFS
  ├─ Shortest distance?
  │  └─ → BFS/Dijkstra
  └─ All possibilities?
     └─ → DFS/Backtracking
```

---

**Master this framework, and you'll solve problems like a Google engineer.** 🚀
