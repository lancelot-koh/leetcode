# T4-6 — Bit Manipulation 位运算

> **Core idea:** Use binary operations to solve problems in O(1) space and often O(1) time. Core tricks: XOR for cancellation, AND/OR/shift for bit extraction and construction, n & (n-1) to clear lowest set bit.
> **核心思想：** 用二进制运算以O(1)空间（通常O(1)时间）解决问题。核心技巧：XOR抵消、AND/OR/移位提取/构造位、n&(n-1)清除最低位1。
>
> Complexity: O(1) per bitwise operation; O(n) if iterating over bits × n elements.

---

## How It Works — Mental Model 直觉理解

Bitwise operations work on individual binary digits in parallel. XOR is the most powerful interview tool because it satisfies `a ^ a = 0` (self-cancellation) and `a ^ 0 = a` (identity), making it ideal for finding the unique element among duplicates: all paired elements cancel to zero, leaving only the singleton. The `n & (n-1)` trick works because subtracting 1 flips the lowest set bit and all bits below it; ANDing with the original clears exactly the lowest set bit. This is why `isPow2` checks `n & (n-1) == 0` — a power of two has exactly one set bit, so clearing it yields zero. Shifting right by 1 (`i >> 1`) is equivalent to integer division by 2, which makes counting bits via `dp[i] = dp[i>>1] + (i&1)` an elegant O(n) DP: every number's bit count is its right-shifted version's count plus its last bit.

**Key invariant:** XOR is commutative and associative, so `a^b^a = b` regardless of order — you can XOR elements in any sequence and paired elements will cancel.

**Common mistake:** Using `1 << k` when k ≥ 31 in Java. `1` is a 32-bit int, so `1 << 31` becomes a negative number. Use `1L << k` to work with long arithmetic when k can be large.

---

## Step-by-Step Trace (Single Number — XOR cancellation)

```
Input: [4, 1, 2, 1, 2]  Find the unique element.

result = 0
XOR 4: result = 0^4 = 4      (binary: 100)
XOR 1: result = 4^1 = 5      (binary: 101)
XOR 2: result = 5^2 = 7      (binary: 111)
XOR 1: result = 7^1 = 6      (binary: 110)  ← pair (1,1) cancels
XOR 2: result = 6^2 = 4      (binary: 100)  ← pair (2,2) cancels
Answer: 4  ✓
```

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
// Get bit k (0-indexed from right): shift bit k to position 0, then mask with 1
int getBit(int n, int k)   { return (n >> k) & 1; }

// Set bit k: OR with a mask that has only bit k set
int setBit(int n, int k)   { return n | (1 << k); }

// Clear bit k: AND with a mask that has all bits set except bit k
int clearBit(int n, int k) { return n & ~(1 << k); }

// Toggle bit k: XOR flips only the bit that matches the 1 in the mask
int toggleBit(int n, int k){ return n ^ (1 << k); }

// Clear lowest set bit: n-1 flips the lowest set bit and all bits below it; AND clears them
int clearLSB(int n)        { return n & (n - 1); }

// Extract lowest set bit: -n in two's complement = ~n+1; the lowest set bit is the only one that survives AND
int lsb(int n)             { return n & (-n); }

// Check power of 2: a power of 2 has exactly one bit set, so clearing the lowest set bit yields 0
boolean isPow2(int n)      { return n > 0 && (n & (n - 1)) == 0; }

// XOR trick: a ^ a = 0, a ^ 0 = a
// Count set bits (Brian Kernighan): each iteration removes exactly one set bit → O(number of set bits)
int countBits(int n) {
    int count = 0;
    while (n != 0) { n &= (n - 1); count++; }  // strip lowest set bit; count how many strips needed
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
    for (int n : nums) { result ^= n; }   // XOR with itself cancels → only the unique element survives
    return result;
}
```

### Single Number III — two distinct singles (LC 260)

```java
public int[] singleNumber(int[] nums) {
    int xor = 0;
    for (int n : nums) { xor ^= n; }      // xor = a ^ b (all duplicates cancelled)

    int diff = xor & (-xor);              // isolate the lowest bit where a and b differ
    int a = 0;
    for (int n : nums) {
        if ((n & diff) != 0) { a ^= n; }  // XOR only numbers with that bit set; a and b land in different groups, so one unique survives
    }
    return new int[]{a, xor ^ a};         // xor ^ a = (a^b) ^ a = b
}
```

### Count Bits for 0..n (DP + LSB)

```java
public int[] countBits(int n) {
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        dp[i] = dp[i >> 1] + (i & 1);  // i>>1 drops the last bit (already counted); (i&1) adds it back if it was 1
    }
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
    for (int n : nums) { res ^= n; }
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
    for (int i = 1; i <= n; i++) {
        dp[i] = dp[i >> 1] + (i & 1);
    }
    return dp;
}
```

### Missing Number (LC 268) — XOR with indices
```java
public int missingNumber(int[] nums) {
    int result = nums.length;
    for (int i = 0; i < nums.length; i++) {
        result ^= i ^ nums[i];
    }
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
