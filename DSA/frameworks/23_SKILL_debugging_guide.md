# ⚠️ Debugging Guide - Fix Wrong Answers Systematically

**Master the art of finding and fixing bugs quickly**

---

## 📍 Why This Matters

### When Needed: **50%+ of your coding time** ⭐⭐⭐⭐⭐

When your solution gives **Wrong Answer (WA)**, you need to:
- Find the bug systematically
- Fix it without breaking everything
- Verify the fix

---

## 🎯 The Debugging Framework

### Symptom 1: Wrong Answer (WA)

```
Your output ≠ Expected output
```

**Systematic approach:**

#### Step 1: Test Simple Cases

```
Test smallest possible inputs:
□ Empty array []
□ Single element [1]
□ All same [1,1,1]
□ Negative numbers [-1, -2]
□ Mixed [−1, 0, 1]
□ Already solved [-5, -1]

Example: "Two Sum"
Input: [] → Should return empty or error
Input: [1] → No pair possible
Input: [1, 2, 3], target=4 → Should find [1,3]
```

---

#### Step 2: Add Debug Prints

```java
// Before:
if (arr[i] == target) {
    return arr[i];
}

// After (with debug):
if (arr[i] == target) {
    System.out.println("Found at index: " + i);
    System.out.println("Value: " + arr[i]);
    return arr[i];
}
```

---

#### Step 3: Trace Execution

Walk through code with actual input:

```
Input: [2, 7, 11, 15], target = 9

i=0, left=0, right=3
  sum = 2 + 15 = 17 > 9 → right--
  
i=0, left=0, right=2
  sum = 2 + 11 = 13 > 9 → right--
  
i=0, left=0, right=1
  sum = 2 + 7 = 9 = 9 → return [0, 1] ✓
```

---

#### Step 4: Identify Pattern

Common patterns:
- ✅ Off-by-one errors
- ✅ Uninitialized variables
- ✅ Wrong comparison operator
- ✅ Missing edge case
- ✅ Logic error

---

### Symptom 2: Time Limit Exceeded (TLE)

```
Your solution is too slow → O(n²) when need O(n)
```

**Diagnosis:**

#### Check 1: Are You In a Loop?

```java
❌ WRONG:
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {     // O(n²)!
        check(i, j);
    }
}

✅ CORRECT:
for (int i = 0; i < n; i++) {        // O(n)
    check(i);
}
```

---

#### Check 2: Are You Sorting?

```java
❌ WRONG:
Arrays.sort(arr);  // O(n log n)
// Then using sorted order

✅ Think:
"Do I actually need sorted order? 
 Can I use a different approach?"
```

---

#### Check 3: Unnecessary Operations in Loop

```java
❌ WRONG:
for (int i = 0; i < n; i++) {
    String str = "";
    for (char c : something) {
        str += c;           // String concatenation O(n) each!
    }
}

✅ CORRECT:
StringBuilder sb = new StringBuilder();
for (int i = 0; i < n; i++) {
    for (char c : something) {
        sb.append(c);       // O(1) amortized
    }
}
```

---

#### Check 4: Exponential Recursion

```java
❌ WRONG:
int fib(int n) {
    return fib(n-1) + fib(n-2);  // 2^n!
}

✅ CORRECT:
int fib(int n) {
    memo[n] = fib(n-1) + fib(n-2);  // Memoize
    return memo[n];
}
```

---

### Symptom 3: Memory Limit Exceeded (MLE)

```
Your solution uses too much memory
```

**Diagnosis:**

#### Check 1: Storing Everything?

```java
❌ WRONG:
List<Integer> allElements = new ArrayList<>();
for (int i = 0; i < n; i++) {
    allElements.add(something);  // O(n) space
}

✅ CORRECT:
Process on-the-fly, don't store all
```

---

#### Check 2: 2D Array When 1D Enough?

```java
❌ WRONG:
int[][] dp = new int[n][m];  // O(n×m) space

✅ CORRECT:
if (only need previous row):
int[] prev = new int[m];  // O(m) space
```

---

#### Check 3: Recursive Call Stack Too Deep

```java
❌ WRONG:
void recurse(int n) {
    recurse(n-1);  // Stack depth O(n), might overflow!
}

✅ CORRECT:
Use iteration or increase stack size
```

---

## 📋 Common Bug Patterns

### Bug 1: Off-by-One Error

```
❌ Symptom: Almost correct answer

❌ Common causes:
for (int i = 0; i < n) → Should be i < n or i <= n-1
array[i+1] when i = n → Array out of bounds
return count - 1 when should be count

Example:
public int countOnes(int n) {
    int count = 0;
    for (int i = 0; i < n; i++) {  // Correct: i < n
        if (arr[i] == 1) count++;
    }
    return count;
}
```

---

### Bug 2: Uninitialized Variable

```
❌ Symptom: Null pointer or weird values

❌ Common causes:
int maxVal;  → Not initialized!
maxVal = Math.max(maxVal, arr[i]);  → Random value!

✅ Fix:
int maxVal = Integer.MIN_VALUE;  // Initialize properly
maxVal = Math.max(maxVal, arr[i]);
```

---

### Bug 3: Wrong Comparison

```
❌ Symptom: Inverted results

❌ Common causes:
if (arr[i] < target)    → Should be > ?
if (result == false)    → Should be != false ?

❌ Example:
for (int i = 0; i <= n) {  // Should be i < n!
    arr[i] = something;
}
```

---

### Bug 4: Missed Edge Case

```
❌ Symptom: Passes some tests, fails others

❌ Common causes:
□ Empty array
□ Single element  
□ All elements same
□ Negative numbers
□ Zero

Example:
public int division(int a, int b) {
    if (b == 0) return Integer.MAX_VALUE;  // Edge case!
    return a / b;
}
```

---

### Bug 5: Wrong Data Structure

```
❌ Symptom: TLE or MLE

❌ Common causes:
ArrayList remove(0) → O(n) each time!
String concatenation in loop → O(n) each time!
HashMap access with custom object → Need hashCode()

✅ Fix:
Use Queue or LinkedList for frequent remove(0)
Use StringBuilder instead of String +
Implement hashCode() and equals()
```

---

### Bug 6: Modifying While Iterating

```
❌ WRONG:
for (Object obj : list) {
    if (condition) {
        list.remove(obj);  // Concurrent modification!
    }
}

✅ CORRECT:
List<Object> toRemove = new ArrayList<>();
for (Object obj : list) {
    if (condition) {
        toRemove.add(obj);
    }
}
list.removeAll(toRemove);

Or use:
Iterator<Object> it = list.iterator();
while (it.hasNext()) {
    if (condition) {
        it.remove();  // Safe removal
    }
}
```

---

### Bug 7: Integer Overflow

```
❌ WRONG:
int product = a * b;  // Can overflow!
int sum = a + b;      // Can overflow!

✅ CORRECT:
long product = (long) a * b;
long sum = (long) a + b;
```

---

## 🎯 Debugging Checklist

### When Wrong Answer:

```
□ Test with empty input
□ Test with single element
□ Test with all same elements
□ Test with negative numbers
□ Add print statements
□ Trace execution by hand
□ Check boundary conditions
□ Check initialization
□ Check comparison operators
□ Check loop conditions
□ Check array access bounds
□ Check null pointers
```

### When TLE:

```
□ What's time complexity?
□ Are there nested loops?
□ Is sorting necessary?
□ Can I use hash map instead?
□ Am I recomputing same thing?
□ Can I use memoization?
□ Is recursion exponential?
□ Can I optimize to O(n log n)?
```

### When MLE:

```
□ What's space complexity?
□ Can I use 1D instead of 2D?
□ Do I need to store everything?
□ Can I process on-the-fly?
□ Is recursion depth too deep?
□ Am I creating too many objects?
```

---

## 🔍 Debugging Tools

### Tool 1: Print Statements

```java
System.out.println("i = " + i + ", value = " + arr[i]);
```

### Tool 2: Assertions

```java
assert arr[i] >= 0 : "Value must be non-negative";
```

### Tool 3: Debugger (IDE)

- Set breakpoints
- Step through code
- Inspect variables
- Watch expressions

---

## 📊 Most Common Bugs (by frequency)

| Bug | Frequency | Fix Time |
|---|---|---|
| Off-by-one | 20% | 2 min |
| Wrong comparison | 15% | 1 min |
| Missed edge case | 20% | 5 min |
| Uninitialized variable | 10% | 1 min |
| TLE (wrong complexity) | 15% | 10 min |
| Array out of bounds | 10% | 2 min |
| Null pointer | 5% | 3 min |
| MLE (too much space) | 3% | 10 min |
| Integer overflow | 2% | 2 min |

---

## 🚀 Practice Debugging

When you get WA:

```
1. Don't immediately re-code
2. First FIND the bug
3. Then fix it
4. Test fix with original failing case
5. Re-test with all edge cases
```

This trains bug-finding skills that will help in interviews!

---

**Master debugging. It's half the battle in coding.** 🚀
