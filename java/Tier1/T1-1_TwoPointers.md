# T1-1 — Two Pointers 双指针

> **Core idea:** Two indices moving through a sorted array or string, eliminating impossible cases with each step instead of checking all pairs.
> **核心思想：** 两个下标在有序数组或字符串中移动，每步排除不可能的情况，避免暴力枚举所有对。
>
> Complexity: O(n) time, O(1) space. Replaces O(n²) brute force.
> Full reference: `TwoPointer/2PointersPattern.md`

---

## When to Use 什么时候用

| Trigger | Example problem |
|---|---|
| Sorted array + find pair | Two Sum II |
| Remove duplicates in-place | LC 26, 80 |
| Palindrome / reverse | LC 125, 344 |
| Container / area optimization | LC 11 |
| Merge two sorted sequences | LC 88, 21 |

**Not for:** unsorted data with no monotonicity, need all combinations (→ backtracking).

---

## Core Templates 核心模板

### Opposite Direction 左右夹逼

```java
int left = 0, right = nums.length - 1;

while (left < right) {
    int sum = nums[left] + nums[right];
    if      (sum == target) return result;
    else if (sum < target)  left++;    // too small → move left
    else                    right--;   // too big  → move right
}
```

### Fast–Slow (Read–Write) 快慢指针

```java
int slow = 0;                          // write pointer
for (int fast = 0; fast < n; fast++) {
    if (shouldKeep(nums[fast]))
        nums[slow++] = nums[fast];
}
// nums[0..slow-1] is the result
```

---

## Variants 变形

| Pattern | Template | Key problems |
|---|---|---|
| Pair sum (sorted) | Opposite direction | LC 167, 15 |
| Remove element | Fast–slow, keep if `nums[fast] != val` | LC 27 |
| Remove duplicates | Fast–slow, keep if `nums[fast] != nums[fast-1]` | LC 26, 80 |
| Move zeroes | Fast–slow with swap | LC 283 |
| Container with most water | Opposite, move shorter side | LC 11 |
| Valid palindrome | Opposite, skip non-alphanumeric | LC 125 |
| 3Sum | Sort + fix one + opposite direction inside | LC 15 |

---

## Key Examples 关键例题

### Two Sum II (LC 167)
```java
public int[] twoSum(int[] numbers, int target) {
    int l = 0, r = numbers.length - 1;
    while (l < r) {
        int sum = numbers[l] + numbers[r];
        if      (sum == target) return new int[]{l+1, r+1};
        else if (sum < target)  l++;
        else                    r--;
    }
    return new int[]{-1, -1};
}
```

### Remove Duplicates (LC 26)
```java
public int removeDuplicates(int[] nums) {
    int slow = 1;
    for (int fast = 1; fast < nums.length; fast++)
        if (nums[fast] != nums[fast - 1])
            nums[slow++] = nums[fast];
    return slow;
}
```

### 3Sum (LC 15)
```java
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    for (int i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] == nums[i-1]) continue;   // skip duplicate i
        int l = i+1, r = nums.length-1;
        while (l < r) {
            int sum = nums[i] + nums[l] + nums[r];
            if (sum == 0) {
                res.add(List.of(nums[i], nums[l], nums[r]));
                while (l < r && nums[l] == nums[l+1]) l++;
                while (l < r && nums[r] == nums[r-1]) r--;
                l++; r--;
            } else if (sum < 0) l++;
            else                r--;
        }
    }
    return res;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Justify each pointer move | "Moving `left` is safe because current `right` is already the best for this `left`" |
| `left < right` not `<=` | Pointers must not cross; single element can't form a pair |
| Sort first for K-Sum | 3Sum, 4Sum always start with `Arrays.sort` |
| Skip duplicates in 3Sum | Skip at outer (`i`) AND inner (`l`, `r`) loops |
| Fast-slow invariant | `nums[0..slow-1]` always contains the valid result |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 167 Two Sum II, LC 344 Reverse String, LC 125 Valid Palindrome |
| Medium | LC 26 Remove Duplicates, LC 11 Container With Most Water, LC 15 3Sum |
| Hard | LC 18 4Sum, LC 42 Trapping Rain Water |

**Order:** 167 → 26 → 125 → 11 → 15 → 18

---

## One-line Summary

```
Two pointers = use sorted-order monotonicity to move two indices toward each other, eliminating pairs in O(1) each step.
双指针 = 利用有序单调性，两个指针相向移动，每步 O(1) 排除一批候选。
```
