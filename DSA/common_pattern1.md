# common_pattern1 | Problem Library

## 📖 中文 | CHINESE

此文件包含 LeetCode 问题及详细的 7 步框架分析注释。
代码注释为双语：问题标题、框架分析、复杂度分析都有中英文说明。

## 🔍 ENGLISH

This file contains LeetCode problems with detailed 7-step framework annotations.
Code comments are bilingual: problem titles, framework analysis, and complexity analysis have Chinese-English explanations.

---

## 📋 Pattern Index

Pattern	Typical     State
BFS Grid	        row,col
BFS Graph	        node
BFS Resource	    node,k
BFS Race Car	    pos,speed
DFS Tree	        node
Backtracking	    index,path
Dijkstra	        distance,node
Heap	            priority,value
Union Find	        parent
Topological Sort	indegree


Day 1

Pattern 1: BFS Grid 
State: (row, col)

---

## What is BFS Grid?

BFS Grid is a pattern for exploring cells in a grid/matrix where each cell is a node. You start from one or more cells and visit all connected cells level by level, where "connected" usually means adjacent in 4 or 8 directions.

**Real-world examples:**
- Counting islands of land surrounded by water
- Spreading of infection or rotting from multiple sources
- Finding shortest path in an unweighted grid

---

## 核心步骤 Core Steps

### 7-Step BFS Grid Framework

**1. Understand the Problem**
- Identify what is a "cell" (node) in the grid
- Define what "connected" means (4 adjacent: up, down, left, right OR 8 adjacent: including diagonals)
- Determine starting position(s) (single source or multi-source)

**2. State Definition**
- State = `(row, col)` coordinates of current cell
- State space size = rows × cols

**3. Initialize Data Structures**
- Create a `Queue` for BFS
- Mark visited cells (use original grid, separate array, or set - choose one method)
- Prepare direction vectors for neighboring cells: `{{-1,0}, {1,0}, {0,-1}, {0,1}}`

**4. Initialization Loop**
- For **single-source BFS**: Add starting cell to queue, mark visited
- For **multi-source BFS**: Add all starting cells to queue, mark all visited

**5. BFS Main Loop** (while queue not empty)
- Get queue size (number of cells at current level)
- Process each cell at this level:
  - Extract current cell from queue
  - Try all 4 (or 8) neighbors
  - Check: within bounds + target condition + not visited
  - Add valid neighbors to queue, mark visited

**6. Track Progress** (if needed)
- For level-by-level processing: increment level counter after processing all cells at current level
- For counting: increment counter when discovering new valid states
- For shortest path: steps = BFS level number

**7. Return Answer**
- Return the computed result (count, steps, visited cells, etc.)

---

### Problem 1: Number of Islands
**LeetCode 200 | Medium**

**💡 Key Insight & Why It Works:**

想象一个地图，上面有陆地（1）和水（0）。相邻的陆地连在一起，组成一个岛屿。你要数有多少个岛屿。

**怎么做？** 
- 从任何未访问的陆地开始，用 BFS 把整个岛屿的所有陆地都"探索"一遍，标记为已访问
- 每完成一次 BFS，就是找到了一个岛屿
- 继续找下一个未访问的陆地，重复

**为什么用 BFS？** 因为我们只关心"连通"，不关心距离，所以 BFS 很完美。

**💬 For Interview - Just Say:**
- 从每个未访问的陆地开始，用 BFS 探索整个岛屿
- 标记已访问的陆地，避免重复计算
- 每完成一次 BFS = 找到一个岛屿
```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   将网格中的每个单元格看作节点，相邻的陆地（'1'）形成无向图
 *   问题转化为：计算连通分量数量
 *   本质：离散数学中的图连通性问题
 *
 * 状态 State:
 *   单个状态 = (row, col) 一个单元格的坐标
 *   状态空间大小 = O(rows × cols) = 10^4 个状态
 *   状态代表：当前正在探索的陆地单元格
 *
 * 辅助数据结构 Aux Structure:
 *   - Queue<int[]>: BFS队列，维护待探索的单元格
 *   - 修改原grid: 标记已访问（省空间，不用额外visited数组）
 *   - int[][] DIRECTIONS: 上下左右四个方向常量
 *
 * 状态转移 Transition:
 *   从状态(r,c)出发 → 检查四个相邻方向
 *   条件：相邻单元格必须是陆地('1')且未访问
 *   行为：加入队列，标记为'0'（已访问）
 *   转移公式：(r,c) → (r±1,c) 和 (r,c±1)
 *
 * 选择算法 Solver:
 *   BFS (Breadth-First Search)
 *   理由：无权图，求连通分量，BFS足够（比DFS更安全，无栈溢出风险）
 *
 * 复杂度分析:
 *   时间: O(rows × cols)  - 每个单元格访问一次
 *   空间: O(rows × cols)  - 队列最多存储所有单元格
 *   单位时间：4个方向检查 = O(4) = O(1)
 *
 * 不变量 Invariant:
 *   - 访问过的陆地一定被标记为'0'
 *   - 队列中的所有元素都未被处理
 *   - 每次BFS执行完 = 探索完一个连通分量
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    // 四个方向常量：上、下、左、右
    private final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int rows = grid.length;
        int cols = grid[0].length;
        int islands = 0;

        // 主逻辑：遍历每个单元格
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // 发现新陆地 → 启动新的BFS
                if (grid[r][c] == '1') {
                    islands++;
                    bfs(grid, r, c, rows, cols);
                }
            }
        }
        return islands;
    }

    // BFS: 探索单个连通分量
    private void bfs(char[][] grid, int startRow, int startCol, int rows, int cols) {
        // Aux: BFS队列
        Queue<int[]> queue = new LinkedList<>();
        
        // Transition: 初始状态入队
        queue.offer(new int[]{startRow, startCol});
        grid[startRow][startCol] = '0'; // 标记已访问
        
        // Solver: 广度优先搜索
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1];
            
            // Transition: 检查四个方向
            for (int[] dir : DIRECTIONS) {
                int newRow = r + dir[0];
                int newCol = c + dir[1];
                
                // State检查：边界 + 陆地 + 未访问
                if (newRow >= 0 && newRow < rows && newCol >= 0 && 
                    newCol < cols && grid[newRow][newCol] == '1') {
                    queue.offer(new int[]{newRow, newCol});
                    grid[newRow][newCol] = '0'; // 立即标记，避免重复入队
                }
            }
        }
    }
}
```

### Problem 2: Rotting Oranges
**LeetCode 994 | Medium**

**💡 Key Insight & Why It Works:**

橙子会腐烂。腐烂的橙子会把相邻的新鲜橙子也感染腐烂。你要计算多少分钟后所有橙子都腐烂（或判断不可能）。

**关键：多源 BFS**
- 不是从一个起点，而是从所有腐烂的橙子同时开始
- 每一"层" BFS = 过去一分钟，所有腐烂的橙子同时去感染相邻的
- BFS 的层数 = 需要的分钟数

**为什么这样？** 因为所有腐烂的橙子同时工作，所以 BFS 的分层正好对应时间。

**💬 For Interview - Just Say:**
- 从所有腐烂的橙子同时开始，用多源 BFS
- 每一"层" = 过去一分钟
- BFS 的层数就是答案

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   多源BFS：所有腐烂橙子(2)同时向相邻新鲜橙子(1)扩散
 *   问题转化为：求最短时间使所有新鲜橙子腐烂
 *   本质：多源最短路径，时间维度 = BFS层数
 *
 * 状态 State:
 *   单个状态 = (row, col) 一个单元格的坐标
 *   隐含时间维度 = BFS层数（每层代表一分钟）
 *   多源初始化：所有腐烂橙子(2)都是起点
 *
 * 辅助数据结构 Aux Structure:
 *   - Queue<int[]>: BFS队列，维护待处理的腐烂橙子
 *   - int fresh: 计数器，追踪未腐烂的新鲜橙子数
 *   - 修改原grid: 标记已腐烂状态（省空间）
 *   - int[][] DIRECTIONS: 上下左右四个方向常量
 *
 * 状态转移 Transition:
 *   从状态(r,c)出发 → 检查四个相邻方向
 *   条件：相邻单元格必须是新鲜橙子(1)且未腐烂
 *   行为：标记为'2'（已腐烂），加入队列，fresh递减
 *   转移公式：(r,c) → (r±1,c) 和 (r,c±1)
 *   关键：按层处理，每层对应一分钟
 *
 * 选择算法 Solver:
 *   多源BFS (Multi-source BFS)
 *   理由：多个起点，求最短时间，BFS按层处理保证正确性
 *
 * 复杂度分析:
 *   时间: O(rows × cols)  - 每个单元格最多访问一次
 *   空间: O(rows × cols)  - 队列最多存储所有单元格
 *   单位时间：4个方向检查 = O(4) = O(1)
 *
 * 不变量 Invariant:
 *   - 访问过的新鲜橙子一定被标记为'2'
 *   - 同一层处理的橙子距源点距离相同
 *   - fresh > 0 且队列为空 → 不可能腐烂所有橙子
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    private final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int orangesRotting(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Aux: BFS队列 + 计数器
        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;

        // Transition: 多源初始化 - 所有腐烂橙子入队
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) {
                    queue.offer(new int[]{r, c});
                } else if (grid[r][c] == 1) {
                    fresh++;
                }
            }
        }

        // 边界检查：无新鲜橙子，无需腐烂
        if (fresh == 0) return 0;

        // Solver: 多源BFS，按层处理
        int minutes = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean changed = false;

            // Transition: 处理当前层所有腐烂橙子
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int r = curr[0], c = curr[1];

                // State转移：检查四个方向
                for (int[] dir : DIRECTIONS) {
                    int newRow = r + dir[0];
                    int newCol = c + dir[1];

                    // 边界检查 + 状态检查：仅感染新鲜橙子
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && 
                        newCol < cols && grid[newRow][newCol] == 1) {
                        grid[newRow][newCol] = 2;
                        fresh--;
                        queue.offer(new int[]{newRow, newCol});
                        changed = true;
                    }
                }
            }

            // 本层有新感染 → 时间推进
            if (changed) minutes++;
        }

        return fresh == 0 ? minutes : -1;
    }
}
```

⸻

Pattern 2: BFS Graph
State: node

### Problem 3: Clone Graph
**LeetCode 133 | Medium**

**💡 Key Insight & Why It Works:**

你要复制一个图（不是复制引用，而是真正的深拷贝）。原图和新图的结构完全一样，但是完全独立的两个图。

**怎么做？用BFS加一个映射表**
- 想象你有一本书，记录"原节点→新节点"的对应关系
- 从起点开始，用BFS逐个访问原图的节点
- 每访问一个新节点，就在新图中创建对应的节点，记录在映射表里
- 对于每个节点的邻接节点，如果还没创建过，就创建；如果已经创建过，就直接连接

**为什么有效？** 映射表确保每个节点只创建一次，BFS确保不漏掉任何节点。

**💬 For Interview - Just Say:**
- 用BFS遍历原图，同时在新图中创建对应节点
- 用HashMap记录"原→新"的映射，避免重复创建
- 对每个节点，连接其新节点的所有邻接点

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   图的深拷贝 - 遍历原图所有节点，创建克隆节点和边
 *   问题转化为：图遍历 + 节点映射（原→克隆）
 *   本质：无权图遍历，维持拓扑结构
 *
 * 状态 State:
 *   单个状态 = (node) 当前正在处理的原节点
 *   状态空间大小 = O(N) = 节点总数
 *   状态代表：当前遍历到的原图节点
 *
 * 辅助数据结构 Aux Structure:
 *   - Map<Node, Node> map: 原节点→克隆节点的映射（避免重复创建）
 *   - Queue<Node> queue: BFS队列，维护待处理的原节点
 *
 * 状态转移 Transition:
 *   从状态node出发 → 检查所有邻接节点neighbor
 *   条件：neighbor未被克隆过
 *   行为：创建克隆neighbor，加入队列，添加边到克隆图
 *   转移公式：(node) → 所有未访问的neighbor
 *
 * 选择算法 Solver:
 *   BFS (Breadth-First Search)
 *   理由：无权图，遍历所有节点和边，BFS保证不漏掉任何节点
 *   Map去重：已克隆节点不会重复创建
 *
 * 复杂度分析:
 *   时间: O(N + E)  - 访问N个节点，遍历E条边
 *   空间: O(N)  - Map存储映射 + 队列空间
 *
 * 不变量 Invariant:
 *   - map中的节点对应的克隆都已创建
 *   - 每处理一个节点 = 添加其所有出边到克隆图
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) return null;

        // Aux: HashMap映射 + BFS队列
        Map<Node, Node> map = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();

        // Modeling: 初始化起点
        Node clone = new Node(node.val);
        map.put(node, clone);
        queue.offer(node);

        // Solver: 广度优先搜索
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            
            // Transition: 处理当前节点的所有邻接节点
            for (Node neighbor : curr.neighbors) {
                // 邻接节点是否已克隆
                if (!map.containsKey(neighbor)) {
                    map.put(neighbor, new Node(neighbor.val));
                    queue.offer(neighbor);
                }
                // 在克隆图中添加边
                map.get(curr).neighbors.add(map.get(neighbor));
            }
        }

        return clone;
    }
}
```

### Problem 4: Valid Tree / Graph Valid Tree
**LeetCode 261 | Medium**

**💡 Key Insight & Why It Works:**

你要判断一个给定的边集合是否构成一个有效的树。树有个特殊性质：n个节点，恰好n-1条边，所有节点连通，没有环。

**怎么做？BFS计数**
- 从节点0开始，用BFS访问所有能到达的节点
- 如果访问的节点数 = n，说明所有节点连通
- 如果边数 ≠ n-1，说明不是树

**为什么有效？** 树的定义就是：连通 + n-1条边。BFS确保检查连通性，边数检查确保没有环。

**💬 For Interview - Just Say:**
- 检查边数：必须等于 n-1
- 用BFS从节点0开始遍历
- 如果访问的节点数 = n，说明是树；否则不是

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   图连通性问题 - 判断给定边集是否形成树
 *   树的定义：n个节点，n-1条边，连通（1个连通分量），无环
 *   问题转化：(1) 边数检查 (2) 连通性检查 (通过BFS访问所有节点)
 *   本质：图论中的连通分量问题
 *
 * 状态 State:
 *   单个状态 = (node) 当前正在访问的节点
 *   状态空间大小 = O(n) 个节点状态
 *   状态代表：当前遍历中访问的节点
 *
 * 辅助数据结构 Aux Structure:
 *   - Map<Integer, List<Integer>>: 邻接表表示图
 *   - Queue<Integer>: BFS队列，维护待探索的节点
 *   - Set<Integer>: visited集合，标记已访问的节点
 *
 * 状态转移 Transition:
 *   从状态(node)出发 → 检查所有邻接未访问节点
 *   条件：邻接节点必须未被访问
 *   行为：加入队列，标记为已访问
 *   转移公式：(node) → (neighbor) 对所有邻接点
 *
 * 选择算法 Solver:
 *   BFS (Breadth-First Search)
 *   理由：无权无向图，检查连通性，BFS恰好确保访问所有连通节点
 *
 * 复杂度分析:
 *   时间: O(n + m)  - 访问n个节点，遍历m=n-1条边
 *   空间: O(n)  - 邻接表+队列+visited集合
 *   单位时间：度数求和 = O(2m) = O(n)
 *
 * 不变量 Invariant:
 *   - 访问过的节点一定在visited集合中
 *   - 队列中的所有节点都已标记visited
 *   - BFS完成后，visited.size() = 连通分量大小
 *   - 若visited.size() == n 且 edges.length == n-1 → 有效树
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public boolean validTree(int n, int[][] edges) {
        // 前置检查：树必须有恰好n-1条边
        if (edges.length != n - 1) return false;

        // 建立邻接表
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        // BFS遍历检查连通性
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.offer(0);
        visited.add(0);

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int neighbor : graph.get(curr)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        // 检查是否访问了所有节点(连通)
        return visited.size() == n;
    }
}
```

⸻

Pattern 3: BFS Word Transformation
State: word

### Problem 5: Word Ladder
**LeetCode 127 | Hard**

**💡 Key Insight & Why It Works:**

你要从一个单词变换到另一个单词，每次只能改一个字母，中间的每个单词都必须在词表中。求最少要改几次。

**怎么做？想象单词转换的"距离"，用BFS找最短距离**
- 把每个单词看作一个节点，如果两个单词只差一个字母，就有条边
- 用BFS从起始单词开始，一层一层地探索
- 第一次到达目标单词时，BFS的层数 = 最少改的次数

**为什么有效？** BFS在无权图中找最短路径。每一层代表改一次字母。

**💬 For Interview - Just Say:**
- 建模：把单词和转换关系看成图
- 用BFS从起始单词开始，每次探索"只差一个字母"的单词
- 第一次到达目标单词时，返回层数（改的次数）

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   转换路径问题 - 每次改一个字母，找最短变换路径
 *   本质：图搜索问题，每个单词是节点，可以相互转换则有边
 *   目标：求beginWord到endWord的最短路径长度
 *
 * 状态 State:
 *   单个状态 = (word) 当前的单词
 *   状态空间大小 = N（词表中的单词数）
 *   状态代表：当前探索到的单词
 *
 * 辅助数据结构 Aux Structure:
 *   - Queue<String>: BFS队列，维护待探索的单词
 *   - HashSet<String> wordSet: 词表快速查询
 *   - HashSet<String> visited: 标记已访问单词，避免重复探索
 *   - int steps: 层数计数（转换步数）
 *
 * 状态转移 Transition:
 *   从状态(word)出发：
 *   1. 尝试改变word每一位的字母（26个选择）
 *   2. 检查新单词是否在wordSet中
 *   3. 如果在且未访问，加入队列，标记为已访问
 *   转移公式：word → newWord（仅改变一位字母）
 *   关键：直接枚举26个字母，而不是预生成所有邻接词
 *
 * 选择算法 Solver:
 *   BFS (Breadth-First Search)
 *   理由：求最短路径，BFS保证第一次到达endWord就是最优解
 *   关键技巧：按层处理，每层对应一个转换步数
 *
 * 复杂度分析:
 *   时间: O(N × L × 26)
 *     = O(词数) × O(单词长) × O(字母尝试)
 *   空间: O(N)
 *     = visited + wordSet + 队列空间
 *   L = 单词长度（通常≤20）
 *   26 = 字母表大小
 *
 * 不变量 Invariant:
 *   - visited中的单词一定已处理或正在队列中
 *   - 队列中的所有单词距离源点相同（BFS性质）
 *   - steps每次增加对应一个转换步数
 *   - 每个单词最多被访问一次（visited保证）
 *   - 从不访问不在wordSet中的单词（只转移到有效单词）
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return 0;

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(beginWord);
        visited.add(beginWord);

        int steps = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr.equals(endWord)) return steps;

                // 状态转移：尝试改变每一位字符
                char[] chars = curr.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    char original = chars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue; // 避免重复
                        chars[j] = c;
                        String next = new String(chars);
                        if (wordSet.contains(next) && !visited.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }
                    }
                    chars[j] = original;
                }
            }
            steps++;
        }

        return 0;
    }
}
```

⸻

Pattern 4: BFS State Space (Lock Problem)
State: combination (string representation)

### Problem 6: Open the Lock
**LeetCode 752 | Medium**

**💡 Key Insight & Why It Works:**

你有个转盘锁，4个数字（0-9），你要从"0000"转到目标组合。有些组合是"死锁"，不能经过。问最少要转多少次？

**怎么做？BFS搜索所有可能的转盘状态**
- 把每个转盘组合看成一个"状态"
- 从"0000"开始，每个状态可以转到8个新状态（4个数字，每个可以±1）
- 用BFS逐层探索，跳过死锁状态
- 第一次到达目标组合时，BFS的层数 = 最少转的次数

**为什么有效？** BFS在无权图中找最短路径。每一层代表转一次。

**💬 For Interview - Just Say:**
- 建模：每个转盘组合是一个状态
- 用BFS从"0000"开始，生成相邻的8个状态（±1每位数字）
- 跳过死锁，第一次到达目标时返回层数

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   状态空间搜索问题 - 4位数字转盘，每位可独立旋转(0-9循环)
 *   本质：求状态空间中从"0000"到目标组合的最短路径
 *   约束：避开deadends(死锁状态)
 *
 * 状态 State:
 *   单个状态 = (combination) 一个4位组合字符串，如"0025"
 *   状态空间大小 = 10^4 = 10000个可能的组合
 *   状态代表：当前转盘显示的数字组合
 *
 * 辅助数据结构 Aux Structure:
 *   - Set<String> dead: 存储所有deadends(禁止通过的状态)
 *   - Set<String> visited: 防止重复访问，追踪已探索的组合
 *   - Queue<String>: BFS队列，维护待探索的组合
 *   - List<String> neighbors: 当前状态的8个邻接状态
 *
 * 状态转移 Transition:
 *   从状态curr出发 → 尝试4位中任意一位的两个操作
 *   - 向上转(+1)：'0'→'1', ..., '9'→'0' (循环)
 *   - 向下转(-1)：'0'→'9', ..., '9'→'0' (循环)
 *   邻接数 = 4位 × 2方向 = 8个邻接状态
 *   转移条件：下一状态不在dead集合且未visited
 *
 * 选择算法 Solver:
 *   BFS (Breadth-First Search)
 *   理由：无权状态图，求最短路径，BFS最优
 *   关键：避开deadends，按层处理保证最短
 *
 * 复杂度分析:
 *   时间: O(10^4 × 4) = O(40000)
 *     = 最多访问10^4个状态 × 每个状态检查4位字符
 *   空间: O(10^4)
 *     = visited集合 + dead集合 + 队列最多容纳10^4个状态
 *   BFS层数 = 最短转盘转数 = O(log target)通常情况下更小
 *
 * 不变量 Invariant:
 *   - visited中的所有状态都是从"0000"可达的
 *   - dead中的状态永远不会被加入队列
 *   - 同一状态最多被处理一次（visited保证）
 *   - 每次步数+1对应转盘统一转动一次
 *   - BFS首次发现target = 最少转数
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> dead = new HashSet<>(Arrays.asList(deadends));
        if (dead.contains("0000")) return -1;

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer("0000");
        visited.add("0000");

        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr.equals(target)) return steps;

                // 状态转移：8个邻接状态
                for (String next : getNeighbors(curr)) {
                    if (!dead.contains(next) && !visited.contains(next)) {
                        visited.add(next);
                        queue.offer(next);
                    }
                }
            }
            steps++;
        }

        return -1;
    }

    private List<String> getNeighbors(String state) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = state.toCharArray();
        for (int i = 0; i < 4; i++) {
            char original = chars[i];
            // 向上转
            chars[i] = original == '9' ? '0' : (char)(original + 1);
            neighbors.add(new String(chars));
            // 向下转
            chars[i] = original == '0' ? '9' : (char)(original - 1);
            neighbors.add(new String(chars));
            chars[i] = original;
        }
        return neighbors;
    }
}
```

⸻

Pattern 5: BFS Multiple Dimensions (Race Car)
State: (position, speed)

### Problem 7: Race Car
**LeetCode 818 | Hard**

**💡 Key Insight & Why It Works:**

小车从位置0开始，速度1，加速时位置加速度（速度翻倍），反向时速度变反号。要最少几步到达目标？

**关键：不仅是位置，还有速度！**
- 同一个位置，不同速度 = 不同状态
- 状态是(位置, 速度)对，不只是位置
- 用BFS探索所有可能的(位置, 速度)变化
- 修剪：不要走离目标太远的位置

**为什么有效？** BFS在状态空间中找最短路径。状态包括位置和速度两个维度。

**💬 For Interview - Just Say:**
- 关键：状态 = (位置, 速度)，不只是位置
- 用BFS从(0, 1)开始探索：选择加速或反向
- 修剪远离目标太远的状态，第一次到达目标时返回步数

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   动态系统 - 赛车位置和速度随时间演化
 *   问题转化为：在(position, speed)状态空间中找最短路径
 *   本质：多维状态的BFS探索，缺少任一维都无法决策
 *
 * 状态 State:
 *   单个状态 = (position, speed) 二维状态
 *   - position: 当前位置，范围[-target, 2*target]
 *   - speed: 当前速度，初始为1，每次加速翻倍或反向
 *   状态空间大小 = O(target × log(target)²)
 *   初始状态 = (0, 1) 起点和速度
 *
 * 辅助数据结构 Aux Structure:
 *   - Queue<int[]>: BFS队列，存储(position, speed)状态对
 *   - Set<String>: 记录已探索过的状态，避免重复处理
 *   - String key格式: "pos,speed" 用于状态去重
 *
 * 状态转移 Transition:
 *   从状态(pos, speed)出发，有两种操作：
 *   1. Accelerate: nextPos = pos + speed, nextSpeed = speed * 2
 *   2. Reverse: nextPos = pos, nextSpeed = -speed (位置不变，速度反向)
 *   剪枝条件：|nextPos| < target * 2 才加入队列
 *
 * 选择算法 Solver:
 *   BFS (Breadth-First Search)
 *   理由：无权状态图，求最短步数，BFS保证第一次到达目标就是最优
 *   多维状态不影响BFS的正确性，只是扩大了状态空间
 *
 * 复杂度分析:
 *   时间: O(target × log(target)²)
 *     = 状态数 O(target × log(target)) × 每个状态转移O(1)
 *   空间: O(target × log(target))
 *     = Set<visited>的大小
 *
 * 不变量 Invariant:
 *   - visited中的状态都已被处理过，不会再处理
 *   - 队列中的所有(pos, speed)对代表的距离都相同（BFS特性）
 *   - position == target 时返回的steps一定是最小值
 *   - 任何时刻，|pos| ≥ target*2的状态都被剪枝（不加入队列）
 * ─────────────────────────────────────────────────────────────
 */

class Solution {
    public int racecar(int target) {
        Queue<int[]> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(new int[]{0, 1});
        visited.add("0,1");

        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int position = curr[0];
                int speed = curr[1];

                if (position == target) return steps;

                // 状态转移1：加速
                int nextPos = position + speed;
                int nextSpeed = speed * 2;
                String keyA = nextPos + "," + nextSpeed;
                if (Math.abs(nextPos) < target * 2 && !visited.contains(keyA)) {
                    visited.add(keyA);
                    queue.offer(new int[]{nextPos, nextSpeed});
                }

                // 状态转移2：反向
                int reverseSpeed = speed > 0 ? -1 : 1;
                String keyR = position + "," + reverseSpeed;
                if (!visited.contains(keyR)) {
                    visited.add(keyR);
                    queue.offer(new int[]{position, reverseSpeed});
                }
            }
            steps++;
        }

        return -1;
    }
}
```

⸻

Pattern 6: BFS Resource Tracking
State: (node, remaining_k)

### Problem 8: Cheapest Flights Within K Stops
**LeetCode 787 | Medium**

**💡 Key Insight & Why It Works:**

从一个城市飞到另一个城市，最多只能停靠k次。每个航班有成本。要找最便宜的方案。

**关键：同一城市，不同停靠次数的成本可能不同！**
- 不是简单地找某城市的最小成本
- 状态是(城市, 停靠次数)对
- 到达某城市经过3次停靠 vs 2次停靠 = 不同状态，成本也不同
- 用Dijkstra按成本逐个探索，直到第一次到达目标

**为什么有效？** Dijkstra贪心保证：第一次到达目标时，该成本就是最优的。

**💬 For Interview - Just Say:**
- 关键：状态 = (城市, 停靠次数)，不只是城市
- 用优先队列（按成本排序）从起点开始
- 第一次到达目标城市时返回成本

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * * 建模 Modeling: 图中从源点到目标点的最短路径问题，限制最多经过K条边（K+1个顶点）。| Modeling: Shortest path problem in a graph from source to destination with constraint of at most K edges (K+1 vertices).
* 状态 State: (城市, 停止数)表示到达某城市且已经过的停止次数；目标是找到dist[目标城市][≤K停止]的最小值。| State: (city, stops) represents arrival at a city with a certain number of stops taken; goal is to find minimum dist[destination][≤K stops].
* 辅助数据结构 Aux Structure: 距离矩阵dist[city][stops]、优先队列存储(成本, 城市, 停止数)。| Aux Structure: Distance matrix dist[city][stops], priority queue storing (cost, city, stops).
* 状态转移 Transition: 从(城市u, 停止数s)出发，对每条边(u→v, 权重w)，更新dist[v][s+1] = min(dist[v][s+1], dist[u][s] + w)。| Transition: From state (city u, stops s), for each edge (u→v, weight w), update dist[v][s+1] = min(dist[v][s+1], dist[u][s] + w).
* 选择算法 Solver: Dijkstra算法或BFS+优先队列，按成本贪心扩展状态。| Solver: Dijkstra's algorithm or BFS with priority queue, greedily expand states by cost.
* 复杂度分析: 时间O((N+E)×K×logNK)，空间O(N×K)；其中N是城市数，E是航班数。| Complexity: Time O((N+E)×K×logNK), Space O(N×K); where N is number of cities, E is number of flights.
* 不变量 Invariant: 优先队列中弹出的每个状态都是到该城市该停止数的最小成本；dist[v][s]≤dist[v][s+1]。| Invariant: Each state popped from priority queue is minimum cost to reach that city with that stop count; dist[v][s]≤dist[v][s+1].
 * 
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] flight : flights) {
            graph.get(flight[0]).add(new int[]{flight[1], flight[2]});
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{src, 0});
        int[] minCost = new int[n];
        Arrays.fill(minCost, Integer.MAX_VALUE);
        minCost[src] = 0;

        int stops = 0;
        while (!queue.isEmpty() && stops <= k) {
            int size = queue.size();
            int[] tempCost = Arrays.copyOf(minCost, n);

            // 状态转移：本层的所有城市
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int city = curr[0];
                int cost = curr[1];

                for (int[] next : graph.get(city)) {
                    int nextCity = next[0];
                    int price = next[1];
                    if (cost + price < tempCost[nextCity]) {
                        tempCost[nextCity] = cost + price;
                        queue.offer(new int[]{nextCity, cost + price});
                    }
                }
            }

            minCost = tempCost;
            stops++;
        }

        return minCost[dst] == Integer.MAX_VALUE ? -1 : minCost[dst];
    }
}
```

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   有权最短路径(带约束) - 同上，但使用优先级队列优化
 *   问题本质相同：多维度约束下的最低成本路径
 *
 * 状态 State:
 *   单个状态 = (city, cost, stops) 三维状态
 *   与BFS版本相同，但优先级队列按cost排序
 *   状态空间大小 = O(n × k)（理论最坏）
 *
 * 辅助数据结构 Aux Structure:
 *   - Map<Integer, List<int[]>> graph: 邻接表表示有向图
 *   - PriorityQueue<int[]> pq: 按cost排序，存储(city, cost, stops)三元组
 *   - 无需额外visited数组（通过stops检查）
 *
 * 状态转移 Transition:
 *   从优先级队列中取出成本最低的状态(city, cost, stops)：
 *   1. 如果city == dst，立即返回cost（贪心最优性）
 *   2. 如果stops > k，跳过该状态（约束检查）
 *   3. 探索所有邻接城市，加入队列
 *   贪心性：总是先处理最低成本的城市
 *
 * 选择算法 Solver:
 *   Dijkstra with Stop Constraint
 *   理由：
 *   - 有权图求最短路径，Dijkstra经典适用
 *   - PriorityQueue优化：O(E log V) vs BFS O(k×E)
 *   - 停靠次数约束通过states中的stops维度管理
 *   - 贪心选择：总是最低成本优先
 *
 * 复杂度分析:
 *   时间: O(E log V) = O(E log n)
 *     = O(边数 × log节点数)
 *     Dijkstra标准复杂度，比BFS版本通常更优
 *   空间: O(V) = O(n)
 *     优先级队列在实践中远小于O(n×k)
 *     因为Dijkstra快速收敛到最优解
 *
 * 不变量 Invariant:
 *   - PriorityQueue中的所有元素未被取出处理
 *   - 第一次取出dst状态的cost = 全局最优（贪心性）
 *   - 同一(city, stops)状态最多被处理一次（隐含去重）
 *   - 超过k的stops状态会被跳过（不加入新状态）
 *
 * 关键技巧:
 *   1. 贪心处理：PriorityQueue保证取出最低成本
 *   2. 早期终止：到达dst时立即返回（无需完整遍历）
 *   3. 约束过滤：stops > k时continue跳过
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] flight : flights) {
            graph.get(flight[0]).add(new int[]{flight[1], flight[2]});
        }

        // 按成本排序的优先级队列
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{src, 0, 0}); // {city, cost, stops}

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int city = curr[0];
            int cost = curr[1];
            int stops = curr[2];

            // 提前到达目标
            if (city == dst) return cost;

            // 超过停靠限制
            if (stops > k) continue;

            // 状态转移：探索所有邻接城市
            for (int[] next : graph.get(city)) {
                pq.offer(new int[]{next[0], cost + next[1], stops + 1});
            }
        }

        return -1;
    }
}
```

⸻

Pattern 7: BFS Bitmask State Compression
State: (node, visitedMask)

### Problem 9: Shortest Path Visiting All Nodes
**LeetCode 847 | Hard**

**💡 Key Insight & Why It Works:**

你要找一条最短的路径，访问所有节点。注意：不是最短路径从A到B，而是要访问所有节点。

**关键：同一个节点，访问过的节点集合不同 = 不同状态！**
- 状态是(当前节点, 已访问节点集合)的组合
- 用位掩码(bitmask)表示已访问的节点集合（对n≤15的图很优雅）
- 到达同一节点，但已访问节点集合不同 = 完全不同的状态
- 用BFS探索所有状态，直到找到访问全部节点的最短路径

**为什么有效？** 位掩码巧妙编码了"访问过哪些节点"的信息，使状态空间有限且可遍历。

**💬 For Interview - Just Say:**
- 关键：状态 = (节点, 已访问集合)，不是只有节点
- 用位掩码表示已访问的节点（比如第bit位=1表示节点已访问）
- 用BFS从所有可能的起点开始（或逐个尝试）
- 当找到访问完全部节点的状态时，返回步数

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   扩展维度：从(node)单维 → (node, visitedMask)二维
 *   问题本质：遍历所有节点的最短路径
 *   关键洞察：同一节点但访问状态不同 = 不同的问题状态
 *   例如：node 2 已访问{0,2} vs 已访问{1,2} 是两个不同状态
 *
 * 状态 State:
 *   单个状态 = (node, visitedMask)
 *   node: 当前所在节点，范围[0, n-1]
 *   visitedMask: 位掩码表示访问过的节点集合
 *   状态空间大小 = n × 2^n = 15 × 2^15 = O(500K)
 *   多源初始化：每个节点作为起点，初始mask = 1<<i
 *
 * 辅助数据结构 Aux Structure:
 *   - Queue<int[]>: BFS队列，存储(node, mask)对
 *   - boolean[n][2^n] visited: 追踪已探索的状态
 *   - int targetMask: 目标掩码 = (1<<n)-1，表示所有节点都访问过
 *   - int[][] graph: 邻接表表示图
 *
 * 状态转移 Transition:
 *   从状态(node, mask)出发：
 *   1. 探索所有邻接节点 next
 *   2. 计算新掩码：nextMask = mask | (1 << next)
 *   3. 如果状态(next, nextMask)未访问过，加入队列
 *   转移公式：(u, mask) → (v, mask | (1<<v)) 对所有邻接点v
 *
 * 选择算法 Solver:
 *   BFS (Breadth-First Search)
 *   理由：求最短路径，BFS保证第一次到达目标就是最优解
 *   多源BFS：初始化n个不同的起点
 *
 * 复杂度分析:
 *   时间: O(n × 2^n × n)
 *     = O(状态数) × O(每个状态检查邻接点)
 *     = O(n × 2^n) × O(度数)
 *     = ~O(n^2 × 2^n)
 *   空间: O(n × 2^n)
 *     = 访问数组 + 队列空间
 *   状态数 = n × 2^n（关键约束：n ≤ 20）
 *
 * 不变量 Invariant:
 *   - mask中第i位=1 ⟺ 节点i已访问
 *   - 同一状态最多被处理一次（visited保证）
 *   - targetMask = (1<<n)-1 = 2^n-1（所有n位都是1）
 *   - BFS发现target = 最短路径
 *
 * 关键技巧:
 *   1. 位操作：mask |= (1<<node) 表示访问节点
 *   2. 多源初始化：避免尝试所有起点的嵌套循环
 *   3. Bitmask压缩：节省内存（vs Set<Integer>或boolean[]）
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        // Modeling: 目标状态 = 所有n个节点都访问过
        int targetMask = (1 << n) - 1;

        // Aux: BFS队列 + 访问状态追踪
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][1 << n];

        // Transition: 多源初始化
        // 理由：题目可从任意节点开始，所以所有节点都是起点
        for (int i = 0; i < n; i++) {
            int initialMask = 1 << i; // 起点i已访问
            queue.offer(new int[]{i, initialMask});
            visited[i][initialMask] = true;
        }

        // Solver: 广度优先搜索
        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            // Transition: 按层处理（BFS的标志）
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int node = curr[0];
                int mask = curr[1];

                // State检查：目标状态
                if (mask == targetMask) {
                    return steps; // 所有节点都访问了
                }

                // State转移：探索所有邻接节点
                for (int next : graph[node]) {
                    // 更新访问掩码：节点next标记为已访问
                    int nextMask = mask | (1 << next);

                    // 状态去重：避免重复探索(next, nextMask)
                    if (!visited[next][nextMask]) {
                        visited[next][nextMask] = true;
                        queue.offer(new int[]{next, nextMask});
                    }
                }
            }

            // 一层探索完毕，步数加1
            steps++;
        }

        return -1; // 不可达（正常情况不会发生）
    }
}
```

⸻

## Day 2: DFS Pattern

Pattern 8: DFS Tree
State: node / path / depth

### Problem 10: Number of Islands (DFS version)
**LeetCode 200 | Medium**

**💡 Key Insight & Why It Works:**

数岛屿的DFS版本。和BFS版本一样，但用递归代替队列。代码更简洁，但栈可能溢出。

**怎么做？递归探索每个连通的陆地区域**
- 扫描每个格子，发现陆地就启动DFS
- DFS递归探索四个方向的相邻陆地
- 标记已访问的格子（改成水），避免重复
- 每启动一次DFS = 一个完整的岛屿

**为什么有效？** 递归自然地探索一个连通分量的所有节点，无需额外的visited数组。

**💬 For Interview - Just Say:**
- 扫描每个格子，发现'1'就岛屿数+1，启动DFS
- DFS递归探索四个方向，标记已访问
- 比BFS代码简洁，但大网格可能栈溢出

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   将网格中的每个单元格看作节点，相邻的陆地（'1'）形成无向图
 *   问题转化为：计算连通分量数量
 *   本质：离散数学中的图连通性问题（同Problem 1，但用DFS替代BFS）
 *
 * 状态 State:
 *   单个状态 = (row, col) 一个单元格的坐标
 *   状态空间大小 = O(rows × cols)
 *   状态代表：当前正在探索的陆地单元格
 *
 * 辅助数据结构 Aux Structure:
 *   - 递归调用栈: DFS隐含的栈，维护探索路径
 *   - 修改原grid: 标记已访问（'0'），无需额外visited数组
 *   - 相邻方向: 上下左右四个方向的递归调用
 *
 * 状态转移 Transition:
 *   从状态(r,c)出发 → 递归检查四个相邻方向
 *   条件：相邻单元格必须是陆地('1')且在边界内
 *   行为：立即标记为'0'（已访问），然后递归探索
 *   转移公式：dfs(r±1,c) 和 dfs(r,c±1)
 *
 * 选择算法 Solver:
 *   DFS (Depth-First Search)
 *   理由：无权图，求连通分量，DFS代码更简洁
 *   对比BFS：使用递归栈而非队列，但可能在巨大网格上栈溢出
 *
 * 复杂度分析:
 *   时间: O(rows × cols)  - 每个单元格访问一次
 *   空间: O(rows × cols)  - 递归栈深度最坏情况下访问所有单元格
 *   单位时间：边界检查 + 递归调用 = O(1)
 *
 * 不变量 Invariant:
 *   - 访问过的陆地一定被标记为'0'（不可逆）
 *   - 每次DFS完成 = 探索完一个完整连通分量
 *   - grid修改是永久的（原地修改，DFS完成后不恢复）
 *   - islands计数 = 启动DFS的次数 = 连通分量个数
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int rows = grid.length;
        int cols = grid[0].length;
        int islands = 0;

        // 主逻辑：遍历每个单元格
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    islands++;
                    dfs(grid, r, c, rows, cols);
                }
            }
        }

        return islands;
    }

    // DFS: 递归探索单个连通分量
    private void dfs(char[][] grid, int row, int col, int rows, int cols) {
        // 边界检查 + 状态检查
        if (row < 0 || row >= rows || col < 0 || col >= cols || grid[row][col] != '1') {
            return;
        }

        // 标记已访问
        grid[row][col] = '0';
        
        // 状态转移：递归探索四个方向
        dfs(grid, row - 1, col, rows, cols);
        dfs(grid, row + 1, col, rows, cols);
        dfs(grid, row, col - 1, rows, cols);
        dfs(grid, row, col + 1, rows, cols);
    }
}
```

⸻

### Problem 11: Path Sum
**LeetCode 112 | Easy**

**💡 Key Insight & Why It Works:**

检查树中是否存在从根到叶子节点的路径，其和等于目标值。

**怎么做？递归减法**
- 沿着路径从根往下走，每次减去当前节点的值
- 到达叶子节点时，检查剩余的和是否为0
- 递归探索左右子树

**为什么有效？** 递归自然地遍历从根到叶的所有路径，减法避免了累加和的重复计算。

**💬 For Interview - Just Say:**
- 递归检查：是否存在根到叶的路径，和等于目标
- 递归函数传递 targetSum - node.val（不是累加）
- 到达叶子节点时，检查 targetSum - node.val == 0

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   树路径求和问题 - 检查是否存在从根到叶的路径，其节点值之和等于targetSum
 *   问题本质：树的路径查询问题
 *   路径定义：必须从根节点开始，以叶子节点结束
 *
 * 状态 State:
 *   单个状态 = (node, remainingSum)
 *   node: 当前访问的树节点
 *   remainingSum: 还需要累加的和 = targetSum - 沿途节点值之和
 *   状态空间大小 = O(N) 个节点
 *
 * 辅助数据结构 Aux Structure:
 *   - 递归调用栈: 隐含的状态存储（DFS的调用栈）
 *   - 树的遍历：不需要显式的visited数组（树无环）
 *   - 参数传递：remainingSum从父节点递推给子节点
 *
 * 状态转移 Transition:
 *   从状态(node, remainingSum)出发：
 *   1. 减去当前节点值：nextSum = remainingSum - node.val
 *   2. 递归探索左右子树：(left, nextSum) 或 (right, nextSum)
 *   转移公式：(node, sum) → (left/right, sum - node.val)
 *
 * 选择算法 Solver:
 *   DFS (深度优先搜索/递归)
 *   理由：树路径问题，DFS最自然；检查特定路径，不需要找最短/最长路径
 *   早期返回：找到一个有效路径就返回true，无需遍历全树
 *
 * 复杂度分析:
 *   时间: O(N) 最坏情况下访问所有N个节点
 *        O(H) 最好情况下访问树高H个节点（路径存在且靠近根）
 *        其中H是树的高度，最坏O(N)（链形树）
 *   空间: O(H) 递归栈深度 = 树的高度
 *        最坏O(N)（链形树），最好O(log N)（平衡树）
 *
 * 不变量 Invariant:
 *   - remainingSum = 从当前节点到叶子需要累加的值
 *   - 叶子节点 = left为null且right为null
 *   - 若remainingSum == node.val且node是叶子 → 找到有效路径
 *   - DFS过程中，任何节点的remainingSum = targetSum - (路径上已访问节点值之和)
 *
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;

        // 叶子节点检查
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }

        // DFS: 递归检查左右子树
        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }
}
```

⸻

### Problem 12: Binary Tree Diameter
**LeetCode 543 | Easy**

**💡 Key Insight & Why It Works:**

树的直径 = 任意两节点间最长的路径。注意：路径不一定经过根节点。

**怎么做？每个节点计算它的左右子树高，然后相加**
- 对每个节点，计算左子树高和右子树高
- 经过该节点的最长路径 = 左高 + 右高
- 在DFS过程中追踪全局最大值
- 最后返回该节点到叶子的高度（供上层使用）

**为什么有效？** 最长路径一定经过某个节点，那个节点的左右高度之和就是经过它的最长路径。

**💬 For Interview - Just Say:**
- 用DFS后序遍历（先计算子树高度）
- 在每个节点，计算 leftHeight + rightHeight（经过该节点的路径长）
- 追踪全局最大，返回本节点的高度供上层用

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   树直径问题 - 任意两节点间最长路径（不必经过根）
 *   本质：在DFS遍历时，于每个节点处计算经过该节点的最长路径
 *   关键洞察：树的直径 = 某节点的左子树高 + 右子树高的最大值
 *
 * 状态 State:
 *   单个状态 = (node) 当前正在计算高度的节点
 *   状态空间大小 = O(n) = 树中的所有节点
 *   返回值：从该节点到叶子的最大高度 = 1 + max(左高, 右高)
 *
 * 辅助数据结构 Aux Structure:
 *   - int diameter: 全局变量，跟踪找到的最大直径
 *   - 递归栈：隐含地维护DFS遍历顺序和父子关系
 *
 * 状态转移 Transition:
 *   后序遍历（post-order）：先递归左右子树，再处理当前节点
 *   1. leftHeight = dfs(node.left)
 *   2. rightHeight = dfs(node.right)
 *   3. 更新全局：diameter = max(diameter, leftHeight + rightHeight)
 *   4. 返回当前高度：1 + max(leftHeight, rightHeight)
 *
 * 选择算法 Solver:
 *   DFS（深度优先搜索）+ 后序遍历
 *   理由：树结构，后序遍历保证先获得子树信息再处理当前节点
 *   时间复杂度最优，每个节点仅访问一次
 *
 * 复杂度分析:
 *   时间: O(n) - 每个节点访问一次，在每个节点O(1)更新diameter
 *   空间: O(h) - 递归栈深度，h=树的高度，最坏O(n)（斜树）
 *
 * 不变量 Invariant:
 *   - dfs(node)返回的高度始终 >= 0（空节点返回0）
 *   - diameter永远是某条经过某节点的完整路径长度
 *   - 每个节点的高度 = 1 + max(左子树高, 右子树高)
 *   - diameter在整个DFS过程中单调不减（只会增大或保持）
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    private int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return diameter;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = dfs(node.left);
        int rightHeight = dfs(node.right);

        // 更新全局最大直径(关键：在每个节点更新，不是return时)
        diameter = Math.max(diameter, leftHeight + rightHeight);
        
        // 返回该节点的高度供上层使用
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
```

⸻

### Problem 13: Valid Tree (DFS version)
**LeetCode 261 | Medium**

**💡 Key Insight & Why It Works:**

判断边的集合是否能构成一棵树。树的定义：n个节点，n-1条边，连通，无环。

**怎么做？边数 + DFS连通性检查**
- 先检查边数是否等于n-1
- 再用DFS从节点0开始遍历所有可达的节点
- 如果访问到了n个节点，说明连通且无环 = 是树

**为什么有效？** 树就是连通 + n-1条边。DFS检查连通性，边数检查确保无环。

**💬 For Interview - Just Say:**
- 检查边数：必须等于 n-1
- 用DFS从节点0开始递归遍历
- 如果访问的节点数 = n，说明是树；否则不是

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   图连通性问题 - 判断给定边集是否形成树
 *   树的定义：n个节点，n-1条边，连通（1个连通分量），无环
 *   问题转化：(1) 边数检查 (2) 连通性检查 (通过DFS访问所有节点)
 *   本质：图论中的连通分量问题（同Problem 10，但用DFS在一般图上）
 *
 * 状态 State:
 *   单个状态 = (node) 当前正在访问的节点
 *   状态空间大小 = O(n) 个节点状态
 *   状态代表：当前DFS遍历中访问的节点
 *
 * 辅助数据结构 Aux Structure:
 *   - Map<Integer, List<Integer>>: 邻接表表示图
 *   - Set<Integer>: visited集合，标记已访问的节点
 *   - 递归调用栈: DFS隐含的栈，维护探索路径
 *
 * 状态转移 Transition:
 *   从状态(node)出发 → 递归检查所有邻接未访问节点
 *   条件：邻接节点必须未被访问
 *   行为：标记为已访问，递归探索
 *   转移公式：dfs(neighbor) 对所有邻接未访问节点
 *
 * 选择算法 Solver:
 *   DFS (Depth-First Search)
 *   理由：无权无向图，检查连通性，DFS代码简洁
 *   对比BFS：使用递归而非队列，相同时空复杂度
 *
 * 复杂度分析:
 *   时间: O(n + m)  - 访问n个节点，遍历m=n-1条边
 *   空间: O(n)  - 邻接表+visited+递归栈
 *
 * 不变量 Invariant:
 *   - 访问过的节点一定在visited集合中
 *   - 每次DFS完成 = 探索完一个连通分量
 *   - visited.size() == n 且 edges.length == n-1 → 有效树
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public boolean validTree(int n, int[][] edges) {
        // 前置检查：树必须有恰好n-1条边
        if (edges.length != n - 1) return false;

        // 建立邻接表
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        // DFS遍历检查连通性
        Set<Integer> visited = new HashSet<>();
        dfs(0, graph, visited);

        // 检查是否访问了所有节点(连通)
        return visited.size() == n;
    }

    private void dfs(int node, Map<Integer, List<Integer>> graph, Set<Integer> visited) {
        // 标记已访问
        visited.add(node);

        // 状态转移：递归探索所有邻接未访问节点
        for (int neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, graph, visited);
            }
        }
    }
}
```

⸻

## Day 3: Backtracking Pattern

Pattern 9: Backtracking Permutation
State: (path, used[])

### Problem 14: Permutations
**LeetCode 46 | Medium**

**💡 Key Insight & Why It Works:**

生成一个列表的所有排列。比如[1,2,3]要生成6个排列。

**怎么做？回溯：选择→递归→撤销**
- 想象在构建一个排列，一步步选择数字
- 每选一个，标记为"已用"，再递归处理剩余的
- 当排列长度等于原列表长度，找到一个答案
- 然后"撤销选择"，恢复状态，尝试其他数字

**为什么有效？** 回溯会遍历所有可能的选择顺序，同时撤销确保不会重复或遗漏。

**💬 For Interview - Just Say:**
- 用回溯：选 → 递归 → 撤销
- 追踪已用的数字（visited数组）
- 排列长度 = 输入长度时，保存答案

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   排列生成问题 - 从n个元素中生成所有排列
 *   问题本质：选择优先级顺序，找出所有可能的排列
 *   关键约束：每个元素恰好用一次，不同顺序 = 不同排列
 *
 * 状态 State:
 *   单个状态 = (path, visited[])
 *   path: 当前正在构建的排列，长度从0到n
 *   visited[]: 布尔数组，标记哪些元素已被使用
 *   状态空间大小 = n! × 2^n （所有排列及其构建过程）
 *
 * 辅助数据结构 Aux Structure:
 *   - List<Integer> path: 当前排列的元素序列
 *   - boolean[] visited: 长度为n，tracking已用元素
 *   - List<List<Integer>> result: 收集所有完整排列
 *
 * 状态转移 Transition:
 *   回溯三步骤（Choose → Explore → Unchoose）：
 *   1. Choose: 选择第i个未使用元素
 *      visited[i] = true, path.add(nums[i])
 *   2. Explore: 递归处理更深一层
 *      backtrack(...)
 *   3. Unchoose: 撤销选择，恢复状态
 *      path.remove(...), visited[i] = false
 *   当 path.size() == n 时，找到一个完整排列
 *
 * 选择算法 Solver:
 *   Backtracking (深度优先搜索 + 状态恢复)
 *   理由：需要枚举所有排列，回溯天然支持"探索→回退"的流程
 *   关键：visited[] 确保不重复选择，path长度用来判断终止条件
 *
 * 复杂度分析:
 *   时间: O(N! × N)
 *     = N! 个排列 × N 次操作（每个排列需要N步）
 *     = 递归深度 N × 每层最多 N 个选择
 *   空间: O(N)
 *     = 递归栈深度 O(N) + path/visited 数据结构 O(N)
 *     = 不计result的话只需O(N)
 *
 * 不变量 Invariant:
 *   - visited[i] = true ⟺ nums[i] 已在path中
 *   - path.size() ∈ [0, n]，严格递增（选择）或递减（回溯）
 *   - 任意时刻path中的元素互不重复
 *   - 当path.size() == n时，path是一个完整排列
 *   - 回溯后visited状态完全恢复（visited[i]复原为false）
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, new ArrayList<>(), new boolean[nums.length], result);
        return result;
    }

    private void backtrack(int[] nums, List<Integer> path, boolean[] visited,
                          List<List<Integer>> result) {
        // 终止条件：路径完整
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        // 选择→探索→撤销
        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) continue;

            // 1. Choose
            visited[i] = true;
            path.add(nums[i]);

            // 2. Explore
            backtrack(nums, path, visited, result);

            // 3. Unchoose (回溯)
            path.remove(path.size() - 1);
            visited[i] = false;
        }
    }
}
```

### Problem 15: Permutations II (with duplicates)
**LeetCode 47 | Medium**

**💡 Key Insight & Why It Works:**

生成含重复数字的列表的所有**唯一**排列。比如[1,1,2]要生成3个唯一排列（不是6个）。

**关键：排序 + 聪明的跳过重复**
- 先排序，把重复的数字放在一起
- 在同一个递归层，如果两个数字相同，只选第一个
- 跳过规则：如果当前 = 前一个 且 前一个还没被选 → 跳过
- 这样避免生成重复的排列

**为什么有效？** 排序分组重复元素，跳过规则确保在决策树中不会生成重复的分支。

**💬 For Interview - Just Say:**
- 先排序，把重复的聚在一起
- 用回溯：选 → 递归 → 撤销
- 跳过重复：if (nums[i] == nums[i-1] && !visited[i-1]) continue

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 排列生成问题 - 从n个元素（含重复）生成所有唯一排列。问题本质：通过排序分组重复元素，使用回溯树的剪枝策略避免重复的排列组合。
 * | Modeling: Permutation generation with duplicates - Generate all unique permutations from n elements. The essence is grouping duplicates after sorting and pruning the backtracking decision tree to avoid duplicate results.
 *
 * 状态 State: 单个状态 = (path, visited[])，其中path是正在构建的排列、visited[]标记已用元素。状态空间大小 = n! × 2^n（所有排列及其构建过程），多源初始化时从空排列[0,0,...,0]开始。
 * | State: Single state = (path, visited[]). path contains current permutation being built, visited[] tracks which elements are used. State space size = n! × 2^n. Initial state is empty path with all elements unmarked.
 *
 * 辅助数据结构 Aux Structure: List<Integer> path存储当前排列序列，boolean[] visited长度为n标记已用元素，List<List<Integer>> result收集完整排列。核心：Arrays.sort()预处理使重复元素相邻，便于剪枝。
 * | Aux Structure: List<Integer> path stores current permutation, boolean[] visited marks used elements, List<List<Integer>> result collects all complete permutations. Core: Arrays.sort() groups duplicates together for effective pruning.
 *
 * 状态转移 Transition: 回溯三步（Choose→Explore→Unchoose）。剪枝条件：i > 0 && nums[i] == nums[i-1] && !visited[i-1]则跳过，防止同一递归层选择重复值。当path.size() == n时加入result，回溯返回上层。
 * | Transition: Backtracking three steps (Choose→Explore→Unchoose). Pruning: skip if i > 0 && nums[i] == nums[i-1] && !visited[i-1], preventing duplicate choices at the same recursion level. Add to result when path.size() == n.
 *
 * 选择算法 Solver: Backtracking with Duplicate Pruning。理由：需枚举所有排列，排序+剪枝策略使得only process each unique permutation path once。visited[]确保不重复选择同一元素，剪枝条件确保不重复生成相同排列。
 * | Solver: Backtracking with Duplicate Pruning. Reason: Generate all permutations; sorting + pruning ensures each unique path processed once. visited[] prevents reusing same element; pruning avoids duplicate permutation generation.
 *
 * 复杂度分析: 时间O(N! × N) - N!个排列×N次操作（每排列需N步）。空间O(N) - 递归栈深度O(N)+path/visited数据结构O(N)，不计result空间。剪枝条件不改变渐近复杂度但显著优化常数因子。
 * | Complexity: Time O(N! × N) = N! permutations × N operations per permutation. Space O(N) = recursion stack O(N) + path/visited structures O(N), not counting result. Pruning optimizes constant factors without changing asymptotic complexity.
 *
 * 不变量 Invariant: (1)visited[i]=true⟺nums[i]已在path中；(2)任意时刻path中元素互不重复；(3)当path.size()==n时是完整排列；(4)重复值中仅当前一个被标记为!visited时才能选择当前值（保证排列唯一性）。
 * | Invariant: (1) visited[i]=true ⟺ nums[i] is in path; (2) Elements in path are always distinct; (3) When path.size()==n, path is complete permutation; (4) For duplicate values, can only choose current if previous is !visited at same level (ensures uniqueness).
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, new ArrayList<>(), new boolean[nums.length], result);
        return result;
    }

    private void backtrack(int[] nums, List<Integer> path, boolean[] visited,
                          List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }

            if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) {
                continue;
            }

            visited[i] = true;
            path.add(nums[i]);

            backtrack(nums, path, visited, result);

            path.remove(path.size() - 1);
            visited[i] = false;
        }
    }
}
```

⸻

Pattern 10: Backtracking Combination
State: (index, path)

### Problem 16: Combination Sum
**LeetCode 39 | Medium**

**💡 Key Insight & Why It Works:**

找所有的组合，使得它们的和等于目标。关键：同一个数字可以用多次。

**怎么做？回溯，但这次允许重复使用**
- 从当前位置开始，选择一个数字加入组合
- 递归时，还是从当前位置开始（允许重复选择同一个数字）
- 如果和等于目标，找到一个答案
- 如果和超过目标，剪枝返回

**为什么有效？** 传入 start=i（不是i+1）允许下一次选择从同一位置开始，从而重复使用该数字。

**💬 For Interview - Just Say:**
- 回溯：选 → 递归（从同一位置） → 撤销
- 用 remaining 追踪还需多少和
- 剪枝：remaining < 0 时返回

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将候选数字看作决策树节点，每层代表选择哪个数字，每条路径代表一个组合。问题转化为：在决策树中找出所有和等于目标值的路径。
 * | Modeling: Model candidate numbers as a decision tree where each level represents choosing an element, and each path represents a combination. Transform the problem into: find all paths in the decision tree where the sum equals the target value.
 *
 * 状态 State: 单个状态 = (start, remaining) 表示当前起始索引和剩余的目标值。状态空间大小 = O(N × T)，其中N为候选数字个数，T为目标值大小。状态代表：还需要再找多少和，且从哪个位置开始选择。
 * | State: A single state = (start, remaining) represents the current starting index and remaining target value. State space = O(N × T) where N is number of candidates and T is the target value. Each state represents: how much sum is still needed and from which position to continue selection.
 *
 * 辅助数据结构 Aux Structure: (1) List<Integer> path：维护当前选择的数字序列；(2) List<List<Integer>> result：存储所有有效的组合；(3) 无需visited数组，因为允许重复使用同一个数字。
 * | Aux Structure: (1) List<Integer> path: maintains the sequence of currently selected numbers; (2) List<List<Integer>> result: stores all valid combinations; (3) No visited array needed since the same element can be reused multiple times.
 *
 * 状态转移 Transition: 从状态(start, remaining)出发，遍历[start, n)位置的候选数字。对于候选数[i]，选择它→递归进入状态(i, remaining - candidates[i])→回溯移除该数字。剪枝：若remaining < 0则直接返回；若remaining == 0则找到一个有效组合。
 * | Transition: From state (start, remaining), iterate candidates from position start to n. For each candidate[i]: select it → recursively enter state (i, remaining - candidates[i]) → backtrack by removing it. Pruning: if remaining < 0 return early; if remaining == 0 found a valid combination.
 *
 * 选择算法 Solver: 使用回溯算法（Backtracking）配合深度优先搜索（DFS）。理由：需要探索决策树的所有分支以找出所有组合，且要求保持当前路径状态以便回溯。传递start=i而非i+1允许同一元素重复使用。
 * | Solver: Use Backtracking with Depth-First Search (DFS). Rationale: must explore all branches of the decision tree to find all combinations while maintaining current path state for backtracking. Passing start=i instead of i+1 allows the same element to be reused.
 *
 * 复杂度分析: 时间复杂度 = O(N^(T/M)) 其中T=目标值，M=最小候选数字（决策树的最大深度是T/M，每层最多N个分支）。空间复杂度 = O(T/M)用于递归调用栈和path列表的最大深度。
 * | Complexity Analysis: Time = O(N^(T/M)) where T=target, M=minimum candidate (max tree depth is T/M, each level has at most N branches). Space = O(T/M) for recursion call stack and maximum depth of path list.
 *
 * 不变量 Invariant: (1) path中的元素和 + remaining = 目标值（目标值守恒）；(2) path中的所有元素索引 ≥ start（确保合法选择顺序）；(3) remaining >= 0（剪枝维护）；(4) 每次remaining == 0时path中的组合必定有效；(5) 回溯后path恢复到进入前状态。
 * | Invariant: (1) sum(path) + remaining = target (target value conservation); (2) all indices in path ≥ start (ensures valid selection order); (3) remaining ≥ 0 (pruning maintenance); (4) whenever remaining == 0, the combination in path is guaranteed valid; (5) after backtracking, path is restored to its pre-entry state.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] candidates, int remain, int start, List<Integer> path,
                          List<List<Integer>> result) {
        if (remain == 0) {
            result.add(new ArrayList<>(path));
            return;
        }

        if (remain < 0) {
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            path.add(candidates[i]);
            backtrack(candidates, remain - candidates[i], i, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

### Problem 17: Combination Sum II
**LeetCode 40 | Medium**

**💡 Key Insight & Why It Works:**

找所有组合使得和等于目标。关键：每个数字**只能用一次**（不像Problem 16），而且数字可能重复。

**怎么做？排序 + 回溯 + 跳过重复**
- 先排序把重复数字聚在一起
- 从当前位置开始，选择一个数字
- 递归时，从下一位置开始（start=i+1，确保每个数字只用一次）
- 跳过重复：如果当前 = 前一个 且 前一个还没被用 → 跳过

**为什么有效？** start=i+1确保每个元素只用一次；排序+跳过规则避免生成重复组合。

**💬 For Interview - Just Say:**
- 先排序
- 回溯：选 → 递归（从下一位置 start=i+1） → 撤销
- 跳过重复：if (nums[i] == nums[i-1] && !used[i-1]) continue

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将数组看作一个候选数字集合，目标是找出所有不重复的组合使其和等于目标值。问题的本质是在决策树中进行剪枝搜索，每个数字只能使用一次，且数组中可能存在重复元素。
 * | Modeling: Model the array as a candidate set where each element can be used at most once. The goal is to find all unique combinations that sum to the target value. The core is a pruned decision tree search where duplicates must be skipped intelligently at the same recursion level.
 *
 * 状态 State: 单个状态 = (start_index, remaining_target, current_path)，其中start_index表示从数组中的哪个位置开始选择，remaining_target是还需要达到的目标和，current_path是当前已选数字的列表。状态空间大小为O(2^N)，因为每个数字有"选"或"不选"两种选择。
 * | State: A single state = (start_index, remaining_target, current_path), where start_index marks the starting position for selection, remaining_target is the remaining sum needed, and current_path stores selected numbers. State space size is O(2^N) since each element has two choices: include or exclude.
 *
 * 辅助数据结构 Aux Structure: 需要使用递归调用栈来维护回溯过程，ArrayList<Integer>来存储当前路径，List<List<Integer>>来存储最终结果，以及一个排序后的候选数组用于快速识别重复元素。
 * | Aux Structure: Requires a recursion call stack for backtracking, ArrayList<Integer> for current path maintenance, List<List<Integer>> for final results, and a sorted candidate array to efficiently identify and skip duplicate elements.
 *
 * 状态转移 Transition: 在每个递归层级中，遍历从start_index开始的所有候选数：如果当前数等于前一个数且不在循环的首次迭代中，则跳过该数（去重）。否则，将其加入路径，递归调用下一层（start_index递进为i+1），回溯时从路径中移除该数。转移公式：(start, remain) → (i+1, remain-candidates[i]) 对于每个有效的i。
 * | Transition: At each recursion level, iterate through all candidates starting from start_index. Skip duplicates: if current == previous and i > start, continue. Otherwise, add candidate to path, recurse with (i+1, remain - candidates[i]), then backtrack. Transition formula: (start, remain) → (i+1, remain - candidates[i]) for each valid index i.
 *
 * 选择算法 Solver: 使用Backtracking (回溯法)算法。理由是：(1) 需要探索所有可能的组合，(2) 需要对无效路径进行剪枝（remain < 0），(3) 需要动态维护当前路径并在决策树中撤销选择，这些都是回溯法的核心特征。相比暴力枚举，回溯通过约束条件大幅减少搜索空间。
 * | Solver: Use Backtracking algorithm. Reasons: (1) explore all possible combinations, (2) prune invalid paths (remain < 0), (3) dynamically maintain current path and undo decisions in the decision tree. This is the core of backtracking. Compared to brute force, backtracking significantly reduces search space through constraint pruning.
 *
 * 复杂度分析: 时间复杂度：O(2^N) 用于决策树的所有节点遍历（最坏情况，每个元素都有选或不选的决定），N是数组长度。每个叶节点（有效组合）需O(N)时间拷贝结果。总时间为O(2^N × N)。空间复杂度：O(N)用于递归调用栈深度，O(N)用于当前路径存储，故总空间为O(N)（不计算结果存储空间）。
 * | Complexity: Time: O(2^N × N), where O(2^N) for decision tree traversal and O(N) for copying each valid combination to results. N is the array length. Space: O(N) for recursion call stack depth plus O(N) for current path storage, totaling O(N) excluding result storage.
 *
 * 不变量 Invariant: 核心不变量：(1) candidates数组始终保持排序状态，确保重复元素相邻便于去重，(2) 在回溯过程中，current_path始终满足 sum(current_path) + remaining_target = target，(3) 访问过的元素索引满足 i >= start_index，保证每个元素最多使用一次，(4) 当处理相邻相同元素时，只在第一次出现时选择（i == start时选择，i > start时跳过），这避免了重复组合的生成。
 * | Invariant: Key invariants: (1) candidates array always sorted to keep duplicates adjacent for easy skipping, (2) during backtracking, sum(current_path) + remaining_target = target, (3) visited indices satisfy i >= start_index ensuring each element used at most once, (4) for adjacent duplicate elements, only the first occurrence is considered (select when i == start, skip when i > start), preventing duplicate combinations in results.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] candidates, int remain, int start, List<Integer> path,
                          List<List<Integer>> result) {
        if (remain == 0) {
            result.add(new ArrayList<>(path));
            return;
        }

        if (remain < 0) {
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }

            path.add(candidates[i]);
            backtrack(candidates, remain - candidates[i], i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

⸻

Pattern 11: Backtracking Subset
State: (index, path)

### Problem 18: Subsets
**LeetCode 78 | Medium**

**💡 Key Insight & Why It Works:**

生成一个列表的所有2^n个子集（包括空集）。比如[1,2]要生成[[],[1],[2],[1,2]]。

**怎么做？回溯，对每个位置选或不选**
- 先把当前路径加入结果（关键：先加，再探索）
- 然后从当前位置开始，对每个元素，选它→递归→撤销
- 最后返回

**为什么有效？** 对每个元素有"选"或"不选"两种选择，递归枚举所有组合。先加入结果确保空集也被包括。

**💬 For Interview - Just Say:**
- 对每个元素：选或不选
- 先把当前子集加入结果
- 然后递归探索：选当前元素 → 从下一位置继续 → 撤销

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 子集生成问题 - 从n个元素的集合中生成所有2^n个子集(包括空集)。问题本质：每个元素有"选"或"不选"两种选择，需要枚举所有组合。
 * | Modeling: Subset generation problem - generate all 2^n subsets from a set of n elements (including the empty set). Problem essence: each element has two choices ("include" or "exclude"), requiring enumeration of all combinations.
 *
 * 状态 State: 单个状态 = (start, path)，其中start是当前考虑的索引，path是当前构建的子集列表。状态空间大小 = 2^n（每个子集对应一个状态），初始状态 = (0, [])，目标状态 = 所有路径长度从0到n的完整子集。
 * | State: Single state = (start, path), where start is the current index being considered and path is the current subset being constructed. State space size = 2^n (each subset corresponds to one state). Initial state = (0, []), target states = all complete subsets with path lengths from 0 to n.
 *
 * 辅助数据结构 Aux Structure: List<Integer> path 维护当前构建的子集；List<List<Integer>> result 收集所有完整子集；int start 控制递归索引以避免重复。
 * | Aux Structure: List<Integer> path maintains the current subset being constructed; List<List<Integer>> result collects all complete subsets; int start controls the recursion index to avoid duplicates.
 *
 * 状态转移 Transition: 回溯三步骤 - (1) 先将当前path添加到result(关键：在探索前添加)；(2) 从start到n遍历每个元素，选中nums[i]后path.add(nums[i])并递归调用backtrack(start+1)；(3) 回溯时path.remove()撤销选择。决策树每个节点代表一个子集，向下延伸代表添加新元素。
 * | Transition: Backtracking three steps - (1) add current path to result first (key: add before exploring); (2) iterate from start to n, for each element nums[i], add to path and recursively call backtrack(start+1); (3) backtrack by removing from path. Each node in the decision tree represents a subset, extending downward represents adding new elements.
 *
 * 选择算法 Solver: 回溯法(Backtracking) - 递归枚举所有子集。理由：需要生成所有2^n个子集，回溯天然支持"选择→探索→撤销"的流程，start参数防止生成重复子集(只向前不向后)，递归深度为n。
 * | Solver: Backtracking algorithm - recursively enumerate all subsets. Reason: need to generate all 2^n subsets; backtracking naturally supports the "choose-explore-unchoose" process; the start parameter prevents generating duplicate subsets (only move forward, not backward); recursion depth is n.
 *
 * 复杂度分析: 时间复杂度 O(n × 2^n) = 生成2^n个子集 × 每个子集平均长度O(n)；空间复杂度 O(n) = 递归栈深度O(n) + path数据结构O(n)，不计result的输出空间。
 * | Complexity: Time complexity O(n × 2^n) = generating 2^n subsets × average subset length O(n); Space complexity O(n) = recursion stack depth O(n) + path data structure O(n), excluding the output space of result.
 *
 * 不变量 Invariant: (1) 任何时刻path中的元素严格按输入顺序排列(有序)；(2) 每个子集在result中仅出现一次(无重复)；(3) 决策树的每条路径从根到叶对应唯一一个子集；(4) 回溯后path状态完全恢复，start+1保证了元素间的独立选择；(5) 空集[]在第一次递归调用就被加入result。
 * | Invariant: (1) Elements in path are always arranged in strict input order (sorted); (2) each subset appears in result only once (no duplicates); (3) each path from root to leaf in the decision tree corresponds to a unique subset; (4) after backtracking, path state is completely restored, start+1 ensures independent choice among elements; (5) the empty set [] is added to result on the first recursive call.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] nums, int start, List<Integer> path,
                          List<List<Integer>> result) {
        result.add(new ArrayList<>(path));

        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);
            backtrack(nums, i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

### Problem 19: Subsets II (with duplicates)
**LeetCode 90 | Medium**

**💡 Key Insight & Why It Works:**

生成含重复数字的列表的所有**唯一**子集。关键是避免生成重复的子集。

**怎么做？排序 + 聪明的跳过**
- 先排序，把重复数字聚在一起
- 先加入当前子集到结果
- 对每个元素，如果它等于前一个 且 前一个还没在本层选过 → 跳过
- 否则选它→递归→撤销

**为什么有效？** 排序把重复元素聚在一起，跳过规则在决策树的同一层避免重复选择，从而避免生成重复子集。

**💬 For Interview - Just Say:**
- 先排序
- 先加入当前子集
- 跳过重复：if (i > start && nums[i] == nums[i-1]) continue
- 然后选元素 → 递归 → 撤销

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 生成所有包含重复元素的数组的不重复子集。将问题转化为：构造一个决策树，在每个位置决定是否选取当前元素，同时处理重复元素的去重。本质上是 2^N 组合的有选择性地生成，通过去重策略过滤重复子集。
 * | Modeling: Generate all unique subsets from an array with duplicate elements. Transform the problem into: construct a decision tree where at each position we decide whether to include the current element, while handling duplicate elements deduplication. Essentially selective generation of 2^N combinations, filtering duplicate subsets through deduplication strategy.
 *
 * 状态 State: 单个状态 = (当前索引 start, 已构建的部分路径 path)。状态空间 = O(N × 2^N)，因为有 N 个索引位置，每个位置对应一棵递归子树。状态代表：从 start 开始的所有可能子集的前缀路径。
 * | State: Individual state = (current index start, partially constructed path). State space = O(N × 2^N), because there are N index positions and each position corresponds to a recursive subtree. State represents: all possible prefix paths of subsets starting from start index.
 *
 * 辅助数据结构 Aux Structure: - List<List<Integer>> result: 存储所有生成的子集 - List<Integer> path: 当前正在构建的路径（候选子集） - 已排序的数组 nums: 排序后的输入数组，使相同元素相邻，便于去重检测
 * | Aux Structure: - List<List<Integer>> result: stores all generated subsets - List<Integer> path: current path being constructed (candidate subset) - Sorted array nums: input array sorted so identical elements are adjacent for duplicate detection
 *
 * 状态转移 Transition: 从 start 位置遍历到数组末尾，对每个元素执行：(1) 跳过重复：若当前元素与前一个相同且 i > start，则 continue（关键剪枝）。(2) 选择：将元素加入 path，递归调用 backtrack(i+1)。(3) 撤销：从 path 移除该元素，回溯。转移条件：nums[i] == nums[i-1] && i > start 时跳过，否则递归探索。
 * | Transition: Iterate from start position to array end, for each element: (1) Skip duplicates: if current element equals previous AND i > start, then continue (key pruning). (2) Choose: add element to path, recursively call backtrack(i+1). (3) Undo: remove element from path, backtrack. Transition condition: skip when nums[i] == nums[i-1] && i > start, otherwise explore recursively.
 *
 * 选择算法 Solver: 回溯法 (Backtracking)，具体是组合生成 (Combination Generation)。理由：(1) 需要枚举所有可能的组合；(2) 去重策略（排序+相邻检测）是迭代回溯的关键；(3) 回溯的撤销机制自然处理选择与不选择；(4) 虽然可用 BFS 或迭代，但 DFS 回溯最直观且不易出错。
 * | Solver: Backtracking algorithm, specifically Combination Generation. Reasons: (1) Need to enumerate all possible combinations; (2) Deduplication strategy (sorting + adjacent detection) is key to iterative backtracking; (3) Backtracking's undo mechanism naturally handles choice and skip; (4) Although BFS or iteration possible, DFS backtracking is most intuitive and error-free.
 *
 * 复杂度分析: 时间复杂度：O(2^N × N)。生成 2^N 个子集，每个子集平均包含 N/2 个元素，加入结果集时需要 O(N) 复制时间。空间复杂度：O(N)（不计输出），用于递归栈深度最大 O(N)，path 数组最大 O(N)。注意：输出空间 O(2^N × N) 不计入常规空间复杂度。
 * | Complexity: Time complexity: O(2^N × N). Generate 2^N subsets, each subset contains average N/2 elements, adding to result requires O(N) copy time. Space complexity: O(N) excluding output, for recursion stack max O(N), path array max O(N). Note: Output space O(2^N × N) not counted in standard space complexity.
 *
 * 不变量 Invariant: (1) 排序不变：输入数组始终保持已排序状态，确保重复元素相邻。(2) 路径不变：path 数组严格按升序构建（因为从 start 递推 start+1）。(3) 去重不变：每个不重复子集恰好被加入一次，因为跳过条件 i > start 保证在同一递推层不会重复处理相同元素。(4) 结果不变：每次递归调用结束后，path 必须恢复到调用前的状态。
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int[] nums, int start, List<Integer> path,
                          List<List<Integer>> result) {
        result.add(new ArrayList<>(path));

        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            path.add(nums[i]);
            backtrack(nums, i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

### Problem 20: Word Search
**LeetCode 79 | Medium**

**💡 Key Insight & Why It Works:**

在2D网格中查找一个单词是否存在。可以上下左右移动，但不能重复访问同一个格子。

**怎么做？回溯 + 4方向DFS**
- 从每个格子开始，尝试匹配单词的第一个字母
- 如果匹配，标记该格子为已访问
- 向4个方向探索，继续匹配单词的下一个字母
- 如果整个单词都匹配了，返回true
- 如果当前路径失败，撤销访问标记，回溯

**为什么有效？** 递归枚举所有可能的路径，同时通过访问标记避免在同一条路径中重复访问。

**💬 For Interview - Just Say:**
- 从每个格子开始，尝试匹配单词
- 如果当前格子 = 单词当前字母，标记访问，向4个方向递归
- 如果访问完了整个单词，返回true
- 撤销访问标记，回溯

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是在给定时间限制下，找到最小的吃香蕉速度。这是典型的"在答案上二分搜索"问题，搜索空间为[1, max(piles)]。Koko需要在h小时内吃完所有香蕉，需要找到满足时间限制的最小速度。
 * | Modeling: The problem essence is finding the minimum eating speed to finish all bananas within a time limit. This is a classic "binary search on answer" pattern where the search space is [1, max(piles)]. Koko must complete all piles within h hours, requiring the minimum speed that satisfies the time constraint.
 * 
 * 二维网格看作一个树形决策空间，每个单元格是一个节点，可以向四个方向探索。问题转化为：在这个树中寻找一条路径，使得沿着路径访问的字符组成目标单词，且不能重复访问同一个单元格。本质是带约束的路径搜索问题。
 * | Map the 2D grid as a tree-shaped decision space where each cell is a node with four directional choices. Transform the problem into: find a path in this tree such that characters along the path form the target word without revisiting any cell. The essence is a constrained path-search problem with backtracking.
 *
 * 状态 State: 单个状态 = (row, col, index) 其中index表示已匹配的word字符位置；状态空间大小 = O(rows × cols × word.length)。状态代表当前在网格的某个位置，已经匹配了word的前index个字符，下一步需要匹配word[index]。
 * | State: Single state = (row, col, index) where index is the matched position in word; state space size = O(rows × cols × word.length). A state represents being at a grid position having matched the first index characters of word, ready to match word[index] next.
 *
 * 辅助数据结构 Aux Structure: boolean[][] visited：标记已访问的单元格（防止同一路径重复访问）；String word：目标单词（参数）；int[][] DIRECTIONS：四个方向常量（上下左右）。递归调用栈隐式维护当前路径信息，无需显式路径数据结构。
 * | Aux Structure: boolean[][] visited: mark visited cells to prevent revisiting in the same path; String word: target word (parameter); int[][] DIRECTIONS: four directional constants (up-down-left-right). The recursion call stack implicitly maintains current path information without explicit path data structure.
 *
 * 状态转移 Transition: 从状态(r,c,idx)出发，检查四个相邻方向(r±1,c)和(r,c±1)；条件：边界内、未访问、且相邻单元格字符等于word[idx]；行为：标记访问、递归进入(r',c',idx+1)、回溯时取消标记（关键）。转移条件：grid[r'][c'] == word[idx]且visited[r'][c']==false。
 * | Transition: From state (r,c,idx), check four adjacent cells at (r±1,c) and (r,c±1); condition: in-bounds, unvisited, and grid[r'][c']==word[idx]; action: mark visited, recurse to (r',c',idx+1), unmark on backtrack (critical). Transition condition ensures character match and no revisiting in current path.
 *
 * 选择算法 Solver: 回溯法（Backtracking）是最佳选择，因为这是一个搜索所有可能路径的问题，且需要在找到答案后立即返回。DFS+回溯能够高效地剪枝不可能的分支。相比BFS，回溯法更简洁且空间效率更高（只需维护单条路径的visited状态）。
 * | Solver: Backtracking is optimal because this is a path-search problem requiring all possibilities to be explored but returning immediately upon finding a solution. DFS with backtracking efficiently prunes impossible branches. Compared to BFS, backtracking is more concise with better space efficiency (maintain visited state for only one path at a time).
 *
 * 复杂度分析: 时间复杂度：O(rows × cols × 4^word.length)。最坏情况下，从每个单元格出发都能找到匹配路径，每步有4个方向选择，递归深度为word.length。实际情况因字符匹配而大幅剪枝。空间复杂度：O(rows × cols + word.length)，visited数组O(rows × cols)，递归栈深度O(word.length)。
 * | Complexity: Time: O(rows × cols × 4^word.length) worst case - starting from each cell with 4-directional branching at each step to depth word.length. Actual pruning from character matching reduces this significantly. Space: O(rows × cols + word.length) - visited array plus recursion depth.
 *
 * 不变量 Invariant: 每次进入递归前，当前单元格已标记为visited且已验证grid[r][c]==word[idx]；递归返回前，必须取消标记(visited[r][c]=false)以允许其他路径使用该单元格；当idx==word.length时找到完整匹配。路径中任意单元格最多被当前路径访问一次，保证不重复使用。
 * | Invariant: Before entering recursion, current cell must be marked visited and grid[r][c]==word[idx] verified; must unmark (visited[r][c]=false) before returning to allow other paths to reuse the cell; complete match found when idx==word.length. Any cell in a path is visited at most once, ensuring no cell reuse within same path.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (dfs(board, word, r, c, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] board, String word, int row, int col, int index) {
        if (index == word.length()) {
            return true;
        }

        if (row < 0 || row >= board.length || col < 0 ||
            col >= board[0].length || board[row][col] != word.charAt(index)) {
            return false;
        }

        char temp = board[row][col];
        board[row][col] = '#';

        boolean found = dfs(board, word, row + 1, col, index + 1) ||
                       dfs(board, word, row - 1, col, index + 1) ||
                       dfs(board, word, row, col + 1, index + 1) ||
                       dfs(board, word, row, col - 1, index + 1);

        board[row][col] = temp;
        return found;
    }
}
```

Day 4
Binary Search
exmaple: 
Minimum Capacity
Minimum Speed
Minimum Time
Maximum Length


⸻

## Day 4: Binary Search Pattern

Pattern 13: Binary Search Answer
State: candidate answer

### Problem 21: Koko Eating Bananas
**LeetCode 875 | Medium**

**💡 Key Insight & Why It Works:**

Koko在h小时内吃完香蕉，每小时固定速度。要找最慢的速度。

**关键：在答案上二分**
- 答案的范围是[1, max(piles)]
- 如果速度s能在时间内吃完，那么任何 > s 的速度也行
- 如果速度s不能在时间内吃完，那么任何 < s 的速度也不行
- 这个单调性允许我们二分搜索

**怎么做？**
- 左右指针：left=1，right=max(piles)
- 对每个中点速度mid，检查是否能在时间内吃完
- 如果行：试更慢的速度（right=mid）
- 如果不行：试更快的速度（left=mid+1）

**💬 For Interview - Just Say:**
- 答案具有单调性：s能完成 → 所有>s都能完成
- 二分搜索答案而不是数据
- 每次检查：计算该速度需要多少小时

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是在给定时间限制下，找到最小的吃香蕉速度。这是典型的"在答案上二分搜索"问题，搜索空间为[1, max(piles)]。Koko需要在h小时内吃完所有香蕉，需要找到满足时间限制的最小速度。
 * | Modeling: The problem essence is finding the minimum eating speed to finish all bananas within a time limit. This is a classic "binary search on answer" pattern where the search space is [1, max(piles)]. Koko must complete all piles within h hours, requiring the minimum speed that satisfies the time constraint.
 *
 * 状态 State: 搜索空间的状态由吃香蕉的速度speed决定，范围为[1, max(piles)]。每个速度对应一个布尔判断：能否在h小时内吃完所有香蕉。状态空间大小为O(max(piles))，通过二分缩减到O(log(max(piles)))。
 * | State: The search space state is determined by the eating speed, ranging from [1, max(piles)]. Each speed corresponds to a boolean decision: can all bananas be finished within h hours? The state space size is O(max(piles)), reduced to O(log(max(piles))) through binary search.
 *
 * 辅助数据结构 Aux Structure: 不需要额外的数据结构来维护状态，仅需要左右指针(left, right)进行二分搜索。可选：预先计算所有pile的最大值来确定搜索边界。需要一个校验函数来判断给定速度是否能在时间限制内完成。
 * | Aux Structure: No additional data structures needed to maintain state, only left and right pointers (left, right) for binary search. Optional: pre-compute the maximum pile to determine search boundaries. Requires a validation function to check if a given speed can complete within the time limit.
 *
 * 状态转移 Transition: 对于每个候选速度mid，调用canFinish()函数计算所需小时数。若hours <= h，表示speed足够快，可尝试更慢的速度(right = mid)；否则需要更快的速度(left = mid + 1)。计算小时数公式：hours += (pile + speed - 1) / speed（向上取整）。
 * | Transition: For each candidate speed mid, invoke canFinish() to calculate required hours. If hours <= h, the speed is sufficient and slower speeds can be tried (right = mid); otherwise faster speed is needed (left = mid + 1). Hour calculation formula: hours += (pile + speed - 1) / speed (ceiling division).
 *
 * 选择算法 Solver: 使用二分搜索算法，因为答案空间具有单调性质：如果速度s能在时间内完成，则任何s' > s也能完成。标准的left < right循环变体，找最小的满足条件的值。时间复杂度O(N × log(max(piles)))：N次pile遍历，log(max(piles))次二分迭代。
 * | Solver: Use binary search algorithm because the answer space has monotonic property: if speed s finishes in time, any s' > s also finishes. Standard left < right loop variant to find the minimum satisfying value. Time complexity O(N × log(max(piles))): N pile traversals per O(log(max(piles))) binary iterations.
 *
 * 复杂度分析: 时间复杂度为O(N × log(max(piles)))，其中N是pile数量，log(max(piles))是二分迭代数。每次canFinish()需要遍历所有piles计算总小时数。空间复杂度为O(1)，仅使用常数额外空间存储指针和临时变量。
 * | Complexity: Time complexity is O(N × log(max(piles))), where N is number of piles and log(max(piles)) is binary search iterations. Each canFinish() requires traversing all piles to calculate total hours. Space complexity is O(1), using only constant extra space for pointers and temporary variables.
 *
 * 不变量 Invariant: 关键不变量：[1, left)范围内的所有速度都能在时间内完成；[right, max(piles)]范围内的所有速度也都能完成。二分搜索维持left <= right的状态，最终left == right时找到最小满足条件的速度。canFinish()函数的单调性保证了二分搜索的正确性。
 * | Invariant: Key invariant: all speeds in range [1, left) can finish within time; all speeds in range [right, max(piles)] can also finish. Binary search maintains left <= right state; when left == right, minimum satisfying speed is found. Monotonicity of canFinish() function guarantees binary search correctness.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = 0;

        for (int pile : piles) {
            right = Math.max(right, pile);
        }

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (canFinish(piles, h, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean canFinish(int[] piles, int h, int speed) {
        long hours = 0;

        for (int pile : piles) {
            hours += (pile + speed - 1) / speed;
        }

        return hours <= h;
    }
}
```

⸻

## Day 5: Heap / Priority Queue Pattern

Pattern 14: Heap Priority Queue
State: (priority, value)

### Problem 22: Top K Largest Elements
**LeetCode 215 | Medium**

**💡 Key Insight & Why It Works:**

找数组中第K大的元素。

**关键：维护一个大小为K的最小堆**
- 想象你有一个堆，最多放K个最大的数字
- 遍历数组，每个数字和堆顶（最小值）比较
- 如果大于堆顶，移除堆顶，加入新数字
- 最后堆顶就是第K大

**为什么有效？** 最小堆保证堆顶是K个元素中最小的（也就是第K大）。任何小于堆顶的数字肯定不在前K大里。

**💬 For Interview - Just Say:**
- 维护K大小的最小堆
- 遍历数组：如果 num > heap.peek()，poll堆顶，offer新数字
- 最后 heap.peek() = 第K大

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 这是一个优先级队列问题，目标是从n个无序数字中找出前K大的元素。通过维护一个大小为K的最小堆，每次新元素与堆顶比较，如果更大则替换堆顶。这样最终堆中保留的K个元素就是最大的K个数。本质是利用堆的有序性质进行增量式的K元素选择。
 * | Modeling: This is a priority queue problem aiming to find the K largest elements from n unsorted numbers. By maintaining a min-heap of size K, each new element is compared with the heap's minimum; if larger, it replaces the top. The heap's ordered property ensures the final K elements are the largest. The essence is incremental K-element selection using heap ordering.
 *
 * 状态 State: 状态定义：(heap_state, heap_size)，其中heap_state表示堆的当前结构，heap_size∈[0,K]表示当前堆中的元素个数。初始状态：heap为空，heap_size=0。关键约束：堆始终保持大小≤K，当超过K时堆顶（最小元素）被移除。状态空间大小为O(K)，因为堆大小不超过K。
 * | State: The state is defined as the K elements and their frequencies maintained in a min-heap. The heap size never exceeds K, with heap space O(K), containing at most min(n, K) elements throughout operations.
 *
 * 辅助数据结构 Aux Structure: 使用PriorityQueue<Integer>作为最小堆来维护K个最大元素。默认PriorityQueue是最小堆，堆顶总是当前K个元素中的最小值。当新元素到来时，只需O(log K)时间进行offer/poll操作。另需int[]或List存储最终结果。核心不变量：heap.size() ≤ K，heap.peek()永远是堆中最小值（也是所有K个元素中的最小值）。
 * | Aux Structure: Use PriorityQueue<Integer> as a min-heap to maintain K largest elements. By default, PriorityQueue is a min-heap where the top element is always the smallest in the K elements. When a new element arrives, offer/poll takes O(log K) time. An int[] or List stores the final result. Core invariant: heap.size() ≤ K, and heap.peek() is always the minimum of the K elements.
 *
 * 状态转移 Transition: 对数组中每个元素num执行转移：(1)如果num > heap.peek()（堆非空时），执行两步：poll移除堆顶最小值，offer加入num到堆中。(2)如果heap.size() < K，直接offer(num)。转移完成后，堆保持有序性：新元素自动上浮或下沉到正确位置。关键：每次转移后堆大小自动调整在[当前元素数最小值, K]范围内。
 * | Transition: For each element num in the array, perform: (1) If num > heap.peek() (when heap is non-empty), poll the minimum and offer num. (2) If heap.size() < K, directly offer(num). After each transition, heap maintains ordering: new elements automatically float up or sink to correct positions. Key: after transition, heap size auto-adjusts within [min(elements seen, K)] range.
 *
 * 选择算法 Solver: 选择算法：基于堆的贪心算法（Heap-based Greedy）。理由：(1)需要快速找出当前最小值（堆顶O(1)）；(2)需要快速插入/删除（offer/poll都是O(log K)）；(3)K值远小于n时，使用K大小的堆比排序整个数组更高效。对比方案：排序整个数组O(n log n)，快速选择O(n)平均。堆方案在K较小时最优。
 * | Solver: Use the min-heap algorithm. First build frequency table with hash map (O(n)), then maintain a K-element min-heap while iterating through frequencies (O(n log K)), finally extract K elements from heap. The min-heap design ensures efficient K-element tracking and fast frequency comparison.
 *
 * 复杂度分析: 时间复杂度：O(n log K)。外层遍历n个元素（O(n)），每个元素进行一次offer操作（O(log K)），偶尔poll操作也是O(log K)。总共最多n次offer + K次poll = n×O(log K)。空间复杂度：O(K)。PriorityQueue最多存储K个元素，最终结果数组大小为K。当K很小（相对n）时，此方案远优于排序。
 * | Complexity: Time complexity O(n log K) where n is array length; frequency table construction O(n), heap operations O(n log K). Space complexity O(n) for frequency table and O(K) for heap, total O(n).
 *
 * 不变量 Invariant: 关键不变量：(1)堆的大小始终≤K；(2)堆顶元素heap.peek()是当前K个元素中的最小值；(3)所有在堆中的元素都≥heap.peek()；(4)已被poll移出的元素都≤当前所有堆中元素；(5)最终堆中的K个元素就是整个数组中最大的K个。维护这些不变量确保算法的正确性：任何时刻如果一个元素小于heap.peek()，它不可能是前K大的数。
 * | Invariant: The heap always contains K elements with the highest frequencies seen so far, with size never exceeding K. The root is always the minimum frequency among the K elements, ensuring any new element entering heap must have strictly higher frequency. The final K elements in heap are the globally top-K frequent elements.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int[] topKLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll();
        }

        return result;
    }
}
```

### Problem 23: Top K Frequent Elements
**LeetCode 347 | Medium**

**💡 Key Insight & Why It Works:**

找频率最高的K个不同元素。

**怎么做？两步走：统计 + 堆**
- 第一步：用HashMap统计每个元素出现的次数
- 第二步：维护一个大小为K的最小堆，按频率排序
- 遍历HashMap的所有元素，用频率和堆顶比较
- 如果频率 > 堆顶频率，poll堆顶，offer新元素
- 最后堆中的K个元素就是最频繁的K个

**为什么有效？** 最小堆按频率排序，堆顶始终是K个元素中频率最低的。任何频率低于堆顶的元素肯定不是最频繁的K个。

**💬 For Interview - Just Say:**
- 用HashMap统计频率
- 维护K大小的最小堆（按频率）
- 遍历HashMap：如果 freq > heap.peek().freq，poll堆顶，offer新元素

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 统计每个元素的出现频率，然后找出频率最高的K个元素。这是一个频率排序问题，需要高效地识别前K大的元素。
 * | Modeling: Count the frequency of each element, then identify the K elements with the highest frequencies. This is a frequency ranking problem requiring efficient identification of top-K elements.
 *
 * 状态 State: 状态定义为小顶堆中维护的K个元素及其频率。堆的大小始终不超过K，堆空间为O(K)，整个操作中堆最多包含min(n, K)个元素。
 * | State: The state is defined as the K elements and their frequencies maintained in a min-heap. The heap size never exceeds K, with heap space O(K), containing at most min(n, K) elements throughout operations.
 *
 * 辅助数据结构 Aux Structure: 使用哈希表存储每个元素的频率（O(n)空间），使用小顶堆（最小堆）维护频率最高的K个元素。小顶堆按频率排序，顶部元素是这K个元素中频率最低的。
 * | Aux Structure: Use a hash table to store the frequency of each element (O(n) space), and use a min-heap to maintain the top-K highest-frequency elements. The min-heap is ordered by frequency, with the root element being the lowest frequency among the K elements.
 *
 * 状态转移 Transition: 遍历频率表，对每个元素：若堆大小小于K则直接加入；若堆大小等于K且元素频率大于堆顶则弹出堆顶并加入新元素；否则跳过。每次加入或删除后都需要调整堆以维护小顶堆性质。
 * | Transition: Iterate through the frequency table. For each element: if heap size < K, add directly; if heap size = K and element frequency > root frequency, remove root and insert new element; otherwise skip. Adjust the heap after each insertion/deletion to maintain the min-heap property.
 *
 * 选择算法 Solver: 采用小顶堆算法。先用哈希表统计频率（O(n)），再遍历频率表维护K元素的小顶堆（O(n log K)），最后从堆中提取K个元素。小顶堆设计保证了高效的K元素追踪和快速的频率比较。
 * | Solver: Use the min-heap algorithm. First build frequency table with hash map (O(n)), then maintain a K-element min-heap while iterating through frequencies (O(n log K)), finally extract K elements from heap. The min-heap design ensures efficient K-element tracking and fast frequency comparison.
 *
 * 复杂度分析: 时间复杂度O(n log K)，其中n是数组长度，建频率表O(n)，堆操作O(n log K)。空间复杂度O(n)用于频率表，O(K)用于堆，总计O(n)。
 * | Complexity Analysis: Time complexity O(n log K) where n is array length; frequency table construction O(n), heap operations O(n log K). Space complexity O(n) for frequency table and O(K) for heap, total O(n).
 *
 * 不变量 Invariant: 堆中始终保持K个频率最高的元素，堆的大小不超过K。堆顶元素始终是当前K个元素中频率最低的，确保任何新元素如果要进堆必须频率严格高于堆顶。最终堆中的K个元素是全局频率最高的K个。
 * | Invariant: The heap always contains K elements with the highest frequencies seen so far, with size never exceeding K. The root is always the minimum frequency among the K elements, ensuring any new element entering heap must have strictly higher frequency. The final K elements in heap are the globally top-K frequent elements.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();

        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> freq.get(a) - freq.get(b));

        for (int num : freq.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll();
        }

        return result;
    }
}
```

### Problem 24: Meeting Rooms
**LeetCode 252 | Easy**

**💡 Key Insight & Why It Works:**

检查一个人能否参加所有会议（时间不冲突）。

**怎么做？排序 + 检查相邻重叠**
- 按会议开始时间排序
- 逐个检查相邻的两个会议
- 如果当前会议开始时间 < 前一个会议结束时间 → 重叠，不行
- 反复检查，如果没有重叠，可以参加所有会议

**为什么有效？** 排序后，如果存在任何重叠，必定表现为相邻的两个会议重叠。无需检查所有对。

**💬 For Interview - Just Say:**
- 排序：按会议开始时间
- 检查相邻会议：if (current.start < previous.end) 返回false
- 如果完整扫描无重叠，返回true

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将所有会议表示为区间集合，问题转化为：判断是否存在任何两个区间重叠（start[i] < end[i-1]）。本质是线性扫描中的无约束冲突检测问题，通过排序将二维问题简化为一维扫描。
 * | Modeling: Represent all meetings as a set of intervals; the problem becomes: determine if any two intervals overlap (start[i] < end[i-1]). The essence is conflict detection in a linear sweep; sorting transforms the 2D problem into a 1D scan.
 *
 * 状态 State: 状态空间为所有会议的排序序列。单个状态是排序后相邻的两个会议对 (meeting[i], meeting[i+1])。关键不变量：排序后只需检查相邻会议是否重叠，无需全局比较。状态空间大小 = O(n) 个相邻对。
 * | State: The state space is the sorted sequence of all meetings. A single state is a pair of adjacent sorted meetings (meeting[i], meeting[i+1]). Key invariant: after sorting, only adjacent meetings need overlap checking; no global comparison needed. State space size = O(n) adjacent pairs.
 *
 * 辅助数据结构 Aux Structure: - int[][] intervals：输入会议列表，包含每个会议的开始和结束时间 - 排序函数：按 start_time 升序排列，使用 Comparator: (a, b) -> a[0] - b[0] - int previous_end：记录前一个会议的结束时间，用于与当前会议的开始时间比较 无需额外数据结构（原地排序）。
 * | Aux Structure: - int[][] intervals: input meeting list with start and end times - Sorting function: sort by start_time in ascending order using Comparator: (a, b) -> a[0] - b[0] - int previous_end: track previous meeting's end time to compare with current meeting's start - No extra data structures needed (in-place sorting).
 *
 * 状态转移 Transition: 1. 排序：按会议开始时间升序排列所有区间 2. 扫描：对每个会议 i（从 i=1 开始）- 检查条件：intervals[i][0] < intervals[i-1][1]（当前开始 < 前一个结束）- 如果真：存在重叠，返回 false - 如果假：无重叠，继续下一个会议 3. 扫描完成无冲突：返回 true
 * | Transition: 1. Sort: arrange all intervals in ascending order by meeting start time 2. Scan: for each meeting i (starting from i=1) - Check: intervals[i][0] < intervals[i-1][1] (current start < previous end) - If true: overlap exists, return false - If false: no overlap, move to next meeting 3. Scan completes without conflict: return true
 *
 * 选择算法 Solver: 使用排序 + 线性扫描的贪心算法。理由：问题不需要全局最优解，只需判断"存在冲突"。排序是关键（O(n log n)），后续单次扫描验证（O(n)）。对比方案：暴力 O(n²) 两两比较不必要；堆解法用于"最少房间数"问题（Problem 38），本题无此需求。
 * | Solver: Use sorting + linear scan with greedy approach. Rationale: the problem doesn't require a global optimum, just existence of conflict. Sorting is the key (O(n log n)), followed by single-pass validation (O(n)). Alternative: brute force O(n²) pairwise comparison is unnecessary; heap solution applies to "minimum rooms" problem (Problem 38), not needed here.
 *
 * 复杂度分析: 时间复杂度：O(n log n) - 排序：O(n log n)（主导操作） - 单次扫描：O(n) - 总计：O(n log n) 空间复杂度：O(1) 或 O(n) - 如果排序使用原地算法（如快排变体）：O(1) 辅助空间 - 如果使用标准库排序（Java Arrays.sort）：O(log n) 递归栈 - 输入数组本身不计入复杂度分析
 * | Complexity: Time: O(n log n) - Sorting: O(n log n) (dominant operation) - Single scan: O(n) - Total: O(n log n) Space: O(1) or O(n) - In-place sorting (e.g., quicksort variant): O(1) auxiliary space - Standard library sort (Java Arrays.sort): O(log n) recursion stack - Input array not counted in complexity analysis
 *
 * 不变量 Invariant: 1. 排序不变量：排序后 intervals[i][0] <= intervals[i+1][0] 对所有 i 成立 2. 扫描不变量：处理完 i 之后，所有 j < i 的会议已检查无冲突 3. 终止条件：扫描到第一个重叠时立即返回 false；完整扫描无冲突返回 true 4. 正确性保证：由于排序，任意两个会议的冲突必定表现为相邻检查（start[i] < end[i-1]）
 * | Invariant: 1. Sorting invariant: after sorting, intervals[i][0] <= intervals[i+1][0] holds for all i 2. Scanning invariant: after processing i, all j < i meetings are verified conflict-free 3. Termination: return false immediately upon first overlap; return true if scan completes without conflict 4. Correctness guarantee: sorting ensures any two meetings' conflict manifests as adjacent check (start[i] < end[i-1])
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i - 1][1]) {
                return false;
            }
        }

        return true;
    }
}
```

### Problem 25: Dijkstra Shortest Path
**Standard Algorithm**

**💡 Key Insight & Why It Works:**

找从一个节点到其他所有节点的最短路径（有权图，权重非负）。

**怎么做？贪心 + 最小堆**
- 用数组记录到每个节点的最短距离，初始都是无穷
- 起点距离设为0，加入优先队列
- 每次从队列弹出距离最小的节点
- 更新它的所有邻接节点的距离
- 如果发现更短的路，更新距离，加入队列

**为什么有效？** 最小堆保证每次处理的都是"当前已知最短距离的节点"。一旦确定最短距离，就不会变化。

**💬 For Interview - Just Say:**
- 用最小堆按距离排序（距离, 节点）
- 弹出距离最小的节点，更新其邻接点
- 如果发现更短路径，更新距离并加入队列

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将图中节点的最短路径问题建模为从源点到各节点的最小距离。通过贪心策略逐步扩展已知最短距离的节点集合。
 * | Modeling: Model the shortest path problem in a graph as finding minimum distances from source to all nodes. Expand the set of nodes with known shortest distances using a greedy strategy.
 *
 * 状态 State: 状态为当前已确定最短距离的节点集合，以及每个节点到源点的最短距离。状态空间大小为O(2^n * n)种距离组合，但实际探索仅需O(n)个确定节点。
 * | State: The state is defined as the K elements and their frequencies maintained in a min-heap. The heap size never exceeds K, with heap space O(K), containing at most min(n, K) elements throughout operations.
 *
 * 辅助数据结构 Aux Structure: 使用最小堆(优先队列)维护未处理节点的当前最小距离，距离数组记录源点到各节点的最短距离，以及已访问集合标记已确定最短路径的节点。
 * | Aux Structure: Use min-heap (priority queue) to track unprocessed nodes by current distance, distance array stores shortest path to each node, and visited set marks nodes with finalized shortest paths.
 *
 * 状态转移 Transition: 从堆中弹出距离最小的未访问节点u，检查其所有邻边(u,v)，若dist[u]+weight(u,v)<dist[v]，更新dist[v]并将v加入堆。重复直至堆空。
 * | Transition: Pop the minimum distance unvisited node u from heap, relax all edges (u,v): if dist[u]+weight(u,v)<dist[v], update dist[v] and insert v into heap. Repeat until heap is empty.
 *
 * 选择算法 Solver: 使用Dijkstra贪心算法配合最小堆实现。堆版本复杂度优于邻接表Bellman-Ford，适用于非负权边的单源最短路径问题。
 * | Solver: Apply Dijkstra greedy algorithm with min-heap implementation. Heap-optimized version outperforms Bellman-Ford on sparse graphs with non-negative weights for single-source shortest paths.
 *
 * 复杂度分析: 时间复杂度为O((V+E)logV)，其中每条边最多被检查一次，每次堆操作为O(logV)。空间复杂度为O(V)用于距离数组、堆和visited集合。
 * | Complexity: Time complexity is O((V+E)logV) where each edge is examined once and heap operations cost O(logV). Space complexity is O(V) for distance array, heap, and visited set.
 *
 * 不变量 Invariant: 已访问节点的距离值为真实最短路径，未访问节点的堆顶距离为当前最小值。堆中每个节点的距离值不小于其真实最短距离。
 * | Invariant: Finalized nodes' distances are true shortest paths. Unvisited nodes' heap-top distance is current minimum. Each node in heap has distance >= its true shortest path distance.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public int[] dijkstra(int n, List<int[]>[] graph, int source) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int cost = curr[1];

            if (cost > dist[node]) {
                continue;
            }

            for (int[] next : graph[node]) {
                int neighbor = next[0];
                int weight = next[1];
                int newCost = cost + weight;

                if (newCost < dist[neighbor]) {
                    dist[neighbor] = newCost;
                    pq.offer(new int[]{neighbor, newCost});
                }
            }
        }

        return dist;
    }
}
```





⸻

## Day 6: Graph Connectivity Pattern

Pattern 15: Connectivity - DFS/BFS
State: node
Questions: Connected? Reachable? Groups? Components? Province? Island?

### Problem 26: Number of Connected Components
**LeetCode 323 | Medium**

**💡 Key Insight & Why It Works:**

数无向图中有多少个连通分量（独立的连通区域）。

**怎么做？DFS计数**
- 扫描每个未访问过的节点
- 对于每个未访问节点，启动DFS
- DFS会访问该节点的所有连通的节点
- 每启动一次DFS = 一个连通分量
- 计数DFS被启动的次数

**为什么有效？** DFS会探索一个连通分量的所有节点，一旦DFS结束，该分量的所有节点都被标记为已访问。下一次启动DFS时，就是新的分量。

**💬 For Interview - Just Say:**
- 扫描每个节点，若未访问，启动DFS，分量数+1
- DFS标记该节点及其所有连通的节点为已访问
- 最后分量数 = DFS启动的次数

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将图的连通性问题转化为并查集模型：每条边的两个端点应在同一集合内；当两个端点已在同一集合时，说明图中存在环，否则需要合并。本质是将n个节点分组，统计最终有多少个独立的连通分量。
 * | Modeling: Transform graph connectivity into Union Find model where each edge unites two components; count final number of disjoint sets. Essence: partition n nodes into groups where nodes in same group are connected, then count total groups.
 *
 * 状态 State: 单个状态 = (parent[]) 数组，记录每个节点的根节点；状态空间大小 = O(2^n)种可能的parent配置。状态代表：当前图的并查集结构，parent[i]值决定节点i属于哪个连通分量。
 * | State: parent[] array where parent[i] indicates root of node i's component. State space = O(2^n) possible parent configurations. Represents: current union-find structure and which component each node belongs to.
 *
 * 辅助数据结构 Aux Structure: int[] parent：存储每个节点的父节点指针，初始parent[i]=i（每个节点自成一个根）；int rank或int size数组（可选）：用于路径压缩或按秩合并的优化。无需额外visited数组或邻接表，只依赖parent数组操作。
 * | Aux Structure: int[] parent to store root pointers (initially parent[i]=i); optional int[] rank/size for union by rank/size optimization. Path compression happens in find() operation. No need for visited array or adjacency list.
 *
 * 状态转移 Transition: 对每条边(u,v)执行union操作：(1)分别找到u和v的根rootU和rootV；(2)若rootU==rootV，说明u、v已在同一分量，跳过（或检测到环）；(3)若rootU!=rootV，合并两个分量：parent[rootU]=rootV（连通分量数减1）。每次成功union操作将两个分量合并为一个。
 * | Transition: For each edge (u,v), find root of u and root of v. If roots equal, u,v already connected (cycle detected). If roots differ, union: set parent[rootU]=rootB to merge components. Each successful union reduces component count by 1. Process all n-1 edges to merge n isolated nodes into 1 tree structure (if connected).
 *
 * 选择算法 Solver: Union Find（并查集）with Path Compression and Union by Rank。理由：O(α(n))≈O(1)均摊时间复杂度远优于DFS/BFS的O(V+E)；对于稀疏图更高效；动态维护连通性的标准算法。关键操作：find(x)实现路径压缩，union(a,b)实现按秩合并。
 * | Solver: Union Find with Path Compression and Union by Rank. Why: O(α(n)) amortized complexity per operation (nearly O(1)), better than DFS/BFS O(V+E); ideal for dynamic connectivity; standard algorithm for maintaining connected components. Key: path compression in find(), union by rank.
 *
 * 复杂度分析: 时间: O(n + m × α(n)) 其中n=节点数，m=边数，α(n)是反阿克曼函数≈O(1)。m条边的union操作 × α(n)单位复杂度，加上初始化n个节点的O(n)。空间: O(n) 用于存储parent数组（以及可选的rank数组）。实际上单次find/union操作都是常数级。
 * | Time: O(n + m × α(n)) where α(n)≈O(1) is inverse Ackermann function. m edge unions × α(n) per operation + O(n) initialization. Space: O(n) for parent array and optional rank array. Each find/union operation effectively O(1).
 *
 * 不变量 Invariant: (1)parent[i]永远指向i所在分量的根节点或沿路径走向根；(2)两个节点在同一分量 ⟺ find(u)==find(v)；(3)初始时分量数=n，每次成功union操作分量数减1；(4)最后分量数=n-成功union的次数 或 =n-min(边数,n-1)；(5)路径压缩保证树高度为O(log n)，使find()时间趋于O(1)。
 * | Invariants: (1) parent[i] points toward root of i's component; (2) find(u)==find(v) ⟺ same component; (3) component count decreases by 1 with each successful union; (4) final_components = n - successful_unions; (5) path compression keeps tree height O(log n), amortizing find() to O(1).
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public int countComponents(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        boolean[] visited = new boolean[n];
        int components = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, graph, visited);
                components++;
            }
        }

        return components;
    }

    private void dfs(int node, List<List<Integer>> graph, boolean[] visited) {
        visited[node] = true;

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited);
            }
        }
    }
}
```

```java
class Solution {

    public int countComponents(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n];
        int components = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                bfs(i, graph, visited);
                components++;
            }
        }

        return components;
    }

    private void bfs(int start, List<List<Integer>> graph, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
    }
}
```

### Problem 27: Redundant Connection
**LeetCode 684 | Medium**

**💡 Key Insight & Why It Works:**

找无向图中造成环的边。一个树有n个节点和n-1条边，这里有n条边，说明多了一条。

**怎么做？Union Find找重复边**
- 初始化：每个节点自成一个分量
- 对每条边，检查两个端点是否已在同一分量
- 如果已在同一分量，这条边会造成环，返回它
- 否则，合并两个分量，继续下一条边

**为什么有效？** Union Find快速检查连通性。一旦发现两个端点已连通，新边就会造成环。

**💬 For Interview - Just Say:**
- 用Union Find维护连通分量
- 对每条边：检查两端点是否已连通
- 已连通 → 这条边造成环，返回它
- 未连通 → 合并分量，继续

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将无向图中的边集合建模为连通性问题。每条边(u,v)尝试连接两个节点；如果边的两端点已在同一连通分量中，该边即为冗余边，会形成环。问题本质是：从有向图中识别并返回第一条造成周期的边。
 * | Modeling: Model the edge set in an undirected graph as a connectivity problem. Each edge (u,v) attempts to connect two nodes; if both endpoints are already in the same connected component, that edge is redundant and creates a cycle. The problem essence is: identify and return the first edge that causes a cycle in the union-find structure.
 *
 * 状态 State: 状态为Union-Find数据结构中的parent[]数组，存储每个节点的父节点指针。状态空间大小为O(n)，其中n是节点数量（最多n-1条边构成树）。状态演化过程：初始parent[i]=i（每个节点自成一个分量）→ 依次处理边 → 执行union操作改变parent指向。
 * | State: The state is the parent[] array in Union-Find, storing parent pointers for each node. State space size is O(n) where n is the number of nodes (at most n-1 edges form a tree). State evolution: initial parent[i]=i (each node in its own component) → process edges sequentially → union operations modify parent pointers.
 *
 * 辅助数据结构 Aux Structure: Union-Find数据结构包含parent[]数组和rank[]数组（可选，用于路径压缩优化）。核心操作：find(x)通过递归追踪父节点，同时进行路径压缩使得树扁平化；union(a,b)将两个分量合并，通过检查find(a)==find(b)判断是否已连通。
 * | Aux Structure: Union-Find structure contains parent[] array and optional rank[] array for path compression optimization. Core operations: find(x) recursively follows parent pointers and performs path compression to flatten the tree; union(a,b) merges two components and checks if find(a)==find(b) to detect if nodes are already connected.
 *
 * 状态转移 Transition: 对每条边(u,v)执行union操作：调用find(u)和find(v)获取两个根节点。若根节点相同，说明u和v已在同一分量，边(u,v)造成环—返回该边。若根节点不同，执行合并操作parent[root_u]=root_v，两个分量统一为一个。边的处理顺序严格遵循输入顺序，确保返回第一条冗余边。
 * | Transition: For each edge (u,v), execute union operation: call find(u) and find(v) to get root nodes of both endpoints. If roots are identical, u and v are already in same component, edge (u,v) creates a cycle—return this edge. If roots differ, merge components by setting parent[root_u]=root_v. Edge processing order strictly follows input order to ensure returning the first redundant edge.
 *
 * 选择算法 Solver: Union-Find（并查集）算法，配合路径压缩和按秩合并优化。选择理由：(1)高效的连通性检查O(α(n))接近常数；(2)支持动态增加边并实时检测环；(3)无需构建完整图结构，直接处理边列表；(4)单遍扫描即可找到答案，避免DFS/BFS的栈空间开销。
 * | Solver: Union-Find algorithm with path compression and union-by-rank optimizations. Reasons for choice: (1) efficient connectivity check O(α(n)) nearly constant; (2) supports dynamic edge addition with real-time cycle detection; (3) no need to build complete graph structure, process edge list directly; (4) single pass finds answer, avoiding stack space overhead of DFS/BFS.
 *
 * 复杂度分析: 时间复杂度O(n×α(n))≈O(n)，其中n是边数，α(n)是反Ackermann函数（实际应用中接近常数）。对每条边执行find和union各一次，每次操作近似O(1)。空间复杂度O(n)用于parent[]和rank[]数组，不需要额外的visited集合或图的邻接表表示。
 * | Complexity: Time complexity O(n×α(n))≈O(n) where n is number of edges and α(n) is inverse Ackermann function (practically constant). Execute find and union once per edge, each operation nearly O(1). Space complexity O(n) for parent[] and rank[] arrays, no need for additional visited set or adjacency list representation.
 *
 * 不变量 Invariant: (1)parent[root]==root恒成立，即根节点必指向自己；(2)对于已处理的每条边，若union返回false，说明两端点已在同一分量，此后两点间有路径保持不变；(3)已合并的分量数=处理边数-重复边数；(4)路径压缩后，任何find(x)操作都会使x逐步靠近根节点，维持树的扁平化特性。
 * | Invariant: (1) parent[root]==root always holds, i.e., root node always points to itself; (2) for each processed edge, if union returns false, both endpoints are already in same component with a path between them thereafter; (3) number of merged components = number of edges processed - number of redundant edges; (4) after path compression, any find(x) operation brings x closer to root, maintaining tree flattening property.
 * ─────────────────────────────────────────────────────────────
 */
class UnionFind {

    int[] parent;

    public UnionFind(int n) {
        parent = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            return false;
        }

        parent[rootA] = rootB;
        return true;
    }
}
```

```java
class Solution {

    public int[] findRedundantConnection(int[][] edges) {
        UnionFind uf = new UnionFind(edges.length);

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            if (!uf.union(u, v)) {
                return edge;
            }
        }

        return new int[0];
    }
}
```

Number of Provinces
```java
class Solution {

    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;

        for (int city = 0; city < n; city++) {
            if (!visited[city]) {
                dfs(city, isConnected, visited);
                provinces++;
            }
        }

        return provinces;
    }

    private void dfs(int city, int[][] graph, boolean[] visited) {
        visited[city] = true;

        for (int neighbor = 0; neighbor < graph.length; neighbor++) {
            if (graph[city][neighbor] == 1 && !visited[neighbor]) {
                dfs(neighbor, graph, visited);
            }
        }
    }
}
```

```java
class Solution {

    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;

        for (int city = 0; city < n; city++) {
            if (!visited[city]) {
                bfs(city, isConnected, visited);
                provinces++;
            }
        }

        return provinces;
    }

    private void bfs(int start, int[][] graph, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int city = queue.poll();

            for (int neighbor = 0; neighbor < graph.length; neighbor++) {
                if (graph[city][neighbor] == 1 && !visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
    }
}
```

### Problem 28: Accounts Merge
**LeetCode 721 | Hard**

**💡 Key Insight & Why It Works:**

多个账户，如果它们共享邮箱，就应该合并。要找出所有应该合并的账户。

**怎么做？Union Find基于邮箱**
- 把邮箱看作节点，同一账户的邮箱都连在一起
- 对每个账户，合并其所有邮箱到第一个邮箱
- 扫描完所有账户后，用邮箱的根分组
- 每组邮箱按字母排序，加上账户名

**为什么有效？** 邮箱是持久化的身份标识。共享邮箱的账户必定属于同一个人，Union Find高效地追踪这种关系。

**💬 For Interview - Just Say:**
- Union Find基于邮箱而非账户号
- 对每个账户，合并其邮箱到第一个邮箱
- 按邮箱的根分组，排序后输出

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 账户合并问题建模为图论问题，其中每个邮箱是一个节点，同一账户内的邮箱之间有无向边。目标是找到所有连通分量（关联的邮箱），并将同组的邮箱按字母顺序排序后与账户名合并输出。
 * | Modeling: Model the account merge problem as a graph where each email is a node and emails in the same account are connected. The goal is to find all connected components of emails and output them grouped by account with sorted emails.
 *
 * 状态 State: 状态定义为(email, parent)对，表示邮箱及其所属的根节点。状态空间大小为O(N×K)，其中N是账户数，K是每个账户的平均邮箱数。初始状态下每个邮箱是自己的根，目标状态是将所有相关邮箱归并到同一根节点下。
 * | State: State is defined as (email, parent) pairs representing each email and its root parent. The state space size is O(N×K) where N is the number of accounts and K is the average number of emails per account. Initially each email is its own root; the goal is to merge related emails under the same root.
 *
 * 辅助数据结构 Aux Structure: 使用HashMap<String, String>作为并查集的parent数组（支持字符串key），HashMap<String, String>记录email到账户名的映射，HashMap<String, TreeSet<String>>按root分组并自动排序邮箱。并查集核心操作：find()执行路径压缩，union()合并两个邮箱所属的连通分量。
 * | Aux Structure: Use HashMap<String, String> as the parent array for Union Find (supporting string keys), HashMap<String, String> to map email to account name, and HashMap<String, TreeSet<String>> to group emails by root with automatic sorting. Core Union Find operations: find() with path compression and union() to merge two connected components.
 *
 * 状态转移 Transition: 对于每个账户，取第一个邮箱作为该组的代表元，将其他所有邮箱与其union，使其parent指向同一个根。转移过程：account[1]作为firstEmail，对于account[i]（i≥2），执行union(firstEmail, account[i])，建立所有同账户邮箱的连接关系。
 * | Transition: For each account, use the first email as the representative and union all other emails with it, making them point to the same root. Process: use account[1] as firstEmail, then for account[i] (i≥2), execute union(firstEmail, account[i]) to connect all emails in the same account.
 *
 * 选择算法 Solver: 使用Union Find算法而非BFS/DFS的原因是：①合并操作高效（接近O(1)），②自然支持连通分量的分组，③避免显式构建邮箱图的邻接表，④String-based实现通过HashMap支持灵活的数据类型。算法流程：初始化→合并→分组→排序输出。
 * | Solver: Choose Union Find because: ①merge operations are efficient (nearly O(1)), ②naturally supports grouping connected components, ③avoids explicitly building email adjacency lists, ④String-based implementation via HashMap supports flexible data types. Process: initialize→merge→group by root→sort and output.
 *
 * 复杂度分析: 时间复杂度O(N×K×α(N×K)+N×K×log(K))，其中N×K为总邮箱数，α为反阿克曼函数（接近常数），union/find操作接近O(1)，最后对每组邮箱排序需O(K×log(K))。空间复杂度O(N×K)存储所有邮箱的parent和映射关系。
 * | Complexity: Time complexity O(N×K×α(N×K)+N×K×log(K)) where N×K is total emails, α is inverse Ackermann function (nearly constant), union/find are nearly O(1), and sorting each group costs O(K×log(K)). Space complexity O(N×K) for storing parent pointers and all mappings.
 *
 * 不变量 Invariant: ①parent数组维持的不变量：find(x)必然返回某个节点作为根，路径压缩保证后续查询更快。②同一账户的所有邮箱必须有相同的根节点（union后保证）。③emailToName映射保证每个邮箱对应唯一账户名（覆盖更新无影响）。④TreeSet自动排序保证输出的邮箱有序，满足题目要求的按字母序排列。
 * | Invariant: ①The parent array maintains that find(x) returns a root node, and path compression ensures faster future queries. ②All emails in the same account must have the same root (guaranteed after union). ③emailToName mapping ensures each email maps to exactly one account name. ④TreeSet auto-sorting ensures output emails are alphabetically ordered as required.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        UnionFind uf = new UnionFind();
        Map<String, String> emailToName = new HashMap<>();

        for (List<String> account : accounts) {
            String name = account.get(0);
            String firstEmail = account.get(1);

            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                emailToName.put(email, name);
                uf.union(firstEmail, email);
            }
        }

        Map<String, TreeSet<String>> groups = new HashMap<>();

        for (String email : emailToName.keySet()) {
            String root = uf.find(email);
            groups.computeIfAbsent(root, k -> new TreeSet<>()).add(email);
        }

        List<List<String>> result = new ArrayList<>();

        for (String root : groups.keySet()) {
            List<String> merged = new ArrayList<>();
            String firstEmail = groups.get(root).first();

            merged.add(emailToName.get(firstEmail));
            merged.addAll(groups.get(root));

            result.add(merged);
        }

        return result;
    }
}
```

```java
class UnionFind {

    Map<String, String> parent = new HashMap<>();

    public String find(String x) {
        parent.putIfAbsent(x, x);

        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x)));
        }

        return parent.get(x);
    }

    public void union(String a, String b) {
        String rootA = find(a);
        String rootB = find(b);

        if (!rootA.equals(rootB)) {
            parent.put(rootA, rootB);
        }
    }
}
```

Pattern 2 Shortest path
State: distance



Pattern 3 Union find
State: parent


⸻

Pattern 17: Topological Sort
State: indegree[]

### Problem 29: Course Schedule
**LeetCode 207 | Medium**

**💡 Key Insight & Why It Works:**

有先决条件的课程。能否完成所有课程？如果存在环（A需要B，B需要A），就无法完成。

**怎么做？拓扑排序检查环**
- 计算每门课程的入度（还需多少前置课程）
- 把入度为0的课程（没有前置条件）加入队列
- 处理一门课程，它的所有依赖课程的入度-1
- 如果依赖课程入度变为0，加入队列
- 如果能处理所有课程，说明无环

**为什么有效？** 如果有环，总有课程入度不能变为0，永远无法处理。无环 ⟺ 能处理全部课程。

**💬 For Interview - Just Say:**
- 计算入度（前置条件数）
- 处理入度为0的课程，减少依赖课程的入度
- 如果处理了全部课程，无环，返回true

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 课程先决条件形成有向图，检测图中是否存在环。问题转化为：拓扑排序可行性检验（所有节点可被排序 ⟺ 无环）。本质：有向无环图(DAG)判定。
 * | Modeling: Course prerequisites form a directed graph; detect if a cycle exists. The problem transforms to: verify if topological sort is feasible (all nodes can be ordered ⟺ no cycle). Essence: directed acyclic graph (DAG) detection.
 *
 * 状态 State: 单个状态 = 课程编号(node)，关键属性 = 入度(indegree)。入度 = 该课程未满足的先决条件数。状态空间大小 = O(V)个课程，每个课程的入度范围[0, V-1]。
 * | State: Each state = course number (node); key attribute = indegree. Indegree = number of unsatisfied prerequisites for that course. State space = O(V) courses with indegree range [0, V-1].
 *
 * 辅助数据结构 Aux Structure: (1) 邻接表graph: 先决条件 → 依赖课程的映射 (2) 入度数组indegree[V]: 每门课程未完成的先决条件计数 (3) 队列queue: 存储入度为0的课程(可立即选修) (4) 计数器count: 记录已选完成的课程数。
 * | Aux Structure: (1) Adjacency list graph: prerequisite → dependent courses mapping (2) indegree array[V]: count of unmet prerequisites per course (3) Queue: stores courses with indegree 0 (can take immediately) (4) Counter: tracks courses completed.
 *
 * 状态转移 Transition: (1) 初始化：遍历prerequisites建图，计算每门课程的入度 (2) 入队：所有入度为0的课程加入队列 (3) 处理：取出一门课程，其所有依赖课程的入度-1 (4) 再入队：如果某依赖课程入度变为0，加入队列。转移本质：逐步消除"完成条件"。
 * | Transition: (1) Initialize: traverse prerequisites to build graph, compute indegree for each course (2) Enqueue: add all courses with indegree 0 (3) Process: dequeue a course, decrement indegree of all dependent courses (4) Re-enqueue: if dependent course indegree becomes 0, enqueue it. Essence: progressively eliminate "completion conditions."
 *
 * 选择算法 Solver: Kahn算法(拓扑排序 + 入度)。理由：直观处理"先决条件"约束，无需DFS递归(避免栈溢出)，O(V+E)遍历保证高效。关键：入度=0 ⟺ 可处理，循环处理直至队列空。
 * | Solver: Kahn's algorithm (topological sort + indegree). Why: intuitively handles "prerequisite" constraints without DFS recursion (avoids stack overflow), O(V+E) traversal ensures efficiency. Key: indegree=0 ⟺ processable; loop until queue empty.
 *
 * 复杂度分析: 时间 O(V+E)：初始化O(V)，遍历E条边各一次，每个顶点处理一次。空间 O(V+E)：邻接表O(V+E)，入度数组O(V)，队列最多O(V)。其中V=课程数，E=先决条件数。
 * | Complexity: Time O(V+E): initialization O(V), each of E edges traversed once, each vertex processed once. Space O(V+E): adjacency list O(V+E), indegree array O(V), queue up to O(V). V=courses, E=prerequisites.
 *
 * 不变量 Invariant: (1) indegree[i]始终≥0，递减（永不增加） (2) count递增：每处理一门课程count++ (3) queue非空 ∧ count<V ⟹ 图存在环 (4) BFS完成后：count==V ⟺ DAG(无环) ⟺ 拓扑排序存在。(5) 已处理课程的所有先决条件已完成(不变式保证正确性)。
 * | Invariant: (1) indegree[i] always ≥ 0, decreasing (never increases) (2) count increments: count++ per course processed (3) queue empty ∧ count<V ⟹ cycle exists (4) After BFS: count==V ⟺ DAG (acyclic) ⟺ topological sort exists (5) All prerequisites of processed courses are complete (invariant ensures correctness).
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        int[] indegree = new int[numCourses];

        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prereq = pre[1];
            graph.get(prereq).add(course);
            indegree[course]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int count = 0;

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            count++;

            for (int next : graph.get(curr)) {
                indegree[next]--;

                if (indegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        return count == numCourses;
    }
}
```

### Problem 30: LC1293 Shortest Path in Grid with Obstacles Elimination
**LeetCode 1293 | Hard**

**💡 Key Insight & Why It Works:**

在网格中找最短路，但可以穿过最多k个障碍。关键：同一位置，不同的"剩余消除次数" = 不同的状态。

**怎么做？多维度状态BFS**
- 状态是(行, 列, 剩余消除次数)
- 比如到达(2,3)且还有3次消除机会 vs 2次消除 = 不同状态
- BFS探索所有状态，找到目标位置的最短路

**为什么有效？** 保留消除次数的信息使我们能发现更优路径。一个位置可能从不同的"消除次数"到达，选择最优的。

**💬 For Interview - Just Say:**
- 状态 = (行, 列, 剩余消除次数)不仅仅位置
- 用BFS，对每个状态尝试4个方向
- 如果遇到障碍且还有消除次数，消除它→转到新状态

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   将网格路径问题转化为三维状态空间，追踪位置(row,col)和剩余消除能力k，求最短路径长度。
 *   Modeling: Transform the grid pathfinding problem into a 3D state space tracking position (row, col) and remaining elimination capability k, seeking minimum path length.
 *
 * 状态 State:
 *   (row, col, remainingK)表示当前行列坐标和剩余的可消除障碍物数量。
 *   State: (row, col, remainingK) represents the current row-column coordinate and the remaining count of eliminable obstacles.
 *
 * 辅助数据结构 Aux Structure:
 *   使用三维visited数组visited[row][col][k]标记(row,col,k)状态是否已访问，队列存储(row,col,k,dist)元组进行BFS。
 *   Aux Structure: Use a 3D visited array visited[row][col][k] to mark whether state (row, col, k) has been visited; queue stores (row, col, k, dist) tuples for BFS traversal.
 *
 * 状态转移 Transition:
 *   从当前状态(r,c,k,d)向四个方向移动：若下一位置无障碍或k>0可消除障碍，则转移到新状态(nr,nc,k',d+1)，其中k'=k(无障碍)或k-1(有障碍被消除)。
 *   Transition: From current state (r, c, k, d), move in four directions: if the next cell has no obstacle or k > 0 can eliminate it, transition to new state (nr, nc, k', d+1), where k' = k (no obstacle) or k - 1 (obstacle eliminated).
 *
 * 选择算法 Solver:
 *   采用BFS算法，从起点(0,0,k)开始，依次处理队列中的状态，首次到达终点(m-1,n-1,任意k)时输出距离。
 *   Solver: Apply BFS algorithm starting from (0, 0, k), process states in queue sequentially, return distance when reaching destination (m-1, n-1, any k) for the first time.
 *
 * 复杂度分析:
 *   时间复杂度O(m*n*k)，空间复杂度O(m*n*k)，其中m、n为网格维度，k为最大消除次数。
 *   Complexity: Time complexity O(m*n*k), space complexity O(m*n*k), where m, n are grid dimensions and k is the maximum elimination count.
 *
 * 不变量 Invariant:
 *   对于任意状态(r,c,k)，若已访问则队列中不再处理该状态；到达目标前，所有已出队状态的最短距离不超过当前处理状态的距离。
 *   Invariant: For any state (r, c, k), if visited, it will not be processed again from queue; before reaching target, shortest distance of all dequeued states does not exceed the distance of currently processing state.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public int shortestPath(int[][] grid, int k) {
        int rows = grid.length;
        int cols = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[rows][cols][k + 1];

        queue.offer(new int[]{0, 0, k});
        visited[0][0][k] = true;

        int steps = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int row = curr[0];
                int col = curr[1];
                int remain = curr[2];

                if (row == rows - 1 && col == cols - 1) {
                    return steps;
                }

                for (int[] dir : dirs) {
                    int nr = row + dir[0];
                    int nc = col + dir[1];

                    if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                        continue;
                    }

                    int nextRemain = remain - grid[nr][nc];

                    if (nextRemain < 0) {
                        continue;
                    }

                    if (visited[nr][nc][nextRemain]) {
                        continue;
                    }

                    visited[nr][nc][nextRemain] = true;
                    queue.offer(new int[]{nr, nc, nextRemain});
                }
            }

            steps++;
        }

        return -1;
    }
}
```

⸻

### Problem 31: LC864 Shortest Path to Get All Keys
**LeetCode 864 | Hard**

**💡 Key Insight & Why It Works:**

在迷宫中收集所有钥匙，有些门需要对应的钥匙才能通过。求最短路。

**关键：位置 + 已有的钥匙集合 = 状态**
- 同一位置，有钥匙集合A vs 集合B = 不同状态
- 用位掩码(6bit)表示6个钥匙是否已收集
- BFS探索所有(位置, 钥匙集合)的组合

**怎么做？**
- 状态：(x, y, keyMask)其中keyMask = 01010101... 表示有哪些钥匙
- 移动时，如果遇到门，检查是否有对应钥匙
- 如果捡到钥匙，更新keyMask
- 当 keyMask = 全1（所有钥匙） 且 在终点 → 返回步数

**💬 For Interview - Just Say:**
- 状态 = (位置, 钥匙位掩码)
- 用BFS，每次检查移动后是否有钥匙或钥匙是否足以过门
- 位掩码更新后是新的状态

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   将迷宫遍历问题转化为状态空间搜索，每个状态包含当前位置与已收集钥匙的集合。
 *   Modeling: Transform maze traversal into state-space search where each state encodes current position and collected keys as a bitmask.
 *
 * 状态 State:
 *   (x, y, keyMask) 其中 (x, y) 为当前位置，keyMask 为 0-5 位二进制数表示 6 把钥匙的收集状态。
 *   State: (x, y, keyMask) where (x, y) is current position and keyMask is a 6-bit integer encoding which of the 6 keys have been collected.
 *
 * 辅助数据结构 Aux Structure:
 *   使用队列存储 BFS 搜索前沿，使用 3D 访问数组 visited[x][y][keyMask] 避免重复探索状态。
 *   Aux Structure: Use queue for BFS frontier and 3D visited array visited[x][y][keyMask] to prevent revisiting the same state.
 *
 * 状态转移 Transition:
 *   从当前状态向四个方向移动，若遇到钥匙则更新 keyMask，若遇到门检查对应钥匙位是否为 1，只有位为 1 时才能通过。
 *   Transition: Move in four directions; upon encountering a key, set the corresponding bit in keyMask; for doors, check if the key bit is set before allowing passage.
 *
 * 选择算法 Solver:
 *   使用 BFS 找最短路径，当 keyMask == 63 (所有 6 把钥匙)时返回步数，保证首次到达目标状态时距离最小。
 *   Solver: Apply BFS to find shortest path; return step count when keyMask == 63 (all 6 keys collected), guaranteeing optimality by BFS property.
 *
 * 复杂度分析:
 *   时间复杂度 O(M × N × 2^6) 其中 M, N 为迷宫大小，2^6 为钥匙状态数；空间复杂度 O(M × N × 2^6)。
 *   Complexity: Time O(M × N × 2^6) where M, N are maze dimensions and 2^6 is key state space; Space O(M × N × 2^6) for visited array.
 *
 * 不变量 Invariant:
 *   BFS 中首次访问的状态距离最小；visited[x][y][keyMask] == true 保证该状态已被处理过，不会重复入队。
 *   Invariant: States are visited in order of increasing distance in BFS; visited[x][y][keyMask] == true ensures a state is processed exactly once and never re-queued.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public int shortestPathAllKeys(String[] grid) {
        int rows = grid.length;
        int cols = grid[0].length();

        int startRow = 0;
        int startCol = 0;
        int totalKeys = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = grid[r].charAt(c);

                if (ch == '@') {
                    startRow = r;
                    startCol = c;
                }

                if (ch >= 'a' && ch <= 'f') {
                    totalKeys++;
                }
            }
        }

        int targetMask = (1 << totalKeys) - 1;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[rows][cols][1 << totalKeys];

        queue.offer(new int[]{startRow, startCol, 0});
        visited[startRow][startCol][0] = true;

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int steps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int row = curr[0];
                int col = curr[1];
                int mask = curr[2];

                if (mask == targetMask) {
                    return steps;
                }

                for (int[] dir : dirs) {
                    int nr = row + dir[0];
                    int nc = col + dir[1];

                    if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                        continue;
                    }

                    char cell = grid[nr].charAt(nc);

                    if (cell == '#') {
                        continue;
                    }

                    int nextMask = mask;

                    if (cell >= 'a' && cell <= 'f') {
                        nextMask |= 1 << (cell - 'a');
                    }

                    if (cell >= 'A' && cell <= 'F') {
                        if ((mask & (1 << (cell - 'A'))) == 0) {
                            continue;
                        }
                    }

                    if (visited[nr][nc][nextMask]) {
                        continue;
                    }

                    visited[nr][nc][nextMask] = true;
                    queue.offer(new int[]{nr, nc, nextMask});
                }
            }

            steps++;
        }

        return -1;
    }
}
```

⸻

### Problem 32: LC847 Shortest Path Visiting All Nodes
**LeetCode 847 | Hard**

**💡 Key Insight & Why It Works:**

访问图中所有节点，求最短路径。（这和Problem 9是同一题）

**关键：位置 + 已访问的节点集合 = 状态**
- 同一个节点，访问过的节点集合不同 = 不同的状态
- 用位掩码(n bit)表示访问过的节点集合
- BFS探索所有(节点, 访问掩码)的组合

**怎么做？**
- 状态：(节点, 访问掩码)
- 可从任何节点开始（尝试所有n个起点）
- 每次移动到邻接节点，更新访问掩码
- 当访问掩码全1（所有节点访问过） → 返回步数

**💬 For Interview - Just Say:**
- 状态 = (节点, 访问位掩码)不仅仅是节点
- BFS从所有可能的起点开始
- 每次尝试移动到邻接节点，更新访问掩码
- 访问掩码全1时返回步数

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   将图遍历问题转化为状态搜索，每个状态由当前节点和已访问节点的二进制掩码组成，目标是找到访问所有节点的最短路径长度。
 *   Modeling: Transform the graph traversal problem into state search where each state consists of the current node and a bitmask representing visited nodes, aiming to find the shortest path length to visit all nodes.
 *
 * 状态 State:
 *   (node, visitedMask) 其中 node ∈ [0, n-1] 表示当前位置，visitedMask 是长度为 n 的二进制数，第 i 位为 1 表示节点 i 已访问。
 *   State: (node, visitedMask) where node ∈ [0, n-1] represents current position and visitedMask is an n-bit binary number with bit i set to 1 if node i has been visited.
 *
 * 辅助数据结构 Aux Structure:
 *   使用队列存储 (node, visitedMask, distance) 三元组进行 BFS；使用集合/哈希表 visited 追踪已探索的状态以避免重复。
 *   Aux Structure: Use a queue to store (node, visitedMask, distance) tuples for BFS; use a set/hash table visited to track explored states and avoid revisiting.
 *
 * 状态转移 Transition:
 *   从状态 (u, mask) 出发，遍历 u 的所有邻接节点 v，若 v 尚未在 mask 中被访问，则转移到新状态 (v, mask | (1 << v))，距离加 1。
 *   Transition: From state (u, mask), iterate all adjacent nodes v of u; if v is not yet visited in mask, transition to new state (v, mask | (1 << v)) with distance incremented by 1.
 *
 * 选择算法 Solver:
 *   采用广度优先搜索 BFS，从每个可能的起点遍历，当到达状态 (node, (1<<n)-1) 时返回距离，BFS 确保首次到达目标时得到最短路径。
 *   Solver: Use breadth-first search (BFS) starting from each possible starting node; return the distance when reaching state (node, (1<<n)-1), guaranteeing the shortest path since BFS explores level by level.
 *
 * 复杂度分析:
 *   时间复杂度 O(n² × 2^n)，因为有 O(n × 2^n) 个状态，每个状态处理 O(n) 条边；空间复杂度 O(n × 2^n) 用于存储访问集合和队列。
 *   Complexity: Time O(n² × 2^n) since there are O(n × 2^n) states and each state processes O(n) edges; Space O(n × 2^n) for visited set and queue storage.
 *
 * 不变量 Invariant:
 *   BFS 访问的状态距离单调非递减；任何已访问的状态 (node, mask) 必然通过最短路径到达；visitedMask 中被置位的节点集合恰好对应已遍历的节点。
 *   Invariant: BFS-visited states have non-decreasing distance; any visited state (node, mask) is reached via the shortest path; the set of nodes with bits set in visitedMask exactly corresponds to traversed nodes.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public int shortestPathLength(int[][] graph) {
        int n = graph.length;
        int targetMask = (1 << n) - 1;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][1 << n];

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

                if (mask == targetMask) {
                    return steps;
                }

                for (int neighbor : graph[node]) {
                    int nextMask = mask | (1 << neighbor);

                    if (!visited[neighbor][nextMask]) {
                        visited[neighbor][nextMask] = true;
                        queue.offer(new int[]{neighbor, nextMask});
                    }
                }
            }

            steps++;
        }

        return -1;
    }
}
```

⸻

BFS + Dynamic State
Race Car
State: (position, speed)



Open Lock：
state: combination


Word Ladder：
state: word



Dijkstra 变体
普通：(distance, node)
升级： (distance, node, k)


Cheapest Flights Within K Stops
State： (city, remainingStops, cost)



DFS State Return
Diameter of Binary Tree
State: node
return: height


Max Path Sum
State: node
Return bestDownPath




Backtracking State
State: path, used[]



Combination:
State: index, path


Subset
State: index, path

N-Queens
State: (row, columns, diag, antiDiag)



Binary Search on Answer
State: candidateAnswer


KOKO
state: speed


Capacity To Ship
state: capacity



Split Array Largest Sum
State: maxsum


Union Find
State: (parent[], rank[])

* Number of Islands II
* Connected Components
* Redundant Connection



Topological Sort
State：indegree


Course Schedule






1
BFS + State
(node, extraInfo)
代表题：
* Race Car
* LC864
* LC847

⸻

2
Dijkstra
代表题：
* Network Delay
* Swim in Rising Water
* Path With Minimum Effort

⸻

3
Backtracking
代表题：
* Permutation
* Combination
* Word Search

⸻

4
Binary Search on Answer
代表题：
* Koko
* Ship Capacity
* Split Array Largest Sum



30秒内说出：
1. State
2. Transition
3. Visited
4. Why this pattern
