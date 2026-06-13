# 🏗️ DSA Problem-Solving Frameworks

**Master the systematic frameworks that Google engineers use to solve ANY problem**

---

## 📍 The Framework Philosophy

Instead of memorizing problems, learn to **think systematically**. These frameworks teach you **how to approach** any problem, not just patterns you've seen.

**The difference:**
- ❌ Junior: Memorize problem → See new problem → Panic
- ✅ Senior: Learn frameworks → See new problem → Apply framework systematically

---

## 🎯 The Framework Ecosystem

All frameworks follow the same philosophy: **CLARIFY → ANALYZE → IDENTIFY PATTERNS → CODE**

### 1. 📊 **DSA Problem-Solving Framework** (`mental_model.md`)

**The master framework for ANY DSA problem**

**6-Step Universal Approach:**
1. **Clarify** - Understand input, output, constraints
2. **Build the World** - What are entities and relationships?
3. **Identify Goal** - What am I ultimately finding?
4. **Identify State** - What describes current situation?
5. **Identify Transitions** - How do I move between states?
6. **Classify Problem** - Which algorithm world fits?

**Problem Worlds:**
- Dependency World → Topological Sort
- Reachability World → DFS/BFS/Union Find
- Shortest Path World → BFS/Dijkstra
- Optimization World → DP/Greedy

**Use this when:** You see ANY DSA problem and don't know where to start

**Real examples included:**
- Word Ladder (Shortest Path World)
- House Robber (Optimization World)

---

### 2. ⚙️ **Dynamic Programming Framework** (`DP.md`)

**The systematic approach to DP problems**

**8-Step Framework (mirroring Graph Framework):**
1. Clarification - What are we optimizing?
2. Entity - What represents subproblem?
3. Relationship - How do subproblems connect?
4. Goal - Count/Min/Max/Reachable?
5. State & Transitions - Define dp[i] and formula
6. Aggregation Type - How to combine choices
7. Optimization & Implementation - Coding strategy
8. Common Issues - Debugging guide

**4 Aggregation Types:**
- **COUNT DP** (use `+`) - How many ways?
- **MIN/MAX DP** (use `Math.min/max`) - What's the best?
- **REACHABLE DP** (use `||`) - Is it possible?
- **COMPLEX DP** - 2D, state machine, tree, bitmask

**DP Variants:**
- Linear DP, Sequence DP, Grid DP, Knapsack, State Machine, Interval, Tree, Bitmask

**Use this when:** You see an optimization or counting problem

**Real examples included:**
- LC70: Climbing Stairs (COUNT DP)
- LC322: Coin Change (MIN/MAX DP)
- LC122: Stock Trading (COMPLEX DP with state machine)

---

### 3. 🌐 **Graph Problem-Solving Framework** (`Graph_framework.md`)

**The systematic approach to graph problems**

**8-Step Framework:**
1. Clarification - What exactly are we finding?
2. Entity - What do nodes represent?
3. Relationship - What do edges represent?
4. Goal - Find/count/minimize/maximize?
5. Constraint - Time/space limits? Graph properties?
6. Pattern - Which problem type? (Connectivity, Shortest Path, Cycle, etc.)
7. Code - Choose algorithm and implement
8. What Got Stuck? - Debugging common issues

**Problem Types & Algorithms:**
- Connectivity/Components → DFS, BFS, Union-Find
- Shortest Path → BFS (unweighted), Dijkstra, Bellman-Ford
- Longest Path → DP, Backtracking, Binary Search
- Topological Order → DFS, Kahn's algo
- Cycle Detection → DFS, Union-Find
- Bipartite Check → BFS/DFS coloring
- Path Counting → DP, DFS + memoization

**Use this when:** You see a graph problem

---

### 4. 🎤 **Clarification & Assumptions Guide** (`clarify.md`)

**The skill that separates Google L5 from junior engineers**

**The 5-Element Checklist (I-O-C-O-E):**
- **I** - INPUT: What exactly comes in?
- **O** - OUTPUT: What exactly goes out?
- **C** - CONSTRAINT: What are hard limits?
- **O** - OPTIMIZATION: What am I optimizing?
- **E** - EDGE CASES: What breaks assumptions?

**Clarification by Problem Type:**
- Graph Problems - 8 key questions
- Array Problems - 6 key questions
- Tree Problems - 5 key questions
- String Problems - 5 key questions

**Output Clarification Examples:**
- Any path vs. Shortest path vs. All paths (different algorithms!)
- Distance only vs. Actual path (need different code)
- Any valid order vs. Lexicographically smallest (different complexity)

**Why it matters:**
- Interview-winning skill
- Prevents wasting 30 minutes on wrong solution
- Shows maturity and systematic thinking
- Buys you 2-3 minutes of thinking time

**Use this when:** Starting ANY interview problem

---

### 5. 🗣️ **DSA Terminology & Vocabulary Guide** (`terms.md`)

**Master the language of algorithms**

**12 Vocabulary Sections:**
1. DP Vocabulary - State, Transition, Recurrence, Metric, Aggregation, etc.
2. Graph Vocabulary - Node, Edge, Reachable, Cycle, Topological Sort, etc.
3. BFS Vocabulary - Frontier, Level, Visited Set, Expansion, etc.
4. DFS Vocabulary - Backtrack, Choose, Explore, Undo, etc.
5. Sliding Window - Window, Expand, Shrink, Frequency Map, etc.
6. Greedy Vocabulary - Greedy Choice, Exchange Argument, Pruning, etc.
7. Binary Search - Search Space, Monotonic Property, Threshold, etc.
8. Union Find - Union, Find, Root, Path Compression, etc.
9. Problem Classification - Optimization, Counting, Reachability, etc.
10. Quick Reference Table - Pattern types and key terms
11. Interview Cheat Sheet - What to ask and explain
12. The Biggest Insight - Using vocabulary signals algorithmic thinking

**Why it matters:**
- Interviewer judges through your words
- Vocabulary signals understanding, not memorization
- Shows you think algorithmically

**Use this when:** 
- You want to speak like a Google engineer
- You need to recall the right term during interview

---

## 🗺️ How These Frameworks Connect

```
Start with ANY Problem
        ↓
    1. CLARIFY
    (Use clarify.md framework)
        ↓
    2. ANALYZE
    (Use mental_model.md 6-step framework)
        ↓
    3. IDENTIFY WORLD
    ├─ Dependency World?    → Topological Sort
    ├─ Reachability World?  → DFS/BFS (Use Graph_framework.md)
    ├─ Shortest Path World? → BFS/Dijkstra (Use Graph_framework.md)
    └─ Optimization World?  → DP/Greedy (Use DP.md for DP)
        ↓
    4. CODE with TERMINOLOGY
    (Use terms.md for correct language)
        ↓
    5. SOLVE!
```

---

## 📚 Framework Usage by Problem Type

### Graph Problems
1. Use **clarify.md** - Ask clarifying questions
2. Use **Graph_framework.md** - 8-step approach
3. Use **mental_model.md** - Identify which world
4. Use **terms.md** - Speak the language

**Example:** Word Ladder
- Clarify: Is it shortest path?
- Graph framework: Entities (words), relationships (one-letter different)
- Mental model: Shortest Path World → BFS
- Terms: "frontier", "level", "expansion"

### DP Problems
1. Use **clarify.md** - Ask clarifying questions
2. Use **mental_model.md** - Identify as Optimization World
3. Use **DP.md** - 8-step approach
4. Use **terms.md** - Speak the language

**Example:** Coin Change
- Clarify: Minimize coins? Unlimited use?
- Mental model: Optimization World → DP
- DP framework: State (amount), transitions (use coin), metric (min), aggregation (Math.min)
- Terms: "state", "transition", "aggregation", "metric"

---

## 🎯 Quick Framework Selection

**"I see a problem. Which framework do I use?"**

```
Can I model this as a sequence of choices/states?
├─ YES → Use DP Framework
└─ NO → Continue

Does it involve graphs, paths, connectivity, ordering?
├─ YES → Use Graph Framework
└─ NO → Continue

Does it need clarification on edge cases or output format?
├─ YES → Use Clarification Framework (first!)
└─ Continue

For ANY problem: Start with clarify.md + mental_model.md
Then specialize based on problem type
Always use terms.md to explain your thinking
```

---

## 📈 Learning Progression

### Week 1: Build Foundation
- Read mental_model.md (6-step framework)
- Read clarify.md (5-element checklist)
- Practice applying to 5 different problems

### Week 2: DP Problems
- Read DP.md completely
- Practice 7-day plan in DP.md
- Use terms.md for vocabulary

### Week 3: Graph Problems
- Read Graph_framework.md completely
- Practice graph problems systematically
- Use terms.md for vocabulary

### Week 4: Integration
- For each problem:
  1. Start with clarify.md
  2. Use mental_model.md to classify
  3. Use specialized framework (DP or Graph)
  4. Use terms.md to communicate
  5. Code!

---

## 🏆 Why These Frameworks Work

### Before Frameworks
```
See problem → Panic → Try random stuff → Wrong answer
```

### With Frameworks
```
See problem → Clarify → Analyze → Classify → Apply framework → Code → Correct answer
```

### The Results
✅ You solve problems systematically (not by luck)
✅ You can handle unseen problems (not just memorized ones)
✅ You can explain your thinking (interview communication)
✅ You handle follow-ups easily (framework adapts)
✅ You interview at Google level

---

## 📋 Framework Cheat Sheets

### Mental Model 6-Step Checklist
```
□ Step 1: Clarify (Input? Output? Constraints?)
□ Step 2: Build World (Entities? Relationships?)
□ Step 3: Goal (Any/Shortest/Longest/Count/Min/Max?)
□ Step 4: State (What describes situation?)
□ Step 5: Transitions (How do states relate?)
□ Step 6: Classify (Which algorithm world?)
```

### DP 8-Step Checklist
```
□ Step 1: Clarify (What's being optimized?)
□ Step 2: Entity (What is subproblem?)
□ Step 3: Relationship (How do they connect?)
□ Step 4: Goal (Count/Min/Max/Reachable?)
□ Step 5: State & Transitions (What's the formula?)
□ Step 6: Aggregation (How to combine?)
□ Step 7: Implementation (Code it)
□ Step 8: Debug (What went wrong?)
```

### Graph 8-Step Checklist
```
□ Step 1: Clarify (What exactly finding?)
□ Step 2: Entity (What are nodes?)
□ Step 3: Relationship (What are edges?)
□ Step 4: Goal (Find/count/min/max?)
□ Step 5: Constraint (Time/space limits?)
□ Step 6: Pattern (Which problem type?)
□ Step 7: Code (Implement algorithm)
□ Step 8: Debug (What went wrong?)
```

### Clarification 5-Element Checklist
```
□ INPUT: What exactly comes in?
□ OUTPUT: What exactly goes out?
□ CONSTRAINT: What are hard limits?
□ OPTIMIZATION: What am I optimizing?
□ EDGE CASES: What breaks assumptions?
```

---

## 🎁 Master These 5 Frameworks

### They Work Together

1. **mental_model.md** - The master framework (start here)
2. **clarify.md** - Ask the right questions (use first)
3. **DP.md** - Solve optimization problems (use for DP)
4. **Graph_framework.md** - Solve graph problems (use for graphs)
5. **terms.md** - Speak the language (use always)

**Once you master these 5:**
- ✅ You can solve ANY problem systematically
- ✅ You can explain your thinking clearly
- ✅ Interviewers see systematic thinking
- ✅ You interview at Google/Meta level

---

**Master these frameworks. Master interviews. Master DSA.** 🚀

---

## 📂 File Structure

```
frameworks/
├── README.md                    (This file)
├── mental_model.md              (6-step universal framework)
├── clarify.md                   (Clarification guide)
├── DP.md                        (8-step DP framework)
├── Graph_framework.md           (8-step graph framework)
└── terms.md                     (Vocabulary reference)
```

---

## 🔗 Cross-References

If you're studying:
- **Optimization problems** → DP.md (then use mental_model.md + clarify.md)
- **Graph problems** → Graph_framework.md (then use mental_model.md + clarify.md)
- **Any problem** → Start with clarify.md + mental_model.md
- **Communication** → terms.md

All frameworks link together coherently. Use them as an integrated system, not individually.
