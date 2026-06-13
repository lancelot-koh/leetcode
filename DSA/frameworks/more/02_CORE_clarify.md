# 🎯 Clarification & Assumptions Guide

**Master the skill that separates Google Senior engineers from juniors**

---

## 📍 The Key Insight

### Junior Engineer Approach
```
See problem
  ↓
"What algorithm do I use?"
  ↓
Code immediately
```

### Google L5/L6 Approach
```
See problem
  ↓
"What assumptions am I making?"
  ↓
Ask 5-10 clarifying questions
  ↓
THEN code
```

**This single difference = huge impact on original questions.**

---

## 🎬 Why Clarification Matters

### Example: "Find a Path"

**Without Clarification:**
```
Task: Find a path in graph
Solution: BFS → 30 minutes of work
```

**With Clarification:**

**Q: Any valid path?**
A: "Shortest path" → Algorithm completely changes! DFS vs BFS

**Q: Return distance or actual path?**
A: "Actual path" → Need to reconstruct path, track parent pointers

**Q: Constraints on time/space?**
A: "Must be O(1) space" → Can't store visited set!

**Different answers = completely different solutions!**

---

## 🏗️ Universal Clarification Framework

### The 5-Element Checklist (I O C O)

Every problem has 5 core clarifiable elements:

| Element | What | Why |
|---------|------|-----|
| **I** | INPUT | What exactly comes in? |
| **O** | OUTPUT | What exactly goes out? |
| **C** | CONSTRAINT | What limits do I have? |
| **O** | OPTIMIZATION | What am I optimizing? |
| **E** | EDGE CASES | What breaks assumptions? |

**Mnemonic: I-O-C-O-E (remember this!)**

---

## 1️⃣ INPUT Clarification

**Ask: What exactly is coming in? What assumptions am I making about it?**

### Graph Problems

```
⚠️ KEY QUESTIONS:

1. Directed or undirected?
   → Changes algorithm completely
   Example: Cycle detection is easy in directed, different in undirected

2. Weighted or unweighted?
   → Determines shortest path algorithm
   Unweighted: BFS
   Weighted: Dijkstra, Bellman-Ford

3. Positive weights only?
   → Negative weights change algorithm
   Negative weights + cycles: Bellman-Ford becomes mandatory

4. Can there be cycles?
   → Changes approach for reachability, strongly connected components

5. Can graph be disconnected?
   → Need to handle multiple components

6. Can there be duplicate edges?
   → May need to skip duplicates

7. Self-loops possible?
   → Affects cycle detection

8. How is graph represented?
   → Adjacency list vs matrix → affects space/time tradeoffs
```

**Real Interview Example:**

```
Interviewer: "Given a graph, find the shortest path."

You: "Let me clarify a few things first:
    - Is the graph directed or undirected?
    - Are there edge weights? If so, positive only?
    - Can the graph have cycles?
    - Is it guaranteed to be connected?"

Interviewer: "Directed, weighted positive, yes cycles, disconnected."

You: "Then I'll use Dijkstra's algorithm, and handle disconnected 
     components by returning -1 if end is unreachable."
```

### Array Problems

```
⚠️ KEY QUESTIONS:

1. Can numbers be negative?
   → Affects sorting, comparison logic
   
2. Can there be duplicates?
   → May need different approach (set vs count)
   
3. Can array be empty?
   → Need base case handling
   
4. Integer overflow possible?
   → Use long instead of int
   
5. Are numbers in a specific range?
   → May enable counting sort, radix sort
   
6. Sorted or unsorted?
   → Dramatically changes algorithm
   Unsorted: O(n) scan needed
   Sorted: Binary search possible
```

### Tree Problems

```
⚠️ KEY QUESTIONS:

1. Binary tree or general tree?
   → Binary search tree vs arbitrary tree
   
2. Can tree be empty?
   → Need null/None handling
   
3. Values unique?
   → Affects search complexity
   
4. Balanced or unbalanced?
   → May need AVL/Red-Black properties
   
5. Parent pointers available?
   → Can go up or only down
```

### String Problems

```
⚠️ KEY QUESTIONS:

1. Case sensitive?
   → Affects comparison
   
2. Unicode or ASCII only?
   → Affects character handling
   
3. Can string be empty?
   → Base case
   
4. Special characters allowed?
   → Affects validation
   
5. What encoding?
   → Multi-byte characters matter
```

---

## 2️⃣ OUTPUT Clarification

**Ask: What exactly do I return? This is easy to get wrong!**

### Path Problems

| Output Type | Algorithm | Complexity |
|------------|-----------|------------|
| **Any path** | DFS | O(V+E) |
| **Shortest path** | BFS/Dijkstra | O(V+E) or O((V+E)logV) |
| **All paths** | Backtracking | O(V^V) |
| **Shortest distance only** | BFS/Dijkstra | O(V+E) or O((V+E)logV) |
| **Actual path + distance** | BFS/Dijkstra + parent pointers | Same + O(V) reconstruction |

**Critical Difference:**
```
Shortest Path (Word Ladder I):
Input: beginWord, endWord, wordList
Output: Length of shortest path
→ Use BFS, return depth when found

All Shortest Paths (Word Ladder II):
Input: beginWord, endWord, wordList
Output: All paths with minimum length
→ Use BFS for distance, DFS for path reconstruction
→ Completely different problem!
```

### Sorting/Ordering Problems

| Output Type | What It Means |
|------------|---|
| **Any valid order** | Topological sort is fine, just pick any |
| **Lexicographically smallest** | Must track and choose minimum |
| **All valid orderings** | Need to generate all permutations |

---

## 3️⃣ CONSTRAINT Clarification

**Ask: What are the hard limits? These often change the algorithm!**

### Memory Constraints

```
Example: LRU Cache
"The cache can hold at most K items"
→ Must implement eviction policy
→ Without knowing K, can't design solution

Example: Array
"Must solve in O(1) space"
→ Can't use HashMap or extra arrays
→ May need in-place modification
```

### Time Constraints

```
Example: Real-time system
"Must process in < 100ms"
→ Need to estimate operations per second
→ O(n²) might be too slow for n=10,000

Example: Batch job
"Must complete before next day's run (24 hours)"
→ Much more lenient
→ O(n²) might be acceptable
```

### Parallelization

```
Example: Task Scheduling
"Can tasks run in parallel?"
→ With parallelism: All independent tasks at once
→ Without parallelism: Linear sequence
→ Completely changes answer
```

### Data Size Constraints

```
"How many nodes/edges in graph?"
→ n = 100: O(n²) is fine
→ n = 1,000,000: Must be O(n log n)
→ Changes algorithm choice drastically
```

### Availability Constraints

```
"Can a node become unavailable?"
→ Without: Just find path once
→ With: Need dynamic path recomputation
→ Requires different approach
```

---

## 4️⃣ OPTIMIZATION GOAL Clarification

**Ask: What am I ultimately trying to optimize?**

### Different Goals, Same Problem

**Example: Coin Change**

```
Goal 1: Minimum coins needed
→ "Give me the fewest coins to make amount"
→ Use DP with Math.min()

Goal 2: Number of ways to make amount
→ "How many different combinations of coins?"
→ Use DP with addition (+)

Goal 3: Specific coin combinations
→ "Show me all combinations"
→ Use backtracking

COMPLETELY DIFFERENT SOLUTIONS!
```

**Example: Find Paths**

```
Goal 1: Any valid path (existence)
→ DFS/BFS, return true when found

Goal 2: Shortest path (optimization)
→ BFS or Dijkstra, return distance

Goal 3: All shortest paths
→ BFS for distance + DFS for paths

Goal 4: Number of paths
→ DP or DFS with memoization

Goal 5: Longest path
→ DP, DFS (different metric)

FIVE DIFFERENT ALGORITHMS!
```

### Common Optimization Goals

| Goal | Algorithm | Key Metric |
|------|-----------|-----------|
| **Find existence** | DFS/BFS | Just return yes/no |
| **Shortest** | BFS/Dijkstra | Minimize distance/cost |
| **Longest** | DP/DFS | Maximize distance |
| **Count ways** | DP/Backtracking | Sum all paths |
| **Minimize cost** | DP/Greedy/Dijkstra | Cost |
| **Maximize value** | DP/Greedy | Value |
| **Find all** | Backtracking | All possibilities |

---

## 5️⃣ EDGE CASES & SPECIAL CONDITIONS

**Ask: What breaks my assumptions? What's the edge case?**

### Always Consider

| Category | Questions | Examples |
|----------|-----------|----------|
| **Empty** | Empty input possible? | Empty array, empty graph |
| **Single** | Single element? | array=[1], graph with 1 node |
| **Duplicates** | Duplicates allowed? | [1,1,1], same node twice |
| **Order** | Order matters? | Sorted vs unsorted |
| **Cycles** | Cycles possible? | In graph, in linked list |
| **Disconnected** | All parts connected? | Disconnected graphs |
| **Overflow** | Integer overflow? | Large products, deep recursion |
| **Special Values** | Zeros, negatives, max values? | Division by zero, negative weights |

### Real Example: Graph Problem

```
Problem: "Find connected components in graph"

Edge cases to handle:
1. Empty graph → Return 0
2. Single node → Return 1
3. Disconnected nodes → Return n
4. All connected → Return 1
5. Cycles present → Doesn't matter for connectivity
6. Weighted → Doesn't matter for connectivity
```

---

## 🎤 The Interview Script

### How to Ask Clarifying Questions

**DO THIS:**

```
"Before I jump into the solution, let me clarify a few assumptions:

1. For the INPUT: [ask 2-3 input questions]
2. For the OUTPUT: What exactly should I return? 
3. Are there any CONSTRAINTS I should know about?
4. What's the OPTIMIZATION goal - shortest? 
   Minimum? Count? All possible?

Can you confirm these assumptions?"
```

**NOT THIS:**

```
❌ "Um, is it directed?"
❌ "Do I need to worry about efficiency?"
❌ Random scattered questions
```

### Why This Works

- ✅ Shows systematic thinking
- ✅ Prevents wasting 30 minutes on wrong solution
- ✅ Demonstrates communication skills
- ✅ Shows you understand the problem space

---

## 📋 Quick Reference Checklists

### For Graph Problems

```
□ Directed or undirected?
□ Weighted or unweighted?
□ Positive weights only?
□ Cycles possible?
□ Graph guaranteed connected?
□ Return distance or path?
□ Any memory/time constraints?
□ Multi-source or single source?
```

### For DP Problems

```
□ What's the optimization goal?
   □ Minimize?
   □ Maximize?
   □ Count ways?
   □ Check reachable?
□ What's the state?
□ What are the transitions?
□ What are base cases?
□ Can I optimize space?
```

### For Array/String Problems

```
□ Can input be empty?
□ Can values be negative?
□ Can there be duplicates?
□ Sorted or unsorted?
□ Case sensitive (strings)?
□ What's the optimization? (min/max/find/count?)
□ Time/space constraints?
□ Integer overflow possible?
```

---

## 🔄 Follow-Up Question Pattern

**When interviewer changes the problem:**

```
"What changed in this follow-up?

- Did the INPUT change?  (e.g., now weighted instead of unweighted)
- Did the OUTPUT change? (e.g., now return path instead of distance)
- Did the CONSTRAINT change? (e.g., now O(1) space)
- Did the GOAL change? (e.g., now count ways instead of find shortest)

Which of these changed?"
```

**Why this matters:**

Most follow-ups ONLY change ONE of these four elements. If you identify which one, you know exactly what part of your algorithm needs to change.

---

## 💡 Common Clarification Mistakes

### ❌ Mistake 1: Assuming Sorted

```
Problem: "Find two numbers that sum to target"

❌ Junior assumes array is sorted (not stated!)
   → Codes two pointers
   → Doesn't work for unsorted

✅ Senior asks: "Is the array sorted?"
   → If no: Use hash map instead
```

### ❌ Mistake 2: Assuming Unique Values

```
Problem: "Find majority element"

❌ Junior assumes all values unique
   → Uses hash set
   → Fails with duplicates

✅ Senior asks: "Can values repeat?"
   → If yes: Use hash map with counts
```

### ❌ Mistake 3: Assuming Small Size

```
Problem: "Find shortest path"

❌ Junior codes O(n²) Dijkstra
   → Works for n=1000
   → Timeout for n=100,000

✅ Senior asks: "How large is the graph?"
   → Adjusts algorithm accordingly
```

### ❌ Mistake 4: Misunderstanding Output

```
Problem: "Find all paths"

❌ Junior returns just one path
   → Misunderstood "all"

✅ Senior asks: "Should I return ANY path or ALL paths?"
   → Clear on expected output
```

---

## 🎯 Real Interview Examples

### Example 1: Topological Sort

```
Interviewer: "Given tasks with dependencies, 
             find a valid order to complete them."

Junior approach:
- Just codes topological sort
- Returns some valid order

Senior approach:
- "Before I code, let me confirm:
  - Do I return ANY valid order, or 
    the LEXICOGRAPHICALLY SMALLEST order?
  - These require different algorithms."

Impact:
- "Any valid" → Simple DFS, O(V+E)
- "Lexicographically smallest" → Need priority queue, O((V+E) log V)
```

### Example 2: Shortest Path

```
Interviewer: "Find shortest distance from A to B"

Junior approach:
- Codes Dijkstra immediately

Senior approach:
- "Few clarifications:
  1. Is the graph weighted?
  2. Can weights be negative?
  3. Do I return just distance or actual path?
  4. What's the size constraint?"

Impact:
- Unweighted → BFS, O(V+E)
- Weighted, positive → Dijkstra, O((V+E) log V)
- Weighted, negative → Bellman-Ford, O(VE)
- Just distance vs actual path → Different reconstruction needs
```

---

## 🚀 The Interview-Winning Phrase

**Remember this sentence:**

```
"Let me clarify a few assumptions before jumping into the solution."
```

Then ask your I-O-C-O-E questions systematically.

**This single phrase:**
- ✅ Shows maturity
- ✅ Prevents wrong solution
- ✅ Buys you 2-3 minutes of thinking time
- ✅ Impresses interviewer

---

## 📊 Decision Matrix

When clarification reveals different options:

| Clarification | Impact | Algorithm |
|---|---|---|
| Directed vs Undirected | Huge | Changes connectivity approach |
| Weighted vs Unweighted | Huge | Changes shortest path algorithm |
| Any vs Shortest vs All | Huge | Completely different solutions |
| Min vs Max | Huge | Inverts optimization goal |
| Empty input possible | Medium | Adds base case handling |
| Duplicates allowed | Medium | Changes data structure choice |
| Time/Space constraint | Medium | May eliminate algorithms |
| Integer overflow | Low | Changes data type (int vs long) |

---

## ✨ Why Clarification = Google L5 Behavior

**Junior engineer:**
- Sees problem → Codes → Gets wrong answer → Rethinks

**Senior engineer:**
- Sees problem → Clarifies → Codes → Gets right answer first time

**Google Interview:**
- They're testing: "Can you clarify before diving in?"
- This is MORE important than perfect code

---

## 🎁 Your Clarification Template

Copy this and use for EVERY problem:

```
"Before I start, let me confirm a few things:

INPUT:
- [Ask 2-3 input clarification questions]

OUTPUT:
- What exactly should I return?

CONSTRAINTS:
- Are there any time/space/availability constraints?

OPTIMIZATION:
- What am I optimizing for? (shortest/longest/count/min/max?)

EDGE CASES:
- Can input be empty?
- Can there be duplicates?
- [Other relevant edge cases]"
```

**Use this template for the next 10 problems you solve.**

After that, it becomes automatic.

---

## 🏆 The Result

Once clarification becomes your default:

1. ✅ You rarely solve the wrong problem
2. ✅ You choose the right algorithm faster
3. ✅ Your code works first time
4. ✅ Interviewers see systematic thinking
5. ✅ You're ready for original questions

**This skill alone will improve your interview performance by 20%+**

---

**Master clarification. Master interviews.** 🚀
