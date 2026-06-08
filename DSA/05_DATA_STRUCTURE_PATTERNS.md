# 辅助数据结构设计完整指南 | Auxiliary Data Structure Design Guide

## 中文版本 | CHINESE VERSION

# 辅助数据结构设计 - 快速参考

| DS类型 | 数据结构 | 复杂度 | 使用场景 | 例子 |
|---------|---|----------|----------|---------|
| **Visited** | boolean[]/Set | O(1) | Mark visited states | BFS, DFS, Graph |
| **Graph** | Adjacency List | O(V+E) | Store connections | Connected Components |
| **Weighted Graph** | Map<Node,List<Edge>> | O(log E) | Weighted edges | Dijkstra, Shortest Path |
| **Frequency** | Map<K,Int> | O(1) avg | Count occurrences | Sliding Window, Top K |
| **Indegree** | int[] | O(1) | Track dependencies | Topological Sort |
| **Distance** | int[] | O(1) | Track min distances | Dijkstra, BFS |
| **Parent** | int[] | O(1) | Reconstruct paths | Path recovery |
| **Priority Queue** | PriorityQueue | O(log n) | Process by priority | Dijkstra, Top K |
| **Monotonic Queue** | Deque | O(1) amort | Maintain order | Sliding Window Max |
| **Prefix Sum** | int[] or Map | O(1) query | Quick range sum | Range Sum Query |
| **Union Find** | int[] + rank | O(α(n)) | Track connectivity | Accounts Merge |
| **Backtracking** | List + boolean[] | O(1) | Track choices | Permutation, Combination |
| **位掩码** | int | O(1) | 压缩状态 | LC847, LC864 |
| **备忘录缓存** | Map/数组 | O(1) 平均 | 缓存结果 | DP、记忆化 |
| **结果分组** | Map<K,List<V>> | O(1) 平均 | 分组结果 | 账户合并、字母异位词 |

⸻

# 如何设计辅助数据结构 - Design Methodology

这是面试中最关键的决策点。选对辅助数据结构，直接影响代码复杂度和运行效率。

## 第一步：识别问题的核心需求

先问自己：**我在解这个问题中需要做什么？**

常见的核心操作：
```
1. 标记(Mark) → boolean[], Set, visited
2. 计数(Count) → Map<K, Int>, frequency
3. 查找(Search) → Map, Set, Trie
4. 排序(Order) → PriorityQueue, 排序列表
5. 连接(Connect) → 并查集, 邻接表
6. 跟踪(Track) → 父亲数组, Stack, Queue
7. 恢复(Recover) → 父亲/路径数组
8. 优化(Optimize) → DP数组, 备忘录Map
```

⸻

## 第二步：按需求匹配 - 核心操作矩阵

| 核心操作 | 轻度需求 | 中度需求 | 高度需求 |
|---------|---------|---------|---------|
| 标记访问 | boolean[] | Set | 位掩码 |
| 计数频率 | HashMap | HashMap | HashMap (预计算) |
| 查找 | HashMap | HashSet | Trie |
| 排序 | PriorityQueue | TreeMap | 线段树 |
| 连接 | List | 邻接表 | 并查集 |
| 追踪路径 | 父亲[] | Stack/Queue | 路径 + 成本 |
| 恢复 | 父亲[] | 父亲[] + 指针 | 回溯List |
| 优化缓存 | HashMap | 数组 | 2D数组 |

⸻

## 第三步：决策树 - Decision Tree

**问题1：需要查询吗？**
```
YES → 哈希表 (HashMap/HashSet)
NO  → 线性存储 (数组/列表)
```

**问题2：查询频繁吗？(超过100次)**
```
YES → 哈希表 或 树/Trie (看具体场景)
NO  → 线性查找 足够
```

**问题3：需要排序吗？**
```
YES → PriorityQueue 或 TreeMap
NO  → 无序 就够
```

**问题4：有多维信息吗？(如count+index)**
```
YES → 考虑 Map<K, 自定义对象> 或分离数据
NO  → 单Map足够
```

**问题5：空间换时间值得吗？**
```
YES (n < 10^6) → 预计算 + 额外存储
NO  (n > 10^7) → 按需计算，最小存储
```

⸻

## 第四步：常见场景速查表

### 场景1：BFS/DFS 图遍历
**需要的辅助结构：**
```java
Set<节点> visited = new HashSet<>();    // 防止重复访问
Queue<节点> queue = new LinkedList<>(); // BFS队列
// 或
Stack<节点> stack = new Stack<>();      // DFS栈
```
**为什么选Set？**
- O(1) 查询和添加
- 自动去重
- 空间效率 (vs boolean数组)

**何时用boolean[]代替？**
- 节点编号 0 到 n-1 且 n < 10^6
- 需要极快访问和写入

---

### 场景2：频率统计 (滑窗, Top K)
**需要的辅助结构：**
```java
Map<Integer, Integer> freq = new HashMap<>();
// 统计每个数字出现次数

// 进阶: 同时需要最大频率
int maxFreq = 0;
Map<Integer, Integer> freq = new HashMap<>();
```

**为什么HashMap？**
- O(1) 平均 getOrDefault/put
- 动态大小 (不用预先知道范围)
- 键可以是任意类型 (Integer, String, 对象)

**何时用int[]代替？**
- 数字范围 [0, 26] (如字母)
- 需要极快速度且空间充足

---

### 场景3：最大/最小值查询 (Dijkstra, Top K)
**需要的辅助结构：**
```java
// 最小堆 (优先队列)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
// 最大堆
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> b-a);

// 复杂对象
PriorityQueue<状态> pq = new PriorityQueue<>(
    (s1, s2) -> s1.cost - s2.cost
);
```

**为什么PriorityQueue？**
- O(log n) 插入和删除
- 自动维护堆性质
- 比排序数组灵活 (动态更新)

**何时用TreeMap代替？**
- 需要 range query (getRange, subMap)
- 需要按值统计 (如计数)

---

### 场景4：路径恢复 (BFS, Dijkstra)
**需要的辅助结构：**
```java
Map<节点, 节点> parent = new HashMap<>();
// parent[当前] = 上一个

// 单链表形式恢复
List<节点> path = new ArrayList<>();
while (current != null) {
    path.add(current);
    current = parent.get(current);
}
Collections.reverse(path);
```

**为什么Map？**
- 节点可能不连续
- 支持任意类型的节点
- O(1) 查询前驱节点

**何时用int[]代替？**
- 节点 0-n-1 编号连续
- 内存紧张 (int[] vs HashMap)

---

### 场景5：状态压缩 (LC847, LC864)
**需要的辅助结构：**
```java
// 访问掩码
int mask = 0;
mask |= (1 << nodeId);  // 标记访问
boolean visited = (mask & (1 << nodeId)) != 0;

// 状态 + 掩码DP
Map<Integer, Integer> dp = new HashMap<>();
// dp[mask] = 访问mask中所有节点的最少步数
```

**为什么位掩码？**
- 节点数 n ≤ 20 时效率极高
- 2^n 状态压缩成单个int
- O(1) 位操作

**何时不用位掩码？**
- n > 20 (状态爆炸)
- 用Set<Integer> 代替

---

### 场景6：依赖追踪 (拓扑排序)
**需要的辅助结构：**
```java
Map<节点, List<节点>> graph = new HashMap<>();      // 邻接表
Map<节点, Integer> indegree = new HashMap<>();      // 入度
Queue<节点> queue = new LinkedList<>();             // 拓扑序

// 或用数组形式 (节点编号0-n-1)
int[] indegree = new int[n];
List<Integer>[] graph = new ArrayList[n];
```

**为什么这个组合？**
- 图 追踪所有依赖关系
- 入度 追踪当前约束
- 队列 维护可处理的节点

---

### 场景7：区间合并 (合并区间, 会议室)
**需要的辅助结构：**
```java
// 一般不需要额外结构!
// 只需: 排序 + 单指针遍历
Arrays.sort(intervals, (a, b) -> a.start - b.start);

// 仅需保存结果
List<区间> result = new ArrayList<>();
```

**为什么最少？**
- 排序后问题简化
- 顺序遍历足够
- 预先建模 = 少用数据结构

---

### 场景8：分组/聚合 (账户合并, 分组字母异位词)
**需要的辅助结构：**
```java
// 按值分组
Map<String, List<String>> groups = new HashMap<>();
groups.computeIfAbsent(key, k -> new ArrayList<>()).add(value);

// 如果同时需要查询归属
Map<String, String> parent = new HashMap<>();  // 并查集
```

**为什么Map<K, List<V>>？**
- 一对多映射
- 支持多个值同一键
- O(1) 添加和查询

---

## 第五步：性能评估矩阵

| 数据结构 | 查询 | 插入 | 删除 | 空间 | 适用场景 |
|---------|------|------|------|------|---------|
| HashSet | O(1) | O(1) | O(1) | O(n) | 去重、标记 |
| TreeSet | O(log n) | O(log n) | O(log n) | O(n) | 排序、范围 |
| PriorityQueue | O(1) | O(log n) | O(log n) | O(n) | Top K、最小/大值 |
| HashMap | O(1) | O(1) | O(1) | O(n) | 计数、分组 |
| TreeMap | O(log n) | O(log n) | O(log n) | O(n) | 排序键、范围 |
| int[] | O(1) | O(1) | O(1) | O(n) | 固定范围、极快 |
| boolean[] | O(1) | O(1) | O(1) | O(n) | 标记、访问 |
| 位掩码 | O(1) | O(1) | O(1) | O(1) | n ≤ 20 状态 |

⸻

## 第六步：反模式 - What NOT to Do

❌ **反模式1：过度设计**
```java
// 错误：为了100个元素用TreeSet
TreeSet<Integer> set = new TreeSet<>();  // O(log n) 过度了

// 正确：
HashSet<Integer> set = new HashSet<>();  // O(1) 足够
```

❌ **反模式2：忽视空间**
```java
// 错误：n=10^7时还用Map
Map<Integer, Integer> map = new HashMap<>();  // 内存爆炸

// 正确：
int[] count = new int[n];  // 或只保留必要数据
```

❌ **反模式3：选错数据类型**
```java
// 错误：字母频率用HashMap
Map<Character, Integer> freq = new HashMap<>();

// 正确：
int[] freq = new int[26];  // 或 int[] freq = new int[256]
```

❌ **反模式4：忘记初始化**
```java
// 错误：
map.get(key)++; // 空指针!

// 正确：
map.put(key, map.getOrDefault(key, 0) + 1);
// 或
map.merge(key, 1, Integer::sum);
```

⸻

## 实战案例 - 设计示例

### 案例1：LC560 子数组和等于 K
**问题分析:**
- 需要：快速查找 (currentSum - k) 是否存在过
- 需要：计数 (某个和出现几次)
- 不需要：排序

**设计决策:**
```java
// 选择: HashMap<Integer, Integer>
Map<Integer, Integer> sumCount = new HashMap<>();

核心逻辑:
int currentSum = 0;
for (int num : nums) {
    currentSum += num;
    // 查询 O(1)
    count += sumCount.getOrDefault(currentSum - k, 0);
    // 更新 O(1)
    sumCount.put(currentSum, sumCount.getOrDefault(currentSum, 0) + 1);
}
```

---

### 案例2：LC239 滑动窗口最大值
**问题分析:**
- 需要：快速获取最大值 O(1)
- 需要：维护窗口内元素有序性
- 不需要：完全排序

**设计决策:**
```java
// 选择: Deque<Integer> (单调递减)
Deque<Integer> deque = new ArrayDeque<>();

核心逻辑:
for (int i = 0; i < n; i++) {
    // 移除过期 O(1) amort
    while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
        deque.pollFirst();
    }
    // 移除较小值 O(1) amort
    while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
        deque.pollLast();
    }
    deque.offerLast(i);
    // 查询最大值 O(1)
    if (i >= k - 1) {
        result[i - k + 1] = nums[deque.peekFirst()];
    }
}
```

---

### 案例3：LC76 最小窗口子串
**问题分析:**
- 需要：追踪需求字符 (目标字符)
- 需要：追踪窗口字符 (当前字符)
- 需要：快速比较频率匹配

**设计决策:**
```java
// 选择: 两个HashMap + formed计数器
Map<Character, Integer> need = new HashMap<>();
Map<Character, Integer> window = new HashMap<>();
int formed = 0;  // 代替完全遍历比较

核心逻辑:
for (int right = 0; right < s.length(); right++) {
    char ch = s.charAt(right);
    window.put(ch, window.getOrDefault(ch, 0) + 1);
    
    // O(1) 检查单个字符是否匹配
    if (need.containsKey(ch) && 
        window.get(ch).intValue() == need.get(ch).intValue()) {
        formed++;
    }
    
    while (formed == need.size()) {
        // 找最短窗口
        ...
    }
}
```

---

### 案例4：LC864 获取所有钥匙的最短路径
**问题分析:**
- 状态多维：(row, col) + keyMask
- 需要：追踪访问过的状态 O(1)
- 需要：BFS队列

**设计决策:**
```java
// 选择: 3D boolean数组 + 队列
boolean[][][] visited = new boolean[rows][cols][64];
Queue<int[]> queue = new LinkedList<>();
// 或用Set<String> 追踪状态

核心逻辑:
while (!queue.isEmpty()) {
    int[] curr = queue.poll();
    int r = curr[0], c = curr[1], mask = curr[2];
    
    // O(1) 检查状态是否访问过
    if (visited[r][c][mask]) continue;
    visited[r][c][mask] = true;
    
    for (int[] dir : dirs) {
        int nr = r + dir[0];
        int nc = c + dir[1];
        
        // 计算新掩码 O(1)
        int newMask = mask;
        if (grid[nr][nc] >= 'a' && grid[nr][nc] <= 'f') {
            newMask |= (1 << (grid[nr][nc] - 'a'));
        }
        
        queue.offer(new int[]{nr, nc, newMask});
    }
}
```

⸻

## 总结：辅助数据结构设计的黄金法则

1. **明确需求第一** - 先问 "我需要做什么操作"
2. **选最小化结构** - 不过度设计，够用就好
3. **考虑边界情况** - n的大小、内存限制
4. **权衡时空** - 是否值得用空间换时间
5. **预先建模** - 好的建模能减少90%的数据结构复杂度
6. **测试常见场景** - 差一，空值, 空集合 等

⸻

---

## 英文版本 | ENGLISH VERSION

# 辅助数据结构设计 - 快速参考

| DS Type | Data Structure | Complexity | Use Case | Example |

这是面试中最关键的决策点。
选对辅助数据结构，直接影响代码复杂度和运行效率。

## Step 1: 识别问题的核心需求

先问自己：
**我在解这个问题中需要做什么？**

常见的核心操作：
```
1. Mark (标记) → boolean[], Set, visited
2. Count (计数) → Map<K, Int>, frequency
3. Search (查找) → Map, Set, Trie
4. Order (排序) → PriorityQueue, Sorted List
5. Connect (连接) → Union Find, Adjacency List
6. Track (跟踪) → Parent array, Stack, Queue
7. Recover (恢复) → Parent/Path array
8. Optimize (优化) → DP array, Memo Map
```

⸻

## Step 2: 按需求匹配 - Core Operation Matrix

| 核心操作 | 轻度需求 | 中度需求 | 高度需求 |
|---------|---------|---------|---------|
| Mark访问 | boolean[] | Set | Bitmask |
| Count频率 | HashMap | HashMap | HashMap (预计算) |
| Search查找 | HashMap | HashSet | Trie |
| Order排序 | PriorityQueue | TreeMap | Segment Tree |
| Connect连接 | List | Adjacency List | Union Find |
| Track路径 | Parent[] | Stack/Queue | Path + Cost |
| Recover恢复 | Parent[] | Parent[] + Pointer | Backtracking List |
| Optimize缓存 | HashMap | Array | 2D Array |

⸻

## Step 3: 决策树 - Decision Tree

**Question 1: 需要查询吗？**
```
YES → Hash Table (HashMap/HashSet)
NO  → Linear Storage (Array/List)
```

**Question 2: 查询频繁吗？(超过100次)**
```
YES → Hash Table 或 Tree/Trie (看具体场景)
NO  → Linear Search 足够
```

**Question 3: 需要排序吗？**
```
YES → PriorityQueue 或 TreeMap
NO  → Unsorted 就够
```

**Question 4: 有多维信息吗？(如count+index)**
```
YES → 考虑 Map<K, CustomObject> 或分离数据
NO  → 单Map足够
```

**Question 5: 空间换时间值得吗？**
```
YES (n < 10^6) → 预计算 + 额外存储
NO  (n > 10^7) → 按需计算，最小存储
```

⸻

## Step 4: 常见场景速查表

### 场景1: BFS/DFS 图遍历
**需要的辅助结构：**
```java
Set<Node> visited = new HashSet<>();    // 防止重复访问
Queue<Node> queue = new LinkedList<>(); // BFS队列
// 或
Stack<Node> stack = new Stack<>();      // DFS栈
```
**为什么选Set？**
- O(1) 查询和添加
- 自动去重
- 空间效率 (vs boolean array)

**何时用boolean[]代替？**
- 节点编号 0 到 n-1 且 n < 10^6
- 需要极快访问和写入

---

### 场景2: 频率统计 (Sliding Window, Top K)
**需要的辅助结构：**
```java
Map<Integer, Integer> freq = new HashMap<>();
// 统计每个数字出现次数

// 进阶: 同时需要最大频率
int maxFreq = 0;
Map<Integer, Integer> freq = new HashMap<>();
```

**为什么HashMap？**
- O(1) 平均 getOrDefault/put
- 动态大小 (不用预先知道范围)
- 键可以是任意类型 (Integer, String, Object)

**何时用int[]代替？**
- 数字范围 [0, 26] (如字母)
- 需要极快速度且空间充足

---

### 场景3: 最大/最小值查询 (Dijkstra, Top K)
**需要的辅助结构：**
```java
// 最小堆 (优先队列)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
// 最大堆
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> b-a);

// 复杂对象
PriorityQueue<State> pq = new PriorityQueue<>(
    (s1, s2) -> s1.cost - s2.cost
);
```

**为什么PriorityQueue？**
- O(log n) 插入和删除
- 自动维护堆性质
- 比排序数组灵活 (动态更新)

**何时用TreeMap代替？**
- 需要 range query (getRange, subMap)
- 需要按值统计 (如计数)

---

### 场景4: 路径恢复 (BFS, Dijkstra)
**需要的辅助结构：**
```java
Map<Node, Node> parent = new HashMap<>();
// parent[current] = previous

// 单链表形式恢复
List<Node> path = new ArrayList<>();
while (current != null) {
    path.add(current);
    current = parent.get(current);
}
Collections.reverse(path);
```

**为什么Map？**
- 节点可能不连续
- 支持任意类型的节点
- O(1) 查询前驱节点

**何时用int[]代替？**
- 节点 0-n-1 编号连续
- 内存紧张 (int[] vs HashMap)

---

### 场景5: 状态压缩 (LC847, LC864)
**需要的辅助结构：**
```java
// 访问掩码
int mask = 0;
mask |= (1 << nodeId);  // 标记访问
boolean visited = (mask & (1 << nodeId)) != 0;

// 状态 + 掩码DP
Map<Integer, Integer> dp = new HashMap<>();
// dp[mask] = min steps to visit all nodes in mask
```

**为什么Bitmask？**
- 节点数 n ≤ 20 时效率极高
- 2^n 状态压缩成单个int
- O(1) 位操作

**何时不用Bitmask？**
- n > 20 (状态爆炸)
- 用Set<Integer> 代替

---

### 场景6: 依赖追踪 (Topological Sort)
**需要的辅助结构：**
```java
Map<Node, List<Node>> graph = new HashMap<>();      // 邻接表
Map<Node, Integer> indegree = new HashMap<>();      // 入度
Queue<Node> queue = new LinkedList<>();             // 拓扑序

// 或用数组形式 (节点编号0-n-1)
int[] indegree = new int[n];
List<Integer>[] graph = new ArrayList[n];
```

**为什么这个组合？**
- Graph 追踪所有依赖关系
- Indegree 追踪当前约束
- Queue 维护可处理的节点

---

### 场景7: 区间合并 (Merge Intervals, Meeting Rooms)
**需要的辅助结构：**
```java
// 一般不需要额外结构!
// 只需: 排序 + 单指针遍历
Arrays.sort(intervals, (a, b) -> a.start - b.start);

// 仅需保存结果
List<Interval> result = new ArrayList<>();
```

**为什么最少？**
- 排序后问题简化
- 顺序遍历足够
- 预先建模 = 少用数据结构

---

### 场景8: 分组/聚合 (Accounts Merge, Group Anagrams)
**需要的辅助结构：**
```java
// 按值分组
Map<String, List<String>> groups = new HashMap<>();
groups.computeIfAbsent(key, k -> new ArrayList<>()).add(value);

// 如果同时需要查询归属
Map<String, String> parent = new HashMap<>();  // Union Find
```

**为什么Map<K, List<V>>？**
- 一对多映射
- 支持多个值同一键
- O(1) 添加和查询

---

## Step 5: 性能评估矩阵

| 数据结构 | 查询 | 插入 | 删除 | 空间 | 适用场景 |
|---------|------|------|------|------|---------|
| HashSet | O(1) | O(1) | O(1) | O(n) | 去重、标记 |
| TreeSet | O(log n) | O(log n) | O(log n) | O(n) | 排序、Range |
| PriorityQueue | O(1) | O(log n) | O(log n) | O(n) | Top K、最小/大值 |
| HashMap | O(1) | O(1) | O(1) | O(n) | 计数、分组 |
| TreeMap | O(log n) | O(log n) | O(log n) | O(n) | 排序Key、Range |
| int[] | O(1) | O(1) | O(1) | O(n) | 固定范围、极快 |
| boolean[] | O(1) | O(1) | O(1) | O(n) | 标记、访问 |
| Bitmask | O(1) | O(1) | O(1) | O(1) | n ≤ 20 状态 |

⸻

## Step 6: 反模式 - What NOT to Do

❌ **反模式1: 过度设计**
```java
// 错误：为了100个元素用TreeSet
TreeSet<Integer> set = new TreeSet<>();  // O(log n) 过度了

// 正确：
HashSet<Integer> set = new HashSet<>();  // O(1) 足够
```

❌ **反模式2: 忽视空间**
```java
// 错误：n=10^7时还用Map
Map<Integer, Integer> map = new HashMap<>();  // 内存爆炸

// 正确：
int[] count = new int[n];  // 或只保留必要数据
```

❌ **反模式3: 选错数据类型**
```java
// 错误：字母频率用HashMap
Map<Character, Integer> freq = new HashMap<>();

// 正确：
int[] freq = new int[26];  // 或 int[] freq = new int[256]
```

❌ **反模式4: 忘记初始化**
```java
// 错误：
map.get(key)++; // NPE!

// 正确：
map.put(key, map.getOrDefault(key, 0) + 1);
// 或
map.merge(key, 1, Integer::sum);
```

⸻

## 实战案例 - Design Examples

### 案例1: LC560 Subarray Sum Equals K
**问题分析:**
- 需要：快速查找 (currentSum - k) 是否存在过
- 需要：计数 (某个和出现几次)
- 不需要：排序

**设计决策:**
```java
// 选择: HashMap<Integer, Integer>
Map<Integer, Integer> sumCount = new HashMap<>();

核心逻辑:
int currentSum = 0;
for (int num : nums) {
    currentSum += num;
    // 查询 O(1)
    count += sumCount.getOrDefault(currentSum - k, 0);
    // 更新 O(1)
    sumCount.put(currentSum, sumCount.getOrDefault(currentSum, 0) + 1);
}
```

---

### 案例2: LC239 Sliding Window Maximum
**问题分析:**
- 需要：快速获取最大值 O(1)
- 需要：维护窗口内元素有序性
- 不需要：完全排序

**设计决策:**
```java
// 选择: Deque<Integer> (monotonic decreasing)
Deque<Integer> deque = new ArrayDeque<>();

核心逻辑:
for (int i = 0; i < n; i++) {
    // 移除过期 O(1) amort
    while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
        deque.pollFirst();
    }
    // 移除较小值 O(1) amort
    while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
        deque.pollLast();
    }
    deque.offerLast(i);
    // 查询最大值 O(1)
    if (i >= k - 1) {
        result[i - k + 1] = nums[deque.peekFirst()];
    }
}
```

---

### 案例3: LC76 Minimum Window Substring
**问题分析:**
- 需要：追踪需求字符 (target chars)
- 需要：追踪窗口字符 (current chars)
- 需要：快速比较频率匹配

**设计决策:**
```java
// 选择: 两个HashMap + formed计数器
Map<Character, Integer> need = new HashMap<>();
Map<Character, Integer> window = new HashMap<>();
int formed = 0;  // 代替完全遍历比较

核心逻辑:
for (int right = 0; right < s.length(); right++) {
    char ch = s.charAt(right);
    window.put(ch, window.getOrDefault(ch, 0) + 1);
    
    // O(1) 检查单个字符是否匹配
    if (need.containsKey(ch) && 
        window.get(ch).intValue() == need.get(ch).intValue()) {
        formed++;
    }
    
    while (formed == need.size()) {
        // 找最短窗口
        ...
    }
}
```

---

### 案例4: LC864 Shortest Path to Get All Keys
**问题分析:**
- 状态多维：(row, col) + keyMask
- 需要：追踪访问过的状态 O(1)
- 需要：BFS队列

**设计决策:**
```java
// 选择: 3D boolean数组 + Queue
boolean[][][] visited = new boolean[rows][cols][64];
Queue<int[]> queue = new LinkedList<>();
// 或用Set<String> 追踪状态

核心逻辑:
while (!queue.isEmpty()) {
    int[] curr = queue.poll();
    int r = curr[0], c = curr[1], mask = curr[2];
    
    // O(1) 检查状态是否访问过
    if (visited[r][c][mask]) continue;
    visited[r][c][mask] = true;
    
    for (int[] dir : dirs) {
        int nr = r + dir[0];
        int nc = c + dir[1];
        
        // 计算新掩码 O(1)
        int newMask = mask;
        if (grid[nr][nc] >= 'a' && grid[nr][nc] <= 'f') {
            newMask |= (1 << (grid[nr][nc] - 'a'));
        }
        
        queue.offer(new int[]{nr, nc, newMask});
    }
}
```

⸻

## 总结: 辅助数据结构设计的黄金法则

1. **明确需求第一** - 先问 "我需要做什么操作"
2. **选最小化结构** - 不过度设计，够用就好
3. **考虑边界情况** - n的大小、内存限制
4. **权衡时空** - 是否值得用空间换时间
5. **预先建模** - 好的建模能减少90%的数据结构复杂度
6. **测试常见场景** - Off-by-one, null, empty 等

⸻

# Modeling

Modeling

这是个非常好的问题。
如果说：
DFS/BFS/DP 是战术
那么：
Modeling 是战略
Google L5/L6，尤其是 Hard 题，本质上很多时候不是考算法，而是考：
你能否把现实问题抽象成一个程序可以求解的模型。
我自己会把建模(Modeling)分成 10大类模型，基本覆盖95%的面试题。

⸻

1. Graph Modeling
最常见。
把问题抽象成：
Node
Edge
例如：
Course Schedule
Course
↓
Node

Prerequisite
↓
Directed Edge

⸻

Word Ladder
Word
↓
Node

One-char change
↓
Edge

⸻

Accounts Merge
Email
↓
Node

Same Account
↓
Edge

⸻

识别词：
relationship
dependency
connection
route
network

⸻

2. State Space Modeling
Google最喜欢。
不是图本身。
而是：
状态
↓
Node

状态变化
↓
Edge
例如：
Race Car
(position,speed)

⸻

Open Lock
combination

⸻

LC1293
(row,col,k)

⸻

LC864
(row,col,keyMask)

⸻

本质：
状态图(State Graph)

⸻

3. Grid Modeling
二维空间。
例如：
Number of Islands
Rotting Oranges
Word Search

⸻

建模：
Cell
↓
Node

上下左右
↓
Edge

⸻

很多Grid题实际上是Graph题。

⸻

4. Interval Modeling
区间问题。
例如：
Meeting Rooms
Merge Intervals
Insert Interval

⸻

状态：
[start,end]

⸻

关键技巧：
排序
后再处理。

⸻

5. Resource Consumption Modeling
非常Google。
例如：
剩余油量
剩余K次机会
剩余预算
剩余时间

⸻

LC1293
(row,col,k)

⸻

Cheapest Flights
(city,stops)

⸻

资源本身进入State。

⸻

6. Collection Modeling
收集类问题。
例如：
Collect Keys
Visit Cities
Visit Nodes

⸻

状态：
visitedMask

⸻

LC864
keys

⸻

LC847
visited nodes

⸻

TSP
visited cities

⸻

关键词：
all
every
collect
visit all

⸻

7. Dependency Modeling
依赖关系。
例如：
Course Schedule
Build System
Task Workflow

⸻

建模：
Directed Graph

⸻

辅助：
Indegree

⸻

Topological Sort。

⸻

8. Optimization Modeling
这类很多人忽略。
问题其实是：
如何定义目标函数
例如：
Koko
speed

⸻

Ship Capacity
capacity

⸻

目标：
minimum feasible value

⸻

于是：
Binary Search on Answer

⸻

9. Connectivity Modeling
例如：
Islands
Provinces
Accounts Merge

⸻

问题核心：
哪些东西属于同一个Group

⸻

解法：
DFS
BFS
Union Find

⸻

10. Transformation Modeling
Google特别喜欢。
例如：
Word Ladder
hit
↓
hot
↓
dot
↓
dog

⸻

问题变成：
状态A
如何变成
状态B

⸻

本质：
Shortest Transformation Path

⸻

更高级的一层
其实所有Hard题最终都在回答两个问题：
Question 1
什么东西是状态？
例如：
Race Car
position?
speed?

⸻

LC864
position?
keys?

⸻

LC1293
position?
remaining k?

⸻

Question 2
什么东西影响未来？
这是Google最爱问的。
例如：
LC1293
(row,col)
够吗？
不够。

⸻

因为：
remaining k
影响未来。

⸻

所以：
(row,col,k)
才是状态。

⸻

Google Hard 建模框架
以后看到新题。
先不要想：
DFS?
BFS?
DP?
先问：
Step 1
现实问题是什么？

⸻

Step 2
我应该把什么抽象成Node？

⸻

Step 3
状态是什么？
未来决策需要知道什么？

⸻

Step 4
状态如何变化？
Transition

⸻

Step 5
状态空间大小？
10^5?
10^6?
2^n?

⸻

Step 6
再决定：
DFS
BFS
DP
Dijkstra
Union Find
Topological Sort

⸻

你最近刷的这些题：
Race Car
LC1293
LC864
LC847
Cheapest Flights
其实已经开始进入 Google Hard 的核心能力了：
不是写算法

而是：

Modeling
→ State Design
→ Transition Design
→ Choose Solver
真正的高手看到题目，脑子里第一反应已经不是 BFS，而是：
"这个问题的状态到底是什么？什么信息会影响未来决策？"
这一步做好了，后面的 BFS/DFS 往往只是体力活。


# State Design

State Design
↓
State Compression
↓
State Transition

你如何建模(Modeling)
你如何组织状态(State)
你如何构建辅助数据结构(Data Structure)
你如何设计Transition

⸻

先说结论
如果我们把 Google / Meta / Uber / Airbnb 常见 DSA Pattern 全部展开。
其实大概可以分成：
Level 1：核心 Pattern（你已经覆盖）
1. BFS
2. DFS
3. Backtracking
4. Binary Search
5. Heap
6. Sliding Window
7. Prefix Sum
8. Union Find
9. Topological Sort
10. Dijkstra

⸻

Level 2：高级 Pattern（刚才补充）
11. Monotonic Stack
12. Monotonic Queue
13. Trie
14. Two Pointer
15. Fast Slow Pointer
16. Greedy
17. Sweep Line
18. Segment Tree
19. Fenwick Tree
20. Simulation

⸻

但真正容易被 Google 用来出 Hard 的
不是新 Pattern。
而是：
State 的升级
例如：

⸻

State Evolution 1
普通 BFS
(node)
↓

⸻

资源限制
(node, remainingK)
↓

⸻

库存系统
(node, keyMask)
↓

⸻

动态系统
(position, speed)
↓

⸻

访问状态
(node, visitedMask)

⸻

这其实是同一个 BFS。

⸻

State Evolution 2
普通 DFS
(node)
↓
路径
(node, path)
↓
权重
(node, currentSum)
↓
缓存
(node, memo)
↓
状态机
(day, holdingStock)

⸻

State Evolution 3
普通 DP
dp[i]
↓
二维
dp[i][j]
↓
区间
dp[left][right]
↓
状态压缩
dp[mask]
↓
图DP
dp[node][mask]

⸻

Google Hard 很喜欢最后两种。

⸻

下面这些是你目前还没系统整理过的
我觉得值得知道。

⸻

Pattern 21
State Compression
超高频 Hard

⸻

State
mask

⸻

例如：
LC847
Shortest Path Visiting All Nodes

⸻

State
(node, mask)

⸻

mask
00101
表示：
访问过节点0和2

⸻

Pattern 22
Memoization DFS
Google 很喜欢。

⸻

普通 DFS
dfs(node)

⸻

升级
dfs(node)
+
cache

⸻

代表题：
Longest Increasing Path

⸻

State
(row, col)
缓存：
memo[row][col]

⸻

Pattern 23
Interval DP
很多人害怕。

⸻

State
dp[left][right]

⸻

代表：
Burst Balloons

⸻

Transition
枚举最后戳哪个

⸻

Pattern 24
State Machine DP
非常 Google。

⸻

Stock 系列

⸻

State
holding
sold
rest

⸻

Transition
buy
sell
skip

⸻

这其实像系统设计。

⸻

Pattern 25
Multi-source BFS

⸻

普通
queue.add(start)

⸻

升级
queue.add(allStarts)

⸻

例如：
Rotting Oranges

⸻

State
(row,col)

⸻

但起点多个。

⸻

Pattern 26
Bidirectional BFS
Google 很喜欢。

⸻

State
beginSet
endSet

⸻

Word Ladder 优化版。

⸻

复杂度下降非常明显。

⸻

Pattern 27
Meet in the Middle
非常高级。

⸻

State
leftHalf
rightHalf

⸻

典型：
40个元素。

⸻

暴力：
2^40

⸻

拆成：
2^20
+
2^20

⸻

Pattern 28
K-way Merge

⸻

State
(value, listId, index)

⸻

Heap

⸻

代表：
Merge K Sorted Lists

⸻

Pattern 29
Difference Array
这个很多人漏掉。

⸻

Prefix Sum 的反向。

⸻

State
diff[]

⸻

Transition
diff[l]+=v
diff[r+1]-=v

⸻

区间更新神器。

⸻

Pattern 30
Event Simulation
Google偶尔喜欢。

⸻

State
currentTime
eventQueue

⸻

代表：
Task Scheduler
CPU Simulation
Meeting Room

⸻

最后一个层级
真正的 Google Hard
其实会出现：
Hybrid Pattern
例如：
LC864
State
(row,col,keyMask)
Pattern
BFS
+
Bitmask

⸻

LC847
(node, visitedMask)
Pattern
BFS
+
Bitmask

⸻

Cheapest Flights
(city, stops)
Pattern
Dijkstra
+
Resource Constraint

⸻

Burst Balloons
dp[left][right]
Pattern
Interval DP

⸻

Race Car
(position, speed)
Pattern
BFS
+
Dynamic State

⸻

所以如果从 Google L6 的角度。
我不会建议你去背 30 个 Pattern。
我会建议你建立一张：
State Taxonomy（状态分类图）
Position State
(row,col)
(node)

⸻

Resource State
(node,k)

⸻

Inventory State
(node,keyMask)

⸻

Dynamic State
(position,speed)

⸻

Progress State
(index,path)

⸻

Relationship State
(parent)
(indegree)

⸻

Optimization State
(distance,node)
(priority,node)

⸻

Compression State
(mask)

⸻

Time State
(currentTime,event)

⸻

Decision State
(holding,sold,rest)

⸻

如果你能把这 10 类 State 看懂，实际上已经覆盖了绝大部分 Google DSA 面试中真正有区分度的题目。很多 Hard 题看起来陌生，但本质上只是把这些 State 重新组合了一次。


# State Transition

state transition：状态如何变化。
你说得很对：
state = 当前状态/数据结构
state transition = 程序真正怎么推进
也就是算法里的"控制语句"。
核心技巧可以分成这几类：
1. 条件转移
最常见：
if (condition) {
    move to next state;
}
例子：
BFS:
if next state is valid and not visited
    push into queue
关键词：
valid
visited
boundary
constraint
2. 选择 / 不选择
Backtracking 经典模式：
choose
explore
unchoose
例如 Subset / Combination：
path.add(nums[i]);
dfs(i + 1);
path.remove(path.size() - 1);
状态变化是：
path 变长
index 前进
remain 变小
3. 消耗资源型转移
比如 LC1293：
(row, col, remainingK)
遇到障碍：
nextK = k - grid[nr][nc];
这类题关键是：
状态不只是位置，还包括剩余资源
类似：
k stops
remaining obstacles
keys collected
fuel
health
budget
4. Bitmask 转移
用于：
visited all
collect all keys
state compression
例如：
nextMask = mask | (1 << next);
代表：
把 next 这个节点标记为 visited
LC847 / LC864 都是这个模式。
5. 方向移动转移
Grid BFS/DFS：
int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
状态从：
(row, col)
变成：
(row + dr, col + dc)
适合：
Number of Islands
Word Search
Rotting Oranges
Shortest Path Grid
6. 图邻居转移
Graph：
for (int nei : graph.get(node)) {
    ...
}
状态从：
node
变成：
neighbor
适合：
Connected Components
Clone Graph
Course Schedule
Dijkstra
7. 最短路代价转移
Dijkstra：
newCost = cost + weight;
if (newCost < dist[next]) {
    dist[next] = newCost;
}
状态变化：
(node, cost)
重点是：
cost 变小才更新
8. DP 状态转移
DP 其实就是"状态转移公式"。
例如：
dp[i] = dp[i-1] + dp[i-2]
或者：
dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]
DP 的核心就是：
当前状态由哪些旧状态推出来？
9. 计数 / 层级转移
BFS level：
one level = one step / one minute / one operation
例如 Rotting Oranges：
每一层 BFS = 1 minute
Open Lock：
每一层 BFS = 1 turn
10. 终止条件转移
任何 state transition 都要有 stop condition：
if (state == target) return answer;
例子：
mask == targetMask
row == m-1 && col == n-1
path.size() == nums.length
remain == 0

⸻

最重要的总结：
State = 我现在在哪里 / 我已经有什么 / 我还剩什么

Transition = 我下一步可以怎么走 / 走了之后状态怎么变
面试时你可以这样讲：
I'll define the state first, then define valid transitions from one state to another. Once the state and transitions are clear, the algorithm becomes much easier to implement.
这个就是你说的"真正的程序控制语句"。


# Auxiliary Data Structure Design

Auxiliary Data Structure Design

对，我们前面其实已经聊了：
Modeling
    ↓
State Design
    ↓
Transition Design
下一层就是：
Auxiliary Data Structure Design
（辅助数据结构设计）
这个实际上是很多 Google Medium/Hard 的关键。
很多题：
State 想对了
50%

辅助结构选对
30%

DFS/BFS代码
20%

⸻

辅助数据结构到底是什么？
本质：
帮助管理状态
帮助加速查询
帮助避免重复计算
帮助组织结果
例如：
LC864
State:
(row,col,keyMask)
辅助结构：
visited[row][col][mask]

⸻

没有这个结构：
无限循环
TLE

⸻

## 第一类：Visited Family（标记已访问状态）

最常见的辅助结构

### 1️⃣ Boolean Visited Array
最简单的访问标记

```java
boolean[] visited;

// 初始化
visited = new boolean[n];

// 标记已访问
visited[node] = true;

// 检查是否访问过
if (!visited[node]) {
    // 未访问，可以处理
}
```

使用场景：
* Connected Components（连通分量）
* Valid Tree（验证树）
* Simple DFS/BFS

⸻

### 2️⃣ Matrix Visited
处理二维网格问题

```java
boolean[][] visited;

// 初始化
visited = new boolean[rows][cols];

// 标记已访问
visited[r][c] = true;

// 检查是否访问过
if (!visited[nr][nc]) {
    visited[nr][nc] = true;
    queue.add(new int[]{nr, nc});
}
```

使用场景：
* Number of Islands（岛屿数量）
* Word Search（单词搜索）
* Rotting Oranges（腐烂的橙子）

⸻

### 3️⃣ State Visited（多维状态）
处理复杂的状态空间

```java
// 3D visited数组
boolean[][][] visited;
visited = new boolean[rows][cols][k+1];

// 标记状态
visited[nr][nc][remainK] = true;

// 检查是否访问过
if (!visited[nr][nc][nextK]) {
    visited[nr][nc][nextK] = true;
    queue.add(new int[]{nr, nc, nextK});
}
```

使用场景：
* LC1293：带障碍消除限制的最短路径
* LC864：收集钥匙的最短路径

⸻

### 4️⃣ Generic State（泛化状态）
当状态无法用数组表示时

```java
Set<String> visited = new HashSet<>();

// 标记状态
String state = position + "," + speed;
visited.add(state);

// 检查是否访问过
if (!visited.contains(state)) {
    visited.add(state);
}
```

使用场景：
* Race Car（动态速度状态）
* 复杂的状态组合

⸻

**Google经常问：**
"Why isn't visited[node] enough?"
* 因为状态可能包含多个维度
* 单个node访问过≠该状态访问过
* 需要考虑所有影响结果的因素

## 第二类：Graph Construction（图的构建）

最常见的预处理步骤

### 无向/有向图（Unweighted Graph）

```java
// 方式1：使用Map + List
Map<Integer, List<Integer>> graph = new HashMap<>();

// 方式2：使用数组
List<Integer>[] graph = new ArrayList[n];

// 构建图
for (int[] edge : edges) {
    int u = edge[0], v = edge[1];
    graph.get(u).add(v);
    // graph.get(v).add(u);  // 无向图才加这行
}

// 遍历邻居
for (int neighbor : graph.get(node)) {
    if (!visited[neighbor]) {
        visited[neighbor] = true;
        queue.add(neighbor);
    }
}
```

使用场景：
* Course Schedule（课程安排）
* Clone Graph（克隆图）
* Connected Components（连通分量）
* Valid Tree（验证树）

⸻

### 加权图（Weighted Graph）

```java
// 存储：邻接节点 + 权重
Map<Integer, List<int[]>> graph = new HashMap<>();

// 构建图
for (int[] flight : flights) {
    int from = flight[0], to = flight[1], cost = flight[2];
    graph.get(from).add(new int[]{to, cost});
}

// Dijkstra中的使用
for (int[] next : graph.get(node)) {
    int neighbor = next[0];
    int weight = next[1];
    int newDist = distance + weight;
    
    if (newDist < dist[neighbor]) {
        dist[neighbor] = newDist;
        pq.offer(new int[]{neighbor, newDist});
    }
}
```

使用场景：
* Dijkstra（最短路径）
* Cheapest Flights（最便宜航班）
* Network Delay Time（网络延迟）

## 第三类：Frequency Table（频率计数）

超高频的辅助结构

```java
// 初始化
Map<Character, Integer> freq = new HashMap<>();
// 或
Map<Integer, Integer> freq = new HashMap<>();

// 更新频率（标准技巧）
freq.put(key, freq.getOrDefault(key, 0) + 1);

// 检查频率
if (freq.getOrDefault(key, 0) > 0) {
    // 存在
}

// 减少频率
freq.put(key, freq.get(key) - 1);
if (freq.get(key) == 0) {
    freq.remove(key);
}
```

使用场景及例子：

| 问题 | 代码 | 说明 |
|------|------|------|
| Top K Frequent | `freq.put(num, freq.getOrDefault(num, 0) + 1)` | 统计所有数字频率 |
| Anagram | `freq.get(c) == freq.getOrDefault(c, 0)` | 比较字符频率 |
| Sliding Window | `freq.put(c, freq.getOrDefault(c, 0) + 1)` | 维护窗口内字符频率 |
| Substring | `if (freq.getOrDefault(c, 0) > 0)` | 检查字符存在 |

⸻

**Sliding Window中的应用**

```java
// LC3: Longest Substring Without Repeating
Map<Character, Integer> charCount = new HashMap<>();
int left = 0;

for (int right = 0; right < s.length(); right++) {
    char c = s.charAt(right);
    charCount.put(c, charCount.getOrDefault(c, 0) + 1);
    
    // 发现重复，收缩窗口
    while (charCount.get(c) > 1) {
        char leftChar = s.charAt(left);
        charCount.put(leftChar, charCount.get(leftChar) - 1);
        left++;
    }
    
    maxLen = Math.max(maxLen, right - left + 1);
}
```

## 第四类：Indegree Table（入度表）

Topological Sort（拓扑排序）专属

```java
// 初始化
int[] indegree = new int[numCourses];
List<List<Integer>> graph = new ArrayList<>();

// 构建图和入度表
for (int[] pre : prerequisites) {
    int course = pre[0], prereq = pre[1];
    graph.get(prereq).add(course);
    indegree[course]++;  // 增加入度
}

// Topological Sort（BFS）
Queue<Integer> queue = new LinkedList<>();

// 1. 找所有入度为0的节点
for (int i = 0; i < numCourses; i++) {
    if (indegree[i] == 0) {
        queue.offer(i);
    }
}

// 2. BFS处理
int count = 0;
while (!queue.isEmpty()) {
    int curr = queue.poll();
    count++;
    
    // 处理所有邻接节点
    for (int next : graph.get(curr)) {
        indegree[next]--;  // 减少入度
        if (indegree[next] == 0) {
            queue.offer(next);
        }
    }
}

// 3. 检查是否有环
return count == numCourses;
```

使用场景：
* Course Schedule（课程安排）
* Alien Dictionary（外星人字典）
* Build Order（构建顺序）

⸻

## 第五类：Distance Table（距离表）

最短路径算法专属

```java
// 初始化
int[] dist = new int[n];
final int INF = Integer.MAX_VALUE;
Arrays.fill(dist, INF);
dist[source] = 0;

// Dijkstra核心更新
for (int[] next : graph.get(node)) {
    int neighbor = next[0];
    int weight = next[1];
    int newDist = dist[node] + weight;
    
    // 只有更优的距离才更新
    if (newDist < dist[neighbor]) {
        dist[neighbor] = newDist;
        pq.offer(new int[]{neighbor, newDist});
    }
}

// 最后返回距离
return dist[target] == INF ? -1 : dist[target];
```

使用场景：
* Dijkstra（最短路径）
* Bellman Ford（最短路径）
* Network Delay Time（网络延迟）
* Swim in Rising Water（在上升的水中游泳）

**关键点：**
* 始终初始化为无穷大
* 只有更小的距离才更新
* 用优先队列加速处理

## 第六类：Parent Table（父节点表）

用于路径恢复和回溯

```java
// 初始化
int[] parent = new int[n];
Arrays.fill(parent, -1);

// BFS/DFS中记录父节点
for (int neighbor : graph.get(node)) {
    if (visited[neighbor]) continue;
    
    visited[neighbor] = true;
    parent[neighbor] = node;  // 记录parent
    queue.add(neighbor);
}

// 最后反推路径
List<Integer> path = new ArrayList<>();
int curr = target;
while (curr != -1) {
    path.add(0, curr);  // 加到前面
    curr = parent[curr];
}
```

完整例子：A → B → C → D

```
记录过程：
parent[B] = A;
parent[C] = B;
parent[D] = C;

反推路径：
curr = D -> path.add(D)
curr = C -> path.add(C)
curr = B -> path.add(B)
curr = A -> path.add(A)
result: [A, B, C, D]
```

使用场景：
* 最短路径恢复
* BFS/DFS路径重建
* Follow-up: "Can you return the actual path?"

**Google Follow-up常问：**
* Can you reconstruct the path?
* Print the actual steps taken
* Find any valid path (not just existence)

## 第七类：Priority Queue / Heap（优先队列）

很多人只会用Heap，不会设计元素

### Dijkstra：(node, cost)

```java
// Dijkstra中的Priority Queue
PriorityQueue<int[]> pq = new PriorityQueue<>(
    (a, b) -> a[1] - b[1]  // 按cost升序
);

pq.offer(new int[]{source, 0});

while (!pq.isEmpty()) {
    int[] curr = pq.poll();
    int node = curr[0];
    int cost = curr[1];
    
    if (cost > dist[node]) continue;  // 已处理过
    
    for (int[] next : graph.get(node)) {
        int neighbor = next[0];
        int weight = next[1];
        int newCost = cost + weight;
        
        if (newCost < dist[neighbor]) {
            dist[neighbor] = newCost;
            pq.offer(new int[]{neighbor, newCost});
        }
    }
}
```

⸻

### Cheapest Flights：(city, cost, stops)

```java
// 需要追踪额外的信息
PriorityQueue<int[]> pq = new PriorityQueue<>(
    (a, b) -> a[1] - b[1]  // 按cost升序
);

pq.offer(new int[]{src, 0, 0});  // city, cost, stops

while (!pq.isEmpty()) {
    int[] curr = pq.poll();
    int city = curr[0];
    int cost = curr[1];
    int stops = curr[2];
    
    if (city == dst) return cost;
    if (stops > k) continue;  // 超过K站，剪枝
    
    for (int[] next : graph.get(city)) {
        pq.offer(new int[]{next[0], cost + next[1], stops + 1});
    }
}
```

⸻

### Top K：(frequency, value)

```java
// 维护最小堆，大小为K
PriorityQueue<Integer> minHeap = new PriorityQueue<>(
    (a, b) -> freq.get(a) - freq.get(b)
);

for (int num : freq.keySet()) {
    minHeap.offer(num);
    if (minHeap.size() > k) {
        minHeap.poll();  // 删除最小的
    }
}

// 结果中的元素就是Top K
int[] result = new int[k];
for (int i = k - 1; i >= 0; i--) {
    result[i] = minHeap.poll();
}
```

⸻

**关键点：**
设计PriorityQueue时要考虑：
* 排序标准是什么？(comparator)
* 需要存什么信息？(数组/对象大小)
* 什么时候更新？(新状态出现时)

## 第八类：Monotonic Queue（单调队列）

滑窗高级题的利器

```java
// Sliding Window Maximum
Deque<Integer> deque = new LinkedList<>();

for (int i = 0; i < nums.length; i++) {
    // 1. 移除窗口外的元素
    while (!deque.isEmpty() && deque.peek() < i - k + 1) {
        deque.poll();
    }
    
    // 2. 维护单调性（递减）
    while (!deque.isEmpty() && 
           nums[deque.peekLast()] <= nums[i]) {
        deque.pollLast();
    }
    
    // 3. 添加当前元素
    deque.add(i);
    
    // 4. 记录答案
    if (i >= k - 1) {
        result[i - k + 1] = nums[deque.peek()];
    }
}
```

维护单调队列的目的：
* 递增：用于找区间最小值
* 递减：用于找区间最大值

使用场景：
* Sliding Window Maximum（滑窗最大值）
* Trapping Rain Water（接雨水）
* 其他滑窗优化问题

⸻

## 第九类：Prefix Sum（前缀和）

神级技巧，Google超高频

### 一维Prefix Sum

```java
// 初始化
int[] prefix = new int[nums.length + 1];
for (int i = 0; i < nums.length; i++) {
    prefix[i + 1] = prefix[i] + nums[i];
}

// 查询区间和：O(1)
int rangeSum = prefix[right + 1] - prefix[left];
```

⸻

### Prefix Sum + HashMap（超高频）

```java
// LC560: Subarray Sum Equals K
int sum = 0;
int count = 0;
Map<Integer, Integer> prefixCount = new HashMap<>();
prefixCount.put(0, 1);  // 初始化

for (int num : nums) {
    sum += num;
    
    // 找前面是否存在 (sum - k) 的前缀和
    int target = sum - k;
    count += prefixCount.getOrDefault(target, 0);
    
    // 记录当前前缀和出现次数
    prefixCount.put(sum, prefixCount.getOrDefault(sum, 0) + 1);
}

return count;
```

**核心思想：**
当前和 = S，目标和 = K
需要找前面的 (S - K) 前缀和
如果存在，说明中间区间和 = K

⸻

### Modulo Prefix Sum

```java
// LC523: Continuous Subarray Sum
Map<Integer, Integer> prefixMod = new HashMap<>();
prefixMod.put(0, -1);  // 初始化，处理从头开始的情况
int sum = 0;

for (int i = 0; i < nums.length; i++) {
    sum = (sum + nums[i]) % k;
    
    if (prefixMod.containsKey(sum)) {
        // 两个相同的模数差值 = k的倍数
        if (i - prefixMod.get(sum) >= 2) {
            return true;
        }
    } else {
        prefixMod.put(sum, i);
    }
}
```

**关键：** 如果 `prefix[i] % k == prefix[j] % k`，
则 `sum[j+1..i] % k == 0`

使用场景（Google高频）：
* Subarray Sum Equals K
* Continuous Subarray Sum
* Path Sum III
* Contiguous Array

## 第十类：Union Find（并查集）

Connectivity高级题的标准工具

```java
class UnionFind {
    int[] parent;
    int[] rank;
    
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }
    
    // 带路径压缩的find
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // 路径压缩
        }
        return parent[x];
    }
    
    // 带rank优化的union
    public boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        
        if (rootA == rootB) {
            return false;  // 已经在同一集合
        }
        
        // 按rank合并（较小树挂到较大树下）
        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }
        
        return true;
    }
}
```

### 应用例子：Redundant Connection

```java
public int[] findRedundantConnection(int[][] edges) {
    UnionFind uf = new UnionFind(edges.length);
    
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1];
        
        // 如果已在同一集合，说明这条边形成环
        if (!uf.union(u, v)) {
            return edge;  // 这是冗余边
        }
    }
    
    return new int[0];
}
```

使用场景：
* Accounts Merge（合并账户）
* Redundant Connection（冗余连接）
* Number of Provinces（省份数）
* Connected Components in an Undirected Graph

⸻

## 第十一类：Backtracking State（回溯状态）

Backtracking三件套

```java
List<List<Integer>> result = new ArrayList<>();
List<Integer> path = new ArrayList<>();
boolean[] visited = new boolean[nums.length];

// Permutation例子
dfs(nums, path, visited, result);

private void dfs(int[] nums, List<Integer> path, 
                 boolean[] visited, List<List<Integer>> result) {
    // 1. 终止条件
    if (path.size() == nums.length) {
        result.add(new ArrayList<>(path));
        return;
    }
    
    // 2. 选择
    for (int i = 0; i < nums.length; i++) {
        if (visited[i]) continue;
        
        // 选择
        visited[i] = true;
        path.add(nums[i]);
        
        // 递归
        dfs(nums, path, visited, result);
        
        // 回溯
        path.remove(path.size() - 1);
        visited[i] = false;
    }
}
```

Backtracking三件套：
1. **Path**: 记录当前路径
2. **Visited**: 记录已使用元素
3. **Result**: 存储所有解

使用场景：
* Permutation（全排列）
* Combination（组合）
* Subset（子集）
* Word Search（单词搜索）

⸻

第十一类：Backtracking State Container
很多人忽略。

⸻

Path
List<Integer> path

⸻

Visited
boolean[] visited

⸻

Result
List<List<Integer>>

⸻

这是Backtracking三件套。

## 第十二类：Bitmask（位掩码）

Google Hard核心，用于状态压缩

```java
// LC847: 访问所有节点的最短路径
Queue<int[]> queue = new LinkedList<>();
boolean[][] visited = new boolean[n][1 << n];

// 初始化：从每个节点开始
for (int i = 0; i < n; i++) {
    int mask = 1 << i;
    queue.offer(new int[]{i, mask});
    visited[i][mask] = true;
}

int steps = 0;
while (!queue.isEmpty()) {
    int size = queue.size();
    
    for (int i = 0; i < size; i++) {
        int[] curr = queue.poll();
        int node = curr[0];
        int mask = curr[1];
        
        // 检查是否访问所有节点
        if (mask == (1 << n) - 1) {
            return steps;
        }
        
        // 处理邻接节点
        for (int next : graph[node]) {
            int nextMask = mask | (1 << next);
            if (!visited[next][nextMask]) {
                visited[next][nextMask] = true;
                queue.offer(new int[]{next, nextMask});
            }
        }
    }
    steps++;
}
```

**关键操作：**
* 添加：`mask |= (1 << i);`
* 检查：`(mask & (1 << i)) != 0`
* 全集：`mask == (1 << n) - 1`

使用场景：
* LC847：访问所有节点
* LC864：收集所有钥匙
* TSP：旅行商问题

⸻

## 第十三类：Memo Cache（记忆化缓存）

DP和DFS Memo的核心

```java
// 方式1：使用Map
Map<String, Integer> memo = new HashMap<>();

private int dfs(int index, String state) {
    String key = index + "," + state;
    if (memo.containsKey(key)) {
        return memo.get(key);
    }
    
    // 处理逻辑
    int result = ...;
    
    memo.put(key, result);
    return result;
}

// 方式2：使用2D数组
Integer[][] memo = new Integer[m][n];

private int dfs(int row, int col) {
    if (memo[row][col] != null) {
        return memo[row][col];
    }
    
    int result = ...;
    memo[row][col] = result;
    return result;
}
```

使用场景：
* Coin Change（硬币兑换）
* Word Break（单词拆分）
* Longest Increasing Path（最长递增路径）
* 任何DFS + 重复子问题

**选择数据结构：**
* 状态简单（2D）→ 2D数组更快
* 状态复杂 → Map更灵活

⸻

## 第十四类：Result Grouping（结果分组）

业务题特别常见

```java
// Group By Pattern
Map<String, List<String>> groups = new HashMap<>();

for (String item : items) {
    String key = computeKey(item);  // 计算分组键
    groups.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
}

// 返回分组结果
return new ArrayList<>(groups.values());

// 例子1：Accounts Merge
Map<String, TreeSet<String>> emailGroups = new HashMap<>();
for (String email : emailToName.keySet()) {
    String root = uf.find(email);
    emailGroups.computeIfAbsent(root, k -> new TreeSet<>())
               .add(email);
}

// 例子2：Group Anagrams
Map<String, List<String>> groups = new HashMap<>();
for (String word : strs) {
    char[] chars = word.toCharArray();
    Arrays.sort(chars);
    String key = new String(chars);  // 排序后的字符
    groups.computeIfAbsent(key, k -> new ArrayList<>())
          .add(word);
}
return new ArrayList<>(groups.values());

// 例子3：Top K Frequent Elements
Map<Integer, Integer> freq = new HashMap<>();
for (int num : nums) {
    freq.put(num, freq.getOrDefault(num, 0) + 1);
}

Map<Integer, List<Integer>> freqToNums = new HashMap<>();
for (int num : freq.keySet()) {
    int f = freq.get(num);
    freqToNums.computeIfAbsent(f, k -> new ArrayList<>())
              .add(num);
}
```

使用场景：
* Accounts Merge（合并账户）
* Group Anagrams（分组字母异位词）
* Top K Frequent Elements（前K个高频元素）
* 任何需要分组或分类的问题

## 总结：Google Hard的真正套路

⸻

### 完整的解题框架

```
题目
 ↓
1️⃣ Modeling（建模）
    把现实问题抽象成图、网格、状态空间等
 ↓
2️⃣ State Design（状态设计）
    什么信息影响未来决策？
 ↓
3️⃣ Auxiliary Structure Design（辅助结构）
    ⭐ 这一步最能拉开差距 ⭐
 ↓
4️⃣ Transition Design（状态转移）
    如何从一个状态到另一个状态？
 ↓
5️⃣ Choose Algorithm（选择算法）
    BFS / DFS / DP / Dijkstra / Union Find
```

⸻

### 案例分析

#### 案例1：LC864 (Shortest Path to Get All Keys)

```
Model:      Grid + State Space
State:      (row, col, keyMask)
Aux:        visited[row][col][mask]
Transition: 4 directions
Algorithm:  BFS
```

为什么需要 `visited[row][col][mask]`？
* 同一个位置，不同的钥匙状态 = 不同的问题状态
* 需要追踪"访问过这个(位置,钥匙集合)吗？"

⸻

#### 案例2：Dijkstra

```
Model:      Weighted Graph
State:      (node, cost)
Aux:        dist[], PriorityQueue<int[]>
Transition: neighbor update
Algorithm:  Dijkstra
```

为什么需要 `dist[]`？
* 记录到每个节点的最小距离
* 用来判断是否发现了更优路径

为什么需要 `PriorityQueue`？
* 加速：总是处理最小距离的节点
* 否则要 O(V²)，用堆可以 O((V+E) log V)

⸻

#### 案例3：Course Schedule (Topological Sort)

```
Model:      Dependency Graph
State:      课程
Aux:        graph[], indegree[]
Transition: indegree--
Algorithm:  Topological Sort (BFS)
```

为什么需要 `indegree[]`？
* 追踪"这门课还有多少前置条件未完成"
* 入度=0 时才能处理这门课

为什么需要 `graph[]`？
* 快速找到"如果完成这门课，后续哪些课可以解锁"

⸻

### ⭐ Google面试官关注点

**很多题在讲到第3步（Auxiliary Structure）时，面试官就基本知道你能不能解决了**

因为：
* State 想对了，说明理解了问题
* Aux Structure 选对了，说明理解了时间/空间复杂度的权衡
* 后面的代码？大多数资深工程师都会写

**真正拉开差距的问题：**
1. "这个问题到底需要维护哪些信息？"
2. "用什么数据结构维护最合适？"
3. "为什么这样设计？有其他选择吗？"
4. "时间空间复杂度各是多少？"

⸻

### 快速决策表

| 需求 | 选择结构 | 原因 |
|------|--------|------|
| 标记访问状态 | visited array/Set | O(1)查询，简单 |
| 存储连接关系 | Adjacency List | O(V+E)遍历，自然 |
| 统计频率 | Map<K,Int> | O(1)更新，灵活 |
| 追踪依赖 | indegree array | O(1)访问，拓扑排序 |
| 最短路径 | dist array | O(1)比较，记录最优 |
| 路径恢复 | parent array | O(path_len)反推，简单 |
| 优先级处理 | PriorityQueue | O(log n)插删，自动排序 |
| 滑窗最值 | Monotonic Deque | O(1) amort，高效 |
| 快速求和 | Prefix Sum | O(1)查询，降低复杂度 |
| 连通性判断 | Union Find | O(α(n))接近O(1) |
| 回溯选择 | List + boolean[] | 支持撤销，标准 |
| 状态压缩 | Bitmask | 内存节约，32状态 |
| 避免重复 | Memo Map/Array | 加速DFS,标准DP |
| 结果分组 | Map<K,List<V>> | 自然分类，灵活 |
