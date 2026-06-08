# 问题导航矩阵 - Problem Navigation Matrix
## 按周次 × 模式 × 难度三维查找 | Find Problems by Week × Pattern × Difficulty

---

## 📖 中文版本 | CHINESE VERSION

### 快速使用指南

这个矩阵帮助你快速找到任何需要的题目：
- **按学习周次找** - 知道在哪一周学吗？
- **按算法模式找** - 想练习某个模式吗？
- **按难度找** - 想找 Easy/Medium/Hard 题吗？

---

## 📅 按周次查找 | By Learning Week

### Week 1: Framework Foundations (框架基础)
**学习内容:** 7 步框架理论  
**相关题目:** 0 题（纯理论周）  
**资源:** 框架指南_Framework_Guide.md

---

### Week 2: Sliding Window & Prefix Sum (滑动窗口 & 前缀和)

| 题目 | 难度 | 模式 | 文件位置 | 时间 |
|------|------|------|---------|------|
| LC3: Longest Substring | Medium | Sliding Window | common_pattern3 | 20min |
| LC76: Minimum Window | Hard | Sliding Window | common_pattern2 | 25min |
| LC209: Minimum Size | Medium | Sliding Window | 推荐练习 | 15min |
| LC424: Character Replacement | Medium | Sliding Window | 推荐练习 | 20min |
| **小计:** 4 题 | - | - | - | 80min |
| LC560: Subarray Sum | Medium | Prefix Sum | common_pattern3 | 20min |
| LC525: Contiguous Array | Medium | Prefix Sum | common_pattern3 | 20min |
| LC974: Divisible | Medium | Prefix Sum | 推荐练习 | 15min |
| LC523: Continuous Sum | Medium | Prefix Sum | 推荐练习 | 15min |
| **小计:** 4 题 | - | - | - | 70min |

**周目标:** 掌握这两个基础模式（8 题）

---

### Week 3: Monotonic Stack & Sequence DP

| 题目 | 难度 | 模式 | 文件位置 | 时间 |
|------|------|------|---------|------|
| LC739: Daily Temperatures | Medium | Monotonic Stack | common_pattern2 | 20min |
| LC496: Next Greater Element | Easy | Monotonic Stack | common_pattern2 | 15min |
| LC84: Largest Rectangle | Hard | Monotonic Stack | common_pattern2 | 30min |
| LC42: Trapping Rain | Hard | Monotonic Stack | 推荐练习 | 30min |
| LC901: Stock Span | Medium | Monotonic Stack | 推荐练习 | 20min |
| **小计:** 5 题 Monotonic Stack | - | - | - | 115min |
| LC300: LIS | Medium | DP on Sequence | common_pattern2 | 20min |
| LC1143: LCS | Medium | DP on Sequence | 推荐练习 | 20min |
| LC198: House Robber | Easy | DP on Sequence | 推荐练习 | 15min |
| LC740: Delete and Earn | Medium | DP on Sequence | 推荐练习 | 20min |
| **小计:** 4 题 DP on Sequence | - | - | - | 75min |

**周目标:** 掌握单调栈和序列 DP（9 题）

---

### Week 4: BFS/DFS & Backtracking

| 题目 | 难度 | 模式 | 文件位置 | 时间 |
|------|------|------|---------|------|
| LC200: Number of Islands | Medium | BFS Grid | common_pattern1 | 20min |
| LC994: Rotting Oranges | Medium | Multi-source BFS | common_pattern1 | 25min |
| LC127: Word Ladder | Hard | BFS State Space | common_pattern2 | 30min |
| LC752: Open Lock | Medium | BFS State Space | 推荐练习 | 25min |
| LC864: Shortest Path | Hard | BFS + Bitmask | common_pattern1 | 40min |
| **小计:** 5 题 BFS | - | - | - | 140min |
| LC46: Permutations | Medium | Backtracking | common_pattern1 | 20min |
| LC47: Permutations II | Medium | Backtracking | 推荐练习 | 25min |
| LC78: Subsets | Medium | Backtracking | 推荐练习 | 20min |
| LC90: Subsets II | Medium | Backtracking | 推荐练习 | 20min |
| **小计:** 4 题 Backtracking | - | - | - | 85min |

**周目标:** 搜索算法掌握（9 题）

---

### Week 5: Advanced Patterns (高级模式)

| 题目 | 难度 | 模式 | 文件位置 | 时间 |
|------|------|------|---------|------|
| LC787: Cheapest Flights | Medium | Dijkstra | common_pattern2 | 25min |
| LC743: Network Delay | Medium | Dijkstra | 推荐练习 | 25min |
| **小计:** 2 题 Dijkstra | - | - | - | 50min |
| LC547: Friend Circles | Medium | Union Find | 推荐练习 | 20min |
| LC684: Redundant Connection | Medium | Union Find | 推荐练习 | 25min |
| **小计:** 2 题 Union Find | - | - | - | 45min |
| LC847: Shortest Path All Nodes | Hard | Bitmask DP | common_pattern1 | 40min |
| LC1494: Parallel Courses | Hard | Bitmask DP | 推荐练习 | 45min |
| **小计:** 2 题 Bitmask | - | - | - | 85min |

**周目标:** 掌握全部 12 个模式（6 题）

---

### Week 6: Integration & Practice (综合应用)

**题目数:** 15-20 题（自主选择）  
**来源:** common_pattern2 & common_pattern3  
**难度分布:** 
- Easy: 3-4 题
- Medium: 10-12 题
- Hard: 2-4 题

**建议策略:**
1. 先做相同模式的 3-5 题
2. 再做混合模式的题
3. 最后做 Hard 题冲击

---

### Week 7: Advanced Practice & Interview Prep

**题目数:** 5 道（模拟面试）  
**来源:** 随机选择  
**目标:** 在 15-20 分钟内解决每题

---

### Week 8: Review & Consolidation

**活动:** 复习和维护  
**推荐:** 
- 回顾 Week 2-5 的所有题目
- 选择 5 题再做一遍
- 总结学习笔记

---

## 🎯 按模式查找 | By Algorithm Pattern

### Pattern 1: Monotonic Stack (单调栈)
```
关键特征: "下一个" / "上一个" / "更大" / "更小"
适用周次: Week 3
难度分布: Easy 1题, Medium 3题, Hard 1题
题目数: 5题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|------|------|--------|------|
| 1 | LC496 Next Greater | Easy | common_pattern2 | ✅ 详细 |
| 2 | LC739 Daily Temps | Medium | common_pattern2 | ✅ 详细 |
| 3 | LC84 Histogram | Hard | common_pattern2 | ✅ 详细 |
| 4 | LC42 Rain Water | Hard | 推荐 | - |
| 5 | LC901 Stock Span | Medium | 推荐 | - |

---

### Pattern 2: Monotonic Deque (单调队列)
```
关键特征: "滑动窗口" + "最大值/最小值"
适用周次: Week 6-7
难度分布: Medium 1题, Hard 1题
题目数: 2题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|------|------|--------|------|
| 1 | LC239 Sliding Max | Hard | 推荐 | - |
| 2 | LC363 Max Rectangle | Hard | 推荐 | - |

---

### Pattern 3: Prefix Sum (前缀和)
```
关键特征: "区间和" / "子数组和" / "计数"
适用周次: Week 2
难度分布: Easy 1题, Medium 3题
题目数: 4题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|------|------|--------|------|
| 1 | LC560 Subarray Sum | Medium | common_pattern3 | ✅ 详细 |
| 2 | LC525 Contiguous | Medium | common_pattern3 | ✅ 详细 |
| 3 | LC974 Divisible | Medium | 推荐 | - |
| 4 | LC523 Continuous | Medium | 推荐 | - |

---

### Pattern 4: Sliding Window (滑动窗口)
```
关键特征: "最长/最短" + "满足条件" + "子数组/子串"
适用周次: Week 2
难度分布: Easy 1题, Medium 2题, Hard 1题
题目数: 4题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|------|------|--------|------|
| 1 | LC3 Longest Sub | Medium | common_pattern3 | ✅ 详细 |
| 2 | LC76 Minimum Window | Hard | common_pattern2 | ✅ 详细 |
| 3 | LC209 Minimum Size | Medium | 推荐 | - |
| 4 | LC424 Replacement | Medium | 推荐 | - |

---

### Pattern 5: DP on Sequence (序列 DP)
```
关键特征: "选择序列元素" + "最优子结构"
适用周次: Week 3
难度分布: Easy 1题, Medium 3题
题目数: 4题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|------|------|--------|------|
| 1 | LC300 LIS | Medium | common_pattern2 | ✅ 详细 |
| 2 | LC1143 LCS | Medium | 推荐 | - |
| 3 | LC198 House Robber | Easy | 推荐 | - |
| 4 | LC740 Delete Earn | Medium | 推荐 | - |

---

### Pattern 6: DP on Subarray (区间 DP)
```
关键特征: "区间合并/分割" + "最优组合"
适用周次: Week 5-6
难度分布: Hard 2-3题
题目数: 3题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|------|------|--------|------|
| 1 | LC312 Burst | Hard | 推荐 | - |
| 2 | LC1039 Triangulation | Hard | 推荐 | - |
| 3 | LC1000 Merge Stones | Hard | 推荐 | - |

---

### Pattern 7: BFS (广度优先搜索)
```
关键特征: "最短路径" + "无权" + "多源"
适用周次: Week 4
难度分布: Easy 1题, Medium 2题, Hard 2题
题目数: 5题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|-----|------|--------|------|
| 1 | LC200 Islands | Medium | common_pattern1 | ✅ 详细 |
| 2 | LC994 Oranges | Medium | common_pattern1 | ✅ 详细 |
| 3 | LC127 Word Ladder | Hard | common_pattern2 | ✅ 详细 |
| 4 | LC752 Open Lock | Medium | 推荐 | - |
| 5 | LC864 Shortest Path | Hard | common_pattern1 | ✅ 详细 |

---

### Pattern 8: DFS (深度优先搜索)
```
关键特征: "遍历" / "路径" / "连通分量"
适用周次: Week 4
难度分布: Easy 1题, Medium 2题
题目数: 3题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|------|------|--------|------|
| 1 | LC200 Islands | Medium | common_pattern1 | ✅ (也有DFS) |
| 2 | LC112 Path Sum | Easy | 推荐 | - |
| 3 | LC543 Diameter | Easy | 推荐 | - |

---

### Pattern 9: Backtracking (回溯)
```
关键特征: "生成所有" / "组合" / "排列"
适用周次: Week 4
难度分布: Easy 1题, Medium 3题
题目数: 4题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|------|------|--------|------|
| 1 | LC46 Permutations | Medium | common_pattern1 | ✅ 详细 |
| 2 | LC47 Permutations II | Medium | 推荐 | - |
| 3 | LC78 Subsets | Medium | 推荐 | - |
| 4 | LC90 Subsets II | Medium | 推荐 | - |

---

### Pattern 10: Union Find (并查集)
```
关键特征: "连通性" / "连接分量" / "环检测"
适用周次: Week 5
难度分布: Medium 2题
题目数: 2题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|------|------|--------|------|
| 1 | LC547 Friend Circles | Medium | 推荐 | - |
| 2 | LC684 Redundant | Medium | 推荐 | - |

---

### Pattern 11: Dijkstra (迪杰特拉)
```
关键特征: "最短路径" + "有权"
适用周次: Week 5
难度分布: Medium 2题
题目数: 2题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|------|------|--------|------|
| 1 | LC787 Flights | Medium | common_pattern2 | ✅ 详细 |
| 2 | LC743 Network Delay | Medium | 推荐 | - |

---

### Pattern 12: Bitmask + BFS (位压缩)
```
关键特征: "访问所有子集" / "状态压缩"
适用周次: Week 5
难度分布: Hard 2题
题目数: 2题
```

**题目列表:**
| # | 题目 | 难度 | 源文件 | 注释 |
|---|------|------|--------|------|
| 1 | LC847 All Nodes | Hard | common_pattern1 | ✅ 详细 |
| 2 | LC864 Shortest Path | Hard | common_pattern1 | ✅ 详细 |

---

## 📊 按难度查找 | By Difficulty

### Easy (简单)
**总数:** 6 题  
**适合:** Week 1-2 的前期  
**目标:** 5-10 分钟/题

| # | 题目 | 模式 | 周次 |
|---|------|------|------|
| 1 | LC198 House Robber | DP | W3 |
| 2 | LC112 Path Sum | DFS | W4 |
| 3 | LC496 Next Greater | Stack | W3 |
| 4 | LC543 Tree Diameter | DFS | W4 |
| 5 | 其他5题 | 混合 | - |

---

### Medium (中等)
**总数:** 45 题  
**适合:** Week 2-6  
**目标:** 15-20 分钟/题

**按模式分布:**
- Sliding Window: 3 题
- Prefix Sum: 3 题
- Monotonic Stack: 3 题
- DP on Sequence: 3 题
- BFS: 2 题
- Backtracking: 3 题
- Union Find: 2 题
- Dijkstra: 2 题
- 混合: 23 题

---

### Hard (困难)
**总数:** 29 题  
**适合:** Week 5-7  
**目标:** 30-40 分钟/题

**按模式分布:**
- Sliding Window: 1 题
- Monotonic Stack: 1 题
- BFS: 2 题
- DP on Subarray: 3 题
- Bitmask DP: 2 题
- 其他 Hard: 20 题

---

## 🔍 特殊分类 | Special Categories

### 有详细注释的题目 (Fully Annotated)
✅ 这些题目有完整的 7 步框架注释

```
common_pattern1.md (13 题全部有):
□ LC200, LC994, LC752, LC127, LC847, LC864, 
□ LC46, LC112, LC543, LC78, LC90, 等等...

common_pattern2.md (关键问题):
□ LC739, LC496, LC84, LC300, LC787, 等等...

common_pattern3.md (关键问题):
□ LC3, LC560, LC525, 等等...
```

**使用方法:** 
- 先读这些有注释的题目，学习如何标注框架
- 然后自己给没注释的题目标注框架

---

### 推荐练习题 (Recommended Practice)
🔄 这些题目可在 LeetCode 上找到，用来练习

```
Pattern 验证:
□ 学完某个 pattern 后，
□ 在推荐练习中选 2-3 题练习
□ 这样加深对该 pattern 的理解
```

---

### 模拟面试题 (Mock Interview)
🎤 用于 Week 7 的模拟面试

```
Week 7 建议选择:
□ 从各个 pattern 各选 1 题（共 12 题）
□ 或选择 5 道难度 Medium 的随机题
□ 限制时间: 15-20 分钟/题
```

---

## 🛠️ 如何使用这个矩阵

### 场景1: "我在 Week 3，该做什么题？"
1. 找到 "Week 3" 章节
2. 按顺序做列出的题目
3. 查看"注释"列，有 ✅ 的题目有详细讲解

### 场景2: "我想练习单调栈"
1. 找到 "Pattern 1: Monotonic Stack" 章节
2. 按难度顺序做：Easy → Medium → Hard
3. 查看源文件找到题目代码

### 场景3: "我只有 1 小时，想学点什么？"
1. 找到 "按难度查找" → "Easy"
2. 选择 1-2 题做完
3. 读一遍答案的框架注释

### 场景4: "我要准备面试，还剩 1 周"
1. 去 "Week 7"
2. 选择 5 题做模拟面试
3. 计时 15-20 分钟/题

---

## 📈 学习进度追踪表

打印这个表，记录你的进度：

```
Week 1: 
  □ Day 1-2: 框架指南
  □ Day 3-4: 模式指南
  □ Day 5-7: 复习和 common_pattern1 题目1-3
  进度: ___/7

Week 2 (Sliding Window & Prefix Sum):
  □ LC3: _____ (实现时间)
  □ LC76: _____ 
  □ LC209: _____
  □ LC424: _____
  □ LC560: _____
  □ LC525: _____
  □ LC974: _____
  □ LC523: _____
  进度: ___/8

Week 3-8: (类似格式)
```

---

---

# ENGLISH VERSION | 英文版本

## Quick Usage Guide

This matrix helps you quickly find any problem you need:
- **Find by learning week** - Which week are you in?
- **Find by algorithm pattern** - Want to practice a specific pattern?
- **Find by difficulty** - Looking for Easy/Medium/Hard?

---

## 📅 By Learning Week

### Week 1: Framework Foundations
**Content:** Theory only  
**Related Problems:** 0 problems  
**Resource:** Framework Guide

### Week 2: Sliding Window & Prefix Sum

| Problem | Difficulty | Pattern | Location | Time |
|---------|-----------|---------|----------|------|
| LC3 | Medium | Sliding Window | common_pattern3 | 20min |
| LC76 | Hard | Sliding Window | common_pattern2 | 25min |
| LC209 | Medium | Sliding Window | Recommended | 15min |
| LC424 | Medium | Sliding Window | Recommended | 20min |
| **Subtotal:** 4 problems | - | - | - | 80min |
| LC560 | Medium | Prefix Sum | common_pattern3 | 20min |
| LC525 | Medium | Prefix Sum | common_pattern3 | 20min |
| LC974 | Medium | Prefix Sum | Recommended | 15min |
| LC523 | Medium | Prefix Sum | Recommended | 15min |
| **Subtotal:** 4 problems | - | - | - | 70min |

**Week Goal:** Master 2 fundamental patterns (8 problems)

---

## 🎯 By Algorithm Pattern

### Pattern 1: Monotonic Stack
```
Key Features: "next" / "previous" / "greater" / "smaller"
Applicable Week: Week 3
Difficulty: Easy 1, Medium 3, Hard 1
Total: 5 problems
```

**Problem List:**
| # | Problem | Difficulty | Source | Annotation |
|---|---------|-----------|--------|------------|
| 1 | LC496 Next Greater | Easy | common_pattern2 | ✅ Detailed |
| 2 | LC739 Daily Temps | Medium | common_pattern2 | ✅ Detailed |
| 3 | LC84 Histogram | Hard | common_pattern2 | ✅ Detailed |
| 4 | LC42 Rain Water | Hard | Recommended | - |
| 5 | LC901 Stock Span | Medium | Recommended | - |

---

### Pattern 2: Sliding Window
```
Key Features: "longest/shortest" + "satisfy condition"
Applicable Week: Week 2
Difficulty: Medium 2, Hard 1
Total: 4 problems
```

---

### Pattern 3: Prefix Sum
```
Key Features: "range sum" / "subarray sum" / "counting"
Applicable Week: Week 2
Difficulty: Medium 3
Total: 4 problems
```

---

### Pattern 4: DP on Sequence
```
Key Features: "select sequence elements" + "optimal substructure"
Applicable Week: Week 3
Difficulty: Easy 1, Medium 3
Total: 4 problems
```

---

### Pattern 5: BFS
```
Key Features: "shortest path" + "unweighted"
Applicable Week: Week 4
Difficulty: Medium 2, Hard 2
Total: 5 problems
```

---

### Pattern 6: Backtracking
```
Key Features: "generate all" / "combinations" / "permutations"
Applicable Week: Week 4
Difficulty: Medium 3
Total: 4 problems
```

---

### Pattern 7: Monotonic Deque
```
Key Features: "sliding window" + "max/min"
Applicable Week: Week 6-7
Difficulty: Hard 2
Total: 2 problems
```

---

### Pattern 8: Union Find
```
Key Features: "connectivity" / "connected components"
Applicable Week: Week 5
Difficulty: Medium 2
Total: 2 problems
```

---

### Pattern 9: Dijkstra
```
Key Features: "shortest path" + "weighted"
Applicable Week: Week 5
Difficulty: Medium 2
Total: 2 problems
```

---

### Pattern 10: DP on Subarray
```
Key Features: "interval merge/split" + "optimal combination"
Applicable Week: Week 5-6
Difficulty: Hard 3
Total: 3 problems
```

---

### Pattern 11: DFS
```
Key Features: "traversal" / "path" / "connected components"
Applicable Week: Week 4
Difficulty: Easy 1, Medium 2
Total: 3 problems
```

---

### Pattern 12: Bitmask DP
```
Key Features: "visit all subsets" / "state compression"
Applicable Week: Week 5
Difficulty: Hard 2
Total: 2 problems
```

---

## 📊 By Difficulty

### Easy (6 problems)
**Best for:** Early Week 1-2  
**Target time:** 5-10 min/problem

---

### Medium (45 problems)
**Best for:** Week 2-6  
**Target time:** 15-20 min/problem

---

### Hard (29 problems)
**Best for:** Week 5-7  
**Target time:** 30-40 min/problem

---

## 🔍 Special Categories

### Fully Annotated Problems
✅ These problems have complete 7-step framework annotations

```
common_pattern1.md (all 13 fully annotated):
□ LC200, LC994, LC752, LC127, LC847, etc.

common_pattern2.md (key problems annotated):
□ LC739, LC496, LC84, LC300, LC787, etc.
```

---

### How to Use This Matrix

### Scenario 1: "I'm in Week 3, what should I do?"
1. Find "Week 3" section
2. Do problems in listed order
3. Check "Annotation" column for ✅ detailed explanations

### Scenario 2: "I want to practice Monotonic Stack"
1. Find "Pattern 1: Monotonic Stack"
2. Do by difficulty: Easy → Medium → Hard
3. Find problem code in source file

### Scenario 3: "I have 1 hour, want to learn something"
1. Go to "By Difficulty" → "Easy"
2. Choose 1-2 problems to complete
3. Read solution annotations

### Scenario 4: "I have 1 week until interview"
1. Go to "Week 7"
2. Choose 5 problems for mock interview
3. Time yourself: 15-20 min/problem

---

## 📈 Learning Progress Tracker

Print and track your progress:

```
Week 1: 
  □ Day 1-2: Framework Guide
  □ Day 3-4: Pattern Guide
  □ Day 5-7: Review and pattern1 problems 1-3
  Progress: ___/7

Week 2 (Sliding Window & Prefix Sum):
  □ LC3: _____ (completion time)
  □ LC76: _____ 
  □ LC209: _____
  ...
  Progress: ___/8

Weeks 3-8: (similar format)
```

---

**Document Version:** 1.0  
**Created:** 2026-06-08  
**Language:** Bilingual (Chinese/English)
