# T4-8 — Anagram Detection 异位词检测

> **Core idea:** Two strings are anagrams if they have the same character frequencies. Use `int[26]` for O(1) comparison. For grouping: sort characters as a canonical key. For sliding-window anagrams: maintain a frequency window.
> **核心思想：** 两字符串互为异位词当且仅当字符频率相同。用`int[26]`做O(1)比较。分组时：排序字符作为规范键。滑动窗口异位词：维护频率窗口。
>
> Complexity: O(n) with frequency array; O(n log n) with sort key. See T1-11 for full Frequency Map reference.
> Full reference: `Tier1/T1-11_FrequencyMap.md`

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
    if (s.length() != t.length()) return false;
    int[] count = new int[26];
    for (char c : s.toCharArray()) count[c - 'a']++;
    for (char c : t.toCharArray()) count[c - 'a']--;
    for (int f : count) if (f != 0) return false;
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
    if (s.length() < p.length()) return result;

    int[] pCount = new int[26], wCount = new int[26];
    for (char c : p.toCharArray()) pCount[c - 'a']++;

    int k = p.length();
    for (int i = 0; i < s.length(); i++) {
        wCount[s.charAt(i) - 'a']++;
        if (i >= k) wCount[s.charAt(i - k) - 'a']--;
        if (Arrays.equals(pCount, wCount)) result.add(i - k + 1);
    }
    return result;
}
```

### Permutation in string (LC 567) — same idea

```java
public boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) return false;
    int[] count = new int[26];
    for (char c : s1.toCharArray()) count[c - 'a']++;
    for (int i = 0; i < s2.length(); i++) {
        count[s2.charAt(i) - 'a']--;
        if (i >= s1.length()) count[s2.charAt(i - s1.length()) - 'a']++;
        if (allZero(count)) return true;
    }
    return false;
}

boolean allZero(int[] count) {
    for (int f : count) if (f != 0) return false;
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
