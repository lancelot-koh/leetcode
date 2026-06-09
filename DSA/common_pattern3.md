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

### What is Sliding Window?
简单来说：用两个指针维护一个连续区间，根据题目条件动态扩张或收缩。  
Simply put: Use two pointers to maintain a continuous interval, expanding or shrinking based on conditions.

**核心步骤 Core Steps:**
1. right 指针向右扩展 → add new element
2. 检查是否满足条件 → check condition
3. 如果不满足 → left 指针向右收缩 → remove left element
4. 更新答案 → update answer

---

* 建模 Modeling: 问题本质是在**连续子数组**中寻找某种特性（最长/最短）。不需要枚举所有子数组(O(n²))，而是通过维护一个有效窗口动态调整。

* Modeling: The key is finding a property (longest/shortest) in **continuous subarrays**. Instead of checking all O(n²) subarrays, maintain a valid window and adjust it dynamically.

* 状态 State: 状态 = (left 指针位置, right 指针位置, 窗口内数据)。状态空间 = O(n²) 个可能的窗口，但只需维护**一个有效窗口**。

* State: State = (left pointer, right pointer, window data). State space = O(n²) possible windows, but we only maintain **one valid window at a time**.

* 辅助数据结构 Aux Structure: 根据具体问题选择：Set(去重)、Map(频率统计)、或简单变量(和、计数)。目的：快速判断窗口是否满足条件。

* Aux Structure: Choose based on problem: Set (uniqueness), Map (frequency), or simple variables (sum, count). Purpose: quickly check if window is valid.

* 状态转移 Transition: 三个关键动作：
  * (1) right++ → 扩展窗口，加入新元素 | expand: add nums[right]
  * (2) 检查是否违反条件 | check if invalid
  * (3) left++ → 收缩窗口，移除左端元素 | shrink: remove nums[left]

* Transition: Three key actions:
  * (1) right++ → expand window, add nums[right]
  * (2) check if condition violated
  * (3) left++ → shrink window, remove nums[left]

* 选择算法 Solver: 滑动窗口是一种**贪心策略**：当找到一个有效窗口后，left 不需要回退。这保证了时间复杂度是 O(n)，而不是 O(n²)。

* Solver: Sliding window is a **greedy strategy**: once you find a valid window, left pointer never backtracks. This ensures O(n) time instead of O(n²).

* 复杂度分析 Complexity: 时间 O(n) —— 每个元素最多被访问 2 次（一次 right，一次 left）。空间 O(?) —— 取决于辅助结构（通常 O(1) 或 O(alphabet size))。

* Complexity Analysis: Time O(n) — each element visited at most twice (once by right, once by left). Space O(?) — depends on auxiliary structure (usually O(1) or O(alphabet size)).

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

**核心思路 Key Idea:**
- 固定窗口大小 = k
- 滑动窗口：移除左端，加入右端
- 维护最大和，最后除以 k 得平均值

**7步框架 7-Step Framework:**

1. 建模 Modeling: 在数组中找长度为 k 的子数组，使平均值最大
   - Transform: 先找最大和 → 再除以 k = 最大平均值

2. 状态 State: (windowSum) 当前窗口的和
   - 窗口大小固定 = k，位置由 right 指针决定
   
3. 辅助数据 Aux Structure: 一个变量 `windowSum`，每次滑动更新它
   
4. 状态转移 Transition:
   ```
   右移：windowSum = windowSum - nums[i-k] + nums[i]
   ```
   
5. 选择算法 Solver: 滑动窗口（单次遍历）
   
6. 复杂度 Complexity: O(n) 时间，O(1) 空间
   
7. 不变量 Invariant: 窗口始终包含恰好 k 个元素

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

**核心思路 Key Idea:**
- 维持一个**没有重复字符**的窗口
- right 扩展窗口，发现重复时 left 收缩
- 在窗口有效时更新答案

**7步框架 7-Step Framework:**

1. 建模 Modeling: 找最长的不含重复字符的子串
   - Transform: 窗口内字符必须都不同 → 找最长的

2. 状态 State: (left, right) 定义的窗口
   - 窗口内的字符必须全部唯一
   
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

**核心思路 Key Idea:**
- 窗口中**最多替换 k 个字符**使所有字符相同
- 判断条件：`窗口大小 - 最频繁字符的频率 <= k`
- 如果超过 k，left 收缩窗口

**7步框架 7-Step Framework:**

1. 建模 Modeling: 找最长子串，最多替换 k 个字符使所有字符相同
   - Key：只需替换那些"不是最频繁字符"的字符

2. 状态 State: (left, right) 和 freq[] 字符频率
   
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

**核心思路 Key Idea:**
- 找最小的窗口，**包含目标字符串的所有字符**
- 两个 Map：`need`（目标） vs `window`（当前窗口）
- right 扩展直到**有效**，left 收缩寻找**最小**

**7步框架 7-Step Framework:**

1. 建模 Modeling: 找最小子串，包含 t 中的所有字符
   - Transform: 需要匹配字符种类数

2. 状态 State: (left, right) + need[] + window[]
   - `formed` = 有多少个字符的频率已匹配
   
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

**核心思路 Key Idea:**
- 找每个固定大小窗口中的**最大值**
- 暴力：每个窗口 O(k)，总共 O(nk) ❌
- 优化：用单调递减的双端队列维护候选值 ✅

**7步框架 7-Step Framework:**

1. 建模 Modeling: 每个窗口的最大值（固定大小 k）
   - Key：不需要存储所有元素，只存"可能的最大值"

2. 状态 State: Deque 存储索引（递减顺序）
   - 队头 = 当前窗口的最大值索引
   
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

### What is Prefix Sum?
简单来说：预先计算累积和，使得任何区间的和都能在 O(1) 时间查询。  
Simply put: Precompute cumulative sums so any range sum query is O(1).

**核心公式 Core Formula:**
```
prefix[i] = prefix[i-1] + nums[i]
查询区间和：sum(l, r) = prefix[r+1] - prefix[l]
```

**为什么有用？Why useful?**
- 暴力遍历：O(n²)（对每个范围求和）Brute force: O(n²)
- Prefix Sum：O(n) 预处理 + O(1) 查询 Prefix Sum: O(n) preprocess + O(1) query

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

**核心思路 Key Idea:**
- 预先计算 `prefix[i]` = 前 i 个元素的和
- 任何区间和 = `prefix[right+1] - prefix[left]` → O(1) 查询

**7步框架 7-Step Framework:**

1. 建模 Modeling: 快速查询任意区间的和
   - Transform：预计算 prefix 数组

2. 状态 State: prefix[i] = nums[0] + nums[1] + ... + nums[i-1]
   
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

**核心思路 Key Idea:**
- 找有多少个子数组的**和 = k**
- 暴力：O(n²) 枚举所有子数组 ❌
- 优化：前缀和 + HashMap，O(n) ✅

**关键公式 Key Formula:**
```
prefix[i] - prefix[j] = k
  ↓
说明：区间 [j+1...i] 的和 = k

转化为：prefix[i] - k 是否在 map 中？
```

**7步框架 7-Step Framework:**

1. 建模 Modeling: 计算有多少个子数组和 = k
   - Transform：从枚举子数组 → 查找前缀和对

2. 状态 State: 当前的 prefix（累积和）
   
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
