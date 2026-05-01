# T3-6 — Fast & Slow Pointers (Floyd's) 快慢指针（弗洛伊德）

> **Core idea:** Two pointers move at different speeds (slow=1 step, fast=2 steps). If a cycle exists, they meet inside it. Use a second phase to find the cycle entry, length, or middle node.
> **核心思想：** 两个指针以不同速度移动（慢=1步，快=2步）。有环则必在环内相遇。第二阶段用于找环入口、环长或链表中点。
>
> Complexity: O(n) time, O(1) space.
> Full reference: `TwoPointer/2PointersPattern.md` Pattern 2 (Fast-Slow)

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Detect cycle in linked list | LC 141 |
| Find cycle entry point | LC 142 |
| Find middle of linked list | LC 876 |
| Find duplicate in [1..n] array | LC 287 |
| Happy number (cycle in sequence) | LC 202 |
| Palindrome linked list | LC 234 |

**Signal:** linked list cycle, "detect loop", "find duplicate without extra space", "middle node."

---

## Core Templates 核心模板

### Cycle detection (phase 1)

```java
ListNode slow = head, fast = head;

while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow == fast) {
        // cycle detected — proceed to phase 2
        break;
    }
}
// If fast == null or fast.next == null → no cycle
```

### Find cycle entry (phase 2)

```java
// After phase 1 meeting point, reset one pointer to head
ListNode entry = head;
while (entry != slow) {
    entry = entry.next;
    slow = slow.next;
}
return entry;   // cycle entry node
```

### Find middle of linked list

```java
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
return slow;    // slow is at middle (or second-middle for even length)
```

---

## Why Phase 2 Works 为什么第二阶段有效

```
Let: F = distance from head to cycle entry
     C = cycle length
     k = distance from cycle entry to meeting point

Phase 1 meeting: slow traveled F+k, fast traveled F+k+nC
Fast = 2 × Slow  →  F+k+nC = 2(F+k)  →  F = nC-k

Phase 2: entry pointer travels F more steps from head
         slow pointer travels F more steps from meeting point
         Both arrive at cycle entry simultaneously.
```

---

## Variants 变形

| Variant | Approach | Example |
|---|---|---|
| Cycle detection | Phase 1 only; return bool | LC 141 |
| Cycle entry | Phase 1 + Phase 2 (reset one to head) | LC 142 |
| Middle node | Stop when fast reaches end | LC 876 |
| Palindrome list | Find middle, reverse second half, compare | LC 234 |
| Find duplicate (array as implicit list) | `nums[i]` is "next pointer"; run Floyd's | LC 287 |
| K-th from end | Advance fast k steps first; then both move | LC 19 (two pointers) |

---

## Key Examples 关键例题

### Linked List Cycle (LC 141)
```java
public boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}
```

### Linked List Cycle II — entry point (LC 142)
```java
public ListNode detectCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) {
            ListNode entry = head;
            while (entry != slow) {
                entry = entry.next;
                slow = slow.next;
            }
            return entry;
        }
    }
    return null;
}
```

### Find the Duplicate Number (LC 287) — array as linked list
```java
public int findDuplicate(int[] nums) {
    // Treat nums[i] as "next pointer"; duplicate = cycle entry
    int slow = nums[0], fast = nums[0];
    do {
        slow = nums[slow];
        fast = nums[nums[fast]];
    } while (slow != fast);

    slow = nums[0];
    while (slow != fast) {
        slow = nums[slow];
        fast = nums[fast];
    }
    return slow;
}
```

### Palindrome Linked List (LC 234)
```java
public boolean isPalindrome(ListNode head) {
    // Find middle
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    // Reverse second half
    ListNode prev = null, cur = slow;
    while (cur != null) {
        ListNode next = cur.next;
        cur.next = prev;
        prev = cur;
        cur = next;
    }

    // Compare
    ListNode left = head, right = prev;
    while (right != null) {
        if (left.val != right.val) return false;
        left = left.next;
        right = right.next;
    }
    return true;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `fast != null && fast.next != null` | Both checks needed: fast.next can be null if even length |
| Phase 2 resets ONE pointer to head | Not both; slow stays at meeting point |
| `do-while` for LC 287 | Start condition same as loop condition; `do-while` avoids pre-check issue |
| Middle: slow stops at second-middle for even | `1→2→3→4`: slow lands on 3. If you need first-middle, stop `fast.next.next != null` |
| After reversal, restore if asked | Some problems require the original structure; reverse back after comparison |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 141 Linked List Cycle |
| Easy | LC 876 Middle of the Linked List |
| Medium | LC 142 Linked List Cycle II |
| Medium | LC 287 Find the Duplicate Number |
| Medium | LC 234 Palindrome Linked List |
| Medium | LC 202 Happy Number |

**Order:** 141 → 876 → 142 → 287 → 234 → 202

---

## One-line Summary

```
Fast-slow pointers: if cycle exists they meet in O(n); reset one to head and walk together to find the cycle entry.
快慢指针：有环则O(n)内相遇；重置一个指针回头，同步前进即可找到环入口。
```
