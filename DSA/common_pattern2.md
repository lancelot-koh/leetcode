# common_pattern2 | Problem Library

## 📖 中文 | CHINESE

此文件包含 LeetCode 问题及详细的 7 步框架分析注释。
代码注释为双语：问题标题、框架分析、复杂度分析都有中英文说明。

**English**: This file contains LeetCode problems with detailed 7-step framework annotations.
Code comments are bilingual: problem titles, framework analysis, and complexity analysis have Chinese-English explanations.

---

Pattern	                        Typical State
Monotonic Stack	                stack
Monotonic Queue	                deque
Trie	                        TrieNode
Segment Tree	                range,value
Fenwick Tree	                prefix
Interval Sweep	                time,event
Line Sweep + Heap	            position,activeBuildings
Greedy	                        current best
Two Pointers	                left,right
Fast Slow Pointer	            slow,fast
Bitmask State Compression	    visitedMask
Memoized DFS	                node,cache
DP on Graph	                    node,dp
DP on Sequence	                dp[i]
DP on Subarray	                dp[l][r]
State Machine DP	            state
Multi-source BFS	            queue
Bidirectional BFS	            beginSet,endSet
A* Search	                    cost,heuristic
Simulation	                    system state


⸻

## Google High-Frequency Patterns (Advanced)

Pattern 11: Monotonic Stack
State: stack (indices)
Transition: push / pop when finding greater element

### Problem 29: Daily Temperatures
**LeetCode 739 | Medium**

**💡 Key Insight & Why It Works:**

对于每一天，你要找出下一个更温暖的日子有多少天之后。

**暴力办法：** 对每一天，往后查找下一个更温暖的日子 → O(n²) 太慢

**聪明办法：用单调栈**
- 栈里存的是"还没找到答案的日子的索引"
- 栈从底到顶：温度越来越低（递减）
- 当来到一个新的热日子时：
  - 把栈里所有"比它冷"的日子都弹出来，这些就是它们的答案！
  - 因为这个日子就是它们之后第一个更温暖的

**为什么快？** 每个日子最多进栈一次、出栈一次，所以是 O(n)

**💬 For Interview - Just Say:**
- 用单调递减栈存储日子索引
- 新日子来时，弹出所有比它冷的日子，记录距离
- 栈维持递减，每个元素最多弹出一次

```java
/*
 * 怎么想到这个解法？
 * 单调栈维持递减，新日子来时弹出更冷的，计算距离。
 */
```java
/*
 * 怎么想到这个解法？
 * 单调栈维持递减，新日子来时弹出更冷的，计算距离。
 */
```java
/*
 * 怎么想到这个解法？
 * 单调栈找下一个更大元素：栈存值，当前>栈顶就是答案，弹出并记录。
 */
```java
/*
 * 怎么想到这个解法？
 * 单调栈找柱子的左右边界：递增栈，遇到更矮的弹出并计算面积。
 */
```java
/*
 * 怎么想到这个解法？
 * 双端队列维持递减顺序：移除超出窗口和小于等于当前的，队首=最大值。
 */
```java
/*
 * 怎么想到这个解法？
 * Trie+DFS回溯：构建Trie指导搜索，只走有效前缀，找到单词加入结果。
 */
```java
/*
 * 怎么想到这个解法？
 * Trie预计算：排序产品，每节点存3个建议，查询时直接返回预存列表。
 */
```java
/*
 * 怎么想到这个解法？
 * 合并排序计数逆序对：从右向左，合并时统计右侧小于左侧的个数。
 */
```java
/*
 * 怎么想到这个解法？
 * Fenwick树+坐标压缩：从右向左查询小于当前的已处理元素，再标记。
 */
```java
/*
 * 怎么想到这个解法？
 * 扫描线最小堆：排序会议，用堆追踪最早结束房间，复用时poll后offer。
 */
```java
/*
 * 怎么想到这个解法？
 * 扫描线最大堆：为每个建筑创建进出事件，按x排序，堆顶维持最高高度。
 */
```java
/*
 * 怎么想到这个解法？
 * 扫描线最大堆：为每个建筑创建进出事件，按x排序，堆顶维持最高高度。
 */
```java
/*
 * 怎么想到这个解法？
 * 扫描线最大堆：为每个建筑创建进出事件，按x排序，堆顶维持最高高度。
 */
```java
/*
 * 怎么想到这个解法？
 * 扫描线最大堆：为每个建筑创建进出事件，按x排序，堆顶维持最高高度。
 */
```java
/*
 * 怎么想到这个解法？
 * 扫描线最大堆：为每个建筑创建进出事件，按x排序，堆顶维持最高高度。
 */
```java
/*
 * 怎么想到这个解法？
 * 扫描线最大堆：为每个建筑创建进出事件，按x排序，堆顶维持最高高度。
 */
```java
    public boolean isHappy(int n) {
        Set<Integer> visited = new HashSet<>();

        while (n != 1) {
            if (visited.contains(n)) {
                return false;
            }
            visited.add(n);
            n = getNext(n);
        }
        return true;
    }

    private int getNext(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
}
```

**Alternative (Floyd's Cycle Detection):**

```java
class Solution {
    public boolean isHappy(int n) {
        int slow = n;
        int fast = n;

        do {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        } while (slow != fast);

        return slow == 1;
    }

    private int getNext(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
}
```
⸻

⸻

## Google Hard Patterns (Advanced Modeling)

Pattern 21: Bitmask State Compression
State: (node, visitedMask)

### Problem 46: LC847 - Shortest Path Visiting All Nodes
**LeetCode 847 | Hard**

**💡 Key Insight & Why It Works:**

访问图中所有节点的最短路径。(已在Problem 9更新过)

**状态 = (节点, 已访问位掩码)**
- 同一节点不同访问集合 = 不同状态  
- BFS探索所有状态组合
- 位掩码紧凑表示集合

**怎么做？**
- 可从任何节点开始
- BFS逐层扩展状态
- 访问掩码全1时返回步数

/**
 * 建模 Modeling: 将访问所有节点最短路径问题编码为状态空间搜索，用位掩码表示已访问节点集合。| Modeling: Encode the shortest path visiting all nodes as state-space search where bitmask represents the set of visited nodes.
 * 状态 State: (当前节点, 已访问节点位掩码) 对应从0出发访问掩码中所有节点到达当前节点的最少边数。| State: (current node, visited bitmask) represents minimum edges to reach current node from node 0 visiting all nodes in bitmask.
 * 辅助数据结构 Aux Structure: 队列用于BFS遍历，二维DP表dp[node][mask]记录状态最短距离，避免重复访问。| Aux Structure: Queue for BFS, 2D DP table dp[node][mask] stores shortest distance to each state, prevents revisiting.
 * 状态转移 Transition: 从状态(u, mask)探索邻接节点v，若v未在mask中则转移到(v, mask|2^v)，距离+1。| Transition: From state (u, mask) explore adjacent node v; if v not in mask, transition to (v, mask|(1<<v)) with distance +1.
 * 选择算法 Solver: BFS从(0, 1)出发逐层扩展，首次到达(任意节点, 全1掩码)时返回距离。| Solver: BFS starting from (0, 1) explores level-by-level; return distance when reaching (any node, full bitmask) for first time.
 * 复杂度分析: 时间O(2^n * n^2)遍历所有状态和边，空间O(2^n * n)存储DP表。| Complexity: Time O(2^n * n^2) visiting all states and edges, Space O(2^n * n) for DP table.
 * 不变量 Invariant: 每个状态(u, mask)仅入队一次，保证BFS找到的距离是最短路径。| Invariant: Each state (u, mask) enqueued at most once, ensuring BFS distance is shortest path.
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



### Problem 47: LC864 - Shortest Path to Get All Keys
**LeetCode 864 | Hard**

**💡 Key Insight & Why It Works:**

网格中收集所有钥匙。有钥匙a-f和门A-F。(已在Problem 31更新过)

**状态 = (行, 列, 钥匙位掩码)**
- 位置+已获钥匙集合确定状态
- BFS最短路径
- 最多6个钥匙，位掩码高效

**怎么做？**
- 从起点@开始
- 获得钥匙时更新掩码
- 门需对应钥匙才能通过

/**
 * 建模 Modeling: 在网格中找到收集所有钥匙的最短路径，使用位掩码表示已获得的钥匙状态。| Modeling: Find the shortest path to collect all keys in a grid, using bitmask to represent acquired key states.
 * 状态 State: (行, 列, 钥匙掩码)表示当前位置和已获得的钥匙集合 | State: (row, col, key_bitmask) representing current position and set of acquired keys
 * 辅助数据结构 Aux Structure: 队列存储(行,列,掩码,距离)，访问集合跟踪已访问的状态 | Aux Structure: Queue storing (row, col, bitmask, distance), visited set to track explored states
 * 状态转移 Transition: 从当前状态四向移动，若到达钥匙则更新掩码，若所有钥匙已收集则返回距离 | Transition: Move in four directions from current state, update bitmask when reaching a key, return distance when all keys collected
 * 选择算法 Solver: BFS多源最短路径，逐层探索确保找到最小距离 | Solver: Multi-source BFS shortest path, layer-by-layer exploration to guarantee minimum distance
 * 复杂度分析: 时间O(行*列*2^钥匙数)，空间O(行*列*2^钥匙数)存储状态 | Complexity: Time O(rows*cols*2^num_keys), Space O(rows*cols*2^num_keys) for state storage
 * 不变量 Invariant: 访问过的(位置,掩码)状态不重复探索，BFS保证首次到达目标状态时距离最小 | Invariant: Visited (position, bitmask) states explored only once, BFS guarantees minimum distance when target state first reached
 */
```java
class Solution {
    public int shortestPathAllKeys(String[] grid) {
        int m = grid.length;
        int n = grid[0].length();
        Queue<int[]> queue = new LinkedList<>();
        int keyCount = 0;
        boolean[][][] visited = new boolean[m][n][64];

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                char ch = grid[r].charAt(c);
                if (ch == '@') {
                    queue.offer(new int[]{r, c, 0});
                    visited[r][c][0] = true;
                }
                if (ch >= 'a' && ch <= 'f') {
                    keyCount++;
                }
            }
        }

        int targetMask = (1 << keyCount) - 1;
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

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    char ch = grid[nr].charAt(nc);
                    if (ch == '#') {
                        continue;
                    }

                    int nextMask = mask;
                    if (ch >= 'a' && ch <= 'f') {
                        nextMask |= (1 << (ch - 'a'));
                    }

                    if (ch >= 'A' && ch <= 'F') {
                        if ((mask & (1 << (ch - 'A'))) == 0) {
                            continue;
                        }
                    }

                    if (!visited[nr][nc][nextMask]) {
                        visited[nr][nc][nextMask] = true;
                        queue.offer(new int[]{nr, nc, nextMask});
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

Pattern 22
Memoized DFS
State:
node
cache
代表：
* Longest Increasing Path
```java
class Solution {
    private int[][] memo;
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        memo = new int[m][n];
        int result = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                result = Math.max(result, dfs(matrix, r, c));
            }
        }
        return result;
    }

    private int dfs(int[][] matrix, int row, int col) {
        if (memo[row][col] != 0) {
            return memo[row][col];
        }

        int best = 1;
        for (int[] dir : dirs) {
            int nr = row + dir[0];
            int nc = col + dir[1];

            if (nr < 0 || nr >= matrix.length || nc < 0 || nc >= matrix[0].length) {
                continue;
            }

            if (matrix[nr][nc] > matrix[row][col]) {
                best = Math.max(best, 1 + dfs(matrix, nr, nc));
            }
        }

        memo[row][col] = best;
        return best;
    }
}
```


⸻

### Problem 48: Memoized DFS - Longest Increasing Path in Matrix
**LeetCode 329 | Hard**

**💡 Key Insight & Why It Works:**

矩阵中最长的递增路径。相邻值必须严格递增。

**怎么做？记忆化DFS**
- 从每个格子DFS，记忆化结果
- 向4个方向探索，如果下个值更大则继续
- memo[i][j] = 从(i,j)开始的最长路径

**为什么有效？** 记忆化避免重复计算，每个位置只算一次。

**💬 For Interview - Just Say:**
- 从每个格子DFS
- 向4个方向，值递增时继续
- 记忆化结果

/**
 * 建模 Modeling: 从矩阵中每个单元格出发进行深度优先搜索，找到以该单元格为起点的最长递增路径长度。| Modeling: Perform depth-first search from each cell in the matrix to find the longest increasing path starting from that cell.
 * 状态 State: dp[i][j] 表示从矩阵位置(i,j)出发的最长递增路径长度。| State: dp[i][j] represents the length of the longest increasing path starting from position (i,j) in the matrix.
 * 辅助数据结构 Aux Structure: 使用递忆(记忆化)哈希表或二维数组存储已计算过的状态，避免重复计算。| Aux Structure: Use memoization via hash table or 2D array to store computed states and avoid redundant calculations.
 * 状态转移 Transition: 从(i,j)出发，向四个相邻方向(上下左右)递推，若相邻单元格的值更大则继续搜索，dp[i][j] = 1 + max(相邻更大值的最长路径)。| Transition: From (i,j), explore four adjacent directions; if an adjacent cell has a larger value, continue recursively; dp[i][j] = 1 + max(longest path from larger adjacent cells).
 * 选择算法 Solver: 递推式的记忆化深度优先搜索(Memoized DFS)，从每个单元格触发递推，利用缓存避免重复遍历。| Solver: Memoized DFS with recursive formulation; trigger DFS from each cell and use cache to avoid redundant traversals.
 * 复杂度分析: 时间复杂度O(m*n)，每个单元格最多访问一次；空间复杂度O(m*n)用于缓存和递归栈。| Complexity: Time O(m*n) as each cell is visited at most once; Space O(m*n) for memoization and recursion stack.
 * 不变量 Invariant: 对于任意单元格(i,j)，其dp值是从该位置出发严格递增路径的最大长度，不同起点的搜索互不影响。| Invariant: For any cell (i,j), its dp value is the maximum length of strictly increasing paths starting from that position; searches from different starting points are independent.
 */

⸻

Pattern 23: DP on Graph
State: node, dp

### Problem 49: Course Schedule IV
**LeetCode 1462 | Medium**

**💡 Key Insight & Why It Works:**

课程A是B的前置条件吗？考虑传递关系。

**怎么做？Floyd-Warshall传播可达性**
- 初始化：直接的前置关系
- 对每个中间节点k，检查能否通过k连接i到j
- 最后reachable[i][j] = j是否可从i到达

**为什么有效？** Floyd-Warshall动态规划地构建传递闭包。

**💬 For Interview - Just Say:**
- Floyd-Warshall三层循环
- 更新传递关系：if i→k && k→j then i→j
- 查询reachable数组

```java
class Solution {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        boolean[][] reachable = new boolean[numCourses][numCourses];

        for (int[] edge : prerequisites) {
            reachable[edge[0]][edge[1]] = true;
        }

        for (int k = 0; k < numCourses; k++) {
            for (int i = 0; i < numCourses; i++) {
                for (int j = 0; j < numCourses; j++) {
                    if (reachable[i][k] && reachable[k][j]) {
                        reachable[i][j] = true;
                    }
                }
            }
        }

        List<Boolean> result = new ArrayList<>();
        for (int[] q : queries) {
            result.add(reachable[q[0]][q[1]]);
        }

        return result;
    }
}
```


⸻

Pattern 24
DP on Sequence
State:
dp[i]
代表：
* LIS
```java
/*
 * 怎么想到这个解法?
 * 图的拓扑排序+记忆化：用DFS和缓存判断节点可达性。
 */
class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        // Aux: dp数组，dp[i] = 以i结尾的LIS长度
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int result = 1;

        // Solver: DP遍历
        for (int i = 0; i < n; i++) {
            // Transition: 检查所有之前的位置
            for (int j = 0; j < i; j++) {
                // 状态转移：nums[j] < nums[i]时可以扩展
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            // 更新全局最大值
            result = Math.max(result, dp[i]);
        }
        return result;
    }
}
```

Problem:

Longest increasing subsequence

Model:

Sequence DP

State:

dp[i] = LIS length ending at i

Transition:

if nums[j] < nums[i]

    dp[i] = max(dp[i], dp[j] + 1)

Answer:

max(dp[i])

Complexity:

O(n²)

⸻

Pattern 25
DP on Subarray
State:
dp[l][r]
代表：
* Burst Balloons
```java
class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] arr = new int[n + 2];
        arr[0] = 1;
        arr[n + 1] = 1;

        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];

        for (int len = 2; len < n + 2; len++) {
            for (int left = 0; left + len < n + 2; left++) {
                int right = left + len;

                for (int k = left + 1; k < right; k++) {
                    dp[left][right] = Math.max(
                        dp[left][right],
                        dp[left][k] + dp[k][right] + arr[left] * arr[k] * arr[right]
                    );
                }
            }
        }

        return dp[0][n + 1];
    }
}
```


### Problem 50: Interval DP - Burst Balloons
**LeetCode 312 | Hard**

**💡 Key Insight & Why It Works:**

爆气球获得硬币，顺序影响硬币数（相邻气球相乘）。求最大硬币。

**关键：逆向思维，最后爆哪个球**
- 区间DP：dp[left][right] = 爆left和right间的所有球的最大硬币
- 枚举最后爆的球k
- 当k爆掉时，left和right还在，贡献 = left×k×right
- 加上left→k和k→right的最大硬币

**为什么有效？** 逆向思维避免状态爆炸。

**💬 For Interview - Just Say:**
- 区间DP，逆向考虑
- 枚举最后爆的球k
- 合并两个子区间的答案

```java
/*
 * 怎么想到这个解法?
 * 区间DP：从小区间扩展到大区间，枚举所有可能的分割点。
 */
class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            minPrice = Math.min(minPrice, price);
            maxProfit = Math.max(maxProfit, price - minPrice);
        }

        return maxProfit;
    }
}
```
⸻

Pattern 27: Multi-source BFS
State: queue with multiple starting points

### Problem 52: Rotting Oranges (Multi-source)
**LeetCode 994 | Medium**

**💡 Key Insight & Why It Works:**

腐烂的橙子每分钟感染相邻的新鲜橙子。求腐烂全部所需时间。(已在Problem 2更新过)

**关键：多源BFS，从所有腐烂的橙子同时开始**
- 初始化：所有腐烂的橙子加入队列
- BFS分层处理，每层=1分钟
- 每层更新相邻的新鲜橙子为腐烂
- 队列空时，返回分钟数

**为什么有效？** 多源BFS模拟同时传播。

**💬 For Interview - Just Say:**
- 多源BFS，所有腐烂橙子初始入队
- 每层=1分钟
- 感染相邻橙子

```java
/*
 * 怎么想到这个解法?
 * 多源BFS：橙子同时腐烂，每层代表一分钟。
 */
class State {
    int row;
    int col;
    int g;
    int f;

    State(int row, int col, int g, int f) {
        this.row = row;
        this.col = col;
        this.g = g;
        this.f = f;
    }
}
```


Google偶尔出现。

⸻

Pattern 30: Simulation
State: system state
Transition: follow defined rules

### Problem 56: Design Snake Game
**LeetCode 353 | Medium**

**💡 Key Insight & Why It Works:**

模拟贪吃蛇游戏：移动→碰撞→吃食物。

**怎么做？状态机模拟**
- 双端队列存蛇身(头→尾)
- 集合存蛇的所有位置(快速碰撞检测)
- 每次移动：计算新头 → 检查碰撞 → 检查食物 → 更新蛇身
- 碰撞=游戏结束，吃食物=蛇长+1

**为什么有效？** 清晰的状态转移，Deque+Set高效。

**💬 For Interview - Just Say:**
- Deque维护蛇身
- Set快速检测碰撞
- 移动：加头→检查食物→移除尾或保留

/**
 * 建模 Modeling: 用队列模拟蛇身体，用集合跟踪蛇身位置，模拟蛇的移动和进食过程 | Modeling: Use a queue to simulate the snake's body, use a set to track occupied positions, and simulate movement and food consumption
 * 状态 State: 蛇身位置列表、蛇头坐标、当前方向、食物位置、游戏结果 | State: Snake body positions, head coordinates, current direction, food location, game outcome
 * 辅助数据结构 Aux Structure: 双端队列存储蛇身、集合存储蛇身坐标快速查询碰撞 | Aux Structure: Deque for snake body, set for O(1) collision detection
 * 状态转移 Transition: 根据方向计算新头位置，检查碰撞和食物，更新蛇身（吃食物加头，否则删尾） | Transition: Calculate new head position from direction, check collisions and food, update body by adding head (and removing tail if no food)
 * 选择算法 Solver: 模拟驱动，逐次处理移动命令，实时更新状态 | Solver: Simulation-driven, process movement commands sequentially and update state in real-time
 * 复杂度分析: 时间O(1)每次移动，空间O(n)存储蛇身 | Complexity: O(1) per move operation, O(n) space for snake body storage
 * 不变量 Invariant: 蛇身无自交，蛇头始终在游戏区域内或碰撞结束游戏 | Invariant: No self-intersection in snake body, head collision triggers game end, snake boundaries are respected
 */
```java
/*
 * 怎么想到这个解法？
 * 双端队列模拟蛇身，集合快速检测碰撞。每次移动：计算新头→检查碰撞→检查食物→更新蛇身。
 */
```

## Summary: Pattern 2 Problems

**Covered 56+ Problems** organized by 30 patterns:
- Monotonic Stack (3 problems)
- Monotonic Queue (1 problem)  
- Trie (2 problems)
- Range Query / Segment Tree (3 problems)
- Interval Sweep (2 problems)
- Greedy (2 problems)
- Two Pointers (2 problems)
- Fast Slow Pointer (2 problems)
- Bitmask State Compression (2 problems)
- Memoized DFS (1 problem)
- DP on Graph (1 problem)
- Interval DP (1 problem)
- State Machine DP (1 problem)
- Multi-source BFS (2 problems)
- Bidirectional BFS (1 problem)
- A* Search (1 problem)
- Simulation (1 problem)
```java
class SnakeGame {
    private int width;
    private int height;
    private int[][] food;
    private int foodIndex;
    private Deque<Integer> snake;
    private Set<Integer> occupied;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        snake = new LinkedList<>();
        occupied = new HashSet<>();
        snake.offerFirst(0);
        occupied.add(0);
        foodIndex = 0;
    }

    public int move(String direction) {
        int head = snake.peekFirst();
        int row = head / width;
        int col = head % width;

        switch (direction) {
            case "U":
                row--;
                break;
            case "D":
                row++;
                break;
            case "L":
                col--;
                break;
            case "R":
                col++;
                break;
        }

        if (row < 0 || row >= height || col < 0 || col >= width) {
            return -1;
        }

        int newHead = row * width + col;
        boolean eatFood = foodIndex < food.length && row == food[foodIndex][0] && col == food[foodIndex][1];

        if (!eatFood) {
            int tail = snake.pollLast();
            occupied.remove(tail);
        }

        if (occupied.contains(newHead)) {
            return -1;
        }

        snake.offerFirst(newHead);
        occupied.add(newHead);

        if (eatFood) {
            foodIndex++;
        }

        return snake.size() - 1;
    }
}
```

```java
class Solution {
    public int robotSim(int[] commands, int[][] obstacles) {
        Set<String> obstacleSet = new HashSet<>();

        for (int[] obs : obstacles) {
            obstacleSet.add(obs[0] + "," + obs[1]);
        }

        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int dir = 0;
        int x = 0;
        int y = 0;
        int maxDistance = 0;

        for (int command : commands) {
            if (command == -1) {
                dir = (dir + 1) % 4;
            } else if (command == -2) {
                dir = (dir + 3) % 4;
            } else {
                for (int step = 0; step < command; step++) {
                    int nx = x + dirs[dir][0];
                    int ny = y + dirs[dir][1];
                    String key = nx + "," + ny;

                    if (obstacleSet.contains(key)) {
                        break;
                    }

                    x = nx;
                    y = ny;
                    maxDistance = Math.max(maxDistance, x * x + y * y);
                }
            }
        }

        return maxDistance;
    }
}
```


⸻

对你最重要的
如果从 Google 最近几年 L5/L6 的实际面试来看。
我会这样分：
Tier 1（必须熟）
* BFS
* DFS
* Backtracking
* Binary Search
* Heap
* Dijkstra
* Sliding Window
* Union Find
* Topological Sort
* Two Pointers

⸻

Tier 2（建议熟）
* Monotonic Stack
* Trie
* Greedy
* Prefix Sum
* Fast Slow Pointer

⸻

Tier 3（见过即可）
* Segment Tree
* Fenwick Tree
* Sweep Line
* A*
* Advanced DP
