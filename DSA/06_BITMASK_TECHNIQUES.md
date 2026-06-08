# 位掩码技巧和操作 | Bitmask Techniques & Operations

## 中文版本 | CHINESE VERSION

位掩码技巧和操作

用一个整数表示多个开关状态的高效方式

| 技巧 | 操作 | 时间 | 使用场景 |
|-----------|-----------|------|----------|
| 左移 | `1 << n` | O(1) | 在第n位创建bit (= 2^n) |
| OR (设置) | `mask \| (1<<i)` | O(1) | 打开第i位，添加元素 |
| AND (检查) | `mask & (1<<i)` | O(1) | 检查第i位是否打开 |
| XOR (切换) | `mask ^ (1<<i)` | O(1) | 切换第i位状态 |
| 删除 | `mask &= ~(1<<i)` | O(1) | 关闭第i位 |
| 统计1的个数 | `Integer.bitCount(mask)` | O(1) | 统计mask中有多少个1 |
| 全掩码 | `(1 << n) - 1` | O(1) | 创建所有n位都打开的掩码 |
| 检查全部 | `mask == (1<<n)-1` | O(1) | 检查所有位是否都打开 |

⸻

### 核心概念

非常好，我们先完全脱离算法。
就把位掩码当成一个：
用一个整数表示多个开关状态

本质：位掩码 = 压缩的 Set<Integer>
* 快10倍以上
* 占用极少内存（1个int = 32个状态）
* 可直接作为BFS/DP状态部分

### 基本示例：4个开关

```
bit位：  bit3  bit2  bit1  bit0
标签：    D     C     B     A
例子：    0     1     0     1  = 0101

含义：A=打开, B=关闭, C=打开, D=关闭
```

⸻

### 技巧1：左移 (<<)

作用：生成特定位置的bit

```
1 << 0 = 0001 = 1      (2^0)
1 << 1 = 0010 = 2      (2^1)
1 << 2 = 0100 = 4      (2^2)
1 << 3 = 1000 = 8      (2^3)
1 << 4 = 10000 = 16    (2^4)

规律：1 << n = 2^n
```

Java代码：
```java
for (int i = 0; i < 4; i++) {
    int bit = 1 << i;  // 0,1,2,3 -> 1,2,4,8
    System.out.println(bit);
}
```

⸻

### 技巧2：OR (|) - 添加/设置

作用：打开某个开关，添加元素到集合

```
当前：0001 (只有A)

打开C(bit2)：mask |= (1 << 2);
  0001  (A)
  OR
  0100  (C)
  -----
  0101  (A+C)

再打开D(bit3)：mask |= (1 << 3);
  0101  (A+C)
  OR
  1000  (D)
  -----
  1101  (A+C+D)
```

Java代码：
```java
int mask = 0;
mask |= (1 << 0);  // 添加A -> mask = 0001
mask |= (1 << 2);  // 添加C -> mask = 0101
mask |= (1 << 3);  // 添加D -> mask = 1101
```

应用场景：
* LC864：`mask |= (1 << (cell - 'a'));` 收集钥匙
* LC847：`mask |= (1 << nextNode);` 访问节点

⸻

### 技巧3：AND (&) - 检查

作用：检查某个开关是否打开

```
当前：1101 (A,C,D已打开)

检查A(bit0)：
  mask & (1 << 0)
  1101
  AND
  0001
  -----
  0001 (非0 -> A存在)

检查B(bit1)：
  mask & (1 << 1)
  1101
  AND
  0010
  -----
  0000 (等于0 -> B不存在)
```

Java代码：
```java
// Google最常见写法
if ((mask & (1 << i)) != 0) {
    System.out.println("第" + i + "个元素已存在");
}

// 更简洁的写法
if ((mask & (1 << i)) > 0) {
    // 元素存在
}
```

应用场景：
* LC864：检查是否有对应的钥匙
* LC847：检查是否已访问过某个节点

⸻

### 技巧4：XOR (^) - 切换

作用：反转某个bit的状态，用于配对检查

```
当前：1101

Toggle A(bit0)：mask ^= (1 << 0);
  1101
  XOR
  0001
  -----
  1100 (A被关掉)

再执行一次 Toggle A：
  1100
  XOR
  0001
  -----
  1101 (A又打开)
```

规律：
```
1 ^ 1 = 0  (相同 = 0)
0 ^ 1 = 1  (不同 = 1)
```

Java代码：
```java
int mask = 0b1101;
mask ^= (1 << 0);  // 翻转bit0 -> 0b1100
mask ^= (1 << 0);  // 翻转bit0 -> 0b1101 (回到原状)
```

应用场景：
* 配对计数（出现偶数次 = 消失，奇数次 = 存在）
* 状态翻转

⸻

### 技巧5：删除/清除某个Bit

作用：关闭特定的bit，删除元素

```
当前：1101，删除C(bit2)
mask &= ~(1 << 2);

Step 1：计算 1 << 2
  0100

Step 2：取反 ~(0100)
  1011

Step 3：AND 运算
  1101
  AND
  1011
  -----
  1001 (只剩A和D)
```

Java代码：
```java
int mask = 0b1101;
mask &= ~(1 << 2);  // 删除bit2
// 结果：0b1001
```

这个操作的原理：
* `~(1 << i)` 产生一个除了第i位外全是1的mask
* AND后只有其他位被保留，第i位被清零

⸻

### 技巧6：判断全部完成

作用：检查是否收集了所有元素（Google最爱）

```
假设有4个节点/钥匙：

生成目标mask：
targetMask = (1 << 4) - 1;
  1 << 4  = 10000  (十进制16)
  减1     = 1111   (十进制15)

检查：
if (mask == targetMask) {
    return steps;  // 全部完成
}

例如：
0000 -> 0100 -> 0110 -> 1111 (完成!)
```

Java代码：
```java
int n = 4;  // 4个元素
int targetMask = (1 << n) - 1;  // 15 = 0b1111

if (mask == targetMask) {
    System.out.println("所有元素已收集!");
}
```

应用场景（Google Hard）：
* LC847：`if (mask == (1 << n) - 1) return steps;` 访问所有节点
* LC864：`if (mask == (1 << totalKeys) - 1) return steps;` 收集所有钥匙
* 旅行商问题（TSP）：访问所有城市最小成本

⸻

### 技巧7：统计1的个数

作用：计算有多少个bit被设置

```
当前：1101

Binary.bitCount(1101) = 3
因为有3个1
```

Java代码：
```java
int mask = 0b1101;
int count = Integer.bitCount(mask);  // 返回3

// 其他语言
// C++：__builtin_popcount(mask);
// Python：bin(mask).count('1');
```

用途：
* 计算已访问节点数
* 计算已收集钥匙数
* 状态压缩后信息提取

⸻

### Google最常用的5个操作汇总

| 操作 | 代码 | 含义 | 例子 |
|------|------|------|------|
| 添加 | `mask \|= (1<<i);` | 添加元素i | 0101 -> 0111 |
| 检查 | `(mask & (1<<i)) != 0` | 元素i存在? | 0111检查bit2 = true |
| 删除 | `mask &= ~(1<<i);` | 删除元素i | 0111 -> 0101 |
| 切换 | `mask ^= (1<<i);` | 翻转元素i | 0101 -> 0111 |
| 全集 | `mask == (1<<n)-1` | 全部完成? | 1111 = true |

⸻

### 实际例子：LC864

问题：从 @ 出发收集所有钥匙，求最短路径

```java
// 状态定义
State: (row, col, keyMask)

// 核心操作
// 1. 检查是否收集所有钥匙
if (mask == (1 << totalKeys) - 1) {
    return steps;
}

// 2. 遇到钥匙时更新mask
if (cell >= 'a' && cell <= 'f') {
    nextMask |= (1 << (cell - 'a'));  // 收集
}

// 3. 检查是否有对应的钥匙
if (cell >= 'A' && cell <= 'F') {
    if ((mask & (1 << (cell - 'A'))) == 0) {
        continue;  // 没有钥匙，不能通过
    }
}
```

⸻

### 为什么用位掩码？

位掩码 = 一个超快的 Set<Integer>

```java
// 方式1：用Set（较慢）
Set<Integer> visited = new HashSet<>();
visited.add(node);
if (visited.contains(node)) { ... }
// 时间复杂度：O(log n)，需要哈希表

// 方式2：用位掩码（快10倍以上）
int mask = 0;
mask |= (1 << node);
if ((mask & (1 << node)) != 0) { ... }
// 时间复杂度：O(1)，直接bit操作
```

优势：
* ✅ 查询和更新极快 O(1)
* ✅ 内存使用极少（1个int存32个状态）
* ✅ 可直接作为BFS/DP的状态部分
* ✅ 自然支持状态压缩

劣势：
* ❌ 最多只能表示32-64个元素
* ❌ 代码可读性较低（需要理解bit操作）

⸻

### 常见面试题

| 题目 | 状态 | 核心技巧 |
|------|------|----------|
| LC847 | (node, visitedMask) | 访问所有节点 |
| LC864 | (row, col, keyMask) | 收集所有钥匙 |
| 旅行商问题 | (city, visitedMask) | 访问所有城市 |
| LC1595 | (g1Mask, g2Mask) | 连接两个组 |

⸻

### 总结

位掩码本质：用bit位作为boolean数组的压缩版本
* 单个整数可以存储多个boolean状态
* 支持快速的集合操作（添加、删除、检查）
* 是Google Hard题中状态压缩的标准工具

---

## 英文版本 | ENGLISH VERSION

Bitmask Techniques & Operations

An efficient way to use a single integer to represent multiple switch states

| Technique | Operation | Time | Use Case |
|-----------|-----------|------|----------|
| Left Shift | `1 << n` | O(1) | Create bit at position n (= 2^n) |
| OR (Set) | `mask \| (1<<i)` | O(1) | Turn on bit i, add element |
| AND (Check) | `mask & (1<<i)` | O(1) | Check if bit i is on |
| XOR (Toggle) | `mask ^ (1<<i)` | O(1) | Toggle bit i state |
| Delete | `mask &= ~(1<<i)` | O(1) | Turn off bit i |
| Count Ones | `Integer.bitCount(mask)` | O(1) | Count how many 1s in mask |
| Full Mask | `(1 << n) - 1` | O(1) | Create all n bits on |
| Check All | `mask == (1<<n)-1` | O(1) | Check if all bits are on |

⸻

### Core Concept

First, let's separate this from the algorithm completely.
Think of bitmask as:
Using a single integer to represent multiple switch states

Essence: Bitmask = Compressed Set<Integer>
* 10x+ faster
* Uses minimal memory (1 int = 32 states)
* Can be used directly as part of BFS/DP state

### Basic Example: 4 Switches

```
bit pos:  bit3  bit2  bit1  bit0
label:     D     C     B     A
example:   0     1     0     1  = 0101

meaning: A=ON, B=OFF, C=ON, D=OFF
```

⸻

### Technique 1: Left Shift (<<)

Purpose: Generate bit at specific position

```
1 << 0 = 0001 = 1      (2^0)
1 << 1 = 0010 = 2      (2^1)
1 << 2 = 0100 = 4      (2^2)
1 << 3 = 1000 = 8      (2^3)
1 << 4 = 10000 = 16    (2^4)

Pattern: 1 << n = 2^n
```

Java code:
```java
for (int i = 0; i < 4; i++) {
    int bit = 1 << i;  // 0,1,2,3 -> 1,2,4,8
    System.out.println(bit);
}
```

⸻

### Technique 2: OR (|) - Set/Add

Purpose: Turn on a switch, add element to set

```
Current: 0001 (only A)

Turn on C (bit2): mask |= (1 << 2);
  0001  (A)
  OR
  0100  (C)
  -----
  0101  (A+C)

Then turn on D (bit3): mask |= (1 << 3);
  0101  (A+C)
  OR
  1000  (D)
  -----
  1101  (A+C+D)
```

Java code:
```java
int mask = 0;
mask |= (1 << 0);  // Add A -> mask = 0001
mask |= (1 << 2);  // Add C -> mask = 0101
mask |= (1 << 3);  // Add D -> mask = 1101
```

Use cases:
* LC864: `mask |= (1 << (cell - 'a'));` collect keys
* LC847: `mask |= (1 << nextNode);` visit nodes

⸻

### Technique 3: AND (&) - Check

Purpose: Check if a switch is on

```
Current: 1101 (A,C,D on)

Check A (bit0):
  mask & (1 << 0)
  1101
  AND
  0001
  -----
  0001 (non-zero -> A exists)

Check B (bit1):
  mask & (1 << 1)
  1101
  AND
  0010
  -----
  0000 (zero -> B doesn't exist)
```

Java code:
```java
// Most common Google way
if ((mask & (1 << i)) != 0) {
    System.out.println("Element " + i + " exists");
}

// More concise way
if ((mask & (1 << i)) > 0) {
    // Element exists
}
```

Use cases:
* LC864: Check if we have the corresponding key
* LC847: Check if node has been visited

⸻

### Technique 4: XOR (^) - Toggle

Purpose: Flip a bit state, useful for pairing checks

```
Current: 1101

Toggle A (bit0): mask ^= (1 << 0);
  1101
  XOR
  0001
  -----
  1100 (A turned off)

Toggle A again:
  1100
  XOR
  0001
  -----
  1101 (A turned back on)
```

Pattern:
```
1 ^ 1 = 0  (same = 0)
0 ^ 1 = 1  (different = 1)
```

Java code:
```java
int mask = 0b1101;
mask ^= (1 << 0);  // Flip bit0 -> 0b1100
mask ^= (1 << 0);  // Flip bit0 -> 0b1101 (back to original)
```

Use cases:
* Pairing count (even occurrences = disappear, odd = present)
* State flipping

⸻

### Technique 5: Delete/Clear a Bit

Purpose: Turn off a specific bit, remove element

```
Current: 1101, delete C (bit2)
mask &= ~(1 << 2);

Step 1: Calculate 1 << 2
  0100

Step 2: Negate ~(0100)
  1011

Step 3: AND operation
  1101
  AND
  1011
  -----
  1001 (only A and D remain)
```

Java code:
```java
int mask = 0b1101;
mask &= ~(1 << 2);  // Delete bit2
// Result: 0b1001
```

How this works:
* `~(1 << i)` produces a mask with all 1s except position i
* AND keeps other bits, clears bit i to 0

⸻

### Technique 6: Check if All Complete

Purpose: Check if all elements are collected (Google favorite)

```
Assume 4 nodes/keys:

Generate target mask:
targetMask = (1 << 4) - 1;
  1 << 4  = 10000  (decimal 16)
  minus 1 = 1111   (decimal 15)

Check:
if (mask == targetMask) {
    return steps;  // All complete
}

Example:
0000 -> 0100 -> 0110 -> 1111 (done!)
```

Java code:
```java
int n = 4;  // 4 elements
int targetMask = (1 << n) - 1;  // 15 = 0b1111

if (mask == targetMask) {
    System.out.println("All elements collected!");
}
```

Use cases (Google Hard):
* LC847: `if (mask == (1 << n) - 1) return steps;` visit all nodes
* LC864: `if (mask == (1 << totalKeys) - 1) return steps;` collect all keys
* TSP: Minimum cost to visit all cities

⸻

### Technique 7: Count Number of 1s

Purpose: Count how many bits are set

```
Current: 1101

Integer.bitCount(1101) = 3
Because there are 3 ones
```

Java code:
```java
int mask = 0b1101;
int count = Integer.bitCount(mask);  // Returns 3

// Other languages
// C++: __builtin_popcount(mask);
// Python: bin(mask).count('1');
```

Uses:
* Count visited nodes
* Count collected keys
* Extract info from compressed state

⸻

### Top 5 Operations Used by Google

| Operation | Code | Meaning | Example |
|-----------|------|---------|---------|
| Add | `mask \|= (1<<i);` | Add element i | 0101 -> 0111 |
| Check | `(mask & (1<<i)) != 0` | Element i exists? | 0111 check bit2 = true |
| Delete | `mask &= ~(1<<i);` | Delete element i | 0111 -> 0101 |
| Toggle | `mask ^= (1<<i);` | Flip element i | 0101 -> 0111 |
| Check All | `mask == (1<<n)-1` | All complete? | 1111 = true |

⸻

### Real Example: LC864

Problem: Start from @ and collect all keys, find shortest path

```java
// State definition
State: (row, col, keyMask)

// Core operations
// 1. Check if all keys are collected
if (mask == (1 << totalKeys) - 1) {
    return steps;
}

// 2. Update mask when finding a key
if (cell >= 'a' && cell <= 'f') {
    nextMask |= (1 << (cell - 'a'));  // Collect
}

// 3. Check if we have the corresponding key
if (cell >= 'A' && cell <= 'F') {
    if ((mask & (1 << (cell - 'A'))) == 0) {
        continue;  // No key, cannot pass
    }
}
```

⸻

### Why Use Bitmask?

Bitmask = A super fast Set<Integer>

```java
// Way 1: Using Set (slower)
Set<Integer> visited = new HashSet<>();
visited.add(node);
if (visited.contains(node)) { ... }
// Time complexity: O(log n), requires hash table

// Way 2: Using Bitmask (10x+ faster)
int mask = 0;
mask |= (1 << node);
if ((mask & (1 << node)) != 0) { ... }
// Time complexity: O(1), direct bit operations
```

Advantages:
* ✅ Query and update extremely fast O(1)
* ✅ Minimal memory usage (1 int stores 32 states)
* ✅ Can be used directly as part of BFS/DP state
* ✅ Naturally supports state compression

Disadvantages:
* ❌ Can only represent 32-64 elements max
* ❌ Lower code readability (need to understand bit ops)

⸻

### Common Interview Problems

| Problem | State | Core Technique |
|---------|-------|-----------------|
| LC847 | (node, visitedMask) | Visit all nodes |
| LC864 | (row, col, keyMask) | Collect all keys |
| TSP | (city, visitedMask) | Visit all cities |
| LC1595 | (g1Mask, g2Mask) | Connect two groups |

⸻

### Summary

Bitmask essence: Use bit positions as a compressed version of boolean array
* A single integer can store multiple boolean states
* Support fast set operations (add, delete, check)
* Standard tool for state compression in Google Hard problems
