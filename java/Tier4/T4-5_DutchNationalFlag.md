# T4-5 — Dutch National Flag (3-Way Partition) 荷兰国旗问题（三路分区）

> **Core idea:** Three pointers `lo`, `mid`, `hi` partition an array into three regions in one pass: [0..lo-1] < pivot, [lo..mid-1] = pivot, [mid..hi] unseen, [hi+1..n-1] > pivot.
> **核心思想：** 三个指针`lo`、`mid`、`hi`一次遍历将数组分成三个区域：小于枢轴、等于枢轴、大于枢轴。
>
> Complexity: O(n) time, O(1) space — single pass.
> Full reference: `TwoPointer/2PointersPattern.md` Pattern 4 (Partition)

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
            swap(nums, lo++, mid++);   // 0: goes to lo region; both advance
        } else if (nums[mid] == 1) {
            mid++;                     // 1: already in correct region
        } else {
            swap(nums, mid, hi--);     // 2: goes to hi region; mid stays (new element to check)
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
        if      (nums[mid] < p) swap(nums, lo++, mid++);
        else if (nums[mid] == p) mid++;
        else                     swap(nums, mid, hi--);
    }
    // After: nums[0..lo-1] < p, nums[lo..mid-1] == p, nums[mid..n-1] > p
}
```

### 2-way variant: Move zeros (LC 283)

```java
public void moveZeroes(int[] nums) {
    int insertPos = 0;
    for (int num : nums)
        if (num != 0) nums[insertPos++] = num;
    while (insertPos < nums.length) nums[insertPos++] = 0;
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
        if      (nums[mid] == 0) swap(nums, lo++, mid++);
        else if (nums[mid] == 1) mid++;
        else                     swap(nums, mid, hi--);
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
