# T1-11 — Frequency Map 频率哈希表

> **Core idea:** Use a HashMap (or int[26] / int[128]) to count occurrences of elements. Transforms "do these two things match?" problems into O(1) lookups instead of O(n) scans.
> **核心思想：** 用HashMap（或int[26]/int[128]）统计元素出现次数，把"这两个东西匹配吗？"问题转化为 O(1) 查找，而非 O(n) 扫描。
>
> Complexity: O(n) build, O(1) query. Replaces O(n²) brute-force matching.

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Anagram / permutation check | LC 242, 438, 567 |
| Character count / balance | LC 383, 387 |
| First non-repeating character | LC 387 |
| Group by signature | LC 49 Group Anagrams |
| Two Sum / complement lookup | LC 1 |
| Sliding window with frequency constraint | LC 3, 76, 340 |
| Count elements meeting threshold | LC 347 Top K Frequent |

---

## How it Works — Mental Model 原理与直觉

A frequency map trades space for time: instead of scanning one string to check if each character appears in another string (O(n·m)), you count occurrences of every element into a table in one pass (O(n)), then look up any element in O(1). The core idea is that two collections are "equivalent by frequency" (anagram, permutation, etc.) if and only if their frequency maps are identical.

The choice of data structure matters: `int[26]` is a perfect hash for lowercase ASCII — the character index is the hash, with zero collisions. `HashMap<Character, Integer>` handles any alphabet but carries boxing overhead and hash computation cost. Use `int[26]` when you can; use `HashMap` when the key space is large or non-character.

**Invariant (anagram check):** After incrementing all counts for `s1` and decrementing all counts for `s2`, the frequency array is all-zero if and only if the two strings are anagrams. Any non-zero entry reveals an imbalance.

---

## Step-by-Step Trace — Valid Anagram (LC 242) 执行追踪

```
s="anagram", t="nagaram"
After counting s: a=3, n=1, g=1, r=1, m=1
Decrement with t: a-=3→0, n-=1→0, g-=1→0, a already 0, r-=1→0, a already 0, m-=1→0
All counts zero → true  ✓
```

---

## Core Templates 核心模板

### int[26] for lowercase letters (fastest) 小写字母用数组

```java
int[] freq = new int[26];   // index 0='a', index 25='z'; no hashing needed

// Count characters
for (char c : s.toCharArray()) { freq[c - 'a']++; }   // c-'a' maps char to [0,25]

// Check all zero — used after decrement pass to verify anagram
boolean allZero = true;
for (int f : freq) { if (f != 0) { allZero = false; break; } }

// Compare two strings — O(26) = O(1)
int[] a = new int[26], b = new int[26];
for (char c : s1.toCharArray()) { a[c - 'a']++; }
for (char c : s2.toCharArray()) { b[c - 'a']++; }
boolean anagram = Arrays.equals(a, b);   // faster than looping manually
```

### HashMap for general elements

```java
Map<Integer, Integer> freq = new HashMap<>();
for (int num : nums) { freq.merge(num, 1, Integer::sum); }  // merge: put if absent, else add
int count = freq.getOrDefault(target, 0);   // never throws NPE; returns 0 if key absent
```

### Frequency of frequencies (nested counting)

```java
Map<String, List<String>> groups = new HashMap<>();
for (String word : words) {
    char[] arr = word.toCharArray();
    Arrays.sort(arr);
    String key = new String(arr);              // canonical form: all anagrams share the same sorted key
    groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);   // create list on first encounter
}
```

---

## Variants 变形

| Problem | Key idea | Example |
|---|---|---|
| Valid anagram | `int[26]` count s1, decrement with s2, check all zero | LC 242 |
| Group anagrams | Sort chars as key → group by key | LC 49 |
| First unique char | Count then scan for count==1 | LC 387 |
| Two Sum | `map[complement]` lookup | LC 1 |
| Ransom note | Count magazine, check ransomNote | LC 383 |
| Find duplicate | `HashSet.add()` returns false on duplicate | LC 217 |
| Sliding window max distinct | freq map + distinct count | LC 340 |
| Top K frequent | freq map + min-heap of size k | LC 347 |

---

## Key Examples 关键例题

### Valid Anagram (LC 242)
```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) { return false; }
    int[] count = new int[26];
    for (char c : s.toCharArray()) { count[c - 'a']++; }
    for (char c : t.toCharArray()) { count[c - 'a']--; }
    for (int f : count) { if (f != 0) { return false; } }
    return true;
}
```

### Group Anagrams (LC 49)
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

### Two Sum (LC 1)
```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();  // value → index
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) { return new int[]{map.get(complement), i}; }
        map.put(nums[i], i);
    }
    return new int[]{};
}
```

### Top K Frequent Elements (LC 347)
```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) { freq.merge(n, 1, Integer::sum); }

    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
        minHeap.offer(new int[]{e.getKey(), e.getValue()});
        if (minHeap.size() > k) { minHeap.poll(); }
    }
    return minHeap.stream().mapToInt(a -> a[0]).toArray();
}
```

### Longest Substring with At Most K Distinct (combining freq map + sliding window)
```java
public int lengthOfLongestSubstringKDistinct(String s, int k) {
    Map<Character, Integer> freq = new HashMap<>();
    int left = 0, max = 0;
    for (int right = 0; right < s.length(); right++) {
        freq.merge(s.charAt(right), 1, Integer::sum);
        while (freq.size() > k) {
            char lc = s.charAt(left++);
            if (freq.merge(lc, -1, Integer::sum) == 0) { freq.remove(lc); }
        }
        max = Math.max(max, right - left + 1);
    }
    return max;
}
```

---

## Key Java APIs 常用Java API

```java
// HashMap
map.getOrDefault(key, 0)          // safe get with default
map.merge(key, 1, Integer::sum)   // increment count (idiomatic)
map.put(key, map.getOrDefault(key, 0) + 1)  // equivalent
map.computeIfAbsent(key, k -> new ArrayList<>()).add(val)

// int[26] comparison
Arrays.equals(a, b)               // O(26) = O(1) — fast anagram check

// Frequency of max
Collections.frequency(list, elem) // count in list
```

---

## Common Mistake / Gotcha 常见错误

**Two Sum: insert before lookup:** If you insert `nums[i]` into the map before checking for its complement, you risk using the same element twice (e.g., `nums=[3,3], target=6`: inserting 3 at i=0 then finding complement=3 at i=0 gives `{0,0}` — wrong). Always check for the complement first, then insert.

**Not removing zero-count entries in sliding window:** When using `freq.size()` to count distinct elements in a window, leaving zero-count entries in the map inflates `freq.size()`. After decrementing, always check: `if (freq.merge(c, -1, Integer::sum) == 0) freq.remove(c)`.

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `int[26]` beats HashMap | For lowercase letters: faster, no boxing, O(1) `Arrays.equals` |
| `map.merge(k, 1, Integer::sum)` | Clean one-liner for frequency counting |
| Remove key when count = 0 | In sliding window: `if (freq.merge(c, -1, Integer::sum) == 0) freq.remove(c)` to track distinct count via `freq.size()` |
| Sorted chars as anagram key | `Arrays.sort(arr); new String(arr)` gives canonical group key |
| Lookup before insert (Two Sum) | Check complement EXISTS before inserting current element — not after |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 242 Valid Anagram, LC 1 Two Sum, LC 383 Ransom Note |
| Easy | LC 387 First Unique Character, LC 217 Contains Duplicate |
| Medium | LC 49 Group Anagrams |
| Medium | LC 347 Top K Frequent Elements |
| Medium | LC 438 Find All Anagrams, LC 567 Permutation in String |

**Order:** 242 → 1 → 49 → 347 → 217 → 387 → 438

---

## One-line Summary

```
Frequency map = count elements in O(n); use int[26] for chars, HashMap for general; enables O(1) matching instead of O(n) scan.
频率哈希表 = O(n) 统计元素；字符用int[26]，通用用HashMap；把 O(n) 扫描变为 O(1) 查找。
```
