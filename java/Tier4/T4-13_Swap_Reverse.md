# T4-13 — Swap / Reverse (In-Place Array Manipulation) 交换/翻转（原地数组操作）

> **Core idea:** Fundamental building blocks for in-place array transformations. Swap two elements in O(1); reverse a subarray in O(n); rotate by composing reversals.
> **核心思想：** 原地数组变换的基础操作。O(1)交换两元素；O(n)翻转子数组；用翻转组合实现旋转。
>
> Complexity: O(1) per swap; O(n) for full reversal or rotation.

---

## How It Works — Mental Model 直觉理解

Reversal is the simplest two-pointer technique: one pointer starts at the left end, one at the right, and they swap elements while converging toward the center. The rotation-by-reversal trick works because reversing the entire array and then reversing each of the two parts independently is equivalent to a cyclic shift. Intuitively: reversing everything puts every element in the wrong position in a perfectly symmetric way; reversing each segment then corrects the internal ordering within each segment, leaving the segments themselves in the right order. The next-permutation algorithm builds on the same idea: reverse a suffix to get the lexicographically smallest arrangement of that suffix after a targeted swap.

**Key invariant:** After `reverse(nums, lo, hi)`, every element at index `lo + k` moves to `hi - k`. The range `[lo, hi]` is fully mirrored with no element outside the range affected.

**Common mistake:** Forgetting `k %= n` before applying the three-reversal rotation. If `k == n`, rotating by the full length is a no-op; without the modulo, `reverse(nums, 0, k-1)` could use `k-1 = n-1` and corrupt the result. Similarly, if `k == 0` after the modulo, all three reversals cancel out correctly — the code is safe.

---

## Step-by-Step Trace (Rotate Right by k)

```
Input: [1,2,3,4,5], k=2

Step 1 — reverse all [0..4]:    [5,4,3,2,1]
Step 2 — reverse first k [0..1]: [4,5,3,2,1]
Step 3 — reverse rest  [2..4]:  [4,5,1,2,3]  ✓

Verification: 4,5 were the last 2 elements → now at front. 1,2,3 shifted right by 2.
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Rotate array left/right by k | LC 189 |
| Reverse words in string | LC 151, 557 |
| Next permutation | LC 31 |
| Previous permutation / rearrange | LC 556 |
| Palindrome check in-place | two-pointer comparison |
| Reverse linked list | LC 206 |

**Signal:** "rotate", "reverse", "in-place", "without extra space", "rearrange."

---

## Core Templates 核心模板

### Swap two elements

```java
void swap(int[] nums, int i, int j) {
    int tmp = nums[i]; nums[i] = nums[j]; nums[j] = tmp;
}

// XOR swap (interview trick — avoid in production):
// nums[i] ^= nums[j]; nums[j] ^= nums[i]; nums[i] ^= nums[j];
```

### Reverse subarray `[lo, hi]`

```java
void reverse(int[] nums, int lo, int hi) {
    while (lo < hi) { swap(nums, lo++, hi--); }
}
```

### Rotate array right by k (three-reversal trick)

```java
public void rotate(int[] nums, int k) {
    int n = nums.length;
    k %= n;                 // k can be > n; rotation by n is a no-op
    reverse(nums, 0, n-1);  // step 1: reverse entire array — puts last k elements at front (but backwards)
    reverse(nums, 0, k-1);  // step 2: un-reverse the first k elements (the ones that should be at front)
    reverse(nums, k, n-1);  // step 3: un-reverse the remaining n-k elements (the ones that should be at back)
}
```

### Reverse linked list

```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null, cur = head;
    while (cur != null) {
        ListNode next = cur.next;  // save the next node before overwriting the pointer
        cur.next = prev;           // reverse the link: point current node backward
        prev = cur;                // advance prev to the current node (will become new head at the end)
        cur = next;                // advance cur to the saved next node
    }
    return prev;  // when cur is null, prev is the new head (the old tail)
}
```

### Reverse words in string

```java
public String reverseWords(String s) {
    char[] arr = s.trim().replaceAll("\\s+", " ").toCharArray();
    reverse(arr, 0, arr.length - 1);  // reverse all
    int start = 0;
    for (int i = 0; i <= arr.length; i++) {
        if (i == arr.length || arr[i] == ' ') {
            reverse(arr, start, i - 1);  // reverse each word
            start = i + 1;
        }
    }
    return new String(arr);
}

void reverse(char[] arr, int lo, int hi) {
    while (lo < hi) { char tmp = arr[lo]; arr[lo++] = arr[hi]; arr[hi--] = tmp; }
}
```

---

## Key Examples 关键例题

### Rotate Array (LC 189)
```java
public void rotate(int[] nums, int k) {
    k %= nums.length;
    reverse(nums, 0, nums.length - 1);
    reverse(nums, 0, k - 1);
    reverse(nums, k, nums.length - 1);
}
void reverse(int[] nums, int lo, int hi) {
    while (lo < hi) { int t = nums[lo]; nums[lo++] = nums[hi]; nums[hi--] = t; }
}
```

### Next Permutation (LC 31) — uses swap + reverse
```java
public void nextPermutation(int[] nums) {
    int n = nums.length, i = n - 2;

    // Step 1: find rightmost ascending pair (nums[i] < nums[i+1])
    // Everything to the right of i is in descending order (already the largest possible suffix)
    while (i >= 0 && nums[i] >= nums[i + 1]) { i--; }

    if (i >= 0) {
        // Step 2: find the smallest element > nums[i] in the descending suffix
        // Scanning right-to-left finds the first element larger than nums[i]
        int j = n - 1;
        while (nums[j] <= nums[i]) { j--; }
        swap(nums, i, j);    // swap nums[i] with the next-larger value to make the permutation bigger
    }

    // Step 3: reverse the suffix after i to make it the smallest possible arrangement
    // (the suffix was descending = largest; reversing makes it ascending = smallest)
    reverse(nums, i + 1, n - 1);
}
```

### Reverse Linked List (LC 206)
```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null, cur = head;
    while (cur != null) {
        ListNode next = cur.next;
        cur.next = prev;
        prev = cur;
        cur = next;
    }
    return prev;
}
```

---

## Rotation by Reversal: Why It Works 旋转原理

```
Original:   [1, 2, 3 | 4, 5]    rotate right by 2
After rev all: [5, 4, 3, 2, 1]
After rev [0,k-1]: [4, 5, 3, 2, 1]
After rev [k,n-1]: [4, 5, 1, 2, 3]  ✓
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `k %= n` before rotation | k can exceed array length |
| Three-reversal rotation | Works for left rotation too: reverse [0,n-k-1], [n-k,n-1], then all |
| XOR swap requires `i != j` | `arr[i] ^= arr[i]` zeroes the element if i == j |
| Next permutation step order | Find, swap, reverse — all three steps required |
| Reverse chars vs reverse words | LC 557 reverses each word; LC 151 reverses word order |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 344 Reverse String |
| Easy | LC 206 Reverse Linked List |
| Medium | LC 189 Rotate Array |
| Medium | LC 151 Reverse Words in a String |
| Medium | LC 31 Next Permutation |
| Medium | LC 557 Reverse Words in a String III |

**Order:** 344 → 206 → 189 → 557 → 151 → 31

---

## One-line Summary

```
Swap = tmp variable; Reverse = two-pointer converge; Rotate = three reversals (all, first k, rest k..n-1).
交换 = 临时变量；翻转 = 双指针收拢；旋转 = 三次翻转（全部、前k、后n-k）。
```
