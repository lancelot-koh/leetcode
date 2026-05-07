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

## How it Works — Mental Model 原理与直觉

Binary search works by maintaining a search interval that is guaranteed to contain the answer. At each step, we check the midpoint. Because the search space is monotone, the result of the midpoint check tells us which *half* to discard entirely — we never need to look at discarded elements again. With n elements, we need at most log₂(n) halvings before the interval collapses to a single element.

The hardest part is not the algorithm — it's boundary conditions. The choice of `[lo, hi]` (closed) vs `[lo, hi)` (half-open) determines whether the loop uses `lo <= hi` or `lo < hi`, and whether `hi` is set to `mid` or `mid - 1`. Pick one convention and never mix them within a template.

**Invariant (exact match, closed interval):** The target, if it exists, always lies within `[lo, hi]`. When `lo > hi`, the interval is empty and the target is absent.

**Invariant (left boundary, half-open):** `lo` is the smallest index not yet ruled out. When the loop ends, `lo == hi` is the first index where `nums[lo] >= target`.

---

## Step-by-Step Trace — Left Boundary 执行追踪

```
Input: nums=[1,3,5,5,7], target=5   (find first occurrence)
lo=0, hi=5 (half-open)
Step 1: mid=2, nums[2]=5 >= 5 → hi=2  (mid might be the answer)
Step 2: mid=1, nums[1]=3 <  5 → lo=2  (mid too small)
Step 3: lo==hi==2 → exit, return 2  ✓ (first 5 is at index 2)
```

---

## Core Templates 核心模板

### Exact match (find target) 精确查找

```java
int lo = 0, hi = nums.length - 1;           // closed [lo, hi]: both endpoints are valid candidates
while (lo <= hi) {                           // lo==hi is a valid 1-element interval; must check it
    int mid = lo + (hi - lo) / 2;           // avoids (lo+hi) integer overflow
    if      (nums[mid] == target) { return mid; }
    else if (nums[mid] < target)  { lo = mid + 1; }   // target is to the right of mid
    else                          { hi = mid - 1; }   // target is to the left of mid
}
return -1;   // interval is empty; target not found
```

### Left boundary (first position ≥ target) 左边界

```java
int lo = 0, hi = nums.length;               // half-open [lo, hi): hi=n means "past the end"
while (lo < hi) {                           // lo==hi means interval is empty → done
    int mid = lo + (hi - lo) / 2;
    if (nums[mid] < target) { lo = mid + 1; }   // mid is definitely not the answer; cut left half
    else                    { hi = mid; }        // mid could be the answer; keep it in the interval
}
return lo;   // first index where nums[lo] >= target  (lo == n if all < target)
```

### Right boundary (last position ≤ target) 右边界

```java
int lo = 0, hi = nums.length;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (nums[mid] <= target) { lo = mid + 1; }   // mid is ≤ target, so answer is mid or further right
    else                     { hi = mid; }        // mid is > target, shrink right
}
return lo - 1;   // last index where nums[i] <= target; lo-1 because lo overshot by one
```

### Search on answer (minimize) 答案二分（最小化）

```java
int lo = minPossible, hi = maxPossible;     // set bounds to the full range of possible answers
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (canAchieve(mid)) { hi = mid; }      // mid works → it's a candidate; try something smaller
    else                 { lo = mid + 1; }  // mid too small → answer must be strictly larger
}
return lo;   // smallest value for which canAchieve returns true
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
        if (nums[mid] < target) { lo = mid + 1; }
        else                    { hi = mid; }
    }
    return lo;
}
```

### Koko Eating Bananas (LC 875) — Search on Answer
```java
public int minEatingSpeed(int[] piles, int h) {
    // int lo = 1, hi = Arrays.stream(piles).max().getAsInt();
    int lo = 1, hi = 0;
    for(int pile: piles) {
        if (pile > hi) {
            hi = pile;
        }
    }
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        int hours = 0;
        for (int p : piles) { hours += (p + mid - 1) / mid; }
        if (hours <= h) { hi = mid; }
        else            { lo = mid + 1; }
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
        if (nums[mid] == target) { return mid; }
        if (nums[lo] <= nums[mid]) {                       // left half sorted
            if (nums[lo] <= target && target < nums[mid]) { hi = mid - 1; }
            else                                          { lo = mid + 1; }
        } else {                                            // right half sorted
            if (nums[mid] < target && target <= nums[hi]) { lo = mid + 1; }
            else                                          { hi = mid - 1; }
        }
    }
    return -1;
}
```

---

## Common Mistake / Gotcha 常见错误

**Infinite loop with `hi = mid` in a closed interval:** If you use `lo <= hi` but set `hi = mid` (instead of `mid - 1`), and `lo == mid`, the loop never shrinks and runs forever. The half-open template sets `hi = mid` precisely because the loop condition `lo < hi` guarantees `lo != hi`, so the interval always shrinks.

**Wrong `canAchieve` search bounds:** For "search on answer" problems, the lower bound is often 1 (not 0) and the upper bound is the maximum individual element (not the array size). Using `hi = n` when the answer is actually `max(piles)` means the search never reaches the true answer.

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Overflow prevention | `mid = lo + (hi - lo) / 2` always |
| `lo <= hi` vs `lo < hi` | Closed `[lo,hi]` uses `<=`; half-open `[lo,hi)` uses `<` — never mix them |
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
