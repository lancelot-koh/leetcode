# T1-10 — StringBuilder & Palindrome Check StringBuilder与回文检测

> **Core idea:** Use `StringBuilder` for efficient string construction in O(n) instead of repeated `+` concatenation (O(n²)). Palindrome checks use two-pointer comparison in O(n) O(1) space.
> **核心思想：** 用 `StringBuilder` 在 O(n) 内高效构建字符串，避免 `+` 拼接的 O(n²)。回文检测用双指针比较，O(n) 时间 O(1) 空间。
>
> Complexity: StringBuilder append O(1) amortized; Palindrome check O(n) time O(1) space.

---

## When to Use 什么时候用

**StringBuilder:**
| Trigger | Example |
|---|---|
| Build string in a loop | Reverse string, serialize tree |
| Append characters one by one | Backtracking path building |
| Avoid repeated string `+` in loop | Any loop with string concatenation |
| Delete / modify last character | Backtracking undo |

**Palindrome:**
| Trigger | Example |
|---|---|
| Is string a palindrome? | LC 125, 680 |
| Longest palindromic substring | LC 5 |
| Count palindromic substrings | LC 647 |
| Partition into palindromes | LC 131 |

---

## How it Works — Mental Model 原理与直觉

**StringBuilder:** Java `String` is immutable — every `+` creates a new `String` object and copies all previous characters. In a loop of n iterations this produces O(1) + O(2) + … + O(n) = O(n²) total work. `StringBuilder` uses an internal resizable char array with amortized O(1) appends (like ArrayList), collapsing the total to O(n).

**Palindrome (expand around center):** Every palindrome has a center — either a single character (odd length) or a gap between two equal characters (even length). If we expand outward from each of the n + (n-1) = 2n-1 possible centers and track the longest match, we cover all palindromic substrings in O(n²) without any additional space. This beats the naive O(n³) approach of checking every substring.

**Invariant (expand):** At each step of `expand(s, l, r)`, `s[l+1..r-1]` is already confirmed to be a palindrome. The check `s[l] == s[r]` extends that palindrome by one layer on each side.

---

## Step-by-Step Trace — Expand Around Center (LC 5) 执行追踪

```
Input: s = "babad"
Center i=0 ('b'): odd expand → just "b" (len=1)
Center i=1 ('a'): odd expand → l=0,r=2: b==b → l=-1,r=3 (stop) → len=3 "bab"; start=0
Center i=1..2 (gap 'a','b'): even → s[1]≠s[2] → len=0
Center i=2 ('b'): odd expand → l=1,r=3: a==a → l=0,r=4: b==d? No → len=3 "aba"; start=1
...
maxLen=3, both "bab" and "aba" are valid; return s.substring(0,3)="bab"
```

---

## Core Templates 核心模板

### StringBuilder Pattern

```java
// Build string efficiently — O(n) total vs O(n²) with String +
StringBuilder sb = new StringBuilder();
for (char c : chars) {
    sb.append(c);           // O(1) amortized: internal array doubles when full
}
String result = sb.toString();

// Backtracking: add/remove last char
sb.append(c);              // choose — extend current path
backtrack(sb);             // explore — recurse with extended path
sb.deleteCharAt(sb.length() - 1);  // unchoose — undo in O(1) (no shifting)

// Common operations
sb.reverse();              // reverse entire builder
sb.insert(0, c);           // insert at front — O(n), avoid in hot loops
sb.length();               // current length
sb.charAt(i);              // read at index
sb.delete(start, end);     // delete range [start, end)
```

### Palindrome Check (Two Pointers) 双指针回文检测

```java
public boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) { return false; }   // mismatch → not palindrome
        left++; right--;   // matched pair; move inward
    }
    return true;   // all pairs matched; middle character (if any) is trivially OK
}

// With character filtering (LC 125) — skip non-alphanumeric before comparing
public boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
        while (left < right && !Character.isLetterOrDigit(s.charAt(left)))  { left++; }   // skip junk
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) { right--; }  // skip junk
        if (Character.toLowerCase(s.charAt(left)) !=
            Character.toLowerCase(s.charAt(right))) return false;
        left++; right--;
    }
    return true;
}
```

### Longest Palindromic Substring — Expand Around Center 中心扩展法

```java
public String longestPalindrome(String s) {
    int start = 0, maxLen = 1;
    for (int i = 0; i < s.length(); i++) {
        // Must try BOTH odd (center at i) and even (center between i and i+1) — missing either gives wrong answer
        int len1 = expand(s, i, i);       // odd-length palindromes centered at i
        int len2 = expand(s, i, i + 1);   // even-length palindromes centered between i and i+1
        int len = Math.max(len1, len2);
        if (len > maxLen) { maxLen = len; start = i - (len - 1) / 2; }   // back-calculate start from center
    }
    return s.substring(start, start + maxLen);
}

private int expand(String s, int left, int right) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        left--; right++;   // extend palindrome one layer outward
    }
    // Loop exited: s[left] != s[right] (or out of bounds). The palindrome is s[left+1..right-1]
    return right - left - 1;   // length = (right-1) - (left+1) + 1 = right - left - 1
}
```

---

## Variants 变形

| Problem | Technique | Example |
|---|---|---|
| Valid palindrome (ignore non-alnum) | Two pointer + `isLetterOrDigit` | LC 125 |
| Valid palindrome II (delete one char) | Two pointer + try deleting left OR right | LC 680 |
| Longest palindromic substring | Expand around center (odd + even) | LC 5 |
| Count palindromic substrings | Expand around center, count each expansion | LC 647 |
| Palindrome partition | Check palindrome + backtracking | LC 131 |
| Reverse string | `sb.reverse()` or two-pointer swap | LC 344 |
| Reverse words | Split + reverse order | LC 151 |

---

## Key Examples 关键例题

### Valid Palindrome II (LC 680) — Delete At Most One Char
```java
public boolean validPalindrome(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) {
        if (s.charAt(l) != s.charAt(r)) {
            return isPalin(s, l+1, r) || isPalin(s, l, r-1);
        }
        l++; r--;
    }
    return true;
}
private boolean isPalin(String s, int l, int r) {
    while (l < r) { if (s.charAt(l++) != s.charAt(r--)) return false; }
    return true;
}
```

### Count Palindromic Substrings (LC 647)
```java
public int countSubstrings(String s) {
    int count = 0;
    for (int i = 0; i < s.length(); i++) {
        count += expand(s, i, i);     // odd
        count += expand(s, i, i+1);   // even
    }
    return count;
}
private int expand(String s, int l, int r) {
    int count = 0;
    while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
        count++; l--; r++;
    }
    return count;
}
```

### StringBuilder in Backtracking
```java
private void backtrack(char[] chars, int start, StringBuilder sb, List<String> res) {
    if (start == chars.length) { res.add(sb.toString()); return; }
    for (int i = start; i < chars.length; i++) {
        sb.append(chars[i]);                          // choose
        backtrack(chars, i + 1, sb, res);            // explore
        sb.deleteCharAt(sb.length() - 1);            // unchoose
    }
}
```

---

## Common Mistake / Gotcha 常见错误

**Forgetting the even-length center:** Most people remember to try `expand(s, i, i)` (odd palindromes) but forget `expand(s, i, i+1)` (even palindromes). The string "abba" has no single-character center — its palindrome is even-length. Checking only odd centers returns "a" (length 1) instead of "abba" (length 4).

**`sb.insert(0, c)` in a hot loop:** This looks O(1) but is actually O(n) because it shifts all existing characters right. If you need a string in reverse order, append and call `sb.reverse()` once at the end — O(n) total instead of O(n²).

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Never `+` in loop | `"" + c` in a loop = O(n²); always use `StringBuilder.append()` |
| `deleteCharAt(sb.length()-1)` | Efficient O(1) removal of last char for backtracking |
| Two palindrome lengths | Always check BOTH odd (center at i) and even (center between i and i+1) |
| Expand returns length | `return right - left - 1` after loop (right and left went one past the palindrome) |
| `Character.isLetterOrDigit(c)` | Use for filtering in palindrome problems with mixed characters |
| `Character.toLowerCase(c)` | Case-insensitive comparison |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 125 Valid Palindrome, LC 344 Reverse String |
| Easy | LC 9 Palindrome Number |
| Medium | LC 5 Longest Palindromic Substring |
| Medium | LC 647 Count Palindromic Substrings |
| Medium | LC 680 Valid Palindrome II |
| Medium | LC 131 Palindrome Partitioning |

**Order:** 125 → 5 → 647 → 680 → 131

---

## One-line Summary

```
StringBuilder = O(1) append for string building in loops; palindrome = expand-around-center or two-pointer in O(n) O(1).
StringBuilder = 循环中 O(1) 追加；回文 = 中心扩展或双指针，O(n) 时间 O(1) 空间。
```
