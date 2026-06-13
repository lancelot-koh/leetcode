# 📦 Array/Subarray - The Contiguous Element Pattern

**Master prefix sums, subarrays, and element relationships**

---

## 📍 Why This Matters

### Interview Frequency: **15% of all problems** ⭐⭐⭐⭐⭐

### The Insight
Many array problems reduce to **finding subarrays with properties**.

```
Array: [1, 2, 3, 4, 5]
Subarray: [2, 3, 4]   (contiguous)
Subsequence: [1, 3, 5] (not contiguous)
```

---

## 🎯 The Core Concept

### Types of Problems

| Type | Goal | Technique |
|---|---|---|
| **Prefix Sum** | Query range sum O(1) | Precompute prefix |
| **Max Subarray** | Longest/max sum | Kadane's algorithm |
| **Two Sum** | Find pair with sum | Hash map |
| **K Element** | Best K consecutive | Sliding window |
| **Partition** | Split array | Two pointer |

---

## 🔧 The 6-Step Array/Subarray Framework

### Step 1: Identify Subarray Property

What makes a subarray valid?
```
- Sum equals target?
- Sum ≤ K?
- All unique elements?
- Product > threshold?
```

---

### Step 2: Choose Technique

```
If need: Query range sum → Prefix sum
If need: Max/min of subarray → Kadane / Prefix
If need: K consecutive elements → Sliding window
If need: Pairs with property → Two pointer or hash map
```

---

### Step 3-6: Implement Pattern

---

## 📚 Code Templates

### Template 1: Prefix Sum (Range Queries)

```java
int[] prefix = new int[n + 1];
for (int i = 0; i < n; i++) {
    prefix[i + 1] = prefix[i] + arr[i];
}

// Query sum from index l to r: O(1)
int rangeSum = prefix[r + 1] - prefix[l];
```

---

### Template 2: Kadane's Algorithm (Max Subarray)

```java
public int maxSubArray(int[] nums) {
    int maxSoFar = nums[0];
    int maxHere = nums[0];
    
    for (int i = 1; i < nums.length; i++) {
        maxHere = Math.max(nums[i], maxHere + nums[i]);
        maxSoFar = Math.max(maxSoFar, maxHere);
    }
    
    return maxSoFar;
}
```

**Idea:** At each position, decide: start new subarray or extend previous?

---

### Template 3: Subarray with Sum = Target

```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0, 1);
    
    int count = 0;
    int prefixSum = 0;
    
    for (int num : nums) {
        prefixSum += num;
        
        // How many prefix sums = prefixSum - k?
        if (prefixCount.containsKey(prefixSum - k)) {
            count += prefixCount.get(prefixSum - k);
        }
        
        prefixCount.put(prefixSum, prefixCount.getOrDefault(prefixSum, 0) + 1);
    }
    
    return count;
}
```

**Idea:** If prefixSum[i] - prefixSum[j] = k, then sum(j+1...i) = k

---

## 💡 Quick Examples

### Example 1: Maximum Subarray (LC53)

```java
public int maxSubArray(int[] nums) {
    int max = nums[0];
    int current = nums[0];
    
    for (int i = 1; i < nums.length; i++) {
        current = Math.max(nums[i], current + nums[i]);
        max = Math.max(max, current);
    }
    
    return max;
}
```

**Trace:**
```
nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]

i=0: current=-2, max=-2
i=1: current=max(1,-2+1)=1, max=1
i=2: current=max(-3,1-3)=-3, max=1
i=3: current=max(4,-3+4)=4, max=4
i=4: current=max(-1,4-1)=3, max=4
(continue...)

max = 6 ([4,-1,2,1])
```

---

### Example 2: Subarray Sum Equals K (LC560)

```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);
    
    int sum = 0, count = 0;
    
    for (int num : nums) {
        sum += num;
        
        if (map.containsKey(sum - k)) {
            count += map.get(sum - k);
        }
        
        map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    
    return count;
}
```

---

### Example 3: Product of Array Except Self (LC238)

```java
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    
    result[0] = 1;
    for (int i = 1; i < n; i++) {
        result[i] = result[i - 1] * nums[i - 1];
    }
    
    int right = 1;
    for (int i = n - 1; i >= 0; i--) {
        result[i] *= right;
        right *= nums[i];
    }
    
    return result;
}
```

**Idea:** Prefix product from left, suffix product from right

---

## ⚠️ Common Pitfalls

### Pitfall 1: Off-by-One in Prefix

```java
❌ WRONG:
for (int i = 1; i <= n; i++) {
    prefix[i] = nums[i] + prefix[i-1];  // Wrong index!
}

✅ CORRECT:
for (int i = 0; i < n; i++) {
    prefix[i+1] = prefix[i] + nums[i];
}
```

---

### Pitfall 2: Not Handling Negative Numbers (Kadane)

```java
❌ WRONG:
maxSoFar = max + current;  // Doesn't restart with negative

✅ CORRECT:
maxHere = Math.max(current, maxHere + current);  // Can restart
```

---

### Pitfall 3: Forgetting Edge Cases

```
□ Array with all negatives?
□ Array with zeros?
□ Single element?
□ Empty array?
```

---

## 🚀 Practice Plan

**Easy:** LC53, LC560, LC238
**Medium:** LC152, LC209, LC713
**Hard:** LC42, LC407

---

## 📋 Essential Patterns

| Pattern | Use Case |
|---|---|
| **Prefix Sum** | Range query |
| **Kadane** | Max subarray |
| **Left-Right** | Product except self |
| **Hash + Prefix** | Subarray with property |

---

**Master array manipulation. It's 15% of interviews.** 🚀
