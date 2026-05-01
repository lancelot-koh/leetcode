# T4-6 — Bit Manipulation 位运算

> **Core idea:** Use binary operations to solve problems in O(1) space and often O(1) time. Core tricks: XOR for cancellation, AND/OR/shift for bit extraction and construction, n & (n-1) to clear lowest set bit.
> **核心思想：** 用二进制运算以O(1)空间（通常O(1)时间）解决问题。核心技巧：XOR抵消、AND/OR/移位提取/构造位、n&(n-1)清除最低位1。
>
> Complexity: O(1) per bitwise operation; O(n) if iterating over bits × n elements.

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Find single non-duplicate | LC 136, 137, 260 |
| Power of 2 / 4 check | LC 231, 342 |
| Count set bits (Hamming weight) | LC 191 |
| Number of differing bits | LC 461 Hamming Distance |
| Subset enumeration via bitmask | bitmask DP |
| Swap without temp, negate without minus | interview tricks |

**Signal:** "without extra space", "O(1) space", XOR-based uniqueness, bitmask state.

---

## Core Operations 核心操作

```java
// Get bit k (0-indexed from right)
int getBit(int n, int k)   { return (n >> k) & 1; }

// Set bit k
int setBit(int n, int k)   { return n | (1 << k); }

// Clear bit k
int clearBit(int n, int k) { return n & ~(1 << k); }

// Toggle bit k
int toggleBit(int n, int k){ return n ^ (1 << k); }

// Clear lowest set bit
int clearLSB(int n)        { return n & (n - 1); }

// Extract lowest set bit
int lsb(int n)             { return n & (-n); }

// Check power of 2
boolean isPow2(int n)      { return n > 0 && (n & (n - 1)) == 0; }

// XOR trick: a ^ a = 0, a ^ 0 = a
// Count set bits (Brian Kernighan)
int countBits(int n) {
    int count = 0;
    while (n != 0) { n &= (n - 1); count++; }
    return count;
}
```

---

## Key Identities 关键恒等式

| Identity | Meaning |
|---|---|
| `a ^ a = 0` | XOR with self = 0 (cancel) |
| `a ^ 0 = a` | XOR with 0 = identity |
| `a & (a-1)` | Clears lowest set bit |
| `a & (-a)` | Extracts lowest set bit |
| `a >> k` | Divide by 2^k (arithmetic shift) |
| `a << k` | Multiply by 2^k |
| `~a + 1 = -a` | Two's complement negation |
| `a ^ b ^ b = a` | XOR twice = no-op (cancellation) |

---

## Core Templates 核心模板

### Single Number (XOR cancellation)

```java
public int singleNumber(int[] nums) {
    int result = 0;
    for (int n : nums) result ^= n;   // all pairs cancel; unique remains
    return result;
}
```

### Single Number III — two distinct singles (LC 260)

```java
public int[] singleNumber(int[] nums) {
    int xor = 0;
    for (int n : nums) xor ^= n;      // xor = a ^ b

    int diff = xor & (-xor);           // lowest bit where a and b differ
    int a = 0;
    for (int n : nums)
        if ((n & diff) != 0) a ^= n;  // isolate group with that bit set
    return new int[]{a, xor ^ a};
}
```

### Count Bits for 0..n (DP + LSB)

```java
public int[] countBits(int n) {
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++)
        dp[i] = dp[i >> 1] + (i & 1);  // dp[i] = dp[i/2] + last bit
    return dp;
}
```

---

## Variants 变形

| Variant | Technique | Example |
|---|---|---|
| Single number (pairs) | XOR all | LC 136 |
| Single number (triplets) | Count each bit mod 3 | LC 137 |
| Two single numbers | XOR → diff bit → split groups | LC 260 |
| Power of 2 | `n & (n-1) == 0` | LC 231 |
| Hamming weight | `n &= n-1` loop | LC 191 |
| Hamming distance | `Integer.bitCount(a ^ b)` | LC 461 |
| Subset enumeration | `for (int mask = 0; mask < (1<<n); mask++)` | bitmask DP |
| Reverse bits | Swap bit pairs iteratively | LC 190 |

---

## Key Examples 关键例题

### Single Number (LC 136)
```java
public int singleNumber(int[] nums) {
    int res = 0;
    for (int n : nums) res ^= n;
    return res;
}
```

### Number of 1 Bits (LC 191)
```java
public int hammingWeight(int n) {
    int count = 0;
    while (n != 0) { n &= n - 1; count++; }
    return count;
}
```

### Counting Bits (LC 338)
```java
public int[] countBits(int n) {
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++)
        dp[i] = dp[i >> 1] + (i & 1);
    return dp;
}
```

### Missing Number (LC 268) — XOR with indices
```java
public int missingNumber(int[] nums) {
    int result = nums.length;
    for (int i = 0; i < nums.length; i++)
        result ^= i ^ nums[i];
    return result;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `1 << k` overflow for k ≥ 31 | Use `1L << k` for long arithmetic |
| Signed right shift vs unsigned | Java `>>` is arithmetic (preserves sign); `>>>` is logical |
| `Integer.bitCount(n)` | Built-in for Hamming weight — use in interviews if allowed |
| XOR order doesn't matter | Commutative and associative: `a^b^c = c^b^a` |
| `n & (n-1)` clears lowest set bit | Useful for checking power of 2 and counting bits |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 136 Single Number |
| Easy | LC 191 Number of 1 Bits |
| Easy | LC 231 Power of Two |
| Easy | LC 268 Missing Number |
| Medium | LC 338 Counting Bits |
| Medium | LC 260 Single Number III |
| Medium | LC 461 Hamming Distance |
| Hard | LC 137 Single Number II |

**Order:** 136 → 191 → 231 → 268 → 338 → 461 → 260 → 137

---

## One-line Summary

```
Bit manipulation = XOR cancels pairs; n&(n-1) clears LSB; bit-by-bit DP for counting; bitmask for subset state.
位运算 = XOR抵消成对元素；n&(n-1)清最低位；逐位DP计数；位掩码表示子集状态。
```
