# Day 7 Plan — Backtracking + Heap + Greedy

By now you have covered:
- Graph BFS / DFS
- Topological sort
- Matrix traversal
- Recursive tree DFS
- Iterative traversal
- Trie
- LRU Cache
- Union Find basics

Your foundation is becoming much more stable. Day 7 shifts focus from basic traversal toward **state management + decision patterns**:
- Backtracking
- Heap / Top K
- Interval / Greedy
- Advanced BFS

---

## Time Allocation

| Topic | Time |
|---|---|
| Backtracking | 40% |
| Heap / Priority Queue | 25% |
| Interval / Greedy | 20% |
| Advanced BFS | 15% |

---

## Phase 1 — Backtracking `(40%)`

Very important for Google-style interviews.

> **Goal:** understand `choose → recurse → undo` deeply.

### Universal Template

```java
void dfs(/* state */) {
    // base case
    if (done) { result.add(new ArrayList<>(path)); return; }

    for (/* each choice */) {
        path.add(choice);       // choose
        dfs(/* next state */);  // recurse
        path.remove(...);       // undo
    }
}
```

> **Key insight:** Backtracking = DFS + state rollback.

### Must-Do Problems

| Problem | Pattern |
|---|---|
| Subsets | basic backtracking, no pruning |
| Permutations | `visited[]` to track used elements |
| Combination Sum | allow element reuse |
| Letter Combinations of Phone Number | multi-branch at each step |
| Generate Parentheses | constraint-based pruning |

---

## Phase 2 — Heap / Priority Queue `(25%)`

Very high ROI.

### Min Heap vs Max Heap

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();               // default
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
```

### Recognition Pattern

When you see **top K / smallest K / largest K / streaming order** → think **heap**.

### Must-Do Problems

| Problem | Pattern |
|---|---|
| Top K Frequent Elements | min heap of size K |
| Kth Largest Element | min heap |
| Merge K Sorted Lists | min heap on list heads |
| Meeting Rooms II | interval + min heap on end times |

---

## Phase 3 — Interval / Greedy `(20%)`

Very common in interviews.

### Core Sorting Pattern

```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);  // sort by start time
```

### Greedy Thinking

> Make the **locally optimal decision** at each step to achieve the global optimum.

### Must-Do Problems

| Problem | Pattern |
|---|---|
| Merge Intervals | sort + merge overlapping |
| Insert Interval | merge logic with new interval |
| Non-overlapping Intervals | greedy — remove min intervals |
| Meeting Rooms | detect any overlap |

---

## Phase 4 — Advanced BFS `(15%)`

You have already improved a lot here. Now focus on these patterns:

### BFS Level Traversal Template

```java
int steps = 0;
while (!queue.isEmpty()) {
    int size = queue.size();        // snapshot current level
    for (int i = 0; i < size; i++) {
        Node curr = queue.poll();
        // process curr
        // add neighbors to queue
    }
    steps++;                        // one full level = one distance unit
}
```

> `size` snapshot is the key — it represents one distance layer.

### Must-Do Problems

| Problem | Pattern |
|---|---|
| Rotting Oranges | multi-source level BFS |
| Word Ladder | shortest transformation, BFS on states |
| Open the Lock | state BFS with visited set |

---

## Main Goal

Not: memorize 30 solutions.

**Goal:** recognize state-management patterns on sight.

| Pattern | Key Idea |
|---|---|
| Backtracking | undo state after each branch |
| Heap | maintain dynamic ordering |
| Greedy | local optimal → global optimum |
| BFS level | each level = one distance step |

---

## Google Interview Reminder

Practice **talking while coding**:

> *"I'm exploring all possible choices recursively. After recursion returns, I roll back state to ensure sibling branches are unaffected."*

That communication skill matters a lot for Staff-level interviews.
