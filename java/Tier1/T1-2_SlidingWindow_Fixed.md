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

## How it Works — Mental Model 原理与直觉

Think of the window as a physical frame of width k sliding over the array. At every step, exactly one new element enters from the right and exactly one old element exits from the left. Because the frame size never changes, you can maintain any **additive aggregate** (sum, character frequency) with a single add and a single subtract rather than recomputing the entire window.

The key insight is that consecutive windows overlap in `k-1` elements: window `[i, i+k-1]` and window `[i+1, i+k]` share positions `[i+1, i+k-1]`. Recomputing from scratch wastes that shared work. The slide formula captures only the difference.

**Invariant:** After each iteration `i`, `windowSum` equals the sum of exactly `nums[i-k+1 .. i]` — the window that just ended at index `i`.

---

## Step-by-Step Trace — Max Average Subarray 执行追踪

```
Input: nums=[1,12,-5,-6,50,3], k=4
Build first window: sum = 1+12+(-5)+(-6) = 2,  result = 2
i=4: sum += 50, sum -= nums[0]=1  → sum = 51, result = 51
i=5: sum += 3,  sum -= nums[1]=12 → sum = 42, result = 51
Answer: 51 / 4 = 12.75
```

---

## Core Template 核心模板

```java
// Pattern: O(1) slide — add right, remove left
int windowSum = 0;

// Build first window [0, k-1] explicitly to avoid the "if i >= k" branch inside the loop
for (int i = 0; i < k; i++) { windowSum += nums[i]; }
int result = windowSum;

// Slide: window moves from [1,k] to [n-k, n-1]
for (int i = k; i < nums.length; i++) {
    windowSum += nums[i];        // add incoming right element (new right boundary = i)
    windowSum -= nums[i - k];   // remove outgoing left element (left boundary was i-k)
    result = Math.max(result, windowSum);
}
```

**For character/frequency windows (HashMap or int[26]):**

```java
int[] window = new int[26], need = new int[26];
for (char c : t.toCharArray()) { need[c - 'a']++; }

for (int i = 0; i < s.length(); i++) {
    window[s.charAt(i) - 'a']++;           // add right
    if (i >= k) { window[s.charAt(i-k) - 'a']--; }  // remove left
    if (i >= k - 1 && Arrays.equals(window, need)) { return true; }
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
    for (int i = 0; i < k; i++) { sum += nums[i]; }
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
    for (char c : s1.toCharArray()) { need[c - 'a']++; }
    int k = s1.length();

    for (int i = 0; i < s2.length(); i++) {
        win[s2.charAt(i) - 'a']++;
        if (i >= k) { win[s2.charAt(i - k) - 'a']--; }
        if (i >= k - 1 && Arrays.equals(win, need)) { return true; }
    }
    return false;
}
```

### Find All Anagrams (LC 438)
```java
public List<Integer> findAnagrams(String s, String p) {
    int[] need = new int[26], win = new int[26];
    for (char c : p.toCharArray()) { need[c - 'a']++; }
    List<Integer> res = new ArrayList<>();

    for (int i = 0; i < s.length(); i++) {
        win[s.charAt(i) - 'a']++;
        if (i >= p.length()) { win[s.charAt(i - p.length()) - 'a']--; }
        if (i >= p.length() - 1 && Arrays.equals(win, need)) { res.add(i - p.length() + 1); }
    }
    return res;
}
```

---

## Common Mistake / Gotcha 常见错误

**Off-by-one in window-start reporting:** When an anagram is found at right-index `i` (0-based), the window's left index is `i - k + 1`, not `i - k`. Returning `i - k` shifts every answer one position to the left. Verify with a single-character pattern: `p="a"`, `s="a"`, k=1, i=0 → start = 0 - 1 + 1 = 0. Correct.

**`Arrays.equals` on the wrong moment:** Checking `Arrays.equals(win, need)` before the window is fully built (i.e., before `i >= k - 1`) will produce false positives on partial windows. Always guard with `if (i >= k - 1)`.

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| O(1) slide formula | `windowSum += nums[i] - nums[i - k]` — one add, one subtract |
| Use `int[26]` not HashMap | For lowercase letters: `int[26]` is O(1) compare with `Arrays.equals` |
| `Arrays.equals(win, need)` | O(26) = O(1) — don't iterate characters each time |
| First window boundary | Build separately OR handle with `if (i >= k)` guard in loop |
| Index of window start | When result found at right-index `i` (0-based), window starts at `i - k + 1` |

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
