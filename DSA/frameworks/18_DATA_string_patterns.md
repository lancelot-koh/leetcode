# 🧩 String Patterns - Character Manipulation

**Master string-specific algorithms and patterns**

---

## Interview Frequency: **3% of problems** ⭐⭐

---

## Core Patterns

### Pattern 1: Character Frequency

```java
Map<Character, Integer> freq = new HashMap<>();
for (char c : s.toCharArray()) {
    freq.put(c, freq.getOrDefault(c, 0) + 1);
}
```

### Pattern 2: Anagram Check

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    
    int[] chars = new int[26];
    for (int i = 0; i < s.length(); i++) {
        chars[s.charAt(i) - 'a']++;
        chars[t.charAt(i) - 'a']--;
    }
    
    for (int count : chars) {
        if (count != 0) return false;
    }
    return true;
}
```

### Pattern 3: Palindrome

```java
public boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) return false;
        left++;
        right--;
    }
    return true;
}
```

---

## Examples

- **Valid Parentheses:** Stack-based
- **Longest Palindromic:** DP or expand around center
- **Group Anagrams:** Frequency map
- **Implement Trie:** Prefix tree

---

**Master String Patterns. It's 3% of interviews.** 🚀
