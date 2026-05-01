# T4-7 — Cyclic Sort 循环排序

> **Core idea:** For arrays containing values in range [1..n] (or [0..n-1]), each value belongs at index `value - 1`. Swap each element to its correct position in a single pass. Then scan for misplaced elements.
> **核心思想：** 对于值在[1..n]范围的数组，每个值应在索引`value-1`处。一次遍历将每个元素交换到正确位置，再扫描找错位元素。
>
> Complexity: O(n) time, O(1) space.

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
    int correctIdx = nums[i] - 1;   // where nums[i] SHOULD be (1-indexed)
    if (nums[i] != nums[correctIdx]) {
        // swap nums[i] to its correct position
        int tmp = nums[correctIdx];
        nums[correctIdx] = nums[i];
        nums[i] = tmp;
    } else {
        i++;    // nums[i] is in correct position (or duplicate)
    }
}
```

### Find all missing numbers (after sort pass)

```java
List<Integer> missing = new ArrayList<>();
for (int i = 0; i < nums.length; i++)
    if (nums[i] != i + 1) missing.add(i + 1);
return missing;
```

### Find all duplicates (after sort pass)

```java
List<Integer> duplicates = new ArrayList<>();
for (int i = 0; i < nums.length; i++)
    if (nums[i] != i + 1) duplicates.add(nums[i]);
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
        else i++;
    }
    List<Integer> result = new ArrayList<>();
    for (int k = 0; k < nums.length; k++)
        if (nums[k] != k + 1) result.add(k + 1);
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
        else i++;
    }
    List<Integer> result = new ArrayList<>();
    for (int k = 0; k < nums.length; k++)
        if (nums[k] != k + 1) result.add(nums[k]);
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
        } else i++;
    }
    for (int k = 0; k < n; k++)
        if (nums[k] != k + 1) return k + 1;
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
