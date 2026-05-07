# T4-5 — Dutch National Flag (3-Way Partition) 荷兰国旗问题（三路分区）

> **Core idea:** Three pointers `lo`, `mid`, `hi` partition an array into three regions in one pass: [0..lo-1] < pivot, [lo..mid-1] = pivot, [mid..hi] unseen, [hi+1..n-1] > pivot.
> **核心思想：** 三个指针`lo`、`mid`、`hi`一次遍历将数组分成三个区域：小于枢轴、等于枢轴、大于枢轴。
>
> Complexity: O(n) time, O(1) space — single pass.
> Full reference: `TwoPointer/2PointersPattern.md` Pattern 4 (Partition)

---

## How It Works — Mental Model 直觉理解

The algorithm partitions the array into four regions that together always cover the entire array. `lo` is the boundary where 0s end; `mid` is the boundary of the unknown region; `hi` is the boundary where 2s begin. At any point `mid` points into the unknown zone — the only region that changes. When `mid` sees a 0, it swaps it to the front (extending the 0-region), and since the element that came from `lo` was already processed (it was a 1 sitting right behind `lo`), both pointers advance. When `mid` sees a 2, it swaps it to the back (extending the 2-region), but the element that arrives from `hi` is unknown, so `mid` must NOT advance — it needs to examine that new element next. When `mid` sees a 1, it is already in the right place so just advance `mid`. The loop ends when `mid` crosses `hi`, meaning the unknown region is empty.

**Key invariant:** At every iteration: `nums[0..lo-1]` are all 0, `nums[lo..mid-1]` are all 1, `nums[hi+1..n-1]` are all 2, and `nums[mid..hi]` is the unknown zone.

**Common mistake:** Advancing `mid` after a swap with `hi`. The element that arrived from position `hi` is unknown — you haven't inspected it yet. Advancing `mid` would skip its inspection and potentially place a 0 or 2 inside the "equals" region.

---

## Step-by-Step Trace

```
Input: [2, 0, 2, 1, 1, 0]   lo=0, mid=0, hi=5

mid=0: nums[0]=2 → swap(mid=0,hi=5): [0,0,2,1,1,2], hi=4
mid=0: nums[0]=0 → swap(lo=0,mid=0): [0,0,2,1,1,2], lo=1, mid=1
mid=1: nums[1]=0 → swap(lo=1,mid=1): [0,0,2,1,1,2], lo=2, mid=2
mid=2: nums[2]=2 → swap(mid=2,hi=4): [0,0,1,1,2,2], hi=3
mid=2: nums[2]=1 → mid=3
mid=3: nums[3]=1 → mid=4
mid=4 > hi=3 → done: [0,0,1,1,2,2]  ✓
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Sort array of 0s, 1s, 2s | LC 75 |
| 3-way partition around pivot (QuickSort optimization) | QuickSort with duplicates |
| Separate elements into 3 groups by condition | — |
| Move zeros to end, nonzeros to front | LC 283 (2-way variant) |

**Signal:** exactly 3 categories to partition; duplicates are common; need single-pass O(n) solution.

---

## Core Templates 核心模板

### Classic Dutch National Flag (sort colors LC 75)

```java
public void sortColors(int[] nums) {
    int lo = 0, mid = 0, hi = nums.length - 1;

    while (mid <= hi) {
        if (nums[mid] == 0) {
            swap(nums, lo++, mid++);   // 0 → swap to front; the element from lo was a 1 (already processed), so advance mid too
        } else if (nums[mid] == 1) {
            mid++;                     // 1 is already in the correct middle region; just expand it
        } else {
            swap(nums, mid, hi--);     // 2 → swap to back; the element arriving from hi is unknown, do NOT advance mid
        }
    }
}

void swap(int[] nums, int a, int b) {
    int tmp = nums[a]; nums[a] = nums[b]; nums[b] = tmp;
}
```

### Generic 3-way partition around pivot

```java
// Partition nums around pivot value p: [<p] [=p] [>p]
void threeWayPartition(int[] nums, int p) {
    int lo = 0, mid = 0, hi = nums.length - 1;
    while (mid <= hi) {
        if      (nums[mid] < p) { swap(nums, lo++, mid++); }
        else if (nums[mid] == p) { mid++; }
        else                     { swap(nums, mid, hi--); }
    }
    // After: nums[0..lo-1] < p, nums[lo..mid-1] == p, nums[mid..n-1] > p
}
```

### 2-way variant: Move zeros (LC 283)

```java
public void moveZeroes(int[] nums) {
    int insertPos = 0;
    for (int num : nums) {
        if (num != 0) { nums[insertPos++] = num; }
    }
    while (insertPos < nums.length) { nums[insertPos++] = 0; }
}
```

---

## Invariant 不变量

```
At any point during the loop:
  nums[0..lo-1]  = 0  (sorted, less)
  nums[lo..mid-1] = 1  (sorted, equal)
  nums[mid..hi]   = unknown (to be processed)
  nums[hi+1..n-1] = 2  (sorted, greater)
```

When `mid` encounters a 2 and swaps with `hi`, the new `nums[mid]` is unknown — do NOT advance `mid`.

---

## Variants 变形

| Variant | Regions | Example |
|---|---|---|
| Sort 0s/1s/2s | 3 regions, 3 pointers | LC 75 |
| Move zeros to end | 2 regions (nonzero / zero) | LC 283 |
| Segregate negative/positive | 2 regions | custom |
| QuickSort 3-way | Partition then recurse on < and > regions | optimized QuickSort |
| Rainbow sort (k colors) | Counting sort O(n+k) usually better | — |

---

## Key Examples 关键例题

### Sort Colors (LC 75)
```java
public void sortColors(int[] nums) {
    int lo = 0, mid = 0, hi = nums.length - 1;
    while (mid <= hi) {
        if      (nums[mid] == 0) { swap(nums, lo++, mid++); }
        else if (nums[mid] == 1) { mid++; }
        else                     { swap(nums, mid, hi--); }
    }
}
void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
```

### Wiggle Sort II (LC 324) — uses 3-way partition
```java
// Find median with QuickSelect, then 3-way partition around median,
// then interleave: place larger in odd positions, smaller in even positions
// (Complex — see full solution in LC 324)
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Don't advance `mid` after swap with `hi` | The incoming element from `hi` is unknown |
| Advance BOTH `lo` and `mid` after swap with `lo` | `nums[lo]` was 1 (already processed), so `mid` can advance too |
| Loop condition: `mid <= hi` | `mid` crossing `hi` means all elements processed |
| 2-way is simpler | If only 2 groups needed (zero/nonzero), use single pointer |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 75 Sort Colors |
| Medium | LC 283 Move Zeroes |
| Hard | LC 324 Wiggle Sort II |

**Order:** 283 → 75 → 324

---

## One-line Summary

```
Dutch National Flag = lo/mid/hi three pointers, one pass; swap 0→front (advance both), skip 1 (advance mid), swap 2→back (advance hi only).
荷兰国旗 = lo/mid/hi三指针一次遍历；0换到前(两者前进), 1跳过(mid前进), 2换到后(只hi后退)。
```
