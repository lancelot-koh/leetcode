# T4-3 — Quick Select 快速选择

> **Core idea:** Partition array around a pivot (like QuickSort's partition step). After partition, the pivot is at its final sorted position. Recurse only into the half containing the k-th element. O(n) average, O(n²) worst case.
> **核心思想：** 围绕枢轴分区（类似QuickSort的分区步骤）。分区后枢轴在其最终排序位置。只递归进包含第k个元素的那一半。平均O(n)，最坏O(n²)。
>
> Complexity: O(n) average, O(n²) worst case (bad pivot). O(1) extra space.
> Full reference: `Heap/description.md` (compare with heap approach T2-3)

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Kth largest / Kth smallest (single element) | LC 215 |
| Median of unsorted array (one-shot) | — |
| Partial sort — only need first k elements | custom |

**QuickSelect vs Heap:**
| Method | Time | Space | When |
|---|---|---|---|
| QuickSelect | O(n) avg | O(1) | Single Kth element; can mutate input |
| Min-heap size k | O(n log k) | O(k) | Streaming; multiple queries; no mutation |
| Sort | O(n log n) | O(1) | Simple; k close to n |

---

## Core Templates 核心模板

### QuickSelect — find Kth largest

```java
public int findKthLargest(int[] nums, int k) {
    return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    // kth largest = (n-k)th smallest (0-indexed)
}

int quickSelect(int[] nums, int lo, int hi, int k) {
    if (lo == hi) return nums[lo];

    int pivotIdx = partition(nums, lo, hi);
    if (pivotIdx == k)      return nums[pivotIdx];
    else if (pivotIdx < k)  return quickSelect(nums, pivotIdx + 1, hi, k);
    else                    return quickSelect(nums, lo, pivotIdx - 1, k);
}

int partition(int[] nums, int lo, int hi) {
    // Randomize pivot to avoid O(n²) worst case
    int randIdx = lo + (int)(Math.random() * (hi - lo + 1));
    swap(nums, randIdx, hi);

    int pivot = nums[hi], i = lo;
    for (int j = lo; j < hi; j++)
        if (nums[j] <= pivot) swap(nums, i++, j);
    swap(nums, i, hi);
    return i;
}

void swap(int[] nums, int a, int b) {
    int tmp = nums[a]; nums[a] = nums[b]; nums[b] = tmp;
}
```

### Lomuto partition (above) vs Hoare partition

```java
// Hoare: two pointers converge; pivot ends up in [lo..pivotIdx] region
// Use Lomuto (above) for simplicity in interviews
```

---

## Variants 变形

| Variant | Change | Example |
|---|---|---|
| Kth largest | Target index = `n - k` | LC 215 |
| Kth smallest | Target index = `k - 1` | custom |
| Median | Target index = `n/2` | — |
| Dutch National Flag | 3-way partition (0s/1s/2s) | LC 75 (see T4-5) |
| Partial sort first k | Stop after pivot reaches k | custom |

---

## Key Examples 关键例题

### Kth Largest Element in Array (LC 215)
```java
public int findKthLargest(int[] nums, int k) {
    return quickSelect(nums, 0, nums.length - 1, nums.length - k);
}

int quickSelect(int[] nums, int lo, int hi, int target) {
    int pivot = nums[hi], i = lo;
    for (int j = lo; j < hi; j++)
        if (nums[j] <= pivot) swap(nums, i++, j);
    swap(nums, i, hi);

    if (i == target)     return nums[i];
    if (i < target)      return quickSelect(nums, i + 1, hi, target);
    return quickSelect(nums, lo, i - 1, target);
}

void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Randomize pivot | `Math.random()` pivot prevents O(n²) on sorted/reverse-sorted input |
| Target index for Kth largest | `n - k` (0-indexed); Kth smallest = `k - 1` |
| Mutates input | QuickSelect rearranges nums; copy if original must be preserved |
| Recursion depth | Average O(log n), worst O(n); iterative version avoids stack issues |
| `i` after partition is final position | Pivot is in its sorted position; no need to include it in recursion |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 215 Kth Largest Element in an Array |
| Medium | LC 347 Top K Frequent Elements (QuickSelect on freq) |
| Medium | LC 973 K Closest Points to Origin |

**Order:** 215 → 973 → 347

---

## One-line Summary

```
QuickSelect = partition around random pivot; recurse into the half containing rank k; O(n) average with no extra space.
快速选择 = 随机枢轴分区；只递归含第k名的那半；平均O(n)且不用额外空间。
```
