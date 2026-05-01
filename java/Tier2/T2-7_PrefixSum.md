# T2-7 — Prefix Sum 前缀和

> **Core idea:** Precompute cumulative sums so any range sum `[l, r]` is answered in O(1) as `prefix[r+1] - prefix[l]`. Combine with HashMap to count subarrays with a target sum.
> **核心思想：** 预计算前缀和，任何区间和`[l,r]`用`prefix[r+1]-prefix[l]`在O(1)内回答。结合HashMap可统计目标和子数组数量。
>
> Complexity: O(n) build, O(1) query. HashMap variant: O(n) time and space.
> Full reference: `PrefixSum/desciption.md`, `Prefix_Sum_HashMap/description.md`

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Range sum query (immutable array) | LC 303 |
| 2D range sum query | LC 304 |
| Subarray sum equals k (count) | LC 560 |
| Longest subarray with sum = k | LC 325 |
| Subarray sum divisible by k | LC 974 |
| Equal number of 0s and 1s | LC 525 |

**Signal:** "subarray", "range", "sum equals", "count subarrays" without sliding window (negatives present).

---

## Core Templates 核心模板

### Build prefix array

```java
int[] prefix = new int[n + 1];      // prefix[0] = 0 (sentinel)
for (int i = 0; i < n; i++)
    prefix[i + 1] = prefix[i] + nums[i];

int rangeSum = prefix[r + 1] - prefix[l];   // sum of nums[l..r]
```

### Count subarrays with sum = k (HashMap)

```java
Map<Integer, Integer> freq = new HashMap<>();
freq.put(0, 1);          // empty prefix has sum 0
int sum = 0, count = 0;

for (int num : nums) {
    sum += num;
    count += freq.getOrDefault(sum - k, 0);   // look for complement
    freq.merge(sum, 1, Integer::sum);
}
return count;
```

### Longest subarray with sum = k (putIfAbsent)

```java
Map<Integer, Integer> firstSeen = new HashMap<>();
firstSeen.put(0, -1);    // sum 0 seen at index -1
int sum = 0, maxLen = 0;

for (int i = 0; i < nums.length; i++) {
    sum += nums[i];
    if (firstSeen.containsKey(sum - k))
        maxLen = Math.max(maxLen, i - firstSeen.get(sum - k));
    firstSeen.putIfAbsent(sum, i);   // keep FIRST occurrence for longest
}
return maxLen;
```

### 2D prefix sum

```java
int[][] prefix = new int[m+1][n+1];
for (int i = 1; i <= m; i++)
    for (int j = 1; j <= n; j++)
        prefix[i][j] = matrix[i-1][j-1]
            + prefix[i-1][j] + prefix[i][j-1] - prefix[i-1][j-1];

// Sum of region (r1,c1) to (r2,c2):
int sum = prefix[r2+1][c2+1] - prefix[r1][c2+1]
        - prefix[r2+1][c1] + prefix[r1][c1];
```

---

## Variants 变形

| Variant | Key change | Example |
|---|---|---|
| Range sum (static) | `prefix[r+1] - prefix[l]` | LC 303 |
| 2D range sum | Inclusion-exclusion on 4 corners | LC 304 |
| Count subarrays sum=k | HashMap, lookup `sum-k`, insert after | LC 560 |
| Longest subarray sum=k | `putIfAbsent` for first index | LC 325 |
| Divisible by k | Store `((sum%k)+k)%k` as key | LC 974 |
| Equal 0s and 1s | Remap 0→-1; find longest subarray sum=0 | LC 525 |
| Prefix XOR | Replace `+` with `^`; find subarray XOR=k | LC 1442 |

---

## Key Examples 关键例题

### Subarray Sum Equals K (LC 560)
```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    freq.put(0, 1);
    int sum = 0, count = 0;
    for (int num : nums) {
        sum += num;
        count += freq.getOrDefault(sum - k, 0);
        freq.merge(sum, 1, Integer::sum);
    }
    return count;
}
```

### Contiguous Array (LC 525) — equal 0s and 1s
```java
public int findMaxLength(int[] nums) {
    Map<Integer, Integer> firstSeen = new HashMap<>();
    firstSeen.put(0, -1);
    int sum = 0, maxLen = 0;
    for (int i = 0; i < nums.length; i++) {
        sum += nums[i] == 0 ? -1 : 1;   // remap 0→-1
        if (firstSeen.containsKey(sum))
            maxLen = Math.max(maxLen, i - firstSeen.get(sum));
        else
            firstSeen.put(sum, i);
    }
    return maxLen;
}
```

### Subarray Sums Divisible by K (LC 974)
```java
public int subarraysDivByK(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    freq.put(0, 1);
    int sum = 0, count = 0;
    for (int num : nums) {
        sum += num;
        int rem = ((sum % k) + k) % k;    // normalize negative remainder
        count += freq.getOrDefault(rem, 0);
        freq.merge(rem, 1, Integer::sum);
    }
    return count;
}
```

### Range Sum Query 2D (LC 304)
```java
class NumMatrix {
    private int[][] prefix;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        prefix = new int[m+1][n+1];
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                prefix[i][j] = matrix[i-1][j-1]
                    + prefix[i-1][j] + prefix[i][j-1] - prefix[i-1][j-1];
    }

    public int sumRegion(int r1, int c1, int r2, int c2) {
        return prefix[r2+1][c2+1] - prefix[r1][c2+1]
             - prefix[r2+1][c1]   + prefix[r1][c1];
    }
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `prefix[0] = 0` sentinel | Enables sum of `[0..r]` without special case |
| `freq.put(0, 1)` sentinel | Counts subarrays starting from index 0 |
| Lookup BEFORE insert | For count: check `sum-k` then add `sum`. Inserting first would count current element itself |
| `putIfAbsent` for longest | Keep the FIRST occurrence to maximize length |
| Negative modulo: `((sum%k)+k)%k` | Java `%` returns negative for negative operands |
| Remap 0→-1 for binary arrays | Equal 0s and 1s → sum = 0 subarray problem |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 303 Range Sum Query Immutable |
| Medium | LC 560 Subarray Sum Equals K |
| Medium | LC 525 Contiguous Array |
| Medium | LC 974 Subarray Sums Divisible by K |
| Medium | LC 325 Maximum Size Subarray Sum Equals k |
| Medium | LC 304 Range Sum Query 2D |
| Hard | LC 1074 Number of Submatrices That Sum to Target |

**Order:** 303 → 560 → 525 → 974 → 325 → 304 → 1074

---

## One-line Summary

```
Prefix sum = precompute cumulative sums for O(1) range queries; +HashMap transforms "count subarrays" into complement lookups.
前缀和 = 预计算累积和实现O(1)区间查询；+HashMap将"统计子数组"转化为补数查找。
```
