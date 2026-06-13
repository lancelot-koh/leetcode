# 🔗 Linked List - The Pointer Manipulation Pattern

**Master in-place modifications and pointer operations**

---

## 📍 Why This Matters

### Interview Frequency: **10% of all problems** ⭐⭐⭐⭐

### The Insight
Linked lists test **pointer thinking**. You must manipulate pointers without losing references.

---

## 🎯 The Core Concept

### Linked List Definition
```java
public class ListNode {
    int val;
    ListNode next;
}
```

### Key Characteristics
- ✅ Sequential access only (no random access)
- ✅ O(1) insertion/deletion at known position
- ✅ O(n) search
- ✅ Pointer manipulation is tricky

---

## 🔧 The 5-Step Linked List Framework

### Step 1: Identify Operation Type

| Operation | Technique | Space |
|---|---|---|
| **Reverse** | Swap pointers | O(1) |
| **Detect Cycle** | Slow & fast | O(1) |
| **Find Intersection** | Two pointers | O(1) |
| **Merge** | Compare pointers | O(1) |
| **Partition** | Split & recombine | O(1) or O(n) |

---

### Step 2: Use Dummy Node (Usually)

```java
ListNode dummy = new ListNode(0);
dummy.next = head;
ListNode prev = dummy;
ListNode curr = head;
```

**Why:** Simplifies edge cases, especially head modification

---

### Step 3: Maintain Pointers Carefully

```
Usually keep:
- prev: Previous node
- curr: Current node  
- next: Next node (save before modifying!)
```

**Critical:** Always save next before modifying!
```java
ListNode nextTemp = curr.next;  // SAVE FIRST
curr.next = prev;               // Then modify
```

---

### Step 4: Move Pointers

```java
prev = curr;
curr = nextTemp;
```

---

### Step 5: Return Correct Node

```java
return dummy.next;  // Return new head
```

---

## 📚 Code Templates

### Template 1: Reverse Linked List

```java
public ListNode reverse(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    
    while (curr != null) {
        ListNode nextTemp = curr.next;  // Save next
        curr.next = prev;                 // Reverse pointer
        prev = curr;                      // Move prev
        curr = nextTemp;                  // Move curr
    }
    
    return prev;  // New head
}
```

**Trace:**
```
1 → 2 → 3
↓
null ← 1   2 → 3
       ↑prev
         curr
         
null ← 1 ← 2   3
          ↑prev
             curr

null ← 1 ← 2 ← 3
               ↑prev
                null
                curr (done)

Return: 3
```

---

### Template 2: Slow & Fast (Cycle Detection)

```java
public boolean hasCycle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;           // Move 1 step
        fast = fast.next.next;      // Move 2 steps
        
        if (slow == fast) return true;  // They meet = cycle
    }
    
    return false;  // Reached end = no cycle
}
```

---

### Template 3: Merge Two Sorted Lists

```java
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    
    while (list1 != null && list2 != null) {
        if (list1.val <= list2.val) {
            curr.next = list1;
            list1 = list1.next;
        } else {
            curr.next = list2;
            list2 = list2.next;
        }
        curr = curr.next;
    }
    
    // Attach remaining
    curr.next = (list1 != null) ? list1 : list2;
    
    return dummy.next;
}
```

---

## 💡 Quick Examples

### Example 1: Reverse Linked List (LC206)

```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    while (head != null) {
        ListNode next = head.next;
        head.next = prev;
        prev = head;
        head = next;
    }
    return prev;
}
```

### Example 2: Linked List Cycle (LC141)

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

### Example 3: Merge Two Sorted (LC21)

```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(-1);
    ListNode curr = dummy;
    
    while (l1 != null && l2 != null) {
        if (l1.val < l2.val) {
            curr.next = l1;
            l1 = l1.next;
        } else {
            curr.next = l2;
            l2 = l2.next;
        }
        curr = curr.next;
    }
    
    curr.next = l1 != null ? l1 : l2;
    return dummy.next;
}
```

### Example 4: Remove Nth Node (LC19)

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    ListNode first = dummy;
    ListNode second = dummy;
    
    for (int i = 0; i <= n; i++) {
        first = first.next;
    }
    
    while (first != null) {
        first = first.next;
        second = second.next;
    }
    
    second.next = second.next.next;
    return dummy.next;
}
```

---

## ⚠️ Common Pitfalls

### Pitfall 1: Losing Reference

```java
❌ WRONG:
curr.next = prev;
curr = curr.next;  // Lost reference to next!

✅ CORRECT:
ListNode next = curr.next;  // Save first
curr.next = prev;
curr = next;
```

---

### Pitfall 2: Null Pointer Exception

```java
❌ WRONG:
while (curr != null) {
    curr = curr.next.next;  // What if curr.next is null?
}

✅ CORRECT:
while (curr != null && curr.next != null) {
    curr = curr.next.next;
}
```

---

### Pitfall 3: Wrong Return Value

```java
❌ WRONG:
return curr;  // curr is null after traversal

✅ CORRECT:
return dummy.next;  // Use dummy to track new head
```

---

## 🚀 Practice Plan

**Easy:** LC206, LC21, LC141
**Medium:** LC2, LC86, LC143
**Hard:** LC25, LC138, LC146

---

## 📋 Essential Linked List Patterns

| Problem | Pattern | Technique |
|---|---|---|
| Reverse | Two pointer | Pointer swap |
| Cycle | Floyd | Slow & fast |
| Merge | Compare | Dummy node |
| Remove | Two pointer | n-node gap |
| Partition | Split/join | Track sections |
| Clone | Map | Deep copy |

---

**Master linked list pointer logic. It's 10% of interviews.** 🚀
