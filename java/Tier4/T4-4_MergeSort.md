# T4-4 — Merge Sort 归并排序

> **Core idea:** Divide array in half recursively; merge two sorted halves back together. During the merge step, count inversions or compute cross-half answers. Stable, O(n log n) guaranteed.
> **核心思想：** 递归地将数组对半分割，再将两个有序半段合并回去。合并阶段可统计逆序对或计算跨半段答案。稳定排序，O(n log n)保证。
>
> Complexity: O(n log n) time, O(n) space (merge buffer).
> Full reference: `DP/description.md`

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Count inversions in array | LC 315, 493 |
| Stable sort requirement | — |
| Merge K sorted lists/arrays | LC 23 (see T2-3) |
| Sort linked list (no random access) | LC 148 |
| Count smaller/larger numbers after self | LC 315 |

**Signal:** inversions, "smaller numbers to the right", linked-list sort, external sort on large data.

---

## Core Templates 核心模板

### Standard Merge Sort

```java
void mergeSort(int[] nums, int lo, int hi) {
    if (lo >= hi) return;
    int mid = lo + (hi - lo) / 2;
    mergeSort(nums, lo, mid);
    mergeSort(nums, mid + 1, hi);
    merge(nums, lo, mid, hi);
}

void merge(int[] nums, int lo, int mid, int hi) {
    int[] tmp = Arrays.copyOfRange(nums, lo, hi + 1);
    int left = 0, right = mid - lo + 1, idx = lo;
    int rightLen = hi - lo;

    while (left <= mid - lo && right <= rightLen) {
        if (tmp[left] <= tmp[right]) nums[idx++] = tmp[left++];
        else                         nums[idx++] = tmp[right++];
    }
    while (left <= mid - lo)  nums[idx++] = tmp[left++];
    while (right <= rightLen) nums[idx++] = tmp[right++];
}
```

### Count Inversions (augmented merge)

```java
long inversions = 0;

void merge(int[] nums, int lo, int mid, int hi) {
    int[] tmp = Arrays.copyOfRange(nums, lo, hi + 1);
    int left = 0, right = mid - lo + 1, idx = lo;
    int rightLen = hi - lo;

    while (left <= mid - lo && right <= rightLen) {
        if (tmp[left] <= tmp[right]) {
            nums[idx++] = tmp[left++];
        } else {
            // All remaining left elements are > tmp[right] → inversions
            inversions += (mid - lo + 1) - left;
            nums[idx++] = tmp[right++];
        }
    }
    while (left <= mid - lo)  nums[idx++] = tmp[left++];
    while (right <= rightLen) nums[idx++] = tmp[right++];
}
```

### Sort Linked List (LC 148)

```java
public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) return head;

    // Find middle (fast-slow)
    ListNode slow = head, fast = head.next;
    while (fast != null && fast.next != null) {
        slow = slow.next; fast = fast.next.next;
    }
    ListNode mid = slow.next;
    slow.next = null;                        // split list

    ListNode left  = sortList(head);
    ListNode right = sortList(mid);
    return mergeLists(left, right);
}

ListNode mergeLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0), cur = dummy;
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) { cur.next = l1; l1 = l1.next; }
        else                   { cur.next = l2; l2 = l2.next; }
        cur = cur.next;
    }
    cur.next = l1 != null ? l1 : l2;
    return dummy.next;
}
```

---

## Variants 变形

| Variant | Key idea | Example |
|---|---|---|
| Count inversions | On right-pick in merge: `count += leftSize - leftPointer` | LC 315, 493 |
| Sort linked list | Find mid with fast-slow; split and recurse | LC 148 |
| Merge K sorted lists | K-way merge with heap | LC 23 |
| Count smaller numbers after self | Coordinate compress + merge sort | LC 315 |
| Reverse pairs | Right < 2×left: count in merge step | LC 493 |

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Stable: equal elements take LEFT | `tmp[left] <= tmp[right]` → take left; preserves relative order |
| Inversion count: right side taken | When right element wins, ALL remaining left elements form inversions with it |
| Copy to tmp array | Don't sort in-place during merge; copy first, then write back |
| Linked list: split with fast-slow | Use `fast = head.next` (not head) to get left-biased middle for even lists |
| Iterative bottom-up merge sort | Start with window=1; double each round; avoids recursion depth |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 148 Sort List |
| Hard | LC 315 Count of Smaller Numbers After Self |
| Hard | LC 493 Reverse Pairs |
| Hard | LC 327 Count of Range Sum |

**Order:** 148 → 315 → 493 → 327

---

## One-line Summary

```
Merge sort = split in half recursively, merge sorted halves; count inversions during the merge step when right side is taken.
归并排序 = 递归对半分，合并有序半段；右侧被取时统计逆序对。
```
