# 📈 Complexity Analysis - How to Evaluate Solutions

**Master time & space analysis - the skill that proves you understand your code**

---

## 📍 Why This Matters

### Essential for: **100% of problems** ⭐⭐⭐⭐⭐

Every solution needs complexity analysis. Interviewers judge:
- ✅ Do you understand what your code does?
- ✅ Can you analyze algorithms?
- ✅ Do you know how to optimize?

---

## 🎯 The Core Concept

### Time Complexity: How Fast?

```
O(1): Constant          (1 operation)
O(log n): Binary        (divide in half each time)
O(n): Linear            (loop through once)
O(n log n): Good sort   (merge sort, quick sort)
O(n²): Nested loops     (every pair)
O(n³): Triple nested    (every triplet)
O(2^n): Exponential     (all subsets)
O(n!): Factorial        (all permutations)
```

**Practical:**
```
n = 100:      n² = 10,000     (Fast)
n = 1,000:    n² = 1M         (Fast)
n = 10,000:   n² = 100M       (OK)
n = 100,000:  n² = 10B        (Slow! Timeout)
n = 1,000,000: n² = 1T        (Way too slow)

General rule: 10^8 operations per second
```

### Space Complexity: How Much Memory?

```
O(1): Constant          (no extra space)
O(log n): Stack depth   (recursive binary search)
O(n): Extra array       (copy, hash map)
O(n²): 2D array         (dp table)
O(n!): All permutations (store all results)
```

---

## 🔧 The Analysis Framework

### Step 1: Identify Operations

Count **key operations** not total lines:
```java
for (int i = 0; i < n; i++) {           // n iterations
    for (int j = 0; j < n; j++) {       // n iterations each
        arr[i][j] = i + j;              // 1 operation
    }
}
// Total: n × n × 1 = n²
```

---

### Step 2: Identify Loops

```
Single loop: O(n)
Nested loops: O(n²)
Three nested: O(n³)

Loop with division:
  for (int i = 1; i < n; i *= 2)  → O(log n)
  
Loop with division in nested:
  for each n items, do log n work → O(n log n)
```

---

### Step 3: Identify Data Structures

```
Array access arr[i]: O(1)
HashMap get/put: O(1) average
TreeMap get/put: O(log n)
List remove(0): O(n)  ← Expensive!
Queue offer/poll: O(1)
Stack push/pop: O(1)
```

---

### Step 4: Identify Recursive Calls

**Master Theorem:**
```
T(n) = a×T(n/b) + f(n)

If f(n) = O(n^d):
  - If a > b^d: O(n^log_b(a))
  - If a = b^d: O(n^d × log n)
  - If a < b^d: O(n^d)

Example: Binary Search
  T(n) = 1×T(n/2) + O(1)
  a=1, b=2, d=0, b^d=1
  a=b^d → O(log n)
```

---

## 📚 Analysis Patterns

### Pattern 1: Nested Loops

```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        doSomething();  // O(1)
    }
}
// Analysis: n × n × O(1) = O(n²)
```

---

### Pattern 2: Loop with Early Exit

```java
for (int i = 0; i < n; i++) {
    if (found) break;  // Early exit
    doSomething();
}
// Best case: O(1)
// Worst case: O(n)
// Average: depends on probability
```

---

### Pattern 3: Divide & Conquer

```java
// Example: Merge Sort
void mergeSort(int[] arr, int l, int r) {
    if (l >= r) return;  // O(1)
    
    int mid = (l + r) / 2;
    mergeSort(arr, l, mid);      // T(n/2)
    mergeSort(arr, mid+1, r);    // T(n/2)
    merge(arr, l, mid, r);       // O(n) merge
}

// T(n) = 2×T(n/2) + O(n)
// a=2, b=2, d=1, b^d=2
// a=b^d → O(n log n)
```

---

### Pattern 4: Hash Map

```java
Map<Character, Integer> map = new HashMap<>();

for (char c : s.toCharArray()) {
    map.put(c, map.getOrDefault(c, 0) + 1);  // O(1) amortized
}
// Total: O(n)

// Space: O(k) where k = number of unique chars
```

---

### Pattern 5: Sorting

```
Merge Sort:     O(n log n) time, O(n) space
Quick Sort:     O(n log n) average, O(n²) worst, O(log n) space
Heap Sort:      O(n log n) time, O(1) space
Counting Sort:  O(n + k) where k = range
Radix Sort:     O(d×n) where d = digits
```

---

## 💡 Quick Analysis Examples

### Example 1: Two Sum (Brute Force)

```java
for (int i = 0; i < n; i++) {
    for (int j = i+1; j < n; j++) {
        if (arr[i] + arr[j] == target) {
            return true;
        }
    }
}

Time: O(n²)   - nested loops
Space: O(1)   - only pointers
```

---

### Example 2: Two Sum (Hash Map)

```java
Map<Integer, Integer> seen = new HashMap<>();

for (int num : nums) {
    int complement = target - num;
    if (seen.containsKey(complement)) {
        return true;
    }
    seen.put(num, 1);
}

Time: O(n)   - single loop, O(1) map operations
Space: O(n)  - store all elements in map
```

---

### Example 3: Binary Search

```java
int left = 0, right = n - 1;

while (left <= right) {
    int mid = left + (right - left) / 2;
    if (arr[mid] == target) return mid;
    else if (arr[mid] < target) left = mid + 1;
    else right = mid - 1;
}

Time: O(log n)  - halve range each iteration
Space: O(1)     - only pointers, no extra data structure
```

---

### Example 4: Merge Sort

```java
void mergeSort(int[] arr, int l, int r) {
    if (l >= r) return;
    
    int mid = (l + r) / 2;
    mergeSort(arr, l, mid);         // T(n/2)
    mergeSort(arr, mid+1, r);       // T(n/2)
    merge(arr, l, mid, r);          // O(n)
}

Time: O(n log n)  - T(n) = 2T(n/2) + O(n)
Space: O(n)       - temporary merge array
```

---

### Example 5: Quick Sort

```java
void quickSort(int[] arr, int l, int r) {
    if (l >= r) return;
    
    int pivot = partition(arr, l, r);  // O(n)
    quickSort(arr, l, pivot-1);        // T(k)
    quickSort(arr, pivot+1, r);        // T(n-k-1)
}

Time: O(n log n) average, O(n²) worst
      (depends on pivot choice)
Space: O(log n) average (recursion depth)
```

---

## 🎯 Common Time Complexities by Algorithm

| Algorithm | Time | Space | When |
|---|---|---|---|
| Linear Search | O(n) | O(1) | Unsorted, any element |
| Binary Search | O(log n) | O(1) | Sorted array |
| Bubble Sort | O(n²) | O(1) | Small arrays only |
| Merge Sort | O(n log n) | O(n) | Need stability |
| Quick Sort | O(n log n) avg | O(log n) | General purpose |
| Heap Sort | O(n log n) | O(1) | In-place |
| DFS | O(V+E) | O(V) | All vertices/edges |
| BFS | O(V+E) | O(V) | Level-order |
| Dijkstra | O((V+E)log V) | O(V) | Shortest path |
| DP | O(n²) typical | O(n) typical | Overlapping subproblems |

---

## 📋 Interview Cheat Sheet

**What to Say:**
- "Time complexity is O(...) because..."
- "The ... loop runs ... times"
- "Space is O(...) for the ..."
- "This can be optimized to O(...) by..."

**What NOT to Say:**
- ❌ "It's O(n)" without explanation
- ❌ "Depends on the input" (vague!)
- ❌ "Pretty fast" (not technical!)

**Always Analyze:**
```
□ Best case
□ Worst case  
□ Average case
□ Space complexity
□ Can I optimize?
```

---

## ⚠️ Common Pitfalls

### Pitfall 1: Forgetting Hash Map is O(1) Average

```java
❌ WRONG:
"Hash map has O(log n) complexity"

✅ CORRECT:
"Hash map get/put is O(1) amortized, O(n) worst case with hash collisions"
```

---

### Pitfall 2: Confusing Nested vs Sequential Loops

```java
for (int i = 0; i < n; i++) { }        // O(n)
for (int j = 0; j < m; j++) { }        // O(m)
// Sequential total: O(n + m)

for (int i = 0; i < n; i++) {          // O(n²)
    for (int j = 0; j < n; j++) { }
}
// Nested total: O(n²)
```

---

### Pitfall 3: Forgetting Recursive Space

```java
int result = recurse(n-1);  // Recursion depth!

// Time: O(n) iterations
// Space: O(n) for recursion stack!

✅ Always account for call stack depth
```

---

### Pitfall 4: Overestimating Complexity

```java
❌ WRONG:
for (int i = 0; i < n; i++) {
    binary_search();      // Says O(n log n) total
}
// Wrong! If n operations do O(log n) work each

✅ CORRECT:
Total = n × O(log n) = O(n log n)
```

---

## 🚀 Quick Reference

```
Problem size guides algorithm choice:

n ≤ 10²:      O(n³) OK      (bubble sort, brute force)
n ≤ 10³:      O(n²) OK      (basic DP, nested loops)
n ≤ 10⁴:      O(n²) tight   (optimized brute force)
n ≤ 10⁵:      O(n log n)    (merge sort, binary search)
n ≤ 10⁶:      O(n) needed   (single loop, hash map)
n ≤ 10⁷:      O(n) tight    (need optimal algorithm)
n ≤ 10⁹:      O(log n)      (binary search on answer)
```

---

**Master complexity analysis. It's the language of algorithm thinking.** 🚀
