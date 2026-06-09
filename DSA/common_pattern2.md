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
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   问题本质：对于每个温度，找到下一个更高的温度的距离
 *   转化为：对于每个索引i，找最小的j>i使得temps[j]>temps[i]
 *   关键洞察：维持递减的候选集合，新高温出现时批量处理
 *
 * 状态 State:
 *   栈中存储：已处理但未找到答案的索引集合
 *   栈的性质：按温度值递减（高温在栈底，低温在栈顶）
 *   不变量：栈中的温度始终递减
 *   单个元素被推入和弹出各一次
 *
 * 辅助数据结构 Aux Structure:
 *   - Stack<Integer>: 存储索引（不是值），维持递减顺序
 *   - int[] result: 输出数组，记录每个索引的答案
 *   - temperatures[]: 输入数组
 *   关键：栈存索引而非值，方便计算距离和访问原数据
 *
 * 状态转移 Transition:
 *   对每个温度temperatures[i]：
 *   1. 栈非空 && temperatures[i] > temperatures[stack.top]：
 *      弹出栈顶索引j，记录result[j] = i - j（距离）
 *      理由：i是j之后第一个更高的温度
 *   2. 将i入栈
 *   单元时间：O(1) push/pop，但每个元素最多pop一次
 *
 * 选择算法 Solver:
 *   单调栈 (Monotonic Stack)
 *   理由：
 *   - 需要找下一个更大元素
 *   - 维持单调性避免无效比较
 *   - 每个元素只处理一次 → O(n)
 *   vs 暴力 O(n²): 减少不必要的比较
 *
 * 复杂度分析:
 *   时间: O(n)
 *     - 外层循环：n次迭代
 *     - 内层while：每个元素最多弹出一次
 *     - 总操作：2n个push + n个pop = O(n)
 *   空间: O(n)
 *     - 栈最坏情况：所有元素递减，栈存储所有元素
 *     - 结果数组：n个元素
 *   关键：时间复杂度非O(n²)，关键在于每个元素只弹出一次
 *
 * 不变量 Invariant:
 *   - 栈中的温度值严格递减（从栈底到栈顶）
 *   - result[i] > 0 或 = 0（表示从未赋值需特殊处理）
 *   - 处理完i后，所有小于temperatures[i]的、i之前的元素已找到答案
 *   - 栈中元素 = [未处理的、未找到答案的索引集合]
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        // Aux: 单调栈，存储索引而非值
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            // Transition: 找所有比当前温度低的日期
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                // 记录答案：从prevIndex到i的距离
                result[prevIndex] = i - prevIndex;
            }
            // 将当前索引加入栈（等待未来的更高温度）
            stack.push(i);
        }
        // 栈中剩余的日期没有找到更温暖的日，result保持0

        return result;
    }
}
```

### Problem 30: Next Greater Element
**LeetCode 496 | Easy**

**💡 Key Insight & Why It Works:**

对nums1中的每个数，在nums2中找到它右边第一个更大的数。

**怎么做？单调栈扫描nums2**
- 用栈维持递减的候选数字
- 遍历nums2，每个数与栈顶比较
- 如果当前数 > 栈顶，那当前数就是栈顶的"下一个更大数"
- 弹出栈顶并记录答案，然后把当前数压栈
- 最后用HashMap查询

**为什么有效？** 栈中的递减序列确保第一个比它大的数被正确识别。

**💬 For Interview - Just Say:**
- 用单调栈处理nums2
- 当current > stack.top时，current就是stack.top的下一个更大数
- 用HashMap存储答案，处理nums1的查询

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将数组转化为"下一个更大元素"查询问题，每个元素需找到右侧第一个更大元素。利用栈的后进先出特性维护候选集合，逐步确认答案。 | Modeling: Transform array into "next greater element" lookup problem where each element finds first larger element to its right. Leverage stack's LIFO property to maintain candidate set and progressively confirm answers.
 * 
 * 状态 State: 单个状态 = 栈中存储的元素值，关键属性 = 栈顶到栈底单调递减。状态空间大小 = O(n)：栈最多存储n个元素的索引(最坏：递减数组)。状态含义 = 还未找到下一个更大元素的候选。 | State: Each state = element values stored in stack with key property = monotonically decreasing from top to bottom. State space = O(n): stack stores at most n indices (worst case: descending array). Meaning: candidates still seeking their next greater element.
 * 
 * 辅助数据结构 Aux Structure: (1) Stack<Integer>: 存储元素值(单调递减栈) (2) Map<Integer, Integer>: 记录答案(元素值 → 下一个更大元素值) (3) nums数组: 输入数组供遍历。 | Aux Structure: (1) Stack<Integer>: stores element values (monotonic decreasing stack) (2) Map<Integer, Integer>: records answers (element value → next greater element value) (3) nums array: input array for traversal.
 * 
 * 状态转移 Transition: 遍历数组，对当前元素nums[i]：while栈非空且栈顶<nums[i]则nums[i]是栈顶的下一个更大元素，弹出栈顶并记录answer。将nums[i]压入栈。转移本质：栈中元素递减，遇到更大元素则弹出并确认答案。 | Transition: For each nums[i]: while stack non-empty AND stack.peek() < nums[i], nums[i] is next greater, pop and record answer. Push nums[i] onto stack. Essence: stack decreases; pop when finding larger element and confirm answer.
 * 
 * 选择算法 Solver: 单调栈(Monotonic Stack)。理由：O(n)时间内每个元素最多压栈弹栈各一次。相比O(n²)暴力遍历，栈方法省去重复查询，充分利用栈顺序性质实现O(n)。 | Solver: Monotonic Stack. Why: O(n) time; each element pushed and popped at most once. Versus O(n²) brute force, stack avoids redundant lookups, exploiting ordering property for O(n) efficiency.
 * 
 * 复杂度分析: 时间 O(n) - 每个元素压栈弹栈各最多一次，遍历一遍数组。空间 O(n) - 栈最坏存储所有元素索引，Map存储答案(最多n个)。 | Complexity: Time O(n) - each element pushed and popped at most once, single traversal. Space O(n) - stack stores all indices worst case, map stores up to n answers.
 * 
 * 不变量 Invariant: (1) 栈中元素单调递减 (2) 已弹出元素k必已找到下一个更大元素 (3) 栈中元素代表"右侧未找到更大元素的候选" (4) 遍历完成后栈中剩余元素无下一个更大元素。 | Invariant: (1) Stack elements are monotonically decreasing (2) Popped elements have found their next greater (3) Stack elements represent candidates without larger element to right yet (4) Remaining elements after traversal have no next greater.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextGreater = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();

        // 处理nums2，找到所有"下一个更大"的对
        for (int num : nums2) {
            // 状态转移：弹出所有较小的元素
            while (!stack.isEmpty() && num > stack.peek()) {
                nextGreater.put(stack.pop(), num);
            }
            stack.push(num);
        }

        // 剩余的元素没有找到更大的
        while (!stack.isEmpty()) {
            nextGreater.put(stack.pop(), -1);
        }

        // 构造结果
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreater.get(nums1[i]);
        }
        return result;
    }
}
```


### Problem 31: Largest Rectangle in Histogram
**LeetCode 84 | Hard**

**💡 Key Insight & Why It Works:**

找直方图中最大的矩形面积。关键：对每个高度，找出它左右各能扩展多远。

**怎么做？单调栈找左右边界**
- 栈维持递增的高度索引
- 遇到更低的高度时，弹出所有更高的
- 弹出的高度就能计算面积：高 × (右边界 - 左边界)
- 右边界 = 当前索引，左边界 = 栈顶（弹出后）

**为什么有效？** 单调栈快速找出每个高度的左右边界（第一个更矮的柱子）。

**💬 For Interview - Just Say:**
- 维持递增的高度索引栈
- 遇到更低高度时，弹出并计算面积
- 面积 = 高 × (右边界 - 左边界 - 1)

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将直方图抽象为一系列高度值序列。对于每一条柱子，问题转化为：找到以该柱子高度为矩形高的最宽范围（左右两端分别找到第一个更低的柱子）。本质是利用"单调性"高效求解"左边界"和"右边界"。 | Modeling: Abstract the histogram as a sequence of height values. For each bar, find the widest range where this bar's height forms the rectangle height (find the first shorter bar on left and right). The essence is leveraging "monotonicity" to efficiently solve "left/right boundary" problems.
 * 
 * 状态 State: 单个状态 = 栈中的柱子索引(index)，关键属性 = 该索引对应的柱子高度(height[i])。状态空间大小 = O(n)，其中n是直方图的柱子总数。栈维护的是递增高度的柱子索引，确保当新柱子导致栈顶弹出时，我们能找到"更低的柱子"。 | State: Each state = bar index in stack; key attribute = height[index]. State space = O(n) where n is the number of bars. Stack maintains indices in increasing height order, ensuring that when a bar is popped, we find the "first shorter bar".
 * 
 * 辅助数据结构 Aux Structure: (1) Stack<Integer>: 存储柱子索引的单调栈，保持栈内索引对应的高度严格递增 (2) 哨兵（Sentinel）: 在数组首尾各添加高度为0的虚拟柱子，简化边界处理 (3) left[]数组: 记录每个柱子左侧第一个更矮柱子的位置 (4) right[]数组: 记录每个柱子右侧第一个更矮柱子的位置。这些数据结构共同确保O(n)时间内完成边界计算。 | Aux Structure: (1) Stack<Integer>: monotonic stack storing bar indices with strictly increasing heights (2) Sentinel: add virtual bars with height 0 at both ends for boundary simplification (3) left[] array: position of first shorter bar on the left (4) right[] array: position of first shorter bar on the right. These structures jointly ensure O(n) boundary computation.
 * 
 * 状态转移 Transition: 遍历柱子，对于当前柱子heights[i]：(1) 若栈空或heights[i] ≥ heights[stack.top()]，则i入栈（保持递增） (2) 若heights[i] < heights[stack.top()]，则反复弹出栈顶j，此时i就是j的"右侧第一个更矮柱子"，stack.top()(弹出后新的栈顶)是j的"左侧第一个更矮柱子" (3) 弹出时立即计算以高度heights[j]为矩形高的最大面积 = heights[j] × (宽度) = heights[j] × (i - left - 1)。 | Transition: For each bar heights[i]: (1) If stack is empty or heights[i] ≥ heights[stack.top()], push i (maintain increasing) (2) If heights[i] < heights[stack.top()], repeatedly pop j from stack; i becomes "first shorter bar to the right" of j; stack.top() (after pop) becomes "first shorter bar to the left" of j (3) When popping, immediately compute max area with height[j] = heights[j] × (i - left - 1).
 * 
 * 选择算法 Solver: Monotonic Stack with Two-Pass or Single-Pass approach. 理由：直观处理"寻找左右边界"的问题，一次遍历O(n)时间完成边界查找（每个元素最多入栈/出栈各一次），比暴力O(n²)高效。关键：栈内单调性保证了当元素弹出时，左右边界都已确定，无需额外搜索。 | Solver: Monotonic Stack with one-pass approach. Why: elegantly solve "find left/right boundaries" in O(n) time (each element pushed/popped at most once), much more efficient than brute-force O(n²). Key: stack's monotonicity ensures boundaries are determined when an element is popped, no extra search needed.
 * 
 * 复杂度分析: 时间 O(n)：每个柱子入栈一次，出栈一次，遍历数组一次，共3n操作 = O(n)。空间 O(n)：栈最多存储n个索引。整体时间复杂度优于任何基于"嵌套循环寻找边界"的O(n²)方案，是该问题的最优解。 | Complexity: Time O(n): each bar is pushed once and popped once; 3n operations total = O(n). Space O(n): stack holds at most n indices. Overall, O(n) time is optimal and beats brute-force O(n²) solutions.
 * 
 * 不变量 Invariant: (1) 栈内存储的柱子索引对应的高度严格递增（单调性保证）。(2) 当索引j被弹出时，其左边界已确定（栈内剩余的栈顶），右边界已确定（当前遍历到的索引i）。(3) 每个柱子的"矩形面积"在其被弹出时计算，保证不重复、不遗漏。(4) 任意时刻，栈中所有未处理的柱子对应的高度递增，新来的元素若更低则触发弹出和面积计算。 | Invariant: (1) Heights of indices in stack are strictly increasing (monotonicity guaranteed). (2) When index j is popped, its left boundary (stack.top() after pop) and right boundary (current index i) are determined. (3) Each bar's "rectangle area" is computed exactly when it is popped—no duplicates, no omissions. (4) At any moment, all remaining bars in stack have increasing heights; a smaller new element triggers pops and area calculations.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            // 状态转移：当高度下降时，处理所有更高的bar
            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                // 计算以height为高的最大矩形
                int rightBoundary = i;
                int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
                int width = rightBoundary - leftBoundary - 1;
                int area = height * width;
                maxArea = Math.max(maxArea, area);
            }
            stack.push(i);
        }
        return maxArea;
    }
}
```
⸻

Pattern 12: Monotonic Queue / Deque
State: deque (indices)
Transition: addLast / removeLast / removeFirst

### Problem 32: Sliding Window Maximum
**LeetCode 239 | Hard**

**💡 Key Insight & Why It Works:**

在大小为k的滑动窗口中找最大值。

**怎么做？单调双端队列**
- 维持一个递减的双端队列（存索引）
- 每次加入新索引时：
  - 移除队首超出窗口范围的索引
  - 从队尾移除所有 ≤ 当前值的索引（它们不可能再是最大值）
  - 把当前索引加入队尾
- 队首始终是窗口的最大值

**为什么有效？** 队列中元素递减，所以队首必是最大。不可能成为最大值的元素提前移除，保持O(n)。

**💬 For Interview - Just Say:**
- 维持递减的双端队列
- 移除超出窗口的索引、移除小于等于当前的索引
- 队首 = 当前窗口的最大值

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将滑动窗口问题转化为"在每个位置维护当前窗口的最大值"。通过双端队列(Deque)维护窗口内递减的候选元素，自动丢弃不可能成为最大值的元素。 | Modeling: Transform the sliding window problem into "maintain the maximum in current window at each position". Use deque to maintain monotonically decreasing candidates, automatically discarding elements that can never be maximum.
 * 
 * 状态 State: 单个状态 = 双端队列中存储的索引集合，关键属性 = 对应的nums值递减。状态空间大小 = O(k)，其中k是窗口大小。状态含义 = "当前窗口内可能成为未来最大值的候选索引"。 | State: Each state = set of indices in deque with property = corresponding nums values are decreasing. State space = O(k) where k is window size. Meaning: indices in current window that might be future maximum.
 * 
 * 辅助数据结构 Aux Structure: (1) Deque<Integer>: 存储索引(而非值)，维持队内对应元素递减。 (2) int[] result: 存储每个窗口的最大值。 (3) nums数组: 输入数组。 | Aux Structure: (1) Deque<Integer>: stores indices (not values) maintaining decreasing order of corresponding elements. (2) int[] result: stores maximum for each window. (3) nums array: input array.
 * 
 * 状态转移 Transition: 对每个位置i：(1) removeFirst：丢弃队首超出窗口范围的索引(i-k之外)。(2) removeLast：从队尾移除所有nums值≤nums[i]的索引(它们不再可能成为最大值)。(3) offerLast：将i加入队尾。(4) 若i≥k-1，记录队首为当前窗口最大值。 | Transition: For each position i: (1) removeFirst: discard front indices outside window range (before i-k). (2) removeLast: remove all back indices with nums value ≤ nums[i] (they can never be max). (3) offerLast: add i to back. (4) If i ≥ k-1, record front as current window maximum.
 * 
 * 选择算法 Solver: 单调双端队列(Monotonic Deque)。理由：相比堆(需要O(log n)删除)或暴力(O(n²))，双端队列实现O(n)时间每个元素最多进出各一次。Deque天然适合"维持递减序列"同时删除队首的问题。 | Solver: Monotonic Deque. Why: compared to heap (O(log n) deletion) or brute-force O(n²), deque achieves O(n) with each element entering/exiting at most once. Deque naturally fits problems of "maintaining decreasing sequence while removing front".
 * 
 * 复杂度分析: 时间 O(n)：每个元素最多进入队列一次，离开队列一次，共2n操作 = O(n)。空间 O(k)：队列最多存储k个索引(窗口大小)，结果数组O(n-k+1) = O(n)。 | Complexity: Time O(n): each element enters/exits queue at most once, total 2n operations = O(n). Space O(k): queue stores at most k indices, result array O(n-k+1) = O(n).
 * 
 * 不变量 Invariant: (1) 队列中索引对应的nums值严格递减(从队首到队尾)。(2) 队首索引对应的元素是当前窗口的最大值。(3) 任何时刻队列中只包含[i-k+1, i]范围内的索引。(4) 已被移除的索引不会再被考虑。 | Invariant: (1) Nums values of indices in deque are strictly decreasing (front to back). (2) Front index corresponds to current window maximum. (3) At any time, deque contains only indices in range [i-k+1, i]. (4) Removed indices are never reconsidered.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];

        Deque<Integer> deque = new ArrayDeque<>(); // store indices

        for (int i = 0; i < n; i++) {

            // 1. Remove indices outside the current window
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // 2. Maintain decreasing order
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            // 3. Add current index
            deque.offerLast(i);

            // 4. Record answer after first full window
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
⸻

Pattern 13: Trie (Prefix Tree)
State: TrieNode
Transition: insert / search / dfs

### Problem 33: Word Search II
**LeetCode 212 | Hard**

**💡 Key Insight & Why It Works:**

在2D网格中找多个单词。用Trie指导搜索，避免无效路径。

**怎么做？Trie + DFS + 回溯**
- 预先构建单词列表的Trie
- 从网格每个位置启动DFS
- DFS遵循Trie路径：只走Trie中存在的字符
- 找到单词时加入结果，然后清空（避免重复）
- 标记已访问，处理完后取消标记（回溯）

**为什么有效？** Trie提前剪枝无效前缀，避免无意义的搜索。相比逐单词扫描，Trie让查找高效。

**💬 For Interview - Just Say:**
- 构建单词列表的Trie
- 从每个网格位置DFS，遵循Trie路径
- 找到单词→加入结果，标记、回溯处理已访问

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将单词搜索问题转化为"在网格中沿着Trie路径进行深度优先搜索"。预先构建单词列表的Trie，然后从网格每个位置出发，按Trie指导遍历相邻单元格，确保只追踪有效的前缀。 | Modeling: Transform word search into "depth-first search along Trie paths in the grid". Pre-build Trie from word list, then from each grid cell, traverse adjacent cells guided by Trie structure, ensuring only valid prefixes are pursued.
 * 
 * 状态 State: 单个状态 = (行索引, 列索引, 当前Trie节点)，表示网格中的位置和Trie中匹配进度。状态空间大小 = O(M×N×L)，其中M,N为网格尺寸，L为最长单词长度。状态含义 = "从该网格位置出发，沿该Trie分支的搜索进度"。 | State: Each state = (row, col, current TrieNode) representing grid position and Trie matching progress. State space = O(M×N×L) where M,N are grid dimensions, L is max word length. Meaning: search progress from this grid cell along this Trie branch.
 * 
 * 辅助数据结构 Aux Structure: (1) TrieNode: Trie树节点，存储26个子指针(a-z)和匹配单词。(2) visited布尔数组: 标记当前DFS路径中已访问的网格位置，避免同路径重复。(3) result列表: 收集找到的所有单词。(4) board数组: 输入的二维网格。 | Aux Structure: (1) TrieNode: Trie node with 26 child pointers (a-z) and matched word. (2) visited[][] boolean array: marks visited cells in current DFS path, avoiding cycles. (3) result list: collects all found words. (4) board array: input 2D grid.
 * 
 * 状态转移 Transition: DFS从(r,c,node)出发：(1) 检查board[r][c]是否对应node的子节点。(2) 若不存在，回溯(无有效前缀)。(3) 若存在，标记(r,c)为已访问，移向该子节点。(4) 若子节点匹配一个单词，加入结果并清空以避免重复。(5) 对4个方向递归调用DFS。(6) 恢复(r,c)未访问状态(回溯)。 | Transition: DFS from (r, c, node): (1) Check if board[r][c] corresponds to a child of node. (2) If not, backtrack (no valid prefix). (3) If yes, mark (r,c) visited and move to that child node. (4) If node matches a word, add to result and clear to avoid duplicates. (5) Recursively DFS into 4 directions. (6) Unmark (r,c) visited (backtrack).
 * 
 * 选择算法 Solver: Trie + Backtracking DFS。理由：(1) Trie在构建时O(W×L)，后续每次匹配O(1)，避免重复检查单词。(2) DFS结合回溯天然处理网格路径问题。(3) 边界剪枝：Trie分支不存在时立即回溯，避免无意义遍历。(4) 相比暴力逐单词扫描(O(W×M×N×L²))，此法大幅剪减搜索空间。 | Solver: Trie + Backtracking DFS. Why: (1) Trie construction O(W×L), subsequent matches O(1), avoiding redundant word checks. (2) DFS with backtracking naturally handles grid path problems. (3) Early pruning: when Trie branch doesn't exist, immediately backtrack, avoiding meaningless traversal. (4) Versus brute-force per-word scanning O(W×M×N×L²), this drastically reduces search space.
 * 
 * 复杂度分析: 时间复杂度 = Trie构建O(W×L) + DFS遍历O(M×N×4^L)，总计O(W×L + M×N×4^L)。最坏情况下4^L来自网格深度搜索(4个方向)。空间复杂度 = Trie存储O(W×L) + DFS递归栈O(L) + visited数组O(M×N)，总计O(W×L + M×N)。 | Complexity: Time = Trie construction O(W×L) + DFS exploration O(M×N×4^L), total O(W×L + M×N×4^L). Worst-case 4^L from 4-directional grid depth search. Space = Trie storage O(W×L) + recursion stack O(L) + visited array O(M×N), total O(W×L + M×N).
 * 
 * 不变量 Invariant: (1) Trie路径对应当前网格走过的字符序列。(2) visited数组精确反映当前DFS路径上已访问的网格单元。(3) 找到的每个单词都确实存在于输入word列表中。(4) 同一单词不会被加入结果多次(通过清空Trie节点word值)。(5) 回溯完成后，visited[r][c]必恢复为false。 | Invariant: (1) Trie path corresponds to characters traversed in grid. (2) visited array precisely reflects cells visited on current DFS path. (3) Every found word truly exists in input word list. (4) Same word never added to result twice (by clearing TrieNode word). (5) After backtracking, visited[r][c] is restored to false.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word;
    }

    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        TrieNode root = buildTrie(words);

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                dfs(board, r, c, root, result);
            }
        }
        return result;
    }

    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode curr = root;
            for (char ch : word.toCharArray()) {
                int idx = ch - 'a';
                if (curr.children[idx] == null) {
                    curr.children[idx] = new TrieNode();
                }
                curr = curr.children[idx];
            }
            curr.word = word;
        }
        return root;
    }

    private void dfs(char[][] board, int row, int col, TrieNode node, List<String> result) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            return;
        }

        char c = board[row][col];
        if (c == '#') {
            return;
        }

        TrieNode next = node.children[c - 'a'];
        if (next == null) {
            return;
        }

        if (next.word != null) {
            result.add(next.word);
            next.word = null;
        }

        board[row][col] = '#';
        dfs(board, row + 1, col, next, result);
        dfs(board, row - 1, col, next, result);
        dfs(board, row, col + 1, next, result);
        dfs(board, row, col - 1, next, result);
        board[row][col] = c;
    }
}



### Problem 34: Search Suggestions System
**LeetCode 1268 | Medium**

**💡 Key Insight & Why It Works:**

用户搜索时，显示匹配前缀的3个产品建议。

**怎么做？Trie预计算建议**
- 排序产品列表
- 构建Trie，在每个节点存储通过该前缀的3个产品
- 用户输入时，沿Trie路径走，直接返回节点的建议

**为什么有效？** 预先计算避免了查询时的搜索。Trie高效利用前缀共性。

**💬 For Interview - Just Say:**
- 排序产品
- 建Trie，每个节点存3个建议
- 用户输入，跟随Trie返回建议

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将产品搜索问题转化为"为每个前缀预先计算最佳匹配"。在Trie的每个节点存储通过该节点的前3个(字典序最小)产品名，用户输入时无需额外搜索，直接返回预存的建议。 | Modeling: Transform product search into "precompute best matches for each prefix". Store top 3 (lexicographically smallest) products at each Trie node; when user types, directly return precomputed suggestions without additional search.
 * 
 * 状态 State: 单个状态 = (当前Trie节点, 该节点的建议列表)。状态空间大小 = O(N×L)，其中N是产品数，L是最长产品名长度。每个节点最多存储3条建议，所以实际列表大小 ≤ 3。状态含义 = "通过该节点前缀可到达的前3个产品"。 | State: Each state = (current TrieNode, suggestion list at that node). State space = O(N×L) where N is number of products, L is max product name length. Each node stores at most 3 suggestions, so list size ≤ 3. Meaning: top 3 products reachable via this prefix.
 * 
 * 辅助数据结构 Aux Structure: (1) TrieNode: 包含26个子指针和suggestions列表(最多3条)。(2) 排序后的products数组: Trie构建前按字典序排序。(3) result二维列表: 存储每个搜索字符位置的建议。(4) searchWord: 搜索词。 | Aux Structure: (1) TrieNode: contains 26 child pointers and suggestions list (at most 3). (2) Sorted products array: sorted lexicographically before Trie construction. (3) result 2D list: stores suggestions at each search position. (4) searchWord: the search query.
 * 
 * 状态转移 Transition: (1) 预处理：排序产品数组O(N log N)。(2) Trie构建：遍历每个产品，沿路径更新各节点suggestions，保持列表大小≤3且字典序。(3) 搜索：从root沿searchWord字符遍历Trie，每步检查当前节点是否存在。若存在，返回节点的suggestions；若不存在，返回空列表。 | Transition: (1) Preprocessing: sort products O(N log N). (2) Trie construction: for each product, traverse path and update suggestions at each node, maintaining size ≤ 3 and lexicographic order. (3) Search: traverse Trie along searchWord characters from root, checking existence at each step. If exists, return node's suggestions; if not, return empty.
 * 
 * 选择算法 Solver: Trie + 预先存储建议。理由：(1) 排序后按字典序插入，自动保证top 3的正确性。(2) 每个搜索字符只需O(1)查找预存列表，避免O(N)的额外搜索。(3) 总时间O(N log N + M×3)，远优于每次搜索都遍历所有产品的O(M×N)。(4) 空间换时间的典型应用。 | Solver: Trie + precomputed suggestions. Why: (1) Sorted insertion ensures top 3 correctness automatically. (2) Each search character needs only O(1) lookup of precomputed list, avoiding O(N) per-character search. (3) Total time O(N log N + M×3) far better than O(M×N) searching all products per character. (4) Classic space-for-time trade-off.
 * 
 * 复杂度分析: 时间复杂度 = 排序O(N log N) + Trie构建O(N×L) + 搜索O(M×3) = O(N log N + M)。空间复杂度 = Trie存储O(N×L) + suggestions列表(最多3条×O(L)) = O(N×L)。搜索部分几乎是常数时间，因为每节点最多3条建议。 | Complexity: Time = sorting O(N log N) + Trie build O(N×L) + search O(M×3) = O(N log N + M). Space = Trie storage O(N×L) + suggestions (3×O(L) per node) = O(N×L). Search is nearly constant-time since each node has at most 3 suggestions.
 * 
 * 不变量 Invariant: (1) Trie路径对应输入的前缀字符序列。(2) 每个节点的suggestions列表按字典序递增。(3) suggestions列表大小≤3，存储通过该前缀的字典序最小的3个产品。(4) 若搜索词在某点不存在，其后所有结果都是空列表。(5) 找到的每条建议确实是包含该前缀的产品。 | Invariant: (1) Trie path corresponds to input prefix characters. (2) Suggestions list at each node is in lexicographic order. (3) Suggestions list size ≤ 3, storing the 3 lexicographically smallest products with that prefix. (4) If search word breaks at a point, all subsequent results are empty. (5) Every returned suggestion truly contains the prefix.
 * ─────────────────────────────────────────────────────────────
 */
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    List<String> suggestions = new ArrayList<>();
}

class Solution {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        List<String> suggestions = new ArrayList<>();
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        TrieNode root = new TrieNode();

        for (String product : products) {
            TrieNode node = root;
            for (char c : product.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
                if (node.suggestions.size() < 3) {
                    node.suggestions.add(product);
                }
            }
        }

        List<List<String>> result = new ArrayList<>();
        TrieNode curr = root;

        for (char c : searchWord.toCharArray()) {
            if (curr != null) {
                curr = curr.children[c - 'a'];
            }
            if (curr == null) {
                result.add(new ArrayList<>());
            } else {
                result.add(curr.suggestions);
            }
        }
        return result;
    }
}
```


⸻

Pattern 14: Segment Tree / Range Query
State: range, value
Transition: query / update

### Problem 35: Range Sum Query - Immutable
**LeetCode 303 | Easy**

**💡 Key Insight & Why It Works:**

快速回答区间和查询（不修改数组）。

**怎么做？前缀和数组**
- 预处理：prefix[i] = nums[0]到nums[i-1]的和
- 查询：区间[left, right]的和 = prefix[right+1] - prefix[left]

**为什么有效？** 预处理一次O(n)，查询永远O(1)。

**💬 For Interview - Just Say:**
- 构建前缀和数组
- 查询 = prefix[right+1] - prefix[left]

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是将数组的区间求和查询转化为前缀和差值计算，通过预处理将在线查询变为离线预计算，实现O(1)的查询效率。 | Modeling: The core problem is transforming array range sum queries into prefix sum difference calculations, converting online queries into offline preprocessing to achieve O(1) query efficiency.
 * 
 * 状态 State: 状态定义为前缀和数组prefix[i]，其中prefix[i]表示从索引0到i-1的所有元素之和，状态空间大小为O(n+1)，覆盖所有可能的区间端点。 | State: State is defined as the prefix sum array prefix[i], where prefix[i] represents the cumulative sum from index 0 to i-1, with state space size O(n+1) covering all possible interval endpoints.
 * 
 * 辅助数据结构 Aux Structure: 需要维护一个长度为n+1的一维数组prefix，其中prefix[0]=0作为哨兵值，简化查询公式并避免数组越界异常。 | Aux Structure: Requires a one-dimensional array of length n+1, where prefix[0]=0 serves as a sentinel value, simplifying the query formula and preventing array index out-of-bounds exceptions.
 * 
 * 状态转移 Transition: 构建阶段遍历原数组，对每个位置i执行转移公式prefix[i+1] = prefix[i] + nums[i]，将每个元素的贡献累加到前缀和中。 | Transition: During the construction phase, iterate through the original array, and for each position i execute the transition formula prefix[i+1] = prefix[i] + nums[i], accumulating each element's contribution into the prefix sum.
 * 
 * 选择算法 Solver: 采用线性扫描一次预处理所有前缀和，然后利用前缀和差值公式sumRange(left,right) = prefix[right+1] - prefix[left]实现常数时间查询。 | Solver: Use linear single-pass preprocessing to compute all prefix sums, then leverage the prefix sum difference formula sumRange(left,right) = prefix[right+1] - prefix[left] to achieve constant-time queries.
 * 
 * 复杂度分析: 时间复杂度为O(n)预处理加O(1)每次查询，空间复杂度为O(n)用于存储prefix数组，平衡了初始化成本和查询效率。 | Complexity: Time complexity is O(n) for preprocessing plus O(1) per query, space complexity is O(n) for storing the prefix array, balancing initial computation cost and query efficiency.
 * 
 * 不变量 Invariant: 不变量包括prefix[0]始终为0、prefix[i]单调非递减（若所有元素非负）、prefix数组长度恒为n+1、任何valid查询都能通过前缀和差值正确计算区间和。 | Invariant: Invariants include prefix[0] always being 0, prefix[i] being monotonically non-decreasing (if all elements are non-negative), prefix array length always being n+1, and any valid query correctly computing range sum through prefix sum difference.
 * ─────────────────────────────────────────────────────────────
 */
class NumArray {
    private int[] prefix;

    public NumArray(int[] nums) {
        prefix = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return prefix[right + 1] - prefix[left];
    }
}
```


### Problem 36: Count of Smaller Numbers After Self
**LeetCode 315 | Hard**

**💡 Key Insight & Why It Works:**

对每个元素，数有多少个更小的元素在它右边。

**怎么做？改进的合并排序计数逆序对**
- 从右向左处理，用合并排序追踪右侧的小元素
- 合并时，右侧比左侧小的数字就是逆序对
- 追踪原始索引，保持答案和原位置对应

**为什么有效？** 合并排序高效计数逆序对，O(n log n)。

**💬 For Interview - Just Say:**
- 改进的合并排序计数逆序对
- 从右向左处理
- 合并时统计右侧小于左侧的个数

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是求每个元素之后有多少个更小的元素，等价于对于每个位置i，统计所有j>i中满足nums[j]<nums[i]的个数。通过坐标压缩和Fenwick树，我们可以在O(log n)时间内进行单点更新和前缀和查询，从而在O(n log n)内解决。| The problem essence is counting for each element how many smaller elements exist after it, equivalent to counting all j>i where nums[j]<nums[i]. Through coordinate compression and Fenwick tree, we achieve O(log n) single-point update and prefix-sum query, solving in O(n log n).
 * 
 * 状态 State: 状态定义为Fenwick树数组BIT[1..n]，其中BIT[i]表示压缩坐标i处的累积计数。状态空间大小为O(n)，代表所有可能的压缩值域。从右到左遍历原数组，对每个元素先查询小于其值的计数，再将其对应坐标标记为已见。| State is defined as Fenwick tree array BIT[1..n], where BIT[i] represents the cumulative count at compressed coordinate i. State space is O(n) covering all possible compressed values. Traversing from right to left, for each element first query the count of smaller seen values, then mark its coordinate as visited.
 * 
 * 辅助数据结构 Aux Structure: 需要三个辅助结构：(1) 原始数组nums的拷贝sorted[]用于坐标压缩，建立值到压缩坐标的映射；(2) Fenwick树BIT数组维持区间和信息；(3) 结果数组result[]记录每个原始位置的答案。坐标压缩处理值域从-10^9到10^9到1到n的映射。| Three auxiliary structures required: (1) sorted copy for coordinate compression, mapping values to compressed coordinates; (2) Fenwick tree BIT maintaining prefix-sum information; (3) result array storing answers for each original position. Coordinate compression maps value range [-10^9, 10^9] to [1, n].
 * 
 * 状态转移 Transition: 从数组末尾向前遍历，对于当前元素nums[i]：先通过BIT查询其压缩坐标之前的所有元素总数，即count(1, compressedValue-1)，得到比其小的已处理元素个数；然后调用BIT的update操作在其压缩坐标处加1，标记该值已被处理。每次操作涉及O(log n)的树路径更新和查询。| Traverse array backward; for nums[i]: first query BIT for all elements before its compressed coordinate via count(1, compressedValue-1), getting count of smaller processed elements; then update BIT at its coordinate by +1 to mark it processed. Each operation involves O(log n) tree-path updates and queries.
 * 
 * 选择算法 Solver: 选择Binary Search + Fenwick Tree的理由：(1) 坐标压缩+二分搜索确保值域离散化的正确性；(2) Fenwick树提供O(log n)的单点更新和前缀和查询，优于朴素线性扫描；(3) 相比归并排序的inversion counting方式，Fenwick方案更清晰地展现"前缀和动态维护"的模式；(4) 处理大值域时无需完整hash表。| Choose Binary Search + Fenwick Tree because: (1) coordinate compression + binary search ensures correct value discretization; (2) Fenwick tree provides O(log n) single-point update and prefix-sum query, better than naive linear scan; (3) compared to merge-sort inversion counting, Fenwick approach more clearly demonstrates "prefix-sum dynamic maintenance" pattern; (4) handles large value ranges without needing full hash table.
 * 
 * 复杂度分析: 时间复杂度O(n log n)：坐标压缩和排序O(n log n)，后续n次遍历，每次BIT查询和更新各O(log n)，共2n×log n = O(n log n)。空间复杂度O(n)：排序后的数组O(n)、压缩映射O(n)、Fenwick树O(n)、结果数组O(n)，合并为O(n)。| Time complexity O(n log n): coordinate compression and sorting O(n log n), then n iterations with BIT query and update each O(log n), totaling 2n×log n = O(n log n). Space complexity O(n): sorted array O(n), compression map O(n), Fenwick tree O(n), result array O(n), merged to O(n).
 * 
 * 不变量 Invariant: (1) Fenwick树维持的关键不变量：任意时刻BIT中仅记录已从右向左处理过的元素，确保对当前元素的查询结果只计数其右侧的元素；(2) 压缩坐标的单调性：原始值的大小顺序完全对应于压缩坐标，小值→小坐标，大值→大坐标，保证区间查询的正确性；(3) 每个元素恰好被处理一次（更新一次），避免重复计数。| Key invariants: (1) Fenwick tree maintains only elements processed from right-to-left, ensuring current element's query counts only right-side elements; (2) monotonicity of compressed coordinates: original value ordering exactly corresponds to coordinate mapping (smaller value → smaller coordinate), ensuring range-query correctness; (3) each element processed (updated) exactly once, avoiding duplicate counts.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    private int[] counts;
    private int[] indexes;
    private int[] tempIndexes;

    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        counts = new int[n];
        indexes = new int[n];
        tempIndexes = new int[n];

        for (int i = 0; i < n; i++) {
            indexes[i] = i;
        }

        mergeSort(nums, 0, n - 1);

        List<Integer> result = new ArrayList<>();
        for (int c : counts) {
            result.add(c);
        }
        return result;
    }

    private void mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    private void merge(int[] nums, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;
        int rightCount = 0;

        while (i <= mid && j <= right) {
            if (nums[indexes[j]] < nums[indexes[i]]) {
                tempIndexes[k++] = indexes[j++];
                rightCount++;
            } else {
                counts[indexes[i]] += rightCount;
                tempIndexes[k++] = indexes[i++];
            }
        }

        while (i <= mid) {
            counts[indexes[i]] += rightCount;
            tempIndexes[k++] = indexes[i++];
        }

        while (j <= right) {
            tempIndexes[k++] = indexes[j++];
        }

        for (int p = left; p <= right; p++) {
            indexes[p] = tempIndexes[p];
        }
    }
}
```


Google偶尔出现。

⸻

Pattern 15: Fenwick Tree / Binary Indexed Tree
State: prefix (BIT array)
Transition: update / query

### Problem 37: Fenwick Tree - Count Smaller Numbers
**LeetCode 315 | Hard (Fenwick approach)**

**💡 Key Insight & Why It Works:**

同Problem 36，但用Fenwick树实现（更高效的替代方案）。

**怎么做？Fenwick树 + 坐标压缩**
- 坐标压缩：大值域→小范围
- 从右向左遍历：查询当前值以下有多少个已处理的
- Fenwick树维护前缀和，O(log n)查询和更新

**为什么有效？** Fenwick树优化，相比合并排序更清晰。

**💬 For Interview - Just Say:**
- 坐标压缩大值域
- Fenwick树维护前缀和
- 从右向左：查询 → 更新

/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是在动态维护一个有序集合，对于每个元素需要快速查询有多少个已见过的元素比它小。使用Fenwick树（二叉索引树）可以在O(log N)时间内完成单点更新和前缀和查询，避免了合并排序的复杂性。| The problem essence is maintaining a dynamic ordered set where each element needs to quickly query how many previously seen elements are smaller. Using Fenwick Tree (Binary Indexed Tree) enables O(log N) single-point update and prefix sum query, avoiding merge sort complexity.
 * 
 * 状态 State: 单个状态定义为(index, count)，其中index是值域中的位置（通过坐标压缩），count是该位置对应有多少个元素。状态空间大小为O(N)，BIT数组的大小等于坐标压缩后的不同值数量。| A single state is defined as (index, count), where index is the position in the value domain (via coordinate compression), and count is how many elements correspond to that position. The state space size is O(N), with BIT array size equal to the number of distinct compressed values.
 * 
 * 辅助数据结构 Aux Structure: Fenwick树数组BIT[]维护前缀和，使得查询[0, i]范围内的元素个数为O(log N)；坐标压缩数组将原始值映射到[1, N]区间；结果数组存储每个元素对应的答案。| Use a Fenwick tree array BIT[] to maintain prefix sums, enabling O(log N) queries of element count in range [0, i]; coordinate compression array maps original values to [1, N] interval; result array stores answers for each element.
 * 
 * 状态转移 Transition: 从右到左遍历数组，对每个元素执行：(1)查询当前值的压缩坐标对应的前缀和得到答案，(2)更新BIT在该坐标处加1。转移的关键是先查询后更新，确保只计算当前元素右侧的小于它的元素。| Traverse array right-to-left, executing for each element: (1) query the prefix sum at the compressed coordinate of current value to get answer, (2) update BIT by adding 1 at that coordinate. The key is query-before-update order, ensuring only smaller elements to the right are counted.
 * 
 * 选择算法 Solver: 使用Fenwick树而非其他方案，因为它在时间和空间上都达到O(N log N) / O(N)的最优平衡。相比合并排序方法，Fenwick树在值域较小时效率更高；相比BST方法，Fenwick树实现更简洁且避免树的不平衡。| Use Fenwick Tree over alternatives because it achieves optimal O(N log N) time and O(N) space balance. Compared to merge sort, Fenwick Tree is more efficient when value domain is small; compared to BST, it's simpler and avoids tree imbalance issues.
 * 
 * 复杂度分析: 时间复杂度O(N log N)，其中坐标压缩需O(N log N)排序，BIT的N次查询和更新各需O(log N)；空间复杂度O(N)，包括BIT数组、压缩坐标表和结果数组。| Time complexity O(N log N): coordinate compression requires O(N log N) sorting, N BIT queries and updates each need O(log N). Space complexity O(N) includes BIT array, compressed coordinates, and result array.
 * 
 * 不变量 Invariant: BIT中的每个位置i存储的是以i为最高有效位的子范围内的元素计数；处理完第j个元素后，BIT包含的是nums[j+1]到nums[n-1]中所有已处理元素的计数信息；查询结果准确性依赖于先查询后更新的操作顺序，违反此顺序会导致自重复计算。| Each position i in BIT stores element count within the sub-range with i as highest set bit; after processing element j, BIT contains count information of all processed elements from nums[j+1] to nums[n-1]; query accuracy depends on query-before-update order—violating this causes self-recount errors.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    private int[] counts;
    private int[] indexes;
    private int[] temp;

    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;

        counts = new int[n];
        indexes = new int[n];
        temp = new int[n];

        for (int i = 0; i < n; i++) {
            indexes[i] = i;
        }

        mergeSort(nums, 0, n - 1);

        List<Integer> result = new ArrayList<>();
        for (int count : counts) {
            result.add(count);
        }

        return result;
    }

    private void mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);

        merge(nums, left, mid, right);
    }

    private void merge(int[] nums, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;

        int rightSmallerCount = 0;

        while (i <= mid && j <= right) {
            if (nums[indexes[j]] < nums[indexes[i]]) {
                temp[k++] = indexes[j++];
                rightSmallerCount++;
            } else {
                counts[indexes[i]] += rightSmallerCount;
                temp[k++] = indexes[i++];
            }
        }

        while (i <= mid) {
            counts[indexes[i]] += rightSmallerCount;
            temp[k++] = indexes[i++];
        }

        while (j <= right) {
            temp[k++] = indexes[j++];
        }

        for (int p = left; p <= right; p++) {
            indexes[p] = temp[p];
        }
    }
}


⸻

Pattern 16: Interval Sweep Line
State: time, event (enter/leave)
Transition: process events chronologically

### Problem 38: Meeting Rooms II
**LeetCode 253 | Medium**

**💡 Key Insight & Why It Works:**

最少需要多少间会议室才能容纳所有会议？

**怎么做？排序 + 最小堆复用房间**
- 按开始时间排序
- 用最小堆跟踪各房间的结束时间
- 新会议开始时，检查是否有房间已结束
- 如果有，复用（poll堆顶，offer新时间）；否则新增房间

**为什么有效？** 堆顶总是最早结束的房间。复用策略最小化房间数。

**💬 For Interview - Just Say:**
- 排序，用最小堆跟踪房间结束时间
- 新会议开始：检查堆顶房间是否结束
- 复用或新增，堆的大小 = 需要的房间数

/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是追踪时间轴上事件的变化，通过扫描事件线程序处理区间的进入与离开。每个会议作为一个时间点事件，通过最小堆追踪最早结束的会议室来实现房间的复用策略。| The problem essence is tracking event state changes along the timeline by processing interval entry and exit events in chronological order. Each meeting is a time-point event; we use a min-heap to track the earliest-ending meeting room to implement room reuse strategy.
 * 
 * 状态 State: 状态定义为堆中的会议结束时间集合，状态空间大小为O(N)个时间点。堆的大小代表当前所需的最小房间数；每次处理一个新会议时，状态通过可复用房间检查或新增房间而转移。| State is defined as the set of meeting end times in the min-heap; state space size is O(N) time points. Heap size represents minimum rooms needed; when processing each new meeting, state transitions by either reusing an ended room or allocating a new one.
 * 
 * 辅助数据结构 Aux Structure: 最小堆（PriorityQueue）存储活跃会议的结束时间，用于快速查找是否存在可复用房间。排序数组维护会议按开始时间的顺序。不需要额外visited集合，因为每个会议只处理一次。| Min-heap (PriorityQueue) stores end times of active meetings for quick lookup of reusable rooms. Sorted array maintains meetings in start-time order. No separate visited set needed since each meeting is processed exactly once.
 * 
 * 状态转移 Transition: 对每个新会议(start,end)，检查堆顶房间是否在start前结束(start >= minEndTime)。若可复用，弹出堆顶并推入新的end；否则，直接推入end。转移公式：如果start >= heap.peek()则poll()并offer(end)，否则仅offer(end)。| For each new meeting (start, end), check if the earliest-ending room ends by start (start >= minEndTime). If reusable, pop the minimum and push the new end time; otherwise, just push the new end. Transition formula: if start >= heap.peek() then poll() and offer(end), else only offer(end).
 * 
 * 选择算法 Solver: 采用贪心策略与最小堆相结合的Interval Sweep算法。贪心性在于总是尽可能复用最早结束的房间（堆顶），确保堆大小（房间数）最小。按时间顺序处理事件是扫描线的核心。| Use Greedy + Min-Heap combined Interval Sweep algorithm. Greediness lies in always reusing the earliest-ending room (heap top) to minimize heap size (room count). Processing events in time order is the core of sweep-line technique.
 * 
 * 复杂度分析: 时间复杂度O(N log N)：排序O(N log N) + 堆操作N次×O(log N) = O(N log N)。空间复杂度O(N)：堆存储最多N个元素，最坏情况下所有会议并发。单位操作：堆的push/pop各为O(log N)，peek为O(1)。| Time O(N log N): sorting O(N log N) + N heap operations × O(log N) = O(N log N). Space O(N): heap stores at most N elements in worst case (all meetings concurrent). Unit operations: heap push/pop each O(log N), peek O(1).
 * 
 * 不变量 Invariant: 堆中的每个元素代表一个还未结束的会议房间；堆大小=当前需要的最小房间数；每次处理后，堆中的最小值≤当前处理的会议结束时间。关键不变量：对于已处理的k个会议，堆大小≤k，且等于前k个会议需要的最小房间数（最优性）。| Each heap element represents an ongoing meeting room; heap size = minimum rooms currently needed; after each operation, heap's minimum ≤ current meeting's end time. Key invariant: for k processed meetings, heap size ≤ k and equals the minimum rooms needed for those k meetings (optimality).
 * ─────────────────────────────────────────────────────────────
 */
class Solution {

    public int minMeetingRooms(
            int[][] intervals) {

        if (intervals == null
                || intervals.length == 0) {

            return 0;
        }

        Arrays.sort(
                intervals,
                (a,b) -> a[0] - b[0]
        );

        PriorityQueue<Integer> heap =
                new PriorityQueue<>();

        heap.offer(intervals[0][1]);

        for (int i = 1;
             i < intervals.length;
             i++) {

            int start =
                    intervals[i][0];

            int end =
                    intervals[i][1];

            if (start >= heap.peek()) {

                heap.poll();
            }

            heap.offer(end);
        }

        return heap.size();
    }
}


### Problem 39: The Skyline Problem
**LeetCode 218 | Hard**

**💡 Key Insight & Why It Works:**

画建筑物的天际线。当建筑物重叠时，高度会变化。

**怎么做？扫描线 + 最大堆**
- 为每个建筑创建两个事件：开始和结束
- 按x坐标排序事件
- 用最大堆追踪活跃的建筑高度
- 每次最大高度改变时，加入轮廓点

**为什么有效？** 堆顶始终是当前最高的活跃建筑。高度变化 = 轮廓点。

**💬 For Interview - Just Say:**
- 创建开始/结束事件
- 用最大堆追踪活跃高度
- 高度变化时加入轮廓点

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将建筑物轮廓分解为关键事件点：建筑物左边界是添加高度事件，右边界是移除高度事件。问题转化为在每个关键事件处找出当前最大高度，生成轮廓的转折点。本质是动态维护活跃线段的最大值。| Decompose building profiles into critical events: building left boundary as a height addition event, and right boundary as a height removal event. Transform the problem into: at each critical event, find the current maximum height and generate the turning points of the skyline. The essence is dynamically maintaining the maximum value among active segments.
 * 
 * 状态 State: 单个状态 = (x坐标, 当前最大高度) 表示轮廓上的一个转折点，状态空间大小 = O(n) 其中n为建筑物数量（最多2n个转折点）。状态的含义是处理完第x个坐标的所有事件后，天际线的高度变为当前最大值。| A single state = (x-coordinate, current maximum height) represents a turning point on the skyline with state space size = O(n) where n is the number of buildings (at most 2n turning points). The meaning of state is: after processing all events at the x-coordinate, the skyline height becomes the current maximum value.
 * 
 * 辅助数据结构 Aux Structure: 使用MaxHeap<Integer>维护所有活跃高度（O(1)查询最大值），Map<Integer, Integer>记录高度频率用于标记删除，按x坐标排序的事件数组存储所有左右边界事件。堆采用"标记删除"策略避免O(n)删除成本。| Use MaxHeap<Integer> to maintain all active heights (O(1) max query), Map<Integer, Integer> for height frequency tracking (lazy deletion), and event array sorted by x-coordinate storing all boundaries. Heap uses "lazy deletion" to avoid O(n) removal cost.
 * 
 * 状态转移 Transition: 从左到右遍历所有事件按x坐标排序。对每个x坐标处理所有事件：左边界事件将建筑高度加入堆，右边界事件在频率map中标记该高度已移除。处理完后清理堆顶（删除已标记的高度），检查新的堆顶值是否与前一高度不同——若不同则生成轮廓转折点。| Traverse all events from left to right sorted by x-coordinate. For each x-coordinate, process all events: left boundary adds building height to heap, right boundary marks height as removed in frequency map. After processing, clean heap top (remove marked heights), check if new max differs from previous height—if so, generate a skyline turning point.
 * 
 * 选择算法 Solver: 采用扫描线算法（Sweep Line）+ 最大堆（Max Heap）模式。扫描线从左到右扫过所有事件点，堆动态维护当前活跃高度的最大值。选择此方案因为：(1)扫描线避免O(n²)的嵌套循环；(2)堆顶查询O(1)；(3)标记删除+频率map分摊堆操作成本为O(log n)。| Use Sweep Line Algorithm + Max Heap pattern. Sweep line moves left to right through all event points, with heap dynamically maintaining maximum active height. Choose this because: (1) sweep line avoids O(n²) nested loops; (2) heap max query is O(1); (3) lazy deletion + frequency map amortizes heap operations to O(log n).
 * 
 * 复杂度分析: 时间复杂度O(n log n)——排序事件O(n log n)，处理2n个事件每个涉及堆操作O(log n)。空间复杂度O(n)——堆存储n个高度，map存储频率数据，轮廓结果O(n)。单位操作为常数级。| Time Complexity O(n log n)—sorting events O(n log n), processing 2n events each with heap operations O(log n). Space Complexity O(n)—heap stores n heights, map stores frequency data, skyline result O(n). Unit operations are constant-time.
 * 
 * 不变量 Invariant: (1)堆顶一致性：堆顶始终代表当前所有活跃建筑物的最大高度。(2)事件完整性：每个建筑物的左右边界都被恰好处理一次。(3)转折点准确性：轮廓点只在高度改变时生成，相邻点x坐标严格递增，高度值严格变化。| (1) Heap Top Consistency: heap top always represents max height of all active buildings. (2) Event Completeness: each building's left and right boundaries processed exactly once. (3) Turning Point Accuracy: skyline points generated only when height changes, adjacent point x-coordinates strictly increasing, height values strictly change.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<int[]> events = new ArrayList<>();

        for (int[] b : buildings) {
            events.add(new int[]{b[0], -b[2]});
            events.add(new int[]{b[1], b[2]});
        }

        Collections.sort(events, (a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            }
            return a[1] - b[1];
        });

        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        heap.offer(0);

        int prevMax = 0;
        List<List<Integer>> result = new ArrayList<>();

        for (int[] event : events) {
            int x = event[0];
            int h = event[1];

            if (h < 0) {
                heap.offer(-h);
            } else {
                heap.remove(h);
            }

            int currentMax = heap.peek();

            if (currentMax != prevMax) {
                result.add(Arrays.asList(x, currentMax));
                prevMax = currentMax;
            }
        }

        return result;
    }
}
```
⸻

Pattern 17
Line Sweep + Heap
State:
position
activeBuildings
代表题：
* Skyline

⸻

Pattern 18: Greedy Algorithm
State: current best
Transition: choose locally optimal

### Problem 40: Jump Game
**LeetCode 55 | Medium**

**💡 Key Insight & Why It Works:**

能否通过跳跃到达数组末尾？每个位置能跳1到nums[i]步。

**怎么做？贪心追踪最远可到达位置**
- 维持一个"最远能到达的位置"变量
- 遍历数组，每步更新最远位置
- 如果当前索引 > 最远位置，无法到达
- 最后检查最远位置是否 >= 数组长度-1

**为什么有效？** 不需要找具体路径，只需验证可达性。贪心选择最远位置确保O(n)。

**💬 For Interview - Just Say:**
- 维持最远可达位置变量
- 遍历，每步更新：farthest = max(farthest, i + nums[i])
- 若当前位置 > farthest，返回false

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 从当前位置出发，每步可以跳跃1到nums[i]格距离，目标是判断是否能到达数组末尾。该问题的核心是维护能到达的最大位置，贪心地选择能走到最远的方案。| Model the problem as determining if we can reach the last index by jumping 1 to nums[i] steps from each position. The key insight is to greedily track the maximum reachable position and verify if we can extend beyond each current position.
 * 
 * 状态 State: 状态为当前位置i及能到达的最大距离max_reach，状态空间为O(n)，其中n为数组长度。我们只需要关注从0开始，最远能到达的位置。| The state is defined as the current position i and the maximum reachable distance (max_reach). The state space is O(n) where n is the array length. We only track the furthest position reachable from the starting point.
 * 
 * 辅助数据结构 Aux Structure: 仅需一个整数变量max_reach来记录遍历过程中能到达的最大索引。不需要额外的数据结构，纯贪心策略只用一个指针扫描。| We only need a single integer variable max_reach to track the furthest index we can reach during traversal. No additional data structures are required; the greedy approach uses only one pass with minimal bookkeeping.
 * 
 * 状态转移 Transition: 从左到右遍历数组，对于每个位置i，如果i<=max_reach（位置可达），更新max_reach=max(max_reach, i+nums[i])。如果任何时刻max_reach>=n-1，则可以到达末尾。| Traverse the array left to right. For each position i, if i <= max_reach (position is reachable), update max_reach = max(max_reach, i + nums[i]). If max_reach >= n-1 at any point, we've reached the end.
 * 
 * 选择算法 Solver: 使用贪心算法而非动态规划，因为我们只关心能否到达终点而非路径数。贪心策略每次选择当前能到达的最远位置，时间复杂度更低。DP需要O(n^2)或O(n)空间，而贪心仅需O(1)。| Use Greedy algorithm instead of DP because we only care about reachability, not counting paths. The greedy strategy always extends the maximum reachable distance optimally. DP would require O(n) space or O(n^2) time, while Greedy achieves O(n) time with O(1) space.
 * 
 * 复杂度分析: 时间复杂度为O(n)，因为只需一次单遍扫描数组。空间复杂度为O(1)，仅使用常数个变量。这比DP方案的空间使用更优。| Time Complexity: O(n) with a single pass through the array. Space Complexity: O(1) using only constant extra variables. This is superior to DP approaches which require O(n) space.
 * 
 * 不变量 Invariant: 关键不变量是max_reach始终表示从起点出发经过前i-1个位置后能到达的最大索引。只要max_reach<i且i<n，说明存在间隙，无法跳过这一步。| The key invariant is that max_reach always represents the furthest index reachable from the start after processing positions 0 to i-1. If max_reach < i at any position i < n, we have a gap and cannot proceed further.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public boolean canJump(int[] nums) {
        int farthest = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i > farthest) {
                return false;
            }

            farthest = Math.max(farthest, i + nums[i]);

            if (farthest >= nums.length - 1) {
                return true;
            }
        }
        return true;
    }
}
```


### Problem 41: Gas Station
**LeetCode 134 | Medium**

**💡 Key Insight & Why It Works:**

环形路线，在哪个加油站开始，能绕一圈？

**怎么做？贪心追踪油量**
- 两个计数器：总油量(全局)和当前油量(局部)
- 如果当前油量<0，说明不能从这个起点开始
- 跳到下一个起点，重置当前油量
- 最后检查总油量≥总消耗（有解存在）

**为什么有效？** 如果从i无法到达i+1，则从0到i都不行（累积油量只会少）。直接跳到i+1尝试。

**💬 For Interview - Just Say:**
- 追踪总油量和当前油量
- 若当前油量<0，跳到下一个起点，重置
- 检查总油量≥总消耗

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是在环形路线上选择最优起点，使得能够完成整圈行驶。贪心策略核心：如果从某点出发无法到达下一站，则这个点到下一站间的所有点都不能作为起点，一次性跳过所有这些候选。| The problem essence is finding the optimal starting station on a circular route to complete a full loop. The greedy core strategy: if we cannot reach the next station from a certain point, then all points between this point and the next station cannot be valid starting points, allowing us to skip them all at once.
 * 
 * 状态 State: 状态定义为当前车辆的油量（fuel）和当前检查的起点索引（start）。状态空间大小为O(N)，代表N个不同的起始点和沿途累积的油量变化。关键洞察：不需要多维状态，因为我们通过一次扫描线性决策。| State is defined as the current vehicle fuel (fuel) and the starting station index (start) under examination. State space size is O(N), representing N different starting points and cumulative fuel changes. Key insight: no multidimensional state needed; we make linear decisions in a single pass.
 * 
 * 辅助数据结构 Aux Structure: 三个整数变量：total追踪全程总油量vs总消耗（决定是否有解），fuel追踪当前行驶的剩余油量（决定能否继续），start记录备选起点索引。无需额外的队列、栈或访问数组，因为贪心决策是局部最优的。| Three integer variables: total tracks cumulative (fuel - cost) to determine if a solution exists, fuel tracks current remaining fuel to decide if we can continue, and start records the candidate starting index. No extra queue, stack, or visited array needed; greedy decisions are locally optimal.
 * 
 * 状态转移 Transition: 对每个加油站i，更新fuel += gas[i] - cost[i]。若fuel < 0（无法到达下一站），则：(1)将全局total累加该段，(2)重置fuel=0，(3)设start=i+1（跳过[0...i]所有点）。转移公式：fuel<0则start=i+1,fuel=0，否则继续；最后检查total≥0判断是否有解。| For each station i, update fuel += gas[i] - cost[i]. If fuel < 0 (cannot reach next station), then: (1) accumulate this segment to total, (2) reset fuel = 0, (3) set start = i+1 (skip all points in [0...i]). Transition formula: if fuel < 0 then start = i+1, fuel = 0; else continue; finally check total ≥ 0 to determine if solution exists.
 * 
 * 选择算法 Solver: 采用贪心算法（单遍扫描）。贪心策略的正确性：(1)若全程总油≥总消耗则必有解，(2)若从i无法到达i+1，则[0...i]中任何点都无法到达i+1（因累计油量只会更少），(3)因此可安全跳过，直接尝试i+1为起点。时间复杂度O(N)，一次遍历解决。| Use Greedy Algorithm (single-pass scan). Correctness of greedy strategy: (1) if total fuel ≥ total cost, a solution must exist; (2) if we cannot reach station i+1 from station i, then no point in [0...i] can reach i+1 either (accumulated fuel only decreases); (3) thus we can safely skip and try i+1 as start. Time O(N), solved in one pass.
 * 
 * 复杂度分析: 时间复杂度O(N)：单次遍历所有N个加油站，每站进行O(1)的检查和状态更新。空间复杂度O(1)：只使用三个整数变量(total, fuel, start)，不依赖输入规模的额外空间。单位操作：加减运算和比较各为O(1)。| Time O(N): single pass through N stations, O(1) check and update per station. Space O(1): only three integer variables (total, fuel, start) needed, no space scaling with input size. Unit operations: addition, subtraction, and comparison each O(1).
 * 
 * 不变量 Invariant: (1)在扫描到第i个站时，fuel表示从start到i的累积油量，(2)total表示[0...i-1]所有已放弃区间的油量累积，(3)若某刻fuel<0，则[0...i]都不能作为起点（之前所有段的累积油量<=0），(4)最后如果total+fuel≥0，则start位置必是有效起点。关键不变量：每次重设start时，舍弃的区间不会再被考虑，且全局最优性由"总油≥总消耗"保证。| (1) When scanning station i, fuel represents cumulative fuel from start to i; (2) total represents cumulative fuel of all abandoned intervals in [0...i-1]; (3) if fuel < 0 at some point, then no station in [0...i] can be a valid start (all prior segment sums ≤ 0); (4) if total + fuel ≥ 0 at the end, the start position must be valid. Key invariant: each time we reset start, abandoned intervals are never reconsidered, and global optimality is guaranteed by "total fuel ≥ total cost".
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0;
        int tank = 0;
        int start = 0;

        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;

            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }

        return total >= 0 ? start : -1;
    }
}
```
⸻

Pattern 19: Two Pointers
State: left, right
Transition: left++ / right--

### Problem 42: Container With Most Water
**LeetCode 11 | Medium**

**💡 Key Insight & Why It Works:**

两条竖线，盛水面积由较短的线决定。找最大面积。

**怎么做？双指针从两端逼近**
- 从最宽的容器开始（左0，右末尾）
- 移动较矮的指针（因为只有提高高度才能增加面积）
- 面积 = 宽 × min(左高, 右高)

**为什么有效？** 宽度已最大，移动高指针只会减少宽度。移动矮指针有可能找到更高的。

**💬 For Interview - Just Say:**
- 双指针从两端开始
- 总是移动较矮的指针
- 面积 = 宽 × min(高)

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是在两个柱子之间的盛水面积取决于较矮柱子的高度和两柱间的距离。通过双指针从两端向中间收缩，在每一步选择移除较矮的柱子，以探索更多可能的高度组合，同时保证不会错过最优解。| The problem essence is that water area between two bars is limited by the shorter bar's height and the distance between them. Using two pointers from both ends moving inward, at each step we remove the shorter bar to explore more height combinations while guaranteeing we won't miss the optimal solution.
 * 
 * 状态 State: 状态定义为两个指针位置(left, right)和当前最大面积max_area。状态空间大小为O(N²)个可能的指针对组合。每个状态转移后，指针间的距离减少1，最终收敛到left == right时结束，总共经历N-1次状态转移。| State is defined as two pointer positions (left, right) and current maximum area max_area. State space size is O(N²) possible pointer pairs. After each transition, the distance between pointers decreases by 1, eventually converging when left == right, with N-1 total transitions.
 * 
 * 辅助数据结构 Aux Structure: 仅需一个变量max_area追踪全局最大面积。不需要堆、栈或哈希表等辅助数据结构。两个指针left和right作为遍历状态的唯一数据结构，实现O(1)空间复杂度（不计输入数组）。| Only need a single variable max_area to track the global maximum area. No heap, stack, or hash table needed. Two pointers left and right serve as the only data structure for traversal state, achieving O(1) space complexity (excluding input array).
 * 
 * 状态转移 Transition: 从left=0, right=N-1开始，计算当前面积area = min(height[left], height[right]) × (right - left)，更新max_area = max(max_area, area)。然后，若height[left] < height[right]则left++，否则right--。转移的关键是：永远移动较矮的指针，因为移动较高的指针只会减小面积（距离减少，高度不会增加）。| Start with left=0, right=N-1; compute area = min(height[left], height[right]) × (right - left), update max_area = max(max_area, area). Then, if height[left] < height[right] then left++, else right--. Key insight: always move the shorter pointer because moving the taller pointer only decreases area (distance decreases, height won't increase).
 * 
 * 选择算法 Solver: 采用贪心两指针算法。贪心性在于：从最宽的容器开始，总是舍弃较矮的柱子去寻找更高的柱子，因为宽度已经最大，只有提高高度才能增加面积。这种贪心策略保证了时间复杂度为线性，同时不会遗漏最优解。| Use Greedy Two-Pointer algorithm. Greediness: start from the widest container, always discard the shorter bar to search for taller bars, because width is already maximal and only increasing height can increase area. This greedy strategy guarantees linear time complexity while ensuring the optimal solution isn't missed.
 * 
 * 复杂度分析: 时间复杂度O(N)：两个指针各遍历一次数组，共N-1步转移。空间复杂度O(1)：只使用常数个额外变量(left, right, max_area)，不使用与输入规模相关的额外空间。单位操作：每步计算面积O(1)，指针移动O(1)，总共N次常数时间操作。| Time O(N): two pointers traverse the array once, N-1 total transitions. Space O(1): only constant extra variables (left, right, max_area), no space proportional to input size. Unit operations: area calculation O(1) per step, pointer movement O(1), total N constant-time operations.
 * 
 * 不变量 Invariant: (1) left < right始终成立直到收敛；(2) max_area中存储的是[0, right]范围内所有探索过的指针对的最大面积；(3) 对于任何left和right之间被跳过的指针对(i, j)，其面积必不超过已探索过的某个包含较矮柱子的指针对面积（因为新增的宽度被高度限制所抵消）；(4) 当left == right时，所有可能的指针对都已被考虑（直接或被跳过的优化性质）。| (1) left < right holds until convergence; (2) max_area stores the maximum area from all explored pointer pairs in [0, right] range; (3) for any skipped pair (i, j) between left and right, its area cannot exceed some already-explored pair containing the shorter bar (new width offset by height limitation); (4) when left == right, all possible pairs have been considered (directly or by skip optimization property).
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            int area = (right - left) * Math.min(height[left], height[right]);
            maxArea = Math.max(maxArea, area);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }
}
```


### Problem 43: Two Sum II - Input is Sorted
**LeetCode 167 | Easy**

**💡 Key Insight & Why It Works:**

排序数组中找两个数和等于目标。

**怎么做？双指针夹逼**
- 从两端开始
- 如果和 = 目标，找到答案
- 如果和 < 目标，左指针右移（需要更大的和）
- 如果和 > 目标，右指针左移（需要更小的和）

**为什么有效？** 排序后从两端逼近，每步确定地排除不可能的区间。

**💬 For Interview - Just Say:**
- 双指针从两端开始
- 根据和与目标的关系移动指针
- 和 = 目标时返回

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是在有序数组中快速定位两个和为目标值的元素。利用数组已排序的特性，从两端向中间逼近，通过和与目标值的比较决定指针移动方向，高效地搜索答案。| The problem essence is quickly locating two elements in a sorted array that sum to the target. Leverage the sorted property by approaching from both ends, deciding pointer movement direction by comparing sum to target, efficiently searching for the answer.
 * 
 * 状态 State: 状态定义为两个指针位置(left, right)和当前计算的两数之和。状态空间为O(N)，最坏需要探索N个不同的指针对位置。状态含义 = "当前检查的两个元素及其和"。| State is defined as two pointer positions (left, right) and the current sum of two elements. State space is O(N), worst case exploring N different pointer pair positions. Meaning: current two elements under examination and their sum.
 * 
 * 辅助数据结构 Aux Structure: 仅需两个指针变量left和right。无需额外的哈希表、栈或访问数组。target值和numbers数组作为输入参数。| Only two pointer variables left and right needed. No hash table, stack, or visited array required. Target value and numbers array serve as input parameters.
 * 
 * 状态转移 Transition: 从left=0, right=N-1开始。对每次迭代：(1) 计算sum = numbers[left] + numbers[right]；(2) 若sum == target，返回[left+1, right+1]（1-indexed）；(3) 若sum < target，说明需要更大的和，left++（更大的数）；(4) 若sum > target，说明需要更小的和，right--（更小的数）。| Start with left=0, right=N-1. For each iteration: (1) compute sum = numbers[left] + numbers[right]; (2) if sum == target, return [left+1, right+1] (1-indexed); (3) if sum < target, need larger sum, left++ (find larger number); (4) if sum > target, need smaller sum, right-- (find smaller number).
 * 
 * 选择算法 Solver: 两指针算法。理由：(1) 利用已排序特性，无需额外搜索或排序；(2) 每步指针移动确定性强，不可能遗漏答案；(3) 时间复杂度O(N)优于哈希表的O(N)空间消耗；(4) 空间高效O(1)；(5) 相比二分搜索的两次调用，两指针更简洁直观。| Use Two-Pointer algorithm. Why: (1) leverage sorted property, no extra searching or sorting; (2) each pointer movement is deterministic, impossible to miss answer; (3) O(N) time without O(N) space of hash table; (4) space-efficient O(1); (5) simpler than two binary search calls.
 * 
 * 复杂度分析: 时间复杂度O(N)：最坏情况下left和right各遍历整个数组一次，共2N步操作。空间复杂度O(1)：仅使用常数个指针变量，不使用额外的数据结构。每次迭代是常数时间O(1)的加法、比较和指针移动。| Time O(N): worst case left and right each traverse the array once, 2N total steps. Space O(1): only constant pointer variables, no extra data structures. Each iteration is O(1): addition, comparison, and pointer movement.
 * 
 * 不变量 Invariant: (1) left < right始终成立（除非找到答案或搜索结束）；(2) numbers[0...left-1]的和都小于target（因为left不再回退）；(3) numbers[right+1...N-1]的和都大于target（因为right不再向前）；(4) 每次移动后，搜索空间严格减少（left增或right减，范围变小）；(5) 答案必定存在（题目保证），且必在搜索范围内。| (1) left < right holds (unless answer found or search ends); (2) sums of all numbers[0...left-1] are less than target (left never retreats); (3) sums of all numbers[right+1...N-1] are greater than target (right never advances); (4) after each move, search space strictly shrinks (left increases or right decreases); (5) answer exists (guaranteed) and is within search range.
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];

            if (sum == target) {
                return new int[]{left + 1, right + 1};
            }

            if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{};
    }
}
```
⸻

Pattern 20: Fast Slow Pointer / Floyd's Cycle Detection
State: slow, fast pointers
Transition: slow += 1, fast += 2

### Problem 44: Linked List Cycle
**LeetCode 141 | Easy**

**💡 Key Insight & Why It Works:**

链表中是否有环？Floyd快慢指针算法。

**怎么做？快慢指针相对运动**
- 慢指针每步走1，快指针每步走2
- 如果有环，快指针最终会追上慢指针
- 如果没环，快指针先到达null

**为什么有效？** 相对速度是1，在环中必然追上。无环时快指针先完成。

**💬 For Interview - Just Say:**
- 快慢指针，速度比2:1
- 有环 → 相遇，无环 → 快指针到null

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是检测链表中是否存在环，通过Floyd循环检测算法，利用两个指针以不同速度遍历链表。快指针每次移动两步，慢指针每次移动一步；如果存在环，两指针必定相遇；如果无环，快指针先到达链表末尾。这个相对运动模型将环检测转化为指针追踪问题。| The problem essence is detecting whether a cycle exists in a linked list. Using Floyd's cycle detection algorithm, two pointers traverse the list at different speeds: fast pointer moves two steps, slow pointer moves one step. If a cycle exists, the two pointers must meet; if no cycle, fast pointer reaches the end first. This relative-motion model transforms cycle detection into pointer-tracking.
 * 
 * 状态 State: 状态定义为(slowPos, fastPos)二元组，表示慢指针和快指针当前所在的链表节点位置。状态空间为O(n)，其中n为链表长度或环长。从初始状态(head, head)开始，通过每次迭代更新状态：slow = slow.next，fast = fast.next.next，直到两指针相等或fast到达末尾。状态转移体现了指针相对距离的逐步缩小。| State is defined as tuple (slowPos, fastPos) representing current positions of slow and fast pointers in the linked list. State space is O(n) where n is list length or cycle length. Starting from initial state (head, head), iterate by updating: slow = slow.next, fast = fast.next.next, until pointers meet or fast reaches end. State transitions show gradual reduction of relative distance between pointers.
 * 
 * 辅助数据结构 Aux Structure: 不需要额外的哈希表或集合来存储已访问节点，这是快慢指针方法的核心优势。只需两个指针变量slow和fast，指向链表节点；可选的prev指针用于标记相遇点的前驱节点。空间复杂度O(1)使其相比HashSet方案优越，HashSet方案需要O(n)额外空间来记录所有访问过的节点引用。| No hash table or set needed to store visited nodes—this is the core advantage of fast-slow pointer method. Only two pointer variables (slow, fast) pointing to list nodes; optional prev pointer for marking predecessor of meeting point. O(1) space complexity is superior to HashSet approach which requires O(n) space to track all visited node references.
 * 
 * 状态转移 Transition: 每次迭代执行两个基本操作：(1) 慢指针前进一步：slow = slow.next；(2) 快指针前进两步：fast = fast.next.next（前提是fast和fast.next都非空，否则无环）。转移的关键在于保持二者速度比例1:2，在环形结构中，快指针每轮会"追近"慢指针一个位置，直到两者在同一节点相遇。转移终止条件：slow == fast（发现环）或fast == null || fast.next == null（到达末尾，无环）。| Each iteration performs two operations: (1) slow pointer advances one step: slow = slow.next; (2) fast pointer advances two steps: fast = fast.next.next (requires fast and fast.next non-null, else no cycle). Transition's key is maintaining 1:2 speed ratio; within cycle structure, fast pointer "closes gap" with slow by one position per round until they meet at same node. Termination conditions: slow == fast (cycle found) OR fast == null || fast.next == null (end reached, no cycle).
 * 
 * 选择算法 Solver: 选择Fast Slow Pointer的关键原因：(1) 时间复杂度O(n)，单次遍历，优于哈希表的平均O(n)和最坏情况；(2) 空间复杂度O(1)，仅需两个指针变量，远优于HashSet的O(n)；(3) 数学上的相对运动原理保证了必然相遇性：若环存在，两指针进入环后，快指针每轮相对慢指针接近1步，最终必相遇；(4) 代码简洁，易于理解和实现，无需维护额外数据结构的复杂性。| Choose fast-slow pointer for key reasons: (1) O(n) time complexity with single traversal, better than hash table's average O(n) and worst-case scenarios; (2) O(1) space complexity using only two pointers, far superior to HashSet's O(n); (3) mathematical relative-motion principle guarantees collision: if cycle exists, after entering it, fast pointer closes gap with slow by one position per round, ensuring eventual meeting; (4) simple, clean code, no complex auxiliary data structure maintenance.
 * 
 * 复杂度分析: 时间复杂度O(n)：设链表长度为L，环长为C。慢指针走过L+C步进入环并移动至相遇点，快指针移动步数为2(L+C)。两指针相遇需最多L+C步，因为快指针相对速度为1步/轮，最坏需要走完整环长。总体时间为O(L+C) = O(n)。空间复杂度O(1)：仅使用slow和fast两个指针变量，与输入规模无关。| Time complexity O(n): if list length is L and cycle length is C, slow pointer takes L+C steps to enter and reach meeting point; fast pointer takes 2(L+C) steps. Meeting requires at most L+C steps since relative speed is 1 step/round, worst-case traversing full cycle. Total time O(L+C) = O(n). Space complexity O(1): only two pointer variables used, independent of input size.
 * 
 * 不变量 Invariant: (1) 指针位置单调性：每次迭代后，slow和fast都严格向前移动（除了相遇时停止），不会后退；(2) 速度不变性：fast始终以slow的两倍速度移动（fast走2步 vs slow走1步），这个比例在整个算法执行中保持恒定；(3) 环的不变性：若链表中存在环，从某个点进入后，继续向前移动必将再次访问该点，这个拓扑性质保证了快指针必定追上慢指针；(4) 无环的保证：若fast或fast.next为null，则不存在环，算法安全终止。| Key invariants: (1) pointer position monotonicity: after each iteration, slow and fast strictly move forward (except when meeting stops), never backward; (2) velocity invariance: fast always moves at twice slow's speed (fast 2 steps vs slow 1 step), this ratio remains constant throughout execution; (3) cycle topology invariance: if cycle exists, continuing forward from any point eventually revisits it; this topological property guarantees fast pointer catches slow; (4) acyclic guarantee: if fast or fast.next becomes null, no cycle exists, algorithm safely terminates.
 * ─────────────────────────────────────────────────────────────
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
}
```

### Problem 45: Happy Number
**LeetCode 202 | Easy**

**💡 Key Insight & Why It Works:**

反复平方数字之和，最终到达1（快乐数）或陷入循环（非快乐数）。

**怎么做？追踪已见过的数字**
- 用Set记录见过的数
- 计算数字平方和→新数字
- 如果=1，返回true
- 如果重复出现，陷入循环，返回false

**为什么有效？** 状态空间有限，必然要么到1要么循环。

**💬 For Interview - Just Say:**
- 用Set记录已见的数
- 反复计算平方和
- 到1返回true，重复返回false

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 快乐数问题本质是反复对数字的各位平方和进行转换，直到得到1（快乐数）或进入循环（非快乐数）。问题转化为：在数字转换的序列中检测是否存在循环，如果存在则非快乐数，否则是快乐数。核心思想是状态空间有限，必然要么收敛到1，要么进入循环。| The Happy Number problem essence is repeatedly transforming a number by summing squares of its digits until reaching 1 (happy) or entering a cycle (unhappy). Problem transforms to: detect whether a cycle exists in the digit transformation sequence; if cycle exists then unhappy number, otherwise happy. Core insight is that state space is finite, so must either converge to 1 or enter a cycle.
 * 
 * 状态 State: 状态定义为当前的数值n，其中n是通过平方和转换得到的中间值或初始值。状态空间大小为O(log n)，因为对于数字n，其各位平方和最多为81×位数，快速缩小。从初始数值n开始，逐步生成后继状态nextN = sumOfSquares(n)。关键是追踪已见过的状态，以便检测循环。| State is defined as current number n, intermediate or initial value obtained through digit-square-sum transformation. State space size O(log n) because for number n, sum of digit squares is at most 81×digit-count, shrinking rapidly. Starting from initial n, progressively generate successor states nextN = sumOfSquares(n). Key is tracking seen states to detect cycles.
 * 
 * 辅助数据结构 Aux Structure: 需要两种辅助结构之一实现循环检测：(1) HashSet<Integer> seen：存储所有已访问过的数值，快速查询O(1)；或(2) 快慢指针slow/fast：两个指针以不同速度遍历状态序列，若相遇则存在循环（Floyd's Cycle Detection）。两种方法均可行，前者直观，后者空间高效O(1)。| Two auxiliary approaches for cycle detection: (1) HashSet<Integer> seen storing all visited numbers for O(1) lookup; or (2) slow/fast pointers traversing state sequence at different speeds, meeting indicates cycle exists (Floyd's Cycle Detection). Both viable; former intuitive, latter space-efficient O(1).
 * 
 * 状态转移 Transition: 从状态n出发，执行转换nextN = sumOfSquares(n)：对n的每一位数字d，累加d²到总和。若nextN == 1则是快乐数，返回true；若nextN已在seen集合中则检测到循环，返回false；否则标记nextN为已见，继续下一轮转换。循环检测关键：任何重复出现的数值都标志着进入了循环。转移公式：state[i+1] = Σ(digit²) 对state[i]的所有位。| From state n, execute transformation nextN = sumOfSquares(n): accumulate d² for each digit d of n. If nextN == 1 then happy number, return true; if nextN already in seen set then cycle detected, return false; else mark nextN as seen, continue next iteration. Cycle detection key: any repeated number signals entering a cycle. Transition formula: state[i+1] = Σ(digit²) for all digits of state[i].
 * 
 * 选择算法 Solver: 选择Cycle Detection算法有两种实现：(1) HashSet方法：直观清晰，易于理解和实现，每次迭代检查O(1)是否已见；(2) Floyd's Cycle Detection(快慢指针)：空间优化到O(1)，适合极端内存限制场景。对于此题推荐HashSet方法因状态数量有限（最多~1000），两者性能相近但HashSet代码更简洁。从算法选择角度，这是典型的"状态空间有限→循环检测"问题范式。| Two Cycle Detection implementations: (1) HashSet approach intuitive and clear, O(1) per-iteration check if seen; (2) Floyd's Cycle Detection (slow/fast pointers) optimizes space to O(1), suitable for extreme memory constraints. For this problem HashSet recommended since state count bounded (~1000), performance similar but HashSet code cleaner. From algorithm perspective, this is canonical "finite state space → cycle detection" problem pattern.
 * 
 * 复杂度分析: HashSet方法：时间复杂度O(log n × log(log n))（最坏O(n log n)），因为链长O(log n)，每个数值的平方和计算O(log n)；空间复杂度O(log n)存储HashSet。Floyd方法：时间复杂度同上O(log n × log(log n))，空间复杂度O(1)不需额外存储。实际上对任意输入n，状态序列长度非常有限，通常20-30步以内就能判定。整数n的位数 = log₁₀(n)，各位平方和 ≤ 81×位数，快速收敛。| HashSet method: time O(log n × log(log n)) worst O(n log n), chain length O(log n), digit-square-sum computation O(log n); space O(log n) for HashSet. Floyd method: time same O(log n × log(log n)), space O(1) no extra storage. Actually for any n, state sequence length very limited, typically within 20-30 steps for determination. Integer n's digit count = log₁₀(n), digit-square-sum ≤ 81×digit-count, rapid convergence.
 * 
 * 不变量 Invariant: (1) HashSet seen中的所有数值都是从初始n通过合法的平方和转换得到的，任何数值最多见一次；(2) 算法终止条件二者其一：nextN == 1（找到快乐数）或 nextN ∈ seen（检测到循环）；(3) 循环的存在唯一性：若存在循环则必然是唯一的、确定的数值集合，如4→16→37→58→89→145→42→20→4；(4) 快乐数必然以1结尾，非快乐数必然进入某个固定循环（最知名的是4→16→...→4的循环）；(5) Floyd方法中，若存在循环则slow和fast必然相遇（周期为P的循环，fast追赶slow需P次相对移动）。| Key invariants: (1) all numbers in HashSet seen obtained from initial n through legitimate digit-square-sum transformations, each number seen at most once; (2) termination condition one of two: nextN == 1 (found happy) or nextN ∈ seen (cycle detected); (3) cycle existence uniqueness: if cycle exists then unique, deterministic set of values, e.g., 4→16→37→58→89→145→42→20→4; (4) happy numbers always end at 1, unhappy numbers always enter fixed cycle (famous cycle: 4→16→...→4); (5) in Floyd method, if cycle exists then slow and fast must meet (cycle period P requires P relative advances for fast to catch slow).
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
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
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   问题：找长度最长的递增子序列
 *   转化为子问题：对每个位置i，计算以i结尾的LIS长度
 *   递推关系：dp[i] = max(dp[j] + 1) for all j < i where nums[j] < nums[i]
 *   本质：动态规划，从小到大积累最优结果
 *
 * 状态 State:
 *   dp[i] = 以索引i为结尾的最长递增子序列长度
 *   状态空间：O(n)个状态，每个状态一个值
 *   初值：dp[i] = 1（单个元素本身就是长度1的递增序列）
 *   答案：max(dp[i]) for all i
 *
 * 辅助数据结构 Aux Structure:
 *   - int[] dp: 记录每个位置的最优值
 *   - int result: 全局最大值
 *   - nums[]: 输入数组
 *   无需额外的visited、map等
 *
 * 状态转移 Transition:
 *   对每个i：
 *   检查所有j < i：
 *     if nums[j] < nums[i]:
 *       dp[i] = max(dp[i], dp[j] + 1)
 *   转移公式：dp[i] = max(1, max(dp[j]+1) for all valid j)
 *   转移含义：以i结尾的LIS可以扩展自之前的某个递增序列
 *
 * 选择算法 Solver:
 *   动态规划 (Dynamic Programming) - O(n²) 版本
 *   理由：
 *   - 问题具有最优子结构（最长LIS包含某个最长子LIS）
 *   - 问题具有重叠子问题（多个dp[i]依赖同一个dp[j]）
 *   高级：O(n log n) 使用二分查找LIS（贪心+二分）
 *
 * 复杂度分析:
 *   时间: O(n²)
 *     - 外层：n个位置
 *     - 内层：对每个i检查所有j < i
 *     - 总：n + (n-1) + ... + 1 = n(n+1)/2 = O(n²)
 *   空间: O(n)
 *     - dp数组：n个元素
 *   优化：使用BIT/SegmentTree可达O(n log n)
 *
 * 不变量 Invariant:
 *   - dp[i] ≥ 1（最坏情况只有元素i自己）
 *   - dp[i]代表的递增序列最后一个元素是nums[i]
 *   - 对所有i，dp[i] ≤ result（当前最大值）
 *   - dp[i] = 1 + max(dp[j]: j<i && nums[j]<nums[i])
 * ─────────────────────────────────────────────────────────────
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
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是确定最优的气球爆破顺序以最大化得分。采用逆向思维：不考虑"先爆什么"，而是"最后爆什么"。对于区间[left, right]内的气球，考虑最后爆掉的气球k，当k被爆破时，left和right气球仍然存在，计算其得分。| The problem essence is determining the optimal order to burst balloons to maximize coins. Use reverse thinking: instead of "what to burst first", think "what to burst last". For balloons in range [left, right], consider the last balloon k to burst; when k bursts, left and right balloons still exist.
 * 
 * 状态 State: 状态定义为dp[left][right]，表示爆破[left+1, right-1]范围内所有气球并确保left和right气球最后仍存在时的最大得分。状态空间大小为O(n²)，代表所有可能的子区间。状态含义 = "该子问题的最优解"。| State is defined as dp[left][right], representing maximum coins when bursting all balloons in range [left+1, right-1] while ensuring left and right balloons still exist at the end. State space size O(n²), covering all sub-intervals. Meaning: optimal solution for that sub-problem.
 * 
 * 辅助数据结构 Aux Structure: (1) 扩展气球数组nums：首尾各加1作为边界哨兵，避免边界特殊处理。(2) 二维DP表dp[n][n]：记录所有子区间的最大得分。(3) 递推计算利用前面计算过的子问题结果。| (1) Extended balloons array with 1 added at both ends as sentinel boundaries. (2) 2D DP table dp[n][n] storing maximum coins for all sub-intervals. (3) Recurrence uses previously computed sub-problems.
 * 
 * 状态转移 Transition: 对于区间[left, right]，枚举最后爆破的气球k（left < k < right），计算：dp[left][right] = max(dp[left][k] + dp[k][right] + nums[left]*nums[k]*nums[right])。其中nums[left]*nums[k]*nums[right]是爆掉k时获得的得分（left和right仍在）。转移遵循区间长度从小到大，确保子问题先被解决。| For interval [left, right], enumerate the last balloon k to burst (left < k < right), compute: dp[left][right] = max(dp[left][k] + dp[k][right] + nums[left]*nums[k]*nums[right]). The product nums[left]*nums[k]*nums[right] is the score when bursting k. Transition processes intervals by increasing length, ensuring sub-problems are solved first.
 * 
 * 选择算法 Solver: 区间动态规划（Interval DP）。理由：(1) 问题具有最优子结构（最优解包含子问题的最优解）；(2) 无法用贪心直接求解（爆破顺序影响后续得分）；(3) 区间DP自然处理"子区间"问题；(4) 逆向思维（最后爆哪个）避免了前向思维的复杂性。| Use Interval DP. Why: (1) problem has optimal substructure (optimal solution contains optimal sub-problems); (2) cannot use greedy directly (burst order affects future scores); (3) interval DP naturally handles sub-interval problems; (4) reverse thinking (last balloon) avoids complexity of forward thinking.
 * 
 * 复杂度分析: 时间复杂度O(n³)：枚举区间长度O(n) × 枚举左端点O(n) × 枚举最后爆破的气球O(n) = O(n³)。空间复杂度O(n²)：DP表大小为O(n²)。无法优化至更低阶，因为需要考虑所有子区间和最后爆破的选择。| Time O(n³): enumerate interval length O(n) × left endpoint O(n) × last balloon O(n) = O(n³). Space O(n²): DP table size O(n²). Cannot optimize lower because all sub-intervals and choices must be considered.
 * 
 * 不变量 Invariant: (1) dp[i][i+1] = 0（无有效气球在中间，得分为0）。(2) dp[left][right]表示的爆破不涉及left和right气球本身。(3) 对于任何合法的区间[left,right]，dp[left][right] ≥ 0。(4) 最终答案为dp[0][n+1]，其中n是原始气球数。(5) 每个dp值都基于更小子区间的dp值计算，避免循环依赖。| (1) dp[i][i+1] = 0 (no valid balloon between, score 0). (2) dp[left][right] represents bursting without involving left and right balloons themselves. (3) For any valid interval [left,right], dp[left][right] ≥ 0. (4) Final answer is dp[0][n+1] where n is original balloon count. (5) Each dp value computed from smaller sub-interval values, no circular dependencies.
 * ─────────────────────────────────────────────────────────────
 */
```

⸻

Pattern 26: State Machine DP
State: (day, holding/sold/rest)

### Problem 51: Best Time to Buy and Sell Stock
**LeetCode 121 | Easy**

**💡 Key Insight & Why It Works:**

股票最多买卖一次，求最大利润。

**怎么做？追踪最低价格**
- 遍历价格，追踪到目前为止的最低价格
- 对每个价格，计算如果现在卖出的利润 = 当前价 - 最低价
- 追踪最大利润

**为什么有效？** 最大利润 = 最低价的右边找最大价。贪心永远在最低价买。

**💬 For Interview - Just Say:**
- 追踪最低价格和最大利润
- 每个价格：利润 = 当前价 - 最低价
- 更新最大利润

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将问题建模为在价格数组中寻找一个买入点和卖出点，使得利润最大化。核心是在遍历过程中跟踪历史最低价格，贪心地在每个位置计算若卖出的最大利润。| Model the problem as finding an optimal buy and sell point in the price array to maximize profit. The key insight is to greedily track the minimum price seen so far and calculate the maximum profit at each selling position.
 * 
 * 状态 State: 状态定义为当前位置i处的最大利润，以及从起点到i的最小价格min_price。状态空间为O(n)，其中n为价格数组长度。状态值为单个整数，表示能获得的最大利润。| The state is defined as the maximum profit achievable up to position i, and the minimum price encountered from the start to position i. The state space is O(n) where n is the price array length. Each state stores a single profit value.
 * 
 * 辅助数据结构 Aux Structure: 仅需两个整数变量：min_price用于记录当前遍历过程中看到的最低价格，max_profit用于记录到当前位置能获得的最大利润。贪心策略不需要额外的数组或复杂数据结构。| We need only two integer variables: min_price to track the lowest price seen so far, and max_profit to store the maximum profit achievable up to the current position. No additional arrays or complex structures are required for the greedy approach.
 * 
 * 状态转移 Transition: 从左到右遍历价格数组，对于每个价格prices[i]，更新max_profit=max(max_profit, prices[i]-min_price)来计算当前卖出的利润。然后更新min_price=min(min_price, prices[i])以保持最低价格。转移表示在当前位置卖出能获得的最大利润。| Traverse the price array left to right. For each price prices[i], update max_profit = max(max_profit, prices[i] - min_price) to compute the profit if selling at the current price. Then update min_price = min(min_price, prices[i]) to maintain the lowest price seen. Each transition represents the maximum profit if we sell at the current position.
 * 
 * 选择算法 Solver: 使用贪心算法而非动态规划，因为最优子结构具有单调性：最大利润只依赖于历史最低价格和当前价格的差值。不需要考虑之前的决策组合，贪心地维护最低点和最大利润差即可。| Use Greedy algorithm instead of DP because optimal profit depends only on the lowest historical price and current price difference. No need to consider previous decision combinations; greedily maintaining the minimum price and maximum profit difference suffices. This achieves O(n) time with O(1) space versus DP's O(n) space.
 * 
 * 复杂度分析: 时间复杂度为O(n)，因为只需一次单遍扫描价格数组。空间复杂度为O(1)，仅使用两个常数变量min_price和max_profit。相比于DP方案需要O(n)额外空间存储中间结果，贪心方案空间高效得多。| Time Complexity: O(n) with a single pass through the price array. Space Complexity: O(1) using only two constant variables. This is significantly more space-efficient than DP approaches that require O(n) extra space for storing intermediate results.
 * 
 * 不变量 Invariant: 关键不变量是在任意位置i处，min_price始终表示prices[0]到prices[i-1]中的最小值，max_profit始终表示在i之前或在i处卖出能获得的最大利润。这确保了我们不会向前看未来的价格，保持单遍扫描的有效性。| The key invariant is that at position i, min_price always represents the minimum value from prices[0] to prices[i-1], and max_profit always represents the maximum profit achievable by selling at or before position i. This ensures we don't look ahead and maintains the validity of the single-pass approach.
 * ─────────────────────────────────────────────────────────────
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
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是从多个腐烂橙子同时出发，逐分钟向四周传播腐烂。不同于单源BFS，所有腐烂橙子作为初始源，同时推进BFS前沿，每层代表一分钟，直到所有新鲜橙子被传染或确定无法传染。| The problem essence is multiple rotten oranges spreading rotting to adjacent fresh oranges each minute. Unlike single-source BFS, all rotten oranges are initial sources, simultaneously advancing the BFS frontier; each level represents one minute, until all fresh oranges are infected or determined unreachable.
 * 
 * 状态 State: 状态定义为(行索引, 列索引, 时间分钟)，表示某个网格位置在第几分钟被腐烂。状态空间为O(m×n×时间)。关键是BFS的层级结构自动对应时间分钟数，无需显式记录时间。| State is defined as (row, col, minute), representing when a grid cell gets rotten. State space is O(m×n×time). Key is BFS's level structure automatically corresponds to minutes; no explicit time recording needed.
 * 
 * 辅助数据结构 Aux Structure: (1) Queue<int[]>: 存储待处理的腐烂橙子坐标。初始化时，遍历整个网格找出所有腐烂橙子(grid[i][j]==2)并加入队列。(2) 方向数组dirs：四个方向的偏移(上下左右)。(3) 标记数组或直接修改grid：追踪已腐烂的橙子。| (1) Queue<int[]>: stores rotten orange coordinates to process. Initially finds all rotten oranges (grid[i][j]==2) and enqueues them. (2) Direction array: 4-directional offsets (up, down, left, right). (3) Marking array or directly modify grid: track rotted oranges.
 * 
 * 状态转移 Transition: BFS分层处理。对每层（代表第t分钟）：(1) 取出该层所有腐烂橙子(当前队列所有元素)；(2) 对每个腐烂橙子的4个邻居：如果是新鲜橙子，标记为腐烂并入队；(3) 层处理完后，分钟数+1。转移继续直到队列为空。| BFS processes level by level. For each level (representing minute t): (1) dequeue all rotten oranges currently in queue; (2) for each rotten orange's 4 neighbors: if fresh, mark rotten and enqueue; (3) increment minute after level. Continue until queue empty.
 * 
 * 选择算法 Solver: 多源BFS（Multi-source BFS）。理由：(1) 问题天然涉及多个起点同时作用；(2) BFS的层级结构恰好对应时间分钟数；(3) O(m×n)时间一次遍历所有单元格，高效；(4) 相比DFS或Dijkstra，多源BFS最简洁直观。| Use Multi-source BFS. Why: (1) problem naturally involves multiple starting points acting simultaneously; (2) BFS's level structure exactly corresponds to time minutes; (3) O(m×n) time visits all cells once, efficient; (4) simpler and more intuitive than DFS or Dijkstra.
 * 
 * 复杂度分析: 时间复杂度O(m×n)：每个单元格最多访问一次(进队和出队各一次)。空间复杂度O(m×n)：队列在最坏情况下存储整个网格，另外需O(1)的方向数组。总体高效，处理大规模网格也不会TLE。| Time O(m×n): each cell visited at most once (enqueue and dequeue). Space O(m×n): queue stores entire grid in worst case; direction array is O(1). Overall efficient, handles large grids without TLE.
 * 
 * 不变量 Invariant: (1) 队列中的所有元素都代表已腐烂且尚未处理的橙子。(2) 已处理的腐烂橙子不会再入队。(3) BFS的第t层对应第t分钟的腐烂扩展。(4) 若某新鲜橙子最终未被腐烂，则返回-1；否则返回最后一分钟数。| (1) Queue elements represent already-rotten oranges pending processing. (2) Processed rotten oranges never re-enqueued. (3) BFS level t corresponds to t-th minute of rotting. (4) If any fresh orange remains unrotted, return -1; otherwise return final minute count.
 * ─────────────────────────────────────────────────────────────
 */
```

### Problem 53: Walls and Gates (Multi-source)
**LeetCode 286 | Medium**

**💡 Key Insight & Why It Works:**

求每个房间到最近门的距离。

**怎么做？多源BFS从所有门开始**
- 初始化：所有门(值=0)加入队列
- BFS分层，每层=距离增加1
- 相邻房间(INF)更新为当前距离+1
- 距离直接写入grid

**为什么有效？** BFS第一次到达 = 最短距离。

**💬 For Interview - Just Say:**
- 多源BFS，所有门初始入队
- 每层距离+1
- 更新相邻房间距离

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是计算网格中每个房间到最近门的距离。不是从单个房间出发找最近的门，而是从所有门同时出发向外扩展，直到覆盖所有可达房间。这是多源BFS的典型应用，自然地找到每个房间到最近门的距离。| The problem essence is computing distance from each room to the nearest gate. Instead of searching from one room for the nearest gate, start from all gates simultaneously expanding outward until covering all reachable rooms. This is a canonical multi-source BFS application, naturally finding each room's distance to the nearest gate.
 * 
 * 状态 State: 状态定义为(行索引, 列索引, 当前距离)，表示网格中的一个位置及其到最近门的距离。状态空间为O(m×n)，每个单元格对应一个距离值。BFS的层级直接对应距离级别：第t层的所有单元格距离最近的门都是t。| State is defined as (row, col, current_distance), representing a grid cell and its distance to nearest gate. State space is O(m×n), each cell has one distance value. BFS level directly corresponds to distance level: all cells in level t have distance t to nearest gate.
 * 
 * 辅助数据结构 Aux Structure: (1) Queue<int[]>: 存储待处理的门和房间坐标。初始化时找出所有门(值为0)并入队。(2) 方向数组dirs：四方向偏移。(3) 网格本身grid[][]：既是输入也是输出，距离值直接写入grid。(4) 可选visited数组：追踪已访问单元格(也可直接修改grid标记)。| (1) Queue<int[]>: stores gate and room coordinates. Initially finds all gates (value 0) and enqueues them. (2) Direction array: 4-directional offsets. (3) Grid itself grid[][]: both input and output, distances written directly. (4) Optional visited array: tracks visited cells (or modify grid to mark).
 * 
 * 状态转移 Transition: BFS分层处理。初始化：找出所有门并入队，距离初始化为0。BFS层级循环：(1) 取出当前层所有单元格；(2) 对每个单元格的4邻居：如果是房间(值为INF或2147483647)，更新距离为当前距离+1，标记为已访问，并入队；(3) 处理下一层。| BFS processes level by level. Initialization: find all gates and enqueue with distance 0. Level loop: (1) dequeue all cells at current level; (2) for each cell's 4 neighbors: if room (value INF), update distance to current+1, mark visited, enqueue; (3) process next level.
 * 
 * 选择算法 Solver: 多源BFS。理由：(1) 要找每个房间到最近门的距离，多源BFS从所有门同时出发是最自然的；(2) BFS保证第一次到达某单元格就是最短距离(单位边权)；(3) 时间复杂度O(m×n)最优；(4) 空间复杂度O(m×n)可接受；(5) 代码简洁，直观反映问题的多源特性。| Use Multi-source BFS. Why: (1) finding each room's distance to nearest gate, multi-source BFS from all gates is most natural; (2) BFS guarantees first visit to a cell is shortest distance (unit edge weight); (3) time complexity O(m×n) optimal; (4) space complexity O(m×n) acceptable; (5) code clean, clearly reflects multi-source nature.
 * 
 * 复杂度分析: 时间复杂度O(m×n)：每个单元格最多访问一次，进出队各一次，总2mn操作。空间复杂度O(m×n)：队列在最坏情况下存储整个网格的单元格数。初始化找门O(mn)，BFS遍历O(mn)，总体O(mn)。| Time O(m×n): each cell visited at most once, enqueue and dequeue each, total 2mn operations. Space O(m×n): queue stores entire grid in worst case. Initialization O(mn), BFS O(mn), total O(mn).
 * 
 * 不变量 Invariant: (1) 队列中所有单元格的距离从0单调非递减。(2) 已访问过的单元格其距离值不再改变，即最终得到的距离是到最近门的距离。(3) BFS第t层的所有单元格距离都是t。(4) 房间和墙分别有不同的初值(INF和-1)，在BFS中分别被更新或保持。| (1) Distance of all cells in queue is non-decreasing from 0. (2) Visited cell's distance never changes; it's the final distance to nearest gate. (3) All cells in BFS level t have distance t. (4) Rooms and walls have distinct initial values (INF and -1), updated or preserved during BFS.
 * ─────────────────────────────────────────────────────────────
 */
```

⸻

Pattern 28: Bidirectional BFS
State: (beginSet, endSet)

### Problem 54: Word Ladder (Bidirectional)
**LeetCode 127 | Hard (Bidirectional approach)**

**💡 Key Insight & Why It Works:**

从beginWord变到endWord的最短路径。从两头同时找更快！

**怎么做？双向BFS从两端逼近**
- 从beginWord和endWord同时启动BFS
- 维持两个visited集合
- 当两个搜索的集合相交→找到路径
- 总距离 = 两个搜索的距离和

**为什么有效？** 双向搜索指数级减少搜索空间，通常快2倍。

**💬 For Interview - Just Say:**
- 双向BFS从两端同时开始
- 当visited集合相交→路径存在
- 总距离 = 两个距离之和

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 将单词转换问题建模为图搜索问题，其中每个单词是一个节点，相差一个字母的两个单词之间有一条边。使用双向BFS从beginWord和endWord同时出发，当两个搜索前沿相交时停止，大幅减少搜索空间。| Model word transformation as a graph search problem where each word is a node and edges connect words differing by exactly one letter. Use bidirectional BFS starting simultaneously from beginWord and endWord; stop when the two search frontiers meet, drastically reducing the search space compared to unidirectional BFS.
 * 
 * 状态 State: 状态定义为当前单词及其到起点的距离(distance)。对于双向BFS，维护两个集合：visited_from_begin存储从beginWord可到达的单词及距离，visited_from_end存储从endWord可到达的单词及距离。状态空间大小为O(N)，其中N为词库单词数量。| The state is defined as the current word and its distance from the starting point. For bidirectional BFS, maintain two sets: visited_from_begin stores words reachable from beginWord with their distances, visited_from_end stores words reachable from endWord with distances. The state space size is O(N) where N is the word list size.
 * 
 * 辅助数据结构 Aux Structure: 需要两个队列q1和q2分别用于双向BFS的前沿扩展，两个字典visited_from_begin和visited_from_end分别记录从两个方向访问过的单词及其距离。构建邻接表或利用单词距离函数(两个单词相差一个字母)快速找到相邻词。可用集合存储词库以O(1)时间验证单词是否存在。| Need two queues q1 and q2 for expanding the two BFS frontiers, two dictionaries visited_from_begin and visited_from_end to track words visited from each direction with distances. Build an adjacency list or use word distance function (two words differing by one letter) to find neighbors quickly. Use a set for the word list to verify word existence in O(1) time.
 * 
 * 状态转移 Transition: 在每一步中，从队列中取出一个单词，生成所有相邻单词(通过改变每个字母位置，尝试26个字母)。对于每个相邻单词：如果在对方的visited中找到，则两个搜索相交，计算总距离=begin_distance+end_distance；如果未访问过且在词库中，则加入当前方向的队列并标记已访问。优先使用更小的队列进行扩展以保持平衡。| At each step, dequeue a word and generate all adjacent words by changing each letter position to try all 26 letters. For each adjacent word: if found in the opposite direction's visited set, the searches meet and total distance = begin_distance + end_distance; if unvisited and in wordList, enqueue it and mark as visited. Prioritize expanding the smaller frontier to maintain balance.
 * 
 * 选择算法 Solver: 采用双向BFS而非单向BFS，因为双向搜索指数级减少搜索空间。单向BFS在词库L和单词长度M的图中时间复杂度为O(N×M×26)且搜索范围线性增长；双向BFS两个前沿呈平方根增长，通常快2倍。当beginWord和endWord均已知时，双向BFS是最优选择。| Use Bidirectional BFS instead of unidirectional BFS because it exponentially reduces the search space. Unidirectional BFS has time complexity O(N×M×26) with linearly growing search range; bidirectional BFS has two frontiers growing at square-root pace, typically 2× faster. When both beginWord and endWord are known, bidirectional BFS is optimal.
 * 
 * 复杂度分析: 时间复杂度为O(N×M×26)其中N为词库大小，M为单词长度，26为字母表大小。相比单向BFS搜索深度为O(N)，双向BFS搜索深度为O(√N)，实际运行快2-3倍。空间复杂度为O(N)用于存储visited字典和队列。最坏情况下需要访问所有词库单词，但平均情况双向BFS显著优于单向。| Time Complexity: O(N×M×26) where N is word list size and M is word length. Compared to unidirectional BFS with search depth O(N), bidirectional BFS has search depth O(√N), typically 2-3× faster in practice. Space Complexity: O(N) for storing visited dictionaries and queues. Worst case visits all words, but bidirectional BFS significantly outperforms unidirectional on average cases.
 * 
 * 不变量 Invariant: 关键不变量是visited_from_begin和visited_from_end集合始终不重叠(除了相交点)，确保每个单词最多被访问两次。当某个单词同时出现在两个visited中时，表示找到了一条连接路径，该单词是两个搜索的会合点。另一个不变量是队列中等待处理的单词其距离值严格递增，保证BFS的层序特性。| The key invariant is that visited_from_begin and visited_from_end sets remain disjoint (except at the meeting point), ensuring each word is visited at most twice. When a word appears in both visited sets, it represents the meeting point of the two searches and confirms a path exists. Another invariant is that words in the queue have strictly increasing distance values, maintaining BFS's level-order property and guaranteeing shortest path.
 * ─────────────────────────────────────────────────────────────
 */
```

⸻

Pattern 29: A* Search
State: (cost, heuristic)

### Problem 55: Path Finding with A*
**Algorithm**: A* Search with heuristic

**💡 Key Insight & Why It Works:**

找从起点到终点的最短路径，比Dijkstra更快。

**怎么做？用启发式函数指导搜索**
- f(n) = g(n) + h(n)
- g(n) = 已走距离，h(n) = 估计剩余距离(曼哈顿)
- 用优先队列按f值排序，总是探索f最小的
- 到达目标时返回

**为什么有效？** 启发式函数引导搜索朝目标。比无目标搜索快。

**💬 For Interview - Just Say:**
- f = 已走距离 + 估计剩余距离
- 用优先队列按f排序
- 启发式 = 曼哈顿距离

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是在网格中找到从起点到终点的最短路径。不同于Dijkstra的无目标贪心探索，A*算法利用启发式函数h(n)估计从当前节点到终点的距离，引导搜索朝向目标，大幅减少探索的节点数。| The problem essence is finding the shortest path from start to goal in a grid. Unlike Dijkstra's blind greedy exploration, A* uses heuristic function h(n) to estimate distance from current node to goal, guiding search toward target, drastically reducing explored nodes.
 * 
 * 状态 State: 状态定义为(行, 列, 从起点的实际成本g, 估计总成本f)。状态空间为O(m×n)，每个网格单元对应一个最优路径状态。关键是f(n) = g(n) + h(n)其中g是已走距离，h是估计剩余距离。| State is defined as (row, col, actual cost from start g, estimated total cost f). State space is O(m×n), each grid cell corresponds to an optimal path state. Key is f(n) = g(n) + h(n) where g is actual distance traveled, h is estimated remaining distance.
 * 
 * 辅助数据结构 Aux Structure: (1) PriorityQueue<State>：按f值排序，每次取出f最小的节点。(2) Set<String>：记录已访问的节点(避免重复探索)。(3) 方向数组dirs：四方向或八方向移动。(4) 启发函数h(n)：对网格通常使用曼哈顿距离或欧几里得距离。| (1) PriorityQueue<State>: ordered by f value, always extract node with minimum f. (2) Set<String>: tracks visited nodes (avoid revisiting). (3) Direction array: 4-directional or 8-directional movement. (4) Heuristic function h(n): for grids, typically Manhattan distance or Euclidean distance.
 * 
 * 状态转移 Transition: (1) 初始化：起点入队，g=0，h=heuristic(start, goal)，f=h。(2) 循环：取出f最小的节点，若为目标则返回g；(3) 对其邻居：若未访问且非障碍，计算新的g(当前g+边权)，计算h(到目标的估计距离)，计算f=g+h，加入队列。(4) 优化：若邻居已访问但新g更小，更新该邻居的g和f，重新加入队列。| Transition: (1) Initialization: enqueue start with g=0, h=heuristic(start,goal), f=h. (2) Loop: dequeue node with minimum f; if it's goal return g. (3) For neighbors: if unvisited and not obstacle, compute new g(current g + edge weight), compute h(estimated distance to goal), compute f=g+h, enqueue. (4) Optimization: if neighbor visited but new g smaller, update its g and f, re-enqueue.
 * 
 * 选择算法 Solver: A*搜索。理由：(1) 相比BFS/Dijkstra，启发式函数h(n)引导搜索朝向目标；(2) 若h可接受(不过估计)，A*保证找到最优路径；(3) 相比DFS可能陷入死胡同，A*有目标感；(4) 实践中对许多问题都比无启发搜索快得多；(5) 可调节启发式强度平衡最优性与速度。| Use A* Search. Why: (1) compared to BFS/Dijkstra, heuristic function h(n) guides search toward goal; (2) if h is admissible (never overestimates), A* guarantees optimal path; (3) versus DFS which may get stuck, A* has goal sense; (4) in practice often much faster than uninformed search; (5) can tune heuristic strength to balance optimality and speed.
 * 
 * 复杂度分析: 时间复杂度依赖启发式质量。最坏O(b^d)其中b是分支因子，d是深度(如Dijkstra)。但好的启发式可将其降至O(d)近似线性。空间复杂度O(b^d)用于优先级队列和visited集合。启发式越准确，搜索越高效；完全准确的启发式会直接导向目标。| Time complexity depends on heuristic quality. Worst case O(b^d) where b is branching factor, d is depth (like Dijkstra). Good heuristic can reduce to approximately O(d) linear. Space complexity O(b^d) for priority queue and visited set. Better heuristic means more efficient search; perfect heuristic goes straight to goal.
 * 
 * 不变量 Invariant: (1) PriorityQueue中所有节点的f值≥前一个出队节点的f值(单调性)。(2) 已出队的节点其最优路径已确定(无需更新)。(3) 启发函数h(n)总是≤实际剩余距离(可接纳性)，保证最优性。(4) g值代表已确认的最短路径，f值用于排序优先级。| (1) All f values in PriorityQueue ≥ previous dequeued node's f (monotonicity). (2) Dequeued nodes have final optimal path (no updates needed). (3) Heuristic h(n) always ≤ actual remaining distance (admissibility), guaranteeing optimality. (4) g value represents confirmed shortest path, f value determines priority ordering.
 * ─────────────────────────────────────────────────────────────
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
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver → Complexity → Invariant
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling: 问题本质是模拟贪吃蛇游戏的完整状态机。游戏状态包含：蛇体坐标序列、食物列表、当前分数。每次移动时，根据方向计算新的头部位置，检查碰撞和食物，更新蛇体和分数。核心是高效管理蛇体和占用单元格的状态。| The problem essence is simulating the complete state machine of a snake game. Game state includes: snake body coordinate sequence, food list, current score. With each move, calculate new head position based on direction, check collision and food, update snake and score. Core is efficiently managing snake body and occupied cell states.
 * 
 * 状态 State: 游戏状态定义为(蛇体列表, 食物索引, 分数, 游戏活跃状态)。蛇体用Deque存储，从头到尾。食物索引记录下一条食物在food数组中的位置。状态空间为O(蛇长度)，最坏为O(w×h)。状态转移体现了蛇的移动、生长、碰撞等。| Game state is defined as (snake body list, food index, score, active status). Snake body stored in Deque from head to tail. Food index tracks next food position in food array. State space is O(snake length), worst case O(w×h). State transitions reflect snake movement, growth, collisions.
 * 
 * 辅助数据结构 Aux Structure: (1) Deque<Integer>：存储蛇身体，使用单个整数编码(row*width+col)表示位置。(2) Set<Integer>：occupied集合，快速查询位置是否被蛇占据O(1)。(3) int[][] food：食物坐标数组，按顺序存储所有食物。(4) 方向映射：将"U","D","L","R"映射到行列变化。| (1) Deque<Integer>: stores snake body using single integer encoding (row*width+col). (2) Set<Integer>: occupied set for O(1) collision detection. (3) int[][] food: food coordinate array storing all foods. (4) Direction mapping: maps "U","D","L","R" to row/col changes.
 * 
 * 状态转移 Transition: 对每个move(direction)：(1) 根据direction计算新的头部位置newHead。(2) 检查越界：newHead的行列是否在[0,height)和[0,width)范围内。(3) 计算newHeadPos = newRow*width + newCol。(4) 检查自碰撞：newHeadPos是否在occupied集合中(不包括尾部，因为尾部即将移除)。(5) 将newHeadPos加入Deque头部和occupied。(6) 检查食物：是否newHeadPos等于当前食物位置；若是foodIndex++分数+1，否则弹出尾部和occupied。| For each move(direction): (1) calculate new head position newHead based on direction. (2) check bounds: row and col in [0,height) and [0,width). (3) compute newHeadPos = newRow*width + newCol. (4) check self-collision: is newHeadPos in occupied set (excluding tail which will be removed). (5) add newHeadPos to Deque front and occupied. (6) check food: if newHeadPos equals current food position, foodIndex++, score+1; else remove tail from Deque and occupied.
 * 
 * 选择算法 Solver: 状态机模拟（State Machine Simulation）。理由：(1) 游戏规则明确定义了各种转移(移动、碰撞、食物)；(2) 每步转移是确定的，无需搜索或优化；(3) Deque和Set的组合高效处理动态的蛇体和碰撞检测；(4) O(1)每次移动的时间复杂度实现简单直观。| Use State Machine Simulation. Why: (1) game rules clearly define various transitions (movement, collision, food); (2) each step transition is deterministic, no search needed; (3) Deque+Set combination efficiently handles dynamic snake and collision; (4) O(1) per-move time achieved simply and intuitively.
 * 
 * 复杂度分析: 时间复杂度O(1)每次move操作：Deque的push/pop是O(1)，Set的add/remove是O(1)，方向计算和边界检查都是O(1)常数操作。空间复杂度O(L)其中L是蛇的最大长度，最坏O(w×h)当蛇遍历整个网格。food数组是O(N)其中N为食物总数，与蛇长度无关。| Time O(1) per move: Deque push/pop O(1), Set add/remove O(1), direction calculation and bounds check O(1). Space O(L) where L is max snake length, worst O(w×h) when snake covers entire grid. Food array O(N) where N is total foods, independent of snake.
 * 
 * 不变量 Invariant: (1) occupied集合精确反映Deque中所有蛇身体的位置。(2) Deque的头部是蛇的当前头部，尾部是蛇的尾部。(3) 蛇不能与自己的身体碰撞(除非尾部被移除)。(4) 分数等于已消耗的食物数量(foodIndex-初始0)。(5) 游戏状态要么活跃(分数返回值)，要么结束(返回-1)。(6) 每次成功移动后，蛇长度不变(无食物)或+1(有食物)。| (1) Occupied set precisely reflects all snake body positions in Deque. (2) Deque front is snake head, back is snake tail. (3) Snake cannot collide with itself except tail being removed. (4) Score equals food consumed count (foodIndex). (5) Game state either active (return score) or dead (return -1). (6) After successful move, snake length unchanged (no food) or +1 (food eaten).
 * ─────────────────────────────────────────────────────────────
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
