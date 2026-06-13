# 🔢 Bit Manipulation - Binary Tricks

**Master XOR and bit operations**

---

## Interview Frequency: **2% of problems** ⭐⭐

---

## Common Patterns

### XOR Properties
- a ^ a = 0
- a ^ 0 = a
- a ^ b = b ^ a (commutative)

### Find Single Number
```java
int single = 0;
for (int num : nums) {
    single ^= num;  // Pairs cancel out
}
return single;
```

### Power of 2
```java
boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```

---

**Master Bit Manipulation. It's 2% of interviews.** 🚀
