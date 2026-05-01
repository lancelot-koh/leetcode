# T4-12 — Sieve of Eratosthenes 埃拉托斯特尼筛法

> **Core idea:** Mark multiples of each prime as composite. After processing all numbers up to √n, remaining unmarked numbers are prime. O(n log log n) time — nearly linear in practice.
> **核心思想：** 将每个质数的倍数标记为合数。处理完√n以内所有数后，剩余未标记的数即为质数。时间O(n log log n)，实践中近似线性。
>
> Complexity: O(n log log n) time, O(n) space.

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| All primes up to n | LC 204 |
| Count primes up to n | LC 204 |
| Prime factorization for many numbers | Smallest prime factor sieve |
| Check primality for many queries | Sieve once, query O(1) |

**Signal:** "count primes", "prime numbers below n", "prime factorization" with multiple queries.

**Single primality test vs Sieve:**
| Method | Time | When |
|---|---|---|
| Trial division | O(√n) per number | Single number |
| Sieve of Eratosthenes | O(n log log n) total | All primes up to n |

---

## Core Templates 核心模板

### Basic sieve

```java
public int countPrimes(int n) {
    boolean[] notPrime = new boolean[n];  // false = prime, true = composite
    int count = 0;

    for (int i = 2; i < n; i++) {
        if (!notPrime[i]) {
            count++;
            // Mark multiples of i as composite (start from i*i)
            for (long j = (long) i * i; j < n; j += i)
                notPrime[(int) j] = true;
        }
    }
    return count;
}
```

### Smallest Prime Factor (SPF) sieve — for factorization

```java
int[] spf = new int[n + 1];   // smallest prime factor
for (int i = 0; i <= n; i++) spf[i] = i;

for (int i = 2; (long) i * i <= n; i++)
    if (spf[i] == i)              // i is prime
        for (int j = i * i; j <= n; j += i)
            if (spf[j] == j) spf[j] = i;  // first time marked: i is smallest factor

// Factorize any number m in O(log m):
List<Integer> factorize(int m) {
    List<Integer> factors = new ArrayList<>();
    while (m > 1) { factors.add(spf[m]); m /= spf[m]; }
    return factors;
}
```

### Linear sieve (O(n) exact — each composite marked exactly once)

```java
int[] primes = new int[n];
int[] spf = new int[n + 1];
int cnt = 0;

for (int i = 2; i <= n; i++) {
    if (spf[i] == 0) primes[cnt++] = spf[i] = i;
    for (int j = 0; j < cnt && primes[j] <= spf[i] && (long) i * primes[j] <= n; j++)
        spf[(int)(i * primes[j])] = primes[j];
}
```

---

## Key Examples 关键例题

### Count Primes (LC 204)
```java
public int countPrimes(int n) {
    boolean[] notPrime = new boolean[n];
    int count = 0;
    for (int i = 2; i < n; i++) {
        if (!notPrime[i]) {
            count++;
            for (long j = (long) i * i; j < n; j += i)
                notPrime[(int) j] = true;
        }
    }
    return count;
}
```

---

## Variants 变形

| Variant | Approach | Use case |
|---|---|---|
| Count primes up to n | Basic sieve | LC 204 |
| All primes up to n | Store unmarked indices | Many problems |
| Smallest prime factor | SPF sieve | Fast factorization |
| Segmented sieve | Sieve in blocks for very large n | Memory constraint |
| Euler's totient φ(n) | Sieve-based φ computation | Combinatorics |

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| Start inner loop at `i*i` | Multiples smaller than i² already marked by smaller primes |
| Cast to `long` for `i*i` | `i*i` overflows int when `i > 46340` |
| Outer loop to `√n` or `n` | SPF sieve: loop to `√n`; basic sieve inner works to n naturally |
| `notPrime[0]` and `notPrime[1]` | 0 and 1 are not prime; skip or initialize to `true` |
| Memory for large n | `boolean[]` uses 1 byte per element; use `BitSet` to save 8× memory |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 204 Count Primes |
| Medium | LC 279 Perfect Squares (BFS; primes tangential) |
| Hard | LC 263/264 Ugly Number I/II |

**Order:** 204 → 263 → 264

---

## One-line Summary

```
Sieve = mark multiples of each prime starting at i²; O(n log log n) time gives all primes up to n.
筛法 = 从i²开始标记每个质数的倍数；O(n log log n)得到n以内所有质数。
```
