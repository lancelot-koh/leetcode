# 📊 Prefix Sum + Suffix Sum Patterns

**Complete guide to combining prefix and suffix sums for optimization**

---

## 🎯 Core Concepts

### Prefix Sum Reminder
```
prefix[i] = sum of elements from index 0 to i
Query: sum(left, right) = prefix[right+1] - prefix[left]
```

### Suffix Sum
```
suffix[i] = sum of elements from index i to end
Query: sum(left, right) = suffix[left] - suffix[right+1]
```

### Combined Pattern
```
For each position i:
- Access sum from 0 to i-1 via prefix[i]
- Access sum from i+1 to end via suffix[i+1]
- Make decisions based on BOTH sides
```

---

## 💡 Key Use Cases

### **1. PARTITION PROBLEMS**
When you need to find a split point where left side and right side satisfy certain conditions.

#### Example: Partition Labels
**Problem:** Divide string into parts where each letter appears in only one part.

```
Input: "ababcbacaddefegdehijhij"
Output: [9,7,8] (lengths of partitions)
Explanation:
Part 1: "ababcbaca" (a,b,c appear only here)
Part 2: "defegde" (d,e,f,g appear only here)  
Part 3: "hijhij" (h,i,j appear only here)
```

**Key Insight:** For each position, we need:
- Characters seen so far (prefix)
- Last occurrence of any character (suffix information via rightmost index)

When prefix information matches suffix information, we can cut.

#### Code:
```java
class Solution {
    public List<Integer> partitionLabels(String s) {
        // suffix: rightmost index of each character
        int[] rightmost = new int[26];
        for (int i = 0; i < s.length(); i++) {
            rightmost[s.charAt(i) - 'a'] = i;
        }
        
        List<Integer> result = new ArrayList<>();
        int start = 0, end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // Extend partition to the rightmost position of current char
            end = Math.max(end, rightmost[s.charAt(i) - 'a']);
            
            // When we reach the end, partition here
            if (i == end) {
                result.add(end - start + 1);
                start = end + 1;
            }
        }
        
        return result;
    }
}
```

**Time:** O(n) | **Space:** O(26) = O(1)

---

### **2. BALANCE PROBLEMS**
Find a point where two sides are balanced or optimized.

#### Example: Trapping Rain Water
**Problem:** Calculate trapped water after raining on elevation map.

```
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation:
| 
||
||_|
|_|_|_|
Shows how 6 units of water get trapped
```

**Key Insight:** Water trapped at position i = min(maxLeft, maxRight) - height[i]
- Need max height to the LEFT of i
- Need max height to the RIGHT of i

#### Code:
```java
class Solution {
    public int trap(int[] height) {
        int n = height.length;
        
        // prefix[i] = max height from 0 to i
        int[] maxLeft = new int[n];
        maxLeft[0] = height[0];
        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i-1], height[i]);
        }
        
        // suffix[i] = max height from i to end
        int[] maxRight = new int[n];
        maxRight[n-1] = height[n-1];
        for (int i = n-2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i+1], height[i]);
        }
        
        // Calculate trapped water
        int water = 0;
        for (int i = 0; i < n; i++) {
            int level = Math.min(maxLeft[i], maxRight[i]);
            water += Math.max(0, level - height[i]);
        }
        
        return water;
    }
}
```

**Time:** O(n) | **Space:** O(n)

**Optimization (O(1) space):** Use two pointers instead of arrays
```java
class Solution {
    public int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int maxLeft = 0, maxRight = 0;
        int water = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= maxLeft) {
                    maxLeft = height[left];
                } else {
                    water += maxLeft - height[left];
                }
                left++;
            } else {
                if (height[right] >= maxRight) {
                    maxRight = height[right];
                } else {
                    water += maxRight - height[right];
                }
                right--;
            }
        }
        
        return water;
    }
}
```

---

### **3. PRODUCT/DIFFERENCE PROBLEMS**
Find values based on comparisons between left and right.

#### Example: Best Sightseeing Pair
**Problem:** Maximize `height[i] + height[j] + (i - j)` where i < j

```
Input: [8,1,5,2,6]
Output: 11
Explanation: (8 + 2 + (0-3)) = 7, or (8 + 5 + (0-2)) = 11
Index 0 + Index 2 = 8 + 5 + (0-2) = 11 ✓
```

**Key Insight:** Rewrite as `height[i] + i + (height[j] - j)`
- Left part: `height[i] + i` (prefix information)
- Right part: `height[j] - j` (suffix information)

#### Code:
```java
class Solution {
    public int maxScoreSightseeingPair(int[] values) {
        int maxScore = 0;
        int maxLeft = values[0]; // height[i] + i
        
        // For each j, use the best i found so far
        for (int j = 1; j < values.length; j++) {
            maxScore = Math.max(maxScore, maxLeft + values[j] - j);
            maxLeft = Math.max(maxLeft, values[j] + j);
        }
        
        return maxScore;
    }
}
```

**Time:** O(n) | **Space:** O(1)

---

### **4. COMPARISON PROBLEMS**
When you need to compare sums or values from both sides of a point.

#### Example: Left and Right Sum Differences
**Problem:** For each index i, calculate |leftSum[i] - rightSum[i]|

```
Input: nums = [10,4,8,3]
Output: [15,1,11,22]
Explanation:
i=0: leftSum=0, rightSum=15, diff=15
i=1: leftSum=10, rightSum=11, diff=1
i=2: leftSum=14, rightSum=3, diff=11
i=3: leftSum=22, rightSum=0, diff=22
```

#### Code:
```java
class Solution {
    public int[] leftRightDifference(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];
        
        // Calculate total sum (suffix sum for all positions)
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        
        int leftSum = 0;
        for (int i = 0; i < n; i++) {
            int rightSum = total - leftSum - nums[i];
            answer[i] = Math.abs(leftSum - rightSum);
            leftSum += nums[i];
        }
        
        return answer;
    }
}
```

**Time:** O(n) | **Space:** O(1) (not counting output array)

---

### **5. OPTIMIZATION WITH CONSTRAINTS**
Find the best position to split or place something.

#### Example: Best Time to Buy and Sell Stock (with analysis)
**Problem:** Find pair (buy, sell) where buy < sell and profit is maximum.

```
Input: prices = [7,1,5,3,6,4]
Output: 5 (buy at 1, sell at 6)
```

**Insight:** For each sell position j:
- Need minimum price to the LEFT (buy price)
- Sell at current position j
- Calculate profit

#### Code:
```java
class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE; // prefix: min so far
        int maxProfit = 0;
        
        for (int price : prices) {
            maxProfit = Math.max(maxProfit, price - minPrice);
            minPrice = Math.min(minPrice, price);
        }
        
        return maxProfit;
    }
}
```

**Time:** O(n) | **Space:** O(1)

---

## 🔄 Prefix + Suffix Pattern Template

### General Pattern:
```java
class Solution {
    public void solve(int[] arr) {
        int n = arr.length;
        
        // Step 1: Build prefix information
        int[] prefix = new int[n];
        prefix[0] = arr[0]; // or some initial value
        for (int i = 1; i < n; i++) {
            prefix[i] = combine(prefix[i-1], arr[i]); // sum, max, min, etc.
        }
        
        // Step 2: Build suffix information
        int[] suffix = new int[n];
        suffix[n-1] = arr[n-1]; // or some initial value
        for (int i = n-2; i >= 0; i--) {
            suffix[i] = combine(suffix[i+1], arr[i]); // sum, max, min, etc.
        }
        
        // Step 3: Use both for each position
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = process(prefix[i], suffix[i], arr[i]);
        }
        
        return result;
    }
}
```

---

## 📋 Problem Classification

| Problem Type | Use Case | Key Insight |
|--------------|----------|-------------|
| **Partition** | LC763 (Labels) | Split where left/right are valid |
| **Balance** | LC42 (Trapping), LC1094 (Car) | Compare left vs right at each position |
| **Product/Score** | LC1014 (Best Sightseeing) | Maximize/minimize left + right expression |
| **Difference** | LC2536 (Left Right Diff) | |left - right| or similar |
| **Optimization** | LC121 (Stock), LC2096 (Median) | Best position to split or make decision |

---

## 🎯 When to Use Prefix+Suffix vs Other Patterns

| Pattern | When to Use | Example |
|---------|------------|---------|
| **Prefix Only** | Single query or cumulative | Range sum queries, subarray sum = k |
| **Suffix Only** | Process from right to left | Next greater element, right view |
| **Prefix + Suffix** | Need both sides of each point | Trapping rain, partition, balance |
| **Sliding Window** | Fixed/variable window | Longest substring, max subarray |
| **Two Pointers** | Sorted array or convergence | Two sum, container with water |

---

## 💪 Practice Problems

### **Easy:**
- LC2536 - Left and Right Sum Differences
- LC2390 - Removing Stars From a String (use prefix stack idea)

### **Medium:**
- LC763 - Partition Labels
- LC1014 - Best Sightseeing Pair
- LC42 - Trapping Rain Water
- LC2096 - Step-By-Step Directions From a Binary Tree Node to Another

### **Hard:**
- LC2306 - Naming a Company (prefix/suffix with constraints)
- LC2617 - Minimum Number of Visited Cells in a Grid (prefix max)

---

## 🚀 Key Takeaway

**Prefix + Suffix is powerful when:**
1. ✅ You need information from BOTH sides of a point
2. ✅ You want to avoid nested loops (O(n²) → O(n))
3. ✅ The problem involves partitioning, balancing, or optimization
4. ✅ You need to combine left and right values

**Think of it as:** "What do I know about everything before me? What do I know about everything after me? How can I use both?"

---

**This pattern is a game-changer for interview problems!** 🎯
