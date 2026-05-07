# T4-8 — Anagram Detection 异位词检测

> **Core idea:** Two strings are anagrams if they have the same character frequencies. Use `int[26]` for O(1) comparison. For grouping: sort characters as a canonical key. For sliding-window anagrams: maintain a frequency window.
> **核心思想：** 两字符串互为异位词当且仅当字符频率相同。用`int[26]`做O(1)比较。分组时：排序字符作为规范键。滑动窗口异位词：维护频率窗口。
>
> Complexity: O(n) with frequency array; O(n log n) with sort key. See T1-11 for full Frequency Map reference.
> Full reference: `Tier1/T1-11_FrequencyMap.md`

---

## How It Works — Mental Model 直觉理解

Two strings are anagrams if and only if they are identical permutations of the same multiset of characters. An `int[26]` array captures that multiset: increment for each character in the first string, decrement for each character in the second, and any non-zero entry means the two strings differ in that character's frequency. For sliding-window anagram search, you avoid recomputing the full window from scratch by treating it as a running sum: add the incoming character, remove the outgoing character. This makes each window comparison O(1) after an O(26) check or — with the `formed` counter optimization — O(1) altogether. The canonical sorted-key trick for grouping works because sorting produces a canonical form that all anagrams of the same word share, turning a grouping problem into a hash map lookup.

**Key invariant:** For the sliding window, `wCount` always reflects exactly the character frequencies of the current window of size `k`. Adding one character on the right and removing one on the left keeps the window valid without scanning the interior.

**Common mistake:** Using `Arrays.equals` on every step of a variable-size window (LC 76). For a fixed-size window it costs O(26) = O(1), which is fine. For a variable-size window where you need to shrink from the left, use the `formed` counter instead — it turns each character event into an O(1) update while still knowing instantly whether the window is a valid anagram.

---

## Step-by-Step Trace (Sliding Window — Find All Anagrams)

```
s = "cbaebabacd", p = "abc"   pCount = {a:1, b:1, c:1}

i=0 (c): wCount={c:1}         → not equal to pCount
i=1 (b): wCount={c:1,b:1}     → not equal
i=2 (a): wCount={c:1,b:1,a:1} → Arrays.equals(pCount,wCount) ✓ → add index 0
i=3 (e): add e; remove s[0]=c → wCount={b:1,a:1,e:1} → no match
i=4 (b): add b; remove s[1]=b → wCount={a:1,b:1,e:1} → no match
i=5 (a): add a; remove s[2]=a → wCount={a:1,b:1,e:1} → no match
i=6 (b): add b; remove s[3]=e → wCount={a:1,b:2}     → no match
i=7 (a): add a; remove s[4]=b → wCount={a:2,b:1}     → no match
i=8 (c): add c; remove s[5]=a → wCount={a:1,b:1,c:1} ✓ → add index 6
Result: [0, 6]  ✓
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Check if two strings are anagrams | LC 242 |
| Group strings that are anagrams of each other | LC 49 |
| Find all anagram positions in string | LC 438 |
| Check if permutation of pattern exists in string | LC 567 |
| Minimum window containing anagram | LC 76 |

**Signal:** "anagram", "permutation of", "rearrangement", "same characters different order."

---

## Core Templates 核心模板

### Valid anagram check

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) { return false; }  // different lengths → cannot be anagrams
    int[] count = new int[26];
    for (char c : s.toCharArray()) { count[c - 'a']++; }  // tally up characters in s
    for (char c : t.toCharArray()) { count[c - 'a']--; }  // tally down characters in t
    for (int f : count) { if (f != 0) { return false; } }  // any non-zero → frequency mismatch
    return true;
}
```

### Group anagrams (sort as key)

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    for (String s : strs) {
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        map.computeIfAbsent(new String(arr), k -> new ArrayList<>()).add(s);
    }
    return new ArrayList<>(map.values());
}
```

### Find all anagram positions in string (LC 438) — sliding window

```java
public List<Integer> findAnagrams(String s, String p) {
    List<Integer> result = new ArrayList<>();
    if (s.length() < p.length()) { return result; }

    int[] pCount = new int[26], wCount = new int[26];
    for (char c : p.toCharArray()) { pCount[c - 'a']++; }

    int k = p.length();
    for (int i = 0; i < s.length(); i++) {
        wCount[s.charAt(i) - 'a']++;          // slide window right: add new character
        if (i >= k) { wCount[s.charAt(i - k) - 'a']--; }  // slide window left: remove character that fell off
        if (Arrays.equals(pCount, wCount)) { result.add(i - k + 1); }  // window start index = i - k + 1
    }
    return result;
}
```

### Permutation in string (LC 567) — same idea

```java
public boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) { return false; }
    int[] count = new int[26];
    for (char c : s1.toCharArray()) { count[c - 'a']++; }
    for (int i = 0; i < s2.length(); i++) {
        count[s2.charAt(i) - 'a']--;
        if (i >= s1.length()) { count[s2.charAt(i - s1.length()) - 'a']++; }
        if (allZero(count)) { return true; }
    }
    return false;
}

boolean allZero(int[] count) {
    for (int f : count) { if (f != 0) { return false; } }
    return true;
}
```

---

## Optimization: `formed` counter (avoid O(26) comparison each step)

```java
// Track how many characters are fully "balanced" in the window
int formed = 0, required = 0;
// required = number of distinct chars in pattern

// When count[c] goes from -1 → 0: formed++  (window now has enough of c)
// When count[c] goes from 0 → -1: formed--  (window no longer has enough)

// Window is valid when formed == required
```

---

## Variants 变形

| Variant | Approach | Example |
|---|---|---|
| Valid anagram | `int[26]`, increment/decrement, check all zero | LC 242 |
| Group anagrams | Sort chars as HashMap key | LC 49 |
| Find all anagram positions | Fixed-window `int[26]`, `Arrays.equals` | LC 438 |
| Permutation in string | Same as LC 438, return bool | LC 567 |
| Minimum window substring | Variable window + `formed` counter | LC 76 |

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `int[26]` beats HashMap | For lowercase only: faster, no boxing, O(26) `Arrays.equals` |
| `Arrays.equals(a, b)` is O(26) = O(1) | Fast window comparison for fixed alphabet |
| `formed` counter avoids O(26) per step | Important for minimum window (LC 76) where window size varies |
| Sorted char key for grouping | `Arrays.sort(arr); new String(arr)` is the canonical group key |
| Unicode / case insensitive | Use `toLowerCase()` first; use HashMap if non-ASCII |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 242 Valid Anagram |
| Medium | LC 49 Group Anagrams |
| Medium | LC 438 Find All Anagrams in a String |
| Medium | LC 567 Permutation in String |
| Hard | LC 76 Minimum Window Substring |

**Order:** 242 → 49 → 567 → 438 → 76

---

## One-line Summary

```
Anagram = same char frequencies; int[26] for O(1) check; sorted chars for grouping key; sliding window for substring anagrams.
异位词 = 字符频率相同；int[26]做O(1)检验；排序字符做分组键；滑动窗口找子串异位词。
```
