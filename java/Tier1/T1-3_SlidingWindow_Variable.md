# T1-3 — Sliding Window (Variable Size) 可变窗口滑动

> **Core idea:** Maintain a dynamic window `[left, right]`. Expand `right` to include elements; shrink `left` to restore validity. Each element enters and leaves at most once → O(n).
> **核心思想：** 维护动态窗口 `[left, right]`。右扩纳入元素，左缩恢复合法性。每个元素最多进出各一次 → O(n)。
>
> Complexity: O(n) time, O(k) space where k = alphabet/constraint size.
> Full reference: `SlidingWindow/description.md` Patterns 2–6

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| "longest substring / subarray" + constraint | LC 3, 424, 340 |
| "shortest subarray / substring" + constraint | LC 76, 209 |
| "at most K distinct / changes" | LC 340, 1004 |
| "exactly K" | LC 992 → use atMost(K) - atMost(K-1) |
| "count valid subarrays" | LC 713 |

**Not for:** array with negative numbers + sum constraint → use Prefix Sum + HashMap.

---

## Core Templates 核心模板

### Longest (shrink when invalid) 最长问题

```java
int left = 0, max = 0;
Map<Character, Integer> freq = new HashMap<>();

for (int right = 0; right < s.length(); right++) {
    freq.merge(s.charAt(right), 1, Integer::sum);  // expand

    while (!isValid(freq))  {                       // shrink when invalid
        char lc = s.charAt(left++);
        if (freq.merge(lc, -1, Integer::sum) == 0) freq.remove(lc);
    }

    max = Math.max(max, right - left + 1);          // update OUTSIDE loop
}
```

### Shortest (shrink when valid) 最短问题

```java
int left = 0, min = Integer.MAX_VALUE;
// ... setup window state ...

for (int right = 0; right < n; right++) {
    addToWindow(nums[right]);

    while (isValid()) {                             // shrink while valid
        min = Math.min(min, right - left + 1);     // update INSIDE loop
        removeFromWindow(nums[left++]);
    }
}
```

### At-most-K → Count all valid subarrays

```java
private int atMost(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    int left = 0, count = 0;
    for (int right = 0; right < nums.length; right++) {
        freq.merge(nums[right], 1, Integer::sum);
        while (freq.size() > k) {
            if (freq.merge(nums[left++], -1, Integer::sum) == 0) freq.remove(nums[left-1]);
        }
        count += right - left + 1;   // all subarrays ending at right
    }
    return count;
}
// exactlyK = atMost(k) - atMost(k-1)
```

---

## Variants & Shrink Rule 变形与收缩规则

| Goal | Shrink when | Update answer |
|---|---|---|
| Longest substring | window **invalid** | after `while` (outside) |
| Shortest substring | window **valid** | inside `while` |
| Count valid subarrays | window **invalid** | `count += right - left + 1` |
| Exactly K | — | `atMost(k) - atMost(k-1)` |

---

## Key Examples 关键例题

### Longest Substring Without Repeating (LC 3)
```java
public int lengthOfLongestSubstring(String s) {
    Set<Character> set = new HashSet<>();
    int left = 0, max = 0;
    for (int right = 0; right < s.length(); right++) {
        while (set.contains(s.charAt(right))) {
            set.remove(s.charAt(left++));
        }
        set.add(s.charAt(right));
        max = Math.max(max, right - left + 1);
    }
    return max;
}
```

### Longest Repeating Char Replacement (LC 424)
```java
public int characterReplacement(String s, int k) {
    int[] freq = new int[26];
    int left = 0, maxFreq = 0, max = 0;
    for (int right = 0; right < s.length(); right++) {
        maxFreq = Math.max(maxFreq, ++freq[s.charAt(right) - 'A']);
        while ((right - left + 1) - maxFreq > k)  // replacements needed > k
            freq[s.charAt(left++) - 'A']--;
        max = Math.max(max, right - left + 1);
    }
    return max;
}
```

### Minimum Window Substring (LC 76)
```java
public String minWindow(String s, String t) {
    Map<Character, Integer> need = new HashMap<>(), win = new HashMap<>();
    for (char c : t.toCharArray()) need.merge(c, 1, Integer::sum);
    int left = 0, formed = 0, required = need.size();
    int minLen = Integer.MAX_VALUE, start = 0;

    for (int right = 0; right < s.length(); right++) {
        char rc = s.charAt(right);
        win.merge(rc, 1, Integer::sum);
        if (need.containsKey(rc) && win.get(rc).equals(need.get(rc))) formed++;

        while (formed == required) {
            if (right - left + 1 < minLen) { minLen = right - left + 1; start = left; }
            char lc = s.charAt(left++);
            if (need.containsKey(lc) && win.merge(lc, -1, Integer::sum) < need.get(lc)) formed--;
        }
    }
    return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
}
```

### Subarrays with K Different Integers (LC 992)
```java
public int subarraysWithKDistinct(int[] nums, int k) {
    return atMost(nums, k) - atMost(nums, k - 1);
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `left` never goes back | Both pointers only move right → O(n) total |
| `formed` counter trick | Track satisfied conditions as a count, not by re-checking the whole map |
| Negative numbers | If array has negatives, sliding window breaks → use prefix sum + HashMap |
| `count += right - left + 1` | Every subarray ending at `right` starting from `[left..right]` is valid |
| Exact-K = atMost(K) - atMost(K-1) | Can't maintain "exactly" directly; decompose into two at-most problems |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 3 Longest Substring Without Repeating |
| Medium | LC 424 Longest Repeating Char Replacement |
| Medium | LC 340 At Most K Distinct Characters |
| Medium | LC 1004 Max Consecutive Ones III |
| Hard | LC 76 Minimum Window Substring |
| Hard | LC 992 Subarrays with K Different Integers |

**Order:** 3 → 424 → 1004 → 340 → 76 → 992

---

## One-line Summary

```
Variable sliding window = expand right, shrink left when invalid (longest) or when valid (shortest).
可变滑动窗口 = 右扩左缩：非法时收缩（最长）或合法时收缩（最短）。
```
