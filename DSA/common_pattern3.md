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

### 🎯 Sliding Window - Simple Explanation

**概念:** 用两个指针（left 和 right）维护一个窗口，根据条件动态扩张或收缩。

**为什么？** 不用 O(n²) 逐个检查所有子数组，而是聪明地维护"有效窗口"。

**三个关键步骤：**
```
1. right 向右扩展 → 加入新元素
2. 检查窗口是否有效
3. 如果无效 → left 向右收缩 → 移除左端元素
```

**关键洞察：** 
- left 只向右移，从不回退
- 每个元素最多被访问 2 次（一次 right，一次 left）
- 所以时间复杂度 = O(n)，不是 O(n²)

很多人以为：
Sliding Window
=
两个指针

### Problem 1: Fixed Size Window - Maximum Average Subarray
**LeetCode 643 | Easy**

**💡 Key Insight & Why It Works:**

想象你要在数组里找一个长度固定为 k 的子数组，让平均数最大。

**笨办法：** 一个一个算，每次都重新加一遍 k 个数 → 慢

**聪明办法：** 
- 第一次：加第一个 k 个数，得到和
- 第二次：只需要移除最左边的数，加上新的数
- 比较所有的和，最大的和除以 k 就是答案

**为什么这样快？** 每个数只看两次，不用重复计算

**💬 For Interview - Just Say:**
- 维持一个**大小固定为 k 的窗口**
- 每次右移：移除左端，加入右端的新元素
- 追踪最大的窗口和，最后除以 k 就是最大平均值

```java
/**
 * 建模 Modeling: 找长度为k的子数组，使平均值最大 | Find subarray of length k with maximum average
 * 状态 State: windowSum = 当前窗口的和 | windowSum = sum of current window
 * 辅助数据结构 Aux Structure: 一个变量 windowSum | Simple variable windowSum
 * 状态转移 Transition: 移除左端，加入右端 | Remove left, add right: windowSum = windowSum - nums[left] + nums[right]
 * 选择算法 Solver: 滑动窗口 | Sliding window
 * 复杂度分析: O(n) 时间，O(1) 空间 | Time O(n), Space O(1)
 * 不变量 Invariant: 窗口大小恰好为 k | Window size always k
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

### Problem 2: Variable Size Window - Longest Substring Without Repeating Characters
**LeetCode 3 | Medium**

**💡 Key Insight & Why It Works:**

想象你拿着一个透明的框，框着字符串的一部分。框的左边和右边可以移动。

你的目标：找到最长的一段，里面的字母都不重复。

**怎么做？**
- 你的右手不断向右移，把新字母加进框里
- 如果框里出现了重复的字母，你的左手就从左往右移，把重复的字母扔出去
- 你记住沿路中最大的框是多大

**为什么这样做有效？**
- 不用检查所有可能的子串（那样太慢了）
- 左手只向右走，永远不回头，所以整个过程只需要遍历一遍字符串
- 那个最大的合法框就是你的答案

**💬 For Interview - Just Say:**
- 维持一个**没有重复字符**的窗口
- right 扩展窗口，发现重复时 left 收缩
- 在窗口有效时更新最长长度

**📚 For Learning - Full Framework:**
- **Modeling:** 找最长的不含重复字符的子串
- **State:** (left, right) 定义的窗口，窗口内字符全部唯一
- **Aux Structure:** Set<Character> 追踪窗口内的字符
- **Transition:** right 扩展时添加字符；发现重复 → left 收缩直到重复消除
- **Solver:** 滑动窗口（变大小）
- **Complexity:** O(n) 时间，O(min(字符集, n)) 空间
- **Invariant:** 窗口中永远没有重复字符
   
3. 辅助数据 Aux Structure: Set<Character>
   - 快速判断字符是否在窗口中（O(1)）
   
4. 状态转移 Transition:
   ```
   right++ 加入新字符
   ↓
   如果字符重复：left++ 移除左端，直到重复消除
   ↓
   记录最长窗口长度
   ```
   
5. 选择算法 Solver: 滑动窗口（变大小）
   
6. 复杂度 Complexity: O(n) 时间，O(min(字符集, n)) 空间
   
7. 不变量 Invariant: 窗口中永远没有重复字符

```java
/**
 * 建模 Modeling: 找最长不重复字符的子串 | Find longest substring without repeating chars
 * 状态 State: [left, right] 窗口，窗口内字符全唯一 | Window with all unique chars
 * 辅助数据结构 Aux Structure: Set<Character> 追踪窗口内的字符 | Track chars in window
 * 状态转移 Transition: right扩展，发现重复→left收缩直到重复消除 | Expand with right, shrink with left when duplicate
 * 选择算法 Solver: 滑动窗口 | Sliding window
 * 复杂度分析: O(n) 时间，O(min(字符集, n)) 空间 | Time O(n), Space O(min(charset, n))
 * 不变量 Invariant: 窗口内没有重复字符 | No duplicates in window
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

### Problem 3: At Most K Pattern - Longest Repeating Character Replacement
**LeetCode 424 | Medium**

**💡 Key Insight & Why It Works:**

你想让一个字符串的一部分所有字母都相同，最多改 k 个字母。

**关键问题：** 我需要改几个字母才能让窗口内所有字母都一样？

**答案：** 窗口里最常见的字母有多少个，就只需要改剩下的

比如：窗口有 10 个字母，最常见的字母出现 7 次，就只需要改 3 个。

**所以条件是：** `窗口大小 - 最高频率 <= k`

**💬 For Interview - Just Say:**
- 维持窗口，追踪每个字符的频率
- 判断条件：`窗口大小 - 最高频率 <= k` 就有效
- 如果超过 k，left 收缩；记录最长有效窗口
   
3. 辅助数据 Aux Structure: int[] freq（26个字母的频率）+ maxFreq（最高频率）
   
4. 状态转移 Transition:
   ```
   right++ 扩展窗口，更新 freq[]
   ↓
   检查：(窗口大小 - maxFreq) > k？
   ↓
   是 → left++ 收缩
   否 → 继续扩展
   ```
   
5. 选择算法 Solver: 滑动窗口
   
6. 复杂度 Complexity: O(n) 时间，O(1) 空间（固定 26 个字母）
   
7. 不变量 Invariant: 窗口满足条件后 left 不回退

```java
/**
 * 建模 Modeling: 最长子串，最多替换k个字符使所有字符相同 | Find longest substring with at most k replacements
 * 状态 State: [left, right] 窗口 + freq[] 频率 | Window + character frequencies
 * 辅助数据结构 Aux Structure: int[] freq + int maxFreq | Frequency array + max frequency
 * 状态转移 Transition: 扩展 right，检查 (size - maxFreq) <= k，如果否则收缩 left | Expand right, check condition, shrink left if needed
 * 选择算法 Solver: 滑动窗口 | Sliding window
 * 复杂度分析: O(n) 时间，O(1) 空间 | Time O(n), Space O(1)
 * 不变量 Invariant: (窗口大小 - 最高频率) <= k | Window size - max frequency <= k
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

### Problem 4: Minimum Window Pattern - Minimum Window Substring
**LeetCode 76 | Hard**

**💡 Key Insight & Why It Works:**

你要在一个大字符串里找最小的子串，里面包含了所有目标字母。

**两个阶段：**
1. **扩展阶段：** 右手不断向右，加字母，直到窗口包含了所有目标字母
2. **收缩阶段：** 左手向右移，去掉不需要的字母，找到最小的有效窗口

**关键：** 一旦右手找到了所有字母，左手就开始尽可能地缩小窗口，找最短的答案。

**💬 For Interview - Just Say:**
- 用 two pointers + 两个 Map（目标字符频率 vs 窗口字符频率）
- right 扩展直到窗口包含所有必需字符
- 然后 left 收缩寻找最小的有效窗口
   
3. 辅助数据 Aux Structure: 两个 HashMap
   - `need`: 目标字符的频率（不变）
   - `window`: 当前窗口的频率（动态）
   
4. 状态转移 Transition:
   ```
   阶段1：扩展（right++）
   ↓ 直到窗口包含所有目标字符（formed == need.size()）
   
   阶段2：收缩（left++）
   ↓ 记录最小窗口，移除字符直到窗口无效
   ↓ 重复
   ```
   
5. 选择算法 Solver: 双指针滑动窗口
   
6. 复杂度 Complexity: O(m+n) 时间，O(1) 空间（固定字符集）
   
7. 不变量 Invariant: 窗口要么包含所有目标字符，要么我们记录过一个有效窗口

```java
/**
 * 建模 Modeling: 找最小子串包含 t 的所有字符 | Find minimum window containing all chars from t
 * 状态 State: [left, right] 窗口 + formed（匹配的字符种类数）| Window + formed count
 * 辅助数据结构 Aux Structure: HashMap need（目标）+ HashMap window（当前）| Two hashmaps: need and window
 * 状态转移 Transition: 扩展 right 直到有效，收缩 left 找最小 | Expand right until valid, shrink left to minimize
 * 选择算法 Solver: 双指针滑动窗口 | Two-pointer sliding window
 * 复杂度分析: O(m+n) 时间，O(1) 空间 | Time O(m+n), Space O(1)
 * 不变量 Invariant: 窗口有效时包含所有目标字符 | When valid, window contains all target chars
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

### Problem 5: Sliding Window + Deque - Sliding Window Maximum
**LeetCode 239 | Hard**

**💡 Key Insight & Why It Works:**

你要找每个 k 大小窗口里的最大值。

**笨办法：** 每个窗口都扫一遍，找最大值 → 很慢

**聪明办法：** 用一个特殊的队列，只存"可能是最大值"的元素

**队列的规则：**
- 队列从大到小排列（后面的数都比前面的小）
- 新数来了：把所有比它小的数扔出队列（它们永远赢不了）
- 过期的数（超出窗口）也要扔出来
- 队头永远是当前窗口的最大值

**为什么快？** 每个数最多进队和出队一次，不用每次都比较

**💬 For Interview - Just Say:**
- 用**单调递减的 Deque** 存索引
- 移除窗口外的索引（左边过期）
- 移除队尾所有比当前元素小的索引，然后加入当前索引
- 队头始终是当前窗口的最大值
   
3. 辅助数据 Aux Structure: Deque<Integer>（单调递减）
   - 存索引不存值（方便判断是否过期）
   
4. 状态转移 Transition:
   ```
   对每个新元素 nums[i]：
   1. 移除过期索引（< i-k+1）
   2. 移除队尾的"较小值"（< nums[i]）
   3. 加入当前索引到队尾
   4. 队头 = 当前窗口最大值
   ```
   
5. 选择算法 Solver: 单调双端队列
   
6. 复杂度 Complexity: O(n) 时间，O(k) 空间
   - 每个元素最多入队出队各 1 次
   
7. 不变量 Invariant: 队列严格递减，队头永远是最大值

```java
/**
 * 建模 Modeling: 每个窗口的最大值（用单调队列）| Window maximum using monotonic deque
 * 状态 State: Deque 存递减顺序的索引 | Deque with decreasing indices
 * 辅助数据结构 Aux Structure: Deque<Integer>（单调递减）| Monotonic decreasing deque
 * 状态转移 Transition: 移除过期 → 移除小值 → 加入当前 | Remove expired → remove smaller → add current
 * 选择算法 Solver: 单调双端队列 | Monotonic deque
 * 复杂度分析: O(n) 时间，O(k) 空间 | Time O(n), Space O(k)
 * 不变量 Invariant: 队列递减，队头是最大值 | Queue decreasing, front is maximum
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

### 🎯 Prefix Sum - Simple Explanation

**概念:** 预先计算每个位置前面所有元素的和，这样查询任意区间的和只需 O(1)。

**核心公式：**
```
prefix[i] = 前 i 个元素的和
查询 [L, R] 的和 = prefix[R+1] - prefix[L]
```

**为什么有用？**
- 暴力方法：每次查询都要遍历，O(n²) 太慢
- Prefix Sum：一次初始化 O(n)，之后每次查询 O(1)

---

Pattern 1
普通 Prefix Sum

⸻

State
prefix[i] = 前 i 个元素的累积和 (cumulative sum of first i elements)

⸻

Transition
前缀和递推：prefix[i] = prefix[i-1] + nums[i]

⸻

查询
任意区间 [l, r] 的和 = prefix[r+1] - prefix[l]

⸻

### Problem 6: Basic Prefix Sum - Range Sum Query
**LeetCode 303 | Easy**

**💡 Key Insight & Why It Works:**

你经常要查询"数组从某个位置到另一个位置的和是多少"。

**笨办法：** 每次查询都重新加一遍 → 慢

**聪明办法：** 提前算好每个位置的"累计和"

比如数组是 `[1, 2, 3, 4]`，累计和是 `[0, 1, 3, 6, 10]`

要查询位置 1 到 3 的和？ 就是 `6 - 1 = 5`（就是 2 + 3）

**为什么？** 因为：`累计和[3] - 累计和[1] = 中间部分的和`

**💬 For Interview - Just Say:**
- 预计算 prefix 数组，prefix[i] = 前 i 个元素的和
- 查询区间 [L, R] 的和 = prefix[R+1] - prefix[L]
- 初始化 O(n)，查询 O(1)
   
3. 辅助数据 Aux Structure: int[] prefix（长度 n+1）
   - prefix[0] = 0（哨兵）
   
4. 状态转移 Transition:
   ```
   prefix[i] = prefix[i-1] + nums[i-1]
   ```
   
5. 选择算法 Solver: 一次线性扫描构建 prefix 数组
   
6. 复杂度 Complexity: O(n) 预处理 + O(1) 查询，O(n) 空间
   
7. 不变量 Invariant: prefix[i] 准确反映前 i 个元素的和

```java
/**
 * 建模 Modeling: 快速查询任意区间的和 | Fast range sum queries
 * 状态 State: prefix[i] = 前 i 个元素的和 | Cumulative sum up to index i
 * 辅助数据结构 Aux Structure: int[] prefix（长度 n+1）| Prefix array of size n+1
 * 状态转移 Transition: prefix[i] = prefix[i-1] + nums[i-1] | Cumulative recurrence
 * 选择算法 Solver: 线性扫描构建 | Linear scan to build
 * 复杂度分析: O(n) 预处理 + O(1) 查询 | O(n) preprocess + O(1) query
 * 不变量 Invariant: prefix[i] 准确 = sum(nums[0...i-1]) | Correct cumulative sum
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

**💡 Key Insight & Why It Works:**

你要数有多少个子数组的和等于某个目标 k。

**关键想法：** 如果我现在的累计和是 10，我要找和为 3 的子数组，那我需要找有没有累计和为 7 的时刻。因为 10 - 7 = 3！

**步骤：**
1. 走过数组，记住"我到这儿累计和是多少"
2. 对于每个位置，看看之前是否出现过 `当前和 - k` 这个值
3. 如果出现过，说明中间有个子数组的和等于 k

**为什么用 HashMap？** 因为可能同一个累计和出现很多次，我们要记住它出现了几次

**💬 For Interview - Just Say:**
- 用 HashMap 记录每个 prefix 出现的次数
- 初始化 map.put(0, 1)
- 遍历数组：prefix += num，查找 (prefix - k) 是否在 map 中
- 如果存在，count += map.get(prefix - k)，然后 map.put(prefix, ...)
   
3. 辅助数据 Aux Structure: HashMap<Integer, Integer>
   - Key = 前缀和值
   - Value = 该前缀和出现的频率（可能多次）
   
4. 状态转移 Transition:
   ```
   对每个元素：
   1. prefix += nums[i]（更新前缀和）
   2. 查找 (prefix - k) 是否在 map 中
   3. 如果在 → count += map.get(prefix - k)
   4. map.put(prefix, map.get(prefix) + 1)
   ```
   
5. 选择算法 Solver: 前缀和 + HashMap（支持负数）
   
6. 复杂度 Complexity: O(n) 时间，O(n) 空间
   
7. 不变量 Invariant: map 初始化包含 {0 → 1}；count 只增不减

```java
/**
 * 建模 Modeling: 计算和 = k 的子数组个数 | Count subarrays with sum = k
 * 状态 State: prefix = 当前累积和 | Current cumulative sum
 * 辅助数据结构 Aux Structure: HashMap<Integer, Integer> 前缀和频率 | Prefix sum frequencies
 * 状态转移 Transition: 查找 (prefix - k)，计数 | Check if (prefix - k) exists, count matches
 * 选择算法 Solver: 前缀和 + HashMap | Prefix sum + HashMap
 * 复杂度分析: O(n) 时间，O(n) 空间 | Time O(n), Space O(n)
 * 不变量 Invariant: map 初始 {0 → 1}，count 单调增 | Map starts with {0→1}, count increases
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

**💡 Key Insight & Why It Works:**

你要数有多少个子数组的和能被 k 整除。

**神奇的规律：** 如果两个不同位置的累计和除以 k 的余数相同，那么这两个位置之间的子数组和就能被 k 整除！

比如：
- 位置 3 的累计和是 17，17 % 5 = 2
- 位置 7 的累计和是 32，32 % 5 = 2
- 那么位置 4 到 7 的和一定能被 5 整除（因为 32 - 17 = 15，15 能被 5 整除）

**所以：** 我们只需要记住每个余数出现过几次，就能数出有多少个这样的子数组。

**💬 For Interview - Just Say:**
- 如果两个位置的 prefix % k 相同，那么中间的子数组和一定被 k 整除
- 用 HashMap 记录每个 mod 值出现的次数
- 遍历：mod = ((prefix % k) + k) % k，查找并计数

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

**💡 Key Insight & Why It Works:**

和 Problem 8 很像，但这次还要求子数组长度至少为 2。

**关键：** 如果两个位置的累计和除以 k 的余数相同，中间的子数组和就能被 k 整除。

**额外检查：** 只有当这两个位置距离 >= 2 时，才符合要求。

比如：
- 位置 1 的余数是 3
- 位置 4 的余数也是 3
- 距离 = 4 - 1 = 3 >= 2，所以有效！

**💬 For Interview - Just Say:**
- 如果两个位置的 prefix % k 相同，中间的子数组和被 k 整除
- 用 HashMap 记录每个 mod 值第一次出现的位置
- 保证距离 >= 2 才算有效

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

**💡 Key Insight & Why It Works:**

这是 1D 前缀和的 2D 版本。你要快速查询矩阵里任意矩形区域的和。

**思路：** 提前算好"从左上角到任何位置"的累计和

**查询时的技巧：** 用"容斥原理"（加法和减法）

比如你要查询右下角位置为 (5,5)，左上角为 (2,2) 的矩形：
- 先加上完整的大矩形（0,0 到 5,5）
- 减去上面的矩形（0,0 到 1,5）
- 减去左边的矩形（0,0 到 5,1）
- 再加回被减了两次的左上角（0,0 到 1,1）

这样就得到中间矩形的和。

**💬 For Interview - Just Say:**
- 预计算 2D prefix 数组：prefix[r][c] = 左上角 (0,0) 到 (r-1,c-1) 的和
- 公式：prefix[r][c] = prefix[r-1][c] + prefix[r][c-1] - prefix[r-1][c-1] + matrix[r-1][c-1]
- 查询矩形和用容斥原理：加减四个大矩形的和

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

**💡 Key Insight & Why It Works:**

你要找最长的子数组，里面有相同数量的 0 和 1。

**创意技巧：** 把问题转换一下！
- 把所有 0 变成 -1
- 把所有 1 保持为 +1
- 现在，"0 和 1 一样多"就变成了"和为 0"

比如 `[1, 0, 1, 0]` 变成 `[1, -1, 1, -1]`，和为 0，所以整个数组满足条件！

**然后：** 用前缀和 + HashMap 的方法来找最长的和为 0 的子数组。

**为什么这样聪明？** 因为我们已经知道怎么快速找"和为某个值"的子数组，只需要套用同样的方法。

**💬 For Interview - Just Say:**
- 创意转换：0 当做 -1，1 当做 +1
- 这样等数量的 0 和 1 的子数组，其和 = 0
- 转化成：找最长的子数组和 = 0，用 HashMap + prefix 方法

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
