# T1-4 — Binary Search 二分查找

> **Core idea:** On any monotone search space, eliminate half the candidates each step.
> **核心思想：** 在具有单调性的搜索空间中，每步排除一半候选。
>
> Golden rule: `mid = lo + (hi - lo) / 2` — never `(lo + hi) / 2` (integer overflow).
> Complexity: O(log n) per query.
> Full reference: `BinarySearch/description.md`

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Sorted array + find target | LC 704 |
| First / last occurrence | LC 34 |
| Minimize/maximize value under constraint | LC 875, 1011, 410 |
| Rotated sorted array | LC 33, 153 |
| Answer lies in a numeric range | "minimum capacity", "minimum speed" |

**Binary search on answer:** whenever you can write `f(x)` = "can we achieve result x?" and `f` is monotone (false…false→true…true), binary search for the boundary.

---

## Core Templates 核心模板

### Exact match (find target) 精确查找

```java
int lo = 0, hi = nums.length - 1;           // closed [lo, hi]
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    if      (nums[mid] == target) return mid;
    else if (nums[mid] < target)  lo = mid + 1;
    else                          hi = mid - 1;
}
return -1;
```

### Left boundary (first position ≥ target) 左边界

```java
int lo = 0, hi = nums.length;               // half-open [lo, hi)
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (nums[mid] < target) lo = mid + 1;   // mid too small
    else                    hi = mid;        // mid might be answer
}
return lo;   // first index where nums[lo] >= target  (lo == n if all < target)
```

### Right boundary (last position ≤ target) 右边界

```java
int lo = 0, hi = nums.length;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (nums[mid] <= target) lo = mid + 1;
    else                     hi = mid;
}
return lo - 1;   // last index where nums[i] <= target
```

### Search on answer (minimize) 答案二分（最小化）

```java
int lo = minPossible, hi = maxPossible;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (canAchieve(mid)) hi = mid;      // works, try smaller
    else                 lo = mid + 1;  // too small
}
return lo;
```

---

## Variants 变形

| Pattern | Condition to move `lo` | Example |
|---|---|---|
| Exact match | `nums[mid] < target` → lo++; `> target` → hi-- | LC 704 |
| First occurrence | `nums[mid] < target` → lo = mid+1 | LC 34 left |
| First bad version | `!isBad(mid)` → lo = mid+1 | LC 278 |
| Min eating speed | `!canFinish(mid)` → lo = mid+1 | LC 875 |
| Min capacity | `!canShip(mid)` → lo = mid+1 | LC 1011 |
| Rotated array | check which half is sorted, decide direction | LC 33 |
| 2D matrix | flatten to 1D: `matrix[mid/n][mid%n]` | LC 74 |

---

## Key Examples 关键例题

### First & Last Position (LC 34)
```java
public int[] searchRange(int[] nums, int target) {
    int first = leftBound(nums, target);
    if (first == nums.length || nums[first] != target) return new int[]{-1, -1};
    int last  = leftBound(nums, target + 1) - 1;
    return new int[]{first, last};
}
private int leftBound(int[] nums, int target) {
    int lo = 0, hi = nums.length;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] < target) lo = mid + 1;
        else                    hi = mid;
    }
    return lo;
}
```

### Koko Eating Bananas (LC 875) — Search on Answer
```java
public int minEatingSpeed(int[] piles, int h) {
    int lo = 1, hi = Arrays.stream(piles).max().getAsInt();
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        int hours = 0;
        for (int p : piles) hours += (p + mid - 1) / mid;
        if (hours <= h) hi = mid;
        else            lo = mid + 1;
    }
    return lo;
}
```

### Search in Rotated Array (LC 33)
```java
public int search(int[] nums, int target) {
    int lo = 0, hi = nums.length - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] == target) return mid;
        if (nums[lo] <= nums[mid]) {                       // left half sorted
            if (nums[lo] <= target && target < nums[mid]) hi = mid - 1;
            else                                          lo = mid + 1;
        } else {                                            // right half sorted
            if (nums[mid] < target && target <= nums[hi]) lo = mid + 1;
            else                                          hi = mid - 1;
        }
    }
    return -1;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Overflow prevention | `mid = lo + (hi - lo) / 2` always |
| `lo <= hi` vs `lo < hi` | Closed `[lo,hi]` uses `<=`; half-open `[lo,hi)` uses `<` |
| Predicate direction | Decide if you minimize "first true" or maximize "last true" before coding |
| Ceiling division | `(a + b - 1) / b` = `ceil(a / b)` without floats |
| Left vs right boundary | Left: `hi = mid`; Right: `lo = mid + 1`, return `lo - 1` |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 704 Binary Search, LC 278 First Bad Version |
| Medium | LC 34 Find First and Last Position |
| Medium | LC 875 Koko Eating Bananas, LC 1011 Ship Packages |
| Medium | LC 33 Search in Rotated Sorted Array, LC 153 Find Minimum |
| Hard | LC 410 Split Array Largest Sum, LC 4 Median of Two Sorted Arrays |

**Order:** 704 → 34 → 278 → 875 → 33 → 153 → 1011 → 410

---

## One-line Summary

```
Binary search = eliminate half the search space each step using monotonicity; always use lo + (hi-lo)/2.
二分查找 = 利用单调性每步排除一半；始终用 lo + (hi-lo)/2 避免溢出。
```
