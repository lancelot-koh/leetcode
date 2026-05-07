# Sliding Window 滑动窗口全模板（5大模式）

---

## 模式1：固定窗口（Fixed Window）

**特征**：窗口大小固定 = `k`

**关键词**：`size = k`、`average / sum / count`

**模板**

```java
int window = 0;

// 1. 初始化前 k 个
for (int i = 0; i < k; i++) {
    window += nums[i];
}

int result = window;

// 2. 滑动窗口
for (int right = k; right < nums.length; right++) {
    window += nums[right];
    window -= nums[right - k];

    result = Math.max(result, window);
}
```

**代表题**
- Max Average Subarray
- Sliding Window Sum

---

## 模式2：可变窗口（Longest / Shortest）

**特征**：求最长 / 最短子串或子数组

**模板（Longest）**

```java
int left = 0;

for (int right = 0; right < n; right++) {
    // expand: add nums[right] to window

    while (invalid) {
        // shrink: remove nums[left] from window
        left++;
    }

    result = Math.max(result, right - left + 1);
}
```

**模板（Shortest）**

```java
int left = 0;

for (int right = 0; right < n; right++) {
    // expand: add nums[right] to window

    while (valid) {
        result = Math.min(result, right - left + 1);
        // shrink: remove nums[left] from window
        left++;
    }
}
```

**代表题**
- Longest Substring Without Repeating Characters
- Minimum Window Substring

---

## 模式3：固定窗口 + 频率（Permutation / Anagram）

**特征**
- 窗口大小固定
- 需要匹配字符频率

**模板**

```java
int[] count = new int[26];
int required = p.length();

for (char c : p.toCharArray()) {
    count[c - 'a']++;
}

int left = 0;

for (int right = 0; right < s.length(); right++) {
    char rc = s.charAt(right);
    if (count[rc - 'a'] > 0) {
        required--;
    }
    count[rc - 'a']--;

    if (right - left + 1 == p.length()) {
        if (required == 0) {
            // found a match at left
        }

        char lc = s.charAt(left);
        if (count[lc - 'a'] >= 0) {
            required++;
        }
        count[lc - 'a']++;
        left++;
    }
}
```

**代表题**
- Find All Anagrams in a String
- Permutation in String

---

## 模式4：AtMost Trick（Exactly K）

**特征**：`count + exactly K`

**核心公式**

```
exactly(K) = atMost(K) - atMost(K - 1)
```

**模板**

```java
int atMost(int[] nums, int k) {
    int left = 0, count = 0;

    for (int right = 0; right < nums.length; right++) {
        // expand: add nums[right] to window

        while (invalid) {
            // shrink: remove nums[left] from window
            left++;
        }

        count += right - left + 1;
    }

    return count;
}
```

**调用方式**

```java
return atMost(nums, k) - atMost(nums, k - 1);
```

**代表题**
- Subarrays with K Distinct Integers
- Binary Subarrays With Sum
- Count Number of Nice Subarrays

---

## 模式5：单调队列（Sliding Window Max / Min）

**特征**：求滑动窗口内的最大值 / 最小值

**模板**

```java
Deque<Integer> dq = new ArrayDeque<>();
int[] result = new int[n - k + 1];

for (int i = 0; i < n; i++) {
    // remove indices outside window
    if (!dq.isEmpty() && dq.peekFirst() == i - k) {
        dq.pollFirst();
    }

    // maintain decreasing monotonic order (for max)
    while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
        dq.pollLast();
    }

    dq.offerLast(i);

    if (i >= k - 1) {
        result[i - k + 1] = nums[dq.peekFirst()];
    }
}
```

**代表题**
- Sliding Window Maximum
- LC 1438（Longest Subarray with Limit — 双队列）

---

## 一张总图

| # | 模式 | 关键词 | 核心操作 |
|---|------|--------|----------|
| 1 | 固定窗口 | `size = k`, sum/avg | 进一个、出一个 |
| 2 | 可变窗口 | longest / shortest | expand + shrink |
| 3 | 固定窗口 + 频率 | anagram, permutation | 频率计数 + required |
| 4 | AtMost Trick | exactly K, count | `atMost(K) - atMost(K-1)` |
| 5 | 单调队列 | max / min in window | deque 维护单调性 |

---

## 一眼判断法（面试必用）

```
window size 固定？          → YES → 模式1 / 模式3
longest / shortest？        → YES → 模式2
count + exactly K？         → YES → 模式4
max / min in window？       → YES → 模式5
```

---

> **最重要一句话：** 先分类，再写代码，不要边想边写
