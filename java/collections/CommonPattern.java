package collections;

import java.util.*;

/**
 * Algorithm interview patterns grouped by frequency and importance.
 *
 * TIER 1 — Core (appears in almost every interview)
 * TIER 2 — Very common (medium / hard rounds)
 * TIER 3 — Harder problems (senior / FAANG level)
 * TIER 4 — Good to know (occasional / niche)
 *  # Algorithm Interview Patterns — Ranked by Frequency

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

 * 
 */
public class CommonPattern {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {

        // ════════════════════════════════════════════════════════════════
        //  TIER 1 — Core patterns (in almost every interview)
        // ════════════════════════════════════════════════════════════════

        // ── T1-1. Two pointers ──
        // Use on SORTED arrays or strings. Left and right close in from both ends.
        // Eliminates the O(n²) nested loop for pair problems → O(n).
        int[] sorted = {1, 2, 3, 4, 6};
        int twoSum = 6, p = 0, q = sorted.length - 1;
        while (p < q) {
            int sum = sorted[p] + sorted[q];
            if (sum == twoSum) { System.out.println("T1-1 pair: " + sorted[p] + "+" + sorted[q]); break; }
            else if (sum <  twoSum) { p++; }
            else { q--; }
        }

        // ── T1-2. Sliding window — fixed size k ──
        // Subtract left edge, add right edge each step. O(n), no inner loop.
        int[] nums1 = {1, 2, 3, 4, 5};
        int k = 3, winSum = 0;
        for (int i = 0; i < k; i++) {
            winSum += nums1[i];
        } 
        int maxWin = winSum;
        for (int i = k; i < nums1.length; i++) {
            winSum += nums1[i] - nums1[i - k];
            maxWin = Math.max(maxWin, winSum);
        }
        System.out.println("T1-2 max sum window k=" + k + ": " + maxWin);

        // ── T1-3. Sliding window — variable size ──
        // Expand right freely; shrink left whenever the constraint is violated.
        // Example: longest subarray with sum ≤ target.
        int target1 = 7, wLeft = 0, wCur = 0, wBest = 0;
        for (int right = 0; right < nums1.length; right++) {
            wCur += nums1[right];
            while (wCur > target1) {
                wCur -= nums1[wLeft++];
            }
            wBest = Math.max(wBest, right - wLeft + 1);
        }
        System.out.println("T1-3 longest subarray sum<=" + target1 + ": " + wBest);

        // ── T1-4. Binary search ──
        // Halves the search space each step → O(log n).
        // Always use mid = lo + (hi-lo)/2 to prevent integer overflow.
        // lo <= hi   → exact match search
        // lo <  hi   → boundary / insertion-point search
        int[] sortedArr = {1, 3, 5, 7, 9};
        int searchTarget = 7, bsLo = 0, bsHi = sortedArr.length - 1, bsResult = -1;
        while (bsLo <= bsHi) {
            int mid = bsLo + (bsHi - bsLo) / 2;
            if (sortedArr[mid] == searchTarget) { bsResult = mid; break; }
            else if (sortedArr[mid] <  searchTarget) { bsLo = mid + 1; }
            else { bsHi = mid - 1; }
        }
        System.out.println("T1-4 binary search index of " + searchTarget + ": " + bsResult);

        // ── T1-5. BFS — graph ──
        // Queue + visited[]. Explores level by level; guarantees shortest path
        // in an unweighted graph. O(V+E).
        int[][] graph = {{1,2},{0,3},{0,4},{1},{2}};
        boolean[] bfsVis = new boolean[graph.length];
        Queue<Integer> bfsQ = new LinkedList<>();
        bfsQ.offer(0); 
        bfsVis[0] = true;
        System.out.print("T1-5 BFS: ");
        while (!bfsQ.isEmpty()) {
            int node = bfsQ.poll();
            System.out.print(node + " ");
            for (int nb : graph[node]) {
                if (!bfsVis[nb]) {
                    bfsVis[nb] = true; bfsQ.offer(nb);
                }
            }
        }
        System.out.println();

        // ── T1-6. Binary tree traversals — recursive ──
        // Inorder   (L→root→R): visits BST nodes in sorted order.
        // Preorder  (root→L→R): serialise / copy a tree.
        // Postorder (L→R→root): process children before parent (e.g., delete).
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);   
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4); 
        root.left.right = new TreeNode(5);

        System.out.print("T1-6 inorder:   "); 
        inorder(root);   
        System.out.println(); // 4 2 5 1 3

        System.out.print("T1-6 preorder:  "); 
        preorder(root);  
        System.out.println(); // 1 2 4 5 3
        
        System.out.print("T1-6 postorder: "); 
        postorder(root); 
        System.out.println(); // 4 5 2 3 1

        // ── T1-7. Binary tree inorder — iterative ──
        // Pattern: push every left child until null; pop and process; go right.
        // Avoids recursion stack overflow on skewed trees.
        Deque<TreeNode> tStk = new ArrayDeque<>();
        TreeNode tCur = root;
        System.out.print("T1-7 iterative inorder: ");
        while (tCur != null || !tStk.isEmpty()) {
            while (tCur != null) { tStk.push(tCur); tCur = tCur.left; }
            tCur = tStk.pop();
            System.out.print(tCur.val + " ");
            tCur = tCur.right;
        }
        System.out.println();

        // ── T1-8. Level-order traversal (BFS with levels) ──
        // Inner loop drains exactly one level per outer iteration.
        // Template for: max depth, right-side view, zigzag traversal.
        List<List<Integer>> levels = new ArrayList<>();
        Queue<TreeNode> lvlQ = new LinkedList<>();
        if (root != null) { lvlQ.offer(root); }
        while (!lvlQ.isEmpty()) {
            int sz = lvlQ.size();
            List<Integer> level = new ArrayList<>();
            for (int x = 0; x < sz; x++) {
                TreeNode tn = lvlQ.poll();
                level.add(tn.val);
                if (tn.left  != null) { lvlQ.offer(tn.left); }
                if (tn.right != null) { lvlQ.offer(tn.right); }
            }
            levels.add(level);
        }
        System.out.println("T1-8 level-order: " + levels); // [[1],[2,3],[4,5]]

        // ── T1-9. 1-D DP — bottom-up (coin change) ──
        // Build dp[] smallest-to-largest; each cell reuses earlier answers.
        // Sentinel trick: fill with (amount+1) as "infinity" so Math.min works.
        // dp[i] = min coins to make amount i.
        int[] coins = {1, 2, 5};
        int amount = 11;
        int[] dp1 = new int[amount + 1];
        Arrays.fill(dp1, amount + 1);
        dp1[0] = 0;
        for (int am = 1; am <= amount; am++) {
            for (int coin : coins) {
                if (coin <= am) { dp1[am] = Math.min(dp1[am], dp1[am - coin] + 1); }
            }
        }
        System.out.println("T1-9 min coins for " + amount + ": " + dp1[amount]); // 3

        // ── T1-10. StringBuilder & string basics ──
        // String += in a loop is O(n²) — every += allocates a new String object.
        // StringBuilder.append() is O(1) amortised. Always use it in loops.
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < 5; x++) { sb.append(x).append(","); }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println("T1-10 built: " + sb); // 0,1,2,3,4

        // Two-pointer palindrome check — O(n), O(1)
        String pal = "racecar";
        int palL = 0, palR = pal.length() - 1;
        boolean isPal = true;
        while (palL < palR) {
            if (pal.charAt(palL++) != pal.charAt(palR--)) { isPal = false; break; }
        }
        System.out.println("T1-10 \"" + pal + "\" palindrome: " + isPal);

        // ── T1-11. Frequency map ──
        // int[26] — fastest for lowercase a-z: O(n) time, O(1) space.
        // HashMap  — works for any type; use merge() to avoid null-check boilerplate.
        String s1 = "aabbccda";
        int[] freq = new int[26];
        for (char c : s1.toCharArray())  { 
            freq[c - 'a']++;
        }
        System.out.println("T1-11 freq['a']: " + freq[0]); // 3

        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : s1.toCharArray())  { 
            freqMap.merge(c, 1, Integer::sum);
        }
        System.out.println("T1-11 freqMap: " + freqMap);


        // ════════════════════════════════════════════════════════════════
        //  TIER 2 — Very common (medium / hard rounds)
        // ════════════════════════════════════════════════════════════════

        // ── T2-1. Matrix BFS — 4-direction grid ──
        // Standard for: shortest path in maze, number of islands, flood fill.
        // dirs = {Δrow, Δcol} for the 4 cardinal moves.
        // Check bounds AND visited BEFORE enqueueing to avoid re-processing.
        int[][] grid = {{0,0,1,0},{0,0,0,0},{1,0,1,0},{0,0,0,0}};
        int mRows = grid.length, mCols = grid[0].length;
        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
        boolean[][] mVis = new boolean[mRows][mCols];
        Queue<int[]> mQ = new LinkedList<>();
        mQ.offer(new int[]{0, 0}); 
        mVis[0][0] = true;
        int mSteps = 0;
        
        while (!mQ.isEmpty()) {
            int sz = mQ.size();
            for (int x = 0; x < sz; x++) {
                int[] cell = mQ.poll();
                for (int[] dir : dirs) {
                    int nr = cell[0] + dir[0], nc = cell[1] + dir[1];
                    if (nr >= 0 && nr < mRows && nc >= 0 && nc < mCols
                            && !mVis[nr][nc] && grid[nr][nc] == 0) {
                        mVis[nr][nc] = true; mQ.offer(new int[]{nr, nc});
                    }
                }
            }
            mSteps++;
        }
        System.out.println("T2-1 matrix BFS levels traversed: " + mSteps);

        // ── T2-2. Backtracking ──
        // Framework: make choice → recurse → undo choice ("backtrack").
        // Prune BEFORE recursing to cut invalid branches early.
        // Example: all permutations of [1,2,3].
        List<List<Integer>> perms = new ArrayList<>();
        backtrack(perms, new ArrayList<>(), new int[]{1, 2, 3}, new boolean[3]);
        System.out.println("T2-2 permutations: " + perms); // [[1,2,3],[1,3,2]...]

        // ── T2-3. Top-K elements — min-heap of size k ──
        // Poll heap when size > k → heap always holds the k largest elements.
        // heap.peek() = kth largest. Total cost O(n log k) vs O(n log n) for full sort.
        int[] heapNums = {3, 1, 4, 1, 5, 9, 2, 6};
        int kHeap = 3;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // min on top
        for (int num : heapNums) {
            minHeap.offer(num);
            if (minHeap.size() > kHeap) { minHeap.poll(); }
        }
        System.out.println("T2-3 top-" + kHeap + " largest: " + minHeap); // [5,6,9]

        // ── T2-4. Monotonic stack — next greater element ──
        // Stack holds indices in strictly increasing value order.
        // When a larger element arrives, pop everything smaller — each popped element
        // just found its "next greater". Every element pushed/popped once → O(n).
        int[] a = {2, 1, 5, 6, 2, 3};
        int[] nextGreater = new int[a.length];
        Arrays.fill(nextGreater, -1);
        Deque<Integer> monoStk = new ArrayDeque<>();
        for (int idx = 0; idx < a.length; idx++) {
            while (!monoStk.isEmpty() && a[monoStk.peek()] < a[idx]) {
                nextGreater[monoStk.pop()] = a[idx];
            }
            monoStk.push(idx);
        }
        System.out.println("T2-4 next greater: " + Arrays.toString(nextGreater)); // [5,5,6,-1,3,-1]

        // ── T2-5. Kadane's algorithm — max subarray sum ──
        // At each position: extend the current subarray OR start fresh.
        // curMax = max(nums[i],  curMax + nums[i])
        int[] kadane = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int kCur = kadane[0], kGlobal = kadane[0];
        for (int idx = 1; idx < kadane.length; idx++) {
            kCur    = Math.max(kadane[idx], kCur + kadane[idx]);
            kGlobal = Math.max(kGlobal, kCur);
        }
        System.out.println("T2-5 max subarray sum: " + kGlobal); // 6 ([4,-1,2,1])

        // ── T2-6. 2-D DP — LCS ──
        // dp[r][c] = LCS of word1[0..r-1] and word2[0..c-1].
        // If chars match: diagonal + 1.   Else: best of skipping either char.
        String word1 = "abcde", word2 = "ace";
        int wm = word1.length(), wn = word2.length();
        int[][] dp2d = new int[wm + 1][wn + 1];
        for (int r = 1; r <= wm; r++) {
            for (int c = 1; c <= wn; c++) {
                if (word1.charAt(r-1) == word2.charAt(c-1)) { dp2d[r][c] = dp2d[r-1][c-1] + 1; }
                else { dp2d[r][c] = Math.max(dp2d[r-1][c], dp2d[r][c-1]); }
            }
        }
        System.out.println("T2-6 LCS(\"" + word1 + "\",\"" + word2 + "\"): " + dp2d[wm][wn]); // 3

        // ── T2-7. Prefix sum ──
        // Build once O(n); answer any range-sum query in O(1).
        // prefix[i] = sum of nums[0..i-1]  →  sum(l,r) = prefix[r+1] - prefix[l]
        int[] nums2 = {1, 2, 3, 4, 5};
        int[] prefix = new int[nums2.length + 1];
        for (int i = 0; i < nums2.length; i++) { prefix[i+1] = prefix[i] + nums2[i]; }
        System.out.println("T2-7 range sum [1,3]: " + (prefix[4] - prefix[1])); // 9

        // ── T2-8. Merge intervals ──
        // Sort by start time. Greedily merge: if next.start <= current.end, extend end.
        // Otherwise the current interval is done; next becomes the new current.
        int[][] itvs = {{1,3},{2,6},{8,10},{15,18}};
        Arrays.sort(itvs, Comparator.comparingInt(iv -> iv[0]));
        List<int[]> merged = new ArrayList<>();
        for (int[] iv : itvs) {
            if (merged.isEmpty() || merged.get(merged.size()-1)[1] < iv[0]) {
                merged.add(iv);
            } else {
                merged.get(merged.size()-1)[1] = Math.max(merged.get(merged.size()-1)[1], iv[1]);
            }
        }
        System.out.print("T2-8 merged intervals: ");
        for (int[] iv : merged) { System.out.print(Arrays.toString(iv) + " "); }
        System.out.println(); // [1,6] [8,10] [15,18]


        // ════════════════════════════════════════════════════════════════
        //  TIER 3 — Harder problems (senior / FAANG level)
        // ════════════════════════════════════════════════════════════════

        // ── T3-1. Union-Find (DSU) ──
        // Near-O(1) amortised per operation with path compression + union by rank.
        // Use for: connected components, cycle detection, Kruskal's MST.
        int ufN = 6;
        int[] ufP = new int[ufN], ufR = new int[ufN];
        for (int v = 0; v < ufN; v++) { ufP[v] = v; }
        ufUnion(ufP, ufR, 0, 1); ufUnion(ufP, ufR, 1, 2); ufUnion(ufP, ufR, 3, 4);
        System.out.println("T3-1 same component (0,2): " + (ufFind(ufP,0) == ufFind(ufP,2))); // true
        System.out.println("T3-1 same component (0,3): " + (ufFind(ufP,0) == ufFind(ufP,3))); // false

        // ── T3-2. Topological sort — Kahn's BFS ──
        // Repeatedly remove nodes with in-degree 0.
        // If result.size() < V, a cycle exists → not a valid DAG.
        int topoN = 6;
        int[][] topoEdges = {{5,2},{5,0},{4,0},{4,1},{2,3},{3,1}};
        List<List<Integer>> topoAdj = new ArrayList<>();
        int[] inDeg = new int[topoN];
        for (int v = 0; v < topoN; v++) { topoAdj.add(new ArrayList<>()); }
        for (int[] e : topoEdges) { topoAdj.get(e[0]).add(e[1]); inDeg[e[1]]++; }
        Queue<Integer> topoQ = new LinkedList<>();
        for (int v = 0; v < topoN; v++) { if (inDeg[v] == 0) { topoQ.offer(v); } }
        List<Integer> topoOrder = new ArrayList<>();
        while (!topoQ.isEmpty()) {
            int v = topoQ.poll();
            topoOrder.add(v);
            for (int nb : topoAdj.get(v)) { if (--inDeg[nb] == 0) { topoQ.offer(nb); } }
        }
        System.out.println("T3-2 topo order: " + topoOrder);

        // ── T3-3. Dijkstra's shortest path ──
        // Single-source, non-negative weights. O((V+E) log V) with a min-heap.
        // Skip stale heap entries (d > dist[node]) instead of decreasing keys.
        int dijkN = 5;
        List<List<int[]>> dAdj = new ArrayList<>();
        for (int v = 0; v < dijkN; v++) { dAdj.add(new ArrayList<>()); }
        // edges: {from, to, weight}
        int[][] dEdges = {{0,1,2},{0,2,4},{1,2,1},{1,3,7},{2,4,3},{3,4,1}};
        for (int[] e : dEdges) {
            dAdj.get(e[0]).add(new int[]{e[1], e[2]});
            dAdj.get(e[1]).add(new int[]{e[0], e[2]});
        }
        int[] dist = new int[dijkN];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        PriorityQueue<int[]> dPQ = new PriorityQueue<>(Comparator.comparingInt(x -> x[1]));
        dPQ.offer(new int[]{0, 0}); // {node, distance}
        while (!dPQ.isEmpty()) {
            int[] dCur = dPQ.poll();
            int dNode = dCur[0], d = dCur[1];
            if (d > dist[dNode]) { continue; } // stale — shorter path already found
            for (int[] edge : dAdj.get(dNode)) {
                int nb = edge[0], w = edge[1];
                if (dist[dNode] + w < dist[nb]) {
                    dist[nb] = dist[dNode] + w;
                    dPQ.offer(new int[]{nb, dist[nb]});
                }
            }
        }
        System.out.println("T3-3 Dijkstra from 0: " + Arrays.toString(dist)); // [0,2,3,7,6]

        // ── T3-4. 0/1 Knapsack ──
        // Each item used AT MOST once.
        // Iterate capacity RIGHT-TO-LEFT so each item is counted once only
        // (left-to-right would allow the same item to be reused → unbounded knapsack).
        int[] weights = {2, 3, 4, 5};
        int[] values  = {3, 4, 5, 6};
        int cap = 8;
        int[] knap = new int[cap + 1];
        for (int item = 0; item < weights.length; item++) {
            for (int w = cap; w >= weights[item]; w--) {
                knap[w] = Math.max(knap[w], knap[w - weights[item]] + values[item]);
            }
        }
        System.out.println("T3-4 0/1 knapsack max value (cap=" + cap + "): " + knap[cap]); // 10

        // ── T3-5. Top-down memoization ──
        // Recursive solution + HashMap cache. Equivalent to bottom-up DP but easier
        // to write when the recurrence has irregular or sparse access.
        // Without memo: O(2^n). With memo: O(n) for Fibonacci.
        Map<Integer, Long> memo = new HashMap<>();
        System.out.println("T3-5 Fib(10) memoized: " + fibMemo(10, memo)); // 55

        // ── T3-6. Fast & slow pointers — cycle detection ──
        // slow moves 1 step, fast moves 2. They meet only if a cycle exists. O(n), O(1).
        // Simulated: next[i] = next node index. 0→1→2→3→4→2 (cycle at 2).
        int[] nxt = {1, 2, 3, 4, 2};
        int slow = 0, fast = 0;
        do { slow = nxt[slow]; fast = nxt[nxt[fast]]; } while (slow != fast);
        System.out.println("T3-6 cycle meeting point: " + slow);

        // ── T3-7. Monotonic deque — sliding window maximum ──
        // Deque stores indices in DECREASING value order; front = current window max.
        // Before adding index x:
        //   1. Evict out-of-window indices from front.
        //   2. Evict smaller indices from back (they can never be the future max).
        // Each index added/removed at most once → O(n).
        int[] swArr = {1, 3, -1, -3, 5, 3, 6, 7};
        int swK = 3;
        int[] swMax = new int[swArr.length - swK + 1];
        Deque<Integer> mDeq = new ArrayDeque<>();
        for (int x = 0; x < swArr.length; x++) {
            while (!mDeq.isEmpty() && mDeq.peekFirst() < x - swK + 1) { mDeq.pollFirst(); }
            while (!mDeq.isEmpty() && swArr[mDeq.peekLast()] < swArr[x]) { mDeq.pollLast(); }
            mDeq.offerLast(x);
            if (x >= swK - 1) { swMax[x - swK + 1] = swArr[mDeq.peekFirst()]; }
        }
        System.out.println("T3-7 sliding window max k=" + swK + ": " + Arrays.toString(swMax));
        // [3,3,5,5,6,7]

        // ── T3-8. TreeMap / TreeSet ──
        // Red-black tree: O(log n) per op; keys always sorted.
        // Key API (memorise these for interviews):
        //   floorKey(k)   → greatest key ≤ k       lowerKey(k)   → greatest key < k
        //   ceilingKey(k) → smallest key ≥ k       higherKey(k)  → smallest key > k
        //   firstKey() / lastKey() → min / max key
        TreeMap<Integer, String> tm = new TreeMap<>();
        tm.put(1,"one"); tm.put(3,"three"); tm.put(5,"five"); tm.put(7,"seven");
        System.out.println("T3-8 floorKey(4)="   + tm.floorKey(4)   + "  ceilingKey(4)=" + tm.ceilingKey(4));
        System.out.println("T3-8 lowerKey(5)="   + tm.lowerKey(5)   + "  higherKey(5)="  + tm.higherKey(5));

        TreeSet<Integer> ts = new TreeSet<>(Arrays.asList(1, 3, 5, 7));
        System.out.println("T3-8 floor(4)=" + ts.floor(4) + "  ceiling(4)=" + ts.ceiling(4));


        // ════════════════════════════════════════════════════════════════
        //  TIER 4 — Good to know (occasional / niche)
        // ════════════════════════════════════════════════════════════════

        // ── T4-1. DFS — iterative (explicit stack) ──
        // Same traversal as recursive DFS but avoids call-stack overflow on deep graphs.
        boolean[] dfsVis = new boolean[graph.length];
        Deque<Integer> dfsStk = new ArrayDeque<>();
        dfsStk.push(0); dfsVis[0] = true;
        System.out.print("T4-1 DFS iterative: ");
        while (!dfsStk.isEmpty()) {
            int node = dfsStk.pop();
            System.out.print(node + " ");
            for (int nb : graph[node]) {
                if (!dfsVis[nb]) { dfsVis[nb] = true; dfsStk.push(nb); }
            }
        }
        System.out.println();

        // ── T4-2. DFS — recursive ──
        // Cleaner when you need backtracking (mark/unmark on enter/exit).
        int[][] edges22 = {{0,1},{0,2},{1,3},{2,4}};
        List<List<Integer>> g2 = new ArrayList<>();
        for (int v = 0; v < 5; v++) { g2.add(new ArrayList<>()); }
        for (int[] e : edges22) { g2.get(e[0]).add(e[1]); g2.get(e[1]).add(e[0]); }
        boolean[] vis22 = new boolean[5];
        System.out.print("T4-2 DFS recursive: ");
        dfsRec(g2, 0, vis22);
        System.out.println();

        // ── T4-3. Quick select — Kth smallest ──
        // Average O(n), worst O(n²). Like quicksort but only recurse into the
        // partition containing index k; discard the other side entirely.
        int[] qArr = {7, 2, 1, 6, 5, 3, 4, 8};
        System.out.println("T4-3 3rd smallest: " + quickSelect(qArr, 0, qArr.length-1, 2)); // 3

        // ── T4-4. Merge sort ──
        // Stable, O(n log n) guaranteed. Preferred when stability matters or when
        // counting inversions. Divide → sort halves → merge.
        int[] msArr = {5, 2, 8, 1, 9, 3};
        mergeSort(msArr, 0, msArr.length - 1);
        System.out.println("T4-4 merge sorted: " + Arrays.toString(msArr)); // [1,2,3,5,8,9]

        // ── T4-5. Dutch National Flag — 3-way partition ──
        // One pass, O(n), O(1). Three zones: [0..lo) = 0s, [lo..mid) = 1s, (hi..] = 2s.
        // Do NOT advance mid after swapping with hi — received value is uninspected.
        int[] dnf = {2, 0, 2, 1, 1, 0};
        int dnfLo = 0, dnfMid = 0, dnfHi = dnf.length - 1;
        while (dnfMid <= dnfHi) {
            if      (dnf[dnfMid] == 0) { int t=dnf[dnfLo]; dnf[dnfLo]=dnf[dnfMid]; dnf[dnfMid]=t; dnfLo++; dnfMid++; }
            else if (dnf[dnfMid] == 1) { dnfMid++; }
            else                       { int t=dnf[dnfMid]; dnf[dnfMid]=dnf[dnfHi]; dnf[dnfHi]=t; dnfHi--; }
        }
        System.out.println("T4-5 DNF sorted: " + Arrays.toString(dnf)); // [0,0,1,1,2,2]

        // ── T4-6. Bit manipulation ──
        // Power-of-2 check:  (n & (n-1)) == 0   — clears the lowest set bit; 0 if only one bit set
        // Get bit i:         (n >> i) & 1
        // Set bit i:         n | (1 << i)
        // Clear bit i:       n & ~(1 << i)
        // XOR trick:         a^a = 0, so duplicate pairs cancel → unique element survives
        int bn = 12;
        System.out.println("T4-6 12 power of 2: " + ((bn & (bn-1)) == 0) + "  bit count: " + Integer.bitCount(bn));
        int[] xorArr = {4, 1, 2, 1, 2};
        int single = 0;
        for (int num : xorArr) { single ^= num; }
        System.out.println("T4-6 single number: " + single); // 4

        // ── T4-7. Cyclic sort ──
        // Array contains integers in [1..n]. Swap value v to index v-1.
        // After sorting: any position where cyclic[i] != i+1 is a missing/duplicate.
        int[] cyc = {3, 1, 5, 4, 2};
        int ci = 0;
        while (ci < cyc.length) {
            int correct = cyc[ci] - 1;
            if (cyc[ci] != cyc[correct]) { int t=cyc[ci]; cyc[ci]=cyc[correct]; cyc[correct]=t; }
            else { ci++; }
        }
        System.out.println("T4-7 cyclic sorted: " + Arrays.toString(cyc)); // [1,2,3,4,5]

        // ── T4-8. Anagram detection ──
        // Sort both strings and compare — O(n log n).
        // Frequency array — O(n) time, O(1) space (preferred).
        String sa = "listen", sb2 = "silent";
        int[] af = new int[26];
        for (char c : sa.toCharArray()) { af[c - 'a']++; }
        for (char c : sb2.toCharArray()) { af[c - 'a']--; }
        boolean isAnagram = true;
        for (int f : af) { if (f != 0) { isAnagram = false; break; } }
        System.out.println("T4-8 \"" + sa + "\" & \"" + sb2 + "\" anagram: " + isAnagram);

        // ── T4-9. Deque — stack / queue / max-heap ──
        // ArrayDeque beats Stack (no synchronization overhead) and LinkedList (no pointer overhead).
        Deque<Integer> deqS = new ArrayDeque<>();
        deqS.push(1); deqS.push(2); deqS.push(3);
        System.out.println("T4-9 stack pop (LIFO): " + deqS.pop()); // 3

        Deque<Integer> deqQ = new ArrayDeque<>();
        deqQ.offer(1); deqQ.offer(2); deqQ.offer(3);
        System.out.println("T4-9 queue poll (FIFO): " + deqQ.poll()); // 1

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.offer(3); maxHeap.offer(1); maxHeap.offer(4);
        System.out.println("T4-9 max-heap peek: " + maxHeap.peek()); // 4

        // ── T4-10. GCD / LCM ──
        // Euclidean GCD: gcd(a,b) = gcd(b, a%b) → O(log min(a,b)).
        // LCM: a/gcd(a,b)*b — divide BEFORE multiplying to avoid long overflow.
        System.out.println("T4-10 GCD(12,8)=" + gcd(12,8) + "  LCM(4,6)=" + lcm(4,6));

        // ── T4-11. Fast exponentiation (power mod) ──
        // base^exp % mod in O(log exp) via repeated squaring.
        // If exp is odd: multiply result by base. Square base each step. Halve exp.
        System.out.println("T4-11 2^10 % 1000 = " + powMod(2, 10, 1000)); // 24

        // ── T4-12. Sieve of Eratosthenes ──
        // All primes ≤ n in O(n log log n). Start inner loop at x*x — smaller
        // multiples were already crossed off by earlier primes.
        int sieveN = 30;
        boolean[] composite = new boolean[sieveN + 1];
        for (int x = 2; (long)x * x <= sieveN; x++) {
            if (!composite[x]) {
                for (int mul = x*x; mul <= sieveN; mul += x) { composite[mul] = true; }
            }
        }
        List<Integer> primes = new ArrayList<>();
        for (int x = 2; x <= sieveN; x++) { if (!composite[x]) { primes.add(x); } }
        System.out.println("T4-12 primes ≤ " + sieveN + ": " + primes);

        // ── T4-13. Swap / reverse (utility) ──
        int[] arr = {1, 2, 3, 4, 5};
        int tmp = arr[1]; arr[1] = arr[3]; arr[3] = tmp;      // swap indices 1 and 3
        System.out.println("T4-13 after swap:    " + Arrays.toString(arr));
        int lo = 0, hi = arr.length - 1;
        while (lo < hi) { int t=arr[lo]; arr[lo]=arr[hi]; arr[hi]=t; lo++; hi--; }
        System.out.println("T4-13 after reverse: " + Arrays.toString(arr));
    }


    // ════════════════════════════════════════════════════════════════
    //  HELPER METHODS
    // ════════════════════════════════════════════════════════════════

    // T1-6 — recursive tree traversals
    static void inorder(TreeNode n) {
        if (n == null) { return; }
        inorder(n.left); System.out.print(n.val + " "); inorder(n.right);
    }
    static void preorder(TreeNode n) {
        if (n == null) { return; }
        System.out.print(n.val + " "); preorder(n.left); preorder(n.right);
    }
    static void postorder(TreeNode n) {
        if (n == null) { return; }
        postorder(n.left); postorder(n.right); System.out.print(n.val + " ");
    }

    // T2-2 — backtracking (permutations)
    static void backtrack(List<List<Integer>> res, List<Integer> cur,
                          int[] nums, boolean[] used) {
        if (cur.size() == nums.length) { res.add(new ArrayList<>(cur)); return; }
        for (int idx = 0; idx < nums.length; idx++) {
            if (used[idx]) { continue; }
            used[idx] = true;
            cur.add(nums[idx]);
            backtrack(res, cur, nums, used);
            cur.remove(cur.size() - 1); // undo choice
            used[idx] = false;
        }
    }

    // T3-1 — Union-Find: path halving + union by rank
    static int ufFind(int[] p, int x) {
        while (p[x] != x) { p[x] = p[p[x]]; x = p[x]; }
        return x;
    }
    static void ufUnion(int[] p, int[] r, int x, int y) {
        int px = ufFind(p, x), py = ufFind(p, y);
        if (px == py) { return; }
        if (r[px] < r[py]) { int t = px; px = py; py = t; }
        p[py] = px;
        if (r[px] == r[py]) { r[px]++; }
    }

    // T3-5 — memoized Fibonacci
    static long fibMemo(int n, Map<Integer, Long> memo) {
        if (n <= 1) { return n; }
        if (memo.containsKey(n)) { return memo.get(n); }
        long val = fibMemo(n-1, memo) + fibMemo(n-2, memo);
        memo.put(n, val);
        return val;
    }

    // T4-2 — recursive DFS
    static void dfsRec(List<List<Integer>> adj, int node, boolean[] vis) {
        vis[node] = true;
        System.out.print(node + " ");
        for (int nb : adj.get(node)) { if (!vis[nb]) { dfsRec(adj, nb, vis); } }
    }

    // T4-3 — quick select (Lomuto partition)
    static int quickSelect(int[] arr, int lo, int hi, int k) {
        int pivot = arr[hi], store = lo;
        for (int x = lo; x < hi; x++) {
            if (arr[x] <= pivot) { int t=arr[store]; arr[store]=arr[x]; arr[x]=t; store++; }
        }
        int t = arr[store]; arr[store] = arr[hi]; arr[hi] = t;
        if (store == k) { return arr[store]; }
        return store < k ? quickSelect(arr, store+1, hi, k)
                         : quickSelect(arr, lo, store-1, k);
    }

    // T4-4 — merge sort
    static void mergeSort(int[] arr, int lo, int hi) {
        if (lo >= hi) { return; }
        int mid = lo + (hi - lo) / 2;
        mergeSort(arr, lo, mid);
        mergeSort(arr, mid+1, hi);
        mergeSorted(arr, lo, mid, hi);
    }
    static void mergeSorted(int[] arr, int lo, int mid, int hi) {
        int[] tmp = Arrays.copyOfRange(arr, lo, hi+1);
        int l = 0, r = mid - lo + 1;
        for (int x = lo; x <= hi; x++) {
            if (l > mid - lo) { arr[x] = tmp[r++]; }
            else if (r > hi  - lo) { arr[x] = tmp[l++]; }
            else if (tmp[l] <= tmp[r]) { arr[x] = tmp[l++]; }
            else { arr[x] = tmp[r++]; }
        }
    }

    // T4-10 — GCD / LCM
    static int  gcd(int a, int b)   { return b == 0 ? a : gcd(b, a % b); }
    static long lcm(long a, long b) { return a / gcd((int)a, (int)b) * b; }

    // T4-11 — fast exponentiation
    static long powMod(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) { result = result * base % mod; }
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }
}
