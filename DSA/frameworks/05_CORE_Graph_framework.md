Graph Problem-Solving Framework

1. Clarification

- What is the input? (adjacency list, matrix, edge list, implicit graph?)
- What exactly are we finding/computing? (path, count, minimum, existence?)
- Are there multiple test cases or constraints I'm missing?
- Can I ask: directed or undirected? weighted or unweighted? cyclic or acyclic?
- Any special cases? (empty graph, single node, disconnected components, self-loops?)

2. Entity

- What do nodes represent? (people, cities, tasks, states, positions, etc.)
- How many nodes? (N nodes, indexed 0 to N-1?)
- What data is stored per node? (just connectivity, or values/labels?)
- Can nodes have states? (visited, distance, cost?)

3. Relationship

- What do edges represent? (friendship, road distance, dependency, direction, cost?)
- Edge properties:
  - Directed vs undirected?
  - Weighted vs unweighted?
  - Can have duplicates or self-loops?
  - How many edges? (sparse or dense?)
- How are relationships defined? (explicit edges given, or implicit/generated?)

4. Goal

- Primary goal: Find, count, minimize, maximize, or check existence?
- Specific target: shortest path? longest path? all paths? connected component? cycle? ordering? flow?
- Output format: single answer, path trace, count, boolean, all solutions?
- Optimization: if multiple solutions exist, which is best? (by length, cost, lexicographic?)

5. Constraint

- Time complexity target: O(V+E), O(V²), O(V·E log V)?
- Space complexity: O(V), O(E), O(V²)?
- Graph properties:
  - Is it a DAG? Bipartite? Tree? Complete?
  - Can it have negative weights?
  - What's the max V and E?
- Problem-specific: Memory limit? Follow-up queries? Must handle updates?

6. Pattern

Recognize the problem type and viable approaches:

┌──────────────────────────┬────────────────────────────────┬────────────────────────────────────────────────────┐
│       Problem Type       │            Patterns            │                 Viable Approaches                  │
├──────────────────────────┼────────────────────────────────┼────────────────────────────────────────────────────┤
│ Connectivity/Components  │ Connected? Which component?    │ DFS, BFS, Union-Find                               │
├──────────────────────────┼────────────────────────────────┼────────────────────────────────────────────────────┤
│ Shortest Path            │ Min distance/cost?             │ BFS (unweighted), Dijkstra, Bellman-Ford, DP (DAG) │
├──────────────────────────┼────────────────────────────────┼────────────────────────────────────────────────────┤
│ Longest Path             │ Max distance/cost?             │ DP (DAG), Backtracking, Binary Search (on answer)  │
├──────────────────────────┼────────────────────────────────┼────────────────────────────────────────────────────┤
│ Topological Order        │ Linear ordering? Dependencies? │ DFS, Kahn's algo, DP                               │
├──────────────────────────┼────────────────────────────────┼────────────────────────────────────────────────────┤
│ Cycle Detection          │ Has cycle? Find cycle?         │ DFS, Union-Find                                    │
├──────────────────────────┼────────────────────────────────┼────────────────────────────────────────────────────┤
│ Bipartite Check          │ Two-colorable?                 │ BFS/DFS coloring                                   │
├──────────────────────────┼────────────────────────────────┼────────────────────────────────────────────────────┤
│ Spanning Tree            │ Min/max weight spanning?       │ Kruskal, Prim                                      │
├──────────────────────────┼────────────────────────────────┼────────────────────────────────────────────────────┤
│ Path Counting            │ How many paths?                │ DP, DFS + memoization                              │
├──────────────────────────┼────────────────────────────────┼────────────────────────────────────────────────────┤
│ Reachability/State Space │ Can reach target state?        │ BFS, DFS, DP                                       │
└──────────────────────────┴────────────────────────────────┴────────────────────────────────────────────────────┘

Pick your approach:
- DFS: cycles, connectivity, topological sort, all paths, backtracking
- BFS: shortest unweighted path, level-by-level, nearest neighbors
- DP: optimal substructure (DAG, counting, min/max with state)
- Binary Search: search on answer space (minimize X such that goal achievable?)
- Greedy: optimal choices that don't require revisiting (MST, Dijkstra)
- Union-Find: dynamic connectivity, incremental merging

7. Code

- Choose representation: adjacency list (most flexible) vs matrix vs edge list
- Initialize: graph, visited/distance arrays, queue/stack for traversal
- Core logic: implement chosen algorithm (DFS, BFS, DP, etc.)
- Handle edge cases: empty graph, single node, disconnected components, cycles, negative weights
- Optimize: pruning, memoization, early termination

8. What Got Stuck?

Common issues to debug:
- Wrong answer: off-by-one in indexing? missed a case? wrong algorithm picked?
- TLE (Time Limit): O(V·E) when should be O(V+E)? revisiting nodes? inefficient structure?
- MLE (Memory): storing too much? recursion depth? use iterative instead?
- Logic errors: not marking visited? wrong direction for edges? forgot to handle undirected?
- Edge cases: empty input? disconnected graph? cycles in shortest path? negative weights?

---
Example Application:
Problem: "Find shortest path from A to B in weighted graph"
1. Clarification: Is it always connected? Can have cycles?
2. Entity: Nodes are destinations, indexed 0 to N-1