# 快速查阅卡片库 - Quick Reference Cards
## 算法框架 × 模式 × 数据结构 快速查找 | Framework × Pattern × DS Quick Lookup

---

## 📖 中文版本 | CHINESE VERSION

---

## 1️⃣ 7 步框架速记卡 | 7-Step Framework Quick Card

### 完整框架
```
问题 → Modeling → State → Aux → Transition → Solver → Complexity → Code
      建模      状态   辅助    转移        算法      复杂度      代码
```

### 每步的关键问题

| 步骤 | 英文名 | 关键问题 | 输出 | 示例 |
|------|--------|--------|------|------|
| 1️⃣ | **Modeling** | 问题本质是什么？节点和边是什么？ | 数学模型 | 图、序列、树、状态空间 |
| 2️⃣ | **State** | 状态由什么组成？为什么需要这些变量？ | 状态定义 | (node), (i,j), (mask) |
| 3️⃣ | **Aux** | 什么操作最频繁？什么 DS 最优？ | 数据结构 | Queue, Stack, Map, Set |
| 4️⃣ | **Transition** | A 状态如何到达 B？有什么约束？ | 转移规则 | for loop, recursion, BFS |
| 5️⃣ | **Solver** | 选什么算法？为什么？ | 算法选择 | BFS, DP, Greedy, DFS |
| 6️⃣ | **Complexity** | 时空复杂度是多少？能 AC 吗？ | 复杂度分析 | O(n²) time, O(n) space |
| 7️⃣ | **Code** | 按模板写代码 | 完整代码 | 3个标准模板 |

---

## 2️⃣ 12 个模式速查表 | 12 Patterns Quick Reference

### 快速模式识别决策树
```
问题关键词 → 推荐模式
─────────────────────────────
"最短路径" → BFS (无权) / Dijkstra (有权)
"最长/最大" → DP / Sliding Window / Greedy
"下一个/上一个" → Monotonic Stack
"滑动窗口最大值" → Monotonic Deque
"子数组和" → Prefix Sum / Sliding Window
"所有子集/排列" → Backtracking / DP
"连通分量" → Union Find / DFS
"访问所有节点" → Bitmask DP / BFS
```

### 12 个模式一览

| # | 模式 | 英文名 | 何时用 | 复杂度 | 难度 |
|---|------|--------|--------|--------|------|
| 1 | 单调栈 | Monotonic Stack | 找"下一个更大" | O(n) | ⭐⭐⭐ |
| 2 | 单调队列 | Monotonic Deque | 窗口最大值 | O(n) | ⭐⭐⭐⭐ |
| 3 | 前缀和 | Prefix Sum | 快速区间和 | O(1)查询 | ⭐⭐ |
| 4 | 滑动窗口 | Sliding Window | 最长/最短子串 | O(n) | ⭐⭐ |
| 5 | 序列DP | DP on Sequence | 子序列最优 | O(n²) | ⭐⭐⭐ |
| 6 | 区间DP | DP on Subarray | 区间合并最优 | O(n³) | ⭐⭐⭐⭐ |
| 7 | BFS | Breadth-First Search | 无权最短路径 | O(V+E) | ⭐⭐⭐ |
| 8 | DFS | Depth-First Search | 遍历/路径 | O(V+E) | ⭐⭐ |
| 9 | 回溯 | Backtracking | 生成所有组合 | O(2^n) | ⭐⭐⭐ |
| 10 | 并查集 | Union Find | 连通性问题 | O(α(n)) | ⭐⭐⭐ |
| 11 | 迪杰特拉 | Dijkstra | 有权最短路径 | O(ElogV) | ⭐⭐⭐⭐ |
| 12 | 位压缩 | Bitmask DP | 访问所有子集 | O(2^n·n) | ⭐⭐⭐⭐⭐ |

---

## 3️⃣ 数据结构决策表 | Data Structure Decision Matrix

### 根据操作选择 DS

| 核心操作 | 最优选择 | 复杂度 | 何时用 | 避免 |
|---------|---------|--------|--------|------|
| 标记访问 | boolean[] | O(1) | 节点编号0-n | 稀疏图用Set |
| 频率统计 | HashMap | O(1)avg | 动态计数 | 范围固定时用Array |
| 查询排序 | TreeMap | O(logn) | 需要有序 | 不频繁查询用排序 |
| 最小值 | PriorityQueue | O(logn) | 频繁pop/push | 静态数据用Sort |
| 连通性 | Union Find | O(α(n)) | 连通分量 | 动态删除用DFS |
| 路径追踪 | parent[] | O(1) | 恢复路径 | 需要保存所有用List |
| 前缀信息 | HashMap/Array | O(1) | 计数前缀 | 频繁修改用Segment Tree |
| 栈操作 | Stack/Deque | O(1) | LIFO | 需要随机访问用Array |
| 队列操作 | Queue/Deque | O(1) | FIFO | 需要优先级用PriorityQueue |

### 常见组合

```
BFS问题:
  Queue + Set/boolean[] + Graph
  
DFS问题:  
  Stack/Recursion + Set/boolean[] + Graph
  
Sliding Window:
  HashMap/int[] + left/right指针
  
Monotonic Stack:
  Stack + 结果数组
  
优先队列搜索:
  PriorityQueue + distance[] + visited[]
```

---

## 4️⃣ 复杂度速查表 | Complexity Analysis Quick Ref

### 时间复杂度等级（从快到慢）
```
O(1)       常数级 (最快)
O(log n)   对数级
O(√n)      平方根级
O(n)       线性级
O(n log n) 线性对数级
O(n²)      二次方级
O(n³)      三次方级
O(2^n)     指数级
O(n!)      阶乘级 (最慢)
```

### 数据范围与可行算法
```
n ≤ 10         任何算法（O(2^n) 可行）
n ≤ 100        O(n³) DP 可行
n ≤ 1,000      O(n²) DP 勉强可行
n ≤ 10,000     O(n²) 紧张 / O(n log n) 舒适
n ≤ 100,000    O(n log n) 必须 / O(n) 最好
n ≤ 1,000,000  只有 O(n) 或 O(n log n)
n > 10,000,000 只有 O(n) 或接近 O(1)
```

### 常见操作复杂度
```
数组访问        O(1)
数组查找        O(n)
数组排序        O(n log n)
HashMap get/put O(1) 平均
HashSet add/has O(1) 平均
TreeMap get/put O(log n)
PriorityQueue   O(log n) for push/pop
Binary Search   O(log n)
DFS/BFS         O(V + E)
Dijkstra        O((V+E) log V)
Union Find      O(α(n)) ≈ O(1)
```

---

## 5️⃣ 框架代码模板 | Framework Code Templates

### Template 1: BFS
```java
public Type solve(Input input) {
    // Modeling + State + Aux
    Queue<State> queue = new LinkedList<>();
    Set<State> visited = new HashSet<>();
    
    // 初始状态
    queue.offer(initialState);
    visited.add(initialState);
    
    // Transition + Solver
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            State curr = queue.poll();
            
            // 检查目标
            if (isTarget(curr)) return answer;
            
            // 状态转移
            for (State next : getNeighbors(curr)) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }
    }
    return notFound;
}
```

### Template 2: DP on Sequence
```java
public Type solve(Input input) {
    // State
    Type[] dp = new Type[n];
    
    // 初始状态
    dp[0] = base;
    
    // Transition
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (valid(j, i)) {
                dp[i] = optimize(dp[i], f(dp[j]));
            }
        }
    }
    return dp[n-1];
}
```

### Template 3: Sliding Window
```java
public Type solve(Type[] nums) {
    int left = 0;
    Type result = initialValue;
    Map<Type, Integer> window = new HashMap<>();
    
    for (int right = 0; right < nums.length; right++) {
        // 扩展窗口
        window.put(nums[right], window.getOrDefault(nums[right], 0) + 1);
        
        // 收缩窗口直到满足条件
        while (!isValid(window)) {
            window.put(nums[left], window.get(nums[left]) - 1);
            left++;
        }
        
        // 记录答案
        result = updateResult(result, right - left + 1);
    }
    return result;
}
```

---

## 6️⃣ 常见状态设计 | Common State Designs

### Level 1: 简单状态
```
BFS Grid:        (row, col)
BFS Graph:       (node)
DFS Tree:        (node)
Array Index:     (i)
```

### Level 2: 扩展状态（加约束/资源）
```
BFS with limit:     (node, k)     # k次转移限制
Race car:           (pos, speed)  # 位置和速度
Grid with key:      (r, c, keys)  # 位置和钥匙
```

### Level 3: 压缩状态（用位表示集合）
```
访问节点子集:  (node, visitedMask)
              # visitedMask = 二进制表示访问过的节点
例子: (0, 0b101) = 节点0，访问过节点0和2
```

### Level 4: 多源状态
```
多源BFS起点:  queue.offer(sourceNode)
             for每个源点
例子: 腐烂橙子问题，初始化所有腐烂橙子到队列
```

---

## 7️⃣ 问题识别速查 | Problem Identification Cheat Sheet

### 关键词 → 推荐模式 + 数据结构

```
"最短"/"最小"
  → BFS (无权) / Dijkstra (有权) / DP
  → 数据结构: Queue + Set / PriorityQueue

"最长"/"最大"
  → DP / Sliding Window / Greedy
  → 数据结构: dp[] + HashMap / Deque

"下一个大于"/"上一个小于"
  → Monotonic Stack
  → 数据结构: Stack

"滑动窗口最大值"
  → Monotonic Deque
  → 数据结构: Deque

"子数组和"/"区间和"
  → Prefix Sum / Sliding Window
  → 数据结构: HashMap / int[]

"所有"/"每一个"/"计数"
  → Backtracking / DP / BFS
  → 数据结构: List/Set + recursion

"连通"/"连接"/"分量"
  → Union Find / DFS / BFS
  → 数据结构: Union Find / Set

"选择最优"
  → DP
  → 数据结构: dp[]

"排列"/"组合"
  → Backtracking / DP
  → 数据结构: List + recursion
```

---

## 8️⃣ 常见陷阱速查 | Common Pitfalls Quick Reference

| 陷阱 | 症状 | 解决方案 |
|------|------|---------|
| 状态不完整 | 同一条件下有多个答案 | 加入缺失的维度 |
| 状态设计错误 | 转移不唯一或有矛盾 | 重新定义什么代表状态 |
| 初始值错误 | 最后答案偏差1或完全错 | 仔细检查 base case |
| 转移条件遗漏 | 某些情况处理不了 | 列举所有可能的转移 |
| 边界检查缺失 | 数组越界或空指针 | 检查边界：空、单个、n-1 |
| 重复计数 | 答案偏大 | 检查状态是否会重复访问 |
| 复杂度超限 | Time Limit Exceeded | 优化 DS 或转移方式 |
| 空间超限 | Memory Limit Exceeded | DP 滚动数组或减少辅助 DS |

---

## 9️⃣ 面试临场速查 | Interview Cheat Sheet

### 面试时间分配
```
5分钟  - 理解题意，确认约束
3分钟  - 建模 + 算法选择
10分钟 - 设计方案 + 复杂度分析
15分钟 - 编码实现
5分钟  - 走样例 + 讨论优化
```

### 卡住时的救命问题
```
"什么是状态？"
  → 定义清楚最小的信息单位

"状态如何转移？"
  → 从一个状态到下一个状态的规则

"为什么选这个算法？"
  → 引用状态空间大小和问题特性

"有没有遗漏的约束？"
  → 重读题目，标记所有限制

"能优化吗？"
  → 考虑更优的 DS 或状态压缩
```

### 讲述代码的方法
```
1. "这道题的本质是..."
   (用1句话描述问题)

2. "我的状态是... 转移是..."
   (2句话描述框架)

3. "我用... 数据结构来..."
   (解释为什么选这个 DS)

4. "时间/空间复杂度是..."
   (明确说出复杂度)
```

---

## 🔟 学习效率速查 | Learning Efficiency Tips

### 快速掌握一个模式的方法
```
Step 1 (15min): 看模式指南的讲解
Step 2 (20min): 看一个有详细注释的例题
Step 3 (30min): 自己做一题，不看答案
Step 4 (20min): 对照答案，补充框架注释
Step 5 (30min): 再做一题，时间内完成
Step 6 (15min): 总结这个模式的要点

总共: 2.5小时掌握一个模式
```

### 快速提升速度的方法
```
□ 模板化：用三个标准模板 (BFS, DP, Sliding Window)
□ 快速识别：看到题目立即识别模式（≤3分钟）
□ 跳过细节：先实现核心逻辑，再补充边界检查
□ 反复练习：同一个模式最少练3题
□ 定时模拟：定期做限时题目
```

---

---

# ENGLISH VERSION | 英文版本

---

## 1️⃣ 7-Step Framework Quick Card

### Complete Framework
```
Problem → Modeling → State → Aux → Transition → Solver → Complexity → Code
```

### Key Questions per Step

| Step | Name | Key Questions | Output | Examples |
|------|------|---------------|--------|----------|
| 1️⃣ | Modeling | What's the essence? What are nodes/edges? | Math model | Graph, Sequence, Tree |
| 2️⃣ | State | What variables? Why needed? | State def | (node), (i,j), (mask) |
| 3️⃣ | Aux | Most frequent ops? Best DS? | Data Structure | Queue, Stack, Map |
| 4️⃣ | Transition | How A→B? What constraints? | Transition rules | Loop, recursion, BFS |
| 5️⃣ | Solver | Which algorithm? Why? | Algorithm | BFS, DP, Greedy, DFS |
| 6️⃣ | Complexity | Time/space? AC? | Complexity | O(n²) time, O(n) space |
| 7️⃣ | Code | Use template | Complete code | 3 standard templates |

---

## 2️⃣ 12 Patterns Quick Reference

### Quick Pattern Identification Tree
```
Problem Keywords → Recommended Pattern
─────────────────────────────────────
"shortest path" → BFS (unweighted) / Dijkstra (weighted)
"longest/max" → DP / Sliding Window / Greedy
"next/previous greater" → Monotonic Stack
"sliding window max" → Monotonic Deque
"subarray sum" → Prefix Sum / Sliding Window
"all subsets/permutations" → Backtracking / DP
"connected components" → Union Find / DFS
"visit all nodes" → Bitmask DP / BFS
```

### 12 Patterns Overview

| # | Pattern | When to Use | Complexity | Difficulty |
|---|---------|------------|-----------|-----------|
| 1 | Monotonic Stack | Find "next greater" | O(n) | ⭐⭐⭐ |
| 2 | Monotonic Deque | Window max/min | O(n) | ⭐⭐⭐⭐ |
| 3 | Prefix Sum | Quick range sum | O(1) query | ⭐⭐ |
| 4 | Sliding Window | Longest/shortest substring | O(n) | ⭐⭐ |
| 5 | DP on Sequence | Optimal subsequence | O(n²) | ⭐⭐⭐ |
| 6 | DP on Subarray | Optimal interval merge | O(n³) | ⭐⭐⭐⭐ |
| 7 | BFS | Unweighted shortest path | O(V+E) | ⭐⭐⭐ |
| 8 | DFS | Traversal/paths | O(V+E) | ⭐⭐ |
| 9 | Backtracking | Generate all combinations | O(2^n) | ⭐⭐⭐ |
| 10 | Union Find | Connectivity | O(α(n)) | ⭐⭐⭐ |
| 11 | Dijkstra | Weighted shortest path | O(ElogV) | ⭐⭐⭐⭐ |
| 12 | Bitmask DP | Visit all subsets | O(2^n·n) | ⭐⭐⭐⭐⭐ |

---

## 3️⃣ Data Structure Decision Matrix

### Choose by Operation

| Operation | Best Choice | Complexity | When | Avoid |
|-----------|-------------|-----------|------|-------|
| Mark visited | boolean[] | O(1) | Nodes 0-n | Use Set for sparse |
| Count frequency | HashMap | O(1) avg | Dynamic | Use Array for fixed range |
| Range query | TreeMap | O(log n) | Need sorted | Use sort for infrequent |
| Min value | PriorityQueue | O(log n) | Frequent pop/push | Use sort for static |
| Connectivity | Union Find | O(α(n)) | Connected components | Use DFS for dynamic |
| Path recovery | parent[] | O(1) | Restore path | Use List for all paths |
| Prefix tracking | HashMap/Array | O(1) | Count prefixes | Use Segment Tree for frequent updates |
| Stack ops | Stack/Deque | O(1) | LIFO | Use Array for random access |
| Queue ops | Queue/Deque | O(1) | FIFO | Use PriorityQueue for priority |

---

## 4️⃣ Complexity Analysis Quick Reference

### Time Complexity Levels (Fast to Slow)
```
O(1)       Constant
O(log n)   Logarithmic
O(√n)      Square root
O(n)       Linear
O(n log n) Linearithmic
O(n²)      Quadratic
O(n³)      Cubic
O(2^n)     Exponential
O(n!)      Factorial
```

### Data Range vs Viable Algorithm
```
n ≤ 10         Any algorithm (O(2^n) ok)
n ≤ 100        O(n³) DP ok
n ≤ 1,000      O(n²) DP tight
n ≤ 10,000     O(n²) tight / O(n log n) comfortable
n ≤ 100,000    O(n log n) required / O(n) best
n ≤ 1,000,000  Only O(n) or O(n log n)
n > 10,000,000 Only O(n) or near O(1)
```

---

## 5️⃣ Code Templates

### Template 1: BFS
```java
public Type solve(Input input) {
    Queue<State> queue = new LinkedList<>();
    Set<State> visited = new HashSet<>();
    
    queue.offer(initialState);
    visited.add(initialState);
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            State curr = queue.poll();
            if (isTarget(curr)) return answer;
            
            for (State next : getNeighbors(curr)) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }
    }
    return notFound;
}
```

### Template 2: DP on Sequence
```java
public Type solve(Input input) {
    Type[] dp = new Type[n];
    dp[0] = base;
    
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (valid(j, i)) {
                dp[i] = optimize(dp[i], f(dp[j]));
            }
        }
    }
    return dp[n-1];
}
```

### Template 3: Sliding Window
```java
public Type solve(Type[] nums) {
    int left = 0;
    Type result = initialValue;
    Map<Type, Integer> window = new HashMap<>();
    
    for (int right = 0; right < nums.length; right++) {
        window.put(nums[right], window.getOrDefault(nums[right], 0) + 1);
        
        while (!isValid(window)) {
            window.put(nums[left], window.get(nums[left]) - 1);
            left++;
        }
        result = updateResult(result, right - left + 1);
    }
    return result;
}
```

---

## 6️⃣ Common State Designs

### Level 1: Simple States
```
BFS Grid:        (row, col)
BFS Graph:       (node)
DFS Tree:        (node)
Array Index:     (i)
```

### Level 2: Extended States
```
BFS with limit:     (node, k)
Race car:           (pos, speed)
Grid with key:      (r, c, keys)
```

### Level 3: Compressed States
```
Visit node subset:  (node, visitedMask)
Example: (0, 0b101) = node 0, visited nodes 0 and 2
```

---

## 7️⃣ Problem Identification Cheat Sheet

### Keywords → Pattern + DS

```
"shortest"/"minimum"
  → BFS (unweighted) / Dijkstra (weighted) / DP
  → DS: Queue + Set / PriorityQueue

"longest"/"maximum"
  → DP / Sliding Window / Greedy
  → DS: dp[] + HashMap / Deque

"next greater"/"previous smaller"
  → Monotonic Stack
  → DS: Stack

"sliding window max/min"
  → Monotonic Deque
  → DS: Deque

"subarray sum"/"range sum"
  → Prefix Sum / Sliding Window
  → DS: HashMap / int[]

"all"/"each"/"count"
  → Backtracking / DP / BFS
  → DS: List/Set + recursion

"connected"/"connectivity"
  → Union Find / DFS / BFS
  → DS: Union Find / Set

"optimal selection"
  → DP
  → DS: dp[]

"permutation"/"combination"
  → Backtracking / DP
  → DS: List + recursion
```

---

## 8️⃣ Common Pitfalls

| Pitfall | Symptom | Solution |
|---------|---------|----------|
| Incomplete state | Multiple answers same condition | Add missing dimension |
| Wrong state | Non-unique transition | Redefine state meaning |
| Wrong initialization | Off by 1 or wrong | Check base case carefully |
| Missing transitions | Some cases fail | Enumerate all transitions |
| Missing boundary check | Array out of bounds | Check: empty, single, n-1 |
| Double counting | Answer too large | Check for repeated visits |
| Complexity exceeded | TLE | Optimize DS or transition |
| Memory exceeded | MLE | Use rolling array or reduce DS |

---

## 9️⃣ Interview Cheat Sheet

### Time Allocation (30-45 minutes)
```
5 min   - Understand problem, confirm constraints
3 min   - Modeling + algorithm selection
10 min  - Design approach + complexity analysis
15 min  - Implement code
5 min   - Walk examples + discuss optimization
```

### Stuck? Ask Yourself
```
"What's the state?"
  → Define minimum information unit

"How does state transition?"
  → Rules from one state to next

"Why this algorithm?"
  → Reference state space and features

"Any missed constraints?"
  → Reread problem, mark all limits

"Can it be optimized?"
  → Consider better DS or compression
```

### How to Explain Code
```
1. "Essence: ..." (1 sentence problem description)
2. "State: ..., Transition: ..." (2 sentences framework)
3. "Using ... because ..." (explain DS choice)
4. "Time/Space: ..." (state complexity)
```

---

## 🔟 Learning Efficiency Tips

### Master a Pattern in 2.5 Hours
```
15 min - Read pattern explanation
20 min - Study annotated example
30 min - Solve own problem, no answer
20 min - Compare solution, add annotations
30 min - Solve another problem within time
15 min - Summarize pattern insights
─────────
Total: 2.5 hours per pattern
```

### Speed Improvement Methods
```
□ Templatize: Use 3 standard templates
□ Quick identification: Pattern in ≤3 min
□ Skip details: Core logic first, bounds later
□ Repeated practice: Min 3 problems per pattern
□ Timed practice: Regular speed challenges
```

---

**Document Version:** 1.0  
**Created:** 2026-06-08  
**Language:** Bilingual (Chinese/English)
