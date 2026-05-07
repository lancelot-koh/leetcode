# T4-11 — Fast Exponentiation (Binary Exponentiation) 快速幂

> **Core idea:** Compute `base^exp` in O(log exp) by repeatedly squaring. If exp is odd, multiply result by base. Works modulo a prime for combinatorics and large-number problems.
> **核心思想：** 通过反复平方在O(log exp)内计算`base^exp`。指数为奇数时将结果乘以base。结合模运算用于组合数学和大数问题。
>
> Complexity: O(log n) time, O(1) space (iterative).

---

## How It Works — Mental Model 直觉理解

Naively computing `base^exp` multiplies `base` by itself `exp` times — O(exp) multiplications. Fast exponentiation uses the binary representation of `exp`. If the current bit of `exp` is 1, multiply the running result by the current power of base; then square the base and shift the exponent right. This way the algorithm processes each bit of `exp` exactly once, giving O(log exp) multiplications. Think of it as: `base^13 = base^(1101₂) = base^8 × base^4 × base^1` — you accumulate only the contributions from set bits. Squaring the base each step means that after `k` steps the base variable holds `base^(2^k)` — exactly the power you need if bit `k` is set. For modular arithmetic, take `mod` after every multiplication to keep numbers small and prevent overflow.

**Key invariant:** At the start of each loop iteration, `result` holds the product of `base^(2^k)` for every bit position `k` below the current one that was set. When the loop ends, `result` is the complete answer.

**Common mistake:** Using `int n` instead of `long exp` when negating `Integer.MIN_VALUE`. `-Integer.MIN_VALUE` in int arithmetic overflows back to `Integer.MIN_VALUE` (a negative number), making the negative-exponent branch loop forever. Always cast to `long` before negating.

---

## Step-by-Step Trace

```
2^13 (exp = 13 = 1101₂):

exp=13 (odd)  → result *= 2 → result=2;   base=2²=4;  exp=6
exp=6  (even) → skip;                      base=4²=16; exp=3
exp=3  (odd)  → result *= 16 → result=32;  base=16²=256; exp=1
exp=1  (odd)  → result *= 256 → result=8192; base=256²; exp=0
Loop ends → return 8192 = 2^13  ✓
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| `x^n` with large n | LC 50 |
| `x^n mod p` for combinatorics | nCr mod prime |
| Matrix exponentiation for DP speedup | Fibonacci in O(log n) |
| Modular inverse (Fermat's little theorem) | `a^(p-2) mod p` |

**Signal:** "power", "exponent", large n (n > 10⁶), "mod 10^9+7", combinatorics with nCr.

---

## Core Templates 核心模板

### Iterative fast power

```java
public double myPow(double x, int n) {
    long exp = n;                              // cast to long: negating Integer.MIN_VALUE overflows int
    if (exp < 0) { x = 1 / x; exp = -exp; }  // x^(-n) = (1/x)^n; now exp is guaranteed non-negative
    double result = 1.0;

    while (exp > 0) {
        if ((exp & 1) == 1) { result *= x; }  // current bit is 1 → accumulate this power of base into result
        x *= x;                                // square the base: x now represents base^(2^(k+1))
        exp >>= 1;                             // shift to inspect the next higher bit
    }
    return result;
}
```

### Fast power modulo (for combinatorics)

```java
static final int MOD = 1_000_000_007;

long powMod(long base, long exp, long mod) {
    long result = 1;
    base %= mod;              // reduce base before any multiplication to keep numbers small from the start
    while (exp > 0) {
        if ((exp & 1) == 1) { result = result * base % mod; }  // take mod immediately: result * base < mod² which fits in long
        base = base * base % mod;  // square and reduce; without %mod, base² could overflow long
        exp >>= 1;
    }
    return result;
}
```

### Modular inverse (Fermat's little theorem, p must be prime)

```java
long modInverse(long a, long p) {
    return powMod(a, p - 2, p);   // a^(p-2) mod p = a^(-1) mod p
}
```

### nCr mod prime (combinatorics)

```java
long nCr(int n, int r, int p) {
    if (r > n) { return 0; }
    long[] fact = new long[n + 1];
    fact[0] = 1;
    for (int i = 1; i <= n; i++) { fact[i] = fact[i-1] * i % p; }

    return fact[n] * modInverse(fact[r], p) % p * modInverse(fact[n-r], p) % p;
}
```

---

## Variants 变形

| Variant | Approach | Example |
|---|---|---|
| Floating point power | Iterative with `1/x` for negative exp | LC 50 |
| Integer power | Same + handle `n = Integer.MIN_VALUE` | LC 50 |
| Power mod prime | `result * base % mod` each step | combinatorics |
| Modular inverse | `powMod(a, p-2, p)` | nCr problems |
| Matrix exponentiation | Replace multiply with matrix multiply | Fibonacci O(log n) |

---

## Key Examples 关键例题

### Pow(x, n) (LC 50)
```java
public double myPow(double x, int n) {
    long exp = n;
    if (exp < 0) { x = 1 / x; exp = -exp; }
    double result = 1.0;
    while (exp > 0) {
        if ((exp & 1) == 1) { result *= x; }
        x *= x;
        exp >>= 1;
    }
    return result;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `long exp = n` for `Integer.MIN_VALUE` | `-Integer.MIN_VALUE` overflows int; cast to long first |
| `base %= mod` before loop | Reduces large base values immediately |
| `result * base % mod` — two steps | `(result * base) % mod` not `result * (base % mod)` |
| Matrix multiply for Fibonacci | `[[1,1],[1,0]]^n` gives Fibonacci in O(log n) |
| Fermat only for prime modulus | Modular inverse via Fermat requires p to be prime |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 50 Pow(x, n) |
| Medium | LC 372 Super Pow |
| Hard | LC 1916 Count Ways to Build Rooms (nCr mod prime) |

**Order:** 50 → 372 → nCr problems

---

## One-line Summary

```
Fast power = square base, halve exponent each step; if exponent is odd, multiply result by current base. O(log n).
快速幂 = 底数平方、指数减半；指数为奇时结果乘以当前底数。O(log n)。
```
