# 🧮 Math & Number Theory - Mathematical Properties

**GCD, primes, modulo, and number properties**

---

## Interview Frequency: **1% of problems** ⭐

---

## Common Patterns

### GCD (Greatest Common Divisor)
```java
int gcd(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}
```

### Prime Check
```java
boolean isPrime(int n) {
    if (n < 2) return false;
    for (int i = 2; i * i <= n; i++) {
        if (n % i == 0) return false;
    }
    return true;
}
```

---

**Master Math. It's 1% of interviews.** 🚀
