# 10 核心 Pattern 面试准备

**简化版准备清单：10 个高频核心 Pattern，每个 3 道代表题**

---

## 1. Tree DFS (树形搜索)

**核心概念:** 返回值定义 | 前序/中序/后序 | 单路径/全路径

### 代表题

#### LeetCode 104: Maximum Depth of Binary Tree
**思路:** 递归求左右子树最大深度，取较大值 + 1

```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }
}
```
- **复杂度:** O(n) 时间 | O(h) 空间（递归栈深度）
- **关键点:** 空节点深度为 0 | 返回值明确定义

#### LeetCode 437: Path Sum III
**思路:** 每个节点作为路径起点，向下找和为 target 的路径

```java
class Solution {
    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) return 0;
        // 从当前节点开始的路径数 + 左子树 + 右子树
        return countPath(root, targetSum) + 
               pathSum(root.left, targetSum) + 
               pathSum(root.right, targetSum);
    }
    
    private int countPath(TreeNode node, long target) {
        if (node == null) return 0;
        int count = 0;
        // 当前路径满足条件
        if (node.val == target) count = 1;
        // 继续向下，减少目标值
        count += countPath(node.left, target - node.val);
        count += countPath(node.right, target - node.val);
        return count;
    }
}
```
- **复杂度:** O(n²) 时间最坏 | O(h) 空间
- **关键点:** 路径不必从根开始 | 用 long 避免溢出

#### LeetCode 236: Lowest Common Ancestor of a Binary Tree
**思路:** 在左右子树分别找 p、q，若都找到则当前节点是 LCA

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        // 若根是 p 或 q，直接返回
        if (root == p || root == q) return root;
        
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        
        // 左右都找到，当前节点是 LCA
        if (left != null && right != null) return root;
        // 只有一侧找到，返回那一侧
        return left != null ? left : right;
    }
}
```
- **复杂度:** O(n) 时间 | O(h) 空间
- **关键点:** 后序遍历（先左右再中） | 分情况讨论返回值

---

## 2. Graph BFS (图的最短路)

**核心概念:** 最短步数 | 多源 BFS | 隐式图

### 代表题

#### LeetCode 127: Word Ladder
**思路:** 隐式图 BFS，每次变换一个字母，找最短路

```java
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return 0;
        
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        int steps = 1;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String word = queue.poll();
                if (word.equals(endWord)) return steps;
                
                // 遍历所有可能的下一个单词
                for (String neighbor : getNeighbors(word, wordSet)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
            steps++;
        }
        return 0;
    }
    
    private List<String> getNeighbors(String word, Set<String> wordSet) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char old = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                chars[i] = c;
                String newWord = new String(chars);
                if (wordSet.contains(newWord)) {
                    neighbors.add(newWord);
                }
            }
            chars[i] = old;
        }
        return neighbors;
    }
}
```
- **复杂度:** O(L × 26 × N) | O(N) 空间（L=单词长，N=词表大小）
- **关键点:** 每个单词变换一个字母，生成邻接表 | 按层遍历统计距离

#### LeetCode 286: Walls and Gates
**思路:** 多源 BFS，从所有门同时出发，找最短距离

```java
class Solution {
    public void wallsAndGates(int[][] rooms) {
        if (rooms == null || rooms.length == 0) return;
        Queue<int[]> queue = new LinkedList<>();
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        
        // 多源初始化：把所有门加入队列
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[0].length; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        
        // BFS 扩展
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int x = cell[0], y = cell[1];
            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                // 只更新未访问过的空房间
                if (nx >= 0 && nx < rooms.length && ny >= 0 && ny < rooms[0].length 
                    && rooms[nx][ny] == Integer.MAX_VALUE) {
                    rooms[nx][ny] = rooms[x][y] + 1;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
    }
}
```
- **复杂度:** O(m×n) 时间 | O(m×n) 空间
- **关键点:** 多源初始化很重要 | 不需要额外 visited 数组（用 MAX_VALUE 判断）

#### LeetCode 317: Shortest Distance from All Buildings
**思路:** 每栋建筑做 BFS 源点，找被所有建筑都能到达的最短距离点

```java
class Solution {
    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0) return -1;
        int m = grid.length, n = grid[0].length;
        int[][] distance = new int[m][n];
        int[][] count = new int[m][n];
        int buildings = 0;
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        
        // 从每栋建筑做 BFS
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    buildings++;
                    bfs(grid, i, j, distance, count, dirs);
                }
            }
        }
        
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 能被所有建筑到达且距离最小
                if (count[i][j] == buildings && distance[i][j] < result) {
                    result = distance[i][j];
                }
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }
    
    private void bfs(int[][] grid, int row, int col, int[][] distance, int[][] count, int[][] dirs) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{row, col});
        visited[row][col] = true;
        int dist = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            dist++;
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                for (int[] dir : dirs) {
                    int nx = cell[0] + dir[0];
                    int ny = cell[1] + dir[1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny] && grid[nx][ny] == 0) {
                        visited[nx][ny] = true;
                        distance[nx][ny] += dist;
                        count[nx][ny]++;
                        queue.offer(new int[]{nx, ny});
                    }
                }
            }
        }
    }
}
```
- **复杂度:** O(m×n×buildings) 时间 | O(m×n) 空间
- **关键点:** 累加距离，统计被到达次数 | 用 count 数组判断是否所有建筑都能到达

---

## 3. Dijkstra (带权最短路)

**核心概念:** 优先队列 | 松弛条件 | 最短路保证

### 代表题

#### LeetCode 743: Network Delay Time
**思路:** Dijkstra 求最短路，找到达所有节点时间最长的那个

```java
class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        // 构建邻接表
        List<int[]>[] graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] time : times) {
            graph[time[0]].add(new int[]{time[1], time[2]});
        }
        
        // Dijkstra：优先队列（距离，节点）
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        pq.offer(new int[]{0, k});
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], u = curr[1];
            
            if (d > dist[u]) continue; // 跳过已处理节点
            
            for (int[] edge : graph[u]) {
                int v = edge[0], w = edge[1];
                if (dist[u] + w < dist[v]) { // 松弛
                    dist[v] = dist[u] + w;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }
        
        int result = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            result = Math.max(result, dist[i]);
        }
        return result;
    }
}
```
- **复杂度:** O((V+E)logV) 时间 | O(V) 空间
- **关键点:** 优先队列按距离排序 | 松弛条件 dist[u] + w < dist[v]

#### LeetCode 882: Reachable Nodes In Subdivided Graph
**思路:** Dijkstra 计算最短路，计数能到达的所有节点（包含边上）

```java
class Solution {
    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        // 构建邻接表，存 (目标节点, 边上节点数)
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new HashMap<>());
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], cnt = edge[2];
            graph.get(u).put(v, cnt);
            graph.get(v).put(u, cnt);
        }
        
        // Dijkstra：计算到达每个节点的最短边数
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        Map<Integer, Integer> dist = new HashMap<>();
        dist.put(0, 0);
        pq.offer(new int[]{0, 0});
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], u = curr[1];
            
            if (d > dist.getOrDefault(u, Integer.MAX_VALUE)) continue;
            
            for (int v : graph.get(u).keySet()) {
                int cnt = graph.get(u).get(v);
                int newDist = d + cnt + 1; // 边上所有节点 + 目标节点
                if (newDist < dist.getOrDefault(v, Integer.MAX_VALUE) && newDist <= maxMoves) {
                    dist.put(v, newDist);
                    pq.offer(new int[]{newDist, v});
                }
            }
        }
        
        int result = dist.size(); // 能到达的节点数
        // 加上边上能到达的节点
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], cnt = edge[2];
            int fromU = dist.getOrDefault(u, Integer.MAX_VALUE);
            int fromV = dist.getOrDefault(v, Integer.MAX_VALUE);
            int minDist = Math.min(fromU, fromV);
            if (minDist < Integer.MAX_VALUE) {
                result += Math.min(cnt, maxMoves - minDist);
            }
        }
        return result;
    }
}
```
- **复杂度:** O((V+E)logV) 时间 | O(V+E) 空间
- **关键点:** 记录每个节点的最短到达移动数 | 统计边上能到达的节点

#### LeetCode 1631: Path With Minimum Effort
**思路:** Dijkstra 求最小最大值路径（修改比较标准：按最大高度差排序）

```java
class Solution {
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0, 0}); // (最大高度差, x, y)
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int effort = curr[0], x = curr[1], y = curr[2];
            
            if (effort > dist[x][y]) continue;
            
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    int newEffort = Math.max(effort, Math.abs(heights[nx][ny] - heights[x][y]));
                    if (newEffort < dist[nx][ny]) {
                        dist[nx][ny] = newEffort;
                        pq.offer(new int[]{newEffort, nx, ny});
                    }
                }
            }
        }
        return dist[m-1][n-1];
    }
}
```
- **复杂度:** O(m×n×log(m×n)) 时间 | O(m×n) 空间
- **关键点:** 修改距离定义为最大高度差 | 松弛条件取 max

---

## 4. Topological Sort (拓扑排序)

**核心概念:** 依赖关系 | DAG | 入度/DFS 两种方法

### 代表题

#### LeetCode 207: Course Schedule
**思路:** 入度法检测有向图是否存在环

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 构建邻接表和入度数组
        List<Integer>[] graph = new ArrayList[numCourses];
        int[] inDegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] pre : prerequisites) {
            int course = pre[0], prereq = pre[1];
            graph[prereq].add(course);
            inDegree[course]++;
        }
        
        // Kahn 算法：入度为 0 的节点入队
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int count = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            count++;
            // 移除边，更新邻接节点入度
            for (int next : graph[course]) {
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        
        return count == numCourses; // 所有课程都被处理则无环
    }
}
```
- **复杂度:** O(V+E) 时间 | O(V+E) 空间
- **关键点:** 入度为 0 表示无依赖 | 处理完全部节点才能确认无环

#### LeetCode 210: Course Schedule II
**思路:** 拓扑排序返回顺序列表

```java
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new ArrayList[numCourses];
        int[] inDegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] pre : prerequisites) {
            graph[pre[1]].add(pre[0]);
            inDegree[pre[0]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int[] result = new int[numCourses];
        int idx = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            result[idx++] = course;
            for (int next : graph[course]) {
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        
        return idx == numCourses ? result : new int[0];
    }
}
```
- **复杂度:** O(V+E) 时间 | O(V+E) 空间
- **关键点:** 按拓扑顺序加入结果数组 | 无环则返回完整序列

#### LeetCode 269: Alien Dictionary
**思路:** 从单词顺序推断字母顺序，再拓扑排序

```java
class Solution {
    public String alienOrder(String[] words) {
        // 步骤 1：构建图
        Map<Character, Set<Integer>> graph = new HashMap<>();
        Map<Character, Integer> inDegree = new HashMap<>();
        
        // 初始化所有字符
        for (String word : words) {
            for (char c : word.toCharArray()) {
                if (!graph.containsKey(c)) {
                    graph.put(c, new HashSet<>());
                }
                if (!inDegree.containsKey(c)) {
                    inDegree.put(c, 0);
                }
            }
        }
        
        // 步骤 2：比较相邻单词找出边
        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i], w2 = words[i + 1];
            int minLen = Math.min(w1.length(), w2.length());
            for (int j = 0; j < minLen; j++) {
                char c1 = w1.charAt(j), c2 = w2.charAt(j);
                if (c1 != c2) {
                    if (!graph.get(c1).contains(c2)) {
                        graph.get(c1).add((int)c2);
                        inDegree.put(c2, inDegree.get(c2) + 1);
                    }
                    break;
                }
            }
        }
        
        // 步骤 3：拓扑排序
        Queue<Character> queue = new LinkedList<>();
        for (char c : inDegree.keySet()) {
            if (inDegree.get(c) == 0) {
                queue.offer(c);
            }
        }
        
        StringBuilder result = new StringBuilder();
        while (!queue.isEmpty()) {
            char c = queue.poll();
            result.append(c);
            for (int next : graph.get(c)) {
                char nextChar = (char)next;
                inDegree.put(nextChar, inDegree.get(nextChar) - 1);
                if (inDegree.get(nextChar) == 0) {
                    queue.offer(nextChar);
                }
            }
        }
        
        return result.length() == inDegree.size() ? result.toString() : "";
    }
}
```
- **复杂度:** O(N×L + V + E) 时间 | O(V + E) 空间（N=单词数，L=平均长度）
- **关键点:** 相邻单词第一个不同字符确定顺序 | 无环才有有效顺序

---

## 5. Sliding Window (滑动窗口)

**核心概念:** 左右指针 | 扩展/收缩 | At Most K trick

### 代表题

#### LeetCode 3: Longest Substring Without Repeating Characters
**思路:** 右指针扩展，遇到重复字符左指针收缩

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        
        Map<Character, Integer> charIndex = new HashMap<>();
        int left = 0, maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            // 若字符在窗口内出现过，移动左指针
            if (charIndex.containsKey(c) && charIndex.get(c) >= left) {
                left = charIndex.get(c) + 1;
            }
            charIndex.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
}
```
- **复杂度:** O(n) 时间 | O(min(n, 26)) 空间
- **关键点:** 哈希表记录字符最后位置 | left 跳过重复字符

#### LeetCode 76: Minimum Window Substring
**思路:** 右指针扩展直到包含所有字符，左指针收缩找最小窗口

```java
class Solution {
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";
        
        // 统计 t 中字符频率
        Map<Character, Integer> tMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            tMap.put(c, tMap.getOrDefault(c, 0) + 1);
        }
        
        int left = 0, minLen = Integer.MAX_VALUE, minStart = 0;
        int formed = 0; // 窗口中满足的字符种类数
        Map<Character, Integer> windowMap = new HashMap<>();
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);
            
            // 当这个字符的频率等于 t 中的频率时，formed++
            if (tMap.containsKey(c) && windowMap.get(c).equals(tMap.get(c))) {
                formed++;
            }
            
            // 尝试收缩窗口
            while (left <= right && formed == tMap.size()) {
                c = s.charAt(left);
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }
                
                windowMap.put(c, windowMap.get(c) - 1);
                if (tMap.containsKey(c) && windowMap.get(c) < tMap.get(c)) {
                    formed--;
                }
                left++;
            }
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }
}
```
- **复杂度:** O(n) 时间 | O(1) 空间（最多 26 个字母）
- **关键点:** formed 变量判断是否包含所有字符 | left 右移时要更新 formed

#### LeetCode 340: Longest Substring with At Most K Distinct Characters
**思路:** 维护最多 K 个不同字符的窗口，右指针扩展，超过 K 个时左指针收缩

```java
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0) return 0;
        
        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0, maxLen = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
            
            // 若不同字符超过 k，左指针收缩
            while (charCount.size() > k) {
                char leftChar = s.charAt(left);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                if (charCount.get(leftChar) == 0) {
                    charCount.remove(leftChar);
                }
                left++;
            }
            
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
}
```
- **复杂度:** O(n) 时间 | O(k) 空间
- **关键点:** 哈希表维护窗口内字符频率 | 移除频率为 0 的字符

---

## 6. Prefix Sum + HashMap (前缀和 + 哈希表)

**核心概念:** target = sum[j] - sum[i] | 连续和问题

### 代表题

#### LeetCode 560: Subarray Sum Equals K
**思路:** 前缀和 + 哈希表，当 currSum - k 在哈希表中说明找到满足条件的子数组

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> sumCount = new HashMap<>();
        sumCount.put(0, 1); // 初始化：和为 0 的前缀出现 1 次
        
        int currSum = 0, count = 0;
        for (int num : nums) {
            currSum += num;
            // 若 currSum - k 存在，说明有子数组和为 k
            if (sumCount.containsKey(currSum - k)) {
                count += sumCount.get(currSum - k);
            }
            sumCount.put(currSum, sumCount.getOrDefault(currSum, 0) + 1);
        }
        return count;
    }
}
```
- **复杂度:** O(n) 时间 | O(n) 空间
- **关键点:** sumCount.put(0, 1) 初始化很重要 | currSum - k 是关键公式

#### LeetCode 523: Continuous Subarray Sum
**思路:** 前缀和模 m 后使用哈希表，找是否存在长度 >= 2 的子数组

```java
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null || nums.length < 2) return false;
        
        Map<Integer, Integer> sumMod = new HashMap<>();
        sumMod.put(0, -1); // 前缀和为 0 的位置初始化为 -1
        
        int currSum = 0;
        for (int i = 0; i < nums.length; i++) {
            currSum += nums[i];
            int mod = k == 0 ? currSum : currSum % k;
            
            if (sumMod.containsKey(mod)) {
                // 若子数组长度 >= 2 则找到答案
                if (i - sumMod.get(mod) >= 2) {
                    return true;
                }
            } else {
                sumMod.put(mod, i);
            }
        }
        return false;
    }
}
```
- **复杂度:** O(n) 时间 | O(min(n, k)) 空间
- **关键点:** k = 0 特殊处理 | 记录首次出现位置，确保长度 >= 2

#### LeetCode 325: Maximum Size Subarray Sum Equals K
**思路:** 前缀和 + 哈希表，找最长子数组

```java
class Solution {
    public int maxSubArrayLen(int[] nums, int k) {
        Map<Integer, Integer> sumIndex = new HashMap<>();
        sumIndex.put(0, -1); // 前缀和 0 的位置
        
        int currSum = 0, maxLen = 0;
        for (int i = 0; i < nums.length; i++) {
            currSum += nums[i];
            
            // 若 currSum - k 存在，更新最长长度
            if (sumIndex.containsKey(currSum - k)) {
                maxLen = Math.max(maxLen, i - sumIndex.get(currSum - k));
            }
            
            // 只记录第一次出现位置（为了最大化长度）
            if (!sumIndex.containsKey(currSum)) {
                sumIndex.put(currSum, i);
            }
        }
        return maxLen;
    }
}
```
- **复杂度:** O(n) 时间 | O(n) 空间
- **关键点:** 只记录首次出现位置来最大化长度 | currSum - k 的逻辑

---

## 7. Heap / Priority Queue (堆 / 优先队列)

**核心概念:** Top K | 中位数 | 会议室 | 自定义排序

### 代表题

#### LeetCode 215: Kth Largest Element in an Array
**思路:** 最小堆维护 k 个最大元素，堆顶就是第 k 大

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        // 最小堆：堆顶是 k 个最大元素中最小的
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        for (int num : nums) {
            minHeap.offer(num);
            // 堆大小超过 k 时，弹出最小的
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        
        return minHeap.peek(); // 堆顶就是第 k 大
    }
}
```
- **复杂度:** O(n·logk) 时间 | O(k) 空间
- **关键点:** 最小堆大小始终为 k | 堆顶是第 k 大元素

#### LeetCode 253: Meeting Rooms II
**思路:** 按开始时间排序，用最小堆追踪会议室结束时间

```java
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;
        
        // 按开始时间排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        // 最小堆存储会议室的结束时间
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(intervals[0][1]);
        
        for (int i = 1; i < intervals.length; i++) {
            // 若当前会议开始时间 >= 最早结束的会议，可以复用会议室
            if (intervals[i][0] >= minHeap.peek()) {
                minHeap.poll();
            }
            minHeap.offer(intervals[i][1]);
        }
        
        return minHeap.size(); // 堆的大小就是需要的会议室数
    }
}
```
- **复杂度:** O(n·logn) 时间 | O(n) 空间
- **关键点:** 排序是关键 | 比较开始和结束时间判断是否能复用

#### LeetCode 295: Find Median from Data Stream
**思路:** 大堆（左侧）+ 小堆（右侧），维护大小平衡

```java
class MedianFinder {
    private PriorityQueue<Integer> maxHeap; // 左侧：最大堆
    private PriorityQueue<Integer> minHeap; // 右侧：最小堆
    
    public MedianFinder() {
        maxHeap = new PriorityQueue<>((a, b) -> b - a); // 大堆
        minHeap = new PriorityQueue<>(); // 小堆
    }
    
    public void addNum(int num) {
        // 先加入大堆
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        
        // 平衡两个堆的大小
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }
    
    public double findMedian() {
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        }
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
}
```
- **复杂度:** O(logn) 时间 | O(n) 空间
- **关键点:** 左侧最大堆，右侧最小堆 | 大小差 <= 1 | 中位数定义

---

## 8. Dynamic Programming (动态规划)

**核心概念:** 状态定义 | 转移方程 | 初始化边界 | DP 优化

### 代表题

#### LeetCode 70: Climbing Stairs
**思路:** dp[i] = 到达第 i 级的方法数 = dp[i-1] + dp[i-2]

```java
class Solution {
    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        
        // dp[i] 表示到达第 i 级台阶的方法数
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
    
    // 空间优化版本：只需记录前两个值
    public int climbStairsOptimized(int n) {
        if (n == 1) return 1;
        int prev = 1, curr = 2;
        for (int i = 3; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }
}
```
- **复杂度:** O(n) 时间 | O(1) 空间（优化版）
- **关键点:** 状态定义清晰 | 递推关系简单 | 可空间优化

#### LeetCode 322: Coin Change
**思路:** dp[i] = 凑成金额 i 的最少硬币数

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        // dp[i] 表示凑成金额 i 需要的最少硬币数
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // 初始化为不可达
        dp[0] = 0; // 金额 0 需要 0 枚硬币
        
        for (int i = 1; i <= amount; i++) {
            // 尝试用每个硬币
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        
        return dp[amount] > amount ? -1 : dp[amount];
    }
}
```
- **复杂度:** O(amount × n) 时间 | O(amount) 空间（n=硬币数）
- **关键点:** dp[i] = min(dp[i], dp[i-coin] + 1) | 初始化为 amount+1（无穷大）

#### LeetCode 312: Burst Balloons
**思路:** 区间 DP，定义 dp[left][right] = 戳破 (left, right) 中所有气球得到最大硬币数

```java
class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // 在两端添加虚拟气球，便于计算
        int[] balloons = new int[n + 2];
        balloons[0] = 1;
        balloons[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            balloons[i + 1] = nums[i];
        }
        
        // dp[i][j] 表示戳破 (i, j) 之间所有气球得到的硬币
        int[][] dp = new int[n + 2][n + 2];
        
        // 从小到大枚举区间长度
        for (int len = 1; len <= n; len++) {
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                // 枚举最后戳破的气球 k
                for (int k = i; k <= j; k++) {
                    int coins = balloons[i - 1] * balloons[k] * balloons[j + 1]
                              + dp[i][k - 1] + dp[k + 1][j];
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }
        
        return dp[1][n];
    }
}
```
- **复杂度:** O(n³) 时间 | O(n²) 空间
- **关键点:** 反向思考（最后戳破哪个气球） | 添加虚拟边界简化计算

---

## 9. Backtracking (回溯)

**核心概念:** 选择 + 撤销 | 剪枝 | 递归树

### 代表题

#### LeetCode 46: Permutations
**思路:** 选择可用的元素，递归探索，回溯撤销

```java
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, List<Integer> current, List<List<Integer>> result) {
        // 终止条件：当前排列完成
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int num : nums) {
            // 剪枝：跳过已使用元素
            if (current.contains(num)) {
                continue;
            }
            // 选择
            current.add(num);
            // 递归探索
            backtrack(nums, current, result);
            // 撤销
            current.remove(current.size() - 1);
        }
    }
}
```
- **复杂度:** O(n! × n) 时间 | O(n!) 空间
- **关键点:** 选择 → 递归 → 撤销三步 | 使用 HashSet 优化 current.contains()

#### LeetCode 51: N-Queens
**思路:** 逐行放置皇后，检查冲突，回溯尝试

```java
class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
        backtrack(board, 0, result, n);
        return result;
    }
    
    private void backtrack(char[][] board, int row, List<List<String>> result, int n) {
        if (row == n) {
            result.add(boardToList(board));
            return;
        }
        
        for (int col = 0; col < n; col++) {
            // 剪枝：检查是否冲突
            if (isValid(board, row, col, n)) {
                // 选择
                board[row][col] = 'Q';
                // 递归下一行
                backtrack(board, row + 1, result, n);
                // 撤销
                board[row][col] = '.';
            }
        }
    }
    
    private boolean isValid(char[][] board, int row, int col, int n) {
        // 检查列
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }
        // 检查左上对角线
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }
        // 检查右上对角线
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }
        return true;
    }
    
    private List<String> boardToList(char[][] board) {
        List<String> list = new ArrayList<>();
        for (char[] row : board) {
            list.add(new String(row));
        }
        return list;
    }
}
```
- **复杂度:** O(N!) 时间 | O(N²) 空间
- **关键点:** 剪枝很重要（isValid 检查冲突） | 逐行放置

#### LeetCode 79: Word Search
**思路:** DFS + 回溯，访问相邻字符形成单词

```java
class Solution {
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0) return false;
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, word, 0, i, j, new boolean[board.length][board[0].length])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean dfs(char[][] board, String word, int idx, int row, int col, boolean[][] visited) {
        // 终止条件：找到完整单词
        if (idx == word.length()) {
            return true;
        }
        
        // 边界检查和冲突检查
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length
            || visited[row][col] || board[row][col] != word.charAt(idx)) {
            return false;
        }
        
        // 选择
        visited[row][col] = true;
        
        // 递归四个方向
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        for (int[] dir : dirs) {
            if (dfs(board, word, idx + 1, row + dir[0], col + dir[1], visited)) {
                return true;
            }
        }
        
        // 撤销
        visited[row][col] = false;
        return false;
    }
}
```
- **复杂度:** O(n × 4^L) 时间最坏 | O(m×n) 空间（m×n=网格大小，L=单词长）
- **关键点:** visited 数组标记已访问 | 及时撤销 visited 以尝试其他路径

---

## 10. Union Find (并查集)

**核心概念:** 连通性 | 路径压缩 + 按秩合并 | 加权并查集

### 代表题

#### LeetCode 200: Number of Islands
**思路:** Union Find 统计连通分量个数

```java
class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        
        int m = grid.length, n = grid[0].length;
        UnionFind uf = new UnionFind(m * n);
        
        int[][] dirs = {{0,1}, {1,0}};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    // 向下和向右合并
                    for (int[] dir : dirs) {
                        int ni = i + dir[0], nj = j + dir[1];
                        if (ni < m && nj < n && grid[ni][nj] == '1') {
                            uf.union(i * n + j, ni * n + nj);
                        }
                    }
                }
            }
        }
        
        return uf.getCount();
    }
    
    class UnionFind {
        private int[] parent;
        private int[] rank;
        private int count; // 连通分量数
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
            count = 0;
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // 路径压缩
            }
            return parent[x];
        }
        
        public void union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return;
            
            // 按秩合并
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
            count--;
        }
        
        public int getCount() {
            return count;
        }
    }
}
```
- **复杂度:** O(m×n×α(m×n)) 时间 | O(m×n) 空间
- **关键点:** 初始连通分量数 = 陆地数 | 每次 union 减少分量数

#### LeetCode 261: Graph Valid Tree
**思路:** 检查是否为树（连通 + 无环 = n 个节点 n-1 条边 + 连通）

```java
class Solution {
    public boolean validTree(int n, int[][] edges) {
        // 树的条件：n 个节点 n-1 条边 + 连通
        if (edges.length != n - 1) return false;
        
        UnionFind uf = new UnionFind(n);
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            // 若两个节点已连通，则有环
            if (uf.find(u) == uf.find(v)) {
                return false;
            }
            uf.union(u, v);
        }
        
        return true;
    }
    
    class UnionFind {
        private int[] parent;
        
        public UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }
    }
}
```
- **复杂度:** O(n × α(n)) 时间 | O(n) 空间
- **关键点:** 边数不等于 n-1 直接返回 false | 检测环的条件

#### LeetCode 1202: Smallest String With Swaps
**思路:** 按字符可交换关系分组（并查集），每组内排序后重新分配

```java
class Solution {
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        if (s == null || s.length() == 0) return s;
        
        int n = s.length();
        UnionFind uf = new UnionFind(n);
        
        // 建立字符位置的连通关系
        for (List<Integer> pair : pairs) {
            uf.union(pair.get(0), pair.get(1));
        }
        
        // 按根节点分组
        Map<Integer, List<Integer>> groups = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            if (!groups.containsKey(root)) {
                groups.put(root, new ArrayList<>());
            }
            groups.get(root).add(i);
        }
        
        char[] result = new char[n];
        // 对每组进行排序
        for (List<Integer> group : groups.values()) {
            // 提取字符
            List<Character> chars = new ArrayList<>();
            for (int idx : group) {
                chars.add(s.charAt(idx));
            }
            // 排序
            Collections.sort(chars);
            // 分配回去
            Collections.sort(group);
            for (int i = 0; i < group.size(); i++) {
                result[group.get(i)] = chars.get(i);
            }
        }
        
        return new String(result);
    }
    
    class UnionFind {
        private int[] parent;
        
        public UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }
    }
}
```
- **复杂度:** O(n × α(n) + n·log(n)) 时间 | O(n) 空间
- **关键点:** 分组后对各组字符排序 | 按位置升序分配回去

---

## 补充高频 Pattern（可选）

### Two Pointers (双指针)

#### LeetCode 15: 3Sum
**思路:** 排序 + 外层遍历 + 双指针查找

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) return result;
        
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) break; // 优化：若最小数大于 0，无解
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 去重
            
            int target = -nums[i];
            int left = i + 1, right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 去重
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
}
```
- **复杂度:** O(n²) 时间 | O(1) 空间
- **关键点:** 先排序 | 三层嵌套关系：外层遍历 + 内层双指针 | 多处去重

#### LeetCode 42: Trapping Rain Water
**思路:** 双指针从两侧扫描，维护左右最大值

```java
class Solution {
    public int trap(int[] height) {
        if (height == null || height.length < 2) return 0;
        
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0, water = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }
                right--;
            }
        }
        return water;
    }
}
```
- **复杂度:** O(n) 时间 | O(1) 空间
- **关键点:** 水位 = min(leftMax, rightMax) - height[i] | 双指针往中间扫描

### Binary Search (二分查找)

#### LeetCode 33: Search in Rotated Sorted Array
**思路:** 二分查找，判断哪一侧有序，在有序侧判断目标是否存在

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;
            
            // 判断哪一侧有序
            if (nums[left] <= nums[mid]) { // 左侧有序
                // 目标在有序左侧
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else { // 右侧有序
                // 目标在有序右侧
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
```
- **复杂度:** O(logn) 时间 | O(1) 空间
- **关键点:** 判断哪侧有序 | 在有序侧判断目标存在性

#### LeetCode 34: Find First and Last Position of Element in Sorted Array
**思路:** 两次二分查找分别找左边界和右边界

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};
        if (nums == null || nums.length == 0) return result;
        
        result[0] = findFirst(nums, target);
        result[1] = findLast(nums, target);
        return result;
    }
    
    private int findFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // 继续往左找
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left < nums.length && nums[left] == target ? left : -1;
    }
    
    private int findLast(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // 继续往右找
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return right >= 0 && nums[right] == target ? right : -1;
    }
}
```
- **复杂度:** O(logn) 时间 | O(1) 空间
- **关键点:** 找左边界时用 right = mid - 1 逼近左界 | 找右边界时用 left = mid + 1 逼近右界

### Greedy (贪心)

#### LeetCode 55: Jump Game
**思路:** 逆向思考，检查是否能从某位置到达终点

```java
class Solution {
    public boolean canJump(int[] nums) {
        int maxReach = 0; // 能到达的最远位置
        
        for (int i = 0; i < nums.length; i++) {
            // 若当前位置超过最远可达，返回 false
            if (i > maxReach) return false;
            // 更新最远可达
            maxReach = Math.max(maxReach, i + nums[i]);
            // 已能到达终点
            if (maxReach >= nums.length - 1) return true;
        }
        return true;
    }
}
```
- **复杂度:** O(n) 时间 | O(1) 空间
- **关键点:** 维护 maxReach 表示当前能到达的最远位置 | 检查 i > maxReach 判断卡住

#### LeetCode 134: Gas Station
**思路:** 贪心：若能环绕一圈则必存在这样的起点

```java
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0, current = 0, start = 0;
        
        for (int i = 0; i < gas.length; i++) {
            total += gas[i] - cost[i];
            current += gas[i] - cost[i];
            
            // 若到此位置油量为负，则起点往后移
            if (current < 0) {
                current = 0;
                start = i + 1;
            }
        }
        
        // 若总油量为负，无解；否则 start 就是答案
        return total >= 0 ? start : -1;
    }
}
```
- **复杂度:** O(n) 时间 | O(1) 空间
- **关键点:** 若从位置 i 不可达，则 i 前的所有位置也不可达 | 数学保证总油量 >= 0 时必存在解

### Hash Map / Set (哈希表)

#### LeetCode 1: Two Sum
**思路:** 哈希表存储已见数字及其索引，查询 target - num

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }
}
```
- **复杂度:** O(n) 时间 | O(n) 空间
- **关键点:** 一次遍历 | 先查询再插入（避免自身匹配）

#### LeetCode 49: Group Anagrams
**思路:** 排序字符串作为 key，分组异位词

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str : strs) {
            // 将字符排序作为 key
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }
        
        return new ArrayList<>(map.values());
    }
}
```
- **复杂度:** O(n × k·log(k)) 时间 | O(n) 空间（k=平均字符串长）
- **关键点:** 排序后相同字符串是异位词 | 用 key 分组

---

## 学习计划

### 第 1 周：基础模板掌握
- Pattern 1-4：树、图、最短路、拓扑
- 目标：理解框架，手写代码不看答案

### 第 2 周：子数组 / 数据结构
- Pattern 5-7：滑动窗口、前缀和、堆
- 目标：识别问题类型，快速选择方法

### 第 3 周：DP + 回溯
- Pattern 8-9：动态规划、回溯
- 目标：状态定义、剪枝策略

### 第 4 周：图论 + 补充
- Pattern 10 + 补充：并查集、补充高频
- 目标：综合应用，做到 7-8 分钟快速解题

---

## 面试速查表

| Pattern | 时间复杂度 | 空间复杂度 | 关键点 |
|---------|-----------|-----------|--------|
| Tree DFS | O(n) | O(h) | 返回值定义 |
| Graph BFS | O(V+E) | O(V) | 多源初始化 |
| Dijkstra | O((V+E)logV) | O(V) | 优先队列 |
| Topo Sort | O(V+E) | O(V) | 入度 vs DFS |
| Sliding Window | O(n) | O(1) | 何时扩/缩 |
| Prefix Sum | O(n) | O(n) | target trick |
| Heap | O(logn) per op | O(k) | Top K |
| DP | 问题定制 | 问题定制 | 状态转移 |
| Backtracking | O(n!) worst | O(n) stack | 剪枝关键 |
| Union Find | O(α(n)) | O(n) | 路径压缩 |

---

## 实践建议

1. **掌握顺序:** 按难度，先简单再复杂
   - Week 1: Tree DFS, Sliding Window, Heap
   - Week 2: Two Pointers, Binary Search, DP 基础
   - Week 3: Graph BFS, Topological Sort, Union Find
   - Week 4: Dijkstra, Backtracking, DP 进阶

2. **代表题解法:**
   - 第一遍：看解答，理解思路
   - 第二遍：自己写，不看答案
   - 第三遍：讲解，解释给别人听

3. **模拟面试:**
   - 每周末：做 2-3 道综合题
   - 计时：45 分钟内完成（包括讲解）
   - 记录：错误、陷阱、优化

---

## 关键面试技巧

### 解题流程（45 分钟）
1. **澄清（3 分钟）:** 输入范围、边界情况
2. **思路（5 分钟）:** 蛮力 → 优化方案
3. **代码（20 分钟）:** 清晰、边界检查、注释
4. **测试（10 分钟）:** 手工测试、复杂度分析
5. **讲解（7 分钟）:** 思路、复杂度、改进空间

### 常见陷阱
- Tree：空节点、单节点、返回值含义
- Graph：初始化、visited 标记、连通性
- DP：base case、状态定义歧义、数组越界
- Backtracking：撤销不完全、重复计算、剪枝遗漏

---

## 进度追踪

### Pattern 掌握度评分
- ⭐☆☆☆☆ 不了解概念
- ⭐⭐☆☆☆ 理解，需看答案
- ⭐⭐⭐☆☆ 能写，有小错
- ⭐⭐⭐⭐☆ 快速写出，偶有优化空间
- ⭐⭐⭐⭐⭐ 7-8 分钟内完美解决

### 目标状态
所有 10 个 Pattern 达到 ⭐⭐⭐⭐ 或以上

---

**预计准备时间：4-6 周，每天 2-3 小时**
