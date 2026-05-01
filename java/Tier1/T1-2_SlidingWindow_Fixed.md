# T1-2 — Sliding Window (Fixed Size) 固定窗口滑动

> **Core idea:** Maintain a window of exactly k elements. Slide by one: add the new right element, remove the old left element — O(1) update instead of recomputing from scratch.
> **核心思想：** 维护恰好 k 个元素的窗口。每次滑动：加入右边新元素，移出左边旧元素，O(1) 更新避免重复计算。
>
> Complexity: O(n) time, O(1) or O(k) space.
> Full reference: `SlidingWindow/description.md` Pattern 1

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| "subarray of length k" | Max sum subarray of size k |
| "every window of size k" | Sliding window maximum |
| "permutation in string" | Anagram / permutation check |
| "average of subarray of size k" | LC 643 |

**Not for:** window size varies by constraint → use T1-3 variable sliding window.

---

## Core Template 核心模板

```java
// Pattern: O(1) slide — add right, remove left
int windowSum = 0;

// Build first window
for (int i = 0; i < k; i++) windowSum += nums[i];
int result = windowSum;

// Slide
for (int i = k; i < nums.length; i++) {
    windowSum += nums[i];        // add incoming right element
    windowSum -= nums[i - k];   // remove outgoing left element
    result = Math.max(result, windowSum);
}
```

**For character/frequency windows (HashMap or int[26]):**

```java
int[] window = new int[26], need = new int[26];
for (char c : t.toCharArray()) need[c - 'a']++;

for (int i = 0; i < s.length(); i++) {
    window[s.charAt(i) - 'a']++;           // add right
    if (i >= k) window[s.charAt(i-k) - 'a']--;  // remove left
    if (i >= k - 1 && Arrays.equals(window, need)) return true;
}
```

---

## Variants 变形

| Variant | State to maintain | Key problem |
|---|---|---|
| Max/min sum of size k | running `sum` | LC 643 |
| Permutation / anagram check | `int[26]` frequency array | LC 567, 438 |
| Max average subarray | running sum → divide by k | LC 643 |
| Subarray with exactly k distinct | at-most-K trick (→ T1-3) | LC 992 |

---

## Key Examples 关键例题

### Maximum Average Subarray I (LC 643)
```java
public double findMaxAverage(int[] nums, int k) {
    double sum = 0;
    for (int i = 0; i < k; i++) sum += nums[i];
    double max = sum;
    for (int i = k; i < nums.length; i++) {
        sum += nums[i] - nums[i - k];
        max = Math.max(max, sum);
    }
    return max / k;
}
```

### Permutation in String (LC 567)
```java
public boolean checkInclusion(String s1, String s2) {
    int[] need = new int[26], win = new int[26];
    for (char c : s1.toCharArray()) need[c - 'a']++;
    int k = s1.length();

    for (int i = 0; i < s2.length(); i++) {
        win[s2.charAt(i) - 'a']++;
        if (i >= k) win[s2.charAt(i - k) - 'a']--;
        if (i >= k - 1 && Arrays.equals(win, need)) return true;
    }
    return false;
}
```

### Find All Anagrams (LC 438)
```java
public List<Integer> findAnagrams(String s, String p) {
    int[] need = new int[26], win = new int[26];
    for (char c : p.toCharArray()) need[c - 'a']++;
    List<Integer> res = new ArrayList<>();

    for (int i = 0; i < s.length(); i++) {
        win[s.charAt(i) - 'a']++;
        if (i >= p.length()) win[s.charAt(i - p.length()) - 'a']--;
        if (i >= p.length() - 1 && Arrays.equals(win, need)) res.add(i - p.length() + 1);
    }
    return res;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| O(1) slide formula | `result += nums[i] - nums[i - k]` — one add, one subtract |
| Use `int[26]` not HashMap | For lowercase letters: `int[26]` is O(1) compare with `Arrays.equals` |
| `Arrays.equals(win, need)` | O(26) = O(1) — don't iterate characters each time |
| First window boundary | Build separately OR handle with `if (i >= k)` guard in loop |
| Index of window start | When result found at index `i` (0-based), window starts at `i - k + 1` |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 643 Maximum Average Subarray I |
| Medium | LC 567 Permutation in String, LC 438 Find All Anagrams |
| Medium | LC 1343 Number of Sub-arrays of Size K, LC 2090 |

**Order:** 643 → 567 → 438

---

## One-line Summary

```
Fixed sliding window = O(1) slide by adding the new right element and removing the old left element.
固定滑动窗口 = 每次加入右边新元素、移出左边旧元素，O(1) 滑动。
```
