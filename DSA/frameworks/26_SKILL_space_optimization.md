# 💾 Space Optimization - Reduce Memory Usage

**Transform O(n) space to O(1) or O(log n)**

---

## Common Techniques

### Technique 1: Rolling Array

Instead of storing all dp values:
```java
// Before: O(n) space
int[] dp = new int[n];

// After: O(1) space
int prev = 0, curr = 0;
for (int i = 0; i < n; i++) {
    int next = prev + curr;
    prev = curr;
    curr = next;
}
```

### Technique 2: In-Place Modification

```java
// Before: O(n) space
int[] result = new int[n];
for (int x : arr) result[i] = transform(x);

// After: O(1) space
for (int i = 0; i < arr.length; i++) {
    arr[i] = transform(arr[i]);  // Modify in-place
}
```

### Technique 3: Pointer Swapping

```java
// Reverse array in-place
int left = 0, right = n-1;
while (left < right) {
    int temp = arr[left];
    arr[left] = arr[right];
    arr[right] = temp;
    left++;
    right--;
}
```

---

## When to Optimize

```
Phase 1: Get working solution (don't optimize yet)
Phase 2: If time allows, optimize space
Phase 3: Only mention in follow-ups
```

---

**Master space optimization for O(1) solutions.** 🚀
