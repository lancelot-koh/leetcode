# T4-10 — GCD / LCM 最大公约数/最小公倍数

> **Core idea:** GCD via Euclidean algorithm: `gcd(a, b) = gcd(b, a % b)` until b = 0. LCM from GCD: `lcm(a, b) = a / gcd(a, b) * b` (divide first to prevent overflow).
> **核心思想：** GCD用欧几里得算法：`gcd(a,b) = gcd(b, a%b)`直到b=0。LCM由GCD推导：`lcm(a,b) = a / gcd(a,b) * b`（先除防溢出）。
>
> Complexity: O(log(min(a, b))) for GCD.

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
    return b == 0 ? a : gcd(b, a % b);
}

// Iterative version (avoids stack):
int gcd(int a, int b) {
    while (b != 0) { int tmp = b; b = a % b; a = tmp; }
    return a;
}
```

### LCM

```java
long lcm(long a, long b) {
    return a / gcd(a, b) * b;   // divide FIRST to prevent overflow
}
```

### GCD of array

```java
int gcdArray(int[] nums) {
    int result = nums[0];
    for (int i = 1; i < nums.length; i++)
        result = gcd(result, nums[i]);
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
    if (!(str1 + str2).equals(str2 + str1)) return "";  // no common pattern
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
