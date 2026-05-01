# T2-9 — Difference Array 差分数组

> **Core idea:** For range update problems, store `diff[l] += val` and `diff[r+1] -= val`. A single prefix-sum pass reconstructs the result array. O(1) per update, O(n) reconstruction.
> **核心思想：** 区间更新问题中，只记录`diff[l]+=val`和`diff[r+1]-=val`。一次前缀和还原结果数组。每次更新O(1)，重建O(n)。
>
> Complexity: O(1) per range update, O(n) to reconstruct. Better than O(n) per update on raw array.
> Full reference: `PrefixSum/desciption.md` Pattern 6

---

## When to Use 什么时候用

| Trigger | Example |
|---|---|
| Many range increment/decrement updates, then read all | LC 1109, 370 |
| Overlapping time intervals: find max overlap | LC 253 (alternative), 1854 |
| Paint a range repeatedly, final colors | LC 732 (simpler version) |
| Booking system: check capacity | LC 1109 |
| Car pooling (multiple pickups/dropoffs) | LC 1094 |

**Signal:** "range update", "add k to every element from l to r", then "query all elements at the end." No random mid-sequence queries.

**When NOT to use:** If you need to query individual positions mid-sequence (random access), use a Segment Tree or BIT instead.

---

## Core Templates 核心模板

### Basic difference array

```java
int[] diff = new int[n + 1];   // extra slot avoids bounds check

// Range update: add val to [l, r] inclusive
diff[l]     += val;
diff[r + 1] -= val;

// Reconstruct the result array (prefix sum of diff)
int[] result = new int[n];
result[0] = diff[0];
for (int i = 1; i < n; i++)
    result[i] = result[i-1] + diff[i];
```

### Reconstruct in-place (when diff IS the result)

```java
for (int i = 1; i < diff.length; i++)
    diff[i] += diff[i-1];
// diff[i] now holds the actual value at position i
```

### Event-based overlap counting

```java
int[] diff = new int[maxTime + 2];

for (int[] booking : bookings) {
    int l = booking[0], r = booking[1], seats = booking[2];
    diff[l]     += seats;
    diff[r + 1] -= seats;
}

// Prefix sum gives occupancy at each time unit
int[] answer = new int[n];
int cur = 0;
for (int i = 1; i <= n; i++) {
    cur += diff[i];
    answer[i-1] = cur;
}
```

---

## Variants 变形

| Variant | Key idea | Example |
|---|---|---|
| Range add, read all | Diff array + prefix sum | LC 370, 1109 |
| Car pooling (capacity check) | Build diff on trip routes; check max ≤ capacity | LC 1094 |
| Max overlap of intervals | Diff array; find peak of prefix sum | Sweep line variant |
| 2D difference array | 4-corner update: `diff[r1][c1]++`, etc. | LC 2132 |
| Circular range update | Handle wrap-around (two updates if r < l) | interview variant |

---

## Key Examples 关键例题

### Corporate Flight Bookings (LC 1109)
```java
public int[] corpFlightBookings(int[][] bookings, int n) {
    int[] diff = new int[n + 1];
    for (int[] b : bookings) {
        diff[b[0] - 1] += b[2];             // 1-indexed → 0-indexed
        if (b[1] < n) diff[b[1]] -= b[2];   // r+1 might be out of range
    }
    int[] answer = new int[n];
    answer[0] = diff[0];
    for (int i = 1; i < n; i++)
        answer[i] = answer[i-1] + diff[i];
    return answer;
}
```

### Car Pooling (LC 1094)
```java
public boolean carPooling(int[][] trips, int capacity) {
    int[] diff = new int[1001];   // max location = 1000
    for (int[] t : trips) {
        diff[t[1]] += t[0];       // passengers board at t[1]
        diff[t[2]] -= t[0];       // passengers alight at t[2]
    }
    int cur = 0;
    for (int d : diff) {
        cur += d;
        if (cur > capacity) return false;
    }
    return true;
}
```

### Range Addition (LC 370 — premium)
```java
public int[] getModifiedArray(int length, int[][] updates) {
    int[] diff = new int[length + 1];
    for (int[] u : updates) {
        diff[u[0]] += u[2];
        diff[u[1] + 1] -= u[2];
    }
    int[] result = new int[length];
    result[0] = diff[0];
    for (int i = 1; i < length; i++)
        result[i] = result[i-1] + diff[i];
    return result;
}
```

---

## Skills & Pitfalls 技巧与陷阱

| Skill | Detail |
|---|---|
| `diff[r+1]` needs extra slot | Allocate `n+1` or `maxVal+1` to avoid index out of bounds |
| 1-indexed input → subtract 1 | LC 1109 uses 1-indexed flights: `b[0]-1`, `b[1]` (for `b[1]+1-1`) |
| Off-by-one on `r+1` | Update closes AFTER position `r`, so decrement at `r+1` |
| No random queries mid-process | Diff array gives final snapshot only; use Segment Tree for point queries |
| Board at start, alight at end | Car pooling: passengers are ON the car from `from` (inclusive) to `to` (exclusive) |

---

## Practice Problems 练习题

| Difficulty | Problem |
|---|---|
| Medium | LC 1109 Corporate Flight Bookings |
| Medium | LC 1094 Car Pooling |
| Medium | LC 370 Range Addition (premium) |
| Medium | LC 1854 Maximum Population Year |
| Hard | LC 2132 Stamping the Grid (2D diff array) |

**Order:** 1109 → 1094 → 1854 → 370 → 2132

---

## One-line Summary

```
Difference array = mark range boundaries with ±val; one prefix-sum pass reconstructs all values in O(n).
差分数组 = 在区间边界标记±val；一次前缀和O(n)还原所有值。
```
