# Prefix Sum + HashMap 前缀和 + 哈希表

> **Core idea:** Use a running prefix sum and a HashMap of past prefix sums to answer subarray questions in O(n). The key transform: `sum(i, j) = prefix[j] - prefix[i]`, so finding subarrays with a target sum becomes a lookup problem.
> **核心思想：** 用运行中的前缀和 + 过去前缀和的HashMap，O(n) 回答子数组问题。关键变换：`sum(i,j) = prefix[j] - prefix[i]`，寻找目标和的子数组变成查找问题。
>
> This is the combination of two techniques — it appears so frequently that it warrants its own pattern.
> Complexity: O(n) time, O(n) space.
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| subarray sum equals k | 子数组和等于k |
| number of subarrays with sum | 和为某值的子数组数量 |
| longest subarray with sum | 和为某值的最长子数组 |
| subarray sum divisible by k | 子数组和被k整除 |
| equal number of 0s and 1s | 0和1数量相等 |
| balance of two characters | 两类字符数量相等 |
| prefix sum difference | 前缀和差值 |

**Why not Sliding Window? 为什么不用滑动窗口？**

Sliding window requires **monotonicity**: adding an element to the window must consistently increase (or decrease) the tracked value. With **negative numbers**, this breaks — the window can't reliably shrink to find valid subarrays.

Prefix Sum + HashMap handles negatives because it doesn't rely on monotonicity — it uses a lookup table of past prefix sums.
滑动窗口需要单调性，负数会破坏单调性。前缀和+哈希表不依赖单调性，通过查找历史前缀和解决问题。

---

## 2. Core Formula 核心公式

```
prefix[j] - prefix[i] = targetSum
→ prefix[i] = prefix[j] - targetSum

When scanning position j (current prefix sum = runningSum):
look up how many times (runningSum - targetSum) has appeared as a past prefix sum.
```

---

## 3. Quick Decision Guide 快速判断

```
Count subarrays with sum = k?                    → Pattern 1: Count mode
Longest subarray with sum = k?                   → Pattern 2: Max length mode
Subarray sum divisible by k?                     → Pattern 3: Modulo trick
Equal count of two values (0s/1s, A/B)?          → Pattern 4: Remap to ±1 → sum = 0
Subarray with sum in range [lo, hi]?             → Pattern 5: Two lookups
```

---

## 4. Patterns 模式

---

### Pattern 1 — Count Subarrays with Sum = k 统计和为k的子数组数

**When:** count how many contiguous subarrays have sum exactly equal to k.
**适用：** 统计和恰好为k的连续子数组数量。

**Template 模板**

```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    freq.put(0, 1);          // sentinel: prefix sum 0 seen once before array starts

    int runningSum = 0, count = 0;
    for (int num : nums) {
        runningSum += num;
        count += freq.getOrDefault(runningSum - k, 0);  // look up BEFORE inserting
        freq.merge(runningSum, 1, Integer::sum);
    }
    return count;
}
```

**Two critical details 两个关键细节**

1. **`freq.put(0, 1)` sentinel:** handles subarrays starting at index 0. Without it, if the entire prefix sums to k, we'd miss it because `runningSum - k = 0` would have no entry.
2. **Look up before inserting:** prevents using the current prefix sum to match itself (which would create a zero-length subarray).

**Why `count += freq.get(runningSum - k)` and not `count++`?**
The same prefix value can appear multiple times. Each occurrence represents a different valid start point for a subarray ending here.
同一前缀值可能出现多次，每次出现都代表一个不同的有效起点。

---

### Pattern 2 — Longest Subarray with Sum = k 和为k的最长子数组

**When:** find the maximum length of a contiguous subarray with sum equal to k.
**适用：** 找和等于k的最长连续子数组长度。

**Template 模板**

```java
public int maxSubarrayLen(int[] nums, int k) {
    Map<Integer, Integer> firstSeen = new HashMap<>();
    firstSeen.put(0, -1);    // prefix sum 0 first seen at index -1 (before array)

    int runningSum = 0, maxLen = 0;
    for (int i = 0; i < nums.length; i++) {
        runningSum += nums[i];
        if (firstSeen.containsKey(runningSum - k)) {
            maxLen = Math.max(maxLen, i - firstSeen.get(runningSum - k));
        }
        firstSeen.putIfAbsent(runningSum, i);   // only store FIRST occurrence
    }
    return maxLen;
}
```

**Count vs Length — critical difference 统计 vs 最长的关键区别**

| Goal | HashMap stores | Update rule |
|---|---|---|
| **Count** subarrays | `prefix → frequency` | `merge(sum, 1, Integer::sum)` — every occurrence |
| **Max length** subarray | `prefix → earliest index` | `putIfAbsent(sum, i)` — first occurrence only |

**Why `putIfAbsent` for max length?** We want the earliest (leftmost) occurrence of each prefix sum to maximize the subarray length: `length = current_i - earliest_occurrence`.
最长子数组需要最早出现的位置最大化长度：`长度 = 当前i - 最早出现位置`。

---

### Pattern 3 — Modulo Trick (Divisible by k) 取模技巧（被k整除）

**When:** count subarrays whose sum is divisible by k.
**适用：** 统计和能被k整除的子数组数量。

**Core derivation 核心推导**

```
(prefix[j] - prefix[i]) % k == 0
→ prefix[j] % k == prefix[i] % k

Two positions with the same remainder → the subarray between them is divisible by k.
两个位置有相同余数 → 中间这段子数组的和能被k整除。
```

**Template 模板**

```java
public int subarraysDivByK(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    freq.put(0, 1);

    int runningSum = 0, count = 0;
    for (int num : nums) {
        runningSum += num;
        int rem = ((runningSum % k) + k) % k;   // handle Java negative mod
        count += freq.getOrDefault(rem, 0);
        freq.merge(rem, 1, Integer::sum);
    }
    return count;
}
```

**Java negative mod fix:** `(-7) % 3 == -1` in Java, but we need `2`. Fix: `((x % k) + k) % k`.
Java负数取模修正：`(-7) % 3 == -1`，需要是 `2`。修正：`((x % k) + k) % k`。

**Variants 变形**

| Variant | Key change | Example |
|---|---|---|
| Count subarrays divisible by k | mod trick | LC 974 |
| Continuous subarray sum (multiple of k, len ≥ 2) | check `i - firstSeen[rem] >= 2` | LC 523 |

---

### Pattern 4 — Remap to ±1 (Equal Count Problems) 重映射为±1（数量相等问题）

**When:** find subarrays with equal counts of two values (0s and 1s, characters A and B).
**适用：** 找两类值数量相等的子数组（0和1数量相等，A和B数量相等）。

**Key transformation 关键转换**

Remap: `0 → -1`, `1 → +1` (or `A → -1`, `B → +1`). Now "equal counts" = "subarray sum = 0". Use Pattern 1 or Pattern 2.

```java
// Equal 0s and 1s (LC 525)
public int findMaxLength(int[] nums) {
    Map<Integer, Integer> firstSeen = new HashMap<>();
    firstSeen.put(0, -1);

    int runningSum = 0, maxLen = 0;
    for (int i = 0; i < nums.length; i++) {
        runningSum += nums[i] == 1 ? 1 : -1;   // remap 0 → -1
        if (firstSeen.containsKey(runningSum)) {
            maxLen = Math.max(maxLen, i - firstSeen.get(runningSum));
        } else {
            firstSeen.put(runningSum, i);
        }
    }
    return maxLen;
}
```

**Variants 变形**

| Variant | Remap | Target sum | Example |
|---|---|---|---|
| Equal 0s and 1s | 0→-1, 1→+1 | sum = 0 | LC 525 |
| Equal As and Bs | A→-1, B→+1 | sum = 0 | LC 1695 |
| Count subarrays with k ones | 0→0, 1→+1 | sum = k | LC 560 variant |

---

### Pattern 5 — Sum in Range [lo, hi] 和在范围内的子数组

**When:** count subarrays with sum between lo and hi (inclusive).
**适用：** 统计和在 [lo, hi] 范围内的子数组数量。

**Decomposition 分解**

```
count(sum in [lo, hi]) = count(sum <= hi) - count(sum <= lo - 1)
                       = count(sum - hi in prev prefixes) - count(sum - lo in prev prefixes)
```

Or equivalently: use an **ordered structure** (TreeMap, sorted array + binary search) to count how many past prefix sums fall in a range.

**Template 模板 (with sorted list + binary search)**

```java
public int countRangeSum(int[] nums, int lower, int upper) {
    List<Long> prefixes = new ArrayList<>();
    prefixes.add(0L);
    long runningSum = 0, count = 0;

    for (int num : nums) {
        runningSum += num;
        // Count past prefixes where (runningSum - prefix) is in [lower, upper]
        // i.e., prefixes in [runningSum - upper, runningSum - lower]
        long lo = runningSum - upper, hi = runningSum - lower;
        count += upperBound(prefixes, hi) - lowerBound(prefixes, lo);
        insertSorted(prefixes, runningSum);
    }
    return (int) count;
}
```

---

## 5. Advanced Skills 进阶技能

### Skill 1 — Always Initialize `{0: 1}` or `{0: -1}` 始终初始化哨兵

- **Count mode:** `freq.put(0, 1)` — the "empty" prefix (before index 0) has sum 0, seen once
- **Max length mode:** `firstSeen.put(0, -1)` — sum 0 first seen at index -1, so length = `i - (-1) = i + 1`

Without this, subarrays starting at index 0 are invisible to the lookup.

### Skill 2 — Lookup Before Insert 先查找后插入

```java
// CORRECT order:
count += freq.getOrDefault(runningSum - k, 0);   // look up
freq.merge(runningSum, 1, Integer::sum);          // then insert

// WRONG order (using current prefix to match itself = zero-length subarray):
freq.merge(runningSum, 1, Integer::sum);          // insert first
count += freq.getOrDefault(runningSum - k, 0);   // look up — BUG
```

### Skill 3 — Negative Numbers Are Fine 负数完全没问题

Unlike sliding window, prefix sum + HashMap works perfectly with negative numbers because there's no monotonicity assumption — we just look up hash table entries.

### Skill 4 — Recognize the Remap Pattern 识别重映射模式

Any problem asking "equal count of X and Y" or "balance of X and Y" is secretly a "sum = 0" problem after remapping. This is a common disguise in interviews.
任何"X和Y数量相等"或"X和Y平衡"的问题，重映射后都是"和=0"问题。这是面试中常见的伪装。

---

## 6. Interview Script 面试话术

**English:**
> I'd use prefix sum with a HashMap. The key insight is that `sum(i, j) = prefix[j] - prefix[i]`, so finding subarrays with sum = k becomes: for each j, look up how many previous prefix sums equal `prefix[j] - k`. I initialize the map with `{0: 1}` to handle subarrays starting at index 0. I always look up before inserting the current prefix sum to avoid counting zero-length subarrays.

**中文：**
> 我会用前缀和+哈希表。关键洞察是 `sum(i,j) = prefix[j] - prefix[i]`，所以找和为k的子数组，等价于：对每个j，查找有多少历史前缀和等于 `prefix[j] - k`。初始化 `{0: 1}` 处理从下标0开始的子数组。先查找后插入当前前缀和，避免把当前位置自身算进去。

---

## 7. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Count (sum = k) | 560, 930 |
| 2. Max length (sum = k) | 325, 1658 |
| 3. Modulo | 974, 523 |
| 4. Remap ±1 | 525, 1371 |
| 5. Range [lo, hi] | 327 |

**Recommended order:** 560 → 325 → 525 → 974 → 523 → 930 → 327

---

## 8. One-line Summary 一句话总结

```
Prefix Sum + HashMap = transform "find subarray with sum k" into "look up (currentSum - k) in a table of past prefix sums."
前缀和 + 哈希表 = 把"找和为k的子数组"转化为"在历史前缀和表中查找(当前和-k)"。
```

**Related patterns 相关模式**

- `PrefixSum/desciption.md` — broader prefix sum patterns including 2D, difference array, binary search
- `SlidingWindow/description.md` — when input is non-negative and window is monotone
