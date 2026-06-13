# 🚀 INTERVIEW FOLLOW-UP PATTERNS & WORLD EVOLUTION

**Master how interview follow-ups evolve and how to anticipate them**

---

## 📖 TABLE OF CONTENTS

1. **The Core Insight** - Why follow-ups matter
2. **Original 7 Dimensions** - How problems fundamentally change
3. **New 7 Dimensions** - Additional evolution patterns
4. **14-Dimension Overview** - Quick reference matrix
5. **World-by-World Analysis** - Follow-up patterns for each world
6. **Quick Lookup Guides** - Find follow-ups by dimension
7. **Interview Strategy** - How to communicate about follow-ups

---

# 💡 THE CORE INSIGHT

## Why Follow-ups Matter

Google/Meta L6 面试官最喜欢从哪里做 Follow-up?

**面试里的 Follow-up，80% 都来自这个规律：**

```
Problem
  ↓
Assumption (what we assume to be true)
  ↓
Assumption Changes (interviewer modifies assumption)
  ↓
Algorithm Changes (your solution must adapt)
```

**The Hidden Pattern:**
- 面试官并不是在换题，而是在不断修改约束条件
- 你不是在做 20 道题，而是在理解 1 道题的演化路径
- 这正是 Google L6 很喜欢考的思维方式

**Real-world parallel:**
- 流量变大了怎么办？ → Data Size Changes
- 延迟要求变严了怎么办？ → Constraint Changes
- 需要并发支持怎么办？ → Concurrency dimension
- 出现失败怎么办？ → Error Handling

---

# 📊 ORIGINAL 7 FOLLOW-UP DIMENSIONS

The classic patterns that appear in 80% of follow-ups:

## 1. **Cost Changes** 💰

```
无权 → 有权 → 负权

BFS → Dijkstra → Bellman-Ford
```

**Example Chain (Open Lock Problem):**
- Level 1: 最少步数 → BFS
- Level 2: 不同转动成本 → Dijkstra  
- Level 3: 某些转动有奖励（负权） → Bellman-Ford

**When to expect:** "What if edges have weights?" or "costs are different"

---

## 2. **Constraint Changes** 📌

```
无约束 → 有约束 → 更多约束

无限制 → 最多K步 → 最多K资源 → 多个约束

Dijkstra → Bellman-Ford DP → Advanced DP
```

**Example:**
- Base: Shortest path (any number of edges)
- Follow-up: At most K edges (LeetCode 787)
- Solution change: DP with dimension for edge count

**When to expect:** "At most K...", "limit to...", "with constraint..."

---

## 3. **Data Size Changes** 📈

```
n = 1000 → n = 1 billion

O(n²) → External Sort, MapReduce, Distributed
```

**Example:**
- Base: Two Sum with HashMap - O(n)
- Follow-up: 10 billion numbers, can't fit in memory
- Solution: External sort, stream processing, or distributed

**When to expect:** "What if n = 1 billion?" or "we have 100TB data"

---

## 4. **Static → Dynamic** 🔄

```
静态数据 → 动态更新

图建立好了 → 边不断增加 → 边不断增加和删除
```

**Example:**
- Base: Connected components (static graph)
- Follow-up: Edges added over time (add only)
- Follow-up 2: Edges can be added and deleted
- Solution evolution: Union Find → Dynamic connectivity

**When to expect:** "What if data/edges are added continuously?"

---

## 5. **Single Thread → Concurrent** 🔓

```
单线程 → 多线程访问

HashMap → Thread-safe Hash Map
LRU Cache → Concurrent LRU Cache
```

**Example:**
- Base: LRU Cache (single thread)
- Follow-up: Multiple threads access simultaneously
- Solution: Add locks, use ReentrantReadWriteLock, or ConcurrentHashMap

**When to expect:** "Multiple threads access your structure" or "concurrent calls"

---

## 6. **Memory ❌ → Limited Storage** 💾

```
内存充足 → 内存不足

Top K → 100TB 日志怎么处理？

Heap → External Merge Sort, Streaming, Approximation
```

**Example:**
- Base: Top K (fits in memory)
- Follow-up: 100TB of data, find top K
- Solution: External merge sort, approximate algorithms (HyperLogLog)

**When to expect:** "Memory is limited" or "data is huge, can't fit"

---

## 7. **Exact → Approximate** 🎯

```
精确答案 → 近似答案

精确统计 UV → 10 亿用户怎么办？

HashSet → Bloom Filter, HyperLogLog
```

**Example:**
- Base: Unique count with HashSet
- Follow-up: 1 billion users, exact count impossible
- Solution: Bloom filter or HyperLogLog (probabilistic)

**When to expect:** "Approximate answer is OK" or "perfect accuracy not needed"

---

# 📋 NEW 7 FOLLOW-UP DIMENSIONS

Additional evolution patterns that appear in 20% of follow-ups:

## 8. **Output Format Changes** 📊

```
Single → Multiple → Ranked → Detailed

return distance → return path → return all paths → with metadata
```

**Example:**
- LeetCode 28: Find first occurrence
- Follow-up: Find all occurrences
- Solution: Iterate through all, not just first match

**When to expect:** "Return all...", "with indices", "include details"

---

## 9. **Directional/Relationship Changes** 🔀

```
Undirected → Directed → Bidirectional

无向图 → 有向图 → 双向图 → 多重边
```

**Example:**
- Base: Islands (undirected connectivity)
- Follow-up: Water flow (directed graph)
- Solution: Modify traversal logic for directions

**When to expect:** "What if directed?", "one-way edges", "flow direction"

---

## 10. **Validation/Verification** ✅

```
求解问题 → 验证答案 → 完成部分答案

Solve → Verify → Fill missing

BFS/Dijkstra → Path verification
```

**Example:**
- Base: Find shortest path
- Follow-up: Given a path, verify if it's shortest
- Solution: Check all edges sum to claimed distance

**When to expect:** "Verify if this is correct", "given a solution, is it valid?"

---

## 11. **Real-time/Streaming Evolution** 📡

```
离线全部数据 → 在线增量 → 单次扫描 → 持续更新

All data available → Incremental → Single pass → Continuous
```

**Example:**
- Base: Kth largest (all data available)
- Follow-up: Data stream, find running Kth
- Solution: Min-heap of size K (fundamentally different)

**When to expect:** "Streaming input", "data arrives one-by-one", "continuous"

---

## 12. **Error Handling/Recovery** 🔧

```
完美路径 → 处理部分错误 → 完整错误处理 → 回滚

Happy path → Handle errors → Recover → Rollback
```

**Example:**
- Base: LRU Cache get/put
- Follow-up: What if corrupted entry?
- Solution: Add validation, recovery logic

**When to expect:** "What if fails?", "handle errors", "invalid input"

---

## 13. **Batching/Grouping** 📦

```
单个查询 → 多个查询 → 批量优化 → 摊销成本

Single query → K queries → Batch → Amortized
```

**Example:**
- Base: Binary search (single target)
- Follow-up: Find K different targets
- Solution: Batch processing or preprocessing

**When to expect:** "K queries", "multiple operations", "amortize cost"

---

## 14. **Fairness/Distribution** ⚖️

```
最大化总值 → 公平性 → 加权优先级 → 负载均衡

Maximize total → Fair distribution → Weighted → Load balance
```

**Example:**
- Base: Maximize room utilization
- Follow-up: Fair distribution across users
- Solution: Different objective function or constraints

**When to expect:** "Fair to all", "balanced", "weighted priorities"

---

# 📈 14-DIMENSION QUICK REFERENCE MATRIX

At a glance, which dimensions apply to each world:

| World | Cost | Constraint | Size | Dynamic | Concurrent | Memory | Approx | Output | Direction | Validation | Streaming | Error | Batch | Fair |
|-------|------|-----------|------|---------|-----------|--------|--------|--------|-----------|-----------|----------|-------|-------|------|
| 1. Dependency | ❌ | ✅ | ✅ | ✅✅ | ✅ | ⚠️ | ❌ | ✅ | ❌ | ✅ | ✅ | ✅ | ⚠️ | ⚠️ |
| 2. Reachability | ❌ | ✅ | ✅ | ✅✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ⚠️ | ✅ | ❌ |
| 3. Shortest Path | ✅✅ | ✅ | ✅ | ✅ | ⚠️ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ⚠️ |
| 4. Optimization | ✅ | ✅✅ | ✅ | ✅ | ⚠️ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ⚠️ | ✅ | ✅ |
| 5. Search Space | ⚠️ | ✅ | ✅ | ❌ | ⚠️ | ❌ | ❌ | ✅ | ❌ | ✅ | ❌ | ✅ | ✅ | ❌ |
| 6. Enumeration | ✅ | ✅✅ | ✅ | ⚠️ | ⚠️ | ✅ | ✅ | ✅ | ⚠️ | ✅ | ❌ | ⚠️ | ✅ | ⚠️ |
| 7. Continuous Range | ✅ | ✅ | ✅ | ⚠️ | ⚠️ | ✅ | ✅ | ✅ | ❌ | ✅ | ✅ | ⚠️ | ✅ | ❌ |
| 8. Priority | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅✅ | ⚠️ | ✅ | ⚠️ |
| 9. Scheduling | ✅ | ✅✅ | ✅ | ✅ | ⚠️ | ⚠️ | ✅ | ✅ | ❌ | ✅ | ✅ | ⚠️ | ✅ | ⚠️ |
| 10. State Machine | ✅ | ✅✅ | ✅ | ✅ | ⚠️ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ⚠️ |
| 11. Trees | ✅ | ✅ | ✅ | ✅ | ⚠️ | ✅ | ⚠️ | ✅ | ✅ | ✅ | ⚠️ | ⚠️ | ✅ | ❌ |
| 12. Data Structure | ✅ | ✅✅ | ✅ | ✅ | ✅✅ | ✅✅ | ✅ | ✅ | ⚠️ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 13. String Process | ⚠️ | ✅ | ✅ | ⚠️ | ⚠️ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅✅ | ⚠️ | ✅ | ❌ |

**Legend:** ✅✅ = Very Common | ✅ = Common | ⚠️ = Sometimes | ❌ = Rare/Not applicable

---

# 🌍 WORLD-BY-WORLD FOLLOW-UP EVOLUTION

## WORLD 1: DEPENDENCY (Topological Sort)

### Core Problem
```
Find valid ordering of dependent items or detect cycles
Algorithms: Topological Sort, Kahn's, DFS
```

### Key Follow-up Patterns

| Pattern | Question | Solution Shift |
|---------|----------|-----------------|
| **Constraint** | At most K dependencies per node? | Track in-degree ≤ K |
| **Size** | Streaming 10B edges? | Online topological sort |
| **Dynamic** | Add/remove edges dynamically? | Incremental topological sort |
| **Concurrency** | Multiple threads modify graph? | Lock-based or CAS updates |
| **Output** | Return all valid orderings? | Backtracking + topological sort |
| **Streaming** | Continuous edge additions? | Maintain order incrementally |
| **Error** | Cycle detected? | Return error with cycle path |
| **Fairness** | Lexicographic smallest order? | Use priority queue in sort |

### Real Examples

**Basic:** LeetCode 207 (Course Schedule)
**Follow-up (Constraint):** Modify to track in-degree ≤ K
**Follow-up (Dynamic):** LeetCode 1462 (Course Schedule IV) - dynamic queries
**Follow-up (Output):** Generate all topological orderings

---

## WORLD 2: REACHABILITY (DFS/BFS/Union Find)

### Core Problem
```
Check connectivity, count components, find groups
Algorithms: DFS, BFS, Union Find
```

### Key Follow-up Patterns

| Pattern | Question | Solution Shift |
|---------|----------|-----------------|
| **Constraint** | Within K distance reachable? | BFS with distance tracking |
| **Size** | 100B nodes? | Distributed union-find |
| **Dynamic** | Add edges, maintain components? | Dynamic union-find ⭐ MOST COMMON |
| **Concurrency** | Concurrent union operations? | ConcurrentUnionFind with locks |
| **Memory** | Billion nodes, limited RAM? | Streaming DFS or distributed |
| **Output** | Return all nodes in component? | DFS/BFS collection |
| **Direction** | Directed graph reachability? | DFS with direction semantics |
| **Streaming** | Continuous edge additions? | Incremental union-find |

### Real Examples

**Basic:** LeetCode 200 (Number of Islands)
**Follow-up (Dynamic):** Maintain components as edges added
**Follow-up (Streaming):** Online component tracking
**Follow-up (Batching):** Answer K reachability queries

---

## WORLD 3: SHORTEST PATH (BFS/Dijkstra/Bellman-Ford/Floyd-Warshall)

### Core Problem
```
Find minimum distance/cost between points
Algorithms: BFS, Dijkstra, Bellman-Ford, Floyd-Warshall
```

### Key Follow-up Patterns

| Pattern | Question | Solution Shift |
|---------|----------|-----------------|
| **Cost** | Weighted edges? ⭐ CLASSIC | Dijkstra (non-neg) or Bellman-Ford (neg) |
| **Constraint** | At most K edges? | DP or Bellman-Ford variant |
| **Size** | 1M nodes, 10M edges? | Dijkstra with PQ or matrix |
| **Dynamic** | Edge weights change? | Recompute or incremental |
| **Memory** | Can't store full graph? | Streaming shortest path |
| **Approx** | 1B nodes, approximate OK? | A* with heuristic |
| **Output** | Return path not distance? | Track parent pointers |
| **Direction** | Directed vs undirected? | Modify edge directions |
| **Error** | Negative cycle exists? | Detect and report |

### Real Examples

**Basic:** LeetCode 743 (Network Delay Time) - BFS
**Follow-up (Cost):** LeetCode 882 (Reachable Nodes) - Dijkstra with limit
**Follow-up (Constraint):** LeetCode 787 (Cheapest Flights) - Bellman-Ford or DP
**Follow-up (Output):** Track parents, reconstruct path

---

## WORLD 4: OPTIMIZATION (DP/Greedy)

### Core Problem
```
Maximize/minimize value or count ways
Algorithms: DP, Greedy, Divide & Conquer
```

### Key Follow-up Patterns

| Pattern | Question | Solution Shift |
|---------|----------|-----------------|
| **Cost** | Different weights for items? | Modify DP transition |
| **Constraint** | Can't use item > K times? ⭐ VERY COMMON | Add constraint to transition |
| **Size** | n=10B, optimize space? | Rolling array, O(1) space |
| **Dynamic** | New items added? | Incremental DP or rebuild |
| **Memory** | Can't store DP table? | O(1) or O(n) space optimization |
| **Approx** | 99% optimal quickly? | Greedy approximation |
| **Output** | Return items chosen? | Backtrack DP table |
| **Validation** | Given solution, optimal? | Check all dependencies |
| **Streaming** | Items arrive online? | Online DP |

### Real Examples

**Basic:** LeetCode 198 (House Robber)
**Follow-up (Constraint):** At most K items → add DP dimension
**Follow-up (Memory):** Rolling array for space optimization
**Follow-up (Output):** Backtrack to get chosen items

---

[Additional worlds 5-13 follow same format...]

---

# 🎯 QUICK LOOKUP BY FOLLOW-UP DIMENSION

### If asked about **COST**
```
Most Likely: WORLD 3, 4, 8
Question: "What if weights/costs change?"
Strategy: Show how algorithm adapts with different cost models
```

### If asked about **CONSTRAINTS**
```
Most Likely: WORLD 1, 4, 5, 10
Question: "At most K...?", "with limit...?"
Strategy: Add new DP dimension or pruning logic
```

### If asked about **SCALE/BIG DATA**
```
Most Likely: WORLD 3, 8, 12, 13
Question: "How would you handle 1B items?"
Strategy: Explain memory management, streaming, or distribution
```

### If asked about **THREADS/CONCURRENCY**
```
Most Likely: WORLD 2, 8, 12
Question: "Multiple threads access simultaneously?"
Strategy: Explain synchronization, locks, or thread-safe structures
```

### If asked about **DYNAMIC UPDATES**
```
Most Likely: WORLD 1, 2, 3
Question: "Edges/data added over time?"
Strategy: Show incremental maintenance without full recomputation
```

### If asked about **STREAMING/ONLINE**
```
Most Likely: WORLD 8, 11, 13
Question: "Data arrives one-by-one?"
Strategy: Explain online algorithms and single-pass processing
```

---

# 💬 INTERVIEW COMMUNICATION TEMPLATE

### When interviewer says "What if..."

**Step 1: Classify the change**
```
"I see - that's a [Cost/Constraint/Size/Dynamic] change."
```

**Step 2: Explain the bottleneck shift**
```
"With this change, the bottleneck shifts from [component A] to [component B]."
```

**Step 3: Propose the solution**
```
"So instead of [current algorithm], we'd use [new algorithm] which gives us [complexity]."
```

**Step 4: Show you anticipated it**
```
"This is why [original algorithm] was the right choice for those constraints,
but now that constraints change, we need to adapt."
```

### Example Conversation

```
Problem: Find shortest path (unweighted)
Your solution: BFS, O(V+E)

Interviewer: "What if edges have different weights?"

YOU: "That's a Cost change dimension. 
With weighted edges, BFS no longer guarantees shortest path,
so we'd switch to Dijkstra's with O((V+E)logV).
This is why BFS worked for unweighted - it assumed all edges equal cost."

Interviewer: "Good. What if there are negative weights?"

YOU: "That's a deeper Cost change. Dijkstra fails with negatives,
so we'd use Bellman-Ford with O(VE).
Or if we have negative cycles, we need to detect them.
Each algorithm is optimal for its cost assumptions."

Interviewer: "What if we have 1 billion nodes and 10 billion edges?"

YOU: "That's a Data Size change. We can't build full adjacency matrix.
For this scale, we'd use Dijkstra with priority queue (sparse optimized),
and possibly distributed processing if it doesn't fit in memory."
```

---

# 🚀 KEY TAKEAWAY

```
不是在做 20 道题
而是在理解 1 道题的 14 个演化维度

Not memorizing 20 problems
But understanding how 1 problem evolves across 14 dimensions

This is L6 thinking.
```

**Your superpower:** You can anticipate what the interviewer will ask before they ask it.

---

**Happy interviewing!** 🎯
