# Binary Search 二分查找

> **Core idea:** On any search space with monotonicity, eliminate half the candidates each step.
> **核心思想：** 在具有单调性的搜索空间中，每步排除一半候选。
>
> Key rule: `mid = lo + (hi - lo) / 2` — never `(lo + hi) / 2` (overflow).
> Complexity: O(log n) per query.
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| sorted array / sorted range | 有序数组 |
| find target / find position | 查找目标 |
| first / last occurrence | 第一个 / 最后一个 |
| minimum / maximum satisfying condition | 满足条件的最小/最大值 |
| search in rotated array | 旋转有序数组 |
| square root / power | 开方 / 幂 |
| capacity / speed / threshold | 容量 / 速度 / 阈值类 |

**When NOT to use 不适用**

- Input is unsorted and sorting is too expensive → Hash or linear scan
- Need exact all matches → scan
- Search space has no monotonicity

**Binary search on answer 答案二分**

The most powerful application: when you can define a predicate `f(x)` that is monotone (false…false→true…true), binary search for the boundary.
最强用法：定义一个单调谓词 `f(x)`（false…false→true…true），对边界做二分。

---

## 2. Quick Decision Guide 快速判断

```
Find exact target in sorted array?            → Pattern 1: Classic binary search
Find leftmost valid position / first true?    → Pattern 2: Left boundary
Find rightmost valid position / last true?    → Pattern 3: Right boundary
Minimize/maximize a value under constraint?   → Pattern 4: Search on answer
Array was rotated?                            → Pattern 5: Rotated sorted array
Search in 2D sorted matrix?                   → Pattern 6: 2D binary search
```

---

## 3. Patterns 模式

---

### Pattern 1 — Classic Binary Search 经典二分

**When:** find exact target; return -1 if not found.
**适用：** 查找精确目标，不存在返回 -1。

**Template 模板**

```java
int lo = 0, hi = nums.length - 1;

while (lo <= hi) {                          // <= because lo==hi is still valid
    int mid = lo + (hi - lo) / 2;
    if      (nums[mid] == target) { return mid; }
    else if (nums[mid] < target) { lo = mid + 1; }
    else { hi = mid - 1; }
}
return -1;
```

**Key detail:** `lo <= hi` because when `lo == hi`, that single element still needs to be checked.
当 `lo == hi` 时，那个元素仍需检查，所以用 `<=`。

---

### Pattern 2 — Left Boundary 左边界（第一个满足条件的）

**When:** find the **first** position where `nums[i] >= target`, or first index where predicate is true.
**适用：** 找第一个满足条件的位置（leftmost true）。

**Template 模板**

```java
int lo = 0, hi = nums.length;              // hi = n (one past end)

while (lo < hi) {                          // < because [lo, hi) half-open
    int mid = lo + (hi - lo) / 2;
    if (nums[mid] < target) { lo = mid + 1; }  // mid is too small, exclude it
    else { hi = mid; }       // mid might be the answer, keep it
}
return lo;  // lo == hi, first index >= target (or n if all < target)
```

**Invariant:** everything left of `lo` is definitely `< target`; everything from `hi` onward is `>= target`.
`lo` 左边全部 `< target`，`hi` 及右边全部 `>= target`。

**Variants 变形**

| Variant | Condition to move `lo` | Example |
|---|---|---|
| First occurrence of target | `nums[mid] < target` | LC 34 left |
| First position ≥ target | `nums[mid] < target` | `Arrays.binarySearch` |
| First bad version | `!isBad(mid)` → `lo = mid + 1` | LC 278 |
| Minimum in rotated array | `nums[mid] > nums[hi]` → `lo = mid + 1` | LC 153 |

---

### Pattern 3 — Right Boundary 右边界（最后一个满足条件的）

**When:** find the **last** position where `nums[i] <= target`.
**适用：** 找最后一个满足条件的位置（rightmost true）。

**Template 模板**

```java
int lo = 0, hi = nums.length;

while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (nums[mid] <= target) { lo = mid + 1; }  // mid is valid, but maybe not the last
    else { hi = mid; }
}
return lo - 1;  // last position where nums[i] <= target
```

**Finding both boundaries (LC 34)**

```java
// [first, last] occurrence of target
int first = leftBound(nums, target);
if (first == nums.length || nums[first] != target) return new int[]{-1, -1};
int last = leftBound(nums, target + 1) - 1;
return new int[]{first, last};
```

---

### Pattern 4 — Search on Answer 答案二分（搜索结果空间）

**When:** the answer is a value in a range, and you can write a predicate `canAchieve(x)` that is monotone.
**适用：** 答案是某个值域中的数，能写出单调谓词 `canAchieve(x)`。

**Template 模板**

```java
int lo = minPossible, hi = maxPossible;

while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (canAchieve(mid)) { hi = mid; }      // mid works, try smaller (minimize)
    else { lo = mid + 1; }  // mid too small, go larger
}
return lo;
```

**For maximize:** flip to `if (canAchieve(mid)) lo = mid + 1; else hi = mid;` then return `hi - 1`.

**Key insight 核心原理**

You're binary searching the **result space**, not the input array. `canAchieve(mid)` must be O(n) or faster so total is O(n log n).
在**结果空间**上二分，不是在输入数组上。谓词必须 O(n)，总体 O(n log n)。

**Variants 变形**

| Variant | Predicate | Example |
|---|---|---|
| Koko eating bananas (min speed) | can finish in H hours? | LC 875 |
| Ship packages in D days (min capacity) | can ship all? | LC 1011 |
| Split array largest sum (minimize max) | can split into k parts? | LC 410 |
| Magnetic force between balls (maximize min gap) | can place m balls? | LC 1552 |

**Example: Koko Eating Bananas (LC 875)**

```java
public int minEatingSpeed(int[] piles, int h) {
    int lo = 1, 
    
    //hi = Arrays.stream(piles).max().getAsInt();
    int hi = 0;
    for(int pile: piles) {
        if (pile > hi) {
            hi = pile;
        }
    }
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (canFinish(piles, mid, h)) { hi = mid; }
        else { lo = mid + 1; }
    }
    return lo;
}

private boolean canFinish(int[] piles, int speed, int h) {
    int hours = 0;
    for (int p : piles) { hours += (p + speed - 1) / speed; }
    return hours <= h;
}
```

---

### Pattern 5 — Rotated Sorted Array 旋转有序数组

**When:** array was sorted then rotated at some pivot; find target or minimum.
**适用：** 排序后旋转过的数组，查找目标或最小值。

**Template 模板 (find target)**

```java
int lo = 0, hi = nums.length - 1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    if (nums[mid] == target) { return mid; }

    if (nums[lo] <= nums[mid]) {          // left half is sorted
        if (nums[lo] <= target && target < nums[mid]) { hi = mid - 1; }
        else { lo = mid + 1; }
    } else {                              // right half is sorted
        if (nums[mid] < target && target <= nums[hi]) { lo = mid + 1; }
        else { hi = mid - 1; }
    }
}
return -1;
```

**Key insight 核心原理**

In a rotated array, at least one half is always sorted. Check which half is sorted, then test if target falls within it.
旋转数组中，至少一半是有序的。判断哪一半有序，再判断目标是否在其中。

**Variants 变形**

| Variant | Example |
|---|---|
| Search in rotated array | LC 33 |
| Find minimum in rotated array | LC 153 |
| Search with duplicates | LC 81 |

---

### Pattern 6 — 2D Binary Search 二维二分

**When:** matrix where each row and column is sorted (or the whole matrix is sorted row by row).
**适用：** 每行每列均有序的矩阵，或整体有序的矩阵。

**Fully sorted matrix (staircase search) 全局有序矩阵**

```java
// Start from top-right corner
int r = 0, c = n - 1;
while (r < m && c >= 0) {
    if      (matrix[r][c] == target) { return true; }
    else if (matrix[r][c] > target) { c--; }   // current too big, go left
    else { r++; }   // current too small, go down
}
return false;
```

**Row-sorted, first element of each row increasing: treat as 1D**

```java
int lo = 0, hi = m * n - 1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    int val = matrix[mid / n][mid % n];
    if      (val == target) { return true; }
    else if (val < target) { lo = mid + 1; }
    else { hi = mid - 1; }
}
return false;
```

---

## 4. Advanced Skills 进阶技能

### Skill 1 — `lo + (hi - lo) / 2` Not `(lo + hi) / 2`

`(lo + hi)` can overflow when both are near `Integer.MAX_VALUE`. Always use `lo + (hi - lo) / 2`.
`(lo + hi)` 在两者都接近 `Integer.MAX_VALUE` 时会溢出。

### Skill 2 — Half-open `[lo, hi)` vs Closed `[lo, hi]`

| Style | Loop condition | Initial `hi` | Works best for |
|---|---|---|---|
| `[lo, hi]` closed | `lo <= hi` | `n - 1` | Exact match (Pattern 1) |
| `[lo, hi)` half-open | `lo < hi` | `n` | Boundary search (Patterns 2, 3, 4) |

Pick one style and stick with it per problem. Half-open is more consistent for boundary problems.

### Skill 3 — Verify the Predicate Direction 确认谓词方向

Before coding, verify: does the predicate go `false…false→true…true` or the reverse?
- Minimize the first `true` → `if (f(mid)) hi = mid; else lo = mid + 1`
- Maximize the last `true` → `if (f(mid)) lo = mid + 1; else hi = mid`

### Skill 4 — `ceiling` Division Without Float

```java
int ceildiv = (a + b - 1) / b;  // equivalent to Math.ceil((double) a / b)
```
Useful in predicates (e.g., hours to eat piles at speed k).

---

## 5. Interview Script 面试话术

**English:**
> I'd use binary search because [the array is sorted / the answer space is monotone]. I maintain a `[lo, hi)` invariant where everything left of `lo` is definitely [too small / invalid] and everything from `hi` onward [might be valid / is valid]. Since each step halves the space, the total time is O(log n).

**中文：**
> 我会用二分查找，因为[数组有序 / 答案空间有单调性]。维护 `[lo, hi)` 不变量：`lo` 左边全部[太小/不合法]，`hi` 及右边[可能合法/合法]。每步减半搜索空间，时间复杂度 O(log n)。

---

## 6. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Classic | 704 |
| 2. Left boundary | 34, 278, 35 |
| 3. Right boundary | 34 |
| 4. Search on answer | 875, 1011, 410, 1552 |
| 5. Rotated array | 33, 153, 81 |
| 6. 2D | 74, 240 |

**Recommended order:** 704 → 34 → 278 → 875 → 33 → 153 → 1011 → 410 → 74

---

## 7. One-line Summary 一句话总结

```
Binary search = eliminate half the search space each step using monotonicity.
二分查找 = 利用单调性，每步排除一半搜索空间。
```
