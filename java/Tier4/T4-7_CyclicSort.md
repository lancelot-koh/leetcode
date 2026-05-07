# T4-7 — Cyclic Sort 循环排序

> **Core idea:** For arrays containing values in range [1..n] (or [0..n-1]), each value belongs at index `value - 1`. Swap each element to its correct position in a single pass. Then scan for misplaced elements.
> **核心思想：** 对于值在[1..n]范围的数组，每个值应在索引`value-1`处。一次遍历将每个元素交换到正确位置，再扫描找错位元素。
>
> Complexity: O(n) time, O(1) space.

---

## How It Works — Mental Model 直觉理解

When values are drawn from the range [1..n] and the array has length n, there is a bijection between values and indices: value `v` belongs at index `v-1`. Cyclic sort exploits this by turning sorting into a series of targeted swaps: look at `nums[i]`, compute where it should live (`correctIdx = nums[i] - 1`), and swap it there immediately. You repeat this at position `i` until the element sitting at `i` is the one that belongs there, then advance. Because each swap places at least one element into its final position, the total number of swaps across the entire array is at most n, making the whole sort O(n). The algorithm gets its name from the fact that each swap is a step in a cycle — a permutation decomposes into cycles, and each cycle is resolved with `cycle_length - 1` swaps.

**Key invariant:** After the sort pass, every element `v` that appears exactly once in [1..n] sits at index `v-1`. Any index `i` where `nums[i] != i+1` reveals either a missing value (`i+1` is absent) or a duplicate (some value appears twice while `i+1` is missing from the second scan).

**Common mistake:** Using `nums[correctIdx] != i + 1` as the swap condition instead of `nums[i] != nums[correctIdx]`. When duplicates exist, `nums[i]` and `nums[correctIdx]` are equal — a swap would loop forever. You must stop when the two positions hold the same value, not when the target position already holds the right value.

---

## Step-by-Step Trace

```
Input: [3, 1, 4, 3, 2]   n=5, values [1..5] (3 appears twice, 5 is missing)

i=0: nums[0]=3, correctIdx=2; nums[0]≠nums[2] → swap → [4,1,3,3,2]
i=0: nums[0]=4, correctIdx=3; nums[0]≠nums[3] → swap → [3,1,3,4,2]
i=0: nums[0]=3, correctIdx=2; nums[0]==nums[2] (both 3) → advance i=1
i=1: nums[1]=1, correctIdx=0; nums[1]≠nums[0] → swap → [1,3,3,4,2]
i=1: nums[1]=3, correctIdx=2; nums[1]==nums[2] (both 3) → advance i=2
i=2: nums[2]=3, correctIdx=2; nums[2]==nums[2] → advance i=3
i=3: nums[3]=4, correctIdx=3; in place → advance i=4
i=4: nums[4]=2, correctIdx=1; nums[4]≠nums[1] → swap → [1,2,3,4,3]

Second scan: nums[4]=3 ≠ 4+1=5 → missing=5, duplicate=3  ✓
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Values in [1..n]; find missing number | LC 268, 448 |
| Values in [1..n]; find duplicate | LC 287, 442 |
| Find all missing numbers | LC 448 |
| Find all duplicates | LC 442 |
| Smallest missing positive | LC 41 |

**Signal:** array of integers in a known range; "find missing", "find duplicate", O(n) time O(1) space constraint.

**When NOT to use:** Values outside [1..n] range; values not integers; sorted array (binary search instead).

---

## Core Templates 核心模板

### Cyclic Sort pass

```java
int i = 0;
while (i < nums.length) {
    int correctIdx = nums[i] - 1;   // value v belongs at index v-1 (1-indexed values)
    if (nums[i] != nums[correctIdx]) {
        // the element at i is not home yet — swap it to its correct slot
        int tmp = nums[correctIdx];
        nums[correctIdx] = nums[i];
        nums[i] = tmp;
        // do NOT advance i: the new element at i needs to be checked next
    } else {
        i++;    // nums[i] is already at its correct position (or is a duplicate that can't be placed — advance either way)
    }
}
```

### Find all missing numbers (after sort pass)

```java
List<Integer> missing = new ArrayList<>();
for (int i = 0; i < nums.length; i++) {
    if (nums[i] != i + 1) { missing.add(i + 1); }
}
return missing;
```

### Find all duplicates (after sort pass)

```java
List<Integer> duplicates = new ArrayList<>();
for (int i = 0; i < nums.length; i++) {
    if (nums[i] != i + 1) { duplicates.add(nums[i]); }
}
return duplicates;
```

---

## Variants 变形

| Variant | After sort: look for | Example |
|---|---|---|
| Find missing (single) | First `i` where `nums[i] != i+1` | LC 268 |
| Find duplicate (single) | `nums[i] != i+1` → `nums[i]` is duplicate | LC 287 |
| Find all missing | Every `i` where `nums[i] != i+1` → add `i+1` | LC 448 |
| Find all duplicates | Every `i` where `nums[i] != i+1` → add `nums[i]` | LC 442 |
| Smallest missing positive | Place in [1..n] range, skip out-of-range; first gap | LC 41 |

---

## Key Examples 关键例题

### Find All Numbers Disappeared in Array (LC 448)
```java
public List<Integer> findDisappearedNumbers(int[] nums) {
    int i = 0;
    while (i < nums.length) {
        int j = nums[i] - 1;
        if (nums[i] != nums[j]) { int tmp = nums[j]; nums[j] = nums[i]; nums[i] = tmp; }
        else { i++; }
    }
    List<Integer> result = new ArrayList<>();
    for (int k = 0; k < nums.length; k++) {
        if (nums[k] != k + 1) { result.add(k + 1); }
    }
    return result;
}
```

### Find All Duplicates in Array (LC 442)
```java
public List<Integer> findDuplicates(int[] nums) {
    int i = 0;
    while (i < nums.length) {
        int j = nums[i] - 1;
        if (nums[i] != nums[j]) { int tmp = nums[j]; nums[j] = nums[i]; nums[i] = tmp; }
        else { i++; }
    }
    List<Integer> result = new ArrayList<>();
    for (int k = 0; k < nums.length; k++) {
        if (nums[k] != k + 1) { result.add(nums[k]); }
    }
    return result;
}
```

### First Missing Positive (LC 41)
```java
public int firstMissingPositive(int[] nums) {
    int n = nums.length, i = 0;
    while (i < n) {
        int j = nums[i] - 1;
        // Only place if in valid range [1..n] and not already correct
        if (j >= 0 && j < n && nums[i] != nums[j]) {
            int tmp = nums[j]; nums[j] = nums[i]; nums[i] = tmp;
        } else { i++; }
    }
    for (int k = 0; k < n; k++) {
        if (nums[k] != k + 1) { return k + 1; }
    }
    return n + 1;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Stop condition: `nums[i] != nums[j]` | Prevents infinite swap when duplicates exist at both positions |
| Only advance `i` when in correct position | Do NOT `i++` after a swap; the swapped-in element needs checking |
| Out-of-range values: skip | LC 41 has values outside [1..n]; guard with `j >= 0 && j < n` |
| 0-indexed vs 1-indexed | Values [1..n]: `correctIdx = nums[i] - 1`; values [0..n-1]: `correctIdx = nums[i]` |
| Duplicate detection: `nums[i] == nums[j]` | Both positions have the same value; no swap needed, advance `i` |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 268 Missing Number |
| Medium | LC 448 Find All Numbers Disappeared |
| Medium | LC 442 Find All Duplicates |
| Medium | LC 287 Find the Duplicate Number |
| Hard | LC 41 First Missing Positive |

**Order:** 268 → 448 → 442 → 287 → 41

---

## One-line Summary

```
Cyclic sort = swap each value to index value-1; one pass places everything; second scan finds mismatches (missing/duplicate).
循环排序 = 将每个值交换到索引value-1；一次遍历就位；第二次扫描找错位（缺失/重复）。
```
