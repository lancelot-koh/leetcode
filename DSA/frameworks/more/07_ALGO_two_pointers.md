# ↔️ Two Pointers - The Convergent Approach

**Solve from opposite ends elegantly and efficiently**

---

## 📍 Why This Matters

### Interview Frequency: **12% of all problems** ⭐⭐⭐⭐

### The Insight
**Junior Approach:**
```
Problem: Find two numbers that sum to target
Solution: Use hash map for all pairs
Code: O(n) space, nested iterations
Result: Works but not elegant
```

**Senior Approach:**
```
Problem: Find two numbers that sum to target (sorted)
Insight: Use left & right pointers from opposite ends
Code: O(1) space, converge from both sides
Result: Elegant O(n) solution
```

**When to recognize it:**
- "Find pair/triplet/group that satisfies condition"
- "Sorted array"
- "From both ends"
- "Validate something" (like palindrome)

**Key advantage:** O(1) extra space (vs. sliding window's O(k) space)

---

## 🎯 The Core Concept

### What Is Two Pointers?

You maintain **two pointers** (usually left and right) that move **toward each other** or in **opposite directions** to process elements.

```
Two Pointers Converging:
Array: [1, 2, 3, 4, 5, 6, 7]
        L              R

Compare: if nums[L] + nums[R] > target, move R left
         if nums[L] + nums[R] < target, move L right

        L           R
        
        L       R
```

### When Do You Use It?

**Recognize these patterns:**
1. **Sorted array + pair finding** - Two sum, 3sum
2. **Palindrome validation** - Check from both ends
3. **Reverse operation** - Reverse array/string in-place
4. **Container/interval problems** - Find max area
5. **Merge operations** - Merge sorted lists

### Why Does It Work?

**Key insights:**
- ✅ Sorted property allows decision-making
- ✅ If sum too small, left is too small (move left right)
- ✅ If sum too large, right is too large (move right left)
- ✅ Converges to answer in O(n) time
- ✅ No extra space needed (vs. hash map)

---

## 🔧 The 6-Step Two Pointers Framework

### Step 1: Verify Sorted (Usually Required)

For most two-pointer problems on **pairs**:
- Array must be sorted OR
- Naturally monotonic (like strings)

**Example:**
```
Array: [1, 2, 3, 4, 5]  → Sorted ✓
Array: [2, 1, 3]        → Not sorted, need to sort first
```

---

### Step 2: Initialize Pointers

Typical initialization:
```java
int left = 0;
int right = n - 1;
```

Or for moving in same direction:
```java
int slow = 0;
int fast = 1;
```

---

### Step 3: Define Movement Logic

Decide **when** to move left/right:

```
if (condition based on current pair) {
    right--;  // Too large or need smaller values
} else {
    left++;   // Too small or need larger values
}
```

**Example: Two Sum**
```
if (nums[left] + nums[right] > target) {
    right--;  // Sum too large, move right left
} else if (nums[left] + nums[right] < target) {
    left++;   // Sum too small, move left right
} else {
    return [left, right];  // Found it!
}
```

---

### Step 4: Handle Termination

Pointers must eventually meet (or cross):

```java
while (left < right) {
    // Process
    // Move pointers
}
```

---

### Step 5: Collect Results

```
Depending on problem:
- Return the pair itself
- Return indices
- Count solutions
- Build result array
```

---

### Step 6: Verify Edge Cases

```
□ Empty array?
□ Single element?
□ All elements same?
□ No solution exists?
```

---

## 📚 Code Templates

### Template 1: Two Pointer - Convergent (Sorted Array)

```java
public int[] twoSum(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    
    while (left < right) {
        int sum = nums[left] + nums[right];
        
        if (sum == target) {
            return new int[]{left, right};
        } else if (sum < target) {
            left++;      // Too small, move left pointer right
        } else {
            right--;     // Too large, move right pointer left
        }
    }
    
    return new int[]{-1, -1};  // No solution
}
```

**Key points:**
- ✅ Pointers converge from both ends
- ✅ Movement based on sum comparison
- ✅ O(n) time, O(1) space

---

### Template 2: Two Pointer - Same Direction (Fast & Slow)

```java
public boolean hasCycle(ListNode head) {
    if (head == null) return false;
    
    ListNode slow = head;
    ListNode fast = head.next;
    
    while (fast != null && fast.next != null) {
        if (slow == fast) {
            return true;  // Cycle detected
        }
        slow = slow.next;           // Move by 1
        fast = fast.next.next;      // Move by 2
    }
    
    return false;  // No cycle
}
```

**Key points:**
- ✅ Both pointers move in same direction
- ✅ Different speeds (slow=1, fast=2)
- ✅ Used for cycle detection, partition

---

### Template 3: Two Pointer - Partition in-place

```java
public void moveZeroes(int[] nums) {
    int left = 0;  // Position to place next non-zero
    
    for (int right = 0; right < nums.length; right++) {
        if (nums[right] != 0) {
            // Swap
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
        }
    }
}
```

**Key points:**
- ✅ Left tracks "next position to fill"
- ✅ Right scans for valid elements
- ✅ In-place modification

---

## 💡 Real Problem Walkthroughs

### Example 1: Two Sum II - Input Array Sorted (LC167)

**Problem:**
```
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: numbers[1] + numbers[2] = 2 + 7 = 9
(1-indexed: position 1 and 2)
```

**Step 1: Clarify**
```
Q: Is array sorted?
A: Yes, in non-decreasing order

Q: Return indices 0-indexed or 1-indexed?
A: 1-indexed in this problem

Q: Is solution guaranteed to exist?
A: Yes, exactly one solution
```

**Step 2: Recognize Pattern**
```
Pattern: Two pointers converging on sorted array
Reasoning: 
- Array is sorted ✓
- Need to find pair ✓
- Can use left/right from ends ✓
```

**Step 3: Define Movement**
```
If sum too small: increase left (need larger value)
If sum too large: decrease right (need smaller value)
If sum equals: found answer
```

**Step 4-6: Code It**
```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            
            if (sum == target) {
                return new int[]{left + 1, right + 1};  // 1-indexed
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return new int[]{-1, -1};  // Should not reach here
    }
}
```

**Trace:**
```
numbers = [2,7,11,15], target = 9

left=0, right=3: sum=2+15=17 > 9 → right--
left=0, right=2: sum=2+11=13 > 9 → right--
left=0, right=1: sum=2+7=9 = 9 → Found! return [1,2]

Answer: [1, 2]
```

**Complexity:**
- **Time:** O(n)
- **Space:** O(1)

---

### Example 2: Container With Most Water (LC11)

**Problem:**
```
Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: Between indices 1 and 8, area = min(8,7) × (8-1) = 7 × 7 = 49
```

**Pattern Recognition:**
```
Problem: Find maximum area between two lines
Position: Represented by indices
Area formula: min(height[left], height[right]) × (right - left)

Key insight: 
- Start with widest container (left=0, right=n-1)
- Move the SHORTER line inward (can't increase area with longer line)
- Track maximum as we go
```

**Solution:**
```java
class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            // Calculate current area
            int currentArea = Math.min(height[left], height[right]) * (right - left);
            maxArea = Math.max(maxArea, currentArea);
            
            // Move the shorter line (only way to possibly increase area)
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
}
```

**Why This Works:**
```
Start:           [1,8,6,2,5,4,8,3,7]
                  L               R

Area = min(1,7) × 8 = 8

Since height[L]=1 < height[R]=7, move left (no point moving right)
The area can only increase if we find height > 1 on the left

Moving left:    [1,8,6,2,5,4,8,3,7]
                    L           R

Area = min(8,7) × 7 = 49

Now height[L]=8 > height[R]=7, move right

(Continue until pointers meet)
```

**Complexity:**
- **Time:** O(n) - visit each element once
- **Space:** O(1)

---

### Example 3: Valid Palindrome (LC125)

**Problem:**
```
Input: s = "A man, a plan, a canal: Panama"
Output: true
Explanation: "amanaplanacanalpanama" (removing non-alphanumeric)
```

**Pattern:**
```
Compare from both ends
Converge toward center
Skip non-alphanumeric characters
```

**Solution:**
```java
class Solution {
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            // Skip non-alphanumeric on left
            while (left < right && !Character.isAlphaNumeric(s.charAt(left))) {
                left++;
            }
            
            // Skip non-alphanumeric on right
            while (left < right && !Character.isAlphaNumeric(s.charAt(right))) {
                right--;
            }
            
            // Compare (case-insensitive)
            if (Character.toLowerCase(s.charAt(left)) != 
                Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            
            left++;
            right--;
        }
        
        return true;
    }
}
```

**Trace:**
```
s = "A man, a plan, a canal: Panama"

Skip punctuation, compare:
A = A ✓
m = m ✓
a = a ✓
n = n ✓
(all match)

Result: true
```

---

## 🎯 Decision Tree

```
See a problem with arrays/pointers?
  │
  ├─ Looking for pairs/groups?
  │  └─ Is array sorted? → Two Pointers ✓
  │  
  ├─ Palindrome/validation problem?
  │  └─ Yes → Two Pointers (compare from ends) ✓
  │  
  ├─ Cycle detection?
  │  └─ Yes → Two Pointers (slow & fast) ✓
  │  
  ├─ Partition/rearrange in-place?
  │  └─ Yes → Two Pointers (partition) ✓
  │  
  └─ Can solve with left/right pointers?
     └─ Yes → Likely Two Pointers
```

---

## 📋 Interview Cheat Sheet

**What to Say:**
- "I'll use two pointers from opposite ends"
- "Starting position: left=0, right=n-1"
- "If sum too small, move left; if too large, move right"
- "They converge in O(n) time with O(1) space"

**What NOT to Say:**
- ❌ "I'll use a hash set to find the pair"
- ❌ "I'll nested loop through the array"
- ❌ "Requires extra space"

**Key Terms:**
- Converge: Pointers move toward each other
- Partition: Split array into two regions
- Fast & Slow: Different speeds on same direction
- In-place: Modify without extra space

**Variations:**
- **Two Sum (sorted):** Converge from ends
- **Palindrome:** Compare from both ends
- **Reverse:** Swap elements moving toward center
- **Partition:** Left finds large, right finds small

---

## ⚠️ Common Pitfalls

### Pitfall 1: Wrong Movement Logic

```java
❌ WRONG:
if (sum < target) {
    right--;  // Contradicts logic!
}
```

**Why:** If sum too small, we need LARGER value. Right is already large, moving it left makes it smaller!

**Fix:**
```java
✅ CORRECT:
if (sum < target) {
    left++;   // Move left right to get larger value
}
```

---

### Pitfall 2: Forgot Array is Sorted

```java
Problem says "sorted array" but you use hash map
Result: Over-engineered, wastes space
```

**Fix:**
```
Always check: Is array sorted?
If yes: Use two pointers!
If no: Sort first (if feasible) or use different approach
```

---

### Pitfall 3: Off-by-One in Loop Condition

```java
❌ WRONG:
while (left <= right) {  // This includes comparing element to itself!
    // ...
}

For pair problems, this is wrong because you can't use same element twice
```

**Fix:**
```java
✅ CORRECT:
while (left < right) {  // Strict less than
    // ...
}
```

---

### Pitfall 4: Not Handling Duplicates

```java
Problem: "Find unique pairs"
But your solution counts [1,2] twice

Solution: Skip duplicates after finding answer
```

**Fix:**
```java
if (sum == target) {
    result.add(...);
    while (left < right && nums[left] == nums[left+1]) left++;
    while (left < right && nums[right] == nums[right-1]) right--;
    left++;
    right--;
}
```

---

## 🚀 Practice Plan

### Easy (Warm Up)
1. **LC167** - Two Sum II (sorted array)
2. **LC125** - Valid Palindrome
3. **LC977** - Squares of a Sorted Array

### Medium (Build Skill)
4. **LC11** - Container With Most Water
5. **LC15** - 3Sum
6. **LC16** - 3Sum Closest

### Hard (Master)
7. **LC18** - 4Sum
8. **LC259** - 3Sum Smaller

---

## 🔗 Cross-References

**Related Frameworks:**
- **sliding_window.md** - Expanding/shrinking window
- **binary_search.md** - When array is sorted
- **array_subarray.md** - Array manipulation patterns

**Use two pointers when:**
- ✅ Array is sorted
- ✅ Need O(1) space
- ✅ Looking for pairs from opposite ends

**Use sliding window when:**
- ✅ Don't need from opposite ends
- ✅ Need arbitrary window size
- ✅ Element order matters (relative positions)

---

**Master two pointers. It's elegant, efficient, and appears in 12% of interviews.** 🚀
