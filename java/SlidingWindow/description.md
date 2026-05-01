# Sliding Window 滑动窗口

> **Core idea:** Maintain a window `[left, right]` over a sequence; expand right to include elements, shrink left to restore validity. Avoids re-scanning overlapping subproblems.
> **核心思想：** 维护一个区间 `[left, right]`，右指针扩张纳入元素，左指针收缩恢复合法性，避免重复扫描。
>
> Sliding Window = Two Pointers with a **dynamic validity constraint** on the interval.
> Complexity: O(n) — each element enters and leaves the window at most once.
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| substring / subarray | 子串 / 子数组 |
| contiguous / consecutive | 连续 |
| longest / shortest | 最长 / 最短 |
| at most K / at least K | 最多 K 个 / 至少 K 个 |
| without repeating | 不重复 |
| window / sliding | 窗口 |
| minimum covering | 最小覆盖 |

**When NOT to use 不适用的情况**

- Array has **negative numbers** and you need subarray sum → use Prefix Sum + HashMap instead (window sum is non-monotonic)
- Need non-contiguous elements → Two Pointers or DP
- Need all combinations → Backtracking

**Sliding Window vs Two Pointers**

| | Two Pointers | Sliding Window |
|---|---|---|
| Movement rule | Fixed logic (sorted array) | Dynamic: shrink when window is invalid |
| Window meaning | Two positions in relation | A valid interval to maintain |
| Typical use | Pair sum, palindrome, partition | Subarray/substring with constraint |

---

## 2. Quick Decision Guide 快速判断

```
Fixed length k, max/min of each window?     → Pattern 1: Fixed-size window
Longest window satisfying constraint?        → Pattern 2: Variable — Longest (shrink when invalid)
Shortest window satisfying constraint?       → Pattern 3: Variable — Shortest (shrink when valid)
At most K distinct / at most K changes?      → Pattern 4: At-most-K
Exactly K distinct?                          → Pattern 5: Exact-K = atMost(K) - atMost(K-1)
Count how many valid subarrays?             → Pattern 6: Counting Window
Max/min value inside sliding window?         → Pattern 7: Monotonic Deque Window
```

---

## 3. Patterns 模式

---

### Pattern 1 — Fixed-size Window 固定窗口

**When:** window length is given as k; compute something for every window of exactly k elements.
**适用：** 窗口长度固定为 k，对每个大小为 k 的窗口求最大/最小/平均等。

**Template 模板**

```java
// Initialize first window
int windowSum = 0;
for (int i = 0; i < k; i++) windowSum += nums[i];
int max = windowSum;

// Slide: add right, remove left
for (int i = k; i < nums.length; i++) {
    windowSum += nums[i] - nums[i - k];   // O(1) update
    max = Math.max(max, windowSum);
}
```

**Key insight 核心原理**

When you slide by one, you add one element on the right and remove one on the left — O(1) update instead of recomputing the whole window.
滑动时只做一加一减，避免重新求和。

**Variants 变形**

| Variant | Example |
|---|---|
| Maximum average subarray of length k | LC 643 |
| Number of subarrays of size k with average ≥ threshold | LC 1343 |
| Max sum subarray of size k | common |
| Sliding window with hash (permutation check) | LC 567 |

**Example: Permutation in String (LC 567)**

```java
public boolean checkInclusion(String s1, String s2) {
    int[] need = new int[26], window = new int[26];
    for (char c : s1.toCharArray()) { 
        need[c - 'a']++;
    }

    for (int i = 0; i < s2.length(); i++) {
        window[s2.charAt(i) - 'a']++;
        if (i >= s1.length()) {
            window[s2.charAt(i - s1.length()) - 'a']--;
        }
        if (Arrays.equals(window, need)) return true;
    }
    return false;
}
```

---

### Pattern 2 — Variable Window: Longest 可变窗口：最长

**When:** find the longest subarray/substring satisfying a constraint. Shrink when the window becomes invalid.
**适用：** 找满足约束条件的最长子数组/子串。窗口非法时收缩。

**Template 模板**

```java
int left = 0, max = 0;
Map<Character, Integer> freq = new HashMap<>();

for (int right = 0; right < s.length(); right++) {
    // 1. Expand: add s[right] into window
    freq.merge(s.charAt(right), 1, Integer::sum);

    // 2. Shrink: restore validity
    while (!isValid(freq)) {
        char lc = s.charAt(left++);
        freq.merge(lc, -1, Integer::sum);
        if (freq.get(lc) == 0) freq.remove(lc);
    }

    // 3. Update answer (window is always valid here)
    max = Math.max(max, right - left + 1);
}
```

**Key insight 核心原理**

Shrink when **invalid**; update answer after shrinking (window is guaranteed valid).
非法时收缩，收缩后窗口必然合法，此时更新最大长度。

**Variants 变形**

| Variant | Validity condition | Example |
|---|---|---|
| Longest substring no repeat | all chars unique | LC 3 |
| Longest with at most K distinct chars | `freq.size() <= k` | LC 340 |
| Longest repeating char after replacement | `len - maxFreq <= k` | LC 424 |

**Example: Longest Substring Without Repeating (LC 3)**

```java
public int lengthOfLongestSubstring(String s) {
    Set<Character> set = new HashSet<>();
    int left = 0, max = 0;
    for (int right = 0; right < s.length(); right++) {
        while (set.contains(s.charAt(right)))
            set.remove(s.charAt(left++));
        set.add(s.charAt(right));
        max = Math.max(max, right - left + 1);
    }
    return max;
}
```

**Example: Longest Repeating Character Replacement (LC 424)**

```java
public int characterReplacement(String s, int k) {
    int[] freq = new int[26];
    int left = 0, maxFreq = 0, max = 0;
    for (int right = 0; right < s.length(); right++) {
        maxFreq = Math.max(maxFreq, ++freq[s.charAt(right) - 'A']);
        // window length - most frequent char count = replacements needed
        while ((right - left + 1) - maxFreq > k) {
            freq[s.charAt(left++) - 'A']--;
        }
            
        max = Math.max(max, right - left + 1);
    }
    return max;
}
```

---

### Pattern 3 — Variable Window: Shortest 可变窗口：最短

**When:** find the shortest subarray/substring satisfying a constraint. Shrink **while** the window is valid to minimize length.
**适用：** 找满足约束条件的最短子数组/子串。窗口合法时持续收缩。

**Template 模板**

```java
int left = 0, min = Integer.MAX_VALUE;
// window state variables...

for (int right = 0; right < n; right++) {
    // 1. Expand
    addToWindow(nums[right]);

    // 2. Shrink WHILE valid (opposite of "longest")
    while (isValid()) {
        min = Math.min(min, right - left + 1);  // update inside shrink loop
        removeFromWindow(nums[left++]);
    }
}
return min == Integer.MAX_VALUE ? 0 : min;
```

**Key insight 核心原理**

For **shortest**: update answer **inside** the shrink loop — keep shrinking as long as valid to squeeze out the minimum.
For **longest**: update answer **outside** the shrink loop — window is always valid when you update.
最短问题：在收缩循环里更新答案（合法时尽量缩）。
最长问题：在收缩循环外更新答案（收缩后必然合法）。

**Variants 变形**

| Variant | Example |
|---|---|
| Minimum window substring (cover all chars of t) | LC 76 |
| Minimum size subarray sum ≥ target | LC 209 |

**Example: Minimum Window Substring (LC 76)**

```java
public String minWindow(String s, String t) {
    Map<Character, Integer> need = new HashMap<>(), window = new HashMap<>();
    for (char c : t.toCharArray()) need.merge(c, 1, Integer::sum);

    int left = 0, formed = 0, required = need.size();
    int minLen = Integer.MAX_VALUE, minStart = 0;

    for (int right = 0; right < s.length(); right++) {
        char rc = s.charAt(right);
        window.merge(rc, 1, Integer::sum);
        if (need.containsKey(rc) && window.get(rc).equals(need.get(rc))) formed++;

        while (formed == required) {
            if (right - left + 1 < minLen) { minLen = right - left + 1; minStart = left; }
            char lc = s.charAt(left++);
            if (need.containsKey(lc) && window.merge(lc, -1, Integer::sum) < need.get(lc)) formed--;
        }
    }
    return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
}
```

**`formed` counter trick 关键技巧**

Use `formed` to track how many distinct characters are fully satisfied, instead of scanning the whole `need` map each time — O(1) validity check.
用 `formed` 计数已满足的字符种类数，避免每次遍历整个 map，使合法性判断降为 O(1)。

---

### Pattern 4 — At-most-K 最多K个

**When:** "at most K distinct characters / changes / zeros" — shrink when the resource count exceeds K.
**适用：** "最多 K 个不同元素/修改次数/零"，超过 K 时收缩。

**Template 模板**

```java
private int atMost(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    int left = 0, count = 0;
    for (int right = 0; right < nums.length; right++) {
        freq.merge(nums[right], 1, Integer::sum);
        while (freq.size() > k) {                        // resource exceeded
            if (freq.merge(nums[left++], -1, Integer::sum) == 0)
                freq.remove(nums[left - 1]);
        }
        count += right - left + 1;  // all subarrays ending at right are valid
    }
    return count;
}
```

**Key insight 核心原理**

`right - left + 1` counts all valid subarrays ending at `right`. This counting trick is the base for Exact-K.
每个合法窗口 `[left..right]` 包含 `right - left + 1` 个以 `right` 结尾的合法子数组。

**Variants 变形**

| Variant | k means | Example |
|---|---|---|
| At most K distinct chars | distinct char count | LC 340 |
| At most K zeros (flip 0→1) | zeros in window | LC 1004 |
| At most K replacements | `len - maxFreq` | LC 424 |

---

### Pattern 5 — Exact-K 恰好K个

**When:** count subarrays with **exactly** K distinct / sum exactly K. Can't do directly — decompose into two At-most problems.
**适用：** 恰好 K 个不同元素的子数组数。不能直接做，拆成两个「最多」问题。

**Formula 公式**

```
exactly(K) = atMost(K) - atMost(K - 1)
```

**Example: Subarrays with K Different Integers (LC 992)**

```java
public int subarraysWithKDistinct(int[] nums, int k) {
    return atMost(nums, k) - atMost(nums, k - 1);
}
```

**Key insight 核心原理**

"Exactly K" windows are hard to maintain because adding one element can jump past K. Subtraction of two at-most counts gives the exact count cleanly.
直接维护「恰好」K 很难，因为一个元素可能让不同元素数跨过 K。差分两个「最多」问题可以干净地得到精确计数。

---

### Pattern 6 — Counting Window 计数型窗口

**When:** count the **number** of valid subarrays, not just find the longest/shortest.
**适用：** 统计满足条件的子数组数量，而不只是找最长/最短。

**Key formula 关键公式**

```java
count += right - left + 1;  // subarrays ending at right, starting from [left..right]
```

**Why this works:** once `[left, right]` is valid, all `right - left + 1` subarrays ending at `right` (starting at left, left+1, ..., right) are also valid.
合法窗口 `[left, right]` 中，所有以 `right` 结尾的子数组（起点从 left 到 right）都合法。

**Variants 变形**

| Variant | Example |
|---|---|
| Count subarrays with product < k | LC 713 |
| Count subarrays with sum ≤ k (non-negative) | custom |

---

### Pattern 7 — Monotonic Deque Window 单调队列窗口

**When:** need the **max or min value inside a sliding window** of size k in O(n).
**适用：** 在大小为 k 的滑动窗口内，O(n) 求最大值或最小值。

**Template 模板 (max)**

```java
Deque<Integer> deque = new ArrayDeque<>();  // stores indices, front = max

for (int i = 0; i < nums.length; i++) {
    // 1. Evict out-of-window from front
    while (!deque.isEmpty() && deque.peekFirst() < i - k + 1)
        deque.pollFirst();

    // 2. Evict smaller values from back (maintain decreasing order)
    while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i])
        deque.pollLast();

    deque.addLast(i);

    if (i >= k - 1) result[i - k + 1] = nums[deque.peekFirst()];
}
```

**Key insight 核心原理**

The deque is always sorted in decreasing order of value. The front is always the index of the maximum in the current window.
双端队列保持单调递减（值），队头始终是当前窗口最大值的下标。

**Variants 变形**

| Variant | Example |
|---|---|
| Sliding window maximum | LC 239 |
| Sliding window minimum | same, flip comparison |
| Jump game with window constraint | LC 1696 |

---

## 4. Advanced Skills 进阶技能

### Skill 1 — When to Shrink 什么时候收缩

| Goal | Shrink condition | Update answer |
|---|---|---|
| Longest | while **invalid** | after `while` loop (outside) |
| Shortest | while **valid** | inside `while` loop |
| Count | while **invalid** | `count += right - left + 1` after loop |

### Skill 2 — What to Maintain in the Window 窗口内维护什么

Not every problem needs a HashMap. Choose the right state:

| State needed | Data structure |
|---|---|
| Distinct character count | `HashMap<Char, Int>` or `int[26]` |
| Running sum | single `int` |
| Max frequency in window | `int maxFreq` (track lazily) |
| Max/min value | Monotonic deque |

### Skill 3 — Why left Never Goes Back 为什么 left 不回退

`left` is monotonically non-decreasing → both pointers traverse the array at most once → O(n).
If you ever reset `left` backwards, it becomes O(n²). Trust the invariant.

### Skill 4 — `formed` Counter Pattern formed 计数器

Instead of checking `window.equals(need)` O(k) each time, track `formed = number of chars where window[c] >= need[c]`. Valid when `formed == need.size()`. O(1) per step.

### Skill 5 — Negative Numbers 负数问题

Sliding window requires **monotonicity**: adding an element increases some measure, removing decreases it.
With negative numbers, sum is non-monotonic → window shrinking can't restore validity → use **Prefix Sum + HashMap** instead.

---

## 5. Interview Script 面试话术

**English:**
> I'd use sliding window because the problem asks for a contiguous subarray/substring satisfying [constraint]. I expand the right pointer to include elements and shrink the left pointer when the window becomes invalid. Since each element enters and leaves the window at most once, the total time is O(n). [For shortest: I keep shrinking while valid and update the answer inside the shrink loop.]

**中文：**
> 这道题要找满足[约束]的连续子数组/子串，我会用滑动窗口。右指针扩张纳入元素，窗口非法时收缩左指针。每个元素最多进出窗口各一次，所以时间复杂度是 O(n)。[最短问题：合法时持续收缩，在收缩循环内更新最小值。]

---

## 6. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Fixed-size window | 643, 567, 438 |
| 2. Variable — Longest | 3, 340, 424 |
| 3. Variable — Shortest | 76, 209 |
| 4. At-most-K | 340, 1004 |
| 5. Exact-K | 992, 930 |
| 6. Counting | 713 |
| 7. Monotonic deque | 239 |

**Recommended order 建议练习顺序:**
3 → 567 → 209 → 424 → 76 → 713 → 992 → 239

---

## 7. One-line Summary 一句话总结

```
Sliding Window = maintain a valid interval by expanding right and shrinking left,
                 updating the answer whenever the window satisfies the constraint.

滑动窗口 = 右扩左缩维持合法区间，满足条件时更新答案。
```

**Next steps 下一步:**
- Prefix Sum — handles subarrays with negative numbers (non-monotonic sums) 前缀和（处理含负数的区间和）
- Monotonic Stack — for range-based questions within each window 单调栈（窗口内最大/最小范围问题）
