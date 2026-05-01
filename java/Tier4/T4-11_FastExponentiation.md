# T4-11 — Fast Exponentiation (Binary Exponentiation) 快速幂

> **Core idea:** Compute `base^exp` in O(log exp) by repeatedly squaring. If exp is odd, multiply result by base. Works modulo a prime for combinatorics and large-number problems.
> **核心思想：** 通过反复平方在O(log exp)内计算`base^exp`。指数为奇数时将结果乘以base。结合模运算用于组合数学和大数问题。
>
> Complexity: O(log n) time, O(1) space (iterative).

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
    long exp = n;
    if (exp < 0) { x = 1 / x; exp = -exp; }
    double result = 1.0;

    while (exp > 0) {
        if ((exp & 1) == 1) result *= x;   // odd exponent: multiply result
        x *= x;                             // square the base
        exp >>= 1;                          // halve the exponent
    }
    return result;
}
```

### Fast power modulo (for combinatorics)

```java
static final int MOD = 1_000_000_007;

long powMod(long base, long exp, long mod) {
    long result = 1;
    base %= mod;
    while (exp > 0) {
        if ((exp & 1) == 1) result = result * base % mod;
        base = base * base % mod;
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
    if (r > n) return 0;
    long[] fact = new long[n + 1];
    fact[0] = 1;
    for (int i = 1; i <= n; i++) fact[i] = fact[i-1] * i % p;

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
        if ((exp & 1) == 1) result *= x;
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
