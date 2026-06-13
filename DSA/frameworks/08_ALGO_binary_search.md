# 🔍 Binary Search - The Monotonic Property Pattern

**Master the art of searching answer space, not data space**

---

## 📍 Why This Matters

### Interview Frequency: **10% of all problems** ⭐⭐⭐⭐

### The Insight

**Junior Approach:**
```
Problem: Find minimum value where condition is true
Solution: Linear scan through all values
Code: for (int i = 0; i < n; i++)
Result: Works but O(n) when could be O(log n)
```

**Senior Approach:**
```
Problem: Find minimum value where condition is true
Insight: Use binary search on ANSWER space, not data
Code: Define search [low, high], binary search with condition check
Result: O(log n) solution
```

**Key Difference:** Not searching FOR a value. Searching for an ANSWER in a monotonic space.

---

## 🎯 The Core Concept

### What Is Binary Search?

Binary search on **answer space** where there's a **monotonic property**.

```
Problem: Find minimum days to make m bouquets with k flowers

Answer space: [0, 10^9]
Monotonic: If we CAN make bouquets in D days, we CAN in D+1 days
           FFFF TTTT T T T T
                   ↑
                Answer (minimum true value)

Use binary search to find this threshold efficiently.
```

### When Do You Use It?

**Recognize these patterns:**
1. **"Minimize X subject to constraint Y"**
2. **"Find minimum/maximum that satisfies condition"**
3. **"Answer space is monotonic"**
4. **"Brute force is O(n), need O(log n)"**

### Why Does It Work?

**Monotonic Property:**
```
If condition is TRUE at position X, it's TRUE for all positions > X
If condition is FALSE at position X, it's FALSE for all positions < X

FFFF TTTT
    ↑
Binary search finds this boundary
```

**Time Complexity:**
- Linear search: O(n)
- Binary search: O(log n)
- Example: n=10^9 → Only 30 iterations needed!

---

## 🔧 The 6-Step Binary Search Framework

### Step 1: Identify the Monotonic Property

**Ask yourself:**
- What answer space am I searching?
- Is there a monotonic property?
- Can I check if a value works?

**Example: Minimum Speed to Arrive on Time**
```
Answer space: [1, 10^7] miles per hour
Property: If we CAN arrive in time at speed S, we CAN at speed S+1
Check: Can we travel distance D at speed S in time T?
```

---

### Step 2: Define the Search Space

```java
int low = /* minimum possible answer */;
int high = /* maximum possible answer */;
```

**Example:**
```java
int low = 1;              // At least 1 mph
int high = 10_000_000;    // At most 10M mph (safe upper bound)
```

---

### Step 3: Define the Condition Function

Write a function that **checks if a value works:**

```java
private boolean canAchieve(int mid) {
    // Check if answer 'mid' satisfies our condition
    // Return true/false
}
```

**Example: Can we arrive in time at speed S?**
```java
private boolean canArrive(int speed) {
    double time = 0;
    for (int i = 0; i < dist.length - 1; i++) {
        time += Math.ceil((double) dist[i] / speed);
        if (time > hour) return false;
    }
    return time <= hour;
}
```

---

### Step 4: Binary Search on Answer

```java
int answer = low;  // Or high, depending on question

while (low <= high) {
    int mid = low + (high - low) / 2;
    
    if (canAchieve(mid)) {
        answer = mid;  // Record this valid answer
        high = mid - 1; // Try to find smaller (for minimum)
    } else {
        low = mid + 1;  // Need larger (answer not yet valid)
    }
}

return answer;
```

**Two variations:**
```
Finding MINIMUM that works (FFFF TTTT):
if (works(mid)) {
    answer = mid;
    high = mid - 1;  // Try smaller
} else {
    low = mid + 1;   // Need larger
}

Finding MAXIMUM that works (TTTT FFFF):
if (works(mid)) {
    answer = mid;
    low = mid + 1;   // Try larger
} else {
    high = mid - 1;  // Need smaller
}
```

---

### Step 5: Handle Edge Cases

```java
// Check boundaries
if (canAchieve(low)) return low;
if (!canAchieve(high)) return -1;

// Check mid calculation overflow
int mid = low + (high - low) / 2;  // NOT (low + high) / 2
```

---

### Step 6: Verify and Optimize

```java
// Verify answer is correct
assert canAchieve(answer);
assert !canAchieve(answer - 1);  // (for minimum)

// Consider constraints
// Can search space fit in int? Or need long?
```

---

## 📚 Code Templates

### Template 1: Find Minimum That Satisfies Condition

```java
public int findMinimum(int[] input) {
    int low = 0;
    int high = 10_000_000;
    int answer = high;  // If no solution exists
    
    while (low <= high) {
        int mid = low + (high - low) / 2;
        
        if (canSatisfy(mid, input)) {
            answer = mid;      // This works, try smaller
            high = mid - 1;
        } else {
            low = mid + 1;     // Doesn't work, need larger
        }
    }
    
    return answer;
}

private boolean canSatisfy(int value, int[] input) {
    // Check if 'value' satisfies condition
    for (int x : input) {
        if (/* condition not met */) return false;
    }
    return true;
}
```

---

### Template 2: Find Maximum That Satisfies Condition

```java
public int findMaximum(int[] input) {
    int low = 0;
    int high = 10_000_000;
    int answer = -1;  // If no solution exists
    
    while (low <= high) {
        int mid = low + (high - low) / 2;
        
        if (canSatisfy(mid, input)) {
            answer = mid;      // This works, try larger
            low = mid + 1;
        } else {
            high = mid - 1;    // Doesn't work, need smaller
        }
    }
    
    return answer;
}
```

---

### Template 3: Binary Search on Sorted Array (Classic)

```java
public int search(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return -1;
}
```

---

## 💡 Real Problem Walkthroughs

### Example 1: Binary Search (LC704)

**Problem:**
```
Input: nums = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 exists in nums at index 4
```

**Step 1: Identify Pattern**
```
This is CLASSIC binary search on sorted array
```

**Step 2-3: Define Search**
```
Left = 0, Right = n-1
Condition: Check if nums[mid] equals target
```

**Step 4-6: Code**
```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
}
```

**Trace:**
```
nums = [-1,0,3,5,9,12], target = 9

left=0, right=5, mid=2: nums[2]=3 < 9 → left=3
left=3, right=5, mid=4: nums[4]=9 = 9 → return 4

Answer: 4 ✓
```

**Complexity:** O(log n)

---

### Example 2: Minimum Speed to Arrive on Time (LC1870)

**Problem:**
```
Input: dist = [1,3,2], hour = 6
Output: 1
Explanation: At speed 1, time = 1 + 3 + 2 = 6 hours exactly
```

**Step 1: Identify Monotonic Property**
```
Answer space: [1, 10^7] miles per hour
Property: If we CAN arrive in 'hour' hours at speed S,
          we CAN arrive at any speed S' > S

Pattern: FFFF TTTT (can't make it, can make it)
Find minimum speed where condition becomes TRUE
```

**Step 2-3: Define**
```
Low = 1 (minimum speed)
High = 10^7 (safe upper bound)
Condition: Can we complete journey in time?
```

**Step 4-6: Code**
```java
class Solution {
    public int minimumSpeed(int[] dist, int hour) {
        int low = 1;
        int high = 10_000_000;
        int answer = high;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (canArrive(dist, mid, hour)) {
                answer = mid;      // This speed works
                high = mid - 1;    // Try smaller
            } else {
                low = mid + 1;     // Speed too slow
            }
        }
        
        return answer;
    }
    
    private boolean canArrive(int[] dist, int speed, int hour) {
        double time = 0;
        for (int i = 0; i < dist.length - 1; i++) {
            time += Math.ceil((double) dist[i] / speed);
            if (time > hour) return false;
        }
        time += (double) dist[dist.length - 1] / speed;
        return time <= hour;
    }
}
```

**Trace:**
```
dist = [1,3,2], hour = 6

Speed = 1: time = 1 + 3 + 2 = 6 ≤ 6 ✓ Works
Speed = 2: time = 1 + 2 + 1 = 4 ≤ 6 ✓ Works
...

Minimum speed = 1
```

---

### Example 3: Koko Eating Bananas (LC875)

**Problem:**
```
Input: piles = [1,1,1,1,1,1,1,1,1,1], h = 10
Output: 1
Explanation: Need to eat at least 1 banana per hour to finish in 10 hours
```

**Pattern:** Find minimum eating speed

**Solution:**
```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int low = 1;
        int high = 1;
        for (int pile : piles) {
            high = Math.max(high, pile);
        }
        
        int answer = high;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (canFinish(piles, mid, h)) {
                answer = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return answer;
    }
    
    private boolean canFinish(int[] piles, int speed, int h) {
        long hours = 0;
        for (int pile : piles) {
            hours += (pile + speed - 1) / speed;  // Ceiling division
        }
        return hours <= h;
    }
}
```

---

## 🎯 Decision Tree

```
See a problem with "minimize/maximize"?
  │
  ├─ Can I check if a value works? (Binary searchable)
  │  └─ YES → Binary Search ✓
  │  
  ├─ Is answer space monotonic?
  │  └─ YES → Likely Binary Search
  │  
  ├─ Is brute force O(n) but need O(log n)?
  │  └─ YES → Binary Search
  │  
  └─ Examples:
     ├─ Find minimum X where condition is true
     ├─ Find maximum X where condition is true  
     ├─ Find value in sorted array
     └─ Find threshold in monotonic space
```

---

## 📋 Interview Cheat Sheet

**What to Say:**
- "I'll use binary search on the answer space"
- "The answer space is monotonic because..."
- "I'll check if each candidate answer works"
- "This reduces O(n) brute force to O(log n)"

**What NOT to Say:**
- ❌ "I'll just check every value" (shows no optimization thinking)
- ❌ "Binary search only works on sorted arrays" (wrong!)

**Key Terms:**
- Answer Space: Range of possible answers
- Monotonic Property: FFFF TTTT pattern
- Condition Function: Check if value works

---

## ⚠️ Common Pitfalls

### Pitfall 1: Integer Overflow

```java
❌ WRONG:
int mid = (left + right) / 2;  // left + right can overflow!

✅ CORRECT:
int mid = left + (right - left) / 2;
```

---

### Pitfall 2: Wrong Loop Condition

```java
❌ WRONG (for finding minimum):
while (left < right) {  // This might miss the boundary
    ...
}

✅ CORRECT:
while (left <= right) {
    if (works(mid)) {
        answer = mid;
        high = mid - 1;  // Look left for smaller
    } else {
        low = mid + 1;   // Look right
    }
}
```

---

### Pitfall 3: Not Handling Search Space

```java
❌ WRONG:
int high = 100;  // Arbitrary limit, might not cover answer!

✅ CORRECT:
int high = 10_000_000;  // Cover all possible answers
```

---

## 🚀 Practice Plan

### Easy (Classic)
1. **LC704** - Binary Search
2. **LC1281** - Subtract the Product and Sum of Digits of an Integer
3. **LC35** - Search Insert Position

### Medium (Answer Space)
4. **LC875** - Koko Eating Bananas
5. **LC1870** - Minimum Speed to Arrive on Time
6. **LC1011** - Capacity To Ship Packages Within D Days

### Hard (Complex Condition)
7. **LC410** - Split Array Largest Sum
8. **LC878** - Nth Magical Number

---

## 🔗 Cross-References

**Related:**
- **two_pointers.md** - When array already sorted, consider binary search
- **sorting.md** - Might need to sort first
- **complexity_analysis.md** - Understanding O(log n)

---

**Master binary search. Transform O(n) to O(log n) thinking.** 🚀
