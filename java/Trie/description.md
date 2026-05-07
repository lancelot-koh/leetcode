# Trie (Prefix Tree) 字典树 / 前缀树

> **Core idea:** A tree where each path from root to a node spells a prefix. Enables O(L) insert, search, and prefix lookup (L = word length) — faster than HashMap for prefix-based queries.
> **核心思想：** 从根到每个节点的路径拼出一个前缀。O(L) 插入、查找、前缀查询（L=词长），在前缀类查询上比HashMap更优。
>
> Complexity: O(L) per operation; O(total chars) space.
> Linked Java code: `collections/CommonPattern.java`

---

## 1. When to Use 什么时候用

**Trigger keywords 关键词**

| English | 中文 |
|---|---|
| starts with / prefix search | 前缀搜索 / 以...开头 |
| auto-complete / search suggestions | 自动补全 / 搜索提示 |
| word search in board | 单词搜索（矩阵） |
| longest common prefix | 最长公共前缀 |
| dictionary operations | 字典操作 |
| IP routing / longest prefix match | IP路由 / 最长前缀匹配 |
| XOR maximization (binary trie) | XOR最大化（二进制前缀树） |

**Trie vs HashMap**

| Operation | HashMap | Trie |
|---|---|---|
| Exact match | O(L) | O(L) |
| Prefix search ("starts with") | O(n·L) full scan | O(L) |
| All words with prefix | O(n·L) full scan | O(prefix length + results) |
| Space | O(total chars) | O(total chars) — similar |

Use Trie when you need **prefix operations** frequently.
需要频繁进行**前缀操作**时使用Trie。

---

## 2. Core Implementation 核心实现

### Array-based TrieNode (26 lowercase letters) 数组实现

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd = false;
}

class Trie {
    TrieNode root = new TrieNode();

    void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (cur.children[idx] == null) {
                cur.children[idx] = new TrieNode();
            }
            cur = cur.children[idx];
        }
        cur.isEnd = true;
    }

    boolean search(String word) {
        TrieNode node = getNode(word);
        return node != null && node.isEnd;   // must end exactly here
    }

    boolean startsWith(String prefix) {
        return getNode(prefix) != null;      // any node at end of prefix = found
    }

    private TrieNode getNode(String s) {
        TrieNode cur = root;
        for (char c : s.toCharArray()) {
            cur = cur.children[c - 'a'];
            if (cur == null) { return null; }
        }
        return cur;
    }
}
```

### HashMap-based TrieNode (arbitrary alphabet) HashMap实现

```java
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEnd = false;
}
```

Use HashMap when: alphabet is large (digits + letters + symbols), or non-lowercase input.
当字母表较大（数字+字母+符号）或非小写字母输入时使用HashMap。

---

## 3. Quick Decision Guide 快速判断

```
Basic insert / search / startsWith?             → Pattern 1: Standard Trie
Find all words from a board that exist in dict? → Pattern 2: Trie + DFS (Word Search II)
Build trie then query prefixes?                 → Pattern 3: Prefix queries
XOR of max pair?                                → Pattern 4: Binary Trie
```

---

## 4. Patterns 模式

---

### Pattern 1 — Standard Trie (LC 208) 标准字典树

Implement `insert`, `search`, `startsWith`. See Core Implementation above.

**Variants 变形**

| Variant | Change to TrieNode | Example |
|---|---|---|
| Count words with prefix | add `count` field, increment on insert | custom |
| Delete a word | mark `isEnd = false`, prune empty nodes | custom |
| Replace words with shortest root | search for first root match | LC 648 |

**Example: Replace Words (LC 648)**

```java
public String replaceWords(List<String> dictionary, String sentence) {
    Trie trie = new Trie();
    for (String root : dictionary) { trie.insert(root); }

    StringBuilder sb = new StringBuilder();
    for (String word : sentence.split(" ")) {
        if (sb.length() > 0) { sb.append(" "); }
        // Find shortest prefix root
        TrieNode cur = trie.root;
        StringBuilder prefix = new StringBuilder();
        boolean found = false;
        for (char c : word.toCharArray()) {
            if (cur.children[c - 'a'] == null) { break; }
            cur = cur.children[c - 'a'];
            prefix.append(c);
            if (cur.isEnd) { found = true; break; }
        }
        sb.append(found ? prefix : word);
    }
    return sb.toString();
}
```

---

### Pattern 2 — Trie + DFS (Word Search II) 字典树 + DFS

**When:** find all words from a dictionary that exist in a 2D board.
**适用：** 在二维矩阵中找出字典里所有存在的单词。

**Key insight 核心原理**

Build a Trie from the dictionary. Run DFS on the board; traverse the Trie simultaneously. When `node.isEnd == true`, a word is found.
把字典建成Trie，在矩阵上DFS的同时在Trie上走。遇到 `isEnd = true` 就找到一个单词。

This avoids re-searching for each word individually — all words share prefixes in one DFS pass.
避免对每个单词单独搜索，所有单词在一次DFS中共享前缀。

**Template 模板**

```java
public List<String> findWords(char[][] board, String[] words) {
    Trie trie = new Trie();
    for (String w : words) { trie.insert(w); }

    List<String> result = new ArrayList<>();
    int m = board.length, n = board[0].length;

    for (int r = 0; r < m; r++) {
        for (int c = 0; c < n; c++) {
            dfs(board, r, c, trie.root, result);
        }
    }

    return result;
}

private void dfs(char[][] board, int r, int c, TrieNode node, List<String> result) {
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) { return; }
    char ch = board[r][c];
    if (ch == '#' || node.children[ch - 'a'] == null) { return; }

    TrieNode next = node.children[ch - 'a'];
    if (next.word != null) {
        result.add(next.word);
        next.word = null;        // avoid duplicates
    }

    board[r][c] = '#';           // mark visited
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    for (int[] d : dirs) { dfs(board, r + d[0], c + d[1], next, result); }
    board[r][c] = ch;            // restore
}
```

**Optimization:** Store `word` directly in the TrieNode instead of `isEnd` — avoids reconstructing the word.
在TrieNode中直接存储 `word` 而非 `isEnd`，避免重建单词。

**Pruning:** Remove TrieNode leaf after finding a word to avoid redundant DFS paths.

---

### Pattern 3 — Prefix Frequency / Count Queries 前缀频率查询

**When:** count how many inserted words start with a given prefix.
**适用：** 统计多少个已插入单词以某前缀开头。

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    int prefixCount = 0;    // how many words pass through this node
    int wordCount = 0;      // how many words end exactly here
}

void insert(String word) {
    TrieNode cur = root;
    for (char c : word.toCharArray()) {
        int idx = c - 'a';
        if (cur.children[idx] == null) { cur.children[idx] = new TrieNode(); }
        cur = cur.children[idx];
        cur.prefixCount++;
    }
    cur.wordCount++;
}

int countPrefix(String prefix) {
    TrieNode node = getNode(prefix);
    return node == null ? 0 : node.prefixCount;
}
```

---

### Pattern 4 — Binary Trie (XOR Maximization) 二进制字典树（XOR最大化）

**When:** find the pair of numbers with maximum XOR.
**适用：** 找出异或值最大的一对数字。

**Key insight 核心原理**

Insert numbers bit by bit (from MSB to LSB). For each number, greedily choose the opposite bit at each Trie level — maximizes XOR.
逐位（从最高位到最低位）插入数字。查询时贪心选择相反的位，最大化XOR。

```java
class BinaryTrie {
    int[][] children = new int[32 * 10000][2];  // or dynamic nodes
    int size = 1;

    void insert(int num) {
        int cur = 0;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (children[cur][bit] == 0) { children[cur][bit] = size++; }
            cur = children[cur][bit];
        }
    }

    int maxXOR(int num) {
        int cur = 0, xor = 0;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            int want = 1 - bit;            // want opposite bit
            if (children[cur][want] != 0) { cur = children[cur][want]; xor |= (1 << i); }
            else                           { cur = children[cur][bit]; }
        }
        return xor;
    }
}
```

**Variants 变形**

| Variant | Example |
|---|---|
| Maximum XOR of two numbers | LC 421 |
| Maximum XOR with element from array | LC 1707 |

---

## 5. Advanced Skills 进阶技能

### Skill 1 — Array vs HashMap Children 数组 vs HashMap子节点

| `TrieNode[] children = new TrieNode[26]` | `Map<Character, TrieNode> children` |
|---|---|
| O(1) child access | O(1) average, but more memory per node |
| Fixed alphabet (a-z) | Arbitrary characters |
| Less memory overhead | More flexible |

For lowercase letters only: use array. Otherwise: use HashMap.

### Skill 2 — Store Word in TrieNode 在节点中存储单词

Instead of reconstructing the word when `isEnd == true`, store the word directly:

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    String word = null;   // non-null when this node ends a word
}
// insert: node.word = word;  instead of  node.isEnd = true;
```

Avoids path reconstruction during DFS — especially useful in Word Search II.

### Skill 3 — Pruning Dead Branches 剪掉无用分支

After collecting a word in Word Search II, remove the word and prune empty leaf nodes:

```java
next.word = null;
// optionally: prune leaves with no children and no word to speed up future DFS
```

### Skill 4 — Trie Height Limit 限制Trie高度

For XOR or other fixed-length problems, fix the Trie height to the number of bits (e.g., 32 for integers). For variable-length strings, height = max word length.

---

## 6. Interview Script 面试话术

**English:**
> I'd use a Trie because the problem requires [prefix search / finding words starting with X / matching multiple words simultaneously]. A Trie stores characters node by node, so each insert and lookup is O(L) where L is the word length. Unlike a HashMap, it naturally groups words with common prefixes, enabling efficient prefix queries and simultaneous multi-word matching.

**中文：**
> 我会用字典树，因为题目需要[前缀搜索/找以X开头的单词/同时匹配多个单词]。Trie逐字符存储，插入和查找都是 O(L)（L是词长）。与HashMap不同，Trie自然地把有公共前缀的单词聚在一起，支持高效的前缀查询和多单词同时匹配。

---

## 7. Practice Problems by Pattern 按模式练习题

| Pattern | Must-do LeetCode |
|---|---|
| 1. Standard Trie | 208, 648, 677 |
| 2. Trie + DFS | 212 |
| 3. Prefix count | 677, 1268 |
| 4. Binary Trie | 421, 1707 |

**Recommended order:** 208 → 648 → 212 → 421 → 677

---

## 8. One-line Summary 一句话总结

```
Trie = O(L) prefix lookup by sharing common prefixes across all stored words.
字典树 = 通过共享公共前缀，O(L) 实现前缀查找。
```
