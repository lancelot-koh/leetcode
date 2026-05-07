# T4-10 — GCD / LCM 最大公约数/最小公倍数

> **Core idea:** GCD via Euclidean algorithm: `gcd(a, b) = gcd(b, a % b)` until b = 0. LCM from GCD: `lcm(a, b) = a / gcd(a, b) * b` (divide first to prevent overflow).
> **核心思想：** GCD用欧几里得算法：`gcd(a,b) = gcd(b, a%b)`直到b=0。LCM由GCD推导：`lcm(a,b) = a / gcd(a,b) * b`（先除防溢出）。
>
> Complexity: O(log(min(a, b))) for GCD.

---

## How It Works — Mental Model 直觉理解

The Euclidean algorithm is based on one key insight: `gcd(a, b) = gcd(b, a mod b)`. Why? Any common divisor of `a` and `b` also divides `a - b`, and by extension `a mod b = a - (a/b)*b`. Conversely, any common divisor of `b` and `a mod b` also divides `a`. So the two pairs share exactly the same set of common divisors. Replacing `(a, b)` with `(b, a mod b)` strictly reduces the larger number (since `a mod b < b`), guaranteeing convergence. The number of steps is bounded by O(log(min(a,b))) because the pair decreases at least as fast as the Fibonacci sequence in the worst case. LCM follows from the fundamental theorem: `a × b = gcd(a,b) × lcm(a,b)`, rearranged as `lcm = a/gcd(a,b) × b` — divide first to avoid overflow.

**Key invariant:** At every recursive step, `gcd(a, b) = gcd(b, a % b)`. The algorithm maintains this invariant by substitution until b reaches 0, at which point a is the GCD.

**Common mistake:** Computing `a * b / gcd(a, b)` for LCM. If `a` and `b` are both around `10^9`, their product overflows a 32-bit int (and even a 64-bit long in extreme cases). Always divide first: `(a / gcd(a, b)) * b`.

---

## Step-by-Step Trace (Euclidean GCD)

```
gcd(48, 18):
  gcd(48, 18) → gcd(18, 48%18=12)
  gcd(18, 12) → gcd(12, 18%12=6)
  gcd(12, 6)  → gcd(6, 12%6=0)
  gcd(6, 0)   → return 6  ✓

lcm(48, 18) = 48 / 6 * 18 = 8 * 18 = 144  ✓
```

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Simplify fractions | LC 1071, fraction problems |
| Find common period / cycle length | scheduling problems |
| Check if a is divisible by b | `a % b == 0` |
| Tiling: when can a rectangle tile another? | GCD of dimensions |
| Number theory: coprime check | `gcd(a, b) == 1` |

**Signal:** fractions, divisibility, "common factor", "least common multiple", "period."

---

## Core Templates 核心模板

### GCD (Euclidean algorithm)

```java
int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);  // base: gcd(a,0)=a; step: gcd(a,b)=gcd(b, a%b)
}

// Iterative version (avoids stack):
int gcd(int a, int b) {
    while (b != 0) { int tmp = b; b = a % b; a = tmp; }  // keep replacing (a,b) with (b, a%b) until b=0
    return a;  // when b=0, a holds the GCD
}
```

### LCM

```java
long lcm(long a, long b) {
    return a / gcd(a, b) * b;   // divide FIRST: a/gcd gives a smaller number before multiplying by b, preventing overflow
}
```

### GCD of array

```java
int gcdArray(int[] nums) {
    int result = nums[0];
    for (int i = 1; i < nums.length; i++) {
        result = gcd(result, nums[i]);
    }
    return result;
}
```

### Java built-in (Java 9+)

```java
import java.math.BigInteger;

int g = BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
```

---

## Key Properties 关键性质

| Property | Formula |
|---|---|
| `gcd(a, 0) = a` | Base case |
| `gcd(a, b) = gcd(b, a % b)` | Recursive step |
| `lcm(a, b) = a * b / gcd(a, b)` | Relationship |
| Coprime: `gcd(a, b) == 1` | No common factor > 1 |
| `gcd(a, b)` divides `a + b` | Useful for divisibility proofs |
| GCD of n numbers | Apply pairwise left-to-right |

---

## Key Examples 关键例题

### Greatest Common Divisor of Strings (LC 1071)
```java
public String gcdOfStrings(String str1, String str2) {
    if (!(str1 + str2).equals(str2 + str1)) { return ""; }  // no common pattern
    int g = gcd(str1.length(), str2.length());
    return str1.substring(0, g);
}

int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }
```

### Fraction Addition and Subtraction (LC 592)
```java
// For each fraction a/b, maintain running numerator/denominator
// Add: a/b + c/d = (a*d + b*c) / (b*d)  then simplify by gcd
int g = gcd(Math.abs(num), denom);
num /= g; denom /= g;
```

### Check Array Formation Through Concatenation (uses divisibility)
```java
// Check if arr[i] % gcd(all) == 0 for all elements
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `a / gcd(a,b) * b` not `a * b / gcd` | Divide first: `a * b` can overflow int even when result fits |
| Always `Math.abs()` for negative inputs | `gcd(-12, 8)` should return 4; take abs before computing |
| `gcd(0, 0) = 0` by convention | Edge case: handle if both inputs can be 0 |
| LCM of 0 | `lcm(0, x) = 0` mathematically; guard in code |
| `gcd` of array = running gcd | `gcd(gcd(a, b), c)` = `gcd(a, b, c)` — associative |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Easy | LC 1071 Greatest Common Divisor of Strings |
| Medium | LC 592 Fraction Addition and Subtraction |
| Medium | LC 858 Mirror Reflection (use GCD) |
| Medium | LC 1979 Find Greatest Common Divisor of Array |

**Order:** 1979 → 1071 → 858 → 592

---

## One-line Summary

```
GCD = Euclidean: gcd(a,b) = gcd(b, a%b); LCM = a/gcd(a,b)*b (divide first to prevent overflow).
GCD = 欧几里得：gcd(a,b)=gcd(b,a%b)；LCM = a/gcd(a,b)*b（先除防溢出）。
```
