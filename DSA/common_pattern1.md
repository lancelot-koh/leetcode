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
/*
 * 怎么想到这个解法？
 * 从任何未访问的陆地开始，用BFS把整个岛屿的所有陆地都"探索"一遍，标记为已访问。
 * 每完成一次BFS，就是找到了一个岛屿。
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
/*
 * 怎么想到这个解法？
 * 多个腐烂的橙子同时向外扩散，就是多源BFS。每一层代表一分钟，当队列空了就是答案。
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
/*
 * 怎么想到这个解法？
 * 用BFS遍历原图，同时在新图中创建对应节点，用HashMap记录原→新的映射避免重复创建。
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
/*
 * 怎么想到这个解法？
 * 树的本质是n个节点n-1条边且连通。先检查边数，再用BFS判断是否连通。
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
/*
 * 怎么想到这个解法？
 * 把单词和转换关系看成图，用BFS从起始单词逐层找相邻单词，第一次到达目标就是最短。
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
/*
 * 怎么想到这个解法？
 * 每个转盘组合是状态，用BFS从0000开始生成邻接状态（每位±1），跳过死锁。
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
/*
 * 怎么想到这个解法？
 * 状态不仅有位置，还有速度！用BFS搜索(位置,速度)对，修剪离目标太远的状态。
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
/*
 * 怎么想到这个解法？
 * 状态是(城市,停靠次数)对，不是只有城市。用优先队列按成本贪心扩展。
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
/*
 * 怎么想到这个解法？
 * 同样是(城市,停靠次数)状态，但用优先队列按成本排序实现Dijkstra，更高效。
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
/*
 * 怎么想到这个解法？
 * 状态是(节点,已访问集合)对，用位掩码编码访问状态。BFS找访问全部节点的最短路。
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
/*
 * 怎么想到这个解法？
 * 递归探索每个陆地的四个方向，标记已访问。每启动一次递归就是找到一个岛屿。
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
/*
 * 怎么想到这个解法？
 * 递归减法：从根往下走，每次减去节点值。到达叶子时检查剩余和是否为0。
 */
```java
/*
 * 怎么想到这个解法？
 * 递归减法：从根往下走，每次减去节点值。到达叶子时检查剩余和是否为0。
 */
```java
/*
 * 怎么想到这个解法？
 * 递归减法：从根往下走，每次减去节点值。到达叶子时检查剩余和是否为0。
 */
```java
/*
 * 怎么想到这个解法？
 * 递归减法：从根往下走，每次减去节点值。到达叶子时检查剩余和是否为0。
 */
```java
/*
 * 怎么想到这个解法？
 * 递归减法：从根往下走，每次减去节点值。到达叶子时检查剩余和是否为0。
 */
```java
/*
 * 怎么想到这个解法？
 * 回溯处理重复：先排序，再在选择时跳过相同的。每个节点最多选一次。
 */
```java
/*
 * 怎么想到这个解法？
 * 回溯搜索给定和的组合：可重复使用，从某个索引开始避免重复。
 */
```java
/*
 * 怎么想到这个解法？
 * 回溯搜索和为target的组合，但每个数只用一次。先排序并检查去重。
 */
```java
/*
 * 怎么想到这个解法？
 * 回溯生成所有子集：对每个数字选要或不要，分别递归。指数复杂度。
 */
```java
/*
 * 怎么想到这个解法？
 * 子集II处理重复：排序后跳过同层重复元素，避免生成重复子集。
 */
```java
/*
 * 怎么想到这个解法？
 * 子集II处理重复：排序后跳过同层重复元素，避免生成重复子集。
 */
```java
/*
 * 怎么想到这个解法？
 * 子集II处理重复：排序后跳过同层重复元素，避免生成重复子集。
 */
```java
/*
 * 怎么想到这个解法？
 * 堆维护top K：扫描数组，小根堆大小为k，保持最大的k个。
 */
```java
/*
 * 怎么想到这个解法？
 * 先计数频率，再用堆找top K频繁。或用桶排序优化。
 */
```java
/*
 * 怎么想到这个解法？
 * 扫描线思想：排序会议，用最小堆追踪房间结束时间，复用或新增房间。
 */
```java
/*
 * 怎么想到这个解法？
 * Dijkstra最短路：优先队列按距离排序，每次取最小距离节点松弛邻接边。
 */
```java
/*
 * 怎么想到这个解法？
 * BFS或DFS计数连通分量：每发现新节点就启动一个搜索，标记已访问。
 */
```java
/*
 * 怎么想到这个解法？
 * BFS或DFS计数连通分量：每发现新节点就启动一个搜索，标记已访问。
 */
```java
/*
 * 怎么想到这个解法？
 * Union Find合并账户：将同名邮箱的账户合并到同一集合，最后输出。
 */
```java
/*
 * 怎么想到这个解法？
 * 拓扑排序：用入度数组，BFS处理入度为0的节点，入度递减后加入队列。
 */
```java
/*
 * 怎么想到这个解法？
 * 拓扑排序：用入度数组，BFS处理入度为0的节点，入度递减后加入队列。
 */
```java
/*
 * 怎么想到这个解法？
 * 拓扑排序：用入度数组，BFS处理入度为0的节点，入度递减后加入队列。
 */
```java

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
