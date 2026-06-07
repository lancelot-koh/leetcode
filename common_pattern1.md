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

Pattern 1  BFS
1 State(row, col)

* Number of Islands
```java
class Solution {

    private final int[][] DIRECTIONS = {
        {-1, 0}, // up
        {1, 0},  // down
        {0, -1}, // left
        {0, 1}   // right
    };

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int islands = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    islands++;
                    bfs(grid, r, c);
                }
            }
        }
        return islands;
    }

    private void bfs(char[][] grid, int row, int col) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{row, col});
        grid[row][col] = '0';

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();

            for (int[] dir : DIRECTIONS) {
                int newRow = curr[0] + dir[0];
                int newCol = curr[1] + dir[1];

                if (newRow >= 0 && newRow < grid.length && newCol >= 0 &&
                    newCol < grid[0].length && grid[newRow][newCol] == '1') {
                    queue.offer(new int[]{newRow, newCol});
                    grid[newRow][newCol] = '0';
                }
            }
        }
    }
}
```

* Rotting Oranges
```java
class Solution {

    private final int[][] DIRECTIONS = {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };

    public int orangesRotting(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) {
                    queue.offer(new int[]{r, c});
                }
                if (grid[r][c] == 1) {
                    fresh++;
                }
            }
        }

        if (fresh == 0) {
            return 0;
        }

        int minutes = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean rottenThisRound = false;

            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();

                for (int[] dir : DIRECTIONS) {
                    int newRow = curr[0] + dir[0];
                    int newCol = curr[1] + dir[1];

                    if (newRow < 0 || newRow >= rows || newCol < 0 ||
                        newCol >= cols || grid[newRow][newCol] != 1) {
                        continue;
                    }

                    grid[newRow][newCol] = 2;
                    fresh--;
                    queue.offer(new int[]{newRow, newCol});
                    rottenThisRound = true;
                }
            }

            if (rottenThisRound) {
                minutes++;
            }
        }

        return fresh == 0 ? minutes : -1;
    }
}
```

Pattern 2 Graph BFS


* Clone Graph
```java
class Solution {

    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        Map<Node, Node> map = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();

        Node clone = new Node(node.val);
        map.put(node, clone);
        queue.offer(node);

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            for (Node neighbor : curr.neighbors) {
                if (!map.containsKey(neighbor)) {
                    map.put(neighbor, new Node(neighbor.val));
                    queue.offer(neighbor);
                }
                map.get(curr).neighbors.add(map.get(neighbor));
            }
        }

        return clone;
    }
}
```

* Valid Tree
```java
class Solution {

    public boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1) {
            return false;
        }

        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.offer(0);
        visited.add(0);

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int neighbor : graph.get(curr)) {
                if (visited.contains(neighbor)) {
                    continue;
                }
                visited.add(neighbor);
                queue.offer(neighbor);
            }
        }
        return visited.size() == n;
    }
}
```

Pattern 3 Word Transformation
State: Word

* Word Ladder
```java
class Solution {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        int steps = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr.equals(endWord)) {
                    return steps;
                }

                char[] chars = curr.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    char original = chars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
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

Pattern 4 Lock Problem

State: combination

Open the lock 
```java
class Solution {

    public int openLock(String[] deadends, String target) {
        Set<String> dead = new HashSet<>(Arrays.asList(deadends));
        if (dead.contains("0000")) {
            return -1;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer("0000");
        visited.add("0000");

        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr.equals(target)) {
                    return steps;
                }

                for (String next : getNeighbors(curr)) {
                    if (dead.contains(next) || visited.contains(next)) {
                        continue;
                    }
                    visited.add(next);
                    queue.offer(next);
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
            
            chars[i] = original == '9' ? '0' : (char)(original + 1);
            neighbors.add(new String(chars));

            chars[i] = original == '0' ? '9' : (char)(original - 1);
            neighbors.add(new String(chars));
            
            chars[i] = original;
        }
        return neighbors;
    }
}
```

Pattern 5 Multiple Dimensions
State: (position, speed)

Race Car
```java
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

                if (position == target) {
                    return steps;
                }

                int nextPos = position + speed;
                int nextSpeed = speed * 2;
                String keyA = nextPos + "," + nextSpeed;

                if (Math.abs(nextPos) < target * 2 && !visited.contains(keyA)) {
                    visited.add(keyA);
                    queue.offer(new int[]{nextPos, nextSpeed});
                }

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

Pattern 6 Resource Tracking
State: (node, remaining_k)

Cheapest Flights Within K Stops
```java
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

        int stops = 0;
        int[] minCost = new int[n];
        Arrays.fill(minCost, Integer.MAX_VALUE);
        minCost[src] = 0;

        while (!queue.isEmpty() && stops <= k) {
            int size = queue.size();
            int[] tempCost = Arrays.copyOf(minCost, n);

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
class Solution {

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, List<int[]>> graph = new HashMap<>();

        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (int[] flight : flights) {
            graph.get(flight[0]).add(new int[]{flight[1], flight[2]});
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{src, 0, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int city = curr[0];
            int cost = curr[1];
            int stops = curr[2];

            if (city == dst) {
                return cost;
            }

            if (stops > k) {
                continue;
            }

            for (int[] next : graph.get(city)) {
                pq.offer(new int[]{next[0], cost + next[1], stops + 1});
            }
        }

        return -1;
    }
}
```

Pattern 7 Bitmask State
State: (node, visitedMask)

Shortest Path Visiting All Nodes
```java
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

        return -1;
    }
}
```

Day 2 DFS
State: current Node/Path/Depth

DFS number of island:

```java
class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int islands = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    islands++;
                    dfs(grid, r, c);
                }
            }
        }

        return islands;
    }

    private void dfs(char[][] grid, int row, int col) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (row < 0 || row >= rows || col < 0 || col >= cols || grid[row][col] != '1') {
            return;
        }

        grid[row][col] = '0';
        dfs(grid, row - 1, col);
        dfs(grid, row + 1, col);
        dfs(grid, row, col - 1);
        dfs(grid, row, col + 1);
    }
}
```

Path Sum
State: (node, sum)
```java
class Solution {

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }

        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }
}
```

Diameter
State: node
return height
```java
class Solution {

    private int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return diameter;
    }

    private int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = dfs(node.left);
        int rightHeight = dfs(node.right);

        diameter = Math.max(diameter, leftHeight + rightHeight);
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
```

Day 3 backtracking
State: (current position/path, current_path)

Pattern 1 Permutation
State: current path, used[]

Permutation 1
```java
class Solution {

    public List<List<Integer>> permute(int[] nums) {
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

            visited[i] = true;
            path.add(nums[i]);

            backtrack(nums, path, visited, result);

            path.remove(path.size() - 1);
            visited[i] = false;
        }
    }
}
```

Permutation 2 has dueplicated
```java
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

Pattern 2 Combination
State: (index, current_path)
```java
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

Combination Sum II
Difference: each number can only be used once and duplicates exist
```java
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

Pattern 3 Subset
State: (index, current_path)
```java
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

Subset II: No duplicates 
```java
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

Pattern 4 Word Search
State: (row, col, index)

```java
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


Pattern 1 Search space
State: (left, right)



Pattern 2 Answer Search
State: candidate answer

KOKO
```java
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

Day 5 Heap
State: (priority, value)

Top K
State: (freq, number)
Top K Largest Elements
```java
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

Top K Frequent Elements
```java
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

Meeting Room
State: endTime
```java
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

Dijkstra
State: (distance, node)
```java
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





Day 6 Graph

Pattern 1 connectivity
State: node
Connected?
Reachable?
Groups?
Components?
Cluster?
Province?
Island?

Number of Connected Components
```java
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

Redundant Connection
```java
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

Accounts Merge
```java
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


Pattern 4 Topological sort
State: indegree
```java
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

==================================
State 变体
BFS + Extra Dimension
普通 BFS： (row, col)
升级后：(row, col, something)


LC1293 Shortest Path in Grid with Obstacles Elimination
state: (row, col, remainingK)

```java
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

```java
class State {

    int row;
    int col;
    int remain;
}
```

BFS + Inventory
State：(position, keys)
LC864 Shortest Path to Get All Keys
State：(x, y, keyMask)
```java
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

BFS + Bitmask
State：(node, visitedMask)
LC847: Shortest Path Visiting All Nodes
State： currentNode + visitedNodes
```java
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
