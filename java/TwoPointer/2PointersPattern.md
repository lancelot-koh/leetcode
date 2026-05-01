# Two Pointers 双指针

> **Core idea:** Use two indices to control a search space, eliminating impossible cases with each step.
> **核心思想：** 用两个指针控制搜索范围，每次移动都排除不可能的情况。
>
> Complexity gain: O(n²) → O(n)
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| sorted array | 有序数组 |
| pair sum / two sum | 两数之和 |
| remove duplicates / in-place | 去重 / 原地修改 |
| palindrome / reverse | 回文 / 反转 |
| merge two sorted arrays | 合并有序数组 |
| subsequence / alignment | 子序列 / 匹配 |
| cycle detection | 判环 |
| partition / reorder | 分区 / 重排 |

**When NOT to use 不适用的情况**

- Input has no ordering → cannot prune search space 无序且无法建立单调性
- Need ALL combinations → use backtracking instead 需要枚举全部组合用回溯

---

## 2. Quick Decision Guide 快速判断

```
Sorted array, find pair?          → Pattern 1: Opposite Direction
In-place remove/deduplicate?      → Pattern 2: Fast–Slow (Read–Write)
Match subsequence / two strings?  → Pattern 3: Same Direction
Reorder array by condition?       → Pattern 4: Partition
Two sorted arrays → one output?   → Pattern 5: Merge
Linked list cycle?                → Pattern 6: Cycle Detection (Floyd)
3Sum / 4Sum?                      → Pattern 7: K-Sum (Fix + Two Pointers)
Construct max/min greedily?       → Pattern 8: Greedy + Two Pointers
```

---

## 3. Patterns 模式

---

### Pattern 1 — Opposite Direction 左右夹逼

**When:** sorted array, shrink search from both ends
**适用：** 有序数组，从两端向中间夹逼

**Template 模板**

```java
int left = 0, right = nums.length - 1;

while (left < right) {
    if (condition met) {
        // found answer
    } else if (value too small) {
        left++;   // increase value
    } else {
        right--;  // decrease value
    }
}
```

**Key insight 核心原理**

利用单调性：`left++` 让值变大，`right--` 让值变小，每次移动都排除一列可能。
Monotonicity: each pointer move eliminates an entire row/column of possibilities.

**Variants 变形**

| Variant | Example |
|---|---|
| Pair sum in sorted array | Two Sum II (LC 167) |
| Closest pair to target | Two Sum closest |
| Palindrome check | Valid Palindrome (LC 125) |
| Max area between walls | Container With Most Water (LC 11) |
| Reverse in place | Reverse String (LC 344) |

**Example: Two Sum II**

```java
public int[] twoSumSorted(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    while (left < right) {
        int sum = nums[left] + nums[right];
        if (sum == target)  return new int[]{left + 1, right + 1};
        else if (sum < target) left++;
        else                   right--;
    }
    return new int[]{-1, -1};
}
```

---

### Pattern 2 — Fast–Slow (Read–Write) 快慢指针（扫描–写入）

**When:** in-place modification — keep some elements, discard others
**适用：** 原地修改 — 保留部分元素，跳过其余

**Template 模板**

```java
int slow = 0;  // write pointer 写指针

for (int fast = 0; fast < nums.length; fast++) {
    if (shouldKeep(nums[fast])) {
        nums[slow++] = nums[fast];
    }
}
// nums[0..slow-1] is the valid result
```

**Key insight 核心原理**

`fast` 负责扫描（读），`slow` 负责构建结果（写）。
`fast` scans everything; `slow` only advances when writing a valid element.
**Invariant:** `nums[0..slow-1]` always holds the valid result so far.

**Variants 变形**

| Variant | Example |
|---|---|
| Remove duplicates (sorted) | LC 26 |
| Remove all instances of value | LC 27 |
| Keep at most k duplicates | LC 80 |
| Move zeroes to end (swap) | LC 283 |

**Example: Remove Duplicates**

```java
public int removeDuplicates(int[] nums) {
    int slow = 1;
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[fast - 1]) {
            nums[slow++] = nums[fast];
        }
    }
    return slow;
}
```

**Variant: keep at most 2 duplicates (LC 80)**

```java
public int removeDuplicatesII(int[] nums) {
    int slow = 2;
    for (int fast = 2; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow - 2]) {  // compare with 2 positions back
            nums[slow++] = nums[fast];
        }
    }
    return slow;
}
```

---

### Pattern 3 — Same Direction Matching 同向双指针（子序列匹配）

**When:** check if one sequence is a subsequence of another; character alignment
**适用：** 判断子序列、两字符串按规则匹配

**Template 模板**

```java
int i = 0, j = 0;  // i = source, j = target

while (i < s.length() && j < t.length()) {
    if (s.charAt(i) == t.charAt(j)) {
        i++;  // match found, advance source
    }
    j++;      // always advance target scanner
}
return i == s.length();  // all of s matched?
```

**Key insight 核心原理**

一个指针"被动跟随"（只在匹配时前进），另一个"主动扫描"（始终前进）。
One pointer is selective (advances only on match); the other always scans forward.

**Variants 变形**

| Variant | Example |
|---|---|
| Is Subsequence | LC 392 |
| Match with wildcard / skip rules | custom |
| Compress array to unique-only stream | custom |

---

### Pattern 4 — Partition / In-place Rearrangement 分区重排

**When:** reorder elements into regions by a condition, no extra space
**适用：** 按条件把数组分成若干区域，原地完成

**Mental model 区间模型**

```
[ valid zone | unknown | invalid zone ]
  [0..slow-1]  [slow..fast]  [fast+1..end]
```

**Template 模板**

```java
int slow = 0;

for (int fast = 0; fast < nums.length; fast++) {
    if (nums[fast] satisfies condition) {
        swap(nums, slow, fast);
        slow++;
    }
}
```

**Variants 变形**

| Variant | Regions | Example |
|---|---|---|
| Move zeroes to end | [non-zero \| zero] | LC 283 |
| Dutch National Flag | [0s \| 1s \| 2s] | LC 75 |
| QuickSort partition | [< pivot \| pivot \| > pivot] | QuickSort |

**Example: Move Zeroes**

```java
public void moveZeroes(int[] nums) {
    int slow = 0;
    for (int fast = 0; fast < nums.length; fast++) {
        if (nums[fast] != 0) {
            int tmp = nums[slow]; 
            nums[slow] = nums[fast]; 
            nums[fast] = tmp;
            slow++;
        }
    }
}
```

**Example: Sort Colors (Dutch National Flag) — 3 pointers**

```java
public void sortColors(int[] nums) {
    int lo = 0, mid = 0, hi = nums.length - 1;

    while (mid <= hi) {
        if      (nums[mid] == 0) { swap(nums, lo++, mid++); }
        else if (nums[mid] == 1) { mid++; }
        else                     { swap(nums, mid, hi--); }
    }
}
```

---

### Pattern 5 — Merge (Two Sources → One) 多数组合并

**When:** two sorted inputs need to be combined in order
**适用：** 两个有序输入合并成一个有序结果

**Template 模板**

```java
int i = 0, j = 0, k = 0;

while (i < a.length && j < b.length) {
    if (a[i] <= b[j]) result[k++] = a[i++];
    else              result[k++] = b[j++];
}
while (i < a.length) result[k++] = a[i++];
while (j < b.length) result[k++] = b[j++];
```

**Variants 变形**

| Variant | Example |
|---|---|
| Merge sorted arrays | LC 88 (merge backwards in-place) |
| Merge two sorted linked lists | LC 21 |
| Intersection of two arrays | LC 349 |
| Union of two arrays | custom |

**Tip — LC 88 merge in-place:** fill from the back to avoid overwriting:

```java
int i = m - 1, j = n - 1, k = m + n - 1;
while (i >= 0 && j >= 0) {
    nums1[k--] = (nums1[i] > nums2[j]) ? nums1[i--] : nums2[j--];
}
while (j >= 0) nums1[k--] = nums2[j--];
```

---

### Pattern 6 — Cycle Detection (Floyd's) 判环（龟兔赛跑）

**When:** detect or find the start of a cycle in a linked list / repeated-state sequence
**适用：** 链表判环，找环入口

**Template 模板**

```java
// Phase 1: detect 判断是否有环
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow == fast) break;  // cycle detected
}

// Phase 2: find entry 找环入口
slow = head;
while (slow != fast) {
    slow = slow.next;
    fast = fast.next;
}
return slow;  // entry node
```

**Key insight 核心原理（面试必背）**

- `slow` 走 1 步，`fast` 走 2 步 → 相遇则有环
- 相遇后，一个指针回到头，两者同速走 → 再次相遇即为环入口
- Phase 2 works because: distance from head to entry == distance from meeting point to entry (modular).

**Variants 变形**

| Variant | Example |
|---|---|
| Has cycle? | LC 141 |
| Find cycle entry | LC 142 |
| Find middle of list | LC 876 (stop when fast reaches end) |
| Remove nth from end | LC 19 (offset fast by n first) |

**Find middle (no cycle):**

```java
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
return slow;  // middle node
```

---

### Pattern 7 — K-Sum Expansion K-Sum 降维

**When:** 3Sum, 4Sum — reduce to a simpler Two-Pointer problem by fixing outer elements
**适用：** 多数之和问题，逐层降维

**Template 模板 (3Sum)**

```java
Arrays.sort(nums);

for (int i = 0; i < nums.length - 2; i++) {
    if (i > 0 && nums[i] == nums[i - 1]) continue;  // skip duplicates

    int left = i + 1, right = nums.length - 1;
    while (left < right) {
        int sum = nums[i] + nums[left] + nums[right];
        if      (sum == 0) { result.add(...); skip duplicates; left++; right--; }
        else if (sum < 0)  { left++; }
        else               { right--; }
    }
}
```

**Key insight 核心原理**

Sort first → fix the outermost pointer → reduce to (K-1)-Sum → repeat until 2Sum (Two Pointers).
先排序 → 固定最外层 → 内层降维 → 最终用双指针解 2Sum。

**Skip duplicates 去重技巧**

```java
// After finding a valid triplet, skip same values
while (left < right && nums[left] == nums[left + 1])  left++;
while (left < right && nums[right] == nums[right - 1]) right--;
```

**Variants 变形**

| Variant | Example |
|---|---|
| 3Sum (all unique triplets) | LC 15 |
| 3Sum Closest | LC 16 |
| 4Sum | LC 18 |

---

### Pattern 8 — Greedy + Two Pointers 贪心 + 双指针

**When:** construct max/min sequence; make locally optimal choices while scanning
**适用：** 贪心构造最大/最小结果，边扫描边做局部最优决策

**Key insight 核心原理**

双指针控制范围 + 贪心决定在该范围内的选择。
Two pointers shrink the candidate window; greedy picks the best move within it.

**Common problems 典型问题**

| Problem | Greedy rule |
|---|---|
| Container With Most Water (LC 11) | Move the shorter side (moving taller can't help) |
| Trapping Rain Water (LC 42) | Track max from left/right; fill from shorter side |
| Jump Game II (LC 45) | Greedily extend reach within current jump |
| Largest Number (LC 179) | Comparator: prefer ab > ba |

**Example: Container With Most Water**

```java
public int maxArea(int[] h) {
    int left = 0, right = h.length - 1, max = 0;
    while (left < right) {
        max = Math.max(max, Math.min(h[left], h[right]) * (right - left));
        if (h[left] < h[right]) left++;
        else                    right--;
    }
    return max;
}
```

Why move the shorter side? Moving the taller side can only decrease or maintain width while height is still bounded by the shorter — guaranteed no improvement.
为什么移动较短那侧？移动较高一侧宽度减小且高度上限不变，不可能变大。

---

## 4. Advanced Skills 进阶技能

### Skill 1 — Pointer Movement Justification 移动合理性证明

**面试最高频考点。** 你必须能解释：为什么移动这个指针不会错过答案？

Template answer:
> "Moving `left` is safe because the current `right` already gives the best possible result for this `left`. Any `right` to the left of the current `right` would only be worse."

### Skill 2 — Invariant Thinking 不变量思维

Before coding, define what stays true throughout:

```
Invariant for fast-slow:  nums[0..slow-1] contains all valid elements seen so far
Invariant for partition:  nums[0..slow-1] satisfy condition; nums[slow..fast-1] do not
```

If you maintain the invariant with every step, the algorithm is correct by construction.

### Skill 3 — Boundary Control 边界控制

Most bugs come from:
- `left < right` vs `left <= right` — use `<` when pointers must not cross; `<=` when they can meet
- Off-by-one when updating slow after writing vs before
- Forgetting to skip duplicates in K-Sum

### Skill 4 — Transform Into Two Pointers 将问题转化为双指针

```
Palindrome?         → Opposite direction on char array
Two sorted lists?   → Merge pattern
Cycle?              → Floyd fast-slow
Sorted + pair sum?  → Opposite direction
Subsequence?        → Same direction matching
```

---

## 5. Interview Script 面试话术

**English:**
> I'd use two pointers here because [the array is sorted / I need in-place modification / I'm looking for a pair]. By moving the [left / right / fast] pointer when [condition], I eliminate [impossible cases] without missing any valid answer. This brings the complexity from O(n²) down to O(n).

**中文：**
> 我会用双指针，因为[数组有序 / 需要原地修改 / 要找一对数]。当[条件]时移动[左/右/快]指针，每次都排除了不可能的情况，不会漏掉正确答案。时间复杂度从 O(n²) 降到 O(n)。

---

## 6. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Opposite Direction | 167, 125, 11, 344 |
| 2. Fast–Slow (Read–Write) | 26, 27, 80, 283 |
| 3. Same Direction Matching | 392 |
| 4. Partition | 283, 75 |
| 5. Merge | 88, 21, 349 |
| 6. Cycle Detection | 141, 142, 876, 19 |
| 7. K-Sum | 15, 16, 18 |
| 8. Greedy + Pointers | 11, 42, 45 |

**Recommended order 建议练习顺序:**
167 → 26 → 125 → 283 → 75 → 392 → 141 → 876 → 15 → 11 → 42

---

## 7. One-line Summary 一句话总结

```
Two pointers = use pointer movement to eliminate impossible cases at each step.
双指针 = 用指针移动来逐步排除不可能的情况，避免暴力枚举。
```

**Next steps 下一步:**
- Sliding Window — dynamic two pointers with a window constraint 滑动窗口（动态区间版双指针）
- Prefix Sum — complement for range queries 前缀和（区间查询的配套工具）
