# Algorithm Interview Patterns — Ranked by Frequency

> Code for every pattern lives in `collections/CommonPattern.java`.  
> Study order: master Tier 1 on LeetCode → Tier 2 → Tier 3.

---

## Tier 1 — Core (appears in almost every interview)

| ID    | Pattern                          | When to use                                              |
|-------|----------------------------------|----------------------------------------------------------|
| T1-1  | Two pointers                     | Sorted array pair-sum, remove duplicates, container water|
| T1-2  | Sliding window — fixed size      | Max/min of every subarray of length k                    |
| T1-3  | Sliding window — variable size   | Longest/shortest subarray satisfying a constraint        |
| T1-4  | Binary search                    | Sorted array lookup; also: search-on-answer problems     |
| T1-5  | BFS — graph                      | Shortest path (unweighted), level-order, connectivity    |
| T1-6  | Tree traversals — recursive      | Inorder (BST sort), preorder (serialize), postorder (delete) |
| T1-7  | Tree inorder — iterative         | Same as T1-6 but avoids call-stack overflow              |
| T1-8  | Level-order BFS with levels      | Max depth, right-side view, zigzag traversal             |
| T1-9  | 1-D DP — bottom-up               | Coin change, climbing stairs, house robber               |
| T1-10 | StringBuilder & palindrome check | Any string-building loop; palindrome in O(n) O(1) space  |
| T1-11 | Frequency map                    | Anagram, character count, sliding window with constraint  |

---

## Tier 2 — Very common (medium / hard rounds)

| ID   | Pattern                 | When to use                                                  |
|------|-------------------------|--------------------------------------------------------------|
| T2-1 | Matrix BFS              | Shortest path in grid, number of islands, flood fill         |
| T2-2 | Backtracking            | Permutations, subsets, combinations, N-queens                |
| T2-3 | Top-K — min-heap size k | Kth largest, merge K sorted lists, task scheduler            |
| T2-4 | Monotonic stack         | Next greater/smaller element, histogram area, stock prices   |
| T2-5 | Kadane's algorithm      | Maximum subarray sum; appears as sub-problem in harder Qs    |
| T2-6 | 2-D DP                  | LCS, edit distance, unique paths, grid DP                    |
| T2-7 | Prefix sum              | Range sum queries, subarray sum equals k                     |
| T2-8 | Merge intervals         | Meeting rooms, insert interval, employee free time           |
| T2-9 | Difference array        | Range increment/decrement updates; reconstruct with prefix sum|

---

## Tier 3 — Harder problems (senior / FAANG level)

| ID   | Pattern                  | When to use                                                  |
|------|--------------------------|--------------------------------------------------------------|
| T3-1 | Union-Find (DSU)         | Connected components, cycle detection, Kruskal's MST         |
| T3-2 | Topological sort         | Course schedule, build order, alien dictionary               |
| T3-3 | Dijkstra's shortest path | Weighted graph shortest path (non-negative weights)          |
| T3-4 | 0/1 Knapsack             | Partition equal subset, target sum, last stone weight        |
| T3-5 | Top-down memoization     | Irregular recurrences; easier to write than bottom-up DP     |
| T3-6 | Fast & slow pointers     | Linked list cycle, find middle, find duplicate number        |
| T3-7 | Monotonic deque          | Sliding window maximum, jump game variants                   |
| T3-8 | TreeMap / TreeSet        | Time-based key-value store, range problems, ordered sets     |

---

## Tier 4 — Good to know (occasional / niche)

| ID    | Pattern                | When to use                                              |
|-------|------------------------|----------------------------------------------------------|
| T4-1  | DFS — iterative        | Deep graphs where recursion risks stack overflow          |
| T4-2  | DFS — recursive        | Graph/tree with backtracking (mark on enter, unmark on exit) |
| T4-3  | Quick select           | Kth smallest/largest in O(n) avg without full sort       |
| T4-4  | Merge sort             | Stable sort; counting inversions                         |
| T4-5  | Dutch National Flag    | Sort 0s/1s/2s in one pass; 3-way partition               |
| T4-6  | Bit manipulation       | Power-of-2 check, XOR single number, get/set/clear bit   |
| T4-7  | Cyclic sort            | Array with values in [1..n]; find missing/duplicate      |
| T4-8  | Anagram detection      | Group anagrams, valid anagram                            |
| T4-9  | Deque (stack/queue/heap)| Replace Stack with ArrayDeque; max-heap with reverseOrder|
| T4-10 | GCD / LCM              | Fraction simplification, common period problems          |
| T4-11 | Fast exponentiation    | Large power mod; combinatorics with modular arithmetic   |
| T4-12 | Sieve of Eratosthenes  | All primes ≤ n; prime factorisation pre-computation      |
| T4-13 | Swap / reverse         | In-place array manipulation utility                      |

---

## Key reminders

- **Binary search**: always use `mid = lo + (hi - lo) / 2` to avoid overflow.
- **BFS vs DFS**: BFS guarantees shortest path (unweighted); DFS is simpler for exhaustive search.
- **DP choice**: bottom-up (Tier 1) for regular recurrences; top-down memo (T3-5) for irregular ones.
- **Heap size k trick** (T2-3): min-heap keeps k largest; max-heap keeps k smallest.
- **TreeMap API** (T3-8): `floorKey` ≤ k, `ceilingKey` ≥ k, `lowerKey` < k, `higherKey` > k.
- **0/1 Knapsack** (T3-4): iterate capacity right-to-left so each item is used at most once.
- **Monotonic deque** (T3-7): evict out-of-window from front; evict smaller values from back.



===========

1. Sliding Window
2. Two Pointers
3. Monotonic Stack
4. Prefix Sum
5. Binary Search
6. BFS / DFS
7. Backtracking
8. DP
9. Greedy
10. Heap
11. Union Find
12. Topological Sort
13. Trie
14. Sweep Line
15. Prefix Sum + HashMap



====================


你这个问题很好，但我先帮你纠正一个关键点：

👉 你说的“Python”其实是 Pattern（算法模式），不是编程语言 Python 👍
（你前面理解是对的，只是这里用词有点混）

⸻

一、先给你结论

大厂面试常用的核心 Pattern 大概 15～20 个，但扩展可以到 30～40 个（按细分）

你之前的 12 个是“核心骨架”，下面我帮你扩展成一个完整面试级 Pattern 文档（约30+）

⸻

二、完整算法 Pattern 文档（扩展版）

我帮你分成 6 大类，更系统 👇

⸻

🧱 1. 数组 / 区间类

⸻

1️⃣ Two Pointers（双指针）

2️⃣ Sliding Window（滑动窗口）

3️⃣ Prefix Sum（前缀和）

4️⃣ Difference Array（差分数组）

5️⃣ Interval Merge（区间合并）

👉 例：

Merge Intervals
Insert Interval


⸻

6️⃣ Sweep Line（扫描线）⭐重要

👉 本质：

事件 + 排序 + 差分思想

👉 用途：

会议室数量
区间重叠
最大并发

👉 系统设计（你熟的）：

QPS / 并发 / Ads pacing


⸻

🌲 2. 栈 / 队列类

⸻

7️⃣ Monotonic Stack（单调栈）

8️⃣ Monotonic Queue（单调队列）

👉 用途：

滑动窗口最大值


⸻

9️⃣ Stack Simulation（栈模拟）

表达式计算
括号匹配


⸻

🔟 Deque Pattern

双端队列优化窗口


⸻

🔍 3. 搜索类（Graph / Tree）

⸻

1️⃣1️⃣ DFS

1️⃣2️⃣ BFS

1️⃣3️⃣ Backtracking（回溯）

⸻

1️⃣4️⃣ Topological Sort（拓扑排序）

👉 用途：

任务调度
依赖关系


⸻

1️⃣5️⃣ Graph Traversal Pattern

visited + adjacency list


⸻

1️⃣6️⃣ Shortest Path（最短路径）

👉 算法：

Dijkstra
BFS（无权图）


⸻

⚙️ 4. 优化类

⸻

1️⃣7️⃣ Dynamic Programming（DP）

⸻

1️⃣8️⃣ Greedy（贪心）

⸻

1️⃣9️⃣ Binary Search（非常重要）

👉 扩展：

Binary Search on Answer ⭐


⸻

2️⃣0️⃣ Divide and Conquer（分治）

Merge Sort
Quick Sort


⸻

📊 5. 数据结构类

⸻

2️⃣1️⃣ Heap / Priority Queue

2️⃣2️⃣ Union Find（并查集）

⸻

2️⃣3️⃣ Trie（字典树）

👉 用途：

前缀搜索
自动补全


⸻

2️⃣4️⃣ Segment Tree（线段树）⭐进阶

👉 用途：

区间查询 + 更新


⸻

2️⃣5️⃣ Fenwick Tree / BIT（树状数组）

👉 比 segment tree 简单

⸻

🧠 6. 高级 Pattern（大厂高频）

⸻

2️⃣6️⃣ K-way Merge

多个有序数组合并


⸻

2️⃣7️⃣ Sliding Window + HashMap

👉 高频组合：

字符串问题


⸻

2️⃣8️⃣ Prefix Sum + HashMap ⭐

👉 超高频：

Subarray Sum = K


⸻

2️⃣9️⃣ State Compression（状态压缩 DP）

bitmask DP


⸻

3️⃣0️⃣ Meet in the Middle

👉 用于：

n=40 左右的组合爆炸问题


⸻

3️⃣1️⃣ Reservoir Sampling

👉 用于：

流式数据随机采样


⸻

3️⃣2️⃣ Randomized Algorithm

随机化 quickselect


⸻

3️⃣3️⃣ Line Sweep + Heap ⭐

👉 用于：

会议室问题


⸻

3️⃣4️⃣ Graph + BFS + State（重要）

最短路径 + 状态


⸻

3️⃣5️⃣ Multi-source BFS

从多个起点同时扩散


⸻

3️⃣6️⃣ Memoization（记忆化搜索）

DFS + cache


⸻

三、大厂最常考 TOP 15（建议重点）

如果你时间有限，优先掌握：

1. Sliding Window
2. Two Pointers
3. Monotonic Stack
4. Prefix Sum


5. Binary Search
6. BFS / DFS
7. Backtracking
8. DP

9. Greedy
10. Heap
11. Union Find
12. Topological Sort

13. Trie
14. Sweep Line
15. Prefix Sum + HashMap


⸻

四、你这个阶段的建议（非常重要）

你现在已经在做：

系统设计 + Ads + RAG

👉 建议重点：

Monotonic Stack（你刚学）
Sweep Line（Ads / QPS）
Heap（TopK / ranking）
Binary Search（优化）
Prefix Sum（统计）
Graph（workflow / dependency）


⸻

五、一句话总结

算法 Pattern 不是 40 个独立东西，而是 10~15 个核心 + 扩展组合。

⸻

🔥 最关键一句话

真正高手不是会 40 个 Pattern，而是能把 10 个 Pattern 组合起来。

⸻

如果你愿意，我可以帮你做一个更高级的版本👇

👉 把这些 Pattern 映射到 Google Ads / RAG / Distributed System 的真实应用（非常加分）