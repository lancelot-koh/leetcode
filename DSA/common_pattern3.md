# common_pattern3 | Problem Library

## 📖 中文 | CHINESE
此文件包含 LeetCode 问题及详细的 7 步框架分析注释。
代码注释为双语：问题标题、框架分析、复杂度分析都有中英文说明。

## 📖 ENGLISH | 英文
This file contains LeetCode problems with detailed 7-step framework annotations.
Code comments are bilingual: problem titles, framework analysis, and complexity analysis have Chinese-English explanations.

---

Pattern	                        Typical State
Sliding Window	                left,right,windowState
Prefix Sum	                    prefix[i]
Fixed Size Window	            left,right,windowSum
Variable Size Window	        left,right,frequencyMap
At Most K	                    left,right,countMap
Minimum Window	                left,right,needMap,windowMap
Sliding Window + Deque	        deque
Subarray Sum Equals K	        currentSum,prefixCount
Modulo Prefix Sum	            prefix % k
2D Prefix Sum	                prefix[row][col]


一、Sliding Window

* 建模 Modeling: 将滑动窗口问题转化为数据结构设计问题，在每个窗口位置O(1)时间内找到最大值。问题本质：维护窗口内元素的单调性，而非存储所有窗口最大值。

* Modeling: Transform the sliding window problem into a data structure design challenge to find the maximum in each window position in O(1) time. Core insight: maintain monotonicity of window elements rather than storing all maximums.

* 状态 State: 状态 = (窗口右边界位置 i)，窗口范围[i-k+1, i]。状态空间大小 = O(n)个窗口位置。隐含维度：单调队列中存储的是索引（而非值），保证队列单调递减且关键元素始终可见。

* State: State = (window right boundary position i), window range [i-k+1, i]. State space size = O(n) window positions. Implicit dimension: deque stores indices (not values), ensuring monotonic decreasing and critical elements always visible.

* 辅助数据结构 Aux Structure: Deque<Integer>存储窗口内元素的索引(而非值)，维持严格递减顺序。删除策略：(1)移除超出窗口左边界的索引 (2)移除小于当前元素的尾部索引。队列头部始终指向当前窗口的最大值。

* Aux Structure: Deque<Integer> stores indices of window elements (not values) in strictly decreasing order. Removal strategy: (1) remove indices outside left window boundary (2) remove tail indices smaller than current element. Queue front always points to current window maximum.

* 状态转移 Transition: 从位置i-1移动到位置i时：(1)从队列尾部删除所有小于nums[i]的索引(维持递减) (2)从队列头部删除索引<i-k+1的元素(维持窗口范围) (3)添加当前索引i到队列尾部 (4)队列非空时，头部索引对应的值就是当前窗口最大值。

* Transition: When moving from position i-1 to i: (1) remove all indices from queue tail smaller than nums[i] (maintain decreasing) (2) remove indices < i-k+1 from queue front (maintain window bounds) (3) append current index i to queue tail (4) when queue is non-empty, value at front index is current window maximum.

* 选择算法 Solver: 使用单调双端队列(Monotonic Deque)。理由：直接遍历数组O(n)，对每个元素O(1)操作(每个元素最多入队出队各一次)。相比堆/TreeMap的O(n log k)解法，单调队列更优。单调性保证：队列头部元素必为最大值，无需比较。

* Solver: Use Monotonic Deque pattern. Rationale: single array pass O(n), each element O(1) processing (each element enqueued/dequeued at most once). Superior to heap/TreeMap O(n log k) solutions. Monotonicity guarantee: queue front element must be maximum, no comparison needed.

* 复杂度分析 Complexity: 时间复杂度O(n)，每个元素最多入队一次、出队一次，总操作数≤2n。空间复杂度O(k)，双端队列最多存储k个索引(窗口大小)。关键：尽管有嵌套循环外观，实际每个元素仅处理常数次。

* Complexity Analysis: Time O(n), each element enqueued/dequeued at most once, total operations ≤2n. Space O(k), deque stores at most k indices (window size). Key: despite nested loop appearance, each element processes constant times.

* 不变量 Invariant: (1)队列严格递减：队列中indices i1 < i2 必有 nums[i1] > nums[i2]。(2)队列头部索引始终在有效窗口范围[max(0, i-k+1), i]内。(3)所有被移除的元素要么超出窗口边界，要么被找到的更大元素遮挡(永不再被访问)。(4)最大值答案从不遗漏。

* Invariant: (1) Queue strictly decreasing: for indices i1 < i2 in queue, nums[i1] > nums[i2]. (2) Queue front index always in valid window [max(0, i-k+1), i]. (3) All removed elements either exceed window bounds or shadowed by found larger elements (never accessed again). (4) maximum value answer never missed.

很多人以为：
Sliding Window
=
两个指针
其实不是。
本质是：
维护一个连续区间(Window)
并动态扩张/收缩

⸻

核心 State
最基础：
left
right

⸻

State Transition
扩张
right++
收缩
left++

⸻

Pattern 1
Fixed Size Window

⸻

### Problem 1: Fixed Size Window - Maximum Average Subarray
**LeetCode 643 | Easy**
**Link:** https://leetcode.com/problems/maximum-average-subarray-i/
**Key Points:**
- Find max average of contiguous subarray of fixed size k
- Maintain window sum, slide by removing left and adding right
- Average calculation after finding max sum
- Time: O(n), Space: O(1)

```java
/**
 * 建模 Modeling: 在给定数组中找到长度为k的连续子数组,使其平均值最大。| Modeling: Find a contiguous subarray of length k in the given array with the maximum average value.
 * 状态 State: 当前窗口的和以及滑动窗口的左右边界位置。| State: The sum of the current window and the left/right boundary positions of the sliding window.
 * 辅助数据结构 Aux Structure: 维持一个大小为k的固定窗口,记录窗口内元素之和。| Aux Structure: Maintain a fixed-size window of k elements and track the sum of elements within the window.
 * 状态转移 Transition: 向右扩展窗口,添加新元素到和中;当窗口大小超过k时,移除左端元素;更新最大和。| Transition: Expand the window to the right by adding the next element to the sum; remove the leftmost element when window size exceeds k; update the maximum sum.
 * 选择算法 Solver: 使用滑动窗口遍历数组一次,维护固定大小的窗口和,记录最大值,最后用最大和除以k得到最大平均值。| Solver: Use sliding window to traverse the array once, maintain the sum of a fixed-size window, record the maximum sum, and divide by k to get the maximum average.
 * 复杂度分析: 时间复杂度O(n),空间复杂度O(1)。| Complexity: Time complexity O(n), Space complexity O(1).
 * 不变量 Invariant: 窗口始终包含恰好k个元素,窗口内所有元素之和正确反映了当前位置的k元素子数组。| Invariant: The window always contains exactly k elements, and the sum within the window correctly represents the k-element subarray at the current position.
 */
class Solution {
    public double findMaxAverage(int[] nums, int k) {
        int windowSum = 0;

        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        int maxSum = windowSum;

        for (int i = k; i < nums.length; i++) {
            windowSum = windowSum - nums[i - k] + nums[i];
            maxSum = Math.max(maxSum, windowSum);
        }

        return (double) maxSum / k;
    }
}
```
⸻

State
left
right
windowSum

⸻

Transition
add nums[right]

remove nums[left]

⸻

Window Size 固定
例如：
长度 = k

⸻

Pattern 2
Variable Size Window
Google最喜欢

⸻

### Problem 2: Variable Size Window - Longest Substring Without Repeating Characters
**LeetCode 3 | Medium**
**Link:** https://leetcode.com/problems/longest-substring-without-repeating-characters/
**Key Points:**
- Find longest substring with all unique characters
- Expand window by moving right, shrink when duplicate found
- Use Set to track characters in current window
- Time: O(n), Space: O(min(m, n)) where m=alphabet size
- Two-pointer with character set tracking

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   问题：找最长不含重复字符的子串
 *   转化为窗口问题：维持一个字符不重复的滑动窗口
 *   动态扩张：right指针不断右移
 *   动态收缩：当发现重复字符时，left指针左移直到重复消除
 *
 * 状态 State:
 *   窗口 = [left, right] 的连续子串
 *   窗口状态 = 窗口内字符的频率集合
 *   不变量：窗口内不存在重复字符
 *   状态空间：O(n²)个可能的窗口
 *
 * 辅助数据结构 Aux Structure:
 *   - Set<Character> set: 维护当前窗口内的字符（去重）
 *   - int left: 左指针
 *   - int right: 隐含在for循环中
 *   - int result: 全局最大窗口长度
 *
 * 状态转移 Transition:
 *   每次迭代（right++）：
 *   1. 检查新字符s[right]是否在set中
 *   2. 如果存在：收缩窗口left++，直到重复字符被移除
 *   3. 加入新字符到set
 *   4. 记录最大窗口长度
 *   转移特点：动态调整left和right，保持不变量
 *
 * 选择算法 Solver:
 *   滑动窗口 (Sliding Window) - 变大小版本
 *   理由：
 *   - 子问题具有单调性（移除left元素后，right仍有效）
 *   - 无需重新初始化，left只增不减
 *   - 时间复杂度O(n)：每个字符最多被访问2次
 *
 * 复杂度分析:
 *   时间: O(n)
 *     - 外层：right从0到n-1（n次）
 *     - 内层while：left整个过程中最多增加n
 *     - 总操作：2n次字符访问
 *   空间: O(min(m, n))
 *     - m = 字符集大小（26-128）
 *     - set最多存储min(26, window_size)个字符
 *
 * 不变量 Invariant:
 *   - set中不存在重复字符
 *   - set中所有字符都在窗口[left,right]内
 *   - 如果字符在set中，在[left,right]内最后一次出现位置是right之前
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int lengthOfLongestSubstring(String s) {
        // Aux: 维护窗口内的字符集
        Set<Character> set = new HashSet<>();
        int left = 0;
        int result = 0;

        // Solver: 滑动窗口，right指针逐步扩展
        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);

            // Transition: 发现重复 → 收缩窗口
            while (set.contains(ch)) {
                set.remove(s.charAt(left));
                left++;
            }

            // Transition: 加入新字符，扩展窗口
            set.add(ch);
            
            // 记录最大窗口长度
            result = Math.max(result, right - left + 1);
        }

        return result;
    }
}
```
⸻

State
left
right
frequencyMap

⸻

Transition
扩张：
right++

⸻

发现重复：
left++
直到合法

⸻

这里 Window 大小不断变化。

⸻

Pattern 3
At Most K
超高频

⸻

### Problem 3: At Most K Pattern - Longest Repeating Character Replacement
**LeetCode 424 | Medium**
**Link:** https://leetcode.com/problems/longest-repeating-character-replacement/
**Key Points:**
- Find longest substring with at most k character replacements
- Key insight: windowSize - maxFrequency <= k means valid window
- Track frequency of each character and max frequency
- Shrink window when condition violated
- Time: O(n), Space: O(1) (fixed 26 letters)

```java
/**
 * 建模 Modeling: 在字符串中找最长子串，使得通过最多K次替换可以使所有字符相同。| Modeling: Find the longest substring where at most K character replacements make all characters identical.
 * 状态 State: left和right指针定义滑动窗口，count[]记录窗口内每个字符的频数。| State: left and right pointers define sliding window, count[] tracks character frequencies in window.
 * 辅助数据结构 Aux Structure: 哈希表/数组维护窗口内字符频数，maxFreq记录最高频字符的计数。| Aux Structure: Hash table/array maintains character frequencies, maxFreq tracks maximum frequency.
 * 状态转移 Transition: 右指针扩展窗口，若(窗口长度-最高频)>K则左指针收缩，更新最长有效长度。| Transition: Expand with right pointer, shrink left pointer if (window_length - maxFreq) > K, track max length.
 * 选择算法 Solver: 双指针滑动窗口，单次遍历维护字符频数，时间高效。| Solver: Two-pointer sliding window, maintain frequencies in single pass, time-efficient.
 * 复杂度分析: 时间O(n)，空间O(1)(字母集固定)。| Complexity: Time O(n), Space O(1) (fixed alphabet size).
 * 不变量 Invariant: 窗口内满足条件则不回溯左指针，保证O(n)的滑动窗口不变式。| Invariant: Left pointer never backtracks when window valid, maintaining O(n) sliding window invariant.
 */
class Solution {
    public int characterReplacement(String s, int k) {
        int[] freq = new int[26];
        int left = 0;
        int maxFreq = 0;
        int result = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            freq[ch - 'A']++;
            maxFreq = Math.max(maxFreq, freq[ch - 'A']);

            while ((right - left + 1) - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }

            result = Math.max(result, right - left + 1);
        }

        return result;
    }
}
```
⸻

State
left
right
countMap
maxFrequency

⸻

判断：
windowSize - maxFrequency <= k

⸻

不满足：
left++

⸻

Google特别喜欢：
At Most K
类问题。

⸻

Pattern 4
Minimum Window

⸻

### Problem 4: Minimum Window Pattern - Minimum Window Substring
**LeetCode 76 | Hard**
**Link:** https://leetcode.com/problems/minimum-window-substring/
**Key Points:**
- Find minimum window substring containing all chars from t
- Two maps: need (required chars) and window (current window chars)
- Expand right until window valid, then shrink to find minimum
- Track count of formed requirements (when char frequency matches)
- Time: O(m + n) where m=len(s), n=len(t); Space: O(1) (fixed charset)

```java
/**
 * 建模 Modeling: 在字符串中找最小长度的子串，包含目标字符串中的所有字符。| Modeling: Find the minimum length substring containing all characters from target string.
 * 状态 State: left右指针位置，以及当前窗口内各字符的频率计数。| State: Left and right pointer positions, and character frequency counts within current window.
 * 辅助数据结构 Aux Structure: 哈希表存储目标字符频率，哈希表存储窗口字符频率。| Aux Structure: Hash map for target character frequencies, hash map for window character frequencies.
 * 状态转移 Transition: 右指针扩展窗口直至包含所有目标字符，左指针收缩以找最小窗口。| Transition: Expand right pointer until window contains all target chars, shrink left pointer to find minimum.
 * 选择算法 Solver: 双指针滑动窗口，贪心地在每步扩展或收缩指针。| Solver: Two-pointer sliding window with greedy expansion and contraction at each step.
 * 复杂度分析: 时间O(m+n)其中m为源字符串长度n为目标字符串长度；空间O(1)哈希表固定大小。| Complexity: Time O(m+n) where m is source length and n is target length; Space O(1) fixed hash table.
 * 不变量 Invariant: [left,right]窗口始终满足包含所有目标字符，或记录过的最小窗口有效。| Invariant: Window [left,right] always maintains all target chars or previously recorded minimum window is valid.
 */
class Solution {
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }

        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        Map<Character, Integer> window = new HashMap<>();
        int required = need.size();
        int formed = 0;
        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int start = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            window.put(ch, window.getOrDefault(ch, 0) + 1);

            if (need.containsKey(ch) && window.get(ch).intValue() == need.get(ch).intValue()) {
                formed++;
            }

            while (formed == required) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    start = left;
                }

                char leftChar = s.charAt(left);
                window.put(leftChar, window.get(leftChar) - 1);

                if (need.containsKey(leftChar) && window.get(leftChar) < need.get(leftChar)) {
                    formed--;
                }

                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}
```
⸻

State
left
right
needMap
windowMap

⸻

Transition
扩张：
right++

⸻

满足条件后：
不断收缩
left++
寻找最短答案

⸻

Pattern 5
Sliding Window + Deque

⸻

### Problem 5: Sliding Window + Deque - Sliding Window Maximum
**LeetCode 239 | Hard**
**Link:** https://leetcode.com/problems/sliding-window-maximum/
**Key Points:**
- Find maximum value in each sliding window of size k
- Use deque to maintain indices of potential maximums
- Deque stores indices in decreasing order of values
- Remove indices outside window, remove smaller values when new max found
- Time: O(n), Space: O(k) for deque
- Each element added and removed once

```java
/**
 * 建模 Modeling: 使用单调递减双端队列维护窗口内的最大值,当窗口滑动时动态更新。| Modeling: Use a monotonic decreasing deque to maintain the maximum value in the window; update dynamically as the window slides.
 * 状态 State: 双端队列中存储数组元素的索引,满足对应值单调递减。| State: Deque stores indices of array elements where corresponding values are monotonically decreasing.
 * 辅助数据结构 Aux Structure: 单调递减双端队列,队头为当前窗口的最大值索引。| Aux Structure: Monotonic decreasing deque with the front element being the index of the maximum value in the current window.
 * 状态转移 Transition: 移除窗口外的元素,删除队尾所有小于当前元素的索引,将当前索引加入队尾。| Transition: Remove elements outside the window; remove all indices from the back with smaller values than current element; add current index to the back.
 * 选择算法 Solver: 遍历数组,对每个位置维护单调队列,窗口形成后取队头元素值为最大值。| Solver: Traverse the array, maintain the monotonic deque at each position; after window forms, the value at the front of the deque is the maximum.
 * 复杂度分析: 时间复杂度 O(n),每个元素最多入队和出队一次;空间复杂度 O(k),队列最多存储k个元素。| Complexity: Time O(n), each element enters and exits the deque at most once; Space O(k), deque stores at most k elements.
 * 不变量 Invariant: 双端队列始终保持单调递减;队头索引对应的值始终是当前窗口的最大值。| Invariant: Deque always maintains monotonic decreasing order; the value at the front index always corresponds to the maximum in the current window.
 */
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
```

Problem:

Maximum value in every fixed-size window

Model:

Sliding window

State:

current index i

window range [i-k+1, i]

Aux Structure:

monotonic decreasing deque of indices

Transition:

remove expired indices

remove smaller values from back

add current index

Invariant:

front of deque is always max of current window

Complexity:

O(n)
⸻

State
deque
维护：
单调递减

⸻

Google偶尔会问。

⸻

Sliding Window识别口诀
看到：
连续

子数组

substring

longest

shortest

at most

at least
优先想：
Sliding Window

⸻

二、Prefix Sum
这个很多人刷题时会做。
但面试时认不出来。

⸻

本质：
快速计算区间和

⸻

Pattern 1
普通 Prefix Sum

⸻

State
prefix[i]

⸻

Transition
prefix[i]
=
prefix[i-1]
+
nums[i]

⸻

查询
sum(l,r)

=
prefix[r]
-
prefix[l-1]

⸻

### Problem 6: Basic Prefix Sum - Range Sum Query
**LeetCode 303 | Easy**
**Link:** https://leetcode.com/problems/range-sum-query-immutable/
**Key Points:**
- Precompute prefix sums to enable O(1) range sum queries
- prefix[i] = sum of elements from index 0 to i-1
- Query sum(left, right) = prefix[right+1] - prefix[left]
- Constructor: O(n), Query: O(1)
- Space: O(n) for prefix array

```java
/**
 * 建模 Modeling: 将数组分割成区间，预计算前缀和使得任意区间和可在O(1)时间内查询。| Modeling: Partition the array into segments and precompute prefix sums to answer range sum queries in O(1) time.
 * 状态 State: prefix[i] 表示数组前i个元素的累积和。| State: prefix[i] represents the cumulative sum of the first i elements.
 * 辅助数据结构 Aux Structure: 长度为n+1的一维前缀和数组，prefix[0]=0。| Aux Structure: One-dimensional prefix sum array of length n+1 with prefix[0]=0.
 * 状态转移 Transition: prefix[i] = prefix[i-1] + arr[i-1]；区间[L,R]的和 = prefix[R+1] - prefix[L]。| Transition: prefix[i] = prefix[i-1] + arr[i-1]; range sum [L,R] = prefix[R+1] - prefix[L].
 * 选择算法 Solver: 线性扫描一次数组构建前缀和数组。| Solver: Single linear scan to build the prefix sum array.
 * 复杂度分析: 时间O(n)预处理+O(1)查询；空间O(n)。| Complexity: O(n) preprocessing + O(1) query; O(n) space.
 * 不变量 Invariant: prefix[i] 始终维持前i个元素的准确累积和；任意合法区间查询结果正确。| Invariant: prefix[i] always maintains the accurate cumulative sum of the first i elements; any valid range query is correct.
 */
class NumArray {
    int[] prefix;

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


⸻

Pattern 2
Prefix Sum + HashMap
Google超高频

⸻

### Problem 7: Prefix Sum + HashMap - Subarray Sum Equals K
**LeetCode 560 | Medium**
**Link:** https://leetcode.com/problems/subarray-sum-equals-k/
**Key Points:**
- Count number of subarrays with sum equal to k
- Use prefix sum + map to find: if (currentSum - k) exists in map, found match
- Key insight: sum[i] - sum[j] = k means subarray [j+1...i] sums to k
- Track prefix sums in map with their frequencies
- Time: O(n), Space: O(n)
- Map approach beats sliding window (works with negative numbers)

```java
/**
 * ─────────────────────────────────────────────────────────────
 * 框架: Modeling → State → Aux → Transition → Solver
 * ─────────────────────────────────────────────────────────────
 * 
 * 建模 Modeling:
 *   问题：计算子数组和等于k的个数
 *   关键洞察：前缀和差值 = 区间和
 *     sum[0...i] - sum[0...j] = sum[j+1...i]
 *   转化为：当前前缀和减去k，如果结果在map中，说明找到一个匹配
 *   本质：从查找子数组 → 查找前缀和对
 *
 * 状态 State:
 *   prefix = nums[0] + nums[1] + ... + nums[i]
 *   状态空间：O(n)个不同的前缀和值
 *   不变量：每个位置i都有一个累积的prefix和
 *   目标：找所有满足 prefix[i] - prefix[j] = k 的对(i,j)
 *
 * 辅助数据结构 Aux Structure:
 *   - Map<Integer, Integer> map: 前缀和 → 出现频率
 *   - map.put(0, 1): 初始化，代表空前缀
 *   - int prefix: 当前累积和
 *   - int count: 答案计数器
 *
 * 状态转移 Transition:
 *   对每个nums[i]：
 *   1. prefix += nums[i]（累加当前元素）
 *   2. 查找(prefix - k)是否存在于map
 *      如果存在n次 → 说明有n个子数组和等于k
 *   3. 将prefix加入map（记录频率）
 *   转移关键：同一个前缀和可能出现多次，所以需要计数
 *
 * 选择算法 Solver:
 *   前缀和 + HashMap (Prefix Sum + Hash Table)
 *   理由：
 *   - 可处理负数（vs 滑动窗口只适用非负）
 *   - O(1)查询vs O(n²)暴力
 *   - 数据流问题的天然选择
 *
 * 复杂度分析:
 *   时间: O(n)
 *     - 单次遍历：n个元素
 *     - 每个位置：getOrDefault O(1)，put O(1)
 *     - 总：n次O(1)操作
 *   空间: O(n)
 *     - map最坏情况：所有n个前缀和都不同
 *     - map存储最多n+1个条目
 *
 * 不变量 Invariant:
 *   - map中总是存在键0（初始）
 *   - map[prefix] = 有多少个位置的前缀和等于这个值
 *   - 当map包含(prefix-k)时，说明存在之前的位置j使得[j+1...i]和为k
 *   - count只增不减（单调递增）
 * ─────────────────────────────────────────────────────────────
 */
class Solution {
    public int subarraySum(int[] nums, int k) {
        // Aux: HashMap存储前缀和及其出现频率
        Map<Integer, Integer> map = new HashMap<>();
        // 初始化：表示"零元素的前缀和"出现1次
        map.put(0, 1);
        
        int prefix = 0;
        int count = 0;

        // Solver: 单遍历
        for (int num : nums) {
            // Transition: 累加当前元素到前缀和
            prefix += num;
            
            // State查询：当前前缀和减去target
            // 如果(prefix-k)在map中，意味着存在之前的某个位置j，
            // 使得[j+1...i]的和等于k
            int targetPrefix = prefix - k;
            if (map.containsKey(targetPrefix)) {
                count += map.get(targetPrefix);
            }
            
            // Transition: 记录当前前缀和
            map.put(prefix, map.getOrDefault(prefix, 0) + 1);
        }

        return count;
    }
}
```
⸻

State
currentSum
prefixCount

⸻

核心公式
currentSum - k
出现过
↓
找到答案

⸻

例如：
sum = 10

k = 7

需要找：

3

⸻

如果以前出现过 Prefix Sum = 3
说明：
中间区间和 = 7

⸻

这个是 Google 高频中的高频。

⸻

### Problem 8: Modulo Prefix Sum - Subarrays Divisible By K
**LeetCode 974 | Medium**
**Link:** https://leetcode.com/problems/subarrays-divisible-by-k/
**Key Points:**
- Count subarrays whose sum is divisible by k
- Key insight: if prefix1 % k == prefix2 % k, then sum between them is divisible by k
- Handle negative modulo: ((prefix % k) + k) % k
- Store mod values in map with frequencies
- Time: O(n), Space: O(k)

```java
/**
 * 建模 Modeling: 问题本质是在前缀和基础上引入模运算，通过捕捉两个位置的前缀和在模k意义下相等来判断中间子数组和能被k整除，转化为余数匹配检测问题。| Modeling: The core problem extends prefix sum with modulo arithmetic; by capturing two positions where prefix sums have equal remainders modulo k, the subarray between them has a sum divisible by k, transforming the problem into remainder matching detection.
 * 状态 State: 状态为前缀和的模余数remainder = prefix_sum % k，以及该余数首次出现的索引位置，状态空间大小为O(k)个不同的余数，维护remainder → earliest_index的映射关系。| State: State is the modulo remainder of prefix sum (remainder = prefix_sum % k) and the earliest index where this remainder first appears, with state space of O(k) distinct remainders, maintaining a remainder → earliest_index mapping.
 * 辅助数据结构 Aux Structure: 使用HashMap<Integer, Integer>存储每个余数首次出现的索引，初始化map.put(0, -1)作为哨兵，代表"找到从数组开头到某位置的子数组"的情形。| Aux Structure: Use HashMap<Integer, Integer> to store the earliest index for each remainder, initialize with map.put(0, -1) as a sentinel value, representing the case of finding a subarray from array start to some position.
 * 状态转移 Transition: 遍历数组计算实时前缀和prefix，对每个位置i计算remainder = prefix % k，检查remainder是否在map中存在，若存在且i - map[remainder] >= 2则返回true，否则首次遇见此remainder时存储当前索引。| Transition: Traverse array computing running prefix sum, for each position i calculate remainder = prefix % k, check if remainder exists in map; if exists and i - map[remainder] >= 2 return true, else on first encounter of this remainder store current index.
 * 选择算法 Solver: 采用哈希映射单次扫描算法，结合模运算的周期性性质，利用鸽笼原理保证在k+1个元素后必存在重复余数，从而找到满足条件的子数组。| Solver: Use single-pass hash map scan combined with periodicity of modulo arithmetic, leverage pigeonhole principle to guarantee duplicate remainders exist after k+1 elements, finding qualifying subarrays.
 * 复杂度分析: 时间复杂度O(n)单次遍历配合O(1)的哈希查询，空间复杂度O(min(n, k))存储余数映射，k为除数，受模运算最多产生k种不同余数的限制。| Complexity: Time complexity O(n) for single pass with O(1) hash lookups, space complexity O(min(n, k)) for storing remainder mappings, constrained by at most k distinct remainders from modulo operation.
 * 不变量 Invariant: 不变量包括map总是包含{0 → -1}、map中的余数对应的最早索引位置单调递增、任意两个相同余数的索引差 >= 2时中间子数组和必被k整除、map大小不超过k。| Invariant: Invariants include map always containing {0 → -1}, earliest indices for remainders in map being monotonically increasing, difference between indices of identical remainders >= 2 guarantees divisibility by k, map size bounded by k.
 */
class Solution {
    public int subarraysDivByK(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int prefix = 0;
        int result = 0;

        for (int num : nums) {
            prefix += num;
            int mod = ((prefix % k) + k) % k;
            result += map.getOrDefault(mod, 0);
            map.put(mod, map.getOrDefault(mod, 0) + 1);
        }

        return result;
    }
}
```

### Problem 9: Modulo Prefix Sum - Continuous Subarray Sum
**LeetCode 523 | Medium**
**Link:** https://leetcode.com/problems/continuous-subarray-sum/
**Key Points:**
- Find if subarray with sum divisible by k exists (with length >= 2)
- Same modulo logic: if two indices have same prefix % k, subarray between them divisible by k
- Store first occurrence index of each mod value
- Ensure subarray length >= 2 (i - index >= 2)
- Time: O(n), Space: O(k)

```java
/**
 * 建模 Modeling: 问题本质是在前缀和基础上引入模运算，通过捕捉两个位置的前缀和在模k意义下相等来判断中间子数组和能被k整除，转化为余数匹配检测问题。| Modeling: The core problem extends prefix sum with modulo arithmetic; by capturing two positions where prefix sums have equal remainders modulo k, the subarray between them has a sum divisible by k, transforming the problem into remainder matching detection.
 * 状态 State: 状态为前缀和的模余数remainder = prefix_sum % k，以及该余数首次出现的索引位置，状态空间大小为O(k)个不同的余数，维护remainder → earliest_index的映射关系。| State: State is the modulo remainder of prefix sum (remainder = prefix_sum % k) and the earliest index where this remainder first appears, with state space of O(k) distinct remainders, maintaining a remainder → earliest_index mapping.
 * 辅助数据结构 Aux Structure: 使用HashMap<Integer, Integer>存储每个余数首次出现的索引，初始化map.put(0, -1)作为哨兵，代表"找到从数组开头到某位置的子数组"的情形。| Aux Structure: Use HashMap<Integer, Integer> to store the earliest index for each remainder, initialize with map.put(0, -1) as a sentinel value, representing the case of finding a subarray from array start to some position.
 * 状态转移 Transition: 遍历数组计算实时前缀和prefix，对每个位置i计算remainder = prefix % k，检查remainder是否在map中存在，若存在且i - map[remainder] >= 2则返回true，否则首次遇见此remainder时存储当前索引。| Transition: Traverse array computing running prefix sum, for each position i calculate remainder = prefix % k, check if remainder exists in map; if exists and i - map[remainder] >= 2 return true, else on first encounter of this remainder store current index.
 * 选择算法 Solver: 采用哈希映射单次扫描算法，结合模运算的周期性性质，利用鸽笼原理保证在k+1个元素后必存在重复余数，从而找到满足条件的子数组。| Solver: Use single-pass hash map scan combined with periodicity of modulo arithmetic, leverage pigeonhole principle to guarantee duplicate remainders exist after k+1 elements, finding qualifying subarrays.
 * 复杂度分析: 时间复杂度O(n)单次遍历配合O(1)的哈希查询，空间复杂度O(min(n, k))存储余数映射，k为除数，受模运算最多产生k种不同余数的限制。| Complexity: Time complexity O(n) for single pass with O(1) hash lookups, space complexity O(min(n, k)) for storing remainder mappings, constrained by at most k distinct remainders from modulo operation.
 * 不变量 Invariant: 不变量包括map总是包含{0 → -1}、map中的余数对应的最早索引位置单调递增、任意两个相同余数的索引差 >= 2时中间子数组和必被k整除、map大小不超过k。| Invariant: Invariants include map always containing {0 → -1}, earliest indices for remainders in map being monotonically increasing, difference between indices of identical remainders >= 2 guarantees divisibility by k, map size bounded by k.
 */
class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int prefix = 0;

        for (int i = 0; i < nums.length; i++) {
            prefix += nums[i];
            int mod = prefix % k;

            if (map.containsKey(mod)) {
                if (i - map.get(mod) >= 2) {
                    return true;
                }
            } else {
                map.put(mod, i);
            }
        }

        return false;
    }
}
```


⸻

State
prefix % k

⸻

核心：
如果：
prefix1 % k
=
prefix2 % k
那么：
中间和
一定是k倍数

⸻

很多人第一次见很容易懵。

⸻

### Problem 10: 2D Prefix Sum - Matrix Range Sum Query
**LeetCode 304 | Medium**
**Link:** https://leetcode.com/problems/range-sum-query-2d-immutable/
**Key Points:**
- Precompute 2D prefix sums for O(1) range sum queries
- Formula: prefix[r][c] = prefix[r-1][c] + prefix[r][c-1] - prefix[r-1][c-1] + matrix[r-1][c-1]
- Query: sum = prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]
- Constructor: O(m×n), Query: O(1)
- Space: O(m×n)
- 2D principle extends 1D: inclusion-exclusion for overlapping rectangles

```java
/**
 * 建模 Modeling: 在二维网格中建立前缀和，使得任意矩形区域的和可在O(1)时间内查询；核心是利用容斥原理处理二维坐标的重叠计算。| Modeling: Build 2D prefix sum over grid to query any rectangular region sum in O(1) time; core insight uses inclusion-exclusion principle to handle 2D coordinate overlaps.
 * 状态 State: prefix[r][c]表示从(0,0)到(r-1,c-1)的矩形区域的累积和；状态空间为O(m*n)个不同的矩形区域。| State: prefix[r][c] represents cumulative sum of rectangular region from (0,0) to (r-1,c-1); state space is O(m*n) distinct rectangles.
 * 辅助数据结构 Aux Structure: 二维数组prefix[m+1][n+1]，其中prefix[0][c]=0且prefix[r][0]=0作为边界哨兵；维护行列方向的累积和。| Aux Structure: 2D array prefix[m+1][n+1] with prefix[0][c]=0 and prefix[r][0]=0 as boundary sentinels; maintains row and column cumulative sums.
 * 状态转移 Transition: prefix[r][c] = prefix[r-1][c] + prefix[r][c-1] - prefix[r-1][c-1] + matrix[r-1][c-1]；查询(r1,c1)到(r2,c2)的矩形和 = prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]。| Transition: prefix[r][c] = prefix[r-1][c] + prefix[r][c-1] - prefix[r-1][c-1] + matrix[r-1][c-1]; query sum from (r1,c1) to (r2,c2) = prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1].
 * 选择算法 Solver: 二维前缀和预处理+容斥原理查询；通过减去超出矩形的部分，加上被重复减去的角落，精确计算目标区域。| Solver: 2D prefix sum preprocessing + inclusion-exclusion principle for queries; subtract out-of-rectangle regions, add back corner region counted twice, precisely compute target area.
 * 复杂度分析: 时间O(m*n)预处理+O(1)查询；空间O(m*n)存储二维前缀和数组。| Complexity: O(m*n) preprocessing + O(1) query; O(m*n) space for 2D prefix sum array.
 * 不变量 Invariant: prefix数组边界始终为0；prefix[r][c]准确反映(0,0)到(r-1,c-1)的矩形累积和；任意合法矩形查询结果正确；容斥原理保证4项公式的正确性。| Invariant: Prefix array boundaries always 0; prefix[r][c] accurately reflects rectangular sum from (0,0) to (r-1,c-1); any valid rectangular query is correct; inclusion-exclusion principle ensures 4-term formula correctness.
 */
class NumMatrix {
    private int[][] prefix;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        prefix = new int[m + 1][n + 1];

        for (int r = 1; r <= m; r++) {
            for (int c = 1; c <= n; c++) {
                prefix[r][c] = prefix[r - 1][c] + prefix[r][c - 1] - prefix[r - 1][c - 1] + matrix[r - 1][c - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return prefix[row2 + 1][col2 + 1] - prefix[row1][col2 + 1] - prefix[row2 + 1][col1] + prefix[row1][col1];
    }
}
```
⸻

State
prefix[row][col]

⸻

Transition
top
left
topLeft
prefix[r][c]=prefix[r-1][c]+prefix[r][c-1]-prefix[r-1][c-1]+matrix[r-1][c-1]
⸻

查询矩阵区域
O(1)

⸻

Google偶尔出现。

⸻

Prefix Sum识别口诀
看到：
subarray

连续数组

sum

区间和

k
优先想：
Prefix Sum

⸻

Sliding Window vs Prefix Sum
这是面试常问。

⸻

Sliding Window
适用于：
连续区间

动态扩张

动态收缩
例如：
Longest

Shortest

At Most K

⸻

Prefix Sum
适用于：
连续区间

快速求和
例如：
Subarray Sum = K

Range Sum

⸻

Google 高频例子
Sliding Window
LC3
Longest Substring Without Repeating Characters
State
left
right
charCount

⸻

LC76
Minimum Window Substring
State
left
right
needMap
windowMap

⸻

LC424
Character Replacement
State
left
right
countMap
maxFreq

⸻

Prefix Sum
LC560
Subarray Sum Equals K
State
currentSum
prefixCount

⸻

LC523
Continuous Subarray Sum
State
prefix % k

⸻

### Problem 11: Prefix Sum Variant - Contiguous Array
**LeetCode 525 | Medium**
**Link:** https://leetcode.com/problems/contiguous-array/
**Key Points:**
- Find max length subarray with equal 0s and 1s
- Convert problem: treat 0 as -1, find subarray sum = 0
- Use prefix balance map (0 → -1, 1 → +1)
- If prefixBalance repeats, subarray between equals 0
- Track first occurrence of each balance, store max distance
- Time: O(n), Space: O(n)
- Creative prefix sum transformation

```java
/**
 * 建模 Modeling: 将数组转换为前缀和，找到相同前缀和值对应的最大距离，对应连续子数组和为目标值的问题 | Modeling: Transform array to prefix sum and find maximum distance between indices with equal prefix sum values, corresponding to finding longest contiguous subarray with target sum
 * 状态 State: prefix_sum[i]表示从索引0到i的元素和；map记录每个前缀和第一次出现的位置 | State: prefix_sum[i] represents cumulative sum from index 0 to i; map stores first occurrence index of each prefix sum value
 * 辅助数据结构 Aux Structure: 哈希表（map）存储前缀和值到最早出现位置的映射 | Aux Structure: Hash table (map) mapping prefix sum values to their earliest occurrence indices
 * 状态转移 Transition: 对每个位置i，计算当前前缀和；如果前缀和存在于map中，则当前位置与map中对应位置的距离为一个有效的子数组长度 | Transition: For each position i, compute current prefix sum; if prefix sum exists in map, distance from current position to stored position gives valid subarray length
 * 选择算法 Solver: 单遍扫描数组，维护前缀和和哈希表，追踪最大子数组长度 | Solver: Single pass through array maintaining prefix sum and hash table, tracking maximum subarray length
 * 复杂度分析: 时间复杂度O(n)，空间复杂度O(n) | Complexity: Time complexity O(n), Space complexity O(n)
 * 不变量 Invariant: 相同前缀和对应的两个位置之间的子数组和始终为目标值；map中始终保存每个前缀和的第一次出现位置 | Invariant: Subarray between two indices with equal prefix sum always has target sum; map always stores first occurrence of each prefix sum value
 */
class Solution {
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int prefix = 0;
        int result = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                prefix += 1;
            } else {
                prefix -= 1;
            }

            if (map.containsKey(prefix)) {
                result = Math.max(result, i - map.get(prefix));
            } else {
                map.put(prefix, i);
            }
        }

        return result;
    }
}
```

State
prefixBalance
(0 记 -1，1 记 +1)

⸻

如果从 Google DSA 角度看，我会把：
Sliding Window
Prefix Sum
放在和：
BFS
DFS
Backtracking
Binary Search
Heap
同一个重要级别。
因为很多 Google Medium 就是在考你能不能快速识别：
这是维护窗口的问题，还是前缀和统计的问题。
一旦 Pattern 认出来，代码通常比 Graph 题简单得多。

⸻

## Summary: Pattern 3 Problems

**Covered 11 Problems** organized by Sliding Window and Prefix Sum:

### Sliding Window (5 problems)
- **Fixed Size Window**: LC643 Maximum Average Subarray
- **Variable Size Window**: LC3 Longest Substring Without Repeating Characters
- **At Most K**: LC424 Longest Repeating Character Replacement
- **Minimum Window**: LC76 Minimum Window Substring
- **Sliding Window + Deque**: LC239 Sliding Window Maximum

### Prefix Sum (6 problems)
- **Basic Prefix Sum**: LC303 Range Sum Query (1D)
- **Prefix Sum + HashMap**: LC560 Subarray Sum Equals K
- **Modulo Prefix Sum**: LC974 Subarrays Divisible By K
- **Modulo Prefix Sum (variant)**: LC523 Continuous Subarray Sum
- **2D Prefix Sum**: LC304 Range Sum Query (2D)
- **Prefix Sum Variant**: LC525 Contiguous Array

**Key Recognition Patterns:**
- Sliding Window: Look for "longest/shortest", "subarray", "substring", "at most", "at least"
- Prefix Sum: Look for "subarray sum", "range sum", "continuous", "divisible"
- Window Size Matters: Fixed vs Variable determines approach
- Two Maps: Classic pattern for minimum window problems
- Deque: Maintain monotonic property for optimal window queries
- HashMap + Prefix: Perfect for "find sum equals K" type problems
