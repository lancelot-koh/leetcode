# Algorithm Practice — Day 1

---

## Sliding Window

### Longest Substring Without Repeating Characters (LC 3)

**Problem:**
Given a string `s`, find the length of the longest substring without repeating characters.

**Example:**
```
Input: s = "abcabcbb"   Output: 3  // "abc"
Input: s = "bbbbb"      Output: 1  // "b"
```

**Constraints:**
- `0 <= s.length <= 5 * 10^4`
- `s` consists of English letters, digits, symbols and spaces.

```java
public int lengthOfLongestSubstring(String s) {
    
    if (s.length() < 2) return s.length();
    int count = 0;

    Set<Character> set = new HashSet<>();
    int left = 0;
    for(int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        while (set.contains(c)) { // remove duplicate char
            set.remove(s.charAt(left));
            left++;
        }    
        set.add(c);
        count = Math.max(count, i - left + 1);
    }
    return count;
}


public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> map = new HashMap<>();
    int left = 0;
    int max = 0;

    for(int right = 0; right < s.length(); right++) {
       char c = s.charAt(right);
       if (map.containsKey(c)) { 
            left = Math.max(left, map.get(c) + 1);   // move to the last index of char
        }
        map.put(c, right);
        max = Math.max(max, right - left + 1);
    }
    return max;
}
```

---

### Minimum Window Substring (LC 76)

**Problem:**
Given two strings `s` and `t`, return the minimum window substring of `s` such that every character in `t` (including duplicates) is included in the window. Return `""` if no such window exists.

**Example:**
```
Input: s = "ADOBECODEBANC", t = "ABC"   Output: "BANC"
Input: s = "a", t = "a"                 Output: "a"
```

**Constraints:**
- `1 <= s.length, t.length <= 10^5`
- `s` and `t` consist of uppercase and lowercase English letters.
- The answer is unique.

```java
public String minWindow(String s, String t) {
    if (s == null || t == null || s.length() < t.length()) return "";

    int[] freq = new int[128];
    for(char c : t.toCharArray) { freq[c]++; }

    int start = 0;
    int minLen = Integer.MAX_VALUE;
    int left = 0;
    int required = t.length();

    for(int right = 0; right < s.length(); right++) {
         char rightChar = s.charAt(right);
         if (freq[rightChar] > 0) {
            required--;
         }
         freq[rightChar]--;

        while (required == 0) {
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                start = left;
            }

            char leftChar = s.charAt(left);
            freq[leftChar]++;
            if (freq[leftChar] > 0) {
                required++;
            }
            left++;
        }
    }
    return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
}
```

---

## Prefix Sum + HashMap

### Subarray Sum Equals K (LC 560)

**Problem:**
Given an array of integers `nums` and an integer `k`, return the total number of contiguous subarrays whose sum equals `k`.

**Example:**
```
Input: nums = [1,1,1], k = 2   Output: 2
Input: nums = [1,2,3], k = 3   Output: 2
```

**Constraints:**
- `1 <= nums.length <= 2 * 10^4`
- `-1000 <= nums[i] <= 1000`
- `-10^7 <= k <= 10^7`

```java
public int subarraySum(int[] nums, int k) {
    // prefixSum map, base case map.put(0, 1)
    // check if (prefixSum - k) exists in map
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);

    int count = 0;
    int sum = 0;

    for(int i = 0; i < nums.length; i++) {
        sum += nums[i];
        
        count += map.getOrDefault(sum - k, 0);
        map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return count;

}
```

---

### Continuous Subarray Sum (LC 523)

**Problem:**
Given an integer array `nums` and an integer `k`, return `true` if there exists a subarray of length **at least 2** whose sum is a **multiple of k**.

**Example:**
```
Input: nums = [23,2,4,6,7], k = 6   Output: true   // [2,4] sums to 6
Input: nums = [23,2,6,4,7], k = 13  Output: false
```

**Constraints:**
- `1 <= nums.length <= 10^5`
- `0 <= nums[i] <= 10^9`
- `0 <= sum(nums[i]) <= 2^31 - 1`
- `1 <= k <= 2^31 - 1`

```java
public boolean checkSubarraySum(int[] nums, int k) {
    // store (prefixSum % k) -> earliest index, base case map.put(0, -1)
    // same remainder seen with index gap >= 2 means valid subarray
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, -1);

    int sum = 0;
    for(int i = 0; i < nums.length; i++) {
        sum += nums[i];
        int reminder = k == 0 ? sum : sum % k;
        if(map.containsKey(reminder) && i - map.get(reminder) >= 2) {
            return true;
        } else {
            map.put(reminder, i);
        }
    }
    return false;
}
```

---

## Two Pointers

### Two Sum II (LC 167)

**Problem:**
Given a **1-indexed** sorted array `numbers`, find two numbers that add up to `target`. Return `[index1, index2]` (1-indexed). Exactly one solution exists; you may not use the same element twice.

**Example:**
```
Input: numbers = [2,7,11,15], target = 9   Output: [1,2]
Input: numbers = [2,3,4], target = 6       Output: [1,3]
```

**Constraints:**
- `2 <= numbers.length <= 3 * 10^4`
- `-1000 <= numbers[i] <= 1000`
- `numbers` is sorted in non-decreasing order.
- Exactly one valid solution exists.

```java
public int[] twoSum(int[] numbers, int target) {
    int left = 0;
    int right = numbers.length - 1;
    while(left < right) {
        int sum = numbers[left] + numbers[right];
        if (sum == target) {
             return new int[]{left, right};
        } else if (sum > target) {
            right--;
        } else {
            left++;
        }
    }
    return new int[]{};
}
```

---

### 3Sum (LC 15)

**Problem:**
Given an integer array `nums`, return all unique triplets `[nums[i], nums[j], nums[k]]` such that `i != j != k` and `nums[i] + nums[j] + nums[k] == 0`.

**Example:**
```
Input: nums = [-1,0,1,2,-1,-4]   Output: [[-1,-1,2],[-1,0,1]]
Input: nums = [0,0,0]             Output: [[0,0,0]]
```

**Constraints:**
- `3 <= nums.length <= 3000`
- `-10^5 <= nums[i] <= 10^5`

```java
public List<List<Integer>> threeSum(int[] nums) {
    // sort first, fix nums[i], two-pointer on remainder
    // skip duplicates for i, left, right
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    int left, right;

    for(int k = 0;  k < nums.length; k++) {
        if(k > 0 && nums[k] == nums[k-1]) continue;
        
        int left = k + 1;
        int right = nums.length - 1;
        while(left < right) {
            int sum = nums[k] + nums[left] + nums[right];
            if (sum == 0) {
                return res.add(Arrays.asList(nums[k], nums[left], nums[right]));
                left++;
                right--;

                while(left < right && nums[left] == nums[left -1]) left++;
                while(left < right && nums[right] == nums[right+1]) right--;
            } else if(sum < 0) {
                left++;
            } else {
                right--;
            }
        }
    }
    return res;
}
```

---

### Container With Most Water (LC 11)

**Problem:**
Given an integer array `height` of length `n`, find two lines that form a container holding the most water. Return the maximum water amount.

**Example:**
```
Input: height = [1,8,6,2,5,4,8,3,7]   Output: 49
Input: height = [1,1]                  Output: 1
```

**Constraints:**
- `n == height.length`
- `2 <= n <= 10^5`
- `0 <= height[i] <= 10^4`

```java
public int maxArea(int[] height) {
    // left=0, right=n-1; area = min(h[l],h[r])*(r-l)
    // move the pointer with the smaller height inward
    int left = 0, right = height.length - 1;
    int maxArea = 0;
    while(left < right) {
        int h = Math.min(height[left], height[right]) {
        int w = right - left;
        maxArea = Math.max(maxArea, w * h);    
        if (height[left] < height[right]) {
            left++;
        } else {
            right--;
        }
    }
    return maxArea;
}
```

---

## Binary Search

### Koko Eating Bananas (LC 875)

**Problem:**
There are `n` piles of bananas. Koko eats at speed `k` bananas/hour (one pile per hour; partial pile costs a full hour). Guards return in `h` hours. Find the **minimum** integer `k` to finish all piles within `h` hours.

**Example:**
```
Input: piles = [3,6,7,11], h = 8          Output: 4
Input: piles = [30,11,23,4,20], h = 5     Output: 30
```

**Constraints:**
- `1 <= piles.length <= 10^4`
- `piles.length <= h <= 10^9`
- `1 <= piles[i] <= 10^9`

```java
public int minEatingSpeed(int[] piles, int h) {
    // binary search lo=1, hi=max(piles)
    // hours needed at speed mid = sum(ceil(pile/mid))
}
```

---

### Ship Packages Within D Days (LC 1011)

**Problem:**
Given package weights in order and a number of `days`, find the **minimum ship capacity** to ship all packages within `days` days (packages must be shipped in order).

**Example:**
```
Input: weights = [1,2,3,4,5,6,7,8,9,10], days = 5   Output: 15
Input: weights = [3,2,2,4,1,4], days = 3             Output: 6
```

**Constraints:**
- `1 <= days <= weights.length <= 5 * 10^4`
- `1 <= weights[i] <= 500`

```java
public int shipWithinDays(int[] weights, int days) {
    // binary search lo=max(weights), hi=sum(weights)
    // greedily count days needed for capacity mid

}
```

---

## Monotonic Stack

### Daily Temperatures (LC 739)

**Problem:**
Given an array `temperatures`, return an array `answer` where `answer[i]` is the number of days to wait after day `i` for a warmer temperature. If no future day is warmer, `answer[i] = 0`.

**Example:**
```
Input: temperatures = [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]
```

**Constraints:**
- `1 <= temperatures.length <= 10^5`
- `30 <= temperatures[i] <= 100`

```java
public int[] dailyTemperatures(int[] temperatures) {
    // monotonic decreasing stack of indices
    // when temperatures[i] > temperatures[stack.peek()], pop and compute gap
    int[] res = new int[temperatures.length];
    LinkedList<Integer> stack = new LinkedList<>();
    for(int i = 0; i < temperatures.length; i++) {
        while(!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            int index = stack.pop();
            res[index] = i - index;
        }
        stack.push(i);
    }
    return res;
}
```

---
---

## Data Structures

---

## Union Find (Disjoint Set Union)

Union Find tracks a partition of `n` elements into disjoint sets. Two core operations:
- **find(x)** — returns the root representative of x's set. Path compression flattens the tree on every call.
- **union(x, y)** — merges the two sets. Union by rank keeps the tree shallow by attaching the smaller-rank root under the larger one.

Both run in amortised **O(α(n))** ≈ O(1). Classic uses: cycle detection, connected components, Kruskal's MST.

```java
class UnionFind {
    private int[] parent;
    private int[] rank;
    private int count;

    public UnionFind(int n) {
        // TODO
        parent = new int[n];
        rank = new int[n];
        count = n;
        for(int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int x) {
        // path compression
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public boolean union(int x, int y) {
        // union by rank; return false if already in same set
        int px = find(x);
        int py = find(y);

        if (px == py) {
            return false;
        }
        if (rank[px] > rank[py]) {
            parent[py] = px;
        } else if (rank[px]) < rank[py]) {
            parent[px] = py;
        } else {
            parent[py] = px;
            rank[px]++;
        }
        count--;
        return true;
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
```

---

## LRU Cache (LC 146)

Evicts the **Least Recently Used** entry when capacity is exceeded. All operations must be **O(1)**.

Implementation: **HashMap** (key → node) for O(1) lookup + **Doubly Linked List** for O(1) order maintenance.
- Head sentinel = most recently used side.
- Tail sentinel = least recently used side.
- On every `get`/`put`, move the accessed node to the head. On overflow, remove the node just before the tail.

```java
class LRUCache {

    class Node {
        int key, val;
        Node prev, next;
    }

    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head, tail; // sentinels

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node curr = map.get(key);
        removeNode(curr);
        moveToFront(curr);
    
        return curr.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node curr = map.get(key);
            curr.val = value;

            removeNode(curr);
            moveToFront(curr);
        } else {
            Node node = new Node(key, value);
            map.put(key, node);
            moveToFront(node);

            if(map.size() > this.capacity) {
                Node lru = tail.prev;
                removeNode(lru);
                map.remove(lru.key);
            }
        }
    }

    private void moveToFront(Node node) {
        // H <-> E
        // H <-> N  <-> E
        node.next = head.next;
        head.next.prev = node;

        node.prev = head;
        head.next = node;

    }

    private void removeNode(Node node) {
        // TODO
        // A - B  - C
        node.next.prev = node.prev
        node.prev.next = node.next;
    }
}
```

---

## Trie (Prefix Tree) (LC 208)

A tree where each node represents one character. A path from root to a node spells a prefix; `isEnd = true` marks a complete word.

All operations are **O(m)** where m = word length. Shared prefixes are stored only once.
Classic uses: autocomplete, prefix search, Word Search II.

```java
class Trie {

    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        // walk/create nodes char by char, mark isEnd on last node
        TrieNode curr = root;

        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int idx = c - 'a';
            if (curr.children[idx] == null) {
                curr.children[idx] = new TrieNode();
            }
            curr = curr.children[idx];
        }
        curr.isEnd = true;
    }

    public boolean search(String word) {
        // walk nodes; return false if child missing, else return node.isEnd
        TrieNode curr = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int idx = c - 'a';
            if (curr.children[idx] == null) {
                return false;
            }
            curr = curr.children[idx];
        }
        return curr != null && curr.isEnd;

    }

    public boolean startsWith(String prefix) {
        // same walk as search but no isEnd check needed
        TrieNode curr = root;
        for(int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            int idx = c - 'a';
            if(curr.children[idx] == null) {
                return false;
            }
            curr = curr.children[idx];
        }
        return true;
    }
}
```
