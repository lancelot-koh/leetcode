# Prefix Sum 前缀和

> **Core idea:** Transform a range/interval problem into a difference of two prefix values. Query any subarray sum in O(1) after O(n) preprocessing.
> **核心思想：** 把区间问题转化为两个前缀值之差。O(n) 预处理后，O(1) 查询任意子数组和。
>
> Key formula: `sum(l, r) = prefix[r+1] - prefix[l]`
> Complexity: O(n) build, O(1) query.
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| subarray sum | 子数组和 |
| range sum query | 区间和查询 |
| sum equals k | 和等于 k |
| sum divisible by k | 和能被 k 整除 |
| count subarrays | 统计子数组数量 |
| cumulative / prefix | 累积 / 前缀 |
| matrix region sum | 矩阵区域和 |

**Prefix Sum vs Sliding Window 前缀和 vs 滑动窗口**

| Scenario | Use |
|---|---|
| Non-negative numbers, contiguous, size constraint | Sliding Window |
| **Negative numbers** present | Prefix Sum + HashMap |
| Count subarrays with exact sum | Prefix Sum + HashMap |
| Multiple range queries on static array | Prefix Sum array |
| Range update then query | Difference Array |

**Key rule:** If the array has **negative numbers**, sliding window loses its monotonicity guarantee. You must use prefix sum.
关键：数组含**负数**时，滑动窗口失去单调性，必须用前缀和。

---

## 2. Quick Decision Guide 快速判断

```
Static range sum queries?                       → Pattern 1: Basic prefix sum array
Subarray sum equals k (with negatives)?         → Pattern 2: Prefix sum + HashMap
Subarray sum divisible by k?                    → Pattern 3: Prefix sum + Mod
Count subarrays with sum in range?              → Pattern 4: Prefix sum + sorting/BIT
2D matrix region sum?                           → Pattern 5: 2D prefix sum
Range updates, then query totals?               → Pattern 6: Difference array
Longest subarray with sum = k?                  → Pattern 2 variant (store earliest index)
```

---

## 3. Patterns 模式

---

### Pattern 1 — Basic Prefix Sum Array 基础前缀和数组

**When:** multiple range sum queries on a static array.
**适用：** 静态数组上的多次区间和查询。

**Build & Query 构建与查询**

```java
// Build: O(n)
int[] prefix = new int[n + 1];  // 1-indexed: prefix[0] = 0
for (int i = 0; i < n; i++) {
    prefix[i + 1] = prefix[i] + nums[i];
}

// Query sum of nums[l..r] (0-indexed): O(1)
int rangeSum = prefix[r + 1] - prefix[l];
```

**Why size n+1? 为什么开 n+1？**

`prefix[0] = 0` as a sentinel. Query `[0, r]` becomes `prefix[r+1] - prefix[0]` — no special case for l=0.
哨兵值 `prefix[0] = 0`，查询 `[0, r]` 时无需特判 l=0 边界。

**Variants 变形**

| Variant | Example |
|---|---|
| Range sum query (immutable) | LC 303 |
| Running product (careful with zeros) | LC 238 |
| Prefix XOR (find subarray XOR = k) | custom |

---

### Pattern 2 — Prefix Sum + HashMap 前缀和 + 哈希表

**When:** count or find subarrays with sum = k, especially when negative numbers are present.
**适用：** 统计或查找和为 k 的子数组（含负数时滑动窗口失效）。

**Core formula 核心公式**

```
sum(i+1, j) = prefix[j] - prefix[i] = k
→ prefix[i] = prefix[j] - k

When scanning position j with current prefix sum `sum`:
look for how many past prefix sums equal (sum - k).
```

**Template 模板**

```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    freq.put(0, 1);    // sentinel: empty subarray has prefix sum 0

    int sum = 0, count = 0;
    for (int num : nums) {
        sum += num;
        count += freq.getOrDefault(sum - k, 0);  // look up history
        freq.put(sum, freq.getOrDefault(sum, 0) + 1);
        //freq.merge(sum, 1, Integer::sum);          // record current prefix
    }
    return count;
}
```

**Why `freq.put(0, 1)` 为什么初始化 `{0: 1}`**

Handles subarrays starting from index 0. Without it, `[3]` with k=3 wouldn't be found because `sum - k = 0` has no entry.
处理从下标 0 开始的子数组。没有这个初始化，形如 `[3]`, k=3 的情况会漏掉。

**Order matters 顺序很重要**

```java
// CORRECT: look up BEFORE inserting current prefix
count += freq.getOrDefault(sum - k, 0);
freq.merge(sum, 1, Integer::sum);

// WRONG: inserting first would let current prefix match itself
```

**Variants 变形**

| Variant | HashMap stores | Goal | Example |
|---|---|---|---|
| Count subarrays with sum = k | `prefix → count` | count | LC 560 |
| Longest subarray with sum = k | `prefix → earliest index` | max length | LC 325 |
| Longest subarray with equal 0s and 1s | (remap: 0→-1) `prefix → earliest index` | max length | LC 525 |

**Example: Longest Subarray Sum = k (LC 325)**

```java
public int maxSubArrayLen(int[] nums, int k) {
    Map<Integer, Integer> firstSeen = new HashMap<>();
    firstSeen.put(0, -1);  // prefix 0 seen at index -1 (before array)

    int sum = 0, max = 0;
    for (int i = 0; i < nums.length; i++) {
        sum += nums[i];
        if (firstSeen.containsKey(sum - k)) {
            max = Math.max(max, i - firstSeen.get(sum - k));
        }
        firstSeen.putIfAbsent(sum, i);  // only store FIRST occurrence (maximizes length)
    }
    return max;
}
```

**Count vs Length — which map value? 统计数量 vs 最大长度**

| Goal | Store in map | Why |
|---|---|---|
| Count subarrays | `prefix → frequency` | multiple occurrences all contribute |
| Max length subarray | `prefix → earliest index` | earliest index maximizes the length |

---

### Pattern 3 — Prefix Sum + Mod 前缀和 + 取模

**When:** subarrays whose sum is **divisible by k**.
**适用：** 子数组和能被 k 整除。

**Core insight 核心推导**

```
(prefix[j] - prefix[i]) % k = 0
→ prefix[j] % k == prefix[i] % k

Two prefix sums with the same remainder → the subarray between them is divisible by k.
两个前缀和余数相同 → 中间这段子数组的和能被 k 整除。
```

**Template 模板**

```java
public int subarraysDivByK(int[] nums, int k) {
    Map<Integer, Integer> remainderCount = new HashMap<>();
    remainderCount.put(0, 1);

    int sum = 0, count = 0;
    for (int num : nums) {
        sum += num;
        int rem = ((sum % k) + k) % k;  // handle negative remainder in Java
        count += remainderCount.getOrDefault(rem, 0);
        remainderCount.put(rem, remainderCount.getOrDefault(remainderCount, 0) + 1);
        // remainderCount.merge(rem, 1, Integer::sum);
    }
    return count;
}
```

**Negative mod fix 负数取模修正**

Java's `%` can return negative values for negative operands. Use `((sum % k) + k) % k` to always get a non-negative remainder.
Java 的 `%` 对负数会返回负余数，用 `((sum % k) + k) % k` 保证结果非负。

**Variants 变形**

| Variant | Example |
|---|---|
| Subarrays divisible by k | LC 974 |
| Continuous subarray sum (multiple of k, length ≥ 2) | LC 523 |

---

### Pattern 4 — 2D Prefix Sum 二维前缀和

**When:** multiple range sum queries on a 2D matrix.
**适用：** 矩阵区域和的多次查询。

**Build 构建**

```java
int[][] prefix = new int[m + 1][n + 1];

for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        prefix[i][j] = matrix[i-1][j-1]
                     + prefix[i-1][j]
                     + prefix[i][j-1]
                     - prefix[i-1][j-1];  // subtract overlap
    }
}
```

**Query region (r1,c1) to (r2,c2) 查询区域**

```java
int sum = prefix[r2+1][c2+1]
        - prefix[r1][c2+1]
        - prefix[r2+1][c1]
        + prefix[r1][c1];   // add back doubly-subtracted corner
```

**Inclusion-exclusion 容斥原理**

The formula follows inclusion-exclusion: add full rectangle, subtract two over-subtracted edges, add back the doubly-subtracted corner.
公式来自容斥原理：加大矩形，减去两条超减的边，补回被减两次的角。

**Variants 变形**

| Variant | Example |
|---|---|
| Range sum query 2D immutable | LC 304 |
| Number of submatrices summing to target | LC 1074 |

---

### Pattern 5 — Prefix Sum + Binary Search 前缀和 + 二分

**When:** prefix array is strictly increasing (all positive numbers) — binary search on it for range queries.
**适用：** 前缀数组单调递增（全正数）时，对其二分查找。

**Example: Shortest Subarray with Sum ≥ k (LC 862)**

For non-negative arrays: use sliding window. For arrays with negatives: prefix sum + monotonic deque (advanced).

**Simpler case (all positive): binary search on prefix sum**

```java
// prefix is strictly increasing when all nums > 0
// Find smallest len such that prefix[i+len] - prefix[i] >= k
// → binary search for prefix[i] + k in prefix[i+1..n]
```

---

### Pattern 6 — Difference Array 差分数组

**When:** multiple range increment/decrement updates, then query the final array.
**适用：** 对区间做多次加减更新，最后还原数组。

**Core relationship 核心关系**

```
Difference array D is the "inverse" of prefix sum:
差分数组 D 是前缀和的"逆操作"：

D[i] = nums[i] - nums[i-1]
nums[i] = D[0] + D[1] + ... + D[i]  (prefix sum of D)
```

**Template 模板**

```java
// Range update: add val to nums[l..r] in O(1)
int[] diff = new int[n + 1];  // extra slot avoids boundary check
diff[l]     += val;
diff[r + 1] -= val;

// Reconstruct original array: O(n)
int[] result = new int[n];
int running = 0;
for (int i = 0; i < n; i++) {
    running += diff[i];
    result[i] = running;
}
```

**Key insight 核心原理**

`diff[l] += val` marks where the increment starts. `diff[r+1] -= val` marks where it ends. The prefix sum of `diff` gives the final value at each position.
`diff[l] += val` 标记增量开始，`diff[r+1] -= val` 标记结束，前缀和还原每个位置的最终值。

**Variants 变形**

| Variant | Example |
|---|---|
| Corporate flight bookings (range seat add) | LC 1109 |
| Car pooling (range passenger add/remove) | LC 1094 |
| Count overlapping intervals at each point | meeting rooms II |
| Minimum number of arrows (related) | custom |

**Example: Car Pooling (LC 1094)**

```java
public boolean carPooling(int[][] trips, int capacity) {
    int[] diff = new int[1001];
    for (int[] trip : trips) {
        diff[trip[1]] += trip[0];   // passengers board at trip[1]
        diff[trip[2]] -= trip[0];   // passengers leave at trip[2]
    }
    int passengers = 0;
    for (int d : diff) {
        passengers += d;
        if (passengers > capacity) { return false; }
    }
    return true;
}
```

---

## 4. Advanced Skills 进阶技能

### Skill 1 — Transform Range → Prefix Difference 区间问题 → 前缀差

Any question of the form "sum of nums[l..r]" immediately becomes `prefix[r+1] - prefix[l]`. Recognize this transformation first before doing anything else.
任何"求 nums[l..r] 的和"问题，立即转化为 `prefix[r+1] - prefix[l]`。先做这个转化，再想其他。

### Skill 2 — When to Store Count vs Earliest Index 存次数 vs 存最早下标

| Goal | `freq.put(prefix, ...)` | Why |
|---|---|---|
| Count subarrays | store **count** (increment) | every past occurrence forms a valid subarray |
| Longest subarray | store **earliest index** (`putIfAbsent`) | first occurrence maximizes length |

### Skill 3 — The `map.put(0, 1)` Sentinel 哨兵初始化

Always initialize with `{0: 1}` (count mode) or `{0: -1}` (index mode). This handles subarrays that start at index 0 without a special case.
始终初始化 `{0: 1}` (计数模式) 或 `{0: -1}` (下标模式)，处理从下标 0 开始的子数组，无需特判。

### Skill 4 — Negative Numbers Block Sliding Window 负数阻断滑动窗口

Sliding window requires: sum increases as window grows. With negatives: `sum` is non-monotonic → window might shrink when it should grow, or vice versa. **Always switch to prefix sum + HashMap when negatives are possible.**

### Skill 5 — Difference Array = Prefix Sum Inverse 差分数组 = 前缀和的逆

- Prefix sum: build from left, enables O(1) query
- Difference array: update in O(1) (mark endpoints), rebuild with prefix sum in O(n)

They are inverses: use prefix sum when you **read** range sums frequently; use difference array when you **write** range updates frequently.

---

## 5. Interview Script 面试话术

**English:**
> I'd use prefix sum because the array may contain negative numbers, so sliding window can't guarantee monotonicity. For each position j with current prefix sum `sum`, I look up how many previous prefix sums equal `sum - k` using a HashMap. Each such past prefix marks the start of a valid subarray ending at j. I initialize the map with `{0: 1}` to handle subarrays starting at index 0.

**中文：**
> 我会用前缀和，因为数组可能有负数，滑动窗口无法保证单调性。遍历到当前位置 j 时，前缀和为 sum，从 HashMap 中查找历史有多少个前缀和等于 sum - k。每一个这样的历史前缀和，都对应一个以 j 结尾、和为 k 的子数组。初始化 {0: 1} 是为了处理从下标 0 开始的子数组。

---

## 6. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Basic prefix sum | 303, 238 |
| 2. Prefix + HashMap (count) | 560, 523 |
| 2. Prefix + HashMap (max length) | 325, 525 |
| 3. Prefix + Mod | 974, 523 |
| 4. 2D prefix sum | 304, 1074 |
| 5. Prefix + binary search | 862 |
| 6. Difference array | 1109, 1094 |

**Recommended order 建议练习顺序:**
303 → 560 → 325 → 525 → 974 → 304 → 1109 → 1094

---

## 7. One-line Summary 一句话总结

```
Prefix sum = transform range queries into O(1) lookups using precomputed cumulative sums;
             combined with a HashMap, it efficiently counts or locates subarrays by sum.

前缀和 = 把区间查询转化为前缀差的 O(1) 查找；
         配合 HashMap 高效统计或定位满足和条件的子数组。
```

**Next steps 下一步:**
- Difference Array (T2-9) — covered here in Pattern 6, range update complement 差分数组（区间更新的配套工具）
- Binary Indexed Tree / Segment Tree — when both range update AND range query are needed, dynamic 树状数组/线段树（动态区间查询更新）
